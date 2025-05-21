package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class PaymentChequeDetails {
    @Sensitive(MaskType.CHEQUE_NUM)
    @JsonProperty("chequeNumber")
    private long chequeNumber;
    @JsonProperty("chequeDate")
    private Date chequeDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("chequeAmount")
    private double chequeAmount;
    @JsonProperty("chequePayableAt")
    private String chequePayableAt;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("chequeBankName")
    private String chequeBankName;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("chequeMicr")
    private long chequeMicr;

    public PaymentChequeDetails() {
    }

    public PaymentChequeDetails(long chequeNumber, Date chequeDate, double chequeAmount, String chequePayableAt, String chequeBankName, long chequeMICR) {
	super();
	this.chequeNumber = chequeNumber;
	this.chequeDate = chequeDate;
	this.chequeAmount = chequeAmount;
	this.chequePayableAt = chequePayableAt;
	this.chequeBankName = chequeBankName;
	this.chequeMicr = chequeMICR;
    }

    public long getChequeNumber() {
	return chequeNumber;
    }

    public void setChequeNumber(long chequeNumber) {
	this.chequeNumber = chequeNumber;
    }

    public Date getChequeDate() {
	return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
	this.chequeDate = chequeDate;
    }

    public double getChequeAmount() {
	return chequeAmount;
    }

    public void setChequeAmount(double chequeAmount) {
	this.chequeAmount = chequeAmount;
    }

    public String getChequePayableAt() {
	return chequePayableAt;
    }

    public void setChequePayableAt(String chequePayableAt) {
	this.chequePayableAt = chequePayableAt;
    }

    public String getChequeBankName() {
	return chequeBankName;
    }

    public void setChequeBankName(String chequeBankName) {
	this.chequeBankName = chequeBankName;
    }

    public long getChequeMicr() {
	return chequeMicr;
    }

    public void setChequeMicr(long chequeMICR) {
	this.chequeMicr = chequeMICR;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PaymentChequeDetails [chequeNumber=" + chequeNumber + ", chequeDate=" + chequeDate + ", chequeAmount=" + chequeAmount + ", chequePayableAt="
		+ chequePayableAt + ", chequeBankName=" + chequeBankName + ", chequeMicr=" + chequeMicr + "]";
    }

}
