package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "accountType", "accountDetails" })
public class Account {

    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("accountDetails")
    private List<AccountDetails> accountDetails = null;

    public Account() {
	super();
    }

    public Account(String accountType, List<AccountDetails> accountDetails) {
	super();
	this.accountType = accountType;
	this.accountDetails = accountDetails;
    }

    @JsonProperty("accountType")
    public String getAccountType() {
	return accountType;
    }

    @JsonProperty("accountType")
    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }
    
    @JsonProperty("accountDetails")
    public List<AccountDetails> getAccountDetails() {
	return accountDetails;
    }
    
    @JsonProperty("accountDetails")
    public void setAccountDetails(List<AccountDetails> accountDetails) {
	this.accountDetails = accountDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Account{" +
                "accountType='" + accountType + '\'' +
                ", accountDetails=" + accountDetails +
                '}';
    }
}