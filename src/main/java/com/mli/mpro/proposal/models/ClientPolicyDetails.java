package com.mli.mpro.proposal.models;

import java.util.List;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.Payload;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ClientPolicyDetails {

    private String type;

    private String isReplacementSale;

    private List<Payload> policyDetails;
    @Sensitive(MaskType.POLICY_NUM)
    private int exCustomerIssuePolicy;
    private String exCustomerActiveAfyp;
    private int exTotalRiderCount;

    /**
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @return the isReplacementSale
     */
    public String getIsReplacementSale() {
	return isReplacementSale;
    }

    /**
     * @param isReplacementSale the isReplacementSale to set
     */
    public void setIsReplacementSale(String isReplacementSale) {
	this.isReplacementSale = isReplacementSale;
    }

    /**
     * @return the policyDetails
     */
    public List<Payload> getPolicyDetails() {
	return policyDetails;
    }

    /**
     * @param policyDetails the policyDetails to set
     */
    public void setPolicyDetails(List<Payload> policyDetails) {
	this.policyDetails = policyDetails;
    }
    
    

    public int getExCustomerIssuePolicy() {
		return exCustomerIssuePolicy;
	}

	public void setExCustomerIssuePolicy(int exCustomerIssuePolicy) {
		this.exCustomerIssuePolicy = exCustomerIssuePolicy;
	}

	public String getExCustomerActiveAfyp() {
		return exCustomerActiveAfyp;
	}

	public void setExCustomerActiveAfyp(String exCustomerActiveAfyp) {
		this.exCustomerActiveAfyp = exCustomerActiveAfyp;
	}

	public int getExTotalRiderCount() {
		return exTotalRiderCount;
	}

	public void setExTotalRiderCount(int exTotalRiderCount) {
		this.exTotalRiderCount = exTotalRiderCount;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ClientPolicyDetails [type=" + type + ", isReplacementSale=" + isReplacementSale + ", policyDetails=" + policyDetails + "]";
    }

}
