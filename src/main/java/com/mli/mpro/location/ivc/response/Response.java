package com.mli.mpro.location.ivc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Response {
    @JsonProperty("status")
    private String status;
    @JsonProperty("urlSent")
    private String urlSent;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlSent() {
        return urlSent;
    }

    public void setUrlSent(String urlSent) {
        this.urlSent = urlSent;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "OutputResponse{" +
                "status='" + status + '\'' +
                ", urlSent='" + urlSent + '\'' +
                '}';
    }
}
