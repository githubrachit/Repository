
package com.mli.mpro.pratham.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "riderName", "riderSumAssured", "riderPremium", "riderPremiumPaymentTerm", "riderTerm",
		"riderEndDate", "riderPremiumDueDate", "riderLastPremiumDueDate" })
public class RiderDetail {

	@JsonProperty("riderName")
	private String riderName;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("riderSumAssured")
	private String riderSumAssured;
	@Sensitive(MaskType.AMOUNT)
	@JsonProperty("riderPremium")
	private String riderPremium;
	@JsonProperty("riderPremiumPaymentTerm")
	private String riderPremiumPaymentTerm;
	@JsonProperty("riderTerm")
	private String riderTerm;
	@JsonProperty("riderEndDate")
	private String riderEndDate;
	@JsonProperty("riderPremiumDueDate")
	private String riderPremiumDueDate;
	@JsonProperty("riderLastPremiumDueDate")
	private String riderLastPremiumDueDate;

	@JsonProperty("riderName")
	public String getRiderName() {
		return riderName;
	}

	@JsonProperty("riderName")
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}

	@JsonProperty("riderSumAssured")
	public String getRiderSumAssured() {
		return riderSumAssured;
	}

	@JsonProperty("riderSumAssured")
	public void setRiderSumAssured(String riderSumAssured) {
		this.riderSumAssured = riderSumAssured;
	}

	@JsonProperty("riderPremium")
	public String getRiderPremium() {
		return riderPremium;
	}

	@JsonProperty("riderPremium")
	public void setRiderPremium(String riderPremium) {
		this.riderPremium = riderPremium;
	}

	@JsonProperty("riderPremiumPaymentTerm")
	public String getRiderPremiumPaymentTerm() {
		return riderPremiumPaymentTerm;
	}

	@JsonProperty("riderPremiumPaymentTerm")
	public void setRiderPremiumPaymentTerm(String riderPremiumPaymentTerm) {
		this.riderPremiumPaymentTerm = riderPremiumPaymentTerm;
	}

	@JsonProperty("riderTerm")
	public String getRiderTerm() {
		return riderTerm;
	}

	@JsonProperty("riderTerm")
	public void setRiderTerm(String riderTerm) {
		this.riderTerm = riderTerm;
	}

	@JsonProperty("riderEndDate")
	public String getRiderEndDate() {
		return riderEndDate;
	}

	@JsonProperty("riderEndDate")
	public void setRiderEndDate(String riderEndDate) {
		this.riderEndDate = riderEndDate;
	}

	@JsonProperty("riderPremiumDueDate")
	public String getRiderPremiumDueDate() {
		return riderPremiumDueDate;
	}

	@JsonProperty("riderPremiumDueDate")
	public void setRiderPremiumDueDate(String riderPremiumDueDate) {
		this.riderPremiumDueDate = riderPremiumDueDate;
	}

	@JsonProperty("riderLastPremiumDueDate")
	public String getRiderLastPremiumDueDate() {
		return riderLastPremiumDueDate;
	}

	@JsonProperty("riderLastPremiumDueDate")
	public void setRiderLastPremiumDueDate(String riderLastPremiumDueDate) {
		this.riderLastPremiumDueDate = riderLastPremiumDueDate;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RiderDetail [riderName=" + riderName + ", riderSumAssured=" + riderSumAssured + ", riderPremium="
				+ riderPremium + ", riderPremiumPaymentTerm=" + riderPremiumPaymentTerm + ", riderTerm=" + riderTerm
				+ ", riderEndDate=" + riderEndDate + ", riderPremiumDueDate=" + riderPremiumDueDate
				+ ", riderLastPremiumDueDate=" + riderLastPremiumDueDate + "]";
	}

}
