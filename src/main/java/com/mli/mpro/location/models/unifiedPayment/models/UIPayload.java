package com.mli.mpro.location.models.unifiedPayment.models;

import com.mli.mpro.utils.Utility;

import javax.validation.constraints.*;


public class UIPayload {
    @Min(value = 100000000, message = "Transaction ID must have at least 10 digits")
    @Max(value = 9999999999L, message = "Transaction ID must have at least 10 digits")
    private long transactionId;
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "Invalid AgentId must be exactly 6 alphanumeric characters")
    private String agentId;
    @Size(max = 255, message = "Redirect URL cannot exceed 255 characters")
    @Pattern(
            regexp = "^(https?|ftp):\\/\\/([a-zA-Z0-9.-]+)(\\.[a-zA-Z]{2,})+(\\/[\\w\\-._~:/?#[\\\\]@!$&'()*+,;=]*)?$",
            message = "Invalid URL format"
    )
    private String redirectUrl;
    @Pattern(regexp = "^(initial|renewal)$", message = "Invalid payment type.")
    private String paymentType;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "UIPaymentRequestResponse{" +
                "transactionId=" + transactionId +
                ", agentId='" + agentId + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}
