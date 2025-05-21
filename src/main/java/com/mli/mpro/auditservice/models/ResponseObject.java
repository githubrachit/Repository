package com.mli.mpro.auditservice.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mli.mpro.utils.Utility;

public class ResponseObject {
    @JsonIgnore
    private Map<String, Object> response = new HashMap<String, Object>();

    public ResponseObject() {
	super();
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
	return this.response;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
	this.response.put(name, value);

    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ResponseObject [response=" + response + "]";
    }

}

