package com.mli.mpro.location.productRecommendation.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class RequestData {
    @JsonProperty("data")
    private Payload data;
    @JsonProperty("data")
    public Payload getData() {
        return data;
    }
    @JsonProperty("data")
    public void setData(Payload data) {
        this.data = data;
    }

    @Override
    public String toString() { if(Utility.isCalledFromLogs(Thread.currentThread())){
        return Utility.toString(this);
    }
        return "InputRequest{" +
                "data=" + data +
                '}';
    }
}
