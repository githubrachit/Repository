package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AgtMoveDtlType{
    @JsonProperty("date") 
    public String getDate() { 
		 return this.date; } 
    public void setDate(String date) { 
		 this.date = date; } 
    String date;
    @JsonProperty("movementType") 
    public String getMovementType() { 
		 return this.movementType; } 
    public void setMovementType(String movementType) { 
		 this.movementType = movementType; } 
    String movementType;
    @JsonProperty("oldBranchCode") 
    public String getOldBranchCode() { 
		 return this.oldBranchCode; } 
    public void setOldBranchCode(String oldBranchCode) { 
		 this.oldBranchCode = oldBranchCode; } 
    String oldBranchCode;
    @JsonProperty("newBranchCode") 
    public String getNewBranchCode() { 
		 return this.newBranchCode; } 
    public void setNewBranchCode(String newBranchCode) { 
		 this.newBranchCode = newBranchCode; } 
    String newBranchCode;
    @JsonProperty("oldDesignation") 
    public String getOldDesignation() { 
		 return this.oldDesignation; } 
    public void setOldDesignation(String oldDesignation) { 
		 this.oldDesignation = oldDesignation; } 
    String oldDesignation;
    @JsonProperty("newDesignation") 
    public String getNewDesignation() { 
		 return this.newDesignation; } 
    public void setNewDesignation(String newDesignation) { 
		 this.newDesignation = newDesignation; } 
    String newDesignation;
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
    @JsonProperty("reason") 
    public String getReason() { 
		 return this.reason; } 
    public void setReason(String reason) { 
		 this.reason = reason; } 
    String reason;
    @JsonProperty("reinstatementDt") 
    public String getReinstatementDt() { 
		 return this.reinstatementDt; } 
    public void setReinstatementDt(String reinstatementDt) { 
		 this.reinstatementDt = reinstatementDt; } 
    String reinstatementDt;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtMoveDtlType{" +
                "date='" + date + '\'' +
                ", movementType='" + movementType + '\'' +
                ", oldBranchCode='" + oldBranchCode + '\'' +
                ", newBranchCode='" + newBranchCode + '\'' +
                ", oldDesignation='" + oldDesignation + '\'' +
                ", newDesignation='" + newDesignation + '\'' +
                ", reportingTo='" + reportingTo + '\'' +
                ", reportingName='" + reportingName + '\'' +
                ", reason='" + reason + '\'' +
                ", reinstatementDt='" + reinstatementDt + '\'' +
                '}';
    }
}
