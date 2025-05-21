package com.mli.mpro.bankdetails.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class BankDetailsRequest {

    @Sensitive(MaskType.BANK_IFSC)
    private String bankIfscCode;
    @Sensitive(MaskType.BANK_MICR)
    private String bankMicrCode;
    private Long transactionId;

    public String getBankIfscCode() {
        return bankIfscCode;
    }
    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }
    public String getBankMicrCode() {
        return bankMicrCode;
    }
    public void setBankMicrCode(String bankMicrCode) {
        this.bankMicrCode = bankMicrCode;
    }
    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "BankDetailsRequest [bankIfscCode=" + bankIfscCode + ", bankMicrCode=" + bankMicrCode
                + ", transactionId=" + transactionId + "]";
    }
}