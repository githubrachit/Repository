package com.mli.mpro.proposal.models;

import java.util.Date;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "smartTermPlanCounter")
public class SmartTermPlanCounter {

    @Id
    private String id;
    private String counterType;
    private int femaleCounter;
    private int axisSmartTermPlanCount;
    private int yblSmartTermPlanCount;
    private int axisTelemerResultCount;
    private int yblTelemerResultCount;
    private Date updatedTime;
    private String date;

    public SmartTermPlanCounter() {
	super();
    }

    public SmartTermPlanCounter(int femaleCounter, int axisSmartTermPlanCount, int yblSmartTermPlanCount, int axisTelemerResultCount,
	    int yblTelemerResultCount) {
	super();
	this.femaleCounter = femaleCounter;
	this.axisSmartTermPlanCount = axisSmartTermPlanCount;
	this.yblSmartTermPlanCount = yblSmartTermPlanCount;
	this.axisTelemerResultCount = axisTelemerResultCount;
	this.yblTelemerResultCount = yblTelemerResultCount;
    }

    public int getFemaleCounter() {
	return femaleCounter;
    }

    public void setFemaleCounter(int femaleCounter) {
	this.femaleCounter = femaleCounter;
    }

    public int getAxisSmartTermPlanCount() {
	return axisSmartTermPlanCount;
    }

    public void setAxisSmartTermPlanCount(int axisSmartTermPlanCount) {
	this.axisSmartTermPlanCount = axisSmartTermPlanCount;
    }

    public int getYblSmartTermPlanCount() {
	return yblSmartTermPlanCount;
    }

    public void setYblSmartTermPlanCount(int yblSmartTermPlanCount) {
	this.yblSmartTermPlanCount = yblSmartTermPlanCount;
    }

    public int getAxisTelemerResultCount() {
	return axisTelemerResultCount;
    }

    public void setAxisTelemerResultCount(int axisTelemerResultCount) {
	this.axisTelemerResultCount = axisTelemerResultCount;
    }

    public int getYblTelemerResultCount() {
	return yblTelemerResultCount;
    }

    public void setYblTelemerResultCount(int yblTelemerResultCount) {
	this.yblTelemerResultCount = yblTelemerResultCount;
    }

    public String getCounterType() {
	return counterType;
    }

    public void setCounterType(String counterType) {
	this.counterType = counterType;
    }

    public Date getUpdatedTime() {
	return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
	this.updatedTime = updatedTime;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "SmartTermPlanCounter [id=" + id + ", counterType=" + counterType + ", femaleCounter=" + femaleCounter + ", axisSmartTermPlanCount="
		+ axisSmartTermPlanCount + ", yblSmartTermPlanCount=" + yblSmartTermPlanCount + ", axisTelemerResultCount=" + axisTelemerResultCount
		+ ", yblTelemerResultCount=" + yblTelemerResultCount + ", updatedTime=" + updatedTime + ", date=" + date + "]";
    }

}
