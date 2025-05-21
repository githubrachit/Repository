package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplacementSaleReponsePayload  {

	@JsonProperty("replacementSale")
	private String replacementSale;

	@JsonProperty("splitFlag")
	private String splitFlag;

	@JsonProperty("errorCode")
	private String errorCode;

	@JsonProperty("errorMessage")
	private String errorMessage;

	public String getReplacementSale() {
		return replacementSale;
	}

	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setReplacementSale(String replacementSale) {
		this.replacementSale = replacementSale;
	}
}
