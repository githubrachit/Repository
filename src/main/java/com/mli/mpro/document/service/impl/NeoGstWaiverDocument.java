package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.BLANK;
import static com.mli.mpro.productRestriction.util.AppConstants.CURRENT_ADDRESS_NEO;
import static com.mli.mpro.productRestriction.util.AppConstants.WHITE_SPACE;


@Service("NeoGstWaiverDocument")
public class NeoGstWaiverDocument extends NeoBaseDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoGstWaiverDocument.class);

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails;
        try {
            Context context = setDataForDocument(proposalDetails);
            generateBaseDoc(proposalDetails, context, transactionId, retryCount, requestedTime, "neo\\gstWaiverDocument.html");
            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                logger.info("Neo GSTWaiver Doc generation is failed, so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                        0, AppConstants.OTHERS, proposalDetails.getApplicationDetails().getStage());
            } else {
                documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, encodedString, 0);
            }
        } catch (UserHandledException ex) {
            logger.error("Neo gstWaiver document generation failed: {}", Utility.getExceptionAsString(ex));
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DATA_MISSING_FAILURE, AppConstants.OTHERS);
        } catch (Exception ex) {
            logger.error("Exception occurred while generating gstWaiverDoc for Neo, Exception: {}", Utility.getExceptionAsString(ex));
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.TECHNICAL_FAILURE, AppConstants.OTHERS);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Neo gstWaiver document is generated for transactionId {} took {} milliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

    private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {
        Context context = new Context();
        logger.info("Neo GSTWaiver Document data Mapping is initiated for transactionId {}", proposalDetails.getTransactionId());
        try {
            Map<String, Object> dataForDocument = new HashMap<>();
            String name = BLANK;
            String addressString = BLANK;
            String policyNumber = BLANK;
            String mobileNumber = BLANK;
            String salutation = BLANK;
            Date dob = null;

            if (Objects.nonNull(proposalDetails.getPartyInformation().get(0)) && !proposalDetails.getPartyInformation().isEmpty()) {
                BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
                salutation = getSalutationForGstDoc(proposalDetails, salutation);
                if (Objects.nonNull(basicDetails)) {
                    if (checkingNullValuesInPersonalIdentification(basicDetails)) {
                        mobileNumber = (Utility.isNullOrEmptyString(basicDetails.getPersonalIdentification()
                                .getPhone().get(0).getPhoneNumber()) ? BLANK : basicDetails.getPersonalIdentification()
                                .getPhone().get(0).getPhoneNumber());
                    }
                    name = Utility.getName(salutation, basicDetails.getFirstName(), basicDetails.getMiddleName(), basicDetails.getLastName());
                    policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
                    dob = Objects.nonNull(basicDetails.getDob()) ? basicDetails.getDob() : null;

                    AddressDetails addressDetails = basicDetails.getAddress().stream()
                            .filter(Objects::nonNull)
                            .filter(address -> CURRENT_ADDRESS_NEO.equalsIgnoreCase(address.getAddressType()))
                            .map(Address::getAddressDetails).findAny().orElse(null);
                    addressString = extractingAddressDetailsForGstDoc(addressString, addressDetails);
                }
            }
            dataForDocument.put("name", name);
            dataForDocument.put("completeAddress", addressString);
            dataForDocument.put("gstRate", "0%");
            dataForDocument.put("dob", Utility.getFormattedDate(dob));
            dataForDocument.put("proposalNumber", policyNumber);
            dataForDocument.put("mobileNumber", mobileNumber);
            dataForDocument.put("dateTimeStamp", Utility.getFormattedDate(new Date()));
            context.setVariables(dataForDocument);
        } catch (Exception e) {
            logger.error("Data mapping failed for gstWaiverDocument for Neo, Exception: {}", Utility.getExceptionAsString(e));
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Neo GSTWaiver Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return context;
    }

    private boolean checkingNullValuesInPersonalIdentification(BasicDetails basicDetails) {
        return Objects.nonNull(basicDetails.getPersonalIdentification())
                && Objects.nonNull(basicDetails.getPersonalIdentification().getPhone().get(0))
                && !basicDetails.getPersonalIdentification().getPhone().isEmpty();
    }


    private String extractingAddressDetailsForGstDoc(String addressString, AddressDetails addressDetails) {
        if (Objects.nonNull(addressDetails)) {
            addressString = (Utility.isNullOrEmptyString(addressDetails.getHouseNo()) ? BLANK : addressDetails.getHouseNo())
                    + WHITE_SPACE + (Utility.isNullOrEmptyString(addressDetails.getArea()) ? BLANK : addressDetails.getArea())
                    + WHITE_SPACE + (Utility.isNullOrEmptyString(addressDetails.getLandmark()) ? BLANK : addressDetails.getLandmark())
                    + WHITE_SPACE + (Utility.isNullOrEmptyString(addressDetails.getCity()) ? BLANK : addressDetails.getCity())
                    + WHITE_SPACE + (Utility.isNullOrEmptyString(addressDetails.getState()) ? BLANK : addressDetails.getState())
                    + WHITE_SPACE + (Utility.isNullOrEmptyString(addressDetails.getCountry()) ? BLANK : addressDetails.getCountry());
        }
        return addressString;
    }

    private String getSalutationForGstDoc(ProposalDetails proposalDetails, String salutation) {
        if (Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPartyDetails())) {
            salutation = (Utility.isNullOrEmptyString(proposalDetails.getPartyInformation().get(0).getPartyDetails().getSalutation())
                    ? BLANK : proposalDetails.getPartyInformation().get(0).getPartyDetails().getSalutation());
        }
        return salutation;
    }

    protected DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount) throws UserHandledException {
        try {
            DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, pdfDocumentOrDocumentStatus, AppConstants.OTHERS);
            List<DocumentRequestInfo> documentPayload = new ArrayList<>();
            documentPayload.add(documentRequestInfo);
            DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                    AppConstants.OTHERS, AppConstants.OTHERS, documentPayload);

            String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
            if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
                // update the document upload failure status in DB
                logger.info("Neo GSTWaiver Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.OTHERS);
                return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.OTHERS);
            } else {
                // update the document upload success status in DB
                logger.info("Neo GSTWaiver Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                        AppConstants.OTHERS);
                return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                        documentUploadStatus, AppConstants.OTHERS);
            }
        } catch (Exception e) {
            logger.error("s3DocumentSave failed for gstWaiverDocument for Neo, Exception: {}", Utility.getExceptionAsString(e));
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Neo GSTWaiver s3DocumentSave failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
