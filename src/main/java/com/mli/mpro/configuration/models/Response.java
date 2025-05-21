package com.mli.mpro.configuration.models;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;

import java.util.List;

public class Response {
    private Header header;
    private ResponseMsgInfo msginfo;

    private List<FeatureFlag> payload;

    public ResponseMsgInfo getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(ResponseMsgInfo msginfo) {
        this.msginfo = msginfo;
    }

    public List<FeatureFlag> getPayload() {
        return payload;
    }

    public void setPayload(List<FeatureFlag> payload) {
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header=" + header +
                ", msginfo=" + msginfo +
                ", payload=" + payload +
                '}';
    }
}
