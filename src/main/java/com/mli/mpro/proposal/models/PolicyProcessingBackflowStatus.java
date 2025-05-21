package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "policyProcessingBackflowStatus")
public class PolicyProcessingBackflowStatus {

    @Id
    private String id;
    private String tppStatus;
    private String tppSubStatus;
    private String statusDescription;
    private String status;

    public PolicyProcessingBackflowStatus() {
	super();
    }

    public String getTppStatus() {
	return tppStatus;
    }

    public void setTppStatus(String tppStatus) {
	this.tppStatus = tppStatus;
    }

    public String getTppSubStatus() {
	return tppSubStatus;
    }

    public void setTppSubStatus(String tppSubStatus) {
	this.tppSubStatus = tppSubStatus;
    }

    public String getStatusDescription() {
	return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
	this.statusDescription = statusDescription;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PolicyProcessingBackflowStatus [id=" + id + ", tppStatus=" + tppStatus + ", tppSubStatus=" + tppSubStatus + ", statusDescription="
		+ statusDescription + ", status=" + status + "]";
    }

}
