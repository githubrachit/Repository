package com.mli.mpro.proposal.models;

import java.util.Date;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "policyNumber")
public class PolicyNumberGeneration {

    @Id
    @JsonProperty("id")
    private String id;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNumber;
    private boolean numberUsed;
    private Date updatedTime;

    PolicyNumberGeneration() {
    }

    public PolicyNumberGeneration(String id, String policyNumber, boolean numberUsed, Date updatedTime) {
	super();
	this.id = id;
	this.policyNumber = policyNumber;
	this.numberUsed = numberUsed;
	this.updatedTime = updatedTime;
    }

    public String getPolicyNumber() {
	return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    public boolean isNumberUsed() {
	return numberUsed;
    }

    public void setNumberUsed(boolean numberUsed) {
	this.numberUsed = numberUsed;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Date getUpdatedTime() {
	return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
	this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PolicyNumberGeneration [id=" + id + ", policyNumber=" + policyNumber + ", numberUsed=" + numberUsed + "]";
    }

}
