package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.StringJoiner;

public class CibilDetails {

    private List<BureauResponse> bureauResponse;
    private String trlScore;
    private String affulentOrNot;
    private String scoreType;
    private String applicationId;
    @Sensitive(MaskType.NAME)
    private String name;
    private String nameStatus;
    @Sensitive(MaskType.DOB)
    private String dob;
    private String dobStatus;
    @Sensitive(MaskType.PAN_NUM)
    private String panNumber;
    private String panNumberStatus;
    @Sensitive(MaskType.ADDRESS)
    private String address;
    @Sensitive(MaskType.PINCODE)
    private String pincode;

    public List<BureauResponse> getBureauResponse() {
	return bureauResponse;
    }

    public void setBureauResponse(List<BureauResponse> bureauResponse) {
	this.bureauResponse = bureauResponse;
    }

    public String getTrlScore() {
	return trlScore;
    }

    public void setTrlScore(String trlScore) {
	this.trlScore = trlScore;
    }

    public String getAffulentOrNot() {
	return affulentOrNot;
    }

    public void setAffulentOrNot(String affulentOrNot) {
	this.affulentOrNot = affulentOrNot;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameStatus() { return nameStatus; }

    public void setNameStatus(String nameStatus) { this.nameStatus = nameStatus; }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDobStatus() { return dobStatus; }

    public void setDobStatus(String dobStatus) { this.dobStatus = dobStatus; }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPanNumberStatus() { return panNumberStatus; }

    public void setPanNumberStatus(String panNumberStatus) { this.panNumberStatus = panNumberStatus; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", CibilDetails.class.getSimpleName() + "[", "]")
                .add("bureauResponse=" + bureauResponse)
                .add("trlScore='" + trlScore + "'")
                .add("affulentOrNot='" + affulentOrNot + "'")
                .add("scoreType='" + scoreType + "'")
                .add("applicationId='" + applicationId + "'")
                .add("name='" + name + "'")
                .add("nameStatus='" + nameStatus + "'")
                .add("dob='" + dob + "'")
                .add("dobStatus='" + dobStatus + "'")
                .add("panNumber='" + panNumber + "'")
                .add("panNumberStatus='" + panNumberStatus + "'")
                .add("address='" + address + "'")
                .add("pincode='" + pincode + "'")
                .toString();
    }

}
