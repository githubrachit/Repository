package com.mli.mpro.configuration.models;



public class ApiResponse {
    private MsgInfo msginfo;
    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApiResponse(int msgCode, String msg, String msgDescription) {
        this.msginfo = new MsgInfo(msgCode, msg, msgDescription);
    }

    public MsgInfo getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(MsgInfo msginfo) {
        this.msginfo = msginfo;
    }
}
