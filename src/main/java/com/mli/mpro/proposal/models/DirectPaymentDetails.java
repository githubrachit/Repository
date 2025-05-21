package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class DirectPaymentDetails {
    @JsonProperty("voucherNumber")
    private String voucherNumber;
    
    @JsonProperty("voucherUpdatedDate")
    private Date voucherUpdatedDate;

    public DirectPaymentDetails() {
	super();
    }

    public DirectPaymentDetails(String voucherNumber) {
	super();
	this.voucherNumber = voucherNumber;
    }

    public String getvoucherNumber() {
	return voucherNumber;
    }

    public void setvoucherNumber(String voucherNumber) {
	this.voucherNumber = voucherNumber;
    }
    
     public Date getVoucherUpdatedDate() {
        return voucherUpdatedDate;
    }

    public void setVoucherUpdatedDate(Date voucherUpdatedDate) {
        this.voucherUpdatedDate = voucherUpdatedDate;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DirectPaymentDetails [voucherNumber=" + voucherNumber + "]";
    }

}
