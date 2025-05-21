package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.location.services.impl.DocsAppServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GLIPCoverageDetailsMapper {

    private static final Logger logger = LoggerFactory.getLogger(GLIPCoverageDetailsMapper.class);
    private boolean isNeoOrAggregator = false;

    @Autowired
    CoverageDetailsMapper coverageDetailsMapper;

    @Autowired
    private DocsAppServiceImpl docsAppServiceImpl;

    public Context setDataOfCoverageDetails(ProposalDetails proposalDetails) throws UserHandledException {

        if (Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getChannelDetails())) {
            isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                    || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
        }

        Context context = new Context();
        Map<String, Object> dataVariables = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat output = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
        output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String totalPremium = StringUtils.EMPTY;
        logger.info("Mapping coverage details of proposal form document for transactionId {}",
                proposalDetails.getTransactionId());
        try {
            ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
            BankDetails bankdetails = null;
            if (Objects.nonNull(proposalDetails.getBank()) &&
                    Objects.nonNull(proposalDetails.getBank().getBankDetails())) {
                bankdetails = proposalDetails.getBank().getBankDetails().get(0);
            }
            String singleImmediate = StringUtils.EMPTY;
            String singleImmediateDeath = StringUtils.EMPTY;
            String jointImmediate = StringUtils.EMPTY;
            String jointImmediateDeath = StringUtils.EMPTY;
            String singleDeferredDeath = StringUtils.EMPTY;
            String jointDeferredDeath = StringUtils.EMPTY;
            String singleDeferredDeathYears = StringUtils.EMPTY;
            String jointDeferredDeathYears = StringUtils.EMPTY;
            String secondInsurerAbhaNumber = AppConstants.BLANK;
            String firstInsurerAbhaNumber = AppConstants.BLANK;

            //FUL2-159689_SWAGPP_NON_POS_PF_CHANGES
            String annuityOption = productDetails.getProductInfo().getAnnuityOption();
            String immediateAnnuity = StringUtils.EMPTY;
            String defferedAnnuity = StringUtils.EMPTY;
            String defermentPeriod = StringUtils.EMPTY;
            String defermentYear = StringUtils.EMPTY;
            String defermentMonths = StringUtils.EMPTY;
            String immediateAnnuityLastSurvivour = StringUtils.EMPTY;
            String immediateAnnuityEarlyRop = StringUtils.EMPTY;
            String immediateAnnuitylifeThereafter = StringUtils.EMPTY;
            String increasingAnnuityEverYear = StringUtils.EMPTY;
            String increasingAnnuityEveryThreeYear = StringUtils.EMPTY;
            String proportionOfAnnuityLastSurvivor = StringUtils.EMPTY;
            String mileStoneAge = StringUtils.EMPTY;
            String guaranteeAnnuityPeriod = StringUtils.EMPTY;
            String increasingAnnuityPercentage = StringUtils.EMPTY;
            String ropSingleLife = StringUtils.EMPTY;
            String ropJointLife = StringUtils.EMPTY;
            String deathBenefitForAnnuity = productDetails.getProductInfo().getReturnOfPremium();
            if(AppConstants.ANNUITY_PRODUCTS.contains(productDetails.getProductInfo().getProductId())
            		&& !AppConstants.SWAGPP.equalsIgnoreCase(productDetails.getProductInfo().getProductId())) {
            	deathBenefitForAnnuity = productDetails.getProductInfo().getDeathBenefitForAnnuity();
            }

            if (productDetails.getProductInfo().getAnnuityOption().equalsIgnoreCase(AppConstants.SINGLE_LIFE_ANNUITY_OPTION)) {
                if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.IMMEDIATE_ANNUITY_OPTION)) {
                    if (productDetails.getProductInfo().getDeathBenefitForAnnuity().equalsIgnoreCase(AppConstants.YES)) {
                        singleImmediateDeath = AppConstants.YES;
                    } else {
                        singleImmediate = AppConstants.YES;
                    }
                    immediateAnnuity=AppConstants.YES;
                    ropSingleLife = deathBenefitForAnnuity.equalsIgnoreCase(AppConstants.YES)
                            ? productDetails.getProductInfo().getReturnOfPremiumPercentage()
                            : ropSingleLife;
                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.DEFERRED_ANNUITY_OPTION)) {
                    defferedAnnuity = AppConstants.YES;
                    defermentYear = productDetails.getProductInfo().getDefermentPeriod()+ AppConstants.YEARS;
                    defermentMonths = productDetails.getProductInfo().getDefermentPeriodMonth() + AppConstants.NO_OF_MONTHS;
                    defermentPeriod= defermentYear+AppConstants.SPACE+defermentMonths;

                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.DEFERRED_ANNUITY_OPTION) &&
                        productDetails.getProductInfo().getDeathBenefitForAnnuity().equalsIgnoreCase(AppConstants.YES)) {
                    singleDeferredDeath = AppConstants.YES;
                    singleDeferredDeathYears =
                            productDetails.getProductInfo().getDefermentPeriod() + AppConstants.YEARS;
                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.IA_EARLY_ROP)) {
                    immediateAnnuityEarlyRop = AppConstants.YES;
                    ropSingleLife = deathBenefitForAnnuity.equalsIgnoreCase(AppConstants.YES)
                            ? productDetails.getProductInfo().getEarlyROPPercentage()
                            : ropSingleLife;
                    mileStoneAge= productDetails.getProductInfo().getMilestoneAge();

                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.IA_LIFE_THEREAFTER)) {
                    immediateAnnuitylifeThereafter=AppConstants.YES;
                    guaranteeAnnuityPeriod = productDetails.getProductInfo().getGuaranteeAnnuityPeriod();

                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.INCREASING_IA)) {
                    if(productDetails.getProductInfo().getIncreasingAnnuityFrequency().equalsIgnoreCase(AppConstants.ONE)){
                        increasingAnnuityEverYear = AppConstants.YES;
                        increasingAnnuityPercentage= productDetails.getProductInfo().getIncreasingAnnuityPercentage();
                    }
                    if(productDetails.getProductInfo().getIncreasingAnnuityFrequency().equalsIgnoreCase(AppConstants.THREE)){
                        increasingAnnuityEveryThreeYear = AppConstants.YES;
                    }

                }
            } else if (productDetails.getProductInfo().getAnnuityOption().equalsIgnoreCase(AppConstants.JOINT_LIFE_ANNUITY_OPTION)) {
                if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.IMMEDIATE_ANNUITY_OPTION)) {
                    if (productDetails.getProductInfo().getDeathBenefitForAnnuity().equalsIgnoreCase(AppConstants.YES)) {
                        jointImmediateDeath = AppConstants.YES;
                    } else {
                        jointImmediate = AppConstants.YES;
                    }
                    immediateAnnuity=AppConstants.YES;
                    ropSingleLife = deathBenefitForAnnuity.equalsIgnoreCase(AppConstants.YES)
                            ? productDetails.getProductInfo().getReturnOfPremium1stDeath()
                            : ropSingleLife;

                    ropJointLife = deathBenefitForAnnuity.equalsIgnoreCase(AppConstants.YES)
                            ? productDetails.getProductInfo().getReturnOfPremium2ndDeath()
                            : ropJointLife;
                    
                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.DEFERRED_ANNUITY_OPTION)) {
                    defferedAnnuity = AppConstants.YES;
                    defermentYear = productDetails.getProductInfo().getDefermentPeriod()+ AppConstants.YEARS;
                    defermentMonths = productDetails.getProductInfo().getDefermentPeriodMonth() + AppConstants.NO_OF_MONTHS;
                    defermentPeriod= defermentYear+AppConstants.SPACE+defermentMonths;
                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.DEFERRED_ANNUITY_OPTION) &&
                        productDetails.getProductInfo().getDeathBenefitForAnnuity().equalsIgnoreCase(AppConstants.YES)) {
                    jointDeferredDeath = AppConstants.YES;
                    jointDeferredDeathYears =
                            productDetails.getProductInfo().getDefermentPeriod() + AppConstants.YEARS;
                } else if (productDetails.getProductInfo().getAnnuityType().equalsIgnoreCase(AppConstants.IA_LAST_SURVIVOR)) {
                    immediateAnnuityLastSurvivour= AppConstants.YES;
                    proportionOfAnnuityLastSurvivor = productDetails.getProductInfo().getProportionOfAnnuityLastSurvivor();
                }
            }
            String planName = productDetails.getProductInfo().getProductName();
            String micr = Objects.nonNull(bankdetails) ? bankdetails.getMicr() : StringUtils.EMPTY;
            String ifsc = Objects.nonNull(bankdetails) ? bankdetails.getIfsc() : StringUtils.EMPTY;
            String accountNumber = Objects.nonNull(bankdetails) ? bankdetails.getBankAccountNumber() :
                    StringUtils.EMPTY;
            String accHolder = Objects.nonNull(bankdetails) ? bankdetails.getAccountHolderName() : StringUtils.EMPTY;
            String typeOfBank = Objects.nonNull(bankdetails) ? bankdetails.getTypeOfAccount() : StringUtils.EMPTY;
            String bankBranch = Objects.nonNull(bankdetails) ?
                    bankdetails.getBankName().concat(" ").concat(bankdetails.getBankBranch()) : StringUtils.EMPTY;
            String bankingsince = "NA";
            if (proposalDetails != null && proposalDetails.getBank() != null && !proposalDetails.getBank().getBankDetails().isEmpty() && proposalDetails.getBank().getBankDetails().get(0) != null && proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate() != null) {
                bankingsince =
                        String.valueOf(Utility.dateFormatter(proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate()));
            }
            String secAnnMICR;
            String secAnnIFSC;
            String secAnnAccountNumber;
            String secAnnTypeOfBank;
            String secAnnAccHolder;
            String secAnnBankBranch = StringUtils.EMPTY;
            String secAnnBankingsince = "NA";
            secAnnMICR = Objects.nonNull(bankdetails) ? bankdetails.getSecondAnnuitantbankAccountMICR() : "";
            secAnnIFSC = Objects.nonNull(bankdetails) ? bankdetails.getSecondAnnuitantbankAccountIFSC() : "";
            secAnnAccountNumber = Objects.nonNull(bankdetails) ? bankdetails.getSecondAnnuitantbankAccountNo() : "";
            secAnnAccHolder = Objects.nonNull(bankdetails) ?
                    bankdetails.getSecondAnnuitantbankAccountHolderName() : "";
            secAnnTypeOfBank = Objects.nonNull(bankdetails) ? bankdetails.getSecondAnnuitanttypeofAccount() : "";
            if (Objects.nonNull(secAnnTypeOfBank)) {
                secAnnBankBranch = Objects.nonNull(bankdetails) ? bankdetails.getSecondAnnuitantbankName().concat(
                        " ").concat(bankdetails.getSecondAnnuitantbankBranch()) : "";
            }
            if (proposalDetails != null && proposalDetails.getBank() != null && !proposalDetails.getBank().getBankDetails().isEmpty() && proposalDetails.getBank().getBankDetails().get(0) != null && proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate() != null) {
                secAnnBankingsince =
                        String.valueOf(Utility.dateFormatter(proposalDetails.getBank().getBankDetails().get(0).getSecondAnnuitantbankAccOpeningDate()));
            }
            String pan = StringUtils.EMPTY;
            String secAnnPan = StringUtils.EMPTY;
            if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
                    !proposalDetails.getPartyInformation().isEmpty() &&
                    Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()) &&
                    Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails())) {
                pan =
                        proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber();
            }
            if (Objects.nonNull(proposalDetails.getProductDetails()) &&
                    !proposalDetails.getProductDetails().isEmpty() &&
                    Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo()) &&
                    Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getSecondAnnuitantPanNumber())) {
                secAnnPan =
                        proposalDetails.getProductDetails().get(0).getProductInfo().getSecondAnnuitantPanNumber();
            }

            String modeOfPayment = productDetails.getProductInfo().getIncomePayoutFrequency();
            String form60 = "NO";
            String productId = productDetails.getProductInfo().getProductId();
            String panApplied = StringUtils.EMPTY;
            String panAck = StringUtils.EMPTY;
            String appDate = StringUtils.EMPTY;
            String isNRI = StringUtils.EMPTY;
            String isApplicableIncome = StringUtils.EMPTY;
            boolean ispanNotRequired = false;
            String panNotRequired = "NO";
            String secAnnForm60 = "NO";
            String secAnnPanApplied = StringUtils.EMPTY;
            String sourceOfFunds = StringUtils.EMPTY;
            String familyIncomeOption = StringUtils.EMPTY;
            String secAnnPanAck = StringUtils.EMPTY;
            String secAnnAppDate = StringUtils.EMPTY;
            String isChargeableIncome = StringUtils.EMPTY;
            String isIncomeExceedLimit = StringUtils.EMPTY;
            String isTaxableIncome = StringUtils.EMPTY;
            String isItOtherIncome = StringUtils.EMPTY;
            String secAnnIsChargeableIncome = StringUtils.EMPTY;
            String secAnnIsIncomeExceedLimit = StringUtils.EMPTY;
            String secAnnIsTaxableIncome = StringUtils.EMPTY;
            String secAnnIsItOtherIncome = StringUtils.EMPTY;
            String secAnnIsNRI = StringUtils.EMPTY;
            String secAnnIsApplicableIncome = StringUtils.EMPTY;
            boolean secAnnIspanNotRequired = false;
            String secAnnPanNotRequired = "NO";
            String isThisGlipProduct = AppConstants.YES;
            dataVariables = setPayorSectionDetails(proposalDetails,dataVariables);
            if (StringUtils.isEmpty(pan)) {
                pan = "NO";
                ispanNotRequired = true;
                form60 = "YES";
                Form60Details form60Details = proposalDetails.getForm60Details();
                if (Objects.nonNull(form60Details)) {
                    panApplied =
                            Utility.evaluateConditionalOperation(form60Details.getDetailsOfDontHavePan().equalsIgnoreCase("YES"), "YES", "NO");
                    appDate = Utility.dateFormatter(form60Details.getPanApplicationDate());
                    panAck = form60Details.getPanAcknowledgementNo();
                    isIncomeExceedLimit = Utility.evaluateConditionalOperation(form60Details.isIncomeExceedLimit(),
                            "YES", "NO");
                    isTaxableIncome = Utility.evaluateConditionalOperation(form60Details.isTaxableIncome(), "YES",
                            "NO");
                    isChargeableIncome = Utility.evaluateConditionalOperation(form60Details.isChargeableIncome(),
                            "YES", "NO");
                    isItOtherIncome = Utility.evaluateConditionalOperation(form60Details.isItOtherIncome(), "YES",
                            "NO");
                    isNRI = Utility.evaluateConditionalOperation(form60Details.isNRI(), "YES", "NO");
                    isApplicableIncome = Utility.evaluateConditionalOperation(form60Details.isApplicableIncome(),
                            "YES", "NO");
                }
                panNotRequired = Utility.evaluateConditionalOperation(ispanNotRequired, "YES", "NO");
            }
            /*
			 * Start of FUL2-20134
			 * for SPP product we need to remove the Deferment Period details from pdf, for that we are using below tag to hide 
			 * the content. if isThisGlipProduct is 'Yes' we show content else we are going to hide it. 
			 */
           
//            if ((AppConstants.SPP_ID).equalsIgnoreCase(productId)) {
//            	isThisGlipProduct = AppConstants.NO;
//            }
            
            /*
             * end of FUL2-20134 - proposal form coverage details changes
             **/
            // Mapping of Form 60 details in case of GLIP
            //Mapping of Form 60 details in case of SGPP
            if ((AppConstants.ANNUITY_PRODUCTS.contains(productId))
                    && StringUtils.isEmpty(secAnnPan)){
                secAnnPan = "NO";
                if (productDetails.getProductInfo().getAnnuityOption().equalsIgnoreCase(AppConstants.JOINT_LIFE_ANNUITY_OPTION)) {
                    secAnnForm60 = "YES";
                    secAnnIspanNotRequired = true;
                }
                Form60Details form60Details = proposalDetails.getForm60Details();
                if (Objects.nonNull(form60Details)) {
                    secAnnPanApplied =
                            Utility.evaluateConditionalOperation(form60Details.getSecondAnnuitantDetailsOfDontHavePan().equalsIgnoreCase("YES"), "YES", "NO");
                    secAnnPanAck = form60Details.getSecondAnnuitantPanAcknowledgementNo();
                    secAnnAppDate = Utility.dateFormatter(form60Details.getSecondAnnuitantPanApplicationDate());
                    secAnnIsChargeableIncome =
                            Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantChargeableIncome(),
                                    "YES", "NO");
                    secAnnIsIncomeExceedLimit =
                            Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantIncomeExceedLimit(),
                                    "YES", "NO");
                    secAnnIsTaxableIncome =
                            Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantTaxableIncome(),
                                    "YES"
                                    , "NO");
                    secAnnIsItOtherIncome =
                            Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantItOtherIncome(),
                                    "YES"
                                    , "NO");
                    secAnnIsNRI = Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantNRI(), "YES",
                            "NO");
                    secAnnIsApplicableIncome =
                            Utility.evaluateConditionalOperation(form60Details.isSecondAnnuitantApplicableIncome(),
                                    "YES", "NO");
                }
                secAnnPanNotRequired = Utility.evaluateConditionalOperation(secAnnIspanNotRequired, "YES", "NO");
            }
            if (AppConstants.ANNUITY_PRODUCTS.contains(productId) ) {
            	  sourceOfFunds = proposalDetails.getPartyInformation().stream()
         				.filter(Objects::nonNull)
         				.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()))
         				.filter(Objects::nonNull).map(PartyInformation::getBasicDetails).filter(Objects::nonNull)
         				.findFirst().map(BasicDetails::getSourceOfFunds).orElse("");
            	
            }
            if (isNeoOrAggregator) {
                sourceOfFunds = proposalDetails.getPartyInformation().stream()
                        .filter(Objects::nonNull)
                        .filter(Objects::nonNull).map(PartyInformation::getBasicDetails).filter(Objects::nonNull)
                        .findFirst().map(BasicDetails::getSourceOfFunds).orElse("");
            }
            //FUL2-46213
			String npsCustomer = proposalDetails.getPartyInformation().stream()
					.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType())).findFirst()
					.map(PartyInformation::getBasicDetails).map(BasicDetails::getIsNPSCustomer).orElse("");
			if (AppConstants.ANNUITY_PRODUCTS.contains(productId) && AppConstants.YES.equalsIgnoreCase(npsCustomer)) {
				familyIncomeOption = Optional.ofNullable(productDetails).map(ProductDetails::getProductInfo)
						.map(ProductInfo::getFamilyIncomeOption).orElse("");
			}
            // FUL2-195747 PF form changes for ABHA Id
            if (Utility.isJointLifeCase(proposalDetails)) {
                secondInsurerAbhaNumber = Utility.setSecondInsurerJointLifeAbhaNumberInPfForm(proposalDetails);
                firstInsurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
            } else {
                firstInsurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
            }

            dataVariables.put("sourceOfFunds", sourceOfFunds);
            dataVariables.put("singleImmediate", singleImmediate);
            dataVariables.put("singleImmediateDeath", singleImmediateDeath);
            dataVariables.put("jointImmediate", jointImmediate);
            dataVariables.put("jointImmediateDeath", jointImmediateDeath);
            dataVariables.put("singleDeferredDeath", singleDeferredDeath);
            dataVariables.put("jointDeferredDeath", jointDeferredDeath);
            dataVariables.put("singleDeferredDeathYears", singleDeferredDeathYears);
            dataVariables.put("jointDeferredDeathYears", jointDeferredDeathYears);
            dataVariables.put("basePlan", planName);
            dataVariables.put("totalPremium", totalPremium);
            dataVariables.put("modeOfPayment", modeOfPayment);
            dataVariables.put("micrCode", micr);
            dataVariables.put("accountNumber", accountNumber);
            dataVariables.put("IFSCCode", ifsc);
            dataVariables.put("accountHolderName", accHolder);
            dataVariables.put("typeOfAcc", typeOfBank);
            dataVariables.put("bankNameBranch", bankBranch);
            dataVariables.put(AppConstants.BANKING_SINCE, bankingsince);
            dataVariables.put("secAnnMicrCode", secAnnMICR);
            dataVariables.put("secAnnAccountNumber", secAnnAccountNumber);
            dataVariables.put("secAnnIFSCCode", secAnnIFSC);
            dataVariables.put("secAnnAccountHolderName", secAnnAccHolder);
            dataVariables.put("secAnnTypeOfAcc", secAnnTypeOfBank);
            dataVariables.put("secAnnBankNameBranch", secAnnBankBranch);
            dataVariables.put("secAnnBankingSince", secAnnBankingsince);
            dataVariables.put("pan", pan);
            dataVariables.put("isForm60", form60);
            dataVariables.put("appliedForPan", panApplied);
            dataVariables.put("ackNo", panAck);
            dataVariables.put("appDate", appDate);
            dataVariables.put("panNotRequired", panNotRequired);
            dataVariables.put("ispanNotRequired", ispanNotRequired);
            dataVariables.put("secAnnPan", secAnnPan);
            dataVariables.put("secAnnIsForm60", secAnnForm60);
            dataVariables.put("secAnnAppliedForPan", secAnnPanApplied);
            dataVariables.put("secAnnAckNo", secAnnPanAck);
            dataVariables.put("secAnnAppDate", secAnnAppDate);
            dataVariables.put("secAnnPanNotRequired", secAnnPanNotRequired);
            dataVariables.put("secAnnIspanNotRequired", secAnnIspanNotRequired);
            dataVariables.put("income", isChargeableIncome);
            dataVariables.put("businessTurnover", isIncomeExceedLimit);
            dataVariables.put("taxableincome", isTaxableIncome);
            dataVariables.put("agriculture", isItOtherIncome);
            dataVariables.put("nonResident", isNRI);
            dataVariables.put("tribal", isApplicableIncome);
            dataVariables.put("secAnnIncome", secAnnIsChargeableIncome);
            dataVariables.put("secAnnBusinessTurnover", secAnnIsIncomeExceedLimit);
            dataVariables.put("secAnnTaxableincome", secAnnIsTaxableIncome);
            dataVariables.put("secAnnAgriculture", secAnnIsItOtherIncome);
            dataVariables.put("secAnnNonResident", secAnnIsNRI);
            dataVariables.put("secAnnTribal", secAnnIsApplicableIncome);
            dataVariables.put("isThisGlipProduct", isThisGlipProduct);
            dataVariables.put("familyIncomeOption", familyIncomeOption.toUpperCase());
            //FUL2-159429_SWAGPP_NON_POS_PF_CHANGES
            dataVariables.put("annuityOption",annuityOption);
            dataVariables.put("defferedAnnuity", defferedAnnuity);
            dataVariables.put("defermentPeriod",defermentPeriod);
            dataVariables.put("immediateAnnuity", immediateAnnuity);
            dataVariables.put("immediateAnnuityLastSurvivour",immediateAnnuityLastSurvivour);
            dataVariables.put("immediateAnnuitylifeThereafter",immediateAnnuitylifeThereafter);
            dataVariables.put("immediateAnnuityEarlyRop",immediateAnnuityEarlyRop);
            dataVariables.put("increasingAnnuityEveryThreeYear",increasingAnnuityEveryThreeYear);
            dataVariables.put("increasingAnnuityEverYear",increasingAnnuityEverYear);
			dataVariables.put("deathBenefitForAnnuity",
					(AppConstants.DEFERRED_ANNUITY_OPTION.equalsIgnoreCase(productDetails.getProductInfo().getAnnuityType())
					&& AppConstants.SWAGPP.equals(productDetails.getProductInfo().getProductId()))
						? String.join(" - ", deathBenefitForAnnuity.toUpperCase(), productDetails.getProductInfo().getDeferredPeriod())
						: deathBenefitForAnnuity.toUpperCase());
            dataVariables.put("proportionOfAnnuityLastSurvivor",proportionOfAnnuityLastSurvivor);
            dataVariables.put("mileStoneAge",mileStoneAge);
            dataVariables.put("guaranteeAnnuityPeriod",guaranteeAnnuityPeriod);
            dataVariables.put("increasingAnnuityPercentage",increasingAnnuityPercentage);
            dataVariables.put("ropSingleLife",ropSingleLife);
            dataVariables.put("ropJointLife",ropJointLife);
            dataVariables.put("isJointLife",Utility.isJointLifeCase(proposalDetails));
            dataVariables.put("firstInsurerAbhaNumber",firstInsurerAbhaNumber);
            dataVariables.put("secondInsurerAbhaNumber",secondInsurerAbhaNumber);

            dataVariables = coverageDetailsMapper.setPaymentDetails(proposalDetails, dataVariables,isNeoOrAggregator);
            String paymenRenewedBy = getRenewalPaymentMode(proposalDetails);
            dataVariables.put("paymenRenewedBy", paymenRenewedBy);
            dataVariables = setDataForGLIP(productDetails, dataVariables);
            if(isNeoOrAggregator) {
                setdataForNeoBankDetailes(proposalDetails, dataVariables);
            }
            context.setVariables(dataVariables);
        } catch (
                Exception ex) {
            logger.error("Data addition failed for Proposal Form Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Mapping coverage details of proposal form document is completed successfully for " +
                "transactionId " +
                "{}", proposalDetails.getTransactionId());
        return context;
    }

    private void setdataForNeoBankDetailes(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        String isSecondBankAccountDetail = AppConstants.NO;

        setDataForPrimaryBankAccountNumber(proposalDetails,dataVariables);

        if(Utility.isProductSGPPJL(proposalDetails)){
            setDataForSecoundBankDetailes(proposalDetails,dataVariables);
            isSecondBankAccountDetail = setKeyForSecondBankDetails(proposalDetails);
        }
        dataVariables.put("isJointLifeAnnuityBankDetail",isSecondBankAccountDetail);
    }

    private String setKeyForSecondBankDetails(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getSecondaryBankAccountDetails())) {
            return "YES";
        }
        return "NO";
    }

    private void setDataForSecoundBankDetailes(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getSecondaryBankAccountDetails())) {
            String bankBranch = "";
            BankDetails bankdetails = proposalDetails.getPartyInformation().get(0).getSecondaryBankAccountDetails();
            String bankingSince = "NA";
            if (Utility.orTwoExpressions(StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "X"),
                    StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "BY"))) {
                bankingSince = Utility.dateFormatter(bankdetails.getBankAccOpeningDate());
            }
            bankBranch = Objects.nonNull(bankdetails.getBankBranch()) ? (bankdetails.getBankBranch()) : "";
            dataVariables.put("secAnnMicrCode", bankdetails.getMicr());
            dataVariables.put("secAnnIFSCCode", bankdetails.getIfsc());
            dataVariables.put("secAnnAccountNumber", bankdetails.getBankAccountNumber());
            dataVariables.put("secAnnAccountHolderName", bankdetails.getAccountHolderName());
            dataVariables.put("secAnnTypeOfAcc", bankdetails.getTypeOfAccount());
            dataVariables.put("secAnnBankNameBranch", bankBranch);
            dataVariables.put(AppConstants.BANKING_SINCE, bankingSince);
        }
    }

    private void setDataForPrimaryBankAccountNumber(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBankDetails())) {
            String bankBranch = "";
            BankDetails bankdetails = proposalDetails.getPartyInformation().get(0).getBankDetails();
            String bankingSince = "NA";
            if (Utility.orTwoExpressions(StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "X"),
                    StringUtils.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel(), "BY"))) {
                bankingSince = Utility.dateFormatter(bankdetails.getBankAccOpeningDate());
            }
            bankBranch = Objects.nonNull(bankdetails.getBankBranch()) ? (bankdetails.getBankBranch()) : "";
            dataVariables.put("micrCode", bankdetails.getMicr());
            dataVariables.put("IFSCCode", bankdetails.getIfsc());
            dataVariables.put("accountNumber", bankdetails.getBankAccountNumber());
            dataVariables.put("accountHolderName", bankdetails.getAccountHolderName());
            dataVariables.put("typeOfAcc", bankdetails.getTypeOfAccount());
            dataVariables.put("bankNameBranch", bankBranch);
            dataVariables.put(AppConstants.BANKING_SINCE, bankingSince);
        }
    }

    private Map<String, Object> setPayorSectionDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        String payorDiffFormProposer = AppConstants.NO;
        if (!isNeoOrAggregator && proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()){
        String payorName = StringUtils.EMPTY;
        String payorGender = StringUtils.EMPTY;
        String payorDOB = StringUtils.EMPTY;
        String relationShipWithProposer = StringUtils.EMPTY;
        String payorAddress = StringUtils.EMPTY;
        String payorAnnualIncome = StringUtils.EMPTY;
        String payorPan = StringUtils.EMPTY;
        String payorBank = StringUtils.EMPTY;
        String payorBankBranch = StringUtils.EMPTY;
        String payorAccountNumber = StringUtils.EMPTY;
        String payorBankingSince = StringUtils.EMPTY;
        BasicDetails payorDetails = null;
        List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
            payorDiffFormProposer = Utility.evaluateConditionalOperation(proposalDetails.getAdditionalFlags().isPayorDiffFromPropser(), AppConstants.YES, AppConstants.NO);

            if (StringUtils.equalsIgnoreCase(payorDiffFormProposer, AppConstants.YES) && !CollectionUtils.isEmpty(partyInfoList)
                    && partyInfoList.size() >= 3) {
                payorDetails = partyInfoList.get(2).getBasicDetails();

                payorName = Stream.of(payorDetails.getFirstName(), payorDetails.getMiddleName(), payorDetails.getLastName())
                        .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(AppConstants.WHITE_SPACE));
                payorGender = Utility.getGenderValue(payorDetails.getGender());
                payorDOB = Utility.dateFormatter(payorDetails.getDob());
                payorAnnualIncome = payorDetails.getAnnualIncome();
                payorAddress = String.join(" ", payorDetails.getAddress().get(0).getAddressDetails().getArea(),
                        payorDetails.getAddress().get(0).getAddressDetails().getState());
                payorPan = StringUtils.isEmpty(partyInfoList.get(2).getPersonalIdentification().getPanDetails().getPanNumber()) ? AppConstants.BLANK
                        : partyInfoList.get(2).getPersonalIdentification().getPanDetails().getPanNumber();
                relationShipWithProposer = payorDetails.getRelationshipWithProposer();
                payorBank = StringUtils.isEmpty(partyInfoList.get(2).getBankDetails().getBankName()) ? AppConstants.BLANK
                        : partyInfoList.get(2).getBankDetails().getBankName();
                payorBankBranch = StringUtils.isEmpty(partyInfoList.get(2).getBankDetails().getBankBranch()) ? AppConstants.BLANK
                        : partyInfoList.get(2).getBankDetails().getBankBranch();
                payorAccountNumber = partyInfoList.get(2).getBankDetails().getBankAccountNumber();
            }
            dataVariables.put("payorName", payorName);
            dataVariables.put("relationShipWithProposer", relationShipWithProposer);
            dataVariables.put("payoraddress", payorAddress);
            dataVariables.put("payorGender", payorGender);
            dataVariables.put("payorBankingSince", payorBankingSince);
            dataVariables.put("payorAnnualIncome", payorAnnualIncome);
            dataVariables.put("payorPan", payorPan);
            dataVariables.put("payorDOB", payorDOB);
            dataVariables.put("payorAccountNumber", payorAccountNumber);
            dataVariables.put("payorBankBranch", payorBank.concat(" ").concat(payorBankBranch));
            dataVariables.put("isPayorDiffFromProposer", proposalDetails.getAdditionalFlags().isPayorDiffFromPropser());
        }
        dataVariables.put("payorDiffFromProposer", payorDiffFormProposer);
        return dataVariables;
    }

    private Map<String, Object> setDataForGLIP(ProductDetails productDetails, Map<String, Object> dataVariables) {

        logger.info("Mapping Data for GLIP Product");
        /* FUL2-144685 START */
        String premiumPaymentTerm = "";
        String premiumType = StringUtils.EMPTY;
        /* FUL2-144685 END */

        ProductIllustrationResponse illustrationResponse =
                productDetails.getProductInfo().getProductIllustrationResponse();

        if (Objects.nonNull(illustrationResponse.getRequiredModalPremium()))
            dataVariables.put("singlePremiumPrice", String.format("%.0f",
                    roundOffValue(illustrationResponse.getRequiredModalPremium())));
        dataVariables.put("gstCess", String.format("%.0f", roundOffValue(illustrationResponse.getServiceTax())));
        dataVariables.put("premiumPaymentAmount", String.format("%.0f",
                roundOffValue(illustrationResponse.getAfyp())));
        dataVariables.put("premiumPaymentAmountWithGst", String.format("%.0f",
                roundOffValue(illustrationResponse.getTotalRequiredModalPremium())));
        dataVariables.put("dateFirstAnnuity", Utility.dateFormatter(illustrationResponse.getAnnuityStartDate()));
        dataVariables.put("annuityAmount", String.format("%.0f",
                roundOffValue(illustrationResponse.getGuaranteedIncomeAmt())));
        dataVariables.put("incomePayoutFrequency", illustrationResponse.getIncomePayoutFrequency());
        //FUL2-144685 start
        premiumPaymentTerm = productDetails.getProductInfo().getPremiumPaymentTerm();
        premiumType = productDetails.getProductInfo().getPremiumType() ==  null ? premiumType: productDetails.getProductInfo().getPremiumType();
        dataVariables.put("premiumPaymentTerm",(AppConstants.SINGLE_PAY.equalsIgnoreCase(premiumType) ? AppConstants.NA : premiumPaymentTerm));

        String modeOfPremiumPayment ="";
        modeOfPremiumPayment = productDetails.getProductInfo().getModeOfPayment();
        String productId = productDetails.getProductInfo().getProductId();
        if( (AppConstants.SWAGPP.equalsIgnoreCase(productId) || AppConstants.SGPP_ID.equalsIgnoreCase(productId) || AppConstants.SPP_ID.equalsIgnoreCase(productId))
        		&& AppConstants.SINGLE_PAY.equalsIgnoreCase(productDetails.getProductInfo().getPremiumType()) ){
        	modeOfPremiumPayment = AppConstants.SINGLE;
        }
        dataVariables.put("modeOfPremiumPayment", modeOfPremiumPayment);
        //FUL2-144685 end
        return dataVariables;
    }

    private double roundOffValue(String value) {

        double convertedValue = 0;
        if (!StringUtils.isEmpty(value)) {
            convertedValue = Math.round(Double.valueOf(value) * 100.00) / 100.00;
        }
        return convertedValue;
    }
    
	private String getRenewalPaymentMode(ProposalDetails proposalDetails) {
		String paymentRenewedBy = AppConstants.BLANK;
		if (proposalDetails != null && proposalDetails.getProductDetails() != null
				&& proposalDetails.getProductDetails().get(0).getProductInfo() != null) {
			String premiumPaymentTerm = proposalDetails.getProductDetails().get(0).getProductInfo().getPremiumPaymentTerm();
			String premiumType = proposalDetails.getProductDetails().get(0).getProductInfo().getPremiumType();
			if(premiumPaymentTerm.equalsIgnoreCase(AppConstants.ONE) || premiumType.equalsIgnoreCase(AppConstants.SINGLE_PAY)) {
				paymentRenewedBy = AppConstants.NA;
			} else {
				paymentRenewedBy = Optional.of(proposalDetails).map(o-> o.getBank()).map(o->o.getPaymentRenewedBy()).orElse(AppConstants.BLANK);
			}
			if(paymentRenewedBy.equalsIgnoreCase("directDebit")) {
				paymentRenewedBy = AppConstants.DIRECT_DEBIT;
			}
		}
		return paymentRenewedBy;
	}
}
