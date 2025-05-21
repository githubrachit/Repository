package com.mli.mpro.location.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "onboarding-otpdetails")
public class OTPDetails {

    @Id
    @JsonProperty("id")
    private String id;
    private long transactionId;
    private java.util.Date otpGeneratedTime;
    private java.util.Date otpSubmittedTime;
    private String actionType;
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public java.util.Date getOtpGeneratedTime() {
		return otpGeneratedTime;
	}

	public void setOtpGeneratedTime(java.util.Date otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}

	public java.util.Date getOtpSubmittedTime() {
		return otpSubmittedTime;
	}

	public void setOtpSubmittedTime(java.util.Date otpSubmittedTime) {
		this.otpSubmittedTime = otpSubmittedTime;
	}

	@Override
    public String toString() {
        return "OTPValidate{" +
                "id='" + id + '\'' +
                ", transactionId=" + transactionId +
                '}';
    }


}
