package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.location.models.RuralUrbanDetails;
import com.mli.mpro.onboarding.medicalSchedule.model.MedicalGridTPAIdentifierDetails;
import com.mli.mpro.routingposv.models.PersistencyModelDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * This class is used to store all the service responses related to underwriting
 * services..
 *
 */
@JsonPropertyOrder({ "dedupeDetails" })
public class UnderwritingServiceDetails {

    @JsonProperty("dedupeDetails")
    private List<DedupeDetails> dedupeDetails;

    private ClientPolicyDetails clientPolicyProposerDetails;

    private ClientPolicyDetails clientPolicyInsurerDetails;

    @Sensitive(MaskType.MASK_ALL)
    private MedicalGridDetails medicalGridDetails;

    private MedicalGridTPAIdentifierDetails medicalGridTPAIdentifierDetails;

    private ProposalFormRuleDetails proposalFormRuleDetails;

    private CibilDetails cibilDetails;

    private CreditBureauStatus companyCreditBureauStatus;

    private CreditBureauStatus authorizedCreditBureauStatus;

    private IIBResponseDetails iibResDetails;

    private CibilDetails companyCibilDetails;

    private CibilDetails authorizeSignatoryCibilDetails;

    private CersaiCkycDataCompareResponse cersaiCkycDataCompareResponse;

    private FinancialGridDetails financialGridDetails;

    @Sensitive(MaskType.MASK_ALL)
    private MSAFSADetails msaFsaDetails;

    private UnderwritingStatus underwritingStatus;

    private MedicalShedulingDetails medicalShedulingDetails;

    private MiscellaneousRuleStatus miscellaneousRuleStatus;

    private RiskScoreDetails riskScoreDetails;

    private URMURuleStatus urmuRuleStatus;

    private CreditBureauStatus creditBureauStatus;

    private SmartTermPlanCounter smartTermPlanCounter;

    private RuralUrbanDetails ruralUrbanDetails;

    private PersistencyModelDetails persistencyModelDetails;

    @JsonProperty("initiativeTypeBRMSRule")
    private InitiativeTypeBRMSRuleDetails initiativeTypeBRMSRuleDetails;
    //FUL2-224962 financial frictionless
    @JsonProperty("centralServiceResult")
    private CentralServiceResult centralServiceResult;
    
    public UnderwritingServiceDetails() {
	super();
    }

    /**
     * @return the dedupeDetails
     */
    public List<DedupeDetails> getDedupeDetails() {
	return dedupeDetails;
    }

    /**
     * @param dedupeDetails the dedupeDetails to set
     */
    public void setDedupeDetails(List<DedupeDetails> dedupeDetails) {
	this.dedupeDetails = dedupeDetails;
    }

  public CersaiCkycDataCompareResponse getCersaiCkycDataCompareResponse() {
    return cersaiCkycDataCompareResponse;
  }

  public void setCersaiCkycDataCompareResponse(
      CersaiCkycDataCompareResponse cersaiCkycDataCompareResponse) {
    this.cersaiCkycDataCompareResponse = cersaiCkycDataCompareResponse;
  }
    public MedicalGridTPAIdentifierDetails getMedicalGridTPAIdentifierDetails() {
        return medicalGridTPAIdentifierDetails;
    }

    public void setMedicalGridTPAIdentifierDetails(MedicalGridTPAIdentifierDetails medicalGridTPAIdentifierDetails) {
        this.medicalGridTPAIdentifierDetails = medicalGridTPAIdentifierDetails;
    }

    /**
     * @return the clientPolicyProposerDetails
     */
    public ClientPolicyDetails getClientPolicyProposerDetails() {
	return clientPolicyProposerDetails;
    }

    /**
     * @param clientPolicyProposerDetails the clientPolicyProposerDetails to set
     */
    public void setClientPolicyProposerDetails(ClientPolicyDetails clientPolicyProposerDetails) {
	this.clientPolicyProposerDetails = clientPolicyProposerDetails;
    }

    /**
     * @return the clientPolicyInsurerDetails
     */
    public ClientPolicyDetails getClientPolicyInsurerDetails() {
	return clientPolicyInsurerDetails;
    }

    /**
     * @param clientPolicyInsurerDetails the clientPolicyInsurerDetails to set
     */
    public void setClientPolicyInsurerDetails(ClientPolicyDetails clientPolicyInsurerDetails) {
	this.clientPolicyInsurerDetails = clientPolicyInsurerDetails;
    }

    /**
     * @return the medicalGridDetails
     */
    public MedicalGridDetails getMedicalGridDetails() {
	return medicalGridDetails;
    }

    /**
     * @param medicalGridDetails the medicalGridDetails to set
     */
    public void setMedicalGridDetails(MedicalGridDetails medicalGridDetails) {
	this.medicalGridDetails = medicalGridDetails;
    }

    /**
     * @return the proposalFormDetails
     */
    public ProposalFormRuleDetails getProposalFormRuleDetails() {
	return proposalFormRuleDetails;
    }

    /**
     * @param proposalFormRuleDetails the financialGridDetails to set
     */

    public void setProposalFormRuleDetails(ProposalFormRuleDetails proposalFormRuleDetails) {
	this.proposalFormRuleDetails = proposalFormRuleDetails;
    }

    /**
     * @return the financialGridDetails
     */
    public FinancialGridDetails getFinancialGridDetails() {
	return financialGridDetails;
    }

    public CibilDetails getCibilDetails() {
	return cibilDetails;
    }

    public void setCibilDetails(CibilDetails cibilDetails) {
	this.cibilDetails = cibilDetails;
    }

    public CreditBureauStatus getCompanyCreditBureauStatus() {
        return companyCreditBureauStatus;
    }

    public void setCompanyCreditBureauStatus(CreditBureauStatus companyCreditBureauStatus) {
        this.companyCreditBureauStatus = companyCreditBureauStatus;
    }

    public CreditBureauStatus getAuthorizedCreditBureauStatus() {
        return authorizedCreditBureauStatus;
    }

    public void setAuthorizedCreditBureauStatus(CreditBureauStatus authorizedCreditBureauStatus) {
        this.authorizedCreditBureauStatus = authorizedCreditBureauStatus;
    }

    public IIBResponseDetails getIibResDetails() {
        return iibResDetails;
    }

    public CibilDetails getCompanyCibilDetails() {
        return companyCibilDetails;
    }

    public void setCompanyCibilDetails(CibilDetails companyCibilDetails) {
        this.companyCibilDetails = companyCibilDetails;
    }

    public CibilDetails getAuthorizeSignatoryCibilDetails() {
        return authorizeSignatoryCibilDetails;
    }

    public void setAuthorizeSignatoryCibilDetails(CibilDetails authorizeSignatoryCibilDetails) {
        this.authorizeSignatoryCibilDetails = authorizeSignatoryCibilDetails;
    }

    /**
     * @param financialGridDetails the financialGridDetails to set
     */
    public void setFinancialGridDetails(FinancialGridDetails financialGridDetails) {
	this.financialGridDetails = financialGridDetails;
    }

    /**
     * @return the msaFsaDetails
     */
    public MSAFSADetails getMsaFsaDetails() {
	return msaFsaDetails;
    }

    /**
     * @param msaFsaDetails the msaFsaDetails to set
     */
    public void setMsaFsaDetails(MSAFSADetails msaFsaDetails) {
	this.msaFsaDetails = msaFsaDetails;
    }

    /**
     * @return the underwritingStatus
     */
    public UnderwritingStatus getUnderwritingStatus() {
	return underwritingStatus;
    }

    /**
     * @param underwritingStatus the underwritingStatus to set
     */
    public void setUnderwritingStatus(UnderwritingStatus underwritingStatus) {
	this.underwritingStatus = underwritingStatus;
    }

    public MedicalShedulingDetails getMedicalShedulingDetails() {
	return medicalShedulingDetails;
    }

    public void setMedicalShedulingDetails(MedicalShedulingDetails medicalShedulingDetails) {
	this.medicalShedulingDetails = medicalShedulingDetails;
    }

    public MiscellaneousRuleStatus getMiscellaneousRuleStatus() {
	return miscellaneousRuleStatus;
    }

    public void setMiscellaneousRuleStatus(MiscellaneousRuleStatus miscellaneousRuleStatus) {
	this.miscellaneousRuleStatus = miscellaneousRuleStatus;
    }

    public RiskScoreDetails getRiskScoreDetails() { return riskScoreDetails; }

    public void setRiskScoreDetails(RiskScoreDetails riskScoreDetails) {
        this.riskScoreDetails = riskScoreDetails;
    }

    public URMURuleStatus getUrmuRuleStatus() {
	return urmuRuleStatus;
    }

    public void setUrmuRuleStatus(URMURuleStatus urmuRuleStatus) {
	this.urmuRuleStatus = urmuRuleStatus;
    }

    public CreditBureauStatus getCreditBureauStatus() {
	return creditBureauStatus;
    }

    public void setCreditBureauStatus(CreditBureauStatus creditBureauStatus) {
	this.creditBureauStatus = creditBureauStatus;
    }

    public SmartTermPlanCounter getSmartTermPlanCounter() {
	return smartTermPlanCounter;
    }

    public void setSmartTermPlanCounter(SmartTermPlanCounter smartTermPlanCounter) {
	this.smartTermPlanCounter = smartTermPlanCounter;
    }

    public RuralUrbanDetails getRuralUrbanDetails() {
        return ruralUrbanDetails;
    }

    public void setRuralUrbanDetails(RuralUrbanDetails ruralUrbanDetails) {
        this.ruralUrbanDetails = ruralUrbanDetails;
    }

    public PersistencyModelDetails getPersistencyModelDetails() {
        return persistencyModelDetails;
    }

    public void setPersistencyModelDetails(PersistencyModelDetails persistencyModelDetails) {
        this.persistencyModelDetails = persistencyModelDetails;
    }

    @JsonProperty("initiativeTypeBRMSRule")
    public InitiativeTypeBRMSRuleDetails getInitiativeTypeBRMSRuleDetails() {
		return initiativeTypeBRMSRuleDetails;
	}

    @JsonProperty("initiativeTypeBRMSRule")
	public void setInitiativeTypeBRMSRuleDetails(InitiativeTypeBRMSRuleDetails initiativeTypeBRMSRuleDetails) {
		this.initiativeTypeBRMSRuleDetails = initiativeTypeBRMSRuleDetails;
	}

    public CentralServiceResult getCentralServiceResult() {
        return centralServiceResult;
    }

    public void setCentralServiceResult(CentralServiceResult centralServiceResult) {
        this.centralServiceResult = centralServiceResult;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "UnderwritingServiceDetails [dedupeDetails=" + dedupeDetails + ", clientPolicyProposerDetails=" + clientPolicyProposerDetails
		+ ", clientPolicyInsurerDetails=" + clientPolicyInsurerDetails + ", medicalGridDetails=" + medicalGridDetails + ", proposalFormRuleDetails="
		+ proposalFormRuleDetails + ", cibilDetails=" + cibilDetails + ", financialGridDetails=" + financialGridDetails + ", msaFsaDetails="
		+ msaFsaDetails + ", underwritingStatus=" + underwritingStatus + ", medicalShedulingDetails=" + medicalShedulingDetails
		+ ", miscellaneousRuleStatus=" + miscellaneousRuleStatus + ", riskScoreDetails=" + riskScoreDetails + ", urmuRuleStatus=" + urmuRuleStatus
		+ ", creditBureauStatus=" + creditBureauStatus + ", smartTermPlanCounter=" + smartTermPlanCounter + ", ruralUrbanDetails="
        + ruralUrbanDetails +", persistencyModelDetails=" + persistencyModelDetails+ ",companyCibilDetails ="+companyCibilDetails
        +",authorizeSignatoryCibilDetails="+authorizeSignatoryCibilDetails + ", companyCreditBureauStatus=" + companyCreditBureauStatus
            + ", authorizedCreditBureauStatus=" + authorizedCreditBureauStatus + ", iibResDetails=" + iibResDetails +", initiativeTypeBRMSRuleDetails =" + initiativeTypeBRMSRuleDetails
            +", centralServiceResult =" + centralServiceResult+"]";
    }

}
