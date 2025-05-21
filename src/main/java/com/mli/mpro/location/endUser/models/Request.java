package com.mli.mpro.location.endUser.models;

import com.mli.mpro.utils.Utility;

public class Request {
   private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request{" +
                "data=" + data +
                '}';
    }
}
