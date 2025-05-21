package com.mli.mpro.neo.models.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInfo {

    @Sensitive(MaskType.AMOUNT)
    private String sumAssured;
    @Sensitive(MaskType.AMOUNT)
    private String initialPremium;
    private String premiumType;
    private String premiumPaymentTerm;
    private String coverageTerm;
    private String productName;
    @Sensitive(MaskType.AMOUNT)
    private String criticalIllnessSumAssured;
    @Sensitive(MaskType.AMOUNT)
    private String riderSumAssured;
    @Sensitive(MaskType.AMOUNT)
    private String accidentalCoverRiderSumAssured;

    @Sensitive(MaskType.AMOUNT)
    private String secondAnnuitantRiskClass;

    @Sensitive(MaskType.AMOUNT)
    private String secondAnnuitantSumAssured;

    @Sensitive(MaskType.AMOUNT)
    private String isJointLife;

    public String getSumAssured() {
        return sumAssured;
    }

    public ProductInfo setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
        return this;
    }

    public String getInitialPremium() {
        return initialPremium;
    }

    public ProductInfo setInitialPremium(String initialPremium) {
        this.initialPremium = initialPremium;
        return this;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public ProductInfo setPremiumType(String premiumType) {
        this.premiumType = premiumType;
        return this;
    }

    public String getPremiumPaymentTerm() {
        return premiumPaymentTerm;
    }

    public ProductInfo setPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
        return this;
    }

    public String getCoverageTerm() {
        return coverageTerm;
    }

    public ProductInfo setCoverageTerm(String coverageTerm) {
        this.coverageTerm = coverageTerm;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductInfo setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getCriticalIllnessSumAssured() {
        return criticalIllnessSumAssured;
    }

    public ProductInfo setCriticalIllnessSumAssured(String criticalIllnessSumAssured) {
        this.criticalIllnessSumAssured = criticalIllnessSumAssured;
        return this;
    }

    public String getRiderSumAssured() {
        return riderSumAssured;
    }

    public ProductInfo setRiderSumAssured(String riderSumAssured) {
        this.riderSumAssured = riderSumAssured;
        return this;
    }

    public String getAccidentalCoverRiderSumAssured() {
        return accidentalCoverRiderSumAssured;
    }

    public ProductInfo setAccidentalCoverRiderSumAssured(String accidentalCoverRiderSumAssured) {
        this.accidentalCoverRiderSumAssured = accidentalCoverRiderSumAssured;
        return this;
    }

    public String getSecondAnnuitantRiskClass() {
        return secondAnnuitantRiskClass;
    }

    public void setSecondAnnuitantRiskClass(String secondAnnuitantRiskClass) {
        this.secondAnnuitantRiskClass = secondAnnuitantRiskClass;
    }

    public String getSecondAnnuitantSumAssured() {
        return secondAnnuitantSumAssured;
    }

    public void setSecondAnnuitantSumAssured(String secondAnnuitantSumAssured) {
        this.secondAnnuitantSumAssured = secondAnnuitantSumAssured;
    }

    public String getIsJointLife() {
        return isJointLife;
    }

    public void setIsJointLife(String isJointLife) {
        this.isJointLife = isJointLife;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ProductInfo{" +
                "sumAssured='" + sumAssured + '\'' +
                ", initialPremium='" + initialPremium + '\'' +
                ", premiumType='" + premiumType + '\'' +
                ", premiumPaymentTerm='" + premiumPaymentTerm + '\'' +
                ", coverageTerm='" + coverageTerm + '\'' +
                ", productName='" + productName + '\'' +
                ", criticalIllnessSumAssured='" + criticalIllnessSumAssured + '\'' +
                ", riderSumAssured='" + riderSumAssured + '\'' +
                ", accidentalCoverRiderSumAssured='" + accidentalCoverRiderSumAssured + '\'' +
                ",secondAnnuitantRiskClass='"+ secondAnnuitantRiskClass + '\''+
                ",secondAnnuitantSumAssured='"+ secondAnnuitantSumAssured+ '\''+
                ",isJointLife="+ isJointLife+ '\''+
                '}';
    }
}
