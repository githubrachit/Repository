package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.Date;

public class DisabilityDeclaration {

    @Sensitive(MaskType.FIRST_NAME)
    private String declarantFirstName;
    @Sensitive(MaskType.LAST_NAME)
    private String declarantLastName;
    @Sensitive(MaskType.MOBILE)
    private String declarantMobileNumber;
    private String declarantRelationshipWithProposer;
    @Sensitive(MaskType.ADDRESS)
    private String declarantAddress;
    private String disabilityDeclarationFlag;
    private String declarantOtpValidated;
    private Date declarantOtpSubmittedDate;
    private String declarantRelationshipWithOthers;

    public String getDeclarantFirstName() {
        return declarantFirstName;
    }

    public void setDeclarantFirstName(String declarantFirstName) {
        this.declarantFirstName = declarantFirstName;
    }

    public String getDeclarantLastName() {
        return declarantLastName;
    }

    public void setDeclarantLastName(String declarantLastName) {
        this.declarantLastName = declarantLastName;
    }

    public String getDeclarantMobileNumber() {
        return declarantMobileNumber;
    }

    public void setDeclarantMobileNumber(String declarantMobileNumber) {
        this.declarantMobileNumber = declarantMobileNumber;
    }

    public String getDeclarantRelationshipWithProposer() {
        return declarantRelationshipWithProposer;
    }

    public void setDeclarantRelationshipWithProposer(String declarantRelationshipWithProposer) {
        this.declarantRelationshipWithProposer = declarantRelationshipWithProposer;
    }

    public String getDeclarantAddress() {
        return declarantAddress;
    }

    public void setDeclarantAddress(String declarantAddress) {
        this.declarantAddress = declarantAddress;
    }

    public String getDisabilityDeclarationFlag() {
        return disabilityDeclarationFlag;
    }

    public void setDisabilityDeclarationFlag(String disabilityDeclarationFlag) {
        this.disabilityDeclarationFlag = disabilityDeclarationFlag;
    }

    public String getDeclarantOtpValidated() {
        return declarantOtpValidated;
    }

    public void setDeclarantOtpValidated(String declarantOtpValidated) {
        this.declarantOtpValidated = declarantOtpValidated;
    }

    public Date getDeclarantOtpSubmittedDate() {
        return declarantOtpSubmittedDate;
    }

    public void setDeclarantOtpSubmittedDate(Date declarantOtpSubmittedDate) {
        this.declarantOtpSubmittedDate = declarantOtpSubmittedDate;
    }

    public String getDeclarantRelationshipWithOthers() {
        return declarantRelationshipWithOthers;
    }

    public void setDeclarantRelationshipWithOthers(String declarantRelationshipWithOthers) {
        this.declarantRelationshipWithOthers = declarantRelationshipWithOthers;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "DisabilityDeclaration{" +
                "declarantFirstName='" + declarantFirstName + '\'' +
                ", declarantLastName='" + declarantLastName + '\'' +
                ", declarantMobileNumber='" + declarantMobileNumber + '\'' +
                ", declarantRelationshipWithProposer='" + declarantRelationshipWithProposer + '\'' +
                ", declarantAddress='" + declarantAddress + '\'' +
                ", disabilityDeclarationFlag='" + disabilityDeclarationFlag + '\'' +
                ", declarantOtpValidated='" + declarantOtpValidated + '\'' +
                ", declarantOtpSubmittedDate=" + declarantOtpSubmittedDate +
                ", declarantRelationshipWithOthers=" + declarantRelationshipWithOthers +
                '}';
    }
}
