package com.pricingengine.validator;

import com.pricingengine.model.RuleConfig;
import com.pricingengine.model.api.request.PricingEngineRequest;
import com.pricingengine.model.api.response.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

public class QuotationSchemaValidator implements Validator {

    @Override
    public List<ValidationError> validate(PricingEngineRequest request, QuotationResponse quotation, RuleConfig config) {
        List<ValidationError> errors = new ArrayList<>();
        if (quotation.getQuotationNo() == null) {
            errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), "quotationNo present", "missing"));
        }
        if (quotation.getPlanCode() == null) {
            errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), "planCode present", "missing"));
        }
        if (quotation.getVehicleInsuranceType() == null) {
            errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), "vehicleInsuranceType present", "missing"));
        }
        if (quotation.getBasePremium() == null) {
            errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), "basePremium present", "missing"));
        }
        if (quotation.getPolicyPremiumWithoutVat() == null) {
            errors.add(new ValidationError("MANDATORY_FIELD", quotationRef(quotation), "policyPremiumWithoutVat present", "missing"));
        }
        return errors;
    }

    private String quotationRef(QuotationResponse quotation) {
        return String.valueOf(quotation.getQuotationNo()) + "/" + String.valueOf(quotation.getPlanCode());
    }
}
