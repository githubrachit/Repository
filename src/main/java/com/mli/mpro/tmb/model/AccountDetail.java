package com.mli.mpro.tmb.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AccountDetail {

    @Sensitive(MaskType.BANK_ACC_NUM)
    private String accountnumber;
    @Sensitive(MaskType.AMOUNT)
    private String accountbalance;
    private String accountType;
    @Sensitive(MaskType.BANK_IFSC)
    private String ifsc;
    @Sensitive(MaskType.BANK_NAME)
    private String bankName;
    @Sensitive(MaskType.BANK_MICR)
    private String micr;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    private String branchName;
    private String bankAccOpeningDate;


    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(String accountbalance) {
        this.accountbalance = accountbalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccOpeningDate() {
        return bankAccOpeningDate;
    }

    public void setBankAccOpeningDate(String bankAccOpeningDate) {
        this.bankAccOpeningDate = bankAccOpeningDate;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AccountDetail{" +
                "accountnumber='" + accountnumber + '\'' +
                ", accountbalance='" + accountbalance + '\'' +
                ", accountType='" + accountType + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", bankName='" + bankName + '\'' +
                ", micr='" + micr + '\'' +
                ", branchName='" + branchName + '\'' +
                ", bankAccOpeningDate='" + bankAccOpeningDate + '\'' +
                '}';
    }
}

