package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "paymentBonusType",
    "bonusPaymentDate",
    "bonusPaidAmount",
    "puaBonusFaceAmount",
    "puaBonusAmount",
    "puaBonusDeclarationDate"
})
public class BonusDetail implements Serializable
{

    @JsonProperty("paymentBonusType")
    private String paymentBonusType;
    @JsonProperty("bonusPaymentDate")
    private String bonusPaymentDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("bonusPaidAmount")
    private String bonusPaidAmount;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("puaBonusFaceAmount")
    private String puaBonusFaceAmount;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("puaBonusAmount")
    private String puaBonusAmount;
    @JsonProperty("puaBonusDeclarationDate")
    private String puaBonusDeclarationDate;

    @JsonProperty("paymentBonusType")
    public String getPaymentBonusType() {
        return paymentBonusType;
    }

    @JsonProperty("paymentBonusType")
    public void setPaymentBonusType(String paymentBonusType) {
        this.paymentBonusType = paymentBonusType;
    }

    public BonusDetail withPaymentBonusType(String paymentBonusType) {
        this.paymentBonusType = paymentBonusType;
        return this;
    }

    @JsonProperty("bonusPaymentDate")
    public String getBonusPaymentDate() {
        return bonusPaymentDate;
    }

    @JsonProperty("bonusPaymentDate")
    public void setBonusPaymentDate(String bonusPaymentDate) {
        this.bonusPaymentDate = bonusPaymentDate;
    }

    public BonusDetail withBonusPaymentDate(String bonusPaymentDate) {
        this.bonusPaymentDate = bonusPaymentDate;
        return this;
    }

    @JsonProperty("bonusPaidAmount")
    public String getBonusPaidAmount() {
        return bonusPaidAmount;
    }

    @JsonProperty("bonusPaidAmount")
    public void setBonusPaidAmount(String bonusPaidAmount) {
        this.bonusPaidAmount = bonusPaidAmount;
    }

    public BonusDetail withBonusPaidAmount(String bonusPaidAmount) {
        this.bonusPaidAmount = bonusPaidAmount;
        return this;
    }

    @JsonProperty("puaBonusFaceAmount")
    public String getPuaBonusFaceAmount() {
        return puaBonusFaceAmount;
    }

    @JsonProperty("puaBonusFaceAmount")
    public void setPuaBonusFaceAmount(String puaBonusFaceAmount) {
        this.puaBonusFaceAmount = puaBonusFaceAmount;
    }

    public BonusDetail withPuaBonusFaceAmount(String puaBonusFaceAmount) {
        this.puaBonusFaceAmount = puaBonusFaceAmount;
        return this;
    }

    @JsonProperty("puaBonusAmount")
    public String getPuaBonusAmount() {
        return puaBonusAmount;
    }

    @JsonProperty("puaBonusAmount")
    public void setPuaBonusAmount(String puaBonusAmount) {
        this.puaBonusAmount = puaBonusAmount;
    }

    public BonusDetail withPuaBonusAmount(String puaBonusAmount) {
        this.puaBonusAmount = puaBonusAmount;
        return this;
    }

    @JsonProperty("puaBonusDeclarationDate")
    public String getPuaBonusDeclarationDate() {
        return puaBonusDeclarationDate;
    }

    @JsonProperty("puaBonusDeclarationDate")
    public void setPuaBonusDeclarationDate(String puaBonusDeclarationDate) {
        this.puaBonusDeclarationDate = puaBonusDeclarationDate;
    }

    public BonusDetail withPuaBonusDeclarationDate(String puaBonusDeclarationDate) {
        this.puaBonusDeclarationDate = puaBonusDeclarationDate;
        return this;
    }

	@Override
	public String toString() {

        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

		return "BonusDetail [paymentBonusType=" + paymentBonusType + ", bonusPaymentDate=" + bonusPaymentDate
				+ ", bonusPaidAmount=" + bonusPaidAmount + ", puaBonusFaceAmount=" + puaBonusFaceAmount
				+ ", puaBonusAmount=" + puaBonusAmount + ", puaBonusDeclarationDate=" + puaBonusDeclarationDate + "]";
	}

   
}
