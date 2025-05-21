package com.mli.mpro.document.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.InvestorRiskProfileDataMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SourcingDetails;
import com.mli.mpro.utils.Utility;

/**
 * @author akshom4375 Service Class to generate InvestorRiskProfile Document
 */
@Component("investorRiskProfileDocument")
@EnableAsync
public class InvestorRiskProfileDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(InvestorRiskProfileDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    InvestorRiskProfileDataMapper investorRiskProfileDataMapper;

	@Autowired
	private ConfigurationRepository configurationRepository;

    /**
     * Method to generate irp document. Spring Template Engine is used to bind the
     * data dynamically to the static HTML which is stored in templates folder.
     */
    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

	logger.info("Initiating pdf generation for InvestorRiskProfile...");
	DocumentStatusDetails documentStatusDetails = null;
	long transactionId = proposalDetails.getTransactionId();
	long requestedTime = System.currentTimeMillis();
	String documentUploadStatus = StringUtils.EMPTY;
	int retryCount = 0;
	boolean irpFlag = false;
	String agentId = Optional.ofNullable(proposalDetails.getSourcingDetails()).map(SourcingDetails::getAgentId)
			.orElse("");
	try {

	    String productType = proposalDetails.getProductDetails().get(0).getProductType();
	    String channelName = proposalDetails.getChannelDetails().getChannel();

	    if (StringUtils.equalsIgnoreCase(productType, AppConstants.PRODUCT_TYPE_ULIP)) {
		irpFlag = true;
	    }
	    if (irpFlag) {
		Context context = investorRiskProfileDataMapper.setDocumentData(proposalDetails);
		//FUL2-37012 WIP CASE handling for IRP Document
			String processedHtml = (Boolean.TRUE.equals(proposalDetails.getAdditionalFlags().getNewIrpGenerate())) ?
			springTemplateEngine.process("irp\\IRP", context):springTemplateEngine.process("irp\\IRP_OLD_WIP",context);
		//FUL2-247470 IRP New Document
		if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_IRP_PSM_MPRO_FEATURE) &&
				compareWipCaseDate(transactionId,proposalDetails.getApplicationDetails().getCreatedTime(),AppConstants.IRP_NEW_DATE)) {
				processedHtml= springTemplateEngine.process("irp\\IRPNew", context);
		}

		logger.info("Data binding with HTML is done for transactionId {}", transactionId);
		String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);

		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document generation failure status in db
		    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), agentId,
			    AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.INVESTOR_RISK_PROFILE, proposalDetails.getApplicationDetails().getStage());
		} else {
		    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
			    AppConstants.INVESTOR_RISK_PROFILE);
		    List<DocumentRequestInfo> documentPayload = new ArrayList<>();
		    documentPayload.add(documentRequestInfo);
		    DocumentDetails documentDetails = new DocumentDetails(channelName, proposalDetails.getTransactionId(), AppConstants.IRP_MPRO_DOCUMENTID,
			    AppConstants.INVESTOR_RISK_PROFILE, documentPayload);

		    documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		    // FUL2-46310 moving the IRP document to Primary policy for capital guarantee solution product 
		    if (AppConstants.SUCCESS.equalsIgnoreCase(documentUploadStatus) && Utility.isCapitalGuaranteeSolutionProduct(proposalDetails) && proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId() != 0l) {
	            documentDetails.setTransactionId(proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId());
	            documentDetails.setDocumentName(AppConstants.INVESTOR_RISK_PROFILE);
	            documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
	        }
			if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
				// update the document upload failure status in db
				logger.info("Document upload is failed for transactionId {} {}", transactionId,
						AppConstants.INVESTOR_RISK_PROFILE);
				documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						agentId, AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
						AppConstants.INVESTOR_RISK_PROFILE, proposalDetails.getApplicationDetails().getStage());
			} else {
				logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}",
						transactionId, AppConstants.INVESTOR_RISK_PROFILE);
				// update the document upload success status in db
				documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						agentId, documentUploadStatus, 0,
						AppConstants.INVESTOR_RISK_PROFILE, proposalDetails.getApplicationDetails().getStage());
			}
		}
	    } else {
		logger.error("IRP validations returned false for this proposal number!");
	    }

	} catch (UserHandledException ex) {
	    logger.error("Error occurred while IRP Document generation: {}", Utility.getExceptionAsString(ex));
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
	    		agentId, AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.INVESTOR_RISK_PROFILE,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Unknown error occurred while IRP Document generation: {}", Utility.getExceptionAsString(ex));
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
	    		agentId, AppConstants.TECHNICAL_FAILURE, 0, AppConstants.INVESTOR_RISK_PROFILE,
		    proposalDetails.getApplicationDetails().getStage());
	}

	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("IRP document for transactionId {} took {} miliseconds.", proposalDetails.getTransactionId(), processedTime);
    }

	private boolean compareWipCaseDate(long transactionId, Date createdTime, String key) {
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
}
