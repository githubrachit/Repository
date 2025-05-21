package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SplittingorReplacementRequestPayload {

    @JsonProperty("proposerClientId")
    private String proposerClientId;

    @JsonProperty("insuredClientId")
    private String insuredClientId;

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("currentPolicyNumber")
    private String currentPolicyNumber;

    @JsonProperty("currentPolicySignDate")
    private String currentPolicySignDate;

    @JsonProperty("uinNo")
    private String uinNo;

    public String getProposerClientId() {
        return proposerClientId;
    }

    public void setProposerClientId(String proposerClientId) {
        this.proposerClientId = proposerClientId;
    }

    public String getInsuredClientId() {
        return insuredClientId;
    }

    public void setInsuredClientId(String insuredClientId) {
        this.insuredClientId = insuredClientId;
    }

    public String getCurrentPolicyNumber() {
        return currentPolicyNumber;
    }

    public void setCurrentPolicyNumber(String currentPolicyNumber) {
        this.currentPolicyNumber = currentPolicyNumber;
    }

    public String getCurrentPolicySignDate() {
        return currentPolicySignDate;
    }

    public void setCurrentPolicySignDate(String currentPolicySignDate) {
        this.currentPolicySignDate = currentPolicySignDate;
    }

    public String getUinNo() {
        return uinNo;
    }

    public void setUinNo(String uinNo) {
        this.uinNo = uinNo;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SplittingorReplacementRequestPayload [proposerClientId=" + proposerClientId + ", insuredClientId="
                + insuredClientId + ", currentPolicyNumber=" + currentPolicyNumber + ", currentPolicySignDate="
                + currentPolicySignDate + ", uinNo=" + uinNo + "]";
    }
}
