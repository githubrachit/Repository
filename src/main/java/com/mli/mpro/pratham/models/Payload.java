
package com.mli.mpro.pratham.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "policyNo", "goCode", "basePlanType", "objectiveOfInsurance", "proposorTitle", "proposorFirstname",
		"proposorMiddlename", "proposorLastname", "proposorDob", "proposorGender", "lifeinsuredTitle",
		"lifeinsuredFirstname", "lifeinsuredMiddlename", "lifeinsuredLastname", "lifeinsuredDob", "lifeinsuredGender",
		"preferredMailingAddress", "panNo", "relationshipWithLifeInsured", "nomineeDetails", "appointeeTitle",
		"appointeeFirstname", "appointeeMiddlename", "appointeeLastname", "appointeeDob", "appointeeGender", "mobile1",
		"mobile2", "emailid", "bankAccountNo", "bankName", "ifscCode", "micrCode", "planName", "sumAssured",
		"issuanceDate", "commencementDate", "policyTerm", "premiumPaymentTerm", "premiumFrequency", "modalPremium",
		"premiumMethod", "basePremium", "annulaisedPremium", "smokerCode", "stampDutyAmount", "billingDrawDate",
		"pepQuestion", "nonforfeitureoption", "bonusOption", "agentCode", "agentName", "agentAddress", "agentContact",
		"guaranteedDeathBenefit", "riderDetails" })
public class Payload {

	@Sensitive(MaskType.POLICY_NUM)
	@JsonProperty("policyNo")
	private String policyNo;
	@JsonProperty("goCode")
	private String goCode;
	@JsonProperty("basePlanType")
	private String basePlanType;
	@JsonProperty("objectiveOfInsurance")
	private String objectiveOfInsurance;
	@JsonProperty("proposorTitle")
	private String proposorTitle;
	@Sensitive(MaskType.FIRST_NAME)
	@JsonProperty("proposorFirstname")
	private String proposorFirstname;
	@Sensitive(MaskType.MIDDLE_NAME)
	@JsonProperty("proposorMiddlename")
	private String proposorMiddlename;
	@Sensitive(MaskType.LAST_NAME)
	@JsonProperty("proposorLastname")
	private String proposorLastname;
	@Sensitive(MaskType.DOB)
	@JsonProperty("proposorDob")
	private String proposorDob;
	@Sensitive(MaskType.GENDER)
	@JsonProperty("proposorGender")
	private String proposorGender;
	@JsonProperty("lifeinsuredTitle")
	private String lifeinsuredTitle;
	@Sensitive(MaskType.FIRST_NAME)
	@JsonProperty("lifeinsuredFirstname")
	private String lifeinsuredFirstname;
	@Sensitive(MaskType.MIDDLE_NAME)
	@JsonProperty("lifeinsuredMiddlename")
	private String lifeinsuredMiddlename;
	@Sensitive(MaskType.LAST_NAME)
	@JsonProperty("lifeinsuredLastname")
	private String lifeinsuredLastname;
	@Sensitive(MaskType.DOB)
	@JsonProperty("lifeinsuredDob")
	private String lifeinsuredDob;
	@Sensitive(MaskType.GENDER)
	@JsonProperty("lifeinsuredGender")
	private String lifeinsuredGender;
	@Sensitive(MaskType.ADDRESS)
	@JsonProperty("preferredMailingAddress")
	private String preferredMailingAddress;
	@Sensitive(MaskType.PAN_NUM)
	@JsonProperty("panNo")
	private String panNo;
	@JsonProperty("relationshipWithLifeInsured")
	private String relationshipWithLifeInsured;
	@JsonProperty("nomineeDetails")
	private List<NomineeDetail> nomineeDetails = null;
	@JsonProperty("appointeeTitle")
	private String appointeeTitle;
	@Sensitive(MaskType.FIRST_NAME)
	@JsonProperty("appointeeFirstname")
	private String appointeeFirstname;
	@Sensitive(MaskType.MIDDLE_NAME)
	@JsonProperty("appointeeMiddlename")
	private String appointeeMiddlename;
	@Sensitive(MaskType.LAST_NAME)
	@JsonProperty("appointeeLastname")
	private String appointeeLastname;
	@Sensitive(MaskType.DOB)
	@JsonProperty("appointeeDob")
	private String appointeeDob;
	@Sensitive(MaskType.GENDER)
	@JsonProperty("appointeeGender")
	private String appointeeGender;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("mobile1")
	private String mobile1;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("mobile2")
	private String mobile2;
	@Sensitive(MaskType.EMAIL)
	@JsonProperty("emailid")
	private String emailid;
	@Sensitive(MaskType.BANK_ACC_NUM)
	@JsonProperty("bankAccountNo")
	private String bankAccountNo;
	@Sensitive(MaskType.BANK_NAME)
	@JsonProperty("bankName")
	private String bankName;
	@Sensitive(MaskType.BANK_IFSC)
	@JsonProperty("ifscCode")
	private String ifscCode;
	@Sensitive(MaskType.BANK_MICR)
	@JsonProperty("micrCode")
	private String micrCode;
	@JsonProperty("planName")
	private String planName;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("sumAssured")
	private String sumAssured;
	@JsonProperty("issuanceDate")
	private String issuanceDate;
	@JsonProperty("commencementDate")
	private String commencementDate;
	@JsonProperty("policyTerm")
	private String policyTerm;
	@JsonProperty("premiumPaymentTerm")
	private String premiumPaymentTerm;
	@JsonProperty("premiumFrequency")
	private String premiumFrequency;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("modalPremium")
	private String modalPremium;
	@JsonProperty("premiumMethod")
	private String premiumMethod;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("basePremium")
	private String basePremium;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("annulaisedPremium")
	private String annulaisedPremium;
	@JsonProperty("smokerCode")
	private String smokerCode;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("stampDutyAmount")
	private String stampDutyAmount;
	@JsonProperty("billingDrawDate")
	private String billingDrawDate;
	@JsonProperty("pepQuestion")
	private String pepQuestion;
	@JsonProperty("nonforfeitureoption")
	private String nonforfeitureoption;
	@JsonProperty("bonusOption")
	private String bonusOption;
	@JsonProperty("agentCode")
	private String agentCode;
	@Sensitive(MaskType.NAME)
	@JsonProperty("agentName")
	private String agentName;
	@Sensitive(MaskType.ADDRESS)
	@JsonProperty("agentAddress")
	private String agentAddress;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("agentContact")
	private String agentContact;
	@JsonProperty("guaranteedDeathBenefit")
	private String guaranteedDeathBenefit;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("incomepayout")
	private String incomepayout;
	@JsonProperty("riderDetails")
	private List<RiderDetail> riderDetails = null;

	@JsonProperty("policyNo")
	public String getPolicyNo() {
		return policyNo;
	}

	@JsonProperty("policyNo")
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@JsonProperty("goCode")
	public String getGoCode() {
		return goCode;
	}

	@JsonProperty("goCode")
	public void setGoCode(String goCode) {
		this.goCode = goCode;
	}

	@JsonProperty("basePlanType")
	public String getBasePlanType() {
		return basePlanType;
	}

	@JsonProperty("basePlanType")
	public void setBasePlanType(String basePlanType) {
		this.basePlanType = basePlanType;
	}

	@JsonProperty("objectiveOfInsurance")
	public String getObjectiveOfInsurance() {
		return objectiveOfInsurance;
	}

	@JsonProperty("objectiveOfInsurance")
	public void setObjectiveOfInsurance(String objectiveOfInsurance) {
		this.objectiveOfInsurance = objectiveOfInsurance;
	}

	@JsonProperty("proposorTitle")
	public String getProposorTitle() {
		return proposorTitle;
	}

	@JsonProperty("proposorTitle")
	public void setProposorTitle(String proposorTitle) {
		this.proposorTitle = proposorTitle;
	}

	@JsonProperty("proposorFirstname")
	public String getProposorFirstname() {
		return proposorFirstname;
	}

	@JsonProperty("proposorFirstname")
	public void setProposorFirstname(String proposorFirstname) {
		this.proposorFirstname = proposorFirstname;
	}

	@JsonProperty("proposorMiddlename")
	public String getProposorMiddlename() {
		return proposorMiddlename;
	}

	@JsonProperty("proposorMiddlename")
	public void setProposorMiddlename(String proposorMiddlename) {
		this.proposorMiddlename = proposorMiddlename;
	}

	@JsonProperty("proposorLastname")
	public String getProposorLastname() {
		return proposorLastname;
	}

	@JsonProperty("proposorLastname")
	public void setProposorLastname(String proposorLastname) {
		this.proposorLastname = proposorLastname;
	}

	@JsonProperty("proposorDob")
	public String getProposorDob() {
		return proposorDob;
	}

	@JsonProperty("proposorDob")
	public void setProposorDob(String proposorDob) {
		this.proposorDob = proposorDob;
	}

	@JsonProperty("proposorGender")
	public String getProposorGender() {
		return proposorGender;
	}

	@JsonProperty("proposorGender")
	public void setProposorGender(String proposorGender) {
		this.proposorGender = proposorGender;
	}

	@JsonProperty("lifeinsuredTitle")
	public String getLifeinsuredTitle() {
		return lifeinsuredTitle;
	}

	@JsonProperty("lifeinsuredTitle")
	public void setLifeinsuredTitle(String lifeinsuredTitle) {
		this.lifeinsuredTitle = lifeinsuredTitle;
	}

	@JsonProperty("lifeinsuredFirstname")
	public String getLifeinsuredFirstname() {
		return lifeinsuredFirstname;
	}

	@JsonProperty("lifeinsuredFirstname")
	public void setLifeinsuredFirstname(String lifeinsuredFirstname) {
		this.lifeinsuredFirstname = lifeinsuredFirstname;
	}

	@JsonProperty("lifeinsuredMiddlename")
	public String getLifeinsuredMiddlename() {
		return lifeinsuredMiddlename;
	}

	@JsonProperty("lifeinsuredMiddlename")
	public void setLifeinsuredMiddlename(String lifeinsuredMiddlename) {
		this.lifeinsuredMiddlename = lifeinsuredMiddlename;
	}

	@JsonProperty("lifeinsuredLastname")
	public String getLifeinsuredLastname() {
		return lifeinsuredLastname;
	}

	@JsonProperty("lifeinsuredLastname")
	public void setLifeinsuredLastname(String lifeinsuredLastname) {
		this.lifeinsuredLastname = lifeinsuredLastname;
	}

	@JsonProperty("lifeinsuredDob")
	public String getLifeinsuredDob() {
		return lifeinsuredDob;
	}

	@JsonProperty("lifeinsuredDob")
	public void setLifeinsuredDob(String lifeinsuredDob) {
		this.lifeinsuredDob = lifeinsuredDob;
	}

	@JsonProperty("lifeinsuredGender")
	public String getLifeinsuredGender() {
		return lifeinsuredGender;
	}

	@JsonProperty("lifeinsuredGender")
	public void setLifeinsuredGender(String lifeinsuredGender) {
		this.lifeinsuredGender = lifeinsuredGender;
	}

	@JsonProperty("preferredMailingAddress")
	public String getPreferredMailingAddress() {
		return preferredMailingAddress;
	}

	@JsonProperty("preferredMailingAddress")
	public void setPreferredMailingAddress(String preferredMailingAddress) {
		this.preferredMailingAddress = preferredMailingAddress;
	}

	@JsonProperty("panNo")
	public String getPanNo() {
		return panNo;
	}

	@JsonProperty("panNo")
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	@JsonProperty("relationshipWithLifeInsured")
	public String getRelationshipWithLifeInsured() {
		return relationshipWithLifeInsured;
	}

	@JsonProperty("relationshipWithLifeInsured")
	public void setRelationshipWithLifeInsured(String relationshipWithLifeInsured) {
		this.relationshipWithLifeInsured = relationshipWithLifeInsured;
	}

	@JsonProperty("nomineeDetails")
	public List<NomineeDetail> getNomineeDetails() {
		return nomineeDetails;
	}

	@JsonProperty("nomineeDetails")
	public void setNomineeDetails(List<NomineeDetail> nomineeDetails) {
		this.nomineeDetails = nomineeDetails;
	}

	@JsonProperty("appointeeTitle")
	public String getAppointeeTitle() {
		return appointeeTitle;
	}

	@JsonProperty("appointeeTitle")
	public void setAppointeeTitle(String appointeeTitle) {
		this.appointeeTitle = appointeeTitle;
	}

	@JsonProperty("appointeeFirstname")
	public String getAppointeeFirstname() {
		return appointeeFirstname;
	}

	@JsonProperty("appointeeFirstname")
	public void setAppointeeFirstname(String appointeeFirstname) {
		this.appointeeFirstname = appointeeFirstname;
	}

	@JsonProperty("appointeeMiddlename")
	public String getAppointeeMiddlename() {
		return appointeeMiddlename;
	}

	@JsonProperty("appointeeMiddlename")
	public void setAppointeeMiddlename(String appointeeMiddlename) {
		this.appointeeMiddlename = appointeeMiddlename;
	}

	@JsonProperty("appointeeLastname")
	public String getAppointeeLastname() {
		return appointeeLastname;
	}

	@JsonProperty("appointeeLastname")
	public void setAppointeeLastname(String appointeeLastname) {
		this.appointeeLastname = appointeeLastname;
	}

	@JsonProperty("appointeeDob")
	public String getAppointeeDob() {
		return appointeeDob;
	}

	@JsonProperty("appointeeDob")
	public void setAppointeeDob(String appointeeDob) {
		this.appointeeDob = appointeeDob;
	}

	@JsonProperty("appointeeGender")
	public String getAppointeeGender() {
		return appointeeGender;
	}

	@JsonProperty("appointeeGender")
	public void setAppointeeGender(String appointeeGender) {
		this.appointeeGender = appointeeGender;
	}

	@JsonProperty("mobile1")
	public String getMobile1() {
		return mobile1;
	}

	@JsonProperty("mobile1")
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	@JsonProperty("mobile2")
	public String getMobile2() {
		return mobile2;
	}

	@JsonProperty("mobile2")
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	@JsonProperty("emailid")
	public String getEmailid() {
		return emailid;
	}

	@JsonProperty("emailid")
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@JsonProperty("bankAccountNo")
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	@JsonProperty("bankAccountNo")
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	@JsonProperty("bankName")
	public String getBankName() {
		return bankName;
	}

	@JsonProperty("bankName")
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@JsonProperty("ifscCode")
	public String getIfscCode() {
		return ifscCode;
	}

	@JsonProperty("ifscCode")
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	@JsonProperty("micrCode")
	public String getMicrCode() {
		return micrCode;
	}

	@JsonProperty("micrCode")
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	@JsonProperty("planName")
	public String getPlanName() {
		return planName;
	}

	@JsonProperty("planName")
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@JsonProperty("sumAssured")
	public String getSumAssured() {
		return sumAssured;
	}

	@JsonProperty("sumAssured")
	public void setSumAssured(String sumAssured) {
		this.sumAssured = sumAssured;
	}

	@JsonProperty("issuanceDate")
	public String getIssuanceDate() {
		return issuanceDate;
	}

	@JsonProperty("issuanceDate")
	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	@JsonProperty("commencementDate")
	public String getCommencementDate() {
		return commencementDate;
	}

	@JsonProperty("commencementDate")
	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}

	@JsonProperty("policyTerm")
	public String getPolicyTerm() {
		return policyTerm;
	}

	@JsonProperty("policyTerm")
	public void setPolicyTerm(String policyTerm) {
		this.policyTerm = policyTerm;
	}

	@JsonProperty("premiumPaymentTerm")
	public String getPremiumPaymentTerm() {
		return premiumPaymentTerm;
	}

	@JsonProperty("premiumPaymentTerm")
	public void setPremiumPaymentTerm(String premiumPaymentTerm) {
		this.premiumPaymentTerm = premiumPaymentTerm;
	}

	@JsonProperty("premiumFrequency")
	public String getPremiumFrequency() {
		return premiumFrequency;
	}

	@JsonProperty("premiumFrequency")
	public void setPremiumFrequency(String premiumFrequency) {
		this.premiumFrequency = premiumFrequency;
	}

	@JsonProperty("modalPremium")
	public String getModalPremium() {
		return modalPremium;
	}

	@JsonProperty("modalPremium")
	public void setModalPremium(String modalPremium) {
		this.modalPremium = modalPremium;
	}

	@JsonProperty("premiumMethod")
	public String getPremiumMethod() {
		return premiumMethod;
	}

	@JsonProperty("premiumMethod")
	public void setPremiumMethod(String premiumMethod) {
		this.premiumMethod = premiumMethod;
	}

	@JsonProperty("basePremium")
	public String getBasePremium() {
		return basePremium;
	}

	@JsonProperty("basePremium")
	public void setBasePremium(String basePremium) {
		this.basePremium = basePremium;
	}

	@JsonProperty("annulaisedPremium")
	public String getAnnulaisedPremium() {
		return annulaisedPremium;
	}

	@JsonProperty("annulaisedPremium")
	public void setAnnulaisedPremium(String annulaisedPremium) {
		this.annulaisedPremium = annulaisedPremium;
	}

	@JsonProperty("smokerCode")
	public String getSmokerCode() {
		return smokerCode;
	}

	@JsonProperty("smokerCode")
	public void setSmokerCode(String smokerCode) {
		this.smokerCode = smokerCode;
	}

	@JsonProperty("stampDutyAmount")
	public String getStampDutyAmount() {
		return stampDutyAmount;
	}

	@JsonProperty("stampDutyAmount")
	public void setStampDutyAmount(String stampDutyAmount) {
		this.stampDutyAmount = stampDutyAmount;
	}

	@JsonProperty("billingDrawDate")
	public String getBillingDrawDate() {
		return billingDrawDate;
	}

	@JsonProperty("billingDrawDate")
	public void setBillingDrawDate(String billingDrawDate) {
		this.billingDrawDate = billingDrawDate;
	}

	@JsonProperty("pepQuestion")
	public String getPepQuestion() {
		return pepQuestion;
	}

	@JsonProperty("pepQuestion")
	public void setPepQuestion(String pepQuestion) {
		this.pepQuestion = pepQuestion;
	}

	@JsonProperty("nonforfeitureoption")
	public String getNonforfeitureoption() {
		return nonforfeitureoption;
	}

	@JsonProperty("nonforfeitureoption")
	public void setNonforfeitureoption(String nonforfeitureoption) {
		this.nonforfeitureoption = nonforfeitureoption;
	}

	@JsonProperty("bonusOption")
	public String getBonusOption() {
		return bonusOption;
	}

	@JsonProperty("bonusOption")
	public void setBonusOption(String bonusOption) {
		this.bonusOption = bonusOption;
	}

	@JsonProperty("agentCode")
	public String getAgentCode() {
		return agentCode;
	}

	@JsonProperty("agentCode")
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	@JsonProperty("agentName")
	public String getAgentName() {
		return agentName;
	}

	@JsonProperty("agentName")
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@JsonProperty("agentAddress")
	public String getAgentAddress() {
		return agentAddress;
	}

	@JsonProperty("agentAddress")
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	@JsonProperty("agentContact")
	public String getAgentContact() {
		return agentContact;
	}

	@JsonProperty("agentContact")
	public void setAgentContact(String agentContact) {
		this.agentContact = agentContact;
	}

	@JsonProperty("guaranteedDeathBenefit")
	public String getGuaranteedDeathBenefit() {
		return guaranteedDeathBenefit;
	}

	@JsonProperty("guaranteedDeathBenefit")
	public void setGuaranteedDeathBenefit(String guaranteedDeathBenefit) {
		this.guaranteedDeathBenefit = guaranteedDeathBenefit;
	}

	@JsonProperty("riderDetails")
	public List<RiderDetail> getRiderDetails() {
		return riderDetails;
	}

	@JsonProperty("riderDetails")
	public void setRiderDetails(List<RiderDetail> riderDetails) {
		this.riderDetails = riderDetails;
	}
	@JsonProperty("incomepayout")
	public String getIncomepayout() {
		return incomepayout;
	}
	@JsonProperty("incomepayout")
	public void setIncomepayout(String incomepayout) {
		this.incomepayout = incomepayout;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "Payload [policyNo=" + policyNo + ", goCode=" + goCode + ", basePlanType=" + basePlanType
				+ ", objectiveOfInsurance=" + objectiveOfInsurance + ", proposorTitle=" + proposorTitle
				+ ", proposorFirstname=" + proposorFirstname + ", proposorMiddlename=" + proposorMiddlename
				+ ", proposorLastname=" + proposorLastname + ", proposorDob=" + proposorDob + ", proposorGender="
				+ proposorGender + ", lifeinsuredTitle=" + lifeinsuredTitle + ", lifeinsuredFirstname="
				+ lifeinsuredFirstname + ", lifeinsuredMiddlename=" + lifeinsuredMiddlename + ", lifeinsuredLastname="
				+ lifeinsuredLastname + ", lifeinsuredDob=" + lifeinsuredDob + ", lifeinsuredGender="
				+ lifeinsuredGender + ", preferredMailingAddress=" + preferredMailingAddress + ", panNo=" + panNo
				+ ", relationshipWithLifeInsured=" + relationshipWithLifeInsured + ", nomineeDetails=" + nomineeDetails
				+ ", appointeeTitle=" + appointeeTitle + ", appointeeFirstname=" + appointeeFirstname
				+ ", appointeeMiddlename=" + appointeeMiddlename + ", appointeeLastname=" + appointeeLastname
				+ ", appointeeDob=" + appointeeDob + ", appointeeGender=" + appointeeGender + ", mobile1=" + mobile1
				+ ", mobile2=" + mobile2 + ", emailid=" + emailid + ", bankAccountNo=" + bankAccountNo + ", bankName="
				+ bankName + ", ifscCode=" + ifscCode + ", micrCode=" + micrCode + ", planName=" + planName
				+ ", sumAssured=" + sumAssured + ", issuanceDate=" + issuanceDate + ", commencementDate="
				+ commencementDate + ", policyTerm=" + policyTerm + ", premiumPaymentTerm=" + premiumPaymentTerm
				+ ", premiumFrequency=" + premiumFrequency + ", modalPremium=" + modalPremium + ", premiumMethod="
				+ premiumMethod + ", basePremium=" + basePremium + ", annulaisedPremium=" + annulaisedPremium
				+ ", smokerCode=" + smokerCode + ", stampDutyAmount=" + stampDutyAmount + ", billingDrawDate="
				+ billingDrawDate + ", pepQuestion=" + pepQuestion + ", nonforfeitureoption=" + nonforfeitureoption
				+ ", bonusOption=" + bonusOption + ", agentCode=" + agentCode + ", agentName=" + agentName
				+ ", agentAddress=" + agentAddress + ", agentContact=" + agentContact + ", guaranteedDeathBenefit="
				+ guaranteedDeathBenefit + ", riderDetails=" + riderDetails + "]";
	}

}
