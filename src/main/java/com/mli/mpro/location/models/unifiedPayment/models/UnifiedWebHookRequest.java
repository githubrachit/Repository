package com.mli.mpro.location.models.unifiedPayment.models;

import com.mli.mpro.utils.Utility;

public class UnifiedWebHookRequest {
    private String policyNo;
    private String paymentDate;
    private String policyAmount;
    private String amountPaid;
    private String transNo;
    private String merchTxn;
    private String paymentMode;
    private String paymentChannelType;
    private String bankName;
    private String bpRefNo;
    private String branchName;
    private String instrNo;
    private String instrType;
    private String location;
    private String internetMerchantNo;
    private String mandateStartDate;
    private String mandateEndDate;
    private String subscriptionRefId;
    private String mandateId;
    private String maskedCardValue;
    private String orderId;
    private String bankRefNo;
    private String responseCodeDesc;
    private String reasonForPayment;
    private String customerId;
    private String accountNo;
    private String language;
    private String paymentType;
    private String txnStatusDesc;
    private String merchantUnqRef;
    private String additionalInfo;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPolicyAmount() {
        return policyAmount;
    }

    public void setPolicyAmount(String policyAmount) {
        this.policyAmount = policyAmount;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getMerchTxn() {
        return merchTxn;
    }

    public void setMerchTxn(String merchTxn) {
        this.merchTxn = merchTxn;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentChannelType() {
        return paymentChannelType;
    }

    public void setPaymentChannelType(String paymentChannelType) {
        this.paymentChannelType = paymentChannelType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBpRefNo() {
        return bpRefNo;
    }

    public void setBpRefNo(String bpRefNo) {
        this.bpRefNo = bpRefNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public String getInstrType() {
        return instrType;
    }

    public void setInstrType(String instrType) {
        this.instrType = instrType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInternetMerchantNo() {
        return internetMerchantNo;
    }

    public void setInternetMerchantNo(String internetMerchantNo) {
        this.internetMerchantNo = internetMerchantNo;
    }

    public String getMandateStartDate() {
        return mandateStartDate;
    }

    public void setMandateStartDate(String mandateStartDate) {
        this.mandateStartDate = mandateStartDate;
    }

    public String getMandateEndDate() {
        return mandateEndDate;
    }

    public void setMandateEndDate(String mandateEndDate) {
        this.mandateEndDate = mandateEndDate;
    }

    public String getSubscriptionRefId() {
        return subscriptionRefId;
    }

    public void setSubscriptionRefId(String subscriptionRefId) {
        this.subscriptionRefId = subscriptionRefId;
    }

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public String getMaskedCardValue() {
        return maskedCardValue;
    }

    public void setMaskedCardValue(String maskedCardValue) {
        this.maskedCardValue = maskedCardValue;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public String getResponseCodeDesc() {
        return responseCodeDesc;
    }

    public void setResponseCodeDesc(String responseCodeDesc) {
        this.responseCodeDesc = responseCodeDesc;
    }

    public String getReasonForPayment() {
        return reasonForPayment;
    }

    public void setReasonForPayment(String reasonForPayment) {
        this.reasonForPayment = reasonForPayment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTxnStatusDesc() { return txnStatusDesc; }

    public void setTxnStatusDesc(String txnStatusDesc) { this.txnStatusDesc = txnStatusDesc; }

    public String getMerchantUnqRef() { return merchantUnqRef; }

    public void setMerchantUnqRef(String merchantUnqRef) { this.merchantUnqRef = merchantUnqRef; }

    public String getAdditionalInfo() { return additionalInfo; }

    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "UnifiedWebHookRequest{" +
                "policyNo='" + policyNo + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", policyAmount='" + policyAmount + '\'' +
                ", amountPaid='" + amountPaid + '\'' +
                ", transNo='" + transNo + '\'' +
                ", merchTxn='" + merchTxn + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", paymentChannelType='" + paymentChannelType + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bpRefNo='" + bpRefNo + '\'' +
                ", branchName='" + branchName + '\'' +
                ", instrNo='" + instrNo + '\'' +
                ", instrType='" + instrType + '\'' +
                ", location='" + location + '\'' +
                ", internetMerchantNo='" + internetMerchantNo + '\'' +
                ", mandateStartDate='" + mandateStartDate + '\'' +
                ", mandateEndDate='" + mandateEndDate + '\'' +
                ", subscriptionRefId='" + subscriptionRefId + '\'' +
                ", mandateId='" + mandateId + '\'' +
                ", maskedCardValue='" + maskedCardValue + '\'' +
                ", orderId='" + orderId + '\'' +
                ", bankRefNo='" + bankRefNo + '\'' +
                ", responseCodeDesc='" + responseCodeDesc + '\'' +
                ", reasonForPayment='" + reasonForPayment + '\'' +
                ", customerId='" + customerId + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", language='" + language + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", txnStatusDesc='" + txnStatusDesc + '\'' +
                ", merchantUnqRef='" + merchantUnqRef + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
