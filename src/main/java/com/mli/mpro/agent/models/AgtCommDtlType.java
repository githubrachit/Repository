package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AgtCommDtlType{
    @JsonProperty("policyId")
    public String getPolicyId() { 
		 return this.policyId; } 
    public void setPolicyId(String policyId) { 
		 this.policyId = policyId; }
    @Sensitive(MaskType.POLICY_NUM)
    String policyId;
    @JsonProperty("policyholderName") 
    public String getPolicyholderName() { 
		 return this.policyholderName; } 
    public void setPolicyholderName(String policyholderName) { 
		 this.policyholderName = policyholderName; }
    @Sensitive(MaskType.NAME)
    String policyholderName;
    @JsonProperty("policyStatus") 
    public String getPolicyStatus() { 
		 return this.policyStatus; } 
    public void setPolicyStatus(String policyStatus) { 
		 this.policyStatus = policyStatus; } 
    String policyStatus;
    @JsonProperty("policyIssueDt") 
    public String getPolicyIssueDt() { 
		 return this.policyIssueDt; } 
    public void setPolicyIssueDt(String policyIssueDt) { 
		 this.policyIssueDt = policyIssueDt; } 
    String policyIssueDt;
    @JsonProperty("agentSplit") 
    public String getAgentSplit() { 
		 return this.agentSplit; } 
    public void setAgentSplit(String agentSplit) { 
		 this.agentSplit = agentSplit; } 
    String agentSplit;
    @JsonProperty("basePlanName") 
    public String getBasePlanName() { 
		 return this.basePlanName; } 
    public void setBasePlanName(String basePlanName) { 
		 this.basePlanName = basePlanName; } 
    String basePlanName;
    @JsonProperty("premiumMode") 
    public String getPremiumMode() { 
		 return this.premiumMode; } 
    public void setPremiumMode(String premiumMode) { 
		 this.premiumMode = premiumMode; } 
    String premiumMode;
    @JsonProperty("sumAssured") 
    public String getSumAssured() { 
		 return this.sumAssured; } 
    public void setSumAssured(String sumAssured) { 
		 this.sumAssured = sumAssured; }
    @Sensitive(MaskType.AMOUNT)
    String sumAssured;
    @JsonProperty("grossAnnualPremium") 
    public String getGrossAnnualPremium() { 
		 return this.grossAnnualPremium; } 
    public void setGrossAnnualPremium(String grossAnnualPremium) { 
		 this.grossAnnualPremium = grossAnnualPremium; }
    @Sensitive(MaskType.AMOUNT)
    String grossAnnualPremium;
    @JsonProperty("policyTerm") 
    public String getPolicyTerm() { 
		 return this.policyTerm; } 
    public void setPolicyTerm(String policyTerm) { 
		 this.policyTerm = policyTerm; } 
    String policyTerm;
    @JsonProperty("policyInforceDt") 
    public String getPolicyInforceDt() { 
		 return this.policyInforceDt; } 
    public void setPolicyInforceDt(String policyInforceDt) { 
		 this.policyInforceDt = policyInforceDt; } 
    String policyInforceDt;
    @JsonProperty("lastStatusChangeDt") 
    public String getLastStatusChangeDt() { 
		 return this.lastStatusChangeDt; } 
    public void setLastStatusChangeDt(String lastStatusChangeDt) { 
		 this.lastStatusChangeDt = lastStatusChangeDt; } 
    String lastStatusChangeDt;
    @JsonProperty("polDueDt") 
    public String getPolDueDt() { 
		 return this.polDueDt; } 
    public void setPolDueDt(String polDueDt) { 
		 this.polDueDt = polDueDt; } 
    String polDueDt;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtCommDtlType{" +
                "policyId='" + policyId + '\'' +
                ", policyholderName='" + policyholderName + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", policyIssueDt='" + policyIssueDt + '\'' +
                ", agentSplit='" + agentSplit + '\'' +
                ", basePlanName='" + basePlanName + '\'' +
                ", premiumMode='" + premiumMode + '\'' +
                ", sumAssured='" + sumAssured + '\'' +
                ", grossAnnualPremium='" + grossAnnualPremium + '\'' +
                ", policyTerm='" + policyTerm + '\'' +
                ", policyInforceDt='" + policyInforceDt + '\'' +
                ", lastStatusChangeDt='" + lastStatusChangeDt + '\'' +
                ", polDueDt='" + polDueDt + '\'' +
                '}';
    }
}
