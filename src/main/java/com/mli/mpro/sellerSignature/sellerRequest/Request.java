package com.mli.mpro.sellerSignature.sellerRequest;

import com.mli.mpro.utils.Utility;

public class Request {

    private Header header;
    private RequestData requestData;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
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
        return "Request{" +
                "header=" + header +
                ", requestData=" + requestData +
                '}';
    }
}
