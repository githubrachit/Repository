package com.mli.mpro.neo.models.attachment;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class Request {

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public Request setPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("payload = " + payload)
                .toString();
    }
}
