package com.mli.mpro.location.labslist.models;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class SoaResponse {
	
    private Header header;
    private MsgInfo msgInfo;
    private List<ResponsePayload> payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public List<ResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ResponsePayload> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaResponse{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
