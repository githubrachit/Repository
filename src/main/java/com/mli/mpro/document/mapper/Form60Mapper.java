package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.IdentityProofDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import java.util.Objects;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Form60Mapper {

    private static final Logger logger = LoggerFactory.getLogger(Form60Mapper.class);

    /**
     * Mapping data from Form60 to Context DataMap
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfForm60Document(ProposalDetails proposalDetails, int index) throws UserHandledException {
	logger.info("START Form60 Data population");
	Map<String, Object> dataVariables = new HashMap<>();
	HashMap<String, Object> communicationAddress = new HashMap<>();
	HashMap<String, Object> permanentAddress = new HashMap<>();
	HashMap<String, Object> paymentDetails = new HashMap<>();

	String proposalNumber = "";
	String motherName = "";
	String fatherName = "";
	String nationality = "";
	String occupation = "";
	boolean occupationFlag = false;
	String annualIncome = "";
	String panAcknowledgementNo = "";
	String panApplicationDate = "";
	IdentityProofDetails identityProofDetails = null;
	try {
	    String formType = proposalDetails.getApplicationDetails().getFormType();
	    boolean proposerFormFlag = compareValues(formType, "SELF")  || Utility.schemeBCase(formType, proposalDetails.getApplicationDetails().getSchemeType());
	    proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();

	    List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();

	    logger.info("Initiating processing proposalDetails for Form60 Document...");
	    if (!CollectionUtils.isEmpty(partyInfoList)) {
		BasicDetails basicDetails = partyInfoList.get(index).getBasicDetails();
		PersonalIdentification proposerPersonalIdentification = partyInfoList.get(0).getPersonalIdentification();
		List<Phone> phoneList = proposerPersonalIdentification.getPhone();

		// Setting Communication Address will be same for both annuitants
		if (!CollectionUtils.isEmpty(phoneList)) {
			String isdCode = proposerPersonalIdentification.getPhone().get(0).getStdIsdCode();
			communicationAddress
					.put("mobileNumber1", String.join(Objects.isNull(isdCode) ? Strings.EMPTY : isdCode,
							proposerPersonalIdentification.getPhone().get(0).getPhoneNumber()));

		} else {
		    logger.info("Phone data not found!");
		}
		//FUL2-23133 Address will be same for both annuitants
		permanentAddress = getAddressData(proposalDetails);
		//FUL2-23133 Form 60 basic details mapping for Annuity/Non-Annuity Products
		dataVariables = addBasicDetails(basicDetails,index,dataVariables,proposalDetails);
		motherName = basicDetails.getMotherName();
		fatherName = basicDetails.getFatherName();
		nationality = basicDetails.getNationalityDetails().getNationality();
		occupation = basicDetails.getOccupation();
		occupationFlag = compareValues(basicDetails.getOccupation(), "AGRICULTURE");
		annualIncome = basicDetails.getAnnualIncome();
		identityProofDetails = getIdentityProof(proposalDetails.getForm60Details(),index);
		paymentDetails = getPaymentDetails(proposalDetails.getPaymentDetails().getReceipt().get(0),
			proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse());
	    }
		String addressProofName = null;
		String addressProofNumber = null;
		String addressProofAuthority = null;
		boolean noPanFlag;
		if(index==0) {
			boolean addressProofFlag = proposalDetails.getForm60Details().isAddressProofSame();
			logger.info("Form60 isAddressProofSame: {}", addressProofFlag);
			addressProofName = getNonEmptyValue(BooleanUtils.isTrue(addressProofFlag), proposalDetails.getForm60Details().getIdentityProofName(), proposalDetails.getForm60Details().getAddressProofName());
			addressProofNumber = getNonEmptyValue(BooleanUtils.isTrue(addressProofFlag), proposalDetails.getForm60Details().getIdentityProofNumber(), proposalDetails.getForm60Details().getAddressProofNumber());
			addressProofAuthority = getNonEmptyValue(BooleanUtils.isTrue(addressProofFlag), proposalDetails.getForm60Details().getIdentityProofIssuingAuthority(), proposalDetails.getForm60Details().getAddressProofIssuingAuthority());

			noPanFlag = compareValues(proposalDetails.getForm60Details().getDetailsOfDontHavePan(),
					AppConstants.YES);
			panAcknowledgementNo = getNonEmptyValue(noPanFlag,
					proposalDetails.getForm60Details().getPanAcknowledgementNo(), AppConstants.BLANK);
			panApplicationDate = Utility.dateFormatter(proposalDetails.getForm60Details().getPanApplicationDate());
		}
		if(index==1) {
			boolean annuitantAddressProofFlag = proposalDetails.getForm60Details().isSecondAnnuitantAddressProofSame();
			logger.info("Form60 Annuitant isAddressProofSame: {}", annuitantAddressProofFlag);
			addressProofName = getNonEmptyValue(BooleanUtils.isTrue(annuitantAddressProofFlag), proposalDetails.getForm60Details().getSecondAnnuitantIdentityProof(), proposalDetails.getForm60Details().getSecondAnnuitantAddressProof());
			addressProofNumber = getNonEmptyValue(BooleanUtils.isTrue(annuitantAddressProofFlag), proposalDetails.getForm60Details().getSecondAnnuitantIdentityProofNumber(), proposalDetails.getForm60Details().getSecondAnnuitantAddressProofNumber());
			addressProofAuthority = getNonEmptyValue(BooleanUtils.isTrue(annuitantAddressProofFlag), proposalDetails.getForm60Details().getSecondAnnuitantIdentityProofIssuingAuthority(), proposalDetails.getForm60Details().getSecondAnnuitantAddressProofIssuingAuthority());

			noPanFlag = compareValues(proposalDetails.getForm60Details().getSecondAnnuitantDetailsOfDontHavePan(),
					AppConstants.YES);
			panAcknowledgementNo = getNonEmptyValue(noPanFlag,
					proposalDetails.getForm60Details().getSecondAnnuitantPanAcknowledgementNo(),
					AppConstants.BLANK);
			panApplicationDate =
					Utility.dateFormatter(proposalDetails.getForm60Details().getSecondAnnuitantPanApplicationDate());
		}
		//FUL2-5925 Aadhaar Masking in mPRO
	    if(addressProofName.equalsIgnoreCase(AppConstants.AADHAAR) && StringUtils.isNotBlank(addressProofNumber)) {
	    	addressProofNumber = Utility.maskAadhaarNumber(addressProofNumber);
	    }
	    // Putting data in DataMap
	    dataVariables.put(AppConstants.PANDOB_PROPOSALNUMBER, getNonEmptyValue(StringUtils.isNotBlank(proposalNumber), proposalNumber, AppConstants.BLANK));
	    dataVariables.put("formType", formType);
	    dataVariables.put("proposerFormFlag", proposerFormFlag);
	    dataVariables.put("motherName", motherName);
	    dataVariables.put("fatherName", fatherName);
	    dataVariables.put("nationality", nationality);
	    dataVariables.put("occupation", occupation);
	    dataVariables.put("occupationFlag", occupationFlag);
	    dataVariables.put("panAcknowledgementNo", panAcknowledgementNo);
	    dataVariables.put("panApplicationDate", panApplicationDate);
	    dataVariables.put("annualIncome", annualIncome);
	    dataVariables.put("addressProofName", addressProofName);
	    dataVariables.put("addressProofNumber", addressProofNumber);
	    dataVariables.put("addressProofAuthority", addressProofAuthority);
	    dataVariables.put("communicationAddress", communicationAddress);
	    dataVariables.put("permanentAddress", permanentAddress);
	    dataVariables.put("paymentDetails", paymentDetails);
	    dataVariables.put("identityProofDetails", identityProofDetails);

	} catch (Exception ex) {
	    logger.error("Data addition failed for Form60 Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context form60DetailsCxt = new Context();
	form60DetailsCxt.setVariables(dataVariables);
	logger.info("END Form60 Data population");
	return form60DetailsCxt;
    }

	private HashMap<String, Object> getAddressData(ProposalDetails proposalDetails) {
		HashMap<String, Object> permanentAddress = new HashMap<>();
		List<Address> addressList  = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress();
		if (!CollectionUtils.isEmpty(addressList)) {
			AddressDetails proposerPermAddressDetails = addressList.get(1).getAddressDetails();

			permanentAddress.put("houseNo", proposerPermAddressDetails.getHouseNo());
			permanentAddress.put("area", proposerPermAddressDetails.getArea());
			permanentAddress.put("landmark", proposerPermAddressDetails.getLandmark());
			permanentAddress.put("city", proposerPermAddressDetails.getCity());
			permanentAddress.put("state", proposerPermAddressDetails.getState());
			permanentAddress.put("pinCode", proposerPermAddressDetails.getPinCode());
			permanentAddress.put("country", proposerPermAddressDetails.getCountry());
		} else {
			logger.info("Permanent Address data not found!");
		}
		return permanentAddress;
	}

	private Map<String, Object> addBasicDetails(BasicDetails basicDetails, int index, Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
		String title = "";
		String firstName = "";
		String middleName = "";
		String lastName = "";
		String dob = "";
		if (index == 0) {
			title = Utility.getTitle(basicDetails.getGender());
			firstName = basicDetails.getFirstName();
			middleName = basicDetails.getMiddleName();
			lastName = basicDetails.getLastName();
			dob = Utility.dateFormatter(basicDetails.getDob());
		} else if (index == 1) {
			//FUL2-23133 Get second annuitant details from ProductInfo
			ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
			title = Utility.getTitle(productInfo.getSecondAnnuitantSex());
			String[] name= productInfo.getSecondAnnuitantName().split(" ");
			firstName =name[0];
			if (name.length > 2) {
				middleName = name[1];
				lastName = name[2];
			} else if (name.length > 1) {
				lastName = name[1];
			}
			dob = Utility.stringAnnuityFormDateFormatter(productInfo.getSecondAnnuitantDob(),proposalDetails.getChannelDetails().getChannel());
		}
		dataVariables.put("title", title);
		dataVariables.put("firstName", firstName);
		dataVariables.put("middleName", middleName);
		dataVariables.put("lastName", lastName);
		dataVariables.put("dob", dob);
		return dataVariables;
    }

	private String getNonEmptyValue(boolean flag, String value, String blankValue) {
		return flag ? value : blankValue;
	}

	private boolean compareValues(String value1, String value2) {
		return StringUtils.equalsIgnoreCase(value1, value2);
	}

    /**
     * Setting ID Proof Data
     *
     * @param form60Details
     * @return
     */
	private static IdentityProofDetails getIdentityProof(Form60Details form60Details, int index) {
		logger.info("Fetching Identity Proof, setting the extracted data in IdentityProofDetails");
		IdentityProofDetails proofDetails = new IdentityProofDetails();
		String proofName;
		String proofNumber;
		if (index == 0) {
			proofName = form60Details.getIdentityProofName();
			proofNumber = form60Details.getIdentityProofNumber();
			if (proofName.equalsIgnoreCase(AppConstants.AADHAAR) && StringUtils.isNotBlank(proofNumber)) {
				proofNumber = Utility.maskAadhaarNumber(proofNumber);
				proofDetails.setProofNumber(proofNumber);
			} else {
				proofDetails.setProofNumber(proofNumber);
			}
			proofDetails.setProofName(proofName);
			proofDetails.setProofExpiryDate(form60Details.getIdentityProofExpiryDate());
			proofDetails.setIdProofAuthority(form60Details.getIdentityProofIssuingAuthority());
		}else if(index == 1){
			proofName = form60Details.getSecondAnnuitantIdentityProof();
			proofNumber = form60Details.getSecondAnnuitantIdentityProofNumber();
			if (proofName.equalsIgnoreCase(AppConstants.AADHAAR) && StringUtils.isNotBlank(proofNumber)) {
				proofNumber = Utility.maskAadhaarNumber(proofNumber);
				proofDetails.setProofNumber(proofNumber);
			} else {
				proofDetails.setProofNumber(proofNumber);
			}
			proofDetails.setProofName(proofName);
			proofDetails.setProofExpiryDate(String.valueOf(form60Details.getSecondAnnuitantIdentityProofExpiryDate()));
			proofDetails.setIdProofAuthority(form60Details.getSecondAnnuitantIdentityProofIssuingAuthority());
		}
		return proofDetails;
	}

    /**
     * Setting Payment Details in Map
     *
     * @param receipt
     * @param productIllustrationResponse
     * @return
     */
    private static HashMap<String, Object> getPaymentDetails(Receipt receipt, ProductIllustrationResponse productIllustrationResponse) {
	HashMap<String, Object> paymentDetails = new HashMap<>();
	String amount = "";
	String paymentDate = "";
	String paymentMode = "";
	String transactionNumber = "";
	String bankName = "";

	try {
	    if (StringUtils.equalsIgnoreCase(receipt.getPremiumMode(), AppConstants.ONLINE)) {
			amount = productIllustrationResponse.getInitialPremiumPaid();
		    paymentDate = receipt.getTransactionDateTimeStamp();
		    paymentMode = "ONLINE";
		    transactionNumber = receipt.getTransactionReferenceNumber();
		    bankName = AppConstants.NA;
		} else if (StringUtils.equalsIgnoreCase(receipt.getPremiumMode(), AppConstants.CHEQUE)) {
			amount = productIllustrationResponse.getInitialPremiumPaid();
		    paymentDate = Utility.dateFormatter(receipt.getPaymentChequeDetails().getChequeDate());
		    paymentMode = "CHEQUE";
		    transactionNumber = receipt.getPaymentChequeDetails().getChequeNumber() + "";
		    bankName = receipt.getPaymentChequeDetails().getChequeBankName();
		} else if (StringUtils.equalsIgnoreCase(receipt.getPremiumMode(), AppConstants.DEMAND_DRAFT)) {
			amount = productIllustrationResponse.getInitialPremiumPaid();
		    paymentDate = Utility.dateFormatter(receipt.getDemandDraftDetails().getDemandDraftDate());
		    paymentMode = "DEMAND DRAFT";
		    transactionNumber = receipt.getDemandDraftDetails().getDemandDraftNumber();
		    bankName = receipt.getDemandDraftDetails().getDemandDraftBankName();
		} else if (StringUtils.equalsIgnoreCase(receipt.getPremiumMode(), AppConstants.DIRECTDEBIT) || StringUtils.equalsIgnoreCase(receipt.getPremiumMode(), AppConstants.DIRECTDEBITWITHRENEWALS)) {
			amount = productIllustrationResponse.getInitialPremiumPaid();
		    paymentDate = Utility.dateFormatter(receipt.getDirectPaymentDetails().getVoucherUpdatedDate());
		    paymentMode = "DIRECT DEBIT";
		    transactionNumber = receipt.getDirectPaymentDetails().getvoucherNumber();
		}
	}
	catch(Exception ex) {
	    logger.error("Error setting payment details in Form60:",ex);
	}
	paymentDetails.put("amount", amount);
	paymentDetails.put("paymentDate", paymentDate);
	paymentDetails.put("paymentMode", paymentMode);
	paymentDetails.put("transactionNumber", transactionNumber);
	paymentDetails.put("bankName", bankName);
	return paymentDetails;
    }
}
