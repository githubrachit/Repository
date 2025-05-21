
package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isAgentValidated", "agentId", "agentJoiningDate", "agentStatus", "agentLocation", "agentRole",
		"agentMobileNumber", "agentSignDate", "agentCode", "agentEmail", "goCABrokerCode", "agentLicenseStartdate",
		"agentlicenseExpiryDate", "reportingManagerCode", "reportingManagerName", "spLicenseExpiryDate", "spStatus",
		"ssnCode", "specifiedPersonName", "specifiedPersonCode", "spCertificateNumber", "regionalAdvisorId" ,"isPosSeller" })
public class SourcingDetails {

	@JsonProperty("isAgentValidated")
	private boolean isAgentValidated;
	@JsonProperty("agentId")
	private String agentId = "";
	@Sensitive(MaskType.NAME)
	@JsonProperty("agentName")
	private String agentName;
	@Sensitive(MaskType.ADDRESS)
	@JsonProperty("agentLocation")
	private String agentLocation;
	@JsonProperty("agentRole")
	private String agentRole;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("agentMobileNumber")
	private long agentMobileNumber;
	@JsonProperty("agentJoiningDate")
	private Date agentJoiningDate;
	@JsonProperty("agentStatus")
	private String agentStatus;
	@JsonProperty("agentSignDate")
	private Date agentSignDate;
	@JsonProperty("agentCode")
	private String agentCode;
	@Sensitive(MaskType.EMAIL)
	@JsonProperty("agentEmail")
	private String agentEmail;
	@JsonProperty("goCABrokerCode")
	private String goCABrokerCode;
	@JsonProperty("agentLicenseStartdate")
	private String agentLicenseStartdate;
	@JsonProperty("agentlicenseExpiryDate")
	private Date agentlicenseExpiryDate;
	@JsonProperty("reportingManagerCode")
	private String reportingManagerCode;
	@Sensitive(MaskType.NAME)
	@JsonProperty("reportingManagerName")
	private String reportingManagerName;
	@JsonProperty("spStatus")
	private String spStatus;
	@JsonProperty("ssnCode")
	private String ssnCode;
	@Sensitive(MaskType.NAME)
	@JsonProperty("specifiedPersonName")
	private String specifiedPersonName;
	@JsonProperty("specifiedPersonCode")
	private String specifiedPersonCode;
	@JsonProperty("persistency")
	private String persistency;
	@JsonProperty("specifiedPersonDetails")
	private SpeicifiedPersonDetails specifiedPersonDetails;
	@JsonProperty("regionalAdvisorId")
	private String regionalAdvisorId;
	@JsonProperty("salesRegionalManager")
	private String salesRegionalManager;
	@JsonProperty("serviceRegionalManager")
	private String serviceRegionalManager;
	@JsonProperty("assetRegionalManager")
	private String assetRegionalManager;
	@JsonProperty("isPosSeller")
	private boolean isPosSeller;
	@Sensitive(MaskType.AADHAAR_NUM)
	@JsonProperty("agentAadhaar")
	private String agentAadhaar;
	@Sensitive(MaskType.PAN_NUM)
	@JsonProperty("agentPAN")
	private String agentPAN;
	@JsonProperty("agentSegmentDesc")
	private String agentSegmentDesc;
	
	@JsonProperty("dataEntryOperatorDetails")
    private DataEntryOperatorDetails dataEntryOperatorDetails;

	@JsonProperty("campaignId")
	private String campaignId;

	@JsonProperty("campaignLocation")
	private String campaignLocation;

	@JsonProperty("branchMailId")
	private String branchMailId;
	@JsonProperty("designation")
	private String designation;
	@JsonProperty("categoryDesc")
	private String categoryDesc;
	@JsonProperty("regionalAdvisorMobile")
	private String regionalAdvisorMobile;
	@JsonProperty("regionalAdvisorEmail")
	private String regionalAdvisorEmail;
	@JsonProperty("regionalAdvisorName")
	private String regionalAdvisorName;
	@JsonProperty("rAJourneyApplicable")
	private Boolean rAJourneyApplicable;
	public SourcingDetails() {
	}

	public SourcingDetails(boolean isAgentValidated, String agentId, String agentName, String agentLocation,
			String agentRole, long agentMobileNumber, Date agentJoiningDate, String agentStatus, Date agentSignDate,
			String agentCode, String agentEmail, String goCABrokerCode, String agentLicenseStartdate,
			Date agentlicenseExpiryDate, String reportingManagerCode, String reportingManagerName, String spStatus,
			String ssnCode, String specifiedPersonName, String specifiedPersonCode, String persistency,
			SpeicifiedPersonDetails specifiedPersonDetails, String regionalAdvisorId, String salesRegionalManager,
			String serviceRegionalManager, String assetRegionalManager) {
		super();
		this.isAgentValidated = isAgentValidated;
		this.agentId = agentId;
		this.agentName = agentName;
		this.agentLocation = agentLocation;
		this.agentRole = agentRole;
		this.agentMobileNumber = agentMobileNumber;
		this.agentJoiningDate = agentJoiningDate;
		this.agentStatus = agentStatus;
		this.agentSignDate = agentSignDate;
		this.agentCode = agentCode;
		this.agentEmail = agentEmail;
		this.goCABrokerCode = goCABrokerCode;
		this.agentLicenseStartdate = agentLicenseStartdate;
		this.agentlicenseExpiryDate = agentlicenseExpiryDate;
		this.reportingManagerCode = reportingManagerCode;
		this.reportingManagerName = reportingManagerName;
		this.spStatus = spStatus;
		this.ssnCode = ssnCode;
		this.specifiedPersonName = specifiedPersonName;
		this.specifiedPersonCode = specifiedPersonCode;
		this.persistency = persistency;
		this.specifiedPersonDetails = specifiedPersonDetails;
		this.regionalAdvisorId = regionalAdvisorId;
		this.salesRegionalManager = salesRegionalManager;
		this.serviceRegionalManager = serviceRegionalManager;
		this.assetRegionalManager = assetRegionalManager;
	}

	public SourcingDetails(SourcingDetails sourcingDetails) {
		this.isAgentValidated = sourcingDetails.isAgentValidated;
		this.agentId = sourcingDetails.agentId;
		this.agentName = sourcingDetails.agentName;
		this.agentLocation = sourcingDetails.agentLocation;
		this.agentRole = sourcingDetails.agentRole;
		this.agentMobileNumber = sourcingDetails.agentMobileNumber;
		this.agentJoiningDate = sourcingDetails.agentJoiningDate;
		this.agentStatus = sourcingDetails.agentStatus;
		this.agentSignDate = sourcingDetails.agentSignDate;
		this.agentCode = sourcingDetails.agentCode;
		this.agentEmail = sourcingDetails.agentEmail;
		this.goCABrokerCode = sourcingDetails.goCABrokerCode;
		this.agentLicenseStartdate = sourcingDetails.agentLicenseStartdate;
		this.agentlicenseExpiryDate = sourcingDetails.agentlicenseExpiryDate;
		this.reportingManagerCode = sourcingDetails.reportingManagerCode;
		this.reportingManagerName = sourcingDetails.reportingManagerName;
		this.spStatus = sourcingDetails.spStatus;
		this.ssnCode = sourcingDetails.ssnCode;
		this.specifiedPersonName = sourcingDetails.specifiedPersonName;
		this.specifiedPersonCode = sourcingDetails.specifiedPersonCode;
		this.persistency = sourcingDetails.persistency;
		this.specifiedPersonDetails = sourcingDetails.specifiedPersonDetails;
		this.regionalAdvisorId = sourcingDetails.regionalAdvisorId;
		this.salesRegionalManager = sourcingDetails.salesRegionalManager;
		this.serviceRegionalManager = sourcingDetails.serviceRegionalManager;
		this.assetRegionalManager = sourcingDetails.assetRegionalManager;
		this.agentSegmentDesc = sourcingDetails.agentSegmentDesc;

	}

	public Boolean getRAJourneyApplicable() {
		return rAJourneyApplicable;
	}

	public String getRegionalAdvisorEmail() {
		return regionalAdvisorEmail;
	}

	public String getRegionalAdvisorName() {
		return regionalAdvisorName;
	}

	public String getRegionalAdvisorMobile() {
		return regionalAdvisorMobile;
	}
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentLocation() {
		return agentLocation;
	}

	public void setAgentLocation(String agentLocation) {
		this.agentLocation = agentLocation;
	}

	public String getAgentRole() {
		return agentRole;
	}

	public void setAgentRole(String agentRole) {
		this.agentRole = agentRole;
	}

	public long getAgentMobileNumber() {
		return agentMobileNumber;
	}

	public void setAgentMobileNumber(long agentMobileNumber) {
		this.agentMobileNumber = agentMobileNumber;
	}

	public Date getAgentJoiningDate() {
		return agentJoiningDate;
	}

	public void setAgentJoiningDate(Date agentJoiningDate) {
		this.agentJoiningDate = agentJoiningDate;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public Date getAgentSignDate() {
		return agentSignDate;
	}

	public void setAgentSignDate(Date agentSignDate) {
		this.agentSignDate = agentSignDate;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentEmail() {
		return agentEmail;
	}

	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

	public String getGoCABrokerCode() {
		return goCABrokerCode;
	}

	public void setGoCABrokerCode(String goCABrokerCode) {
		this.goCABrokerCode = goCABrokerCode;
	}

	public String getAgentLicenseStartdate() {
		return agentLicenseStartdate;
	}

	public void setAgentLicenseStartdate(String agentLicenseStartdate) {
		this.agentLicenseStartdate = agentLicenseStartdate;
	}

	public Date getAgentlicenseExpiryDate() {
		return agentlicenseExpiryDate;
	}

	public void setAgentlicenseExpiryDate(Date agentlicenseExpiryDate) {
		this.agentlicenseExpiryDate = agentlicenseExpiryDate;
	}

	public String getReportingManagerCode() {
		return reportingManagerCode;
	}

	public void setReportingManagerCode(String reportingManagerCode) {
		this.reportingManagerCode = reportingManagerCode;
	}

	public String getReportingManagerName() {
		return reportingManagerName;
	}

	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}

	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}

	public String getSsnCode() {
		return ssnCode;
	}

	public void setSsnCode(String ssnCode) {
		this.ssnCode = ssnCode;
	}

	public String getSpecifiedPersonName() {
		return specifiedPersonName;
	}

	public void setSpecifiedPersonName(String specifiedPersonName) {
		this.specifiedPersonName = specifiedPersonName;
	}

	public boolean isAgentValidated() {
		return isAgentValidated;
	}

	public void setAgentValidated(boolean isAgentValidated) {
		this.isAgentValidated = isAgentValidated;
	}

	public String getSpecifiedPersonCode() {
		return specifiedPersonCode;
	}

	public void setSpecifiedPersonCode(String specifiedPersonCode) {
		this.specifiedPersonCode = specifiedPersonCode;
	}

	public String getPersistency() {
		return persistency;
	}

	public void setPersistency(String persistency) {
		this.persistency = persistency;
	}

	public SpeicifiedPersonDetails getSpecifiedPersonDetails() {
		return specifiedPersonDetails;
	}

	public void setSpecifiedPersonDetails(SpeicifiedPersonDetails specifiedPersonDetails) {
		this.specifiedPersonDetails = specifiedPersonDetails;
	}

	public String getRegionalAdvisorId() {
		return regionalAdvisorId;
	}

	public void setRegionalAdvisorId(String regionalAdvisorId) {
		this.regionalAdvisorId = regionalAdvisorId;
	}

	public String getSalesRegionalManager() {
		return salesRegionalManager;
	}

	public void setSalesRegionalManager(String salesRegionalManager) {
		this.salesRegionalManager = salesRegionalManager;
	}

	public String getServiceRegionalManager() {
		return serviceRegionalManager;
	}

	public void setServiceRegionalManager(String serviceRegionalManager) {
		this.serviceRegionalManager = serviceRegionalManager;
	}

	public String getAssetRegionalManager() {
		return assetRegionalManager;
	}

	public void setAssetRegionalManager(String assetRegionalManager) {
		this.assetRegionalManager = assetRegionalManager;
	}

	public boolean isPosSeller() {
		return isPosSeller;
	}

	public void setPosSeller(boolean posSeller) {
		isPosSeller = posSeller;
	}

	public String getAgentAadhaar() {
		return agentAadhaar;
	}

	public void setAgentAadhaar(String agentAadhaar) {
		this.agentAadhaar = agentAadhaar;
	}

	public String getAgentPAN() {
		return agentPAN;
	}

	public void setAgentPAN(String agentPAN) {
		this.agentPAN = agentPAN;
	}

	public String getAgentSegmentDesc() {
		return agentSegmentDesc;
	}

	public void setAgentSegmentDesc(String agentSegmentDesc) {
		this.agentSegmentDesc = agentSegmentDesc;
	}

	public DataEntryOperatorDetails getDataEntryOperatorDetails() {
        return dataEntryOperatorDetails;
    }

    public void setDataEntryOperatorDetails(DataEntryOperatorDetails dataEntryOperatorDetails) {
        this.dataEntryOperatorDetails = dataEntryOperatorDetails;
    }

	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignLocation() {
		return campaignLocation;
	}
	public void setCampaignLocation(String campaignLocation) {
		this.campaignLocation = campaignLocation;
	}

	public String getBranchMailId() {
		return branchMailId;
	}
	public void setBranchMailId(String branchMailId) {
		this.branchMailId = branchMailId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "SourcingDetails [isAgentValidated=" + isAgentValidated + ", agentId=" + agentId + ", agentName="
				+ agentName + ", agentLocation=" + agentLocation + ", agentRole=" + agentRole + ", agentMobileNumber="
				+ agentMobileNumber + ", agentJoiningDate=" + agentJoiningDate + ", agentStatus=" + agentStatus
				+ ", agentSignDate=" + agentSignDate + ", agentCode=" + agentCode + ", agentEmail=" + agentEmail
				+ ", goCABrokerCode=" + goCABrokerCode + ", agentLicenseStartdate=" + agentLicenseStartdate
				+ ", agentlicenseExpiryDate=" + agentlicenseExpiryDate + ", reportingManagerCode="
				+ reportingManagerCode + ", reportingManagerName=" + reportingManagerName + ", spStatus=" + spStatus
				+ ", ssnCode=" + ssnCode + ", specifiedPersonName=" + specifiedPersonName + ", specifiedPersonCode="
				+ specifiedPersonCode + ", persistency=" + persistency + ", specifiedPersonDetails="
				+ specifiedPersonDetails + ", regionalAdvisorId=" + regionalAdvisorId + ", salesRegionalManager="
				+ salesRegionalManager + ", serviceRegionalManager=" + serviceRegionalManager
				+ ", assetRegionalManager=" + assetRegionalManager + ", isPosSeller=" + isPosSeller + ", agentAadhaar="
				+ agentAadhaar + ",agentPAN=" + agentPAN + ",agentSegmentDesc=" + agentSegmentDesc
				+ ", dataEntryOperatorDetails = " + dataEntryOperatorDetails + ", campaignId = " + campaignId  +
				", campaignLocation = " + campaignLocation +
				", branchMailId = " + branchMailId +"]";
	}

}
