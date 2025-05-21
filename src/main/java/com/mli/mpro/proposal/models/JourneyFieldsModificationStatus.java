package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "panStatus", "firstNameStatus", "lastNameStatus", "dobStatus", "communicationAddStatus", "permanentAddStatus" })
public class JourneyFieldsModificationStatus {

    @JsonProperty("panStatus")
    private String panStatus;
    @JsonProperty("nameStatus")
    private String nameStatus;
    @JsonProperty("dobStatus")
    private String dobStatus;
    @JsonProperty("communicationAddStatus")
    private String communicationAddStatus;
    @JsonProperty("permanentAddStatus")
    private String permanentAddStatus;
    @JsonProperty("neftBankDetailsStatus")
    private String neftBankDetailsStatus;

    public JourneyFieldsModificationStatus() {
	super();
    }

    public JourneyFieldsModificationStatus(String panStatus, String nameStatus, String dobStatus, String communicationAddStatus, String permanentAddStatus) {
	super();
	this.panStatus = panStatus;
	this.nameStatus = nameStatus;
	this.dobStatus = dobStatus;
	this.communicationAddStatus = communicationAddStatus;
	this.permanentAddStatus = permanentAddStatus;
    }

    @JsonProperty("panStatus")
    public String getPanStatus() {
	return panStatus;
    }

    @JsonProperty("panStatus")
    public void setPanStatus(String panStatus) {
	this.panStatus = panStatus;
    }

    public String getNameStatus() {
	return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
	this.nameStatus = nameStatus;
    }

    @JsonProperty("dobStatus")
    public String getDobStatus() {
	return dobStatus;
    }

    @JsonProperty("dobStatus")
    public void setDobStatus(String dobStatus) {
	this.dobStatus = dobStatus;
    }

    @JsonProperty("communicationAddStatus")
    public String getCommunicationAddStatus() {
	return communicationAddStatus;
    }

    @JsonProperty("communicationAddStatus")
    public void setCommunicationAddStatus(String communicationAddStatus) {
	this.communicationAddStatus = communicationAddStatus;
    }

    @JsonProperty("permanentAddStatus")
    public String getPermanentAddStatus() {
	return permanentAddStatus;
    }

    @JsonProperty("permanentAddStatus")
    public void setPermanentAddStatus(String permanentAddStatus) {
	this.permanentAddStatus = permanentAddStatus;
    }

    public String getNeftBankDetailsStatus() {
        return neftBankDetailsStatus;
    }

    public void setNeftBankDetailsStatus(String neftBankDetailsStatus) {
        this.neftBankDetailsStatus = neftBankDetailsStatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "JourneyFieldsModificationStatus [panStatus=" + panStatus + ", nameStatus=" + nameStatus + ", dobStatus=" + dobStatus
		+ ", communicationAddStatus=" + communicationAddStatus + ", permanentAddStatus=" + permanentAddStatus +  ", neftBankDetailsStatus=" + neftBankDetailsStatus + "]";
    }

}