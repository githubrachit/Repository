package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

public class RequestData {
    private Payload payload;

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
