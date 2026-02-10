# Pricing Engine API Test Framework

BDD API test framework for validating pricing quotations against IC-configured business rules.

## Run

```bash
mvn test -Dic=ALAIN -Denv=local
mvn test -Dic=ALAIN -Denv=uat -Dcucumber.filter.tags="@smoke"
```

## Structure

- `src/test/resources/ic/*.yaml`: per-IC business rules (config-only onboarding).
- `src/test/resources/config`: global + environment layered config.
- `src/test/resources/testdata/requests`: JSON request templates.
- `src/test/resources/features`: Cucumber smoke/regression specs.
- `src/test/java/com/pricingengine/validator`: reusable validators.

## Reporting

Extent report output: `target/ExtentReport_<IC>_<ENV>.html`.
