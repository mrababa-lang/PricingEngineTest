package com.pricingengine.context;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.RuntimeConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;
import io.restassured.response.Response;

import java.util.List;

public class ScenarioContext {
    public RuntimeConfig runtimeConfig;
    public RuleConfig ruleConfig;
    public PricingEngineRequest requestPayload;
    public Response response;
    public List<QuotationResponse> responsePayload;
    public boolean requestLocked;
}
