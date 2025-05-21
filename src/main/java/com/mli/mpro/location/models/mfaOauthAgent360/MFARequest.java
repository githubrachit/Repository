package com.mli.mpro.location.models.mfaOauthAgent360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MFARequest {

    @JsonProperty("data")
    private MFAPayload payload;

    public MFAPayload getPayload() {
        return payload;
    }

    public void setPayload(MFAPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MFARequest{" +
                "payload=" + payload +
                '}';
    }
}
