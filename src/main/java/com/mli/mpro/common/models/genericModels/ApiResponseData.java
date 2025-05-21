package com.mli.mpro.common.models.genericModels;

import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.common.models.Metadata;

public class ApiResponseData<T> {
    private Metadata metadata;
    private MsgInfo msgInfo;
    private T payload;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApiResponseData{" +
                "metadata=" + metadata +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
