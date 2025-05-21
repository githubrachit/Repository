package com.mli.mpro.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class NpsResponsePayload {

    @JsonProperty("serviceStatus")
    private String serviceStatus;

    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    @JsonProperty("dataReceived")
    private String dataReceived;
    @JsonProperty("pranDuplicateFlag")
    private boolean pranDuplicateFlag;

    public boolean getPranDuplicateFlag() {
        return pranDuplicateFlag;
    }

    public void setPranDuplicateFlag(boolean pranDuplicateFlag) {
        this.pranDuplicateFlag = pranDuplicateFlag;
    }


    public NpsResponsePayload() {
    }

    public String getDataReceived() {
        return dataReceived;
    }

    public void setDataReceived(String dataReceived) {
        this.dataReceived = dataReceived;
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
        return "NpsResponsePayload{" +
                "serviceStatus='" + serviceStatus + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                ", dataReceived='" + dataReceived + '\'' +
                ", pranDuplicateFlag=" + pranDuplicateFlag +
                '}';
    }
}
