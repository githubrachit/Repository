package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

public class DocUploadAPIResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public DocUploadAPIResponse setResponse(Response response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "DocUploadAPIResponse{" +
                "response=" + response +
                '}';
    }
}
