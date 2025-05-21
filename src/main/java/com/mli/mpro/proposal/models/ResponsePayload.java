package com.mli.mpro.proposal.models;

import java.util.List;

import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponsePayload {

    Logger logger = LoggerFactory.getLogger(ResponsePayload.class);

    private List<Object> message;

    private String policyStatus;

    private ProposalDetails proposalDetails;
    
    private List<PolicyDocuments> documents;

    public ResponsePayload() {

    }

    public ResponsePayload(Logger logger, List<Object> message) {
	super();
	this.logger = logger;
	this.message = message;
    }

    public ResponsePayload(ProposalDetails proposalDetails) {
	this.proposalDetails = proposalDetails;
    }

    public List<Object> getMessage() {
	return message;
    }

    public void setMessage(List<Object> message) {
	this.message = message;
    }

    public ProposalDetails getProposalDetails() {
	return proposalDetails;
    }

    public void setProposalDetails(ProposalDetails proposalDetails) {
	this.proposalDetails = proposalDetails;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ResponsePayload [message=" + message + ", proposalDetails=" + proposalDetails + "]";
    }

}