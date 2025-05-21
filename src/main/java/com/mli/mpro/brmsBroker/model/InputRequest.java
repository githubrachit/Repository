package com.mli.mpro.brmsBroker.model;

import com.mli.mpro.utils.Utility;

public class InputRequest {

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
        return "InputRequest{" +
                "request=" + request +
                '}';
    }
}
