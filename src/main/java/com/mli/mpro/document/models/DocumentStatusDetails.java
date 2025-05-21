package com.mli.mpro.document.models;

import java.util.Date;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documentStatus")
public class DocumentStatusDetails {
    
    private Date documentGeneratedDate;
    private long transactionId;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNumber;
    private String agentId;
    private String documentStatus;
    private int retryCount;
    private String documentName;
    private String applicationStage;
    private String channel;

    public DocumentStatusDetails() {
	super();
    }

    public DocumentStatusDetails(long transactionId, String policyNumber, String agentId, String documentStatus, int retryCount, String documentName,
	    String applicationStage) {
	super();
	this.transactionId = transactionId;
	this.policyNumber = policyNumber;
	this.agentId = agentId;
	this.documentStatus = documentStatus;
	this.retryCount = retryCount;
	this.documentName = documentName;
	this.applicationStage = applicationStage;
    }

    

    public DocumentStatusDetails(long transactionId, String policyNumber, String agentId,
			String documentStatus, int retryCount, String documentName, String applicationStage, String channel) {
		super();
		this.transactionId = transactionId;
		this.policyNumber = policyNumber;
		this.agentId = agentId;
		this.documentStatus = documentStatus;
		this.retryCount = retryCount;
		this.documentName = documentName;
		this.applicationStage = applicationStage;
		this.channel = channel;
	}

	public long getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
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

    public String getDocumentStatus() {
	return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
	this.documentStatus = documentStatus;
    }

    public int getRetryCount() {
	return retryCount;
    }

    public void setRetryCount(int retryCount) {
	this.retryCount = retryCount;
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    public String getApplicationStage() {
	return applicationStage;
    }

    public void setApplicationStage(String applicationStage) {
	this.applicationStage = applicationStage;
    }

    public Date getDocumentGeneratedDate() {
        return documentGeneratedDate;
    }

    public void setDocumentGeneratedDate(Date documentGeneratedDate) {
        this.documentGeneratedDate = documentGeneratedDate;
    }



    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


	@Override
	public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
		return "DocumentStatusDetails [documentGeneratedDate=" + documentGeneratedDate + ", transactionId="
				+ transactionId + ", policyNumber=" + policyNumber + ", agentId=" + agentId + ", documentStatus="
				+ documentStatus + ", retryCount=" + retryCount + ", documentName=" + documentName
				+ ", applicationStage=" + applicationStage + ", channel=" + channel + "]";
	}

}
