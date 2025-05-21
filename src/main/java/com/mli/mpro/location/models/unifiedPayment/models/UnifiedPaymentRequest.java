package com.mli.mpro.location.models.unifiedPayment.models;


import com.mli.mpro.utils.Utility;

public class UnifiedPaymentRequest {

    private String txnAmt;
    private String customerId;
    private String policyNo;
    private String journey;
    private String journeyEnv;
    private String paymentReason;
    private String premiumDueAmount;
    private String siMaxAmountIncreasePercentage;
    private String siEndDate;
    private String profile;
    private String additionalInfo;
    private String callBackUrl;

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getJourney() {
        return journey;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }

    public String getJourneyEnv() {
        return journeyEnv;
    }

    public void setJourneyEnv(String journeyEnv) {
        this.journeyEnv = journeyEnv;
    }

    public String getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(String paymentReason) {
        this.paymentReason = paymentReason;
    }

    public String getPremiumDueAmount() {
        return premiumDueAmount;
    }

    public void setPremiumDueAmount(String premiumDueAmount) {
        this.premiumDueAmount = premiumDueAmount;
    }

    public String getSiMaxAmountIncreasePercentage() {
        return siMaxAmountIncreasePercentage;
    }

    public void setSiMaxAmountIncreasePercentage(String siMaxAmountIncreasePercentage) {
        this.siMaxAmountIncreasePercentage = siMaxAmountIncreasePercentage;
    }

    public String getSiEndDate() {
        return siEndDate;
    }

    public void setSiEndDate(String siEndDate) {
        this.siEndDate = siEndDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAdditionalInfo() { return additionalInfo; }

    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

    public String getCallBackUrl() { return callBackUrl; }

    public void setCallBackUrl(String callBackUrl) { this.callBackUrl = callBackUrl; }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "UnifiedPaymentRequest{" +
                "txnAmt='" + txnAmt + '\'' +
                ", customerId='" + customerId + '\'' +
                ", policyNo='" + policyNo + '\'' +
                ", journey='" + journey + '\'' +
                ", journeyEnv='" + journeyEnv + '\'' +
                ", paymentReason='" + paymentReason + '\'' +
                ", premiumDueAmount='" + premiumDueAmount + '\'' +
                ", siMaxAmountIncreasePercentage='" + siMaxAmountIncreasePercentage + '\'' +
                ", siEndDate='" + siEndDate + '\'' +
                ", profile='" + profile + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", callBackUrl='" + callBackUrl + '\'' +
                '}';
    }
}

