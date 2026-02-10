package com.pricingengine.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.pricingengine.steps,com.pricingengine.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary,com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @ignore")
public class CucumberTestSuite {
}
