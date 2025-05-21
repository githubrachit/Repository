package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.utils.Utility;

public class Result {
    private ResponsePayload response;

    public ResponsePayload getResponse() {
        return response;
    }

    public void setResponse(ResponsePayload response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Result{" +
                "response=" + response +
                '}';
    }
}
