package com.mli.mpro.location.models.mfaOauthAgent360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.agent.models.Payload;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {

    private String token;
    private Payload agent360Response;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Payload getAgent360Response() {
        return agent360Response;
    }

    public void setAgent360Response(Payload agent360Response) {
        this.agent360Response = agent360Response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponseData{" +
                "token='" + token + '\'' +
                ", agent360Response=" + agent360Response +
                '}';
    }
}
