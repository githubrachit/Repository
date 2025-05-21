package com.mli.mpro.location.labslist.models;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.utils.Utility;

public class PayloadRequest {
	
    private Header header;
    private Category category;
    private Payload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PayloadRequest{" +
                "header=" + header +
                ", category=" + category +
                ", payload=" + payload +
                '}';
    }
}
