package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ProposalNomineeDetails {
    @Sensitive(MaskType.DOB)
    private String dob;
    @Sensitive(MaskType.FIRST_NAME)
    private String firstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    private String middleName;
    @Sensitive(MaskType.LAST_NAME)
    private String lastName;
    private String relationshipWithProposer;
    @Sensitive(MaskType.GENDER)
    private String gender;
    private String title;
    private int percentageShare;
    private String nomineeRealtionshipOther;
    private String guardianRelationshipOther;
    private String nomineeMinor;
    @Sensitive(MaskType.NAME)
    private String guardianName;
    private String guardianRelation;
    private String specifyRelationship;
    private String reasonForNomination;
    private String guardianSpecifyRelation;
    @Sensitive(MaskType.FIRST_NAME)
    private String fatherFirstName;
    @Sensitive(MaskType.LAST_NAME)
    private String fatherLastName;
    @Sensitive(MaskType.FIRST_NAME)
    private String guardianFirstName;
    @Sensitive(MaskType.LAST_NAME)
    private String guardianLastName;
    //FUL2-19322 to save guardian gender
    @Sensitive(MaskType.GENDER)
    private String guardianGender;
    @Sensitive(MaskType.DOB)
    private String appointeeDOB;
    @Sensitive(MaskType.GENDER)
    private String appointeeGender;

    public String getDob() {
	return dob;
    }

    public void setDob(String dob) {
	this.dob = dob;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getRelationshipWithProposer() {
	return relationshipWithProposer;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public int getPercentageShare() {
	return percentageShare;
    }

    public void setPercentageShare(int percentageShare) {
	this.percentageShare = percentageShare;
    }

    public String getMiddleName() {
	return middleName;
    }

    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    public String getNomineeRealtionshipOther() {
	return nomineeRealtionshipOther;
    }

    public void setNomineeRealtionshipOther(String nomineeRealtionshipOther) {
	this.nomineeRealtionshipOther = nomineeRealtionshipOther;
    }

    public String getGuardianRelationshipOther() {
	return guardianRelationshipOther;
    }

    public void setGuardianRelationshipOther(String guardianRelationshipOther) {
	this.guardianRelationshipOther = guardianRelationshipOther;
    }

    public String getNomineeMinor() {
	return nomineeMinor;
    }

    public void setNomineeMinor(String nomineeMinor) {
	this.nomineeMinor = nomineeMinor;
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

    public String getSpecifyRelationship() {
	return specifyRelationship;
    }

    public void setSpecifyRelationship(String specifyRelationship) {
	this.specifyRelationship = specifyRelationship;
    }

    public String getReasonForNomination() {
	return reasonForNomination;
    }

    public void setReasonForNomination(String reasonForNomination) {
	this.reasonForNomination = reasonForNomination;
    }

    public String getGuardianSpecifyRelation() {
	return guardianSpecifyRelation;
    }

    public void setGuardianSpecifyRelation(String guardianSpecifyRelation) {
	this.guardianSpecifyRelation = guardianSpecifyRelation;
    }

    public void setRelationshipWithProposer(String relationshipWithProposer) {
	this.relationshipWithProposer = relationshipWithProposer;
    }

    public String getFatherFirstName() { return fatherFirstName; }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFatherLastName() { return fatherLastName; }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getGuardianFirstName() { return guardianFirstName; }

    public void setGuardianFirstName(String guardianFirstName) { this.guardianFirstName = guardianFirstName; }

    public String getGuardianLastName() { return guardianLastName; }

    public void setGuardianLastName(String guardianLastName) { this.guardianLastName = guardianLastName; }

    public String getGuardianGender() {
        return guardianGender;
    }

    public void setGuardianGender(String guardianGender) {
        this.guardianGender = guardianGender;
    }

    public String getAppointeeDOB() {
        return appointeeDOB;
    }

    public void setAppointeeDOB(String appointeeDOB) {
        this.appointeeDOB = appointeeDOB;
    }


    public String getAppointeeGender() {
        return appointeeGender;
    }

    public void setAppointeeGender(String appointeeGender) {
        this.appointeeGender = appointeeGender;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ProposalNomineeDetails [dob=" + dob + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
		+ ", relationshipWithProposer=" + relationshipWithProposer + ", gender=" + gender + ", title=" + title + ", percentageShare=" + percentageShare
		+ ", nomineeRealtionshipOther=" + nomineeRealtionshipOther + ", guardianRelationshipOther=" + guardianRelationshipOther + ", nomineeMinor="
		+ nomineeMinor + ", guardianName=" + guardianName + ", guardianRelation=" + guardianRelation + ", specifyRelationship=" + specifyRelationship
		+ ", reasonForNomination=" + reasonForNomination + ", guardianSpecifyRelation=" + guardianSpecifyRelation
        + ", fatherFirstName=" + fatherFirstName + ", fatherLastName=" + fatherLastName +",guardianFirstName="+ guardianFirstName +",guardianLastName="+guardianLastName +",appointeeDOB="+appointeeDOB +",appointeeGender= "+appointeeGender +" ]";
    }

    public ProposalNomineeDetails() {
    }

    public ProposalNomineeDetails(String dob, String firstName, String middleName, String lastName, String relationshipWithProposer, String gender, String title, int percentageShare, String nomineeRealtionshipOther, String guardianRelationshipOther, String nomineeMinor, String guardianName, String guardianRelation, String specifyRelationship, String reasonForNomination, String guardianSpecifyRelation, String fatherFirstName, String fatherLastName, String guardianFirstName, String guardianLastName, String guardianGender, String appointeeDOB, String appointeeGender) {
        this.dob = dob;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.relationshipWithProposer = relationshipWithProposer;
        this.gender = gender;
        this.title = title;
        this.percentageShare = percentageShare;
        this.nomineeRealtionshipOther = nomineeRealtionshipOther;
        this.guardianRelationshipOther = guardianRelationshipOther;
        this.nomineeMinor = nomineeMinor;
        this.guardianName = guardianName;
        this.guardianRelation = guardianRelation;
        this.specifyRelationship = specifyRelationship;
        this.reasonForNomination = reasonForNomination;
        this.guardianSpecifyRelation = guardianSpecifyRelation;
        this.fatherFirstName = fatherFirstName;
        this.fatherLastName = fatherLastName;
        this.guardianFirstName = guardianFirstName;
        this.guardianLastName = guardianLastName;
        this.guardianGender = guardianGender;
        this.appointeeDOB = appointeeDOB;
        this.appointeeGender = appointeeGender;
    }
    
    public ProposalNomineeDetails(String defaultValue) {
        this.dob = defaultValue;
        this.firstName = defaultValue;
        this.middleName = defaultValue;
        this.lastName = defaultValue;
        this.relationshipWithProposer = defaultValue;
        this.gender = defaultValue;
        this.title = defaultValue;
        this.percentageShare = 0;
        this.nomineeRealtionshipOther = defaultValue;
        this.guardianRelationshipOther = defaultValue;
        this.nomineeMinor = defaultValue;
        this.guardianName = defaultValue;
        this.guardianRelation = defaultValue;
        this.specifyRelationship = defaultValue;
        this.reasonForNomination = defaultValue;
        this.guardianSpecifyRelation = defaultValue;
        this.fatherFirstName = defaultValue;
        this.fatherLastName = defaultValue;
        this.guardianFirstName = defaultValue;
        this.guardianLastName = defaultValue;
        this.guardianGender = defaultValue;
        this.appointeeDOB = defaultValue;
        this.appointeeGender = defaultValue;
    }
}
