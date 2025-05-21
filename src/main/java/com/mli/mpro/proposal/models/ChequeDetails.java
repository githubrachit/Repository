package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class ChequeDetails {

    @JsonProperty("chequeResponse")
    private boolean chequeResponse;

    public ChequeDetails() {
    }

    public ChequeDetails(boolean chequeResponse) {
	super();
	this.chequeResponse = chequeResponse;
    }

    public boolean isChequeResponse() {
	return chequeResponse;
    }

    public void setChequeResponse(boolean chequeResponse) {
	this.chequeResponse = chequeResponse;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ChequeDetails [chequeResponse=" + chequeResponse + "]";
    }

}
