package com.mli.mpro.location.models;

import java.util.List;

public class IIBResponsePayload {
    private List<IIBExistingPolicyDetails> iIBExistingPolicyDetails;

    private String errorMsg;

    public IIBResponsePayload(){
    }

    public IIBResponsePayload(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<IIBExistingPolicyDetails> getiIBExistingPolicyDetails() {
        return iIBExistingPolicyDetails;
    }

    public void setiIBExistingPolicyDetails(List<IIBExistingPolicyDetails> iIBExistingPolicyDetails) {
        this.iIBExistingPolicyDetails = iIBExistingPolicyDetails;
    }

    @Override
    public String toString() {
        return "IIBResponsePayload{" +
                "iIBExistingPolicyDetails=" + iIBExistingPolicyDetails +
                "errorMsg=" + errorMsg +
                '}';
    }
}
