package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

public class Response {
    private MetaData metaData;
    private ResponseData responseData;
    public static class Builder {
        private ResponseData responseData;

        public Builder responseData(ResponseData responseData) {
            this.responseData = responseData;
            return this;
        }

        public Response build() {
            Response response = new Response();
            response.responseData = this.responseData;
            return response;
        }
    }
    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Response{" +
                "metaData=" + metaData +
                ", responseData=" + responseData +
                '}';
    }
}
