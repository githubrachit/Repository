package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.utils.Utility;

public class OutputResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OutputResponse{" +
                "result=" + result +
                '}';
    }
}
