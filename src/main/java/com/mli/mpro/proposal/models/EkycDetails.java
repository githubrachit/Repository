package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"isPanMatchPanApi", "isPanWaiver", "isDobWaiver", "dobProofWaiverSource"})
public class EkycDetails {
    @JsonProperty("isPanMatchPanApi")
    private String isPanMatchPanApi;
    @JsonProperty("isPanWaiver")
    private String isPanWaiver;
    @JsonProperty("isDobWaiver")
    private String isDobWaiver;
    @JsonProperty("dobProofWaiverSource")
    private String dobProofWaiverSource;
    @JsonProperty("isHaltMedicalwaiver")
    private String isHaltMedicalWaiver;
    @JsonProperty("isHaltIncomeProofWaiver")
    private String isHaltIncomeProofWaiver;
    @JsonProperty("videoMerFlag")
    private String videoMerFlag;

    @JsonProperty("ekycConsentReceived")
    private String ekycConsentReceived;

    public EkycDetails() {
    }

    public EkycDetails(String isPANMatchPANAPI, String isPANWaiver, String isDOBWaiver, String DOBProofWaiverSource,
                       String ekycConsentReceived) {
        this.isPanMatchPanApi = isPANMatchPANAPI;
        this.isPanWaiver = isPANWaiver;
        this.isDobWaiver = isDOBWaiver;
        this.dobProofWaiverSource = DOBProofWaiverSource;
        this.ekycConsentReceived = ekycConsentReceived;
    }

    @JsonProperty("isPanMatchPanApi")
    public String getIsPanMatchPanApi() {
        return isPanMatchPanApi;
    }

    @JsonProperty("isPanMatchPanApi")
    public EkycDetails setIsPanMatchPanApi(String isPanMatchPanApi) {
        this.isPanMatchPanApi = isPanMatchPanApi;
        return this;
    }

    @JsonProperty("isPanWaiver")
    public String getIsPanWaiver() {
        return isPanWaiver;
    }

    @JsonProperty("isPanWaiver")
    public EkycDetails setIsPanWaiver(String isPanWaiver) {
        this.isPanWaiver = isPanWaiver;
        return this;
    }

    @JsonProperty("isDobWaiver")
    public String getIsDobWaiver() {
        return isDobWaiver;
    }

    @JsonProperty("isDobWaiver")
    public EkycDetails setIsDobWaiver(String isDobWaiver) {
        this.isDobWaiver = isDobWaiver;
        return this;
    }

    @JsonProperty("dobProofWaiverSource")
    public String getDobProofWaiverSource() {
        return dobProofWaiverSource;
    }

    @JsonProperty("dobProofWaiverSource")
    public EkycDetails setDobProofWaiverSource(String dobProofWaiverSource) {
        this.dobProofWaiverSource = dobProofWaiverSource;
        return this;
    }

    public String getIsHaltMedicalWaiver() {
        return isHaltMedicalWaiver;
    }

    public void setIsHaltMedicalWaiver(String isHaltMedicalWaiver) {
        this.isHaltMedicalWaiver = isHaltMedicalWaiver;
    }

    public String getIsHaltIncomeProofWaiver() {
        return isHaltIncomeProofWaiver;
    }

    public void setIsHaltIncomeProofWaiver(String isHaltIncomeProofWaiver) {
        this.isHaltIncomeProofWaiver = isHaltIncomeProofWaiver;
    }

    public String getVideoMerFlag() {
        return videoMerFlag;
    }

    public void setVideoMerFlag(String videoMerFlag) {
        this.videoMerFlag = videoMerFlag;
    }

    @JsonProperty("ekycConsentReceived")
    public String getEkycConsentReceived() {
        return ekycConsentReceived;
    }

    @JsonProperty("ekycConsentReceived")
    public EkycDetails setEkycConsentReceived(String ekycConsentReceived) {
        this.ekycConsentReceived = ekycConsentReceived;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", EkycDetails.class.getSimpleName() + "[", "]")
                .add("isPanMatchPanApi='" + isPanMatchPanApi + "'")
                .add("isPanWaiver='" + isPanWaiver + "'")
                .add("isDobWaiver='" + isDobWaiver + "'")
                .add("dobProofWaiverSource='" + dobProofWaiverSource + "'")
                .add("isHaltMedicalWaiver='" + isHaltMedicalWaiver + "'")
                .add("isHaltIncomeProofWaiver='" + isHaltIncomeProofWaiver + "'")
                .add("videoMerFlag='" + videoMerFlag + "'")
                .add("ekycConsentReceived='" + ekycConsentReceived + "'")
                .toString();
    }
}
