package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

public class Request {
    private MetaData metaData;
    private RequestData requestData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }
    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request [metaData=" + metaData + ", requestData=" + requestData + "]";
    }
}
