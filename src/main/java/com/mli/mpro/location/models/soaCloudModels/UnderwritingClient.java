package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnderwritingClient {
    @JsonProperty("isMedCase")
    private String isMedCase;

    @JsonProperty("underWritingCategory")
    private String underWritingCategory;

    @JsonProperty("counterOfferMarkStatus")
    private String counterOfferMarkStatus;

    @JsonProperty("clientLevelPolicyRejection")
    private String clientLevelPolicyRejection;

    @JsonProperty("clientLevelPolicyLoading")
    private String clientLevelPolicyLoading;

    public String getIsMedCase() {
        return isMedCase;
    }

    public void setIsMedCase(String isMedCase) {
        this.isMedCase = isMedCase;
    }

    public String getUnderWritingCategory() {
        return underWritingCategory;
    }

    public void setUnderWritingCategory(String underWritingCategory) {
        this.underWritingCategory = underWritingCategory;
    }

    public String getCounterOfferMarkStatus() {
        return counterOfferMarkStatus;
    }

    public void setCounterOfferMarkStatus(String counterOfferMarkStatus) {
        this.counterOfferMarkStatus = counterOfferMarkStatus;
    }

    public String getClientLevelPolicyRejection() {
        return clientLevelPolicyRejection;
    }

    public void setClientLevelPolicyRejection(String clientLevelPolicyRejection) {
        this.clientLevelPolicyRejection = clientLevelPolicyRejection;
    }

    public String getClientLevelPolicyLoading() {
        return clientLevelPolicyLoading;
    }

    public void setClientLevelPolicyLoading(String clientLevelPolicyLoading) {
        this.clientLevelPolicyLoading = clientLevelPolicyLoading;
    }

    @Override
    public String toString() {
        return "UnderwritingClient{" +
                "isMedCase='" + isMedCase + '\'' +
                ", underWritingCategory='" + underWritingCategory + '\'' +
                ", counterOfferMarkStatus='" + counterOfferMarkStatus + '\'' +
                ", clientLevelPolicyRejection='" + clientLevelPolicyRejection + '\'' +
                ", clientLevelPolicyLoading='" + clientLevelPolicyLoading + '\'' +
                '}';
    }
}
