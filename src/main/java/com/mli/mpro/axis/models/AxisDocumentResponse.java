package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AxisDocumentResponse {

    @JsonProperty("bankStatementstatus")
    private String bankStatementstatus;
    @JsonProperty("OGStatementstatus")
    private String OGStatementstatus;

    public AxisDocumentResponse() {
    }

    public AxisDocumentResponse(String bankStatementstatus, String OGStatementstatus) {
	super();
	this.bankStatementstatus = bankStatementstatus;
	this.OGStatementstatus = OGStatementstatus;
    }

    
    public String getBankStatementstatus() {
	return bankStatementstatus;
    }

    public void setBankStatementstatus(String bankStatementstatus) {
	this.bankStatementstatus = bankStatementstatus;
    }

    @JsonProperty("OGStatementstatus")
    public String getOGStatementstatus() {
	return OGStatementstatus;
    }

    @JsonProperty("OGStatementstatus")
    public void setOGStatementstatus(String oGStatementstatus) {
	OGStatementstatus = oGStatementstatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "AxisDocumentResponse [bankStatementstatus=" + bankStatementstatus + ", OGStatementstatus=" + OGStatementstatus + "]";
    }

}
