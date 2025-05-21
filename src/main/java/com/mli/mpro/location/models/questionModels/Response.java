package com.mli.mpro.location.models.questionModels;

import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.common.models.MsgInfo;

public class Response <T>{
    private Metadata metadata;
    private MsgInfo msgInfo;
    public T responseData;

    public Response() {
    }

    public Response(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Response(Metadata metadata, MsgInfo msgInfo, T responseData) {
        this.metadata = metadata;
        this.msgInfo = msgInfo;
        this.responseData = responseData;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public T getResponseData() {
        return responseData;
    }

    @Override
    public String toString() {
        return "Response{" +
                "metadata=" + metadata +
                ", msgInfo=" + msgInfo +
                ", responseData=" + responseData +
                '}';
    }
}
