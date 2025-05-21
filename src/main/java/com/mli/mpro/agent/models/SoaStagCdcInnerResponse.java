package com.mli.mpro.agent.models;

public class SoaStagCdcInnerResponse {
    private String payload;
    private MsgInfo msgInfo;

    public String getPayload() {
        return payload;
    }

    public SoaStagCdcInnerResponse setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public SoaStagCdcInnerResponse setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
        return this;
    }

    @Override
    public String toString() {
        return "SoaStagCdcInnerResponse{" +
                "payload='" + payload + '\'' +
                ", msgInfo=" + msgInfo +
                '}';
    }
}
