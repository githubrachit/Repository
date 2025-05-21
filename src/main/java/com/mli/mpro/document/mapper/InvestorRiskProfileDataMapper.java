package com.mli.mpro.document.mapper;

import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.sellerSignature.service.SellerSignatureService;
import com.mli.mpro.utils.Utility;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import java.util.TimeZone;

import com.mli.mpro.utils.UtilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;

import static com.mli.mpro.productRestriction.util.AppConstants.CHANNEL_AXIS;
import static com.mli.mpro.productRestriction.util.AppConstants.OSP;

@Service
public class InvestorRiskProfileDataMapper {
	
	@Autowired
	private UIConfigurationService uiConfigurationService;
	@Autowired
	SellerSignatureService sellerSignatureService;
	@Autowired
	private UtilityService utilityService;

	private static final Logger logger = LoggerFactory.getLogger(InvestorRiskProfileDataMapper.class);

	final String checkbox = "classpath:static/checkbox.png";

	/**
	 * Method to set the data in placeholders required for IRP document
	 *
	 * @param proposalDetails
	 * @return
	 * @throws UserHandledException
	 */
	public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {
		Context context = new Context();
		logger.info("Setting data for IRP document...");

		try {
			String year = "";
			ZonedDateTime now = ZonedDateTime.now(ZoneId.of(AppConstants.APP_TIMEZONE));

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
			String currentDate = now.format(dateFormatter);

			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
			String currentTime = now.format(timeFormatter);

			Map<String, Object> irpDocumentDataMap = new HashMap<>();
			String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();

			BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
			ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
			String firstName = proposerBasicDetails.getFirstName();
			String middleName = StringUtils.isEmpty(proposerBasicDetails.getMiddleName()) ? "" : proposerBasicDetails.getMiddleName();
			String lastName = proposerBasicDetails.getLastName();
			String fullName = String.join(AppConstants.WHITE_SPACE, firstName, middleName, lastName);
			//FUL2-78685
			String irpq1 = getIrpq1(proposalDetails);
			String irpq2 = getIrpq2(proposalDetails);
			String irpq3 = getIrpq3(proposalDetails);
			String irpq4 = getIrpq4(proposalDetails);
			/** set fund details */
			setFundDetails(productInfo,irpDocumentDataMap);

			String riskSelection1 = irpq1;
			String riskSelection2 = irpq2;
			String riskSelection3 = irpq3;

			String riskSelection4 = getRiskSelection4(irpq4);

			int riskSelection1Int = 0;
			int riskSelection2Int = 0;
			int riskSelection3Int = 0;
			if (StringUtils.isNumeric(riskSelection1)) {
				riskSelection1Int = Integer.parseInt(riskSelection1);
			}
			if (StringUtils.isNumeric(riskSelection2)) {
				riskSelection2Int = Integer.parseInt(riskSelection2);
			}
			if (StringUtils.isNumeric(riskSelection3)) {
				riskSelection3Int = Integer.parseInt(riskSelection3);
			}

			int totalRisk = riskSelection1Int + riskSelection2Int + riskSelection3Int;


			logger.info("total risk : {} " , totalRisk);
			String riskProfile = getRiskProfile(totalRisk);

			Boolean isSTPAllocated = productInfo.getFundSelection().isSTPAllocated();
			Boolean isDFAAllocated = productInfo.getFundSelection().isDFAAllocated();
			Boolean isLifeCycleStrategy = productInfo.getFundSelection().isLifecyclePortfolioStrategy();
			Boolean isTriggerBasedStrategy = productInfo.getFundSelection().isTriggerBasedPortfolioStrategy();
			Boolean isNEOOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
					|| proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);

			year = getYear(requestSource);
			String sellerSignature = "";
			// Ful2-97686 set seller signature
			sellerSignature = sellerSignatureService.getSellerSignature(proposalDetails);
			String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
			//FUL2-46310
			policyNumber = Utility.getSecondaryPolicyNumber(proposalDetails, policyNumber);
			policyNumber = Utility.getPrimaryPolicyNumber(proposalDetails, policyNumber);

			/** Setting Values in Data map **/
			irpDocumentDataMap.put("checkBox", checkbox);
			irpDocumentDataMap.put("name", fullName);
			irpDocumentDataMap.put("date", currentDate);
			irpDocumentDataMap.put("time", currentTime);
			irpDocumentDataMap.put("year", year);
			irpDocumentDataMap.put("policyNumber", getValue(policyNumber, StringUtils.isBlank(policyNumber), AppConstants.NA));
			irpDocumentDataMap.put("nameofProduct",
					getValue(proposalDetails.getProductDetails().get(0).getProductInfo().getProductName(), StringUtils.isBlank(proposalDetails.getProductDetails().get(0).getProductInfo().getProductName()), AppConstants.NA));
			irpDocumentDataMap.put("riskSelection1", getValue(riskSelection1, StringUtils.isBlank(riskSelection1), StringUtils.EMPTY));
			irpDocumentDataMap.put("riskSelection2", getValue(riskSelection2, StringUtils.isBlank(riskSelection2), StringUtils.EMPTY));
			irpDocumentDataMap.put("riskSelection3", getValue(riskSelection3, StringUtils.isBlank(riskSelection3), StringUtils.EMPTY));
			irpDocumentDataMap.put("riskSelection4", getValue(riskSelection4, StringUtils.isBlank(riskSelection4), StringUtils.EMPTY));
			irpDocumentDataMap.put("totalRisk", totalRisk);
			irpDocumentDataMap.put("riskProfile", getValue(riskProfile, StringUtils.isBlank(riskProfile), AppConstants.NA));
			irpDocumentDataMap.put("stp", (isSTPAllocated != null) ? isSTPAllocated : StringUtils.EMPTY);
			irpDocumentDataMap.put("dfa", (isDFAAllocated != null) ? isDFAAllocated : StringUtils.EMPTY);
			irpDocumentDataMap.put("lcs", (isLifeCycleStrategy != null) ? isLifeCycleStrategy : StringUtils.EMPTY);
			irpDocumentDataMap.put("tbs", (isTriggerBasedStrategy != null) ? isTriggerBasedStrategy : StringUtils.EMPTY);
			irpDocumentDataMap.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
			irpDocumentDataMap.put("sellerSignature",sellerSignature);
			irpDocumentDataMap.put("otpConfirmationDate",getOTPConfirmationDate(proposalDetails.getPosvDetails(),proposalDetails));
			irpDocumentDataMap.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
			if(Objects.nonNull(proposalDetails.getAdditionalFlags())) {
				irpDocumentDataMap.put("journeyType", proposalDetails.getAdditionalFlags().getJourneyType());
			}
			// Adding feature flag for Midcap Momentum Fund
			//FUL2-186954_Logic to handle fund switchOff or on for IRP Document
			// Adding feature flag for Midcap Momentum Fund
			if(AppConstants.STAR_PRODUCT_ID.equalsIgnoreCase(productInfo.getProductId()) && !isNEOOrAggregator){
				utilityService.setFeatureFlagNIFTYFundForSTART(irpDocumentDataMap,String.valueOf(proposalDetails.getTransactionId()));
			} else if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
				Utility.niftyFundEnableOrNot(uiConfigurationService, null, null, irpDocumentDataMap, "");
			} else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NIFTYFUND_FEATURE_KEY))) {
				irpDocumentDataMap.put("isFeatureFlagNIFTYFund", false);
			}
			// Channel specific check for Nifty Fund
			if (OSP.equals(productInfo.getProductId())
					&& !(proposalDetails.getChannelDetails() != null && CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
						&& proposalDetails.getAdditionalFlags() != null && AppConstants.J3.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getJourneyType()))) {
				irpDocumentDataMap.put("isFeatureFlagNIFTYFund", false);
			}
			context.setVariables(irpDocumentDataMap);

		} catch (Exception ex) {
	    logger.error("Data addition failed for IRP Document:", ex);
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("Data addition failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("IRP data mapping complete...");
		return context;
	}

	private String getValue(String riskSelection1, boolean blank, String empty) {
		return blank ? empty : riskSelection1;
	}

	private String getYear(String requestSource) {
		String year;
		if (isIrpqMatched(requestSource, AppConstants.AXIS_STRING, AppConstants.CSG_STRING)) {
			year = AppConstants.IRP_YEAR;
		} else {
			year = "February2025";
		}
		return year;
	}

	private String getRiskProfile(int totalRisk) {
		return getValue((totalRisk >= 7 && totalRisk < 12) ? "Balanced"
		: (totalRisk >= 12 && totalRisk < 14) ? "Aggressive" : (totalRisk >= 14) ? "Very Aggressive" : StringUtils.EMPTY, totalRisk >= 3 && totalRisk < 7, "Conservative");
	}

	private String getRiskSelection4(String irpq4) {
		return isIrpqMatched(irpq4, "e", "1") ? "1"
				: ((isIrpqMatched(irpq4, "d", "2")) ? "2"
				: ((isIrpqMatched(irpq4, "c", "3")) ? "3"
				: ((isIrpqMatched(irpq4, "b", "4")) ? "4"
				: ((isIrpqMatched(irpq4, "a", "5")) ? "5" : "0"))));
	}

	private boolean isIrpqMatched(String irpq, String charValue, String numericValue) {
		return StringUtils.equalsIgnoreCase(irpq, charValue) || StringUtils.equalsIgnoreCase(irpq, numericValue);
	}
	//FUL2-78685 CSG nullchecks in IRP
	private String getIrpq4(ProposalDetails proposalDetails) {
		if(!StringUtils.isEmpty(proposalDetails.getIrp().getIRPQ4())){
			return proposalDetails.getIrp().getIRPQ4();
		} else if(null!=proposalDetails.getCsgDetails()){
			return (Optional.of(proposalDetails).map(ProposalDetails::getCsgDetails).map(CSGDetails::getIrpQ4Option).orElse(StringUtils.EMPTY));
		}
		return StringUtils.EMPTY;
	}
	//FUL2-78685 CSG nullchecks in IRP
	private String getIrpq3(ProposalDetails proposalDetails) {
		if(!StringUtils.isEmpty(proposalDetails.getIrp().getIRPQ3())){
			return proposalDetails.getIrp().getIRPQ3();
		} else if(null!=proposalDetails.getCsgDetails()){
			return (Optional.of(proposalDetails).map(ProposalDetails::getCsgDetails).map(CSGDetails::getIrpQ3Option).orElse(StringUtils.EMPTY));
		}
		return StringUtils.EMPTY;
	}
	//FUL2-78685 CSG nullchecks in IRP
	private String getIrpq2(ProposalDetails proposalDetails) {
		if(!StringUtils.isEmpty(proposalDetails.getIrp().getIRPQ2())){
			return proposalDetails.getIrp().getIRPQ2();
		} else if(null!=proposalDetails.getCsgDetails()){
			return (Optional.of(proposalDetails).map(ProposalDetails::getCsgDetails).map(CSGDetails::getIrpQ2Option).orElse(StringUtils.EMPTY));
		}
		return StringUtils.EMPTY;
	}
	//FUL2-78685 CSG nullchecks in IRP
	private String getIrpq1(ProposalDetails proposalDetails) {
		if(!StringUtils.isEmpty(proposalDetails.getIrp().getIRPQ1())){
			return proposalDetails.getIrp().getIRPQ1();
		} else if(null!=proposalDetails.getCsgDetails()){
			return (Optional.of(proposalDetails).map(ProposalDetails::getCsgDetails).map(CSGDetails::getIrpQ1Option).orElse(StringUtils.EMPTY));
		}
		return StringUtils.EMPTY;
	}

 private String getOTPConfirmationDate(PosvDetails posvDetails,ProposalDetails proposalDetails){
	 Date otpConfirmation = Objects.nonNull(posvDetails) && Objects.nonNull(posvDetails.getPosvStatus())
			 ? posvDetails.getPosvStatus().getGeneratedOTPDate() : null;
	 if((Objects.nonNull(proposalDetails.getAdditionalFlags()) && Objects.nonNull(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
	     && Objects.nonNull(proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF())
	 && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
		 && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
			 //FUL2-170700 BrmsBroker Integration
			 ||Utility.brmsBrokerEligibility(proposalDetails)){
		  otpConfirmation = proposalDetails.getAdditionalFlags().getCustomerSignDateForCDF();
	 }

	 SimpleDateFormat format = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
	 format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
	 return otpConfirmation != null ? format.format(otpConfirmation) : StringUtils.EMPTY;
 }

	private void setFundDetails(ProductInfo productInfo, Map<String, Object> irpDocumentDataMap) {
		List<FundInfo> fundInfoList = productInfo.getFundSelection().getFundInfo();
		Map<String, Integer> fundValueMap = new HashMap<>();

		for (FundInfo fundInfo : fundInfoList) {
			if (StringUtils.equals(AppConstants.FYPP_PRODUCT_ID, productInfo.getProductId()) &&
					StringUtils.equalsIgnoreCase(fundInfo.getFundName(), AppConstants.PENSION_PRESERVER_OPTION)) {
				fundValueMap.put("pensionPreserverFund", fundInfo.getFundValue());
			} else if (StringUtils.equals(AppConstants.FYPP_PRODUCT_ID, productInfo.getProductId()) &&
					StringUtils.equalsIgnoreCase(fundInfo.getFundName(), AppConstants.PENSION_MAXIMISER_OPTION)) {
				fundValueMap.put("pensionMaximiserFund", fundInfo.getFundValue());
			} else if (!AppConstants.PENSION_PRESERVER_OPTION.equalsIgnoreCase(fundInfo.getFundName()) &&
					!AppConstants.PENSION_MAXIMISER_OPTION.equalsIgnoreCase(fundInfo.getFundName())) {
				fundValueMap.put(fundInfo.getFundName(), fundInfo.getFundValue());
			}
			irpDocumentDataMap.put(AppConstants.HIGH_GROWTH_FUND, fundValueMap.getOrDefault(AppConstants.HIGH_GROWTH_FUND, 0));
			irpDocumentDataMap.put(AppConstants.GROWTH_SUPER_FUND, fundValueMap.getOrDefault(AppConstants.GROWTH_SUPER_FUND, 0));
			irpDocumentDataMap.put(AppConstants.GROWTH_FUND, fundValueMap.getOrDefault(AppConstants.GROWTH_FUND, 0));
			irpDocumentDataMap.put(AppConstants.BALANCED_FUND, fundValueMap.getOrDefault(AppConstants.BALANCED_FUND, 0));
			irpDocumentDataMap.put(AppConstants.CONSERVATIVE_FUND, fundValueMap.getOrDefault(AppConstants.CONSERVATIVE_FUND, 0));
			irpDocumentDataMap.put(AppConstants.SECURE_FUND, fundValueMap.getOrDefault(AppConstants.SECURE_FUND, 0));
			irpDocumentDataMap.put(AppConstants.DIVERSIFIED_EQUITY_FUND, fundValueMap.getOrDefault(AppConstants.DIVERSIFIED_EQUITY_FUND, 0));
			irpDocumentDataMap.put(AppConstants.DYNAMIC_BOND_FUND, fundValueMap.getOrDefault(AppConstants.DYNAMIC_BOND_FUND, 0));
			irpDocumentDataMap.put(AppConstants.MONEY_MARKET_FUND_II, fundValueMap.getOrDefault(AppConstants.MONEY_MARKET_FUND_II, 0));
			irpDocumentDataMap.put(AppConstants.PENSION_PRESERVER_FUND, fundValueMap.getOrDefault(AppConstants.PENSION_PRESERVER_FUND, 0));
			irpDocumentDataMap.put(AppConstants.PENSION_MAXIMISER_FUND, fundValueMap.getOrDefault(AppConstants.PENSION_MAXIMISER_FUND, 0));
			irpDocumentDataMap.put(AppConstants.SUSTAINABLE_EQUITY_FUND, fundValueMap.getOrDefault(AppConstants.SUSTAINABLE_EQUITY_FUND, 0));
			irpDocumentDataMap.put(AppConstants.PURE_GROWTH_FUND, fundValueMap.getOrDefault(AppConstants.PURE_GROWTH_FUND, 0));
			irpDocumentDataMap.put(AppConstants.NIFTY_SMALLCAP_QUALITY_INDEX_FUND, fundValueMap.getOrDefault(AppConstants.NIFTY_SMALLCAP_QUALITY_INDEX_FUND, 0));
			irpDocumentDataMap.put(AppConstants.MIDCAP_MOMENTUM_FUND, fundValueMap.getOrDefault(AppConstants.MIDCAP_MOMENTUM_FUND, 0));
			irpDocumentDataMap.put(AppConstants.NIFTY_ALPHA_FUND_KEY, fundValueMap.getOrDefault(AppConstants.NIFTY_ALPHA_FUND_KEY, 0));
			irpDocumentDataMap.put(AppConstants.NIFTY_MOMENTUM_FUND, fundValueMap.getOrDefault(AppConstants.NIFTY_MOMENTUM_FUND, 0));
			irpDocumentDataMap.put(AppConstants.NIFTY_MOMENTUM_QUALITY_FUND, fundValueMap.getOrDefault(AppConstants.NIFTY_MOMENTUM_QUALITY_FUND, 0));
			irpDocumentDataMap.put(AppConstants.NIFTY_SUSTAINABLE_FUND, fundValueMap.getOrDefault(AppConstants.NIFTY_SUSTAINABLE_FUND, 0));
			irpDocumentDataMap.put(AppConstants.SMART_INNOVATION_FUND, fundValueMap.getOrDefault(AppConstants.SMART_INNOVATION_FUND, 0));
		}
	}

}
