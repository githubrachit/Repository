package com.mli.mpro.bankdetails.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class BankDetails {
    @Sensitive(MaskType.BANK_IFSC)
    private String bnkIfscCode;
    @Sensitive(MaskType.BANK_MICR)
    private String bnkmicrCode;
    @Sensitive(MaskType.BANK_NAME)
    private String bnkName;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    private String bnkBranchName;
    @Sensitive(MaskType.ADDRESS)
    private String bnkAddressLn1;
    private String bnkAddressLn2;
    private String bnkAddressLn3;
    @Sensitive(MaskType.ADDRESS)
    private String bnkBrnchCity;
    @Sensitive(MaskType.ADDRESS)
    private String bnkBrnchState;
    private String bnkBrnchCntry;
    @Sensitive(MaskType.ADDRESS)
    private String bnkBrnchPostalCd;
    private String bnkBrnchLangCd;
    private String bnkBrnchStatus;

    public String getBnkIfscCode() {
        return bnkIfscCode;
    }

    public void setBnkIfscCode(String bnkIfscCode) {
        this.bnkIfscCode = bnkIfscCode;
    }

    public String getBnkmicrCode() {
        return bnkmicrCode;
    }

    public void setBnkmicrCode(String bnkmicrCode) {
        this.bnkmicrCode = bnkmicrCode;
    }

    public String getBnkMicrCode() {
        return bnkmicrCode;
    }

    public void setBnkMicrCode(String bnkmicrCode) {
        this.bnkmicrCode = bnkmicrCode;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getBnkBranchName() {
        return bnkBranchName;
    }

    public void setBnkBranchName(String bnkBranchName) {
        this.bnkBranchName = bnkBranchName;
    }

    public String getBnkAddressLn1() {
        return bnkAddressLn1;
    }

    public void setBnkAddressLn1(String bnkAddressLn1) {
        this.bnkAddressLn1 = bnkAddressLn1;
    }

    public String getBnkAddressLn2() {
        return bnkAddressLn2;
    }

    public void setBnkAddressLn2(String bnkAddressLn2) {
        this.bnkAddressLn2 = bnkAddressLn2;
    }

    public String getBnkAddressLn3() {
        return bnkAddressLn3;
    }

    public void setBnkAddressLn3(String bnkAddressLn3) {
        this.bnkAddressLn3 = bnkAddressLn3;
    }

    public String getBnkBrnchCity() {
        return bnkBrnchCity;
    }

    public void setBnkBrnchCity(String bnkBrnchCity) {
        this.bnkBrnchCity = bnkBrnchCity;
    }

    public String getBnkBrnchState() {
        return bnkBrnchState;
    }

    public void setBnkBrnchState(String bnkBrnchState) {
        this.bnkBrnchState = bnkBrnchState;
    }

    public String getBnkBrnchCntry() {
        return bnkBrnchCntry;
    }

    public void setBnkBrnchCntry(String bnkBrnchCntry) {
        this.bnkBrnchCntry = bnkBrnchCntry;
    }

    public String getBnkBrnchPostalCd() {
        return bnkBrnchPostalCd;
    }

    public void setBnkBrnchPostalCd(String bnkBrnchPostalCd) {
        this.bnkBrnchPostalCd = bnkBrnchPostalCd;
    }

    public String getBnkBrnchLangCd() {
        return bnkBrnchLangCd;
    }

    public void setBnkBrnchLangCd(String bnkBrnchLangCd) {
        this.bnkBrnchLangCd = bnkBrnchLangCd;
    }

    public String getBnkBrnchStatus() {
        return bnkBrnchStatus;
    }

    public void setBnkBrnchStatus(String bnkBrnchStatus) {
        this.bnkBrnchStatus = bnkBrnchStatus;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "BankDetails{" +
                "bnkIfscCode='" + bnkIfscCode + '\'' +
                ", bnkmicrCode='" + bnkmicrCode + '\'' +
                ", bnkName='" + bnkName + '\'' +
                ", bnkBranchName='" + bnkBranchName + '\'' +
                ", bnkAddressLn1='" + bnkAddressLn1 + '\'' +
                ", bnkAddressLn2='" + bnkAddressLn2 + '\'' +
                ", bnkAddressLn3='" + bnkAddressLn3 + '\'' +
                ", bnkBrnchCity='" + bnkBrnchCity + '\'' +
                ", bnkBrnchState='" + bnkBrnchState + '\'' +
                ", bnkBrnchCntry='" + bnkBrnchCntry + '\'' +
                ", bnkBrnchPostalCd='" + bnkBrnchPostalCd + '\'' +
                ", bnkBrnchLangCd='" + bnkBrnchLangCd + '\'' +
                ", bnkBrnchStatus='" + bnkBrnchStatus + '\'' +
                '}';
    }
}