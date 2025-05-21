package com.mli.mpro.onboarding.partner.model;

import com.mli.mpro.onboarding.model.MsgInfo;

public class ProposalNumberAPIResponse {

    private MsgInfo msgInfo;
    private ProposalResponseData responseData;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public ProposalResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ProposalResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "ProposalNumberAPIResponse{" +
                "msgInfo=" + msgInfo +
                ", responseData=" + responseData +
                '}';
    }
}
