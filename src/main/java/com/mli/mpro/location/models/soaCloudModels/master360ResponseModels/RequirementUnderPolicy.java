package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reqIDcode",
    "reqCrtDt",
    "reqSeqNum",
    "reqCrtUsr",
    "reqStatus",
    "reqTypCd"
})
public class RequirementUnderPolicy
{

    @JsonProperty("reqIDcode")
    private String reqIDcode;
    @JsonProperty("reqCrtDt")
    private String reqCrtDt;
    @JsonProperty("reqSeqNum")
    private String reqSeqNum;
    @JsonProperty("reqCrtUsr")
    private String reqCrtUsr;
    @JsonProperty("reqStatus")
    private String reqStatus;
    @JsonProperty("reqTypCd")
    private String reqTypCd;

    @JsonProperty("reqIDcode")
    public String getReqIDcode() {
        return reqIDcode;
    }

    @JsonProperty("reqIDcode")
    public void setReqIDcode(String reqIDcode) {
        this.reqIDcode = reqIDcode;
    }

    public RequirementUnderPolicy withReqIDcode(String reqIDcode) {
        this.reqIDcode = reqIDcode;
        return this;
    }

    @JsonProperty("reqCrtDt")
    public String getReqCrtDt() {
        return reqCrtDt;
    }

    @JsonProperty("reqCrtDt")
    public void setReqCrtDt(String reqCrtDt) {
        this.reqCrtDt = reqCrtDt;
    }

    public RequirementUnderPolicy withReqCrtDt(String reqCrtDt) {
        this.reqCrtDt = reqCrtDt;
        return this;
    }

    @JsonProperty("reqSeqNum")
    public String getReqSeqNum() {
        return reqSeqNum;
    }

    @JsonProperty("reqSeqNum")
    public void setReqSeqNum(String reqSeqNum) {
        this.reqSeqNum = reqSeqNum;
    }

    public RequirementUnderPolicy withReqSeqNum(String reqSeqNum) {
        this.reqSeqNum = reqSeqNum;
        return this;
    }

    @JsonProperty("reqCrtUsr")
    public String getReqCrtUsr() {
        return reqCrtUsr;
    }

    @JsonProperty("reqCrtUsr")
    public void setReqCrtUsr(String reqCrtUsr) {
        this.reqCrtUsr = reqCrtUsr;
    }

    public RequirementUnderPolicy withReqCrtUsr(String reqCrtUsr) {
        this.reqCrtUsr = reqCrtUsr;
        return this;
    }

    @JsonProperty("reqStatus")
    public String getReqStatus() {
        return reqStatus;
    }

    @JsonProperty("reqStatus")
    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public RequirementUnderPolicy withReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
        return this;
    }

    @JsonProperty("reqTypCd")
    public String getReqTypCd() {
        return reqTypCd;
    }

    @JsonProperty("reqTypCd")
    public void setReqTypCd(String reqTypCd) {
        this.reqTypCd = reqTypCd;
    }

    public RequirementUnderPolicy withReqTypCd(String reqTypCd) {
        this.reqTypCd = reqTypCd;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "RequirementUnderPolicy [reqIDcode=" + reqIDcode + ", reqCrtDt=" + reqCrtDt + ", reqSeqNum=" + reqSeqNum
				+ ", reqCrtUsr=" + reqCrtUsr + ", reqStatus=" + reqStatus + ", reqTypCd=" + reqTypCd + "]";
	}

 
}
