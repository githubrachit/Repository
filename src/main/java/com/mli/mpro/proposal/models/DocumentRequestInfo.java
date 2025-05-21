package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class DocumentRequestInfo {

    private String documentType;

    private String actualDocument;

    private String documentName;

    public DocumentRequestInfo() {
	super();
    }

    public DocumentRequestInfo(String documentType, String actualDocument, String documentName) {
	super();
	this.documentType = documentType;
	this.actualDocument = actualDocument;
	this.documentName = documentName;
    }

    public String getDocumentType() {
	return documentType;
    }

    public void setDocumentType(String documentType) {
	this.documentType = documentType;
    }

    public String getActualDocument() {
	return actualDocument;
    }

    public void setActualDocument(String actualDocument) {
	this.actualDocument = actualDocument;
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DocumentInfo [documentType=" + documentType + ", actualDocument=" + actualDocument + ", documentName=" + documentName + "]";
    }

}
