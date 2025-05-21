package com.mli.mpro.productRestriction.models.planCodeModels;

import com.mli.mpro.docsApp.models.AddProposal;
import com.mli.mpro.utils.Utility;

public class Payload {
	private String equoteNumber;

	private String statusCodeDisplayText;

	private AddProposal addProposal;

	private String statusCodekey;
	private String planCode;
	private String planCodePOSV;
	private String planCodeMFSA;
	private String  planCodeTPP;

	public String getPlanCodePOSV() {
		return planCodePOSV;
	}

	public void setPlanCodePOSV(String planCodePOSV) {
		this.planCodePOSV = planCodePOSV;
	}

	public String getPlanCodeMFSA() {
		return planCodeMFSA;
	}

	public void setPlanCodeMFSA(String planCodeMFSA) {
		this.planCodeMFSA = planCodeMFSA;
	}

	public String getPlanCodeTPP() {
		return planCodeTPP;
	}

	public void setPlanCodeTPP(String planCodeTPP) {
		this.planCodeTPP = planCodeTPP;
	}

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

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
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
