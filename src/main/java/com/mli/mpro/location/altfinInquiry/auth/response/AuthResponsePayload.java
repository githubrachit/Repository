package com.mli.mpro.location.altfinInquiry.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;


public class AuthResponsePayload {

    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AuthPayload{" +
                "token='" + token + '\'' +
                '}';
    }
}
