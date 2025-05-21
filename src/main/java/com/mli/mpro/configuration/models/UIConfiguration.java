package com.mli.mpro.configuration.models;

import java.util.Date;
import java.util.List;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*This class has all the fields which are going to save in DB as a document*/
@Document(collection = "uiconfiguration")
public class UIConfiguration {
	@Id
	private String id;
	private String key;
	private String type;
	private Date fromDate;
	private Date toDate;
	private String fundForPf;

	public String getCoolingPeriodDays() {
		return coolingPeriodDays;
	}

	public void setCoolingPeriodDays(String coolingPeriodDays) {
		this.coolingPeriodDays = coolingPeriodDays;
	}

	private String coolingPeriodDays;
	private String fromNFOFund;
	private String toNFOFund;
	private List<String> nfoProductList;
	private List<String> nonNfoProductList;
	private List<String> disabilityProducts;
	private List<String> vernacularProducts;
	private List<String> cisProducts;
	private List<String> shorterJourneyApplicableStates;
	private List<ShorterJourneyChannelDetails> shorterJourneyApplicableChannels;
	private String shorterJourneyAllStateApplicable;
	private String shorterJourneyAllChannelApplicable;
	private List<String> nonApplicableGoNames;

	public List<String> getNonApplicableGoNames() {
		return nonApplicableGoNames;
	}

	public void setNonApplicableGoNames(List<String> nonApplicableGoNames) {
		this.nonApplicableGoNames = nonApplicableGoNames;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFromNFOFund() {
		return fromNFOFund;
	}

	public void setFromNFOFund(String fromNFOFund) {
		this.fromNFOFund = fromNFOFund;
	}

	public String getToNFOFund() {
		return toNFOFund;
	}

	public void setToNFOFund(String toNFOFund) {
		this.toNFOFund = toNFOFund;
	}
	
	public List<String> getNfoProductList() {
		return nfoProductList;
	}

	public void setNfoProductList(List<String> nfoProductList) {
		this.nfoProductList = nfoProductList;
	}
	
	public List<String> getNonNfoProductList() {
		return nonNfoProductList;
	}

	public void setNonNfoProductList(List<String> nonNfoProductList) {
		this.nonNfoProductList = nonNfoProductList;
	}

	public List<String> getDisabilityProducts() {
		return disabilityProducts;
	}

	public void setDisabilityProducts(List<String> disabilityProducts) {
		this.disabilityProducts = disabilityProducts;
	}

	public List<String> getVernacularProducts() {
		return vernacularProducts;
	}

	public void setVernacularProducts(List<String> vernacularProducts) {
		this.vernacularProducts = vernacularProducts;
	}

	public List<String> getCisProducts() {
		return cisProducts;
	}

	public void setCisProducts(List<String> cisProducts) {
		this.cisProducts = cisProducts;
	}

	public String getFundForPf() {
		return fundForPf;
	}

	public void setFundForPf(String fundForPf) {
		this.fundForPf = fundForPf;
	}

	public List<String> getShorterJourneyApplicableStates() {
		return shorterJourneyApplicableStates;
	}

	public void setShorterJourneyApplicableStates(List<String> shorterJourneyApplicableStates) {
		this.shorterJourneyApplicableStates = shorterJourneyApplicableStates;
	}

	public List<ShorterJourneyChannelDetails> getShorterJourneyApplicableChannels() {
		return shorterJourneyApplicableChannels;
	}

	public void setShorterJourneyApplicableChannels(List<ShorterJourneyChannelDetails> shorterJourneyApplicableChannels) {
		this.shorterJourneyApplicableChannels = shorterJourneyApplicableChannels;
	}

	public String getShorterJourneyAllStateApplicable() {
		return shorterJourneyAllStateApplicable;
	}

	public void setShorterJourneyAllStateApplicable(String shorterJourneyAllStateApplicable) {
		this.shorterJourneyAllStateApplicable = shorterJourneyAllStateApplicable;
	}

	public String getShorterJourneyAllChannelApplicable() {
		return shorterJourneyAllChannelApplicable;
	}

	public void setShorterJourneyAllChannelApplicable(String shorterJourneyAllChannelApplicable) {
		this.shorterJourneyAllChannelApplicable = shorterJourneyAllChannelApplicable;
	}


	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "UIConfiguration{" +
				"id='" + id + '\'' +
				", key='" + key + '\'' +
				", type='" + type + '\'' +
				", fromDate=" + fromDate +
				", toDate=" + toDate +
				", coolingPeriodDays=" + coolingPeriodDays +
				", fromNFOFund=" + fromNFOFund +
				", toNFOFund=" + toNFOFund +
				", nfoProductList=" + nfoProductList +
				", nonNfoProductList=" + nonNfoProductList +
				", disabilityProducts=" + disabilityProducts +
				", vernacularProducts=" + vernacularProducts +
				", cisProducts=" + cisProducts +
				", fundForPf='" + fundForPf + '\'' +
				", shorterJourneyApplicableStates='" + shorterJourneyApplicableStates + '\'' +
				// ", shorterJourneyApplicableChannels='" + shorterJourneyApplicableChannels + '\'' +
				", shorterJourneyAllStateApplicable='" + shorterJourneyAllStateApplicable + '\'' +
				", shorterJourneyAllChannelApplicable='" + shorterJourneyAllChannelApplicable + '\'' +
				'}';
	}
}
