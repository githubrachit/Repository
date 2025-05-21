package com.mli.mpro.axis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class InputRequest {

    @JsonProperty("axisDocumentRequest")
    private AxisDocumentRequest axisDocumentRequest;

    private List<Transactions> transactions;

    public InputRequest() {
    }

    public InputRequest(AxisDocumentRequest axisDocumentRequest) {
	super();
	this.axisDocumentRequest = axisDocumentRequest;
    }

    public AxisDocumentRequest getAxisDocumentRequest() {
	return axisDocumentRequest;
    }

    public void setAxisDocumentRequest(AxisDocumentRequest axisDocumentRequest) {
	this.axisDocumentRequest = axisDocumentRequest;
    }

    public List<Transactions> getTransactions() {
	return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
	this.transactions = transactions;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "InputRequest [axisDocumentRequest=" + axisDocumentRequest + ", transactions=" + transactions + "]";
    }

    

}
