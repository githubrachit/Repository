package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.mli.mpro.utils.Utility;

public class PolicySplittingRequest {
    private Request request;

    public PolicySplittingRequest() {
    }

    public PolicySplittingRequest(Request request) {
        this.request = request;
    }

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
        return "PolicySplittingRequest [request=" + request + "]";
    }
}
