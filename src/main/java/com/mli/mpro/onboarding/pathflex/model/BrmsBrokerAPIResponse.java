package com.mli.mpro.onboarding.pathflex.model;

import com.mli.mpro.onboarding.pathflex.errors.ErrorResponse;

import java.util.List;
import java.util.Set;

public class BrmsBrokerAPIResponse {

    private BrmsBrokerResponse brmsBrokerResponse;

    private String msgCode;

    Set<ErrorResponse> errors;

    private String error;

    public BrmsBrokerAPIResponse(){
    }

    public BrmsBrokerAPIResponse(String msgCode, String error) {
        this.msgCode = msgCode;
        this.error = error;
    }

    public BrmsBrokerResponse getBrmsBrokerResponse() {
        return brmsBrokerResponse;
    }

    public void setBrmsBrokerResponse(BrmsBrokerResponse brmsBrokerResponse) {
        this.brmsBrokerResponse = brmsBrokerResponse;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public Set<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorResponse> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "BrmsBrokerAPIResponse{" +
                "brmsBrokerResponse=" + brmsBrokerResponse +
                ", msgCode='" + msgCode + '\'' +
                ", errors=" + errors +
                ", error=" + error +
                '}';
    }
}
