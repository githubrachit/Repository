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
    "refundDate",
    "refundAmount",
    "refundMethodCd",
    "refundMethodDesc"
})
public class RefundDetail 
{

    @JsonProperty("refundDate")
    private String refundDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("refundAmount")
    private String refundAmount;
    @JsonProperty("refundMethodCd")
    private String refundMethodCd;
    @JsonProperty("refundMethodDesc")
    private String refundMethodDesc;

    @JsonProperty("refundDate")
    public String getRefundDate() {
        return refundDate;
    }

    @JsonProperty("refundDate")
    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public RefundDetail withRefundDate(String refundDate) {
        this.refundDate = refundDate;
        return this;
    }

    @JsonProperty("refundAmount")
    public String getRefundAmount() {
        return refundAmount;
    }

    @JsonProperty("refundAmount")
    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public RefundDetail withRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    @JsonProperty("refundMethodCd")
    public String getRefundMethodCd() {
        return refundMethodCd;
    }

    @JsonProperty("refundMethodCd")
    public void setRefundMethodCd(String refundMethodCd) {
        this.refundMethodCd = refundMethodCd;
    }

    public RefundDetail withRefundMethodCd(String refundMethodCd) {
        this.refundMethodCd = refundMethodCd;
        return this;
    }

    @JsonProperty("refundMethodDesc")
    public String getRefundMethodDesc() {
        return refundMethodDesc;
    }

    @JsonProperty("refundMethodDesc")
    public void setRefundMethodDesc(String refundMethodDesc) {
        this.refundMethodDesc = refundMethodDesc;
    }

    public RefundDetail withRefundMethodDesc(String refundMethodDesc) {
        this.refundMethodDesc = refundMethodDesc;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "RefundDetail [refundDate=" + refundDate + ", refundAmount=" + refundAmount + ", refundMethodCd="
				+ refundMethodCd + ", refundMethodDesc=" + refundMethodDesc + "]";
	}

  

}
