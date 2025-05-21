package com.mli.mpro.sellerSignature.sellerRequest;

import com.mli.mpro.utils.Utility;

public class SellerRequest {

    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SellerRequest{" +
                "request=" + request +
                '}';
    }
}
