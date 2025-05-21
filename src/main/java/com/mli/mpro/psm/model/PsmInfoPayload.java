package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class PsmInfoPayload {

    @JsonProperty("channel")
    private String channel;
    @JsonProperty("age")
    private String age;
    @JsonProperty("annualIncome")
    private String annualIncome;
    @JsonProperty("needOfInsurance")
    private String needOfInsurance;
    @JsonProperty("lifeStage")
    private String lifeStage="";

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getNeedOfInsurance() {
        return needOfInsurance;
    }


    public void setNeedOfInsurance(String needOfInsurance) {
        this.needOfInsurance = needOfInsurance;
    }

    public String getLifeStage() {
        return lifeStage;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setLifeStage(String lifeStage) {
        this.lifeStage = lifeStage;
    }

    @Override
    public String toString() {
        return "PsmInfoPayload{" +
                "channel='" + channel + '\'' +
                ", age='" + age + '\'' +
                ", annualIncome='" + annualIncome + '\'' +
                ", needOfInsurance='" + needOfInsurance + '\'' +
                ", lifeStage='" + lifeStage + '\'' +
                '}';
    }
}
