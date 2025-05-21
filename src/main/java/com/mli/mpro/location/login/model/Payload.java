package com.mli.mpro.location.login.model;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

import java.util.List;
public class Payload {

	private String loginStatus;
	private String loginInfo;
	private String agentStatus;
	private String agentCode;
	private String channelName;
	private String commissionShare;
	private String agentxChannel;
	private String agentActiveCode;
	@Sensitive(MaskType.DOB)
	private String dob;
	@Sensitive(MaskType.ADDRESS)
	private String state;
	@Sensitive(MaskType.EMAIL)
	private String emailId;
	private String spliceNseExpiryDate;
	private String specifiedPersonCode;
	private String spStatus;
	private String ssnCode;
	private String specifiedPersonName;
	private String agentLicenseStartDate;
	private String applicationId;
	private String solId;
	private String customerClassification;
	private String customerId;
	private String isPlatinum;
	private String spliceNseStartDate;
	private String role;
	private String goCode;
	private String dtliaExpiry;
	private String customerSegment;
	@Sensitive(MaskType.NAME)
	private String fullName;
	@Sensitive(MaskType.NAME)
	private String goName;
	private List<String> secondaryBranchCodeAll;
	@Sensitive(MaskType.FIRST_NAME)
	private String fName;
	private String title;
	@Sensitive(MaskType.ADDRESS)
	private String agentCity;
	@Sensitive(MaskType.LAST_NAME)
	private String lName;
	@Sensitive(MaskType.MOBILE)
	private String agentMobileNumber;
	private List<String> raId;
	private String amlStatus;
	private String ulipStatus;
	private String userStatus;
	private Information information;
	private Boolean isPosSeller;
	private int remainingCoolingPeriod;


	public int getRemainingCoolingPeriod() {
		return remainingCoolingPeriod;
	}

	public void setRemainingCoolingPeriod(int remainingCoolingPeriod) {
		this.remainingCoolingPeriod = remainingCoolingPeriod;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCommissionShare() {
		return commissionShare;
	}

	public void setCommissionShare(String commissionShare) {
		this.commissionShare = commissionShare;
	}

	public String getAgentxChannel() {
		return agentxChannel;
	}

	public void setAgentxChannel(String agentxChannel) {
		this.agentxChannel = agentxChannel;
	}

	public String getAgentActiveCode() {
		return agentActiveCode;
	}

	public void setAgentActiveCode(String agentActiveCode) {
		this.agentActiveCode = agentActiveCode;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSpliceNseExpiryDate() {
		return spliceNseExpiryDate;
	}

	public void setSpliceNseExpiryDate(String spliceNseExpiryDate) {
		this.spliceNseExpiryDate = spliceNseExpiryDate;
	}

	public String getSpecifiedPersonCode() {
		return specifiedPersonCode;
	}

	public void setSpecifiedPersonCode(String specifiedPersonCode) {
		this.specifiedPersonCode = specifiedPersonCode;
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

	public String getAgentLicenseStartDate() {
		return agentLicenseStartDate;
	}

	public void setAgentLicenseStartDate(String agentLicenseStartDate) {
		this.agentLicenseStartDate = agentLicenseStartDate;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getSolId() {
		return solId;
	}

	public void setSolId(String solId) {
		this.solId = solId;
	}

	public String getCustomerClassification() {
		return customerClassification;
	}

	public void setCustomerClassification(String customerClassification) {
		this.customerClassification = customerClassification;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getIsPlatinum() {
		return isPlatinum;
	}

	public void setIsPlatinum(String isPlatinum) {
		this.isPlatinum = isPlatinum;
	}

	public String getSpliceNseStartDate() {
		return spliceNseStartDate;
	}

	public void setSpliceNseStartDate(String spliceNseStartDate) {
		this.spliceNseStartDate = spliceNseStartDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGoCode() {
		return goCode;
	}

	public void setGoCode(String goCode) {
		this.goCode = goCode;
	}

	public String getDtliaExpiry() {
		return dtliaExpiry;
	}

	public void setDtliaExpiry(String dtliaExpiry) {
		this.dtliaExpiry = dtliaExpiry;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGoName() {
		return goName;
	}

	public void setGoName(String goName) {
		this.goName = goName;
	}

	public List<String> getSecondaryBranchCodeAll() {
		return secondaryBranchCodeAll;
	}

	public void setSecondaryBranchCodeAll(List<String> secondaryBranchCodeAll) {
		this.secondaryBranchCodeAll = secondaryBranchCodeAll;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAgentCity() {
		return agentCity;
	}

	public void setAgentCity(String agentCity) {
		this.agentCity = agentCity;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAgentMobileNumber() {
		return agentMobileNumber;
	}

	public void setAgentMobileNumber(String agentMobileNumber) {
		this.agentMobileNumber = agentMobileNumber;
	}

	public List<String> getRaId() {
		return raId;
	}

	public void setRaId(List<String> raId) {
		this.raId = raId;
	}

	public String getAmlStatus() {
		return amlStatus;
	}

	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}

	public String getUlipStatus() {
		return ulipStatus;
	}

	public void setUlipStatus(String ulipStatus) {
		this.ulipStatus = ulipStatus;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}
	

	public Boolean getIsPosSeller() {
		return isPosSeller;
	}

	public void setIsPosSeller(Boolean isPosSeller) {
		this.isPosSeller = isPosSeller;
	}

	@Override
	public String toString() {
		return "Payload [loginStatus=" + loginStatus + ", loginInfo=" + loginInfo + ", agentStatus=" + agentStatus
				+ ", agentCode=" + agentCode + ", channelName=" + channelName + ", commissionShare=" + commissionShare
				+ ", agentxChannel=" + agentxChannel + ", agentActiveCode=" + agentActiveCode + ", dob=" + dob
				+ ", state=" + state + ", emailId=" + emailId + ", spliceNseExpiryDate=" + spliceNseExpiryDate
				+ ", specifiedPersonCode=" + specifiedPersonCode + ", spStatus=" + spStatus + ", ssnCode=" + ssnCode
				+ ", specifiedPersonName=" + specifiedPersonName + ", agentLicenseStartDate=" + agentLicenseStartDate
				+ ", applicationId=" + applicationId + ", solId=" + solId + ", customerClassification="
				+ customerClassification + ", customerId=" + customerId + ", isPlatinum=" + isPlatinum
				+ ", spliceNseStartDate=" + spliceNseStartDate + ", role=" + role + ", goCode=" + goCode
				+ ", dtliaExpiry=" + dtliaExpiry + ", customerSegment=" + customerSegment + ", fullName=" + fullName
				+ ", goName=" + goName + ", secondaryBranchCodeAll=" + secondaryBranchCodeAll + ", fName=" + fName
				+ ", title=" + title + ", agentCity=" + agentCity + ", lName=" + lName + ", agentMobileNumber="
				+ agentMobileNumber + ", raId=" + raId + ", amlStatus=" + amlStatus + ", ulipStatus=" + ulipStatus
				+ ", userStatus=" + userStatus + ", information=" + information + ", isPosSeller=" + isPosSeller + "]";
	}

}
