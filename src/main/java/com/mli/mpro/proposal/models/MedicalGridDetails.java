package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class MedicalGridDetails {

    private String category;
    private String gridName;
    private String additionalInfo;
    private String testName1;
    private String testName2;
    private String result;

    public MedicalGridDetails() {
	super();
    }

    public MedicalGridDetails(String category, String gridName, String additionalInfo, String testName1, String testName2, String result) {
	super();
	this.category = category;
	this.gridName = gridName;
	this.additionalInfo = additionalInfo;
	this.testName1 = testName1;
	this.testName2 = testName2;
	this.result = result;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getGridName() {
	return gridName;
    }

    public void setGridName(String gridName) {
	this.gridName = gridName;
    }

    public String getAdditionalInfo() {
	return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
	this.additionalInfo = additionalInfo;
    }

    public String getTestName1() {
	return testName1;
    }

    public void setTestName1(String testName1) {
	this.testName1 = testName1;
    }

    public String getTestName2() {
	return testName2;
    }

    public void setTestName2(String testName2) {
	this.testName2 = testName2;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "MedicalGridDetails [category=" + category + ", gridName=" + gridName + ", additionalInfo=" + additionalInfo + ", testName1=" + testName1
		+ ", testName2=" + testName2 + ", result=" + result + "]";
    }

}
