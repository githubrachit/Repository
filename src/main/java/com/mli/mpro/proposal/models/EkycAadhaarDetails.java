package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class EkycAadhaarDetails {

    @JsonProperty("proposerAadhaarPdf")
    private  String proposerAadhaarPdf;
    @JsonProperty("proposerAadhaarImage")
    private  String proposerAadhaarImage;

    public EkycAadhaarDetails() {
    }

    public EkycAadhaarDetails(String proposerAadhaarPdf, String proposerAadhaarImage) {
        this.proposerAadhaarPdf = proposerAadhaarPdf;
        this.proposerAadhaarImage = proposerAadhaarImage;
    }

    public String getProposerAadhaarPdf() {
        return proposerAadhaarPdf;
    }

    public void setProposerAadhaarPdf(String proposerAadhaarPdf) {
        this.proposerAadhaarPdf = proposerAadhaarPdf;
    }

    public String getProposerAadhaarImage() {
        return proposerAadhaarImage;
    }

    public void setProposerAadhaarImage(String proposerAadhaarImage) {
        this.proposerAadhaarImage = proposerAadhaarImage;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "EkycAadhaarDetails{" +
                "proposerAadhaarPdf='" + proposerAadhaarPdf + '\'' +
                ", proposerAadhaarImage='" + proposerAadhaarImage + '\'' +
                '}';
    }
}
