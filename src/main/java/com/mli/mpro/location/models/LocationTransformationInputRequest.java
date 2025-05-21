package com.mli.mpro.location.models;


import com.mli.mpro.utils.Utility;

public class LocationTransformationInputRequest {
    private LocationTransformationRequest request;
    private String data;

    public LocationTransformationInputRequest() {

    }

    public LocationTransformationInputRequest(LocationTransformationRequest request) {
        super();
        this.request = request;
    }

    public LocationTransformationRequest getRequest() {
        return request;
    }

    public void setRequest(LocationTransformationRequest request) {
        this.request = request;
    }


    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTransformationInputRequest{" +
                "request=" + request +
                ", data='" + data + '\'' +
                '}';
    }
}
