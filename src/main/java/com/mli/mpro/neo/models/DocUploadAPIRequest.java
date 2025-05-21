package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * The type Doc upload api request.
 */
public class DocUploadAPIRequest {

    private Request request;

    /**
     * Gets request.
     *
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Sets request.
     *
     * @param request the request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * With request doc upload api request.
     *
     * @param request the request
     * @return the doc upload api request
     */
    public DocUploadAPIRequest withRequest(Request request) {
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
