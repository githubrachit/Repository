package com.mli.mpro.location.amlulip.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputPayload {
	
	private String ulipTrainingExpirationDate;
	private String amlTrainingExpirationDate;
	private String specifiedPersonName;
	private Boolean aml;
	private Boolean ulip;
	private Boolean newApplicationButton;
	
	public String getUlipTrainingExpirationDate() {
		return ulipTrainingExpirationDate;
	}
	public void setUlipTrainingExpirationDate(String ulipTrainingExpirationDate) {
		this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
	}
	public String getAmlTrainingExpirationDate() {
		return amlTrainingExpirationDate;
	}
	public void setAmlTrainingExpirationDate(String amlTrainingExpirationDate) {
		this.amlTrainingExpirationDate = amlTrainingExpirationDate;
	}
	public String getSpecifiedPersonName() {
		return specifiedPersonName;
	}
	public void setSpecifiedPersonName(String specifiedPersonName) {
		this.specifiedPersonName = specifiedPersonName;
	}
	public boolean isAml() {
		return aml;
	}
	public void setAml(boolean aml) {
		this.aml = aml;
	}
	public boolean isUlip() {
		return ulip;
	}
	public void setUlip(boolean ulip) {
		this.ulip = ulip;
	}
	public boolean isNewApplicationButton() {
		return newApplicationButton;
	}
	public void setNewApplicationButton(boolean newApplicationButton) {
		this.newApplicationButton = newApplicationButton;
	}
	@Override
	public String toString() {
		return "OutputPayload [ulipTrainingExpirationDate=" + ulipTrainingExpirationDate
				+ ", amlTrainingExpirationDate=" + amlTrainingExpirationDate + ", specifiedPersonName="
				+ specifiedPersonName + ", aml=" + aml + ", ulip=" + ulip + ", newApplicationButton="
				+ newApplicationButton + "]";
	}
}
