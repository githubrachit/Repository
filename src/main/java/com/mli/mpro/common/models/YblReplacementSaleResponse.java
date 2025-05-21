package com.mli.mpro.common.models;


import java.util.List;

public class YblReplacementSaleResponse {
    private List<YblPolicy> data;
    private Meta meta;

    public List<YblPolicy> getData() {
        return data;
    }

    public void setData(List<YblPolicy> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "YblReplacementSaleResponse{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }
}
