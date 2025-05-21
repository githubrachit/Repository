package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AxisDocumentRequest {

    @JsonProperty("caseId")
    private String caseId;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("OGSStream")
    private String OGSStream;
    @JsonProperty("bankStatementStream")
    private String bankStatementStream;

    public AxisDocumentRequest() {
    }

    public AxisDocumentRequest(String caseId, String transactionId, String oGSStream, String bankStatementStream) {
	super();
	this.caseId = caseId;
	this.transactionId = transactionId;
	OGSStream = oGSStream;
	this.bankStatementStream = bankStatementStream;
    }

    public String getCaseId() {
	return caseId;
    }

    public void setCaseId(String caseId) {
	this.caseId = caseId;
    }

    public String getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
    }

    public String getOGSStream() {
	return OGSStream;
    }

    public void setOGSStream(String oGSStream) {
	OGSStream = oGSStream;
    }

    public String getBankStatementStream() {
	return bankStatementStream;
    }

    public void setBankStatementStream(String bankStatementStream) {
	this.bankStatementStream = bankStatementStream;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "AxisDocumentRequest [caseId=" + caseId + ", transactionId=" + transactionId + ", OGSStream=" + OGSStream + ", bankStatementStream="
		+ bankStatementStream + "]";
    }

}
