package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "paymentMode",
    "paymentDate",
    "amount",
    "status"
})
public class PaymentHistory
{

    @JsonProperty("paymentMode")
    private String paymentMode;
    @JsonProperty("paymentDate")
    private String paymentDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;

    @JsonProperty("paymentMode")
    public String getPaymentMode() {
        return paymentMode;
    }

    @JsonProperty("paymentMode")
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentHistory withPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    @JsonProperty("paymentDate")
    public String getPaymentDate() {
        return paymentDate;
    }

    @JsonProperty("paymentDate")
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentHistory withPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public PaymentHistory withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentHistory withStatus(String status) {
        this.status = status;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "PaymentHistory [paymentMode=" + paymentMode + ", paymentDate=" + paymentDate + ", amount=" + amount
				+ ", status=" + status + "]";
	}

   

}
