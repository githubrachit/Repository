package com.mli.mpro.onboarding.model.datalakesms;

public class Payload {
    private String appAccPass;
    private String appAccId;
    private String encryptionKey;
    private String appId;
    private String msgTo;
    private String msgText;

    public String getAppAccPass() {
        return appAccPass;
    }

    public void setAppAccPass(String appAccPass) {
        this.appAccPass = appAccPass;
    }

    public String getAppAccId() {
        return appAccId;
    }

    public void setAppAccId(String appAccId) {
        this.appAccId = appAccId;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "appAccPass='" + appAccPass + '\'' +
                ", appAccId='" + appAccId + '\'' +
                ", encryptionKey='" + encryptionKey + '\'' +
                ", appId='" + appId + '\'' +
                ", msgTo='" + msgTo + '\'' +
                ", msgText='" + msgText + '\'' +
                '}';
    }
}
