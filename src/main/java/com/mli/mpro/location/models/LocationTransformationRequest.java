
package com.mli.mpro.location.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.proposal.models.Metadata;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"metadata", "requestData"})
public class LocationTransformationRequest {

    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("requestData")
    private LocationTransformationRequestData requestData;

    /**
     * No args constructor for use in serialization
     */
    public LocationTransformationRequest() {
    }

    /**
     * @param requestData
     * @param metadata
     */
    public LocationTransformationRequest(Metadata metadata, LocationTransformationRequestData requestData) {
        super();
        this.metadata = metadata;
        this.requestData = requestData;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("requestData")
    public LocationTransformationRequestData getRequestData() {
        return requestData;
    }

    @JsonProperty("requestData")
    public void setRequestData(LocationTransformationRequestData requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTransformationRequest{" +
                "metadata=" + metadata +
                ", requestData=" + requestData +
                '}';
    }
}
