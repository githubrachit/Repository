package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customerTypeFlag", "customerType", "customerId", "bankBranchCode", "bankBranchName", "title", "customerFullName", "customerFirstName",
	"customerMiddleName", "customerLastName", "dob", "communicationAddressLine1", "communicationAddressLine2", "communicationAddressLine3", "city", "state",
	"country", "pinCode", "emailId", "mobileNumber", "gender", "panCard", "residentialPhoneNumber", "officialPhoneNumber", "permanentAddressLine1",
	"permanentAddressLine2", "permanentAddressLine3", "permanentcity", "permanentstate", "permanentcountry", "permanentPincode", "profession",
	"occupation" ,"motherName","fatherName","maritalStatus","passportNumber","passportIssueDate","passportExpDate","riskCategory","customerClassification",
	"salesRM","serviceRM","serviceRM","asssetRM","lastKYC","paasportIssuancePlace","customerSegment","nomineeDetails","customerOtpSent","customerOtpValidated",
	"accounts","yblBankDetails"})
public class YBLDetails {

    @JsonProperty("customerTypeFlag")
    private String customerTypeFlag;
    @JsonProperty("customerType")
    private String customerType;
    @JsonProperty("customerId")
    private String customerId;
    @Sensitive(MaskType.BRANCH_CODE)
    @JsonProperty("bankBranchCode")
    private String bankBranchCode;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("bankBranchName")
    private String bankBranchName;
    @JsonProperty("title")
    private String title;
    @Sensitive(MaskType.NAME)
    @JsonProperty("customerFullName")
    private String customerFullName;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("customerFirstName")
    private String customerFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("customerMiddleName")
    private String customerMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("customerLastName")
    private String customerLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationAddressLine1")
    private String communicationAddressLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationAddressLine2")
    private String communicationAddressLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationAddressLine3")
    private String communicationAddressLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("city")
    private String city;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("state")
    private String state;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("country")
    private String country;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("pinCode")
    private String pinCode;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panCard")
    private String panCard;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("residentialPhoneNumber")
    private String residentialPhoneNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("officialPhoneNumber")
    private String officialPhoneNumber;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentAddressLine1")
    private String permanentAddressLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentAddressLine2")
    private String permanentAddressLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentAddressLine3")
    private String permanentAddressLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentcity")
    private String permanentcity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentstate")
    private String permanentstate;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentcountry")
    private String permanentcountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("permanentAddressPincode")
    private String permanentAddressPincode;
    @JsonProperty("profession")
    private String profession;
    @JsonProperty("occupation")
    private String occupation;
    @Sensitive(MaskType.NAME)
    @JsonProperty("motherName")
    private String motherName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("fatherName")
    private String fatherName;
    @JsonProperty("maritalStatus")
    private String maritalStatus;
    @Sensitive(MaskType.PASSPORT)
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("passportIssueDate")
    private String passportIssueDate;
    @JsonProperty("passportExpDate")
    private String passportExpDate;
    @JsonProperty("riskCategory")
    private String riskCategory;
    @JsonProperty("customerClassification")
    private String customerClassification;
    @JsonProperty("salesRM")
    private String salesRM;
    @JsonProperty("serviceRM")
    private String serviceRM;
    @JsonProperty("asssetRM")
    private String asssetRM;
    @JsonProperty("lastKYC")
    private String lastKYC;
    @JsonProperty("paasportIssuancePlace")
    private String paasportIssuancePlace;
    @JsonProperty("customerSegment")
    private String customerSegment;
    @JsonProperty("nomineeDetails")
    private NomineeDetails nomineeDetails;
    @JsonProperty("customerOtpSent")
    private String customerOtpSent;
    @JsonProperty("customerOtpValidated")
    private String customerOtpValidated;
    @JsonProperty("accounts")
    private List<Account> accounts;
    @JsonProperty("yblBankDetails")
    private YBLBankDetails yblBankDetails;
    @JsonProperty("nisTraceId")
    private String nisTraceId;

    public YBLDetails() {
	super();
    }

    public YBLDetails(String customerTypeFlag, String customerType, String customerId, String bankBranchCode, String bankBranchName, String title,
	    String customerFullName, String customerFirstName, String customerMiddleName, String customerLastName, String dob, String communicationAddressLine1,
	    String communicationAddressLine2, String communicationAddressLine3, String city, String state, String country, String pinCode, String emailId,
	    String mobileNumber, String gender, String panCard, String residentialPhoneNumber, String officialPhoneNumber, String permanentAddressLine1,
	    String permanentAddressLine2, String permanentAddressLine3, String permanentcity, String permanentstate, String permanentcountry,
	    String permanentAddressPincode, String profession, String occupation, String motherName, String fatherName, String maritalStatus,
	    String passportNumber, String passportIssueDate, String passportExpDate, String riskCategory, String customerClassification, String salesRM,
	    String serviceRM, String asssetRM, String lastKYC, String paasportIssuancePlace, String customerSegment, NomineeDetails nomineeDetails,
	    String customerOtpSent, String customerOtpValidated, List<Account> accounts, YBLBankDetails yblBankDetails) {
	super();
	this.customerTypeFlag = customerTypeFlag;
	this.customerType = customerType;
	this.customerId = customerId;
	this.bankBranchCode = bankBranchCode;
	this.bankBranchName = bankBranchName;
	this.title = title;
	this.customerFullName = customerFullName;
	this.customerFirstName = customerFirstName;
	this.customerMiddleName = customerMiddleName;
	this.customerLastName = customerLastName;
	this.dob = dob;
	this.communicationAddressLine1 = communicationAddressLine1;
	this.communicationAddressLine2 = communicationAddressLine2;
	this.communicationAddressLine3 = communicationAddressLine3;
	this.city = city;
	this.state = state;
	this.country = country;
	this.pinCode = pinCode;
	this.emailId = emailId;
	this.mobileNumber = mobileNumber;
	this.gender = gender;
	this.panCard = panCard;
	this.residentialPhoneNumber = residentialPhoneNumber;
	this.officialPhoneNumber = officialPhoneNumber;
	this.permanentAddressLine1 = permanentAddressLine1;
	this.permanentAddressLine2 = permanentAddressLine2;
	this.permanentAddressLine3 = permanentAddressLine3;
	this.permanentcity = permanentcity;
	this.permanentstate = permanentstate;
	this.permanentcountry = permanentcountry;
	this.permanentAddressPincode = permanentAddressPincode;
	this.profession = profession;
	this.occupation = occupation;
	this.motherName = motherName;
	this.fatherName = fatherName;
	this.maritalStatus = maritalStatus;
	this.passportNumber = passportNumber;
	this.passportIssueDate = passportIssueDate;
	this.passportExpDate = passportExpDate;
	this.riskCategory = riskCategory;
	this.customerClassification = customerClassification;
	this.salesRM = salesRM;
	this.serviceRM = serviceRM;
	this.asssetRM = asssetRM;
	this.lastKYC = lastKYC;
	this.paasportIssuancePlace = paasportIssuancePlace;
	this.customerSegment = customerSegment;
	this.nomineeDetails = nomineeDetails;
	this.customerOtpSent = customerOtpSent;
	this.customerOtpValidated = customerOtpValidated;
	this.accounts = accounts;
	this.yblBankDetails = yblBankDetails;
    }

    

    public YBLDetails(YBLDetails yblDetails) {
	// TODO Auto-generated constructor stub
    }

    @JsonProperty("customerTypeFlag")
    public String getCustomerTypeFlag() {
	return customerTypeFlag;
    }

    @JsonProperty("customerTypeFlag")
    public void setCustomerTypeFlag(String customerTypeFlag) {
	this.customerTypeFlag = customerTypeFlag;
    }

    @JsonProperty("customerType")
    public String getCustomerType() {
	return customerType;
    }

    @JsonProperty("customerType")
    public void setCustomerType(String customerType) {
	this.customerType = customerType;
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
	return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    @JsonProperty("bankBranchCode")
    public String getBankBranchCode() {
	return bankBranchCode;
    }

    @JsonProperty("bankBranchCode")
    public void setBankBranchCode(String bankBranchCode) {
	this.bankBranchCode = bankBranchCode;
    }

    @JsonProperty("bankBranchName")
    public String getBankBranchName() {
	return bankBranchName;
    }

    @JsonProperty("bankBranchName")
    public void setBankBranchName(String bankBranchName) {
	this.bankBranchName = bankBranchName;
    }

    @JsonProperty("title")
    public String getTitle() {
	return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
	this.title = title;
    }

    @JsonProperty("customerFullName")
    public String getCustomerFullName() {
	return customerFullName;
    }

    @JsonProperty("customerFullName")
    public void setCustomerFullName(String customerFullName) {
	this.customerFullName = customerFullName;
    }

    @JsonProperty("customerFirstName")
    public String getCustomerFirstName() {
	return customerFirstName;
    }

    @JsonProperty("customerFirstName")
    public void setCustomerFirstName(String customerFirstName) {
	this.customerFirstName = customerFirstName;
    }

    @JsonProperty("customerMiddleName")
    public String getCustomerMiddleName() {
	return customerMiddleName;
    }

    @JsonProperty("customerMiddleName")
    public void setCustomerMiddleName(String customerMiddleName) {
	this.customerMiddleName = customerMiddleName;
    }

    @JsonProperty("customerLastName")
    public String getCustomerLastName() {
	return customerLastName;
    }

    @JsonProperty("customerLastName")
    public void setCustomerLastName(String customerLastName) {
	this.customerLastName = customerLastName;
    }

    @JsonProperty("dob")
    public String getDob() {
	return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
	this.dob = dob;
    }

    @JsonProperty("communicationAddressLine1")
    public String getCommunicationAddressLine1() {
	return communicationAddressLine1;
    }

    @JsonProperty("communicationAddressLine1")
    public void setCommunicationAddressLine1(String communicationAddressLine1) {
	this.communicationAddressLine1 = communicationAddressLine1;
    }

    @JsonProperty("communicationAddressLine2")
    public String getCommunicationAddressLine2() {
	return communicationAddressLine2;
    }

    @JsonProperty("communicationAddressLine2")
    public void setCommunicationAddressLine2(String communicationAddressLine2) {
	this.communicationAddressLine2 = communicationAddressLine2;
    }

    @JsonProperty("communicationAddressLine3")
    public String getCommunicationAddressLine3() {
	return communicationAddressLine3;
    }

    @JsonProperty("communicationAddressLine3")
    public void setCommunicationAddressLine3(String communicationAddressLine3) {
	this.communicationAddressLine3 = communicationAddressLine3;
    }

    @JsonProperty("city")
    public String getCity() {
	return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
	this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
	return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
	this.state = state;
    }

    @JsonProperty("country")
    public String getCountry() {
	return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
	this.country = country;
    }

    @JsonProperty("pinCode")
    public String getPinCode() {
	return pinCode;
    }

    @JsonProperty("pinCode")
    public void setPinCode(String pinCode) {
	this.pinCode = pinCode;
    }

    @JsonProperty("emailId")
    public String getEmailId() {
	return emailId;
    }

    @JsonProperty("emailId")
    public void setEmailId(String emailId) {
	this.emailId = emailId;
    }

    @JsonProperty("mobileNumber")
    public String getMobileNumber() {
	return mobileNumber;
    }

    @JsonProperty("mobileNumber")
    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    @JsonProperty("gender")
    public String getGender() {
	return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
	this.gender = gender;
    }

    @JsonProperty("panCard")
    public String getPanCard() {
	return panCard;
    }

    @JsonProperty("panCard")
    public void setPanCard(String panCard) {
	this.panCard = panCard;
    }

    @JsonProperty("residentialPhoneNumber")
    public String getResidentialPhoneNumber() {
	return residentialPhoneNumber;
    }

    @JsonProperty("residentialPhoneNumber")
    public void setResidentialPhoneNumber(String residentialPhoneNumber) {
	this.residentialPhoneNumber = residentialPhoneNumber;
    }

    @JsonProperty("officialPhoneNumber")
    public String getOfficialPhoneNumber() {
	return officialPhoneNumber;
    }

    @JsonProperty("officialPhoneNumber")
    public void setOfficialPhoneNumber(String officialPhoneNumber) {
	this.officialPhoneNumber = officialPhoneNumber;
    }

    @JsonProperty("permanentAddressLine1")
    public String getPermanentAddressLine1() {
	return permanentAddressLine1;
    }

    @JsonProperty("permanentAddressLine1")
    public void setPermanentAddressLine1(String permanentAddressLine1) {
	this.permanentAddressLine1 = permanentAddressLine1;
    }

    @JsonProperty("permanentAddressLine2")
    public String getPermanentAddressLine2() {
	return permanentAddressLine2;
    }

    @JsonProperty("permanentAddressLine2")
    public void setPermanentAddressLine2(String permanentAddressLine2) {
	this.permanentAddressLine2 = permanentAddressLine2;
    }

    @JsonProperty("permanentAddressLine3")
    public String getPermanentAddressLine3() {
	return permanentAddressLine3;
    }

    @JsonProperty("permanentAddressLine3")
    public void setPermanentAddressLine3(String permanentAddressLine3) {
	this.permanentAddressLine3 = permanentAddressLine3;
    }

    @JsonProperty("permanentcity")
    public String getPermanentcity() {
	return permanentcity;
    }

    @JsonProperty("permanentcity")
    public void setPermanentcity(String permanentcity) {
	this.permanentcity = permanentcity;
    }

    @JsonProperty("permanentstate")
    public String getPermanentstate() {
	return permanentstate;
    }

    @JsonProperty("permanentstate")
    public void setPermanentstate(String permanentstate) {
	this.permanentstate = permanentstate;
    }

    @JsonProperty("permanentcountry")
    public String getPermanentcountry() {
	return permanentcountry;
    }

    @JsonProperty("permanentcountry")
    public void setPermanentcountry(String permanentcountry) {
	this.permanentcountry = permanentcountry;
    }

    public String getPermanentAddressPincode() {
	return permanentAddressPincode;
    }

    public void setPermanentAddressPincode(String permanentAddressPincode) {
	this.permanentAddressPincode = permanentAddressPincode;
    }

    @JsonProperty("profession")
    public String getProfession() {
	return profession;
    }

    @JsonProperty("profession")
    public void setProfession(String profession) {
	this.profession = profession;
    }

    @JsonProperty("occupation")
    public String getOccupation() {
	return occupation;
    }

    @JsonProperty("occupation")
    public void setOccupation(String occupation) {
	this.occupation = occupation;
    }

    @JsonProperty("motherName")
    public String getMotherName() {
	return motherName;
    }

    @JsonProperty("motherName")
    public void setMotherName(String motherName) {
	this.motherName = motherName;
    }

    @JsonProperty("fatherName")
    public String getFatherName() {
	return fatherName;
    }

    @JsonProperty("fatherName")
    public void setFatherName(String fatherName) {
	this.fatherName = fatherName;
    }

    @JsonProperty("maritalStatus")
    public String getMaritalStatus() {
	return maritalStatus;
    }

    @JsonProperty("maritalStatus")
    public void setMaritalStatus(String maritalStatus) {
	this.maritalStatus = maritalStatus;
    }

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
	return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
	this.passportNumber = passportNumber;
    }

    @JsonProperty("passportIssueDate")
    public String getPassportIssueDate() {
	return passportIssueDate;
    }

    @JsonProperty("passportIssueDate")
    public void setPassportIssueDate(String passportIssueDate) {
	this.passportIssueDate = passportIssueDate;
    }

    @JsonProperty("passportExpDate")
    public String getPassportExpDate() {
	return passportExpDate;
    }

    @JsonProperty("passportExpDate")
    public void setPassportExpDate(String passportExpDate) {
	this.passportExpDate = passportExpDate;
    }

    @JsonProperty("riskCategory")
    public String getRiskCategory() {
	return riskCategory;
    }

    @JsonProperty("riskCategory")
    public void setRiskCategory(String riskCategory) {
	this.riskCategory = riskCategory;
    }

    @JsonProperty("customerClassification")
    public String getCustomerClassification() {
	return customerClassification;
    }

    @JsonProperty("customerClassification")
    public void setCustomerClassification(String customerClassification) {
	this.customerClassification = customerClassification;
    }

    @JsonProperty("salesRM")
    public String getSalesRM() {
	return salesRM;
    }

    @JsonProperty("salesRM")
    public void setSalesRM(String salesRM) {
	this.salesRM = salesRM;
    }

    @JsonProperty("serviceRM")
    public String getServiceRM() {
	return serviceRM;
    }

    @JsonProperty("serviceRM")
    public void setServiceRM(String serviceRM) {
	this.serviceRM = serviceRM;
    }

    @JsonProperty("asssetRM")
    public String getAsssetRM() {
	return asssetRM;
    }

    @JsonProperty("asssetRM")
    public void setAsssetRM(String asssetRM) {
	this.asssetRM = asssetRM;
    }

    @JsonProperty("lastKYC")
    public String getLastKYC() {
	return lastKYC;
    }

    @JsonProperty("lastKYC")
    public void setLastKYC(String lastKYC) {
	this.lastKYC = lastKYC;
    }

    @JsonProperty("paasportIssuancePlace")
    public String getPaasportIssuancePlace() {
	return paasportIssuancePlace;
    }

    @JsonProperty("paasportIssuancePlace")
    public void setPaasportIssuancePlace(String paasportIssuancePlace) {
	this.paasportIssuancePlace = paasportIssuancePlace;
    }

    @JsonProperty("customerSegment")
    public String getCustomerSegment() {
	return customerSegment;
    }

    @JsonProperty("customerSegment")
    public void setCustomerSegment(String customerSegment) {
	this.customerSegment = customerSegment;
    }

    @JsonProperty("nomineeDetails")
    public NomineeDetails getNomineeDetails() {
	return nomineeDetails;
    }

    @JsonProperty("nomineeDetails")
    public void setNomineeDetails(NomineeDetails nomineeDetails) {
	this.nomineeDetails = nomineeDetails;
    }

    @JsonProperty("customerOtpSent")
    public String getCustomerOtpSent() {
	return customerOtpSent;
    }

    @JsonProperty("customerOtpSent")
    public void setCustomerOtpSent(String customerOtpSent) {
	this.customerOtpSent = customerOtpSent;
    }

    @JsonProperty("customerOtpValidated")
    public String getCustomerOtpValidated() {
	return customerOtpValidated;
    }

    @JsonProperty("customerOtpValidated")
    public void setCustomerOtpValidated(String customerOtpValidated) {
	this.customerOtpValidated = customerOtpValidated;
    }

    @JsonProperty("accounts")
    public List<Account> getAccounts() {
	return accounts;
    }

    @JsonProperty("accounts")
    public void setAccounts(List<Account> accounts) {
	this.accounts = accounts;
    }
    
    @JsonProperty("yblBankDetails")
    public YBLBankDetails getYblBankDetails() {
		return yblBankDetails;
	}

	public void setYblBankDetails(YBLBankDetails yblBankDetails) {
		this.yblBankDetails = yblBankDetails;
	}

    public String getNisTraceId() {
        return nisTraceId;
    }

    public void setNisTraceId(String nisTraceId) {
        this.nisTraceId = nisTraceId;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "YBLDetails [customerTypeFlag=" + customerTypeFlag + ", customerType=" + customerType + ", customerId=" + customerId + ", bankBranchCode="
		+ bankBranchCode + ", bankBranchName=" + bankBranchName + ", title=" + title + ", customerFullName=" + customerFullName + ", customerFirstName="
		+ customerFirstName + ", customerMiddleName=" + customerMiddleName + ", customerLastName=" + customerLastName + ", dob=" + dob
		+ ", communicationAddressLine1=" + communicationAddressLine1 + ", communicationAddressLine2=" + communicationAddressLine2
		+ ", communicationAddressLine3=" + communicationAddressLine3 + ", city=" + city + ", state=" + state + ", country=" + country + ", pinCode="
		+ pinCode + ", emailId=" + emailId + ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", panCard=" + panCard
		+ ", residentialPhoneNumber=" + residentialPhoneNumber + ", officialPhoneNumber=" + officialPhoneNumber + ", permanentAddressLine1="
		+ permanentAddressLine1 + ", permanentAddressLine2=" + permanentAddressLine2 + ", permanentAddressLine3=" + permanentAddressLine3
		+ ", permanentcity=" + permanentcity + ", permanentstate=" + permanentstate + ", permanentcountry=" + permanentcountry
		+ ", permanentAddressPincode=" + permanentAddressPincode + ", profession=" + profession + ", occupation=" + occupation + ", motherName="
		+ motherName + ", fatherName=" + fatherName + ", maritalStatus=" + maritalStatus + ", passportNumber=" + passportNumber + ", passportIssueDate="
		+ passportIssueDate + ", passportExpDate=" + passportExpDate + ", riskCategory=" + riskCategory + ", customerClassification="
		+ customerClassification + ", salesRM=" + salesRM + ", serviceRM=" + serviceRM + ", asssetRM=" + asssetRM + ", lastKYC=" + lastKYC
		+ ", paasportIssuancePlace=" + paasportIssuancePlace + ", customerSegment=" + customerSegment + ", nomineeDetails=" + nomineeDetails
		+ ", customerOtpSent=" + customerOtpSent + ", customerOtpValidated=" + customerOtpValidated + ", accounts=" + accounts + ", yblBankDetails=" + yblBankDetails 
		+ ", nisTraceId=" + nisTraceId + "]";
    }

}