package com.mli.mpro.neo.models.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    private String leadId;
    private String equoteNumber;
    @Sensitive(MaskType.POLICY_NUM)
    private String policyNumber;
    private PersonalInfo personalInfo;
    private ProductInfo productInfo;
    private ProposalDetails proposalDetails;
    private String sumAssuredForSecondLife;

    public String getLeadId() {
        return leadId;
    }

    public Payload setLeadId(String leadId) {
        this.leadId = leadId;
        return this;
    }

    public String getEquoteNumber() {
        return equoteNumber;
    }

    public Payload setEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
        return this;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public Payload setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public Payload setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        return this;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public Payload setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
        return this;
    }

    public ProposalDetails getProposalDetails() {
        return proposalDetails;
    }

    public Payload setProposalDetails(ProposalDetails proposalDetails) {
        this.proposalDetails = proposalDetails;
        return this;
    }

    public String getSumAssuredForSecondLife() {
        return sumAssuredForSecondLife;
    }

    public void setSumAssuredForSecondLife(String sumAssuredForSecondLife) {
        this.sumAssuredForSecondLife = sumAssuredForSecondLife;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("equoteNumber = " + equoteNumber)
                .add("leadId = " + leadId)
                .add("personalInfo = " + personalInfo)
                .add("policyNumber = " + policyNumber)
                .add("productInfo = " + productInfo)
                .add("proposalDetails = " + proposalDetails)
                .add("sumAssuredForSecondLife = " + sumAssuredForSecondLife)
                .toString();
    }
}
