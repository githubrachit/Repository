package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.Date;

public class OnboardingEvent {

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("eventCreatedTime")
    private Date eventCreatedTime;
    @JsonProperty("eventCompletedTime")
    private Date eventCompletedTime;
    @JsonProperty("isEventDone")
    private Boolean isEventDone;
    @JsonProperty("bankCorrelationId")
    private String bankCorrelationId;

    @JsonProperty("otpGeneratedTime")
    private Date otpGeneratedTime;
    @JsonProperty("otpSubmittedTime")
    private Date otpSubmittedTime;
    @JsonProperty("isOTPSubmitted")
    private Boolean isOTPSubmitted;
    private String ekyc;
    private Date linkCreatedTime;

    public Date getEventCreatedTime() {
        return eventCreatedTime;
    }

    public void setEventCreatedTime(Date eventCreatedTime) {
        this.eventCreatedTime = eventCreatedTime;
    }

    public Date getEventCompletedTime() {
        return eventCompletedTime;
    }

    public void setEventCompletedTime(Date eventCompletedTime) {
        this.eventCompletedTime = eventCompletedTime;
    }

    public Date getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(Date otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public Date getOtpSubmittedTime() {
        return otpSubmittedTime;
    }

    public void setOtpSubmittedTime(Date otpSubmittedTime) {
        this.otpSubmittedTime = otpSubmittedTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Boolean getEventDone() {
        return isEventDone;
    }

    public void setEventDone(Boolean eventDone) {
        isEventDone = eventDone;
    }

    public String getBankCorrelationId() {
        return bankCorrelationId;
    }

    public void setBankCorrelationId(String bankCorrelationId) {
        this.bankCorrelationId = bankCorrelationId;
    }

    public Boolean getOTPSubmitted() {
        return isOTPSubmitted;
    }

    public void setOTPSubmitted(Boolean OTPSubmitted) {
        isOTPSubmitted = OTPSubmitted;
    }

    public Date getLinkCreatedTime() {
        return linkCreatedTime;
    }

    public void setLinkCreatedTime(Date linkCreatedTime) {
        this.linkCreatedTime = linkCreatedTime;
    }

    public String getEkyc() {
        return ekyc;
    }

    public void setEkyc(String ekyc) {
        this.ekyc = ekyc;
    }

    @Override
    public String toString() {
        return "OnboardingEvent{" +
                "customerId='" + customerId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventCreatedTime=" + eventCreatedTime +
                ", eventCompletedTime=" + eventCompletedTime +
                ", isEventDone=" + isEventDone +
                ", bankCorrelationId='" + bankCorrelationId + '\'' +
                ", otpGeneratedTime=" + otpGeneratedTime +
                ", otpSubmittedTime=" + otpSubmittedTime +
                ", isOTPSubmitted=" + isOTPSubmitted +
                ", ekyc='" + ekyc + '\'' +
                ", linkCreatedTime=" + linkCreatedTime +
                '}';
    }
}
