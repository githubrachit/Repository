package com.mli.mpro.nps.model.responsePayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Payload {

    @JsonProperty("serviceStatus")
    private String serviceStatus;

    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    public Payload() {
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
        return "Payload{" +
                "serviceStatus='" + serviceStatus + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                '}';
    }
}
