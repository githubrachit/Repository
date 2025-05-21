package com.mli.mpro.document.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;

import com.mli.mpro.utils.Utility;
import com.mli.mpro.utils.UtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.axis.models.ProductSolutionMatrix;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.mapper.PSMDocumentMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SpeicifiedPersonDetails;
import com.mli.mpro.proposal.models.PSMDetails;
import com.mli.mpro.proposal.models.UnderwritingServiceDetails;

@Component("psmDocument")
@EnableAsync
public class PSMDocument implements DocumentGenerationservice {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;
    
    @Autowired
    private PSMDocumentMapper psmDocumentMapper;
	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private UtilityService utilityService;

    private static final Logger logger = LoggerFactory.getLogger(PSMDocument.class);
	public static final String FACT_FINDER = "Fact Finder";

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

	String channel = proposalDetails.getChannelDetails().getChannel();
	String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
	long requestedTime = System.currentTimeMillis();
	int retryCount = 0;
	String documentUploadStatus = StringUtils.EMPTY;
	long transactionId = proposalDetails.getTransactionId();
	DocumentStatusDetails documentStatusDetails = null;
	    try {
		String processedHtml = null;
		boolean isAxisCase = AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) && !AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(requestSource);
		if (isAxisCase && !isPSMDataAvailableInProposal(proposalDetails.getPsmDetails()) && !checkUpdatedPsmElegibility(proposalDetails)) {
				logger.info("PSM data is not available in axis case, generating old PSM doc transactionId {}", transactionId);
	     	Context context = setDataForDocument(proposalDetails);
	     	processedHtml = springTemplateEngine.process("PSM", context);
	    } else if ((isAxisCase && isPSMDataAvailableInProposal(proposalDetails.getPsmDetails())) ||
			         	!checkIsPSMUserUploaded(proposalDetails.getUnderwritingServiceDetails(), proposalDetails.getPsmDetails())) {
				logger.info("PSM data is available, generating new PSM doc transactionId {}", transactionId);
		Context context = psmDocumentMapper.setDataForDocument(proposalDetails);
			processedHtml = processPsmDocument(proposalDetails, context);
		}
		logger.info("Data binding with HTML is done for transactionId {}", transactionId);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Data binding with HTML for transactionId {} took {} miliseconds ", transactionId, processedTime);
		String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
		logger.info("HTML to pdf conversation is done for transactionId {}", transactionId);
		processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId, processedTime);
		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document generation failure status in db
		    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, FACT_FINDER,
			    proposalDetails.getApplicationDetails().getStage());
		} else {
			logger.info("PSM Document generation is success for transactionId {}", transactionId);
		    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, FACT_FINDER);
		    List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		    documentpayload.add(documentRequestInfo);
		    DocumentDetails documentDetails = new DocumentDetails(channel, transactionId, "FF_Pr", FACT_FINDER, documentpayload);
				documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
			if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
				documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
			}
			documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		    if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
			// update the document upload failure status in db
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
				proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
				AppConstants.DOCUMENT_UPLOAD_FAILED, 0, FACT_FINDER, proposalDetails.getApplicationDetails().getStage());
		    } else {
			logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, FACT_FINDER);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
				proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, FACT_FINDER,
				proposalDetails.getApplicationDetails().getStage());
		    }
		}
		
	    } catch (UserHandledException ex) {
		logger.error("PSM Document generation failed: ", ex);
		documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, FACT_FINDER,
			proposalDetails.getApplicationDetails().getStage());
		documentHelper.updateDocumentStatus(documentStatusDetails);
	    } catch (Exception ex) {
		logger.error("PSM Document generation failed: ", ex);
		documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, FACT_FINDER,
			proposalDetails.getApplicationDetails().getStage());
	    }
	    documentHelper.updateDocumentStatus(documentStatusDetails);
	    long processedTime = (System.currentTimeMillis() - requestedTime);
	    logger.info("PSM document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);

    }
    //FUL2-247470 PSM Document template Updation
	private String processPsmDocument(ProposalDetails proposalDetails, Context context) {
		if (checkUpdatedPsmElegibility(proposalDetails)) {
			logger.info("PSM_MPRO data is available, generating new PSM_MPRO doc transactionId {}", proposalDetails.getTransactionId());
			return springTemplateEngine.process("PSM_MPRO/PSM_MPRO", context);
		}
		return springTemplateEngine.process("PSMB2C", context);
	}

	private boolean checkUpdatedPsmElegibility(ProposalDetails proposalDetails) {
		return Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.PSM_FEATURE_FLAG_NAME_MPRO)) && checkWipcaseAndMproElegiblity(proposalDetails);
	}

	private boolean checkWipcaseAndMproElegiblity(ProposalDetails proposalDetails) {
     return Objects.nonNull(proposalDetails.getChannelDetails()) && !AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
			 && compareWipCaseDate(proposalDetails.getTransactionId(),proposalDetails.getApplicationDetails().getCreatedTime(),AppConstants.PSM_MPRO_WIP_CASE_DATE);
	}
	private boolean compareWipCaseDate(long transactionId,Date createdTime,String key) {
		try {
			logger.info("Comparing WIP Case Date for transactionId: {}",transactionId);
			String dateStr = configurationRepository.findByKey(key).getValue();
			logger.info("WIP Case Date from Configuration: {}", dateStr);
			String pattern = AppConstants.UTC_DATE_FORMAT;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone(AppConstants.UTC));
			Date wipCaseDate = simpleDateFormat.parse(dateStr);
			Date policyDate = createdTime;
			return policyDate.after(wipCaseDate);
		} catch (Exception e) {
			logger.error("Error occurred while comparing WIP Case Date for transactionId: {}",transactionId,e);
			return false;
		}
	}

    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

	Context context = new Context();
	logger.info("Data Mapping is initiated for transactionId {}", proposalDetails.getTransactionId());
	try {
	    Map<String, Object> dataForDocument = new HashMap<>();
	    BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
	    ProductSolutionMatrix productSuitablityMatrix = proposalDetails.getBancaDetails().getProductSolutionMatrix();
	    SpeicifiedPersonDetails speicifiedPersonDetails = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails();
	    String gender = proposerBasicDetails.getGender();
	    long transactionId = proposalDetails.getTransactionId();
	    String checkbox = "classpath:static/checkbox.png";

	    String sellerName = StringUtils.EMPTY;
		String sellerCode = StringUtils.EMPTY;
		String tag =Utility.evaluateConditionalOperation("F".equals(gender),"MS",
				Utility.evaluateConditionalOperation("Others".equalsIgnoreCase(gender),
						"MX","MR"));

	    if (!StringUtils.isEmpty(productSuitablityMatrix.getIncomeRange())) {
		double incomeRange = Double.valueOf(productSuitablityMatrix.getIncomeRange());
		if (incomeRange < 500000) {
		    dataForDocument.put("checkBox1", checkbox);
		} else if (Utility.andTwoExpressions(incomeRange >= 500000 , incomeRange < 1000000)) {
		    dataForDocument.put("checkBox2", checkbox);
		} else {
		    dataForDocument.put("checkBox3", checkbox);
		}
	    }
	    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	    formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
	    String currentdate = formatter.format(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate());
	    String firstName = proposerBasicDetails.getFirstName();
	    String middleName = proposerBasicDetails.getMiddleName();
	    String lastName = proposerBasicDetails.getLastName();
	    String customerName = Stream.of(tag, firstName, middleName, lastName).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
	    String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
		//FUL2-46310 
	    policyNumber = Utility.getSecondaryPolicyNumber(proposalDetails, policyNumber);
		policyNumber = Utility.getPrimaryPolicyNumber(proposalDetails, policyNumber);
	    String lifeStage = productSuitablityMatrix.getLifeStage();
	    String goal = productSuitablityMatrix.getGoalSelected();
	    String productRecommended = productSuitablityMatrix.getProductsRecommended().replace("#", ",");
	    String productSelected = proposalDetails.getProductDetails().get(0).getProductInfo().getProductName();
	    
	    /* FUL2-39332 -  we are showing only SWP product for Agency and YBL POS in the productRecommended section */
		//FUL2-135547_Setup_of_DCB_Bank_in_Mpro
		if((AppConstants.CHANNEL_AGENCY.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| AppConstants.CHANNEL_CAT.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())        //FUL2-123815
				|| AppConstants.CHANNEL_YBL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| Utility.checkBrokerTmbChannel(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getSourcingDetails().getGoCABrokerCode())|| utilityService.replicaOfUjjivanChannel(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getSourcingDetails().getGoCABrokerCode()))
				&& proposalDetails.getSourcingDetails().isPosSeller()) {
			productRecommended = productSelected;
		}
	    /* FUL2-39332 -  we are showing only SWP product for Agency and YBL POS in the productRecommended section */
	    
	    String currentGoal = !(StringUtils.isEmpty(productSuitablityMatrix.getCurrentCostOfGoal()))
		    ? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getCurrentCostOfGoal()))) : StringUtils.EMPTY;

	    String monthlysavings = !StringUtils.isEmpty(productSuitablityMatrix.getRecurringMonthlySavingsForGoal())
		    ? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getRecurringMonthlySavingsForGoal()))) : StringUtils.EMPTY;

		String annualRate = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getExpectedAnnualRateOfInflation())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getExpectedAnnualRateOfInflation()))) : StringUtils.EMPTY;
		String annualReturn = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getExpectedAnnualReturnOnInvestment())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getExpectedAnnualReturnOnInvestment()))) : StringUtils.EMPTY;
		String futureCost = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getFutureCostOfGoal())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getFutureCostOfGoal()))) : StringUtils.EMPTY;
		String savings = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getSuggestedSavingsPA())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getSuggestedSavingsPA()))) : StringUtils.EMPTY;
		String commitment = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getCommitmentPA())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getCommitmentPA()))) : StringUtils.EMPTY;
		String currentAge = productSuitablityMatrix.getCurrentAgeOfInsured();
	    String goalPeriod = proposalDetails.getProductDetails().get(0).getProductInfo().getPolicyTerm();
	    String paymentPeriod = proposalDetails.getProductDetails().get(0).getProductInfo().getPremiumPaymentTerm();
		String lumpSumsaving = !org.springframework.util.StringUtils.isEmpty(productSuitablityMatrix.getLumpSumSavingsForGoal())
				? String.valueOf(roundOffValue(Double.valueOf(productSuitablityMatrix.getLumpSumSavingsForGoal()))) : StringUtils.EMPTY;
		if(speicifiedPersonDetails!=null) {
			sellerName= Utility.evaluateConditionalOperation(!StringUtils.isEmpty(speicifiedPersonDetails.getSpName()) , speicifiedPersonDetails.getSpName(),AppConstants.BLANK);
			sellerCode=Utility.evaluateConditionalOperation(!StringUtils.isEmpty(speicifiedPersonDetails.getSpSSNCode()) , speicifiedPersonDetails.getSpSSNCode(),AppConstants.BLANK);
		}
		

	    dataForDocument.put("CustomerName", customerName);
	    dataForDocument.put("currentDate", currentdate);
	    dataForDocument.put("transactionId", transactionId);
	    dataForDocument.put("policyNumber", policyNumber);
	    dataForDocument.put("LifeStage", lifeStage);
	    dataForDocument.put("Goals", goal);
	    dataForDocument.put("productsRecomm", productRecommended);
	    dataForDocument.put("ProductSelected", productSelected);
	    dataForDocument.put("age", currentAge);
	    dataForDocument.put("CostForGoal", currentGoal);
	    dataForDocument.put("Periodofgoal", goalPeriod);
	    dataForDocument.put("paymentperiod", paymentPeriod);
	    dataForDocument.put("LumpSum", lumpSumsaving);
	    dataForDocument.put("MonthlySavings", monthlysavings);
	    dataForDocument.put("annualRate", annualRate);
	    dataForDocument.put("annualReturn", annualReturn);
	    dataForDocument.put("futureCost", futureCost);
	    dataForDocument.put("suggestedSavings", savings);
	    dataForDocument.put("commitment", commitment);
	    dataForDocument.put("sellerName", sellerName);
	    dataForDocument.put("sellerCode", sellerCode);
	    dataForDocument.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
	    context.setVariables(dataForDocument);
	} catch (Exception ex) {
	    logger.error("Data addition failed for PSM Document: ", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return context;
    }

    private double roundOffValue(double value) {

	return Math.round(value * 100.00) / 100.00;
    }

    // handling of WIP cases
    private boolean checkIsPSMUserUploaded(UnderwritingServiceDetails uwdetails, PSMDetails psmDetails) {

	boolean isPSMUserUploaded = false;
	List<DocumentDetails> documentDetails = new ArrayList<>();
	if (uwdetails != null && uwdetails.getUnderwritingStatus() != null && uwdetails.getUnderwritingStatus().getRequiredDocuments() != null) {
	    documentDetails = uwdetails.getUnderwritingStatus().getRequiredDocuments();
	}
	List<DocumentDetails> psmDocument = documentDetails.stream()
		.filter(doc -> doc.getMproDocumentId().equalsIgnoreCase("FF_Pr") && doc.isRequiredForMproUi()).collect(Collectors.toList());
	if (!psmDocument.isEmpty()
		&& (psmDetails == null || (!org.springframework.util.StringUtils.isEmpty(psmDetails) && StringUtils.isEmpty(psmDetails.getRecommendedProductName())))) {
	    isPSMUserUploaded = true;
	}
	return isPSMUserUploaded;
    }
    private boolean isPSMDataAvailableInProposal(PSMDetails psmDetails){

		boolean isValidPsmDetailAvailNot = StringUtils.isEmpty(psmDetails.getIsExistingLICover())
				                          || psmDetails.getRecommendedProducts() == null || psmDetails.getRecommendedProducts().isEmpty()
																	|| StringUtils.isEmpty(psmDetails.getRecommendedProductName());

		return !isValidPsmDetailAvailNot;
	}

}
