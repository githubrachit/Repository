package com.mli.mpro.document.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.Receipt;

@Service
public class DigitalConsentMapper {

    private static final Logger logger = LoggerFactory.getLogger(DigitalConsentMapper.class);

    /**
     * Mapping data from Digital Consent to Context DataMap
     *
     * @param proposalDetails
     * @return Context
     * @throws UserHandledException
     */
    public Context setDataOfDigitalConsentDocument(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START Digital Consent Data population");
        Map<String, Object> dataVariables = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
        LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of(AppConstants.APP_TIMEZONE));
        String formattedDateTime = dateTimeNow.format(formatter);
        String title = "";
        String firstName = "";
        String lastName = "";
        String middleName = "";
        String documentGeneration = "";
        String customerId = AppConstants.NOT_APPLICABLE;
        String customerOtpSent = AppConstants.NOT_APPLICABLE;
        String customerOtpValidated = AppConstants.NOT_APPLICABLE;
        boolean directDebitFlag = true;
        String directDebitOtpSent = "";
        String directDebitAmount = "";
        String directDebitOtpValidated = "";
        try {
            List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();
            Map<String, Object> yblMap = new HashMap<>();
            String sourceChannel = proposalDetails.getAdditionalFlags().getSourceChannel();
            boolean isTMPPartner = Utility.isTMBPartner(sourceChannel);
            documentGeneration = formattedDateTime;
            if (!CollectionUtils.isEmpty(partyInformationList)) {
                String gender = partyInformationList.get(0).getBasicDetails().getGender();
                title = Utility.getTitle(gender);
                firstName = partyInformationList.get(0).getBasicDetails().getFirstName();
                middleName = partyInformationList.get(0).getBasicDetails().getMiddleName();
                lastName = partyInformationList.get(0).getBasicDetails().getLastName();
            }

            String fullName = String.join(AppConstants.WHITE_SPACE, title, firstName, middleName, lastName);
            // Remoce extra spaces from fullname
            if (StringUtils.isNotEmpty(fullName)) {
                fullName = fullName.trim().replaceAll("\\s{2,}", AppConstants.WHITE_SPACE);
            }

            if (null != proposalDetails.getYblDetails()) {
                customerId = StringUtils.isNotBlank(proposalDetails.getYblDetails().getCustomerId()) ? proposalDetails.getYblDetails().getCustomerId()
                        : AppConstants.NOT_APPLICABLE;
                customerOtpSent = StringUtils.isNotBlank(proposalDetails.getYblDetails().getCustomerOtpSent())
                        ? proposalDetails.getYblDetails().getCustomerOtpSent()
                        : AppConstants.NOT_APPLICABLE;
                customerOtpValidated = StringUtils.isNotBlank(proposalDetails.getYblDetails().getCustomerOtpValidated())
                        ? proposalDetails.getYblDetails().getCustomerOtpValidated()
                        : AppConstants.NOT_APPLICABLE;
            }

            List<Receipt> receiptsList = proposalDetails.getPaymentDetails().getReceipt();
            if (!CollectionUtils.isEmpty(receiptsList)) {

                if (null != receiptsList.get(0).getYblPaymentDetails() && null != receiptsList.get(0).getYblPaymentDetails().getDirectDebitDetails()) {
                    directDebitFlag = getDirectDebitOtpSentFlag(receiptsList);
                    directDebitOtpSent = getNonEmptyValue(receiptsList.get(0).getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpSent());
                    directDebitOtpValidated = getNonEmptyValue(receiptsList.get(0).getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated());
                }

                if (isTMPPartner && Objects.nonNull(receiptsList.get(0).getPartnerPaymentDetails()) && Objects.nonNull(receiptsList.get(0).getPartnerPaymentDetails().getDirectDebitDetails())) {
                    directDebitFlag = getDirectDebitOtpSentFlag(receiptsList);
                    directDebitOtpSent = getNonEmptyValue(receiptsList.get(0).getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpSent());
                    directDebitOtpValidated = getNonEmptyValue(receiptsList.get(0).getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated());
                }

                directDebitAmount = receiptsList.get(0).getAmount();
            }
            // Setting data in yblMap
            logger.info("Setting Data for Digital Consent in yblMap...");
            yblMap.put("documentGeneration", documentGeneration);
            yblMap.put("customerId", customerId);
            yblMap.put("fullName", fullName);
            yblMap.put("customerOtpSent", customerOtpSent);
            yblMap.put("customerOtpValidated", customerOtpValidated);
            yblMap.put("directDebitFlag", directDebitFlag);
            yblMap.put("directDebitOtpSent", directDebitOtpSent);
            yblMap.put("directDebitAmount", directDebitAmount);
            yblMap.put("directDebitOtpValidated", directDebitOtpValidated);

            // Put Datamap in Context map
            dataVariables.put("yblMap", yblMap);

        } catch (Exception ex) {
	    logger.error("Data addition failed for Digital Consent Doc:",ex);
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context digitalConsentDetailsCxt = new Context();
        digitalConsentDetailsCxt.setVariables(dataVariables);
        logger.info("END Digital Consent Data population");
        return digitalConsentDetailsCxt;
    }

    private String getNonEmptyValue(String value) {
        return StringUtils
                .isNotBlank(value)
                ? value
                : AppConstants.BLANK;
    }

    private boolean getDirectDebitOtpSentFlag(List<Receipt> receiptsList) {
        return StringUtils.equalsIgnoreCase(receiptsList.get(0).getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpSent(),
                "true") ? true : false;
    }
}
