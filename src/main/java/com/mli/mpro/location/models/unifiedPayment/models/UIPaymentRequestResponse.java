package com.mli.mpro.location.models.unifiedPayment.models;

import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.utils.Utility;

import java.util.List;

import javax.validation.Valid;

public class UIPaymentRequestResponse {
    private Metadata metadata;
    @Valid
    private UIPayload payload;
    private List<UnifiedWebHookRequest> unifiedWebHookRequest;
    private String errorMsg;

    public UIPaymentRequestResponse (String errorMsg){
        this.errorMsg = errorMsg;
    }

    public UIPaymentRequestResponse (){}

    public UIPayload getPayload() {
        return payload;
    }

    public void setPayload(UIPayload payload) {
        this.payload = payload;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<UnifiedWebHookRequest> getUnifiedWebHookRequest() { return unifiedWebHookRequest; }

    public void setUnifiedWebHookRequest(List<UnifiedWebHookRequest> unifiedWebHookRequest) { this.unifiedWebHookRequest = unifiedWebHookRequest;}

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "UIPaymentRequestResponse{" +
                "metadata=" + metadata +
                ", payload=" + payload +
                ", unifiedWebHookRequest=" + unifiedWebHookRequest +
                ", errorMsg=" + errorMsg +
                '}';
    }
}
