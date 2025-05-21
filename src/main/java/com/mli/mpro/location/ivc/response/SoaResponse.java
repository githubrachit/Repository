package com.mli.mpro.location.ivc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.brmsBroker.model.InputResponse.MsgInfo;
import com.mli.mpro.utils.Utility;

public class SoaResponse {
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;
    @JsonProperty("payload")
    private SoaResponsePayload payload;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public SoaResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(SoaResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaResponse{" +
                "msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}

