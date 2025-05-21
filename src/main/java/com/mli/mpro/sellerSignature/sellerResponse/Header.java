package com.mli.mpro.sellerSignature.sellerResponse;

import com.mli.mpro.utils.Utility;

public class Header {

    private String soaAppId;
    private String soaCorrelationId;

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Header{" +
                "soaAppId='" + soaAppId + '\'' +
                ", soaCorrelationId='" + soaCorrelationId + '\'' +
                '}';
    }
}
