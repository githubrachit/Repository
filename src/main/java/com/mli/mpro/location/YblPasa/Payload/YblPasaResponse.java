package com.mli.mpro.location.YblPasa.Payload;

import com.mli.mpro.agentSelfIdentifiedSkip.Header;
import com.mli.mpro.nps.model.MsgInfo;

public class YblPasaResponse {
    private Header header;
    private MsgInfo msgInfo;
    private YblResponsePayload payload;

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

    public YblResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(YblResponsePayload payload) {
        this.payload = payload;
    }
}
