package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycRequestOutput {
    private String classIsArray;
    private String className;
    @JsonProperty("Member")
    private List<BRMSEkycRequestMember> member;

    public String getClassIsArray() {
        return classIsArray;
    }

    public void setClassIsArray(String classIsArray) {
        this.classIsArray = classIsArray;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<BRMSEkycRequestMember> getMember() {
        return member;
    }

    public void setMember(List<BRMSEkycRequestMember> member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "BRMSEkycRequestOutput{" +
                "classIsArray='" + classIsArray + '\'' +
                ", className='" + className + '\'' +
                ", member=" + member +
                '}';
    }
}
