package com.mli.mpro.docsApp.models;

import com.mli.mpro.utils.Utility;

public class Payload {
	private String equoteNumber;

	private String statusCodeDisplayText;

	private AddProposal addProposal;

	private String statusCodekey;

	public String getEquoteNumber() {
		return equoteNumber;
	}

	public void setEquoteNumber(String equoteNumber) {
		this.equoteNumber = equoteNumber;
	}

	public String getStatusCodeDisplayText() {
		return statusCodeDisplayText;
	}

	public void setStatusCodeDisplayText(String statusCodeDisplayText) {
		this.statusCodeDisplayText = statusCodeDisplayText;
	}

	public AddProposal getAddProposal() {
		return addProposal;
	}

	public void setAddProposal(AddProposal addProposal) {
		this.addProposal = addProposal;
	}

	public String getStatusCodekey() {
		return statusCodekey;
	}

	public void setStatusCodekey(String statusCodekey) {
		this.statusCodekey = statusCodekey;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ClassPojo [equoteNumber = " + equoteNumber + ", statusCodeDisplayText = " + statusCodeDisplayText
				+ ", addProposal = " + addProposal + ", statusCodekey = " + statusCodekey + "]";
	}
}
