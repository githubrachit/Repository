package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgentResponse {
    @JsonProperty("response") 
    public Response getResponse() { 
		 return this.response; } 
    public void setResponse(Response response) { 
		 this.response = response; } 
    Response response;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgentResponse{" +
                "response=" + response +
                '}';
    }
}
