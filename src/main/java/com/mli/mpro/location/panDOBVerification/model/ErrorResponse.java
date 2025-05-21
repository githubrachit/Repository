package com.mli.mpro.location.panDOBVerification.model;

public class ErrorResponse {
    private Error error;

    public ErrorResponse() {
    }

    public ErrorResponse(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error=" + error +
                '}';
    }
}
