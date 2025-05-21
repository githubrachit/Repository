package com.mli.mpro.tmb.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class RenewalPushRequest {

    @Sensitive(MaskType.BANK_ACC_NUM)
    private String accountNo = "1";
    @Sensitive(MaskType.FIRST_NAME)
    private String customerFirstName = "1";
    @Sensitive(MaskType.LAST_NAME)
    private String customerLastName = "1";
    @Sensitive(MaskType.MOBILE)
    private String mobileNumber = "1";
    @Sensitive(MaskType.DOB)
    private String dob = "1";
    @Sensitive(MaskType.PAN_NUM)
    private String pan = "1";
    private String nationality = "1";
    private String customerClassification = "1";
    private String policyNumber = "1";
    private String partnerproductType = "1";
    private String productCode = "1";
    private String productName = "1";
    private String policyTerm = "1";
    private String policyPaymentTerm = "1";
    private String optrider = "1";
    private String riderCode1 = "1";
    private String riderPremium1 = "1";
    private String ridercode2 = "1";
    private String riderName2 = "1";
    private String riderPremium2 = "1";
    private String riderCode3 = "1";
    private String riderName3 = "1";
    private String riderPremuim3 = "1";
    private String riderCode4 = "1";
    private String riderName4 = "1";
    private String riderPremuim4 = "1";
    private String basePremuim = "1";
    private String totalTax = "1";
    private String totalPremuimWithTax = "1";
    private String paymentMode = "1";
    private String renewalPayment = "1";
    private String optDirerctDebit = "1";

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCustomerClassification() {
        return customerClassification;
    }

    public void setCustomerClassification(String customerClassification) {
        this.customerClassification = customerClassification;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPartnerproductType() {
        return partnerproductType;
    }

    public void setPartnerproductType(String partnerproductType) {
        this.partnerproductType = partnerproductType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }

    public String getOptrider() {
        return optrider;
    }

    public void setOptrider(String optrider) {
        this.optrider = optrider;
    }

    public String getPolicyPaymentTerm() {
        return policyPaymentTerm;
    }

    public void setPolicyPaymentTerm(String policyPaymentTerm) {
        this.policyPaymentTerm = policyPaymentTerm;
    }

    public String getRiderCode1() {
        return riderCode1;
    }

    public void setRiderCode1(String riderCode1) {
        this.riderCode1 = riderCode1;
    }

    public String getRiderPremium1() {
        return riderPremium1;
    }

    public void setRiderPremium1(String riderPremium1) {
        this.riderPremium1 = riderPremium1;
    }

    public String getRidercode2() {
        return ridercode2;
    }

    public void setRidercode2(String ridercode2) {
        this.ridercode2 = ridercode2;
    }

    public String getRiderName2() {
        return riderName2;
    }

    public void setRiderName2(String riderName2) {
        this.riderName2 = riderName2;
    }

    public String getRiderPremium2() {
        return riderPremium2;
    }

    public void setRiderPremium2(String riderPremium2) {
        this.riderPremium2 = riderPremium2;
    }

    public String getRiderCode3() {
        return riderCode3;
    }

    public void setRiderCode3(String riderCode3) {
        this.riderCode3 = riderCode3;
    }

    public String getRiderName3() {
        return riderName3;
    }

    public void setRiderName3(String riderName3) {
        this.riderName3 = riderName3;
    }

    public String getRiderPremuim3() {
        return riderPremuim3;
    }

    public void setRiderPremuim3(String riderPremuim3) {
        this.riderPremuim3 = riderPremuim3;
    }

    public String getRiderCode4() {
        return riderCode4;
    }

    public void setRiderCode4(String riderCode4) {
        this.riderCode4 = riderCode4;
    }

    public String getRiderName4() {
        return riderName4;
    }

    public void setRiderName4(String riderName4) {
        this.riderName4 = riderName4;
    }

    public String getRiderPremuim4() {
        return riderPremuim4;
    }

    public void setRiderPremuim4(String riderPremuim4) {
        this.riderPremuim4 = riderPremuim4;
    }

    public String getBasePremuim() {
        return basePremuim;
    }

    public void setBasePremuim(String basePremuim) {
        this.basePremuim = basePremuim;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalPremuimWithTax() {
        return totalPremuimWithTax;
    }

    public void setTotalPremuimWithTax(String totalPremuimWithTax) {
        this.totalPremuimWithTax = totalPremuimWithTax;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRenewalPayment() {
        return renewalPayment;
    }

    public void setRenewalPayment(String renewalPayment) {
        this.renewalPayment = renewalPayment;
    }

    public String getOptDirerctDebit() {
        return optDirerctDebit;
    }

    public void setOptDirerctDebit(String optDirerctDebit) {
        this.optDirerctDebit = optDirerctDebit;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RenewalPushRequest{" +
                "accountNo='" + accountNo + '\'' +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", pan='" + pan + '\'' +
                ", nationality='" + nationality + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", partnerproductType='" + partnerproductType + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", policyTerm='" + policyTerm + '\'' +
                ", policyPaymentTerm='" + policyPaymentTerm + '\'' +
                ", optrider='" + optrider + '\'' +
                ", riderCode1='" + riderCode1 + '\'' +
                ", riderPremium1='" + riderPremium1 + '\'' +
                ", riderCode2='" + ridercode2 + '\'' +
                ", riderName2='" + riderName2 + '\'' +
                ", riderPremium2='" + riderPremium2 + '\'' +
                ", riderCode3='" + riderCode3 + '\'' +
                ", riderName3='" + riderName3 + '\'' +
                ", riderPremium3='" + riderPremuim3 + '\'' +
                ", riderCode4='" + riderCode4 + '\'' +
                ", riderName4='" + riderName4 + '\'' +
                ", riderPremium4='" + riderPremuim4 + '\'' +
                ", basePremium='" + basePremuim + '\'' +
                ", totalTax='" + totalTax + '\'' +
                ", totalPremiumWithTax='" + totalPremuimWithTax + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", renewalPayment='" + renewalPayment + '\'' +
                ", optDirectDebit='" + optDirerctDebit + '\'' +
                '}';
    }
}
