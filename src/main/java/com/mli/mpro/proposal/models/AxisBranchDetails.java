package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AxisBranchDetails {
    @Sensitive(MaskType.BRANCH_CODE)
    String branchCode;
    String solId;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    String branchName;
    String cluster;
    String strategicBranch;
    String circle;
    String region;
    @Sensitive(MaskType.NAME)
    String branchHeadName;
    @Sensitive(MaskType.MOBILE)
    String branchHeadMob;
    @Sensitive(MaskType.EMAIL)
    String branchHeadEmailId;
    @Sensitive(MaskType.NAME)
    String clusterHeadName;
    @Sensitive(MaskType.MOBILE)
    String clusterHeadMob;
    @Sensitive(MaskType.EMAIL)
    String clusterHeadEmailId;
    @Sensitive(MaskType.NAME)
    String circleHeadName;
    @Sensitive(MaskType.MOBILE)
    String circleHeadMob;
    @Sensitive(MaskType.EMAIL)
    String circleHeadEmailId;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSolId() {
        return solId;
    }

    public void setSolId(String solId) {
        this.solId = solId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStrategicBranch() {
        return strategicBranch;
    }

    public void setStrategicBranch(String strategicBranch) {
        this.strategicBranch = strategicBranch;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBranchHeadName() {
        return branchHeadName;
    }

    public void setBranchHeadName(String branchHeadName) {
        this.branchHeadName = branchHeadName;
    }

    public String getBranchHeadMob() {
        return branchHeadMob;
    }

    public void setBranchHeadMob(String branchHeadMob) {
        this.branchHeadMob = branchHeadMob;
    }

    public String getBranchHeadEmailId() {
        return branchHeadEmailId;
    }

    public void setBranchHeadEmailId(String branchHeadEmailId) {
        this.branchHeadEmailId = branchHeadEmailId;
    }

    public String getClusterHeadName() {
        return clusterHeadName;
    }

    public void setClusterHeadName(String clusterHeadName) {
        this.clusterHeadName = clusterHeadName;
    }

    public String getClusterHeadMob() {
        return clusterHeadMob;
    }

    public void setClusterHeadMob(String clusterHeadMob) {
        this.clusterHeadMob = clusterHeadMob;
    }

    public String getClusterHeadEmailId() {
        return clusterHeadEmailId;
    }

    public void setClusterHeadEmailId(String clusterHeadEmailId) {
        this.clusterHeadEmailId = clusterHeadEmailId;
    }

    public String getCircleHeadName() {
        return circleHeadName;
    }

    public void setCircleHeadName(String circleHeadName) {
        this.circleHeadName = circleHeadName;
    }

    public String getCircleHeadMob() {
        return circleHeadMob;
    }

    public void setCircleHeadMob(String circleHeadMob) {
        this.circleHeadMob = circleHeadMob;
    }

    public String getCircleHeadEmailId() {
        return circleHeadEmailId;
    }

    public void setCircleHeadEmailId(String circleHeadEmailId) {
        this.circleHeadEmailId = circleHeadEmailId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AxisBranchDetails{" +
                "branchCode='" + branchCode + '\'' +
                ", solId=" + solId +
                ", branchName='" + branchName + '\'' +
                ", cluster='" + cluster + '\'' +
                ", strategicBranch='" + strategicBranch + '\'' +
                ", circle='" + circle + '\'' +
                ", region='" + region + '\'' +
                ", branchHeadName='" + branchHeadName + '\'' +
                ", branchHeadMob='" + branchHeadMob + '\'' +
                ", branchHeadEmailId='" + branchHeadEmailId + '\'' +
                ", clusterHeadName='" + clusterHeadName + '\'' +
                ", clusterHeadMob='" + clusterHeadMob + '\'' +
                ", clusterHeadEmailId='" + clusterHeadEmailId + '\'' +
                ", circleHeadName='" + circleHeadName + '\'' +
                ", circleHeadMob='" + circleHeadMob + '\'' +
                ", circleHeadEmailId='" + circleHeadEmailId + '\'' +
                '}';
    }
}