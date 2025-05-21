package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class OasDetails {
    private BhDetails bhDetails;
    private ChDetails chDetails;
    private String digitalOasSkipOpted;
    private String strategicBranch;
    private AxisBranchDetails axisBranchDetails;

    public String getStrategicBranch() {
        return strategicBranch;
    }

    public void setStrategicBranch(String strategicBranch) {
        this.strategicBranch = strategicBranch;
    }


    public AxisBranchDetails getAxisBranchDetails() {
        return axisBranchDetails;
    }

    public void setAxisBranchDetails(AxisBranchDetails axisBranchDetails) {
        this.axisBranchDetails = axisBranchDetails;
    }
    public String getDigitalOasSkipOpted() {
        return digitalOasSkipOpted;
    }

    public void setDigitalOasSkipOpted(String digitalOasSkipOpted) {
        this.digitalOasSkipOpted = digitalOasSkipOpted;
    }

    public BhDetails getBhDetails() {
        return bhDetails;
    }

    public void setBhDetails(BhDetails bhDetails) {
        this.bhDetails = bhDetails;
    }

    public ChDetails getChDetails() {
        return chDetails;
    }

    public void setChDetails(ChDetails chDetails) {
        this.chDetails = chDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "OasDetails{" +
                "bhDetails=" + bhDetails +
                ", chDetails=" + chDetails +
                ", digitalOasSkipOpted='" + digitalOasSkipOpted + '\'' +
                ", strategicBranch='" + strategicBranch + '\'' +
                ", axisBranchDetails=" + axisBranchDetails +
                '}';
    }
}
