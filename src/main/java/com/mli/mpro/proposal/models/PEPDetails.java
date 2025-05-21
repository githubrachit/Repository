
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isPayorPep", "isLIorNomineePEP", "isLIPEP", "isFamilyMemberPEP", "specifyFamilyMembers", "politicalexperience",
	"affiliationstopoliticalparty", "roleinpoliticalParty", "roleOthers", "portfolioHandled", "partyinpower", "pepEverPostedInForeignOffice",
	"foreignOfficeDetails", "incomeSources", "pepConvicted", "convictionDetails" })
public class PEPDetails {
    @JsonProperty("isProposerPEP")
    private boolean isProposerPEP;
    @JsonProperty("isPayorPep")
    private boolean isPayorPep;
    @JsonProperty("isLIOrNomineePEP")
    private boolean isLIOrNomineePEP;
    @JsonProperty("isLIPEP")
    private boolean isLIPEP;
    @JsonProperty("isFamilyMemberPEP")
    private boolean isFamilyMemberPEP;
    @JsonProperty("specifyFamilyMembers")
    private String specifyFamilyMembers;
    @JsonProperty("politicalExperience")
    private String politicalExperience;
    @JsonProperty("affiliationsToPoliticalparty")
    private String affiliationsToPoliticalparty;
    @JsonProperty("roleInPoliticalParty")
    private String roleInPoliticalParty;
    @JsonProperty("roleOthers")
    private String roleOthers;
    @JsonProperty("portfolioHandled")
    private String portfolioHandled;
    @JsonProperty("partyInPower")
    private String partyInPower;
    @JsonProperty("pepEverPostedInForeignOffice")
    private String pepEverPostedInForeignOffice;
    @JsonProperty("foreignOfficeDetails")
    private String foreignOfficeDetails;
    @JsonProperty("incomeSources")
    private String incomeSources;
    @JsonProperty("pepConvicted")
    private String pepConvicted;
    @JsonProperty("convictionDetails")
    private String convictionDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PEPDetails() {
    }

    /**
     */

    public boolean isPayorPep() {
	return isPayorPep;
    }

    public PEPDetails(boolean isProposerPEP, boolean isPayorPep, boolean isLIOrNomineePEP, boolean isLIPEP, boolean isFamilyMemberPEP,
	    String specifyFamilyMembers, String politicalExperience, String affiliationsToPoliticalparty, String roleInPoliticalParty, String roleOthers,
	    String portfolioHandled, String partyInPower, String pepEverPostedInForeignOffice, String foreignOfficeDetails, String incomeSources,
	    String pepConvicted, String convictionDetails) {
	super();
	this.isProposerPEP = isProposerPEP;
	this.isPayorPep = isPayorPep;
	this.isLIOrNomineePEP = isLIOrNomineePEP;
	this.isLIPEP = isLIPEP;
	this.isFamilyMemberPEP = isFamilyMemberPEP;
	this.specifyFamilyMembers = specifyFamilyMembers;
	this.politicalExperience = politicalExperience;
	this.affiliationsToPoliticalparty = affiliationsToPoliticalparty;
	this.roleInPoliticalParty = roleInPoliticalParty;
	this.roleOthers = roleOthers;
	this.portfolioHandled = portfolioHandled;
	this.partyInPower = partyInPower;
	this.pepEverPostedInForeignOffice = pepEverPostedInForeignOffice;
	this.foreignOfficeDetails = foreignOfficeDetails;
	this.incomeSources = incomeSources;
	this.pepConvicted = pepConvicted;
	this.convictionDetails = convictionDetails;
    }

    public void setPayorPep(boolean isPayorPep) {
	this.isPayorPep = isPayorPep;
    }

    public boolean isLIorNomineePEP() {
	return isLIOrNomineePEP;
    }

    public void setLIorNomineePEP(boolean isLIorNomineePEP) {
	this.isLIOrNomineePEP = isLIorNomineePEP;
    }

    public boolean isLIPEP() {
	return isLIPEP;
    }

    public void setLIPEP(boolean isLIPEP) {
	this.isLIPEP = isLIPEP;
    }

    public boolean isFamilyMemberPEP() {
	return isFamilyMemberPEP;
    }

    public void setFamilyMemberPEP(boolean isFamilyMemberPEP) {
	this.isFamilyMemberPEP = isFamilyMemberPEP;
    }

    public String getSpecifyFamilyMembers() {
	return specifyFamilyMembers;
    }

    public void setSpecifyFamilyMembers(String specifyFamilyMembers) {
	this.specifyFamilyMembers = specifyFamilyMembers;
    }

    public String getPoliticalExperience() {
	return politicalExperience;
    }

    public void setPoliticalExperience(String politicalExperience) {
	this.politicalExperience = politicalExperience;
    }

    public String getAffiliationsToPoliticalparty() {
	return affiliationsToPoliticalparty;
    }

    public void setAffiliationsToPoliticalparty(String affiliationsToPoliticalparty) {
	this.affiliationsToPoliticalparty = affiliationsToPoliticalparty;
    }

    public String getRoleInPoliticalParty() {
	return roleInPoliticalParty;
    }

    public void setRoleInPoliticalParty(String roleInPoliticalParty) {
	this.roleInPoliticalParty = roleInPoliticalParty;
    }

    public String getRoleOthers() {
	return roleOthers;
    }

    public void setRoleOthers(String roleOthers) {
	this.roleOthers = roleOthers;
    }

    public String getPortfolioHandled() {
	return portfolioHandled;
    }

    public void setPortfolioHandled(String portfolioHandled) {
	this.portfolioHandled = portfolioHandled;
    }

    public String getPartyInPower() {
	return partyInPower;
    }

    public void setPartyInPower(String partyInPower) {
	this.partyInPower = partyInPower;
    }

    public String getPepEverPostedInForeignOffice() {
	return pepEverPostedInForeignOffice;
    }

    public void setPepEverPostedInForeignOffice(String pepEverPostedInForeignOffice) {
	this.pepEverPostedInForeignOffice = pepEverPostedInForeignOffice;
    }

    public String getForeignOfficeDetails() {
	return foreignOfficeDetails;
    }

    public void setForeignOfficeDetails(String foreignOfficeDetails) {
	this.foreignOfficeDetails = foreignOfficeDetails;
    }

    public String getIncomeSources() {
	return incomeSources;
    }

    public void setIncomeSources(String incomeSources) {
	this.incomeSources = incomeSources;
    }

    public String getPepConvicted() {
	return pepConvicted;
    }

    public void setPepConvicted(String pepConvicted) {
	this.pepConvicted = pepConvicted;
    }

    public String getConvictionDetails() {
	return convictionDetails;
    }

    public void setConvictionDetails(String convictionDetails) {
	this.convictionDetails = convictionDetails;
    }

    public boolean isProposerPEP() {
	return isProposerPEP;
    }

    public void setProposerPEP(boolean isProposerPEP) {
	this.isProposerPEP = isProposerPEP;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PEPDetails [isProposerPEP=" + isProposerPEP + ", isPayorPep=" + isPayorPep + ", isLIOrNomineePEP=" + isLIOrNomineePEP + ", isLIPEP=" + isLIPEP
		+ ", isFamilyMemberPEP=" + isFamilyMemberPEP + ", specifyFamilyMembers=" + specifyFamilyMembers + ", politicalExperience=" + politicalExperience
		+ ", affiliationsToPoliticalparty=" + affiliationsToPoliticalparty + ", roleInPoliticalParty=" + roleInPoliticalParty + ", roleOthers="
		+ roleOthers + ", portfolioHandled=" + portfolioHandled + ", partyInPower=" + partyInPower + ", pepEverPostedInForeignOffice="
		+ pepEverPostedInForeignOffice + ", foreignOfficeDetails=" + foreignOfficeDetails + ", incomeSources=" + incomeSources + ", pepConvicted="
		+ pepConvicted + ", convictionDetails=" + convictionDetails + "]";
    }

}