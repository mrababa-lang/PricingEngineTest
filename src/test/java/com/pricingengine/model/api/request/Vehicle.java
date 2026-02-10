package com.pricingengine.model.api.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public class Vehicle {
    @JsonAlias("IdentityTypeCode")
    private Integer identityTypeCode;
    @JsonAlias("MakeCode")
    private String makeCode;
    @JsonAlias("Make")
    private String make;
    @JsonAlias("ModelCode")
    private String modelCode;
    @JsonAlias("Model")
    private String model;
    @JsonAlias("BodyCategoryCode")
    private String bodyCategoryCode;
    @JsonAlias("ManufactureYear")
    private Integer manufactureYear;
    @JsonAlias("PassengersNo")
    private Integer noOfPassengers;
    @JsonAlias("Cylinders")
    private Integer noOfCylinders;
    @JsonAlias("RegistrationDate")
    private String registrationDate;
    private Integer registrationAgeInMonths;
    @JsonAlias("EstimatedValue")
    private BigDecimal estimatedValue;
    @JsonAlias("UsageCode")
    private String usageCode;
    @JsonAlias("FuelType")
    private String fuelType;
    private Boolean isElectric;
    private Boolean isChinese;

    public Integer getIdentityTypeCode() { return identityTypeCode; }
    public void setIdentityTypeCode(Integer identityTypeCode) { this.identityTypeCode = identityTypeCode; }
    public String getMakeCode() { return makeCode; }
    public void setMakeCode(String makeCode) { this.makeCode = makeCode; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getBodyCategoryCode() { return bodyCategoryCode; }
    public void setBodyCategoryCode(String bodyCategoryCode) { this.bodyCategoryCode = bodyCategoryCode; }
    public Integer getManufactureYear() { return manufactureYear; }
    public void setManufactureYear(Integer manufactureYear) { this.manufactureYear = manufactureYear; }
    public Integer getNoOfPassengers() { return noOfPassengers; }
    public void setNoOfPassengers(Integer noOfPassengers) { this.noOfPassengers = noOfPassengers; }
    public Integer getNoOfCylinders() { return noOfCylinders; }
    public void setNoOfCylinders(Integer noOfCylinders) { this.noOfCylinders = noOfCylinders; }
    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
    public Integer getRegistrationAgeInMonths() { return registrationAgeInMonths; }
    public void setRegistrationAgeInMonths(Integer registrationAgeInMonths) { this.registrationAgeInMonths = registrationAgeInMonths; }
    public BigDecimal getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }
    public String getUsageCode() { return usageCode; }
    public void setUsageCode(String usageCode) { this.usageCode = usageCode; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public Boolean getIsElectric() { return isElectric; }
    public void setIsElectric(Boolean electric) { isElectric = electric; }
    public Boolean getIsChinese() { return isChinese; }
    public void setIsChinese(Boolean chinese) { isChinese = chinese; }
}
