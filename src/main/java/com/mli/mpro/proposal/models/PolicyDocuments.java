package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class PolicyDocuments {

	private String document;
	private String documentName;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PolicyDocuments [document=" + document + ", documentName=" + documentName + "]";
	}

}
