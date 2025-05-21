package com.mli.mpro.location.labslist.models;

import com.mli.mpro.utils.Utility;

public class LabslistSoaResponse {
	
    private LabslistSoaResult result;

    public LabslistSoaResult getResult() {
        return result;
    }

    public void setResult(LabslistSoaResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LabslistSoaResponse{" +
                "result=" + result +
                '}';
    }
}
