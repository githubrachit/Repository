package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePayload {

	private Boolean aml;
	private Boolean ulip;
	private Boolean physicalApplicationEntryButton;
	private Boolean newApplicationButton;
	private Boolean superannuationButton;
	private Boolean rASourcingPopUp;
	private Boolean rAJourneyApplicable;
	private Boolean verticalFilter;
	private Boolean subChannelFilter;
	@JsonProperty("isRA")
	private Boolean isRA;

	public Boolean getAml() {
		return aml;
	}
	public void setAml(Boolean aml) {
		this.aml = aml;
	}
	public Boolean getUlip() {
		return ulip;
	}
	public void setUlip(Boolean ulip) {
		this.ulip = ulip;
	}
	public Boolean getPhysicalApplicationEntryButton() {
		return physicalApplicationEntryButton;
	}
	public void setPhysicalApplicationEntryButton(Boolean physicalApplicationEntryButton) {
		this.physicalApplicationEntryButton = physicalApplicationEntryButton;
	}
	public Boolean getNewApplicationButton() {
		return newApplicationButton;
	}
	public void setNewApplicationButton(Boolean newApplicationButton) {
		this.newApplicationButton = newApplicationButton;
	}

	public Boolean getSuperannuationButton() {
		return superannuationButton;
	}
	public void setSuperannuationButton(Boolean superannuationButton) {
		this.superannuationButton = superannuationButton;
	}

	public Boolean getrASourcingPopUp() {
		return rASourcingPopUp;
	}

	public void setrASourcingPopUp(Boolean rASourcingPopUp) {
		this.rASourcingPopUp = rASourcingPopUp;
	}

	public Boolean getrAJourneyApplicable() {
		return rAJourneyApplicable;
	}

	public void setrAJourneyApplicable(Boolean rAJourneyApplicable) {
		this.rAJourneyApplicable = rAJourneyApplicable;
	}

	public Boolean getVerticalFilter() {
		return verticalFilter;
	}

	public void setVerticalFilter(Boolean verticalFilter) {
		this.verticalFilter = verticalFilter;
	}

	public Boolean getSubChannelFilter() {
		return subChannelFilter;
	}

	public void setSubChannelFilter(Boolean subChannelFilter) {
		this.subChannelFilter = subChannelFilter;
	}

	public Boolean getRA() {
		return isRA;
	}

	public void setRA(Boolean RA) {
		isRA = RA;
	}

	@Override
	public String toString() {
		return "ResponsePayload{" +
				"aml=" + aml +
				", ulip=" + ulip +
				", physicalApplicationEntryButton=" + physicalApplicationEntryButton +
				", newApplicationButton=" + newApplicationButton +
				", superannuationButton=" + superannuationButton +
				", rASourcingPopUp=" + rASourcingPopUp +
				", rAJourneyApplicable=" + rAJourneyApplicable +
				", verticalFilter=" + verticalFilter +
				", subChannelFilter=" + subChannelFilter +
				", isRA=" + isRA +
				'}';
	}
}
