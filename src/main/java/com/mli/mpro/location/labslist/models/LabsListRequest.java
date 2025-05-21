package com.mli.mpro.location.labslist.models;

import com.mli.mpro.utils.Utility;

public class LabsListRequest {

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
        return "LabsListRequest{" +
                "request=" + request +
                '}';
    }
}
