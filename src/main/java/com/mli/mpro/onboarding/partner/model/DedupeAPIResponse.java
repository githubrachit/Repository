package com.mli.mpro.onboarding.partner.model;

public class DedupeAPIResponse {

    private com.mli.mpro.onboarding.model.MsgInfo msgInfo;

    private ResponseData responseData;

    public com.mli.mpro.onboarding.model.MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(com.mli.mpro.onboarding.model.MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "DedupeAPIResponse{" +
                "msgInfo=" + msgInfo +
                ", responseData=" + responseData +
                '}';
    }
}
