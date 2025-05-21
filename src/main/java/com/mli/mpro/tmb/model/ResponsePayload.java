
package com.mli.mpro.tmb.model;


public class ResponsePayload {
    private String msgInfo;
    private String transactionId;
    private String eventId;
    private boolean isEventCompleted;
    private String ekyc;

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isEventCompleted() {
        return isEventCompleted;
    }

    public void setEventCompleted(boolean eventCompleted) {
        isEventCompleted = eventCompleted;
    }

    public String getEkyc() {
        return ekyc;
    }

    public void setEkyc(String ekyc) {
        this.ekyc = ekyc;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "msgInfo='" + msgInfo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", isEventCompleted=" + isEventCompleted +
                ", ekyc='" + ekyc + '\'' +
                '}';
    }
}
