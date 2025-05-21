package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycRequestInput extends BRMSEkycRequestOutput {
    @JsonProperty("Member")
    private List<BRMSEkycRequestMember> member;

    //setters and getters
    public List<BRMSEkycRequestMember> getMember() {
        return member;
    }
    public void setMember(List<BRMSEkycRequestMember> member) {
        this.member = member;
    }
    //toString
    @Override
    public String toString() {
        return "EkycInput [member=" + member + "]";
    }
}
