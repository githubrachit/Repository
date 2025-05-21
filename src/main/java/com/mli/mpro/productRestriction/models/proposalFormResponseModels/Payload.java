package com.mli.mpro.productRestriction.models.proposalFormResponseModels;


import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;


public class Payload {

    private String kickoutMsg;
    private String resultFlag;
    private String kickoutMsg2;
    private String resultFlag2;
    private String maxAllowable;
    private String maxAllowableCIRider;
    private String maxAllowableAciRider;
    private String maxAllowableWopRider;
    private String maxAllowableAcoRider;
    private String maxAllowableTermPlusRider;
    private String maxAllowablePartnerCareRider;

    public String getMaxAllowableCIRider() {
        return maxAllowableCIRider;
    }

    public void setMaxAllowableCIRider(String maxAllowableCIRider) {
        this.maxAllowableCIRider = maxAllowableCIRider;
    }

    public String getMaxAllowableAciRider() {
        return maxAllowableAciRider;
    }

    public void setMaxAllowableAciRider(String maxAllowableAciRider) {
        this.maxAllowableAciRider = maxAllowableAciRider;
    }

    public String getMaxAllowableWopRider() {
        return maxAllowableWopRider;
    }

    public void setMaxAllowableWopRider(String maxAllowableWopRider) {
        this.maxAllowableWopRider = maxAllowableWopRider;
    }

    public String getMaxAllowableAcoRider() {
        return maxAllowableAcoRider;
    }

    public void setMaxAllowableAcoRider(String maxAllowableAcoRider) {
        this.maxAllowableAcoRider = maxAllowableAcoRider;
    }

    public String getMaxAllowableTermPlusRider() {
        return maxAllowableTermPlusRider;
    }

    public void setMaxAllowableTermPlusRider(String maxAllowableTermPlusRider) {
        this.maxAllowableTermPlusRider = maxAllowableTermPlusRider;
    }

    public String getMaxAllowablePartnerCareRider() {
        return maxAllowablePartnerCareRider;
    }

    public void setMaxAllowablePartnerCareRider(String maxAllowablePartnerCareRider) {
        this.maxAllowablePartnerCareRider = maxAllowablePartnerCareRider;
    }

    public String getKickoutMsg() {
        return kickoutMsg;
    }

    public void setKickoutMsg(String kickoutMsg) {
        this.kickoutMsg = kickoutMsg;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getKickoutMsg2() {
        return kickoutMsg2;
    }

    public void setKickoutMsg2(String kickoutMsg2) {
        this.kickoutMsg2 = kickoutMsg2;
    }

    public String getResultFlag2() {
        return resultFlag2;
    }

    public void setResultFlag2(String resultFlag2) {
        this.resultFlag2 = resultFlag2;
    }

    public String getMaxAllowable() {
        return maxAllowable;
    }

    public void setMaxAllowable(String maxAllowable) {
        this.maxAllowable = maxAllowable;
    }


    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "kickoutMsg='" + kickoutMsg + '\'' +
                ", resultFlag='" + resultFlag + '\'' +
                ", kickoutMsg2='" + kickoutMsg2 + '\'' +
                ", resultFlag2='" + resultFlag2 + '\'' +
                ", maxAllowable='" + maxAllowable + '\'' +
                ", maxAllowableCIRider='" + maxAllowableCIRider + '\'' +
                ", maxAllowableAciRider='" + maxAllowableAciRider + '\'' +
                ", maxAllowableWopRider='" + maxAllowableWopRider + '\'' +
                ", maxAllowableAcoRider='" + maxAllowableAcoRider + '\'' +
                ", maxAllowableTermPlusRider='" + maxAllowableTermPlusRider + '\'' +
                ", maxAllowablePartnerCareRider='" + maxAllowablePartnerCareRider + '\'' +
                '}';
    }
}