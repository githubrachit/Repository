package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class YBLBankDetails {

	private String requestReferenceNumber;
	private String requestReferenceTimeStamp;
	private String responseTimeStamp;
	private String responseStatus;
	@Sensitive(MaskType.BANK_ACC_NUM)
	private String accountNumber;
	@Sensitive(MaskType.BANK_ACC_HOLDER_NAME)
	private String accountHolderName;
	private String accountOpeningDate;
	private String bankProductType;
	@Sensitive(MaskType.BANK_IFSC)
	private String ifsc;
	@Sensitive(MaskType.BANK_MICR)
	private String micr;
	@Sensitive(MaskType.ADDRESS)
	private String bankBranchAddress;
	private String accountStatus;
	private String operationMode;
	@Sensitive(MaskType.BANK_NAME)
	private String bankName;

	public String getRequestReferenceNumber() {
		return requestReferenceNumber;
	}

	public void setRequestReferenceNumber(String requestReferenceNumber) {
		this.requestReferenceNumber = requestReferenceNumber;
	}

	public String getRequestReferenceTimeStamp() {
		return requestReferenceTimeStamp;
	}

	public void setRequestReferenceTimeStamp(String requestReferenceTimeStamp) {
		this.requestReferenceTimeStamp = requestReferenceTimeStamp;
	}

	public String getResponseTimeStamp() {
		return responseTimeStamp;
	}

	public void setResponseTimeStamp(String responseTimeStamp) {
		this.responseTimeStamp = responseTimeStamp;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountOpeningDate() {
		return accountOpeningDate;
	}

	public void setAccountOpeningDate(String accountOpeningDate) {
		this.accountOpeningDate = accountOpeningDate;
	}

	public String getBankProductType() {
		return bankProductType;
	}

	public void setBankProductType(String bankProductType) {
		this.bankProductType = bankProductType;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getMicr() {
		return micr;
	}

	public void setMicr(String micr) {
		this.micr = micr;
	}

	public String getBankBranchAddress() {
		return bankBranchAddress;
	}

	public void setBankBranchAddress(String bankBranchAddress) {
		this.bankBranchAddress = bankBranchAddress;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "YBLBankDetails{" +
				"requestReferenceNumber='" + requestReferenceNumber + '\'' +
				", requestReferenceTimeStamp='" + requestReferenceTimeStamp + '\'' +
				", responseTimeStamp='" + responseTimeStamp + '\'' +
				", responseStatus='" + responseStatus + '\'' +
				", accountNumber='" + accountNumber + '\'' +
				", accountHolderName='" + accountHolderName + '\'' +
				", accountOpeningDate='" + accountOpeningDate + '\'' +
				", bankProductType='" + bankProductType + '\'' +
				", ifsc='" + ifsc + '\'' +
				", micr='" + micr + '\'' +
				", bankBranchAddress='" + bankBranchAddress + '\'' +
				", accountStatus='" + accountStatus + '\'' +
				", operationMode='" + operationMode + '\'' +
				", bankName='" + bankName + '\'' +
				'}';
	}
}