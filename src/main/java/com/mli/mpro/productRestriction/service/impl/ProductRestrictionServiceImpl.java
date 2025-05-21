package com.mli.mpro.productRestriction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.EncryptionRequest;
import com.mli.mpro.common.models.EncryptionResponse;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.configuration.models.CIRiderAgeSA;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.service.impl.PersonalDetailsMapper;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.productRestriction.exception.CustomErrorMessage;
import com.mli.mpro.productRestriction.models.InputRequest;
import com.mli.mpro.productRestriction.models.ResponseData;
import com.mli.mpro.productRestriction.models.proposalFormRequestModels.OutputRequest;
import com.mli.mpro.productRestriction.service.loader.SchemaFactory;
import com.mli.mpro.productRestriction.service.loader.ValidationFactory;
import com.mli.mpro.productRestriction.util.ProposalUtil;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.RiderDetails;
import com.mli.mpro.productRestriction.models.Date;
import com.mli.mpro.productRestriction.models.ResponsePayload;
import com.mli.mpro.productRestriction.models.*;
import com.mli.mpro.productRestriction.repository.LimitationDataRepository;
import com.mli.mpro.productRestriction.repository.ProductRestrictionRepository;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.repository.RestrictionRepository;
import com.mli.mpro.productRestriction.service.ProductRestrictionService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.*;
import io.jsonwebtoken.security.InvalidKeyException;
import com.networknt.schema.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.utils.Utility.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.springframework.http.HttpStatus;
import com.mli.mpro.productRestriction.models.common.ErrorResponse;
import org.springframework.web.client.RestTemplate;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.config.BeanUtil;
import org.springframework.util.CollectionUtils;
import com.mli.mpro.location.models.clientPolicyDetailsResponseModels.OutputResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import com.mli.mpro.location.models.clientPolicyDetailsRequestModels.Request;
import com.mli.mpro.neo.models.Header;
import com.mli.mpro.location.models.clientPolicyDetailsRequestModels.Payload;
import com.mli.mpro.productRestriction.models.common.ErrorResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import static com.mli.mpro.productRestriction.util.AppConstants.HIGH_SCHOOL_CAPITAL;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * @author Monocept
 */
@Service
public class ProductRestrictionServiceImpl implements ProductRestrictionService {

    private static final Logger logger = LoggerFactory.getLogger(ProductRestrictionServiceImpl.class);
    private ProductRestrictionRepository productRestrictionRepository;
    private RestrictionRepository restrictionRepository;
    private LimitationDataRepository limitationDataRepository;
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ProposalUtil proposalUtil;
    @Autowired
    private CustomErrorMessage customErrorMessage;
    private ErrorMessageConfig errorMessageConfig;
    @Autowired
    private PersonalDetailsMapper personalDetailsMapper;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SchemaFactory schemaFactory;
    @Autowired
    private ValidationFactory validationFactory;
    @Autowired
    private ExternalServiceConfig config;

    @Autowired
    private RSAEncryptionDecryptionUtil rsaEncryptionDecryptionUtil;

    @Autowired
    private UtilityService utilityService;
    @Autowired
    SoaCloudService soaCloudService;
    @Value("${urlDetails.clientPolicyDetails.dataLakeURL}")
    private String cpdDataLakeUrl;

    private String channel = null;

    String[] keyAndIVParts = null;


    //FUL2-13894 Include senior school in underGraduateEducations
    private static final List<String> underGraduateEducations = Collections.unmodifiableList(Arrays.asList(AppConstants.SENIOR_SCHOOL,"ILLITERATE", "PRIMARY SCHOOL", HIGH_SCHOOL_CAPITAL, "DIPLOMA"));
    private static final List<String> GraduateAndaboveGraduateEducations = Collections.unmodifiableList(Arrays.asList("GRADUATE", "POST GRADUATE", "PROFESSIONAL"));
    private static final List<String> educations = Collections.unmodifiableList(Arrays.asList("Illiterate","Primary School","High School","Senior School","Graduate","Post Graduate","Professional","Diploma"));
    private static final List<String> occupations = Collections.unmodifiableList(Arrays.asList("Professional","Salaried","Student","Self Employed From Home","Agriculture","Housewife","Retired","Others","Self Employed"));
    private static final List<String> axisBurgundyCodes = Collections.unmodifiableList(Arrays.asList("SBPRV","SBPRS","NREPV","NROPV","BGFRN","WSSTF","BSNRE","BSNRO"));
    private static final List<String> axisPriorityCodes = Collections.unmodifiableList(Arrays.asList("SBPBG","SBPBS","PBSPA","PBNRE","PBNRO","NRPBS","PSNRE","PSNRO","PBFRN","CAPBG"));
    /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1 : To Handle the scenario of annual income of specific customer classification*/

    // FUL2-5522 OTP and SMTP product restriction for CAT and Sparc
    // Adding String Literals for Logger
    private static final String COMMUNICATION_CITY_LOG = "Restrict Communication City {} is not allowed";
    public static final String CONTENT_TYPE ="Content-Type";
    private static final String VALUE = " with value ";
    private static final String ERR_MSG = "Something went wrong";
    private static final String COMMUNICATION_CITY_MSG = "Restrict Communication City | ";
   private static final String OCCUPATION_MSG = "Occupation | ";
   private static final String INSURED_OCCUPATION_LOG = "Insured Occupation {} is not allowed";
    private static final String PROPOSER_EDUCATION_MSG = "Proposer education | ";
    private static final String PROPOSER_EDUCATION_LOG = "Proposer education {} is not allowed";
    private static final String INSURED_EDUCATION_MSG = "Insured education | ";
    private static final String INSURED_EDUCATION_LOG = "Insured education {} is not allowed";
    //FUL2-52018
    private static final String AGENT_CODE_MSG = "Agent code | ";
    private static final String AGENT_CODE_LOG = "Agent code {} is not allowed";

    private static final Logger log = LoggerFactory.getLogger(ProductRestrictionServiceImpl.class);
    private List<String> messages;
    private List<String> errorMessages;
    private List<String> errorFields;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    private Map<String, ArrayList<Date>> agentCodeProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> pinCodeProductRestrictionMasterDataMap = null;
    //FUL2-8502 Product Restrictions - Pin codes for Term Plans
    private Map<String, ArrayList<Date>> pinCode1CRSumAssuredProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> pinCode2CRSumAssuredProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> pinCodeSjb5LacSumAssuredProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> countryProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> countryWithCIRiderProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> countryWithWOPRiderProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> countryWithADBRiderProductRestrictionMasterDataMap = null;
    List<String> cities = null;
    private List<String> sspNriCountries = null;
    private List<String> fatfCountries = null;
    //FUL2-103947
    private List<String> CIRiderCodesList=null;
    private List<String> CIRiderStatusCodes=null;
    private  List<CIRiderAgeSA> ageGroupMaxSumAssured=null;
    private double CIMaxSumAssured;
    private double CIMinSumAssured;
    private static final List<String> exceptionalCustomerClassification = Collections.unmodifiableList(Arrays.asList("Axis Burgundy","Axis Priority","Yes Premia","Yes First"));

    private static final String PROPOSER_INCOME_LOG = "Proposer annual income {} is not allowed";
    private static final String INSURED_INCOME_LOG = "Insured annual income {} is not allowed";
    private static final String PROPOSER_INCOME_MSG = "Proposer annual income | ";
    private static final String INSURED_INCOME_MSG = "Insured annual income | ";

    //FUL2-53692
    private Map<String, ArrayList<Date>> instaProtectPincodeProductRestrictionMasterDataMap = null;
    private Map<String, ArrayList<Date>> instaProtectAgentCodeProductRestrictionMasterDataMap = null;
    private static final String SUM_ASSURED_MSG = "Sum Assured | ";
    private static final String SUM_ASSURED_GREATER_MSG = "Sum Assured > | ";
    private static final String ANNUAL_INCOME_LESS_MSG = "Annual Income < | ";
    private static final String SUM_ASSURED_LOG = "Sum Assured {} is not allowed";
    private static final String PROPOSER_OCCUPATION_MSG = "Proposer Occupation | ";
    private static final String PROPOSER_OCCUPATION_LOG = "Proposer Occupation {} is not allowed";
    private static final String INSURED_OCCUPATION_MSG = "Insured Occupation | ";
    // private static final String INSURED_OCCUPATION_LOG = "Insured Occupation {} is not allowed";
    private static final String COMMUNICATION_PINCODE_MSG = "Communication Pin code | ";
    private static final String COMMUNICATION_PINCODE_LOG = "Communication Pin code {} is not allowed";
    private static final String POS_HEALTH_MSG = "The response for Medical, Health and Lifestyle may be Negative or Industry type selected as Marine,CRPF, Mining etc. Please verify the response to proceed";
    private static final String AGENCY_YBL_POS_MSG = "The response for Lifestyle may be Negative or Industry type selected as Marine, CRPF, Mining etc. Please verify the response to proceed.";
    private static final String PINCODE_ERROR_LOG_1CR = "Pin Code 1 CR Sum Assured restriction data not available in DB";
    private static final String PINCODE_ERROR_LOG_2CR = "Pin Code 2 CR Sum Assured restriction data not available in DB";
    //JIRA 53306 new changes below 5L income for SSP
    private static final String INSURED_AGE_MSG= "Insured Age | ";
    private static final String PROPOSER_AGE_MSG= "Proposer Age | ";
    private static final int ANNUAL_INCOME_3L= 300000;
    private static final int ANNUAL_INCOME_5L= 500000;
    private static final int ANNUAL_INCOME_10L= 1000000;
    private static final double SUM_ASSURED_25L= 2500000;
    private static final double SUM_ASSURED_50L= 5000000;
    private static final int AGE_18= 18;
    private static final int AGE_40= 40;
    private EmailSmsService emailService;
    @Autowired
    public ProductRestrictionServiceImpl(ProductRestrictionRepository productRestrictionRepository, RestrictionRepository restrictionRepository,
                                         LimitationDataRepository limitationDataRepository,
                                         ErrorMessageConfig errorMessageConfig,EmailSmsService emailService) {
        this.productRestrictionRepository = productRestrictionRepository;
        this.restrictionRepository = restrictionRepository;
        this.limitationDataRepository=limitationDataRepository;
        this.errorMessageConfig = errorMessageConfig;
        this.emailService=emailService;
    }

    /**
     * Note - In below validations wherever Feature Flag - DISABLESTOPRULE is used, those conditions are disabled under the
     * Epic:FUL2-144984 - All Product StopRule Removal
     */

    //FUL2-64466
    private Map<String, ArrayList<Date>> flexiProtectPinCodeProductRestrictionMasterDataMap = null;

    private List<String> cipCountries = null;

    enum TypeOfData {
        AGENT_CODE, PIN_CODE, PIN_CODE_1CR_SUM_ASSURED, PIN_CODE_2CR_SUM_ASSURED, COUNTRY, /*COUNTRY_WITH_CI_RIDER,
        COUNTRY_WITH_WOP_RIDER, COUNTRY_WITH_ADB_RIDER,*/ CITY, INSTA_PROTECT_PIN_CODE, INSTA_PROTECT_AGENT_CODE
    }

    @Value("${urlDetails.clientPolicyDetails}")
    private String cpdUrl;

    @Value("${urlDetails.aws_proposalForm}")
    private String pfRuleUrl;

    @Value("#{${urlDetails.onboarding.clientid.partner.map}}")
    private Map<String, String> clientIdPartnerMap;

    /**
     * Note - In below validations wherever Feature Flag - DISABLESTOPRULE is used, those conditions are disabled under the
     * Epic:FUL2-144984 - All Product StopRule Removal
     */
    @Override
    public List<String> update(String typeOfData, List<String> dataToUpdate, String status) {

        try {
            boolean isExistingType = contains(typeOfData);
            messages = new ArrayList<>();
            if (isExistingType) {
                ProductRestrictionMasterData productRestrictionMasterData = productRestrictionRepository.findByType(typeOfData);

                if (productRestrictionMasterData != null) {
                    saveProductRestrictionMasterData(typeOfData, dataToUpdate, status, productRestrictionMasterData, messages);
                } else {
                    if (status.equalsIgnoreCase(AppConstants.ACTIVATE)) {
                        createProductRestrictionMasterData(typeOfData, dataToUpdate, status, productRestrictionMasterData, messages);
                    } else {
                        log.info("{} with value {} is already deactivate", typeOfData, dataToUpdate);
                    }
                }
            } else {
                log.info("{} is not a valid type. Use a valid type to update data", typeOfData);
                messages.add(typeOfData + " is not valid type. Use a valid type to update data. Valid types are :" + Arrays.asList(TypeOfData.values()));
            }
        } catch (Exception e) {
            log.error("Something went wrong during update data with exception ", e);
            messages.add(ERR_MSG);
        }
        return messages;
    }

    @Override
    public List<String> updateRestrictionData(RestrictionData restrictionData) {
        messages = new ArrayList<>();
        String channel = restrictionData.getChannel();
        String productId = restrictionData.getProductId();
        String customerClassification = restrictionData.getCustomerClassification();
        RestrictionData restrictionDataFromDb = restrictionRepository.findByProductIdAndChannelAndCustomerClassification(productId,channel,customerClassification);
        RestrictionData mergedRestrictionData = null;
        if(restrictionDataFromDb!=null) {
            List<String> occupationsToUpdate = restrictionData.getOccupations();
            List<String> educationsToUpdate = restrictionData.getEducations();
            if(occupationsToUpdate!=null && !occupationsToUpdate.isEmpty() && !occupations.containsAll(occupationsToUpdate)){
                messages.add("Please enter valid occupations");
            } else if(educationsToUpdate!=null && !educationsToUpdate.isEmpty() && !educations.containsAll(educationsToUpdate)){
                messages.add("Please enter valid educations");
            } else {
                mergedRestrictionData = Utility.mergeJsonObjects(restrictionData, restrictionDataFromDb);
                restrictionRepository.save(mergedRestrictionData);
                messages.add("Data merged successfully");
            }
        } else {
            messages.add("No data found in DB");
        }
        return messages;
    }

    @Override
    public ValidateProposalDataResponse validateProposalData(ProductRestrictionPayload productRestrictionPayload) {

        ValidateProposalDataResponse validateProposalDataResponse = new ValidateProposalDataResponse();

        String customerClassification = null;
        RestrictionData restrictionData = null;
        ResponsePayload responsePayload = null;
        messages = new ArrayList<>();
        try {

            List<ProductRestrictionMasterData> productRestrictionMasterDataList = productRestrictionRepository.findAll();
            cities = new ArrayList<>();
            log.info("Setting ProductRestrictionMasterDataMap");
            for (ProductRestrictionMasterData productRestrictionMasterData : productRestrictionMasterDataList) {
                String type = productRestrictionMasterData.getType();
                switch (type) {
                    case AppConstants.AGENT_CODE :
                        agentCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
                    case AppConstants.PIN_CODE :
                        pinCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
                    case AppConstants.PIN_CODE_1CR_SUM_ASSURED :
                        pinCode1CRSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
                    case AppConstants.PIN_CODE_2CR_SUM_ASSURED :
                        pinCode2CRSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
                    case AppConstants.PIN_CODE_SJB_5L_SUM_ASSURED:
                        pinCodeSjb5LacSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
                    case AppConstants.COUNTRY :
                        countryProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                        break;
//                    case AppConstants.COUNTRY_WITH_CI_RIDER :
//                        countryWithCIRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                        break;
//                    case AppConstants.CITY :
//                        cities = productRestrictionMasterData.getDataMap().keySet().stream().collect(Collectors.toList());
//                        break;
//                    case AppConstants.COUNTRY_WITH_WOP_RIDER :
//                        countryWithWOPRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                        break;
//                    case AppConstants.COUNTRY_WITH_ADB_RIDER :
//                        countryWithADBRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                        break;
                    case AppConstants.SPP_NRI_COUNTRY :
                        sspNriCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
                        break;
                    case AppConstants.FATF_COUNTRY :
                        fatfCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
                        break;
                    case AppConstants.INSTA_PROTECT_PIN_CODE :
                    	instaProtectPincodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    	break;
                    case AppConstants.INSTA_PROTECT_AGENT_CODE :
                    	instaProtectAgentCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    	break;
//                    case AppConstants.FLEXI_PROTECT_RESTRICTED_PIN_CODE :
//                    	flexiProtectPinCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                    	break;
//                    case AppConstants.CIP_COUNTRY :
//                        cipCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
//                        break;
                    default:
                        log.info("No ProductRestrictionMasterDataMap found ");
                }
            }

            String productId = productRestrictionPayload.getProductId();
            errorMessages = new ArrayList<>();

            boolean isSspSwissReCase = false;
            if(productRestrictionPayload.isSspSwissReCase()){
                log.info(" Checking swiss-re case for transaction id {} isSspSwissReCase {}  ", productRestrictionPayload.getTransactionId(), productRestrictionPayload.isSspSwissReCase());
                isSspSwissReCase = productRestrictionPayload.isSspSwissReCase();
            }

            if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && TRUE.equalsIgnoreCase(productRestrictionPayload.getIsCIRiderSelected())) {
                Configuration configurationData = configurationRepository.findByType(CI_RIDER_CODES);

                if (Objects.nonNull(configurationData)) {
                    CIRiderSAFunction(productRestrictionPayload, messages, configurationData);
                }
            }



            /*FUL2-48288 Restriction of FATF countries in mPRO journey */
            if( Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& Boolean.TRUE.equals(productRestrictionPayload.getFatfCountryFlag())){
                checkFatfCountry(productRestrictionPayload, messages);
                //FUL2-83504 CIP - NRI Country Stop Rules(on screen 2 proceed button)
                if(Utility.andTwoExpressions(!isEmpty(productId), productId.equals(AppConstants.CIP_PRODUCT_ID))
                        && !productRestrictionPayload.isPosSeller()){  //FUL2-159877 Enable product validation in form-c for CIP
                    validateCIPCountryList(productRestrictionPayload, messages);
                }
            }
                /* FUL2-9472 WLS Product Restriction : Build new logic for WLS removed the previous one*/
            /*
            F21-578 WLP City and GIP Sourcing Restriction Logic: restrict the cities for channel AXIS and AGENCY of Whole life Super (Any Variant), Guaranteed Income Plan (Any Variant)
             */
            else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& Utility.orTwoExpressions(AppConstants.GIP_PRODUCT_ID.equals(productId),AppConstants.WLS_PRODUCT_ID.equals(productId))) {
                messages = null;
                responsePayload = validateProductLimitationData(productRestrictionPayload);
                if (responsePayload != null) {
                    messages = checkFrequency(responsePayload.getMessages());
                    validateProposalDataResponse = setValidateProposalDataResponse(validateProposalDataResponse, responsePayload);
                }
            }
            /*
            FUL2-5523 Enabling CIP Plan for NRI Customers : created the validateCIPProductRestrictionData for Enabling Country Restriction for CIP.
            */ else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(productId)) {
                messages = new ArrayList<>();
                if (AppConstants.TWO.equalsIgnoreCase(productRestrictionPayload.getCurrentScreen())) {
                    messages = validateCIPProductRestrictionData(productRestrictionPayload);
                }
                //FUL2-83504 CIP - BMI and Medical Stop Rules(on screen 4 proceed button)
                validateStopRulesForCIP(productRestrictionPayload);

            }else if (Utility.or(AppConstants.SMTP_PRODUCT_ID.equals(productId), (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && AppConstants.OTP_PRODUCT_ID.equals(productId)), SSP_PRODUCT_ID.equals(productId) ,(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && FPS_PRODUCT_ID.equals(productId)))) {
                    messages = new ArrayList<>();
                    errorFields = new ArrayList<>();
                    channel = getChannel(productRestrictionPayload);
                    setChannel(productRestrictionPayload);
                    productRestrictionPayload.setChannel(channel);

                if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))) {
                    /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1*/
                    customerClassification = setCustomerClassification(productRestrictionPayload);
                    productRestrictionPayload.setCustomerClassification(customerClassification);
                    //setting channel and customerClassification as "None" to fetch restriction data for flexi product solution : Ful2-64466
                    channel = setFPSChannelAndCustomerClassificationAsNone(productId, channel);
                    customerClassification = setFPSChannelAndCustomerClassificationAsNone(productId, customerClassification);
                }
                restrictionData = restrictionRepository.findByProductIdAndChannelAndCustomerClassification(productId, channel, customerClassification);
                //FUL2-26978 updating the proposer values for insured values in form 3 scheme A(here we are capturing the insurer values for Scheme A)
				updatingForm3DataForSchemeA(productRestrictionPayload);
                messages = validateProductRestrictionData(productRestrictionPayload,restrictionData, messages, isSspSwissReCase);
                messages = validateRestrictionData(productRestrictionPayload,restrictionData,messages, isSspSwissReCase);


            }
            else if (Utility.andTwoExpressions(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& !isEmpty(productId), AppConstants.SJB_PRODUCT_ID.equals(productId))) {
            	validateSJBAndAddMessages(productRestrictionPayload,messages);
            }
			// FUL2-14592/14927 Health questions validation for POS seller
            else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& productRestrictionPayload.isPosSeller()) {
                long transactionId = productRestrictionPayload.getTransactionId();
                ProposalDetails proposalDetails = proposalRepository.findByTransactionId(transactionId);
                String currentScreen = productRestrictionPayload.getCurrentScreen();
                //BMI calculation issue if we execute in 2nd screen due to height and weight is 0, so don't execute in 2nd screen
                if(!"2".equalsIgnoreCase(currentScreen)) {
                    validatePosvQuestions(productRestrictionPayload, proposalDetails, messages, errorMessages);
                }
                // changes done for FUL2-14801,14805; proposal rejection notification;trigger email notification to Seller & customer
                sendEmailAndSmsIfApplicable(messages, productRestrictionPayload);

                validateSumAssuredForPosSeller(proposalDetails, productRestrictionPayload,errorMessages,messages);
            }
            //FUL2-84431 POS-System should not allow POS with NRI country having CRA address
            if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && (Boolean.TRUE.equals(productRestrictionPayload.isPosSeller())) && (AppConstants.INDIAN_NATIONALITY.equalsIgnoreCase(productRestrictionPayload.getNationality())
                    && (!AppConstants.INDIA_COUNTRY.equalsIgnoreCase(productRestrictionPayload.getCommunicationCountry()) || !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(productRestrictionPayload.getPermanentCountry())))){
                messages.add("country other than India");
                log.info("Value selected for country other than India is not valid for this plan. communication country: {}, permanent country: {}",productRestrictionPayload.getCommunicationCountry(),productRestrictionPayload.getPermanentCountry());
            }


            // FUL2 - 147357 WOP Rider not allowed for NRI Customers
            else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))){
                ProposalDetails proposalDetails = proposalRepository.findByTransactionId(productRestrictionPayload.getTransactionId());
                String productType = proposalDetails!= null && proposalDetails.getProductDetails() != null ? proposalDetails.getProductDetails().get(0).getProductType(): "";

                if(personalDetailsMapper.isTraditionalProposalForm(productType, productId) && NRI.equalsIgnoreCase(productRestrictionPayload.getNationality()) ) {
                    boolean isWOPRider = false;
                    boolean isSSESProduct = false;
                    String permanentCountry = getPermanentCountry(productRestrictionPayload);
                    String communicationCountry = getCommCountry(productRestrictionPayload);
                    isSSESProduct = Utility.isSSESProduct(productId, productRestrictionPayload.getIsSSESProduct(), productRestrictionPayload.getSSESSolveOption());
                    isWOPRider = productRestrictionPayload.isWOPPlusRider();
                    if (isWOPRider && !isSSESProduct) {
                        if ((permanentCountry!=null && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(permanentCountry)) || (communicationCountry!=null && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(communicationCountry)) ) {
                            if(communicationCountry!=null && !communicationCountry.isEmpty() && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(communicationCountry)) {
                                messages.add(AppConstants.COMMUNICATION_COUNTRY + communicationCountry);
                                log.info("Communication Country {} is not allowed", communicationCountry);
                            }
                            if(permanentCountry!=null && !permanentCountry.isEmpty() && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(permanentCountry)) {
                                messages.add(AppConstants.PERMANENT_COUNTRY + permanentCountry);
                                log.info("Permanent Country {} is not allowed", permanentCountry);
                            }
                        }
                    }
                }
            }



        } catch (Exception e) {
            log.error("Something went wrong! Exception occurred while validateProposalData {}", Utility.getExceptionAsString(e));
            errorMessages.add(ERR_MSG);
        }
        List<String> uniqueMessageList=new ArrayList<>();
        if(nonNull(messages)) {
            for (String msg : messages) {
                if (!uniqueMessageList.contains(msg)) {
                    uniqueMessageList.add(msg);
                }
            }
        }
        validateProposalDataResponse.setFailedMessages(errorMessages);
        validateProposalDataResponse.setErrorFields(errorFields);
        validateProposalDataResponse.setRestrictionMessages(uniqueMessageList);
        return validateProposalDataResponse;
    }

    @Override
    public RequestResponse validateProduct(RequestResponse inputPayload,@RequestHeader MultiValueMap<String, String> headerMap, ErrorResponseParams errorResponseParams) throws JsonProcessingException, UserHandledException {
        RequestResponse payload = new RequestResponse();
        ValidateProposalDataResponse validateProposalDataResponse = new ValidateProposalDataResponse();
        ProductRestrictionPayload productRestrictionPayload = new ProductRestrictionPayload();
        InputRequest inputRequest = null;
        com.mli.mpro.productRestriction.models.proposalFormRequestModels.OutputRequest request = null;
        com.mli.mpro.productRestriction.models.proposalFormResponseModels.OutputResponse pfResponse = null;
        String customerClassification;
        List<String> restrictionMessages;
        RestrictionData restrictionData;
        ResponsePayload responsePayload = new ResponsePayload();
        errorMessages = new ArrayList<>();
        messages = new ArrayList<>();
        String encryptionSource=null;
        String decryptedString=null;
        String kek=null;
        try {
            encryptionSource=getEncryptionSource(headerMap);
            if(AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)){
                kek = headerMap.getFirst("kek");
                logger.info("Inside ujjivan utility kek {} " , kek);
                errorResponseParams.setEncryptionSource(encryptionSource);
                decryptedString = decryption(inputPayload.getPayload(), kek,errorResponseParams);
                //utility code for Ujjivan
                logger.info("Inside ujjivan utility decryptedString {} " , decryptedString);
            }
            else {
                decryptedString = Utility.decryptRequest(inputPayload.getPayload());
            }
            inputRequest = deserializeRequest(decryptedString);
            log.info("Payload for InputRequest: {}", inputRequest);

            List<Object> errorList = new ArrayList<>();
            mandatoryValidation(inputRequest, errorList);
            if (!errorList.isEmpty()) {
                return Utility.errorResponse(HttpStatus.BAD_REQUEST, errorList, errorResponseParams.getEncryptionSource(), errorResponseParams.getIVandKey());
            }

            JsonNode jsonNode = objectMapper.readTree(decryptedString);
            Set<ValidationMessage> errorSets= schemaFactory.getSchema(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getProductId(),
                    inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getSource()).validate(jsonNode);
            Set<ErrorResponse> errors= new HashSet<>();
            for (ValidationMessage e : errorSets) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(customErrorMessage.getCustomErrorCodeList().get(e.getMessage().split(":")[0].substring(2)));
                errorResponse.setMessage(customErrorMessage.getCustomErrorMessageList().get(e.getMessage().split(":")[0].substring(2)));
                log.error("Json validation errors for productId {}: {}"
                        , inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getProductId(), e.getMessage());
                errors.add(errorResponse);
            }

            productWiseFunctionalValidation(inputRequest,errors);
            if (!errors.isEmpty()) {
                return Utility.errorResponse(HttpStatus.BAD_REQUEST, new ArrayList<>(errors), errorResponseParams.getEncryptionSource(), errorResponseParams.getIVandKey());
            }
            productRestrictionPayload = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload();

            List<ProductRestrictionMasterData> productRestrictionMasterDataList = productRestrictionRepository.findAll();
            cities = new ArrayList<>();
            log.info("Setting ProductRestrictionMasterDataMap");
            setRestrictionMasterData(productRestrictionMasterDataList);
            String productId = productRestrictionPayload.getProductId();

            /*FUL2-48288 Restriction of FATF countries in mPRO journey */
            if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&& Boolean.TRUE.equals(productRestrictionPayload.getFatfCountryFlag())){
                checkFatfCountry(productRestrictionPayload, messages);
                //FUL2-83504 CIP - NRI Country Stop Rules(on screen 2 proceed button)
                if(Utility.andTwoExpressions(!isEmpty(productId), productId.equals(AppConstants.CIP_PRODUCT_ID))
                        && !(AppConstants.FORM3.equalsIgnoreCase(productRestrictionPayload.getFormType())
                        && !Utility.schemeBCase(productRestrictionPayload.getSchemeType()))
                        && !productRestrictionPayload.isPosSeller()){
                    validateCIPCountryList(productRestrictionPayload, messages);
                }
            }
            else {
                HttpHeaders headersToPass = new HttpHeaders();
                headersToPass.add("x-api-key",config.getUrlDetails().get(CLOUD_X_API_KEY));
                headersToPass.add("x-apigw-api-id",config.getUrlDetails().get(CLOUD_X_APIGW_API_ID));
                headersToPass.add(HttpHeaders.CONTENT_TYPE,"application/json");
                long requestedTime = System.currentTimeMillis();
                request = proposalUtil.setDataForProposalForm(productRestrictionPayload);
                log.info("The request being sent to the Proposal Form soa service {}", request);
                log.info("The request time for Proposal Form soa service {}", requestedTime);
                HttpEntity<OutputRequest> httpEntity = new HttpEntity(request,headersToPass);
                try {
                    pfResponse = new RestTemplate().postForEntity(pfRuleUrl, httpEntity, com.mli.mpro.productRestriction.models.proposalFormResponseModels.OutputResponse.class).getBody();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    log.error("Exception stack trace of BRMS service {}", ex.getMessage());
                    log.error("exception occured for processing request {} ", request);
                    log.error("Proposal form rule service failed to give response {} ", pfResponse);
                }
                log.info("The Response received from BRMS service {}", pfResponse);
                log.info("Total time taken by pf rule api {}",System.currentTimeMillis()-requestedTime);
                String[] kickoutMsg2 = pfResponse.getResponse().getPayload().getKickoutMsg2().split(";");
                String resultFlag = pfResponse.getResponse().getPayload().getResultFlag2();
                if(resultFlag.equalsIgnoreCase(N)){
                    messages = Arrays.asList(kickoutMsg2);
                    if((!pfResponse.getResponse().getPayload().getMaxAllowable().trim().isEmpty() && pfResponse.getResponse().getPayload().getMaxAllowable()!=null) &&
                            (productRestrictionPayload.getSumAssured() > Double.parseDouble(pfResponse.getResponse().getPayload().getMaxAllowable()))) {
                        messages.add("Maximum allowable Sum Assured for base policy is "+pfResponse.getResponse().getPayload().getMaxAllowable());
                    }

                    if((!pfResponse.getResponse().getPayload().getMaxAllowableCIRider().trim().isEmpty() && pfResponse.getResponse().getPayload().getMaxAllowableCIRider()!=null) &&
                            Double.parseDouble(productRestrictionPayload.getCurrentCIRiderSumAssured()) > Double.parseDouble(pfResponse.getResponse().getPayload().getMaxAllowableCIRider())) {
                        messages.add("Maximum allowable Sum Assured for CI Rider is "+pfResponse.getResponse().getPayload().getMaxAllowableCIRider());
                    }

                    if((!pfResponse.getResponse().getPayload().getMaxAllowableAciRider().trim().isEmpty() && pfResponse.getResponse().getPayload().getMaxAllowableAciRider()!=null) &&
                            Double.parseDouble(productRestrictionPayload.getCurrentACIRiderSumAssured()) > Double.parseDouble(pfResponse.getResponse().getPayload().getMaxAllowableAciRider())) {
                        messages.add("Maximum allowable Sum Assured for ACI Rider is "+pfResponse.getResponse().getPayload().getMaxAllowableAciRider());
                    }

                    if((!pfResponse.getResponse().getPayload().getMaxAllowableAcoRider().trim().isEmpty() && pfResponse.getResponse().getPayload().getMaxAllowableAcoRider()!=null) &&
                            Double.parseDouble(productRestrictionPayload.getCurrentACORiderSumAssured()) > Double.parseDouble(pfResponse.getResponse().getPayload().getMaxAllowableAcoRider())) {
                        messages.add("Maximum allowable Sum Assured for ACO Rider is "+pfResponse.getResponse().getPayload().getMaxAllowableAcoRider());
                    }
                }


            }
        } catch (Exception e) {
            log.error(ERR_MSG+"! Exception occurred while validateProduct {}",e.getMessage());
            errorMessages.add("Exception occurred while validateProduct "+e.getMessage());
        }
        List<String> uniqueMessageList=new ArrayList<>();
        for(String msg:messages){
            if(!uniqueMessageList.contains(msg)){
                uniqueMessageList.add(msg);
            }
        }
        validateProposalDataResponse.setFailedMessages(errorMessages);
        validateProposalDataResponse.setErrorFields(errorFields);
        validateProposalDataResponse.setRestrictionMessages(uniqueMessageList);

        com.mli.mpro.productRestriction.models.OutputResponse outputResponse = new com.mli.mpro.productRestriction.models.OutputResponse();
        com.mli.mpro.productRestriction.models.Response response = new com.mli.mpro.productRestriction.models.Response();
        ResponseData responseData = new ResponseData();

        com.mli.mpro.productRestriction.models.ErrorResponse errorResponse = new com.mli.mpro.productRestriction.models.ErrorResponse();
        restrictionMessages = validateProposalDataResponse.getRestrictionMessages().stream().distinct().collect(Collectors.toList());

        errorResponse.setErrorMessages(validateProposalDataResponse.getFailedMessages());
        responsePayload.setMessages(restrictionMessages);
        responsePayload.setErrorFields(validateProposalDataResponse.getErrorFields());
        responsePayload.setShouldEnableDoc(validateProposalDataResponse.getShouldEnableDoc());

        if(restrictionMessages.contains(AppConstants.BLACKLISTED_COUNTRIES)){
            responsePayload.setEligible(false);
        }
        else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && AppConstants.TRUE.equalsIgnoreCase(productRestrictionPayload.getIsCIRiderSelected()) && !AppConstants.productSet.contains(productRestrictionPayload.getProductId())){
            responsePayload.setEligible((errorMessages.isEmpty() && restrictionMessages.isEmpty()));
        }
        else if(Objects.nonNull(pfResponse) && Objects.nonNull(pfResponse.getResponse()) && AppConstants.N.equalsIgnoreCase(pfResponse.getResponse().getPayload().getResultFlag2())) {
            responsePayload.setEligible(false);
        }
        else {
            responsePayload.setEligible(true);
        }
        if (AppConstants.NA.equalsIgnoreCase(request.getRequest().getPayload().getProductCode())) {
            responsePayload.setEligible(false);
        }
        String industryType = productRestrictionPayload.getIndustryType();
        if (AppConstants.SWAG.equals(productRestrictionPayload.getProductId()) && productRestrictionPayload.isPosSeller() && !StringUtils.isEmpty(industryType) && BLOCKED_INDUSTRY_TYPE.stream().anyMatch(i -> i.equals(industryType))) {
            responsePayload.setEligible(false);
        }
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        response.setMetadata(inputRequest.getRequest().getMetadata());
        response.setErrorResponse(errorResponse);
        outputResponse.setResponse(response);
        log.info("Response Payload of OutputResponse: {}", outputResponse);

        if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
            payload.setPayload(EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
        }else {
            payload = Utility.encryptResponse(outputResponse);
        }

        return payload;
    }

    private void setRestrictionMasterData(List<ProductRestrictionMasterData> productRestrictionMasterDataList) {

        for (ProductRestrictionMasterData productRestrictionMasterData : productRestrictionMasterDataList) {
            String type = productRestrictionMasterData.getType();
            switch (type) {
                case AppConstants.AGENT_CODE :
                    agentCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.PIN_CODE :
                    pinCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.PIN_CODE_1CR_SUM_ASSURED :
                    pinCode1CRSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.PIN_CODE_2CR_SUM_ASSURED :
                    pinCode2CRSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.PIN_CODE_SJB_5L_SUM_ASSURED:
                    pinCodeSjb5LacSumAssuredProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.COUNTRY :
                    countryProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
//                case AppConstants.COUNTRY_WITH_CI_RIDER :
//                    countryWithCIRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                    break;
//                case AppConstants.CITY :
//                    cities = productRestrictionMasterData.getDataMap().keySet().stream().collect(Collectors.toList());
//                    break;
//                case AppConstants.COUNTRY_WITH_WOP_RIDER :
//                    countryWithWOPRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                    break;
//                case AppConstants.COUNTRY_WITH_ADB_RIDER :
//                    countryWithADBRiderProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                    break;
                case AppConstants.SPP_NRI_COUNTRY :
                    sspNriCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
                    break;
                case AppConstants.FATF_COUNTRY :
                    fatfCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
                    break;
                case AppConstants.INSTA_PROTECT_PIN_CODE :
                    instaProtectPincodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
                case AppConstants.INSTA_PROTECT_AGENT_CODE :
                    instaProtectAgentCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
                    break;
//                case AppConstants.FLEXI_PROTECT_RESTRICTED_PIN_CODE :
//                    flexiProtectPinCodeProductRestrictionMasterDataMap = productRestrictionMasterData.getDataMap();
//                    break;
//                case AppConstants.CIP_COUNTRY :
//                    cipCountries = new ArrayList<>(productRestrictionMasterData.getDataMap().keySet());
//                    break;
                default:
                    log.info("No ProductRestrictionMasterDataMap found ");
            }
        }
    }

    private void mandatoryValidation(InputRequest inputRequest, List<Object> errorList) {
        String productId = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getProductId();
        if (Objects.isNull(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload())) {
            errorList.add(new ErrorResponse(customErrorMessage.getCustomErrorCodeList().get(PRODUCT_REDIRECTION_PATH)
                    , customErrorMessage.getCustomErrorMessageList().get(PRODUCT_REDIRECTION_PATH)));
        }
        else if (productId == null || productId.trim().isEmpty()) {
                    errorList.add(new ErrorResponse(customErrorMessage.getCustomErrorCodeList().get(PRODUCT_PATH)
                            , customErrorMessage.getCustomErrorMessageList().get(PRODUCT_PATH)));
        }
    }

    private InputRequest deserializeRequest(String decryptedString) throws IOException {
        return objectMapper.readValue(decryptedString, InputRequest.class);
    }

    private void productWiseFunctionalValidation(InputRequest inputRequest, Set<ErrorResponse> errors) {
        try {
            validationFactory.getValidationService()
                    .validateRequest(inputRequest, errors);
        } catch (Exception e) {
            log.error("Implementation not found for channel {} and error response {}",inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().
                    getChannel(),e);
        }
    }

    private void validatePolicyDetails(List<com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails> policyDetailsList, long transactionId, double twoYearCISumAssured, double existingCIRiderSumAssured) {

        for (com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails policyDetail: policyDetailsList){
            String riderSumAssured=isCIRiderApplicable(policyDetail,transactionId);
            if(validateLast2yearData(policyDetail) && !StringUtils.isEmpty(riderSumAssured)){
                twoYearCISumAssured+=Double.parseDouble(riderSumAssured);
            }
            if(!StringUtils.isEmpty(riderSumAssured)){
                existingCIRiderSumAssured+=Double.parseDouble(riderSumAssured);
            }
        }
    }

    private double validateAgeLimit(int age, List<CIRiderAgeSA>ageGroupMaxSumAssured) {
        for ( CIRiderAgeSA ageGroup:ageGroupMaxSumAssured) {
            if(age>=ageGroup.getMinAge() && age <=ageGroup.getMaxAge()){
                return ageGroup.getMaxSumAssured();
            }
        }
        return 0.0;
    }


    private void incomeMultiplierForCIRider(double twoYearCISumAssured, double currentCIRiderSum, double maxIncome,double ageLimit,List<String> messages, Long transactionId) {
        try {
            if(twoYearCISumAssured>0){
                validateIncomeMultiple(twoYearCISumAssured,maxIncome,currentCIRiderSum,ageLimit,messages,transactionId);
            }else{
                if(currentCIRiderSum>ageLimit ){
                    messages.add(CI_RIDER_SUM_ASSURED_INCOME + ageLimit);
                }else if(currentCIRiderSum>maxIncome){
                    messages.add(CI_RIDER_SUM_ASSURED_INCOME + maxIncome);
                }else if (currentCIRiderSum<CIMinSumAssured){
                    messages.add(CI_RIDER_MIN_SUM_ASSURED+currentCIRiderSum);
                }
            }
        }catch (Exception e ){
            log.error("Exception occurred during validating the CI rider max income multiplier sum assured for transactionId {} , {}",transactionId,Utility.getExceptionAsString(e));
        }
    }

    private void validateIncomeMultiple(double twoYearCISumAssured, double maxIncome, double currentCIRiderSum, double ageLimit, List<String> messages, Long transactionId) {
        log.info("In Income Multiplier max existing sum for transactionId {}, {}",transactionId,twoYearCISumAssured);
        if(twoYearCISumAssured+currentCIRiderSum >ageLimit){
            addingRestrictionMessage(ageLimit,twoYearCISumAssured,messages);
        }else if(twoYearCISumAssured+currentCIRiderSum >maxIncome){
            addingRestrictionMessage(maxIncome,twoYearCISumAssured,messages);
        }
    }

    private void addingRestrictionMessage(double maxSumAssured, double twoYearCISumAssured,List<String> messages) {
        double val=maxSumAssured-twoYearCISumAssured;
        if(val<1){
            messages.add(CI_RIDER_SUM_ASSURED_INCOME +0);
        }else if(val<CIMinSumAssured){
            messages.add(CI_RIDER_MIN_SUM_ASSURED+val);
        }else {
            messages.add(CI_RIDER_SUM_ASSURED_INCOME+val);
        }
    }

    private void validateOneCroreSumForCIRider(double existingCIRiderSumAssured, double currentCIRiderSum, List<String> messages, Long transactionId) {
        log.info("CI Rider Function validating max Sum assured for transactionId {} , {}",transactionId,existingCIRiderSumAssured);
        if(existingCIRiderSumAssured+currentCIRiderSum >CIMaxSumAssured){
            double val=CIMaxSumAssured-existingCIRiderSumAssured;
            if(val<1){
                messages.add(CI_RIDER_SUM_ASSURED + 0);
            }else if(val< CIMinSumAssured){
                messages.add(CI_RIDER_MIN_SUM_ASSURED + val);
            }else{
                messages.add(CI_RIDER_SUM_ASSURED + val);
            }
        }
    }


    private String isCIRiderApplicable(com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails policyDetails, Long transactionId) {
        String sumassured="";
        try {
            log.info("In CI Rider validating Income Multiplier SumAssured for transactionId {}",transactionId);
            if(Objects.nonNull(policyDetails.getRiderDetails()) && !policyDetails.getRiderDetails().isEmpty()){
                for( RiderDetails rider: policyDetails.getRiderDetails()){
                    if(CIRiderCodesList.contains(rider.getRiderCode().trim()) && CIRiderStatusCodes.contains(rider.getRiderCoverStatusCode().trim())){
                        return rider.getRiderSumAssured();
                    }
                }
            }
        }catch (Exception e){
            log.error("Exception occured in the getting the rider sum assured for transactionId {}, {}",transactionId, Utility.getExceptionAsString(e));
        }
        return sumassured;
    }

    private boolean validateLast2yearData(com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails policyDetails)  {
        boolean eligible=false;
        try{
            DateTimeFormatter formator = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date=policyDetails.getIssueDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date1 = sdf.parse(LocalDate.now().minusMonths(24).toString());
            java.util.Date date2 = sdf.parse(LocalDate.parse(date,formator).toString());
            eligible = date1.before(date2);
            return eligible;

        }catch (Exception e){
            log.error("error occurred during parsing in date ");
        }
        return eligible;
    }

    private String setFPSChannelAndCustomerClassificationAsNone(String productId, String channelOrCustomerClassification) {
        return FPS_PRODUCT_ID.equalsIgnoreCase(productId) ? NONE : channelOrCustomerClassification;
    }

    private List<String> checkFatfCountry(ProductRestrictionPayload productRestrictionPayload, List<String> messages) {

        boolean isCountryNull = Objects.isNull(productRestrictionPayload.getCommunicationCountry())
                || Objects.isNull(productRestrictionPayload.getPermanentCountry());
        if(fatfCountries == null) {
            errorMessages.add("FATF Countries data not available in DB");
            log.info("FATF Countries data not available in DB");
        }
        else{
            boolean isFatfCountry = fatfCountries.stream().anyMatch(fatfCountry ->
                    fatfCountry.equalsIgnoreCase(productRestrictionPayload.getCommunicationCountry()) ||
                    fatfCountry.equalsIgnoreCase(productRestrictionPayload.getPermanentCountry()));
            if(!isCountryNull && isFatfCountry){
                log.info("FATF Countries blocker for all Product and all channels TransactionID {}", productRestrictionPayload.getTransactionId());
                messages.add(BLACKLISTED_COUNTRIES);
            }
        }
        return messages;
    }

    private void updatingForm3DataForSchemeA(ProductRestrictionPayload productRestrictionPayload) {
		try {
			if (AppConstants.FORM3.equalsIgnoreCase(productRestrictionPayload.getFormType())
                    && !Utility.schemeBCase(productRestrictionPayload.getSchemeType())) {
				log.info("updating the form 3 scheme A data for transactionId {}",
						productRestrictionPayload.getTransactionId());
				String insuredEducation = productRestrictionPayload.getInsurerEducation();
				productRestrictionPayload.setEducation(insuredEducation);
				log.info("insured education : {}", insuredEducation);
				String insuredAnnualIncome = productRestrictionPayload.getInsurerAnnualIncome();
				productRestrictionPayload.setIncome(insuredAnnualIncome);
				log.info("insured annual income : {}", Utility.getMaskedValue(insuredAnnualIncome, MaskType.AMOUNT));
				String insuredOccupation = productRestrictionPayload.getInsurerOccupation();
				productRestrictionPayload.setOccupation(insuredOccupation);
				log.info("insured occupation : {}", insuredOccupation);
				java.util.Date insurerDateOfBirth = productRestrictionPayload.getInsurerDateOfBirth();
				productRestrictionPayload.setDateOfBirth(insurerDateOfBirth);
				log.info("insurer DateOfBirth : {}", insurerDateOfBirth);
			}
		} catch (Exception ex) {
			log.error("Exception in updating the form 3 scheme A data for transactionId {}, {}",
					productRestrictionPayload.getTransactionId(), Utility.getExceptionAsString(ex));
		}
	}

    /**
     * FUL2-13207
     *
     * @param productRestrictionPayload
     * @param messages
     */
    private void validateSJBAndAddMessages(ProductRestrictionPayload productRestrictionPayload,List<String> messages) {
    	String formType=productRestrictionPayload.getFormType();
        String annualIncomeData = null;
        java.util.Date ageData = null;
        String educationData = null;
        Integer educationCode;
    	if(isEmpty(formType)) {
            return;
        }
        //FUL2-159877 Enable product validation in form-c scheme-A for SJB
    	if(formType.equalsIgnoreCase(AppConstants.DEPENDENT)
                || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(productRestrictionPayload.getSchemeType()))) {
            annualIncomeData = productRestrictionPayload.getInsurerAnnualIncome();
            ageData = productRestrictionPayload.getInsurerDateOfBirth();
            educationData = productRestrictionPayload.getInsurerEducation();
    	}else if(formType.equalsIgnoreCase(AppConstants.SELF) || Utility.schemeBCase(formType,productRestrictionPayload.getSchemeType())) {
            annualIncomeData = productRestrictionPayload.getIncome();
            ageData = productRestrictionPayload.getDateOfBirth();
            educationData = productRestrictionPayload.getEducation();
    	}else {
            return;
        }
    	final Integer age=isNull(ageData)?null:getAge(ageData);

        RestrictionData restrictionData;
        restrictionData = restrictionRepository.findByProductIdAndChannel(productRestrictionPayload.getProductId(), AppConstants.NONE);
        if (isNull(restrictionData) ||
                or(isNull(restrictionData.getMinimumSumAssured()),
                        isNull(restrictionData.getMaximumSumAssured()),
                        isNull(restrictionData.getMinimumIncome()),
                        isNull(restrictionData.getMinimumHighSchoolSumAssured()),
                        isNull(restrictionData.getAgeIncomeBand()))) {
            messages.add("No data found in DB");
            log.error("SJB: any one of using property of restrictionData is missing");
            return;
        }
        Double sumAssured = productRestrictionPayload.getSumAssured();
        //FUL2-15177 SJB-Product restriction Changes---NEW
        //Screen 2 Validation
        log.info("Pincode = {} , SumAssured = {}",productRestrictionPayload.getCommunicationPinCode(), Utility.getMaskedValue(sumAssured, MaskType.AMOUNT));
        if (!productRestrictionPayload.getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AXIS)
                && productRestrictionPayload.getCommunicationPinCode() != null
                && sumAssured != null
                && pinCode2CRSumAssuredProductRestrictionMasterDataMap != null
                && sumAssured > restrictionData.getSumAssured()) {
            List<Date> communicationPinCodeDates = pinCodeSjb5LacSumAssuredProductRestrictionMasterDataMap.get(productRestrictionPayload.getCommunicationPinCode());
            if (communicationPinCodeDates != null) {
                messages.add("Maximum Sum assured allowed is 5 Lacs");
                log.info("Maximum Sum assured allowed is 5 Lacs");
                return;
            }
        }
        //Screen 3 Validation
        if (isNull(productRestrictionPayload) || or(isNull(annualIncomeData),isEmpty(annualIncomeData),isNull(productRestrictionPayload.getSumAssured()))) {
            log.info("SJB: any one property of productRestrictionPayload is missing to validate");
            return;
        }
        Integer annualIncome = Integer.parseInt(annualIncomeData);
        if(!isEmpty(educationData)) {
    		educationCode=getEducationCode(educationData);
            log.info("SJB: validation applying for SJB with data~ TransactionId:{} age:{},annualIncome:{},sumAssured:{},educationCode:{}", productRestrictionPayload.getTransactionId(), age, Utility.getMaskedValue(annualIncome, MaskType.AMOUNT), Utility.getMaskedValue(sumAssured, MaskType.AMOUNT), educationCode);

    		if(or(Double.compare(sumAssured, restrictionData.getMinimumSumAssured())<0, Double.compare(sumAssured, restrictionData.getMaximumSumAssured())>0)) {
                messages.add("Maximum or minimum SA limit reached");
                return;
                //System should not allow user to enter SA value which are not within the mentioned range.
                //Education vs SA restrictions
    		}else if(and(Integer.compare(annualIncome, restrictionData.getMinimumIncome())>0,
    				or(and(educationCode.equals(0), Double.compare(sumAssured, restrictionData.getMinimumSumAssured())>0)
    						,and(educationCode.equals(1),Double.compare(sumAssured,restrictionData.getMinimumHighSchoolSumAssured())>0)))){
                messages.add("Maximum SA limit reached as per education selected for this plan");
                return;
            }
        }
        //age, income and SA logic
    	if(Integer.compare(annualIncome, restrictionData.getMinimumIncome())<0) {
    		if(Double.compare(sumAssured, restrictionData.getMinimumSumAssured())>0) {
                messages.add("Maximum SA limit reached as per age and income selected for this plan");
            }
    	}else {
    		if(isNull(age)) {
                return;
            }
            //check with income multiple
            Optional<Integer> incomeMultiple = restrictionData.getAgeIncomeBand().stream()
                    .filter(ageIncomeBand -> age >= getMinAgeBand(ageIncomeBand) && age <= getMaxAgeBand(ageIncomeBand))
                    .map(Utility::getMultiple)
                    .findFirst();
    		if(!incomeMultiple.isPresent()) {
                messages.add("Given age not applicable for policy");
                return;
            }
    		Integer multiple=annualIncome*incomeMultiple.get();
    		if(Double.compare(sumAssured, multiple)>0) {
                messages.add("Maximum SA limit reached as per age and income selected for this plan");
            }
        }
    }

    /**
     * @param edu
     * @return
     */
    private Integer getEducationCode(String edu) {
    	if("PRIMARY SCHOOL".equalsIgnoreCase(edu)) { //below 10th
            return 0;
    	}else if(HIGH_SCHOOL_CAPITAL.equalsIgnoreCase(edu) || "DIPLOMA".equalsIgnoreCase(edu)) {  //10th and below 12th
            return 1;
    	}else if(GraduateAndaboveGraduateEducations.contains(edu.toUpperCase()) || AppConstants.SENIOR_SCHOOL.equalsIgnoreCase(edu)) { // 12th and above
            return 2;
    	}else {
            return 0;
        }
    }
    /* FUL2-9472 WLS Product Restriction */

    private List<String> validateRestrictionData(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSspSwissReCase) {

        String communicationCity = null, proposerEducation = null, insuredEducation = null, proposerOccupation = null, insuredOccupation = null,
                panNumber = null, proposerAnnualIncome = null, insuredAnnualIncome = null, communicationCountry = null, customerClassification = null,
                communicationPinCode = null;

        boolean isFormTwo = false, isSmoker = false;
        double sumAssured = 0.0d, spouseAnnualIncome = 0.0d, spouseTotalInsuranceCover = 0.0d;
        boolean isSSESProduct = false;
        long transactionId = productRestrictionPayload.getTransactionId();
        log.info ("transaction id : {}",transactionId);
        String dedupeFlag = productRestrictionPayload.getDedupeFlag();
        log.info("dedupe flag : {}", dedupeFlag);
        String productId = productRestrictionPayload.getProductId();
        log.info("product id : {}", productId);
        String nationality = productRestrictionPayload.getNationality();
        log.info("nationality : {}", nationality);
        proposerEducation = getproposerEducation(productRestrictionPayload);
        log.info("proposer education : {}", proposerEducation);
        proposerAnnualIncome = productRestrictionPayload.getIncome();
        log.info("proposer annual income : {}", Utility.getMaskedValue(proposerAnnualIncome, MaskType.AMOUNT));
        panNumber = productRestrictionPayload.getPanNumber();
        log.info("pan number : {}", Utility.getMaskedValue(panNumber, MaskType.PAN_NUM));
        proposerOccupation = getproposerOccupation(productRestrictionPayload);
        log.info("proposer occupation : {}", proposerOccupation);
        communicationCountry = productRestrictionPayload.getCommunicationCountry();
        log.info("communication country : {}", communicationCountry);
        sumAssured = productRestrictionPayload.getSumAssured();
        log.info("sum assured :{}", Utility.getMaskedValue(sumAssured, MaskType.AMOUNT));
        spouseAnnualIncome = productRestrictionPayload.getSpouseAnnualIncome();
        log.info("spouse Annual Income {}", Utility.getMaskedValue(spouseAnnualIncome, MaskType.AMOUNT));
        spouseTotalInsuranceCover = productRestrictionPayload.getSpouseTotalInsuranceCover();
        log.info("spouse Total Insurance Cover{}", spouseTotalInsuranceCover);
        //FUL2-53692 we have different stop rules for Smart Secure Easy Solution
        isSSESProduct = Utility.isSSESProduct(productId, productRestrictionPayload.getIsSSESProduct(), productRestrictionPayload.getSSESSolveOption());
        if (productRestrictionPayload.getFormType().equalsIgnoreCase(AppConstants.DEPENDENT)) {
            isFormTwo = true;
            log.info("Checking if Form2 case : {}", isFormTwo);
        }
        communicationCity = productRestrictionPayload.getCommunicationCity();
        log.info("communication city : {}", communicationCity);

        isSmoker = productRestrictionPayload.isSmoker();
        log.info("Smoker : {}", isSmoker);
        /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1: Removed logic build for JIRA FUL2-5522 because of new requirment*/
        customerClassification = productRestrictionPayload.getCustomerClassification();
        log.info("customer classification : {}",customerClassification);

        communicationPinCode = productRestrictionPayload.getCommunicationPinCode();
        log.info("communication pin code {}", communicationPinCode);

        if (isFormTwo) {
            insuredEducation = productRestrictionPayload.getInsurerEducation();
            log.info("insured education : {}", insuredEducation);
            insuredAnnualIncome = productRestrictionPayload.getInsurerAnnualIncome();
            log.info("insured annual income : {}", Utility.getMaskedValue(insuredAnnualIncome, MaskType.AMOUNT));
            insuredOccupation = productRestrictionPayload.getInsurerOccupation();
            log.info("insured occupation : {}", insuredOccupation);
        }

       if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))){
            validateProposerEducation(productRestrictionPayload, restrictionData, messages, isSSESProduct, isSspSwissReCase);
            validateInsurerEducation(productRestrictionPayload, restrictionData, messages, isSspSwissReCase);
            validateProposerAnnualIncome(productRestrictionPayload, restrictionData, messages, isSSESProduct, isSspSwissReCase);
            validateInsurerAnnualIncome(productRestrictionPayload, restrictionData, messages, isSspSwissReCase);
            validateProposerOccupation(productRestrictionPayload, restrictionData, messages, isSSESProduct, isSspSwissReCase);
            validateInsurerOccupation(productRestrictionPayload, restrictionData, messages, isSspSwissReCase);
            validateCity(productId, communicationCity, communicationCountry, messages);
            validateSumAssured(productId, sumAssured, restrictionData, messages, proposerOccupation, isSSESProduct, isSspSwissReCase);
            validateSmoker(isSmoker, restrictionData, messages);
        }
        if(SSP_PRODUCT_ID.equals(productId) && !isSspSwissReCase && (AppConstants.NRI.equalsIgnoreCase(nationality) ||
                sspNriCountries.contains(getCommCountry(productRestrictionPayload)) && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(getCommCountry(productRestrictionPayload)))) {
            return messages;
        }

        validatePanNumber(panNumber,productRestrictionPayload,messages);

        //FUL2-8502 Product Restrictions - Pin codes for Term Plans
        //FUL2-40915 SSP - Negative & Conditional Pincode : Existing Customer Logic Change
        //FUL2-49759 SSP - Pincode Logic Change - Agent level Based
        log.info("Checking SSP & dedupeFlag for sumAssured based pincode validation for transactionId {}", transactionId);
        //FUL2-123815
        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && (!CHANNEL_AGENCY.equalsIgnoreCase(productRestrictionPayload.getChannel()) ||
                !CHANNEL_CAT.equalsIgnoreCase(productRestrictionPayload.getChannel())) && !isSspDedupeEx(productId, dedupeFlag)) {
            if(!isSSESProduct && productRestrictionPayload.getProductId().equalsIgnoreCase(SSP_PRODUCT_ID) && !isSspSwissReCase){
                validationSSP1CrOr2CrSumAssured(productRestrictionPayload,restrictionData,messages);
            }else if(!(FPS_PRODUCT_ID.equals(productId)) && !isSSESProduct && !isSspSwissReCase){
                validatePinCodeFor1CrSumAssured(communicationPinCode, sumAssured, proposerOccupation, proposerAnnualIncome, restrictionData, messages);
                validatePinCodeFor2CrSumAssured(communicationPinCode, sumAssured, proposerOccupation, proposerAnnualIncome, restrictionData, messages);
            }
        }
        return messages;
    }

    private List<String> validateProductRestrictionData(ProductRestrictionPayload productRestrictionPayload,
                                                        RestrictionData restrictionData, List<String> messages, boolean isSspSwissReCase) throws UserHandledException {
    	String agentCode = null;
        String communicationPinCode = null;
        String communicationCountry = null;
        String permanentCountry = null;
        String proposerIncome = null;
        String proposerEducation = null;

        boolean isCIRider = false;
        boolean isWOPRider = false;
        boolean isADBRider = false;
        boolean isDiabetic = false;
        boolean isSSESProduct = false;

        String nationality = productRestrictionPayload.getNationality();
        String productId = productRestrictionPayload.getProductId();
        long transactionId = productRestrictionPayload.getTransactionId();
        log.info ("transactionid {}",transactionId);
        String dedupeFlag = productRestrictionPayload.getDedupeFlag();
        log.info("dedupe flag {}", dedupeFlag);
        agentCode = productRestrictionPayload.getAgentId();
        log.info("agentCode {}",agentCode);
        communicationPinCode = productRestrictionPayload.getCommunicationPinCode();
        communicationCountry = getCommCountry(productRestrictionPayload);
        log.info("communication pincode {} and country {} data", communicationPinCode, communicationCountry);
        permanentCountry = getPermanentCountry(productRestrictionPayload);
        log.info("permanent country {} data", permanentCountry);
        isCIRider = productRestrictionPayload.isCIRider();
        log.info("Checked if CI Rider present {}", isCIRider);
        isWOPRider = productRestrictionPayload.isWOPPlusRider();
        log.info("Checked if WOP Rider present {}", isWOPRider);
        isADBRider = productRestrictionPayload.isADBRider();
        log.info("Checked if ADB Rider present {}", isADBRider);
        isDiabetic = productRestrictionPayload.isDiabetic();
        log.info("Checked if Diabetic {}", isDiabetic);
        proposerIncome = productRestrictionPayload.getIncome();
        log.info("proposer annual income : {}", Utility.getMaskedValue(proposerIncome, MaskType.AMOUNT));
        proposerEducation = getproposerEducation(productRestrictionPayload);
        log.info("proposer education : {}", proposerEducation);
        //FUL2-53692 we have different stop rules for Smart Secure Easy Solution
        isSSESProduct = Utility.isSSESProduct(productId, productRestrictionPayload.getIsSSESProduct(), productRestrictionPayload.getSSESSolveOption());

        //FUL2-144984
        //FUL2-48288 SSP-NRI Restriction for FormC


        //FUL2-25393 All Channels Except Axis | SSP-NRI changes

        //FUL2-64466 FPS NRI Restriction
        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))) {
            fpsNriValidation(productRestrictionPayload, messages, productId);
            if ((!isSSESProduct && !isSspSwissReCase || FPS_PRODUCT_ID.equals(productId)) && (AppConstants.NRI.equalsIgnoreCase(nationality) ||
                    sspNriCountries.contains(getCommCountry(productRestrictionPayload)) && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(getCommCountry(productRestrictionPayload)))) {
                return messages;
            }
            setAgentCode(agentCode, productId, isSspSwissReCase);
        }


        //FUL2-144984

        //FUL2-40915 SSP - Negative & Conditional Pincode : Existing Customer Logic Change
        //FUL2-123815
        if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && !CHANNEL_AGENCY.equalsIgnoreCase(productRestrictionPayload.getChannel()) || !CHANNEL_CAT.equalsIgnoreCase(productRestrictionPayload.getChannel())){
            checkNegativePincode(transactionId, productId, dedupeFlag, communicationPinCode, isSSESProduct, proposerEducation, isSspSwissReCase);
        }else if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))){
            checkNegativePincodeForAgency(productRestrictionPayload, restrictionData, messages, isSSESProduct, proposerEducation, isSspSwissReCase);
        }
        //CIRider for critical illness is not allowed for housewife in ssp plan FUL2-147355
        if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && AppConstants.OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(productRestrictionPayload.getOccupation()) && !productRestrictionPayload.isPosSeller() && AppConstants.TRUE.equalsIgnoreCase(productRestrictionPayload.getIsCIRiderSelected()) &&
                productId.equalsIgnoreCase(AppConstants.SSP_PRODUCT_ID) )
        {

            messages.add("Critical Illness and Disability Rider");
            log.info("Critical Illness and Disability Rider is not applicable for this occupation");
        }

        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isCIRider) {
            if (isDiabetic) {
                messages.add("Diabetic CI Rider | " + isDiabetic);
                log.info("Diabetic with CI Rider {} is not allowed", isDiabetic);
            } else if (countryWithCIRiderProductRestrictionMasterDataMap != null) {
                List<Date> communicationCountryWithCIRiderDates = countryWithCIRiderProductRestrictionMasterDataMap.get(communicationCountry);
                List<Date> permanentCountryWithCIRiderDates = countryWithCIRiderProductRestrictionMasterDataMap.get(permanentCountry);
                setCountry(communicationCountryWithCIRiderDates, permanentCountryWithCIRiderDates,
                        communicationCountry, permanentCountry);
            } else {
                log.info("Country with CI rider restriction data not available in DB");
            }
        }

        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isWOPRider && !isSSESProduct) {
            if (isDiabetic) {
                messages.add("Diabetic WOP Rider | " + isDiabetic);
                log.info("Diabetic with WOP Rider {} is not allowed", isDiabetic);
            } else if (countryWithWOPRiderProductRestrictionMasterDataMap != null) {
                List<Date> communicationCountryWithWOPRiderDates = countryWithWOPRiderProductRestrictionMasterDataMap.get(communicationCountry);
                List<Date> permanentCountryWithWOPRiderDates = countryWithWOPRiderProductRestrictionMasterDataMap.get(permanentCountry);
                setCountry(communicationCountryWithWOPRiderDates, permanentCountryWithWOPRiderDates,
                        communicationCountry, permanentCountry);
            } else {
                log.info(COUNTRY_MESSAGE);
            }
        }

        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isADBRider) {
            setCounryADBRider(communicationCountry, permanentCountry);
        }

        return messages;
    }

    //FUL2-144984
    /**
	 * @implNote This method is used to validate Flexi Protect Solution NRI cases.
	 * @return messages
	 */

    private String getEducation(ProductRestrictionPayload productRestrictionPayload) {
        if(AppConstants.DEPENDENT.equalsIgnoreCase(productRestrictionPayload.getFormType())){
            return !StringUtils.isEmpty(productRestrictionPayload.getInsurerEducation())?productRestrictionPayload.getInsurerEducation().toUpperCase().trim():"";
        }else if (AppConstants.SELF.equalsIgnoreCase(productRestrictionPayload.getFormType())
                || Utility.schemeBCase(productRestrictionPayload.getFormType(), productRestrictionPayload.getSchemeType())){
            return !StringUtils.isEmpty(productRestrictionPayload.getEducation())?productRestrictionPayload.getEducation().toUpperCase().toUpperCase().trim():"";
        }
        return productRestrictionPayload.getEducation().toUpperCase().trim();
    }

    private LimitationData getLimitationData(String channel, String productId, String occupation, String city) throws UserHandledException {
        return limitationDataRepository.findByChannelAndProductIdAndOccupationAndCommunicationCity(channel, productId, occupation, city);
    }

    /* FUL2-9472 WLS Product Restriction */
    private LimitationData getLimitationData(String productId, String limitationType) throws UserHandledException {
        return limitationDataRepository.findByProductIdAndLimitationType(productId, limitationType);
    }

    public static boolean contains(String typeOfData) {
        for (TypeOfData type : TypeOfData.values()) {
            if (type.name().equals(typeOfData)) {
                return true;
            }
        }
        return false;
    }

    private int getAge(java.util.Date dob) {
        LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(date, today);
        return period.getYears();
    }
    private void saveProductRestrictionMasterData(String typeOfData, List<String> dataToUpdate, String status, ProductRestrictionMasterData productRestrictionMasterData, List<String> messages) {
        try {
            Map<String, ArrayList<Date>> dataMaps = productRestrictionMasterData.getDataMap();
            for (String data : dataToUpdate) {
                Set<String> keySet = dataMaps.keySet();
                boolean isDataPresent = keySet.contains(data.toUpperCase());
                if (Utility.andTwoExpressions(isDataPresent, AppConstants.ACTIVATE.equalsIgnoreCase(status))) {
                    ArrayList<Date> startEndDates = dataMaps.get(data.toUpperCase());
                    Date lastIndexOfDates = startEndDates.get(startEndDates.size() - 1);
                    if (lastIndexOfDates.getEndDate() != null) {
                        Date date = new Date();
                        date.setStartDate(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
                        startEndDates.add(date);
                        log.info("Updating start date for {}", data);
                        messages.add("Activated " + typeOfData + VALUE + data);
                        log.info("Activated {} with value {}", typeOfData, data);
                    } else {
                        messages.add("Already active " + typeOfData + VALUE + data);
                        log.info("Already active {} with value {}", typeOfData, data);
                    }
                } else if (Utility.andTwoExpressions(isDataPresent, status.equalsIgnoreCase(AppConstants.DEACTIVATE))) {
                    ArrayList<Date> startEndDates = dataMaps.get(data.toUpperCase());
                    Date lastIndexOfDates = startEndDates.get(startEndDates.size() - 1);
                    if (lastIndexOfDates.getEndDate() != null) {
                        messages.add("Already de-active " + typeOfData + VALUE + data);
                        log.info("Already de-active {} with value {}", typeOfData, data);
                    } else {
                        lastIndexOfDates.setEndDate(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
                        messages.add("End date is updated for " + typeOfData + VALUE + data);
                        log.info("Setting end date for {} with value {}", typeOfData, data);
                    }
                } else if (Utility.andTwoExpressions(!isDataPresent, status.equalsIgnoreCase(AppConstants.ACTIVATE))) {
                    ArrayList<Date> dates = new ArrayList<>();
                    Date date = new Date();
                    date.setStartDate(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
                    dates.add(date);
                    dataMaps.put(data.toUpperCase(), dates);
                    log.info("New data of type {} with value {} is inserted and activated", typeOfData, data);
                    messages.add("New data of type " + typeOfData + VALUE + data + " is inserted and activated");
                } else if (Utility.andTwoExpressions(!isDataPresent, status.equalsIgnoreCase(AppConstants.DEACTIVATE))) {
                    messages.add(typeOfData + VALUE + data + " doesn't exist to restriction");
                    log.info("{} with value {} doesn't exist to restriction", typeOfData, data);
                }
            }
            productRestrictionRepository.save(productRestrictionMasterData);
            log.info("Data updated successfully");
        } catch (Exception e) {
            messages.add(ERR_MSG);
            log.error("Something went wrong! Exception occurred while saveProductRestrictionMasterData : ",e);
        }
    }

    private void createProductRestrictionMasterData(String typeOfData, List<String> dataToUpdate, String status, ProductRestrictionMasterData productRestrictionMasterData, List<String> messages) {
        try {
            productRestrictionMasterData = new ProductRestrictionMasterData();
            log.info("Creating new object of ProductRestriction is same is not present in DB with type {}", typeOfData);
            Map<String, ArrayList<Date>> dataMap = new HashMap<String, ArrayList<Date>>();
            for (String specificData : dataToUpdate) {
                if (status.equalsIgnoreCase(AppConstants.ACTIVATE)) {
                    ArrayList<Date> startEndDates = new ArrayList<>();
                    Date date = new Date();
                    date.setStartDate(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
                    startEndDates.add(date);
                    dataMap.put(specificData.toUpperCase(), startEndDates);
                    productRestrictionMasterData.setType(typeOfData);
                    productRestrictionMasterData.setDataMap(dataMap);
                    messages.add("New data of type " + typeOfData + VALUE + specificData + " is inserted and activated");
                } else {
                    log.info("{} with value {} is already deactivate", typeOfData, specificData);
                }
            }
            productRestrictionRepository.save(productRestrictionMasterData);
            log.info("Data updated successfully");
        } catch (Exception e) {
            messages.add(ERR_MSG);
            log.error("Something went wrong! Exception occurred while createProductRestrictionMasterData : ",e);
        }
    }
    private void sendEmailAndSmsIfApplicable(List<String> messages, ProductRestrictionPayload productRestrictionPayload) {
        long transactionId = productRestrictionPayload.getTransactionId();
        try{
            utilityService= BeanUtil.getBean(UtilityService.class);
            if (!CollectionUtils.isEmpty(messages) && messages.contains(POS_HEALTH_MSG))
            {
                //Ful2-32235
                //FUL2-135547_Setup_of_DCB_Bank_in_Mpro
                //Ful-144865 Setup of motilal oswal
                if(Utility.isAgencyYblChannel(productRestrictionPayload.getChannel()) ||
                        Utility.checkBrokerTmbChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode())|| utilityService.replicaOfUjjivanChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode())) {
                    messages.remove(POS_HEALTH_MSG);
                    messages.add(AGENCY_YBL_POS_MSG);
                    log.info("Updated message for agency and ybl pos seller #{}# for transactionId {}", messages.get(0), transactionId);
                }
                log.info("trigger email notification to Seller & customer for Tx id {}", transactionId);
                //Ful2-32231
                emailService.sendEmailAndSmsForPos(productRestrictionPayload);
            }
        }catch (UserHandledException exception){
            log.error("Exception in sendEmailAndSms for transactionId {} is ", transactionId, exception);
        }catch (Exception ex){
            log.error("Exception in sendEmailAndSmsIfApplicable for transactionId {} is",transactionId, ex);
        }
    }

    private String getChannel(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getChannel()) && productRestrictionPayload.getChannel().equals(CHANNEL_AGENCY) &&
                !isEmpty(productRestrictionPayload.getAgentRole()) && catAgentRoles.stream().anyMatch(productRestrictionPayload.getAgentRole().trim()::equalsIgnoreCase) ?
                "CAT" : productRestrictionPayload.getChannel();
    }

    private void setChannel(ProductRestrictionPayload productRestrictionPayload) {
    //FUL2-123815
        channel = Utility.evaluateConditionalOperation(Utility.andTwoExpressions(!isEmpty(productRestrictionPayload.getChannel()) , CHANNEL_CAT.equalsIgnoreCase(productRestrictionPayload.getChannel())) ,
                "CAT",productRestrictionPayload.getChannel());

        if (Utility.andTwoExpressions(!isEmpty(productRestrictionPayload.getChannel()), AppConstants.CHANNEL_UCB.equalsIgnoreCase(productRestrictionPayload.getChannel()))) {

            channel = "UCB";
        } else if (Utility.andFiveExpressions(!isEmpty(productRestrictionPayload.getChannel()),
                !AppConstants.CHANNEL_YBL.equalsIgnoreCase(productRestrictionPayload.getChannel()), channel.charAt(0) == 'B',
                !Utility.isTMBChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode()),
                !utilityService.replicaOfUjjivanChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode()))) {
            channel = "LVB";
        } else if(Utility.isTMBChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode())
                || utilityService.replicaOfUjjivanChannel(productRestrictionPayload.getChannel(), productRestrictionPayload.getGoCode())) {
        	// Setting channel = BY for TMB channel to provide product specific boundary conditions same as YBL.
        	channel = "BY";
        }
        log.info("channel : {}", channel);
    }



    private boolean isSSPNRICheck(String productId, String nationality, ProductRestrictionPayload productRestrictionPayload, boolean isSspSwissReCase) {
        return SSP_PRODUCT_ID.equals(productId) && !isSspSwissReCase && (AppConstants.NRI.equalsIgnoreCase(nationality) ||
                sspNriCountries.contains(getCommCountry(productRestrictionPayload)) && !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(getCommCountry(productRestrictionPayload)));
    }


    private void validateInsuredLimitationData(LimitationData insuredLimitationData, String insurerEducation, String channel,
                                               String productId, String insurerOccupation, String communicationCity) {
        try {
            if (insuredLimitationData != null) {
                if (insuredLimitationData.getEducation().equalsIgnoreCase(AppConstants.YES)) {
                    validateData(insuredLimitationData, insurerEducation, insurerOccupation, communicationCity);
                }
            } else {
                insuredLimitationData = getLimitationData(channel, productId, AppConstants.NONE, communicationCity.toUpperCase());

                if (insuredLimitationData != null && insuredLimitationData.getEducation().equalsIgnoreCase(AppConstants.YES)) {
                    validateData(insuredLimitationData, insurerEducation, communicationCity);
                }
            }
        } catch (Exception e) {
            errorMessages.add(ERR_MSG);
            log.error("Something went wrong! Exception occurred while setInsurerLimitationData : ",e);
        }
    }

    private String getproposerEducation(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getEducation()) ? productRestrictionPayload.getEducation().trim().toUpperCase() : AppConstants.BLANK;
    }

    private String getproposerOccupation(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getOccupation()) ? productRestrictionPayload.getOccupation().trim() : AppConstants.BLANK;
    }

    private String getInsurerEducation(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getInsurerEducation()) ? productRestrictionPayload.getInsurerEducation() : AppConstants.BLANK;
    }

    private String getCommCountry(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getCommunicationCountry()) ? productRestrictionPayload.getCommunicationCountry().trim().toUpperCase() : AppConstants.BLANK;
    }

    private String getPermanentCountry(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getPermanentCountry()) ? productRestrictionPayload.getPermanentCountry().trim().toUpperCase() : AppConstants.BLANK;
    }

    private String getCompanyCountry(ProductRestrictionPayload productRestrictionPayload) {
        return !isEmpty(productRestrictionPayload.getCompanyCountry()) ? productRestrictionPayload.getCompanyCountry().trim().toUpperCase() : AppConstants.BLANK;
    }

    private void setAgentCode(String agentCode, String instaProtectAndSSPProductId, boolean isSspSwissReCase) {

    	boolean isInstaProtectAgentBlocked = checkSSPAndInstaProtectBlockedAgent(agentCode, instaProtectAndSSPProductId, isSspSwissReCase);
    	if (agentCodeProductRestrictionMasterDataMap != null) {
            List<Date> agentDates = agentCodeProductRestrictionMasterDataMap.get(agentCode);
            if (agentDates != null && !isInstaProtectAgentBlocked) {
                Date lastIndexOfDate = agentDates.get(agentDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(AGENT_CODE_MSG + agentCode);
                    log.info(AGENT_CODE_LOG, agentCode);
                }
            }
        } else {
            errorMessages.add("Agent Code restriction data not available in DB");
            log.info("Agent Code restriction data not available in DB");
        }
    }

	/**
	 * FUL2-53692
	 *
	 * @implNote This method is used to check SSP and insta protect blocked agentId.
	 * @param agentCode
	 * @return
	 */
	private boolean checkSSPAndInstaProtectBlockedAgent(String agentCode, String instaProtectAndSSPProductId,  boolean isSspSwissReCase) {
		boolean isInstaProtectBlockedAgent = false;
		if (instaProtectAgentCodeProductRestrictionMasterDataMap != null
				&& !isSspSwissReCase && AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(instaProtectAndSSPProductId)) {
			log.info("validating agentCode {} for SSP and InstaProtect product", agentCode);
			List<Date> agentDates = instaProtectAgentCodeProductRestrictionMasterDataMap.get(agentCode);
			if (agentDates != null) {
				Date lastIndexOfDate = agentDates.get(agentDates.size() - 1);
				if (lastIndexOfDate.getEndDate() == null) {
					messages.add(AGENT_CODE_MSG + agentCode);
					log.info(AGENT_CODE_LOG, agentCode);
					isInstaProtectBlockedAgent = true;
				}
			}
		}
		return isInstaProtectBlockedAgent;
	}


	private void setCountry(List<Date> communicationCountryDates, List<Date> permanentCountryDates,
                            String communicationCountry, String permanentCountry) {
        if (communicationCountryDates != null) {
            Date lastIndexOfDate = communicationCountryDates.get(communicationCountryDates.size() - 1);
            if (lastIndexOfDate.getEndDate() == null) {
                messages.add(AppConstants.COMMUNICATION_COUNTRY + communicationCountry);
                log.info("Communication Country {} is not allowed", communicationCountry);
            }
        }
        if (permanentCountryDates != null) {
            Date lastIndexOfDate = permanentCountryDates.get(permanentCountryDates.size() - 1);
            if (lastIndexOfDate.getEndDate() == null) {
                messages.add(AppConstants.PERMANENT_COUNTRY + permanentCountry);
                log.info("Permanent Country {} is not allowed", permanentCountry);
            }
        }
    }

    private void setCounryADBRider(String communicationCountry, String permanentCountry) {
        if (countryWithADBRiderProductRestrictionMasterDataMap != null) {
            List<Date> communicationCountryWithADBRiderDates = countryWithADBRiderProductRestrictionMasterDataMap.get(communicationCountry);
            List<Date> permanentCountryWithADBRiderDates = countryWithADBRiderProductRestrictionMasterDataMap.get(permanentCountry);
            setCountry(communicationCountryWithADBRiderDates, permanentCountryWithADBRiderDates,
                    communicationCountry, permanentCountry);
        } else {
            errorMessages.add("Country with ADB rider restriction data not available in DB");
            log.info("Country with ADB rider restriction data not available in DB");
        }
    }



    private void validateData(LimitationData proposerLimitationData, String education, String occupation, String city) {
        if (underGraduateEducations.contains(education.toUpperCase())) {
            messages.add("Restrict Proposer Education | " + education);
            log.info("Restrict Proposer Education {} is not allowed", education);

            if (proposerLimitationData.getCommunicationCity().equalsIgnoreCase(city)) {
                messages.add(COMMUNICATION_CITY_MSG + city);
                log.info(COMMUNICATION_CITY_LOG, city);
            }

            if (proposerLimitationData.getOccupation().equalsIgnoreCase(occupation)) {
                messages.add("Restrict Proposer Occupation | " + occupation);
                log.info("Restrict Proposer Occupation {} is not allowed", occupation);
            }
        }
    }

    private void validateData(LimitationData proposerLimitationData, String education, String city) {
        if (underGraduateEducations.contains(education.toUpperCase())) {
            messages.add("Restrict Proposer Education | " + education);
            log.info("Restrict Proposer Education {} is not allowed", education);

            if (proposerLimitationData.getCommunicationCity().equalsIgnoreCase(city)) {
                messages.add(COMMUNICATION_CITY_MSG + city);
                log.info(COMMUNICATION_CITY_LOG, city);
            }
        }
    }

    /* FUL2-9472 WLS Product Restriction */

    private String setCustomerClassification (ProductRestrictionPayload productRestrictionPayload){
        String customerClassification = productRestrictionPayload.getCustomerClassification();

        log.info("customer classification {}", customerClassification);
        if (!isEmpty(customerClassification) && Utility.andThreeExpressions(!isEmpty(customerClassification), channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS), axisBurgundyCodes.contains(customerClassification.toUpperCase()))) {
            customerClassification = "Axis Burgundy";
        } else if (!isEmpty(customerClassification) && Utility.andThreeExpressions(!isEmpty(customerClassification), channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS), axisPriorityCodes.contains(customerClassification.toUpperCase()))) {
            customerClassification = "Axis Priority";
        } else if (!channel.equalsIgnoreCase(AppConstants.CHANNEL_YBL) ||
                !Utility.isTMBChannel(channel, productRestrictionPayload.getGoCode())
                || !utilityService.replicaOfUjjivanChannel(channel, productRestrictionPayload.getGoCode())){
            customerClassification = "None";
        }

        return customerClassification;
    }

    //FUL2-25393 All Channels Except Axis | SSP-NRI changes
    private void addMessageConditionally(boolean condition){
        if(condition) {
            messages.add(SSP_NRI_MSG);
            log.info(SSP_NRI_MSG);
        }
    }

    private void validateProposerEducation(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSSESProduct, boolean isSspSwissReCase) {
        String proposerEducation = productRestrictionPayload.getEducation();
        String proposerAnnualIncome = productRestrictionPayload.getIncome();
        String proposerOccupation = productRestrictionPayload.getOccupation();
        String productId = productRestrictionPayload.getProductId();
        String nationality = productRestrictionPayload.getNationality();
        String formType = productRestrictionPayload.getFormType();
        String schemeType = productRestrictionPayload.getSchemeType();
    	boolean isError=false;
        //FUL2-25393 All Channels Except Axis | SSP-NRI changes

        if (isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (Utility.checkSchemeBWithWipCase(formType,schemeType)) {
                validateSSPNRIRuleForEducation(proposerEducation, restrictionData);
            }
            return;
        }
    	/*FUL2-6814 OTP and SmTP Stop Rules - Phase 1 : Added condition to allow High School*/
    	/*FUL2-8762 OTP and SmTP Stop Rules - Phase 2 for house wife : Added condition to Education >= Graduate. */
        if (Utility.and(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))&&!isSSESProduct,AppConstants.OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation),
                !StringUtils.isEmpty(proposerEducation), !GRADUATE.equalsIgnoreCase(proposerEducation),
                !POST_GRADUATE.equalsIgnoreCase(proposerEducation))) {
            messages.add(PROPOSER_EDUCATION_MSG + proposerEducation);
            log.info(PROPOSER_EDUCATION_LOG, proposerEducation);
        }else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))){
        	if (FPS_PRODUCT_ID.equalsIgnoreCase(productId)) {
    			isError = !StringUtils.isEmpty(proposerEducation) &&
                        !Utility.or(GraduateAndaboveGraduateEducations.contains(proposerEducation.toUpperCase().trim()),
                                SENIOR_SCHOOL.equalsIgnoreCase(proposerEducation), AppConstants.DIPLOMA.equalsIgnoreCase(proposerEducation));
            } else if (!StringUtils.isEmpty(proposerEducation) && underGraduateEducations.contains(proposerEducation.toUpperCase().trim())
                    && !AppConstants.HIGH_SCHOOL.equalsIgnoreCase(proposerEducation)
    					&& !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome)
                    <= restrictionData.getMinimumEducationIncome()) {
                isError =true;
    		}
    	}
        proposerEducationMessage(messages, proposerEducation, formType, isError, schemeType);
    }

	private void proposerEducationMessage(List<String> messages, String proposerEducation, String formType, boolean isError, String schemeType) {
		if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isError && AppConstants.FORM3.equalsIgnoreCase(formType)
                && !Utility.schemeBCase(schemeType)) {
    		messages.add(INSURED_EDUCATION_MSG + proposerEducation);
    		log.info(INSURED_EDUCATION_MSG, proposerEducation);
    	}
        else if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isError) {
    		messages.add(PROPOSER_EDUCATION_MSG + proposerEducation);
    		log.info(PROPOSER_EDUCATION_LOG, proposerEducation);
    	}
    }
    private void validateSSPNRIRuleForEducation(String education, RestrictionData restrictionData) {
        addMessageConditionally(!isEmpty(education) && !restrictionData.getNriEducations().contains(education.trim().toUpperCase()));
    }

    private void validateInsurerEducation(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSspSwissReCase){
        String insuredEducation = productRestrictionPayload.getInsurerEducation();
        String productId = productRestrictionPayload.getProductId();
        String nationality = productRestrictionPayload.getNationality();
        String formType = productRestrictionPayload.getFormType();
        String insurerNationality = productRestrictionPayload.getInsurerNationality();

        //FUL2-147943 Restricted education for SSP JointLife's SecondaryLife
        if (AppConstants.YES.equalsIgnoreCase(productRestrictionPayload.getIsJointLife()) && !isEmpty(insuredEducation)
				&& AppConstants.JOINTLIFE_NATIONALITY.contains(insurerNationality.toUpperCase())
				&& (!GraduateAndaboveGraduateEducations.contains(insuredEducation.toUpperCase().trim())
						&& !AppConstants.SENIOR_SCHOOL.equalsIgnoreCase(insuredEducation))) {
        	messages.add(INSURED_EDUCATION_MSG + insuredEducation);
            log.info(INSURED_EDUCATION_LOG, insuredEducation);
		}

        //FUL2-25393 All Channels Except Axis | SSP-NRI changes

        if (isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
                validateSSPNRIRuleForEducation(insuredEducation, restrictionData);
            }
            return;
        }
        /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1 : Added condition to allow High School*/

    }
    private void validatePanNumber(String panNumber, ProductRestrictionPayload productRestrictionPayload, List<String> messages) {
        if (productRestrictionPayload.getNationality().trim().equalsIgnoreCase("Indian") && isEmpty(panNumber)) {
            messages.add("Pan number | " + panNumber);
            log.info("Pan number can't be blank or null for Indian");
        }
    }
    private void validatePosvQuestions(ProductRestrictionPayload productRestrictionPayload, ProposalDetails proposalDetails, List<String> messages, List<String> errorMessages) {

        try {
            Map<String, String> illitrateMap = new HashMap<>();
            List<PartyInformation> partyInformations = proposalDetails.getPartyInformation();
            String formType=productRestrictionPayload.getFormType();
            for (PartyInformation partyInformation : partyInformations) {
                illitrateMap.put(partyInformation.getPartyType(), partyInformation.getBasicDetails().getEducation());
            }
            String msg = AppConstants.BLANK ;
            if(formType.equalsIgnoreCase(AppConstants.SELF)
                    || Utility.schemeBCase(formType,productRestrictionPayload.getSchemeType())) {
                msg = msg + validateProposerEducationPOS(illitrateMap,proposalDetails.getLifeStyleDetails().get(0) ,proposalDetails.getEmploymentDetails());
                msg = msg + occupationValidationPOS(proposalDetails.getLifeStyleDetails().get(0));
                msg = msg + pepValidationPOS(proposalDetails.getEmploymentDetails());
                msg = msg + pregnancyValidationPOS(proposalDetails.getPartyInformation().get(0));
            }else if(formType.equalsIgnoreCase(AppConstants.DEPENDENT)) {
                msg =  msg + validateInsuredEducationPOS(illitrateMap,proposalDetails.getLifeStyleDetails().get(1),proposalDetails.getEmploymentDetails());
                msg =  msg + occupationValidationPOS(proposalDetails.getLifeStyleDetails().get(1));
                msg =  msg + pepValidationPOS(proposalDetails.getEmploymentDetails());
                msg =  msg + pregnancyValidationPOS(proposalDetails.getPartyInformation().get(1));
            }
            if(!isEmpty(msg)) {
                messages.add(POS_HEALTH_MSG);
                return;
            }
            validatePosvMedicalQuestionsForAxisPosSeller(productRestrictionPayload, proposalDetails, messages);
        } catch (Exception e) {
            errorMessages.add(ERR_MSG);
            log.error("Something went wrong!! Exception occurred while validatePosvQuestions:{}",Utility.getExceptionAsString(e));
        }
    }
    private void validatePosvMedicalQuestionsForAxisPosSeller(ProductRestrictionPayload productRestrictionPayload, ProposalDetails proposalDetails, List<String> messages) {
        String msg = AppConstants.BLANK;
        //FUL2-64273 Medical Ques Validation
        if(nonNull(proposalDetails.getAdditionalFlags()) && AppConstants.N
                .equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowHealthQuesOnPosv())
                && Objects.nonNull(proposalDetails.getPosvDetails())){
            Map<String, String> questionMap = new HashMap<>();
            createPosvQuesAnsMap(questionMap,proposalDetails);
            if (questionMap.get("POSMED1").equals("Y") || questionMap.get("POSMED2").equals("Y") || questionMap.get("H13F").equals("Y")) {
                messages.add(POS_HEALTH_MSG);
                return;
            }
            if (questionMap.get("H13fi").equals("Y")) {
                String panOrCigretOrBeediOrGutka = questionMap.get("H13Fia");
                msg = validateTobaccoPOS(panOrCigretOrBeediOrGutka, questionMap);
            }
            if (!isEmpty(msg)) {
                messages.add(POS_HEALTH_MSG);
                return;
            }
            if (questionMap.get("H13Fii").equals("Y")) {
                String beersOrWineOrHardLiquor = questionMap.get("H13Fiia");
                msg = validateAlcoholPOS(beersOrWineOrHardLiquor, questionMap);
            }
            if (!isEmpty(msg)) {
                messages.add(POS_HEALTH_MSG);
            }
        }

    }
    private void validateSumAssuredForPosSeller(ProposalDetails proposalDetails, ProductRestrictionPayload productRestrictionPayload,List<String> errorMessages,List<String> messages){
        log.info("Entering validateSumAssuredForPosSeller() for validating Pos seller SA for Transaction ID {}", productRestrictionPayload.getTransactionId());
        String gender = null;
        String productId = null;
        DedupeDetails dedupeDetails = null;
        String channelName = "";
        double totalSumAssured = productRestrictionPayload.getSumAssured();
        try {
            productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
            gender = proposalDetails.getPartyInformation().get(0).getBasicDetails().getGender();
            channelName = productRestrictionPayload.getChannel();
            totalSumAssured = getSumAssuredFromProductIllustrationResponse(proposalDetails);
            validatePOSSellerSumAssured(productId, gender, totalSumAssured,messages);
        } catch (Exception ex){
            errorMessages.add(ERR_MSG);
            log.error("Exception during getting data from proposalDetails for Transaction ID {}", productRestrictionPayload.getTransactionId());
            return;
        }

        if(messages.contains(AppConstants.MSG_POS_TRANSGENDER_MAX_SA) || messages.contains(AppConstants.MSG_POS_MAX_SA)){
            return;
        }
        try {
            UnderwritingServiceDetails underwritingServiceDetails = proposalDetails.getUnderwritingServiceDetails();
            if(underwritingServiceDetails!=null) {
                List<DedupeDetails> dedupeDetail =  underwritingServiceDetails.getDedupeDetails();
                if(dedupeDetail!=null) {
                    dedupeDetails= dedupeDetail.stream()
                            .filter(d -> AppConstants.PROPOSER.equalsIgnoreCase(d.getClientType()))
                            .findFirst().orElse(null);
                }
            }
        } catch (Exception ex){
            errorMessages.add(ERR_MSG);
            log.error("Exception during getting dedupe details from proposalDetails for Transaction ID {}", productRestrictionPayload.getTransactionId());
            return;
        }

        if (dedupeDetails != null && AppConstants.EXACT_MATCH.equalsIgnoreCase(dedupeDetails.getDedupeFlag())) {
            try {
                com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest policyDetails = setDataForClientPolicyDetails(proposalDetails,dedupeDetails.getClientId());
                OutputResponse response = new OutputResponse();
                HttpEntity<com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest> request = new HttpEntity<>(policyDetails);
                response = getOutputResponseForCPD(policyDetails, response, request);
                String msgCode = Objects.nonNull(response) ?response.getResponse().getMsgInfo().getMsgCode() : "";
                if(msgCode.equalsIgnoreCase("200")){
                    log.info("Total sumAssured before calling CPD service is {} for Transaction ID {}", totalSumAssured, productRestrictionPayload.getTransactionId());
                    com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.Payload payloads = Objects.nonNull(response) ? response.getResponse().getPayload() : null;
                    List<String> posProductList = SWAG.equalsIgnoreCase(productId) ? FDRD_SWAG_SWP_POS_PRODUCTS : POS_PRODUCTS;
                    //FUL2-32184
                    if(payloads!=null && CollectionUtils.isEmpty(payloads.getPolicyDetails())) {
                        //FUL2-123815
                        if(CHANNEL_AGENCY.equalsIgnoreCase(channelName) || CHANNEL_CAT.equalsIgnoreCase(channelName) || AppConstants.CHANNEL_YBL.equalsIgnoreCase(channelName)  || AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channelName)){

                            /*FUL2-32184 we need to consider gdb value instead of sum assured for max SA check for Axis and YBL POS*/
                            totalSumAssured += payloads.getPolicyDetails().stream().filter(payload->posProductList.contains(payload.getPlanCode())
                                        && payload.getGdb() != null && !payload.getGdb().isEmpty()).mapToDouble(payload -> Double.parseDouble(payload.getGdb())).sum();
                            /* End of FUL2-32184 we need to consider gdb value instead of sum assured for max SA check for Axis and YBL POS*/

                        }
                    }
                    log.info("Total sumAssured for previous policies collect from CPD service is {} for Transaction ID {}", totalSumAssured, productRestrictionPayload.getTransactionId());
                    validatePOSSellerSumAssured(productId, gender, totalSumAssured,messages);
                    if(messages.contains(AppConstants.MSG_POS_TRANSGENDER_MAX_SA) || messages.contains(AppConstants.MSG_POS_MAX_SA)){
                        return;
                    }
                } else if (msgCode.equalsIgnoreCase("500")) {
                    log.info("Client Policy details service failed to give response with error code 500 ");
                    errorMessages.add(ERR_MSG);
                    return;
                }

            } catch (Exception ex){
                ex.printStackTrace();
                log.error("Exception stack trace of clientPolicyDetails service {}", ex.getMessage());
            }
        }
    }
    private String validateProposerEducationPOS(Map<String, String> illitrateMap ,LifeStyleDetails lifeStyleDetails ,EmploymentDetails employmentDetails) {
        float bmiValue = calculateBmi(lifeStyleDetails);
        if((!isEmpty(illitrateMap.get(AppConstants.PROPOSER)) && illitrateMap.get(AppConstants.PROPOSER).equalsIgnoreCase(AppConstants.ILLITERATE)) || ((bmiValue < 18f) || (bmiValue > 33f))) {
            return POS_HEALTH_MSG;
        }
        String msg = AppConstants.BLANK;
        String industryType = !isEmpty(employmentDetails.getPartiesInformation().get(0).getPartyDetails().getIndustryDetails().getIndustryType()) ? employmentDetails.getPartiesInformation().get(0).getPartyDetails().getIndustryDetails().getIndustryType(): AppConstants.BLANK;
        switch (industryType) {
            case AppConstants.DEFENCE:
            case AppConstants.MINING:
            case AppConstants.OIL:
            case AppConstants.MERCHANT_MARINE:
            case AppConstants.NAVY:
            case AppConstants.CRPF:
                msg= POS_HEALTH_MSG;
                break;

            default:
                log.info("No industryType found or match");
                break;
        }

        return msg;
    }
    private String occupationValidationPOS(LifeStyleDetails lifeStyleDetails) {
        if(lifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities().equalsIgnoreCase("Yes")|| lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad().equalsIgnoreCase("Yes")){
            return POS_HEALTH_MSG;
        }
        return AppConstants.BLANK;
    }
    private String pepValidationPOS(EmploymentDetails employmentDetails) {
        if(employmentDetails.isPoliticallyExposed()){
            return POS_HEALTH_MSG;
        }
        return AppConstants.BLANK;
    }
    private String pregnancyValidationPOS(PartyInformation partyInformation) {
        if (!isEmpty(partyInformation.getBasicDetails().getGender())
                && partyInformation.getBasicDetails().getGender().equalsIgnoreCase("F")
                && partyInformation.getBasicDetails().getMarriageDetails().isPregnant()
                && !isEmpty(partyInformation.getBasicDetails().getMarriageDetails().getPregnantSince())
                && Float.parseFloat(partyInformation.getBasicDetails().getMarriageDetails()
                .getPregnantSince()) > 6.0) {
            return POS_HEALTH_MSG;
        }
        return AppConstants.BLANK;
    }
    private String validateInsuredEducationPOS(Map<String, String> illitrateMap ,LifeStyleDetails lifeStyleDetails ,EmploymentDetails employmentDetails) {
        float bmiValue = calculateBmi(lifeStyleDetails);
        if((!isEmpty(illitrateMap.get(AppConstants.INSURED)) && illitrateMap.get(AppConstants.INSURED).equalsIgnoreCase(AppConstants.ILLITERATE)) || ((bmiValue < 18f) || (bmiValue > 33f))) {
            return POS_HEALTH_MSG;
        }
        String msg = AppConstants.BLANK;
        String industryType = !isEmpty(employmentDetails.getPartiesInformation().get(1).getPartyDetails().getIndustryDetails().getIndustryType()) ? employmentDetails.getPartiesInformation().get(1).getPartyDetails().getIndustryDetails().getIndustryType(): AppConstants.BLANK;
        switch (industryType) {
            case AppConstants.DEFENCE:
            case AppConstants.MERCHANT_MARINE:
            case AppConstants.CRPF:
            case AppConstants.MINING:
            case AppConstants.OIL:
            case AppConstants.NAVY:
                msg= POS_HEALTH_MSG;
                break;

            default:
                log.info("No industryType found or match");
                break;
        }
        return msg;
    }
    private void createPosvQuesAnsMap(Map<String, String> questionMap ,ProposalDetails proposalDetails){
        List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
        for (PosvQuestion posvQuestion : posvQuestions) {
            questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
        }
    }
    private String validateTobaccoPOS(String panOrCigretOrBeediOrGutka, Map<String, String> questionMap) {
        int count = Integer.parseInt(questionMap.get("H13Fib"));
        String msg = AppConstants.BLANK;
        log.info("Tobacco type for pos {}{}", panOrCigretOrBeediOrGutka,count);
        switch (panOrCigretOrBeediOrGutka) {
            case "CIGARETTE":
            case "BEEDI":
                if(count > 10) {
                    msg = POS_HEALTH_MSG;
                }
                break;
            case "PAN MASALA":
            case "GUTKHA":
                if(count > 5) {
                    msg = POS_HEALTH_MSG;
                }
                break;
            default:
                log.info("No panOrCigretOrBeediOrGutka found ");
        }
        return msg;
    }
    private String validateAlcoholPOS(String beersOrWineOrHardLiquor, Map<String, String> questionMap) {
        String msg = AppConstants.BLANK;
        int quantity = Integer.parseInt(questionMap.get("H13Fiib"));
        log.info("Alcohol type for pos {} {}", beersOrWineOrHardLiquor,quantity);
        switch (beersOrWineOrHardLiquor) {
            case "HARD LIQUOR":
                if(quantity > 180)
                    msg= POS_HEALTH_MSG;
                break;
            case "BEER":
                if(quantity > 700)
                    msg= POS_HEALTH_MSG;
                break;
            case "WINE":
                if(quantity > 400)
                    msg= POS_HEALTH_MSG;
                break;
            default:
                log.info("No beersOrWineOrHardLiquer found ");
        }
        return msg;
    }
    private double getSumAssuredFromProductIllustrationResponse(ProposalDetails proposalDetails) {
        return Optional.ofNullable(proposalDetails.getProductDetails())
                .flatMap(productDetails -> productDetails.stream().findFirst())
                .map(ProductDetails::getProductInfo)
                .map(ProductInfo::getProductIllustrationResponse)
                .map(ProductIllustrationResponse::getGuaranteedDeathBenefit).map(Double::valueOf)
                .orElse(0.0);
    }

    private OutputResponse getOutputResponseForCPD(com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest policyDetails, OutputResponse response, HttpEntity<com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest> request) throws UserHandledException {
        if(FeatureFlagUtil.isFeatureFlagEnabled(CLIENT_POLICY_DETAILS_FEATURE_FLAG)){
            log.info("Into data lake CPD call for validateSumAssuredForPosSeller URL is {}", cpdDataLakeUrl);
            ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(policyDetails, cpdDataLakeUrl);
            if (responseEntity != null && responseEntity.getBody() != null) {
                response = new ObjectMapper().convertValue(responseEntity.getBody(), OutputResponse.class);
            }
            log.info("validateSumAssuredForPosSeller CPD Response via data lake API is {}", response);
        }else {
            log.info("The request being sent to the clientPolicyDetails soa service {}", request);
            try {
                response = new RestTemplate().postForEntity(cpdUrl, request, OutputResponse.class).getBody();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("Exception stack trace of clientPolicyDetails service {}", ex.getMessage());
                log.error("exception occured for processing request {} ", request);
                log.error("Client Policy details service failed to give response {} ", response);
            }
        }
        return response;
    }
    private void validatePOSSellerSumAssured(String productId, String gender, double productRestrictionSumAssured, List<String> messages) {
        log.info("Validating for Gender {} & ProductID {}", gender, productId);

        if ((AppConstants.SWP_PRODUCT_ID.equalsIgnoreCase(productId)) && AppConstants.OTHERS.equalsIgnoreCase(gender) && productRestrictionSumAssured > AppConstants.POS_TRANSGENDER_MAX_SA) {
            messages.add(AppConstants.MSG_POS_TRANSGENDER_MAX_SA);
        } else if ((AppConstants.SWP_PRODUCT_ID.equalsIgnoreCase(productId) || SWAG.equalsIgnoreCase(productId)) && productRestrictionSumAssured > AppConstants.POS_MAX_SA) {
            messages.add(AppConstants.MSG_POS_MAX_SA);
        }
    }
    private com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest setDataForClientPolicyDetails(ProposalDetails proposalDetails, String clientId)
            throws UserHandledException {
        Request policyDetailsRequest = new Request();
        Header header = new Header();
        Payload payload = new Payload();
        com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest request = new com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest();
        try {
            payload.setClientId(clientId);
payload.setPlanCode(ProposalUtil.getPlanCodeBasedOnProductType(proposalDetails));            header.setSoaAppId(AppConstants.FULFILLMENT);
            header.setSoaCorrelationId(String.valueOf(proposalDetails.getTransactionId()));
            policyDetailsRequest.setHeader(header);
            policyDetailsRequest.setPayload(payload);
            request.setRequest(policyDetailsRequest);
        } catch (Exception e) {
            e.printStackTrace();
            List<String> errorMessages = new ArrayList<>();
            ErrorMessageConfig errorMessageConfig = BeanUtil.getBean(ErrorMessageConfig.class);
            errorMessages.add(errorMessageConfig.getErrorMessages().get("requestCreation"));
            throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return request;
    }
    private float calculateBmi(LifeStyleDetails lifeStyleDetails) {

        HeightAndWeight heightAndWeight;
        heightAndWeight = lifeStyleDetails.getHeightAndWeight();
        String heightMetric = heightAndWeight.getHeightMetric();
        float weight = StringUtils.isEmpty(heightAndWeight.getWeight()) ? 1 : Float.valueOf(heightAndWeight.getWeight());
        float heightInMeter = 1;
        if ("CM".equalsIgnoreCase(heightMetric) && !StringUtils.isEmpty(heightAndWeight.getHeight())) {
            heightInMeter = Float.valueOf(heightAndWeight.getHeight()) / 100;
        }
        log.info("Calculated bmi for pos {}", (float) (weight / Math.pow(heightInMeter, 2)));
        return (float) (weight / Math.pow(heightInMeter, 2));
    }


    /*FUL2-13711 Income Criteria Change for SmTP and OTP: Changed the income field for one crore and two crore*/


    //FUL2-144984


    //FUL2-83504 CIP - Medical Stop Rules (Check Question)


    //FUL2-83504 CIP - BMI Stop Rules


    //FUL2-83504 CIP - NRI Country Stop Rules
    private List<String> validateCIPCountryList(ProductRestrictionPayload productRestrictionPayload, List<String> messages) {

        if(cipCountries == null) {
            errorMessages.add("CIP Countries data not available in DB");
            log.info("CIP Countries data not available in DB");
        }
        if(NRI.equalsIgnoreCase(productRestrictionPayload.getNationality()) && Objects.nonNull(cipCountries)) {
            boolean isCIPCountry = cipCountries.stream().anyMatch(cipCountry ->
                    cipCountry.equalsIgnoreCase(productRestrictionPayload.getCommunicationCountry()) &&
                            cipCountry.equalsIgnoreCase(productRestrictionPayload.getPermanentCountry()));
            if (!isCIPCountry) {
                log.info("CIP Countries blocker for all Product and all channels TransactionID {}", productRestrictionPayload.getTransactionId());
                messages.add(CIP_RESTRICTION_MSG);
            }
        }
        return messages;
    }
    /*
  F21-578 WLP City and GIP Sourcing Restriction Logic: creating the method "validateProductRestrictionDataForWLS" to restrict the cities
              for Whole life Super (Any Variant), Guaranteed Income Plan (Any Variant)
   */
    private ResponsePayload validateProductLimitationData(ProductRestrictionPayload productRestrictionPayload) {
        String communicationCity = productRestrictionPayload.getCommunicationCity().trim(),
                communicationState = productRestrictionPayload.getCommunicationState().trim(),
                proposerEducation = getproposerEducation(productRestrictionPayload),
                channel = productRestrictionPayload.getChannel(),
                productId = productRestrictionPayload.getProductId(),
                proposerOccupation = productRestrictionPayload.getOccupation().trim(),
                insurerEducation = getInsurerEducation(productRestrictionPayload),
                insurerOccupation = productRestrictionPayload.getInsurerOccupation().trim(),
                formType = productRestrictionPayload.getFormType();
        ResponsePayload responsePayload = new ResponsePayload();
        messages = new ArrayList<>();
        try {
            if(!isEmpty(productId) && AppConstants.GIP_PRODUCT_ID.equals(productId) && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)) {
                LimitationData proposerLimitationData = getLimitationData(channel, productId, proposerOccupation.toUpperCase(), communicationCity.toUpperCase());

                validateProposerLimitationData(proposerLimitationData, proposerEducation, channel,
                        productId, proposerOccupation, communicationCity);
                if (!isEmpty(formType) && !formType.equalsIgnoreCase(AppConstants.SELF)) {
                    LimitationData insuredLimitationData = getLimitationData(channel, productId, insurerOccupation.toUpperCase(), communicationCity.toUpperCase());
                    validateInsuredLimitationData(insuredLimitationData, insurerEducation, channel,
                            productId, insurerOccupation, communicationCity);
                }
            } /* FUL2-9472 WLS Product Restriction */
            else if (!isEmpty(productId) && AppConstants.WLS_PRODUCT_ID.equals(productId)) {
                LimitationData wlsRestrictionData = getLimitationData(productId,AppConstants.LOCATION_BASED_RESTRICTION);
                if(wlsRestrictionData!=null) {
                    if (!Utility.checkSchemeBWithWipCase(formType, productRestrictionPayload.getSchemeType())) {
                        validateLimitationData(communicationCity, communicationState, insurerEducation, wlsRestrictionData, responsePayload);
                    } else {
                        validateLimitationData(communicationCity, communicationState, proposerEducation, wlsRestrictionData, responsePayload);
                    }
                } else {
                    errorMessages.add(AppConstants.LOCATION_BASED_RESTRICTION + " limitation data not available in DB");
                    log.info(AppConstants.LOCATION_BASED_RESTRICTION + " limitation data not available in DB");
                }
            }
            responsePayload.setMessages(messages);
        } catch (Exception e) {
            log.error("Something went wrong!! Exception occurred while validateProductLimitationData: ",e);
            errorMessages.add(ERR_MSG);
        }
        return responsePayload;
    }
    private List<String> checkFrequency(List<String> messages) {
        List<String> listMessage= new ArrayList<>();
        List<String> finalMessage= new ArrayList<>();
        for(String msg: messages){
            listMessage.add(msg);
            if(Collections.frequency(listMessage,msg)<2){
                finalMessage.add(msg);
            }
        }
        messages.clear();
        messages.addAll(finalMessage);
        return messages;
    }

    private void CIRiderSAFunction(ProductRestrictionPayload productRestrictionPayload, List<String> messages, Configuration configurationData) {
        try {
            String age1 ="";
            double twoYearCISumAssured=0.0;
            double existingCIRiderSumAssured=0.0;
            CIMaxSumAssured=configurationData.getMaxSumAssured();
            CIMinSumAssured=configurationData.getMinSumAssured();
            CIRiderStatusCodes=configurationData.getRiderStatusCodes();
            CIRiderCodesList=configurationData.getRiderCodeList();
            ageGroupMaxSumAssured =configurationData.getAgeGroupMaxSumAssured();
            long transactionId=productRestrictionPayload.getTransactionId();
            if(Utility.and(Objects.nonNull(CIRiderStatusCodes),Objects.nonNull(CIRiderCodesList),Objects.nonNull(ageGroupMaxSumAssured))){
                log.info("Executing CI Rider SumAssured Function for transactionId {} ",transactionId);
                String dedupe = productRestrictionPayload.getDedupeFlag();
                String income = productRestrictionPayload.getIncome();
                if(DEPENDENT.equalsIgnoreCase(productRestrictionPayload.getFormType())){
                    age1 = Utility.getAge(productRestrictionPayload.getInsurerDateOfBirth());
                }else{
                    age1 = Utility.getAge(productRestrictionPayload.getDateOfBirth());
                }
                double currentCIRiderSum=Double.valueOf(StringUtils.isEmpty(productRestrictionPayload.getCurrentCIRiderSumAssured())?"0":productRestrictionPayload.getCurrentCIRiderSumAssured());
                int age = Integer.parseInt(age1);
                double incomeLimit =Double.parseDouble(income)*5;
                double ageLimit = validateAgeLimit(age,ageGroupMaxSumAssured);
                if(Utility.and(EXACT_MATCH.equalsIgnoreCase(dedupe),Objects.nonNull(productRestrictionPayload.getClientPolicyProposerDetails()))){
                    log.info("In CI Rider SumAssured Function dedupe is Exact match for transactionId {} ",transactionId);
                    List<com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails> policyDetailsList = productRestrictionPayload.getClientPolicyProposerDetails();
                    validatePolicyDetails(policyDetailsList,transactionId,twoYearCISumAssured,existingCIRiderSumAssured);
                    validateOneCroreSumForCIRider(existingCIRiderSumAssured,currentCIRiderSum,messages,transactionId);
                    incomeMultiplierForCIRider(twoYearCISumAssured, currentCIRiderSum, incomeLimit,ageLimit,messages,transactionId);
                }else{
                    incomeMultiplierForCIRider(0.0,currentCIRiderSum, incomeLimit,ageLimit,messages,transactionId);
                }
            }
        }catch (Exception e){
            log.error("Exception occurred during validating the CI rider sum assured for transactionId {}",productRestrictionPayload.getTransactionId());
        }
    }

    /* FUL2-9472 WLS Product Restriction */
    private ValidateProposalDataResponse setValidateProposalDataResponse(ValidateProposalDataResponse validateProposalDataResponse, ResponsePayload responsePayload) {
        if(!isEmpty(responsePayload.getShouldEnableDoc()) && responsePayload.getShouldEnableDoc().equalsIgnoreCase("Yes")) {
            validateProposalDataResponse.setShouldEnableDoc(responsePayload.getShouldEnableDoc());
        }
        return validateProposalDataResponse;
    }
    /*
 FUL2-5523 Enabling CIP Plan for NRI Customers : Validating restriction for CIP plan
*/
    private List<String> validateCIPProductRestrictionData(ProductRestrictionPayload productRestrictionPayload) {

        String communicationCountry = null, permanentCountry = null;

        communicationCountry = !isEmpty(productRestrictionPayload.getCommunicationCountry()) ? productRestrictionPayload.getCommunicationCountry().trim().toUpperCase() : AppConstants.BLANK;
        log.info("communication country {}", communicationCountry);
        permanentCountry = !isEmpty(productRestrictionPayload.getPermanentCountry()) ? productRestrictionPayload.getPermanentCountry().trim().toUpperCase() : AppConstants.BLANK;
        log.info("permanent country {}", permanentCountry);

        if (countryWithCIRiderProductRestrictionMasterDataMap != null) {
            List<Date> communicationCountryWithCIRiderDates = countryWithCIRiderProductRestrictionMasterDataMap.get(communicationCountry);
            List<Date> permanentCountryWithCIRiderDates = countryWithCIRiderProductRestrictionMasterDataMap.get(permanentCountry);
            if (communicationCountryWithCIRiderDates != null) {
                Date lastIndexOfDate = communicationCountryWithCIRiderDates.get(communicationCountryWithCIRiderDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(AppConstants.COMMUNICATION_COUNTRY + communicationCountry);
                    log.info("Communication Country {} is not allowed for this plan", communicationCountry);
                }
            }
            if (permanentCountryWithCIRiderDates != null) {
               Date lastIndexOfDate = permanentCountryWithCIRiderDates.get(permanentCountryWithCIRiderDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(AppConstants.PERMANENT_COUNTRY + permanentCountry);
                    log.info("Permanent Country {} is not allowed for this plan", permanentCountry);
                }
            }
        } else {
            errorMessages.add("Country with CI Rider restriction data not available in DB");
            log.info("Country with CI Rider restriction data not available in DB");
        }

        return messages;
    }
    private void validateStopRulesForCIP(ProductRestrictionPayload productRestrictionPayload) {
        ProposalDetails proposalDetails = proposalRepository.findByTransactionId(productRestrictionPayload.getTransactionId());
        String currentScreen = productRestrictionPayload.getCurrentScreen();
        if(!AppConstants.TWO.equalsIgnoreCase(currentScreen) && !productRestrictionPayload.isPosSeller()
                && !AppConstants.FORM3.equalsIgnoreCase(productRestrictionPayload.getFormType())) {
            validateBMIAndMedicalQues(productRestrictionPayload, proposalDetails, messages);
        }
    }
    //FUL2-83504 CIP - BMI and Medical Stop Rules
    private void validateBMIAndMedicalQues(ProductRestrictionPayload productRestrictionPayload, ProposalDetails proposalDetails, List<String> messages) {

        try {
            String formType=productRestrictionPayload.getFormType();
            String msg = AppConstants.BLANK ;
            if(AppConstants.SELF.equalsIgnoreCase(formType)
                    || Utility.schemeBCase(formType,productRestrictionPayload.getSchemeType())) {
                Optional<LifeStyleDetails> lifeStyleDetailsProposer = proposalDetails.getLifeStyleDetails().stream().filter(lifeStyleDetails ->
                        PROPOSER.equalsIgnoreCase(lifeStyleDetails.getPartyType())).findFirst();
                msg = lifeStyleDetailsProposer.isPresent()? msg + validateBMI(lifeStyleDetailsProposer.get()) : msg + BLANK;
            }else if(AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
                Optional<LifeStyleDetails> lifeStyleDetailsInsured = proposalDetails.getLifeStyleDetails().stream().filter(lifeStyleDetails ->
                        INSURED.equalsIgnoreCase(lifeStyleDetails.getPartyType())).findFirst();
                msg = lifeStyleDetailsInsured.isPresent()? msg + validateBMI(lifeStyleDetailsInsured.get()) : msg + BLANK;
            }
            if(!isEmpty(msg)) {
                messages.add(CIP_RESTRICTION_MSG);
                return;
            }
            validateCIPMedicalQuestions(productRestrictionPayload, proposalDetails, messages);
        } catch (Exception e) {
            errorMessages.add(ERR_MSG);
            log.error("Something went wrong!! Exception occurred while validateBmiAndMedicalQuestions {}",Utility.getExceptionAsString(e));
        }
    }

    private void validateProposerLimitationData(LimitationData proposerLimitationData, String proposerEducation, String channel,
                                                String productId, String proposerOccupation, String communicationCity) {
        try {
            if (proposerLimitationData != null) {

                if (proposerLimitationData.getEducation().equalsIgnoreCase(AppConstants.YES)) {
                    validateData(proposerLimitationData, proposerEducation, proposerOccupation, communicationCity);
                }
            } else {
                proposerLimitationData = getLimitationData(channel, productId, AppConstants.NONE, communicationCity.toUpperCase());

                if (proposerLimitationData != null && proposerLimitationData.getEducation().equalsIgnoreCase(AppConstants.YES)) {
                    validateData(proposerLimitationData, proposerEducation, communicationCity);
                }
            }
        } catch (Exception e) {
            errorMessages.add(ERR_MSG);
            log.error("Something went wrong! Exception occurred while validateProposerLimitationData : ",e);
        }
    }
    //FUL2-83504 CIP - BMI Stop Rules
    private String validateBMI(LifeStyleDetails lifeStyleDetails) {
        //FUL2-83504 CIP - BMI Stop Rules Calculation
        float bmiValue = calculateBmi(lifeStyleDetails);
        if((bmiValue < 18f) || (bmiValue > 35f)) {
            return CIP_RESTRICTION_MSG;
        }
        return BLANK;
    }
    private void validateInsurerAnnualIncome(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSspSwissReCase) {
        String formType = productRestrictionPayload.getFormType();
        String insuredAnnualIncome = productRestrictionPayload.getInsurerAnnualIncome();
        String insuredEducation = productRestrictionPayload.getInsurerEducation();
        String insuredOccupation = productRestrictionPayload.getInsurerOccupation();
        String customerClassification = productRestrictionPayload.getCustomerClassification();
        String productId = productRestrictionPayload.getProductId();
        String nationality = productRestrictionPayload.getNationality();
        java.util.Date insuredDateOfBirth = productRestrictionPayload.getInsurerDateOfBirth();
        //FUL2-25393 All Channels Except Axis | SSP-NRI changes
        boolean isMsg = false;

        if (isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
                validateSSPNRIRuleForAnnualIncome(insuredAnnualIncome, restrictionData);
            }
            return;
        }
        /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1: Removed logic build for JIRA FUL2-5522 because of new requirment*/
        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && AppConstants.DEPENDENT.equalsIgnoreCase(formType) && !isEmpty(channel) && (channel.equals(AppConstants.CHANNEL_SPARC) || channel.equalsIgnoreCase(AppConstants.CAT))) {
            isMsg = validateCATAndSparcRulesForInsuredAnnualIncome(insuredOccupation,insuredAnnualIncome,insuredDateOfBirth,insuredEducation,restrictionData);
        }/*FUL2-6814 OTP and SmTP Stop Rules - Phase 1: Added condition to allow Income greater then specified income and Education Graduate and above with High School also allowed based on Customer Classification*/
        else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && (AppConstants.DEPENDENT.equalsIgnoreCase(formType) && !exceptionalCustomerClassification.contains(customerClassification) && !isEmpty(insuredAnnualIncome) && ((Integer.parseInt(insuredAnnualIncome)
                < restrictionData.getMinimumIncome() && underGraduateEducations.contains(insuredEducation.toUpperCase().trim())) || (insuredEducation.equalsIgnoreCase(AppConstants.HIGH_SCHOOL)
                && Integer.parseInt(insuredAnnualIncome)<restrictionData.getMinimumHighSchoolIncome()))) || (AppConstants.DEPENDENT.equalsIgnoreCase(formType) && exceptionalCustomerClassification.contains(customerClassification)
                && !isEmpty(insuredAnnualIncome) && Integer.parseInt(insuredAnnualIncome) < restrictionData.getMinimumIncome())) {
            isMsg = true;
        }
        if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isMsg) {
            messages.add(INSURED_INCOME_MSG + insuredAnnualIncome);
            log.info(INSURED_INCOME_LOG, insuredAnnualIncome);
        }
    }
    private boolean validateCATAndSparcRulesForInsuredAnnualIncome(String insuredOccupation, String insuredAnnualIncome, java.util.Date dateOfBirth, String insuredEducation,
                                                                   RestrictionData restrictionData) {
        if ((!isEmpty(insuredOccupation) && insuredOccupation.trim().equalsIgnoreCase(AppConstants.SALARIED) && !isEmpty(insuredAnnualIncome) &&
                Integer.parseInt(insuredAnnualIncome) < restrictionData.getSparcSalIncome() && getAge(dateOfBirth) <= restrictionData.getSparcAgeLimit())
                || (!isEmpty(insuredOccupation) && insuredOccupation.trim().equalsIgnoreCase(AppConstants.SELF_EMPLOYED) && !isEmpty(insuredAnnualIncome) &&
                Integer.parseInt(insuredAnnualIncome) < restrictionData.getSparcSelfEmployedIncome() && getAge(dateOfBirth) <= restrictionData.getSparcAgeLimit()) ||
                (!isEmpty(insuredAnnualIncome) && Integer.parseInt(insuredAnnualIncome) < restrictionData.getMinimumIncome() && getAge(dateOfBirth) > restrictionData.getSparcAgeLimit())
                || (!isEmpty(insuredAnnualIncome) && ((Integer.parseInt(insuredAnnualIncome) < restrictionData.getMinimumIncome() && !underGraduateEducations.contains(insuredEducation.toUpperCase().trim())) ||
                ((insuredEducation.equalsIgnoreCase(AppConstants.HIGH_SCHOOL) || insuredEducation.equalsIgnoreCase(AppConstants.SENIOR_SCHOOL)) && Integer.parseInt(insuredAnnualIncome)<restrictionData.getMinimumHighSchoolIncome())))) {
            return true;
        }
        return false;
    }
    private void validateSSPNRIRuleForAnnualIncome(String annualIncome, RestrictionData restrictionData) {
        addMessageConditionally(!isEmpty(annualIncome) && Integer.parseInt(annualIncome)<restrictionData.getNriMinimumIncome());
    }
    private void proposerAnnualIncomeMessage(List<String> messages, String proposerAnnualIncome, String formType, boolean isMsg, String schemeType) {
        if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isMsg && AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
            messages.add(INSURED_INCOME_MSG + proposerAnnualIncome);
            log.info(INSURED_INCOME_MSG, proposerAnnualIncome);
        }
        else if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isMsg) {
            messages.add(PROPOSER_INCOME_MSG + proposerAnnualIncome);
            log.info(PROPOSER_INCOME_LOG, proposerAnnualIncome);
        }
    }
    private boolean salAnnualIncomeEducationValidation(String customerClassification, String proposerOccupation, String proposerAnnualIncome, RestrictionData restrictionData, String proposerEducation) {
        return  (!exceptionalCustomerClassification.contains(customerClassification) && AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation) && !isEmpty(proposerAnnualIncome) && ((Integer.parseInt(proposerAnnualIncome)
                < restrictionData.getMinimumIncome() && !StringUtils.isEmpty(proposerEducation) &&  !(AppConstants.POST_GRADUATE.equalsIgnoreCase(proposerEducation)||AppConstants.GRADUATE.equalsIgnoreCase(proposerEducation)|| AppConstants.SENIOR_SCHOOL.equalsIgnoreCase(proposerEducation))) || (AppConstants.SENIOR_SCHOOL.equalsIgnoreCase(proposerEducation)
                && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumEducationIncome()))) ;
    }
    private void validateProposerAnnualIncome(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSSESProduct, boolean isSspSwissReCase) {
        String proposerAnnualIncome = productRestrictionPayload.getIncome();
        String proposerEducation = productRestrictionPayload.getEducation();
        String proposerOccupation = productRestrictionPayload.getOccupation();
        String customerClassification = productRestrictionPayload.getCustomerClassification();
        String formType = productRestrictionPayload.getFormType();
        String schemeType = productRestrictionPayload.getSchemeType();
        String nationality = productRestrictionPayload.getNationality();
        String productId = productRestrictionPayload.getProductId();
        java.util.Date dateOfBirth = productRestrictionPayload.getDateOfBirth();
        //FUL2-25393 All Channels Except Axis | SSP-NRI changes
        boolean isMsg = false;
        if ( isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (AppConstants.SELF.equalsIgnoreCase(formType)
                    ||Utility.schemeBCase(formType,schemeType)) {
                validateSSPNRIRuleForAnnualIncome(proposerAnnualIncome, restrictionData);
            }
            return;
        }

        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && FPS_PRODUCT_ID.equalsIgnoreCase(productId)) {
            isMsg = validateFlexiWealthProtectProposerIncome(proposerAnnualIncome, restrictionData, proposerEducation);
        }
        // FUL2 - 5522 OTP and SmTP Product Restriction - CAT and SPARC
        /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1: Removed logic build for JIRA FUL2-5522 because of new requirment*/
        else if ( Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && !isSSESProduct && !isEmpty(channel)
                && (channel.equals(AppConstants.CHANNEL_SPARC) || channel.equalsIgnoreCase(AppConstants.CAT))) {
            isMsg = validateCATAndSparcRulesForProposerAnnualIncome(proposerOccupation,proposerAnnualIncome,dateOfBirth,proposerEducation,restrictionData,isSspSwissReCase);
        }/*FUL2-53692 stop rules for SSES product */
        else if ( Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isSSESProduct && validateProposerAnnualIncomeForSSES(proposerAnnualIncome, restrictionData , proposerEducation)) {
            isMsg = true;
        }/*FUL2-6814 OTP and SmTP Stop Rules - Phase 1: Added condition to allow Income greater then specified income and Education Graduate and above with High School also allowed based on Customer Classification*/
        else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && !isSSESProduct && (!isSspSwissReCase || OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation))
                && ((!exceptionalCustomerClassification.contains(customerClassification) && !AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation) && !isEmpty(proposerAnnualIncome) && ((Integer.parseInt(proposerAnnualIncome)
                < restrictionData.getMinimumIncome() && !underGraduateEducations.contains(proposerEducation.toUpperCase().trim())) || (proposerEducation.equalsIgnoreCase(AppConstants.SENIOR_SCHOOL)
                && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumEducationIncome()))) || salAnnualIncomeEducationValidation(customerClassification,proposerOccupation,proposerAnnualIncome,restrictionData,proposerEducation)||salAnnualIncomeValidation( customerClassification,  proposerAnnualIncome,  restrictionData, proposerOccupation))) {
            isMsg = true;
        }
        proposerAnnualIncomeMessage(messages, proposerAnnualIncome, formType, isMsg, schemeType);
    }

    private boolean validateProposerAnnualIncomeForSSES(String proposerAnnualIncome, RestrictionData restrictionData , String proposerEducation) {
        return !isEmpty(proposerAnnualIncome) && (Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome() || (SENIOR_SCHOOL.equalsIgnoreCase(proposerEducation)
                && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumEducationIncome()));
    }
    private boolean salAnnualIncomeValidation(String customerClassification, String proposerAnnualIncome, RestrictionData restrictionData, String proposerOccupation) {
        if(Utility.andThreeExpressions(exceptionalCustomerClassification.contains(customerClassification),AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation),(!StringUtils.isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome)>=ANNUAL_INCOME_3L && Integer.parseInt(proposerAnnualIncome)<ANNUAL_INCOME_5L))){
            return false;
        }else if(Utility.andThreeExpressions(!exceptionalCustomerClassification.contains(customerClassification),AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation),(!StringUtils.isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome)>=ANNUAL_INCOME_3L && Integer.parseInt(proposerAnnualIncome)<ANNUAL_INCOME_5L))){
            return false;
        }else if (!isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome()){
            return true;
        }
        return false;
    }
    //FUL2-139538 There is education restriction for occupation - housewife in SSP
    private boolean validateCATAndSparcRulesForProposerAnnualIncome(String proposerOccupation, String proposerAnnualIncome, java.util.Date dateOfBirth, String proposerEducation, RestrictionData restrictionData, boolean isSspSwissReCase) {
        if (!isSspSwissReCase && ((proposerOccupation.equalsIgnoreCase(SALARIED)
                && !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getSparcSalIncome() &&
                getAge(dateOfBirth) <= restrictionData.getSparcAgeLimit())
                || (proposerOccupation.equalsIgnoreCase(SELF_EMPLOYED) &&
                !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getSparcSelfEmployedIncome()
                && getAge(dateOfBirth) <= restrictionData.getSparcAgeLimit())
                || (OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation) &&
                !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome()))) {
            return true;
        }else if(conditionForHousewifeAndAnnualIncome(proposerOccupation,proposerAnnualIncome,restrictionData)
                || conditionForAnnualIncomeAndAge(proposerAnnualIncome,dateOfBirth,restrictionData)
                || conditionForAnnualIncomeAndEducation(proposerAnnualIncome,proposerEducation,restrictionData)
                || conditionForEducationAndAnnualIncome(proposerEducation,proposerAnnualIncome,restrictionData)){
            return true;
        }
        return false;
    }
    private boolean conditionForEducationAndAnnualIncome(String proposerEducation, String proposerAnnualIncome, RestrictionData restrictionData) {
        return ((proposerEducation.equalsIgnoreCase(HIGH_SCHOOL) || proposerEducation.equalsIgnoreCase(SENIOR_SCHOOL)) &&
                Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumHighSchoolIncome());
    }

    private boolean conditionForAnnualIncomeAndEducation(String proposerAnnualIncome, String proposerEducation, RestrictionData restrictionData) {
        return !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getSparcSalIncome() &&
                !underGraduateEducations.contains(proposerEducation.toUpperCase().trim());
    }

    private boolean conditionForAnnualIncomeAndAge(String proposerAnnualIncome, java.util.Date dateOfBirth, RestrictionData restrictionData) {
        return !isEmpty(proposerAnnualIncome)  && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome()
                && getAge(dateOfBirth) > restrictionData.getSparcAgeLimit();
    }

    //FUL2-139538 There is education restriction for occupation - housewife in SSP
    private boolean conditionForHousewifeAndAnnualIncome(String proposerOccupation, String proposerAnnualIncome, RestrictionData restrictionData) {
        return AppConstants.OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation) &&
                !isEmpty(proposerAnnualIncome) && Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome();

    }
    private void validateProposerOccupation(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSSESProduct, boolean isSspSwissReCase) {
        String proposerOccupation = productRestrictionPayload.getOccupation();
        String formType = productRestrictionPayload.getFormType();
        String schemeType = productRestrictionPayload.getSchemeType();
        String productId = productRestrictionPayload.getProductId();
        String nationality = productRestrictionPayload.getNationality();
        boolean isMsg=false;
        boolean isOccupationMsg=false;
        //FUL2-25393 All Channels Except Axis | SSP-NRI changes

        if (isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (AppConstants.SELF.equalsIgnoreCase(formType)
                    ||Utility.schemeBCase(formType,schemeType)) {
                validateSSPNRIRuleForOccupation(proposerOccupation, restrictionData);
            }
            return;
        }
        isOccupationMsg = !isEmpty(proposerOccupation) && !restrictionData.getOccupations().contains(proposerOccupation.toUpperCase());
        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && (isSSESProduct && (!isSspSwissReCase || OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation)))
                || FPS_PRODUCT_ID.equalsIgnoreCase(productId)) {
            isMsg = isOccupationMsg;
        }else if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && (!isSspSwissReCase || OCCUPATION_HOUSE_WIFE.equalsIgnoreCase(proposerOccupation))){
            isMsg = isOccupationMsg && !proposerOccupation.equalsIgnoreCase(AppConstants.OCCUPATION_HOUSE_WIFE);
        }
        proposerOccupationMessage(messages, proposerOccupation, formType, isMsg, schemeType, isSspSwissReCase);
    }
    private void validateInsurerOccupation(ProductRestrictionPayload productRestrictionPayload, RestrictionData restrictionData, List<String> messages, boolean isSspSwissReCase) {
        String insuredOccupation = productRestrictionPayload.getInsurerOccupation();
        String formType = productRestrictionPayload.getFormType();
        String productId = productRestrictionPayload.getProductId();
        String nationality = productRestrictionPayload.getNationality();
        //FUL2-25393 All Channels Except Axis | SSP-NRI changes

        if (isSSPNRICheck(productId,nationality,productRestrictionPayload, isSspSwissReCase)) {
            if (AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
                validateSSPNRIRuleForOccupation(insuredOccupation, restrictionData);
            }
            return;
        }
        if(AppConstants.DEPENDENT.equalsIgnoreCase(formType) && !isEmpty(insuredOccupation) && !restrictionData.getOccupations().contains(insuredOccupation.toUpperCase())) {
            messages.add(INSURED_OCCUPATION_MSG + insuredOccupation);
            log.info(INSURED_OCCUPATION_LOG, insuredOccupation);
        }
    }
    private void validateCity(String productId, String communicationCity, String communicationCountry,List<String> messages){
        //Different logic for City as only +Ve cities are provided and functionality works on -Ve values
        if(!cities.isEmpty()) {
            if (!isEmpty(productId) && !isEmpty(communicationCity) && !isEmpty(communicationCountry) && productId.equals(AppConstants.OTP_PRODUCT_ID)) {
                boolean isCommunicationCityAvailable = cities.stream().anyMatch(communicationCity.trim()::equalsIgnoreCase);
                if (!isCommunicationCityAvailable && communicationCountry.trim().equalsIgnoreCase("India")) {
                    messages.add("Communication City | " + communicationCity);
                    log.info("Communication City {} is not allowed", communicationCity);
                }
            }
        } else {
            errorMessages.add("City restriction data not available in DB");
            log.info("City restriction data not available in DB");
        }
    }

    private void validateSumAssured( String productId, double sumAssured, RestrictionData restrictionData, List<String> messages,
                                     String proposerOccupation, boolean isSSESProduct, boolean isSspSwissReCase) {
        /*FUL2_8762 OPT and Smtp Stop Rules - Phase 2 | Housewife*/

        if(!isSSESProduct && !isEmpty(proposerOccupation) && proposerOccupation.equalsIgnoreCase(AppConstants.OCCUPATION_HOUSE_WIFE) && sumAssured > 0.0d &&!(sumAssured>= restrictionData.getHousewifeMinSumAssured() && sumAssured <= restrictionData.getHousewifeMaxSumAssured())) {
            messages.add(SUM_ASSURED_MSG + sumAssured);
        }
        else if(!(FPS_PRODUCT_ID.equals(productId)) && !isEmpty(proposerOccupation) && !isSSESProduct && !isSspSwissReCase
                && sumAssured > 0.0d && (((sumAssured <SUM_ASSURED_25L)
                && AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation))||(sumAssured < restrictionData.getMinimumSumAssured()
                && !proposerOccupation.equalsIgnoreCase(AppConstants.OCCUPATION_HOUSE_WIFE)
                && !AppConstants.SALARIED.equalsIgnoreCase(proposerOccupation)))){
            messages.add(SUM_ASSURED_MSG +sumAssured);
            log.info(SUM_ASSURED_LOG,sumAssured);
        }
    }

    private void validateSmoker(boolean isSmoker, RestrictionData restrictionData, List<String> messages) {
        if (restrictionData.getShouldAllowSmoker().equals("No") && isSmoker) {
            messages.add("Smoker | " + isSmoker);
        }
    }
    private boolean isSspDedupeEx(String productId, String dedupeFlag) {
        return (SSP_PRODUCT_ID.equals(productId) && AppConstants.DEDUPE_EX.equalsIgnoreCase(dedupeFlag));
    }
    private void validationSSP1CrOr2CrSumAssured(ProductRestrictionPayload productRestrictionPayload,RestrictionData restrictionData, List<String> messages) {
        String proposerOccupation = getproposerOccupation(productRestrictionPayload);
        String education=getEducation(productRestrictionPayload);
        try{
            validatePinCodeFor1CrSumAssuredSSP(productRestrictionPayload,proposerOccupation ,restrictionData, messages,education);
            validatePinCodeFor2CrSumAssuredSSP(productRestrictionPayload,proposerOccupation, restrictionData, messages,education);
        }catch (Exception ex){
            log.error("Exception occurred in validationSSP1CrOr2CrSumAssured for transaction id {}, {}",productRestrictionPayload.getTransactionId(),Utility.getExceptionAsString(ex));
        }

    }
    private void validatePinCodeFor1CrSumAssuredSSP(ProductRestrictionPayload productRestrictionPayload,String proposerOccupation, RestrictionData restrictionData, List<String> messages, String education) {
        if (pinCode1CRSumAssuredProductRestrictionMasterDataMap != null) {
            List<Integer> rulesList1Cr = Arrays.asList(1, 2, 3,4);
            boolean notNullOccupation =!isEmpty(proposerOccupation);
            int rules1CrSumAssured=checkingCondition1CrSumAssured(productRestrictionPayload.getSumAssured(),proposerOccupation,productRestrictionPayload.getIncome(),restrictionData,education);
            log.info("For TransactionId {} SPP 1Cr SumAssured rule {}",productRestrictionPayload.getTransactionId(),rules1CrSumAssured);
            if (notNullOccupation && rulesList1Cr.contains(rules1CrSumAssured)) {
                List<Date> communicationPinCodeDates = pinCode1CRSumAssuredProductRestrictionMasterDataMap.get(productRestrictionPayload.getCommunicationPinCode());
                if (communicationPinCodeDates != null) {
                    Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                    pinCodeCodeRestriction1Cr(lastIndexOfDate,rules1CrSumAssured,proposerOccupation,restrictionData,messages,productRestrictionPayload);
                }
            }
        } else {
            errorMessages.add(PINCODE_ERROR_LOG_1CR);
            log.info(PINCODE_ERROR_LOG_1CR);
        }
    }
    /*FUL2-13711 Income Criteria Change for SmTP and OTP: Changed the income field for one crore and two crore*/
    private void validatePinCodeFor1CrSumAssured(String communicationPinCode, double sumAssured, String proposerOccupation,
                                                 String proposerAnnualIncome, RestrictionData restrictionData, List<String> messages) {
        if (pinCode1CRSumAssuredProductRestrictionMasterDataMap != null) {

            boolean notNullOccupation =!isEmpty(proposerOccupation);
            boolean notSalaried =!proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED);
            boolean salariedAnnualIncomeLessSumAssured=(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                    Integer.parseInt(proposerAnnualIncome) < restrictionData.getSalAssessedIncomeForOneCroreSumAssured());

            boolean salariedSumAssuredGreaterOneCrore=(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                    Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForOneCroreSumAssured() && sumAssured > AppConstants.ONE_CRORE);


            if (notNullOccupation && (notSalaried || salariedAnnualIncomeLessSumAssured || salariedSumAssuredGreaterOneCrore)) {
                List<Date> communicationPinCodeDates = pinCode1CRSumAssuredProductRestrictionMasterDataMap.get(communicationPinCode);
                if (communicationPinCodeDates != null) {
                    Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                    if (lastIndexOfDate.getEndDate() == null) {
                        String message=setMessageFor1CrSumAssured( notSalaried,salariedAnnualIncomeLessSumAssured,salariedSumAssuredGreaterOneCrore,proposerOccupation,restrictionData);
                        messages.add(message +" | "+ COMMUNICATION_PINCODE_MSG + communicationPinCode);
                        log.info(message);
                    }
                }
            }
        } else {
            errorMessages.add(PINCODE_ERROR_LOG_1CR);
            log.info(PINCODE_ERROR_LOG_1CR);
        }
    }
    private String setMessageFor1CrSumAssured( boolean notSalaried, boolean salariedAnnualIncomeLessSumAssured, boolean salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterOneCrore, String occupation, RestrictionData restrictionData) {
        int salAssessedIncome=restrictionData.getSalAssessedIncomeForOneCroreSumAssured();
        if(notSalaried){
            return OCCUPATION_MSG+ occupation;
        }
        else if(salariedAnnualIncomeLessSumAssured){
            return ANNUAL_INCOME_LESS_MSG+ salAssessedIncome;
        }
        else if (salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterOneCrore ){
            return  SUM_ASSURED_GREATER_MSG+AppConstants.ONE_CRORE;
        }
        return "";
    }
    /*FUL2-13711 Income Criteria Change for SmTP and OTP: Changed the income field for one crore and two crore*/
    private void validatePinCodeFor2CrSumAssured(String communicationPinCode, double sumAssured, String proposerOccupation,
                                                 String proposerAnnualIncome, RestrictionData restrictionData, List<String> messages) {
        if (pinCode2CRSumAssuredProductRestrictionMasterDataMap != null) {

            boolean notNullOccupation =!isEmpty(proposerOccupation);
            boolean notSalariedOrNotSelfEmp=!proposerOccupation.matches("(?i)Salaried|Self Employed");
            boolean salariedAnnualIncomeLessSumAssured=(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                    Integer.parseInt(proposerAnnualIncome) < restrictionData.getSalAssessedIncomeForTwoCroreSumAssured());

            boolean selfEmpAnnualIncomeLessSumAssured=(proposerOccupation.equalsIgnoreCase(AppConstants.SELF_EMPLOYED) &&
                    Integer.parseInt(proposerAnnualIncome) < restrictionData.getSelfEmployedAssessedIncome());

            boolean salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore=(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                    Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForTwoCroreSumAssured()
                    && sumAssured > AppConstants.TWO_CRORE);

            boolean selfEmpAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore=(proposerOccupation.equalsIgnoreCase(AppConstants.SELF_EMPLOYED) &&
                    Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSelfEmployedAssessedIncome() && sumAssured > AppConstants.TWO_CRORE);


            if (notNullOccupation && (notSalariedOrNotSelfEmp || salariedAnnualIncomeLessSumAssured ||selfEmpAnnualIncomeLessSumAssured || salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore ||selfEmpAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore )) {
                List<Date> communicationPinCodeDates = pinCode2CRSumAssuredProductRestrictionMasterDataMap.get(communicationPinCode);
                if (communicationPinCodeDates != null) {
                    Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                    if (lastIndexOfDate.getEndDate() == null) {
                        String message=setMessageFor2CrSumAssured(notSalariedOrNotSelfEmp,salariedAnnualIncomeLessSumAssured,selfEmpAnnualIncomeLessSumAssured,salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore,selfEmpAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore,proposerOccupation,restrictionData);
                        messages.add(message+" | "+COMMUNICATION_PINCODE_MSG + communicationPinCode);
                        log.info(message);
                    }
                }
            }
        } else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE))) {
            errorMessages.add(PINCODE_ERROR_LOG_2CR);
            log.info(PINCODE_ERROR_LOG_2CR);
        }
    }
    /**
     * @implNote This method is used to validate Flexi Protect Solution NRI cases.
     * @param productRestrictionPayload
     * @param messages
     * @param productId
     * @return messages
     */
    private List<String> fpsNriValidation(ProductRestrictionPayload productRestrictionPayload, List<String> messages,
                                          String productId) {

        if (FPS_PRODUCT_ID.equals(productId)) {
            String communicationCountry = null;
            String permanentCountry = null;
            communicationCountry = getCommCountry(productRestrictionPayload);
            log.info("communication country {}", communicationCountry);
            permanentCountry = getPermanentCountry(productRestrictionPayload);
            log.info("permanent country {}", permanentCountry);
            String nationality = productRestrictionPayload.getNationality();
            try {
                if (FORM_TYPE_SELF.equalsIgnoreCase(productRestrictionPayload.getFormType())
                        && (!AppConstants.INDIAN_NATIONALITY.equalsIgnoreCase(nationality)
                        || !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(permanentCountry)
                        || !AppConstants.INDIA_COUNTRY.equalsIgnoreCase(communicationCountry))) {
                    messages.add(FPS_NRI_MSG);
                    log.info(FPS_NRI_MSG);
                }
            } catch (Exception e) {
                log.error("Something went wrong!! Exception occurred while fpsNriValidation ", e);
                errorMessages.add(ERR_MSG);
            }
        }
        return messages;
    }
    private void checkNegativePincode(long transactionId, String productId, String dedupeFlag,
                                      String communicationPinCode, boolean isSSESProduct, String proposerEducation, boolean isSspSwissReCase) {
        if (FPS_PRODUCT_ID.equals(productId)) {
            log.info("Checking FPS negative pincode validation for transactionId {}", transactionId);
            setFPSCommunicationPinCode(communicationPinCode, proposerEducation);
        } else {
            log.info("Checking SSP & dedupeFlag for negative pincode validation for transactionId {}", transactionId);
            if (!isSspDedupeEx(productId, dedupeFlag)) {
                setCommunicationPinCode(communicationPinCode, isSSESProduct, isSspSwissReCase);
            }
        }
    }
    //FUL2-49759 SSP - Pincode Logic Change - Agent level Based
    private void checkNegativePincodeForAgency(ProductRestrictionPayload productRestrictionPayload,
                                               RestrictionData restrictionData, List<String> messages, boolean isSSESProduct, String proposerEducation, boolean isSspSwissReCase) throws UserHandledException {
        String communicationPinCode = null;
        long transactionId = productRestrictionPayload.getTransactionId();
        String dedupeFlag = productRestrictionPayload.getDedupeFlag();
        String productId = productRestrictionPayload.getProductId();
        communicationPinCode = productRestrictionPayload.getCommunicationPinCode();
        double sumAssured = productRestrictionPayload.getSumAssured();
        if(isNull(sumAssured)){
            sumAssured = 0.0;
        }
        String proposerOccupation = getproposerOccupation(productRestrictionPayload);
        String proposerAnnualIncome = productRestrictionPayload.getIncome();
        log.info ("Getting value for transactionId {} dedupe flag {} communication pincode {} sum assured {} " +
                        "proposer occupation {} and proposer annual income {}",transactionId, dedupeFlag, communicationPinCode,
                Utility.getMaskedValue(sumAssured, MaskType.AMOUNT), proposerOccupation,
                Utility.getMaskedValue(proposerAnnualIncome, MaskType.AMOUNT));
        ProposalDetails proposalDetails=proposalRepository.findBySourcingDetailsAgentIdAndTransactionId(productRestrictionPayload.getAgentId(),
                transactionId);
        if(nonNull(proposalDetails)) {
            if(FPS_PRODUCT_ID.equals(productId)) {
                log.info("Checking for negative pincode validation for transactionId {}", transactionId);
                setFPSCommunicationPinCode(communicationPinCode, proposerEducation);
            } else {
                log.info("Checking SSP and Agent Level for negative pincode validation for transactionId {}", transactionId);
                if (checkEligibleAgentLevel(productId, proposalDetails, isSspSwissReCase) && !AppConstants.DEDUPE_EX.equalsIgnoreCase(dedupeFlag)) {
                    setCommunicationPinCode(communicationPinCode, isSSESProduct, isSspSwissReCase);
                    if(!isSSESProduct && SSP_PRODUCT_ID.equalsIgnoreCase(productRestrictionPayload.getProductId()) && !isSspSwissReCase){
                        validationSSP1CrOr2CrSumAssured(productRestrictionPayload,restrictionData,messages);
                    }else if(!isSSESProduct && !isSspSwissReCase) {
                        validatePinCodeFor1CrSumAssured(communicationPinCode, sumAssured, proposerOccupation, proposerAnnualIncome, restrictionData, messages);
                        validatePinCodeFor2CrSumAssured(communicationPinCode, sumAssured, proposerOccupation, proposerAnnualIncome, restrictionData, messages);
                    }
                }
            }
        }
    }
    /* FUL2-9472 WLS Product Restriction */
    private void validateLimitationData(String city, String state, String education, LimitationData wlsLimitationData, ResponsePayload responsePayload) {
        Map<String,String> fullyRestrictedLocationsMap = wlsLimitationData.getFullyRestrictedLocations(),
                partialRestrictedLocationsMap = wlsLimitationData.getPartialRestrictedLocations();
        if(fullyRestrictedLocationsMap != null && partialRestrictedLocationsMap != null) {
            ArrayList<String> fullyRestrictedCities = new ArrayList<>(fullyRestrictedLocationsMap.keySet()),
                    partialRestrictedCities = new ArrayList<>(partialRestrictedLocationsMap.keySet());
            if((fullyRestrictedCities.stream().anyMatch(city.trim()::equalsIgnoreCase) && state.equalsIgnoreCase(fullyRestrictedLocationsMap.get(city.trim().toUpperCase()))) ||
                    (partialRestrictedCities.stream().anyMatch(city.trim()::equalsIgnoreCase) &&
                            state.equalsIgnoreCase(partialRestrictedLocationsMap.get(city.trim().toUpperCase()))  && !isEmpty(education) && underGraduateEducations.stream().anyMatch(education::equalsIgnoreCase)) || isEmpty(education)) {
                messages.add(COMMUNICATION_CITY_MSG + city);
                log.info(COMMUNICATION_CITY_LOG, city);
                responsePayload.setMessages(messages);
            } else if(partialRestrictedCities.stream().anyMatch(city.trim()::equalsIgnoreCase) &&
                    state.equalsIgnoreCase(partialRestrictedLocationsMap.get(city.trim().toUpperCase())) && !isEmpty(education) && !underGraduateEducations.stream().anyMatch(education::equalsIgnoreCase)){
                responsePayload.setShouldEnableDoc("Yes");
            }
        }
    }
    //FUL2-83504 CIP - Medical Stop Rules
    private void validateCIPMedicalQuestions(ProductRestrictionPayload productRestrictionPayload, ProposalDetails proposalDetails, List<String> messages) {
        if(nonNull(proposalDetails.getAdditionalFlags()) && AppConstants.N
                .equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowHealthQuesOnPosv())
                && Objects.nonNull(proposalDetails.getPosvDetails())){
            Map<String, String> questionMap = new HashMap<>();
            createPosvQuesAnsMap(questionMap,proposalDetails);
            if(isAnyMedQuesMarkedAsYes(questionMap)) {
                messages.add(CIP_RESTRICTION_MSG);
            }
        }
    }
    private boolean validateFlexiWealthProtectProposerIncome(String proposerAnnualIncome, RestrictionData restrictionData, String proposerEducation) {

        if(proposerAnnualIncome != null){
            return Integer.parseInt(proposerAnnualIncome) < restrictionData.getMinimumIncome();
        }
        return false;
    }
    private void validateSSPNRIRuleForOccupation(String occupation, RestrictionData restrictionData) {
        addMessageConditionally(!isEmpty(occupation) && !restrictionData.getNriOccupations().contains(occupation.trim().toUpperCase()));
    }
    private void proposerOccupationMessage(List<String> messages, String proposerOccupation, String formType, boolean isMsg, String schemeType, boolean isSspSwissReCase) {
        if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isMsg && (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))) {
            messages.add(INSURED_OCCUPATION_MSG + proposerOccupation);
            log.info(INSURED_OCCUPATION_MSG, proposerOccupation);
        } else if (Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(DISABLESTOPRULE)) && isMsg) {
            messages.add(PROPOSER_OCCUPATION_MSG + proposerOccupation);
            log.info(PROPOSER_OCCUPATION_LOG, proposerOccupation);
        }
    }
    private void validatePinCodeFor2CrSumAssuredSSP(ProductRestrictionPayload productRestrictionPayload, String proposerOccupation, RestrictionData restrictionData, List<String> messages, String education) {
        if (pinCode2CRSumAssuredProductRestrictionMasterDataMap != null) {

            List<Integer> rules2Cr = Arrays.asList(1, 2, 3,4,5,6,7);
            int rules2CrSumAssured= conditionChecking2Cr(productRestrictionPayload.getSumAssured(),proposerOccupation,productRestrictionPayload.getIncome(),restrictionData,education);
            log.info("For TransactionId {} SPP 2Cr SumAssured rule {}",productRestrictionPayload.getTransactionId(),rules2CrSumAssured);
            if(!isEmpty(proposerOccupation)&& rules2Cr.contains(rules2CrSumAssured)){
                List<Date> communicationPinCodeDates = pinCode2CRSumAssuredProductRestrictionMasterDataMap.get(productRestrictionPayload.getCommunicationPinCode());
                if (communicationPinCodeDates != null) {
                    Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                    pinCodeRestriction2Cr(lastIndexOfDate,rules2CrSumAssured,proposerOccupation,restrictionData,productRestrictionPayload,messages);
                }
            }

        } else {
            errorMessages.add(PINCODE_ERROR_LOG_2CR);
            log.info(PINCODE_ERROR_LOG_2CR);
        }
    }
    private int checkingCondition1CrSumAssured(double sumAssured, String proposerOccupation, String proposerAnnualIncome, RestrictionData restrictionData, String education) {
        if(!proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED))
        {
            return 1;
        } else if(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) < restrictionData.getSalAssessedIncomeForOneCroreSumAssured()){
            return 2;
        } else if(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForOneCroreSumAssured() && sumAssured > AppConstants.ONE_CRORE ){
            return 3;
        }else if(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForOneCroreSumAssured() && sumAssured <= AppConstants.ONE_CRORE && underGraduateEducations.contains(education)){
            return 4;
        }
        return 0;
    }
    private void pinCodeCodeRestriction1Cr(Date lastIndexOfDate, int rules1CrSumAssured, String proposerOccupation, RestrictionData restrictionData, List<String> messages, ProductRestrictionPayload productRestrictionPayload) {
        if (lastIndexOfDate.getEndDate() == null) {
            if(rules1CrSumAssured==4){
                setEducationMessage(productRestrictionPayload,messages);
            }else{
                String message=setMessageFor1CrSumAssuredSSP(rules1CrSumAssured,proposerOccupation,restrictionData);
                messages.add(message +" | "+ COMMUNICATION_PINCODE_MSG + productRestrictionPayload.getCommunicationPinCode());
                log.info("for transactionId {} SSP Error msg for 1cr sumAssured {} ",productRestrictionPayload.getTransactionId(),messages);
            }
        }
    }
    private String setMessageFor2CrSumAssured(boolean notSalariedOrNotSelfEmp, boolean salariedAnnualIncomeLessSumAssured, boolean selfEmpAnnualIncomeLessSumAssured, boolean salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore, boolean selfEmpAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore, String occupation , RestrictionData restrictionData){
        int salAssessedIncome=restrictionData.getSalAssessedIncomeForTwoCroreSumAssured();
        if(notSalariedOrNotSelfEmp){
            return OCCUPATION_MSG+occupation;
        }
        else if(salariedAnnualIncomeLessSumAssured){
            return ANNUAL_INCOME_LESS_MSG+salAssessedIncome;
        }
        else if (selfEmpAnnualIncomeLessSumAssured ){
            return ANNUAL_INCOME_LESS_MSG+salAssessedIncome;
        }else if (salariedAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore){
            return SUM_ASSURED_GREATER_MSG+AppConstants.TWO_CRORE;
        }else if (selfEmpAnnualIncomeGreaterAssessedSumAssuredAndSumAssuredGreaterTwoCrore){
            return SUM_ASSURED_GREATER_MSG+AppConstants.TWO_CRORE;
        }
        return "";
    }
    /**
     * @implNote This method is used to validate Flexi Protect Solution pinCodes.
     * @param communicationPinCode
     */
    private void setFPSCommunicationPinCode(String communicationPinCode, String proposerEducation) {

        if (flexiProtectPinCodeProductRestrictionMasterDataMap != null) {
            List<Date> communicationPinCodeDates = flexiProtectPinCodeProductRestrictionMasterDataMap
                    .get(communicationPinCode);
            if (communicationPinCodeDates != null && !communicationPinCode.isEmpty()
                    && RESTRICTED_PROPOSER_EDUCATIONS.contains(proposerEducation)) {
                Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(COMMUNICATION_PINCODE_MSG + communicationPinCode);
                    log.info(COMMUNICATION_PINCODE_LOG, communicationPinCode);
                }
            }
        } else {
            errorMessages.add(FPS_ERROR_MSG);
            log.info(FPS_ERROR_MSG);
        }
    }
    private void setCommunicationPinCode(String communicationPinCode, boolean isSSESProduct, boolean isSspSwissReCase) {

        boolean isInstaProtectPinCodeValidated = checkCommunicationPinCodeForInstaProtect(communicationPinCode, isSSESProduct, isSspSwissReCase);
        if (pinCodeProductRestrictionMasterDataMap != null  &&  !isSspSwissReCase) {
            List<Date> communicationPinCodeDates = pinCodeProductRestrictionMasterDataMap.get(communicationPinCode);
            if (communicationPinCodeDates != null && !isInstaProtectPinCodeValidated) {
               Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(COMMUNICATION_PINCODE_MSG + communicationPinCode);
                    log.info(COMMUNICATION_PINCODE_LOG, communicationPinCode);
                }
            }
        } else {
            if(!isSspSwissReCase){
                errorMessages.add("Pin Code restriction data not available in DB");
                log.info("Pin Code restriction data not available in DB");
            }
        }
    }
    //FUL2-49759 SSP - Pincode Logic Change - Agent level Based
    private boolean checkEligibleAgentLevel(String productId, ProposalDetails proposalDetails, boolean isSspSwissReCase) throws UserHandledException {
        if(nonNull(proposalDetails.getSourcingDetails())) {

            String agentSegmentDesc = proposalDetails.getSourcingDetails().getAgentSegmentDesc();
            log.info("Checking Agent level eligibility for transactionId {} agentSegmentDesc {}",
                    proposalDetails.getTransactionId(), agentSegmentDesc);
            try {
                if (!StringUtils.isEmpty(agentSegmentDesc)) {
                    return (SSP_PRODUCT_ID.equals(productId) && AgentLevel.getValue(agentSegmentDesc) < FIVE && !isSspSwissReCase);
                }
            } catch (Exception e) {
                log.error("Data addition failed for agentSegmentDesc for transactionId {} {} ",
                        proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
                List<String> errorMessages = new ArrayList<>();
                errorMessages.add("Agent not found");
                throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return false;
    }
    //FUL2-83504 CIP - Medical Stop Rules (Check Question)
    private boolean isAnyMedQuesMarkedAsYes(Map<String, String> questionMap) {
        return AppConstants.MEDICAL_QUES_CIP.stream()
                .anyMatch(medicalQuestion -> AppConstants.Y.equalsIgnoreCase(questionMap.get(medicalQuestion)));
    }
    private int  conditionChecking2Cr( double sumAssured, String proposerOccupation, String proposerAnnualIncome, RestrictionData restrictionData, String education) {
        if (!proposerOccupation.matches("(?i)Salaried|Self Employed")){
            return 1;
        }else if (proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) < restrictionData.getSalAssessedIncomeForTwoCroreSumAssured()){
            return 2;
        }else if (proposerOccupation.equalsIgnoreCase(AppConstants.SELF_EMPLOYED) &&
                Integer.parseInt(proposerAnnualIncome) < restrictionData.getSelfEmployedAssessedIncome()){
            return 3;
        }else if(proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForTwoCroreSumAssured()
                && sumAssured > AppConstants.TWO_CRORE){
            return 4;
        }else if (proposerOccupation.equalsIgnoreCase(AppConstants.SALARIED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSalAssessedIncomeForTwoCroreSumAssured()
                && sumAssured <= AppConstants.TWO_CRORE && underGraduateEducations.contains(education)){
            return 5;
        }else if(proposerOccupation.equalsIgnoreCase(AppConstants.SELF_EMPLOYED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSelfEmployedAssessedIncome() && sumAssured > AppConstants.TWO_CRORE){
            return 6;
        }else if (proposerOccupation.equalsIgnoreCase(AppConstants.SELF_EMPLOYED) &&
                Integer.parseInt(proposerAnnualIncome) >= restrictionData.getSelfEmployedAssessedIncome() && sumAssured <= AppConstants.TWO_CRORE && underGraduateEducations.contains(education)){
            return 7;
        }
        return 0;
    }
    private void pinCodeRestriction2Cr(Date lastIndexOfDate, int condition, String proposerOccupation, RestrictionData restrictionData, ProductRestrictionPayload productRestrictionPayload, List<String> messages) {
        if (lastIndexOfDate.getEndDate() == null){
            if(condition==5 || condition==7)
            {
                setEducationMessage(productRestrictionPayload,messages);
            }else {
                String message = setMessageFor2CrSumAssured2SSP(condition, proposerOccupation, restrictionData);
                messages.add(message + " | " + COMMUNICATION_PINCODE_MSG + productRestrictionPayload.getCommunicationPinCode());
                log.info("for transactionId {} SSP Error msg for 2cr sumAssured {} ",productRestrictionPayload.getTransactionId(),messages);
            }
        }
    }
    private void setEducationMessage(ProductRestrictionPayload productRestrictionPayload,List<String> messages) {

        if(DEPENDENT.equalsIgnoreCase(productRestrictionPayload.getFormType())){
            messages.add(INSURED_EDUCATION_MSG + productRestrictionPayload.getInsurerEducation());
            log.info(INSURED_EDUCATION_LOG, productRestrictionPayload.getInsurerEducation());
        }else if(SELF.equalsIgnoreCase(productRestrictionPayload.getFormType())
                || Utility.schemeBCase(productRestrictionPayload.getFormType(), productRestrictionPayload.getSchemeType())){
            messages.add(PROPOSER_EDUCATION_MSG + productRestrictionPayload.getEducation());
            log.info(PROPOSER_EDUCATION_LOG, productRestrictionPayload.getEducation());
        }
        log.info("for transactionId {} SSP Error msg for 1cr sumAssured Education {} ",productRestrictionPayload.getTransactionId(),messages);
    }
    private String setMessageFor1CrSumAssuredSSP(int rule, String proposerOccupation, RestrictionData restrictionData) {
        int salAssessedIncome=restrictionData.getSalAssessedIncomeForOneCroreSumAssured();
        String s = "";
        switch (rule){
            case 1 :
                s= s+OCCUPATION_MSG+proposerOccupation;
                break;
            case 2 :
                s=s+ ANNUAL_INCOME_LESS_MSG+salAssessedIncome;
                break;
            case 3 :
                s=s+ SUM_ASSURED_GREATER_MSG+AppConstants.ONE_CRORE;
                break;
            default:{
                s=s+"";
                return s;
            }
        }
        return s;
    }
    /**
     * FUL2-53692
     *
     * @implNote This method is used to check insta protect pincodes for totally
     *           blocked and partially allowed.
     * @param communicationPinCode
     * @param isSSESProduct
     * @return
     */
    private boolean checkCommunicationPinCodeForInstaProtect(String communicationPinCode, boolean isSSESProduct, boolean isSspSwissReCase) {
        boolean isInstaProtectPinCodeValidated = false;
        if (instaProtectPincodeProductRestrictionMasterDataMap != null && isSSESProduct && !isSspSwissReCase ) {
            log.info("validating communicationPinCode {} for InstaProtect", communicationPinCode);
            List<Date> communicationPinCodeDates = instaProtectPincodeProductRestrictionMasterDataMap
                    .get(communicationPinCode);
            if (communicationPinCodeDates != null) {
               Date lastIndexOfDate = communicationPinCodeDates.get(communicationPinCodeDates.size() - 1);
                if (lastIndexOfDate.getEndDate() == null) {
                    messages.add(COMMUNICATION_PINCODE_MSG + communicationPinCode);
                    log.info(COMMUNICATION_PINCODE_LOG, communicationPinCode);
                    isInstaProtectPinCodeValidated = true;
                }
            }
        }
        return isInstaProtectPinCodeValidated;
    }
    private String setMessageFor2CrSumAssured2SSP(int rule, String proposerOccupation, RestrictionData restrictionData) {
        int salAssessedIncome=restrictionData.getSalAssessedIncomeForTwoCroreSumAssured();
        int SelfEmployedAssessedIncome=restrictionData.getSelfEmployedAssessedIncome();
        String s = "";

        switch (rule){
            case 1 :
                s= s+OCCUPATION_MSG+proposerOccupation;
                break;
            case 2 :
                s=s+ ANNUAL_INCOME_LESS_MSG+salAssessedIncome;
                break;
            case 3 :
                s=s+ ANNUAL_INCOME_LESS_MSG+SelfEmployedAssessedIncome;
                break;
            case 4 :
                s=s+ SUM_ASSURED_GREATER_MSG+AppConstants.TWO_CRORE;
                break;
            case 6 :
                s=s+ SUM_ASSURED_GREATER_MSG+AppConstants.TWO_CRORE;
                break;
            default:{
                s+="";
                return s;
            }
        }
        return s;
    }
    public String getEncryptionSource(MultiValueMap<String, String> headerMap) {
        String clientId = Utility.getClientIdFromToken(headerMap);
        logger.info("ClientId from token: {} and clientIdPartnerMap:{}",clientId,clientIdPartnerMap);
        String encryptionSource = clientIdPartnerMap.get(clientId);
        logger.info("Encryption source for clientId {} is {}",clientId,encryptionSource);
        return encryptionSource;
    }

    @Override
    public EncryptionRequest encryption(EncryptionResponse inputPayload) throws NoSuchPaddingException, IllegalBlockSizeException, CertificateException,
            NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, UserHandledException {

        String randomIV = Utility.randomString(16);
        String encryptedResponse = null;
        final byte[] FIXED_IV = randomIV.getBytes();
        String key = Utility.randomString(32);
        try {
            String encryptRequest = objectMapper.writeValueAsString(inputPayload.getPayload());
            encryptedResponse = EncryptionDecryptionUtil.encrypt(encryptRequest, key, FIXED_IV);
            logger.info("encryption response  {} " , encryptedResponse);
        } catch (UserHandledException e) {
            logger.error("Error occured while doing encryption {}", e.getMessage());
            throw new UserHandledException(Collections.singletonList(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String keyAndIV = key + ":" + randomIV;
        String encryptedKEK = rsaEncryptionDecryptionUtil.rsaEncrypt(CBC_ALGO_PADDING1, keyAndIV);
        logger.info("encryption KEK using RSA  {} " , encryptedKEK);
        return new EncryptionRequest(encryptedResponse,encryptedKEK);
    }


    public String decryption(String encryptedPayloadBase64, String encryptedKeyAndIVBase64, ErrorResponseParams errorResponseParams) throws Exception  {
        String keyAndIVEncodedString = rsaEncryptionDecryptionUtil.rsaDecrypt(CBC_ALGO_PADDING1,encryptedKeyAndIVBase64);
        String[] keyAndIVParts = keyAndIVEncodedString.split(":");
        SecretKeySpec aesKey = new SecretKeySpec(keyAndIVParts[0].getBytes(), ALGORITHM);
        byte[] iv = keyAndIVParts[1].getBytes();
        if(Objects.nonNull(errorResponseParams)){
            errorResponseParams.setIVandKey(keyAndIVParts);
        }
        return AESCBCAlgoCrypto.decrypt(CBC_ALGO,encryptedPayloadBase64,aesKey, new IvParameterSpec(iv));

    }

}

