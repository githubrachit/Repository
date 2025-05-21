package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

public class ResponseData {
    private Payload payload;
    public static class Builder {
        private Payload payload;

        public Builder payload(Payload payload) {
            this.payload = payload;
            return this;
        }

        public ResponseData build() {
            ResponseData responseData = new ResponseData();
            responseData.payload = this.payload;
            return responseData;
        }
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
        return "RequestData{" +
                "payload=" + payload +
                '}';
    }
}
