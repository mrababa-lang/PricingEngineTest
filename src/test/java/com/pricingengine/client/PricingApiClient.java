package com.pricingengine.client;

import com.pricingengine.model.RuntimeConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PricingApiClient {

    public Response quote(RuntimeConfig config, String body) {
        return RestAssured.given()
                .baseUri(config.baseUrl)
                .basePath(config.endpointPath)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + config.auth.token)
                .body(body)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }
}
