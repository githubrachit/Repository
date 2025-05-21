package com.mli.mpro.location.endUser.models;

import com.mli.mpro.utils.Utility;

public class SoaRequest {
    private Data request;
    public Data getRequest() {
        return request;
    }

    public void setRequest(Data request) {
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
