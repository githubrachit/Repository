package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;


public class PartnerPaymentDetails {

    @JsonProperty("directDebitDetails")
    private DirectDebitDetails directDebitDetails;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("paymentAccount")
    private String paymentAccount;
    @Sensitive(MaskType.BANK_IFSC)
    @JsonProperty("paymentIfsc")
    private String paymentIfsc;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("paymentMicr")
    private String paymentMicr;
    @JsonProperty("paymentBankBranchAddress")
    private String paymentBankBranchAddress;
    @JsonProperty("paymentBankProductType")
    private String paymentBankProductType;

    public PartnerPaymentDetails() {
        super();
    }

    public PartnerPaymentDetails(DirectDebitDetails directDebitDetails) {
        super();
        this.directDebitDetails = directDebitDetails;
    }

    public PartnerPaymentDetails(DirectDebitDetails directDebitDetails,
                                 String paymentAccount, String paymentIfsc, String paymentMicr, String paymentBankBranchAddress,
                                 String paymentBankProductType) {
        super();
        this.directDebitDetails = directDebitDetails;
        this.paymentAccount = paymentAccount;
        this.paymentIfsc = paymentIfsc;
        this.paymentMicr = paymentMicr;
        this.paymentBankBranchAddress = paymentBankBranchAddress;
        this.paymentBankProductType = paymentBankProductType;
    }

    public DirectDebitDetails getDirectDebitDetails() {
        return directDebitDetails;
    }

    public void setDirectDebitDetails(DirectDebitDetails directDebitDetails) {
        this.directDebitDetails = directDebitDetails;
    }


    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentIfsc() {
        return paymentIfsc;
    }

    public void setPaymentIfsc(String paymentIfsc) {
        this.paymentIfsc = paymentIfsc;
    }

    public String getPaymentMicr() {
        return paymentMicr;
    }

    public void setPaymentMicr(String paymentMicr) {
        this.paymentMicr = paymentMicr;
    }

    public String getPaymentBankBranchAddress() {
        return paymentBankBranchAddress;
    }

    public void setPaymentBankBranchAddress(String paymentBankBranchAddress) {
        this.paymentBankBranchAddress = paymentBankBranchAddress;
    }

    public String getPaymentBankProductType() {
        return paymentBankProductType;
    }

    public void setPaymentBankProductType(String paymentBankProductType) {
        this.paymentBankProductType = paymentBankProductType;
    }


    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "PartnerPaymentDetails [directDebitDetails="
                + directDebitDetails + ", paymentAccount=" + paymentAccount + ", paymentIfsc=" + paymentIfsc
                + ", paymentMicr=" + paymentMicr + ", paymentBankBranchAddress=" + paymentBankBranchAddress
                + ", paymentBankProductType=" + paymentBankProductType + "]";
    }

}
