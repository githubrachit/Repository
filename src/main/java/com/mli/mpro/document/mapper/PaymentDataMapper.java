package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.location.services.impl.DocsAppServiceImpl;
import com.mli.mpro.neo.models.attachment.Payload;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Class to map request data for Payment Receipt generation
 * @author akshom4375
 */
@Service
public class PaymentDataMapper {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDataMapper.class);

    @Autowired
    private DocsAppServiceImpl docsAppServiceImpl;
	@Value("${swp.uin}")
	private String swpUin;

	DateTimeFormatter df = DateTimeFormatter.ofPattern(AppConstants.DD_MM_YYYY_SLASH);
    /**
     * Mapping data from Payment to Context DataMap
     * 
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfPaymentDocument(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START Payment Receipt Data population for transactionId {}", proposalDetails.getTransactionId());
	Map<String, Object> dataVariables = new HashMap<>();
	boolean isNeoOrAggregator = false;
	try {
		//NEORW-390: This will check that incoming request is from NEO or Aggregator
		isNeoOrAggregator = checkNeoChannel(proposalDetails);

		List<Receipt> paymentReceipts = Objects.nonNull(proposalDetails.getPaymentDetails()) ? proposalDetails.getPaymentDetails().getReceipt()
				: Collections.emptyList();
		List<PartyInformation> partyInfoList = Objects.nonNull(proposalDetails.getPartyInformation()) ? proposalDetails.getPartyInformation()
				: Collections.emptyList();
		List<ProductDetails> productDetailsList = Objects.nonNull(proposalDetails.getProductDetails()) ? proposalDetails.getProductDetails()
				: Collections.emptyList();

		String amount = "";
		String firstName = "";
		String middleName="";
		String lastName = "";
		String planName = "";
		String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
		String  refNo = proposalDetails.getApplicationDetails().getPolicyNumber();
		//FUL2-46310 
		policyNumber = Utility.getSecondaryPolicyNumber(proposalDetails, policyNumber);
		policyNumber = Utility.getPrimaryPolicyNumber(proposalDetails, policyNumber);
		String title = "", docGenerationDate = "", paymentDate = "", paymentMethod = "";
		//NEORW-390: Added Policy Date, Lead Id and Payment Mode fields for Second Payment Generation
		String policyDate = DateTimeUtils.format(proposalDetails.getApplicationDetails().getCreatedTime(), "dd/MM/yyyy");
		String leadId = "";
		String paymentMode = "";

		if (isNeoAndAggAndProductExist(isNeoOrAggregator, productDetailsList)) {
			policyDate = getPolicyDate(proposalDetails);
			leadId = Utility.nullSafe(proposalDetails.getLeadId());
			paymentMode = !paymentReceipts.isEmpty() ? paymentReceipts.get(0).getModeOfPayment() : AppConstants.BLANK;
		}
		setUINNumber(dataVariables);
		amount= setPremiumAmount(paymentReceipts,proposalDetails,productDetailsList);
		planName = setPlanName(proposalDetails);
		/*FUL2-11549 Payment acknowledgement for all channels : Added the below 4 newly required fields*/
		title = setTitle(proposalDetails);
		docGenerationDate = LocalDateTime.now(ZoneId.of(AppConstants.APP_TIMEZONE)).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		//FUL2-11549 Payment acknowledgement for all channels
		// Fetching receipt data for Secondary policy using getProposal api from proposal service - To fetch date for the salesstory secondary policy.
		proposalDetails = docsAppServiceImpl.setSecondPolicyReceipt(proposalDetails);
		paymentMethod = setPaymentMethod(paymentReceipts);

		if(AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
				&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentReceipts.get(0).getPremiumMode()))
		{
			paymentMethod = AppConstants.DIRECT_DEBIT_IVR;
		}


		paymentDate = setPaymentDate(proposalDetails);

		if(Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getPaymentDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
				&& !proposalDetails.getPaymentDetails().getReceipt().isEmpty()
				&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt().get(0))
				&& Objects.nonNull(proposalDetails.getChannelDetails())
				&& Objects.nonNull(proposalDetails.getChannelDetails().getChannel())
				&& Utility.isChannelNeoOrAggregator(proposalDetails)) {
			paymentDate = proposalDetails.getPaymentDetails().getReceipt().get(0).getPaymentDate();
			paymentMethod = proposalDetails.getPaymentDetails().getReceipt().get(0).getModeOfPayment();
		}

	    if (!CollectionUtils.isEmpty(partyInfoList)) {
				if(isNeoAggAndForm2(proposalDetails, isNeoOrAggregator)){
					firstName = partyInfoList.get(1).getBasicDetails().getFirstName();
					middleName = Utility.middleNameSpacing(partyInfoList.get(1).getBasicDetails().getMiddleName());
					lastName = partyInfoList.get(1).getBasicDetails().getLastName();
				} else {
					firstName = partyInfoList.get(0).getBasicDetails().getFirstName();
					middleName= Utility.middleNameSpacing(partyInfoList.get(0).getBasicDetails().getMiddleName());
					lastName = partyInfoList.get(0).getBasicDetails().getLastName();
				}
	    }

	    dataVariables.put("amount", amount);
	    dataVariables.put("refNo", refNo);
	    dataVariables.put("policyNumber", policyNumber);
	    dataVariables.put("planName", planName);
	    dataVariables.put("firstName", firstName);
		  dataVariables.put("middleName", middleName);
	    dataVariables.put("lastName", lastName);
		/*FUL2-11549 Payment acknowledgement for all channels*/
		dataVariables.put("title", title);
		dataVariables.put("docGenerationDate", docGenerationDate);
		dataVariables.put("paymentDate", paymentDate);
		dataVariables.put("paymentMethod", paymentMethod);
	    //NEORW-390: Added Policy Date, Lead Id and Payment Mode fields for Second Payment Generation
		dataVariables.put("policyDate", policyDate);
		dataVariables.put("leadId", leadId);
		dataVariables.put("paymentMode", paymentMode);
		dataVariables.put("equoteNumber", Utility.nullSafe(proposalDetails.getEquoteNumber()));
		dataVariables.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
		dataVariables.put("isCGSProduct", Utility.isCapitalGuaranteeSolutionProduct(proposalDetails));
		dataVariables.put("gstWaiver",proposalDetails.getAdditionalFlags().getGstWaiverRequired());
	} catch (Exception ex) {
	    logger.error("Data addition failed for Payment Receipt Document: ",  ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context paymentDetailsCxt = new Context();
	paymentDetailsCxt.setVariables(dataVariables);
	logger.info("END Payment Receipt Data population for transactionId {}", proposalDetails.getTransactionId());
	return paymentDetailsCxt;
    }

	private String getPolicyDate(ProposalDetails proposalDetails) {
		String policyDate;
		if (isSWPProduct(proposalDetails)) {
			policyDate = getPolicyDate(proposalDetails, true);
		} else {
			policyDate = getPolicyDate(proposalDetails, false);
		}
		return policyDate;
	}

	private static boolean isNeoAndAggAndProductExist(boolean isNeoOrAggregator, List<ProductDetails> productDetailsList) {
		return isNeoOrAggregator && !productDetailsList.isEmpty() && Objects.nonNull(productDetailsList.get(0).getProductInfo());
	}

	private static boolean isNeoAggAndForm2(ProposalDetails proposalDetails, boolean isNeoOrAggregator) {
		return isNeoOrAggregator && Utility.isApplicationIsForm2(proposalDetails);
	}

	private void setUINNumber(Map<String, Object> dataVariables) {
			dataVariables.put(AppConstants.SWP_UIN, swpUin);
	}

	private boolean checkNeoChannel(ProposalDetails proposalDetails){
		if (Objects.nonNull(proposalDetails.getChannelDetails()) &&
				(AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) ||
						AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()))) {
			return true;
		}
		return false;
	}
	//FUL2-11549 Payment acknowledgement for all channels
	// we have updated the amount as per new requirement as in salesstory - separate amount should be display for Payment Acknowledgement.
	private String setPremiumAmount(List<Receipt> paymentReceipts, ProposalDetails proposalDetails,
			List<ProductDetails> productDetailsList) {
		String amount = "";

	      if (null != paymentReceipts && !CollectionUtils.isEmpty(paymentReceipts) && paymentReceipts.size() >= 1) {
			  /*FUL2-17826 removed the temporary gst Waiver changes*/
	    	   if (checkNeoChannel(proposalDetails) && !productDetailsList.isEmpty()
					&& Objects.nonNull(productDetailsList.get(0).getProductInfo())) {
				amount = productDetailsList.get(0).getProductInfo().getInitialPremium();
			}

			else if (proposalDetails.getSalesStoriesProductDetails() != null
					&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())
					&& proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() != 0L
					&& !StringUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo()
							.getProductIllustrationResponse().getInitialPremiumforSales())) {
				amount = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumforSales();
			}
			else {
				amount = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid();
			}
		}
		return amount;
	}



	private String setPlanName(ProposalDetails proposalDetails){
		String planName = "";
		List<ProductDetails> productDetailsList = Objects.nonNull(proposalDetails.getProductDetails()) ? proposalDetails.getProductDetails()
				: Collections.emptyList();
		if (Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)){
			planName = proposalDetails.getSalesStoriesProductDetails().getProductDetails().get(0).getProductInfo().getProductName();			
		}
		else if(!productDetailsList.isEmpty() && Objects.nonNull(productDetailsList.get(0).getProductInfo())) {
			planName = productDetailsList.get(0).getProductInfo().getProductName();
		}
		return planName;
	}

    public Context setDataOfPaymentDocument(Payload payload) {
    	Map<String, Object> dataVariables = new HashMap<>();
		String policyDate = "";
		if (isSWPProduct(payload.getProposalDetails())) {
			policyDate = getPolicyDate(payload.getProposalDetails(), true);
		} else {
			policyDate = getPolicyDate(payload.getProposalDetails(), false);
		}
		String paymentDate = org.apache.commons.lang3.StringUtils.EMPTY;
		String paymentMethod = org.apache.commons.lang3.StringUtils.EMPTY;
		if(Objects.nonNull(payload.getProposalDetails()) && Objects.nonNull(payload.getProposalDetails().getPaymentDetails())
			&& Objects.nonNull(payload.getProposalDetails().getPaymentDetails().getReceipt())
			&& !payload.getProposalDetails().getPaymentDetails().getReceipt().isEmpty()
			&& Objects.nonNull(payload.getProposalDetails().getPaymentDetails().getReceipt().get(0))) {
			paymentDate = payload.getProposalDetails().getPaymentDetails().getReceipt().get(0).getPaymentDate();
			paymentMethod = payload.getProposalDetails().getPaymentDetails().getReceipt().get(0).getModeOfPayment();
		}
		String docGenerationDate = LocalDateTime.now(ZoneId.of(AppConstants.APP_TIMEZONE)).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dataVariables.put("docGenerationDate", docGenerationDate);
		dataVariables.put("paymentDate", paymentDate);
		dataVariables.put("firstName", payload.getPersonalInfo().getFirstName());
    	dataVariables.put("amount", payload.getProductInfo().getInitialPremium());
    	dataVariables.put("paymentMethod", paymentMethod);
    	dataVariables.put("policyDate", policyDate);
    	dataVariables.put("planName", payload.getProductInfo().getProductName());
    	dataVariables.put("equoteNumber", payload.getEquoteNumber());
    	dataVariables.put("leadId", payload.getLeadId());
    	dataVariables.put("policyNumber", payload.getPolicyNumber());
		  dataVariables.put("lifeCover", payload.getProductInfo().getSumAssured());
			if (payload.getSumAssuredForSecondLife() != null){
				dataVariables.put("lifeCoverSecond",payload.getSumAssuredForSecondLife());
			}
			if (Utility.isPaymentFrequencySingle(payload.getProposalDetails())) {
				dataVariables.put("paymentFrequency", "Single");
			}else{
				dataVariables.put("paymentFrequency", payload.getProposalDetails().getProductDetails().get(0).getProductInfo()
								.getBenefitReturnFrequency());
			}
		dataVariables.put("coverageTerm", payload.getProductInfo().getCoverageTerm());
		dataVariables.put("income", payload.getProposalDetails().getProductDetails().get(0).getProductInfo().getBenefitMonthlyIncome());
		setUINNumber(dataVariables);
    	Context context = new Context();
    	context.setVariables(dataVariables);
    	return context;
	}

	/*FUL2-11549 Payment acknowledgement for all channels : This method will return the title based on the gender*/
	private String setTitle(ProposalDetails proposalDetails) {
    	String title = "";
    	PartyInformation partyInformation = proposalDetails.getPartyInformation().stream().filter(p->p.getPartyType()
				.equalsIgnoreCase(AppConstants.PROPOSER)).findAny().orElse(null);
    	if(partyInformation!=null) {
			String gender = partyInformation.getBasicDetails().getGender();
			if (gender.equalsIgnoreCase(AppConstants.MALE)) {
				title = AppConstants.MR;
			} else if (gender.equalsIgnoreCase(AppConstants.FEMALE)) {
				title = AppConstants.MS;
			} else {
				title = AppConstants.MX;
			}
		}
		return title;
	}

	/*FUL2-11549 Payment acknowledgement for all channels : This method is used to return user friendly premium mode*/
	private String setPaymentMethod(List<Receipt> receipts) {
    	String actualPremiumMode = AppConstants.BLANK;
    	if(!receipts.isEmpty()){
    		actualPremiumMode = receipts.get(0).getPremiumMode();
		}
		return Utility.getPaymentMethods(actualPremiumMode);
	}

	/*FUL2-11549 Payment acknowledgement for all channels : This method is used to generate payment date in required format using existing format*/
	private String setPaymentDate(ProposalDetails proposalDetails) throws ParseException {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String channel = proposalDetails.getChannelDetails().getChannel();
		String date = AppConstants.BLANK;
		if (receipt != null) {
			String paymentMethod = receipt.getPremiumMode();
			switch (paymentMethod) {
				case "online":
					if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) && Objects
						.nonNull(paymentMethod)) {
						boolean isCatAxisAB = Utility.isDIYJourney(proposalDetails);
						String stringDate = Utility.getStringDateFromDate(
							new SimpleDateFormat(isCatAxisAB ? AppConstants.UTC_DATE_FORMAT : AppConstants.UTC_DATE_FORMAT)
								.parse(receipt.getTransactionDateTimeStamp()));
						date = Utility.dateFormatterWithTimeZone(stringDate,
							AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN,
							AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
					} else if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(channel)) {
						date = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
							AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z,
							AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
					} else {
						date = receipt.getTransactionDateTimeStamp();
					}
					break;
				case "payLater":
					date = setDateForPayLater(proposalDetails);
					break;
				case "cheque":
					if (Objects.nonNull(receipt.getPaymentChequeDetails())) {
						String stringDate = Utility.getStringDateFromDate(receipt.getPaymentChequeDetails().getChequeDate());
						date = Utility.dateFormatterWithTimeZone(stringDate, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_SLASH);
					}
					break;
				case "demandDraft":
					if (Objects.nonNull(receipt.getDemandDraftDetails())) {
						String stringDate = Utility.getStringDateFromDate(receipt.getDemandDraftDetails().getDemandDraftDate());
						date = Utility.dateFormatterWithTimeZone(stringDate,AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_SLASH);
					}
					break;
				case "directDebit":
					date = setPaymentDateForDirectDebit(proposalDetails, receipt, channel, date);
					break;
				case AppConstants.DIRECTDEBITWITHRENEWALS:
				date = directDebitAxisAndYbl(receipt, channel, date);
					break;
				default:
					date = AppConstants.BLANK;
			}
		}
		return date;
	}

	private String setPaymentDateForDirectDebit(ProposalDetails proposalDetails, Receipt receipt, String channel, String date) {
		if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)||AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel)) {
			String stringDate="";
			if(proposalDetails.getAdditionalFlags()!= null  &&
					!StringUtils.isEmpty(proposalDetails.getAdditionalFlags().getRequestSource()) &&
					proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase(AppConstants.AXIS_TELESALES_REQUEST)){
				stringDate = receipt.getPaymentDate();
				date = Utility.dateFormatterWithTimeZone(stringDate,
						AppConstants.E_MMM_DD_YYYY_HH_MM_SS_Z,
						AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
			}// ful2_20889 date and time issue for axis in pf
			else {
				date = directDebitAxisAndYbl(receipt, channel, date);
			}
		}else if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(channel)) {
      date = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
          AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z,
          AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
    }
		return date;
	}

	/*FUL2-11549 Payment acknowledgement for all channels*/
	private String setDateForPayLater(ProposalDetails proposalDetails) {
		String date = "";
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		Boolean enachStatus = Objects.nonNull(proposalDetails.getBank()) && proposalDetails.getBank().getEnachDetails() != null
				&& Utility.andTwoExpressions( !isEmpty(proposalDetails.getBank().getEnachDetails().getEnachStatus())
				, "SUCCESS".equalsIgnoreCase(proposalDetails.getBank().getEnachDetails().getEnachStatus()));
		if(!isEmpty(receipt.getIsSIOpted()) && receipt.getIsSIOpted().equalsIgnoreCase("True")) {
			date = receipt.getTransactionDateTimeStamp();
		} else if(enachStatus){
			date = proposalDetails.getBank().getEnachDetails().getFirstCollectionDate().toString();
		}
		return date;
	}


	public static boolean isSWPProduct(ProposalDetails proposalDetails) {
		if (Objects.nonNull(proposalDetails.getProductDetails())
				&& !proposalDetails.getProductDetails().isEmpty()) {
			return AppConstants.SWP.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType());
		}
		return false;
	}

	private String getPolicyDate(ProposalDetails proposalDetails, boolean isSWP) {
    	if (Objects.nonNull(proposalDetails.getPaymentDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
				&& !proposalDetails.getPaymentDetails().getReceipt().isEmpty()
				&& !org.springframework.util.StringUtils.isEmpty(proposalDetails.getPaymentDetails().getReceipt().get(0).getPaymentDate())) {

			String channel = proposalDetails.getChannelDetails().getChannel();
    		String paymentDate = proposalDetails.getPaymentDetails().getReceipt().get(0).getPaymentDate();
    		if (isSWP) {
    			if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)){
    				return Utility.dateFormatter(paymentDate, AppConstants.DD_MM_YYYY_HYPHEN, AppConstants.DD_MM_YYYY_SLASH);
    			}else{
    				return Utility.dateFormatter(paymentDate, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN_A);
    			}
			} else {
				return Utility.dateFormatter(paymentDate, AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)
						? AppConstants.DD_MM_YYYY_HYPHEN : AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN,  AppConstants.DD_MM_YYYY_SLASH);
			}
		} else {
    		return LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(df);
		}
	}
	private String directDebitAxisAndYbl(Receipt receipt, String channel, String date) {
		DirectPaymentDetails directPaymentDetails = receipt.getDirectPaymentDetails();
		YBLPaymentDetails yblPaymentDetails = receipt.getYblPaymentDetails();
		PartnerPaymentDetails partnerPaymentDetails = receipt.getPartnerPaymentDetails();
		if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) && Objects.nonNull(directPaymentDetails)) {
			String stringDate = Utility.getStringDateFromDate(directPaymentDetails.getVoucherUpdatedDate());
			date = Utility.dateFormatterWithTimeZone(stringDate,AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
		} else if (AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel) && Objects.nonNull(yblPaymentDetails) &&
				Objects.nonNull(yblPaymentDetails.getDirectDebitDetails())) {
			date = Utility.dateFormatterWithTimeZone(yblPaymentDetails.getDirectDebitDetails().getDirectDebitOtpValidated(),
					AppConstants.E_MMM_DD_YYYY_HH_MM_SS_GMT, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
		} else if (AppConstants.CHANNEL_TMB.equalsIgnoreCase(channel) && Objects.nonNull(partnerPaymentDetails) &&
				Objects.nonNull(partnerPaymentDetails.getDirectDebitDetails())) {
			date = Utility.dateFormatterWithTimeZone(partnerPaymentDetails.getDirectDebitDetails().getDirectDebitOtpValidated(),
					AppConstants.E_MMM_DD_YYYY_HH_MM_SS_GMT, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
		}
		return date;
	}

	public boolean isSWPJLProduct(ProposalDetails proposalDetails) {
		if (Objects.nonNull(proposalDetails.getProductDetails())
				&& !proposalDetails.getProductDetails().isEmpty()) {
			return AppConstants.SWPJL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType());
		}
		return false;
	}
}
