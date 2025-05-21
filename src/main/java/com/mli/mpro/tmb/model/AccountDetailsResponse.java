package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountDetailsResponse {

    @JsonProperty("statuscode")
    private String statuscode = "";
    @JsonProperty("status")
    private String status = "";
    private List<AccountDetail> response;

    public List<AccountDetail> getResponse() {
        return response;
    }

    public void setResponse(List<AccountDetail> response) {
        this.response = response;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountDetailsResponse{" +
                "statuscode='" + statuscode + '\'' +
                ", status='" + status + '\'' +
                ", response=" + response +
                '}';
    }
}
