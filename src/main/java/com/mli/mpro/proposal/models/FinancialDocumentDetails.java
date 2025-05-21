package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class FinancialDocumentDetails {

    private String documentName;

    private String documentStatus;

    public FinancialDocumentDetails() {
	super();
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    public String getDocumentStatus() {
	return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
	this.documentStatus = documentStatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "FinancialDocumentDetails [documentName=" + documentName + ", documentStatus=" + documentStatus + "]";
    }

}
