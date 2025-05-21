package com.mli.mpro.location.training.model;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

public class SaoResponse {

    private Header header;

    private MsgInfo msginfo;

    private SaoResponsePayload payload;

    @Override
    public String toString() {
        return "SaoResponse [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MsgInfo getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(MsgInfo msginfo) {
        this.msginfo = msginfo;
    }

    public SaoResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(SaoResponsePayload payload) {
        this.payload = payload;
    }
}
