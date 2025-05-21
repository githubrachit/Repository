
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isFundAllocated", "isSTPAllocated", "isDFAAllocated", "fundInfo", "lifecyclePortfolioStrategy", "triggerBasedPortfolioStrategy", "fund1", "fund2", "triggerPercentage", "isAcceptableFund"})
public class FundSelection {

    @JsonProperty("isFundAllocated")
    private boolean isFundAllocated;
    @JsonProperty("isSTPAllocated")
    private boolean isSTPAllocated;
    @JsonProperty("isDFAAllocated")
    private boolean isDFAAllocated;

    @JsonProperty("fundInfo")
    private List<FundInfo> fundInfo;
    @JsonProperty("lifecyclePortfolioStrategy")
	private boolean lifecyclePortfolioStrategy;
	@JsonProperty("triggerBasedPortfolioStrategy")
	private boolean triggerBasedPortfolioStrategy;
	@JsonProperty("fund1")
	private String fund1;
	@JsonProperty("fund2")
	private String fund2;
	@JsonProperty("triggerPercentage")
	private String triggerPercentage;
	@JsonProperty("isAcceptableFund")
	private String isAcceptableFund;
	//FUL2-82109
	@JsonProperty("isRecommendedFundAllocated")
   	private boolean isRecommendedFundAllocated;

	
	 @JsonProperty("esgFundSelected")
		private String esgFundSelected;
	 
    public String getEsgFundSelected() {
		return esgFundSelected;
	}

	public void setEsgFundSelected(String esgFundSelected) {
		this.esgFundSelected = esgFundSelected;
	}

	/**
     * No args constructor for use in serialization
     * 
     */
    public FundSelection() {
    }

    public FundSelection(boolean isFundAllocated, boolean isSTPAllocated, boolean isDFAAllocated, List<FundInfo> fundInfo, boolean lifecyclePortfolioStrategy, boolean triggerBasedPortfolioStrategy,
			String fund1, String fund2, String triggerPercentage, String isAcceptableFund, boolean isRecommendedFundAllocated) {
	super();
	this.isFundAllocated = isFundAllocated;
	this.isSTPAllocated = isSTPAllocated;
	this.isDFAAllocated = isDFAAllocated;
	this.fundInfo = fundInfo;
	this.lifecyclePortfolioStrategy = lifecyclePortfolioStrategy;
	this.triggerBasedPortfolioStrategy = triggerBasedPortfolioStrategy;
	this.fund1 = fund1;
	this.fund2 = fund2;
	this.triggerPercentage = triggerPercentage;
	this.isAcceptableFund = isAcceptableFund;
	this.isRecommendedFundAllocated= isRecommendedFundAllocated;
    }

    public boolean isFundAllocated() {
	return isFundAllocated;
    }

    public void setFundAllocated(boolean isFundAllocated) {
	this.isFundAllocated = isFundAllocated;
    }

    public boolean isSTPAllocated() {
	return isSTPAllocated;
    }

    public void setSTPAllocated(boolean isSTPAllocated) {
	this.isSTPAllocated = isSTPAllocated;
    }

    public boolean isDFAAllocated() {
	return isDFAAllocated;
    }

    public void setDFAAllocated(boolean isDFAAllocated) {
	this.isDFAAllocated = isDFAAllocated;
    }

    public List<FundInfo> getFundInfo() {
	return fundInfo;
    }

    public void setFundInfo(List<FundInfo> fundInfo) {
	this.fundInfo = fundInfo;
    }
    
    public boolean isLifecyclePortfolioStrategy() {
		return lifecyclePortfolioStrategy;
	}

	public void setLifecyclePortfolioStrategy(boolean lifecyclePortfolioStrategy) {
		this.lifecyclePortfolioStrategy = lifecyclePortfolioStrategy;
	}

	public boolean isTriggerBasedPortfolioStrategy() {
		return triggerBasedPortfolioStrategy;
	}

	public void setTriggerBasedPortfolioStrategy(boolean triggerBasedPortfolioStrategy) {
		this.triggerBasedPortfolioStrategy = triggerBasedPortfolioStrategy;
	}

	public String getFund1() {
		return fund1;
	}

	public void setFund1(String fund1) {
		this.fund1 = fund1;
	}

	public String getFund2() {
		return fund2;
	}

	public void setFund2(String fund2) {
		this.fund2 = fund2;
	}

	public String getTriggerPercentage() {
		return triggerPercentage;
	}

	public void setTriggerPercentage(String triggerPercentage) {
		this.triggerPercentage = triggerPercentage;
	}

	public String getIsAcceptableFund() {
		return isAcceptableFund;
	}

	public void setIsAcceptableFund(String isAcceptableFund) {
		this.isAcceptableFund = isAcceptableFund;
	}
	

	public boolean isRecommendedFundAllocated() {
		return isRecommendedFundAllocated;
	}

	public void setRecommendedFundAllocated(boolean isRecommendedFundAllocated) {
		this.isRecommendedFundAllocated = isRecommendedFundAllocated;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "FundSelection [isFundAllocated=" + isFundAllocated + ", isSTPAllocated=" + isSTPAllocated
				+ ", isDFAAllocated=" + isDFAAllocated + ", fundInfo=" + fundInfo + ", lifecyclePortfolioStrategy="
				+ lifecyclePortfolioStrategy + ", triggerBasedPortfolioStrategy=" + triggerBasedPortfolioStrategy
				+ ", fund1=" + fund1 + ", fund2=" + fund2 + ", triggerPercentage=" + triggerPercentage
				+ ", isAcceptableFund=" + isAcceptableFund 
				+ ", isRecommendedFundAllocated=" + isRecommendedFundAllocated + "]";
	}

}