
package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.neo.models.JourneyStatus;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"createdTime", "updatedTime", "applicationStatus", "stage", "policyNumber", "formType", "backendHandlerType", "posvJourneyStatus",
        "policyProcessingJourneyStatus", "journeyStatus"})
public class ApplicationDetails {

    @JsonProperty("createdTime")
    private Date createdTime;
    @JsonProperty("applicationStatus")
    private String applicationStatus;
    @JsonProperty("updatedTime")
    private Date updatedTime;
    @JsonProperty("stage")
    private String stage;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("formType")
    private String formType;
    @JsonProperty("backendHandlerType")
    private String backendHandlerType;
    @JsonProperty("posvJourneyStatus")
    private String posvJourneyStatus;
    @JsonProperty("posvJourneyFlag")
    private String posvJourneyFlag;
    @JsonProperty("policyProcessingJourneyStatus")
    private String policyProcessingJourneyStatus;
    @JsonProperty("journeyStatus")
    private JourneyStatus journeyStatus;
    @JsonProperty("otpDateTimeStamp")
    private String otpDateTimeStamp;
    @JsonProperty("underwritingParallelPosvStage")
    private String underwritingParallelPosvStage;
    @JsonProperty("posvType")
    private String posvType;
    @Sensitive(MaskType.CKYC_NUM)
    private String ckycNumber;
    private String isCkycFetchedSuccess;
    @JsonProperty("isComboSale")
    private String isComboSale;
    private String isParallelJourney;

    @JsonProperty("physicalJourneyEnabled")
    private String physicalJourneyEnabled;

    @JsonProperty("rePolicyNumber")
    private String rePolicyNumber;

    private String schemeType;
    @JsonProperty("quoteId")
    private String quoteId;

    @JsonProperty("bIGeneratedDateOriginal")
    private Date bIGeneratedDateOriginal;
    @JsonProperty("goGreen")
    private String goGreen;
    @JsonProperty("isPbComboSale")
    private String isPbComboSale;

    public String getIsPbComboSale() {
        return isPbComboSale;
    }

    public void setIsPbComboSale(String isPbComboSale) {
        this.isPbComboSale = isPbComboSale;
    }

    public Date getbIGeneratedDateOriginal() {
        return bIGeneratedDateOriginal;
    }

    public void setbIGeneratedDateOriginal(Date bIGeneratedDateOriginal) {
        this.bIGeneratedDateOriginal = bIGeneratedDateOriginal;
    }

    /**
     * No args constructor for use in serialization
     */
    public ApplicationDetails() {
    }

    public ApplicationDetails(String backendHandlerType) {
        super();
        this.backendHandlerType = backendHandlerType;
    }

    /**
     * @param createdTime
     * @param applicationStatus
     * @param updatedTime
     * @param stage
     * @param policyNumber
     * @param formType
     * @param backendHandlerType
     * @param posvJourneyStatus
     * @param policyProcessingJourneyStatus
     */
    public ApplicationDetails(Date createdTime, String applicationStatus, Date updatedTime, String stage, String policyNumber, String formType,
                              String backendHandlerType, String posvJourneyStatus, String policyProcessingJourneyStatus, String posvJourneyFlag, JourneyStatus journeyStatus,
                              String isCkycFetchedSuccess, String ckycNumber) {
        super();
        this.createdTime = createdTime;
        this.applicationStatus = applicationStatus;
        this.updatedTime = updatedTime;
        this.stage = stage;
        this.policyNumber = policyNumber;
        this.formType = formType;
        this.backendHandlerType = backendHandlerType;
        this.posvJourneyStatus = posvJourneyStatus;
        this.policyProcessingJourneyStatus = policyProcessingJourneyStatus;
        this.posvJourneyFlag = posvJourneyFlag;
        this.journeyStatus = journeyStatus;
        this.ckycNumber = ckycNumber;
        this.isCkycFetchedSuccess = isCkycFetchedSuccess;
    }

    public ApplicationDetails(ApplicationDetails applicationDetails) {

        if (applicationDetails != null) {
            this.applicationStatus = applicationDetails.applicationStatus;

            this.stage = applicationDetails.stage;

            this.formType = applicationDetails.formType;

            this.posvJourneyFlag = applicationDetails.posvJourneyFlag;
            this.physicalJourneyEnabled = applicationDetails.physicalJourneyEnabled;
            this.rePolicyNumber = applicationDetails.rePolicyNumber;
        }
    }
    public String getIsCkycFetchedSuccess() {
        return isCkycFetchedSuccess;
    }

    public void setIsCkycFetchedSuccess(String isCkycFetchedSuccess) {
        this.isCkycFetchedSuccess = isCkycFetchedSuccess;
    }

    public String getCkycNumber() {
        return ckycNumber;
    }

    public void setCkycNumber(String ckycNumber) {
        this.ckycNumber = ckycNumber;
    }

    @JsonProperty("createdTime")
    public Date getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("createdTime")
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("applicationStatus")
    public String getApplicationStatus() {
        return applicationStatus;
    }

    @JsonProperty("applicationStatus")
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }

    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    @JsonProperty("formType")
    public String getFormType() {
        return formType;
    }

    @JsonProperty("formType")
    public void setFormType(String formType) {
        this.formType = formType;
    }

    @JsonProperty("updatedTime")
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @JsonProperty("updatedTime")
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @JsonProperty("policyNumber")
    public String getPolicyNumber() {
        return policyNumber;
    }

    @JsonProperty("policyNumber")
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @JsonProperty("backendHandlerType")
    public String getBackendHandlerType() {
        return backendHandlerType;
    }

    @JsonProperty("backendHandlerType")
    public void setBackendHandlerType(String backendHandlerType) {
        this.backendHandlerType = backendHandlerType;
    }

    @JsonProperty("posvJourneyStatus")
    public String getPosvJourneyStatus() {
        return posvJourneyStatus;
    }

    @JsonProperty("posvJourneyStatus")
    public void setPosvJourneyStatus(String posvJourneyStatus) {
        this.posvJourneyStatus = posvJourneyStatus;
    }

    @JsonProperty("policyProcessingJourneyStatus")
    public String getPolicyProcessingJourneyStatus() {
        return policyProcessingJourneyStatus;
    }

    @JsonProperty("policyProcessingJourneyStatus")
    public void setPolicyProcessingJourneyStatus(String policyProcessingJourneyStatus) {
        this.policyProcessingJourneyStatus = policyProcessingJourneyStatus;
    }

    @JsonProperty("posvJourneyFlag")
    public String getPosvJourneyFlag() {
        return posvJourneyFlag;
    }

    @JsonProperty("posvJourneyFlag")
    public void setPosvJourneyFlag(String posvJourneyFlag) {
        this.posvJourneyFlag = posvJourneyFlag;
    }

    @JsonProperty("journeyStatus")
    public JourneyStatus getJourneyStatus() { return journeyStatus; }

    @JsonProperty("journeyStatus")
    public ApplicationDetails setJourneyStatus(JourneyStatus journeyStatus) { this.journeyStatus = journeyStatus; return this; }

    public String getOtpDateTimeStamp() { return otpDateTimeStamp; }

    public ApplicationDetails setOtpDateTimeStamp(String otpDateTimeStamp) { this.otpDateTimeStamp = otpDateTimeStamp; return this; }

    public String getUnderwritingParallelPosvStage() {
		return underwritingParallelPosvStage;
	}

	public void setUnderwritingParallelPosvStage(String underwritingParallelPosvStage) {
		this.underwritingParallelPosvStage = underwritingParallelPosvStage;
	}

	public String getPosvType() {
		return posvType;
	}

	public void setPosvType(String posvType) {
		this.posvType = posvType;
	}

    @JsonProperty("isComboSale")
    public String getIsComboSale()
    {
        return isComboSale;
    }

    @JsonProperty("isComboSale")
    public ApplicationDetails setIsComboSale(String isComboSale) {
        this.isComboSale = isComboSale;
        return this;
    }

    public String getIsParallelJourney() {
        return isParallelJourney;
    }

    public void setIsParallelJourney(String isParallelJourney) {
        this.isParallelJourney = isParallelJourney;
    }

    public String getPhysicalJourneyEnabled() {
        return physicalJourneyEnabled;
    }

    public void setPhysicalJourneyEnabled(String physicalJourneyEnabled) {
        this.physicalJourneyEnabled = physicalJourneyEnabled;
    }

    public String getRePolicyNumber() {
        return rePolicyNumber;
    }

    public void setRePolicyNumber(String rePolicyNumber) {
        this.rePolicyNumber = rePolicyNumber;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getGoGreen() {
        return goGreen;
    }

    public void setGoGreen(String goGreen) {
        this.goGreen = goGreen;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("ApplicationDetails{");
        sb.append("createdTime=").append(createdTime);
        sb.append(", applicationStatus='").append(applicationStatus).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", stage='").append(stage).append('\'');
        sb.append(", policyNumber='").append(policyNumber).append('\'');
        sb.append(", formType='").append(formType).append('\'');
        sb.append(", backendHandlerType='").append(backendHandlerType).append('\'');
        sb.append(", posvJourneyStatus='").append(posvJourneyStatus).append('\'');
        sb.append(", posvJourneyFlag='").append(posvJourneyFlag).append('\'');
        sb.append(", policyProcessingJourneyStatus='").append(policyProcessingJourneyStatus).append('\'');
        sb.append(", journeyStatus=").append(journeyStatus);
        sb.append(", ckycNumber=").append(ckycNumber);
        sb.append(", isCkycFetchedSuccess=").append(isCkycFetchedSuccess);
        sb.append(", isComboSale=").append(isComboSale);
        sb.append(", isParallelJourney=").append(isParallelJourney);
        sb.append(", physicalJourneyEnabled=").append(physicalJourneyEnabled);
        sb.append(", rePolicyNumber=").append(rePolicyNumber);
        sb.append(", schemeType=").append(schemeType);
        sb.append(", quoteId=").append(quoteId);
        sb.append(", isPbComboSale=").append(isPbComboSale);
        sb.append(", bIGeneratedDateOriginal=").append(bIGeneratedDateOriginal);
        sb.append('}');
        return sb.toString();
    }

}
