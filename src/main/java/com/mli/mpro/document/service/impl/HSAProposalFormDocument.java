package com.mli.mpro.document.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.mapper.GSTWaiverDeclarationMapper;
import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.PosvDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

/**
 * Proposal Form Document Generator class for HSA type products
 * @author SayanGhosh
 *
 */

@Component("hsaProposalFormDocument")
@EnableAsync
public class HSAProposalFormDocument implements DocumentGenerationservice {

	private static final Logger logger = LoggerFactory.getLogger(HSAProposalFormDocument.class);

	@Autowired
	SpringTemplateEngine springTemplateEngine;
	@Autowired
	private DocumentHelper documentHelper;
	@Autowired
	private HSAPersonalDetailsMapper hsaPersonalDetailsMapper;
	@Autowired
	private HSACoverageDetailsMapper hsaCoverageDetailsMapper;
	@Autowired
	private HSALifeInsuredDetailsMapper hsaLifeInsuredDetailsMapper;
	@Autowired
	private HSAMedicalDetailsMapper hsaMedicalDetailsMapper;
	@Autowired
	private HSADeclarationMapper hsaDeclarationMapper;
	@Autowired
	private GSTWaiverDeclarationMapper gstWaiverDeclarationMapper;
	@Autowired
	private PPHINomineeDetailsMapper pphINomineeDetailsMapper;


	@Override
	public void generateDocument(ProposalDetails proposalDetails) {
		long requestedTime = System.currentTimeMillis();
		long transactionId = proposalDetails.getTransactionId();
		logger.info("START generateDocument for transactionId {} at applicationStage {}", transactionId, proposalDetails.getApplicationDetails().getStage());
		DocumentStatusDetails documentStatusDetails = null;
		int retryCount = 0;
		try {
			Context personalDetailscontext = hsaPersonalDetailsMapper.mapDataForPersonalDetails(proposalDetails);
		    Context declarationContext = hsaDeclarationMapper.mapDataForDeclarationDetails(proposalDetails);
		    Context coverageDetailsCtx = hsaCoverageDetailsMapper.mapDataForCoverageDetails(proposalDetails);
		    Context lifeInsuredCtx = hsaLifeInsuredDetailsMapper.mapDataForLifeInsureDetails(proposalDetails);
		    Context medicalDetailsctx = hsaMedicalDetailsMapper.mapDataForMedicalDetails(proposalDetails);
			Context gstWaiverDeclarationContext = gstWaiverDeclarationMapper.setDataOfGSTWaiverDeclarationDocument(proposalDetails);
			Context pphINomineeDetailsCtx = pphINomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

		    Map<String, Object> completeDetails = new HashMap<>();
		    completeDetails = setDataForProposalForm(proposalDetails, completeDetails);
			String processedHtmlPersonalDetails;
			String processedHtmlDeclaration;
			String processedHTMLCoverageHTML;
			String processedHTMLlifeInsured;
			String processedHTMLMedicalDetails;
			String pfTemplate;
			String gstWaiverDeclarationDetails = null;
			pfTemplate= "hsa\\HSAProposalForm";
			processedHtmlPersonalDetails = springTemplateEngine.process("hsa\\HSAPersonalDetails", personalDetailscontext);
			processedHtmlDeclaration = springTemplateEngine.process("hsa\\HSAPFDeclaration", declarationContext);
			processedHTMLCoverageHTML = springTemplateEngine.process("hsa\\HSACoverageDetails", coverageDetailsCtx);
			processedHTMLlifeInsured = springTemplateEngine.process("hsa\\HSALifeInsuredDetails", lifeInsuredCtx);
			processedHTMLMedicalDetails = springTemplateEngine.process("hsa\\HSAMedicalDetails", medicalDetailsctx);
			gstWaiverDeclarationDetails = springTemplateEngine.process("nri\\gstWaiverDeclarationDetails", gstWaiverDeclarationContext);
			String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);
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
			completeDetails.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
			completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
			completeDetails.put("pdfVersion",declarationContext.getVariable("pdfVersion"));
			completeDetails.put("isNotYBLProposal",declarationContext.getVariable("isNotYBLProposal"));
		    proposalFormDetailsCtx.setVariables(completeDetails);
		    String proposalFormProcessedHTML = springTemplateEngine.process(pfTemplate, proposalFormDetailsCtx);

			String pdfDocumentOrDocumentStatus = documentHelper.generatePDFDocument(proposalFormProcessedHTML, 0);

		    if (pdfDocumentOrDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
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
		    logger.error("Error while HSA pf document generation:", ex);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
			    proposalDetails.getApplicationDetails().getStage());
		} catch (Exception ex) {
		    logger.error("HSA pf document generation failed:", ex);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
			    proposalDetails.getApplicationDetails().getStage());
		}
		documentHelper.updateDocumentStatus(documentStatusDetails);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("END generateDocument for transactionId {} took {} miliseconds", proposalDetails.getTransactionId(), processedTime);

	}

	private Map<String, Object> setDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

		logger.info("fetching data for proposal form main...");
		String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
		String purposeOfInsurance = Utility.setDefaultValuePosSeller(proposalDetails);
		String objectiveOfInsurance = proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance();
		String exitingCustomer = (proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() != null
			&& StringUtils.hasLength(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber())) ? "YES" : "NO";
		String previousPolicyNumber = "YES".equalsIgnoreCase(exitingCustomer)
			? "YES-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
		String channel = proposalDetails.getChannelDetails().getChannel();
		String customerId = Objects.nonNull(proposalDetails.getBancaDetails()) ? proposalDetails.getBancaDetails().getCustomerId() : "";
		String productSolution = (Objects.nonNull(proposalDetails.getSalesStoriesProductDetails())
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
			String imageType = Utility.imageType(proposalDetails);
			proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
					proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());

		}
		if (channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
		    gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
		}

		 // FUL2-13674 photo supress with blank
	    if (Utility.andTwoExpressions(StringUtils.hasLength(photoType), AppConstants.DOC_TYPE.equalsIgnoreCase(photoType))){
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

		return dataVariables;
	    }

}
