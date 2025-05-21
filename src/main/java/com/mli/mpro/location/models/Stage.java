package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;

public class Stage {

    private String stageNumber;
    private String stageName;
    private String isPolicy;

    public Stage() {
    }

    public Stage(String stageNumber, String stageName) {
        this.stageNumber = stageNumber;
        this.stageName = stageName;
    }

    public String getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(String stageNumber) {
        this.stageNumber = stageNumber;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getIsPolicy() {
        return isPolicy;
    }

    public void setIsPolicy(String isPolicy) {
        this.isPolicy = isPolicy;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Stage{" +
                "stageNumber='" + stageNumber + '\'' +
                ", stageName='" + stageName + '\'' +
                ", isPolicy='" + isPolicy + '\'' +
                '}';
    }
}
