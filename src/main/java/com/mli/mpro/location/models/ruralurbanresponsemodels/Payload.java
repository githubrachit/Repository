package com.mli.mpro.location.models.ruralurbanresponsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("Policy Number")
    private String policyNumber;
    @JsonProperty("Tagging")
    private String tagging;
    @JsonProperty("Match Score")
    private String matchScore;
    @JsonProperty("Matched with")
    private String matchedWith;
    @JsonProperty("Partial Match")
    private String partialMatch;

    public String getTagging() {
        return tagging;
    }

    public void setTagging(String tagging) {
        this.tagging = tagging;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    public String getMatchedWith() {
        return matchedWith;
    }

    public void setMatchedWith(String matchedWith) {
        this.matchedWith = matchedWith;
    }

    public String getPartialMatch() {
        return partialMatch;
    }

    public void setPartialMatch(String partialMatch) {
        this.partialMatch = partialMatch;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "policyNumber='" + policyNumber + '\'' +
                ", tagging='" + tagging + '\'' +
                ", matchScore='" + matchScore + '\'' +
                ", matchedWith='" + matchedWith + '\'' +
                ", partialMatch='" + partialMatch + '\'' +
                '}';
    }
}
