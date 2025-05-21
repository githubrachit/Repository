package com.mli.mpro.location.ifsc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;


public class Transaction {
    @Sensitive(MaskType.BANK_IFSC)
	private String ifscCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ifscStatus;
	@Sensitive(MaskType.BANK_MICR)
	private String microCode;
	@Sensitive(MaskType.BANK_BRANCH_NAME)
	private String micrBankName;
	@Sensitive(MaskType.BANK_BRANCH_NAME)
	private String micrBranchName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String micrStatus;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String matchStatus;

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getIfscStatus() {
		return ifscStatus;
	}

	public void setIfscStatus(String ifscStatus) {
		this.ifscStatus = ifscStatus;
	}

	public String getMicroCode() {
		return microCode;
	}

	public void setMicroCode(String microCode) {
		this.microCode = microCode;
	}

	public String getMicrBankName() {
		return micrBankName;
	}

	public void setMicrBankName(String micrBankName) {
		this.micrBankName = micrBankName;
	}

	public String getMicrBranchName() {
		return micrBranchName;
	}

	public void setMicrBranchName(String micrBranchName) {
		this.micrBranchName = micrBranchName;
	}

	public String getMicrStatus() {
		return micrStatus;
	}

	public void setMicrStatus(String micrStatus) {
		this.micrStatus = micrStatus;
	}

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Transactions [ifscCode=" + ifscCode + ", ifscStatus=" + ifscStatus + ", microCode=" + microCode
				+ ", micrBankName=" + micrBankName + ", micrBranchName=" + micrBranchName + ", micrStatus=" + micrStatus
				+ ", matchStatus=" + matchStatus + "]";
	}
}
