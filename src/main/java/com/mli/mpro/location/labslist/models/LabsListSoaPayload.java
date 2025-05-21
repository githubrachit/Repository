package com.mli.mpro.location.labslist.models;

import com.mli.mpro.utils.Utility;

public class LabsListSoaPayload {
	
    private PayloadRequest request;

    public PayloadRequest getRequest() {
        return request;
    }

    public void setRequest(PayloadRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LabsListSoaPayload{" +
                "request=" + request +
                '}';
    }
}
