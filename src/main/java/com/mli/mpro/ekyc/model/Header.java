package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"soaCorrelationId","soaAppId"})
public class Header {

    @JsonProperty("soaAppId")
    private String soaAppId;
    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;

    public Header(String soaAppId, String soaCorrelationId) {
        super();
        this.soaAppId = soaAppId;
        this.soaCorrelationId = soaCorrelationId;
    }

    public Header() { super();
    }

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Header{" +
                "soaAppId='" + soaAppId + '\'' +
                ", soaCorrelationId='" + soaCorrelationId + '\'' +
                '}';
    }
}

