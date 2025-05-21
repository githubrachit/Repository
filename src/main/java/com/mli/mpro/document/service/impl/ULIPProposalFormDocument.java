package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.mapper.GSTWaiverDeclarationMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ApplicationDetails;
import com.mli.mpro.proposal.models.DedupeDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.PosvDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

/**
 * @author akshom4375
 *
 */
@Component("ulipProposalFormDocument")
@EnableAsync
public class ULIPProposalFormDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(ULIPProposalFormDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private ULIPDeclarationMapper ulipDeclarationMapper;

    @Autowired
    private ULIPLifeInsuredMapper ulipLifeInsuredMapper;

    @Autowired
    private ULIPChildAndCoverageMapper ulipChildAndCoverageMapper;

    @Autowired
    private ULIPNomineeMapper ulipNomineeMapper;

    @Autowired
    private ULIPPersonalDetailsMapper ulipPersonalMapper;

    @Autowired
    private MedicalDetailsMapper ulipMedicalMapper;

	@Autowired
	private GSTWaiverDeclarationMapper gstWaiverDeclarationMapper;
	@Autowired
	private PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

	final String checkbox = "classpath:static/checkbox.png";

    /**
     * Method to generate ULIP PRoposal document. Spring Template Engine is used to bind the
     * data dynamically to the static HTML which is stored in templates folder.
     */
    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Generating ULIP proposal form document for transactionId {} {}",proposalDetails.getTransactionId(), "%m");
	DocumentStatusDetails documentStatusDetails = null;
	long transactionId = proposalDetails.getTransactionId();
	int retryCount = 0;
	String documentUploadStatus = "";
	long requestedTime = System.currentTimeMillis();

	try {
	    Map<String, Object> completeULIPProposalFormDetails = new HashMap<>();
	    Context medicalDetailsContext = ulipMedicalMapper.setMedicalData(proposalDetails);
	    Context personalDetailsContext = ulipPersonalMapper.setDataForProposalForm(proposalDetails);
	    Context coverageDetailsContext = ulipChildAndCoverageMapper.setDataOfCoverage(proposalDetails);
	    Context declarationContext = ulipDeclarationMapper.setDataOfDeclaration(proposalDetails);
	    Context ulipLifeInsuredContext = ulipLifeInsuredMapper.setDataOfLifeInsured(proposalDetails);
		Context gstWaiverDeclarationContext = gstWaiverDeclarationMapper.setDataOfGSTWaiverDeclarationDocument(proposalDetails);
		Context pphINomineeDetailsCtx = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

		String productName = proposalDetails.getProductDetails().get(0).getProductInfo().getProductName();
		logger.info("Setting ULIP context data in a dataMap");
		logger.info("Setting ULIP context data for ULIP product : {} ", productName);
		completeULIPProposalFormDetails = setDocumentData(proposalDetails, completeULIPProposalFormDetails);

		String processedHTMLPersonalDetails = null;
		String processedHTMLMedicalDetails = null;
		String processedHTMLNomineeDetails = null;
		String processedHTMLChildAndCoverageDetails = null;
		String processedHTMLDeclarationDetails = null;
		String processedHTMLLifeInsuredDetails = null;
		String gstWaiverDeclarationDetails = null;
		String finalProcessedHtml = null;
		logger.info("Setting ULIP Data transactionId {} productName : {}", proposalDetails.getTransactionId(), productName);
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		if (AppConstants.SELF.equalsIgnoreCase(formType)
			|| Utility.schemeBCase(formType, schemeType)) {
			Context nomineeDetailsContext = ulipNomineeMapper.setDataOfNominee(proposalDetails);
			processedHTMLNomineeDetails = springTemplateEngine.process("ulip\\nomineeDetails", nomineeDetailsContext);
			completeULIPProposalFormDetails.put("nomineeDetails", processedHTMLNomineeDetails);
		}

		processedHTMLPersonalDetails = springTemplateEngine.process("ulip\\personalDetails", personalDetailsContext);
		processedHTMLMedicalDetails = springTemplateEngine.process("ulip\\medicalDetails", medicalDetailsContext);
		processedHTMLChildAndCoverageDetails = springTemplateEngine.process("ulip\\childAndCoverageDetails", coverageDetailsContext);
		processedHTMLDeclarationDetails = springTemplateEngine.process("ulip\\declarationDetails", declarationContext);
		processedHTMLLifeInsuredDetails = springTemplateEngine.process("ulip\\insuredDetails", ulipLifeInsuredContext);
		gstWaiverDeclarationDetails = springTemplateEngine.process("nri\\gstWaiverDeclarationDetails", gstWaiverDeclarationContext);
		String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);

		Context ctx = new Context();
	    completeULIPProposalFormDetails.put("medicalDetails", processedHTMLMedicalDetails);
	    completeULIPProposalFormDetails.put("personalDetails", processedHTMLPersonalDetails);
	    completeULIPProposalFormDetails.put("childAndCoverageDetails", processedHTMLChildAndCoverageDetails);
	    completeULIPProposalFormDetails.put("insuredDetails", processedHTMLLifeInsuredDetails);
	    completeULIPProposalFormDetails.put("declarationDetails", processedHTMLDeclarationDetails);
		if(FeatureFlagUtil.isFeatureFlagEnabled(FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION)
				&& null != proposalDetails.getApplicationDetails().getFormType()
				&& !(AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()))
				&& AppConstants.NRI.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getResidentialStatus())
				&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
			completeULIPProposalFormDetails.put("gstWaiverDeclarationDetails", gstWaiverDeclarationDetails);
		}
		completeULIPProposalFormDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
//		completeULIPProposalFormDetails.put("pdfVersion",declarationContext.getVariable("pdfVersion"));
		completeULIPProposalFormDetails.put("newPdfVersion", declarationContext.getVariable("newPdfVersion"));
		completeULIPProposalFormDetails.put(NIFTY_FUND_FEATURE_FLAG, declarationContext.getVariable(NIFTY_FUND_FEATURE_FLAG));
		completeULIPProposalFormDetails.put("isNotYBLProposal", declarationContext.getVariable("isNotYBLProposal"));
		ctx.setVariables(completeULIPProposalFormDetails);
		logger.info("CompleteULIP ProposalForm {}", completeULIPProposalFormDetails);

	    logger.info("Spring Thymeleaf Engine to generate pdf with Context as I/P");

		finalProcessedHtml = springTemplateEngine.process("ulip\\main", ctx);
		
		logger.info("Final processedHtml  {}", finalProcessedHtml);

	    logger.info("Generating pdf from ULIP html...");
	    String pdfDocumentOrDocumentStatus = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
	    	    
	    if (pdfDocumentOrDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, pdfDocumentOrDocumentStatus,
			AppConstants.PROPOSAL_FORM_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
			AppConstants.PROPOSAL_FORM_DOCUMENTID, AppConstants.PROPOSAL_FORM_DOCUMENT, documentpayload);
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

			if (AppConstants.SUCCESS.equalsIgnoreCase(documentUploadStatus) && Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
				long transactionIdForCombo = proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() != 0l
						? proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() : proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId();
				documentDetails.setTransactionId(transactionIdForCombo);
				documentDetails.setDocumentName(AppConstants.COMBO_PROPOSAL_FORM_DOCUMENT);
				documentDetails.setMproDocumentId("COMBO_PROPOSAL_FORM_PDF");
				documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
			}
		if (StringUtils.equalsIgnoreCase(documentUploadStatus, AppConstants.FAILED)) {
		    // update the document upload failure status in db
		    logger.info("Document upload is failed for transactionId {} {}", transactionId, AppConstants.PROPOSAL_FORM_DOCUMENT);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
			    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.PROPOSAL_FORM_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		} else {
		    logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
			    AppConstants.PROPOSAL_FORM_DOCUMENT);
		    // update the document upload success status in db
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus,
			    0, AppConstants.PROPOSAL_FORM_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		}
	    }
	} catch (UserHandledException ex) {
	    logger.error("Error occurred while ULIP Proposal Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Error occurred while ULIP Proposal Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	}
	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("ULIP Proposal Form document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);    }

    /**
     * Setting data from input json for ULIP Proposal Document to be generated
     * 
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    private Map<String, Object> setDocumentData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) throws UserHandledException {
	logger.info("START data processing for main template of ULIP Proposal Form");
	try {
	    String existingPolicy = "";
	    String purposeOfInsurance = "";
	    String objectiveOfInsurance = "";
	    String productSolution = "";
	    String comboProposalNumber = "N/A";
	    String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
	    
	    List<ProductDetails> productDetailsList = proposalDetails.getProductDetails();
	    List<DedupeDetails> dedupeDetailsList = proposalDetails.getUnderwritingServiceDetails().getDedupeDetails();

	    if (!CollectionUtils.isEmpty(productDetailsList) ) {
		purposeOfInsurance = Utility.setDefaultValuePosSeller(proposalDetails);
		objectiveOfInsurance = productDetailsList.get(0).getObjectiveOfInsurance();
	    }
	    if ( !CollectionUtils.isEmpty(dedupeDetailsList) ) {
		existingPolicy = (dedupeDetailsList != null && StringUtils.isNotBlank(dedupeDetailsList.get(0).getPreviousPolicyNumber())) ? AppConstants.YES
			: AppConstants.NO;
	    }
	    String previousPolicyNumber = "YES".equalsIgnoreCase(existingPolicy)
			? "-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";

	    String channel = proposalDetails.getChannelDetails().getChannel();
	    String customerId = !StringUtils.isEmpty(proposalDetails.getBancaDetails().getCustomerId()) ? proposalDetails.getBancaDetails().getCustomerId() : AppConstants.BLANK;
	    String ssnCode = StringUtils.equalsIgnoreCase(channel, "BY") ? proposalDetails.getSourcingDetails().getAgentId()
		    : proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode();

	    String gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
	    String photoType =  proposalDetails.getAdditionalFlags().getPhotoType();
		String imageType  = Utility.imageType(proposalDetails) ;
	    String proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
		    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
	    String payorImageURL = null;
		if (proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()) {
		    dataVariables.put(AppConstants.PAYOR, "YES");
		    payorImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), AppConstants.PAYOR, channel,
			    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
		    dataVariables.put("parameter", AppConstants.PAYOR);
		    dataVariables.put("payorImage", payorImageURL);

		}
	    if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
		gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
	    }
	    
	    // FUL2-13674 photo supress with blank 
	    if (!StringUtils.isEmpty(photoType) && AppConstants.DOC_TYPE.equalsIgnoreCase(photoType)) {
			proposerImageURL= "";
		}
	    
	    productSolution = ((null != proposalDetails.getSalesStoriesProductDetails())
			&& "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ? "YES" : AppConstants.NA;
		//FUL2-202646 Go Green PF
		String physicalDocument = Optional.ofNullable(proposalDetails).map(ProposalDetails::getPosvDetails).map(PosvDetails::getGoGreen).isPresent()
				                      ?AppConstants.YES.equalsIgnoreCase(proposalDetails.getPosvDetails().getGoGreen())
				                       ?AppConstants.CAMEL_NO:AppConstants.CAMEL_YES
				                      :AppConstants.CAMEL_YES;
		String nonNriFinalStage = Utility.finalStageNationalityCheck(proposalDetails);
		dataVariables.put(AppConstants.FINAL_STAGE,nonNriFinalStage);
		dataVariables.put(AppConstants.PHYSICAL_POLICY,physicalDocument);
	    dataVariables.put("payor", proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO");
	    dataVariables.put("channel", channel);
	    dataVariables.put("objectiveOfInsurance", objectiveOfInsurance);
	    dataVariables.put("purposeOfInsurance", purposeOfInsurance);
	    dataVariables.put("customerId", customerId);
	    dataVariables.put("gocaBrokerCode", gocaBrokerCode);
	    dataVariables.put("policyNumber", policyNumber);
	    dataVariables.put("proposerImageURL", proposerImageURL);
	    dataVariables.put("ssnCode", ssnCode);
	    dataVariables.put("existingPolicy", StringUtils.isNotEmpty(existingPolicy) ? existingPolicy : AppConstants.NO);
	    dataVariables.put(AppConstants.COMBO_PROPOSAL_NUMBER, comboProposalNumber);
	    // FUL2-46299_FUL2-52053
		if (Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
			dataVariables.put(AppConstants.PREVIOUS_POLICY_NUMBER_KEY,
					proposalDetails.getSalesStoriesProductDetails().getPrimaryPolicyNumber());
			dataVariables.put(AppConstants.COMBO_PROPOSAL_NUMBER, proposalDetails.getSalesStoriesProductDetails().getPrimaryPolicyNumber());
		} else {
			dataVariables.put("previousPolicyNumber", previousPolicyNumber);
		}
	    dataVariables.put("productSolution", productSolution);

	    logger.info("END {}", "%m");
	    return dataVariables;
	} catch (Exception ex) {
	    logger.error("Data addition failed for ULIP Proposal Document:", ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("ULIP Document Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
}