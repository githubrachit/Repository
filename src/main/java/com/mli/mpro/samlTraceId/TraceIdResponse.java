package com.mli.mpro.samlTraceId;

import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.configuration.models.ErrorResponse;

public class TraceIdResponse {
    private Metadata metadata;
    private ResponseData responseData;
    private ErrorResponse errorResponse;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

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
        return "TraceIdResponse{" +
                "metadata=" + metadata +
                ", responseData=" + responseData +
                ", errorResponse=" + errorResponse +
                '}';
    }
}
