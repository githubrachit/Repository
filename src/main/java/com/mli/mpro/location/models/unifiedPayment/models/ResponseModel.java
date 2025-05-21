package com.mli.mpro.location.models.unifiedPayment.models;

import com.mli.mpro.utils.Utility;

public class ResponseModel {
    private ResultInfoModel resultInfoModel;
    private Object messageModel;
    private Payload payload;
    private String timestamp;
    private String message;
    private Object detail;
    private ErrorResponse errorResponse;

    public ResultInfoModel getResultInfoModel() {
        return resultInfoModel;
    }

    public void setResultInfoModel(ResultInfoModel resultInfoModel) {
        this.resultInfoModel = resultInfoModel;
    }

    public Object getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(Object messageModel) {
        this.messageModel = messageModel;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponseModel{" +
                "resultInfoModel=" + resultInfoModel +
                ", messageModel=" + messageModel +
                ", payload=" + payload +
                ", timestamp='" + timestamp + '\'' +
                ", message='" + message + '\'' +
                ", detail=" + detail +
                ", errorResponse=" + errorResponse +
                '}';
    }
}