package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class CkycDataResponse {

    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("parentCompany")
    private String parentCompany;
    @JsonProperty("downloadFromCkycResponseDetails")
    private DownloadFromCkycResponseDetails downloadFromCkycResponseDetails;

    public CkycDataResponse() {
    }

    public CkycDataResponse(String requestId, String parentCompany, DownloadFromCkycResponseDetails downloadFromCkycResponseDetails) {
        this.requestId = requestId;
        this.parentCompany = parentCompany;
        this.downloadFromCkycResponseDetails = downloadFromCkycResponseDetails;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public CkycDataResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    @JsonProperty("parentCompany")
    public String getParentCompany() {
        return parentCompany;
    }

    @JsonProperty("parentCompany")
    public CkycDataResponse setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
        return this;
    }

    @JsonProperty("downloadFromCkycResponseDetails")
    public DownloadFromCkycResponseDetails getDownloadFromCkycResponseDetails() {
        return downloadFromCkycResponseDetails;
    }

    @JsonProperty("downloadFromCkycResponseDetails")
    public CkycDataResponse setDownloadFromCkycResponseDetails(DownloadFromCkycResponseDetails downloadFromCkycResponseDetails) {
        this.downloadFromCkycResponseDetails = downloadFromCkycResponseDetails;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", CkycDataResponse.class.getSimpleName() + "[", "]")
                .add("requestId='" + requestId + "'")
                .add("parentCompany='" + parentCompany + "'")
                .add("downloadFromCkycResponseDetails=" + downloadFromCkycResponseDetails)
                .toString();
    }
}
