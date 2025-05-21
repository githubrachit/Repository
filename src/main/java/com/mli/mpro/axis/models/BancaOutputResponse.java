package com.mli.mpro.axis.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class BancaOutputResponse {

    private String isCombo;
    @Sensitive(MaskType.AMOUNT)
    private double totalPremium;
    private List<BancaInfo> transactions;
    public BancaOutputResponse() {
	super();
    }
    public BancaOutputResponse(String isCombo, double totalPremium, List<BancaInfo> transactions) {
	super();
	this.isCombo = isCombo;
	this.totalPremium = totalPremium;
	this.transactions = transactions;
    }
    public String getIsCombo() {
        return isCombo;
    }
    public void setIsCombo(String isCombo) {
        this.isCombo = isCombo;
    }
    public double getTotalPremium() {
        return totalPremium;
    }
    public void setTotalPremium(double totalPremium) {
        this.totalPremium = totalPremium;
    }
    public List<BancaInfo> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<BancaInfo> transactions) {
        this.transactions = transactions;
    }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "BancaOutputResponse [isCombo=" + isCombo + ", totalPremium=" + totalPremium + ", transactions=" + transactions + "]";
    }        
}
