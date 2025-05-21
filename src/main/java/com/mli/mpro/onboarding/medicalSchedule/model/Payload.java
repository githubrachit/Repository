package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "gender", "policyNumber", "firstName", "dateOfBirth", "mobileNumber", "emailId", "commHouseNo", "commCity", "commState", "commPinCode",
	"isMedicalRequired", "tobaccoConsumption", "amount", "leadID", "prefTestDate", "prefTestTime", "prefLabID", "type", "testlist",
	"caseId","testdate","testTime","labId","proposalNo" })

public class Payload {

    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("firstName")
    private String firstName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("commHouseNo")
    private String commHouseNo;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("commCity")
    private String commCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("commState")
    private String commState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("commPinCode")
    private String commPinCode;
    @JsonProperty("isMedicalRequired")
    private String isMedicalRequired;
    @JsonProperty("tobaccoConsumption")
    private String tobaccoConsumption;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("leadID")
    private String leadID;
    @JsonProperty("prefTestDate")
    private String prefTestDate;
    @JsonProperty("prefTestTime")
    private String prefTestTime;
    @JsonProperty("prefLabID")
    private String prefLabID;
    @JsonProperty("serviceType")
    private String serviceType;
    @JsonProperty("testlist")
    private Testlist testlist;

    @JsonProperty("caseId")
    private String caseId;
    @JsonProperty("testdate")
    private String testdate;
    @JsonProperty("testTime")
    private String testTime;
    @JsonProperty("labId")
    private String labId;
    @JsonProperty("proposalNo")
    private String proposalNo;
    /* end of FUL2-36877 Medical Scheduling Confirmation */


    public Payload() {
	super();
    }

    @JsonProperty("gender")
    public String getGender() {
	return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
	this.gender = gender;
    }

    @JsonProperty("policyNumber")
    public String getPolicyNumber() {
	return policyNumber;
    }

    @JsonProperty("policyNumber")
    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
	return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
	return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("mobileNumber")
    public String getMobileNumber() {
	return mobileNumber;
    }

    @JsonProperty("mobileNumber")
    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    @JsonProperty("emailId")
    public String getEmailId() {
	return emailId;
    }

    @JsonProperty("emailId")
    public void setEmailId(String emailId) {
	this.emailId = emailId;
    }

    @JsonProperty("commHouseNo")
    public String getCommHouseNo() {
	return commHouseNo;
    }

    @JsonProperty("commHouseNo")
    public void setCommHouseNo(String commHouseNo) {
	this.commHouseNo = commHouseNo;
    }

    @JsonProperty("commCity")
    public String getCommCity() {
	return commCity;
    }

    @JsonProperty("commCity")
    public void setCommCity(String commCity) {
	this.commCity = commCity;
    }

    @JsonProperty("commState")
    public String getCommState() {
	return commState;
    }

    @JsonProperty("commState")
    public void setCommState(String commState) {
	this.commState = commState;
    }

    @JsonProperty("commPinCode")
    public String getCommPinCode() {
	return commPinCode;
    }

    @JsonProperty("commPinCode")
    public void setCommPinCode(String commPinCode) {
	this.commPinCode = commPinCode;
    }

    @JsonProperty("isMedicalRequired")
    public String getIsMedicalRequired() {
	return isMedicalRequired;
    }

    @JsonProperty("isMedicalRequired")
    public void setIsMedicalRequired(String isMedicalRequired) {
	this.isMedicalRequired = isMedicalRequired;
    }

    @JsonProperty("tobaccoConsumption")
    public String getTobaccoConsumption() {
	return tobaccoConsumption;
    }

    @JsonProperty("tobaccoConsumption")
    public void setTobaccoConsumption(String tobaccoConsumption) {
	this.tobaccoConsumption = tobaccoConsumption;
    }

    @JsonProperty("amount")
    public String getAmount() {
	return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
	this.amount = amount;
    }

    @JsonProperty("leadID")
    public String getLeadID() {
	return leadID;
    }

    @JsonProperty("leadID")
    public void setLeadID(String leadID) {
	this.leadID = leadID;
    }

    @JsonProperty("prefTestDate")
    public String getPrefTestDate() {
	return prefTestDate;
    }

    @JsonProperty("prefTestDate")
    public void setPrefTestDate(String prefTestDate) {
	this.prefTestDate = prefTestDate;
    }

    @JsonProperty("prefTestTime")
    public String getPrefTestTime() {
	return prefTestTime;
    }

    @JsonProperty("prefTestTime")
    public void setPrefTestTime(String prefTestTime) {
	this.prefTestTime = prefTestTime;
    }

    @JsonProperty("prefLabID")
    public String getPrefLabID() {
	return prefLabID;
    }

    @JsonProperty("prefLabID")
    public void setPrefLabID(String prefLabID) {
	this.prefLabID = prefLabID;
    }

    public String getServiceType() {
	return serviceType;
    }

    public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
    }

    @JsonProperty("testlist")
    public Testlist getTestlist() {
	return testlist;
    }

    @JsonProperty("testlist")
    public void setTestlist(Testlist testlist) {
	this.testlist = testlist;
    }

    @JsonProperty("caseId")
    public String getCaseId() {
		return caseId;
	}

    @JsonProperty("caseId")
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	 @JsonProperty("testdate")
	public String getTestdate() {
		return testdate;
	}

	 @JsonProperty("testdate")
	public void setTestdate(String testdate) {
		this.testdate = testdate;
	}

	 @JsonProperty("testTime")
	public String getTestTime() {
		return testTime;
	}

	 @JsonProperty("testTime")
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	 @JsonProperty("labId")
	public String getLabId() {
		return labId;
	}

	 @JsonProperty("labId")
	public void setLabId(String labId) {
		this.labId = labId;
	}

	 @JsonProperty("proposalNo")
	public String getProposalNo() {
		return proposalNo;
	}

	 @JsonProperty("proposalNo")
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "gender='" + gender + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", commHouseNo='" + commHouseNo + '\'' +
                ", commCity='" + commCity + '\'' +
                ", commState='" + commState + '\'' +
                ", commPinCode='" + commPinCode + '\'' +
                ", isMedicalRequired='" + isMedicalRequired + '\'' +
                ", tobaccoConsumption='" + tobaccoConsumption + '\'' +
                ", amount='" + amount + '\'' +
                ", leadID='" + leadID + '\'' +
                ", prefTestDate='" + prefTestDate + '\'' +
                ", prefTestTime='" + prefTestTime + '\'' +
                ", prefLabID='" + prefLabID + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", testlist=" + testlist +
                ", caseId='" + caseId + '\'' +
                ", testdate='" + testdate + '\'' +
                ", testTime='" + testTime + '\'' +
                ", labId='" + labId + '\'' +
                ", proposalNo='" + proposalNo + '\'' +
                '}';
    }
}
