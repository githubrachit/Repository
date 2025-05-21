package com.mli.mpro.location.productRecommendation.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {
    private String channel;
    private String age;
    @Sensitive(MaskType.AMOUNT)
    private String annualIncome;
    private String needOfInsurance;
    private String lifeStage;
    @Sensitive(MaskType.DOB)
    private String dateOfBirth;
    private List<String> recommendedProducts;

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

    public void setLifeStage(String lifeStage) {
        this.lifeStage = lifeStage;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(List<String> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "channel='" + channel + '\'' +
                ", age='" + age + '\'' +
                ", annualIncome='" + annualIncome + '\'' +
                ", needOfInsurance='" + needOfInsurance + '\'' +
                ", lifeStage='" + lifeStage + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", recommendedProducts=" + recommendedProducts +
                '}';
    }
}
