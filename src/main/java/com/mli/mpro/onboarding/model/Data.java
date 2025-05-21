package com.mli.mpro.onboarding.model;

import java.util.List;

public class Data {

    public String agentId;
    public String source;
    public String channelName;
    public String subSource;
    //add policy number for saral super-app
    public String policyNumber;

    // add transactionId id
	private long transactionId;
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
    List<CustomerDetails> customerDetailsList;

    public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public List<CustomerDetails> getCustomerDetailsList() {
        return customerDetailsList;
    }

    public void setCustomerDetailsList(List<CustomerDetails> customerDetailsList) {
        this.customerDetailsList = customerDetailsList;
    }

    public String getSubSource() {
        return subSource;
    }

    public void setSubSource(String subSource) {
        this.subSource = subSource;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


}
