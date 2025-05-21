package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequirementDetails {
    @JsonProperty("reqCrtUsr")
    private String reqCrtUsr;

    @JsonProperty("reqStatus")
    private String reqStatus;

    @JsonProperty("reqTypCd")
    private String reqTypCd;

    @JsonProperty("reqCrtDt")
    private String reqCrtDt;

    @JsonProperty("reqIDcode")
    private String reqIDcode;

    @JsonProperty("reqSeqNum")
    private String reqSeqNum;

    public String getReqCrtUsr() {
        return reqCrtUsr;
    }

    public void setReqCrtUsr(String reqCrtUsr) {
        this.reqCrtUsr = reqCrtUsr;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqTypCd() {
        return reqTypCd;
    }

    public void setReqTypCd(String reqTypCd) {
        this.reqTypCd = reqTypCd;
    }

    public String getReqCrtDt() {
        return reqCrtDt;
    }

    public void setReqCrtDt(String reqCrtDt) {
        this.reqCrtDt = reqCrtDt;
    }

    public String getReqIDcode() {
        return reqIDcode;
    }

    public void setReqIDcode(String reqIDcode) {
        this.reqIDcode = reqIDcode;
    }

    public String getReqSeqNum() {
        return reqSeqNum;
    }

    public void setReqSeqNum(String reqSeqNum) {
        this.reqSeqNum = reqSeqNum;
    }

    @Override
    public String toString() {
        return "RequirementDetails{" +
                "reqCrtUsr='" + reqCrtUsr + '\'' +
                ", reqStatus='" + reqStatus + '\'' +
                ", reqTypCd='" + reqTypCd + '\'' +
                ", reqCrtDt='" + reqCrtDt + '\'' +
                ", reqIDcode='" + reqIDcode + '\'' +
                ", reqSeqNum='" + reqSeqNum + '\'' +
                '}';
    }
}
