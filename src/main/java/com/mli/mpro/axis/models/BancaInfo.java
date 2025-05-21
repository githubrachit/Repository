package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customerId", "product", "premium", "customerName", "transactionId", "CRMId", "accountNumber", "status", "message", "policyNo" })
public class BancaInfo {

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("product")
    private String product;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("premium")
    private String premium;
    @Sensitive(MaskType.NAME)
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("CRMId")
    private String cRMId;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNo")
    private String policyNo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BancaInfo() {
    }

    /**
     * 
     * @param message
     * @param customerName
     * @param product
     * @param cRMId
     * @param accountNumber
     * @param transactionId
     * @param customerId
     * @param status
     * @param premium
     * @param policyNo
     */
    public BancaInfo(String customerId, String product, String premium, String customerName, String transactionId, String cRMId, String accountNumber,
	    String status, String message, String policyNo) {
	super();
	this.customerId = customerId;
	this.product = product;
	this.premium = premium;
	this.customerName = customerName;
	this.transactionId = transactionId;
	this.cRMId = cRMId;
	this.accountNumber = accountNumber;
	this.status = status;
	this.message = message;
	this.policyNo = policyNo;
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
	return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    @JsonProperty("product")
    public String getProduct() {
	return product;
    }

    @JsonProperty("product")
    public void setProduct(String product) {
	this.product = product;
    }

    @JsonProperty("premium")
    public String getPremium() {
	return premium;
    }

    @JsonProperty("premium")
    public void setPremium(String premium) {
	this.premium = premium;
    }

    @JsonProperty("customerName")
    public String getCustomerName() {
	return customerName;
    }

    @JsonProperty("customerName")
    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    @JsonProperty("transactionId")
    public String getTransactionId() {
	return transactionId;
    }

    @JsonProperty("transactionId")
    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
    }

    @JsonProperty("CRMId")
    public String getCRMId() {
	return cRMId;
    }

    @JsonProperty("CRMId")
    public void setCRMId(String cRMId) {
	this.cRMId = cRMId;
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
	return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    @JsonProperty("status")
    public String getStatus() {
	return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
	this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
	return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
	this.message = message;
    }

    @JsonProperty("policyNo")
    public String getPolicyNo() {
	return policyNo;
    }

    @JsonProperty("policyNo")
    public void setPolicyNo(String policyNo) {
	this.policyNo = policyNo;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "Transaction [customerId=" + customerId + ", product=" + product + ", premium=" + premium + ", customerName=" + customerName + ", transactionId="
		+ transactionId + ", cRMId=" + cRMId + ", accountNumber=" + accountNumber + ", status=" + status + ", message=" + message + ", policyNo="
		+ policyNo + "]";
    }

}
