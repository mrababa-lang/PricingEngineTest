package com.pricingengine.context;

import com.fasterxml.jackson.databind.JsonNode;
import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.RuntimeConfig;
import io.restassured.response.Response;

public class ScenarioContext {
    public RuntimeConfig runtimeConfig;
    public RuleConfig ruleConfig;
    public JsonNode requestPayload;
    public Response response;
    public JsonNode responseJson;
}
