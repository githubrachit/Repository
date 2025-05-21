package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;

public class LocationTransformationOutputResponse {
    private LocationTranformationResponse response;

    public LocationTransformationOutputResponse() {

    }

    public LocationTransformationOutputResponse(LocationTranformationResponse response) {
        super();
        this.response = response;
    }

    public LocationTranformationResponse getResponse() {
        return response;
    }

    public void setResponse(LocationTranformationResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "LocationTransformationOutputResponse{" +
                "response=" + response +
                '}';
    }
}
