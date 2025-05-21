package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceLinkResponse {
    @JsonProperty("statuscode")
    String statusCode;
    String status;
    String desc;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
