
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isRiderRequired", "riderInfo", "amount", "riderTerm", "riderSumAssured", "riderModalPremium", "riderServiceTax" })
public class RiderDetails {
    @JsonProperty("isRiderRequired")
    private boolean isRiderRequired;
    @JsonProperty("riderInfo")
    private String riderInfo;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("riderTerm")
    private String riderTerm;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderSumAssured")
    private double riderSumAssured;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderModalPremium")
    private double riderModalPremium;

    @JsonProperty("riderServiceTax")
    private String riderServiceTax;

    @JsonProperty("riderInsuredDetails")
    private String riderInsuredDetails;

    @JsonProperty("riderPlanCode")
    private String riderPlanCode;

    @JsonProperty("typeOfValue")
    private String typeOfValue;
    // field added for rider plan code
    //FUL2-9523_Critical_Illness_Plus_Rider
    @JsonProperty("riderVariant")
    private String riderVariant;
    @JsonProperty("riderCIPremiumType")
    private String riderCIPremiumType;

    @JsonProperty("ciRiderPpt")
    private String ciRiderPpt;
    @JsonProperty("riderPpt")
    private String riderPpt;
    @JsonProperty("returnOfPremium")
    private String returnOfPremium;
	@JsonProperty("isSmartUltraProtectRider")
	private String isSmartUltraProtectRider;
	@JsonProperty("riderPremiumType")
	private String riderPremiumType;
    
    /**
     * No args constructor for use in serialization
     * 
     */
    public RiderDetails() {
    }

    public String getRiderPpt() {
        return riderPpt;
    }

    public void setRiderPpt(String riderPpt) {
        this.riderPpt = riderPpt;
    }

    @JsonProperty("riderInfo")
    public String getRiderInfo() {
	return riderInfo;
    }

    public RiderDetails(boolean isRiderRequired, String riderInfo, double amount, String riderTerm, double riderSumAssured, double riderModalPremium,
                        String riderServiceTax, String riderInsuredDetails, String riderPlanCode, String typeOfValue, String ciRiderPpt) {
	super();
	this.isRiderRequired = isRiderRequired;
	this.riderInfo = riderInfo;
	this.amount = amount;
	this.riderTerm = riderTerm;
	this.riderSumAssured = riderSumAssured;
	this.riderModalPremium = riderModalPremium;
	this.riderServiceTax = riderServiceTax;
	this.riderInsuredDetails = riderInsuredDetails;
	this.riderPlanCode = riderPlanCode;
	this.typeOfValue = typeOfValue;
	this.ciRiderPpt = ciRiderPpt;
    }

    @JsonProperty("riderInfo")
    public void setRiderInfo(String riderInfo) {
	this.riderInfo = riderInfo;
    }

    @JsonProperty("amount")
    public double getAmount() {
	return amount;
    }

    @JsonProperty("amount")
    public void setAmount(double amount) {
	this.amount = amount;
    }

    public boolean isRiderRequired() {
	return isRiderRequired;
    }

    public void setRiderRequired(boolean isRiderRequired) {
	this.isRiderRequired = isRiderRequired;
    }

    public String getRiderTerm() {
	return riderTerm;
    }

    public void setRiderTerm(String riderTerm) {
	this.riderTerm = riderTerm;
    }

    public double getRiderSumAssured() {
	return riderSumAssured;
    }

    public void setRiderSumAssured(double riderSumAssured) {
	this.riderSumAssured = riderSumAssured;
    }

    public double getRiderModalPremium() {
	return riderModalPremium;
    }

    public void setRiderModalPremium(double riderModalPremium) {
	this.riderModalPremium = riderModalPremium;
    }

    public String getRiderServiceTax() {
	return riderServiceTax;
    }

    public void setRiderServiceTax(String riderServiceTax) {
	this.riderServiceTax = riderServiceTax;
    }

    public String getRiderInsuredDetails() {
	return riderInsuredDetails;
    }

    public void setRiderInsuredDetails(String riderInsuredDetails) {
	this.riderInsuredDetails = riderInsuredDetails;
    }

    public String getRiderPlanCode() {
	return riderPlanCode;
    }

    public void setRiderPlanCode(String riderPlanCode) {
	this.riderPlanCode = riderPlanCode;
    }

    public String getTypeOfValue() {
        return typeOfValue;
    }

    public void setTypeOfValue(String typeOfValue) {
        this.typeOfValue = typeOfValue;
    }

    public String getRiderVariant() {
		return riderVariant;
	}

	public void setRiderVariant(String riderVariant) {
		this.riderVariant = riderVariant;
	}

	public String getRiderCIPremiumType() {
		return riderCIPremiumType;
	}

	public void setRiderCIPremiumType(String riderCIPremiumType) {
		this.riderCIPremiumType = riderCIPremiumType;
	}

    public String getCiRiderPpt() {
        return ciRiderPpt;
    }

    public void setCiRiderPpt(String ciRiderPpt) {
        this.ciRiderPpt = ciRiderPpt;
    }

    public String getReturnOfPremium() {
		return returnOfPremium;
	}

	public void setReturnOfPremium(String returnOfPremium) {
		this.returnOfPremium = returnOfPremium;
	}
	
	public String getIsSmartUltraProtectRider() {
		return isSmartUltraProtectRider;
	}

	public void setIsSmartUltraProtectRider(String isSmartUltraProtectRider) {
		this.isSmartUltraProtectRider = isSmartUltraProtectRider;
	}

	public String getRiderPremiumType() {
		return riderPremiumType;
	}

	public void setRiderPremiumType(String riderPremiumType) {
		this.riderPremiumType = riderPremiumType;
	}

	@Override
	public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
		return "RiderDetails [isRiderRequired=" + isRiderRequired + ", riderInfo=" + riderInfo + ", amount=" + amount
				+ ", riderTerm=" + riderTerm + ", riderSumAssured=" + riderSumAssured + ", riderModalPremium="
				+ riderModalPremium + ", riderServiceTax=" + riderServiceTax + ", riderInsuredDetails="
				+ riderInsuredDetails + ", riderPlanCode=" + riderPlanCode + ", typeOfValue=" + typeOfValue
				+ ", riderVariant=" + riderVariant + ", riderCIPremiumType="+riderCIPremiumType
                + ", ciRiderPpt=" + ciRiderPpt + 
                ", returnOfPremium=" + returnOfPremium +
                ", isSmartUltraProtectRider=" + isSmartUltraProtectRider +
				", riderPremiumType=" + riderPremiumType +
				"]";
	}

}
