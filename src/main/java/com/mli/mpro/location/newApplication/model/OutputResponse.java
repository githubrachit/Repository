package com.mli.mpro.location.newApplication.model;

public class OutputResponse {
	
    private Result result;

    public OutputResponse() {
    }

    public OutputResponse(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
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
