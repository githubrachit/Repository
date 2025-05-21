package com.mli.mpro.email.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RequestPayload {

	private String documentType;

	private String templateId;

	@Sensitive(MaskType.EMAIL)
	private String mailId;

	private String referenceNumber;

	@Sensitive(MaskType.NAME)
	private String customerName;

	private String planName;

	private String paymentLink;

	@Sensitive(MaskType.AMOUNT)
	private String amount;

	private String applicationNumber;

	private String emailSubject;

	@Sensitive(MaskType.POLICY_NUM)
	private String policyNumber;

	private String agentId;

	private long transactionId;
	
	private String medicalGridStatus;
	
	private String financialGridStatus;

	@Sensitive(MaskType.NAME)
	private String agentName;
	private String proposalRejectReason;

	public String getProposalRejectReason() { return proposalRejectReason; }

	public void setProposalRejectReason(String proposalRejectReason) { this.proposalRejectReason = proposalRejectReason; }

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPaymentLink() {
		return paymentLink;
	}

	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getMedicalGridStatus() {
	    return medicalGridStatus;
	}

	public void setMedicalGridStatus(String medicalGridStatus) {
	    this.medicalGridStatus = medicalGridStatus;
	}

	public String getFinancialGridStatus() {
	    return financialGridStatus;
	}

	public void setFinancialGridStatus(String financialGridStatus) {
	    this.financialGridStatus = financialGridStatus;
	}

    public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RequestPayload [documentType=" + documentType + ", templateId=" + templateId + ", mailId=" + mailId
				+ ", referenceNumber=" + referenceNumber + ", customerName=" + customerName + ", planName=" + planName
				+ ", paymentLink=" + paymentLink + ", amount=" + amount + ", applicationNumber=" + applicationNumber
				+ ", emailSubject=" + emailSubject + ", policyNumber=" + policyNumber + ", agentId=" + agentId
				+ ", transactionId=" + transactionId + ", medicalGridStatus=" + medicalGridStatus
				+ ", financialGridStatus=" + financialGridStatus + ", agentName=" + agentName + "]";
	}

}
