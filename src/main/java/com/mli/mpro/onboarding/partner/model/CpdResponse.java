package com.mli.mpro.onboarding.partner.model;


public class CpdResponse {

    private com.mli.mpro.onboarding.model.MsgInfo msgInfo;
    private CpdSoaResponsePayload payload;

    public com.mli.mpro.onboarding.model.MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(com.mli.mpro.onboarding.model.MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }


    public CpdSoaResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(CpdSoaResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "CpdResponse{" +
                "msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
