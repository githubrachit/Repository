package com.mli.mpro.onboarding.partner.model;

import com.mli.mpro.onboarding.model.MsgInfo;

public class ReplacementSaleAPIResponse {
    private MsgInfo msgInfo;
    private ReplacementResponseData responseData;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public ReplacementResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ReplacementResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "ReplacementSaleAPIResponse{" +
                "msgInfo=" + msgInfo +
                ", responseData=" + responseData +
                '}';
    }
}
