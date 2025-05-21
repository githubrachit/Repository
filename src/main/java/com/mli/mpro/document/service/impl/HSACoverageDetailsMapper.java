	package com.mli.mpro.document.service.impl;

import static com.mli.mpro.productRestriction.util.AppConstants.REQUEST_SOURCE_TELESALES;
import static com.mli.mpro.utils.Utility.zeroIfNullOrEmpty;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.ProposalRiderDetails;
import com.mli.mpro.document.utils.TraditionalFormUtil;
import com.mli.mpro.location.services.impl.DocsAppServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BankDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.Form60Details;
import com.mli.mpro.proposal.models.PanDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PersonalIdentification;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProductIllustrationResponse;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.Receipt;
import com.mli.mpro.proposal.models.RiderDetails;
import com.mli.mpro.utils.Utility;

@Service
public class HSACoverageDetailsMapper {
	private static final Logger logger = LoggerFactory.getLogger(HSACoverageDetailsMapper.class);
	private static final String TRANSGENDER = "Transgender";
	private static final String PAYMENT_MODE = "paymentMode";
	private static final String PAYMENT_DATE = "paymentDate";
	private static final String PAYMENT_BANK = "paymentBank";
	private static final String AMOUNT = "amount";
	private static final String AMOUNT_WORDS = "amountWords";


	@Autowired
	private DocsAppServiceImpl docsAppServiceImpl;

	Map<String, String> sourceOfFunds = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("salaried", "Salary");
			put("agriculture", "Agriculture");
			put("professional", "Professional");
			put("self employed", "Business");
			put("unanswered", AppConstants.OTHER_INCOME);
			put("self employed from home", "Business");
			put("housewife", AppConstants.OTHER_INCOME);
			put("retired", AppConstants.OTHER_INCOME);
			put("others", AppConstants.OTHER_INCOME);
			put("student", AppConstants.OTHER_INCOME);
		}
	};

	public Context mapDataForCoverageDetails(ProposalDetails proposalDetails) throws UserHandledException {
		long transactionId = proposalDetails.getTransactionId();
		logger.info("START mapDataForCoverageDetails for transactionId {}", transactionId);
		Map<String, Object> dataVariables = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat output = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HYPHEN);
		output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		// List of variables used in the template
		try {
			//
			String formType = proposalDetails.getApplicationDetails().getFormType();
			String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
			ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
			// 1. set plan details
			setPlanDetails(proposalDetails, dataVariables);
			// 2. set proposer bank details
			setNeftBankDetails(proposalDetails, dataVariables);
			// 3. set PAN details
			setPanDetails(proposalDetails, dataVariables);
			// 4. mode of payment 5. renewal premium 6. source of funds
			BasicDetails proposerDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
			String occupation = Objects.nonNull(proposerDetails) && !StringUtils.isEmpty(proposerDetails.getOccupation()) ? proposerDetails.getOccupation() : "others";

			String modeOfPayment = productDetails.getProductInfo().getModeOfPayment();
			String renewalPayment = StringUtils.EMPTY;
			Receipt receiptDetails = proposalDetails.getPaymentDetails().getReceipt().get(0);
			String isSIOpted = receiptDetails.getIsSIOpted();
			renewalPayment = setRenewalPayment(proposalDetails, modeOfPayment, isSIOpted, renewalPayment);
			String funds = "";
			if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
				funds = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
						.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
						.findFirst().map(PartyInformation::getBasicDetails).map(BasicDetails::getSourceOfFunds)
						.orElse("");
			} else {
				funds = sourceOfFunds.get(occupation.toLowerCase());
			}

			dataVariables.put("modeOfPayment", modeOfPayment);
			dataVariables.put("renewalPremium", renewalPayment);
			dataVariables.put("sourceOfFund", funds);
			// 7. set payor details
			setPayorDetails(proposalDetails, dataVariables);
			// 8. if max life agent
			String maxEmp = Utility.evaluateConditionalOperation(
					(!StringUtils.isEmpty(proposalDetails.getAdditionalFlags().getIsMaxEmp())
							&& proposalDetails.getAdditionalFlags().getIsMaxEmp().equalsIgnoreCase("YES")),
					"YES", "NO");
			dataVariables.put("isMaxEmp", maxEmp);
			// 9. effective date of coverage
			String effectiveDateOfCoverage = "";
			effectiveDateOfCoverage = productDetails.getProductInfo().getEffectiveDateOfCoverage() != null
						? output.format(sdf.parse(productDetails.getProductInfo().getEffectiveDateOfCoverage()))
						: StringUtils.EMPTY;
			dataVariables.put("effectivePolicy", effectiveDateOfCoverage);
			// 10. set premium payment details
			dataVariables = setPaymentDetails(proposalDetails, dataVariables);

			String coverageTerm = productDetails.getProductInfo().getProductIllustrationResponse().getCoverageTerm();
			if (AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())) {
				setCoverageTillAgeField(proposalDetails, dataVariables, coverageTerm);
			}
			Context coverageDetailsContext = new Context();
			coverageDetailsContext.setVariables(dataVariables);
		} catch (Exception ex) {
			int lineNumber = ex.getStackTrace()[0].getLineNumber();
			logger.error(
					"mapDataForCoverageDetails failed for transactionId {} at line {} with exception {}",
					transactionId, lineNumber, ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("%M Data Mapping Failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("mapDataForCoverageDetails completed successfully for transactionId {}",
				transactionId);
		Context coverageDetailsContext = new Context();
		coverageDetailsContext.setVariables(dataVariables);
		logger.info("END mapDataForCoverageDetails");
		return coverageDetailsContext;
	}

	private void setPlanDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) throws UserHandledException {
		// set base plan details
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		String planName = productDetails.getProductInfo().getProductName();
		String variant = productDetails.getProductInfo().getVariant();
		String sumAssured = String.format("%.0f", Utility
				.roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getSumAssured()));
		String coverageTerm = productDetails.getProductInfo().getProductIllustrationResponse().getCoverageTerm();
		String premiumPaymentTerm = productDetails.getProductInfo().getProductIllustrationResponse()
				.getPremiumPaymentTerm();
		String modalPremium = String.format("%.0f", Utility
				.roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getModalPremium()));
		String modalPremiumWithoutGST = String.valueOf(Utility.roundOffValue(
				productDetails.getProductInfo().getProductIllustrationResponse().getRequiredModalPremium()));
		String gstCess = String.format("%.0f", Utility
				.roundOffValue(productDetails.getProductInfo().getProductIllustrationResponse().getServiceTax()));
		if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
			gstCess = "0";
		}
		String totalPremium = String.format("%.0f", Utility.roundOffValue(
				productDetails.getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()));


		dataVariables.put("basePlan", planName);
		dataVariables.put("variant", StringUtils.isEmpty(variant) ? "NA" : variant);
		dataVariables.put("sumAssured", sumAssured);
		dataVariables.put("coverageTerm", coverageTerm);
		dataVariables.put("premimumPayingTerm", premiumPaymentTerm);
		dataVariables.put("modalPremium", modalPremium);
		dataVariables.put("modalPremiumGST", modalPremiumWithoutGST);
		dataVariables.put("GSTCess", gstCess);
		dataVariables.put("totalPremium", totalPremium);

		// set rider details
		setDataForRiders(productDetails, dataVariables);
	}

	private void setNeftBankDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		BankDetails bankdetails = null;

		if (!CollectionUtils.isEmpty(proposalDetails.getBank().getBankDetails())) {
			bankdetails = proposalDetails.getBank().getBankDetails().get(0);
		}
		String micr = Objects.nonNull(bankdetails) ? bankdetails.getMicr() : "";
		String accountNumber = Objects.nonNull(bankdetails) ? bankdetails.getBankAccountNumber() : "";
		String ifsc = Objects.nonNull(bankdetails) ? bankdetails.getIfsc() : "";
		String accHolder = Objects.nonNull(bankdetails) ? bankdetails.getAccountHolderName() : "";
		String typeOfBank = Objects.nonNull(bankdetails) ? bankdetails.getTypeOfAccount() : "";
		String bankBranch = Objects.nonNull(bankdetails) && Objects.nonNull(bankdetails.getBankName())
				? bankdetails.getBankName().concat(" ").concat(bankdetails.getBankBranch())
				: "";
		String bankingsince = AppConstants.NA;
		if (proposalDetails.getBank() != null
				&& !proposalDetails.getBank().getBankDetails().isEmpty()
				&& proposalDetails.getBank().getBankDetails().get(0) != null
				&& proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate() != null) {
			bankingsince = String.valueOf(Utility
					.dateFormatter(proposalDetails.getBank().getBankDetails().get(0).getBankAccOpeningDate()));
		}

		dataVariables.put("micrCode", micr);
		dataVariables.put("accountNumber", accountNumber);
		dataVariables.put("IFSCCode", ifsc);
		dataVariables.put("accountHolderName", accHolder);
		dataVariables.put("typeOfAcc", typeOfBank);
		dataVariables.put("bankNameBranch", bankBranch);
		dataVariables.put("bankingSince", bankingsince);
	}

	private void setPanDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		String pan = "";
		String panNotRequired = "NO";
		String form60 = "NO";
		String form49a = "NO";
		String insuredPan = "";
		String insuredForm60 = formType.equals(AppConstants.DEPENDENT)
				&& !proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails()
						.getNationality().equalsIgnoreCase(AppConstants.INDIAN_NATIONALITY)
				&& StringUtils.isEmpty(insuredPan) ? "YES" : "NO";
		String panApplied = StringUtils.EMPTY;
		String panAck = StringUtils.EMPTY;
		String appDate = StringUtils.EMPTY;
		boolean ispanNotRequired = false;
		String isChargeableIncome = StringUtils.EMPTY;
		String isIncomeExceedLimit = StringUtils.EMPTY;
		String isTaxableIncome = StringUtils.EMPTY;
		String isItOtherIncome = StringUtils.EMPTY;
		String isApplicableIncome = StringUtils.EMPTY;
		String isNRI = StringUtils.EMPTY;

		if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			pan = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
					.findFirst().map(PartyInformation::getPersonalIdentification)
					.map(PersonalIdentification::getPanDetails).map(PanDetails::getPanNumber).orElse("");

		} else if (Objects.nonNull(proposalDetails.getPartyInformation())
				&& !proposalDetails.getPartyInformation().isEmpty()
				&& Objects.nonNull(proposalDetails.getPartyInformation().get(0).getPersonalIdentification())
				&& Objects.nonNull(
						proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails())) {
			pan = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails()
					.getPanNumber();
		}
		if (StringUtils.isEmpty(pan)) {
			pan = "NO";
			ispanNotRequired = true;
			form60 = "YES";
			form49a = "YES";
			Form60Details form60Details = proposalDetails.getForm60Details();
			// NEORW-80: handle NULL pointer exception for form60Details
			if (Objects.nonNull(form60Details)) {
				panApplied = Utility.evaluateConditionalOperation(
						form60Details.getDetailsOfDontHavePan().equalsIgnoreCase("YES"), "YES", "NO");
				panAck = form60Details.getPanAcknowledgementNo();
				appDate = Utility.dateFormatter(form60Details.getPanApplicationDate());
				isChargeableIncome = Utility.evaluateConditionalOperation(form60Details.isChargeableIncome(), "YES",
						"NO");
				isIncomeExceedLimit = Utility.evaluateConditionalOperation(form60Details.isIncomeExceedLimit(),
						"YES", "NO");
				isTaxableIncome = Utility.evaluateConditionalOperation(form60Details.isTaxableIncome(), "YES",
						"NO");
				isItOtherIncome = Utility.evaluateConditionalOperation(form60Details.isItOtherIncome(), "YES",
						"NO");
				isNRI = Utility.evaluateConditionalOperation(form60Details.isNRI(), "YES", "NO");
				isApplicableIncome = Utility.evaluateConditionalOperation(form60Details.isApplicableIncome(), "YES",
						"NO");
			}
			panNotRequired = Utility.evaluateConditionalOperation(ispanNotRequired, "YES", "NO");
		}

		if (Objects.nonNull(proposalDetails.getPartyInformation())
				&& proposalDetails.getPartyInformation().size() > 1
				&& Objects.nonNull(proposalDetails.getPartyInformation().get(1).getPersonalIdentification())) {
			insuredPan = proposalDetails.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getPanNumber();
		}
		dataVariables.put("pan", pan);
		dataVariables.put("insuredPan", insuredPan);
		dataVariables.put("isForm60", form60);
		dataVariables.put("isForm49a", form49a);
		dataVariables.put("appliedForPan", panApplied);
		dataVariables.put("ackNo", panAck);
		dataVariables.put("appDate", appDate);
		dataVariables.put("panNotRequired", panNotRequired);
		dataVariables.put("ispanNotRequired", ispanNotRequired);
		dataVariables.put("income", isChargeableIncome);
		dataVariables.put("businessTurnover", isIncomeExceedLimit);
		dataVariables.put("taxableincome", isTaxableIncome);
		dataVariables.put("agriculture", isItOtherIncome);
		dataVariables.put("nonResident", isNRI);
		dataVariables.put("tribal", isApplicableIncome);
		dataVariables.put("insuredForm60", insuredForm60);

	}

	private String setRenewalPayment(ProposalDetails proposalDetails, String modeOfPayment, String isSIOpted,
			String renewalPayment) {
		logger.info("Setting Renewal Payment type for transactionID : {}", proposalDetails.getTransactionId());
		if ("SINGLE".equalsIgnoreCase(modeOfPayment)) {
			renewalPayment = "Not Applicable";
		} else if (Utility.andTwoExpressions(
				AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()),
				(Objects.nonNull(proposalDetails.getBank())
						&& !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy()))
						&& AppConstants.DIRECTDEBIT
								.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))) {
			renewalPayment = AppConstants.DIRECT_DEBIT;
		} else if (Utility.andTwoExpressions(!StringUtils.isEmpty(isSIOpted), "TRUE".equalsIgnoreCase(isSIOpted))) {
			renewalPayment = AppConstants.CREDIT_CARD_RENEWAL;
		} else if (Objects.nonNull(proposalDetails.getBank())
				&& !StringUtils.isEmpty(proposalDetails.getBank().getPaymentRenewedBy())) {
			renewalPayment = proposalDetails.getBank().getPaymentRenewedBy();
		}

		// FUL2-18174 if AXISR and IVR Case, renewalPayment should be CHEQUE by default
		if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				&& AppConstants.AXIS_TELESALES_REQUEST
						.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
				&& AppConstants.DIRECTDEBIT
						.equalsIgnoreCase(proposalDetails.getPaymentDetails().getReceipt().get(0).getPremiumMode())) {
			renewalPayment = AppConstants.CHEQUE;
		}

		logger.info("Setting Renewal Payment type for transactionID : {} & renewalPayment : {}",
				proposalDetails.getTransactionId(), renewalPayment);
		return renewalPayment;
	}

	private void setPayorDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		String payorDiffFromProposer = "";
		String payorName = "";
		String relationShipWithProposer = "";
		String payorAddress = "";
		String payorGender = "";
		String payorAnnualIncome = "";
		String payorDOB = "";
		BasicDetails payorDetails = (BasicDetails) Utility.evaluateConditionalOperation(proposalDetails.getPartyInformation().size() > 2, proposalDetails.getPartyInformation().get(2).getBasicDetails(), null);
		payorDiffFromProposer = Utility.evaluateConditionalOperation(Objects.nonNull(proposalDetails.getAdditionalFlags()), (proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO"), AppConstants.NO);

		if (Utility.andTwoExpressions(payorDiffFromProposer.equalsIgnoreCase("YES"), Objects.nonNull(payorDetails))) {
			payorName = Stream
					.of(payorDetails.getFirstName(), payorDetails.getMiddleName(), payorDetails.getLastName())
					.filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));

			payorGender = Utility.getGender(payorDetails.getGender());
			if (Utility.schemeBCase(formType, schemeType) || (AppConstants.SELF.equalsIgnoreCase(formType)
					&& (AppConstants.CEIP.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())
							|| AppConstants.EMPLOYER_EMPLOYEE
									.equalsIgnoreCase(productDetails.getObjectiveOfInsurance()))))
				payorGender = "";
			if (AppConstants.OTHER.equalsIgnoreCase(payorGender)) {
				payorGender = TRANSGENDER;
			}
			payorDOB = Utility.dateFormatter(payorDetails.getDob());
			payorAnnualIncome = payorDetails.getAnnualIncome();
			if (Utility.andThreeExpressions(Objects.nonNull(payorDetails.getAddress()),
					!payorDetails.getAddress().isEmpty(),
					Objects.nonNull(payorDetails.getAddress().get(0).getAddressDetails()))) {
				payorAddress = getFullAddress(payorDetails.getAddress().get(0).getAddressDetails());
			}

			relationShipWithProposer = payorDetails.getRelationshipWithProposer();
		}
		dataVariables.put("isPayorDiffFromProposer",
				Objects.nonNull(proposalDetails.getAdditionalFlags())
						? proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()
						: "");
		dataVariables.put("payorDiffFromProposer", payorDiffFromProposer);
		dataVariables.put("payorName", payorName);
		dataVariables.put("relationShipwithProposer", relationShipWithProposer);
		dataVariables.put("payorAddress", payorAddress);
		dataVariables.put("payorGender", payorGender);
		dataVariables.put("payorAnnualIncome", payorAnnualIncome);
		dataVariables.put("payorDOB", payorDOB);

		setPayorPanAndBank(proposalDetails, dataVariables);
	}

	private void setPayorPanAndBank(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		String payorPan = "";
		String payorBank = "";
		String payorBankBranch = "";
		String payorAccountNumber = "";
		if (Utility.andTwoExpressions(Objects.nonNull(proposalDetails.getPartyInformation()), proposalDetails.getPartyInformation().size() > 2)) {
			if (Utility.andTwoExpressions(Objects.nonNull(proposalDetails.getPartyInformation().get(2).getPersonalIdentification()), Objects.nonNull(proposalDetails.getPartyInformation().get(2).getPersonalIdentification().getPanDetails()))) {
				payorPan = Utility.evaluateConditionalOperation(
						StringUtils.isEmpty(proposalDetails.getPartyInformation().get(2)
								.getPersonalIdentification().getPanDetails().getPanNumber()),
						"NO", proposalDetails.getPartyInformation().get(2).getPersonalIdentification()
								.getPanDetails().getPanNumber());
				}
			if (Objects.nonNull(proposalDetails.getPartyInformation().get(2).getBankDetails())) {
				payorBank = Utility.evaluateConditionalOperation(
						StringUtils.isEmpty(
								proposalDetails.getPartyInformation().get(2).getBankDetails().getBankName()),
						StringUtils.EMPTY,
						proposalDetails.getPartyInformation().get(2).getBankDetails().getBankName());
				payorBankBranch = Utility.evaluateConditionalOperation(
						StringUtils.isEmpty(
								proposalDetails.getPartyInformation().get(2).getBankDetails().getBankBranch()),
						StringUtils.EMPTY,
						proposalDetails.getPartyInformation().get(2).getBankDetails().getBankBranch());
				payorAccountNumber = proposalDetails.getPartyInformation().get(2).getBankDetails()
						.getBankAccountNumber();
			}
		}
		dataVariables.put("payorPan", payorPan);
		dataVariables.put("payorAccountNumber", payorAccountNumber);
		dataVariables.put("payorBankBranch", payorBank.concat(" ").concat(payorBankBranch));
	}

	private void setCoverageTillAgeField(ProposalDetails proposalDetails, Map<String, Object> dataVariables,
			String coverageTerm) {
		String coverageTillAge = "";
		try {
			Date dateOfBirth = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
			LocalDate birthDate = new LocalDate(dateOfBirth);
			LocalDate currentDate = new LocalDate();
			Years age = Years.yearsBetween(birthDate, currentDate);
			coverageTillAge = String.valueOf(Integer.parseInt(coverageTerm) + age.getYears());
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataVariables.put("coverageTillAge", coverageTillAge);
	}

	protected Map<String, Object> setPaymentDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables)
			throws UserHandledException {
		// FUL2-11549 Payment acknowledgement for all channels
		// Fetching receipt data for Secondary policy using getProposal api from
		// proposal service - To fetch date for the salesstory secondary policy.
		proposalDetails = docsAppServiceImpl.setSecondPolicyReceipt(proposalDetails);
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		double amount = 0;
		String paymentDate = StringUtils.EMPTY;
		String paymentMode = StringUtils.EMPTY;
		String bankName = StringUtils.EMPTY;
		try {
			if (Utility.andThreeExpressions(AppConstants.PAY_LATER.equalsIgnoreCase(paymentType), !isEmpty(receipt.getIsSIOpted()), receipt.getIsSIOpted().equalsIgnoreCase("True"))) {
				setPaymentDetailsForPayLater(proposalDetails, dataVariables);
			} else if (AppConstants.ONLINE.equalsIgnoreCase(paymentType)) {
				setPaymentDetailsForOnline(proposalDetails, dataVariables);
			} else if (AppConstants.CHEQUE.equalsIgnoreCase(paymentType)) {
				setPaymentDetailsForCheque(proposalDetails, dataVariables);
			} else if (AppConstants.DEMAND_DRAFT.equalsIgnoreCase(paymentType)) {
				setPaymentDetailsForDemandDraft(proposalDetails, dataVariables);
			} else if (Utility.orTwoExpressions(AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType), AppConstants.DIRECTDEBITWITHRENEWALS.equalsIgnoreCase(paymentType))) {
				setPaymentDetailsForDirectDebit(proposalDetails, dataVariables);
			} else if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPtfPayment())
					&& proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
					&& proposalDetails.getProductDetails().get(0) != null
					&& proposalDetails.getProductDetails().get(0).getProductInfo() != null && AppConstants.GLIP_ID
							.equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
				amount = Utility.setPremiumAmount(proposalDetails);
				String transactionNumber = proposalDetails.getAdditionalFlags().getPtfPolicyNumber();
				bankName = AppConstants.BLANK;
				paymentMode = AppConstants.PTF_PAYMENT;
				paymentDate = Utility
						.dateFormatter(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate());
				dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
				String amountInWords = Utility.convertNumberToWords((int) amount);
				dataVariables.put(AMOUNT_WORDS, amountInWords);
				dataVariables.put(AMOUNT, amount);
				dataVariables.put(PAYMENT_MODE, paymentMode);
				dataVariables.put(PAYMENT_DATE, paymentDate);
				dataVariables.put(PAYMENT_BANK, bankName);
			}
			
		} catch (Exception ex) {
			logger.error("setPaymentDetails Payment details of proposal form document is not found:", ex);
			List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Payment details of proposal form document is not found");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dataVariables;

	}

	private void setPaymentDetailsForPayLater(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		double amount = 0;
		String paymentDate = null;
		String paymentMode = null;
		String bankName = null;
		boolean enachStatus = (Objects.nonNull(proposalDetails.getBank().getEnachDetails()) && !isEmpty(proposalDetails.getBank().getEnachDetails().getEnachStatus()) && proposalDetails.getBank().getEnachDetails().getEnachStatus().equalsIgnoreCase("SUCCESS"));
		if (!(AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && enachStatus)) {
			return;
		}
		if (AppConstants.PAY_LATER.equalsIgnoreCase(paymentType) && !isEmpty(receipt.getIsSIOpted())
				&& receipt.getIsSIOpted().equalsIgnoreCase("True")) {
			amount = Utility.setPremiumAmount(proposalDetails);
			paymentDate = receipt.getTransactionDateTimeStamp();
			paymentMode = "PAY LATER";
			String transactionNumber = receipt.getTransactionReferenceNumber();
			bankName = AppConstants.NA;
			dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);

		} else {
			amount = Utility.setPremiumAmount(proposalDetails);
			paymentDate = proposalDetails.getBank().getEnachDetails().getFirstCollectionDate().toString();
			paymentMode = "PAY LATER";
			String transactionNumber = proposalDetails.getBank().getEnachDetails().getIngenicoTransactionId();
			bankName = proposalDetails.getBank().getEnachDetails().getSponsorBank();
			dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
		}
		String amountInWords = Utility.convertNumberToWords((int) amount);
		dataVariables.put(AMOUNT_WORDS, amountInWords);
		dataVariables.put(AMOUNT, amount);
		dataVariables.put(PAYMENT_MODE, Utility.emptyIfNull(paymentMode));
		dataVariables.put(PAYMENT_DATE, Utility.emptyIfNull(paymentDate));
		dataVariables.put(PAYMENT_BANK, Utility.emptyIfNull(bankName));
	}

	private void setPaymentDetailsForOnline(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		if(!AppConstants.ONLINE.equalsIgnoreCase(paymentType)) {
			return;
		}
		double amount = 0;
		String paymentDate = null;
		amount = Utility.setPremiumAmount(proposalDetails);
		if (AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
			paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
					AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z, AppConstants.DATE_FORMAT);
		} else if (Utility.compareDateFormats(receipt.getTransactionDateTimeStamp(),
				AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN)) {
			paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
					AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DATE_FORMAT);
		} else if (AppConstants.J3_JOURNEY
				.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsOnboardedProduct())) {
			paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
					AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DATE_FORMAT);
		} else {
			paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
					AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z_CHAR, AppConstants.DATE_FORMAT);
		}
		String transactionNumber = receipt.getTransactionReferenceNumber();
		String paymentMode = "ONLINE";
		String bankName = "NA";
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
		String amountInWords = Utility.convertNumberToWords((int) amount);
		dataVariables.put(AMOUNT_WORDS, amountInWords);
		dataVariables.put(AMOUNT, amount);
		dataVariables.put(PAYMENT_MODE, paymentMode);
		dataVariables.put(PAYMENT_DATE, Utility.emptyIfNull(paymentDate));
		dataVariables.put(PAYMENT_BANK, bankName);
	}

	private void setPaymentDetailsForCheque(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		if(!AppConstants.CHEQUE.equalsIgnoreCase(paymentType)) {
			return;
		}
		double amount = 0;
		amount = Utility.setPremiumAmount(proposalDetails);
		String paymentDate = Utility.dateFormatter(receipt.getPaymentChequeDetails().getChequeDate());
		String paymentMode = AppConstants.CHEQUE;
		long chequeNumber = receipt.getPaymentChequeDetails().getChequeNumber();
		String bankName = receipt.getPaymentChequeDetails().getChequeBankName();
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, chequeNumber);
		String amountInWords = Utility.convertNumberToWords((int) amount);
		dataVariables.put(AMOUNT_WORDS, amountInWords);
		dataVariables.put(AMOUNT, amount);
		dataVariables.put(PAYMENT_MODE, paymentMode);
		dataVariables.put(PAYMENT_DATE, paymentDate);
		dataVariables.put(PAYMENT_BANK, bankName);
	}

	private void setPaymentDetailsForDemandDraft(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		if (!AppConstants.DEMAND_DRAFT.equalsIgnoreCase(paymentType)) {
			return;
		}
		double amount = 0;
		amount = Utility.setPremiumAmount(proposalDetails);
		String paymentDate = Utility.dateFormatter(receipt.getDemandDraftDetails().getDemandDraftDate());
		String paymentMode = "DEMAND DRAFT";
		String transactionNumber = receipt.getDemandDraftDetails().getDemandDraftNumber();
		String bankName = receipt.getDemandDraftDetails().getDemandDraftBankName();
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
		String amountInWords = Utility.convertNumberToWords((int) amount);
		dataVariables.put(AMOUNT_WORDS, amountInWords);
		dataVariables.put(AMOUNT, amount);
		dataVariables.put(PAYMENT_MODE, paymentMode);
		dataVariables.put(PAYMENT_DATE, paymentDate);
		dataVariables.put(PAYMENT_BANK, bankName);
	}

	private void setPaymentDetailsForDirectDebit(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		String paymentType = receipt.getPremiumMode();
		if(!(AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType) || AppConstants.DIRECTDEBITWITHRENEWALS.equalsIgnoreCase(paymentType))) {
			return;
		}
		double amount = 0;
		String paymentDate = StringUtils.EMPTY;
		String paymentMode = Utility.evaluateConditionalOperation((proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase(AppConstants.AXIS_TELESALES_REQUEST)
				&& AppConstants.DIRECTDEBIT.equalsIgnoreCase(paymentType)),
				"DIRECT DEBIT (IVR)",
				"DIRECT DEBIT");
		String transactionNumber = StringUtils.EMPTY;
		if (Utility.andTwoExpressions(proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase("BY"),
				(receipt.getYblPaymentDetails() != null
						&& receipt.getYblPaymentDetails().getDirectDebitDetails() != null))) {
			transactionNumber = receipt.getYblPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
			paymentDate = Utility.evaluateConditionalOperation(
					!StringUtils.isEmpty(receipt.getYblPaymentDetails().getDirectDebitDetails()
							.getDirectDebitOtpValidated()),
					receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated(),
					Utility.dateFormatter(new Date()));
		}else if (Utility.andTwoExpressions(Utility.isTMBPartner(proposalDetails.getAdditionalFlags().getSourceChannel())
				, ( receipt.getPartnerPaymentDetails() != null
						&& receipt.getPartnerPaymentDetails().getDirectDebitDetails() != null))) {
			transactionNumber = receipt.getPartnerPaymentDetails().getDirectDebitDetails().getExternalRefNumber();
			paymentDate = Utility.evaluateConditionalOperation(
					!StringUtils.isEmpty(receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated())
					, receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitOtpValidated()
					, Utility.dateFormatter(new Date()));
		} else if (receipt.getDirectPaymentDetails() != null) {
			transactionNumber = receipt.getDirectPaymentDetails().getvoucherNumber();
			paymentDate = Utility.evaluateConditionalOperation(
					receipt.getDirectPaymentDetails().getVoucherUpdatedDate() != null,
					Utility.dateFormatter(receipt.getDirectPaymentDetails().getVoucherUpdatedDate()),
					Utility.dateFormatter(new Date()));
		} else if (Objects.nonNull(proposalDetails.getAdditionalFlags())
				&& Objects.nonNull(proposalDetails.getAdditionalFlags().getRequestSource())
				&& (proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase("Thanos 1")
						|| proposalDetails.getAdditionalFlags().getRequestSource().equalsIgnoreCase("Thanos 2"))
				|| REQUEST_SOURCE_TELESALES
						.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())) {
			transactionNumber = receipt.getPaymentReferenceCode();
			paymentDate = Utility.dateFormatter(receipt.getTransactionDateTimeStamp(),
					AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z, AppConstants.DATE_FORMAT);
		}
		amount = Utility.setPremiumAmount(proposalDetails);
		String bankName = StringUtils.EMPTY;
		dataVariables.put(AppConstants.TRANSACTION_NUMBER, transactionNumber);
		String amountInWords = Utility.convertNumberToWords((int) amount);
		dataVariables.put(AMOUNT_WORDS, amountInWords);
		dataVariables.put(AMOUNT, amount);
		dataVariables.put(PAYMENT_MODE, paymentMode);
		dataVariables.put(PAYMENT_DATE, paymentDate);
		dataVariables.put(PAYMENT_BANK, bankName);
	}

	private void setDataForRiders(ProductDetails productDetails, Map<String, Object> dataVariables)
			throws UserHandledException {

		logger.info("Mapping rider details of proposal form document");

		List<RiderDetails> riderDetails = productDetails.getProductInfo().getRiderDetails();
		List<ProposalRiderDetails> proposalRiderDetails = new ArrayList<>();
		ProductIllustrationResponse illustrationResponse = productDetails.getProductInfo()
				.getProductIllustrationResponse();
		String premiumBackOption = productDetails.getProductInfo().getPremiumBackOption();
		// if riderDetails list is not empty
		if (!riderDetails.isEmpty()) {
			try {
				for (int riderCount = 0; riderCount < riderDetails.size(); riderCount++) {

					checkRiderDataRequired(riderDetails, proposalRiderDetails, illustrationResponse, premiumBackOption,
							riderCount);

					if (!proposalRiderDetails.isEmpty()) {
						dataVariables.put("riderExist", true);
					}
				}
				setRiderPremiumPayingTerm(productDetails, proposalRiderDetails);
			} catch (Exception ex) {
				logger.error("Riding Details not found:", ex);
				List<String> errorMessages = new ArrayList<>();
			    errorMessages.add("Riding Details not found");
			    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		dataVariables.put("riderDetails", proposalRiderDetails);
	}

	private void checkRiderDataRequired(List<RiderDetails> riderDetails,
			List<ProposalRiderDetails> proposalRiderDetails, ProductIllustrationResponse illustrationResponse,
			String premiumBackOption, int riderCount) {
		ProposalRiderDetails details;
		if (riderDetails.get(riderCount).isRiderRequired()) {
			details = new ProposalRiderDetails();
			details.setRiderRequired(true);
			String riderName = riderDetails.get(riderCount).getRiderInfo();
			details.setPremiumBackOption(StringUtils.isEmpty(premiumBackOption) ? "NA" : premiumBackOption);
			details.setPremiumPayingTerm("NA");
			ProposalRiderDetails propRiderDetails = setRiderSpecificData(riderDetails, illustrationResponse, riderCount,
					details, riderName);
			propRiderDetails.setPremiumBackOption(StringUtils.isEmpty(propRiderDetails.getPremiumBackOption()) ? "NA"
					: propRiderDetails.getPremiumBackOption());
			propRiderDetails.setPremiumPayingTerm(StringUtils.isEmpty(propRiderDetails.getPremiumPayingTerm()) ? "NA"
					: propRiderDetails.getPremiumPayingTerm());
			proposalRiderDetails.add(propRiderDetails);
		}
	}

	private ProposalRiderDetails setRiderSpecificData(List<RiderDetails> riderDetails,
			ProductIllustrationResponse illustrationResponse, int riderCount, ProposalRiderDetails details,
			String riderName) {
		switch (riderName) {
		case AppConstants.WOP:
			case AppConstants.AXIS_WOP:
			details = setProposalRiderDetailsWOP(illustrationResponse);
			break;
		case AppConstants.TERM:
			case AppConstants.AXIS_TERM:
			details = setProposalRiderDetailsTERM(illustrationResponse);
			break;
		case AppConstants.ACD:
			case AppConstants.AXIS_ACD:
			details = setProposalRiderDetailsACD(illustrationResponse);
			break;
		case AppConstants.ACD_COVER:
			case AppConstants.AXIS_ACD_COVER:
			details = setProposalRiderDetailsAcdCover(illustrationResponse);
			break;
		case AppConstants.PARTNER:
			case AppConstants.AXIS_PARTNER:
			details = setProposalRiderDetailsPARTNER(illustrationResponse);
			break;
		case AppConstants.CI:
			case AppConstants.AXIS_CI:
			details = setProposalRiderDetailsCI(illustrationResponse);
			break;
		case AppConstants.CAB:
			case AppConstants.AXIS_CAB:
			details = setProposalRiderDetailsCAB(illustrationResponse);
			break;
		case AppConstants.COVID:
			details = setProposalRiderDetailsCOVID(illustrationResponse);
			break;
		// FUL2-9523 set CI Rider details
		case AppConstants.CI_RIDER:
			// FUL2-30525 CIDR- Rider Name Change for ULIP products
		case AppConstants.CI_RIDER_ULIP:
			case AppConstants.AXIS_CI_RIDER_ULIP:
			details = setProposalRiderDetailsCiRider(illustrationResponse, riderDetails, riderCount, riderName);
			break;
		default:
			logger.info("No Rider details found for rider name {}", riderName);
		}
		return details;
	}
	private ProposalRiderDetails setProposalRiderDetailsWOP(ProductIllustrationResponse illustrationResponse) {
        ProposalRiderDetails details = new ProposalRiderDetails();
        double riderSumAssuredWOP = 0;
        details.setRiderName(AppConstants.AXIS_WOP);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getWopPlusRiderTerm()) ? illustrationResponse.getWopPlusRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearWOPPlusRiderPremiumSummary())));
        riderSumAssuredWOP = setRiderSumAssuredWOP(illustrationResponse, riderSumAssuredWOP);
		details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(riderSumAssuredWOP)));
		if (illustrationResponse.getWopPlusRiderGST().equals("0") || StringUtils.isEmpty(illustrationResponse.getWopPlusRiderGST())) {
			details.setRiderGST("");
		} else {
			details.setRiderGST(illustrationResponse.getWopPlusRiderGST());
		}
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsTERM(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_TERM);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getTermPlusRiderTerm()) ? illustrationResponse.getTermPlusRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearTermPlusRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getTermPlusRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getTermPlusRiderGST()) ? illustrationResponse.getTermPlusRiderGST() : "");
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsACD(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_ACD);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAddRiderTerm()) ? illustrationResponse.getAddRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearADDRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getAddRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAddRiderGST()) ? illustrationResponse.getAddRiderGST() : "");
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsAcdCover(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_ACD_COVER);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAccidentCoverRiderTerm())
                ? illustrationResponse.getAccidentCoverRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearAccidentCoverRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getAccidentCoverRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAccidentalCoverRiderGST())
                ? illustrationResponse.getAccidentalCoverRiderGST() : "");
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsPARTNER(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_PARTNER);
        details.setCoverageTerm(
                !StringUtils.isEmpty(illustrationResponse.getPartnerCareRiderTerm()) ? illustrationResponse.getPartnerCareRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearPartnerCareRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getPartnerCareRiderSumAssured())));
        details.setRiderGST("");
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsCI(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_CI);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getAcceleratedCriticalIllnessRiderTerm())
                ? illustrationResponse.getAcceleratedCriticalIllnessRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getAcceleratedCriticalIllnessRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getAccidentalCriticalIllnessRiderGST())
                ? illustrationResponse.getAccidentalCriticalIllnessRiderGST() : "");
        return details;
    }
	private ProposalRiderDetails setProposalRiderDetailsCAB(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.AXIS_CAB);
        details.setCoverageTerm(!StringUtils.isEmpty(illustrationResponse.getCABRiderTerm())
                ? illustrationResponse.getCABRiderTerm() : "0");
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getCABRiderPremium())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getCABRiderSumAssured())));
        details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getCABRiderGST())
                ? illustrationResponse.getCABRiderGST() : "");
		details.setRiderGST(!StringUtils.isEmpty(illustrationResponse.getCABRiderGST())
				? String.format("%.0f", Utility.roundOffValue(illustrationResponse.getCABRiderGST())) : "");
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCOVID(ProductIllustrationResponse illustrationResponse){
        ProposalRiderDetails details = new ProposalRiderDetails();
        details.setRiderName(AppConstants.COVID);
        details.setCoverageTerm(AppConstants.ONE);
        details.setModalPremium(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getCovideRiderPremium())));
        details.setRiderSumAssured(String.format("%.0f", Utility.roundOffValue(illustrationResponse.getCovideRiderSumAssured())));
        //FUL2-11755 COVID Rider GST: Setting value of COVID GST
		if(("0".equals(illustrationResponse.getCovideRiderGST())) || StringUtils.isEmpty(illustrationResponse.getCovideRiderGST())){
			details.setRiderGST("");
		}else {
			details.setRiderGST(illustrationResponse.getCovideRiderGST());
		}
        details.setPremiumPayingTerm(AppConstants.ONE);
        return details;
    }
    private ProposalRiderDetails setProposalRiderDetailsCiRider(ProductIllustrationResponse illustrationResponse, List<RiderDetails> riderDetails,int riderCount, String riderName){
        ProposalRiderDetails details = new ProposalRiderDetails();
        riderName = getCIRiderVariantName(riderDetails, riderCount, riderName);
        return TraditionalFormUtil.setCIRiderDetails(illustrationResponse, details, riderName);
    }
    private String getCIRiderVariantName(List<RiderDetails> riderDetails, int riderCount, String riderName) {
		String riderVariant = riderDetails.get(riderCount).getRiderVariant();
		//FUL2-30525 CIDR- Rider Name Change for ULIP products
		if(AppConstants.CI_RIDERS.contains(riderName)) {
			riderName = riderName +" - "+ (!StringUtils.isEmpty(riderVariant) ? riderVariant : AppConstants.BLANK);
		}
		return riderName;
	}
    private double setRiderSumAssuredWOP(ProductIllustrationResponse illustrationResponse, double riderSumAssuredWOP) {
		try {
			riderSumAssuredWOP = Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearADDRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearTermPlusRiderPremiumSummary()))
					+ illustrationResponse.getModalPremium()
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearAccidentCoverRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getCABRiderPremium()))
					+ Double.valueOf(zeroIfNullOrEmpty(illustrationResponse.getFirstYearSmartHealthPlusPremiumSummary()));
		} catch (Exception ex) {
			logger.error("Error calculating riderSumAssuredWOP because {}",ex.getMessage());
		}
		return riderSumAssuredWOP;
	}
    private void setRiderPremiumPayingTerm(ProductDetails productDetails, List<ProposalRiderDetails> proposalRiderDetails) {
		proposalRiderDetails.forEach(proposalRiderDetails1 -> {
			if (proposalRiderDetails1.getPremiumPayingTerm().equalsIgnoreCase("NA") && (!proposalRiderDetails1.getRiderName().equalsIgnoreCase("Critical Illness and Disability Rider") || !proposalRiderDetails1.getRiderName().equalsIgnoreCase("COVID 19 One Year Term Rider"))) {
				proposalRiderDetails1.setPremiumPayingTerm(productDetails.getProductInfo().getPremiumPaymentTerm());
			}
		});
	}
    private String getFullAddress(AddressDetails addDetails) {
    	//
    	String address = addDetails.getHouseNo();
    	List<String> addressParts = List.of(addDetails.getArea(), addDetails.getVillage(), addDetails.getCity(), addDetails.getState(), addDetails.getCountry(), addDetails.getPinCode());
    	for(String part : addressParts) {
    		if(!StringUtils.isEmpty(part)) {
    			address = String.join(", ", part);
    		}
    	}
    	return address;
    }

}
