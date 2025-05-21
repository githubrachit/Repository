package com.mli.mpro.routingposv.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class PersistencyModelApiResponse {
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("Policy_Number")
    String policyNumber;
    @JsonProperty("Entry_Date")
    String entryDate;
    @JsonProperty("Status_PERS")
    String statusPers;
    @JsonProperty("Scoring_Channel_PERS")
    String scoringModelPers;
    @JsonProperty("Message_PERS")
    String messagePers;
    @JsonProperty("Risky_Tag_PERS")
    String riskyTagPers;
    @JsonProperty("Normalised_Score_PERS")
    String normalizedScorePers;
    @JsonProperty("Error_Code_PERS")
    String errorCodePers;

    public String getErrorCodePers() {
        return errorCodePers;
    }

    public void setErrorCodePers(String errorCodePers) {
        this.errorCodePers = errorCodePers;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getStatusPers() {
        return statusPers;
    }

    public void setStatusPers(String statusPers) {
        this.statusPers = statusPers;
    }

    public String getScoringModelPers() {
        return scoringModelPers;
    }

    public void setScoringModelPers(String scoringModelPers) {
        this.scoringModelPers = scoringModelPers;
    }

    public String getMessagePers() {
        return messagePers;
    }

    public void setMessagePers(String messagePers) {
        this.messagePers = messagePers;
    }

    public String getRiskyTagPers() {
        return riskyTagPers;
    }

    public void setRiskyTagPers(String riskyTagPers) {
        this.riskyTagPers = riskyTagPers;
    }

    public String getNormalizedScorePers() {
        return normalizedScorePers;
    }

    public void setNormalizedScorePers(String normalizedScorePers) {
        this.normalizedScorePers = normalizedScorePers;
    }

    @Override
    public String toString() {


           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

        return "PersistencyModelApiResponse{" +
                "policyNumber='" + policyNumber + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", statusPers='" + statusPers + '\'' +
                ", scoringModelPers='" + scoringModelPers + '\'' +
                ", messagePers='" + messagePers + '\'' +
                ", riskyTagPers='" + riskyTagPers + '\'' +
                ", normalizedScorePers='" + normalizedScorePers + '\'' +
                ", errorCodePers='" + errorCodePers + '\'' +
                '}';
    }

}
