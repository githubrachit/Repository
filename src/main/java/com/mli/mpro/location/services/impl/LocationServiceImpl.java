package com.mli.mpro.location.services.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.*;
import com.mli.mpro.common.models.genericModels.*;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.configuration.ExistingPolicyDataConfiguration;
import com.mli.mpro.configuration.UIConfigurationData;
import com.mli.mpro.configuration.models.ErrorResponse;
import com.mli.mpro.configuration.models.UIConfiguration;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.email.models.RequestPayload;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.location.models.ExistingPolicyStatus;
import com.mli.mpro.configuration.UlipFundsData;
import com.mli.mpro.configuration.DisableProductsData;
import com.mli.mpro.location.config.DirectDebit;
import com.mli.mpro.location.models.soaCloudModels.OnboardingJ2OtpTokenPayload;
import com.mli.mpro.location.models.zeroqc.ekyc.*;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.onboarding.brmsBroker.model.DiyBrmsFieldConfigurationDetails;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.otpservice.OtpResponsePayload;
import com.mli.mpro.otpservice.OtpServiceRequest;
import com.mli.mpro.otpservice.OtpServiceResponse;
import com.mli.mpro.otpservice.Response;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.samlTraceId.TraceIdRequest;
import com.mli.mpro.samlTraceId.TraceIdResponse;
import com.mli.mpro.utils.MaskType;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.yblAccount.models.yblAccountDetails;
import com.mli.mpro.yblAccount.models.OutputResponse;
import com.mli.mpro.yblAccount.models.ResponseData;
import com.mli.mpro.yblAccount.models.ResponsePayload;

import com.mli.mpro.location.models.*;
import com.mli.mpro.location.models.FundsData.AllFundsModel;
import com.mli.mpro.location.models.RecommendedFunds.Aggressive;
import com.mli.mpro.location.models.RecommendedFunds.Balanced;
import com.mli.mpro.location.models.RecommendedFunds.Conservative;
import com.mli.mpro.location.models.RecommendedFunds.VeryAggressive;
import com.mli.mpro.location.repository.*;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.PensionPlans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mli.mpro.location.services.LocationService;
import com.mli.mpro.location.services.BranchCodeService;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.mli.mpro.productRestriction.repository.DirectDebitThanosRepository;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.proposal.models.DirectDebitThanos;
import org.springframework.web.client.RestTemplate;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.J2_EMAIL_BODY;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@Service
public class LocationServiceImpl implements LocationService {

	private LocationRepository locationRepository;
	private PincodeMasterRepository pincodeMasterRepository;
	private AddressMasterRepository addressMasterRepository;
    private CompanyListRepository companyListRepository;
    private LocationOtpRepository locationOtpRepository;
    private BranchCodeRepository branchCodeRepository;
    private GstAccountRepository gstAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
	private final String invalidMessage = "Invalid request for resume journey data";

    @Autowired
	private PensionPlansRepository pensionPlansRepository;
	@Autowired
	ProposalRepository repository;
	@Autowired
	MongoOperations mongoOperation;
	@Autowired
	DirectDebit document;

	@Autowired
	ExternalServiceConfig urlConfig;

	@Value("${urlDetails.axisBranchDetailsUrl}")
	private String axisBranchDetailsUrl;

	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.axisSecretKey}")
	private String axisSecretKey;
	@Value("${nj.token.secret.key}")
	private String tokenSecretKey;
    @Value("${j2.otp.token.expiry}")
    private String j2OtpTokenExpiry;

	@Value("${diy.resume.token.expiry}")
	private String diyResumeTokenExpiry;
	
	@Value("${urlDetails.dataLakeGetAxisBranchDetails}")
    private String axisBranchUrl;

	@Value("${urlDetails.ekycModularityUrl}")
	private String ekycModularityUrl;

	@Autowired
	private DirectDebitThanosRepository directDebitRepository;
	@Autowired
	private UIConfigurationService uiConfigurationService;

	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private SoaCloudService soaCloudService;
	@Autowired
    private EmailSmsService emailSmsService;

	@Autowired
	private OTPDetailsRepository otpDetailsRepository;

	@Autowired
	private SarthiMasterRepository sarthiMasterRepository;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Value("${urlDetails.ekycBrmsModularity.redisExpireTime}")
	private String brmsEkycModularityExpireTime;

	public static final String STAGE = "9";

	private final String channel = "channel";
	private final String channelSource = "channelsource";
	private final String goodec = "goodec";
	private final String journeyTypeDiy = "journeytypediy";
	private final String physicalJourneyEnabled = "physicaljourneyenabled";

	private final String ekycInsured = "ekycinsured";
	private final String ekycPayor = "ekycpayor";
	private final String ekyProposerc = "ekyproposerc";
	private final String NA = "NA";
	String uniqueRedisKey = "";

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, CompanyListRepository companyListRepository,LocationOtpRepository locationOtpRepository,BranchCodeRepository branchCodeRepository, AddressMasterRepository addressMasterRepository, PincodeMasterRepository pincodeMasterRepository
    		,GstAccountRepository gstAccountRepository) {

		super();
		this.locationRepository = locationRepository;
		this.companyListRepository = companyListRepository;
		this.locationOtpRepository = locationOtpRepository;
		this.branchCodeRepository=branchCodeRepository;
		this.addressMasterRepository = addressMasterRepository;
		this.pincodeMasterRepository = pincodeMasterRepository;
		this.gstAccountRepository = gstAccountRepository;
	}


    @Override
    public List<String> getAllCountriesByContinent(String continent) {
	List<LocationDetails> locationDetails = locationRepository.findByTypeAndContainedInIn("Country", continent);
	List<String> countries = getListOfLocations(locationDetails);
	return countries;
    }

    @Override
    public List<String> getAllStatesByCountry(String country) {
	List<LocationDetails> locationDetails = locationRepository.findByTypeAndContainedInIn("State", country);
	List<String> states = getListOfLocations(locationDetails);
	return states;
    }

    @Override
    public List<String> getAllCitiesByStates(String state) {
	List<LocationDetails> locationDetails = locationRepository.findByTypeAndContainedInIn("City", state);
	List<LocationDetails> uniqueLocationDetails = locationDetails.stream().distinct().collect(Collectors.toList());
	List<String> cities = getListOfLocations(uniqueLocationDetails);
	return cities;
    }

    private List<String> getListOfLocations(List<LocationDetails> locationDetails) {
	List<String> listLocations = new ArrayList<String>();
	for (LocationDetails locationDetail : locationDetails) {
	    listLocations.add(locationDetail.getName());
	}
	return listLocations;
    }

    private List<String> getListOfOtpLocations(List<LocationDetailsOtp> locationOtpDetails) {
    List<String> listOtpLocations = new ArrayList<String>();
    for (LocationDetailsOtp locationDetailsOtp : locationOtpDetails) {
    		listOtpLocations.add(locationDetailsOtp.getName());
	}
    return listOtpLocations;
    }

    @Override
    public List<Object> getPhoneCodeForCountry(String continent) {
	List<LocationDetails> locationDetails = locationRepository.findByTypeAndContainedInIn("Country", continent);
	List<Object> phoneCodeList = new ArrayList<>();
	for (LocationDetails locationDetail : locationDetails) {
	    Map<String, String> obj = new HashMap<>();
	    try {
		obj.put("name", locationDetail.getName());
		obj.put("phCode", locationDetail.getPhCode());
	    } catch (Exception e) {
		logger.error("Error occurred while fetching phone code for country:",e);
	    }
	    phoneCodeList.add(obj);
	}
	return phoneCodeList;
    }

    @Override
    public List<String> getCompanyNames(String type) {
	List<String> companyList = new ArrayList<String>();
	logger.info("Company List is being  fetched");
	List<CompanyDetailsMasterData> companyDetailsMasterData = companyListRepository.findCompanyListByType(type);

	try {
	    companyList = companyDetailsMasterData.stream().map(name -> name.getCompanyName()).collect(Collectors.toList());
	} catch (Exception exception) {
	    logger.error("Error while fetching company list: {}",Utility.getExceptionAsString(exception));
	}

	return companyList;
    }

	@Override
	public List<String> getAllOtpStatesByCountry(String country) {
		List<LocationDetailsOtp> locationDetailsOtpp = locationOtpRepository.findByTypeAndContainedInIn("State", country);
		List<String> states = getListOfOtpLocations(locationDetailsOtpp);
		return states;
	}

	@Override
	public List<String> getAllOtpCitiesByStates(String state) {
		List<LocationDetailsOtp> locationDetailsOtpp = locationOtpRepository.findByTypeAndContainedInIn("City", state);
		List<String> cities = getListOfOtpLocations(locationDetailsOtpp);
		return cities;
	}

	@Override
	public List<BranchCodeService> getBranchCode(Pageable pageable) {
		List<BranchCodeService> branchCodeList=new ArrayList<BranchCodeService>();
		try {
			logger.info("Branch code is being  fetched");
			branchCodeList=branchCodeRepository.findBranchCdyStatus(AppConstants.ACTIVE,pageable);
		} catch (Exception e) {
			logger.error("Error while fetching branch code: {}",Utility.getExceptionAsString(e));
		}
		return branchCodeList;
	}
	@Override
	public void transformLocation(AddressDetails addressDetails) {
    	// get address using pincode and replace state, city from pincode master
		setAddressDataFromPincode(addressDetails);
		// transform state city country to mPro acceptable value or set null start
		addressDetails.setCity(getAddressMaster(addressDetails.getCity(), AppConstants.LOCATION_TYPE_CITY));
		addressDetails.setState(getAddressMaster(addressDetails.getState(), AppConstants.LOCATION_TYPE_STATE));
		addressDetails.setCountry(getAddressMaster(addressDetails.getCountry(), AppConstants.LOCATION_TYPE_COUNTRY));
		// transform state city country to mPro acceptable value or set null end
	}
	// method to fetch records from pincode master and set in address details if found
	private void setAddressDataFromPincode(AddressDetails addressDetails) {
		if (!StringUtils.isEmpty(addressDetails.getPinCode())) {
			List<PincodeMaster> pincodeMasters = pincodeMasterRepository.findByPincode(addressDetails.getPinCode());
			if (!CollectionUtils.isEmpty(pincodeMasters)) {
				addressDetails.setState(pincodeMasters.get(0).getState());
				addressDetails.setCity(pincodeMasters.get(0).getCity());
			}else if (!StringUtils.isEmpty(addressDetails.getCity()) && !StringUtils.isEmpty(addressDetails.getState())){
				PincodeMaster pincodeMasterEntry = new PincodeMaster();
				pincodeMasterEntry.setPincode(addressDetails.getPinCode());
				pincodeMasterEntry.setCity(addressDetails.getCity());
				pincodeMasterEntry.setState(addressDetails.getState());
				pincodeMasterRepository.save(pincodeMasterEntry);
			}
		}
	}

	@Override
	public List<PincodeMaster> getLocationByPincode(String pincode) {
		return pincodeMasterRepository.findByPincode(pincode);
	}
	// method to check and return mPro acceptable location
	private String getAddressMaster(String location, String type) {
		if (!StringUtils.isEmpty(location)){
			List<AddressMaster> addressMasters = addressMasterRepository.findByLocationNameIgnoreCaseAndTypeIgnoreCase(location, type);
			if (CollectionUtils.isEmpty(addressMasters)) {
				addressMasters = addressMasterRepository.findByLocationCodeIgnoreCaseAndTypeIgnoreCase(location, type);
			}
			if (!CollectionUtils.isEmpty(addressMasters) && !StringUtils.isEmpty(addressMasters.get(0).getLocationName())) {
				return getLocationFromMproDb(addressMasters.get(0).getLocationName(), type);
			}else{
				return getLocationFromMproDb(location, type);
			}
		}
		return location;
	}

	private String getLocationFromMproDb(String locationName, String type) {
		List<LocationDetails> mProLocation = locationRepository.findByNameIgnoreCaseAndTypeIgnoreCase(locationName, type);
		if (!CollectionUtils.isEmpty(mProLocation)) {
			return mProLocation.get(0).getName();
		}
		logger.error("no location found by the name {} in address master or location db", locationName);
		return null;
	}
	//FUL2-18647 on basis product code get YBL Account
		@Override
		public OutputResponse getGstYblAccount(String type) {
			yblAccountDetails yblAccountDetails = null ;
			OutputResponse outputResponse = new OutputResponse();
			ResponseData responseData = new ResponseData();
			ResponsePayload responsePayload = new ResponsePayload();
			try {
				yblAccountDetails = gstAccountRepository.findByType(type);
				responseData.setMessage(AppConstants.SUCCESS);
				responseData.setStatusCode(200);
			}catch (Exception e) {
			logger.error("Error while fetching gst ybl account {}",Utility.getExceptionAsString(e));
			}
			outputResponse.setResponseData(responseData);
			responsePayload.setYblAccountDetails(yblAccountDetails);
			responseData.setPayload(responsePayload);
			outputResponse.setResponseData(responseData);
			return outputResponse;
		}

		/**
		 * @implNote Method gets all the pension plan details save in DB
		 * @return List<PensionPlans>
		 */
		@Override
		public List<PensionPlans> getPensionPlans() {
			logger.info("LocationServiceImpl - getPensionPlans()");
			List<PensionPlans> pensionPlanList = new ArrayList<>();

			try {
				pensionPlanList = pensionPlansRepository.findAll();
			} catch (Exception e) {
				logger.error("LocationServiceImpl - getPensionPlans() - Error getting pension plans from pensionPlansRepository {}",Utility.getExceptionAsString(e));
			}

			return pensionPlanList;
		}

	@Override
	@Async
	public void generateDirectDebitBulk() {
		DirectDebitThanos thanos = directDebitRepository.findAll().get(0);
		Date startDate = thanos.getStartDate();
		Date endDate = thanos.getEndDate();
		logger.info("startdate and enddate for policies to generate directdebit bulk {},{}", startDate,endDate);
		Date lastRunDate = null;
		try {
			List<String> policies = new ArrayList<>();
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			Query query = null;
			Update update = null;
			long requestedTime = System.currentTimeMillis();
			Date requestDate = new Date(requestedTime);

			int pageNumber = 0;
			Pageable pageRequest = PageRequest.of(pageNumber, thanos.getPageLimit());
			 List<ProposalDetails> proposalDetails = repository.findByApplicationDetailsCreatedTime(startDate,endDate, pageRequest);
			 int c=1;
				logger.info("proposalDetils size for generating bulk {}", proposalDetails.size());
				for (ProposalDetails proposal : proposalDetails) {
					logger.info("bulk generate transactionId {}", c+ " "+proposal.getTransactionId());
					c++;
				}
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info("requested time and response time for getting the data from DB {} and {}", requestDate,
					processedTime/1000.0);
			long iterationTime = 0;
			if (proposalDetails != null) {
				iterationTime = System.currentTimeMillis();
				Date iterationDate = new Date(iterationTime);
				logger.info("time to start iteration the proposalDetails {}", iterationDate);
				for (ProposalDetails details : proposalDetails) {
					thanos = directDebitRepository.findAll().get(0);
					if (details.getAdditionalFlags().isManualDirectDebitDocGenerated()) {
						logger.info("skipping the policyNumber already document generated {}",
								details.getApplicationDetails().getPolicyNumber());
					}
					if ("Thanos".equalsIgnoreCase(details.getChannelDetails().getChannel())
							&& STAGE.contains(details.getApplicationDetails().getStage())
							&& !details.getAdditionalFlags().isManualDirectDebitDocGenerated()) {
						logger.info("started cron job for transactionId {} is {}", details.getTransactionId(),
								details.getAdditionalFlags());
						lastRunDate = details.getApplicationDetails().getCreatedTime();
						logger.info("lastRunDate for transactionId {} is {}", details.getTransactionId(), lastRunDate);
						query = new Query();
						update = new Update();
						query.addCriteria(Criteria.where("applicationDetails.policyNumber")
								.is(details.getApplicationDetails().getPolicyNumber()));
						update.set("additionalFlags.directDebitNewCrIdentifier", true);
						mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
						document.generateDocument(details);
						update.set("additionalFlags.manualDirectDebitDocGenerated", true);
						mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
						policies.add(details.getApplicationDetails().getPolicyNumber());
						query = new Query();
						update = new Update();
						query.addCriteria(Criteria.where("startDate").is(thanos.getStartDate()));
						update.set("startDate", lastRunDate);
						mongoOperation.findAndModify(query, update, options, DirectDebitThanos.class);
						logger.info("after cron job for transactionId {} is {}", details.getTransactionId(),
								details.getAdditionalFlags());
					}
				}
			}
			long processedIterationTime = (System.currentTimeMillis() - iterationTime);
			logger.info("overall time taken for the iteration {}", processedIterationTime/1000.0);
			logger.info("completed policies {}", policies);
		} catch (Exception e) {
			logger.error("Exception occured while execution of cron job {}", e.getMessage());
		}
	}

	/**
	 * @implNote This method is used to get ULIP funds and Strategies based on productId
	 * In DB The recommended funds are configured based on IRP Score.(Balanced, Conservative, Agressive and Very Agressive)
	 * @param productId
	 * @return
	 */
	@Override
	public FundsData getUlipFunds(String productId) {
		List<AllFund> ulipAllFundsList = UlipFundsData.allFunds;
		List<RecommendedFunds> ulipRecommendedFundsList = UlipFundsData.recommendedFunds;
		List<UIConfiguration> uiConfigurationList = UIConfigurationData.uiConfigurationList;
		FundsData fundsData = new FundsData();
		RecommendedFund recommendedFund = new RecommendedFund();
		AllFundsModel allFunds = new AllFundsModel();

		try {

			List<AllFund> allFundsList = ulipAllFundsList.stream()
					.filter(funds -> productId.equals(funds.getProductId())).collect(Collectors.toList());

			List<RecommendedFunds> recommendedFunds = ulipRecommendedFundsList.stream()
					.filter(funds -> productId.equals(funds.getProductId())).collect(Collectors.toList());

			if (allFundsList != null && !allFundsList.isEmpty() && recommendedFunds != null
					&& !recommendedFunds.isEmpty()) {
				allFunds.setFunds(allFundsList.get(0).getAllFunds().getFunds());
				if (allFundsList.get(0).getAllFunds().getStrategies() == null) {
					allFunds.setStrategies(new ArrayList<>());
				} else {
					allFunds.setStrategies(allFundsList.get(0).getAllFunds().getStrategies());
				}

				setRecommendedFunds(recommendedFunds, recommendedFund);

				//FUL2-202402_New_Fund_Addition_NIFTY_ALPHA_50_FUND
				if(uiConfigurationList != null && !uiConfigurationList.isEmpty()) {
				nfoFundModification(productId, allFunds, recommendedFund, uiConfigurationList.get(0));
				}
				fundsData.setAllFunds(allFunds);
				fundsData.setRecommendedFund(recommendedFund);
				fundsData.setProductId(productId);
				fundsData.setType(allFundsList.get(0).getType());
			}
			logger.info("Funds fetched successfully.");

		} catch (Exception e) {
			logger.error("Exception occurred while getting ulipFunds {}", Utility.getExceptionAsString(e));
		}

		return fundsData;
	}

	@Override
	public List<String> getDisableProducts(String channel, String goCode, boolean isPosSeller, boolean isCATAxis, boolean isPhysicalJourney){
		List<String>  disableProductList = new ArrayList<>();
		String channelName = "";
		try{
			if(channel.equalsIgnoreCase("X") && isCATAxis){
				channelName = "CATAxis";
			} else if (channel.equalsIgnoreCase("X") && isPhysicalJourney) {
				channelName = "PhysicalJourneyAxis";
			} else if (!channel.equalsIgnoreCase("X") && isPhysicalJourney) {
				channelName = "PhysicalJourneyNonAxis";
			} else if (!channel.equalsIgnoreCase("X")) {
				Optional<String> channelDetail = DisableProductsData.channelRegex.entrySet().stream().filter(entry-> goCode.matches(entry.getKey())).map(Map.Entry::getValue).filter(Objects::nonNull).findFirst();
				channelName = channelDetail.isPresent() ? channelDetail.get() : "";
			}

			if(!channelName.equalsIgnoreCase("")){
				disableProductList = isPosSeller ? DisableProductsData.posDisableProducts.get(channelName) :  DisableProductsData.nonPosDisableProducts.get(channelName);
			}else{
				logger.info("Channel name not identified for the go code {}", goCode);
			}
		} catch (Exception e){
			logger.error("Exception occured while fetching disable product list: {}", Utility.getExceptionAsString(e));
		}
		return disableProductList;
	}

	private void nfoFundModification(String productId, AllFundsModel allFunds, RecommendedFund recommendedFund,
			UIConfiguration configuration) {
		if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("niftyFund"))
				&& configuration.getNfoProductList().contains(productId)) {
			Utility.niftyFundEnableOrNot(uiConfigurationService, allFunds, recommendedFund, null, "");
		} else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled("niftyFund"))
				&& configuration.getNfoProductList().contains(productId)) {
			Utility.fundRemoved(allFunds, recommendedFund, configuration);
		} else if (configuration.getNonNfoProductList().contains(productId)) {
			Utility.niftyFundEnableOrNot(uiConfigurationService, allFunds, recommendedFund, null, productId);
		}
	}


	// The handling of this code is due to one issue related to NIFTY Fund Addition
			// and testing of NFO, Cooling and after cooling period
	private void setRecommendedFunds(List<RecommendedFunds> recommendedFunds, RecommendedFund recommendedFund) {

		Aggressive aggressiveFund = recommendedFunds.get(0).getAggressive();
		Balanced balancedFund = recommendedFunds.get(0).getBalanced();
		Conservative conservativeFund = recommendedFunds.get(0).getConservative();
		recommendedFund.setAggressive(aggressiveFund);
		recommendedFund.setBalanced(balancedFund);
		recommendedFund.setConservative(conservativeFund);
		VeryAggressive veryAggressiveFund = new VeryAggressive();
		veryAggressiveFund.setFunds(recommendedFunds.get(0).getVeryAggressive().getFunds());
		veryAggressiveFund.setStrategies(recommendedFunds.get(0).getVeryAggressive().getStrategies());
		recommendedFund.setVeryAggressive(veryAggressiveFund);
	}

	@Override
	public ExistingPolicyStatus getExistingPolicy(String productId) {
		List<ExistingPolicyStatus> existingPolicyStatusList = ExistingPolicyDataConfiguration.existingPolicyStatus;
		List<ExistingPolicyStatus> existingPolicyStatus = existingPolicyStatusList.stream()
				.filter(data -> productId.equals(data.getProductId())).collect(Collectors.toList());

		return existingPolicyStatus.get(0);
	}

    @Override
    public List<String> getAllUniqueCities() {

        List<LocationDetails> locationDetails = locationRepository.findDistinctNameByType(AppConstants.LOCATION_TYPE_CITY);
        List<LocationDetails> uniqueLocationDetails = locationDetails.stream().distinct().collect(Collectors.toList());
        return getListOfLocations(uniqueLocationDetails);

    }

    @Override
	public BranchDetailsResponse getAxisBranchDetails(String transactionId, String channelCode) throws Exception {
		BranchDetailsResponse response = new BranchDetailsResponse();
		MsgInfo msgInfo = new MsgInfo();
		logger.info("Calling getAxisBranchDetails with transactionId {} and channelCode {}",transactionId, channelCode);
		try {
			if (null == transactionId || null == channelCode)
			{
				msgInfo.setMsgCode(AppConstants.BAD_REQUEST_CODE);
				msgInfo.setMsgDescription(AppConstants.BAD_REQUEST_MESSAGE);
				msgInfo.setMsg(AppConstants.SOMETHING_WENT_WRONG);
				return buildDefaultResponse(msgInfo);
			}
			BranchDetailsRequest branchDetailsRequest = setBranchDetailsRequest(transactionId, channelCode);
			if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AXIS_BRANCH_DETAILS))) {
				branchDetailsRequest.getRequest().getPayload().setEnquiryType("7");
				branchDetailsRequest.getRequest().getHeader().setSoaAppId(AppConstants.FULFILLMENT);
				ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(branchDetailsRequest, axisBranchUrl);
				if (responseEntity != null && responseEntity.getBody() != null) {
					response = new ObjectMapper().convertValue(responseEntity.getBody(), BranchDetailsResponse.class);
				}
			} else {
				HttpHeaders headers = setHeaders();
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<BranchDetailsRequest> httpEntity = new HttpEntity<>(branchDetailsRequest, headers);
				response = restTemplate
						.exchange(axisBranchDetailsUrl, HttpMethod.POST, httpEntity, BranchDetailsResponse.class)
						.getBody();
			}
			if (Objects.nonNull(response) && !(response).getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase(AppConstants.SUCCESS_RESPONSE))
			{
				  msgInfo = response.getResponse().getMsgInfo();
				return buildDefaultResponse(msgInfo);
			}
			// Sorted Branch Details in Ascending Order based on branchCode
			getSortedBranchDetails(response, transactionId);
		} catch (Exception ex) {
			logger.error("Getting exception while calling Axis Branch Details API for transactionId {} is {}", transactionId, ex.getMessage());
			throw ex;
		}
		return response;
	}


	public BranchDetailsResponse buildDefaultResponse(MsgInfo msgInfo) {
		BranchDetailsResponse response = new BranchDetailsResponse();
		BranchResponse branchResponse = new BranchResponse();
		branchResponse.setMsgInfo(msgInfo);
		branchResponse.setPayload(null);
		response.setResponse(branchResponse);
		logger.info("Called buildDefaultResponse {}",response);
		return  response;
	}


	private HttpHeaders setHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("X-IBM-Client-Id", cleintID);
		headers.add("X-IBM-Client-Secret", axisSecretKey);
		return headers;
	}

	private BranchDetailsRequest setBranchDetailsRequest(String transactionId, String channelCode){
		BranchDetailsRequest request = new BranchDetailsRequest();
		try {
            Header header = new Header();
			header.setSoaAppId(AppConstants.APPNAME);
			header.setSoaCorrelationId(transactionId);
			BranchDetailsRequestPayload payload = new BranchDetailsRequestPayload();
			payload.setChannelCode(channelCode);
			BranchRequest branchRequest = new BranchRequest();
			branchRequest.setHeader(header);
			branchRequest.setPayload(payload);
			request.setRequest(branchRequest);
		} catch (Exception ex){
			logger.error("Getting exception {} while setting Branch Details Request for transactionId {}",ex.getMessage(),transactionId);
		}
		return request;
	}

	private void getSortedBranchDetails(BranchDetailsResponse response, String transactionId) {
		try {
			if (null != response && null != response.getResponse() && null != response.getResponse().getPayload() && null != response.getResponse().getPayload().getAddressDetails()) {
				List<BranchDetails> branchDetails = response.getResponse().getPayload().getAddressDetails();
				branchDetails.sort(Comparator.comparing(BranchDetails::getBranchId));
				response.getResponse().getPayload().setAddressDetails(branchDetails);
			}
		} catch (Exception ex) {
			logger.error("Getting Exception {} while sorting branch details in ascending order for transactionId {}", ex.getMessage(), transactionId);
		}
	}

	@Override
	public OtpServiceResponse fetchJ2OTPServiceResponse(OtpServiceRequest request) throws UserHandledException {
		OtpServiceResponse otpServiceResponse;
		long transactionId = request.getRequest().getPayload().getTransactionId();
		String methodType = request.getRequest().getPayload().getMethodType();
		ProposalDetails proposalDetails = proposalRepository.findByTransactionId(transactionId);
		String dob = request.getRequest().getPayload().getDob();
		boolean isDiyJourney = Utility.isOpenDiyJourney(proposalDetails);

		DiyBrmsFieldConfigurationDetails diyBrmsFieldConfigurationDetails = proposalDetails.getDiyBrmsFieldConfigurationDetails();

		if (isDiyJourney || validateDobOfCustomer(dob, proposalDetails, transactionId, methodType)) {
			logger.info("Dob validation is success for transactionId {} and methodType {}", transactionId, methodType);
			if( isDiyJourney && GENERATE_OTP_TYPE.equalsIgnoreCase(methodType)){
				otpServiceResponse= validateForOTPFlooding(proposalDetails,request,diyBrmsFieldConfigurationDetails);
			}else {
				otpServiceResponse = soaCloudService.fetchOTPServiceResponse(request);
			}
			if (MSG_CODE_OPT_VALIDATE.equalsIgnoreCase(otpServiceResponse.getResponse().getPayload().getMsgCode()) &&
					VALIDATE_OTP_TYPE.equalsIgnoreCase(methodType)) {
				if(isDiyJourney) {
					saveOtpTimeForDiy(proposalDetails,request);
					otpServiceResponse.getResponse().getPayload().setToken(getTokenForJ2Otp(proposalDetails, transactionId));
				}
				else
					otpServiceResponse.getResponse().getPayload().setToken(getTokenForJ2Otp(proposalDetails, transactionId));
			} else if(GENERATE_OTP_TYPE.equalsIgnoreCase(methodType)){
					if(SUCCESS_RESPONSE.equalsIgnoreCase(otpServiceResponse.getResponse().getMsgInfo().getMsgCode())){
					sendOtpCommunicationToCustomer(proposalDetails, otpServiceResponse);
					otpServiceResponse = setMaskedPhoneNoAndEmail(proposalDetails, otpServiceResponse);
				}else if(BAD_REQUEST_CODE.equalsIgnoreCase(otpServiceResponse.getResponse().getMsgInfo().getMsgCode())){
					try {
						String toMobileNumber = Utility.getPartyInfo(proposalDetails).getPersonalIdentification().getPhone().get(0).getPhoneNumber();
						String toEmail = Utility.getPartyInfo(proposalDetails).getPersonalIdentification().getEmail();
						otpServiceResponse.getResponse().getPayload().setMessage(J2_MASK_MSG + maskMobileAndEmail(toMobileNumber, toEmail));
					}catch (Exception ex){
						logger.error("For transactionId {} exception occurred while setting message {}", transactionId,ex.getMessage());
					}
				}
			}
		} else {
			logger.error("Invalid DOB for transactionId {}", transactionId);
			throw new UserHandledException(Arrays.asList(DOB_ISSUE_MSG), HttpStatus.INTERNAL_SERVER_ERROR, true);
		}
		return otpServiceResponse;

	}

	private OtpServiceResponse setMaskedPhoneNoAndEmail(ProposalDetails proposalDetails,
			OtpServiceResponse otpServiceResponse) {
		try {
			otpServiceResponse.getResponse().getPayload().setPhoneNumber(proposalDetails.getPartyInformation().get(0)
					.getPersonalIdentification().getPhone().get(0).getPhoneNumber());

			otpServiceResponse.getResponse().getPayload()
					.setEmail(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getEmail());

		} catch (Exception e) {
			logger.error("Exception in setMaskedPhoneNoAndEmail {}", Utility.getExceptionAsString(e));
		}
		return otpServiceResponse;
	}

	private void saveOtpTimeForDiy(ProposalDetails proposalDetails, OtpServiceRequest request) {

		try {
			String actionType = request.getRequest().getPayload().getActionType();
			String methodType = request.getRequest().getPayload().getMethodType();


			Query query = null;
			Update update = null;
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);

			query = new Query();
			update = new Update();
			query.addCriteria(Criteria.where("transactionId")
					.is(proposalDetails.getTransactionId()));

			if(!FINAL_SUBMIT_OTP_REQUEST_TYPE.equalsIgnoreCase(actionType))
				return;

			logger.info("For transactionId {} saving otp validation time for actionType {}", proposalDetails.getTransactionId(),actionType);

			if(VALIDATE_OTP_TYPE.equalsIgnoreCase(methodType)) {
				update.set("posvDetails.posvStatus.submittedOTPDate", new Date());
			}
			else if(GENERATE_OTP_TYPE.equalsIgnoreCase(methodType)) {
				 update.set("posvDetails.posvStatus.generatedOTPDate", new Date());
				 update.set("posvDetails.posvStatus.submittedOTPDate", new Date());
			}

			mongoOperation.findAndModify(query, update, options, ProposalDetails.class);

			logger.info("For transactionId {} setting otp generation time for methodType {}", proposalDetails.getTransactionId(),methodType);

		} catch (Exception e) {
			logger.error("Exception in  saveOtpTimeForDiy for transactionId {} and exception {}",proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
		}

	}


	private OtpServiceResponse validateForOTPFlooding(ProposalDetails proposalDetails, OtpServiceRequest otpServiceRequest,
			DiyBrmsFieldConfigurationDetails diyBrmsFieldConfigurationDetails) {

		OtpServiceResponse otpServiceResponse = new OtpServiceResponse();
		long transactionId = otpServiceRequest.getRequest().getPayload().getTransactionId();
		Date currentDate = new Date();

		logger.info("For transactionId {} validationForOTPFlooding request {} : ", transactionId, otpServiceRequest);
		OTPDetails otpDetail = new OTPDetails();
		try {

			Integer dailyLimit = Integer.valueOf(diyBrmsFieldConfigurationDetails.getUtmBasedLogic().getSmsDailyLimit());
			Integer instanceLimit = Integer.valueOf(diyBrmsFieldConfigurationDetails.getUtmBasedLogic().getSmsInstanceLimit());
			Integer coolDownPeriod = Integer.valueOf(diyBrmsFieldConfigurationDetails.getUtmBasedLogic().getCoolDownPeriod());

			if(isOtpFloodingDetected(transactionId, dailyLimit,
					instanceLimit, coolDownPeriod, otpServiceRequest.getRequest().getPayload().getActionType()))
				return returnOtpFloodingErrorMessage(transactionId);

			otpServiceResponse = soaCloudService.fetchOTPServiceResponse(otpServiceRequest);

			if (checkSuccessOrFailureResponseOfOTPService(otpServiceResponse)) {
				otpDetail.setTransactionId(transactionId);
				otpDetail.setOtpGeneratedTime(currentDate);
				otpDetail.setActionType(otpServiceRequest.getRequest().getPayload().getActionType());
				logger.info("For transactionId {} validating OTP request saving otpValidate object {} : ",
						transactionId, otpDetail);
				otpDetailsRepository.save(otpDetail);

				saveOtpTimeForDiy(proposalDetails,otpServiceRequest);
			}
		}catch (Exception ex) {
			logger.error("Exception while validating OTP for transactionId {} : {} ", transactionId, ex.getMessage());
		}
		return otpServiceResponse;
	}


	private OtpServiceResponse returnOtpFloodingErrorMessage(long transactionId) {

		OtpServiceResponse otpServiceResponse = new OtpServiceResponse();
		Response response = new Response();
		MsgInfo msgInfo = new MsgInfo();

		msgInfo.setMsgCode(BAD_REQUEST_CODE);
		msgInfo.setMsgDescription(DIY_OTP_VALIDATION_ERROR_MSG);
		response.setMsgInfo(msgInfo);

		OtpResponsePayload payload=new OtpResponsePayload();

		payload.setMsgCode(BAD_REQUEST_CODE);
		payload.setTransactionId(transactionId);

		response.setPayload(payload);
		otpServiceResponse.setResponse(response);

		return otpServiceResponse;
	}

	/**
	 *
	 * @param transactionId
	 * @param dailyLimit
	 * @param instanceLimit
	 * @param coolDownPeriod
	 * @return true if any of the OTP flooding condition is satisfied
	 */
	private boolean isOtpFloodingDetected(long transactionId, Integer dailyLimit, Integer instanceLimit,
			Integer coolDownPeriod,String actionType) {

		Date fromDate = DateTimeUtils.subtractFromDate(new Date(), 30, AppConstants.TimeUnit.SECONDS);
		Date toDate = new Date();

		// Get count of OTP's for Last 30 seconds.
		List<OTPDetails> otpListLast30Seconds = otpDetailsRepository
				.findByTransactionIdAndActionTypeAndOtpGeneratedTimeBetween(transactionId, actionType, fromDate, toDate);

		if (!otpListLast30Seconds.isEmpty())
			return true;

		// Get count of OTP's within current time and cooldown period time.
		fromDate = DateTimeUtils.subtractFromDate(new Date(), coolDownPeriod, AppConstants.TimeUnit.MINUTES);
		List<OTPDetails> otpListCooldownPeriod = otpDetailsRepository
				.findByTransactionIdAndActionTypeAndOtpGeneratedTimeBetween(transactionId, actionType, fromDate, toDate);

		if (instanceLimit.equals(otpListCooldownPeriod.size()))
			return true;

		// Get count of OTP's for the current day
		fromDate = DateTimeUtils.getStartOfDayDate(new Date());
		List<OTPDetails> otpListCurrentDay = otpDetailsRepository
				.findByTransactionIdAndActionTypeAndOtpGeneratedTimeBetween(transactionId, actionType, fromDate, toDate);

		return dailyLimit.equals(otpListCurrentDay.size());

	}

	private boolean validateDobOfCustomer(String dob, ProposalDetails proposalDetails, long transactionId, String methodType) throws UserHandledException{
		logger.info("Into validateDobOfCustomer for dob {} TransactionID {} methodType {}", dob, transactionId, methodType);
		try {
			if(StringUtils.hasLength(dob) && GENERATE_OTP_TYPE.equalsIgnoreCase(methodType)){
				logger.info("Into GENERATE_OTP_TYPE condition for dob {} TransactionID {} methodType {}", dob, transactionId, methodType);
				if(proposalDetails!= null){
					Date customerDobFromDB = Utility.getPartyInfo(proposalDetails).getBasicDetails().getDob();
					Date customerDob = Utility.stringDateFormatter(customerDobFromDB.toString(), AppConstants.E_MMM_DD_YYYY_HH_MM_SS_Z);
					logger.info("Into customerDob condition for customerDobFromDB {} customerDob {} ",customerDobFromDB,customerDob);
					Date requestDate = Utility.stringDateFormatter(dob, AppConstants.DD_MM_YYYY_HYPHEN);
					logger.info("requestDate {}", requestDate);
					LocalDate dobFromRequest = requestDate.toInstant().atZone(ZoneId.of(TIME_Z_ASIA)).toLocalDate();
					LocalDate dobFromDB = customerDob.toInstant().atZone(ZoneId.of(TIME_Z_ASIA)).toLocalDate();
					logger.info("LocalDate comparing dobFromRequest {} dobFromDB {}", dobFromRequest, dobFromDB);
					return dobFromRequest.isEqual(dobFromDB);
				} else {
					logger.error("Into validateDobOfCustomer proposal details are not found for transactionId {}", transactionId);
				}
			} else if (VALIDATE_OTP_TYPE.equalsIgnoreCase(methodType)){
				logger.info("Into VALIDATE_OTP_TYPE condition for dob {} TransactionID {} methodType {}", dob, transactionId, methodType);
				return true;
			}
		} catch (Exception ex){
			throw new UserHandledException(Arrays.asList("Exception while validating dob - "+ ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return false;
	}

	private String getTokenForJ2Otp(ProposalDetails proposalDetails, long transactionId) {
		if(proposalDetails!= null){
			String agentId = proposalDetails.getSourcingDetails().getAgentId();
			String sourceChannel = proposalDetails.getAdditionalFlags().getSourceChannel();

			if(!TELE_DIY.equalsIgnoreCase(sourceChannel) && Utility.isOpenDiyJourney(proposalDetails))
				sourceChannel = AppConstants.BRMS_BROKER_DIY_JOURNEY;
			OnboardingJ2OtpTokenPayload onboardingJ2OtpTokenPayload = new OnboardingJ2OtpTokenPayload(sourceChannel, agentId, J2_OTP_TOKEN_SUBJECT, String.valueOf(transactionId));
			return Utility.generateJwtToken(tokenSecretKey,j2OtpTokenExpiry,onboardingJ2OtpTokenPayload);
		} else {
			logger.error("Into getTokenForJ2Otp proposal details are not found for transactionId {}", transactionId);
			return "";
		}
	}

    private void sendOtpCommunicationToCustomer(ProposalDetails proposalDetails, OtpServiceResponse otpServiceResponse) throws UserHandledException {
        setUpDetailsForOtpAndSendNotification(proposalDetails, otpServiceResponse);
    }

    private void setUpDetailsForOtpAndSendNotification(ProposalDetails proposalDetails, OtpServiceResponse otpServiceResponse) throws UserHandledException {
        logger.info("Preparing communicationDetails for otp generated by soa for transactionId {}", proposalDetails.getTransactionId());
        String toMobileNumber = Utility.getPartyInfo(proposalDetails).getPersonalIdentification().getPhone().get(0).getPhoneNumber();
        String toEmail = Utility.getPartyInfo(proposalDetails).getPersonalIdentification().getEmail();
        String otp = otpServiceResponse.getResponse().getPayload().getOtpCode();
        otpServiceResponse.getResponse().getPayload().setMessage(J2_MASK_MSG + maskMobileAndEmail(toMobileNumber, toEmail));
        logger.info("Otp sending to mobile {} and email {} for transactionId {}", ApplicationUtils.getMaskedValue(toMobileNumber, MaskType.MOBILE), ApplicationUtils.getMaskedValue(toEmail, MaskType.EMAIL), proposalDetails.getTransactionId());
        RequestPayload emailPayload = constructEmailPayload(toEmail);
        com.mli.mpro.sms.models.RequestPayload smsPayload = constructSmsPayload(J2_SMS_BODY.replace("@OTP", otp),toMobileNumber);
        sendNotification(otp, emailPayload, smsPayload, proposalDetails.getTransactionId(), proposalDetails);
    }

    private void sendNotification(String otp, RequestPayload emailPayload, com.mli.mpro.sms.models.RequestPayload smsPayload, long transactionId, ProposalDetails proposalDetails) throws UserHandledException {
	    if(SUCCESS.equalsIgnoreCase(emailSmsService.sendEmail(emailPayload, J2_EMAIL_BODY.replace("@OTP", otp)))){
            logger.info("Success J2 send otp EMAIL for transactionId {}", transactionId);
        } else {
            logger.info("Failure J2 send otp EMAIL for transactionId {}", transactionId);
        }
        if(SUCCESS.equalsIgnoreCase(emailSmsService.sendSMS(smsPayload,proposalDetails))){
            logger.info("Success J2 send otp SMS for transactionId {}", transactionId);
        }else {
            logger.info("Failure J2 send otp SMS for transactionId {}", transactionId);
        }

    }

    private RequestPayload constructEmailPayload(String toEmail){
        RequestPayload emailPayload = new RequestPayload();
        emailPayload.setEmailSubject(J2_OTP_SUBJECT);
        emailPayload.setMailId(toEmail);
        return emailPayload;
    }

    private com.mli.mpro.sms.models.RequestPayload constructSmsPayload(String smsText, String smsTo){
        com.mli.mpro.sms.models.RequestPayload smsPayload = new com.mli.mpro.sms.models.RequestPayload();
        smsPayload.setMessageText(smsText);
        smsPayload.setMessageTo(smsTo);
        return smsPayload;
    }

    private static String maskMobileAndEmail(String mobile, String email){
        String maskMobile ="" , maskEmail ="";
        Pattern pattern = Pattern.compile(J2_MOB_REGEX);
        Matcher matcher = pattern.matcher(mobile);
        if (matcher.matches()) {
            maskMobile = matcher.replaceFirst("$1******$2");
        }
        pattern = Pattern.compile(J2_EMAIL_REGEX);
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            maskEmail = matcher.replaceFirst("$1****@$2****$3");
        }
        return maskMobile + " and email " + maskEmail;
    }

	private boolean checkSuccessOrFailureResponseOfOTPService(OtpServiceResponse otpServiceResponse){
		MsgInfo nullCheckMsgInfo = Optional.ofNullable(otpServiceResponse)
				.map(ap -> ap.getResponse())
				.map(ap -> ap.getMsgInfo())
				.orElse(null);
		return (nullCheckMsgInfo!=null && SUCCESS_RESPONSE.equalsIgnoreCase(nullCheckMsgInfo.getMsgCode()))?true:false;
	}

	@Override
	public SarthiResponsePayload fetchSarthiData(InputRequest inputRequest) {
		SarthiResponsePayload response = new SarthiResponsePayload();
		String agentId = inputRequest.getRequest().getRequestData().getSarthirequestPayload().getAgentId();
		logger.info("Get sarthi data with agent Id {}",agentId);
		if(StringUtils.hasLength(agentId)){
			Optional<SarthiMaster> dataFromDb = Optional.ofNullable(sarthiMasterRepository.findByAgentId(agentId));
			if(dataFromDb.isPresent()){
				logger.info("Master Data found in DB");
				response.setSarthiAvailable(true);
				response.setSarthiName(dataFromDb.get().getSarthiName());
				response.setSarthiContactNumber(dataFromDb.get().getSarthiContactNumber());
			}else{
				response.setSarthiAvailable(false);
			}
		}
		return response;
	}


	@Override
	public GenericApiResponse<ResumeJourneyResponse> validateResumeJourneyData(GenericApiRequest<ResumeJourneyRequest> request) {
		logger.info("Into Validating resume journey data for request {}", request);
		if(Utility.isAnyObjectNull(request, request.getRequest(), request.getRequest().getPayload())){
			logger.error(invalidMessage);
			return buildResponse(null, new MsgInfo("Request Invalid", BAD_REQUEST_CODE, "Bad Request"), null);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ResumeJourneyRequest resumeJourneyRequest = objectMapper.convertValue(request.getRequest().getPayload(), ResumeJourneyRequest.class);
		Pair<MsgInfo,ProposalDetails> validateResult = validateRequestData(resumeJourneyRequest);
		if (validateResult.getSecond().getTransactionId() == 0){
			return buildResponse(null, validateResult.getFirst(), request.getRequest().getMetadata());
		} else {
			logger.info("Resume journey data validated successfully for transactionId {}", validateResult.getSecond().getTransactionId());
			ResumeJourneyResponse resumeJourneyResponse = new ResumeJourneyResponse(validateResult.getSecond().getTransactionId(),getRedirectionUrl(validateResult.getSecond()));
			return buildResponse(resumeJourneyResponse, new MsgInfo("Success", SUCCESS_RESPONSE, "Success"), request.getRequest().getMetadata());
		}
	}

	private String getRedirectionUrl(ProposalDetails proposalDetails){
		logger.info("Getting redirection URL for transactionId {}", proposalDetails.getTransactionId());
		StringBuilder urlBuilder = new StringBuilder();
		String sourceChannel = DIY_JOURNEY_TYPE;
		urlBuilder.append(urlConfig.getUrlDetails().get(URL_NEO_DOMAIN))
				.append("/journey/MQ==?source=")
				.append(getEncodedString(CHANNEL_NEO))
				.append("&transactionId=")
				.append(getEncodedString(String.valueOf(proposalDetails.getTransactionId())));

		String agentId = proposalDetails.getSourcingDetails().getAgentId();
		ResumeJourneyTokenPayload resumeJourneyTokenPayload = new ResumeJourneyTokenPayload(String.valueOf(proposalDetails.getTransactionId()), sourceChannel, agentId, RESUME_DIY_TOKEN_SUBJECT );
		urlBuilder.append("&apitoken=")
				.append(getEncodedString(Utility.generateJwtToken(tokenSecretKey, diyResumeTokenExpiry, resumeJourneyTokenPayload)));
		return urlBuilder.toString();
	}

	public String getEncodedString(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	private Pair<MsgInfo, ProposalDetails> validateRequestData(ResumeJourneyRequest resumeJourneyRequest) {
		String policyNumber = resumeJourneyRequest.getPolicyNumber();
		String requestDob = resumeJourneyRequest.getDob();
		ProposalDetails proposalDetailForPair = new ProposalDetails();

		if (!StringUtils.hasText(requestDob)) {
			logger.error("Invalid dob for resume journey data");
			return Pair.of(buildMsgInfo(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), "Invalid dob"), proposalDetailForPair);
		}

		if (!StringUtils.hasText(policyNumber)) {
			logger.error("Invalid policyNumber for resume journey data");
			return Pair.of(buildMsgInfo(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(),"Invalid policyNumber"), proposalDetailForPair);
		}

		logger.info("Request process with policyNumber {} and dob {}", policyNumber, requestDob);
		ProposalDetails proposalDetails = proposalRepository.findByApplicationDetailsPolicyNumber(policyNumber);

		if (proposalDetails == null) {
			logger.error("Record not found in DB for the policy number {}", policyNumber);
			return Pair.of(buildMsgInfo(NOT_FOUND.value(),"Policy " + NOT_FOUND.getReasonPhrase(),"Record not found for policy number " + policyNumber), proposalDetailForPair);
		}

		String messageToResponse = checkPolicyBelongsToDIYJourney(proposalDetails, null);
		if (StringUtils.hasText(messageToResponse)) {
			logger.error("Record found in DB but not related to DIY journey for the policy number {}", policyNumber);
			return Pair.of(buildMsgInfo(NOT_FOUND.value(),NOT_FOUND.getReasonPhrase(),messageToResponse), proposalDetailForPair);
		}

		messageToResponse = checkPolicyNotPushedToDolphin(proposalDetails, messageToResponse);
		if (StringUtils.hasText(messageToResponse)) {
			logger.error("Policy is already pushed to Dolphin for the policy number {}", policyNumber);
			return Pair.of(buildMsgInfo(OK.value(),PUSHED_TO_DOLPHIN,messageToResponse), proposalDetailForPair);
		}

		Date proposerDate = proposalDetails.getPartyInformation().get(0).getBasicDetails().getDob();
		logger.info("Proposer DOB from DB is {} and from request {}", proposerDate, requestDob);

		try {
			Date dateFromRequest = new SimpleDateFormat(DATE_FORMAT).parse(requestDob);
			if (dateFromRequest.equals(proposerDate)) {
				return Pair.of(buildMsgInfo(OK.value(),STATUS_SUCCESS,"Response generated successfully."), proposalDetails);
			} else {
				return Pair.of(buildMsgInfo(NOT_FOUND.value(),"DOB " + NOT_FOUND.getReasonPhrase(),"DOB not matched with the proposer DOB"), proposalDetailForPair);
			}
		} catch (ParseException e) {
			logger.error("Error while parsing date {}", Utility.getExceptionAsString(e));
			return Pair.of(buildMsgInfo(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase(),"Exception during Date Parsing"), proposalDetailForPair);
		}
	}

	private String checkPolicyNotPushedToDolphin(ProposalDetails proposalDetails, String messageToResponse) {
		messageToResponse = !STAGES_BEFORE_DOLPHIN_PUSH.contains(proposalDetails.getApplicationDetails().getStage()) ? "Policy is already pushed to Dolphin" : null;
		return messageToResponse;
	}

	private MsgInfo buildMsgInfo(int msgCode, String msg, String msgDescription) {
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsgCode(String.valueOf(msgCode));
		msgInfo.setMsg(msg);
		msgInfo.setMsgDescription(msgDescription);
		return msgInfo;
	}

	private String checkPolicyBelongsToDIYJourney(ProposalDetails proposalDetails, String messageToResponse) {
		messageToResponse = DIY_JOURNEY_TYPE.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource()) ? null : "Policy "+proposalDetails.getApplicationDetails().getPolicyNumber()+" not related to DIY journey";
		return messageToResponse;
	}

	private <T> GenericApiResponse<T> buildResponse(T response, MsgInfo msgInfo, Metadata metadata){
		GenericApiResponse<T> genericApiResponse = new GenericApiResponse<>();
		ApiResponseData<T> apiResponseData = new ApiResponseData<>();
		apiResponseData.setPayload(response);
		apiResponseData.setMsgInfo(msgInfo);
		apiResponseData.setMetadata(metadata);
		genericApiResponse.setResponse(apiResponseData);
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse<UIResponsePayload> getEkycBrmsDetails(GenericApiRequest<UIRequestPayload> request) throws UserHandledException {
		if(Utility.isAnyObjectNull(request, request.getRequest(), request.getRequest().getPayload())){
			logger.error(invalidMessage);
			return buildResponse(null, new MsgInfo("Request Invalid", BAD_REQUEST_CODE, "Bad Request"), null);
		}
		try {
			logger.info("Validation API request completed ");
			ObjectMapper objectMapper = new ObjectMapper();
			UIRequestPayload requestPayload = objectMapper.convertValue(request.getRequest().getPayload(), UIRequestPayload.class);
			return buildResponse(constructPayloadAndCallBrmsAPI(requestPayload), new MsgInfo("Success", SUCCESS_RESPONSE, "Success"), request.getRequest().getMetadata());
		} catch (Exception e){
			logger.error("Exception occurred while calling BRMS API getEkycBrmsDetails {}",Utility.getExceptionAsString(e));
			return buildResponse(null, new MsgInfo(e.getMessage(), INTERNAL_SERVER_ERROR_CODE, AppConstants.INTERNAL_SERVER_ERROR), null);
		}
	}

	private UIResponsePayload constructPayloadAndCallBrmsAPI(UIRequestPayload requestPayload) throws UserHandledException{
		logger.info("Constructing request for BRMS API with requestPayload");
		BRMSEkycResponse response = null;
		BRMSEkycRequest brmsEkycRequest = constructRequest(requestPayload);
		boolean isFeatureEnable = FeatureFlagUtil.isFeatureFlagEnabled("brmsEkycModularityRedisEnable");
		if(StringUtils.hasLength(uniqueRedisKey) && isFeatureEnable){
			logger.info("Unique key for BRMS EKYC is {}", uniqueRedisKey);
			response = getResponseFromRedis(uniqueRedisKey);
			if(response != null){
				return constructUIResponse(response);
			}
		}
		try {
			HttpHeaders headers = setHeaders();
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<BRMSEkycRequest> httpEntity = new HttpEntity<>(brmsEkycRequest, headers);
			logger.info("Calling BRMS API with URL {}", ekycModularityUrl);
			response = restTemplate
					.exchange(ekycModularityUrl, HttpMethod.POST, httpEntity, BRMSEkycResponse.class)
					.getBody();
			logger.info("Response from BRMS API is {}", response);
			if(isFeatureEnable){
				setResponseToRedisForBrmsEkyc(response, Integer.valueOf(brmsEkycModularityExpireTime), uniqueRedisKey);
			}
			return constructUIResponse(response);
		}catch (Exception e){
			logger.error("Exception occurred while calling BRMS API constructPayloadAndCallBrmsAPI {}", Utility.getExceptionAsString(e));
			throw new UserHandledException(Arrays.asList(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public BRMSEkycResponse getResponseFromRedis(String key) {
		logger.info("Service initiate to get response from redis for brms ekyc with key {}", key);
		try {
			Object response = redisTemplate.opsForValue().get(key);
			if (response != null){
				logger.info("Response from redis for brms ekyc modularity is found");
				return new ObjectMapper().readValue((String)response,BRMSEkycResponse.class);
			}
		}catch (Exception ex){
			logger.error("Exception during reading redis token ", ex);
		}
		return null;
	}
	public String setResponseToRedisForBrmsEkyc(Object value, int expire, String redisKey) {
		try{
			if(org.springframework.util.StringUtils.hasText(redisKey)){
				logger.info("Service initiate to save BRMS EKYC response into redis for next {}sec", expire);
				String jsonStr = objectMapper.writeValueAsString(value);
				redisTemplate.opsForValue().set(redisKey, jsonStr);
				redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
				return "Success";
			}
		}catch (Exception e){
			logger.error("Exception occurred while setting the token {}", Utility.getExceptionAsString(e));
		}
		return "Failure";
	}

	private UIResponsePayload constructUIResponse(BRMSEkycResponse response){
		UIResponsePayload uiResponsePayload = new UIResponsePayload();
		uiResponsePayload.setStatus(response.getStatus());
		BRMSEkycResponseOutput brmsEkycResponseOutput = response.getOutput().getEkycOutput();
		BRMSEkycOutput brmsEkycOutput = new BRMSEkycOutput();
		brmsEkycOutput.setEkycOutput(brmsEkycResponseOutput);
		uiResponsePayload.setOutput(brmsEkycOutput);
		logger.info("Constructed response for BRMS API completed is {}", uiResponsePayload);
		return uiResponsePayload;
	}

	private BRMSEkycRequest constructRequest(UIRequestPayload requestPayload){
		BRMSEkycRequestMember brmsEkycRequestMember = new BRMSEkycRequestMember();
		BRMSEkycRequestInput brmsEkycRequestInput = new BRMSEkycRequestInput();
		BRMSEkycRequestOutput brmsEkycRequestOutput = new BRMSEkycRequestOutput();
		BRMSEkycRequest brmsEkycRequest = new BRMSEkycRequest();
		brmsEkycRequestMember.setType("10");
		Map<String,String> memberData = new LinkedHashMap<>();
		manageInputMapForRequest(requestPayload, memberData);
		uniqueRedisKey = constructRedisKeyForEkyc(memberData);
		brmsEkycRequestInput.setMember(getBRMSEkycRequestMemberList(memberData));
		brmsEkycRequestInput.setClassIsArray("N");
		brmsEkycRequestInput.setClassName("ekyc_input");
		brmsEkycRequestOutput.setClassIsArray("N");
		brmsEkycRequestOutput.setClassName("ekyc_output");
		manageOutputMapForRequest(memberData);
		brmsEkycRequestOutput.setMember(getBRMSEkycRequestMemberList(memberData));
		brmsEkycRequest.setInput(brmsEkycRequestInput);
		brmsEkycRequest.setOutput(brmsEkycRequestOutput);
		logger.info("Constructed request for BRMS API completed is {}", brmsEkycRequest);
		return brmsEkycRequest;
	}

	private void manageOutputMapForRequest(Map<String, String> memberData) {
		memberData.clear();
		memberData.put(ekycInsured, NA);
		memberData.put(ekycPayor, NA);
		memberData.put(ekyProposerc, NA);
	}

	private void manageInputMapForRequest(UIRequestPayload requestPayload, Map<String, String> memberData) {
		memberData.put(channel, requestPayload.getChannel()==null ? NA : requestPayload.getChannel());
		memberData.put(channelSource, requestPayload.getChannelSource()==null ? NA : requestPayload.getChannelSource());
		memberData.put(goodec, requestPayload.getGoCode()==null ? NA : requestPayload.getGoCode());
		memberData.put(journeyTypeDiy, requestPayload.getJourneyTypeDiy()==null ? NA : requestPayload.getJourneyTypeDiy());
		memberData.put(physicalJourneyEnabled, requestPayload.getPhysicalJourneyEnabled()==null ? NA : requestPayload.getPhysicalJourneyEnabled());
	}
	private String constructRedisKeyForEkyc(Map<String,String> memberData){
		logger.info("Constructing redis key for BRMS API with memberData");
		StringBuilder key = new StringBuilder();
		key.append(BRMS_EKYC_UTM_PREFIX);
		memberData.entrySet().stream().forEach(entry -> {
			if(entry.getKey().equalsIgnoreCase(goodec)){
				key.append(goCodeAlphabeticBased(entry.getValue()));
				return;
			}
			key.append(entry.getValue());
		});
		return key.toString();
	}

	private String goCodeAlphabeticBased(String value){
		logger.info("Getting goCodeAlphabeticBased for value {}", value);
		char[] chars = value.toCharArray();
		if(Character.isAlphabetic(chars[1])){
			return value.substring(0,2);
		} else if(Character.isAlphabetic(chars[0])){
			return value.substring(0,1);
		} else {
			return value;
		}
	}

	// get BRMSEkycRequestMember list using memberData map
	private List<BRMSEkycRequestMember> getBRMSEkycRequestMemberList(Map<String,String> memberData){
		List<BRMSEkycRequestMember> brmsEkycRequestMemberList = new ArrayList<>();
		memberData.entrySet().stream().forEach(entry -> {
			BRMSEkycRequestMember brmsEkycRequestMember = new BRMSEkycRequestMember();
			brmsEkycRequestMember.setType("10");
			brmsEkycRequestMember.setName(entry.getKey());
			brmsEkycRequestMember.setValue(entry.getValue());
			brmsEkycRequestMemberList.add(brmsEkycRequestMember);
		});
		return brmsEkycRequestMemberList;
	}
	@Override
	public TraceIdResponse getTransactionIdByTraceId(TraceIdRequest traceIdRequest){
		String traceId = traceIdRequest.getRequest().getRequestData().getRequestPayload().getTraceId();
		if (traceId == null || traceId.trim().isEmpty()) {
			return createErrorResponse("400", "Trace ID cannot be null or empty.",traceIdRequest.getRequest().getMetadata());
		}
		logger.info("Request is getting to get transactionId for traceId",traceId);
		Long transactionId = getTransactionIdByTraceId(traceId);

		if (transactionId != null) {
			return createSuccessResponse(transactionId, traceIdRequest.getRequest().getMetadata());
		} else {
			return createErrorResponse("204", "Transaction ID not found for the given trace ID.",traceIdRequest.getRequest().getMetadata());
		}
	}
	public Long getTransactionIdByTraceId(String traceId) {
		try {
			return proposalRepository.findTransactionIdByTraceId(traceId)
					.map(ProposalDetails::getTransactionId)
					.orElse(null);
		} catch (Exception e) {
			logger.error("Error fetching transaction ID for trace ID {}: {}", traceId, e.getMessage());
			return null;
		}
	}
	private TraceIdResponse createErrorResponse(String errorCode, String message,Metadata metadata) {
		TraceIdResponse traceIdResponse = new TraceIdResponse();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorCode);
		errorResponse.setMessage(message);
		traceIdResponse.setErrorResponse(errorResponse);
		traceIdResponse.setResponseData(null);
		traceIdResponse.setMetadata(metadata);
		return traceIdResponse;
	}
	private TraceIdResponse createSuccessResponse(Long transactionId,Metadata metadata) {
		TraceIdResponse traceIdResponse = new TraceIdResponse();
		com.mli.mpro.samlTraceId.ResponseData responseData = new com.mli.mpro.samlTraceId.ResponseData();
		com.mli.mpro.samlTraceId.ResponsePayload responsePayload = new com.mli.mpro.samlTraceId.ResponsePayload();

		responsePayload.setTransactionId(transactionId);
		responseData.setResponsePayload(responsePayload);
		traceIdResponse.setResponseData(responseData);
		traceIdResponse.setErrorResponse(null);
		traceIdResponse.setMetadata(metadata);
		return traceIdResponse;
	}
}
