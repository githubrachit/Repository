package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class DocumentInfo {
	private String documentName;
	private String actualDocument;
	private String documentType;
	public DocumentInfo() {
		super();
	}
	public DocumentInfo(String documentName, String actualDocument, String documentType) {
		super();
		this.documentName = documentName;
		this.actualDocument = actualDocument;
		this.documentType = documentType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getActualDocument() {
		return actualDocument;
	}
	public void setActualDocument(String actualDocument) {
		this.actualDocument = actualDocument;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "DocumentInfo [documentName=" + documentName + ", actualDocument=" + actualDocument + ", documentType="
				+ documentType + "]";
	}	
}
