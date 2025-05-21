package com.mli.mpro.auditservice.models;

import com.mli.mpro.utils.Utility;

public class Response {
    private Metadata metadata;
    private ResponseData responseData;
    private ErrorResponse errorResponse;

    
	public Response() {
		super();
	}

	public Metadata getMetadata() {
	return metadata;
    }

    /**
     * @return the errorResonse
     */
    public ErrorResponse getErrorResponse() {
	return errorResponse;
    }

    /**
     * @param errorResonse the errorResonse to set
     */
    public void setErrorResponse(ErrorResponse errorResonse) {
	this.errorResponse = errorResonse;
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

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Response [metadata=" + metadata + ", responseData=" + responseData + "]";
    }
}
