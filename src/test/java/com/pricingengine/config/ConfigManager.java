package com.pricingengine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.RuntimeConfig;

import java.io.IOException;
import java.io.InputStream;

public final class ConfigManager {
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    private ConfigManager() {
    }

    public static RuntimeConfig loadRuntimeConfig(String env) {
        RuntimeConfig global = readYaml("config/global.yaml", RuntimeConfig.class);
        RuntimeConfig envConfig = readYaml("config/env/" + env + ".yaml", RuntimeConfig.class);

        RuntimeConfig config = new RuntimeConfig();
        config.timeoutMs = global.timeoutMs;
        config.maskFields = global.maskFields;
        config.endpointPath = global.endpointPath;
        config.baseUrl = systemOrDefault("baseUrl", envConfig.baseUrl);
        config.auth = envConfig.auth;

        if (config.baseUrl == null || config.baseUrl.isBlank()) {
            throw new IllegalStateException("Missing required field: baseUrl in env config");
        }
        if (config.endpointPath == null || config.endpointPath.isBlank()) {
            throw new IllegalStateException("Missing required field: endpointPath in global config");
        }
        return config;
    }

    public static RuleConfig loadIcRules(String ic) {
        RuleConfig rules = readYaml("ic/" + ic + ".yaml", RuleConfig.class);
        validateRules(rules);
        return rules;
    }

    private static void validateRules(RuleConfig rules) {
        if (rules.icCode == null || rules.icCode.isBlank()) {
            throw new IllegalStateException("Required rule field missing: icCode");
        }
        if (rules.customerRules == null || rules.customerRules.minAge == null || rules.customerRules.maxAge == null) {
            throw new IllegalStateException("Required rule field missing: customerRules.minAge/maxAge");
        }
        if (rules.vehicleRules == null || rules.vehicleRules.allowedVehicleInsuranceTypes == null) {
            throw new IllegalStateException("Required rule field missing: vehicleRules.allowedVehicleInsuranceTypes");
        }
    }

    private static <T> T readYaml(String path, Class<T> type) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("Configuration missing: " + path);
            }
            return YAML_MAPPER.readValue(is, type);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid rule/config format for " + path, e);
        }
    }

    private static String systemOrDefault(String key, String defaultValue) {
        String value = System.getProperty(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
