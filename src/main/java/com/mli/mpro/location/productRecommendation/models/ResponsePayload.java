package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.utils.Utility;

public class ResponsePayload {
    private Header header;
    private ResponseMsgInfo msginfo;
    private Payload payload;

    public Header getHeader() {
        return header;
    }

    public ResponseMsgInfo getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(ResponseMsgInfo msginfo) {
        this.msginfo = msginfo;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponsePayload{" +
                "header=" + header +
                ", msgInfo=" + msginfo +
                ", payload=" + payload +
                '}';
    }
}
