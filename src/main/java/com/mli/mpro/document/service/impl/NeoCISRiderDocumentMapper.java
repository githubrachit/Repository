package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NeoCISRiderDocumentMapper {

    private static final Logger logger = LoggerFactory.getLogger(NeoCISRiderDocumentMapper.class);

    @Value("${cis.ci.rider.name}")
    private String ciRiderName;

    @Value("${cis.wop.rider.name}")
    private String wopRiderName;

    @Value("${cis.add.rider.name}")
    private String addRiderName;

    @Value("${cis.add.rider.uin}")
    private String addRiderUIN;

    @Value("${cis.add.rider.old.uin}")
    private String addRiderOldUIN;

    public Context setCISRiderData (ProposalDetails proposalDetails, List<RiderDetails> riderDetailsList) throws UserHandledException {
        logger.info("Mapping for Neo CIS Rider Document for transactionId {}",proposalDetails.getTransactionId());
        Context context = new Context();
        Map<String, Object> dataForDocument = new HashMap<>();

       if (!riderDetailsList.isEmpty()) {
           try {
             for (RiderDetails riderDetails : riderDetailsList) {
                 settingRiderData(riderDetails, proposalDetails, dataForDocument);
             }
           } catch (Exception e) {
               logger.error("Rider Details not found:", e);
               throw new UserHandledException();
           }
       }
        context.setVariables(dataForDocument);
        return context;
    }

    private void settingRiderData(RiderDetails riderDetails, ProposalDetails proposalDetails, Map<String, Object> dataForDocument) {
        String ciRider = "";
        String wopRider = "";
        String addRider = "";
        double ciRiderSumAssured = 0;
        double addRiderSumAssured = 0;
        String ciRiderNumber = "";
        String wopRiderNumber = "";
        String addRiderNumber = "";
        String addRiderUin = "";
        String place = "";
        String date = "";
        String riderType = riderDetails.getRiderInfo();
        String policyNumber = (Objects.nonNull(proposalDetails.getApplicationDetails()) &&
                Objects.nonNull(proposalDetails.getApplicationDetails().getPolicyNumber()))
                ? proposalDetails.getApplicationDetails().getPolicyNumber()
                : AppConstants.BLANK;

        if (AppConstants.CI_RIDER_LIST.contains(riderType)) {
            logger.info("Addition fo details for CI Rider for transactionId:{}", proposalDetails.getTransactionId());
            ciRider = ciRiderName;
            ciRiderNumber = policyNumber;
            boolean isNullOrEmptySumAssured = isNullOrEmpty(riderDetails.getRiderSumAssured());
            if (isNullOrEmptySumAssured) {
                ciRiderSumAssured = 0.0;
            } else {
                ciRiderSumAssured = riderDetails.getRiderSumAssured();
            }
        }

        if (AppConstants.WOP_RIDER_LIST.contains(riderType)) {
            logger.info("Addition fo details for WOP Rider for transactionId:{}", proposalDetails.getTransactionId());
            wopRider = wopRiderName;
            wopRiderNumber = policyNumber;
        }

        if (AppConstants.ADD_RIDER_LIST.contains(riderType)) {
            logger.info("Addition fo details for ADD Rider for transactionId:{}", proposalDetails.getTransactionId());
            addRider = addRiderName;
            addRiderUin = settingAddRiderUIN(proposalDetails);
            addRiderNumber = policyNumber;
            boolean isNullOrEmptySumAssured = isNullOrEmpty(riderDetails.getRiderSumAssured());
            if (isNullOrEmptySumAssured) {
                addRiderSumAssured = 0.0;
            } else {
                addRiderSumAssured = riderDetails.getRiderSumAssured();
            }
        }

        PartyInformation partyInformation = null;

        if(Objects.nonNull(proposalDetails.getPartyInformation()) && !proposalDetails.getPartyInformation().isEmpty()) {
            partyInformation = proposalDetails.getPartyInformation().get(0);
        }

        place = getPlace(partyInformation);
        date = getDeclarationDate(proposalDetails);

        if (AppConstants.CI_RIDER_LIST.contains(riderType)){
            dataForDocument.put("ciRider", ciRider);
            dataForDocument.put("ciRiderSumAssured", ciRiderSumAssured);
            dataForDocument.put("ciRiderNumber", ciRiderNumber);
        }else if (AppConstants.WOP_RIDER_LIST.contains(riderType)){
            dataForDocument.put("wopRider", wopRider);
            dataForDocument.put("wopRiderNumber", wopRiderNumber);
        }else if (AppConstants.ADD_RIDER_LIST.contains(riderType)){
            dataForDocument.put("addRider", addRider);
            dataForDocument.put("addRiderUin", addRiderUin);
            dataForDocument.put("addRiderNumber", addRiderNumber);
            dataForDocument.put("addRiderSumAssured", String.format("%.2f", addRiderSumAssured));
        }
        dataForDocument.put("place",place);
        dataForDocument.put("date",date);
        dataForDocument.put("policyNumber", policyNumber);
    }

    private String settingAddRiderUIN(ProposalDetails proposalDetails) {
        String addRiderUin;
        if (AppConstants.NEO_YES.equalsIgnoreCase(proposalDetails.getIsWipCaseForRateChange())) {
            addRiderUin = addRiderOldUIN;
        } else {
            addRiderUin = addRiderUIN;
        }
        return addRiderUin;
    }

    private boolean isNullOrEmpty(Double riderSumAssured) {
         return riderSumAssured == null || riderSumAssured == 0.0;
    }

    private String getPlace(PartyInformation partyInformation) {
        if (partyInformation != null) {
            BasicDetails basicDetails = partyInformation.getBasicDetails();
            if (basicDetails != null) {
                List<Address> addresses = basicDetails.getAddress();
                if (addresses != null && !addresses.isEmpty()) {
                    AddressDetails addressDetails = addresses.get(0).getAddressDetails();
                    if (addressDetails != null) {
                        return addressDetails.getCity();
                    }
                }
            }
        }
        return AppConstants.BLANK;
    }

    private String getDeclarationDate (ProposalDetails proposalDetails){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String generatedOtp = (proposalDetails != null &&
                proposalDetails.getApplicationDetails() != null  &&
                proposalDetails.getApplicationDetails().getOtpDateTimeStamp() !=null)
                ? proposalDetails.getApplicationDetails().getOtpDateTimeStamp()
                : null;
        return generatedOtp != null ? generatedOtp.substring(0, 10): StringUtils.EMPTY;
    }
}
