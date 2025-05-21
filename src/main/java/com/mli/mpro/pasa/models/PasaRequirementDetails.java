package com.mli.mpro.pasa.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pasaRequirementDetails")
public class PasaRequirementDetails {


    @JsonProperty("clientId")
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "PasaRequirementDetails{" +
                "clientId=" + clientId +
                '}';
    }

}
