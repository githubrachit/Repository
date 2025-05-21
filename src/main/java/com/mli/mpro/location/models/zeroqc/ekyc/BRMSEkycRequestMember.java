package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycRequestMember {
    @JsonProperty("Type")
    private String type;
    private String value;
    @JsonProperty("Name")
    private String name;

    //setters and getters
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //toString
    @Override
    public String toString() {
        return "EkycMember [type=" + type + ", value=" + value + ", name=" + name + "]";
    }

}
