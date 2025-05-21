package com.mli.mpro.location.models;

import com.mli.mpro.proposal.models.ErrorResponse;
import com.mli.mpro.proposal.models.Metadata;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationTranformationResponse {
    private Metadata metadata;
    private LocationTransformationResponseData responseData;
    private ErrorResponse errorResponse;

    Logger logger = LoggerFactory.getLogger(LocationTranformationResponse.class);

    public LocationTranformationResponse() {

    }

    public LocationTranformationResponse(Metadata metadata, LocationTransformationResponseData responseData) {
        super();
        this.metadata = metadata;
        this.responseData = responseData;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * @return the errorResonse
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    /**
     * @param errorResonse the errorResonse to set
     */
    public void setErrorResponse(ErrorResponse errorResonse) {
        this.errorResponse = errorResonse;
    }

    public void setMetadata(Metadata metadata) {
        logger.debug("Response Metadata {}", metadata);
        this.metadata = metadata;
    }

    public LocationTransformationResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(LocationTransformationResponseData responseData) {
        logger.debug("Response Data being sent {}", responseData);
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTranformationResponse{" +
                "metadata=" + metadata +
                ", responseData=" + responseData +
                ", errorResponse=" + errorResponse +
                '}';
    }
}
