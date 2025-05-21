
package com.mli.mpro.location.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"requestPayload"})
public class LocationTransformationRequestData {

    @JsonProperty("requestPayload")
    private LocationTransformationRequestPayload requestPayload;

    /**
     * No args constructor for use in serialization
     */
    public LocationTransformationRequestData() {
    }

    /**
     * @param requestPayload
     */
    public LocationTransformationRequestData(LocationTransformationRequestPayload requestPayload) {
        super();
        this.requestPayload = requestPayload;
    }

    @JsonProperty("requestPayload")
    public LocationTransformationRequestPayload getRequestPayload() {
        return requestPayload;
    }

    @JsonProperty("requestPayload")
    public void setRequestPayload(LocationTransformationRequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTransformationRequestData{" +
                "requestPayload=" + requestPayload +
                '}';
    }
}
