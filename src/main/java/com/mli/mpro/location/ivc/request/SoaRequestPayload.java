package com.mli.mpro.location.ivc.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaRequestPayload {
    @JsonProperty("proposalNo")
    private String proposalNo;
    @JsonProperty("retry")
    private boolean retry;
    @JsonProperty("ivcToIvc")
    private String ivcToIvc;
    @JsonProperty("ivcToHybrid")
    private String ivcToHybrid;
    @JsonProperty("hybridToIvc")
    private String hybridToIvc;

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public String getIvcToIvc() {
        return ivcToIvc;
    }

    public void setIvcToIvc(String ivcToIvc) {
        this.ivcToIvc = ivcToIvc;
    }

    public String getIvcToHybrid() {
        return ivcToHybrid;
    }

    public void setIvcToHybrid(String ivcToHybrid) {
        this.ivcToHybrid = ivcToHybrid;
    }

    public String getHybridToIvc() {
        return hybridToIvc;
    }

    public void setHybridToIvc(String hybridToIvc) {
        this.hybridToIvc = hybridToIvc;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaRequestPayload{" +
                "proposalNo='" + proposalNo + '\'' +
                ", retry=" + retry +
                ", ivcToIvc='" + ivcToIvc + '\'' +
                ", ivcToHybrid='" + ivcToHybrid + '\'' +
                ", hybridToIvc='" + hybridToIvc + '\'' +
                '}';
    }
}
