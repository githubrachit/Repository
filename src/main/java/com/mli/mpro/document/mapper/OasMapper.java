package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.OasDetails;
import com.mli.mpro.proposal.models.ProductInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SalesStoriesProductDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.*;

import static com.mli.mpro.utils.Utility.isAnyObjectNull;

@Service
public class OasMapper {
    private static final Logger logger = LoggerFactory.getLogger(OasMapper.class);
    private static final  String LIFE_INSURANCE_COMPANY_NAME = "Max Life Insurance";
    /**
     * Mapping data from Document OAS to Context DataMap
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfOasDocument(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START Oas Data population for transactionId {}", proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        String empCodeOfTheSeller = AppConstants.BLANK;
        String proposerCustomerId = AppConstants.BLANK;
        String proposerName     = AppConstants.BLANK;
        String lifeInsuredName = AppConstants.BLANK;
        String planName = AppConstants.BLANK;
        String premiumAmount = AppConstants.BLANK;
        String premiumPaymentTerm = AppConstants.BLANK;
        String policyTerm = AppConstants.BLANK;
        String crmLeadId = AppConstants.BLANK;
        String spName = AppConstants.BLANK;
        String spCode = AppConstants.BLANK;
        boolean isStrategicBranch = false;
        String proposerMobileNo = AppConstants.BLANK;

        String policyNo = proposalDetails.getApplicationDetails().getPolicyNumber();
        if(!isAnyObjectNull(proposalDetails.getOasDetails(), proposalDetails.getOasDetails().getAxisBranchDetails())){
            isStrategicBranch = AppConstants.YES.equalsIgnoreCase(proposalDetails.getOasDetails().getAxisBranchDetails().getStrategicBranch());
            setBranchHeadDetails( proposalDetails.getOasDetails(),dataVariables);
            setClusterHeadDetails( proposalDetails.getOasDetails(),isStrategicBranch,dataVariables);
        }
        if(!isAnyObjectNull(proposalDetails.getSourcingDetails())){
            empCodeOfTheSeller = (proposalDetails.getSourcingDetails().getSpecifiedPersonDetails() != null)
                    ? proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode() : AppConstants.BLANK ;
        }
        if(!isAnyObjectNull(proposalDetails.getBancaDetails())){
            proposerCustomerId = proposalDetails.getBancaDetails().getCustomerId();
            crmLeadId = proposalDetails.getBancaDetails().getLeadId();
        }
        if(!isAnyObjectNull(proposalDetails.getPartyInformation(),
                Utility.getPartyInformationByPartyType(proposalDetails, AppConstants.PROPOSER),
                Utility.getPartyInformationByPartyType(proposalDetails, AppConstants.INSURED),
                Utility.getPartyInformationByPartyType(proposalDetails, AppConstants.PROPOSER).getPersonalIdentification(),
                Utility.getPartyInformationByPartyType(proposalDetails, AppConstants.PROPOSER).getPersonalIdentification().getPhone())) {
            proposerName = Utility.getFullNameByType(proposalDetails, AppConstants.PROPOSER);
            lifeInsuredName = Utility.getFullNameByType(proposalDetails, AppConstants.INSURED);
            proposerMobileNo = Utility.getPartyInformationByPartyType(proposalDetails, AppConstants.PROPOSER).getPersonalIdentification().getPhone().get(0).getPhoneNumber();
        }
        if(!isAnyObjectNull(proposalDetails.getProductDetails(),
                proposalDetails.getProductDetails().get(0),
                proposalDetails.getProductDetails().get(0).getProductInfo(),
                proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse())){
            ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
            planName = checkComboProductAndFetchProductName(proposalDetails.getSalesStoriesProductDetails(),productInfo.getProductName());
            premiumAmount = productInfo.getProductIllustrationResponse().getInitialPremiumPaid();
            premiumPaymentTerm = productInfo.getPremiumPaymentTerm();
            policyTerm = productInfo.getPolicyTerm();
        }
        if(!isAnyObjectNull(proposalDetails.getSourcingDetails(), proposalDetails.getSourcingDetails().getSpecifiedPersonDetails())){
            spName = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpName();
            spCode = proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode();
        }
        try {
            logger.info("Initiating processing proposalDetails for Oas Document for transactionId {}", proposalDetails.getTransactionId());
            dataVariables.put("empCodeOfTheSeller", nAForEmptyString(empCodeOfTheSeller));
            dataVariables.put("proposerCustomerId",nAForEmptyString(proposerCustomerId));
            dataVariables.put("proposerName",nAForEmptyString(proposerName));
            dataVariables.put("lifeInsuredName",nAForEmptyString(lifeInsuredName));
            dataVariables.put("lifeInsuranceCompanyName",LIFE_INSURANCE_COMPANY_NAME);
            dataVariables.put("policyNo", nAForEmptyString(policyNo));
            dataVariables.put("planName",nAForEmptyString(planName));
            dataVariables.put("premiumAmount",nAForEmptyString(premiumAmount));
            dataVariables.put("premiumPaymentTerm", nAForEmptyString(premiumPaymentTerm));
            dataVariables.put("policyTerm", nAForEmptyString(policyTerm));
            dataVariables.put("crmLeadId",nAForEmptyString(crmLeadId));
            dataVariables.put("spName", nAForEmptyString(spName));
            dataVariables.put("spCode",nAForEmptyString(spCode));
            dataVariables.put("strategicBranch", isStrategicBranch);
            dataVariables.put("proposerMobileNo", nAForEmptyString(proposerMobileNo));
            dataVariables.put("policyNumber",nAForEmptyString(proposalDetails.getApplicationDetails().getPolicyNumber()));

        } catch (Exception ex) {
            logger.error("Data addition failed for Oas Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context oasDetailsCxt = new Context();
        oasDetailsCxt.setVariables(dataVariables);
        logger.info("END Oas Data population");
        return oasDetailsCxt;
    }
    private String checkComboProductAndFetchProductName(SalesStoriesProductDetails salesStoriesProductDetails,String productName) {
        if(Objects.nonNull(salesStoriesProductDetails) && AppConstants.YES.equalsIgnoreCase(salesStoriesProductDetails.getIsSalesProduct())
                && !CollectionUtils.isEmpty(salesStoriesProductDetails.getProductDetails())
                && Objects.nonNull(salesStoriesProductDetails.getProductDetails().get(0).getProductInfo())){
            productName = salesStoriesProductDetails.getProductDetails().get(0).getProductInfo().getProductName();
        }
        return productName;
    }
    private String nAForEmptyString(String str){
        return StringUtils.isEmpty(str) ? AppConstants.NA : str;
    }
    private void setBranchHeadDetails(OasDetails oasDetails,Map<String, Object> dataVariables){
        String existingCustomerBH = AppConstants.BLANK;
        String metOrSpokeDateForBh = AppConstants.BLANK;
        String approvalDateTimeBH = AppConstants.BLANK;
        String metSpokeBH = AppConstants.BLANK;
        String  branchName = oasDetails.getAxisBranchDetails().getBranchName();
        String solId = oasDetails.getAxisBranchDetails().getSolId();
        String nameOfTheBranchHead = oasDetails.getAxisBranchDetails().getBranchHeadName();
        String phoneBH = Objects.nonNull(oasDetails.getAxisBranchDetails().getBranchHeadMob())?oasDetails.getAxisBranchDetails().getBranchHeadMob():AppConstants.BLANK;
        String emailBH = Objects.nonNull(oasDetails.getAxisBranchDetails().getBranchHeadEmailId())?oasDetails.getAxisBranchDetails().getBranchHeadEmailId():AppConstants.BLANK;
        if(Objects.nonNull(oasDetails.getBhDetails())){
            metOrSpokeDateForBh = Utility.dateFormatterWithTimeZone(Utility.dateFormatter(oasDetails.getBhDetails().getMetOrSpokeDateForBh(),
                            AppConstants.YYYY_MM_DD_HH_MM_SS, AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A),
                    AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
            approvalDateTimeBH = Utility.dateFormatterWithTimeZone(Utility.dateFormatter(oasDetails.getBhDetails().getBhApprovalTime(),
                            AppConstants.E_MMM_DD_YYYY_HH_MM_SS_Z, AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A),
                    AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
            existingCustomerBH = AppConstants.TRUE.equalsIgnoreCase(oasDetails.getBhDetails().getIsApplicantExistingCustomerForBh()) ? AppConstants.CAMEL_YES : AppConstants.CAMEL_NO ;
            metSpokeBH = AppConstants.PHONE_CALL.equalsIgnoreCase(oasDetails.getBhDetails().getPhoneCallOrPersonMeetForBh()) ? AppConstants.SPOKEN : AppConstants.MET;
        }
        dataVariables.put("branchName", nAForEmptyString(branchName));
        dataVariables.put("solId", nAForEmptyString(solId));
        dataVariables.put("nameOfTheBranchHead",nAForEmptyString(nameOfTheBranchHead));
        dataVariables.put("existingCustomerBH",nAForEmptyString(existingCustomerBH));
        dataVariables.put("BmMet_spokeToCustomerDateBH",nAForEmptyString(metOrSpokeDateForBh));
        dataVariables.put("met_spokeBH",nAForEmptyString(metSpokeBH));
        dataVariables.put("approvalDateTimeBH",nAForEmptyString(approvalDateTimeBH));
        dataVariables.put("phoneBH",nAForEmptyString(phoneBH));
        dataVariables.put("emailBH",nAForEmptyString(emailBH));
    }
    private void setClusterHeadDetails(OasDetails oasDetails,boolean isStrategicBranch,  Map<String, Object> dataVariables){
        String nameOfTheClusterHead = AppConstants.BLANK;
        String existingCustomerCH = AppConstants.BLANK;
        String nameOfCH = AppConstants.BLANK;
        String  approvalDateTimeCH = AppConstants.BLANK;
        String phoneCH = AppConstants.BLANK;
        String emailCH = AppConstants.BLANK;
        String metOrSpokeDateForCh = AppConstants.BLANK;
        if(!isStrategicBranch){
            nameOfTheClusterHead = oasDetails.getAxisBranchDetails().getClusterHeadName();
            nameOfCH = oasDetails.getAxisBranchDetails().getClusterHeadName();
            phoneCH = oasDetails.getAxisBranchDetails().getClusterHeadMob();
            emailCH = oasDetails.getAxisBranchDetails().getClusterHeadEmailId();
            if(Objects.nonNull(oasDetails.getChDetails())){
                existingCustomerCH = AppConstants.TRUE.equalsIgnoreCase(oasDetails.getChDetails().getIsApplicantExistingCustomerForCh()) ? AppConstants.CAMEL_YES : AppConstants.CAMEL_NO ;
                metOrSpokeDateForCh = Utility.dateFormatterWithTimeZone(Utility.dateFormatter(oasDetails.getChDetails().getMetOrSpokeDateForCh()
                                ,AppConstants.YYYY_MM_DD_HH_MM_SS, AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A),
                        AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
                approvalDateTimeCH = Utility.dateFormatterWithTimeZone(Utility.dateFormatter(oasDetails.getChDetails().getChApprovalTime(),
                                AppConstants.E_MMM_DD_YYYY_HH_MM_SS_Z, AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A),
                        AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
            }
        }
        dataVariables.put("existingCustomerCH",nAForEmptyString(existingCustomerCH));
        dataVariables.put("nameOfTheClusterHead", nAForEmptyString(nameOfTheClusterHead));
        dataVariables.put("CHName",nAForEmptyString(nameOfCH));
        dataVariables.put("approvalDateTimeCH",nAForEmptyString(approvalDateTimeCH));
        dataVariables.put("phoneCH",nAForEmptyString(phoneCH));
        dataVariables.put("emailCH",nAForEmptyString(emailCH));
        dataVariables.put("BmMet_spokeToCustomerDateCH", nAForEmptyString(metOrSpokeDateForCh));
    }
}
