package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.thymeleaf.context.Context;

import java.util.*;

public class NeoPennyDropMapper {
    private NeoPennyDropMapper(){
        //private constructor
    }
    private static final Logger logger = LoggerFactory.getLogger(NeoPennyDropMapper.class);

    public static Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {

        logger.info("Start mapping data for penny drop for equote number::{}", proposalDetails.getEquoteNumber());
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put(AppConstants.POLICY_NUMBER, AppConstants.BLANK);
        dataMap.put(AppConstants.PROPOSER_NAME, AppConstants.BLANK);
        dataMap.put(AppConstants.BANK_ACCOUNT_NUMBER, AppConstants.BLANK);
        dataMap.put(AppConstants.MICR_CODE, AppConstants.BLANK);
        dataMap.put(AppConstants.IFSC_CODE, AppConstants.BLANK);
        dataMap.put(AppConstants.BRANCH_NAME, AppConstants.BLANK);
        dataMap.put(AppConstants.IS_PENNYDROP_VERIFIED, AppConstants.NO);
        dataMap.put(AppConstants.DATE, AppConstants.BLANK);

        try {
            dataMap.put(AppConstants.DATE, DateTimeUtils.format(new Date(), AppConstants.DATE_FORMAT));

            Optional.ofNullable(proposalDetails.getApplicationDetails()).ifPresent(
                    applicationDetails -> dataMap.put(AppConstants.POLICY_NUMBER, applicationDetails.getPolicyNumber())
            );
            proposalDetails.getPartyInformation().stream().findFirst().ifPresent(
                    partyInformation -> {
                        Optional.ofNullable(partyInformation.getBasicDetails()).ifPresent(
                                basicDetails -> dataMap.put(AppConstants.PROPOSER_NAME,
                                        Utility.getFullName(basicDetails.getFirstName(), basicDetails.getMiddleName(), basicDetails.getLastName()))
                        );
                        Optional.ofNullable(partyInformation.getBankDetails()).ifPresent(
                                bankDetails -> {
                                    dataMap.put(AppConstants.BANK_ACCOUNT_NUMBER, bankDetails.getBankAccountNumber());
                                    dataMap.put(AppConstants.MICR_CODE, bankDetails.getMicr());
                                    dataMap.put(AppConstants.IFSC_CODE, bankDetails.getIfsc());
                                    dataMap.put(AppConstants.BRANCH_NAME, bankDetails.getBankBranch());
                                    dataMap.put(AppConstants.IS_PENNYDROP_VERIFIED, Utility.convertToYesOrNo(bankDetails.getIsPennyDropVerified()));
                                }
                        );
                    }
            );
        } catch (Exception ex) {
            logger.error("Data addition failed for neo penny drop Document for equote number::{}", proposalDetails.getEquoteNumber(), ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context neoPennyDropContext = new Context();
        neoPennyDropContext.setVariables(dataMap);
        logger.info("End Neo penny drop mapping for equote number::{}", proposalDetails.getEquoteNumber());
        return neoPennyDropContext;
    }
}
