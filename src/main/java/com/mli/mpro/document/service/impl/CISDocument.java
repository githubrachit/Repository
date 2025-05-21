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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.*;

@Component("customerInformationSheetDocument")
@EnableAsync
public class CISDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(CISDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private CISDocumentMapper cisDocumentMapper;

    private static final Map<String, String> PRODUCT_HTML_MAP = new HashMap<>();

    static {
        PRODUCT_HTML_MAP.put(AppConstants.FWAP, "FWAP");
        PRODUCT_HTML_MAP.put(AppConstants.PLANID_FWP,"FWP");
        PRODUCT_HTML_MAP.put(AppConstants.OSP, "OSP");
        PRODUCT_HTML_MAP.put(AppConstants.FTS, "FTS");
        PRODUCT_HTML_MAP.put(AppConstants.SPS_PRODUCT_ID,"SPS");
        PRODUCT_HTML_MAP.put(AppConstants.PLANID_PW,"PWP");
        PRODUCT_HTML_MAP.put(AppConstants.FYPP_PRODUCT_ID,"FYPP");
        PRODUCT_HTML_MAP.put(AppConstants.PLANID_SFPS,"FWP");
        PRODUCT_HTML_MAP.put(AppConstants.SJB,"SJB");
        PRODUCT_HTML_MAP.put(AppConstants.SPP_ID,"SPP");
        PRODUCT_HTML_MAP.put(AppConstants.SWAGPP,"SWAGPP");
        PRODUCT_HTML_MAP.put(AppConstants.SGPP_ID,"SGPP");
        PRODUCT_HTML_MAP.put(AppConstants.GLIP_PRODUCT_ID,"GLIP");
        PRODUCT_HTML_MAP.put(AppConstants.SSP_PRODUCT_ID,"SSPP");
        PRODUCT_HTML_MAP.put(AppConstants.SWAG_PAR,"SWAGPAR");
        PRODUCT_HTML_MAP.put(AppConstants.STEP,"STEP");
        PRODUCT_HTML_MAP.put(AppConstants.SWAG,"SWAG");
        PRODUCT_HTML_MAP.put(AppConstants.SWAG_ELITE_PRODUCT_ID,"SWAGElite");
        PRODUCT_HTML_MAP.put(AppConstants.SWIP,"SWIP");
        PRODUCT_HTML_MAP.put(AppConstants.SAP_PRODUCT_ID,"SAP");
        PRODUCT_HTML_MAP.put(AppConstants.SEWA,"SEWA");
        PRODUCT_HTML_MAP.put(AppConstants.STPP_PRODUCT_ID,"STPP");
        PRODUCT_HTML_MAP.put(AppConstants.STAR_PRODUCT_ID,"STAR");
    }

    private static final Map<String, String> SWP_HTML_MAP = new HashMap<>();

    static {
        SWP_HTML_MAP.put(AppConstants.LUMP_SUM, "SWP_v1_lumpSum");
        SWP_HTML_MAP.put(AppConstants.SHORTTERM_INCOME,"SWP_v2_shortTermIncome");
        SWP_HTML_MAP.put(AppConstants.LONGTERM_INCOME, "SWP_v3_longTermIncome");
        SWP_HTML_MAP.put(AppConstants.WHOLE_LIFE_INCOME, "SWP_v4_wholeLifeIncome");
    }

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

        String productId= proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
        String variant = proposalDetails.getProductDetails().get(0).getProductInfo().getVariant();
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails = null;
        Long transactionId = proposalDetails.getTransactionId();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        long requestedTime = System.currentTimeMillis();
        logger.info("CIS document generation is initiated for transactionId {} and at applicationStage {}",
                transactionId, proposalDetails.getApplicationDetails().getStage());
        try {
            Context  documentData = cisDocumentMapper.setCISData(proposalDetails);
            Context annexureData = new Context();
            String htmlForm = getHtmlBasedOnProduct(productId);

            if(AppConstants.SMART_WEALTH_PLAN.equalsIgnoreCase(productId)){
                htmlForm = getHtmlForSWP(proposalDetails.getSourcingDetails().isPosSeller(), variant);
            }

            Map<String, Object> completeDetails = new HashMap<>();

            String processsedHtmlAnnexure = springTemplateEngine.process("CIS\\Annexure",annexureData);
            String processedHtmlForm = springTemplateEngine.process("CIS\\" + htmlForm, documentData);
            String processedHtmlAnnexureB = springTemplateEngine.process("CIS\\AnnexureB",annexureData);
            String processedHtmlAnnexureBSEWA = springTemplateEngine.process("CIS\\AnnexureB_sewa",annexureData);
            String processedHtmlAnnexureC = springTemplateEngine.process("CIS\\AnnexureC",annexureData);
            String processedHtmlAnnexureBSTPP = springTemplateEngine.process( "CIS\\AnnexureB_STPP",annexureData);
            completeDetails.put("htmlForm",processedHtmlForm);
            completeDetails.put("Annexure",processsedHtmlAnnexure);
            completeDetails.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
            completeDetails.put("isVisibleOrNot",isAnnexureBvisibleOrNot(productId));
            completeDetails.put("AnnexureB",processedHtmlAnnexureB);
            completeDetails.put("isAnnexureVisibleForSewa",Utility.isAnnexureVisibleForSewa(productId));
            completeDetails.put("isAnnexureVisibleForSTPP",Utility.isAnnexureBVisibleForSTPP(productId));
            completeDetails.put("AnnexureB_SEWA",processedHtmlAnnexureBSEWA);
            completeDetails.put("AnnexureB_STPP",processedHtmlAnnexureBSTPP);
            completeDetails.put("AnnexureC",processedHtmlAnnexureC);
            completeDetails.put("isNotSTARProduct",Utility.isNotSTARProduct(productId));
            Context cisFormDetailsCtx = new Context();
            cisFormDetailsCtx.setVariables(completeDetails);

            logger.info("Data binding with CIS HTML is done for transactionId {}", transactionId);

            String finalDocument = springTemplateEngine.process("CIS\\CIS_Form",cisFormDetailsCtx);
            String encodedStringForm = documentHelper.generatePDFDocument(finalDocument, retryCount);

            logger.info("CIS HTML to pdf conversation is done for transactionId {}", transactionId);

            documentStatusDetails = handleDocumentGenerationResponse(encodedStringForm, proposalDetails, channelName);

        } catch (UserHandledException e) {
            logger.error("CIS Document generation failed : {}", Utility.getExceptionAsString(e));
           documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.DATA_MISSING_FAILURE);
        } catch (Exception ex) {
            logger.error("CIS Document generation failed :{}", Utility.getExceptionAsString(ex));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("CIS document for transactionId {} took {} miliseconds ",
                proposalDetails.getTransactionId(), processedTime);

    }
    public String getHtmlBasedOnProduct(String productId) {
        return PRODUCT_HTML_MAP.getOrDefault(productId, "");
    }

    public String getHtmlForSWP(boolean isPosSeller, String variant) {
        if(isPosSeller){
            return "SWP_pos";
        }else {
            return SWP_HTML_MAP.getOrDefault(variant, "");
        }
    }

    private DocumentStatusDetails handleDocumentGenerationResponse(String encodedStringForm, ProposalDetails proposalDetails, String channelName) {
        Long transactionId = proposalDetails.getTransactionId();

        if (AppConstants.FAILED.equalsIgnoreCase(encodedStringForm)) {
            logger.info("CIS Document generation failed, updating DB for transactionId {}", transactionId);
            return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED);
        } else {
            DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                    encodedStringForm, AppConstants.CIS_DOCUMENT);
            List<DocumentRequestInfo> documentpayload = new ArrayList<>();
            documentpayload.add(documentRequest);

            DocumentDetails documentDetail = new DocumentDetails(channelName,
                    proposalDetails.getTransactionId(), AppConstants.CIS_DOCUMENT_ID, AppConstants.CIS_DOCUMENT,
                    documentpayload);

            String documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, 0);

            if (AppConstants.FAILED.equalsIgnoreCase(documentStatusUpload)) {
                logger.info("CIS Document upload failed for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED);
            } else {
                logger.info("CIS Document successfully generated and uploaded to S3 for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, documentStatusUpload);
            }
        }
    }
    private DocumentStatusDetails createDocumentStatusDetails(ProposalDetails proposalDetails, String status) {
        return new DocumentStatusDetails(
                proposalDetails.getTransactionId(),
                proposalDetails.getApplicationDetails().getPolicyNumber(),
                proposalDetails.getSourcingDetails().getAgentId(),
                status,
                0,
                AppConstants.CIS_DOCUMENT,
                proposalDetails.getApplicationDetails().getStage()
        );
    }
    private boolean isAnnexureBvisibleOrNot(String productId) {
        return AppConstants.SPP_ID.equalsIgnoreCase(productId);
    }
}
