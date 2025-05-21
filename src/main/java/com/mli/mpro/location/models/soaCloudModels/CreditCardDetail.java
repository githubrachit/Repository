package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class CreditCardDetail {
    @JsonProperty("crdCardNumber")
    private String crdCardNumber;

    @JsonProperty("crdType")
    private String crdType;

    @JsonProperty("crdCardHolderName")
    private String crdCardHolderName;

    @JsonProperty("crdCardExpiryDt")
    private String crdCardExpiryDt;

    @JsonProperty("nameOfCrdCardOwner")
    private String nameOfCrdCardOwner;

    @JsonProperty("relOfCrdCardOwnerWithLifeInsured")
    private String relOfCrdCardOwnerWithLifeInsured;

    public String getCrdCardNumber() {
        return crdCardNumber;
    }

    public void setCrdCardNumber(String crdCardNumber) {
        this.crdCardNumber = crdCardNumber;
    }

    public String getCrdType() {
        return crdType;
    }

    public void setCrdType(String crdType) {
        this.crdType = crdType;
    }

    public String getCrdCardHolderName() {
        return crdCardHolderName;
    }

    public void setCrdCardHolderName(String crdCardHolderName) {
        this.crdCardHolderName = crdCardHolderName;
    }

    public String getCrdCardExpiryDt() {
        return crdCardExpiryDt;
    }

    public void setCrdCardExpiryDt(String crdCardExpiryDt) {
        this.crdCardExpiryDt = crdCardExpiryDt;
    }

    public String getNameOfCrdCardOwner() {
        return nameOfCrdCardOwner;
    }

    public void setNameOfCrdCardOwner(String nameOfCrdCardOwner) {
        this.nameOfCrdCardOwner = nameOfCrdCardOwner;
    }

    public String getRelOfCrdCardOwnerWithLifeInsured() {
        return relOfCrdCardOwnerWithLifeInsured;
    }

    public void setRelOfCrdCardOwnerWithLifeInsured(String relOfCrdCardOwnerWithLifeInsured) {
        this.relOfCrdCardOwnerWithLifeInsured = relOfCrdCardOwnerWithLifeInsured;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "CreditCardDetail{" +
                "crdCardNumber='" + crdCardNumber + '\'' +
                ", crdType='" + crdType + '\'' +
                ", crdCardHolderName='" + crdCardHolderName + '\'' +
                ", crdCardExpiryDt='" + crdCardExpiryDt + '\'' +
                ", nameOfCrdCardOwner='" + nameOfCrdCardOwner + '\'' +
                ", relOfCrdCardOwnerWithLifeInsured='" + relOfCrdCardOwnerWithLifeInsured + '\'' +
                '}';
    }
}
