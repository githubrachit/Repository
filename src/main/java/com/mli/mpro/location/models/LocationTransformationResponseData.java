package com.mli.mpro.location.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

public class LocationTransformationResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocationTransformationResponsePayload responsePayload;

    public LocationTransformationResponseData() {

    }

    public LocationTransformationResponsePayload getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(LocationTransformationResponsePayload responsePayload) {
        this.responsePayload = responsePayload;
    }

    public LocationTransformationResponseData(LocationTransformationResponsePayload responsePayload) {
        super();
        this.responsePayload = responsePayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTransformationResponseData{" +
                "responsePayload=" + responsePayload +
                '}';
    }
}