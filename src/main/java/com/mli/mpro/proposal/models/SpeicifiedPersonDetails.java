package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SpeicifiedPersonDetails {
	@Sensitive(MaskType.NAME)
	@JsonProperty("spName")
	private String spName;
	@JsonProperty("spCode")
	private String spCode;
	@JsonProperty("amlStatus")
	private Boolean amlStatus;
	@JsonProperty("amlTrainingExpirationDate")
	private String amlTrainingExpirationDate;
	@JsonProperty("ulipStatus")
	private Boolean ulipStatus;
	@JsonProperty("ulipTrainingExpirationDate")
	private String ulipTrainingExpirationDate;
	@JsonProperty("spLicenseStartDate")
	private String spLicenseStartDate;
	@JsonProperty("spLicenseExpiryDate")
	private String spLicenseExpiryDate;
	@JsonProperty("spCertificateNumber")
	private String spCertificateNumber;
	@JsonProperty("spSSNCode")
	private String spSSNCode;
	@JsonProperty("spGoCABrokerCode")
	private String spGoCABrokerCode;
	// need to ask rimpi whether details have to be collected or not
	private String spLocation;
	@Sensitive(MaskType.MOBILE)
	private String spMobileNumber;
	private String spAgentId;

	public SpeicifiedPersonDetails() {
	}

	public SpeicifiedPersonDetails(String spName, String spCode, Boolean amlStatus, String amlTrainingExpirationDate,
			Boolean ulipStatus, String ulipTrainingExpirationDate, String spLicenseStartDate,
			String spLicenseExpiryDate, String spCertificateNumber, String spSSNCode, String spGoCABrokerCode,
			String spLocation, String spMobileNumber) {
		super();
		this.spName = spName;
		this.spCode = spCode;
		this.amlStatus = amlStatus;
		this.amlTrainingExpirationDate = amlTrainingExpirationDate;
		this.ulipStatus = ulipStatus;
		this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
		this.spLicenseStartDate = spLicenseStartDate;
		this.spLicenseExpiryDate = spLicenseExpiryDate;
		this.spCertificateNumber = spCertificateNumber;
		this.spSSNCode = spSSNCode;
		this.spGoCABrokerCode = spGoCABrokerCode;
		this.spLocation = spLocation;
		this.spMobileNumber = spMobileNumber;
	}

	public String getSpAgentId() {
		return spAgentId;
	}

	public void setSpAgentId(String spAgentId) {
		this.spAgentId = spAgentId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public Boolean getAmlStatus() {
		return amlStatus;
	}

	public void setAmlStatus(Boolean amlStatus) {
		this.amlStatus = amlStatus;
	}

	public String getAmlTrainingExpirationDate() {
		return amlTrainingExpirationDate;
	}

	public void setAmlTrainingExpirationDate(String amlTrainingExpirationDate) {
		this.amlTrainingExpirationDate = amlTrainingExpirationDate;
	}

	public Boolean getUlipStatus() {
		return ulipStatus;
	}

	public void setUlipStatus(Boolean ulipStatus) {
		this.ulipStatus = ulipStatus;
	}

	public String getUlipTrainingExpirationDate() {
		return ulipTrainingExpirationDate;
	}

	public void setUlipTrainingExpirationDate(String ulipTrainingExpirationDate) {
		this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
	}

	public String getSpLicenseStartDate() {
		return spLicenseStartDate;
	}

	public void setSpLicenseStartDate(String spLicenseStartDate) {
		this.spLicenseStartDate = spLicenseStartDate;
	}

	public String getSpLicenseExpiryDate() {
		return spLicenseExpiryDate;
	}

	public void setSpLicenseExpiryDate(String spLicenseExpiryDate) {
		this.spLicenseExpiryDate = spLicenseExpiryDate;
	}

	public String getSpCertificateNumber() {
		return spCertificateNumber;
	}

	public void setSpCertificateNumber(String spCertificateNumber) {
		this.spCertificateNumber = spCertificateNumber;
	}

	public String getSpSSNCode() {
		return spSSNCode;
	}

	public void setSpSSNCode(String spSSNCode) {
		this.spSSNCode = spSSNCode;
	}

	public String getSpGoCABrokerCode() {
		return spGoCABrokerCode;
	}

	public void setSpGoCABrokerCode(String spGoCABrokerCode) {
		this.spGoCABrokerCode = spGoCABrokerCode;
	}

	public String getSpLocation() {
		return spLocation;
	}

	public void setSpLocation(String spLocation) {
		this.spLocation = spLocation;
	}

	public String getSpMobileNumber() {
		return spMobileNumber;
	}

	public void setSpMobileNumber(String spMobileNumber) {
		this.spMobileNumber = spMobileNumber;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "SpeicifiedPersonDetails [spName=" + spName + ", spCode=" + spCode + ", amlStatus=" + amlStatus
				+ ", amlTrainingExpirationDate=" + amlTrainingExpirationDate + ", ulipStatus=" + ulipStatus
				+ ", ulipTrainingExpirationDate=" + ulipTrainingExpirationDate + ", spLicenseStartDate="
				+ spLicenseStartDate + ", spLicenseExpiryDate=" + spLicenseExpiryDate + ", spCertificateNumber="
				+ spCertificateNumber + ", spSSNCode=" + spSSNCode + ", spGoCABrokerCode=" + spGoCABrokerCode
				+ ", spLocation=" + spLocation + ", spMobileNumber=" + spMobileNumber + "]";
	}

}
