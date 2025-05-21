package com.mli.mpro.location.models.unifiedPayment.models;

import com.mli.mpro.utils.Utility;

public class ResultInfoModel {
    private String resultCode;
    private String resultStatus;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResultInfoModel{" +
                "resultCode='" + resultCode + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                '}';
    }
}