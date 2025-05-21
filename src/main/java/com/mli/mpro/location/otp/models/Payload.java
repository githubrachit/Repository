package com.mli.mpro.location.otp.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;


public class Payload {
    private Long transactionId;
    @Sensitive(MaskType.EMAIL)
    private String emailId;

    private String policyNumber;

    private String otpKey;

    private String otpValue;

    private String flowType;//This is used to determine for which flow emial/sms needs to be sent with OTP.
    private String otpDeliveryMethod;//This is used to determine which service to call for sending OTP. If EMAIL comes as a value then by email, if SMS then by sms and if EMAIL_SMS then both.

    private String msg;
    private DisabilityDeclaration disabilityDeclaration;
    public static class Builder {
        private String otpKey;
        private String msg;

        public Builder otpKey(String otpKey) {
            this.otpKey = otpKey;
            return this;
        }
        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Payload build() {
            Payload payload = new Payload();
            payload.otpKey = this.otpKey;
            payload.msg = this.msg;
            return payload;
        }
    }
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    public String getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(String otpValue) {
        this.otpValue = otpValue;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getOtpDeliveryMethod() {
        return otpDeliveryMethod;
    }

    public void setOtpDeliveryMethod(String otpDeliveryMethod) {
        this.otpDeliveryMethod = otpDeliveryMethod;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DisabilityDeclaration getDisabilityDeclaration() {
        return disabilityDeclaration;
    }

    public void setDisabilityDeclaration(DisabilityDeclaration disabilityDeclaration) {
        this.disabilityDeclaration = disabilityDeclaration;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "transactionId=" + transactionId +
                ", emailId='" + emailId + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", otpKey='" + otpKey + '\'' +
                ", otpValue='" + otpValue + '\'' +
                ", flowType='" + flowType + '\'' +
                ", otpDeliveryMethod='" + otpDeliveryMethod + '\'' +
                ", msg='" + msg + '\'' +
                ", disabilityDeclaration='" + disabilityDeclaration + '\'' +
                '}';
    }
}
