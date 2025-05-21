package com.mli.mpro.neo.models.attachment;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class GetAttachmentApiRequest {

    private Request request;

    public Request getRequest() {
        return request;
    }

    public GetAttachmentApiRequest setRequest(Request request) {
        this.request = request;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("request = " + request)
                .toString();
    }
}
