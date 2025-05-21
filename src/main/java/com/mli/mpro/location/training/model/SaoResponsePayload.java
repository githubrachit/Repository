package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaoResponsePayload {
	
	private String aml;
	private String ulip;
	private String physicalApplicationEntryButton;
	private String newApplicationButton;
	private String superannuationButton;
	private String rASourcingPopUp;
	private String rAJourneyApplicable;
	private String verticalFilter;
	private String subChannelFilter;

	public String getAml() {
		return aml;
	}
	public void setAml(String aml) {
		this.aml = aml;
	}
	public String getUlip() {
		return ulip;
	}
	public void setUlip(String ulip) {
		this.ulip = ulip;
	}
	public String getPhysicalApplicationEntryButton() {
		return physicalApplicationEntryButton;
	}
	public void setPhysicalApplicationEntryButton(String physicalApplicationEntryButton) {
		this.physicalApplicationEntryButton = physicalApplicationEntryButton;
	}
	public String getNewApplicationButton() {
		return newApplicationButton;
	}
	public void setNewApplicationButton(String newApplicationButton) {
		this.newApplicationButton = newApplicationButton;
	}
	public String getSuperannuationButton() {
		return superannuationButton;
	}
	public void setSuperannuationButton(String superannuationButton) {
		this.superannuationButton = superannuationButton;
	}

	public String getRaSourcingPopUp() {
		return rASourcingPopUp;
	}

	public void setRaSourcingPopUp(String rASourcingPopUp) {
		this.rASourcingPopUp = rASourcingPopUp;
	}

	public String getrAJourneyApplicable() {
		return rAJourneyApplicable;
	}

	public void setrAJourneyApplicable(String rAJourneyApplicable) {
		this.rAJourneyApplicable = rAJourneyApplicable;
	}

	public String getVerticalFilter() {
		return verticalFilter;
	}

	public void setVerticalFilter(String verticalFilter) {
		this.verticalFilter = verticalFilter;
	}

	public String getSubChannelFilter() {
		return subChannelFilter;
	}

	public void setSubChannelFilter(String subChannelFilter) {
		this.subChannelFilter = subChannelFilter;
	}

	@Override
	public String toString() {
		return "SaoResponsePayload{" +
				"aml='" + aml + '\'' +
				", ulip='" + ulip + '\'' +
				", physicalApplicationEntryButton='" + physicalApplicationEntryButton + '\'' +
				", newApplicationButton='" + newApplicationButton + '\'' +
				", superannuationButton='" + superannuationButton + '\'' +
				'}';
	}
}
