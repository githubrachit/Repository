package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

public class OtpOutputResponse {
    private Response response;
    private ErrorResponse errorResponse;
    private int statusCode;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static class Builder {
        private Response response;
        private ErrorResponse errorResponse;
        private int statusCode;

        public Builder response(Response response) {
            this.response = response;
            return this;
        }

        public Builder errorResponse(ErrorResponse errorResponse) {
            this.errorResponse = errorResponse;
            return this;
        }
        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public OtpOutputResponse build() {
            OtpOutputResponse outputResponse = new OtpOutputResponse();
            outputResponse.response = this.response;
            outputResponse.errorResponse = this.errorResponse;
            outputResponse.statusCode = this.statusCode;
            return outputResponse;
        }
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OtpOutputResponse{" +
                "response=" + response +
                ", errorResponse=" + errorResponse +
                ", statusCode=" + statusCode +
                '}';
    }
}
