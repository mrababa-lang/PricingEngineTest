package com.pricingengine.model;

import java.util.List;

public class RuntimeConfig {
    public int timeoutMs;
    public List<String> maskFields;
    public String endpointPath;
    public String baseUrl;
    public Auth auth;

    public static class Auth {
        public String type;
        public String token;
    }
}
