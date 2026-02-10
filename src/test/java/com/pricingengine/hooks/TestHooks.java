package com.pricingengine.hooks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pricingengine.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TestHooks {
    public static final ScenarioContext CONTEXT = new ScenarioContext();
    public static Scenario SCENARIO;
    private static WireMockServer wireMockServer;

    @Before
    public void before(Scenario scenario) {
        SCENARIO = scenario;
        String env = System.getProperty("env", "local");
        if ("local".equalsIgnoreCase(env) && wireMockServer == null) {
            wireMockServer = new WireMockServer(8089);
            wireMockServer.start();
            wireMockServer.stubFor(post(urlEqualTo("/pricing-engine/quote"))
                    .willReturn(okJson(loadResponse())));
        }
        scenario.log("Running with env=" + env + ", ic=" + System.getProperty("ic", "ALAIN"));
    }

    @After
    public void after() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
        SCENARIO = null;
    }

    private String loadResponse() {
        try {
            return new String(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("testdata/responses/multi-quote-response.json").readAllBytes());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load mock response", e);
        }
    }
}
