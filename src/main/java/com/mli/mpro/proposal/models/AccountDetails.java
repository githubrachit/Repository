package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonPropertyOrder({ "accountNumber", "productName", "openDate" })
public class AccountDetails {

    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("openDate")
    private String openDate;

    public AccountDetails() {
	super();

    }

    public AccountDetails(String accountNumber, String productName, String openDate) {
	super();
	this.accountNumber = accountNumber;
	this.productName = productName;
	this.openDate = openDate;
    }

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public String getProductName() {
	return productName;
    }

    public void setProductName(String productName) {
	this.productName = productName;
    }

    public String getOpenDate() {
	return openDate;
    }

    public void setOpenDate(String openDate) {
	this.openDate = openDate;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AccountDetails [accountNumber=" + accountNumber + ", productName=" + productName + ", openDate=" + openDate + "]";
    }

}
