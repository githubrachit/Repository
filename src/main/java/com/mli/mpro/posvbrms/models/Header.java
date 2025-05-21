package com.mli.mpro.posvbrms.models;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.mli.mpro.utils.Utility;


import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "soaCorrelationId", "soaAppId"})
public class Header {


    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;

    @JsonProperty("soaAppId")
    private String soaAppId;

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }


    @Override
    public String toString()
    {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ClassPojo [soaCorrelationId = "+soaCorrelationId+", soaAppId = "+soaAppId+"]";
    }



}
