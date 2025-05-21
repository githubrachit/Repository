package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AxisDirectDebitPOSVData {

	@Sensitive(MaskType.BANK_ACC_NUM)
	@JsonProperty("accountNumber")
    private String accountNumber;
	@Sensitive(MaskType.NAME)
	@JsonProperty("accountHolderName")
    private String accountHolderName;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("debitAmount")
    private String debitAmount;
	@JsonProperty("paymentMethod")
    private String paymentMethod;
	@JsonProperty("startDate")
    private String startDate;
	@JsonProperty("endDate")
    private String endDate;
	@JsonProperty("isSIOpted")
    private String isSIOpted;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("premiumAmount")
	private String premiumAmount;
	
	public AxisDirectDebitPOSVData() {
		super();
	}

	public AxisDirectDebitPOSVData(String accountNumber, String accountHolderName, String debitAmount,
			String paymentMethod, String startDate, String endDate, String isSIOpted) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.debitAmount = debitAmount;
		this.paymentMethod = paymentMethod;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isSIOpted = isSIOpted;
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

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsSIOpted() {
		return isSIOpted;
	}

	public void setIsSIOpted(String isSIOpted) {
		this.isSIOpted = isSIOpted;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "AxisDirectDebitPOSVData [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName
				+ ", debitAmount=" + debitAmount + ", paymentMethod=" + paymentMethod + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", isSIOpted=" + isSIOpted + ",premiumAmount = "+premiumAmount+"]";
	}
	
}
