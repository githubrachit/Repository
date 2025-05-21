package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class LEProductResponse {
    private List<LEProductList> result;

    public List<LEProductList> getResult() {
        return result;
    }

    public void setResult(List<LEProductList> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LEProductResponse{" +
                "result=" + result +
                '}';
    }
}
