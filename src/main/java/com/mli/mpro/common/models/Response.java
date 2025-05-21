package com.mli.mpro.common.models;

import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.utils.Utility;

public class Response {
    private Metadata metadata;
    private ResponseData responseData;
    private ErrorResponse errorResponse;
    private MsgInfo msgInfo;

    public ErrorResponse getErrorResponse() {
	return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
	this.errorResponse = errorResponse;
    }

    public Response() {

    }

    public Response(Metadata metadata, ResponseData responseData) {
	super();
	this.metadata = metadata;
	this.responseData = responseData;
    }

    public Response(ResponseData responseData) {
	this.responseData = responseData;
    }

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

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Response [metadata=" + metadata + ", responseData=" + responseData + ", errorResponse=" + errorResponse + "]";
    }
}
