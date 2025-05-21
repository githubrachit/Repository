package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * @author akshom4375
 * Model Class for Nominee
 */
public class NomineeDataModel {
	@Sensitive(MaskType.DOB)
	private String dob;
	
	private String title;

	@Sensitive(MaskType.NAME)
	private String guardianName;

	private String guardianRelation;

	@Sensitive(MaskType.FIRST_NAME)
	private String firstName;

	@Sensitive(MaskType.MIDDLE_NAME)
	private String middleName;

	@Sensitive(MaskType.LAST_NAME)
	private String lastName;
	@Sensitive(MaskType.GENDER)
	private String gender;

	private String relation;

	private String percentageShare;

	private String guardianRelationshipWhenOther;

	private String relationshipOthers;

	private String reasonForNomination;

	private boolean minorFlag;

	private boolean showOther;

	private boolean showGuradianOther;

	@Sensitive(MaskType.BANK_NAME)
	private String bankName;

	@Sensitive(MaskType.BANK_BRANCH_NAME)
	private String bankBranch;

	@Sensitive(MaskType.BANK_IFSC)
	private String ifsc;

	@Sensitive(MaskType.BANK_MICR)
	private String micr;

	@Sensitive(MaskType.BANK_ACC_NUM)
	private String bankAccountNumber;
	
	private String nomineeShare;
	
	private String typeOfAccount;

	@Sensitive(MaskType.BANK_ACC_HOLDER_NAME)
	private String accountHolderName;

	@Sensitive(MaskType.FIRST_NAME)
	private String fatherFirstName;

	@Sensitive(MaskType.LAST_NAME)
	private String fatherLastName;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianRelation() {
		return guardianRelation;
	}

	public void setGuardianRelation(String guardianRelation) {
		this.guardianRelation = guardianRelation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPercentageShare() {
		return percentageShare;
	}

	public void setPercentageShare(String percentageShare) {
		this.percentageShare = percentageShare;
	}

	public String getGuardianRelationshipWhenOther() {
		return guardianRelationshipWhenOther;
	}

	public void setGuardianRelationshipWhenOther(String guardianRelationshipWhenOther) {
		this.guardianRelationshipWhenOther = guardianRelationshipWhenOther;
	}

	public String getRelationshipOthers() {
		return relationshipOthers;
	}

	public void setRelationshipOthers(String relationshipOthers) {
		this.relationshipOthers = relationshipOthers;
	}

	public String getReasonForNomination() {
		return reasonForNomination;
	}

	public void setReasonForNomination(String reasonForNomination) {
		this.reasonForNomination = reasonForNomination;
	}

	public boolean getMinorFlag() {
		return minorFlag;
	}

	public void setMinorFlag(boolean minorFlag) {
		this.minorFlag = minorFlag;
	}

	public boolean getShowOther() {
		return showOther;
	}

	public void setShowOther(boolean showOther) {
		this.showOther = showOther;
	}

	public boolean getShowGuradianOther() {
		return showGuradianOther;
	}

	public void setShowGuradianOther(boolean showGuradianOther) {
		this.showGuradianOther = showGuradianOther;
	}

	public String getBankName() {
	    return bankName;
	}

	public void setBankName(String bankName) {
	    this.bankName = bankName;
	}

	public String getBankBranch() {
	    return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
	    this.bankBranch = bankBranch;
	}

	public String getIfsc() {
	    return ifsc;
	}

	public void setIfsc(String ifsc) {
	    this.ifsc = ifsc;
	}

	public String getMicr() {
	    return micr;
	}

	public void setMicr(String micr) {
	    this.micr = micr;
	}

	public String getBankAccountNumber() {
	    return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
	    this.bankAccountNumber = bankAccountNumber;
	}

	public String getNomineeShare() {
	    return nomineeShare;
	}

	public void setNomineeShare(String nomineeShare) {
	    this.nomineeShare = nomineeShare;
	}

	public String getTypeOfAccount() {
	    return typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
	    this.typeOfAccount = typeOfAccount;
	}

	public String getAccountHolderName() {
	    return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
	    this.accountHolderName = accountHolderName;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public NomineeDataModel setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
		return this;
	}

	public String getFatherLastName() {
		return fatherLastName;
	}

	public NomineeDataModel setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
		return this;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "NomineeDataModel{" +
				"dob='" + dob + '\'' +
				", title='" + title + '\'' +
				", guardianName='" + guardianName + '\'' +
				", guardianRelation='" + guardianRelation + '\'' +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", gender='" + gender + '\'' +
				", relation='" + relation + '\'' +
				", percentageShare='" + percentageShare + '\'' +
				", guardianRelationshipWhenOther='" + guardianRelationshipWhenOther + '\'' +
				", relationshipOthers='" + relationshipOthers + '\'' +
				", reasonForNomination='" + reasonForNomination + '\'' +
				", minorFlag=" + minorFlag +
				", showOther=" + showOther +
				", showGuradianOther=" + showGuradianOther +
				", bankName='" + bankName + '\'' +
				", bankBranch='" + bankBranch + '\'' +
				", ifsc='" + ifsc + '\'' +
				", micr='" + micr + '\'' +
				", bankAccountNumber='" + bankAccountNumber + '\'' +
				", nomineeShare='" + nomineeShare + '\'' +
				", typeOfAccount='" + typeOfAccount + '\'' +
				", accountHolderName='" + accountHolderName + '\'' +
				", fatherFirstName='" + fatherFirstName + '\'' +
				", fatherLastName='" + fatherLastName + '\'' +
				'}';
	}
}