package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.*;

@Component("neoShieldProRiskDocument")
public class NeoShieldProRiskDocument extends NeoBaseDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoShieldProRiskDocument.class);

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;
            try {
                Context context = setDataForDocument(proposalDetails);
                generateBaseDoc(proposalDetails,context,transactionId,retryCount,requestedTime, "neo\\shieldProRisk.html");
                if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                    // update the document generation failure status in db
                    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                    documentStatusDetails = new DocumentStatusDetails(transactionId,
                            proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                            0, AppConstants.SHIELD_PRO_RISK_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                } else {
                    documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, encodedString, 0);
                }

            } catch (UserHandledException ex) {
                logger.error("Neo shield pro risk document generation failed for equoteNumber {}, transactionId {}:",
                        proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.DATA_MISSING_FAILURE, AppConstants.SHIELD_PRO_RISK_DOCUMENT);
            } catch (Exception ex) {
                logger.error("Neo shield pro risk Document generation failed for equoteNumber {}, transactionId {}:",
                        proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.TECHNICAL_FAILURE, AppConstants.SHIELD_PRO_RISK_DOCUMENT);
            }
            documentHelper.updateDocumentStatus(documentStatusDetails);
            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("Neo shield pro risk document is generated for equoteNumber {}, transactionId {} took {} milliseconds ",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), processedTime);
    }

    protected DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount) {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
                AppConstants.DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus,
                AppConstants.SHIELD_PRO_RISK_DOCUMENT);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                "SHIELD_PRO_RISK_PDF", AppConstants.SHIELD_PRO_RISK_DOCUMENT, documentPayload);

        String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.SHIELD_PRO_RISK_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.SHIELD_PRO_RISK_DOCUMENT);
        } else {
            // update the document upload success status in DB
            logger.info("Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    AppConstants.SHIELD_PRO_RISK_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, AppConstants.SHIELD_PRO_RISK_DOCUMENT);
        }
    }

    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

        Context context = new Context();
        logger.info("Data Mapping for shield pro risk doc is initiated for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        try {
            Map<String, Object> dataForDocument = new HashMap<>();
            String policyNo="";
            String plan="";
            String variant="";
            String annualIncome="";
            String cibilScore="";
            String crifScore="";
            String shieldProRiskScore="";
            String shieldProRiskCategory="";

            if(proposalDetails !=null) {
                policyNo = proposalDetails.getApplicationDetails() !=null? proposalDetails.getApplicationDetails().getPolicyNumber():policyNo;
                plan = !CollectionUtils.isEmpty(proposalDetails.getProductDetails()) && proposalDetails.getProductDetails().get(0).getProductInfo() !=null? proposalDetails.getProductDetails().get(0).getProductInfo().getProductName(): plan;
                if(plan==null){
                    plan="";
                }
                variant= !CollectionUtils.isEmpty(proposalDetails.getProductDetails()) && proposalDetails.getProductDetails().get(0).getProductInfo() !=null?proposalDetails.getProductDetails().get(0).getProductInfo().getVariant(): variant;
                if(variant==null){
                    variant="";
                }
                cibilScore = proposalDetails.getCkycDetails() !=null ? proposalDetails.getCkycDetails().getCibilScore(): cibilScore;
                if(cibilScore ==null){
                    cibilScore="";
                }
                crifScore= proposalDetails.getCkycDetails() !=null ? proposalDetails.getCkycDetails().getCrifScore(): crifScore;
                if(crifScore ==null){
                    crifScore="";
                }
                shieldProRiskScore= proposalDetails.getUnderwritingServiceDetails()!=null && proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails() !=null?
                        proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getNormalisedScoreURMU():shieldProRiskScore;
                if(shieldProRiskScore ==null){
                    shieldProRiskScore="";
                }
                if(proposalDetails.getUnderwritingServiceDetails()!=null && proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails() !=null
                        && !StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU())){
                    int index = proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU().indexOf(')') ;
                    if(index <0){
                        index=0;
                    }
                    shieldProRiskCategory=proposalDetails.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU().substring(0, index);

                }
                annualIncome = Objects.nonNull(proposalDetails.getEmploymentDetails()) &&
                        Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation()) &&
                        !proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty() &&
                        Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0)) &&
                        Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails()) &&
                        Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails().getAnnualIncome())
                        ? proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails().getAnnualIncome() : "";
            }

            dataForDocument.put("policyNo",policyNo );
            dataForDocument.put("plan", plan);
            dataForDocument.put("variant", variant);
            dataForDocument.put("annualIncome", annualIncome);
            dataForDocument.put("cibilScore", cibilScore);
            dataForDocument.put("crifScore", crifScore);
            dataForDocument.put("shieldProRiskScore", shieldProRiskScore);
            dataForDocument.put("shieldProRiskCategory", shieldProRiskCategory);
            context.setVariables(dataForDocument);
        } catch (Exception ex) {
            logger.error("Data addition failed for Shield Pro Risk Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return context;
    }

}
