package com.mli.mpro.document.mapper;

import java.util.*;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.proposal.models.*;
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

@Service
public class AnnuityDetailsMapper {
    private static final Logger logger = LoggerFactory.getLogger(AnnuityDetailsMapper.class);

    /**
     * Mapping data from Annuity to Context DataMap
     * 
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */


	String title = "";
	String firstName = "";
	String middleName = "";
	String lastName = "";
	String singlePremium = "";
	String modeOfPayment = "";
	String gender = "";
	String formattedDob = "";
	String singleLifeWithoutDeathBenifit = "";
	String singleLifeROP = "";
	String jointLifeWithoutDeathBenifit = "";
	String jointLifeROP = "";

    public Context setDataOfAnnuityDocument(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START Annuity Data population");
	Map<String, Object> dataVariables = new HashMap<>();
	try {
	    //Default empty values
		String city="";
	    String panNumber = "";
	    String bankMicrCode = "";
	    String bankAccountNumber = "";
	    String bankIfscCode = "";
	    String bankAccountHoldersName = "";
	    String bankTypeOfAccount = "";
	    String bankName = "";
	    String form60 = "";
	    String otpConfirmationDate = "";
	    String agentName = "";
	    String agentCode = "";
	    String agentKnowsProposerSince = "";
	    String agentKnowsProposerUnitType = "";
		String channel="";
	    HashMap<String, Object> annuityOptionMap = new HashMap<>();
	    HashMap<String, Object> annuityBankMap = new HashMap<>();
	    HashMap<String, Object> agentDataMap = new HashMap<>();

	    List<ProductDetails> productDetailsList = proposalDetails.getProductDetails();
	    List<BankDetails> proposerBankDetailsList = proposalDetails.getBank().getBankDetails();
		channel=proposalDetails.getChannelDetails().getChannel();

	    logger.info("Fetching Data from ProposalDetails for Annuity Document");

	    // Personal Details
		if(channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS)){
			setPersonalDetailsForAxis(productDetailsList);

		}else {
			setPersonalDetails(productDetailsList);
		}
		List<PartyInformation> proposerPartyInfoList = proposalDetails.getPartyInformation();
	    if (!CollectionUtils.isEmpty(proposerPartyInfoList)) {
		boolean panFlag = StringUtils.isNotBlank(proposerPartyInfoList.get(0).getPersonalIdentification().getPanDetails().getPanNumber());
		city = proposerPartyInfoList.get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity();
		panNumber = proposerPartyInfoList.get(0).getPersonalIdentification().getPanDetails().getPanNumber();
		form60 = panFlag ? AppConstants.NO : AppConstants.YES;
	    }

	    // Bank Details
		 if (!CollectionUtils.isEmpty(proposerBankDetailsList)) {
		bankMicrCode = proposerBankDetailsList.get(0).getMicr();
		bankAccountNumber = proposerBankDetailsList.get(0).getBankAccountNumber();
		bankIfscCode = proposerBankDetailsList.get(0).getIfsc();
		bankAccountHoldersName = proposerBankDetailsList.get(0).getAccountHolderName();
		bankTypeOfAccount = proposerBankDetailsList.get(0).getTypeOfAccount();
		bankName = proposerBankDetailsList.get(0).getBankName();
	    }
		 String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
		otpConfirmationDate = setOtpConfirmationDate(proposalDetails, otpConfirmationDate);
		agentName = proposalDetails.getSourcingDetails().getAgentName();
	    agentCode = proposalDetails.getSourcingDetails().getAgentCode();
	    Long agentPhone = proposalDetails.getSourcingDetails().getAgentMobileNumber();
	    agentKnowsProposerSince = proposalDetails.getAdditionalFlags().getAgentKnowsProposerSince();
	    agentKnowsProposerUnitType = proposalDetails.getAdditionalFlags().getAgentKnowsProposerUnitType();

	    logger.info("Setting Data in DataMap for Annuity Document");
	    // Bank Details
		setAnnuityBankMap(bankMicrCode, bankAccountNumber, bankIfscCode, bankAccountHoldersName, bankTypeOfAccount, bankName, annuityBankMap);

		// Annuity Options
		setAnnuityOption(singleLifeWithoutDeathBenifit, singleLifeROP, jointLifeWithoutDeathBenifit, jointLifeROP, annuityOptionMap);

		// Agent Data
		setAgentData(otpConfirmationDate, agentName, agentCode, agentKnowsProposerSince, agentKnowsProposerUnitType, agentDataMap, agentPhone);

		// Putting Data in DataMap for Context
		setNameInfo(dataVariables, title, firstName, middleName, lastName);
		setPersonalData(dataVariables, gender, formattedDob, panNumber, annuityBankMap,city);
		setBankingData(dataVariables, singlePremium, modeOfPayment, form60, annuityOptionMap, agentDataMap);

		dataVariables.put("isTelesalesRequest", requestSource.equalsIgnoreCase(AppConstants.AXIS_TELESALES_REQUEST));

	} catch (Exception ex) {
	    logger.error("Data addition failed for Annuity Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context annuityDetailsCxt = new Context();
	annuityDetailsCxt.setVariables(dataVariables);
	logger.info("END Annuity Data population");
	return annuityDetailsCxt;
    }
	public void setPersonalDetails(List<ProductDetails> productDetailsList) {
		// Personal Details
		if (!CollectionUtils.isEmpty(productDetailsList)) {
			String annuityName = productDetailsList.get(0).getProductInfo().getSecondAnnuitantName();
			String annuityOption = productDetailsList.get(0).getProductInfo().getAnnuityOption();
			singlePremium = productDetailsList.get(0).getProductInfo().getPremiumCommitment();
			modeOfPayment = productDetailsList.get(0).getProductInfo().getModeOfPayment();
			// 8464-Annuity Form changes
			String deathBenefitForAnnuity = productDetailsList.get(0).getProductInfo().getDeathBenefitForAnnuity();
			logger.info("Annuity option is {} selected by user and death benefit information is {} ",annuityOption,deathBenefitForAnnuity );

			if (AppConstants.SINGLE_LIFE_ANNUITY_OPTION.equalsIgnoreCase(StringUtils.trim(annuityOption))) {
				title = AppConstants.NA;
				firstName = AppConstants.NA;
				middleName = AppConstants.NA;
				lastName = AppConstants.NA;
				gender = AppConstants.NA;
				formattedDob = AppConstants.NA;
				// 8464 Annuity Form
				if (deathBenefitForAnnuity.equalsIgnoreCase("YES")) {
					singleLifeROP = AppConstants.YES;
					singleLifeWithoutDeathBenifit = AppConstants.NO;
				} else {
					singleLifeWithoutDeathBenifit = AppConstants.YES;
					singleLifeROP = AppConstants.NO;
				}
				jointLifeWithoutDeathBenifit = AppConstants.NO;
				jointLifeROP = AppConstants.NO;

			} else if (AppConstants.JOINT_LIFE_ANNUITY_OPTION.equalsIgnoreCase(StringUtils.trim(annuityOption)))  {
				title = Utility.getTitle(productDetailsList.get(0).getProductInfo().getSecondAnnuitantSex());
				gender = Utility.getGender(productDetailsList.get(0).getProductInfo().getSecondAnnuitantSex());
				formattedDob = Utility
						.stringAnnuityDateFormatter(productDetailsList.get(0).getProductInfo().getSecondAnnuitantDob());

				// 8464 Annuity Form
				if (deathBenefitForAnnuity.equalsIgnoreCase("YES")) {
					jointLifeROP = AppConstants.YES;
					jointLifeWithoutDeathBenifit = AppConstants.NO;
				} else {
					jointLifeWithoutDeathBenifit = AppConstants.YES;
					jointLifeROP = AppConstants.NO;
				}
				singleLifeROP = AppConstants.NO;
				singleLifeWithoutDeathBenifit = AppConstants.NO;

				setAnnuityName(annuityName);

			}

		}

	}

	public void setPersonalDetailsForAxis(List<ProductDetails> productDetailsList) {

		if (!CollectionUtils.isEmpty(productDetailsList)) {
			String annuityName = productDetailsList.get(0).getProductInfo().getSecondAnnuitantName();
			String annuityOption = productDetailsList.get(0).getProductInfo().getAnnuityOption();
			singlePremium = productDetailsList.get(0).getProductInfo().getPremiumCommitment();
			modeOfPayment = productDetailsList.get(0).getProductInfo().getModeOfPayment();
			if (AppConstants.SINGLE_LIFE_ANNUITY_OPTION.equalsIgnoreCase(StringUtils.trim(annuityOption))) {
				title = AppConstants.NA;
				firstName = AppConstants.NA;
				middleName = AppConstants.NA;
				lastName = AppConstants.NA;
				gender = AppConstants.NA;
				formattedDob = AppConstants.NA;
				singleLifeWithoutDeathBenifit = AppConstants.YES;
				singleLifeROP = AppConstants.YES;
				jointLifeWithoutDeathBenifit = AppConstants.YES;
				jointLifeROP = AppConstants.YES;

			} else {
				title = Utility.getTitle(productDetailsList.get(0).getProductInfo().getSecondAnnuitantSex());
				gender = Utility.getGender(productDetailsList.get(0).getProductInfo().getSecondAnnuitantSex());
				formattedDob = Utility.stringAnnuityDateFormatter(productDetailsList.get(0).getProductInfo().getSecondAnnuitantDob());
				singleLifeWithoutDeathBenifit = AppConstants.NO;
				singleLifeROP = AppConstants.NO;
				jointLifeWithoutDeathBenifit = AppConstants.NO;
				jointLifeROP = AppConstants.NO;
				setAnnuityName(annuityName);

			}

		}
	}
	public void setAnnuityName(String annuityName) {
		// Split annuityName
		if (StringUtils.isNotBlank(annuityName)) {
			String[] splitedAnnuityName = annuityName.split("\\s+");
			firstName = getAnnuityValue(splitedAnnuityName, 0);
			middleName = getAnnuityValue(splitedAnnuityName, 1);
			lastName = getAnnuityValue(splitedAnnuityName, 2);
		}
	}


	private String setOtpConfirmationDate(ProposalDetails proposalDetails, String otpConfirmationDate) {
		try {
            otpConfirmationDate = Utility.dateFormatter(proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate());
			//FUL2-146253 Capital Small Finance Bank
			if(!((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
					&& Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
					||Utility.brmsBrokerEligibility(proposalDetails))) {
				otpConfirmationDate = Utility.dateFormatter(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF());
			}
        }catch(Exception ex) {
		    logger.error("logging error for otp date:",ex);
		}
		return otpConfirmationDate;
	}

	private void setAnnuityOption(String singleLifeWithoutDeathBenifit, String singleLifeROP, String jointLifeWithoutDeathBenifit, String jointLifeROP, HashMap<String, Object> annuityOptionMap) {
		annuityOptionMap.put("singleLifeWithoutDeathBenifit", singleLifeWithoutDeathBenifit);
		annuityOptionMap.put("singleLifeROP", singleLifeROP);
		annuityOptionMap.put("jointLifeWithoutDeathBenifit", jointLifeWithoutDeathBenifit);
		annuityOptionMap.put("jointLifeROP", jointLifeROP);
	}

	private void setBankingData(Map<String, Object> dataVariables, String singlePremium, String modeOfPayment, String form60, HashMap<String, Object> annuityOptionMap, HashMap<String, Object> agentDataMap) {
		dataVariables.put("singlePremium", singlePremium);
		dataVariables.put("annuityOption", annuityOptionMap);
		dataVariables.put("modeOfPayment", modeOfPayment);
		dataVariables.put("agentData", agentDataMap);
		dataVariables.put("form60", form60);
	}

	private void setPersonalData(Map<String, Object> dataVariables, String gender, String formattedDob, String panNumber, HashMap<String, Object> annuityBankMap, String city) {
		dataVariables.put("gender", gender);
		dataVariables.put("dob", StringUtils.isBlank(formattedDob) ? AppConstants.NA : formattedDob);
		dataVariables.put("panNumber", panNumber);
		dataVariables.put("city", city);
		dataVariables.put("annuityBank", annuityBankMap);
	}

	private void setNameInfo(Map<String, Object> dataVariables, String title, String firstName, String middleName, String lastName) {
		dataVariables.put("title", title);
		dataVariables.put("firstName", StringUtils.isBlank(firstName) ? AppConstants.NA : firstName);
		dataVariables.put("middleName", StringUtils.isBlank(middleName) ? AppConstants.NA : middleName);
		dataVariables.put("lastName", StringUtils.isBlank(lastName) ? AppConstants.NA : lastName);
	}

	private void setAgentData(String otpConfirmationDate, String agentName, String agentCode, String agentKnowsProposerSince, String agentKnowsProposerUnitType, HashMap<String, Object> agentDataMap, Long agentPhone) {
		agentDataMap.put("otpConfirmationDate", otpConfirmationDate);
		agentDataMap.put("agentName", agentName);
		agentDataMap.put("agentCode", agentCode);
		agentDataMap.put("agentPhone", agentPhone);
		agentDataMap.put("agentKnowsProposerUnitType", agentKnowsProposerUnitType);
		agentDataMap.put("agentKnowsProposerSince", agentKnowsProposerSince);
	}

	private void setAnnuityBankMap(String bankMicrCode, String bankAccountNumber, String bankIfscCode, String bankAccountHoldersName, String bankTypeOfAccount, String bankName, HashMap<String, Object> annuityBankMap) {
		annuityBankMap.put("bankMicrCode", bankMicrCode);
		annuityBankMap.put("bankAccountNumber", bankAccountNumber);
		annuityBankMap.put("bankIfscCode", bankIfscCode);
		annuityBankMap.put("bankAccountHoldersName", bankAccountHoldersName);
		annuityBankMap.put("bankTypeOfAccount", bankTypeOfAccount);
		annuityBankMap.put("bankName", bankName);
	}

	private String getAnnuityValue(String[] splitedAnnuityName, int i) {
		return splitedAnnuityName.length > i ? splitedAnnuityName[i] : AppConstants.BLANK;
	}


}
