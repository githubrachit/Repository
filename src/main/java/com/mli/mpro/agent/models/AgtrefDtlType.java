package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;

public class AgtrefDtlType{
    @JsonProperty("agentCode") 
    public String getAgentCode() { 
		 return this.agentCode; } 
    public void setAgentCode(String agentCode) { 
		 this.agentCode = agentCode; } 
    String agentCode;
    @JsonProperty("agentName") 
    public String getAgentName() { 
		 return this.agentName; } 
    public void setAgentName(String agentName) { 
		 this.agentName = agentName; }
    @Sensitive(MaskType.NAME)
    String agentName;
    @JsonProperty("dtOfJoning") 
    public String getDtOfJoning() { 
		 return this.dtOfJoning; } 
    public void setDtOfJoning(String dtOfJoning) { 
		 this.dtOfJoning = dtOfJoning; } 
    String dtOfJoning;
    @JsonProperty("designation") 
    public String getDesignation() { 
		 return this.designation; } 
    public void setDesignation(String designation) { 
		 this.designation = designation; } 
    String designation;
    @JsonProperty("status") 
    public String getStatus() { 
		 return this.status; } 
    public void setStatus(String status) { 
		 this.status = status; } 
    String status;
    @JsonProperty("agencyBranchCode") 
    public String getAgencyBranchCode() { 
		 return this.agencyBranchCode; } 
    public void setAgencyBranchCode(String agencyBranchCode) { 
		 this.agencyBranchCode = agencyBranchCode; }
    @Sensitive(MaskType.BRANCH_CODE)
    String agencyBranchCode;
    @JsonProperty("agencyBranchName") 
    public String getAgencyBranchName() { 
		 return this.agencyBranchName; } 
    public void setAgencyBranchName(String agencyBranchName) { 
		 this.agencyBranchName = agencyBranchName; }
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    String agencyBranchName;
    @JsonProperty("reportingTo") 
    public String getReportingTo() { 
		 return this.reportingTo; } 
    public void setReportingTo(String reportingTo) { 
		 this.reportingTo = reportingTo; }
    @Sensitive(MaskType.NAME)
    String reportingTo;
    @JsonProperty("reportingName") 
    public String getReportingName() { 
		 return this.reportingName; } 
    public void setReportingName(String reportingName) { 
		 this.reportingName = reportingName; }
    @Sensitive(MaskType.NAME)
    String reportingName;

    @JsonProperty("campaignId")
    public ArrayList<String> campaignId;
    public ArrayList<String> getCampaignId() {
        return campaignId;
    }
    public void setCampaignId(ArrayList<String> campaignId) {
        this.campaignId = campaignId;
    }

    @JsonProperty("campaignLocation")
    public ArrayList<String> campaignLocation;

    public ArrayList<String> getCampaignLocation() {
        return campaignLocation;
    }

    public void setCampaignLocation(ArrayList<String> campaignLocation) {
        this.campaignLocation = campaignLocation;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtrefDtlType{" +
                "agentCode='" + agentCode + '\'' +
                ", agentName='" + agentName + '\'' +
                ", dtOfJoning='" + dtOfJoning + '\'' +
                ", designation='" + designation + '\'' +
                ", status='" + status + '\'' +
                ", agencyBranchCode='" + agencyBranchCode + '\'' +
                ", agencyBranchName='" + agencyBranchName + '\'' +
                ", reportingTo='" + reportingTo + '\'' +
                ", reportingName='" + reportingName + '\'' +
                '}';
    }
}
