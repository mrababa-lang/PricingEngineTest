@regression @ic_ALAIN
Feature: Pricing engine boundary checks

  Scenario Outline: Validate age and vehicle value boundaries for ALAIN
    Given environment and IC selected
    And request payload is loaded from "testdata/requests/common/multi-quote-request.json"
    And request override for "<caseName>" is applied
    When the pricing API is called
    Then status code is 200
    And all returned quotations match configured rules for that IC

    Examples:
      | caseName          |
      | minAgeMinValue    |
      | maxAgeMaxValue    |
