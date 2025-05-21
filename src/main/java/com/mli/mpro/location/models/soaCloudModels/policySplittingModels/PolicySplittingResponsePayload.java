package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.mli.mpro.utils.Utility;

public class PolicySplittingResponsePayload {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SplittingOutputResponse [response=" + response + "]";
    }
}
