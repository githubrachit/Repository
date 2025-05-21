package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

@Service
public class NeoPayUDocumentMapper {
    private static final Logger logger = LoggerFactory.getLogger(NeoPayUDocumentMapper.class);

    HashMap<String, String> CibilIncomeMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("00001", "1,80,000");
            put("00002", "4,20,000");
            put("00003", "5,40,000");
            put("00004", "6,60,000");
            put("00005", "7,80,000");
            put("00006", "10,20,000");
            put("00007", "13,50,000");
            put("00008", "17,50,000");
            put("00009", "22,50,000");
            put("00010", "25,00,000");
        }
    };

    public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {
        long transactionId = proposalDetails.getTransactionId();
        logger.info("PayU Document Data Mapping for transactionId {}", transactionId);
        Map<String, Object> dataMap = new HashMap<>();
        Context payUDocumentContext = new Context();
        dataMap.put(AppConstants.POLICY_NUMBER, AppConstants.BLANK);
        dataMap.put(AppConstants.CUSTOMER_NAME, AppConstants.BLANK);
        dataMap.put(AppConstants.SUM_ASSURED, AppConstants.BLANK);
        dataMap.put(AppConstants.PAYU_INCOME, AppConstants.BLANK);
        dataMap.put(AppConstants.CIBIL_SCORE, AppConstants.BLANK);
        dataMap.put(AppConstants.CIBIL_STATUS, AppConstants.BLANK);
        dataMap.put(AppConstants.CIBIL_INCOME,AppConstants.BLANK);

        try {
            Optional.ofNullable(proposalDetails.getApplicationDetails()).ifPresent(
                    applicationDetails -> dataMap.put(AppConstants.POLICY_NUMBER, applicationDetails.getPolicyNumber())
            );
            proposalDetails.getPartyInformation().stream()
                    .filter(partyInformation -> AppConstants.LIFE_INSURED.equalsIgnoreCase(partyInformation.getPartyType()))
                    .findFirst()
                    .ifPresent(partyInformation -> {
                        Optional.ofNullable(partyInformation.getBasicDetails()).ifPresent(basicDetails -> {
                            dataMap.put(AppConstants.CUSTOMER_NAME,
                                    Utility.getFullName(basicDetails.getFirstName(), basicDetails.getMiddleName(), basicDetails.getLastName()));
                            dataMap.put(AppConstants.PAYU_INCOME, basicDetails.getPayUIncome());
                        });
                    });

            proposalDetails.getProductDetails().stream().findFirst().ifPresent(
                    productDetails -> {
                        Optional.ofNullable(productDetails.getProductInfo()).ifPresent(
                                productInfo -> dataMap.put(AppConstants.SUM_ASSURED, productInfo.getSumAssured())
                        );
                    }
            );
            Optional.ofNullable(proposalDetails.getQuotePayload())
                    .map(quotePayload -> quotePayload.getApplication())
                    .map(application -> application.getCibilLeadSearchResponse())
                    .map(cibilLeadSearchResponse -> cibilLeadSearchResponse.getScores())
                    .ifPresent(scoresList -> {
                        scoresList.stream()
                                .filter(scores -> AppConstants.CIBILTUSC3.equalsIgnoreCase(scores.getScoreName()))
                                .findFirst()
                                .ifPresent(scores -> {
                                    Optional.ofNullable(scores.getScore()).ifPresent(score -> {
                                        if ("000-1".equals(score)) {
                                            score = "0";
                                        }
                                        dataMap.put(AppConstants.CIBIL_SCORE, score);
                                        // Determine CIBIL_STATUS based on the score value
                                        int scoreValue = Integer.parseInt(score);
                                        if (scoreValue < 650) {
                                            dataMap.put(AppConstants.CIBIL_STATUS, "Fail");
                                        } else {
                                            dataMap.put(AppConstants.CIBIL_STATUS, "Pass");
                                        }
                                    });
                                });
                    });
            Optional.ofNullable(proposalDetails.getQuotePayload())
                    .map(quotePayload -> quotePayload.getApplication())
                    .map(application -> application.getCibilLeadSearchResponse())
                    .map(cibilLeadSearchResponse -> cibilLeadSearchResponse.getScores())
                    .ifPresent(scoresList -> {
                        scoresList.stream()
                                .filter(scores -> AppConstants.CIBILTUSC1.equalsIgnoreCase(scores.getScoreName()))
                                .findFirst()
                                .ifPresent(scores -> {
                                    Optional.ofNullable(scores.getScore()).ifPresent(score -> {
                                        dataMap.put(AppConstants.CIBIL_INCOME, CibilIncomeMap.containsKey(score) ? CibilIncomeMap.get(score) : AppConstants.HYPHEN );
                                    });
                                });
                    });
            // If no score was found or set, default CIBIL_STATUS to blank
            dataMap.putIfAbsent(AppConstants.CIBIL_STATUS, AppConstants.BLANK);
            dataMap.putIfAbsent(AppConstants.CIBIL_INCOME, AppConstants.BLANK);
        } catch (Exception e) {
            logger.error("Data addition failed in PayU Document for transactionId {}", transactionId, e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed for PayU Document");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        payUDocumentContext.setVariables(dataMap);
        logger.info("End of PayU Document Mapper for transactionId {}", transactionId);
        return payUDocumentContext;
    }
}
