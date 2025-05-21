package com.mli.mpro.document.service.impl;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

@Component("covidQuestionnaireDocument")
@EnableAsync
public class CovidQuestionnaireDocument implements DocumentGenerationservice {

	private static final Logger logger = LoggerFactory.getLogger(CovidQuestionnaireDocument.class);

	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private DocumentHelper documentHelper;

	@Autowired
	protected CovidQuestionnaireDocumentMapper covidQuestionnaireDocumnetMapper;
	protected String covidQuestionnaireAnnexureTemplate = "CovidDetails";
	protected String covidQuestionnaireSWPorWOPAnnexureTemplate = "CovidInsureProposalDetail";

	//FUL2-46153 COVID Questionnaire : New Requirement
	@Autowired
	protected Covid19QuestionnaireDetailsMapper covid19QuestionnaireDetailsMapper;
	protected String covidUpdateQuestionnaireAnnexureTemplate = "Covid19QuestionnaireDetails";
	protected String covidUpdateQuestionnaireSWPorWOPAnnexureTemplate = "Covid19QuestionnaireInsuredDetails";

	protected String thanosCovidQuestionnaireAnnexureTemplate = "ThanosCovidDetails";
	protected String thanosCovidQuestionnaireWOPAnnexureTemplate = "ThanosCovidInsuredDetails";

	/*
	 * This is the main method which executes the process of Covid Questionare
	 * document generation by calling necessary methods Here Spring Template Engine
	 * is used to bind the data dynamically to the static HTML which is stored in
	 * templates folder.
	 */

	@Override
	@Async
	public void generateDocument(ProposalDetails proposalDetails) {
		long requestedTime = System.currentTimeMillis();
		int retryCount = 0;
		boolean isFormRequired = covidQuestionnaireDocumnetMapper.isCovidFormRequiredToGenerate(proposalDetails);
		boolean isWOPorSWPJoint = covidQuestionnaireDocumnetMapper.isWOPorSWPJoint(proposalDetails);
		boolean isSSPJointLife = false;
		if (proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
				&& Utility.isSSPJointLife(proposalDetails.getProductDetails().get(0))) {
			isSSPJointLife = true;
		}
		if (isFormRequired && !isWOPorSWPJoint && !isSSPJointLife) {
			generateCovidDocument(proposalDetails, retryCount, 
					requestedTime);
		} else if ((isWOPorSWPJoint || isSSPJointLife) && covidQuestionnaireDocumnetMapper.isCovidWOPorSWPJointFormRequiredToGenerate(proposalDetails)) {
			generateInsuredProposalCovidDocument(proposalDetails, retryCount, 
					requestedTime);
		} else {
			logger.info("Covid WOP/SWP Questionnarie document for transactionId {} not required to generate ",
					proposalDetails.getTransactionId());
		}
	}

	private void generateInsuredProposalCovidDocument(ProposalDetails proposalDetails, int retryCount,
			 long requestedTime) {
		long transactionId = proposalDetails.getTransactionId();
		String channelName = proposalDetails.getChannelDetails().getChannel();
		DocumentStatusDetails documentStatusDetails = null;
		String documentUploadStatus = null;
		String processedHtml = null;

		try {
			//FUL2-46153 COVID Questionnaire : New Requirement
			boolean visibleCovid19Questionnaire = AppConstants.N.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowCovidQuesOnPosv());
			if(visibleCovid19Questionnaire){
				Context covidUpdatedQuestionnaireInsuredAnnexureContext = covid19QuestionnaireDetailsMapper
						.setDataForCovidWOPorSWPQuestionnaire(proposalDetails);

				processedHtml = springTemplateEngine.process(covidUpdateQuestionnaireSWPorWOPAnnexureTemplate, covidUpdatedQuestionnaireInsuredAnnexureContext);
			}
			else {
				Context covidQuestionnaireAnnexureContext = covidQuestionnaireDocumnetMapper
						.setDataForCovidWOPorSWPQuestionnaire(proposalDetails);

				processedHtml = springTemplateEngine.process(covidQuestionnaireSWPorWOPAnnexureTemplate, covidQuestionnaireAnnexureContext);
			}
			logger.info("Data binding with covid WOP/SWP HTML is done for transactionId {}", transactionId);
			
			long processedTime = (System.currentTimeMillis() - requestedTime);
			
			logger.info("Data binding with covid WOP/SWP HTML for transactionId {} took {} miliseconds ", transactionId,
					processedTime);
			
			String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
			
			logger.info(" Covid WOP/SWP HTML to pdf conversation is done for transactionId {}", transactionId);
			
			processedTime = (System.currentTimeMillis() - requestedTime);
			
			logger.info("Covid WOP/SWP HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId,
					processedTime);

			if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
				logger.info("Covid WOP/SWP Document generation is failed so updating in DB for transactionId {}",
						transactionId);
				
				documentStatusDetails = new  DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(),
						AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.COVID_QUESTIONAIRE,
						proposalDetails.getApplicationDetails().getStage());
			} else {
				
				DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
						encodedString, AppConstants.COVID_QUESTIONAIRE);
				
				List<DocumentRequestInfo> documentpayload = new ArrayList<>();
				documentpayload.add(documentRequestInfo);
				DocumentDetails documentDetails = new DocumentDetails(channelName, proposalDetails.getTransactionId(),
						"COVID_Questions", AppConstants.COVID_QUESTIONAIRE, documentpayload);
				documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
				if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
					// update the document upload failure status in db
					
					logger.info("Covid WOP/SWP Document upload is failed for transactionId {} {}", transactionId,
							AppConstants.COVID_QUESTIONAIRE);
					
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
							AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
				} else {
					logger.info("Covid WOP/SWP Document is successfully generated and uploaded to S3 for transactionId {} {}",
							transactionId, AppConstants.COVID_QUESTIONAIRE);
					// update the document upload success status in db
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
							AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
				}
			}
		} catch (UserHandledException ex) {
			logger.error("Covid WOP/SWP Questionnarie generation failed:{}", Utility.getExceptionAsString(ex));
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
					AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
		} catch (Exception ex) {
			logger.error("Covid WOP/SWP Questionnarie Document generation failed: {}", Utility.getExceptionAsString(ex));
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
					AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
		}
		
		documentHelper.updateDocumentStatus(documentStatusDetails);
		
		long processedTime = (System.currentTimeMillis() - requestedTime);
		
		logger.info("Covid WOP/SWP Questionnarie document for transactionId {} took {} miliseconds ",
				proposalDetails.getTransactionId(), processedTime);

	}
	
	private void generateCovidDocument(ProposalDetails proposalDetails, int retryCount,
			  long requestedTime) {
		long transactionId = proposalDetails.getTransactionId();
		String channelName = proposalDetails.getChannelDetails().getChannel();
		DocumentStatusDetails documentStatusDetails = null;
		String documentStatusUpload = null;
		String processedHtmlForm = null;
		try {
			//FUL2-46153 COVID Questionnaire : New Requirement
			boolean visibleCovid19Questionnaire = AppConstants.N.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowCovidQuesOnPosv());
			if(visibleCovid19Questionnaire){
				Context covidUpdatedQuestionnaireAnnexureContext = covid19QuestionnaireDetailsMapper
						.setDataForCovid19QuestionnaireDetails(proposalDetails);

				processedHtmlForm = springTemplateEngine.process(covidUpdateQuestionnaireAnnexureTemplate, covidUpdatedQuestionnaireAnnexureContext);
			}
			else {
				Context covidQuestionnaireAnnexureContext = covidQuestionnaireDocumnetMapper
						.setDataForCovidQuestionnaire(proposalDetails);

				processedHtmlForm = springTemplateEngine.process(covidQuestionnaireAnnexureTemplate, covidQuestionnaireAnnexureContext);
			}
			logger.info("Data binding with covid HTML is done for transactionId {}", transactionId);
			
			String encodedStringForm = documentHelper.generatePDFDocument(processedHtmlForm, retryCount);
			logger.info("Covid HTML to pdf conversation is done for transactionId {}", transactionId);
			if (encodedStringForm.equalsIgnoreCase(AppConstants.FAILED)) {
				documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(),

						AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.COVID_QUESTIONAIRE,
						proposalDetails.getApplicationDetails().getStage());
				logger.info("Covid Document generation is failed so updating in DB for transactionId {}",
						transactionId);
			} else {
				DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
						encodedStringForm, AppConstants.COVID_QUESTIONAIRE);
				List<DocumentRequestInfo> documentpayload = new ArrayList<>();
				documentpayload.add(documentRequest);
				DocumentDetails documentDetail = new DocumentDetails(channelName,
						proposalDetails.getTransactionId(), "COVID_Questions", AppConstants.COVID_QUESTIONAIRE,
						documentpayload);
				documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, retryCount);
				if (documentStatusUpload.equalsIgnoreCase("FAILED")) {
				
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED,
							0, AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
					logger.info("Covid Document upload is failed for transactionId {} {}", transactionId,
							AppConstants.COVID_QUESTIONAIRE);
				} else {
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), documentStatusUpload, 0,
							AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
					logger.info(
							"Covid Document is successfully generated and uploaded to S3 for transactionId {} {}",
							transactionId, AppConstants.COVID_QUESTIONAIRE);
				}
			}
		} catch (UserHandledException ex) {
			logger.error("Covid Questionnarie generation failed : {}", Utility.getExceptionAsString(ex));
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
					AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
		} catch (Exception ex) {
			logger.error("Covid Questionnarie Document generation failed :{}", Utility.getExceptionAsString(ex));
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
					AppConstants.COVID_QUESTIONAIRE, proposalDetails.getApplicationDetails().getStage());
		}
		documentHelper.updateDocumentStatus(documentStatusDetails);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Covid Questionnarie document for transactionId {} took {} miliseconds ",
				proposalDetails.getTransactionId(), processedTime);
	}

}
