package com.mli.mpro.docsApp.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class AddProposal {
	@Sensitive(MaskType.AMOUNT)
	private String amount;
	@Sensitive(MaskType.GENDER)
	private String gender;

	@Sensitive(MaskType.MOBILE)
	private String mobileNumber;

	private String medicalTime;

	@Sensitive(MaskType.POLICY_NUM)
	private List<String> policyNumber;

	private String commHouseNo;

	private Boolean tobaccoConsumption;

	@Sensitive(MaskType.NAME)
	private String nomineeName;
	@Sensitive(MaskType.EMAIL)
	private String emailId;

	private String isMedicalRequired;
	@Sensitive(MaskType.PINCODE)
	private String commPinCode;
	@Sensitive(MaskType.ADDRESS)
	private String commState;

	private String medicalDate;

	private String visitType;

	private String medicalCenter;

	@Sensitive(MaskType.FIRST_NAME)
	private String firstName;
	@Sensitive(MaskType.PAN_NUM)
	private String panCard;

	private List<String> productCode;
	@Sensitive(MaskType.DOB)
	private String dob;

	private String medicalCategory;

	@Sensitive(MaskType.DOB)
	private String nomineeDob;

	@Sensitive(MaskType.NAME)
	private String packageName;

	@Sensitive(MaskType.ADDRESS)
	private String commCity;

	private List<String> testName;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMedicalTime() {
		return medicalTime;
	}

	public void setMedicalTime(String medicalTime) {
		this.medicalTime = medicalTime;
	}

	public List<String> getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(List<String> policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getCommHouseNo() {
		return commHouseNo;
	}

	public void setCommHouseNo(String commHouseNo) {
		this.commHouseNo = commHouseNo;
	}


	public Boolean getTobaccoConsumption() {
		return tobaccoConsumption;
	}

	public void setTobaccoConsumption(Boolean tobaccoConsumption) {
		this.tobaccoConsumption = tobaccoConsumption;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIsMedicalRequired() {
		return isMedicalRequired;
	}

	public void setIsMedicalRequired(String isMedicalRequired) {
		this.isMedicalRequired = isMedicalRequired;
	}

	public String getCommPinCode() {
		return commPinCode;
	}

	public void setCommPinCode(String commPinCode) {
		this.commPinCode = commPinCode;
	}

	public String getCommState() {
		return commState;
	}

	public void setCommState(String commState) {
		this.commState = commState;
	}


	public String getMedicalDate() {
		return medicalDate;
	}

	public void setMedicalDate(String medicalDate) {
		this.medicalDate = medicalDate;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNomineeDob() {
		return nomineeDob;
	}

	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}
	public List<String> getProductCode() {
		return productCode;
	}

	public void setProductCode(List<String> productCode) {
		this.productCode = productCode;
	}

	public String getMedicalCategory() {
		return medicalCategory;
	}

	public void setMedicalCategory(String medicalCategory) {
		this.medicalCategory = medicalCategory;
	}


	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCommCity() {
		return commCity;
	}

	public void setCommCity(String commCity) {
		this.commCity = commCity;
	}

	public List<String> getTestName() {
		return testName;
	}

	public void setTestName(List<String> testName) {
		this.testName = testName;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "AddProposal{" +
				"amount='" + amount + '\'' +
				", gender='" + gender + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", medicalTime='" + medicalTime + '\'' +
				", policyNumber=" + policyNumber +
				", commHouseNo='" + commHouseNo + '\'' +
				", tobaccoConsumption=" + tobaccoConsumption +
				", nomineeName='" + nomineeName + '\'' +
				", emailId='" + emailId + '\'' +
				", isMedicalRequired='" + isMedicalRequired + '\'' +
				", commPinCode='" + commPinCode + '\'' +
				", commState='" + commState + '\'' +
				", medicalDate='" + medicalDate + '\'' +
				", visitType='" + visitType + '\'' +
				", medicalCenter='" + medicalCenter + '\'' +
				", firstName='" + firstName + '\'' +
				", panCard='" + panCard + '\'' +
				", productCode=" + productCode +
				", dob='" + dob + '\'' +
				", medicalCategory='" + medicalCategory + '\'' +
				", nomineeDob='" + nomineeDob + '\'' +
				", packageName='" + packageName + '\'' +
				", commCity='" + commCity + '\'' +
				", testName=" + testName +
				'}';
	}
}
