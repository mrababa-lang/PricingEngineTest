@smoke @ic_ALAIN
Feature: Pricing engine smoke validation

  Scenario: Validate all quotations for ALAIN using a multi-quote response
    Given environment and IC selected
    When the pricing API is called
    Then status code is 200
    And all returned quotations match configured rules for that IC
