package com.mli.mpro.location.models.mfaOauthAgent360;

import com.mli.mpro.utils.Utility;

public class MFAResponse {

    private ResponseData responseData;
    private ErrorResponse errorResponse;


    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MFAResponse{" +
                "responseData=" + responseData +
                ", errorResponse=" + errorResponse +
                '}';
    }
}
