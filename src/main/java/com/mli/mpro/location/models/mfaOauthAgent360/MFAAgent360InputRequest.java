package com.mli.mpro.location.models.mfaOauthAgent360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MFAAgent360InputRequest {

    private MFARequest request;

    public MFARequest getRequest() {
        return request;
    }

    public void setRequest(MFARequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MFAAgent360InputRequest{" +
                "request=" + request +
                '}';
    }
}
