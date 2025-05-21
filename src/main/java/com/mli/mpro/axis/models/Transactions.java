package com.mli.mpro.axis.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Transactions {

    private String transactionId;
    private String voucherNumber;
    
    @JsonProperty("fromDate")
    private Date fromDate;
    @JsonProperty("toDate")
    private Date toDate;
    @JsonProperty("channel")
    private String channel;
    
    
    
    private String crmId;
  
    private String policyNo;
    
    private String status;
  
    public Transactions() {
    }

    public Transactions(String transactionId, String voucherNumber) {
	super();
	this.transactionId = transactionId;
	this.voucherNumber = voucherNumber;
    }

    public String getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
    }

    public String getVoucherNumber() {
	return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
	this.voucherNumber = voucherNumber;
    }
    

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Transactions [transactionId=" + transactionId + ", voucherNumber=" + voucherNumber + ", fromDate=" + fromDate + ", toDate=" + toDate
		+ ", channel=" + channel + ", crmId=" + crmId + ", policyNo=" + policyNo + ", status=" + status + "]";
    }
    

    

}
