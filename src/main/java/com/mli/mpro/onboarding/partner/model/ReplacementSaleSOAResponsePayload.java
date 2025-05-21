package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplacementSaleSOAResponsePayload extends SOAResponsePayload {

	@JsonProperty("replacementSale")
	private String replacementSale;

	@JsonProperty("splitFlag")
	private String splitFlag;

	@JsonProperty("errorCode")
	private String errorCode;

	@JsonProperty("errorMessage")
	private String errorMessage;

	public ReplacementSaleSOAResponsePayload() {
		// TODO Auto-generated constructor stub
	}
	
	public String getReplacementSale() {
		return replacementSale;
	}

	public void setReplacementSale(String replacementSale) {
		this.replacementSale = replacementSale;
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

	@Override
	public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "SplittingorReplacementResponsePayload [replacementSale=" + replacementSale + ", splitFlag=" + splitFlag
				+ ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

	
}
