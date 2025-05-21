package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class NpsApiResponse {
    @JsonProperty("serviceStatus")
    private String serviceStatus;

    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    public NpsApiResponse() {
        //default constructor
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "NpsApiResponse{" +
                "serviceStatus='" + serviceStatus + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                '}';
    }
}
