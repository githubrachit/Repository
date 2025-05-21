package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GSTWaiverDeclarationMapper {

    private static final Logger logger = LoggerFactory.getLogger(GSTWaiverDeclarationMapper.class);
    Map<String, String> proposalFormVersion = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;
        {
            put("A", AppConstants.PDF_VERSION);
            put("F", AppConstants.PDF_VERSION);
            put("T", AppConstants.PDF_VERSION);
            put("K", AppConstants.PDF_VERSION);
            put("B", AppConstants.PDF_VERSION);
            put("B2", AppConstants.PDF_VERSION);
            put("BY", AppConstants.PDF_VERSION);
            put("X", AppConstants.PDF_VERSION);
            put("P", AppConstants.PDF_VERSION);
            put("LX", AppConstants.PDF_VERSION);
        }

    };

    @Autowired
    private SellerConsentDetailsRepository sellerConsentDetailsRepository;

    private String signaturDate;
    private String policyNumber;
    private String fullName="";
    private String fullAddress="";

    private Date otpConfirmation=null;
    public Context setDataOfGSTWaiverDeclarationDocument(ProposalDetails proposalDetails) {
        logger.info("START GSTWaiverDeclaration Data population");
        if(Objects.nonNull(proposalDetails.getPosvDetails()) && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus())
                && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate()))
        {
            otpConfirmation = (proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate());
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat posvDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        posvDateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));

        long transactionId = proposalDetails.getTransactionId();
        Map<String, Object> dataVariables = new HashMap<>();
        try {
            String otpConfirmationDate = otpConfirmation != null ? format.format(otpConfirmation) : StringUtils.EMPTY;
            Date sellerDeclarationSubmissionDate = Utility.otpDatePF(proposalDetails, sellerConsentDetailsRepository);
            logger.info("seller declaration date : {}",sellerDeclarationSubmissionDate);
            String otpConfirmationSellerDeclarationDate = Utility.getOTPforPF(proposalDetails, format, otpConfirmationDate, sellerDeclarationSubmissionDate);
            policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            signaturDate = otpConfirmationSellerDeclarationDate;
            if(Objects.nonNull(proposalDetails.getPartyInformation())
                    && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                    && (Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails()))) {
                StringBuilder name = new StringBuilder();
                setName(name, proposalDetails);
                fullName=name.toString();
                //appennding the address in the mapper
                if(Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress())
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0))
                        && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails())){
                    StringBuilder address = new StringBuilder();
                    setAddress(address, proposalDetails);
                    fullAddress=address.toString();
                }
            }
            dataVariables.put("policyNumber",policyNumber);
            dataVariables.put("signaturDate",signaturDate);
            dataVariables.put("name",fullName);
            dataVariables.put("address",fullAddress);

        } catch (Exception ex) {
            logger.error("Exception occured for GSTWaiver document mapper for transaction {} with exception {} ", transactionId, ex.getMessage());
        }
        Context gstWaiverDeclarationDetailsCxt = new Context();
        gstWaiverDeclarationDetailsCxt.setVariables(dataVariables);
        logger.info("END NRI GST WAIVER population");
        return gstWaiverDeclarationDetailsCxt;
    }

    private void setName(StringBuilder name, ProposalDetails proposalDetails) {
        //appending the firstName, middleName & lastName in the mapper
        if (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName())) {
            name.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName()).append(AppConstants.WHITE_SPACE);
        }
        if (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getMiddleName()))
            name.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getMiddleName()).append(AppConstants.WHITE_SPACE);

        if (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName())) {
            name.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName()).append(AppConstants.WHITE_SPACE);
        }
    }

    private void setAddress(StringBuilder address, ProposalDetails proposalDetails){
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getArea())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getArea()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getVillage())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getVillage()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getLandmark())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getLandmark()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getState())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getState()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry()).append(AppConstants.COMMA_WITH_SPACE);
        }
        if(!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode())) {
            address.append(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode()).append(AppConstants.WHITE_SPACE);
        }
    }
}

