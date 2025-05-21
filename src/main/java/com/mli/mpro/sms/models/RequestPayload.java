package com.mli.mpro.sms.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestPayload {

	private String messageTo;

	@Sensitive(MaskType.POLICY_NUM)
	private String policyNumber;

	private String link;

	@Sensitive(MaskType.NAME)
	private String customerName;

	private long transactionNumber;

	private String messageText;

	@Sensitive(MaskType.AMOUNT)
	private String amount;

	private String type;

	@Sensitive(MaskType.BANK_ACC_NUM)
	private String accountNumber;

	private String docLink;

	private String productName;

	private String enachLink;

	@Sensitive(MaskType.NAME)
	private String sellerName;
	@Sensitive(MaskType.NAME)
	private String agentName;
	private String agentId;

	public String getAgentId() { return agentId; }

	public void setAgentId(String agentId) { this.agentId = agentId; }

	public String getAgentName() { return agentName; }

	public void setAgentName(String agentName) { this.agentName = agentName; }


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDocLink() {
		return docLink;
	}

	public void setDocLink(String docLink) {
		this.docLink = docLink;
	}

	public String getEnachLink() {
		return enachLink;
	}

	public void setEnachLink(String enachLink) {
		this.enachLink = enachLink;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	
	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RequestPayload [messageTo=" + messageTo + ", policyNumber=" + policyNumber + ", link=" + link
				+ ", customerName=" + customerName + ", transactionNumber=" + transactionNumber + ", messageText="
				+ messageText + ", amount=" + amount + ", type=" + type + ", accountNumber=" + accountNumber
				+ ", docLink=" + docLink + ", productName=" + productName + ", enachLink=" + enachLink + "]";
	}



}
