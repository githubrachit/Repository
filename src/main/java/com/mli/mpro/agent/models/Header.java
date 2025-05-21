package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Header{
    @JsonProperty("soaAppId") 
    public String getSoaAppId() { 
		 return this.soaAppId; } 
    public void setSoaAppId(String soaAppId) { 
		 this.soaAppId = soaAppId; } 
    String soaAppId;
    @JsonProperty("soaCorrelationId") 
    public String getSoaCorrelationId() { 
		 return this.soaCorrelationId; } 
    public void setSoaCorrelationId(String soaCorrelationId) { 
		 this.soaCorrelationId = soaCorrelationId; } 
    String soaCorrelationId;

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
