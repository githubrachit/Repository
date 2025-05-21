package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class DownloadFromCkycResponseDetails {

    private CkycDownloadResponseDetail ckycDownloadResponseDetail;

    public CkycDownloadResponseDetail getCkycDownloadResponseDetail() {
        return ckycDownloadResponseDetail;
    }

    public void setCkycDownloadResponseDetail(CkycDownloadResponseDetail ckycDownloadResponseDetail) {
        this.ckycDownloadResponseDetail = ckycDownloadResponseDetail;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", DownloadFromCkycResponseDetails.class.getSimpleName() + "[", "]")
                .add("ckycDownloadResponseDetail=" + ckycDownloadResponseDetail)
                .toString();
    }
}
