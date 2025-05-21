package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PosvBrmsResponsePayload {


    @JsonProperty("output1")
    private String output1;

    @JsonProperty("output2")
    private String output2;
    //FUL2-190847 Talic Green
    @JsonProperty("output3")
    private String output3;

    @JsonProperty("output4")
    private String output4;

    public String getOutput3() {
        return output3;
    }

    public void setOutput3(String output3) {
        this.output3 = output3;
    }

    public PosvBrmsResponsePayload() {

    }

    public PosvBrmsResponsePayload(String output1, String output2) {
        this.output1 = output1;
        this.output2 = output2;
    }

    public String getOutput1() {
        return output1;
    }

    public void setOutput1(String output1) {
        this.output1 = output1;
    }

    public String getOutput2() {
        return output2;
    }

    public void setOutput2(String output2) {
        this.output2 = output2;
    }

    public String getOutput4() {
        return output4;
    }

    public void setOutput4(String output4) {
        this.output4 = output4;
    }

    @Override
    public String toString() {
        return "PosvBrmsResponsePayload{" +
                "output1='" + output1 + '\'' +
                ", output2='" + output2 + '\'' +
                ", output3='" + output3 + '\'' +
                ", output4='" + output4 + '\'' +
                '}';
    }
}
