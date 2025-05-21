package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SplittingorReplacementResponsePayload {

    @JsonProperty("replacementSale")
    private String replacementSale;

    @JsonProperty("splitFlag")
    private String splitFlag;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public String getReplacementSale() {
        return replacementSale;
    }

    public void setReplacementSale(String replacementSale) {
        this.replacementSale = replacementSale;
    }

    public String getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(String splitFlag) {
        this.splitFlag = splitFlag;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SplittingorReplacementResponsePayload [replacementSale=" + replacementSale + ", splitFlag=" + splitFlag
                + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
    }
}
