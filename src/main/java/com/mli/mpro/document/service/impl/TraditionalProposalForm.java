package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.mapper.GSTWaiverDeclarationMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.PosvDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Component("tradProposalForm")
@EnableAsync
public class TraditionalProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(TraditionalProposalForm.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private PersonalDetailsMapper personDetailsMapper;

    @Autowired
    private DeclarationMapper declarationMapper;

    @Autowired
    private CoverageDetailsMapper coverageDetailsMapper;

    @Autowired
    private LifeInsuredDetailsMapper lifeInsuredDetailsMapper;

    @Autowired
    private MedicalDetailsMapper medicalDetailsMapper;

	@Autowired
	private AnnexureMedicalQuesDetailsMapper annexureMedicalQuesDetailsMapper;

	@Autowired
	private GSTWaiverDeclarationMapper gstWaiverDeclarationMapper;
	@Autowired
	private PPHINomineeDetailsMapper pphINomineeDetailsMapper;

	@Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

	DocumentStatusDetails documentStatusDetails = null;
	String documentUploadStatus = "";
	long transactionId = proposalDetails.getTransactionId();
	int retryCount = 0;
	long requestedTime = System.currentTimeMillis();
	logger.info("Proposal Form document generation is initiated for transactionId {} and at applicationStage {}", transactionId,
		proposalDetails.getApplicationDetails().getStage());
	try {
		boolean isSspSwissReCase = Utility.isSSPSwissReCase(proposalDetails);
	    Context personalDetailscontext = personDetailsMapper.setDataForPersonalDetails(proposalDetails);
	    Context declarationContext = declarationMapper.setDataOfDeclaration(proposalDetails);
	    Context coverageDetailsCtx = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
	    Context lifeInsuredCtx = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
	    Context medicalDetailsctx = medicalDetailsMapper.setMedicalData(proposalDetails);
		Context gstWaiverDeclarationContext = gstWaiverDeclarationMapper.setDataOfGSTWaiverDeclarationDocument(proposalDetails);
		Context pphINomineeDetailsCtx = pphINomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);
		Context annexureMedicalDetailsctx =null;
		if(isSspSwissReCase){
			annexureMedicalDetailsctx = annexureMedicalQuesDetailsMapper.setAnnexureData(proposalDetails);
		}
	    Map<String, Object> completeDetails = new HashMap<>();
	    completeDetails = setDataForProposalForm(proposalDetails, completeDetails);
		boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		String productId = productDetails.getProductInfo().getProductId();
		String variant = productDetails.getProductInfo().getVariant();
		String processedHtmlPersonalDetails;
		String processedHtmlDeclaration;
		String processedHTMLCoverageHTML;
		String processedHTMLlifeInsured;
		String processedHTMLMedicalDetails;
		String processedHTMLAnnexureMedicalDetails = "";
		String gstWaiverDeclarationDetails = null;
		String processedHtmlPPHINomineeDetails;
        String pfTemplate;
		//FUL2- 14812 changes POS PF
        //FUL2-36693 adding short and long term variants in swp pos seller
        if(isPosSeller && (AppConstants.SWAG.equals(productId) || ((AppConstants.SWP_PRODUCTCODE.equals(productId))
				&& (AppConstants.LUMP_SUM.equalsIgnoreCase(variant)
				|| AppConstants.SHORTTERM_INCOME.equalsIgnoreCase(variant)
				|| AppConstants.LONGTERM_INCOME.equalsIgnoreCase(variant))))){
			 pfTemplate= "POS_ProposalForm";
			 processedHtmlPersonalDetails = springTemplateEngine.process("POS_PersonalDetails", personalDetailscontext);
			 processedHtmlDeclaration = springTemplateEngine.process("POS_PFDeclaration", declarationContext);
			 processedHTMLCoverageHTML = springTemplateEngine.process("POS_CoverageDetails", coverageDetailsCtx);
			 processedHTMLlifeInsured = springTemplateEngine.process("POS_LifeInsuredDetails", lifeInsuredCtx);
			 processedHTMLMedicalDetails = springTemplateEngine.process("POS_MedicalDetails", medicalDetailsctx);
		}else{
			 pfTemplate= "Trad_ProposalForm";
			 processedHtmlPersonalDetails = springTemplateEngine.process("PersonalDetails", personalDetailscontext);
			 processedHtmlDeclaration = springTemplateEngine.process("PFDeclaration", declarationContext);
			 processedHTMLCoverageHTML = springTemplateEngine.process("CoverageDetails", coverageDetailsCtx);
			 processedHTMLlifeInsured = springTemplateEngine.process("LifeInsuredDetails", lifeInsuredCtx);
			 gstWaiverDeclarationDetails = springTemplateEngine.process("nri\\gstWaiverDeclarationDetails", gstWaiverDeclarationContext);
			 if(isSspSwissReCase){
				 processedHTMLAnnexureMedicalDetails = springTemplateEngine.process("AnnexureMedicalDetails", annexureMedicalDetailsctx);
			 }
			processedHTMLMedicalDetails = springTemplateEngine.process("MedicalDetails", medicalDetailsctx);
		}
		processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);
	    Context proposalFormDetailsCtx = new Context();
	    completeDetails.put("personalDetails", processedHtmlPersonalDetails);
	    completeDetails.put("declarationDetails", processedHtmlDeclaration);
	    completeDetails.put("coverageDetails", processedHTMLCoverageHTML);
	    completeDetails.put("lifeInsuredDetails", processedHTMLlifeInsured);
	    completeDetails.put("medicalDetails", processedHTMLMedicalDetails);
		if(FeatureFlagUtil.isFeatureFlagEnabled(FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION)
				&& null != proposalDetails.getApplicationDetails().getFormType()
				&& !(AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()))
				&& AppConstants.NRI.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getResidentialStatus())
				&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
			completeDetails.put("gstWaiverDeclarationDetails", gstWaiverDeclarationDetails);
		}
		if(isSspSwissReCase){
			completeDetails.put("AnnexureMedicalDetails", processedHTMLAnnexureMedicalDetails);
		}
		completeDetails.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
		completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
		completeDetails.put("pdfVersion",declarationContext.getVariable("pdfVersion"));
		completeDetails.put("isNotYBLProposal", declarationContext.getVariable("isNotYBLProposal"));
	    proposalFormDetailsCtx.setVariables(completeDetails);
	    String proposalFormProcessedHTML = springTemplateEngine.process(pfTemplate, proposalFormDetailsCtx);

		String pdfDocumentOrDocumentStatus = documentHelper.generatePDFDocument(proposalFormProcessedHTML, 0);

	    if (pdfDocumentOrDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {


		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, pdfDocumentOrDocumentStatus,
			AppConstants.PROPOSAL_FORM_DOCUMENT);
			documentStatusDetails =	documentHelper.getDocumentStatusDetails(proposalDetails, transactionId, retryCount, documentRequestInfo);
	    }
	} catch (UserHandledException ex) {
	    logger.error("Proposal Form document generation failed:", ex);
	    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Proposal Form Document generation failed:", ex);
	    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	}

	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("Proposal Form document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

    private Map<String, Object> setDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

	logger.info("fetching data for proposal form main...");
	String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
	String purposeOfInsurance = Utility.setDefaultValuePosSeller(proposalDetails);
	String objectiveOfInsurance = proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance();
	String exitingCustomer = (proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() != null
		&& org.apache.commons.lang3.StringUtils.isNotBlank(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber())) ? "YES" : "NO";
	String previousPolicyNumber = "YES".equalsIgnoreCase(exitingCustomer)
		? "YES-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
	String channel = proposalDetails.getChannelDetails().getChannel();
	String customerId = !StringUtils.isEmpty(proposalDetails.getBancaDetails()) ? proposalDetails.getBancaDetails().getCustomerId() : "";
	String productSolution = (!StringUtils.isEmpty(proposalDetails.getSalesStoriesProductDetails())
		&& "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ? "YES" : "N/A";
	String gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
	String photoType =  proposalDetails.getAdditionalFlags().getPhotoType();
	String payorImageURL = null;
	if (proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()) {
	    dataVariables.put(AppConstants.PAYOR, "YES");
		if(!AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())) {
			payorImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), AppConstants.PAYOR, channel,
					proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
		}
	    dataVariables.put("parameter", AppConstants.PAYOR);
	    dataVariables.put("payorImage", payorImageURL);

	}
	String proposerImageURL = null;
	if(!AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())){
		String imageType  = Utility.imageType(proposalDetails) ;
		proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
				proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());

	}
	if (channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
	    gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
	}
	
	 // FUL2-13674 photo supress with blank 
    if (!StringUtils.isEmpty(photoType) && AppConstants.DOC_TYPE.equalsIgnoreCase(photoType)) {
		proposerImageURL= "";
	}
	//FUL2-202646 Go Green PF
	String physicalDocument = Optional.ofNullable(proposalDetails).map(ProposalDetails::getPosvDetails).map(PosvDetails::getGoGreen).isPresent()
				                    ?AppConstants.YES.equalsIgnoreCase(proposalDetails.getPosvDetails().getGoGreen())
				                     ?AppConstants.CAMEL_NO:AppConstants.CAMEL_YES
				                    :AppConstants.CAMEL_YES;
	String nonNriFinalStage = Utility.finalStageNationalityCheck(proposalDetails);
	dataVariables.put(AppConstants.FINAL_STAGE,nonNriFinalStage);
	dataVariables.put(AppConstants.PHYSICAL_POLICY,physicalDocument);
	dataVariables.put(AppConstants.PAYOR, proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO");
	dataVariables.put("payorImageNA", "Image not available");
	dataVariables.put("proposalNumber", policyNumber);
	dataVariables.put("gocaBrokerCode", gocaBrokerCode);
	dataVariables.put("comboProposalNumber", "N/A");
	dataVariables.put("customerId", customerId);
	dataVariables.put("channel", channel);
	dataVariables.put("purposeOfInsurance", purposeOfInsurance);
	dataVariables.put("objectiveOfInsurance", objectiveOfInsurance);
	dataVariables.put("productSolution", productSolution);
	dataVariables.put("affinityCustomer", "N/A");
	dataVariables.put("existingCustomer", exitingCustomer);
	dataVariables.put("proposerImage", proposerImageURL);
	dataVariables.put("existingPolicyNumber", previousPolicyNumber);

	// FUL2-46299_FUL2-52053 and FUL2-52547
	if (Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
		dataVariables.put(AppConstants.EXISTING_POLICY_NUMBER_KEY,
				proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum());
		dataVariables.put(AppConstants.COMBO_PROPOSAL_NUMBER,
				proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum());
	}

	return dataVariables;
    }
}
