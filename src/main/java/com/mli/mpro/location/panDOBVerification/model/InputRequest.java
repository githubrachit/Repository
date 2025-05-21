package com.mli.mpro.location.panDOBVerification.model;

public class InputRequest {
	
    private Data data;

    public InputRequest() {
    }

    public InputRequest(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InputRequest{" +
                "data=" + data +
                '}';
    }
}
