package com.mli.mpro.configuration.models;

public class OutputResponse {
private Result result;

    public Result getResult() {
        return result;
    }

    public OutputResponse() {
    }

    public OutputResponse(Result result) {
        this.result = result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OutputResponse{" +
                "result=" + result +
                '}';
    }
}
