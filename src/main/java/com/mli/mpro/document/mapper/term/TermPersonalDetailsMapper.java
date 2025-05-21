package com.mli.mpro.document.mapper.term;

import com.google.common.base.Strings;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.ProposalNomineeDetails;
import com.mli.mpro.document.service.impl.BaseMapper;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.DEFENCE;
import static com.mli.mpro.productRestriction.util.AppConstants.FORM3;
import static com.mli.mpro.productRestriction.util.AppConstants.INSURED;
import static com.mli.mpro.productRestriction.util.AppConstants.OTHERS;
import static com.mli.mpro.productRestriction.util.AppConstants.PROPOSER;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Service
public class TermPersonalDetailsMapper {

	private static final Logger logger = LoggerFactory.getLogger(TermPersonalDetailsMapper.class);
    private static final String INDIAN = "Indian";
	public static final String DD_MM_YYYY_CONST = "dd/MM/yyyy";
	public static final String DD_MM_YYYY_HYPEN_FORMAT = "dd-MM-yyyy";
	private boolean isNeoOrAggregator = false;
    @Autowired
    private BaseMapper baseMapper;

    private Map<String,String> insuranceRepositoryMap = new HashMap<>();

    public TermPersonalDetailsMapper(){
		insuranceRepositoryMap.put("1","NSDL Database Management Limited");
		insuranceRepositoryMap.put("2","Central Insurance Repository Limited");
		insuranceRepositoryMap.put("3","SHCIL Projects Limited");
		insuranceRepositoryMap.put("4","Karvy Insurance Repository Limited");
		insuranceRepositoryMap.put("5","CAMS Repository Services Limited");
	}

    //constants
    private static final String MARITALSTATUS = "maritalStatus";
	private static final String TRANSGENDER = "Transgender";
	private static final String OTHER = "Other";
	private static final String OCCUPATION = "occcupation";
    private static final String ANNUAL_INCOME = "annualIncome";
    private static final String NRI_DETAILS = "nriDetails";
    private static final String EMPLOYER_INFO = "employerInfo";
    private static final String NATURE_OF_JOB = "natureOfJob";
    private static final String ORGANIZATION_TYPE = "organizationType";
    private static final String EDUCATION = "education";

    private static final String CHILD_DETAILS = "childDetailRequirement";
    private static final String CHILD_DOB = "childDOB";
    private static final String CHILD_NAME = "childName";
    private static final String IS_NOMINEE = "isNominee";
    private static final String FIRSTNAME = "firstName";
    private static final String MIDDLENAME = "middleName";
   private static final String HIGH_SCHOOL = "High School";
    private static final String TENTH_PASS = "10th pass";
    private static final String SENIOR_SCHOOL = "Senior School";
    private static final String TENTH_PLUS_TWO_PASS = "10+2 Pass";

    public Context setDataForPersonalDetails(ProposalDetails proposalDetails) throws UserHandledException {

	Map<String, Object> dataVariables = new HashMap<>();
	logger.info("Mapping person details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	try {
        //NEORW-173: this will check that incoming request is from NEO or Aggregator
        if (Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getChannelDetails())) {
            isNeoOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                    || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
                    ? true : false;
        }
        String channel = proposalDetails.getChannelDetails().getChannel();
        String formType = proposalDetails.getApplicationDetails().getFormType();
        boolean isForm2 = formType.equals(AppConstants.DEPENDENT) || formType.equals(AppConstants.FORM2);
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		Boolean schemeBCase = Utility.schemeBCase(formType,schemeType);
		Boolean isSwpWlI = AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant());
		//FUL2-19322 boolean flag to check is product is Smart Secure Plus
		boolean isSSP = AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId());
        BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
        //NEWRW: handle null check for insuredBasicDetails
        BasicDetails insuredBasicDetails = proposalDetails.getPartyInformation().size() > 1
                ? proposalDetails.getPartyInformation().get(1).getBasicDetails() : null;
        EmploymentDetails proposerEmploymentDetails = proposalDetails.getEmploymentDetails();
        String firstName = proposerBasicDetails.getFirstName();
        String middleName = proposerBasicDetails.getMiddleName();
        String lastName = proposerBasicDetails.getLastName();
        String gender = proposerBasicDetails.getGender();
        String formattedGender = Utility.getGender(gender);
		//FUL2-147320
		String productType = proposalDetails.getProductDetails().get(0).getProductType();
		String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();

        if(AppConstants.OTHER.equalsIgnoreCase(formattedGender)){
			formattedGender = getGenderTrad(proposalDetails, formattedGender);
		}
		String title = Utility.getTitle(gender);
        Date dob = proposerBasicDetails.getDob();
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String formattedDob = "";
        if(!StringUtils.isEmpty(dob)) {
        formattedDob = isNeoOrAggregator ? Utility.dateFormatter(formatter.format(dob), DD_MM_YYYY_HYPEN_FORMAT, DD_MM_YYYY_CONST) : formatter.format(dob);
        }
        String proposerfatherName = proposerBasicDetails.getFatherName();
        String fatherMiddleName = "";
        String fatherFirstName = proposerfatherName;
        if (proposerfatherName.contains(" ")) {
            fatherFirstName = proposerfatherName.substring(0, proposerfatherName.indexOf(' '));
            fatherMiddleName = proposerfatherName.substring(proposerfatherName.indexOf(' '), proposerfatherName.length());
        }
            String nationality = Objects.nonNull(proposerBasicDetails.getNationalityDetails())
                    ? Utility.nullSafe(proposerBasicDetails.getNationalityDetails().getNationality())
                : AppConstants.BLANK;
            String maritialStatus = Objects.nonNull(proposerBasicDetails.getMarriageDetails())
                    ? Utility.nullSafe(proposerBasicDetails.getMarriageDetails().getMaritalStatus())
                : AppConstants.BLANK;
		//FUL2-116428 NPS via PRAN
		String education="";
		if(npsJourneyCheck(proposalDetails)){
			education=AppConstants.NA;
		}
		else {
			education = Utility.nullSafe(proposerBasicDetails.getEducation());
		}
		if (isNeoOrAggregator) {
			education = settingValueOfEducation(education);
		}

        String email = "";
        String mobileNumber1 = "";
        String mobileNumber2 = "";
        String stdCode = "";
        String landLineNumber = "";
        String houseNo = "";
        String area = "";
        String village = "";
        String landmark = "";
        String city = "";
        String pinCode = "";
        String state = "";
        String country = "";
        String permhouseNo = "";
        String permarea = "";
        String permvillage = "";
        String permlandmark = "";
        String permcity = "";
        String permpinCode = "";
        String permstate = "";
        String permcountry = "";
        String insuredfirstName = "";
        String insuredmiddleName = "";
        String insuredlastName = "";
        String insuredgender = "";
        String insuredformattedGender = "";
        String insuredtitle = "";
        Date insureddob = null;
        String insuredformattedDob = "";
        String insurednationality = "";
        String insuredfatherName = "";
        String insuredfatherMiddleName = "";
        String insuredfatherFirstName = "";
        String proposerRelation = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO) ? "Self" : "SELF";
        String insuredRelation = "";
        String insuredmaritialStatus = "";
        String insurededucation = "";
        String insuredorganisationType = "";
        String insuredoccupation = "";
        String insuredannualIncome = "";
        String insuredemployerName = "";
        String insurednatureOfDuties = "";
        String insuredindustryType = "";
        String insuredCountryOfResidence = "";
        String insuredindustryTypeOthers = "";
        String residenceForTax = Objects.nonNull(proposerBasicDetails.getResidenceForTax()) ? proposerBasicDetails.getResidenceForTax() : StringUtil.EMPTY_STRING;
				boolean isProposerPresent = false;
				if (isNeoOrAggregator
						&& (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)
						      || Utility.isSSPJLProduct(proposalDetails)) || Utility.isProductSGPPJL(proposalDetails)) {
					isProposerPresent = proposalDetails.getPartyInformation().stream().anyMatch(
							partyInformation -> partyInformation.getPartyType().equalsIgnoreCase("PROPOSER"));
				}
        if ((AppConstants.DEPENDENT.equalsIgnoreCase(formType) || isProposerPresent || (proposalDetails.getProductDetails() != null
				&& !proposalDetails.getProductDetails().isEmpty()
				&& Utility.isSSPJointLife(proposalDetails.getProductDetails().get(0)))
				|| (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)))
				&& Objects.nonNull(insuredBasicDetails)) {

            insuredfirstName = insuredBasicDetails.getFirstName();
            insuredmiddleName = insuredBasicDetails.getMiddleName();
            insuredlastName = insuredBasicDetails.getLastName();
            insureddob = insuredBasicDetails.getDob();
            insuredformattedDob = isNeoOrAggregator ? Utility.dateFormatter(formatter.format(insureddob), DD_MM_YYYY_HYPEN_FORMAT, DD_MM_YYYY_CONST) : formatter.format(insureddob);
            insuredgender = insuredBasicDetails.getGender();
            insurednationality = insuredBasicDetails.getNationalityDetails().getNationality();
            setDataForIndustryInsured(dataVariables,
                    proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getPartyDetails().getIndustryDetails());
			if (AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant())) {
				setDataForIndustryProposer(dataVariables,
						proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getIndustryDetails(), true);
			}
            insuredRelation = insuredBasicDetails.getRelationshipWithProposer();
            proposerRelation = "NA";
            insuredfatherName = insuredBasicDetails.getFatherName();
            insuredfatherFirstName = getName(insuredfatherName,FIRSTNAME);
            insuredfatherMiddleName = getName(insuredfatherName,MIDDLENAME);

            insuredformattedGender = Utility.getGender(insuredgender);
			if(AppConstants.OTHER.equalsIgnoreCase(insuredformattedGender)){
				insuredformattedGender = getGenderTrad(proposalDetails,insuredformattedGender);
			}
            insuredtitle = Utility.getTitle(insuredgender);
            insuredmaritialStatus = checkIfValueIsPresent(insuredBasicDetails,MARITALSTATUS);
            insurededucation = checkIfValueIsPresent(insuredBasicDetails,EDUCATION);
            if(isNeoOrAggregator){
            	insurededucation = settingValueOfEducation(insurededucation);
				insuredindustryTypeOthers=proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getIndustryDetails().getIndustryTypeOthers();
			}
            insuredorganisationType = checkIfValueIsPresent(proposerEmploymentDetails,ORGANIZATION_TYPE);
            insuredoccupation = checkIfValueIsPresent(insuredBasicDetails,OCCUPATION);
            insuredannualIncome = checkIfValueIsPresent(insuredBasicDetails,ANNUAL_INCOME);
            insuredemployerName = checkIfValueIsPresent(proposerEmploymentDetails,EMPLOYER_INFO);
            insurednatureOfDuties = checkIfValueIsPresent(proposerEmploymentDetails,NATURE_OF_JOB);
            insuredindustryType = proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getIndustryDetails().getIndustryType();
            insuredCountryOfResidence = checkIfValueIsPresent(insuredBasicDetails,NRI_DETAILS);
			Utility.setInsuredContactDetails(proposalDetails, dataVariables);
        }
        //In case of GLIP Product & SPP Product FUL2- 20134 joint life Annuity Option //made Changes for Proposal form for ALL Annuity products
		if (AppConstants.ANNUITY_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
				&& proposalDetails.getProductDetails().get(0).getProductInfo().getAnnuityOption() != null
				&& proposalDetails.getProductDetails().get(0).getProductInfo().getAnnuityOption().equalsIgnoreCase(AppConstants.JOINT_LIFE_ANNUITY_OPTION)) {
			ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
			String[] name= productInfo.getSecondAnnuitantName().split(" ");
			insuredfirstName =name[0];
			if (name.length > 2) {
				insuredmiddleName = name[1];
				insuredlastName = name[2];
			} else if (name.length > 1) {
				insuredlastName = name[1];
			}
			insuredfatherName = insuredBasicDetails.getFatherName();
			insuredfatherFirstName = getName(insuredfatherName,FIRSTNAME);
			insuredfatherMiddleName = getName(insuredfatherName,MIDDLENAME);
			insuredformattedDob = Utility.stringAnnuityFormDateFormatter(productInfo.getSecondAnnuitantDob(),proposalDetails.getChannelDetails().getChannel());			insurednationality = insuredBasicDetails.getNationalityDetails().getNationality();
			setDataForIndustryInsured(dataVariables,
					proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getPartyDetails().getIndustryDetails());
			if (AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant())) {
				setDataForIndustryProposer(dataVariables,
						proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getIndustryDetails(), true);
			}
			insuredRelation = productInfo.getSecondAnnuitantRelationship();
			proposerRelation = "NA";
			insuredtitle = Utility.getTitle(productInfo.getSecondAnnuitantSex());
			insuredmaritialStatus = checkIfValueIsPresent(insuredBasicDetails,MARITALSTATUS);
			insuredoccupation = checkIfValueIsPresent(insuredBasicDetails,OCCUPATION);
			insuredannualIncome = checkIfValueIsPresent(insuredBasicDetails,ANNUAL_INCOME);
			insuredformattedGender = Utility.getGender(productInfo.getSecondAnnuitantSex());
			if (insuredformattedGender.equalsIgnoreCase(OTHER))
				insuredformattedGender = TRANSGENDER;
			insurededucation = checkIfValueIsPresent(insuredBasicDetails,EDUCATION);
			insuredorganisationType = checkIfValueIsPresent(proposerEmploymentDetails,ORGANIZATION_TYPE);
			insuredemployerName = checkIfValueIsPresent(proposerEmploymentDetails,EMPLOYER_INFO);
			insuredindustryType = proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getIndustryDetails().getIndustryType();
			insuredCountryOfResidence = checkIfValueIsPresent(insuredBasicDetails,NRI_DETAILS);
		}
		if (isNeoOrAggregator && (Utility.isSSPJLProduct(proposalDetails) || Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails))) {
			if (Objects.nonNull(insuredBasicDetails)) {
				proposerRelation = Utility.nullSafe(proposerBasicDetails.getRelationshipWithProposer());
			}
			if (Objects.nonNull(proposerBasicDetails)) {
				insuredRelation = Utility.nullSafe(insuredBasicDetails.getRelationshipWithProposer());
			}
		}
		String industryType = "";
		String industryTypeOthers = "";
		String organisationType = "";
		String annualIncome = "";
		String occupation = "";
		String employerName = "";
		String natureOfDuties = "";

		if (Objects.nonNull(proposerEmploymentDetails.getPartiesInformation()) && !proposerEmploymentDetails.getPartiesInformation().isEmpty()) {
			industryType = proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getIndustryDetails().getIndustryType();
			industryTypeOthers = Utility.nullSafe(proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getIndustryDetails().getIndustryTypeOthers());
			organisationType = proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getOrganisationType();
			if (isNeoOrAggregator && (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails))) {
				insuredannualIncome = proposerEmploymentDetails.getPartiesInformation().get(1)
						.getBasicDetails().getAnnualIncome();
				insuredoccupation = Stream.of(
						Utility.nullSafe(proposerEmploymentDetails.getPartiesInformation().get(1).getBasicDetails()
										.getOccupation()),
						Utility.nullSafe(proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails()
										.getJobTitle()))
						.filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" / "));
				insuredemployerName = proposerEmploymentDetails.getPartiesInformation().get(1)
						.getPartyDetails().getNameOfEmployer();
			}
			annualIncome = isNeoOrAggregator ?
					proposerEmploymentDetails.getPartiesInformation().get(0).getBasicDetails().getAnnualIncome() : proposerBasicDetails.getAnnualIncome();
			occupation = isNeoOrAggregator ?
					Stream.of(
							Utility.nullSafe(proposerEmploymentDetails.getPartiesInformation().get(0).getBasicDetails().getOccupation()),
							Utility.nullSafe(proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getJobTitle()))
							.filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" / "))
					: proposerBasicDetails.getOccupation();
			employerName = proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getNameOfEmployer();
			natureOfDuties = proposerEmploymentDetails.getPartiesInformation().get(0).getPartyDetails().getSpecifyDutiesType();
		}
        String proposerCountryOfResidence = "";
        // set residing country if nationality is Indian
        if (isNeoOrAggregator && INDIAN.equalsIgnoreCase(proposerBasicDetails.getNationalityDetails().getNationality())) {
            proposerCountryOfResidence = "India";
        } else {
            proposerCountryOfResidence = StringUtils.isEmpty(proposerBasicDetails.getNationalityDetails().getNriDetails()) ? ""
                    : proposerBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence();
        }

        List<Address> addressList = proposerBasicDetails.getAddress();
	    if (!(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) && null != addressList && !CollectionUtils.isEmpty(addressList) && addressList.size() >= 1) {
		AddressDetails commAddress = addressList.get(0).getAddressDetails();
		houseNo = commAddress.getHouseNo();
		area = commAddress.getArea();
		village = commAddress.getVillage();
		landmark = commAddress.getLandmark();
		city = commAddress.getCity();
		pinCode = commAddress.getPinCode();
		state = commAddress.getState();
		country = commAddress.getCountry();
	    }

	    if (!(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) && null != addressList && !CollectionUtils.isEmpty(addressList) && addressList.size() >= 2) {
		AddressDetails permAddress = proposerBasicDetails.getAddress().get(1).getAddressDetails();
		permhouseNo = permAddress.getHouseNo();
		permarea = permAddress.getArea();
		permvillage = permAddress.getVillage();
		permlandmark = permAddress.getLandmark();
		permcity = permAddress.getCity();
		permpinCode = permAddress.getPinCode();
		permstate = permAddress.getState();
		permcountry = permAddress.getCountry();
	    }

	    String languageOfCommunication = proposerBasicDetails.getPreferredLanguageOfCommunication();

	    List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();

		// FUL2-156443- Populating incorrectly nameOfEmployer value
		if(StringUtils.hasLength(partyInfoList.get(0).getBSE500Company()) && partyInfoList.get(0).getBSE500Company()!=null && StringUtils.isEmpty(employerName)){
			if(AppConstants.OTHER.equalsIgnoreCase(partyInfoList.get(0).getBSE500Company()) && partyInfoList.get(0).getBasicDetails().getCompanyName() !=null && !StringUtils.isEmpty(partyInfoList.get(0).getBasicDetails().getCompanyName())){
					employerName=partyInfoList.get(0).getBasicDetails().getCompanyName();}
				else {  employerName= partyInfoList.get(0).getBSE500Company();}
		}

        setPhoneNumberDetails(dataVariables,partyInfoList,employerName);
        String exitingEIA = "";
	    String existingRepoName = "";
	    String wishToHoldEia = "";
		String eiaExist = "";
		String accountNumber = "";
		String preferredInsuranceRepository = "";

	    // set data related to EIA
		if (Objects.nonNull(proposerEmploymentDetails)) {
			if (isNeoOrAggregator) {
				wishToHoldEia = Utility.convertToYesOrNo(proposerEmploymentDetails.getWishToHoldEIAAccount());
				exitingEIA = StringUtils.hasLength(proposerEmploymentDetails.getExistingEIANumber())
						? proposerEmploymentDetails.getExistingEIANumber() : AppConstants.NO;
				existingRepoName = StringUtils.hasLength(proposerEmploymentDetails.getExistingEIANumberRepositoryName())
						? Utility.getFullRepositoryName(proposerEmploymentDetails.getExistingEIANumberRepositoryName()) : AppConstants.BLANK;
			} else {

				exitingEIA = StringUtils.hasLength(proposerEmploymentDetails.getExistingEIANumber()) ? AppConstants.YES : AppConstants.NO;
			}
			if (AppConstants.THANOS_CHANNEL
					.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
				eiaExist = (!isEmpty(proposerEmploymentDetails.getExistingEIANumber())
						|| !isEmpty(
						proposerEmploymentDetails.getPreferredInsuranceRepositoryName()))
						? AppConstants.YES : AppConstants.NO;
			} else {
				eiaExist =
						(proposerEmploymentDetails.isEIAExist() && nationality.equalsIgnoreCase(INDIAN))
								? AppConstants.YES : AppConstants.NO;
			}
			accountNumber = proposerEmploymentDetails.getExistingEIANumber();
			preferredInsuranceRepository = StringUtils.hasLength(proposerEmploymentDetails.getPreferredInsuranceRepositoryName()) ? AppConstants.YES : AppConstants.NO;
		}
		setNatureOfDutiesData(proposalDetails, dataVariables);
	    List<PartyDetails> nomineeDetails = new ArrayList<>();
		if(Objects.nonNull(proposalDetails.getNomineeDetails())) {
			nomineeDetails.addAll(proposalDetails.getNomineeDetails().getPartyDetails());
		}
	    List<ProposalNomineeDetails> proposalNomineeDetails = new ArrayList<>();
	    ProposalNomineeDetails details = null;
		//condition -- to validating nominee details--
		/* FUL2-157003 NOMINEE DETAILS ARE COMING ON PROPOSAL FORM IN FORM -C*/
		if (Objects.nonNull(proposalDetails.getNomineeDetails()) && nomineeDetails.size() == 1){
			PartyDetails nominee = nomineeDetails.get(0);
			if (null == nominee.getFirstName() && null == nominee.getDob() && null == nominee.getGender() ) {
				nomineeDetails.clear();
			}
		}
	    for (int i = 0; i < nomineeDetails.size(); i++) {
	    if(isForm2) { // in case for form2 one nominee object is sent from node with empty values
	    	details = new ProposalNomineeDetails("NA");
	    	proposalNomineeDetails.add(details);
	    	break;
	    }
		details = new ProposalNomineeDetails();
		PartyDetails nominee = nomineeDetails.get(i);
		String nomineeDob = Utility.dateFormatter(nominee.getDob());
		details.setDob(isNeoOrAggregator ? Utility.dateFormatter(nomineeDob, DD_MM_YYYY_HYPEN_FORMAT, DD_MM_YYYY_CONST) : nomineeDob);
		details.setFirstName(nominee.getFirstName());
		details.setLastName(nominee.getLastName());
		details.setMiddleName(nominee.getMiddleName());
		if (StringUtils.hasLength(nominee.getFatherOrHusbandName()) && nominee.getFatherOrHusbandName().contains(" ")) {
			details.setFatherFirstName(nominee.getFatherOrHusbandName().substring(0, nominee.getFatherOrHusbandName().indexOf(AppConstants.WHITE_SPACE)));
			details.setFatherLastName(nominee.getFatherOrHusbandName().substring(nominee.getFatherOrHusbandName().indexOf(AppConstants.WHITE_SPACE)+1));
		} else {
			details.setFatherFirstName(nominee.getFatherOrHusbandName());
		}
		details.setPercentageShare((nomineeDetails.size() == 1) ? 100 : nominee.getPercentageShare());
		details.setGender(Utility.getGender(nominee.getGender()));
		if(AppConstants.OTHER.equalsIgnoreCase(details.getGender())){
			details.setGender(getGenderTrad(proposalDetails,AppConstants.OTHER));
		}
		details.setTitle(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel) ? nominee.getSalutation() : Utility.getTitle(nominee.getGender()));
		details.setRelationshipWithProposer(nominee.getRelationshipWithProposer());
        //FUL2-19322 set Gender of Guardian when nominee is minor and Plan is Smart secure Plus
		//FUL2-144668
		if (nominee != null && nominee.getAppointeeDetails() != null ) {
			if(nominee.getAppointeeDetails().getAppointeeDOB() !=null && nominee.getAppointeeDetails().getAppointeeDOB().toString().isEmpty()) {
				details.setAppointeeDOB(nominee.getAppointeeDetails().getAppointeeDOB().toString());

			} else {
				details.setAppointeeDOB(AppConstants.NA);
			}
			if (!StringUtil.isNullOrEmpty(nominee.getAppointeeDetails().getAppointeeGender())) {
				if (AppConstants.OTHER.equalsIgnoreCase(nominee.getAppointeeDetails().getAppointeeGender()) || AppConstants.OTHERS.equalsIgnoreCase(nominee.getAppointeeDetails().getAppointeeGender())) {

					details.setGuardianGender(getGenderTrad(proposalDetails, AppConstants.OTHER));
				} else if (AppConstants.MALE.equalsIgnoreCase(nominee.getAppointeeDetails().getAppointeeGender())) {
					details.setGuardianGender("MALE");
				}
				else if(AppConstants.FEMALE.equalsIgnoreCase(nominee.getAppointeeDetails().getAppointeeGender())){
					details.setGuardianGender("FEMALE");
				}
			}
			else{
				details.setGuardianGender(AppConstants.NA);
			}
		} else {
			details.setAppointeeDOB(AppConstants.NA);
			details.setGuardianGender(AppConstants.NA);
		}
		setNomineeRelatedInfo(details,nominee,channel);
		if (AppConstants.THANOS_CHANNEL
						.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
					setNomineeRelatedInfoForThanos(details, nominee);
		} else {
					setNomineeRelatedInfo(details, nominee,channel);
				}
		proposalNomineeDetails.add(details);
	    }
	    // setting default value "NA" for rest of the mandatory nominee columns
		if (isTraditionalProposalForm(productType, productId)) {
			for (int i = nomineeDetails.size(); i < 3; i++) {
				details = new ProposalNomineeDetails("NA");
				proposalNomineeDetails.add(details);
			}
		}

		if (AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())) {
			dataVariables.put("proposerAnnualIncome", proposerBasicDetails.getAnnualIncome());
			dataVariables.put("proposerOccupation", proposerBasicDetails.getOccupation());
		}

	    String isChildDetailRequired = checkingDataVariablesNullChecks(productId,CHILD_DETAILS, schemeType);
		String childDob = null;
		String childName = null;

		if(Objects.nonNull(proposalDetails.getNomineeDetails())) {
			childDob = Utility.dateFormatter(proposalDetails.getNomineeDetails().getPartyDetails().get(0).getNomineeChildDob());
			childName = proposalDetails.getNomineeDetails().getPartyDetails().get(0).getNomineeChildName();
		}
	    
	    //FUL2-27084 Proposal form changes for Form C
		if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			BasicDetails companyBasicDetails = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
					.filter(Objects::nonNull).map(PartyInformation::getBasicDetails).findFirst()
					.orElse(new BasicDetails());

			PersonalIdentification authorizedSignatoryPersonalInfo = proposalDetails.getPartyInformation().stream()
					.filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.AUTHORIZED_SIGNATORY.equalsIgnoreCase(partyInfo.getPartyType()))
					.filter(Objects::nonNull).map(PartyInformation::getPersonalIdentification).filter(Objects::nonNull)
					.findFirst().orElse(new PersonalIdentification());

			BasicDetails formCInsuredBasicDetails = proposalDetails.getPartyInformation().stream()
					.filter(Objects::nonNull)
					.filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()))
					.filter(Objects::nonNull).map(PartyInformation::getBasicDetails).findFirst()
					.orElse(new BasicDetails());

			title = "M/s";
			firstName = companyBasicDetails.getFirstName();
			dob = companyBasicDetails.getDateOfIncorporation();
			if(!StringUtils.isEmpty(dob))
				formattedDob = formatter.format(dob);
			formattedGender = "";
			nationality = INDIAN;
			annualIncome = companyBasicDetails.getAnnualIncome();
			List<Address> companyAddressList = companyBasicDetails.getAddress();
			if (Objects.nonNull(companyAddressList) && !CollectionUtils.isEmpty(companyAddressList)) {
				AddressDetails commAddress = companyAddressList.get(1).getAddressDetails();
				houseNo = commAddress.getHouseNo();
				area = commAddress.getArea();
				village = commAddress.getVillage();
				landmark = commAddress.getLandmark();
				city = commAddress.getCity();
				pinCode = commAddress.getPinCode();
				state = commAddress.getState();
				country = commAddress.getCountry();
			}
			List<Address> insuredAddressDetails = formCInsuredBasicDetails.getAddress();
			if (Objects.nonNull(insuredAddressDetails) && !CollectionUtils.isEmpty(insuredAddressDetails)) {
				AddressDetails permAddress = insuredAddressDetails.get(1).getAddressDetails();
				permhouseNo = permAddress.getHouseNo();
				permarea = permAddress.getArea();
				permvillage = permAddress.getVillage();
				permlandmark = permAddress.getLandmark();
				permcity = permAddress.getCity();
				permpinCode = permAddress.getPinCode();
				permstate = permAddress.getState();
				permcountry = permAddress.getCountry();
			}
			dataVariables.put("email", authorizedSignatoryPersonalInfo.getEmail());
			List<Phone> phoneList = authorizedSignatoryPersonalInfo.getPhone();
			if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && !phoneList.isEmpty()) {
				dataVariables.put("mobileNumber", phoneList.get(0).getPhoneNumber());
			}
			if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 2) {
				dataVariables.put("alternate", phoneList.get(1).getPhoneNumber());
			}
			if (Objects.nonNull(phoneList) && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 3) {
				dataVariables.put("std", phoneList.get(2).getStdIsdCode());
				dataVariables.put("landline", phoneList.get(2).getPhoneNumber());
			}
			eiaExist = (proposerEmploymentDetails.isEIAExist() && nationality.equalsIgnoreCase(INDIAN))
					? AppConstants.YES
					: AppConstants.NO;
		}
		String customerReminderConsent= Utility.customerReminderConsent(proposalDetails);
	    
	    dataVariables.put("formType", formType.toUpperCase());
	    dataVariables.put("swpWli", isSwpWlI);
		dataVariables.put("schemeBCase", schemeBCase);
	    dataVariables.put("title", title);
	    dataVariables.put(FIRSTNAME, firstName);
	    dataVariables.put("lastName", lastName);
	    dataVariables.put(MIDDLENAME, middleName);
	    dataVariables.put("fatherFirstName", fatherFirstName);
	    dataVariables.put("fatherLastName", fatherMiddleName);
	    dataVariables.put("gender", formattedGender);
	    dataVariables.put("dob", formattedDob);
	    if(proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.THANOS_CHANNEL)){
				dataVariables.put("nationality", nationality.toLowerCase());
			}
	    else{
				dataVariables.put("nationality", nationality);
			}
	    dataVariables.put(EDUCATION, education);
	    dataVariables.put("maritialStatus", maritialStatus);
	    dataVariables.put("organisationType", organisationType);
	    dataVariables.put("occupation", occupation);
	    dataVariables.put("nameOfEmployer", employerName);
	    dataVariables.put("natureOfDuties", natureOfDuties);
        dataVariables.put(ANNUAL_INCOME, annualIncome);
	    dataVariables.put(AppConstants.INDUSTRY_TYPE,
		    org.apache.commons.lang3.StringUtils.isNotBlank(industryType) ? org.apache.commons.lang3.StringUtils.upperCase(industryType)
			    : AppConstants.BLANK);
	    dataVariables.put("industryTypeOthers", industryTypeOthers);
	    dataVariables.put("insuredindustryType", org.apache.commons.lang3.StringUtils.isBlank(insuredindustryType) ? AppConstants.BLANK
		    : org.apache.commons.lang3.StringUtils.upperCase(insuredindustryType));

	    dataVariables.put("houseNumber", houseNo);
	    dataVariables.put("area", area);
	    dataVariables.put("village", village);
	    dataVariables.put("landmark", landmark);
	    dataVariables.put("city", city);
	    dataVariables.put("pinCode", pinCode);
	    dataVariables.put("state", state);
	    dataVariables.put("country", country);
	    dataVariables.put("permHouse", permhouseNo);
	    dataVariables.put("permArea", permarea);
	    dataVariables.put("permvillage", permvillage);
	    dataVariables.put("permlandMark", permlandmark);
	    dataVariables.put("permCity", permcity);
	    dataVariables.put("permPincode", permpinCode);
	    dataVariables.put("permState", permstate);
	    dataVariables.put("permCountry", permcountry);
	    dataVariables.put("languageComm", languageOfCommunication);
		//FUL2-32221 PEP changes
		if (!AppConstants.CIP_PRODUCT_ID.equals(productId)) {
			Utility.addPepDetails(proposalDetails,dataVariables);
		}
	    dataVariables.put("nomineeDetailsCount", proposalNomineeDetails);
	    //FUL2-19322 boolern flag to check is product is Smart Secure Plus
	    dataVariables.put("isSSP", isSSP);
	    dataVariables.put("isEIA", eiaExist);
	    dataVariables.put("wishToHoldEia", wishToHoldEia);
	    dataVariables.put("EAccNumberExist", exitingEIA);
	    dataVariables.put("insuranceRepositoryExist", preferredInsuranceRepository);
	    dataVariables.put("EInsAccNumber", accountNumber);
	    dataVariables.put("insuranceRepository", getInsuranceRepoName(accountNumber, proposerEmploymentDetails));
	    dataVariables.put("existingInsuranceRepositoryName", existingRepoName);
		dataVariables.put(IS_NOMINEE, !isSwpWlI ? checkingDataVariablesNullChecks(formType, IS_NOMINEE, schemeType) : "");
	    dataVariables.put("insuredrelationShipWithProposer", insuredRelation);
	    dataVariables.put("relationShipWithProposer", proposerRelation);
	    dataVariables.put("insuredtitle", insuredtitle);
	    dataVariables.put("insuredfirstName", insuredfirstName);
	    dataVariables.put("insuredlastName", insuredlastName);
	    dataVariables.put("insuredmiddleName", insuredmiddleName);
	    dataVariables.put("insuredfatherFirstName", insuredfatherFirstName);
	    dataVariables.put("insuredfatherLastName", insuredfatherMiddleName);
	    dataVariables.put("insuredgender", insuredformattedGender);
	    dataVariables.put("insureddob", insuredformattedDob);
	    dataVariables.put("insurednationality", insurednationality);
	    dataVariables.put("insurededucation", insurededucation);
	    dataVariables.put("insuredmaritialStatus", insuredmaritialStatus);
	    dataVariables.put("insuredorganisationType", insuredorganisationType);
	    dataVariables.put("insuredoccupation", insuredoccupation);
	    dataVariables.put("insurednameOfEmployer", insuredemployerName);
	    dataVariables.put("insurednatureOfDuties", insurednatureOfDuties);
	    dataVariables.put("insuredannualIncome", insuredannualIncome);
        dataVariables.put("childDob",checkingDataVariablesNullChecks(childDob,CHILD_DOB, schemeType));
        dataVariables.put(CHILD_NAME, checkingDataVariablesNullChecks(childName,CHILD_NAME, schemeType));
        dataVariables.put("isChildDetailsRequired", isChildDetailRequired);
	    dataVariables.put("residingCountry", proposerCountryOfResidence);
	    dataVariables.put("insuredresidingCountry", insuredCountryOfResidence);
	    dataVariables.put("residenceForTax", residenceForTax);
		dataVariables.put("insuredindustryTypeOthers", org.apache.commons.lang3.StringUtils.isBlank(insuredindustryTypeOthers) ?
				AppConstants.BLANK : insuredindustryTypeOthers);
		dataVariables.put("customerReminderConsent",customerReminderConsent);
	    if (isNeoOrAggregator && !AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())) {
			setDataForIndustry(dataVariables, proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getIndustryDetails());
			dataVariables.put("navyReflexive1", proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails().getRank());
			} else if (AppConstants.THANOS_CHANNEL
					.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
				setDataForIndustry(dataVariables,
						proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getPartyDetails()
								.getIndustryDetails());

			}
	} catch (Exception ex) {
	    logger.info("Data addition failed for Proposal Form Document for transactionId {} : ", proposalDetails.getTransactionId(), ex);
	    List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	logger.info("Mapping person details of proposal form document is completed successfully completed for transactionId {}",
		proposalDetails.getTransactionId());
	Context personalDetailsCxt = new Context();
	personalDetailsCxt.setVariables(dataVariables);
	return personalDetailsCxt;
    }
	//FUL2-147320
	public boolean isTraditionalProposalForm(String productType, String productId){
		if(! AppConstants.ULIP.equalsIgnoreCase(productType)
				&&  ! "86".equalsIgnoreCase(productId)
				&&  ! AppConstants.AWP.equalsIgnoreCase(productType) &&
				!AppConstants.ANNUITY_PRODUCTS.contains(productId) &&
				!StringUtils.isEmpty(productType))
		 {
			return true;
		}else {
			return false;
		}
	}

	private String settingValueOfEducation(String education) {
			if (HIGH_SCHOOL.equalsIgnoreCase(education) || "01".equalsIgnoreCase(education)) {
				education = TENTH_PASS;
			} else if ((SENIOR_SCHOOL).equalsIgnoreCase(education) || "08".equalsIgnoreCase(education)) {
				education = TENTH_PLUS_TWO_PASS;
			}
		return education;
	}

	private String getInsuranceRepoName(String accountNumber,
			EmploymentDetails proposerEmploymentDetails) {
		String insuranceRepository = "";
		if (!StringUtils.isEmpty(accountNumber)) {
			if (accountNumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_1)) {
				insuranceRepository = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_1);
			} else if (accountNumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_2)) {
				insuranceRepository = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_2);
			} else if (accountNumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_3)) {
				insuranceRepository = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_3);
			} else if (accountNumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_4)) {
				insuranceRepository = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_4);
			} else if (accountNumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_5)) {
				insuranceRepository = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_5);
			}
		} else {
			insuranceRepository = proposerEmploymentDetails.getPreferredInsuranceRepositoryName();
		}
		return insuranceRepository;
	}

	private String getGenderTrad(ProposalDetails proposalDetails, String formattedGender) {
		if (!isNeoOrAggregator && AppConstants.TRADITIONAL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType())) {
			formattedGender = TRANSGENDER;
		}
		return formattedGender;
	}

	private Map<String, Object> setDataForIndustry(Map<String, Object> dataVariables, IndustryDetails industryDetails) {
        String industryType = industryDetails.getIndustryType();
        industryType = StringUtils.isEmpty(industryType) ? AppConstants.BLANK : industryType;
        if (isNeoOrAggregator) {
            dataVariables.put(AppConstants.INDUSTRY_TYPE, org.apache.commons.lang3.StringUtils.isNotBlank(industryType) ? industryType : AppConstants.BLANK);
        } else {
            dataVariables.put(AppConstants.INDUSTRY_TYPE,
                    org.apache.commons.lang3.StringUtils.isNotBlank(industryType) ? org.apache.commons.lang3.StringUtils.upperCase(industryType)
                            : AppConstants.BLANK);
        }
        IndustryInfo industryInfo = industryDetails.getIndustryInfo();

        try {
            switch (industryType) {

                case AppConstants.DEFENCE:
                    dataVariables.put("defenceReflexive1", industryInfo.isPostedOnDefenceLocation() ? AppConstants.YES : AppConstants.NO);
                    dataVariables.put("defenceReflexive2", industryInfo.getNatureOfRole());
                    dataVariables.put("defence", true);
                    break;

                case AppConstants.DIVING:
                    dataVariables.put("divingReflexive1", industryInfo.isProfessionalDiver() ? AppConstants.YES : AppConstants.NO);
                    dataVariables.put("divingReflexive2", industryInfo.getDiveLocation());
                    dataVariables.put("diving", true);
                    break;
                case AppConstants.MINING:
                    dataVariables.put("miningReflexive1", industryInfo.isWorkingInsideMine() ? AppConstants.YES : AppConstants.NO);
                    dataVariables.put("miningReflexive2", industryInfo.isAnyIllnessRelatedToOccupation() ? AppConstants.YES : AppConstants.NO);
                    dataVariables.put("mining", true);
                    break;
                case AppConstants.AIR_FORCE:
                    dataVariables.put("airforceReflexive1", industryInfo.isFlying() ? "YES" : "NO");
                    dataVariables.put("airforceReflexive2", industryInfo.getTypeOfAirCraft());
                    dataVariables.put("airforce", true);
                    break;
                case AppConstants.OIL:
                    dataVariables.put("oilReflexive1", industryInfo.isBasedAtOffshore() ? AppConstants.YES : AppConstants.NO);
                    dataVariables.put("oil", true);
                    break;
                case AppConstants.NAVY:
                    dataVariables.put("navyReflexive1", industryInfo.getNavyAreaWorking());
                    dataVariables.put("navy", true);
                    break;
                default:
                    logger.info("No Industry type Found");
            }
        } catch (Exception ex) {
	    logger.error("Error while setting Data For Industry :", ex);
        }
        return dataVariables;
    }
    private Map<String, Object> setDataForIndustryInsured(Map<String, Object> dataVariables, IndustryDetails industryDetails) {
        String industryType = industryDetails.getIndustryType();
        industryType = StringUtils.isEmpty(industryType) ? AppConstants.BLANK : industryType;
        dataVariables.put("insuredindustryType",
                org.apache.commons.lang3.StringUtils.isNotBlank(industryType) ? org.apache.commons.lang3.StringUtils.upperCase(industryType)
                        : AppConstants.BLANK);
        IndustryInfo industryInfo = industryDetails.getIndustryInfo();

        try {
            baseMapper.getIndustryType(dataVariables, industryType, industryInfo,false);
        } catch (Exception ex) {
	    logger.error("Error while setting Data For Industry Insured : " , ex);
        }
        return dataVariables;
    }

	private Map<String, Object> setDataForIndustryProposer(Map<String, Object> dataVariables, IndustryDetails industryDetails, boolean isProposerIndustryTypeDefence) {
		String industryType = industryDetails.getIndustryType();
		industryType = StringUtils.isEmpty(industryType) ? AppConstants.BLANK : industryType;
		dataVariables.put(AppConstants.INDUSTRY_TYPE,
				org.apache.commons.lang3.StringUtils.isNotBlank(industryType) ? org.apache.commons.lang3.StringUtils.upperCase(industryType)
						: AppConstants.BLANK);
		IndustryInfo industryInfo = industryDetails.getIndustryInfo();

		try {
			baseMapper.getIndustryType(dataVariables, industryType, industryInfo, isProposerIndustryTypeDefence);
		} catch (Exception ex) {
			logger.error("Error while setting Data For Industry Insured : " , ex);
		}
		return dataVariables;
	}

    private String checkIfValueIsPresent(Object obj , String type) {

    	switch (type) {

			case MARITALSTATUS : {
				BasicDetails insuredBasicDetails = (BasicDetails) obj;
				return isEmpty(insuredBasicDetails.getMarriageDetails().getMaritalStatus()) ? "NA"
						: insuredBasicDetails.getMarriageDetails().getMaritalStatus();
			}
			case EDUCATION :{
				BasicDetails insuredBasicDetails = (BasicDetails) obj;
				return StringUtils.isEmpty(insuredBasicDetails.getEducation()) ? "NA" : insuredBasicDetails.getEducation();
			}
			case ORGANIZATION_TYPE:{
				EmploymentDetails proposerEmploymentDetails = (EmploymentDetails) obj;
				return StringUtils.isEmpty(proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getOrganisationType())
						? "NA" : proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getOrganisationType();
			}
			case OCCUPATION :{
				BasicDetails insuredBasicDetails = (BasicDetails) obj;
				return StringUtils.isEmpty(insuredBasicDetails.getOccupation()) ? "NA" : insuredBasicDetails.getOccupation();
			}
			case ANNUAL_INCOME:{
				BasicDetails insuredBasicDetails = (BasicDetails) obj;
				return StringUtils.isEmpty(insuredBasicDetails.getAnnualIncome()) ? "NA" : insuredBasicDetails.getAnnualIncome();
			}
			case EMPLOYER_INFO :{
				EmploymentDetails proposerEmploymentDetails = (EmploymentDetails) obj;
				return StringUtils.isEmpty(proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getNameOfEmployer()) ? "NA"
						: proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getNameOfEmployer();
			}
			case NATURE_OF_JOB :{
				EmploymentDetails proposerEmploymentDetails = (EmploymentDetails) obj;
				return Utility.ifEmptyThenNA(proposerEmploymentDetails.getPartiesInformation().get(1).getPartyDetails().getSpecifyDutiesType());
			}
			case NRI_DETAILS:{
				BasicDetails insuredBasicDetails = (BasicDetails)obj;
				return StringUtils.isEmpty(insuredBasicDetails.getNationalityDetails().getNriDetails()) ? ""
						: insuredBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence();
			}
			default: return AppConstants.NA;
    	}
	}

	private String checkingDataVariablesNullChecks(Object obj, String type, String schemeType ) {

		 switch (type) {
			 case CHILD_DETAILS: {
				 String productId = (String) obj;
				 return (productId.equalsIgnoreCase("36") || productId.equalsIgnoreCase("81")) ? AppConstants.YES : AppConstants.NO;
			 }
			 case CHILD_DOB:{
			 	String childDob = (String)obj;
			 	return isEmpty(childDob) ? "NA" : childDob;
			 }
			 case CHILD_NAME:{
			 	String childName = (String)obj;
				 return isEmpty(childName) ? "NA" : childName;
			 }
			 case IS_NOMINEE:{
			 	String formType = (String)obj;
				 // checking condition for schemeA
			 	return (formType.equalsIgnoreCase(AppConstants.DEPENDENT) || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))) ? "NA" : "";
			 }
			 default: return AppConstants.NA;
		 }
	 }

	 private String getName(String insuredFatherName,String type) {
		 if (type.equalsIgnoreCase(FIRSTNAME) && insuredFatherName.contains(" ")) {
			 return insuredFatherName.substring(0, insuredFatherName.indexOf(' '));
		 } else if (type.equalsIgnoreCase(MIDDLENAME)) {
			 if (insuredFatherName.contains(" "))
				 return insuredFatherName.substring(insuredFatherName.indexOf(' '), insuredFatherName.length());
			 else
				 return org.apache.commons.lang3.StringUtils.EMPTY;
		 } else {
			 return insuredFatherName;
		 }

	 }

	 private void setPhoneNumberDetails(Map<String, Object> dataVariables, List<PartyInformation> partyInfoList, String employerName) {
		 if (null != partyInfoList && !CollectionUtils.isEmpty(partyInfoList) && partyInfoList.size() >= 1) {
			 dataVariables.put("email",partyInfoList.get(0).getPersonalIdentification().getEmail());
			 List<Phone> phoneList = partyInfoList.get(0).getPersonalIdentification().getPhone();
			 if (null != phoneList && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 1) {
				 dataVariables.put("mobileNumber",partyInfoList.get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber());

			 }
			 if (null != phoneList && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 2) {
				 dataVariables.put("alternate",phoneList.get(1).getPhoneNumber());

			 }
			 if (null != phoneList && !CollectionUtils.isEmpty(phoneList) && phoneList.size() >= 3) {
				 dataVariables.put("std",phoneList.get(2).getStdIsdCode());
				 dataVariables.put("landline",phoneList.get(2).getPhoneNumber());
			 }

				 dataVariables.put("nameOfEmployer",  employerName);

		 }
	 }

    private void setNomineeRelatedInfo(ProposalNomineeDetails details,PartyDetails nominee,String channel){
        if ("Others".equalsIgnoreCase(nominee.getRelationshipWithProposer())) {
            details.setNomineeRealtionshipOther(AppConstants.YES);
            details.setSpecifyRelationship(nominee.getRelationshipOthers());
            details.setReasonForNomination(nominee.getReasonForNomination());
        }
        if (nominee.getDob() != null && Float.valueOf(Utility.getAge(nominee.getDob())) < 18) {
            details.setNomineeMinor(AppConstants.YES);
            AppointeeDetails appointeDetails = nominee.getAppointeeDetails() != null ? nominee.getAppointeeDetails() : new AppointeeDetails();
            details.setGuardianName(appointeDetails.getGuardianNameOfNominee());

            /** changes for FUL- 14812 */
			/* FUL2-155058 Appointee name balnk issue fixed when appointee name contain only 1 word. and handled if name contain more than 2 words*/
			if(!StringUtils.isEmpty(details.getGuardianName())){
				String firstName = "";
				String[] guardianNameArr = details.getGuardianName().split(" ");
				if(guardianNameArr.length >1){
					for (int i=0; i< guardianNameArr.length-1; i++)
					{
						firstName += guardianNameArr[i];
						if(i < guardianNameArr.length - 2){
							firstName += " ";
						}
					}
					details.setGuardianFirstName(firstName);
					details.setGuardianLastName(guardianNameArr[guardianNameArr.length-1]);
				}
				else{
					details.setGuardianFirstName(details.getGuardianName());
				}
			}
            if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)){
            	details.setGuardianRelation(appointeDetails.getRelationwithNominee());
				if (!StringUtils.isEmpty(appointeDetails.getRelationwithNomineeOthers()) && appointeDetails.getRelationwithNominee().equalsIgnoreCase(AppConstants.OTHERS)){
					details.setGuardianRelation(appointeDetails.getRelationwithNomineeOthers());
				}
			}else {
				details.setGuardianRelation(isNeoOrAggregator ? appointeDetails.getGuardianIsYour() : appointeDetails.getRelationwithNominee());
			}
            if (!StringUtils.isEmpty(appointeDetails.getRelationwithNominee()) && appointeDetails.getRelationwithNominee().equalsIgnoreCase("Others")) {
                details.setGuardianRelationshipOther(AppConstants.YES);
                details.setGuardianSpecifyRelation(appointeDetails.getRelationwithNomineeOthers());
            }
        } else {
        	details.setNomineeMinor(AppConstants.NA);
        }
        if (Objects.nonNull(nominee) && Objects.nonNull(nominee.getAppointeeDetails())
				&& Objects.nonNull(nominee.getAppointeeDetails().getAppointeeDOB())){
			details.setAppointeeDOB(Utility.dateFormatter(nominee.getAppointeeDetails().getAppointeeDOB()));
		}
		if (Objects.nonNull(nominee) && Objects.nonNull(nominee.getAppointeeDetails())
				&&Objects.nonNull(nominee.getAppointeeDetails().getAppointeeGender())
				&& !Strings.isNullOrEmpty(nominee.getAppointeeDetails().getAppointeeGender())){
			details.setAppointeeGender(Utility.getGender(nominee.getAppointeeDetails().getAppointeeGender()));
		}

    }

	private void setNomineeRelatedInfoForThanos(ProposalNomineeDetails details,
			PartyDetails nominee) {
		if (AppConstants.OTHER_RELATIONSHIP.equalsIgnoreCase(nominee.getRelationshipWithProposer())) {
			details.setNomineeRealtionshipOther(AppConstants.YES);
			details.setSpecifyRelationship(nominee.getRelationshipOthers());
			details.setReasonForNomination(nominee.getReasonForNomination());
		}
		if (nominee.getDob() != null && Float.parseFloat(Utility.getAge(nominee.getDob())) < 18) {
			details.setNomineeMinor(AppConstants.YES);
			AppointeeDetails appointeDetails =
					nominee.getAppointeeDetails() != null ? nominee.getAppointeeDetails()
							: new AppointeeDetails();
			details.setGuardianName(appointeDetails.getGuardianName());
			/** changes for FUL- 14812 */
			if (!StringUtils.isEmpty(details.getGuardianName())) {
				String[] guardianNameArr = details.getGuardianName().split(" ");
				if (guardianNameArr.length > 1) {
					details.setGuardianFirstName(guardianNameArr[0]);
					details.setGuardianLastName(guardianNameArr[guardianNameArr.length - 1]);
				}
			}
			details.setGuardianRelation(appointeDetails.getGuardianRelation());
			if (!StringUtils.isEmpty(appointeDetails.getGuardianRelation()) && appointeDetails
					.getGuardianRelation().equalsIgnoreCase(AppConstants.OTHER_RELATIONSHIP)) {
				details.setGuardianRelationshipOther(AppConstants.YES);
				details.setGuardianSpecifyRelation(appointeDetails.getGuardianSpecifyRelationship());
			}
		}
	}
	private boolean npsJourneyCheck(ProposalDetails proposalDetails){
		if(null!=proposalDetails
				&& null!=proposalDetails.getChannelDetails()
				&&null!=proposalDetails.getChannelDetails().getChannel()
				&&null!=proposalDetails.getAdditionalFlags()
				&&null!=proposalDetails.getAdditionalFlags().getIsNpsJourney()
				&& AppConstants.CHANNEL_BROKER.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsNpsJourney())){
			return true;
		}
		return false;
	}
	private void setNatureOfDutiesData(ProposalDetails proposalDetails, Map<String, Object> dataForDocument) {
        StringBuilder sspNatureOfDuties = new StringBuilder();
        String partyType = PROPOSER;
        String formType = Utility.getFormType(proposalDetails);
        if(FORM3.equalsIgnoreCase(formType) && !Utility.isSchemeBCase(proposalDetails.getApplicationDetails().getSchemeType())){
            partyType = INSURED;
        }
        PartyInformation partyInformation = Utility.getPartiesInformationByPartyType(proposalDetails, partyType);

        if(getNatureOfRoleForDefence(partyInformation)){
            sspNatureOfDuties.append(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo().getNatureOfRole());
        } else if(Objects.nonNull(Utility.getProductInfo(proposalDetails, null))){
            sspNatureOfDuties.append(proposalDetails.getProductDetails().get(0).getProductInfo().getSSESNatureOfDuties());
            if((OTHERS).equalsIgnoreCase(sspNatureOfDuties.toString())){
                sspNatureOfDuties.append(" - ").append(proposalDetails.getProductDetails().get(0).getProductInfo().getSspNatureOfDuties());
            }
        }
        dataForDocument.put("sspNatureOfDuties", sspNatureOfDuties.toString());
    }
	private boolean getNatureOfRoleForDefence(PartyInformation partyInformation) {
        return Objects.nonNull(partyInformation.getPartyDetails()) && Objects.nonNull(partyInformation.getPartyDetails().getIndustryDetails())
                && DEFENCE.equalsIgnoreCase(partyInformation.getPartyDetails().getIndustryDetails().getIndustryType())
                && Objects.nonNull(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo())
                && StringUtils.hasLength(partyInformation.getPartyDetails().getIndustryDetails().getIndustryInfo().getNatureOfRole());
    }
}