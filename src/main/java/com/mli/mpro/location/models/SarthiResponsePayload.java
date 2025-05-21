package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SarthiResponsePayload {

    private boolean isSarthiAvailable;
    @Sensitive(MaskType.NAME)
    private String sarthiName;
    @Sensitive(MaskType.MOBILE)
    private String sarthiContactNumber;
    private String errorMsg;

    public SarthiResponsePayload(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public SarthiResponsePayload(){}

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public boolean isSarthiAvailable() {
        return isSarthiAvailable;
    }

    public void setSarthiAvailable(boolean sarthiAvailable) {
        isSarthiAvailable = sarthiAvailable;
    }

    public String getSarthiName() {
        return sarthiName;
    }

    public void setSarthiName(String sarthiName) {
        this.sarthiName = sarthiName;
    }

    public String getSarthiContactNumber() {
        return sarthiContactNumber;
    }

    public void setSarthiContactNumber(String sarthiContactNumber) {
        this.sarthiContactNumber = sarthiContactNumber;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SarthiResponsePayload{" +
                "isSarthiAvailable=" + isSarthiAvailable +
                ", sarthiName='" + sarthiName + '\'' +
                ", sarthiContactNumber='" + sarthiContactNumber + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
