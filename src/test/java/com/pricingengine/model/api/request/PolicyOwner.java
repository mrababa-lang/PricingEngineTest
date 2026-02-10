package com.pricingengine.model.api.request;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PolicyOwner {
    @JsonAlias("IdentityTypeCode")
    private Integer identityTypeCode;
    @JsonAlias({"EmiratesIDNo", "CommercialNo"})
    private String ownerIdentifier;
    @JsonAlias("Age")
    private Integer age;
    @JsonAlias("DateOfBirth")
    private String dateOfBirth;
    private Integer licenseIssueAgeInYears;
    private Integer licenseIssueAgeInMonths;
    @JsonAlias("NationalityCode")
    private String nationalityCountryCode;

    public Integer getIdentityTypeCode() { return identityTypeCode; }
    public void setIdentityTypeCode(Integer identityTypeCode) { this.identityTypeCode = identityTypeCode; }
    public String getOwnerIdentifier() { return ownerIdentifier; }
    public void setOwnerIdentifier(String ownerIdentifier) { this.ownerIdentifier = ownerIdentifier; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Integer getLicenseIssueAgeInYears() { return licenseIssueAgeInYears; }
    public void setLicenseIssueAgeInYears(Integer licenseIssueAgeInYears) { this.licenseIssueAgeInYears = licenseIssueAgeInYears; }
    public Integer getLicenseIssueAgeInMonths() { return licenseIssueAgeInMonths; }
    public void setLicenseIssueAgeInMonths(Integer licenseIssueAgeInMonths) { this.licenseIssueAgeInMonths = licenseIssueAgeInMonths; }
    public String getNationalityCountryCode() { return nationalityCountryCode; }
    public void setNationalityCountryCode(String nationalityCountryCode) { this.nationalityCountryCode = nationalityCountryCode; }
}
