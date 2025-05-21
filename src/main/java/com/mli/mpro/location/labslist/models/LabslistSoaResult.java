package com.mli.mpro.location.labslist.models;

import com.mli.mpro.utils.Utility;

public class LabslistSoaResult {
	
    private SoaResponse response;

    public SoaResponse getResponse() {
        return response;
    }

    public void setResponse(SoaResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
    	
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LabslistSoaResult{" +
                "response=" + response +
                '}';
    }
}
