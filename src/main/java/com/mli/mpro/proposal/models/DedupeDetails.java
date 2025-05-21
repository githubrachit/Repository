package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class DedupeDetails {

    @JsonProperty("clientType")
    private String clientType;
    @JsonProperty("clientId")
    private String clientId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("previousPolicyNumber")
    private String previousPolicyNumber;
    @JsonProperty("dedupeFlag")
    private String dedupeFlag;
    @JsonProperty("policyStatus")
    private String policyStatus;
    @JsonProperty("policyPlanCode")
    private String policyPlanCode;
    @JsonProperty("discountFlag")
    private String discountFlag;
    @JsonProperty("prevpolicydate")
    private String prevpolicydate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("exCustomerTotalAfyp")
    private String exCustomerTotalAfyp;
	@Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("exCustomerActivePolicy")
    private String exCustomerActivePolicy;
	@Sensitive(MaskType.POLICY_NUM)
    private int exCustomerIssuePolicy;
    @Sensitive(MaskType.AMOUNT)
    private String exCustomerActiveAfyp;
    private int exTotalRiderCount;


    public String getClientId() {
	return clientId;
    }

    public void setClientId(String clientId) {
	this.clientId = clientId;
    }

    public String getPreviousPolicyNumber() {
	return previousPolicyNumber;
    }

    public void setPreviousPolicyNumber(String previousPolicyNumber) {
	this.previousPolicyNumber = previousPolicyNumber;
    }

    public String getClientType() {
	return clientType;
    }

    public void setClientType(String clientType) {
	this.clientType = clientType;
    }

    public String getDedupeFlag() {
	return dedupeFlag;
    }

    public void setDedupeFlag(String dedupeFlag) {
	this.dedupeFlag = dedupeFlag;
    }

    public String getPolicyStatus() {
	return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
    }

    public String getPolicyPlanCode() {
	return policyPlanCode;
    }

    public void setPolicyPlanCode(String policyPlanCode) {
	this.policyPlanCode = policyPlanCode;
    }

    public String getDiscountFlag() {
        return discountFlag;
    }

    public void setDiscountFlag(String discountFlag) {
        this.discountFlag = discountFlag;
    }
    

    public String getPrevpolicydate() {
		return prevpolicydate;
	}

	public void setPrevpolicydate(String prevpolicydate) {
		this.prevpolicydate = prevpolicydate;
	}

	public String getExCustomerTotalAfyp() {
		return exCustomerTotalAfyp;
	}

	public void setExCustomerTotalAfyp(String exCustomerTotalAfyp) {
		this.exCustomerTotalAfyp = exCustomerTotalAfyp;
	}

	public String getExCustomerActivePolicy() {
		return exCustomerActivePolicy;
	}

	public void setExCustomerActivePolicy(String exCustomerActivePolicy) {
		this.exCustomerActivePolicy = exCustomerActivePolicy;
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

	@Override
	public String toString() {
		 if (Utility.isCalledFromLogs(Thread.currentThread())) {
	            return Utility.toString(this);
	        }
		return "DedupeDetails [clientType=" + clientType + ", clientId=" + clientId + ", previousPolicyNumber="
				+ previousPolicyNumber + ", dedupeFlag=" + dedupeFlag + ", policyStatus=" + policyStatus
				+ ", policyPlanCode=" + policyPlanCode + ", discountFlag=" + discountFlag + ", prevpolicydate="
				+ prevpolicydate + ", exCustomerTotalAfyp=" + exCustomerTotalAfyp + ", exCustomerActivePolicy="
				+ exCustomerActivePolicy + ", exCustomerIssuePolicy=" + exCustomerIssuePolicy
				+ ", exCustomerActiveAfyp=" + exCustomerActiveAfyp + ", exTotalRiderCount=" + exTotalRiderCount + "]";
	}
	
	

}
