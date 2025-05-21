package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycResponseOutput {
    private String ekycinsured;
    private String ekyproposerc;
    private String ekycpayor;
    private String ekyctype;

    public String getEkycinsured() { return ekycinsured; }
    public void setEkycinsured(String ekycinsured) {  this.ekycinsured = ekycinsured;  }
    public String getEkyproposerc() {  return ekyproposerc; }
    public void setEkyproposerc(String ekyproposerc) {  this.ekyproposerc = ekyproposerc; }
    public String getEkycpayor() { return ekycpayor; }
    public void setEkycpayor(String ekycpayor) { this.ekycpayor = ekycpayor; }
    public String getEkyctype() { return ekyctype; }
    public void setEkyctype(String ekyctype) { this.ekyctype = ekyctype; }

    @Override
    public String toString() {
        return "BRMSEkycResponseOutput{" +
                "ekycinsured='" + ekycinsured + '\'' +
                ", ekyproposerc='" + ekyproposerc + '\'' +
                ", ekycpayor='" + ekycpayor + '\'' +
                ", ekyctype='" + ekyctype + '\'' +
                '}';
    }
}
