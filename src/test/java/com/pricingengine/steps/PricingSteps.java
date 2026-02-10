package com.pricingengine.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pricingengine.client.PricingApiClient;
import com.pricingengine.config.ConfigManager;
import com.pricingengine.context.ScenarioContext;
import com.pricingengine.hooks.TestHooks;
import com.pricingengine.validator.RequestSanityValidator;
import com.pricingengine.validator.RuleValidationEngine;
import com.pricingengine.validator.ValidationError;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PricingSteps {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ScenarioContext context = TestHooks.CONTEXT;
    private final PricingApiClient apiClient = new PricingApiClient();

    @Given("environment and IC selected")
    public void environmentAndIcSelected() {
        String env = System.getProperty("env", "local");
        String ic = System.getProperty("ic", "ALAIN");
        context.runtimeConfig = ConfigManager.loadRuntimeConfig(env);
        context.ruleConfig = ConfigManager.loadIcRules(ic);
        assertEquals(ic, context.ruleConfig.icCode, "IC mismatch between runtime and rule file");
    }

    @And("request payload is loaded from {string}")
    public void requestPayloadIsLoaded(String requestPath) throws Exception {
        byte[] bytes = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestPath).readAllBytes();
        context.requestPayload = (ObjectNode) MAPPER.readTree(bytes);

        List<String> issues = RequestSanityValidator.validate(context.requestPayload);
        assertTrue(issues.isEmpty(), "Request sanity failed: " + String.join(" | ", issues));
    }


    @And("request override for {string} is applied")
    public void requestOverrideIsApplied(String caseName) {
        switch (caseName) {
            case "minAgeMinValue" -> {
                context.requestPayload.with("policyOwner").put("age", context.ruleConfig.customerRules.minAge);
                context.requestPayload.with("vehicle").put("estimatedValue", context.ruleConfig.vehicleRules.minEstimatedValue);
            }
            case "maxAgeMaxValue" -> {
                context.requestPayload.with("policyOwner").put("age", context.ruleConfig.customerRules.maxAge);
                context.requestPayload.with("vehicle").put("estimatedValue", context.ruleConfig.vehicleRules.maxEstimatedValue);
            }
            default -> throw new IllegalArgumentException("Unsupported override case: " + caseName);
        }
    }

    @When("the pricing API is called")
    public void pricingApiCalled() throws Exception {
        String request = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(context.requestPayload);
        context.response = apiClient.quote(context.runtimeConfig, request);

        String body = context.response.getBody().asString();
        context.responseJson = MAPPER.readTree(body);

        TestHooks.SCENARIO.attach(request.getBytes(StandardCharsets.UTF_8), "application/json", "request-payload");
        TestHooks.SCENARIO.attach(body.getBytes(StandardCharsets.UTF_8), "application/json", "response-payload");
    }

    @Then("status code is {int}")
    public void statusCodeIs(int expectedStatus) {
        assertEquals(expectedStatus, context.response.getStatusCode(), "Unexpected HTTP status code");
    }

    @And("all returned quotations match configured rules for that IC")
    public void allReturnedQuotationsMatchConfiguredRules() {
        assertNotNull(context.responseJson, "Response is not valid JSON");

        RuleValidationEngine engine = new RuleValidationEngine();
        List<ValidationError> errors = engine.validateAll(context.requestPayload, context.responseJson, context.ruleConfig);

        if (!errors.isEmpty()) {
            TestHooks.SCENARIO.log("Rule failures:\n" +
                    errors.stream().map(ValidationError::toString).reduce((a, b) -> a + "\n" + b).orElse("unknown"));
        }

        assertTrue(errors.isEmpty(), () -> "Quotation validation failed:\n" +
                errors.stream().map(ValidationError::toString).reduce((a, b) -> a + "\n" + b).orElse("unknown"));
    }
}
