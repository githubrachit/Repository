package com.mli.mpro.neo.models.attachment;

import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.StringJoiner;

public class GetAttachmentApiResponse {

    private Response response;

    public GetAttachmentApiResponse() {
    }

    public GetAttachmentApiResponse(Response response) {
        this.response = response;
    }

    public GetAttachmentApiResponse(List<Object> messages) {
        this.response = new Response(messages);
    }

    public Response getResponse() {
        return response;
    }

    public GetAttachmentApiResponse setResponse(Response response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("response = " + response)
                .toString();
    }
}
