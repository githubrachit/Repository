package com.mli.mpro.bankdetails.model;

import com.mli.mpro.agent.models.MsgInfo;

public class SOABankResponse {

    private SOABankHeader header;
    private MsgInfo msgInfo;
    private BankResponsePayload payload;

    public SOABankHeader getHeader() {
        return header;
    }
    public void setHeader(SOABankHeader header) {
        this.header = header;
    }
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }
    public BankResponsePayload getPayload() {
        return payload;
    }
    public void setPayload(BankResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "SOABankResponse [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
    }
}