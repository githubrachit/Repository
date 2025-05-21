package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"questDBNo", "questCompanyNumber", "questPolicyStatus", "questSumAssured", "productType", "response", "rejectDesc"})
public class IibDataMatch {
    @JsonProperty("questDBNo")
    private String questDBNo;
    @JsonProperty("questCompanyNumber")
    private String questCompanyNumber;
    @JsonProperty("questPolicyStatus")
    private String questPolicyStatus;
    @JsonProperty("questSumAssured")
    private String questSumAssured;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("policyBelongsToUser")
    private String policyBelongsToUser;
    @JsonProperty("rejectDesc")
    private String rejectDesc;

    public IibDataMatch() {
    }

    // IGNORE THIS -> @JsonProperty("PlaceHolder")

    public String getQuestDBNo() {
        return questDBNo;
    }

    public String getQuestCompanyNumber() {
        return questCompanyNumber;
    }

    public String getQuestPolicyStatus() {
        return questPolicyStatus;
    }

    public String getQuestSumAssured() {
        return questSumAssured;
    }

    public String getProductType() {
        return productType;
    }

    public String getPolicyBelongsToUser() {
        return policyBelongsToUser;
    }

    public String getRejectDesc() {
        return rejectDesc;
    }

    public IibDataMatch(String questDBNo, String questCompanyNumber, String questPolicyStatus, String questSumAssured, String productType, String response, String rejectDesc) {
        this.questDBNo = questDBNo;
        this.questCompanyNumber = questCompanyNumber;
        this.questPolicyStatus = questPolicyStatus;
        this.questSumAssured = questSumAssured;
        this.productType = productType;
        this.policyBelongsToUser = policyBelongsToUser;
        this.rejectDesc = rejectDesc;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", IibDataMatch.class.getSimpleName() + "[", "]")
                .add("questDBNo='" + questDBNo + "'")
                .add("questCompanyNumber='" + questCompanyNumber + "'")
                .add("questPolicyStatus='" + questPolicyStatus + "'")
                .add("questSumAssured='" + questSumAssured + "'")
                .add("productType='" + productType + "'")
                .add("policyBelongsToUser='" + policyBelongsToUser + "'")
                .add("rejectDesc='" + rejectDesc + "'")
                .toString();
    }
}