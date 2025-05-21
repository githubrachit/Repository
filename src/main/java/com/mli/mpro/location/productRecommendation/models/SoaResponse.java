package com.mli.mpro.location.productRecommendation.models;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.utils.Utility;

public class SoaResponse {
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
        return "SoaResponse{" +
                "response=" + response +
                '}';
    }
}
