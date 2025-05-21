package com.mli.mpro.sellerSignature.sellerResponse;

import com.mli.mpro.utils.Utility;

public class SellerResponse {

   private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SellerResponse{" +
                "response=" + response +
                '}';
    }
}
