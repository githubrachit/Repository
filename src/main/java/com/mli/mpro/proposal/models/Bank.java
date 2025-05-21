package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "chequeDetails", "paymentRenewedBy", "sameAsBankDetails", "bankDetails" })
public class Bank {

    @JsonProperty("sameAsBankDetails")
    private String sameAsBankDetails;

    @JsonProperty("chequeDetails")
    private ChequeDetails chequeDetails;

    @JsonProperty("paymentRenewedBy")
    private String paymentRenewedBy;

    @JsonProperty("bankDetails")
    private List<BankDetails> bankDetails;

    @JsonProperty("accountForBankDetails")
    private String accountForBankDetails;

    @JsonProperty("enachDetails")
    private ENACHDetails enachDetails;

    public Bank()
    {}

    public Bank(Bank bank) {
        if(bank!=null)
        {
            this.sameAsBankDetails = bank.sameAsBankDetails;
            this.chequeDetails = bank.chequeDetails;
            this.paymentRenewedBy = bank.paymentRenewedBy;

            List<BankDetails> bankDetailsist = bank.bankDetails;
            bankDetailsist = (bankDetailsist != null && bankDetailsist.size() != 0) ? bankDetailsist.stream().collect(Collectors.toList())
                    : bankDetailsist;
            this.bankDetails = bankDetailsist;
        }

    }

    public Bank(String sameAsBankDetails, ChequeDetails chequeDetails, String paymentRenewedBy, List<BankDetails> bankDetails, String accountForBankDetails, ENACHDetails enachDetails) {
        this.sameAsBankDetails = sameAsBankDetails;
        this.chequeDetails = chequeDetails;
        this.paymentRenewedBy = paymentRenewedBy;
        this.bankDetails = bankDetails;
        this.accountForBankDetails = accountForBankDetails;
        this.enachDetails = enachDetails;
    }

    public String getSameAsBankDetails() {
        return sameAsBankDetails;
    }

    public void setSameAsBankDetails(String sameAsBankDetails) {
        this.sameAsBankDetails = sameAsBankDetails;
    }

    public ChequeDetails getChequeDetails() {
        return chequeDetails;
    }

    public void setChequeDetails(ChequeDetails chequeDetails) {
        this.chequeDetails = chequeDetails;
    }

    public String getPaymentRenewedBy() {
        return paymentRenewedBy;
    }

    public void setPaymentRenewedBy(String paymentRenewedBy) {
        this.paymentRenewedBy = paymentRenewedBy;
    }

    public List<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(List<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getAccountForBankDetails() { return accountForBankDetails; }

    public void setAccountForBankDetails(String accountForBankDetails) { this.accountForBankDetails = accountForBankDetails; }

    public ENACHDetails getEnachDetails() {
        return enachDetails;
    }

    public void setEnachDetails(ENACHDetails enachDetails) {
        this.enachDetails = enachDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Bank{" +
                "sameAsBankDetails='" + sameAsBankDetails + '\'' +
                ", chequeDetails=" + chequeDetails +
                ", paymentRenewedBy='" + paymentRenewedBy + '\'' +
                ", bankDetails=" + bankDetails +
                ", accountForBankDetails='" + accountForBankDetails + '\'' +
                ", enachDetails=" + enachDetails +
                '}';
    }

}
