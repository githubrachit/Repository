package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Component("mliCkycDocument")
@EnableAsync
public class MliCkycDocument implements DocumentGenerationservice {

  private static final Logger logger = LoggerFactory.getLogger(MliCkycDocument.class);

  @Autowired
  private SpringTemplateEngine springTemplateEngine;

  @Autowired
  private DocumentHelper documentHelper;

  @Autowired
  protected MliCkycDocumentMapper mliCkycDocumentMapper;
  protected String mliCkycTemplate = "mli_ckyc";

  @Override
  public void generateDocument(ProposalDetails proposalDetails) {
    long requestedTime = System.currentTimeMillis();
    int retryCount = 0;
    long transactionId = proposalDetails.getTransactionId();
    String channelName = proposalDetails.getChannelDetails().getChannel();
    DocumentStatusDetails documentStatusDetails = null;
    String documentUploadStatus = null;

    try{
      Context mliKycDocContext = mliCkycDocumentMapper.setDataForMliCkycDocument(proposalDetails);
      String processedHtml = springTemplateEngine.process(mliCkycTemplate, mliKycDocContext);
      logger.info("Data binding with Mli Ckyc HTML is done for transactionId {}", transactionId);

      long processedTime = (System.currentTimeMillis() - requestedTime);

      logger.info("Data binding with Mli Ckyc HTML for transactionId {} took {} miliseconds ", transactionId,
          processedTime);

      String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);

      logger.info(" Mli Ckyc HTML to pdf conversation is done for transactionId {}", transactionId);

      processedTime = (System.currentTimeMillis() - requestedTime);

      logger.info("Mli Ckyc HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId,
          processedTime);

      if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
        logger.info("Mli Ckyc Document generation is failed so updating in DB for transactionId {}",
            transactionId);
        documentStatusDetails = new  DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(),
            AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.MLIC_CKYC,
            proposalDetails.getApplicationDetails().getStage());
      }else {

        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
            encodedString, AppConstants.MLIC_CKYC);

        List<DocumentRequestInfo> documentpayload = new ArrayList<>();
        documentpayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(channelName, proposalDetails.getTransactionId(),
            "MLIC_CKYC_DOCUMENT", AppConstants.MLIC_CKYC, documentpayload);
        documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
          // update the document upload failure status in db

          logger.info("Mli Ckyc Document upload is failed for transactionId {} {}", transactionId,
              AppConstants.MLIC_CKYC);

          documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
              proposalDetails.getApplicationDetails().getPolicyNumber(),
              proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
              AppConstants.MLIC_CKYC, proposalDetails.getApplicationDetails().getStage());
        } else {
          logger.info("Mli Ckyc Document is successfully generated and uploaded to S3 for transactionId {} {}",
              transactionId, AppConstants.MLIC_CKYC);
          // update the document upload success status in db
          documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
              proposalDetails.getApplicationDetails().getPolicyNumber(),
              proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
              AppConstants.MLIC_CKYC, proposalDetails.getApplicationDetails().getStage());
        }
      }
    }catch (UserHandledException ex) {
      logger.error("MliCkyc generation failed : {}", Utility.getExceptionAsString(ex));
      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
          AppConstants.MLIC_CKYC, proposalDetails.getApplicationDetails().getStage());
    } catch (Exception ex) {
      logger.error("MliCkycDocument generation failed :{}", Utility.getExceptionAsString(ex));
      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
          AppConstants.MLIC_CKYC, proposalDetails.getApplicationDetails().getStage());
    }
    documentHelper.updateDocumentStatus(documentStatusDetails);
    long processedTime = (System.currentTimeMillis() - requestedTime);
    logger.info("MliCkycDocument for transactionId {} took {} miliseconds ",
        proposalDetails.getTransactionId(), processedTime);
  }

}
