package com.mli.mpro.location.models.mfaOauthAgent360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private Date timestamp;

    private int errorCode;

    private String errorDescription;

    private List<String> errorMessages;

    public ErrorResponse() {

    }

    public ErrorResponse(Date timestamp, int errorCode, String errorDescription, List<String> errorMessages) {
        super();
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorMessages = errorMessages;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                ", errorMessages=" + errorMessages +
                '}';
    }
}