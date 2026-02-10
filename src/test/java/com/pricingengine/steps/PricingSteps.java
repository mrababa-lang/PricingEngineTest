package com.pricingengine.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricingengine.client.PricingApiClient;
import com.pricingengine.config.ConfigManager;
import com.pricingengine.context.ScenarioContext;
import com.pricingengine.hooks.TestHooks;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.request.PricingRequestFactory;
import com.pricingengine.model.api.response.QuotationResponse;
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
        context.requestPayload = PricingRequestFactory.createDefaultRequest();
        context.requestLocked = false;
        assertEquals(ic, context.ruleConfig.icCode, "IC mismatch between runtime and rule file");

        List<String> issues = RequestSanityValidator.validate(context.requestPayload);
        assertTrue(issues.isEmpty(), "Request sanity failed: " + String.join(" | ", issues));
    }

    @And("request payload is loaded from {string}")
    public void requestPayloadIsLoaded(String requestPath) throws Exception {
        assertRequestNotLocked();
        byte[] bytes = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestPath).readAllBytes();
        context.requestPayload = MAPPER.readValue(bytes, PricingEngineRequest.class);

        List<String> issues = RequestSanityValidator.validate(context.requestPayload);
        assertTrue(issues.isEmpty(), "Request sanity failed: " + String.join(" | ", issues));
    }

    @And("request override for {string} is applied")
    public void requestOverrideIsApplied(String caseName) {
        assertRequestNotLocked();
        switch (caseName) {
            case "minAgeMinValue" -> {
                context.requestPayload.getPolicyOwner().setAge(context.ruleConfig.customerRules.minAge);
                context.requestPayload.getVehicle().setEstimatedValue(context.ruleConfig.vehicleRules.minEstimatedValue);
            }
            case "maxAgeMaxValue" -> {
                context.requestPayload.getPolicyOwner().setAge(context.ruleConfig.customerRules.maxAge);
                context.requestPayload.getVehicle().setEstimatedValue(context.ruleConfig.vehicleRules.maxEstimatedValue);
            }
            default -> throw new IllegalArgumentException("Unsupported override case: " + caseName);
        }
    }

    @And("set policy owner age to {int}")
    public void setPolicyOwnerAgeTo(int age) {
        assertRequestNotLocked();
        context.requestPayload.getPolicyOwner().setAge(age);
    }

    @And("set vehicle manufacture year to {int}")
    public void setVehicleManufactureYearTo(int year) {
        assertRequestNotLocked();
        context.requestPayload.getVehicle().setManufactureYear(year);
    }

    @And("set estimated vehicle value to {double}")
    public void setEstimatedVehicleValueTo(double value) {
        assertRequestNotLocked();
        context.requestPayload.getVehicle().setEstimatedValue(java.math.BigDecimal.valueOf(value));
    }

    @And("set claim history to {int}")
    public void setClaimHistoryTo(int claimHistory) {
        assertRequestNotLocked();
        context.requestPayload.setClaimHistory(claimHistory);
    }

    @When("the pricing API is called")
    public void pricingApiCalled() throws Exception {
        String request = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(context.requestPayload);
        context.response = apiClient.quote(context.runtimeConfig, request);

        String body = context.response.getBody().asString();
        try {
            context.responsePayload = MAPPER.readValue(body, new TypeReference<>() {});
        } catch (Exception exception) {
            throw new AssertionError("Response deserialization failed: " + exception.getMessage(), exception);
        }

        context.requestLocked = true;

        TestHooks.SCENARIO.log("Request Body:\n" + request);
        TestHooks.SCENARIO.log("Response Body:\n" + body);
        TestHooks.SCENARIO.attach(request.getBytes(StandardCharsets.UTF_8), "application/json", "request-payload");
        TestHooks.SCENARIO.attach(body.getBytes(StandardCharsets.UTF_8), "application/json", "response-payload");
    }

    @Then("status code is {int}")
    public void statusCodeIs(int expectedStatus) {
        assertEquals(expectedStatus, context.response.getStatusCode(), "Unexpected HTTP status code");
    }

    @And("all returned quotations match configured rules for that IC")
    public void allReturnedQuotationsMatchConfiguredRules() {
        assertNotNull(context.responsePayload, "Response is not valid quotation payload");

        RuleValidationEngine engine = new RuleValidationEngine();
        List<ValidationError> errors = engine.validateAll(context.requestPayload, context.responsePayload, context.ruleConfig);

        if (!errors.isEmpty()) {
            TestHooks.SCENARIO.log("Rule failures:\n" +
                    errors.stream().map(ValidationError::toString).reduce((a, b) -> a + "\n" + b).orElse("unknown"));
        }

        assertTrue(errors.isEmpty(), () -> "Quotation validation failed:\n" +
                errors.stream().map(ValidationError::toString).reduce((a, b) -> a + "\n" + b).orElse("unknown"));
    }

    private void assertRequestNotLocked() {
        assertFalse(context.requestLocked, "Request object is immutable after API call. Create a new request instance.");
    }
}
