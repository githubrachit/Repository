package com.mli.mpro.location.models.mfaOauthAgent360;

import com.mli.mpro.utils.Utility;

public class OauthBasedAgent360Response {

    private MFAResponse response;

    public MFAResponse getResponse() {
        return response;
    }

    public void setResponse(MFAResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OauthBasedAgent360Response{" +
                "response=" + response +
                '}';
    }
}
