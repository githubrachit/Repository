package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.utils.Utility;

public class SoaRequest {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaRequest{" +
                "request=" + request +
                '}';
    }
}
