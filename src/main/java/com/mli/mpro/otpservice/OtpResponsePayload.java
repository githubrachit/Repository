package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

public class OtpResponsePayload {

    private String unqTokenNo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String otpCode;
    private String msgCode;
    private String message;
    private String msgDescription;
    private long transactionId;
    private String token;
    @Sensitive(MaskType.EMAIL)
    private String email;

    @Sensitive(MaskType.MOBILE)
    private String phoneNumber;

    public String getUnqTokenNo() {
        return unqTokenNo;
    }

    public void setUnqTokenNo(String unqTokenNo) {
        this.unqTokenNo = unqTokenNo;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgDescription() {
        return msgDescription;
    }

    public void setMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "OtpResponsePayload{" +
                "unqTokenNo='" + unqTokenNo + '\'' +
                ", otpCode='" + otpCode + '\'' +
                ", msgCode='" + msgCode + '\'' +
                ", message='" + message + '\'' +
                ", msgDescription='" + msgDescription + '\'' +
                ", transactionId=" + transactionId +
                ", token='" + token + '\'' +
                ", email=" + email +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
