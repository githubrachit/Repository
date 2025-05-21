package com.mli.mpro.routingposv.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.Date;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class PersistencyModelDetails {
    @Sensitive(MaskType.POLICY_NUM)
    String policyNumber;
    Date entryDate;
    String statusPers;
    String scoringModelPers;
    String messagePers;
    String riskyTagPers;
    String normalizedScorePers;


    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
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

        return "PersistenceModelDetails{" +
                "policyNumber='" + policyNumber + '\'' +
                ", entryDate=" + entryDate +
                ", statusPers='" + statusPers + '\'' +
                ", scoringModelPers='" + scoringModelPers + '\'' +
                ", messagePers='" + messagePers + '\'' +
                ", riskyTagPers='" + riskyTagPers + '\'' +
                ", normalizedScorePers='" + normalizedScorePers + '\'' +
                '}';
    }
}
