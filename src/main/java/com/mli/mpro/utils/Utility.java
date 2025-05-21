package com.mli.mpro.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.agentSelf.DataLake.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mli.mpro.agent.models.*;
import com.mli.mpro.agent.models.RequestPayload;
import com.mli.mpro.agentSelf.DataLake.InputRequest;
import com.mli.mpro.agentSelf.DataLake.ResponsePayload;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.OauthTokenResponse;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.common.models.UIOutputResponse;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.models.UIConfiguration;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.InfluencerChannelList;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.document.service.impl.BaseMapper;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.document.utils.ProposalFormChannelVersions;
import com.mli.mpro.location.models.FundsData;
import com.mli.mpro.location.models.RecommendedFund;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.location.newApplication.model.Result;
import com.mli.mpro.location.newApplication.model.SoaResponse;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.onboarding.brmsBroker.model.DiyBrmsFieldConfigurationDetails;
import com.mli.mpro.productRestriction.models.*;
import com.mli.mpro.productRestriction.models.ErrorResponse;
import com.mli.mpro.productRestriction.models.OutputResponse;
import com.mli.mpro.productRestriction.service.loader.JsonSchemaFactory;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.productRestriction.util.GsonTools;
import com.mli.mpro.proposal.models.*;
import com.networknt.schema.ValidationMessage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author akshom4375 Class with Utility Methods for Document generation
 */
public class Utility {

    public static final String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
            "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    public static final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    private static final Set<String> cc60RiderCodes;
    private static final Set<String> otpProductCodes;
    /*FUL2-11549 Payment acknowledgement for all channels*/
    private static final HashMap<String,String> premiumModes = new HashMap<>();
    public static final String INFLUENCER_CHANNEL_YBL = "45";
    public static final String ERROR_OCCURRED_WHILE_PARSING_THE_DATE= "Error occurred while parsing the date {}";
    private Map<String, List<String>> customErrorList;
    public static boolean isDIYJourney(ProposalDetails proposalDetails) {
        return AppConstants.SOURCE_CHANNEL_LIST.contains(proposalDetails.getAdditionalFlags().getSourceChannel());
    }

    public static String diyStaticID(String sourceChannel,int path) {
        return AppConstants.DIY_AGENT_MAP.get(sourceChannel).get(path);
    }

    public static void updateMobileAndEmailDetails(PartyDetails partyDetails, List<NomineeAppointeePfDetails> pfDetails, int i) {
        if (!CollectionUtils.isEmpty(partyDetails.getNomineePhoneDetails())
                && Objects.nonNull(partyDetails.getNomineePhoneDetails().get(0))) {
            pfDetails.get(i).getNomineePfDetails().getCommunicationAddress().
                    setMobile1(nullSafe(partyDetails.getNomineePhoneDetails().get(0).getPhoneNumber()));
        }
        pfDetails.get(i).getNomineePfDetails().getCommunicationAddress().setEmailId(nullSafe(partyDetails.getNomineeEmail()));
        if (Objects.nonNull(partyDetails.getAppointeeDetails())) {
            if (!CollectionUtils.isEmpty(partyDetails.getAppointeeDetails().getAppointeePhoneDetails())
                    && Objects.nonNull(partyDetails.getAppointeeDetails().getAppointeePhoneDetails().get(0))) {
                pfDetails.get(i).getAppointeePfDetails().getCommunicationAddress().
                        setMobile1(nullSafe(partyDetails.getAppointeeDetails().getAppointeePhoneDetails().get(0).getPhoneNumber()));
            }
            pfDetails.get(i).getAppointeePfDetails().getCommunicationAddress()
                    .setEmailId(nullSafe(partyDetails.getAppointeeDetails().getAppointeeEmail()));
        }
    }

    /** Parts of a name: First name, Middle name, Last name */
    public enum NAME_PARTS {
    	FIRST, MIDDLE, LAST
    }

    public static final List<String> SGPPJL_PRODUCTS=Collections.unmodifiableList(Arrays.asList("TIAGJ","TIAGJR"));
    public static final List<String> SGPP_PRODUCTS=Collections.unmodifiableList(Arrays.asList("TIAGS","TIAGSR"));
    private static final Set<String> addRiderCodes;

    static {
        premiumModes.put("online","Online");
        premiumModes.put("payLater","Pay Later");
        premiumModes.put("cheque","Cheque");
        premiumModes.put("demandDraft"," Demand Draft");
        premiumModes.put("directDebit","Direct Debit");
        premiumModes.put(AppConstants.DIRECTDEBITWITHRENEWALS,"Direct Debit");
        premiumModes.put("NPS","NPS");
    }

    static {
        cc60RiderCodes = new HashSet<>();
        cc60RiderCodes.add(AppConstants.TCIGR);
        cc60RiderCodes.add(AppConstants.TCIGPR);
        cc60RiderCodes.add(AppConstants.TCIPR);
        cc60RiderCodes.add(AppConstants.TCIPPR);
        cc60RiderCodes.add(AppConstants.TCIPDR);
        cc60RiderCodes.add(AppConstants.TCIGL);
        cc60RiderCodes.add(AppConstants.TCIGPL);
        cc60RiderCodes.add(AppConstants.TCIPL);
        cc60RiderCodes.add(AppConstants.TCIPPL);
        cc60RiderCodes.add(AppConstants.TCIPDL);

        addRiderCodes = new HashSet<>();
        addRiderCodes.add(PP02);
        addRiderCodes.add(PP03);
    }

    static {

        otpProductCodes = new HashSet<>();
        otpProductCodes.add("TCOTP2");
        otpProductCodes.add("TNOTP2");
        otpProductCodes.add("TCOT60");
        otpProductCodes.add("TNOT60");
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return -1;
        }
    }

    public static String getTitle(String gender) {
        if (StringUtils.equalsIgnoreCase(gender, "M")) {
            return "MR";
        } else if (StringUtils.equalsIgnoreCase(gender, "F")) {
            return "MS";
        } else {
            return "MX";
        }
    }

    public static String getGender(String gender) {
        String formattedgender = "Male";
        if ("F".equals(gender)) {
            formattedgender = "Female";

        } else if ("O".equalsIgnoreCase(gender) || "Others".equalsIgnoreCase(gender) || "Mx".equalsIgnoreCase(gender)) {
            formattedgender = "Other";
        }
        return formattedgender;
    }
    public static String getGenderValue(String gender) {
        String formattedgender = AppConstants.BLANK;
        if (AppConstants.FEMALE.equals(gender)) {
            formattedgender = "Female";

        } else if (AppConstants.MALE.equalsIgnoreCase(gender)) {
            formattedgender = "Male";
        } else if (AppConstants.ZERO.equalsIgnoreCase(gender) || AppConstants.OTHERS.equalsIgnoreCase(gender) || "Mx".equalsIgnoreCase(gender)) {
            formattedgender = "Other";
        }
        return formattedgender;
    }

    /**
	 * @param fullName : Full name from which First, Middle or Last name is to be extracted
	 * @param part     : Part of name {@link NAME_PARTS}
	 * @return
	 */
	public static String getNamePart(String fullName, NAME_PARTS part) {
		if (Objects.isNull(fullName)) return fullName;
		String[] parts = fullName.split(" ");
		String output = "";
		switch (part) {
		case FIRST:
			output = parts[0];
			break;
		case MIDDLE:
			if (parts.length > 2) {
				StringBuilder builder = new StringBuilder();
				for (int i = 1; i < parts.length - 1; i++) {
					builder.append(parts[i]);
				}
				output = builder.toString();
			}
			break;
		case LAST:
			if (parts.length > 1) output = parts[parts.length - 1];
			break;
		}
		return output;
	}

    public static String[] splitName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            StringTokenizer stok = new StringTokenizer(fullName);
            String firstName = stok.nextToken();
            StringBuilder middleName = new StringBuilder();
            String lastName = stok.nextToken();
            while (stok.hasMoreTokens()) {
                middleName.append(lastName + " ");
                lastName = stok.nextToken();
            }
            String[] splitName = {firstName, middleName.toString().trim(), lastName};
            return splitName;
        }
        return null;
    }

    public static String dateFormatter(String date) {
        String formattedDate = "";
        if (!StringUtils.isEmpty(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
            formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
            try {
                Date formatdate = formatter.parse(date);
                formattedDate = formatter.format(formatdate);
            } catch (ParseException e) {
                logger.error(CONVERTING_DATE_FORMAT_FAILED,e);
            }
        }
        return formattedDate;

    }

    public static String dateFormatter(Date date) {
        String formattedDate = "";
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
                formattedDate = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            logger.error(CONVERTING_DATE_FORMAT_FAILED,e);
        }
        return formattedDate;

    }

    public static String dateFormatter(LocalDate date) {
    	String formatterDate = "";
    	try {
    		if (date != null) {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
    			formatterDate = formatter.format(date);
    		}
    	} catch(Exception e) {
            logger.error(CONVERTING_DATE_FORMAT_FAILED,e);
        }
    	return formatterDate;
    }

    public static String stringAnnuityDateFormatter(String date) {
        String formattedDate = "";
        try {
            if (StringUtils.isNotBlank(date)) {
                SimpleDateFormat formatter, FORMATTER;
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                Date dateValue  = formatter.parse(date.substring(0, 24));
                FORMATTER = new SimpleDateFormat(AppConstants.DATE_FORMAT);
                formattedDate = FORMATTER.format(dateValue);
            }
        } catch (Exception ex) {
            logger.error("Error formatting input Annuity date:",ex);
        }
        return formattedDate;
    }

    public static String stringAnnuityFormDateFormatter(String date, String channel) {
        String formattedDate = "";
        SimpleDateFormat format;
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        try {
            if (channel.equalsIgnoreCase(CHANNEL_AXIS)) {
                // In Axis channel, we get date in IST format with correct date
                logger.info("Axis Channel Date conversion");
                format = new SimpleDateFormat("yyyy-MM-dd");
                Date secondAnnuitantDOB = format.parse(date.substring(0, 10));
                logger.info("Second Annuitant DOB = {}", secondAnnuitantDOB);
                formattedDate = formatter.format(secondAnnuitantDOB);
            } else {
                // In Non Axis channel, we get date in UTC format, so we need to add +1 to date
                logger.info("Non Axis Channel Date conversion");
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                Date dateValue = format.parse(date.substring(0, 24));
                dateValue = new Date(dateValue.getTime() + TimeUnit.DAYS.toMillis(1));
                logger.info("Second Annuitant DOB = {}", dateValue);
                formattedDate = formatter.format(dateValue);
            }
        } catch (Exception ex) {
            logger.error("Error formatting input Annuity date:",ex);
        }
        return formattedDate;
    }



    public static String getLastDigitSufix(int number) {
        switch ((number < 20) ? number : number % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static String getPFGeneratedDate() {
        String date = "";
        String month = "";
        String year = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("d");
        DateFormat dateFormat = new SimpleDateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        date = formatter.format(currentDate);
        String ordinalValue = ordinal(Integer.valueOf(date));
        formatter = new SimpleDateFormat("MMM");
        month = formatter.format(currentDate);
        formatter = new SimpleDateFormat("yyyy");
        year = formatter.format(currentDate);

        String pfDate = ordinalValue.concat(" ").concat(month).concat(" ").concat(year);
        return pfDate;
    }

    public static String getPfGeneratedTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        String preferredTime = dateFormat.format(new Date());
        return preferredTime;
    }

    /**
     * Masking first 8 digits of the Aadhar number
     *
     * @param aadhaarNumber
     * @return
     */
    public static String maskAadhaarNumber(String aadhaarNumber) {
        StringBuffer maskedAadhar = new StringBuffer("XXXXXXXX");
        try {
            if (StringUtils.isNotEmpty(aadhaarNumber) && StringUtils.length(aadhaarNumber) >= 8) {
                maskedAadhar.append(StringUtils.substring(aadhaarNumber, 8, aadhaarNumber.length()));
            } else {
                maskedAadhar = new StringBuffer(aadhaarNumber);
            }
        } catch (Exception e) {
            maskedAadhar = new StringBuffer("");
            logger.error(" Masking first 8 digits of the Aadhar number failed:",e);
        }
        return maskedAadhar.toString();
    }

    public static boolean verifyString(String str) {
        boolean result;
        result = (str != null && str.length() > 1);
        return result;

    }

    public static boolean stringEqualCheck(String str1, String str2) {
        boolean result;
        result = (!isEmpty(str1) && str1.equalsIgnoreCase(str2));
        return result;
    }

    public static RestrictionData mergeJsonObjects(RestrictionData currentRestrictionData, RestrictionData existingRestrictionData) {
        Gson jsonMerger = new Gson();
        GsonTools gsonTools = new GsonTools();
        String currentReceivedJsonString = jsonMerger.toJson(currentRestrictionData);
        String existingJsonStringFromRepository = jsonMerger.toJson(existingRestrictionData);
        JsonParser jsonParser = new JsonParser();
        JsonObject currentReceivedJsonObject = jsonParser.parse(currentReceivedJsonString).getAsJsonObject();
        JsonObject existingJsonObject = jsonParser.parse(existingJsonStringFromRepository).getAsJsonObject();

        try {
            existingJsonObject = gsonTools.extendJsonObject(existingJsonObject, GsonTools.ConflictStrategy.PREFER_NON_NULL, currentReceivedJsonObject);
        } catch (GsonTools.JsonObjectExtensionConflictException e) {
            logger.error("Merge Json object failed:",e);
        }

        Object mergedJsonObject = existingJsonObject;

        String src1 = jsonMerger.toJson(mergedJsonObject);

        RestrictionData mergedRestrictionDetails = jsonMerger.fromJson(src1, RestrictionData.class);

        return mergedRestrictionDetails;
    }

    public static String convertNumberToWords(final int n) {
        if (n < 0) {
            return "Minus " + convertNumberToWords(-n);
        }
        if (n < 20) {
            return units[n];
        }
        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }
        if (n < 1000) {
            return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convertNumberToWords(n % 100);
        }
        if (n < 100000) {
            return convertNumberToWords(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convertNumberToWords(n % 1000);
        }
        if (n < 10000000) {
            return convertNumberToWords(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convertNumberToWords(n % 100000);
        }
        return convertNumberToWords(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convertNumberToWords(n % 10000000);
    }

    public static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    public static String getAge(Date dob) {
        try {
            if (null != dob && StringUtils.isNotBlank(dob.toString())) {
                LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = LocalDate.now();
                Period period = Period.between(date, today);
                return String.valueOf(period.getYears());
            }
        } catch (Exception ex) {
            logger.error("Unable to calculate age : ",ex);
        }
        return "0";
    }

    // Return result of AND of two expressions
    public static boolean andTwoExpressions(boolean bool1, boolean bool2) {
        return bool1 && bool2;
    }

    // Return result of AND of three  expressions
    public static boolean andThreeExpressions(boolean bool1, boolean bool2, boolean bool3) {
        return bool1 && bool2 && bool3;
    }
    public static boolean andFiveExpressions(boolean bool1, boolean bool2, boolean bool3,boolean bool4, boolean bool5) {
        return bool1 && bool2 && bool3 && bool4 && bool5 ;
    }

    // Return result of OR of two expressions
    public static boolean orTwoExpressions(boolean bool1, boolean bool2) {
        return bool1 || bool2;
    }

    // Return String type conditional result of evaluating boolean expression
    public static String evaluateConditionalOperation(boolean expression, String result1, String result2) {
        return expression ? result1 : result2;
    }

    // Return Object type conditional result of evaluating boolean expression
    public static Object evaluateConditionalOperation(boolean expression, Object result1, Object result2) {
        return expression ? result1 : result2;
    }

    /* Return Object type conditional result after evaluating boolean expression
     ** if expression=true , then read fieldValue using fieldName1 from instance1
     ** if expression=false , then read instance2
     */
    public static Object evaluateConditionalOperation(boolean expression, Object instance1, String fieldName1, String instance2) {
        return expression ? getFieldValue(instance1, fieldName1) : instance2;
    }

    /* Return Object type conditional result after evaluating boolean expression
     ** if expression=true , then read fieldValue using fieldName1 from instance1
     ** if expression=false , then read instance2
     */
    public static Object evaluateConditionalOperation(boolean expression, Object instance1, String fieldName1, Object instance2) {
        return expression ? getFieldValue(instance1, fieldName1) : instance2;
    }

    /* Return Object type conditional result after evaluating boolean expression
     ** if expression=true , then read instance1
     ** if expression=false , then read fieldValue using fieldName2 from instance2
     */
    public static Object evaluateConditionalOperation(boolean expression, Object instance1, Object instance2, String fieldName2) {
        return expression ? instance1 : getFieldValue(instance2, fieldName2);
    }

    /* Return Object type conditional result after evaluating boolean expression
     ** if expression=true , then read fieldValue using fieldName1 from instance1
     ** if expression=false , then read fieldValue using fieldName2 from instance2
     */
    public static Object evaluateConditionalOperation(boolean expression, Object instance1, String fieldName1, Object instance2, String fieldName2) {
        return expression ? getFieldValue(instance1, fieldName1) : getFieldValue(instance2, fieldName2);
    }

    /* Read fieldValue using fieldName from instance and return it */
    public static Object getFieldValue(Object instance, String fieldName) {
        Object value = null;
        if (null != instance) {
            try {
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(instance);
                value = beanWrapper.getPropertyValue(fieldName);
            } catch (NotReadablePropertyException e) {
                logger.error("Exception occurred while reading value of {}  :  {}", fieldName , e.getMessage());
            }
        }
        return value;
    }

    /**
     * Returns "YES" or "NO" based on given boolean value
     * @param value The boolean value
     * @return "YES" if value is true; otherwise "NO"
     */
    public static String convertToYesOrNo(boolean value) {
    	if(value) return AppConstants.YES;
    	return AppConstants.NO;
    }

    public static String getPosvQuestionValueForm(ProposalDetails proposalDetails, String questionId) {
        try {
            return Optional.ofNullable(proposalDetails)
                    .map(ProposalDetails::getPosvDetails)
                    .map(PosvDetails::getPosvQuestions)
                    .get().stream().map(x -> {
                return questionId.equalsIgnoreCase(x.getQuestionId()) ? Utility.convertToYesOrNo(x.getAnswer()) : NO;
            }).toString();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
            return NO;
        }

    }

    public static String convertToYesOrNo(String questionAnswer) {
        if (Objects.isNull(questionAnswer) || StringUtils.isEmpty(questionAnswer) || NULL.equalsIgnoreCase(questionAnswer)) {
            return AppConstants.NO;
        } else if (AppConstants.YES.equalsIgnoreCase(questionAnswer) || AppConstants.NEO_Y.equalsIgnoreCase(questionAnswer)
                || AppConstants.NEO_YES.equalsIgnoreCase(questionAnswer) || AppConstants.TRUE.equalsIgnoreCase(questionAnswer)
                || "1".equalsIgnoreCase(questionAnswer)) {
            return AppConstants.YES;
        } else if (AppConstants.NO.equalsIgnoreCase(questionAnswer) || AppConstants.NEO_N.equalsIgnoreCase(questionAnswer)
                || AppConstants.NEO_NO.equalsIgnoreCase(questionAnswer) || AppConstants.FALSE.equalsIgnoreCase(questionAnswer)
                || "0".equalsIgnoreCase(questionAnswer)) {
            return AppConstants.NO;
        } else {
            return questionAnswer;
        }
    }



    public static String convertToYesOrNoWithDefault(String keyName) {
        return StringUtils.isEmpty(keyName) ? keyName : convertToYesOrNo(keyName);
    }

    public static String dateFormatter(String date, String actualFormat, String desiredFormat) {
        String formattedDate = "";
        if (!StringUtils.isEmpty(date)) {
            try {
                DateFormat actualDateFormat = new SimpleDateFormat(actualFormat);
                DateFormat desiredDateFormat = new SimpleDateFormat(desiredFormat);
                Date actualDate = actualDateFormat.parse(date);
                formattedDate = desiredDateFormat.format(actualDate);
            } catch (ParseException e) {
                logger.error("Unable to format Date: ",e);
            }
        }
        return formattedDate;
    }

    public static String dateFormatterWithTimeZone(String date, String actualFormat, String desiredFormat) {
        String formattedDate = "";
        if (!StringUtils.isEmpty(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(actualFormat);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                SimpleDateFormat output = new SimpleDateFormat(desiredFormat);
                output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
                formattedDate = output.format(sdf.parse(date));
            } catch (ParseException ex) {
                logger.error("Error converting Effective Date of coverage for stp:", ex);
            }
        }
        return formattedDate;
    }

    public static String dateFormatterWithTimeZone(String date, String format) {
        String formattedDate = "";
        if (!StringUtils.isEmpty(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat output = new SimpleDateFormat(format);
            output.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
            try {
                Date formatDate = formatter.parse(date);
                formattedDate = output.format(formatDate);
            } catch (ParseException e) {
                logger.error(AppConstants.ERR_MSG_FOR_DATE_FORMATTER,getExceptionAsString(e));
            }
        }
        return formattedDate;

    }

    /*FUL2-11549 Payment acknowledgement for all channels : This method is used to return date in desired format*/
    public static String dateFormatter(Date date, String desiredFormat) {
        String formattedDate = "";
        if (date!=null) {
            try {
                DateFormat desiredDateFormat = new SimpleDateFormat(desiredFormat);
                formattedDate = desiredDateFormat.format(date);
            } catch (Exception e) {
                logger.error("Unable to format Date: ",e);
            }
        }
        return formattedDate;
    }

    public static String nullSafe(String s) {
        return Objects.nonNull(s) ? s : AppConstants.BLANK;
    }

    public static String nullSafeCustomDefaultValue(String s, String defaultValue) {
        return Objects.nonNull(s) ? s : defaultValue;
    }

    /**
     * Returns "NA" if given String is null or empty
     * @param value
     * @return "NA" if value is empty or null, otherwise value
     */
    public static String ifEmptyThenNA(String value) {
		if(StringUtils.isEmpty(value)) {
			return AppConstants.NA;
		} else {
			return value;
		}
	}


    public static String getUlipDeclarationVersionDate() {
        return "Online ULIP 1/" + ProposalFormChannelVersions.NEO.getChannelVersion() + "Ver 1.0";
    }

    public static String getStpDeclarationVersionDate() {
        return "Online STP 1/" + ProposalFormChannelVersions.NEO.getChannelVersion() + "Ver 1.0";
    }

    public static String getFullRepositoryName(String repoName) {
        if ("NDM".equalsIgnoreCase(repoName)) {
            return AppConstants.NDM_REPO_NAME;
        } else if ("CIR".equalsIgnoreCase(repoName)) {
            return AppConstants.CIR_REPO_NAME;
        } else if ("SHC".equalsIgnoreCase(repoName)) {
            return AppConstants.SCH_REPO_NAME;
        } else if ("KVY".equalsIgnoreCase(repoName)) {
            return AppConstants.KVY_REPO_NAME;
        } else if ("CAM".equalsIgnoreCase(repoName)) {
            return AppConstants.CAM_REPO_NAME;
        }
        return repoName;
    }

    public static String getPregnancyMonths(String pregnantSince) {
        switch (pregnantSince) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                return "<6 months";
            case "6":
            case "7":
            case "8":
            case "9":
                return ">= 6 months";
            default:
                return pregnantSince.concat(" months");
        }
    }

    public static String convertToPartyRole(String roleInParty) {
        if (StringUtils.isEmpty(roleInParty)) {
            return AppConstants.BLANK;
        } else if (AppConstants.MLA.equalsIgnoreCase(roleInParty)) {
            return AppConstants.ROLE_TWO;
        } else if (AppConstants.MP.equalsIgnoreCase(roleInParty)) {
            return AppConstants.ROLE_THREE;
        } else if (AppConstants.SOCIAL_WORKER.equalsIgnoreCase(roleInParty.replaceAll("\\s", ""))) {
            return AppConstants.ROLE_ONE;
        } else {
            return AppConstants.ROLE_FOUR;
        }
    }

    public static String getDateOnTheBasisOfRateChange(ProposalDetails proposalDetails, boolean isTimeNeeded) {
        String date =  "";
        if(Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getApplicationDetails())){
            date = proposalDetails.getApplicationDetails().getOtpDateTimeStamp();
            if(!isTimeNeeded || date.contains("00:00:00")){
                date = date.substring(0,10);
            }
            return date;
        }
        return date;
       }
    /*FUL2-11549 Payment acknowledgement for all channels : This method is used to return user friendly premium mode*/
    public static String getPaymentMethods(String actualPremiumMode) {
        String desiredPremiumMode = AppConstants.BLANK;
        if(!StringUtils.isEmpty(actualPremiumMode)){
            desiredPremiumMode = premiumModes.get(actualPremiumMode);
        }
        return desiredPremiumMode;
    }

    /*FUL2-11549 Payment acknowledgement for all channels : This method is used to return Date in String after applying UTC timeZone on it*/
    public static String getStringDateFromDate(Date actualDate) {
        String formattedDate = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            formattedDate = dateFormat.format(actualDate);
        } catch (Exception e) {
            logger.error("String to Date formatter failed:",e);
        }
        return formattedDate;
    }

    public static double roundOffValue(double value) {
        return Math.round(value * 100.00) / 100.00;
    }

	/**
	 * @param proposalDetails
	 * @return
	 * @throws NumberFormatException
	 */
	//FUL2-11549 Payment acknowledgement for all channels
	//we have updated the amount as per new requirement as in salesstory - separate amount should be display for PF
    public static double setPremiumAmount(ProposalDetails proposalDetails) throws NumberFormatException {
        double amount = 0;
        String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();

        try {
            logger.info("In setPremiumAmount for transaction {} ", proposalDetails.getTransactionId());

            /*FUL2-17826 removed the temporary gst Waiver changes*/
            if (proposalDetails.getSalesStoriesProductDetails() != null
                    && AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())
                    && proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() != 0L
                    && !StringUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo()
                    .getProductIllustrationResponse().getInitialPremiumforSales())) {
                amount = roundOffValue(Double.valueOf(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumforSales()));
            } else {
                amount = roundOffValue(Double.valueOf(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()));
            }
        } catch (Exception e) {
            logger.error("Exception in  setPremiumAmount", e);
        }
        return amount;
    }

    public static boolean checkIsSWPLumpsum(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getProductDetails())
                && !proposalDetails.getProductDetails().isEmpty()
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())) {
            String productCode = proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCode();
            return AppConstants.TSWPPL.equalsIgnoreCase(productCode) || AppConstants.TSWPPR.equalsIgnoreCase(productCode);
        }
        return false;
    }

    public static boolean isCC60Rider(String riderCode) {
        return Objects.nonNull(riderCode) && cc60RiderCodes.contains(riderCode);
    }

    public static boolean isAddRider(String riderCode) {
        return Objects.nonNull(riderCode) && addRiderCodes.contains(riderCode);
    }

    public static boolean checkIsSTPProduct(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getProductDetails())
                && !proposalDetails.getProductDetails().isEmpty()
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())) {

            return (proposalDetails.getProductDetails().get(0).getProductType().equalsIgnoreCase(AppConstants.STP)
                    || proposalDetails.getProductDetails().get(0).getProductType().equalsIgnoreCase(AppConstants.TRADITIONAL))
                    && !AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())
                    && !otpProductCodes.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCode());
        }
        return false;
    }

    public static String getNullSafeStringValueForDocs(String value, String defaultValue) {
        return Objects.nonNull(value) ? value : defaultValue;
    }
    /**
     * @param strValue
     * @return
     */
    public static int getMinAgeBand(String strValue) {
        String ageBand = strValue.split(":")[0];
        return Integer.parseInt(ageBand.split("-")[0]);
    }
    /**
     * @param strValue
     * @return
     */
    public static int getMaxAgeBand(String strValue) {
        String ageBand = strValue.split(":")[0];
        return Integer.parseInt(ageBand.split("-")[1]);
    }
    /**
     * @param strValue
     * @return
     */
    public static int getMultiple(String strValue) {
        return Integer.parseInt(strValue.split(":")[1]);
    }

    /**
     * @param bool
     * @return
     */
    public static boolean and(boolean... bool) {
        for (int i = 0; i < bool.length; i++) {
            if(!bool[i]) {
                return false;
            }
        }
        return true;
    }
    /**
     * @param bool
     * @return
     */
    public static boolean or(boolean... bool) {
        for (int i = 0; i < bool.length; i++) {
            if(bool[i]) {
                return true;
            }
        }
        return false;
    }
    public static String zeroIfNullOrEmpty(String value){
        return !StringUtils.isEmpty(value) ? value : "0";
    }


    public static boolean isCalledFromLogs(Thread thread) {
        return Arrays.stream(thread.getStackTrace())
                .anyMatch(className -> (LOG_TYPE_LIST.stream()
                        .anyMatch(logClassPackage -> className.getClassName().contains(logClassPackage))));
    }
    public static String getExceptionAsString(Exception exp) {
        return com.mli.mpro.location.auth.filter.Utility.getExceptionAsString(exp);
//        StringWriter sw = new StringWriter();
//        exp.printStackTrace(new PrintWriter(sw));
//		return sw.toString();
	}

    public static String toString(Object obj) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("{");
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                ReflectionUtils.makeAccessible(field);
                Object value = ReflectionUtils.getField(field, obj);
                if (isLoggerField(value)) {
                    continue;
                }
                if (isNull(value)) {
                    value = "null";
                } else {
                    value = field.isAnnotationPresent(Sensitive.class)
                            ? getMaskedValue(Objects.requireNonNull(value),
                            field.getAnnotation(Sensitive.class).value())
                            : value;
                }
                prepareStringBuilder(builder, value, field, obj);
            }
            builder = new StringBuilder(builder.substring(0, builder.length() - 2));
            builder.append("}");
            return builder.toString();
        }  catch (Exception ex) {
            logger.info("Exception occur at masking");
            return builder.append(" **** ").toString();
        }
    }

    private static boolean isNonPrimitiveType(Object value) {
        boolean flag = true;
        if (value.getClass().equals(String.class) ||
                (Objects.nonNull(value.getClass().getSuperclass()) && value.getClass().getSuperclass().equals(Number.class))
                || value.getClass().equals(Boolean.class)
                || value.getClass().equals(Date.class)) {
            flag = false;
        }
        return flag;
    }

    private static boolean isLoggerField(Object val) {
        return val instanceof Logger;
    }

    public static String getFormattedDateString(Date date, String dateFormat) {
        try {
            return DateTimeFormatter.ofPattern(dateFormat).format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } catch (Exception ex) {
            logger.error(ERROR_OCCURRED_WHILE_PARSING_THE_DATE, Utility.getExceptionAsString(ex));
        }
        return "";
    }

    public static void prepareStringBuilder(StringBuilder builder, Object fieldValue, Field field, Object originalObject) {
        try{
        String fieldName = isNull(field.getAnnotation(JsonProperty.class)) ? field.getName() : field.getAnnotation(JsonProperty.class).value();
        builder.append("\"" + fieldName + "\"").append(":");
        if (isNull(ReflectionUtils.getField(field, originalObject)) || isNonPrimitiveType(ReflectionUtils.getField(field, originalObject))) {
            builder.append(fieldValue).append(", ");
        } else {
            Object val = fieldValue.getClass().equals(Date.class) ? Utility.getFormattedDateString((Date)fieldValue, AppConstants.UTC_DATE_FORMAT) : fieldValue;
            val = val.getClass().equals(String.class) ? "\"" + val + "\"" : val;
            builder.append( val ).append(", ");
        }
    }catch(Exception ex){
        logger.error("Error while setting json {}", Utility.getExceptionAsString(ex) );

    }}

    public static String getMaskedValue(Object input, MaskType maskType) {
        try {
            String stringToBeMasked = input.toString();
            return stringToBeMasked.replaceAll(maskType.getValue(), "x").replaceAll(MASKED_REGEX, DEFAULT_MASK);
        }catch(Exception ex) {
            return "null";
        }
    }

    public static String emptyIfNull(String input){
        return Objects.isNull(input) ? "" : input;
    }

    public static boolean isNotNullOrEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    private static Date getDeclarationConfirmationDate(SellerConsentDetails sellerConsentDetails, ProposalDetails proposalDetails){
        Date otpOrDeclarationConfirmation = null;
        if(sellerConsentDetails == null){
            return null;
        }
        boolean posvDoneStatus = Objects.nonNull(proposalDetails.getPosvDetails())
                && Objects.nonNull(proposalDetails.getPosvDetails().getPosvStatus());
        boolean isDeclarationCompleted = AppConstants.SELLER_CONSENT_STATUS.equalsIgnoreCase(sellerConsentDetails.getSellerConsentStatus().name());

        if(isDeclarationCompleted && proposalDetails.getAdditionalFlags().isPaymentDone() && !posvDoneStatus){
            otpOrDeclarationConfirmation = proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate();
        } else if(isDeclarationCompleted && posvDoneStatus){
            otpOrDeclarationConfirmation = sellerConsentDetails.getLastModifiedDate();
        }
        return otpOrDeclarationConfirmation;
    }

    public static String middleNameSpacing(String input){
        return Objects.isNull(input) ? " " : String.join(""," ",input," ");
    }

    /**
     * Checks if is product annuity option.
     *
     * @param proposalDetails the proposal details
     * @param productList     the product list
     * @return true, if is product annuity option
     */
    public static boolean isAnnuityOptionJointLife(ProposalDetails proposalDetails, Stream<String> productList) {
        String productId = getProductId(proposalDetails);
        return productList.anyMatch(p -> p.equals(productId))
                && AppConstants.ANNUITY_OPT_JOINT.equals(Utility.getProductAnnuityOption(proposalDetails, null));
    }


    /**
     * @param proposalDetails
     * @return
     */
    public static String getProductId(ProposalDetails proposalDetails){
        if(Objects.isNull(proposalDetails) ||
                Objects.isNull(proposalDetails.getProductDetails()) ||
                proposalDetails.getProductDetails().isEmpty()) {
            return "";
        }
        ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
        return productInfo.getProductId();
    }

    /**
     * Gets the product annuity option.
     *
     * @param proposalDetails the proposal details
     * @param defaultIfNull the default if null
     * @return the product annuity option
     */
    public static String getProductAnnuityOption(ProposalDetails proposalDetails,String defaultIfNull) {
        ProductInfo productInfo=getProductInfo(proposalDetails, null);
        if(isNull(productInfo)) {
            return defaultIfNull;
        }
        String annuityOption = productInfo.getAnnuityOption();
        return StringUtils.isEmpty(annuityOption)?defaultIfNull:annuityOption;
    }

    /**
     * Gets the product info.
     *
     * @param proposalDetails the proposal details
     * @param defaultIfNull the default if null
     * @return the product info
     */
    public static ProductInfo getProductInfo(ProposalDetails proposalDetails,ProductInfo defaultIfNull) {
        ProductDetails productDetails = getProductDetails(proposalDetails, 0);
        if(isNull(productDetails.getProductInfo())) {
            return defaultIfNull;
        }else {
            return productDetails.getProductInfo();
        }
    }

    /**
     * Gets the product details.
     *
     * @param proposalDetails the proposal details
     * @param index the index
     * @return the product details
     */
    public static ProductDetails getProductDetails(ProposalDetails proposalDetails,int index) {
        if(proposalDetails==null
                || proposalDetails.getProductDetails()==null
                || proposalDetails.getProductDetails().isEmpty()) {
            return new ProductDetails();
        }
        return proposalDetails.getProductDetails().get(index);
    }

    public static boolean isYBLProposal(ProposalDetails proposalDetails) {
        // JV removal for DIY documents
        if(Objects.nonNull(proposalDetails.getDiyBrmsFieldConfigurationDetails())
                && Objects.nonNull(proposalDetails.getDiyBrmsFieldConfigurationDetails().getUtmBasedLogic())
                && AppConstants.Y.equalsIgnoreCase(proposalDetails.getDiyBrmsFieldConfigurationDetails().getUtmBasedLogic().getJvRemoval())){
            return true;
        }
        return (( (proposalDetails.getChannelDetails() !=null && proposalDetails.getChannelDetails().getChannel() !=null &&
             proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_YBL))
                || (proposalDetails.getAdditionalFlags() !=null && proposalDetails.getAdditionalFlags().isYblTelesalesCase() )
                || proposalDetails.getYblDetails() != null) || (isChannelNeoOrAggregator(proposalDetails) && (
                        AppConstants.BANK_JOURNEY_YBL.equalsIgnoreCase(proposalDetails.getBankJourney())||
                                isInfluencerChannelForYBL(proposalDetails))));
    }

    public static boolean isInfluencerChannelForYBL(ProposalDetails proposalDetails){
        return (
                INFLUENCER_CHANNEL_YBL.equals(proposalDetails.getChannelDetails().getInfluencerChannel()));
    }

    public static boolean isChannelNeoOrAggregator(ProposalDetails proposalDetails){
        return (
                proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                        || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR));
    }
    public static boolean isSwpCisWithValidVariant(String htmlForm, String variant) {
        String cleanVariant = variant.replace("\u00A0", " ");
        return AppConstants.SWP_CIS.equalsIgnoreCase(htmlForm) &&
                (AppConstants.LUMP_SUM.equalsIgnoreCase(cleanVariant) ||
                        AppConstants.LONG_TERM_WEALTH.equalsIgnoreCase(cleanVariant) ||
                        AppConstants.SHORT_TERM_WEALTH.equalsIgnoreCase(cleanVariant));
    }
    /**Checks if channel is YBL and nisTraceId is present*/
    public static boolean isChannelYBLNIS(ProposalDetails proposalDetails) {
    	return (Optional.ofNullable(proposalDetails).map(p->p.getChannelDetails()).map(c->c.getChannel()).map(c->AppConstants.CHANNEL_YBL.equalsIgnoreCase(c)).orElse(false)
    			&& Optional.ofNullable(proposalDetails).map(p->p.getYblDetails()).map(p->p.getNisTraceId()).map(id->!id.isBlank()).orElse(false));
    }

    public static void changeProposerAndLifeinsuredForForm2(ProposalDetails proposalDetails){
        if(!CollectionUtils.isEmpty(proposalDetails.getPartyInformation())
                && !CollectionUtils.isEmpty(proposalDetails.getEmploymentDetails().getPartiesInformation())){
            proposalDetails.setPartyInformation(Arrays.asList(proposalDetails.getPartyInformation().get(1), proposalDetails.getPartyInformation().get(0)));
            proposalDetails.getEmploymentDetails().setPartiesInformation(Arrays.asList(proposalDetails.getEmploymentDetails().getPartiesInformation().get(1), proposalDetails.getEmploymentDetails().getPartiesInformation().get(0)));

        }
    }

    public static Date otpDatePF(ProposalDetails proposalDetails, SellerConsentDetailsRepository sellerConsentDetailsRepository){
        Date sellerDeclarationSubmissionDate = null;
        SellerConsentDetails sellerConsentDetails = null;
        if (proposalDetails.getSalesStoriesProductDetails()!=null
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) {
            sellerConsentDetails = sellerConsentDetailsRepository.findByUniqueId(String.valueOf(proposalDetails.getTransactionId()));
            sellerDeclarationSubmissionDate = getDeclarationConfirmationDate(sellerConsentDetails, proposalDetails);
            if(sellerDeclarationSubmissionDate == null){
                sellerConsentDetails = sellerConsentDetailsRepository.findByUniqueId(String.valueOf(proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId()));
                sellerDeclarationSubmissionDate = getDeclarationConfirmationDate(sellerConsentDetails, proposalDetails);
            }
            if(sellerDeclarationSubmissionDate == null){
                sellerConsentDetails = sellerConsentDetailsRepository.findByUniqueId(String.valueOf(proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId()));
                sellerDeclarationSubmissionDate = getDeclarationConfirmationDate(sellerConsentDetails, proposalDetails);
            }
        } else {
            sellerDeclarationSubmissionDate = Objects.nonNull(proposalDetails.getSellerDeclaration()) ? proposalDetails.getSellerDeclaration().getDeclarationSubmitTime() : null;
        }
        return sellerDeclarationSubmissionDate;
    }

    public static String getOTPforPF(ProposalDetails proposalDetails, SimpleDateFormat format, String otpConfirmationDate, Date sellerDeclarationSubmissionDate) {
        String otpConfirmationDeclarationDate;
        if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsSellerDeclarationApplicable())) {
        	otpConfirmationDeclarationDate = sellerDeclarationSubmissionDate != null ? format.format(sellerDeclarationSubmissionDate) : StringUtils.EMPTY;
        } else {
            otpConfirmationDeclarationDate = otpConfirmationDate ;
        }
        return otpConfirmationDeclarationDate;
    }

    public static boolean isProductSWPJL(ProposalDetails proposalDetails) {
        return proposalDetails.getProductDetails().get(0).getProductType()
            .equalsIgnoreCase(AppConstants.SWPJL);
    }

    public static boolean isProductSTEP(ProposalDetails proposalDetails) {
        return proposalDetails.getProductDetails().get(0).getProductType()
                .equalsIgnoreCase(AppConstants.STEP_NEO);
    }

    public static boolean isApplicationIsForm2(ProposalDetails proposalDetails) {
        return proposalDetails.getApplicationDetails().getFormType()
                .equalsIgnoreCase(AppConstants.FORM2);
    }

    public static boolean isAxisJourney(ProposalDetails proposalDetails) {
        return IS_AXIS_JOURNEY.equalsIgnoreCase(proposalDetails.getIsAxisJourney());
    }
    public static boolean isProposerPresent(ProposalDetails proposalDetails) {
        return proposalDetails.getPartyInformation().stream().anyMatch(
            partyInformation -> partyInformation.getPartyType().equalsIgnoreCase("PROPOSER"));
    }

    public static boolean isProposerAndInsuredSame(ProposalDetails proposalDetails) {
    	String formType = proposalDetails.getApplicationDetails().getFormType();
    	String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
    	return (StringUtils.equalsIgnoreCase(formType, "SELF") ||
                StringUtils.equalsIgnoreCase(formType, "form1")
				|| Utility.schemeBCase(formType, schemeType));
    }

    public static String convertToBigDecimal(Double amount){
        if(amount != null && !Double.isNaN(amount)) {
            return new BigDecimal(amount,
                    MathContext.DECIMAL64).setScale(2, RoundingMode.CEILING).toString();
        }
        return StringUtils.EMPTY;
    }

    public static String setAgentAdvisorName(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getChannelDetails()) && Objects
            .nonNull(proposalDetails.getChannelDetails().getInfluencerChannel()) && proposalDetails
            .getChannelDetails().getInfluencerChannel().equalsIgnoreCase(INFLUENCER_CHANNEL_38)) {
            return AGENT_ADVISOR_NAME;
        }
        return AppConstants.BLANK;
    }
    /**
     * Prints the json request.
     *
     * @param object the object
     * @return the string
     */
    public static String printJsonRequest(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            object=isNull(object) ? "null": object.toString();
            return mapper.writeValueAsString(object).replace("\\", "");
        } catch (JsonProcessingException e1) {
            logger.info("Exception printJsonRequest: {} ",Utility.getExceptionAsString(e1));
        }
        return "";
    }

    public static String getJsonRequest(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e1) {
            logger.info("Exception getJsonRequest: {} ",Utility.getExceptionAsString(e1));
        }
        return "";
    }
    public static void addPepDetails(ProposalDetails proposalDetails,Map<String, Object> dataVariables){
        EmploymentDetails proposerEmploymentDetails = proposalDetails.getEmploymentDetails();
        String isPep;
        if (AppConstants.THANOS_CHANNEL
                .equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
            proposerEmploymentDetails.setPoliticallyExposed(
                    proposerEmploymentDetails.getPepDetails().isFamilyMemberPEP() || proposerEmploymentDetails
                            .getPepDetails().isProposerPEP() || proposerEmploymentDetails.getPepDetails()
                            .isLIPEP() || proposerEmploymentDetails.getPepDetails().isLIorNomineePEP());
        }
        if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
            isPep = monitorPEPAndOtherValues(proposerEmploymentDetails, LIPEP);
        } else {
            isPep = monitorPEPAndOtherValues(proposerEmploymentDetails, POLITICALLY_EXPOSED);
        }
        String isProposerPEP = monitorPEPAndOtherValues(proposerEmploymentDetails,PROPOSERPEP);
        String isLIPep = monitorPEPAndOtherValues(proposerEmploymentDetails,LIPEP);
        String isPayor;
        if ((AppConstants.GLIP_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
                || AppConstants.SPP_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()))
                && proposalDetails.getAdditionalFlags().getSecondAnnuitantPEP() != null)
            isPayor = proposalDetails.getAdditionalFlags().getSecondAnnuitantPEP().booleanValue() ? AppConstants.YES : AppConstants.NO;
        else
            isPayor = monitorPEPAndOtherValues(proposerEmploymentDetails, PAYORPEP);
        String isNomineePep = monitorPEPAndOtherValues(proposerEmploymentDetails,NOMINEEPEP);
        String isFamilyPep = monitorPEPAndOtherValues(proposerEmploymentDetails,FAMILY_MEMBERPEP);
        String politicalExperience = proposerEmploymentDetails.getPepDetails().getPoliticalExperience();
        String affiliation = proposerEmploymentDetails.getPepDetails().getAffiliationsToPoliticalparty();
        String role = proposerEmploymentDetails.getPepDetails().getRoleInPoliticalParty();
        String specifyRoleOther = proposerEmploymentDetails.getPepDetails().getRoleOthers();
        String portfolio = monitorPEPAndOtherValues(proposerEmploymentDetails,PORTFOLIO);
        String specifyMember = proposerEmploymentDetails.getPepDetails().getSpecifyFamilyMembers();
        String partyInPower = proposerEmploymentDetails.getPepDetails().getPartyInPower();
        String pepForeign = proposerEmploymentDetails.getPepDetails().getPepEverPostedInForeignOffice();
        String pepOffice = proposerEmploymentDetails.getPepDetails().getForeignOfficeDetails();
        String incomeSourcePep = proposerEmploymentDetails.getPepDetails().getIncomeSources();
        String pepIsCrime = proposerEmploymentDetails.getPepDetails().getPepConvicted();
        String convictedDetails = proposerEmploymentDetails.getPepDetails().getConvictionDetails();
        String productId= proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
        if(AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(productId) && proposalDetails.getAdditionalFlags().getIsPEPDisabled() != null){
            dataVariables.put("isPEPDisabled", proposalDetails.getAdditionalFlags().getIsPEPDisabled().toUpperCase());
        }
        dataVariables.put("isPEP", isPep);
        dataVariables.put("pep", BooleanUtils.isTrue(proposerEmploymentDetails.isPoliticallyExposed()) ? true : false );
        dataVariables.put("isLIPep", isLIPep);
        dataVariables.put("isFamilyPep",isFamilyPep);
        dataVariables.put("isNomineePep", isNomineePep);
        dataVariables.put("isProposerPEP", isProposerPEP);
        dataVariables.put("isPayor", isPayor);
        dataVariables.put("politicalExperience", politicalExperience);
        dataVariables.put("affiliation", affiliation);
        dataVariables.put("role", org.apache.commons.lang3.StringUtils.isNotBlank(role) ? org.apache.commons.lang3.StringUtils.upperCase(role) : AppConstants.BLANK);
        dataVariables.put("specifyRoleOther", specifyRoleOther);
        dataVariables.put("specifyMember", specifyMember);
        dataVariables.put(PORTFOLIO, portfolio);
        dataVariables.put("power", partyInPower);
        dataVariables.put("foreignOffice", pepForeign);
        dataVariables.put("pepOffice", pepOffice);
        dataVariables.put("sourceOfIncome", incomeSourcePep);
        dataVariables.put("criminalCharges", org.apache.commons.lang3.StringUtils.isNotBlank(pepIsCrime) ? org.apache.commons.lang3.StringUtils.upperCase(pepIsCrime) : AppConstants.BLANK );
        dataVariables.put("convictionDetails", convictedDetails);
    }


    public static String monitorPEPAndOtherValues(Object obj , String type)  {

        switch (type){
            case PROPOSERPEP:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return proposerEmploymentDetails.getPepDetails().isProposerPEP() ? AppConstants.YES : AppConstants.NO;
            }
            case LIPEP:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return proposerEmploymentDetails.getPepDetails().isLIPEP() ? AppConstants.YES : AppConstants.NO;
            }
            case PAYORPEP:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return proposerEmploymentDetails.getPepDetails().isPayorPep() ? AppConstants.YES : AppConstants.NO;
            }
            case NOMINEEPEP:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return proposerEmploymentDetails.getPepDetails().isLIorNomineePEP() ? AppConstants.YES : AppConstants.NO;
            }
            case FAMILY_MEMBERPEP:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return proposerEmploymentDetails.getPepDetails().isFamilyMemberPEP() ? AppConstants.YES : AppConstants.NO;
            }
            case PORTFOLIO:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails)obj;
                return  !org.springframework.util.StringUtils.isEmpty(proposerEmploymentDetails.getPepDetails().getPortfolioHandled()) ? proposerEmploymentDetails.getPepDetails().getPortfolioHandled() : AppConstants.NO;
            }
            case POLITICALLY_EXPOSED:{
                EmploymentDetails proposerEmploymentDetails = (EmploymentDetails) obj;
                return proposerEmploymentDetails.isPoliticallyExposed() ? AppConstants.YES : AppConstants.NO;
            }
            default: return AppConstants.NO;
        }
    }

    /**
     * Checks if is AXISR or ybl tele sales.
     *
     * @return true, if is AXISR or ybl tele sales
     */
    public static boolean isAxisOrYblTeleSales(ProposalDetails proposalDetails) {
        return !isEmpty(proposalDetails.getAdditionalFlags().getRequestSource()) &&
                (AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource()))
                || proposalDetails.getAdditionalFlags().isYblTelesalesCase();
    }

    //FUL2-123815 CAt channel login logic
    public static boolean isAgencyYblChannel(final String channel){
        return AppConstants.CHANNEL_AGENCY.equalsIgnoreCase(channel) || AppConstants.CHANNEL_CAT.equalsIgnoreCase(channel) || AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel);
    }
    //FUL2-54076-54297_Check-Broker-Channel
    public static boolean isBrokerChannel(final String channel, String goCode) {
                return AppConstants.CHANNEL_BROKER.equalsIgnoreCase(channel) && StringUtils.isNotEmpty(goCode) &&
                        CHANNEL_BROKER_GO_CODE_START_WITH.stream().anyMatch(e->goCode.toUpperCase().startsWith(e));
    }
    //FUL2-74378_Check-TMB-Bank
    public static boolean isTMBChannel(String channel, String goCode) {
        return CHANNEL_TMB.equalsIgnoreCase(channel) && goCode != null &&
                AppConstants.CHANNEL_TMB_GO_CODE_START_WITH.stream().anyMatch(e -> goCode.toUpperCase().startsWith(e));
    }

    public static boolean isTMBPartner(String sourceChannel) {
        return REQUEST_SOURCE_TMB.equalsIgnoreCase(sourceChannel);
    }
    //FUL2-104019_Check-Ujjivan-Bank
    public static boolean isUjjivanChannel(String channel, String goCode) {
        return CHANNEL_UJJIVAN.equalsIgnoreCase(channel) && goCode != null &&
                AppConstants.CHANNEL_UJJIVAN_GO_CODE_START_WITH.stream().anyMatch(e -> goCode.toUpperCase().startsWith(e));
    }

    /**
     * @param proposalDetails
     * @return
     */
    public static String getFormType(ProposalDetails proposalDetails) {
        if(proposalDetails==null
                || proposalDetails.getApplicationDetails()==null
                || proposalDetails.getApplicationDetails().getFormType()==null) {
            return "";
        }
        return proposalDetails.getApplicationDetails().getFormType();
    }
    public static boolean isForm3(ProposalDetails proposalDetails) {
        return AppConstants.FORM3.equalsIgnoreCase(getFormType(proposalDetails))
                && !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType());
    }

    public static boolean isTalicBICase(ProposalDetails proposalDetails) {
        return (!"BY".equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) &&
                isForm3(proposalDetails));
    }

    public static String imageType(ProposalDetails proposalDetails) {
        return isTalicBICase(proposalDetails) ? "Insured" : "Proposer" ;
    }

    public static String setRenewalPaymentDataForNeo(ProposalDetails proposalDetails,
                                                     Receipt receiptDetails) {
        if (Utility.isPaymentFrequencySingle(proposalDetails)) {
            return "NOT APPLICABLE";
        } else {
            logger.info("Neo :: Setting Renewal Payment type for transactionID : {}",
                    proposalDetails.getTransactionId());
            String paymentRenewedByPbMandate = paymentRenewedByPbMandate(proposalDetails);
            if (org.springframework.util.StringUtils.hasLength(paymentRenewedByPbMandate))
                return paymentRenewedByPbMandate;
            else
                return CHEQUE_PAYMENT_RENEW_MODE;
        }
    }

    private static String paymentRenewedByPbMandate(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getChannelDetails())
                && Objects.nonNull(proposalDetails.getChannelDetails().getChannel())
                && Objects.nonNull(proposalDetails.getChannelDetails().getInfluencerChannel())
                && AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(
                        proposalDetails.getChannelDetails().getChannel())
                && AppConstants.PB_INFLUENCER_CHANNEL_ID.equalsIgnoreCase(
                        proposalDetails.getChannelDetails().getInfluencerChannel())
                && Objects.nonNull(proposalDetails.getBank())
                && org.springframework.util.StringUtils.hasLength(
                        proposalDetails.getBank().getPaymentRenewedBy())) {
            if (PAYMENT_RENEWED_BY_ENACH.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))
                return PAYMENT_RENEWED_BY_ENACH;
            if (PAYMENT_RENEWED_BY_UPI.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))
                return PAYMENT_RENEWED_BY_UPI;
            if (PAYMENT_RENEWED_BY_CC.equalsIgnoreCase(proposalDetails.getBank().getPaymentRenewedBy()))
                return PAYMENT_RENEWED_BY_CC;
        }
        return null;
    }

    public static boolean isPaymentFrequencySingle(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getProductDetails())
                && !proposalDetails.getProductDetails().isEmpty() &&
                Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
            return Arrays.asList("TSWPW1","TSWPW2").contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId());
        }
        return false;
    }

    /** Check form C case and it wont be
     * axis telesales or ybl telesales
     * @param proposalDetails
     * @return
     */
    public static boolean isFormCExceptAxisOrYblTeleSales(ProposalDetails proposalDetails){
        return isForm3(proposalDetails) && !isAxisOrYblTeleSales(proposalDetails);
    }

    /** Return partyInformation wrt form type
     * for medical schedule
     * @param proposalDetails
     * @return
     */
    public static PartyInformation getPartyInfoWrtFormType(ProposalDetails proposalDetails){
        Stream<PartyInformation> partyInfoStream =proposalDetails.getPartyInformation().stream().filter(Objects::nonNull);
        /** In form C journey, Authorize Signatory would be considered as proposer */
        if(isFormCExceptAxisOrYblTeleSales(proposalDetails)){
            partyInfoStream = partyInfoStream.filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()));
        }else{
            partyInfoStream = partyInfoStream.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()));
        }
        return partyInfoStream.findFirst().orElse(new PartyInformation());
    }

    public static PartyInformation getPartyInfo(ProposalDetails proposalDetails){
        Stream<PartyInformation> partyInfoStream =proposalDetails.getPartyInformation().stream().filter(Objects::nonNull);
        partyInfoStream = partyInfoStream.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()));
        return partyInfoStream.findFirst().orElse(new PartyInformation());
    }

    public static String setDefaultValuePosSeller(ProposalDetails proposalDetails){
        logger.info("INTO setDefaultValuePosSeller for need of insurance transaction id {}", proposalDetails.getTransactionId());
        String needOfInsurance = null;
        boolean isObjectNotNull = (proposalDetails.getChannelDetails() != null && proposalDetails.getSourcingDetails() != null);
        if(isObjectNotNull && proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(CHANNEL_AXIS) && proposalDetails.getSourcingDetails().isPosSeller()){
            needOfInsurance = NEED_OF_INSURANCE;
        } else {
            needOfInsurance = proposalDetails.getProductDetails().get(0).getNeedOfInsurance();
        }

        return needOfInsurance;
    }

    public static boolean isCapitalGuaranteeSolutionProduct(ProposalDetails proposalDetails) {
        return proposalDetails.getSalesStoriesProductDetails() != null
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())
                && AppConstants.CAPITAL_GUARANTEE_SOLUTION.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails()
                .getProductDetails().get(0).getProductInfo().getProductId());
    }

    public static boolean isCapitalGuaranteeSolutionPrimaryProduct(ProposalDetails proposalDetails) {
		return isCapitalGuaranteeSolutionProduct(proposalDetails)
				&& proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId() == 0l
				&& proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() != 0l;
	}

	public static boolean isCapitalGuaranteeSolutionSecondaryProduct(ProposalDetails proposalDetails) {
		return isCapitalGuaranteeSolutionProduct(proposalDetails)
				&& proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() == 0l
				&& proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId() != 0l
				&& !org.springframework.util.StringUtils.hasText(proposalDetails.getSalesStoriesProductDetails().getPrimaryPolicyNumber());
	}

	public static String getSecondaryPolicyNumber(ProposalDetails proposalDetails, String proposalNumber) {
		if (Utility.isCapitalGuaranteeSolutionProduct(proposalDetails) && org.springframework.util.StringUtils
				.hasText(proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum())) {
			proposalNumber = proposalNumber + AppConstants.AND
					+ proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum();
		}
		return proposalNumber;
	}

	public static  String getPrimaryPolicyNumber(ProposalDetails proposalDetails, String proposalNumber) {
		if (Utility.isCapitalGuaranteeSolutionProduct(proposalDetails) && org.springframework.util.StringUtils
				.hasText(proposalDetails.getSalesStoriesProductDetails().getPrimaryPolicyNumber())) {
			proposalNumber = proposalNumber + AppConstants.AND
					+ proposalDetails.getSalesStoriesProductDetails().getPrimaryPolicyNumber();
		}
		return proposalNumber;
	}

	public static boolean compareDateFormats(String actualDate, String pattern) {
		boolean checkformat = false;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			dateFormat.setLenient(false);
			dateFormat.parse(actualDate);
			checkformat = true;
		} catch (Exception ex) {
			logger.info("Exception occurred while compareDateFormats {}", ex.getMessage());
		}

		return checkformat;
	}

	public static boolean isSSESProduct(String productId, String isSSESProduct, String isSSESSolveOption) {
		return AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(productId)
				&& AppConstants.SSESPRODUCT.equalsIgnoreCase(isSSESProduct)
				&& AppConstants.SSESSOLVEOPTION.equalsIgnoreCase(isSSESSolveOption);
	}

	public static String isSSESProductorNot(ProposalDetails proposalDetails){
		ProductInfo productInfo = getProductInfo(proposalDetails, null);
		return Optional.ofNullable(productInfo).map(ProductInfo::getIsSSESProduct).orElse(null);
	}

	public static String getSSESProductSolveOption(ProposalDetails proposalDetails){
		ProductInfo productInfo = getProductInfo(proposalDetails, null);
		return Optional.ofNullable(productInfo).map(ProductInfo::getSSESSolveOption).orElse(null);
	}

    public static boolean isWOPPresent(ProposalDetails proposalDetails) {
        boolean flag = false;
        List<RiderDetails> riderDetailsList = (proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails());
        for (RiderDetails rider : riderDetailsList) {
            if (AppConstants.WOP.equalsIgnoreCase(rider.getRiderInfo()) || AppConstants.AXIS_WOP.equalsIgnoreCase(rider.getRiderInfo()) && rider.isRiderRequired()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean isPayorBenefitRiderPresent(ProposalDetails proposalDetails) {
        boolean flag = false;
        List<RiderDetails> riderDetailsList = (proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails());
        for (RiderDetails rider : riderDetailsList) {
            if (AppConstants.RIDERVAR_PB.equalsIgnoreCase(rider.getRiderInfo())
            		&& rider.isRiderRequired()) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    public static void setTokenToRedis(String token, int expire, OauthTokenRepository oauthTokenRepo, String redisKey) {
        try {
            oauthTokenRepo.setToken(token,expire, redisKey);
        } catch (Exception e) {
            logger.info("Service Failed to set Token into Redis");
            logger.error(Utility.getExceptionAsString(e));
        }
    }


    public static String getAouthAccessToken(String oauthTokenUrl, String authClientID, String authClientSecret, String authTokenUsername, String authTokenPassword, OauthTokenRepository oauthTokenRepo, String redisKey){
        String accessToken = "";
        OauthTokenResponse oauthTokenResponse = new OauthTokenResponse();
        int expireTime = 0;
        logger.info("Called SOA Token Service");
        try {
            RestTemplate restTemplate = new RestTemplate();
            String plainClientCredentials = authClientID + ":" + authClientSecret;
            String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
            HttpHeaders headers = new HttpHeaders();
            headers.add(AppConstants.AUTH, "Basic " + base64ClientCredentials);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("grant_type", "password");
            multiValueMap.add("scope", "CustomerServicing");
            multiValueMap.add("username", authTokenUsername);
            multiValueMap.add("password", authTokenPassword);

            HttpEntity<?> httpEntity = new HttpEntity<>(multiValueMap, headers);
            oauthTokenResponse = restTemplate.postForObject(oauthTokenUrl, httpEntity, OauthTokenResponse.class);
            if (oauthTokenResponse != null && !org.springframework.util.StringUtils.isEmpty(oauthTokenResponse.getAccess_token())) {
                accessToken = oauthTokenResponse.getAccess_token();
                expireTime = oauthTokenResponse.getExpires_in()-10;
                setTokenToRedis(accessToken,expireTime,oauthTokenRepo,redisKey);
            }
        } catch (Exception e) {
            logger.info("exception occured to generate token");
            logger.error(Utility.getExceptionAsString(e));
        }

        return accessToken;
    }
    public static String getName(String salutation, String firstName, String middleName, String lastName) {
        return String.join(" ", nullSafe(salutation), nullSafe(firstName),
                nullSafe(middleName), nullSafe(lastName));
    }

    public static String getAddress(String houseNo, String area, String landMark, String pinCode, String city, String state,
                              String country) {
        return String.join(" ", nullSafe(houseNo), nullSafe(area), nullSafe(landMark),
                nullSafe(city), nullSafe(state), nullSafe(country), nullSafe(pinCode));
    }

    public static String getPlanDetailsWithSumAssured(String planDetails, String sumAssured) {
        return String.join(" ", nullSafe(planDetails), "_", nullSafe(sumAssured));
    }

    public static double roundOffValue(String value) {

        double convertedValue = 0;
        if (!StringUtils.isEmpty(value)) {
            convertedValue = Math.round(Double.valueOf(value) * 100.00) / 100.00;
        }
        return convertedValue;
    }
    //FUL2-46150 Vernacular Declaration Addition
    public static boolean newPFDeclaration(ProposalDetails proposalDetails, String deploymentDate) throws UserHandledException {
        if(Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getApplicationDetails()) &&
                Objects.nonNull(proposalDetails.getApplicationDetails().getCreatedTime()) &&
                proposalDetails.getApplicationDetails().getCreatedTime().
                        compareTo(getDeploymentDate(deploymentDate))>=0){
                return true;
        }
        return false;
    }
    private static Date getDeploymentDate(String deploymentDate) throws UserHandledException {
        try {
            return DateUtils.parseDate(deploymentDate,YYYY_MM_DD);
        }catch (ParseException e) {
            logger.error("Failed to parse vernacular declaration visibility date:", e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Failed to parse date");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String convertTextOrNA(String value) {
        return StringUtils.isEmpty(value) ? NA : value;
    }

    public static boolean isNotThanosCovidWIPCase(ProposalDetails proposalDetails) {
        Optional<PosvQuestion> covidQuestion = proposalDetails.getPosvDetails().getPosvQuestions().stream()
                .filter(q -> HC16.equalsIgnoreCase(q.getQuestionId())).findFirst();
        return covidQuestion.isPresent();
    }

    public static boolean checkBrokerTmbChannel(String channel, String goCode) {
		return (isBrokerChannel(channel, goCode) || isTMBChannel(channel, goCode));
	}

    public static String currentDate() {
        Date cDate = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(cDate.toInstant(), ZoneId.systemDefault());
        Date currentDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DD_MM_YYYY_SLASH);
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
        return format.format(currentDate);
    }
    public static String getInsuredName(String formType, ProposalDetails proposalDetails) {
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        if (AppConstants.DEPENDENT.equalsIgnoreCase(formType) || checkForm3WithSchemeA(formType,schemeType)) {
            Optional<PartyInformation> insuredPartyType = proposalDetails.getPartyInformation().stream().filter(partyInformation ->
                    INSURED.equalsIgnoreCase(partyInformation.getPartyType())).findFirst();
            if(insuredPartyType.isPresent() && Objects.nonNull(insuredPartyType.get().getBasicDetails())){
                BasicDetails insuredBasicDetails = insuredPartyType.get().getBasicDetails();
                return insuredBasicDetails.getFirstName() + (StringUtils.isEmpty(insuredBasicDetails.getMiddleName()) ? "" : " "
                        + insuredBasicDetails.getMiddleName()) + " " + insuredBasicDetails.getLastName();
              }
        } else if (checkSchemeBWithWipCase(formType,schemeType)) {
            Optional<PartyInformation> proposedPartyType = proposalDetails.getPartyInformation().stream().filter(partyInformation ->
                    PROPOSER.equalsIgnoreCase(partyInformation.getPartyType())).findFirst();
            if(proposedPartyType.isPresent() && Objects.nonNull(proposedPartyType.get().getBasicDetails())) {
                BasicDetails proposalBasicDetails = proposedPartyType.get().getBasicDetails();
                return  proposalBasicDetails.getFirstName() + (StringUtils.isEmpty(proposalBasicDetails.getMiddleName()) ? "" : " "
                        + proposalBasicDetails.getMiddleName()) + " " + proposalBasicDetails.getLastName();
            }
        }
        return AppConstants.BLANK;
    }

    public static boolean checkForm3WithSchemeA(String formType, String schemeType){
        return (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType));
    }
    public static boolean checkSchemeBWithWipCase(String formType, String schemeType){
        return !StringUtils.isEmpty(formType) && formType.equalsIgnoreCase(AppConstants.SELF) || Utility.schemeBCase(formType,schemeType);
    }

    public static String convertYesNoAndNA(String value) {
        if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("Y")) {
            return AppConstants.YES;
        } else if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("N")) {
            return AppConstants.NO;
        }
        return AppConstants.NA;
    }

    // FUL2-97686 Change agentId as SP code only for axis channel
    public static String setAgentIDOrSpCode(ProposalDetails proposalDetails){
        try {
            String channel = proposalDetails.getChannelDetails().getChannel();
            if (AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel)) {
                if(Utility.isDIYJourney(proposalDetails)){
                    return diyStaticID(proposalDetails.getAdditionalFlags().getSourceChannel(),0);
                }
                return (!StringUtils.isEmpty(proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode()) ?
                        proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode() : AppConstants.BLANK);
            } else {
                return (!StringUtils.isEmpty(proposalDetails.getSourcingDetails().getAgentId()) ?
                        proposalDetails.getSourcingDetails().getAgentId() : AppConstants.BLANK);
            }
        }catch(Exception e){
            logger.error("Getting exception in setting agentId or spCode for transaction id {} for exception {}",proposalDetails.getTransactionId() ,e);
            return "";
        }
    }
//FUL2-98649 VPOSV
    public static LocalDate stringToDateFormatter(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static boolean isNeoTrue(String value) {
        return "1".equals(value) || "01".equals(value) || "Y".equalsIgnoreCase(value) || "Yes".equalsIgnoreCase(value);
    }

    public static boolean isCovidAnnexureApplicable(ProposalDetails proposalDetails) {
        List<Boolean> isCovidAnnexureApplicable = new ArrayList<>();
        CovidQuestionnaire covidQuestionnaire = proposalDetails.getLifeStyleDetails()
                .stream()
                .findFirst()
                .map(LifeStyleDetails::getCovidQuestionnaire)
                .orElse(null);

            if (Objects.nonNull(covidQuestionnaire)) {
                Optional.ofNullable(proposalDetails.getLifeStyleDetails()).ifPresent(
                        lifeStyleDetailsList -> lifeStyleDetailsList.stream().filter(lifeStyleDetails -> !PAYOR.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                                .forEach(
                                        lifeStyleDetails -> {
                                            if (checkingCovidAnnexureTag(lifeStyleDetails)) {
                                                isCovidAnnexureApplicable.add(true);
                                            }
                                        }
                                )
                );
        }
        return isCovidAnnexureApplicable.contains(true);
    }

    private static boolean checkingCovidAnnexureTag(LifeStyleDetails lifeStyleDetails) {
        return Optional.ofNullable(lifeStyleDetails)
                .map(LifeStyleDetails::getCovidQuestionnaire)
                .map(CovidQuestionnaire::getCovidAnnexureApplicable)
                .map(Utility::isNeoTrue)
                 .orElse(false);
    }

    public static boolean isNewCovidQuestionApplicable(ProposalDetails proposalDetails) {
        List<Boolean> isNewApplicable = new ArrayList<>();
       CovidQuestionnaire covidQuestionnaire = proposalDetails.getLifeStyleDetails()
               .stream()
               .findFirst()
               .map(LifeStyleDetails::getCovidQuestionnaire)
               .orElse(null);
       if (Objects.nonNull(covidQuestionnaire)) {
           Optional.ofNullable(proposalDetails.getLifeStyleDetails()).ifPresent(
                   lifeStyleDetailsList -> lifeStyleDetailsList.stream().filter(lifeStyleDetails -> !PAYOR.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                           .forEach(
                                   lifeStyleDetails -> {
                                       if (Utility.isNeoTrue(lifeStyleDetails.getCovidQuestionnaire().getIsNewCovidQuestionApplicable())) {
                                           isNewApplicable.add(true);
                                       }
                                   }
                           )
           );
       }
        return isNewApplicable.contains(true);
    }

    public static boolean isNotNeoYes(ProposalDetails proposalDetails, Function<LifeStyleDetails, String> questionExtractor) {
        return Optional.ofNullable(proposalDetails)
                .map(ProposalDetails::getLifeStyleDetails)
                .filter(details -> !details.isEmpty())
                .flatMap(details -> Optional.ofNullable(questionExtractor)
                        .map(qe -> qe.apply(details.get(0))))
                .map(question -> question != null && !AppConstants.NEO_YES.equals(question))
                .orElse(false);
    }


    public static boolean isAnyObjectNull(Object ... obj){
        for(int i = 0; i < obj.length ; i++){
            if(obj[i] == null){
                return true;
            }
        }
        return false;
    }
    public static boolean isAnyStringNullOrEmpty(String ... obj){
        return Arrays.stream(obj).anyMatch(StringUtils::isEmpty);
    }
    public static String getFullNameByType(ProposalDetails proposalDetails, String nameType){
        String fullName;
        switch (nameType){
            case AppConstants.PROPOSER:
                fullName = getFullName(proposalDetails, AppConstants.PROPOSER);
                break;
            case AppConstants.INSURED:
                fullName = getFullName(proposalDetails, AppConstants.INSURED);
                break;
            default:
                fullName = AppConstants.BLANK;
                break;
        }
        return fullName;
    }

    private static String getFullName(ProposalDetails proposalDetails, String type){
        PartyInformation partyInformation = getPartyInformationByPartyType(proposalDetails,type);
        if(partyInformation !=null
                && partyInformation.getBasicDetails() != null){
            return getNameConcat(partyInformation.getBasicDetails().getFirstName(),
                    partyInformation.getBasicDetails().getLastName());
        }
        return AppConstants.BLANK;
    }

    public static PartyInformation getPartyInformationByPartyType(ProposalDetails proposalDetails, String partyType){
        return proposalDetails.getPartyInformation().stream()
                .filter(d -> partyType.equalsIgnoreCase(d.getPartyType())).findFirst()
                .orElse(new PartyInformation());
    }

    public static PartyInformation getPartiesInformationByPartyType(ProposalDetails proposalDetails, String partyType){
        if(Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getEmploymentDetails()) &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation())) {
            return proposalDetails.getEmploymentDetails().getPartiesInformation().stream()
                    .filter(d -> partyType.equalsIgnoreCase(d.getPartyType())).findFirst()
                    .orElse(new PartyInformation());

        }
        return new PartyInformation();
    }

    private static String getNameConcat(String ... arr) {
        String name =AppConstants.WHITE_SPACE;
        for (int i=0;i<arr.length;i++){
            if(org.springframework.util.StringUtils.hasLength(arr[i]))
                name = name + (AppConstants.WHITE_SPACE) + arr[i];
        }
        return name.trim();
    }

    public static boolean isThanosOrTelesales(String requestSource){
        return AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_TELESALES.equalsIgnoreCase(requestSource) || AppConstants.REQUEST_SOURCE_THANOS1.equalsIgnoreCase(requestSource);
    }

    public static boolean isChannelAggregator(ProposalDetails proposalDetails){
        return AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
    }
    /**
    * @implNote This is used to identify JointLife plan.
    * @param productDetails
    * @return boolean
    */
	public static boolean isSSPJointLife(ProductDetails productDetails) {
		String isJointLife = null;
		if (AppConstants.SSP_PRODUCT_ID.equals(productDetails.getProductInfo().getProductId())
				&& (StringUtils.isEmpty(productDetails.getProductInfo().getIsJointLife())
						|| !StringUtils.isEmpty(productDetails.getProductInfo().getIsJointLife()))) {
			isJointLife = (StringUtils.isEmpty(productDetails.getProductInfo().getIsJointLife()) ? "no"
					: productDetails.getProductInfo().getIsJointLife().toLowerCase());
			return AppConstants.YES.equalsIgnoreCase(isJointLife);
		}
		return false;
	}

    public static boolean schemeBCase(String schemeType){
        return SCHEME_B.equalsIgnoreCase(schemeType);
    }

    public static boolean schemeBCase(String formType, String schemeType){
        return FORM3.equalsIgnoreCase(formType) && SCHEME_B.equalsIgnoreCase(schemeType);
    }

    public static boolean isSchemeBCase(String schemeType){
        return AppConstants.SCHEME_B.equalsIgnoreCase(schemeType);
    }
    public static String getProposerName(ProposalDetails proposalDetails) {
        String formType = proposalDetails.getApplicationDetails().getFormType();
        if (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType,proposalDetails.getApplicationDetails().getSchemeType())) {
            return Utility.getFullNameByType(proposalDetails, AppConstants.PROPOSER);
        } else {
            return Utility.getFullNameByType(proposalDetails, AppConstants.INSURED);
        }
    }
    public static boolean isSSPSwissReCase(ProposalDetails proposalDetails) {
        if(proposalDetails.getAdditionalFlags() != null && Objects.nonNull(proposalDetails.getAdditionalFlags().getIsSspSwissReCase())){
            return AppConstants.SWISS_RE_PRODUCTS.contains(Utility.getProductId(proposalDetails))
                    && ("y").equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsSspSwissReCase());
        } else {
            return false;
        }
    }
    public static boolean isSSPProduct(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getProductDetails())
                && !proposalDetails.getProductDetails().isEmpty()) {
            return SSP.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType());
        }
        return false;
    }

    public static int calculateAgeForPiPosv(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

        //FUL2-135547_Setup_of_DCB_Bank_in_Mpro
        public static boolean isDCBBank (String channel, String goCode){
            return CHANNEL_DCB.equalsIgnoreCase(channel) && goCode != null
                    && Pattern.compile(DCB_GOCODE).matcher(goCode).matches();
        }
    //Ful-144865 motilal oswal
    public static boolean isMotilalOswal(String channel, String goCode) {
        return (channel!=null && CHANNEL_MOTILAL_OSWAL.equalsIgnoreCase(channel)) && (goCode != null
                && MOTILAL_OSWAL_GOCODE.stream().anyMatch(e -> goCode.toUpperCase().startsWith(e)));
    }
    //Ful-182598 Setup of stock holding
    public static boolean isStockHolding(String channel, String goCode) {
        return (channel!=null && CHANNEL_STOCK_HOLDING.equalsIgnoreCase(channel)) && (goCode != null
                && STOCK_HOLDING_GOCODE.stream().anyMatch(e -> goCode.toUpperCase().startsWith(e)));
    }

    public static String getFullName(String firstName, String middleName, String lastName) {
        return Stream.of(firstName, middleName, lastName)
                .filter(Utility::isNotNullOrEmpty)
                .collect(joining(WHITE_SPACE));
    }

    public static String decryptRequest(String payload) {
        String decryptedRequest = null;
        try {
            String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
            byte[] decryptedBytes = EncryptionDecryptionOnboardingUtil.decrypt(
                    java.util.Base64.getDecoder().decode(payload), java.util.Base64.getDecoder().decode(key));
            decryptedRequest = new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("Exception while decrypting: {}", com.mli.mpro.utils.Utility.getExceptionAsString(ex));
        }
        return decryptedRequest;
    }

    public static RequestResponse errorResponse(HttpStatus httpStatus, List<Object> errorMessages, String encryptionSource,String[] IVandKey) throws UserHandledException, JsonProcessingException {
        RequestResponse encResponse = new RequestResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        OutputResponse outputResponse;
        if(UJJIVAN.equalsIgnoreCase(encryptionSource))
        {
            outputResponse = getFailureResponse(httpStatus.value(), errorMessages);
            String response = EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse) , IVandKey[0], IVandKey[1].getBytes());
            encResponse.setPayload(response);
            return encResponse;

        }
        return encryptResponse(getFailureResponse(httpStatus.value(), errorMessages));
    }
    public static OutputResponse getFailureResponse(int code , List<Object> errorMessages) {
        ErrorResponse errorResponse = new ErrorResponse(code,errorMessages);
        OutputResponse outputResponse = new OutputResponse(new com.mli.mpro.productRestriction.models.Response(errorResponse));
        return outputResponse;
    }
    public static long getProcessedTime(long requestedTime) {
        long processingTime = System.currentTimeMillis();
        return (processingTime - requestedTime) / 1000;
    }
    public static RequestResponse encryptResponse(OutputResponse response) {
        RequestResponse encResponse = new RequestResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
            String payload = EncryptionDecryptionOnboardingUtil.encrypt(objectMapper.writeValueAsString(response),
                    java.util.Base64.getDecoder().decode(key));
            encResponse.setPayload(payload);
        } catch (Exception e) {
            logger.error("Exception while encrypting: {}", Utility.getExceptionAsString(e));
        }
        return encResponse;
    }
    public static boolean isSSPJLProduct(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getProductDetails())
                && !proposalDetails.getProductDetails().isEmpty()
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getIsSspJLRiderSelected())) {
            return NEO_YES.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getIsSspJLRiderSelected());
        }
        return false;
    }
    //FUL2-18654_New_Fund_Addition_For_FAST_TRACK_SUPER_PLAN
    public static UIOutputResponse isNftySmallCapIndexFundPopUpEnableOrNot(UIConfiguration configuration, UIOutputResponse uIOutputResponse){

        ZoneId istZone = ZoneId.of(TIME_Z_ASIA);
        ZonedDateTime currentDateTimeIST = ZonedDateTime.now(istZone);
        LocalDate currentDate = currentDateTimeIST.toLocalDate();
        LocalDate startDate = DateTimeUtils.convertDateToLocalDate(configuration.getFromDate());
        LocalDate endDate = DateTimeUtils.convertDateToLocalDate(configuration.getToDate());
        String fromNFOFund = configuration.getFromNFOFund();
        String toNFOFund = configuration.getToNFOFund();
        HashMap output = new HashMap();

        if((currentDate.isAfter(startDate)||currentDate.isEqual(startDate))
                && (currentDate.isBefore(endDate) || currentDate.isEqual(endDate))){
        	String formattedStartDate = formatDateWithSuffix(startDate);
        	String formattedEndDate = formatDateWithSuffix(endDate);

            output.put("popUp", "No");//NFO Period
            // output.put("startDate", formattedStartDate);
            // output.put("endDate", formattedEndDate);
            // output.put("fromNFOFund", fromNFOFund);
            // output.put("toNFOFund", toNFOFund);
        }
        else {
            output.put("popUp","No");
        }
        uIOutputResponse.setResponse(output);
        return uIOutputResponse;
    }

    public static void niftyFundEnableOrNot ( UIConfigurationService uiConfigurationService, FundsData.AllFundsModel allFunds, RecommendedFund recommendedFund, Map<String, Object> dataVariables, String productId){

    	List<UIConfiguration> configuration = uiConfigurationService.getConfigurationByTypeAndKey("uiswitch", "NIFTY_INDEX");
        ZoneId istZone = ZoneId.of(TIME_Z_ASIA);
        ZonedDateTime currentDateTimeIST = ZonedDateTime.now(istZone);
        LocalDate currentDate = currentDateTimeIST.toLocalDate();
        boolean isFundRemoved = false;
        boolean isPFFund = true;
        if(configuration!=null && !configuration.isEmpty()) {
            LocalDate startDate = DateTimeUtils.convertDateToLocalDate(configuration.get(0).getFromDate());
            LocalDate endDate = DateTimeUtils.convertDateToLocalDate(configuration.get(0).getToDate());
            String coolingPeriodDays= configuration.get(0).getCoolingPeriodDays();
            LocalDate coolingPeriodDate = endDate.plusDays(Long.parseLong(coolingPeriodDays));
			logger.info("CurrentDate {} StartDate {} EndDate {} CoolingPeriodDate {} CurrentDateTimeIST {}",
					currentDate, startDate, endDate, coolingPeriodDate, currentDateTimeIST);

            if ( StringUtils.isBlank(productId) && (currentDate.isBefore(startDate) ||
                    (currentDate.isAfter(endDate) && (currentDate.isBefore(coolingPeriodDate) || currentDate.isEqual(coolingPeriodDate))))) {
                fundRemoved(allFunds, recommendedFund, configuration.get(0));
                isFundRemoved = true;
            }
			if (StringUtils.isNotBlank(productId) && configuration.get(0).getNonNfoProductList().contains(productId)
					&& (currentDate.isBefore(startDate) || currentDate.isBefore(endDate)
							|| currentDate.isEqual(endDate))) {
				fundRemoved(allFunds, recommendedFund, configuration.get(0));
			}
            if(YES.equalsIgnoreCase(configuration.get(0).getFundForPf()) && isNFOPeriod(currentDate,startDate,endDate)) {
                isPFFund = false;
            }
            //This logic is for handling the ProposalForm and IRP Document logic for not showing fund if removed or switched off.
            if(dataVariables != null) {
                if (isFundRemoved) {
                    dataVariables.put(NIFTY_FUND_FEATURE_FLAG, false);
                } else if (isPFFund) {
                    dataVariables.put(NIFTY_FUND_FEATURE_FLAG, true);
                } else {
                    dataVariables.put(NIFTY_FUND_FEATURE_FLAG, false);
                }
            }
        }
    }
    /**
     * @implNote This method is used to provide response based on configuration for enabling/disabling Smart Ultra Protect Rider
     * @param configuration
     * @param uIOutputResponse
     * @implSpec FUL2-169089
     * @return
     */
    public static UIOutputResponse isRiderEnabled(UIConfiguration configuration, UIOutputResponse uIOutputResponse, String key){
        ZoneId istZone = ZoneId.of(TIME_Z_ASIA);
        ZonedDateTime currentDateTimeIST = ZonedDateTime.now(istZone);
        LocalDate currentDate = currentDateTimeIST.toLocalDate();
        LocalDate startDate = DateTimeUtils.convertDateToLocalDate(configuration.getFromDate());
        LocalDate endDate = DateTimeUtils.convertDateToLocalDate(configuration.getToDate());
        HashMap output = new HashMap();
        if((currentDate.isAfter(startDate)||currentDate.isEqual(startDate))
                && (currentDate.isBefore(endDate) || currentDate.isEqual(endDate))){
            String formattedStartDate = formatDateWithSuffix(startDate);
            String formattedEndDate = formatDateWithSuffix(endDate);
            output.put(key, "Yes");//Rider Enabled
            output.put("startDate", formattedStartDate);
            output.put("endDate", formattedEndDate);
        }
        else {
            output.put(key,"No");//Rider Disabled
        }
        uIOutputResponse.setResponse(output);
        return uIOutputResponse;
    }
	public static void fundRemoved(FundsData.AllFundsModel allFunds, RecommendedFund recommendedFund, UIConfiguration configuration) {
		List<String> allFundList = new ArrayList<>();
		List<String> recommendedList = new ArrayList<>();

		//Removing NIFTY Fund in cooling period from all fund list.
		if (allFunds != null && recommendedFund != null) {
			allFunds.getFunds().forEach(funds -> {
				if (!funds.equalsIgnoreCase(getFundName(configuration))) {
					allFundList.add(funds);
				}
			});

			// Removing NIFTY Fund in cooling period from recommended fund list.
			recommendedFund.getVeryAggressive().getFunds().forEach(funds -> {
				if (!funds.equalsIgnoreCase(getFundName(configuration))) {
					recommendedList.add(funds);
				}
			});
			allFunds.setFunds(allFundList);
			recommendedFund.getVeryAggressive().setFunds(recommendedList);
		}
	}

	private static String getFundName(UIConfiguration configuration) {
		return configuration.getFromNFOFund().replace("Max Life ", "");

	}

    private static String formatDateWithSuffix(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String dateString = date.format(formatter);

        int dayOfMonth = date.getDayOfMonth();
        String suffix;

        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
            suffix = "th";
        } else {
            switch (dayOfMonth % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
            }
        }

        return dateString.replaceFirst(" ", suffix + " ");
    }

    /**
     * This method is used for creating agent360 request
     * @param payload
     * @return AgentRequest payload
     */
    public static SoaApiRequest createRequestForAgent360Info(RequestPayload payload) {
        SoaApiRequest agent360InfoDetails = new SoaApiRequest();

        com.mli.mpro.agent.models.Request agent360Request = new com.mli.mpro.agent.models.Request();
        com.mli.mpro.agent.models.RequestHeader agent360RequestHeader = new com.mli.mpro.agent.models.RequestHeader();
        agent360RequestHeader.setApplicationId(AgentSelfConstants.FULFILLMENT);
        agent360RequestHeader.setCorrelationId(UUID.randomUUID().toString());
        agent360Request.setHeader(agent360RequestHeader);

        com.mli.mpro.agent.models.RequestPayload agent360RequestPayload = new com.mli.mpro.agent.models.RequestPayload();
        agent360RequestPayload.setAgentId(payload.getAgentId());
        agent360RequestPayload.setSearchBy(payload.getSearchBy());
        agent360RequestPayload.setSearchValue(payload.getSearchValue());
        agent360RequestPayload.setDob(BLANK);
        agent360RequestPayload.setPanNo(BLANK);

        agent360RequestPayload.setServices(payload.getServices());
        agent360Request.setPayload(agent360RequestPayload);
        agent360InfoDetails.setRequest(agent360Request);

        return agent360InfoDetails;
    }

	public static boolean isValidateYblTelesalebranchCodeRequest(com.mli.mpro.common.models.InputRequest inputRequest) {
		if (inputRequest != null && inputRequest.getRequest() != null
				&& inputRequest.getRequest().getRequestData() != null
				&& inputRequest.getRequest().getRequestData().getRequestPayload() != null
				&& inputRequest.getRequest().getRequestData().getRequestPayload().getBranchCd() != null) {
			String branchCode = inputRequest.getRequest().getRequestData().getRequestPayload().getBranchCd();
			if (branchCode != null && !StringUtils.isEmpty(branchCode)) {
				return true;
			}
		}
		return false;
	}

    public static ClientHttpRequestFactory clientHttpRequestFactory(int timeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeout);
        factory.setReadTimeout(timeout);
        return factory;
    }

    public static List<String> getPosvArrayQuestionAnswers(String questionId, ProposalDetails proposalDetails){
        return null;
    }
    public static ResponseEntity<Object> buildErrorResponse(String msg, String msgDescription, int msgCode,
                                                      HttpStatus httpStatus) {
        com.mli.mpro.location.newApplication.model.Response response = new com.mli.mpro.location.newApplication.model.Response();
        Result result = new Result();
        ResponseMsgInfo msgInfo = new ResponseMsgInfo(msgCode, msg, msgDescription);
        response.setMsginfo(msgInfo);
        response.setPayload(new SoaResponse());
        result.setResponse(response);
        return new ResponseEntity<>(result, httpStatus);
    }

    public static boolean isProductSGPPJL(ProposalDetails proposalDetails) {
 	    if(Objects.nonNull(proposalDetails.getProductDetails())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0))
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())){
 	        return SGPPJL_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId());
        }
 	    return false;
    }

    public static boolean isProductSGPP(ProposalDetails proposalDetails) {
        if(Objects.nonNull(proposalDetails.getProductDetails())
                && Objects.nonNull(proposalDetails.getProductDetails().get(0))
                && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())){
            return SGPP_PRODUCTS.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId());
        }
        return false;
    }

    public static boolean isProductBothSGPPOrSGPPJL(ProposalDetails proposalDetails) {
        if(Utility.isProductSGPP(proposalDetails) || Utility.isProductSGPPJL(proposalDetails)){
            return true;
        }
        return false;
    }

    @Retryable(value = {Exception.class}, maxAttempts = 3)
    public static String getOAuthAccessToken(String oauthTokenUrl, String authClientID, String authClientSecret, String authTokenUsername, String authTokenPassword, OauthTokenRepository oauthTokenRepo, String redisKey, LinkedMultiValueMap<String, String> multiValueMap){
        String accessToken = "";
        OauthTokenResponse oauthTokenResponse = new OauthTokenResponse();
        int expireTime = 0;
        logger.info("Called SOA Token Service for ifsc");
        try {
            RestTemplate restTemplate = new RestTemplate();
            String plainClientCredentials = authClientID + ":" + authClientSecret;
            String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
            HttpHeaders headers = new HttpHeaders();
            headers.add(AppConstants.AUTH, "Basic " + base64ClientCredentials);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<?> httpEntity = new HttpEntity<>(multiValueMap, headers);
            oauthTokenResponse = restTemplate.postForObject(oauthTokenUrl, httpEntity, OauthTokenResponse.class);
            if (oauthTokenResponse != null && !org.springframework.util.StringUtils.isEmpty(oauthTokenResponse.getAccess_token())) {
                accessToken = oauthTokenResponse.getAccess_token();
                expireTime = oauthTokenResponse.getExpires_in()-10;
                setTokenToRedis(accessToken,expireTime,oauthTokenRepo,redisKey);
            }
        } catch (Exception e) {
            logger.error("exception {} occured to generate token for ifsc",e.getMessage());
            throw e;
        }
        return accessToken;

    }
    //Medical DHU changes
    public static String getArrayAns(String questionId, String subQuestionId, BaseMapper baseMapper, Map<String, List<PosvQuestion>> posvQAMap) {
        try {
            String arrayAnswer = baseMapper.getMedicalQuestionAnswer(posvQAMap, questionId, subQuestionId);
            if (StringUtils.equalsIgnoreCase(arrayAnswer, "None")) {
                return arrayAnswer;
            }
            if (StringUtils.equalsIgnoreCase(arrayAnswer,NA))
                return NA;
            int arrayAnswerLength = arrayAnswer.length()-1;
            arrayAnswer = arrayAnswer.substring(1,arrayAnswerLength);
            logger.info("Answer returned for arrayAns questionId to Thymeleaf is {}",arrayAnswer);
            return arrayAnswer;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception while sending arrayAns to Thymeleaf ");
            return BLANK;
        }
    }


    public static String getArrayAnsYesOrNoResponse(String arrayAnswer) {
        try {
            if (org.springframework.util.StringUtils.isEmpty(arrayAnswer) || StringUtils.equalsIgnoreCase(arrayAnswer, NA))
                return NA;
            return StringUtils.equalsIgnoreCase(arrayAnswer, "None") ? NO : YES;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception while sending getArrayAnsYesOrNoResponse to Thymeleaf");
            return BLANK;
        }
    }
    public static HttpHeaders setAPISecretInHeaders(String apiClientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("api_client_secret", apiClientSecret);
        return headers;
    }

    public static String getCurrentDateInString() {
        LocalDateTime date = LocalDateTime.now(ZoneId.of(ASIA_KOLKATA_ZONE));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_MM_DD_YYYY_HH_MM_SS_GMT);
        return dateTimeFormatter.format(date);
    }

    public static Boolean checkSSPFinancialDocumentAndSection(ProposalDetails proposalDetails) {
        logger.info("{} check SSP product mandatory document uploaded for AND section", proposalDetails.getTransactionId());
        if (AppConstants.SMART_SECURE_PLUS_PLAN.equalsIgnoreCase(proposalDetails.
                getProductDetails().get(0).getProductInfo().getProductId())) {
            long count = proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments()
                    .stream().filter(documentDetails -> AppConstants.FINANCIAL.equalsIgnoreCase(documentDetails.getType())
                            && documentDetails.isRequiredForMproUi() && documentDetails.ismProDocumentStatus()
                            && (AppConstants.SALARY_SLIP_DOC_ID.equalsIgnoreCase(documentDetails.getMproDocumentId())
                            || AppConstants.CREDIT_BANK_STATEMENT_DOC_ID.equalsIgnoreCase(documentDetails.getMproDocumentId())))
                    .count();
            logger.info("{} Mandatory document uploaded for SSP product count is {}", proposalDetails.getTransactionId(), count);
            return count == 2 ? true : checkSSPFinancialDocumentORSection(proposalDetails);
        }
        return true;
    }

    public static Boolean isAllDocumentUploaded(ProposalDetails proposalDetails) {
        List<DocumentDetails> documentList = proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus()
                .getRequiredDocuments().stream()
                .filter(documentDetails -> documentDetails.isRequiredForMproUi()
                        && (!AppConstants.UNDERWRITING_STATUS_REQUIREDDOCUMENT_TYPE.equalsIgnoreCase(documentDetails.getType()))
                        && (!FINANCIAL.equalsIgnoreCase(documentDetails.getType()))
                        && (!AppConstants.DOCUMENT_TYPE_MEDICAL.equalsIgnoreCase(documentDetails.getType()))
                        && (!(AppConstants.ISMS_DOCUMENT.equalsIgnoreCase(documentDetails.getDocumentName()) && AppConstants.TPP_REQUIREMENT_DOC_TYPE.equalsIgnoreCase(documentDetails.getType())))
                        && (!documentDetails.ismProDocumentStatus() && (documentDetails.getS3Filepath() == null
                        || documentDetails.getS3Filepath().isEmpty()))).collect(Collectors.toList());
        if(documentList.isEmpty())
        {
            logger.info("for transactionId {} financial Documents check", proposalDetails.getTransactionId());
            return AppConstants.HASHPASS_STATUS.equalsIgnoreCase(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getFinancialGridStatus())
                    //FUL2-49615 Code changes for AutoSubmit requirement and resolve old Code issue
                    || (proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus()
                    .getRequiredDocuments().stream()
                    .anyMatch(documentDetails -> (FINANCIAL.equalsIgnoreCase(documentDetails.getType())
                            && documentDetails.isRequiredForMproUi()))
                    ? proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus()
                    .getRequiredDocuments().stream()
                    .filter(documentDetails -> documentDetails.isRequiredForMproUi()
                            && (FINANCIAL.equalsIgnoreCase(documentDetails.getType()))
                            && (documentDetails.ismProDocumentStatus())
                            && checkSSPFinancialDocumentAndSection(proposalDetails)).count() >= 1 :true);
        }
        return false;
    }
    public static Boolean checkSSPFinancialDocumentORSection(ProposalDetails proposalDetails) {
        logger.info("{} check SSP product mandatory document uploaded for OR section", proposalDetails.getTransactionId());
        return proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments()
                .stream().filter(documentDetails -> AppConstants.FINANCIAL.equalsIgnoreCase(documentDetails.getType())
                        && documentDetails.isRequiredForMproUi() && documentDetails.ismProDocumentStatus()
                        && !AppConstants.SALARY_SLIP_DOC_ID.equalsIgnoreCase(documentDetails.getMproDocumentId())
                        && !AppConstants.CREDIT_BANK_STATEMENT_DOC_ID.equalsIgnoreCase(documentDetails.getMproDocumentId()))
                .count() >= 1 ? true : false;
    }


    public static boolean isPaymentCompleted(ProposalDetails mergedProposalDetails) {
        try {
            if(CHANNEL_AXIS.equalsIgnoreCase(mergedProposalDetails.getChannelDetails().getChannel())
                    && AppConstants.REQUEST_SOURCE_AXIS.equalsIgnoreCase(mergedProposalDetails.getAdditionalFlags().getRequestSource())
                    && !(AppConstants.YES.equalsIgnoreCase(mergedProposalDetails.getApplicationDetails().getPhysicalJourneyEnabled())
                    && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(PHYSICAL_JOURNEY_FEATURE_FLAG)))){
                return isAxisPaymentDone(mergedProposalDetails);
            }
            if (mergedProposalDetails.getPaymentDetails() != null) {
                Receipt receipt = mergedProposalDetails.getPaymentDetails().getReceipt().get(0);
                logger.info("inside isPaymentCompleted for transactionId {} with flag {} and paymentDetails {}",
                        mergedProposalDetails.getTransactionId(),
                        mergedProposalDetails.getAdditionalFlags().isPaymentDone(),
                        mergedProposalDetails.getPaymentDetails());
                switch (receipt.getPremiumMode().toUpperCase()) {
                    case PREMIUM_MODE_CHEQUE:
                        PaymentChequeDetails chequeDetails = receipt.getPaymentChequeDetails();
                        return chequeDetails.getChequeAmount() > 0
                                && !org.springframework.util.StringUtils.isEmpty(chequeDetails.getChequeMicr())
                                && String.valueOf(chequeDetails.getChequeMicr()).length() == 9
                                && !org.springframework.util.StringUtils.isEmpty(chequeDetails.getChequeBankName())
                                && !org.springframework.util.StringUtils.isEmpty(chequeDetails.getChequeNumber())
                                && !org.springframework.util.StringUtils.isEmpty(chequeDetails.getChequePayableAt())
                                && !org.springframework.util.StringUtils.isEmpty(chequeDetails.getChequeDate());
                    case PREMIUM_MODE_DEMAND_DRAFT:
                        DemandDraftDetails demandDraftDetails = receipt.getDemandDraftDetails();
                        return demandDraftDetails.getDemandDraftAmount() > 0
                                && !org.springframework.util.StringUtils.isEmpty(demandDraftDetails.getDemandDraftMicr())
                                && String.valueOf(demandDraftDetails.getDemandDraftMicr()).length() == 9
                                && !org.springframework.util.StringUtils.isEmpty(demandDraftDetails.getDemandDraftBankName())
                                && !org.springframework.util.StringUtils.isEmpty(demandDraftDetails.getDemandDraftNumber())
                                && !org.springframework.util.StringUtils.isEmpty(demandDraftDetails.getDemandDraftPayableAt())
                                && !org.springframework.util.StringUtils.isEmpty(demandDraftDetails.getDemandDraftDate());
                    default:
                        return mergedProposalDetails.getAdditionalFlags().isPaymentDone();
                }

            }
        }catch(Exception ex){
            logger.info("error in isPaymentCompleted with msg {}", Utility.getExceptionAsString(ex));
        }
        return false;
    }

    private static boolean isAxisPaymentDone(ProposalDetails proposalDetails) {
        logger.info("entered isPaymentDone method of with transactionId {}",proposalDetails.getTransactionId());
        boolean isVoucherPaymentDone = false;
        try {
            isVoucherPaymentDone = (Objects.nonNull(proposalDetails.getPaymentDetails())
                    && Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
                    && Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt().get(0).getDirectPaymentDetails())
                    && !org.springframework.util.StringUtils.isEmpty(proposalDetails.getPaymentDetails().getReceipt().get(0).getDirectPaymentDetails().getvoucherNumber())
                    && !org.springframework.util.StringUtils.isEmpty(proposalDetails.getPaymentDetails().getReceipt().get(0).getDirectPaymentDetails().getVoucherUpdatedDate()));
        } catch (NullPointerException ex) {
            logger.error("Exception in isPaymentDone method with transactionId {}",Utility.getExceptionAsString(ex));
        }
        logger.info("payment status for triggering email and sms {} for transactionId {}",
                proposalDetails.getAdditionalFlags().isPaymentDone()||isVoucherPaymentDone, proposalDetails.getTransactionId());
        return proposalDetails.getAdditionalFlags().isPaymentDone()||isVoucherPaymentDone;
    }
    public static boolean isSellerDeclarationCompleted(ProposalDetails mergedProposalDetails, SellerConsentDetailsRepository sellerConsentDetailsRepository, ConfigurationRepository configurationRepository) {
        try {
            logger.info("inside isSellerDeclarationCompleted for policyNumber {} with flag {}",
                    mergedProposalDetails.getApplicationDetails().getPolicyNumber(), mergedProposalDetails.getAdditionalFlags().getSellerConsentStatus());
            if (Objects.nonNull(mergedProposalDetails.getChannelDetails())
                    //Ful2-48777 Seller declaration Mandatory for all Branch codes
                    && isSellerDeclarationFlowAvailable(mergedProposalDetails.getChannelDetails().getChannel(), configurationRepository)) {
                SellerConsentDetails sellerConsentDetails = sellerConsentDetailsRepository.findByPolicyNumber(mergedProposalDetails.getApplicationDetails().getPolicyNumber());
                logger.info("sellerConsentDetails {}",sellerConsentDetails);
                return AppConstants.COMPLETED.equalsIgnoreCase(mergedProposalDetails.getAdditionalFlags().getSellerConsentStatus())
                        || (Objects.nonNull(sellerConsentDetails) && Objects.nonNull(sellerConsentDetails.getSellerConsentStatus())
                        && COMPLETED.equalsIgnoreCase(sellerConsentDetails.getSellerConsentStatus().toString()));
            }
        } catch (Exception ex) {
            logger.error("error in isSellerDeclarationCompleted with msg {}", Utility.getExceptionAsString(ex));
        }
        return false;
    }

    private static boolean isSellerDeclarationFlowAvailable(String channelCode, ConfigurationRepository configurationRepository){
        try {
            List<Configuration> channelConfigurations = configurationRepository.findByKeyIgnoreCase(AppConstants.SWITCH_OFF_CHANNEL);
            Optional<Configuration> findConfigOptional = channelConfigurations.stream().filter(configuration -> configuration.getValue().equalsIgnoreCase(channelCode)).findFirst();
            logger.info("isChannel Available For sellerDeclarationflow for channel {} present {}", channelCode, findConfigOptional.isPresent());
            return !findConfigOptional.isPresent();
        }catch (Exception ex){
            logger.error("error occurred inside isSellerDeclarationFlowAvailable for channelCode {} and error {}", channelCode,Utility.getExceptionAsString(ex));
        }
        return false;
    }

    public static String getFormTypeTransformation(String formType, String objectiveOfInsurance) {
        if ((FORM_TYPE_SELF.equalsIgnoreCase(formType) && EMPLOYER_EMPLOYEE.equalsIgnoreCase(objectiveOfInsurance))) {
            formType = FORM_3;
        }
        formType = formType.toLowerCase();
        return FORM_TYPE_MAP.get(formType);
    }
    public static boolean brmsBrokerEligibility(ProposalDetails proposalDetails) {
        try {

			if (isBajajCapitalHopJourney(proposalDetails))
				return true;

            String isBrmsApplicable = AppConstants.BLANK;
            String digitalJourney = AppConstants.BLANK;
            BrmsFieldConfigurationDetails brmsFieldConfigurationDetails = Optional.ofNullable(proposalDetails)
                    .map(ProposalDetails::getBrmsFieldConfigurationDetails)
                    .orElse(null);
            if (Objects.nonNull(brmsFieldConfigurationDetails)) {
                isBrmsApplicable = Objects.nonNull(brmsFieldConfigurationDetails.getIsBRMSApiApplicable())
                        ? brmsFieldConfigurationDetails.getIsBRMSApiApplicable()
                        : AppConstants.BLANK;
                digitalJourney = Objects.nonNull(brmsFieldConfigurationDetails.getDigitalJourney())
                        ? brmsFieldConfigurationDetails.getDigitalJourney()
                        : AppConstants.BLANK;
            }
            return AppConstants.Y.equalsIgnoreCase(isBrmsApplicable) && AppConstants.CDF_JOURNEY.equalsIgnoreCase(digitalJourney)
                    && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured in brmsBrokerEligibility");
        }
        return false;
    }

    private static boolean isBajajCapitalHopJourney(ProposalDetails proposalDetails) {
		try {
			String sourceChannel = proposalDetails.getAdditionalFlags().getSourceChannel();
			String journeyType = proposalDetails.getAdditionalFlags().getJourneyType();
			String partnerSourceCode = proposalDetails.getAdditionalFlags().getPartnerSourceCode();

			logger.info("Proposal Enabling CDF  for Bajaj Capital Hop Journey sourceChannel {} and journeyType {} and partnerSourceCode {}",
					sourceChannel,journeyType,partnerSourceCode);

			if(BAJAJ_HOP_JOURNEY_SOURCE_CHANNEL.equalsIgnoreCase(sourceChannel) && JOURNEY_TYPE_BROKER.equalsIgnoreCase(journeyType)
					&& BAJAJ_HOP_JOURNEY_PARTNER_SOURCE_CODE.equalsIgnoreCase(partnerSourceCode)) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("Error occurred while checking isBajajBrokerPosSeller {}",
					Utility.getExceptionAsString(ex));
		}

		return false;
	}

    public static boolean isNullOrEmptyString(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    public static boolean isMedicalSchedulingDone(ProposalDetails proposalDetails) {
        try {

            ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
            if(productInfo.getProductId().equals(GLIP_ID) && proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus()==null) {
                return true;
            }
            logger.info("inside isMedicalSchedulingDone for transactionId {} with scheduling {} and product details {}",
                    proposalDetails.getTransactionId(),
                    proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails(),
                    productInfo);
            if ((MEDICAL_SCHEDULING_ENABLED_PRODUCT_IDS.contains(productInfo.getProductId())
                    || (SMART_WEALTH_PLAN.equalsIgnoreCase(productInfo.getProductId())
                    && WHOLE_LIFE_VARIANT.equalsIgnoreCase(productInfo.getVariant())))
                    && proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus().contains(CAT_MEDICAL_CATEGORY)
                    && proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry().equalsIgnoreCase(INDIA)
            ) {
                return MEDICAL_SCHEDULING.stream().anyMatch(s -> s.equalsIgnoreCase(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getScheduleStatus()));
            }else{
                return true;
            }
        }catch (Exception ex){
            logger.info("error in isMedicalSchedulingDone with msg {}", Utility.getExceptionAsString(ex));
        }
        return false;
    }

    public static boolean isFinancialGridPassed(UnderwritingServiceDetails underwritingServiceDetails) {
        return underwritingServiceDetails.getUnderwritingStatus() != null
                && !org.springframework.util.StringUtils.isEmpty(underwritingServiceDetails.getUnderwritingStatus().getFinancialGridStatus());
    }

    public static boolean isMedicalGridPassed(ProposalDetails proposalDetails) {
         if(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId().equals(GLIP_ID)&&proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus()==null) {
             return true;
         }
        return proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus() != null
                && !org.springframework.util.StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus());
    }
    public static String getFormattedDate(Date date) {
        try {
            DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            logger.error("Exception occurred while dateParsing, Exception: {}", Utility.getExceptionAsString(e));
            return date.toString();
        }
    }

    public static boolean checkNriParty(ProposalDetails proposalDetails,int i) {
        if (Objects.nonNull(proposalDetails) && !CollectionUtils.isEmpty(proposalDetails.getPartyInformation()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i)) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails()) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails().getNationality())) {
            NationalityDetails nationalityDetails = proposalDetails.getPartyInformation().get(i).getBasicDetails().
                    getNationalityDetails();
            return ("NRI".equalsIgnoreCase(nationalityDetails.getNationality())
                    || OTHERS.equalsIgnoreCase(nationalityDetails.getNationality())
                    || "PIO".equalsIgnoreCase(nationalityDetails.getNationality())
                    || "PIO/OCI".equalsIgnoreCase(nationalityDetails.getNationality()));
        }
        return false;
    }

    public static boolean checkDomesticParty(ProposalDetails proposalDetails, int i) {
        if (!CollectionUtils.isEmpty(proposalDetails.getPartyInformation()) && Objects.nonNull(proposalDetails.getPartyInformation().get(i))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails())
                && StringUtils.isNotEmpty(proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails().getNationality())) {
            String nationality = proposalDetails.getPartyInformation().get(i).getBasicDetails().getNationalityDetails().getNationality();
            return (INDIAN_NATIONALITY.equalsIgnoreCase(nationality));

        }

        return false;
    }


    public static boolean isPartyProposer(ProposalDetails proposalDetails) {
        if (Utility.isApplicationIsForm2(proposalDetails)) {
            return proposalDetails.getPartyInformation().size() > 1
                    && PROPOSER.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getPartyType());
        }
        return proposalDetails.getPartyInformation().size() > 1
                && PROPOSER.equalsIgnoreCase(proposalDetails.getPartyInformation().get(1).getPartyType());
    }

    public static Date stringDateFormatter(String date, String format) {
        Date formatdate = null;
        try {
            if (!org.springframework.util.StringUtils.isEmpty(date)) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                formatter.setTimeZone(TimeZone.getTimeZone(TIME_Z_ASIA));
                formatdate = formatter.parse(date);
            }
        } catch (Exception ex) {
            logger.error(ERROR_OCCURRED_WHILE_PARSING_THE_DATE,Utility.getExceptionAsString(ex));
        }
        return formatdate;
    }

    // Set Abha Number for proposer in case of Joint life
    public static String setSecondInsurerJointLifeAbhaNumberInPfForm(ProposalDetails proposalDetails) {
        String insurerAbhaNumber = BLANK;
        try {
            if (null != proposalDetails.getPartyInformation() && null != proposalDetails.getPartyInformation().get(1).getPersonalIdentification()
                    && !StringUtils.isEmpty(proposalDetails.getPartyInformation().get(1).getPersonalIdentification().getInsurerAbhaId())) {
                insurerAbhaNumber = proposalDetails.getPartyInformation().get(1).getPersonalIdentification().getInsurerAbhaId();
                logger.info("Proposer/Joint Life Abha Number for transactionId {} is {}", proposalDetails.getTransactionId(), insurerAbhaNumber);
            }
        } catch (Exception ex) {
            logger.error("Getting exception {} while setting abha number for proposer for transactionId {}", ex.getMessage(), proposalDetails.getTransactionId());
        }
        return insurerAbhaNumber;
    }

    public static String setInsurerAbhaNumberInPfForm(ProposalDetails proposalDetails) {
        String insurerAbhaNumber = BLANK;
        try {
            if (null != proposalDetails.getPartyInformation() && null != proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
                    && !StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAbhaId())) {
                insurerAbhaNumber = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getAbhaId();
                logger.info("Insurer Abha Number for transactionId {} is {}", proposalDetails.getTransactionId(), insurerAbhaNumber);
            }
        } catch (Exception ex) {
            logger.error("Getting exception {} while setting abha number for insurer for transactionId {}", ex.getMessage(), proposalDetails.getTransactionId());
        }
        return insurerAbhaNumber;
    }


    public static <T> String generateJwtToken(String tokenSecretKey, String tokenExpiry, T payload) {
        String jwtToken = "";
        Key hmacKey = new SecretKeySpec(tokenSecretKey.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());

        jwtToken = Jwts.builder()
                .claim("payload", payload)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Long.parseLong(tokenExpiry), ChronoUnit.SECONDS)))
                .signWith(hmacKey)
                .compact();

        logger.info("Token generated successfully");
        return jwtToken;
    }

    // FUL2-195747 Check joint life case for ABHA ID
    public static boolean isJointLifeCase(ProposalDetails proposalDetails) {
        boolean nullChecks = (null != proposalDetails.getProductDetails() && null != proposalDetails.getProductDetails().get(0)
                && null != proposalDetails.getProductDetails().get(0).getProductInfo());
        if( nullChecks && GLIP_PRODUCT_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())){
            return JOINT_LIFE.equals(proposalDetails.getProductDetails().get(0).getProductInfo().getAnnuityOption());
        }
        return nullChecks && !StringUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo().getIsJointLife())
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getIsJointLife());
    }
    //FUL2-196166 Persistency Tag Mapping
    public static void setAgentJoiningDt(AgentResponse agent360response){
        String reInstatementDt = Optional.ofNullable(agent360response.getResponse().getPayload()).map(Payload::getAgtMoveDtlType).map(ap->ap.get(0)).map(ap->ap.getReinstatementDt()).orElse(AppConstants.BLANK);
        String dtOfJoining =Optional.ofNullable(agent360response.getResponse().getPayload()).map(Payload::getAgtContrtDtlType).map(AgtContrtDtlType::getDtOfJoining).orElse(AppConstants.BLANK);
        if(org.springframework.util.StringUtils.hasLength(reInstatementDt))
            Stream.of(agent360response).map(AgentResponse::getResponse).map(com.mli.mpro.agent.models.Response::getPayload).map(Payload::getAgtBsDtlType).findFirst().ifPresent(ap -> ap.setUwAgentJoiningDt(reInstatementDt));
        else if(org.springframework.util.StringUtils.hasLength(dtOfJoining))
            Stream.of(agent360response).map(AgentResponse::getResponse).map(com.mli.mpro.agent.models.Response::getPayload).map(Payload::getAgtBsDtlType).findFirst().ifPresent(ap -> ap.setUwAgentJoiningDt(dtOfJoining));
        else {
            Stream.of(agent360response).map(AgentResponse::getResponse).map(com.mli.mpro.agent.models.Response::getPayload).map(Payload::getAgtBsDtlType).findFirst().ifPresent(ap -> ap.setUwAgentJoiningDt(AppConstants.DEFAULT_JOINING_DATE));
        }
        logger.info("Received value of reInstatementDt {},dtOfJoining {}",reInstatementDt,dtOfJoining);
    }


    public Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> validateJson(String inputRequest, String source) {
        JsonSchemaFactory jsonSchemaFactory = new JsonSchemaFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors=new HashSet<>();

        if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_SCHEMA_VALIDATION_FEATURE))) {
			logger.error("Location validateJson  Schema Validation feature flag is disabled");
			return errors;
		}

        try{
        JsonNode jsonNode = null;
            jsonNode = objectMapper.readTree(inputRequest);
            Set<ValidationMessage> errorSets = jsonSchemaFactory.getSchema(source).getJsonSchema().validate(jsonNode);
            Map<String, List<String>> customErrorLst = jsonSchemaFactory.getSchema(source).getErrorList();
            this.customErrorList=customErrorLst;
            errors = setErrorResponse(errorSets, customErrorList);
        } catch (JsonProcessingException | NullPointerException e) {
            logger.error("Exception occured in validateJson {}", Utility.getExceptionAsString(e));
        }
        return errors;
    }
    private static Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> setErrorResponse(Set<ValidationMessage> errorSets, Map<String, List<String>> customErrorList) {
        Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors = new HashSet<>();
        for (ValidationMessage e : errorSets) {
            try {
                com.mli.mpro.onboarding.partner.model.ErrorResponse errorResponse = new com.mli.mpro.onboarding.partner.model.ErrorResponse();
                if(customErrorList.get(e.getMessage().split(":")[0].substring(2))!=null) {
                    errorResponse.setErrorCode(customErrorList.get(e.getMessage().split(":")[0].substring(2)).get(0));
                    errorResponse.setMessage(customErrorList.get(e.getMessage().split(":")[0].substring(2)).get(1));
                    errors.add(errorResponse);
                }
            }catch(Exception ex){
                logger.error("Exception occured in setErrorResponse {}", Utility.getExceptionAsString(ex));
            }
        }
        return errors;
    }
    public void validateDate(Date dob,Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors,String errorPath){
    	if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_SCHEMA_VALIDATION_FEATURE))) {
			logger.error("Location validateDate  Schema Validation feature flag is disabled");
			return ;
		}
        boolean isInvalidDate=false;
            if (dob!=null && (new Date()).before(dob)) {
                isInvalidDate = true;
            }
            if (isInvalidDate) {
                setErrorToContext(customErrorList.get(errorPath).get(0)
                        , customErrorList.get(errorPath).get(1), errors);
            }
    }


    private void setErrorToContext(String errorCode, String errorMessage, Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors) {
        errors.add(new com.mli.mpro.onboarding.partner.model.ErrorResponse(errorCode, errorMessage));
    }

    public void validateTransactionId(long transactionId,      Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors,String txnIdErrorPath) {
    	if(Boolean.FALSE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_SCHEMA_VALIDATION_FEATURE))) {
			logger.error("Location validateTransactionId  Schema Validation feature flag is disabled");
			return ;
		}
    	if (transactionId != 0 && (transactionId < MIN_TRANSACTION_ID || transactionId > MAX_TRANSACTION_ID)) {
            setErrorToContext(customErrorList.get(txnIdErrorPath).get(0)
                    , customErrorList.get(txnIdErrorPath).get(1), errors);
        }
    }

    public static boolean isOpenDiyJourney(ProposalDetails proposalDetails) {
    	String journeyType = null;
    	DiyBrmsFieldConfigurationDetails diyBrmsFieldConfigurationDetails = null;
		if (Objects.nonNull(proposalDetails)) {
			diyBrmsFieldConfigurationDetails = proposalDetails.getDiyBrmsFieldConfigurationDetails();
			if (Objects.nonNull(diyBrmsFieldConfigurationDetails) && Objects.nonNull(diyBrmsFieldConfigurationDetails.getUtmBasedLogic())) {
				journeyType = diyBrmsFieldConfigurationDetails.getUtmBasedLogic().getJourneyType();
			}
		}

		return journeyType != null && !journeyType.isEmpty();
    }

    public static RequestResponse encryptRequest(String response) {
    RequestResponse encResponse = new RequestResponse();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
        String payload = EncryptionDecryptionOnboardingUtil.encrypt(response,
                java.util.Base64.getDecoder().decode(key));
        encResponse.setPayload(payload);
    } catch (Exception e) {
        logger.error("Exception while encrypting: {}", Utility.getExceptionAsString(e));
    }
    return encResponse;
}
public static String getNewOauthAccessToken(String oauthTokenUrl,String  authClientId,String authClientSecret,String dataLakeApiId,String dataLakeApiKey,String transactionId) {
    logger.info("inside token generation for transactionId{}", transactionId);
    String token = AppConstants.BLANK;
    try{
    InputRequest inputRequest = new InputRequest();
    com.mli.mpro.agentSelf.DataLake.OutputResponse outputResponse = new com.mli.mpro.agentSelf.DataLake.OutputResponse();
    TokenRequest tokenRequest = new TokenRequest();
    TokenRequestHeader tokenHeader = new TokenRequestHeader();
    com.mli.mpro.agentSelf.DataLake.TokenRequestPayload tokenRequestPayload = new com.mli.mpro.agentSelf.DataLake.TokenRequestPayload();
    tokenHeader.setAppId(FULFILLMENT);
    tokenHeader.setCorrelationId(CORRELATIONID);
    tokenRequestPayload.setClientId(authClientId);
    tokenRequestPayload.setClientSecret(authClientSecret);
    tokenRequest.setTokenRequestHeader(tokenHeader);
    tokenRequest.setTokenRequestPayload(tokenRequestPayload);
    inputRequest.setTokenRequest(tokenRequest);
    HttpHeaders headers = new HttpHeaders();
    headers.add(X_APIGW_API_ID, dataLakeApiId);
    headers.add(X_API_KEY, dataLakeApiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> httpEntity = new HttpEntity<>(inputRequest,headers);
    outputResponse = new RestTemplate().postForObject(oauthTokenUrl, httpEntity, com.mli.mpro.agentSelf.DataLake.OutputResponse.class);
    logger.info("Oauth Token response received with HttpsEntity{} & url {}for transactionId{} is {}", httpEntity, oauthTokenUrl, transactionId, outputResponse);
    boolean tokenFlag = Optional.ofNullable(outputResponse).map(com.mli.mpro.agentSelf.DataLake.OutputResponse::getTokenResponse).map(TokenResponse::getResponsePayload).map(ResponsePayload::getAccessToken).isPresent();
    if (tokenFlag) {
        token = outputResponse.getTokenResponse().getResponsePayload().getAccessToken();
    }
}catch(Exception e){
        logger.info("error occurred while setting token for transactionId{}-{}",transactionId,e.toString());
        e.printStackTrace();
    }
 return token;
}
    //FUL2-202646 Go Green PF
    public static String finalStageNationalityCheck(ProposalDetails proposalDetails){
        ConfigurationRepository configurationRepository= BeanUtil.getBean(ConfigurationRepository.class);
        String formType = proposalDetails.getApplicationDetails().getFormType();
        boolean finalPfStage = false;
        try {
            String stage = Optional.ofNullable(proposalDetails).map(ProposalDetails::getApplicationDetails).map(ApplicationDetails::getStage).orElse(BLANK);
            logger.info("stage for the proposal form with transactionId {} is {}", proposalDetails.getTransactionId(), stage);
            if(thanosStage(proposalDetails,stage) || STAGE_SIX.equalsIgnoreCase(stage) || STAGE_SEVEN.equalsIgnoreCase(stage)) {
                finalPfStage=true;
            }
            boolean yblteleSales = Optional.ofNullable(proposalDetails).map(ProposalDetails::getAdditionalFlags).map(AdditionalFlags::isYblTelesalesCase).orElse(false);
            boolean isNri= checkNRIstatus(formType, proposalDetails);
            boolean isFormc= (isForm3(proposalDetails));
            boolean isPosvEligible = AppConstants.YES.equalsIgnoreCase(proposalDetails.getApplicationDetails().getPhysicalJourneyEnabled())
                                             ? isPosvEligibleForPhysicalJourney(proposalDetails,configurationRepository)
                                             : isPosvEligibleForDigitalJourney(proposalDetails, configurationRepository);
            if(finalPfStage && !isNri && !isFormc && isPosvEligible && !yblteleSales ) {
                return YES;
            }
            logger.info("final value of finalPfStage {} , isNri{} ,isFormc{} and isPosvEligible{}  for transactionId{}", finalPfStage, isNri,isFormc, isPosvEligible,proposalDetails.getTransactionId());
        }catch(Exception e){
            logger.info("Exception occurred while performing final stage check for transactionId{} -{}", proposalDetails.getTransactionId(), e.toString());
        }
        return NO;

    }

    private static boolean thanosStage(ProposalDetails proposalDetails, String stage) {
        return THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) &&
                STAGE_FIVE.equalsIgnoreCase(stage) && StringUtils.isNotEmpty(proposalDetails.getPosvDetails().getGoGreen());

    }

    public static  boolean checkNRIstatus(String formType, ProposalDetails proposalDetails) {
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        boolean isNRI = false;
        try {
            if ((formType.equalsIgnoreCase(AppConstants.DEPENDENT) || (formType.equalsIgnoreCase(AppConstants.FORM3) && !Utility.schemeBCase(schemeType)))
                    && StringUtils.isNotEmpty(proposalDetails.getPartyInformation().get(1).getBasicDetails()
                    .getNationalityDetails().getNationality())
                    && proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails()
                    .getNationality().equalsIgnoreCase(NRI)) {
                logger.info("Inside form2 condition of nri for transactionId{}",proposalDetails.getTransactionId());
                isNRI = true;
            } else if ((formType.equalsIgnoreCase(AppConstants.SELF) || Utility.schemeBCase(formType, schemeType))
                    && StringUtils.isNotEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails()
                    .getNationalityDetails().getNationality())
                    && proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails()
                    .getNationality().equalsIgnoreCase(NRI)) {
                logger.info("Inside form1 condition of nri for transactionId{}",proposalDetails.getTransactionId());
                isNRI = true;
            } else {
                logger.info("Inside else condition of nri for transactionId{}",proposalDetails.getTransactionId());
                isNRI = false;
            }
        }catch(Exception e){
            logger.info("Exception occured while checking nri status for transactionId{} -{}",proposalDetails.getTransactionId(),e.toString());
        }
        return isNRI;
    }
    public static boolean isPosvEligibleForDiyJourney(ProposalDetails proposalDetails){
        try {
            if (Objects.nonNull(proposalDetails.getDiyBrmsFieldConfigurationDetails())
                    && Objects.nonNull(proposalDetails.getDiyBrmsFieldConfigurationDetails().getUtmBasedLogic())
                    && N.equalsIgnoreCase(proposalDetails.getDiyBrmsFieldConfigurationDetails().getUtmBasedLogic().getPosvApplicability())) {
                return false;
            }
        }catch(Exception e){
            logger.info("Error Occured while finding eligibility for transactionId{}",proposalDetails.getTransactionId());
        }
        return true;
    }
    public static boolean isBajajBrokerPosSeller(ProposalDetails proposalDetails) {
        try {
            String channel = proposalDetails.getChannelDetails().getChannel();
            boolean isPosSeller = proposalDetails.getSourcingDetails().isPosSeller();
            String goCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();

            if(isPosSeller && CHANNEL_BROKER.equalsIgnoreCase(channel) && goCode.startsWith(BAJAJ_GO_CODE)) {
                return true;
            }
        } catch (Exception ex) {
            logger.error("Error occurred while checking isBajajBrokerPosSeller {}",
                    Utility.getExceptionAsString(ex));
        }
        return false;
    }
    public static boolean isPosvEligibleForDigitalJourney(ProposalDetails proposalDetails,
                                                          ConfigurationRepository configurationRepository) {
        String goCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
        logger.info("Inside posveligibility for digital with transactionId{}",proposalDetails.getTransactionId());
        try {
            if(!org.springframework.util.StringUtils.hasLength(goCode)) {
                return true;
            }
            Configuration configuration = configurationRepository.findByType("posvSuppressionChannelConfig");
            List<String> configuredGoCodes = configuration.getGoCode();
            Optional<String> findConfigOptional = configuredGoCodes.stream()
                    .filter(goCode::startsWith).findFirst();

            logger.info("goCode Available For posvSuppressionChannelConfig for goCode {} present {}", goCode,
                    findConfigOptional.isPresent());
            logger.info("brmsEligibility is {} and feature flag is {} for transactionId{}",Utility.brmsBrokerEligibility(proposalDetails),
                    Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_BRMS_BROKER)),proposalDetails.getTransactionId());
            // If go code is present in config, posv should not be applicable
            return !(findConfigOptional.isPresent()||Utility.brmsBrokerEligibility(proposalDetails) || Utility.isBajajBrokerPosSeller(proposalDetails) || !isPosvEligibleForDiyJourney(proposalDetails));

        } catch (Exception e) {
            logger.error("Error occurred while checking isPosvEligibleForPhysicalJourney {}",
                    Utility.getExceptionAsString(e));
        }
        return true;
    }
    public static boolean isPosvEligibleForPhysicalJourney(ProposalDetails proposalDetails,
                                                           ConfigurationRepository configurationRepository) {
        String goCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
        String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
        String channel = proposalDetails.getChannelDetails( ).getChannel();
        logger.info("Inside posveligibility for physical with transactionId{}",proposalDetails.getTransactionId());
        try {
            if (org.springframework.util.StringUtils.isEmpty(goCode) || AppConstants.NO
                    .equalsIgnoreCase(proposalDetails.getApplicationDetails().getPhysicalJourneyEnabled())) {
                return true;
            }

            Configuration configuration = configurationRepository.findByType("physicalJourneyPosvConfig");
            List<String> configuredGoCodes = configuration.getGoCode();
            List<String> configuredProductIds = configuration.getProductId();
            Optional<String> findConfigOptionalGoCode = configuredGoCodes.stream()
                    .filter(goCode::startsWith).findFirst();
            boolean isProductPresentInConfig = configuredProductIds.contains(productId);
            logger.info("goCode and product Available For isPosvEligibleForPhysicalJourney for goCode {} product{} present {} {}", goCode,
                    productId, findConfigOptionalGoCode.isPresent(), isProductPresentInConfig);
            /*FUL2-159570 If product is SGPP, posv should be suppressed for all channels except Group business
             * Group business Identifier : Channel J and GoCode starts with ZH
             */
            if(configuredProductIds.contains(AppConstants.SGPP) && AppConstants.SGPP.equalsIgnoreCase(productId)
                    && AppConstants.CHANNEL_BROKER.equalsIgnoreCase(channel) && goCode.startsWith(AppConstants.ZH)) {
                isProductPresentInConfig = false;
            }
            //If go code is present in config posv should not be applicable
            return !findConfigOptionalGoCode.isPresent() && !isProductPresentInConfig;

        } catch (Exception e) {
            logger.error("Error occurred while checking isPosvEligibleForPhysicalJourney {}", Utility.getExceptionAsString(e));
        }
        return true;
    }

    public static void clearWithPrefixFromRedisCache(String keyPrefix, RedisTemplate redisTemplate, int redisScanCount) {
        try {
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_REDIS_SCAN))) {
                logger.info("clearing the key {} usingScan method", keyPrefix);
                RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
                if (connectionFactory != null) {
                    List<String> newApplicationKeys = new ArrayList<>();
                    newApplicationKeys.add(keyPrefix + AppConstants.NEWAPPLICATION);
                    newApplicationKeys.add(keyPrefix + AppConstants.NEWAPPLICATION_HITCOUNT);
                    redisTemplate.delete(newApplicationKeys);
                    ScanOptions options = ScanOptions.scanOptions().match(keyPrefix + "_" + "*").count(redisScanCount).build();
                    RedisConnection connection = connectionFactory.getConnection();
                    Cursor<byte[]> cursor = connection.scan(options);
                    while (cursor.hasNext()) {
                        byte[] keyBytes = cursor.next();
                        String key = new String(keyBytes);
                        redisTemplate.delete(key);
                    }
                    cursor.close();
                }
            } else {
                deleteRedisDetailsByPreFixKeys(keyPrefix, redisTemplate);
            }

        } catch (Exception e) {
            logger.error("Error occurred while clearing redis cache with prefix {} - {}", keyPrefix, Utility.getExceptionAsString(e));
        }
    }

    public static void deleteRedisDetailsByPreFixKeys(String keyPrefix, RedisTemplate redisTemplate) {
        Set<String> keys = redisTemplate.opsForValue().getOperations().keys(keyPrefix + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    public static void setDataForVernacularAndDisabilityDeclaration(ProposalDetails proposalDetails,
			Map<String, Object> dataVariables) {

		AdditionalFlags additionalFlags = proposalDetails != null && proposalDetails.getAdditionalFlags() != null
				? proposalDetails.getAdditionalFlags()
				: null;
		VernacularDeclaration vernacularDeclaration = (additionalFlags != null
				&& additionalFlags.getVernacularDeclaration() != null) ? additionalFlags.getVernacularDeclaration()
						: null;
		DisabilityDeclaration disabilityDeclaration = (additionalFlags != null
				&& additionalFlags.getDisabilityDeclaration() != null) ? additionalFlags.getDisabilityDeclaration()
						: null;
		ApplicationDetails applicationDetails = proposalDetails != null && proposalDetails.getAdditionalFlags() != null
				? proposalDetails.getApplicationDetails()
				: null;
		String formType = applicationDetails != null && applicationDetails.getFormType() != null
				? applicationDetails.getFormType()
				: AppConstants.BLANK;
		String schemeType = applicationDetails != null && applicationDetails.getFormType() != null
				? applicationDetails.getSchemeType()
				: AppConstants.BLANK;
		PartyInformation partyInformation = (AppConstants.FORM3.equalsIgnoreCase(formType)
				&& !Utility.schemeBCase(schemeType))
						? proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
								.filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()))
								.findFirst().orElse(null)
						: (proposalDetails.getPartyInformation() != null
								&& !proposalDetails.getPartyInformation().isEmpty()
								&& proposalDetails.getPartyInformation().get(0) != null
										? proposalDetails.getPartyInformation().get(0)
										: null);
		BasicDetails basicDetails = partyInformation != null && partyInformation.getBasicDetails() != null
				? partyInformation.getBasicDetails()
				: null;
		PosvDetails posvDetails = proposalDetails != null ? proposalDetails.getPosvDetails() : null;
		PosvStatus posvStatus = posvDetails != null && posvDetails.getPosvStatus() != null ? posvDetails.getPosvStatus()
				: null;
		boolean visibleDisabilityDeclaration = true;
		String disabilityDeclarationFlag = disabilityDeclaration != null
				&& disabilityDeclaration.getDisabilityDeclarationFlag() != null
						? disabilityDeclaration.getDisabilityDeclarationFlag()
						: AppConstants.CAMEL_NO;
		String vernacularAccepted = vernacularDeclaration != null
				&& vernacularDeclaration.getVernacularAccepted() != null ? vernacularDeclaration.getVernacularAccepted()
						: AppConstants.CAMEL_NO;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		String policySubmittedDate = (applicationDetails != null && applicationDetails.getUpdatedTime() != null
				&& AppConstants.STAGE_SIX.equalsIgnoreCase(applicationDetails.getStage()))
						? format.format(applicationDetails.getUpdatedTime())
						: AppConstants.BLANK;
		String posvOtpSubmittedDate = posvStatus != null && posvStatus.getSubmittedOTPDate() != null
				? format.format(posvStatus.getSubmittedOTPDate())
				: AppConstants.BLANK;
		String disabilityDeclarantProposalSignature = posvOtpSubmittedDate;
		String preferredLanguage = getPreferredLanguage(basicDetails, posvDetails, applicationDetails);
		String vernacularDeclarantName = vernacularDeclaration != null
				? (vernacularDeclaration.getVernacularFirstName() != null
						? vernacularDeclaration.getVernacularFirstName() + AppConstants.SPACE
						: AppConstants.BLANK)
						+ (vernacularDeclaration.getVernacularLastName() != null
								? vernacularDeclaration.getVernacularLastName()
								: AppConstants.BLANK)
				: AppConstants.BLANK;
		String vernacularAddress = vernacularDeclaration != null && vernacularDeclaration.getVernacularAddress() != null
				? vernacularDeclaration.getVernacularAddress()
				: AppConstants.BLANK;
		String disabilityDeclarationName = disabilityDeclaration != null
				? (disabilityDeclaration.getDeclarantFirstName() != null
						? disabilityDeclaration.getDeclarantFirstName() + AppConstants.SPACE
						: AppConstants.BLANK)
						+ (disabilityDeclaration.getDeclarantLastName() != null
								? disabilityDeclaration.getDeclarantLastName()
								: AppConstants.BLANK)
				: AppConstants.BLANK;
		String disabilityDeclarantAddress = disabilityDeclaration != null
				&& disabilityDeclaration.getDeclarantAddress() != null ? disabilityDeclaration.getDeclarantAddress()
						: AppConstants.BLANK;
		String relationshipWithProposer = determineRelationship(disabilityDeclaration);
		String disabilityDeclarantcontactNumber = disabilityDeclaration != null
				&& disabilityDeclaration.getDeclarantMobileNumber() != null
						? disabilityDeclaration.getDeclarantMobileNumber()
						: AppConstants.BLANK;
		String disabilityDeclarantOtpSubmittedDate = disabilityDeclaration != null
				&& disabilityDeclaration.getDeclarantOtpSubmittedDate() != null
						? format.format(disabilityDeclaration.getDeclarantOtpSubmittedDate())
						: AppConstants.BLANK;
		dataVariables.put("visibleDisabilityDeclaration", visibleDisabilityDeclaration);
		dataVariables.put("vernacularDeclarantName",
				AppConstants.CAMEL_YES.equalsIgnoreCase(vernacularAccepted) ? vernacularDeclarantName
						: AppConstants.BLANK);
		dataVariables.put("vernacularAddress",
				AppConstants.CAMEL_YES.equalsIgnoreCase(vernacularAccepted) ? vernacularAddress : AppConstants.BLANK);
		dataVariables.put("policySubmittedDate",
				AppConstants.CAMEL_YES.equalsIgnoreCase(vernacularAccepted) ? policySubmittedDate : AppConstants.BLANK);
		dataVariables.put("preferredLanguage",
				AppConstants.CAMEL_YES.equalsIgnoreCase(vernacularAccepted) ? preferredLanguage : AppConstants.BLANK);
		dataVariables.put("posvOtpSubmittedDate",
				AppConstants.CAMEL_YES.equalsIgnoreCase(vernacularAccepted) ? posvOtpSubmittedDate
						: AppConstants.BLANK);
		dataVariables.put("disabilityDeclarationName",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag) ? disabilityDeclarationName
						: AppConstants.BLANK);
		dataVariables.put("disabilityDeclarantAddress",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag) ? disabilityDeclarantAddress
						: AppConstants.BLANK);
		dataVariables.put("disabilityDeclarantcontactNumber",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag) ? disabilityDeclarantcontactNumber
						: AppConstants.BLANK);
		dataVariables.put("disabilityDeclarantOtpSubmittedDate",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag) ? disabilityDeclarantOtpSubmittedDate
						: AppConstants.BLANK);
		dataVariables.put("relationshipWithProposer",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag) ? relationshipWithProposer
						: AppConstants.BLANK);
		dataVariables.put("disabilityDeclarantProposalSignature",
				AppConstants.CAMEL_YES.equalsIgnoreCase(disabilityDeclarationFlag)
						? disabilityDeclarantProposalSignature
						: AppConstants.BLANK);

	}

	public static String determineRelationship(DisabilityDeclaration disabilityDeclaration) {
		String relationshipWithProposer;
		relationshipWithProposer = disabilityDeclaration != null
				&& disabilityDeclaration.getDeclarantRelationshipWithProposer() != null
						? disabilityDeclaration.getDeclarantRelationshipWithProposer()
						: AppConstants.BLANK;
		if (AppConstants.DECLARANT_OTHERS.equalsIgnoreCase(relationshipWithProposer)) {
			relationshipWithProposer = disabilityDeclaration.getDeclarantRelationshipWithOthers() != null
					? disabilityDeclaration.getDeclarantRelationshipWithOthers()
					: AppConstants.BLANK;
		}
		return relationshipWithProposer;
	}

	public static String getPreferredLanguage(BasicDetails basicDetails, PosvDetails posvDetails,
			ApplicationDetails applicationDetails) {
		if (applicationDetails != null && AppConstants.STAGE_SIX.equalsIgnoreCase(applicationDetails.getStage())) {
			if (posvDetails != null && posvDetails.getSelectedLanguage() != null
					&& !StringUtils.isEmpty(posvDetails.getSelectedLanguage())) {
				return posvDetails.getSelectedLanguage();
			}
			if (basicDetails != null && basicDetails.getPreferredLanguageOfCommunication() != null) {
				return basicDetails.getPreferredLanguageOfCommunication();
			}
		}
		return AppConstants.BLANK;
	}

	public static String customerReminderConsent(ProposalDetails proposalDetails) {
		String customerReminderConsent = AppConstants.BLANK;
		if (proposalDetails.getProductDetails() != null && !CollectionUtils.isEmpty(proposalDetails.getProductDetails())
				&& proposalDetails.getProductDetails().get(0).getProductInfo() != null && !StringUtils.isEmpty(
						proposalDetails.getProductDetails().get(0).getProductInfo().getCustomerReminderConsent())) {
			customerReminderConsent = proposalDetails.getProductDetails().get(0).getProductInfo()
					.getCustomerReminderConsent().toUpperCase();
		}
		return customerReminderConsent;
	}

    /**
     * @param configuration
     * @param uIOutputResponse
     * @return
     * @implNote This method is used to provide response based on configuration for enabling/disabling disability and vernacular declaration on 4th and 6th page of UI
     * @implSpec FUL2-208757
     */
    public static UIOutputResponse checkDeclarationApplability(String key, UIConfiguration configuration, String productId, UIOutputResponse uIOutputResponse) {
        HashMap output = new HashMap();
        switch (key) {
            case "disability":
                if (!configuration.getDisabilityProducts().isEmpty() && configuration.getDisabilityProducts().contains(productId)) {
                    output.put(key, CAMEL_YES);
                    uIOutputResponse.setResponse(output);
                } else {
                    output.put(key, CAMEL_NO);
                    uIOutputResponse.setResponse(output);
                }
                break;
            case "vernacular":
                if (!configuration.getVernacularProducts().isEmpty() && configuration.getVernacularProducts().contains(productId)) {
                    output.put(key, CAMEL_YES);
                    uIOutputResponse.setResponse(output);
                } else {
                    output.put(key, CAMEL_NO);
                    uIOutputResponse.setResponse(output);
                }
                break;
            case "cis":
                if (!configuration.getCisProducts().isEmpty() && configuration.getCisProducts().contains(productId)) {
                    output.put(key, CAMEL_YES);
                    uIOutputResponse.setResponse(output);
                } else {
                    output.put(key, CAMEL_NO);
                    uIOutputResponse.setResponse(output);
                }
                break;
            default:
                output.put(key, CAMEL_NO);
                uIOutputResponse.setResponse(output);
        }
        return uIOutputResponse;
    }
    public static String getGenerateCISOrNot(ProposalDetails proposalDetails) {
        if (proposalDetails != null && proposalDetails.getAdditionalFlags() != null && proposalDetails.getAdditionalFlags().getIsCISApplicable()!=null) {
            return proposalDetails.getAdditionalFlags().getIsCISApplicable();
        }
        return CAMEL_NO;
    }

    public static String getClientIdFromToken(MultiValueMap<String, String> headers) {


        String clientId = null;

        String authorizationToken = headers.getFirst(AppConstants.AUTH.toLowerCase());

        logger.info("Fetching clientId from aut token authorizationToken {} headers {} " , authorizationToken, headers);
        if (Objects.nonNull(authorizationToken))
            clientId = getSecrete(authorizationToken);


        return clientId;
    }

    public static String getSecrete(String authorizationToken) {
        String clientId  = null;
        try {
            String[] jwtParts = authorizationToken.split("\\.");
            String payload = new String(java.util.Base64.getUrlDecoder().decode(jwtParts[1]));
            JsonObject jsonObject = new Gson().fromJson(payload, JsonObject.class);
            JsonElement payloadElement = jsonObject.get(AppConstants.CLIENTID);
            clientId = payloadElement.getAsString();
            logger.info("set secret successfully");
        } catch (Exception e) {
            logger.error("exception while getting jwt token paylaod {}", Utility.getExceptionAsString(e));
        }
        return clientId;
    }

    public static String randomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789=+/";
        return RandomStringUtils.random(length, characters);
    }

    public static String convertToYesorNoWithDefaultYes(String questionAnswer) {
        if (Objects.isNull(questionAnswer) || StringUtils.isEmpty(questionAnswer)) {
            return AppConstants.YES;
        }else if (AppConstants.NEO_YES.equalsIgnoreCase(questionAnswer) || "1".equalsIgnoreCase(questionAnswer)) {
            return AppConstants.YES;
        } else if (AppConstants.NEO_NO.equalsIgnoreCase(questionAnswer) || "2".equalsIgnoreCase(questionAnswer)) {
            return AppConstants.NO;
        } else {
            return YES;
        }
    }
    public static String determineNumberOfDays(String premiumPaymentMode) {
        if(AppConstants.MONTHLY.equalsIgnoreCase(premiumPaymentMode)){
            return AppConstants.GRACE_PERIOD_MONTHLY;
        } else if (!AppConstants.BLANK.equalsIgnoreCase(premiumPaymentMode) && !AppConstants.SINGLE.equalsIgnoreCase(premiumPaymentMode)) {
            return AppConstants.GRACE_PERIOD;
        } else {
            return AppConstants.NA;
        }
    }
    public static boolean isWopRider(String rider) {
        return WOP_RIDER.equalsIgnoreCase(rider);
    }

    public static String evaluateFiledValue(String fieldValue){
        return (fieldValue==null)? BLANK:fieldValue;

    }

    public static Date stringToDate(String stringDate){
        if(org.springframework.util.StringUtils.isEmpty(stringDate))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            logger.error("Exception while converting stringToDate: {}"
                    , Utility.getExceptionAsString(e));
        }
        return date;

    }
    public static void safeSleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            logger.error("InterruptedException exception occurred {}", getExceptionAsString(e));
        }
    }
    public static boolean isCIDSRAnnexureVisibleOrNot(String rider) {
        return StringUtils.isNotBlank(rider) && (CIDS_RIDER.equalsIgnoreCase(rider)||CID_RIDER.equalsIgnoreCase(rider));
    }

    public static String getUIN(ProductIllustrationResponse productIllustrationResponse, String riderName) {
        String uin = AppConstants.BLANK;
        RiderUIN riderUIN = (productIllustrationResponse != null && productIllustrationResponse.getRiderUIN() != null)
                ? productIllustrationResponse.getRiderUIN()
                : null;
        if (riderName == null || riderName.isEmpty() || riderUIN == null) {
            return uin;
        }
        switch (riderName) {
            case AppConstants.TERM_RIDER_NAME:
                return riderUIN.getTermPlusRiderUIN() != null ? riderUIN.getTermPlusRiderUIN() : "";

            case AppConstants.ADD_RIDER_NAME:
                return riderUIN.getAddRiderUIN() != null ? riderUIN.getAddRiderUIN() : "";

            case AppConstants.WOP_RIDER_NAME:
                return riderUIN.getWopPlusRiderUIN() != null ? riderUIN.getWopPlusRiderUIN() : "";

            case AppConstants.AXIS_SUPR_RIDER_NAME:
                return riderUIN.getSuprRiderUIN()!=null ? riderUIN.getSuprRiderUIN():"";

            default:
                return AppConstants.BLANK;
        }
    }

    public static void iibDetailsAdder(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        try {
            String schemeAvailable = Optional.ofNullable(proposalDetails.getApplicationDetails().getSchemeType()).orElse("");
            String schemeAorB = SCHEME_A.equalsIgnoreCase(schemeAvailable) ? SCHEME_A : SCHEME_B.equalsIgnoreCase(schemeAvailable) ? SCHEME_B : "";
            IIBResponseDetails iibResponseDetails = proposalDetails.getUnderwritingServiceDetails().getIibResDetails();
            String iibFormAndPartyType = Utility.iibFormAndPartyType(proposalDetails, iibResponseDetails);
            dataVariables.put("iibFormAndPartyType", iibFormAndPartyType);
            List<IibDataMatch> proposerData = (org.springframework.util.StringUtils.hasText(iibFormAndPartyType) || JOINT_PROPOSER.equalsIgnoreCase(iibFormAndPartyType))
                    ? getIibDataMatch(iibResponseDetails, false) : Collections.emptyList();
            List<IibDataMatch> insurerData = (JOINT.equalsIgnoreCase(iibFormAndPartyType) || JOINT_INSURED.equalsIgnoreCase(iibFormAndPartyType))
                    ? getIibDataMatch(iibResponseDetails, true) : Collections.emptyList();
            dataVariables.put("iibDataMatches", proposerData);
            dataVariables.put("jointLifeIibDataMatches", insurerData);
            dataVariables.put("schemeAorB", schemeAorB);
        } catch (Exception e) {
            logger.error("Error Occurred in iibDetailsAdder Method for Txn Id {} with Exception -> {}", proposalDetails.getTransactionId(), e.getMessage());
        }
    }

    private static List<IibDataMatch> getIibDataMatch(IIBResponseDetails iibResponseDetails, boolean getJointLifeData) {
        try {
            return Optional.ofNullable(iibResponseDetails)
                    .map(details -> {
                        if (getJointLifeData && "200".equalsIgnoreCase(details.getJointLifeInsuredPayload().getStatusCode())) {
                            return details.getJointLifeInsuredPayload().getIibResponse();
                        } else if ("200".equalsIgnoreCase(details.getStatusCode())) {
                            return details.getIibResponse();
                        } else {
                            return null;
                        }
                    })
                    .map(iibPayload -> iibPayload != null ? iibPayload.getIibDataMatch() : Collections.<IibDataMatch>emptyList())
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            logger.error("Error occurred while getting IibDataMatch: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    public static String iibFormAndPartyType(ProposalDetails proposalDetails, IIBResponseDetails iibResponseDetails) {
        String iibFormAndPartyType = "";
        try {
            if (ObjectUtils.isEmpty(iibResponseDetails)) {
                return iibFormAndPartyType;
            }
            boolean isJointLife = YES.equalsIgnoreCase(Optional.ofNullable(proposalDetails.getProductDetails().get(0).getProductInfo().getIsJointLife()).orElse(""));
            String formType = Optional.ofNullable(proposalDetails.getApplicationDetails().getFormType()).orElse("");
            boolean jointLifeIibDataPresent = !ObjectUtils.isEmpty(iibResponseDetails.getJointLifeInsuredPayload())
                    && "200".equalsIgnoreCase(Optional.ofNullable(iibResponseDetails.getJointLifeInsuredPayload().getStatusCode()).orElse(""));
            boolean nonJointLifeIibDataPresent = "200".equalsIgnoreCase(Optional.ofNullable(iibResponseDetails.getStatusCode()).orElse(""));
            if (isJointLife) {
                if (jointLifeIibDataPresent && nonJointLifeIibDataPresent) {
                    iibFormAndPartyType = JOINT;
                } else if (!jointLifeIibDataPresent && nonJointLifeIibDataPresent) {
                    iibFormAndPartyType = JOINT_PROPOSER;
                } else if (jointLifeIibDataPresent) {
                    iibFormAndPartyType = JOINT_INSURED;
                }
            } else {
                iibFormAndPartyType = isSelfOrDependentOrFormC(formType);
            }
        } catch (Exception e) {
            logger.error("Error Occurred in iibFormAndPartyType Method for Txn Id {} with Exception -> {}", proposalDetails.getTransactionId(), e.getMessage());
        }
        logger.info("iibFormAndPartyType for TransactionId {} is -> {}", proposalDetails.getTransactionId(), iibFormAndPartyType);
        return iibFormAndPartyType;
    }

    private static String isSelfOrDependentOrFormC(String formType) {
        return FORM_TYPE_SELF.equalsIgnoreCase(formType) ? FORM_TYPE_SELF : DEPENDENT.equalsIgnoreCase(formType) ? DEPENDENT : FORM3.equalsIgnoreCase(formType) ? FORM3 : "";
    }

    public static boolean isAnnexureVisibleForSewa(String productId){
        return AppConstants.SEWA.equalsIgnoreCase(productId);
    }

    public static String replaceEncodedAmpersand(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("%26", "&");
    }

    public static void setInsuredContactDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        try {
            logger.info("Mapping insured contact details of proposal form document for transactionId {}",
                    proposalDetails.getTransactionId());
            dataVariables.put("isInsurerMajor", org.springframework.util.StringUtils.hasText(proposalDetails.getAdditionalFlags().getIsInsurerMajor()) ? proposalDetails.getAdditionalFlags().getIsInsurerMajor().toUpperCase() : "");
            if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsInsurerMajor())) {
                Optional<PartyInformation> insuredPartyType = proposalDetails.getPartyInformation().stream().filter(partyInformation ->
                        AppConstants.INSURED.equalsIgnoreCase(partyInformation.getPartyType())).findFirst();
                if (insuredPartyType.isPresent() &&  Objects.nonNull(insuredPartyType.get().getPersonalIdentification())
                        && insuredPartyType.get().getPersonalIdentification().getPhone().size() > 0) {
                    String insuredEmail = insuredPartyType.get().getBasicDetails().getPersonalIdentification().getEmail();
                    String insuredMobile = insuredPartyType.get().getBasicDetails().getPersonalIdentification().getPhone().get(0).getPhoneNumber();
                    dataVariables.put("insuredEmail", org.springframework.util.StringUtils.hasText(insuredEmail) ? insuredEmail : "");
                    dataVariables.put("insuredMobileNumber", org.springframework.util.StringUtils.hasText(insuredMobile) ? insuredMobile : "");
                }
            }
            logger.info("document for transactionId {} isInsurerMajor: {} insuredEmail: {} insuredMobileNumber: {}", proposalDetails.getTransactionId() , proposalDetails.getAdditionalFlags().getIsInsurerMajor() , dataVariables.get("insuredEmail") , dataVariables.get("insuredMobileNumber"));
        } catch (Exception ex) {
            logger.error("Error occurred while setting insured contact details for transactionId {} : {}", proposalDetails.getTransactionId(), Utility.getExceptionAsString(ex));
        }
    }
    public static boolean isNFOPeriod(LocalDate currentDate, LocalDate startDate, LocalDate endDate) {
        return (currentDate.isAfter(startDate) || currentDate.isEqual(startDate)) &&
                (currentDate.isBefore(endDate) || currentDate.isEqual(endDate));
    }
    public static boolean isAnnexureBVisibleForSTPP(String productId){
        return AppConstants.STPP_PRODUCT_ID.equalsIgnoreCase(productId);
    }

    public static boolean checkInfluencerChannelCode(String influencerChannel) {
        return Objects.nonNull(influencerChannel) &&
                (InfluencerChannelList.getInstance().getInfluencerChannelCodeList().contains(influencerChannel));
    }
    public static boolean isNotSTARProduct(String productId) {
        return !STAR_PRODUCT_ID.equalsIgnoreCase(productId);
    }

    public static Parameter getParameterValues(String parameterName) {
        AWSSimpleSystemsManagement ssmClient = AWSSimpleSystemsManagementClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
        GetParameterRequest parameterRequest = new GetParameterRequest() .withName(parameterName).withWithDecryption(true);
        GetParameterResult parameterResult = ssmClient.getParameter(parameterRequest);
        Parameter parameter = parameterResult.getParameter();
        logger.info("inside getParameterValues parameterValue : {}", parameter.getValue());
        return parameterResult.getParameter();
    }

    public static void updateNomineeAddressDetails(PartyDetails partyDetails, List<NomineeAppointeePfDetails> pfDetails, int i) {
        if (!CollectionUtils.isEmpty(partyDetails.getNomineeAddress())) {
            for (Address address : partyDetails.getNomineeAddress()) {
                if (Objects.nonNull(address) && org.springframework.util.StringUtils.hasLength(address.getAddressType())) {
                    if ("CRA".equalsIgnoreCase(address.getAddressType())) {
                        PfCommunicationAddress pfAddress = pfDetails.get(i).getNomineePfDetails().getCommunicationAddress();
                        updatePfAddress(pfAddress, address);
                    } else if ("PRA".equalsIgnoreCase(address.getAddressType())) {
                        PfPermanentAddress pfAddress = pfDetails.get(i).getNomineePfDetails().getPermanentAddress();
                        updatePfAddress(pfAddress, address);
                    }
                }
            }
        }
    }

    public static void updateAppointeeAddressDetails(PartyDetails partyDetails, List<NomineeAppointeePfDetails> pfDetails, int i) {
        if (Objects.nonNull(partyDetails.getAppointeeDetails())
                && !CollectionUtils.isEmpty(partyDetails.getAppointeeDetails().getAppointeeAddress())){
            for (Address address : partyDetails.getAppointeeDetails().getAppointeeAddress()) {
                if (Objects.nonNull(address) && org.springframework.util.StringUtils.hasLength(address.getAddressType())) {
                    if ("CRA".equalsIgnoreCase(address.getAddressType())) {
                        PfCommunicationAddress pfAddress = pfDetails.get(i).getAppointeePfDetails().getCommunicationAddress();
                        updatePfAddress(pfAddress, address);
                    } else if ("PRA".equalsIgnoreCase(address.getAddressType())) {
                        PfPermanentAddress pfAddress = pfDetails.get(i).getAppointeePfDetails().getPermanentAddress();
                        updatePfAddress(pfAddress, address);
                    }
                }
            }
        }
    }

    public static void updateBankDetails(PartyDetails partyDetails, List<NomineeAppointeePfDetails> pfDetails, int i) {
        if (checkForNomineeBankDetails(partyDetails)) {
            PfBankDetails pfBankDetails = pfDetails.get(i).getNomineePfDetails().getBankDetails();
            BankDetails bankDetails = partyDetails.getNomineeBankDetails().getBankDetails().get(0);
            updatePfBankDetails(pfBankDetails, bankDetails);
        }
        if (checkForAppointeeBankDetails(partyDetails)) {
            PfBankDetails pfBankDetails = pfDetails.get(i).getAppointeePfDetails().getBankDetails();
            BankDetails bankDetails = partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails().get(0);
            updatePfBankDetails(pfBankDetails, bankDetails);
        }
    }

    private static void updatePfAddress(PfCommunicationAddress pfAddress, Address address) {
        if(Objects.nonNull(address.getAddressDetails())) {
            pfAddress.setCity(nullSafe(address.getAddressDetails().getCity()));
            pfAddress.setCountry(nullSafe(address.getAddressDetails().getCountry()));
            pfAddress.setPinCode(nullSafe(address.getAddressDetails().getPinCode()));
            pfAddress.setState(nullSafe(address.getAddressDetails().getState()));
            pfAddress.setVillage(nullSafe(address.getAddressDetails().getVillage()));
            pfAddress.setRoad(nullSafe(address.getAddressDetails().getArea()));
            pfAddress.setLandmark(nullSafe(address.getAddressDetails().getLandmark()));
            pfAddress.setHouseNo(nullSafe(address.getAddressDetails().getHouseNo()));
        }
    }

    private static void updatePfAddress(PfPermanentAddress pfAddress, Address address) {
        if(Objects.nonNull(address.getAddressDetails())) {
            pfAddress.setCity(nullSafe(address.getAddressDetails().getCity()));
            pfAddress.setCountry(nullSafe(address.getAddressDetails().getCountry()));
            pfAddress.setPinCode(nullSafe(address.getAddressDetails().getPinCode()));
            pfAddress.setState(nullSafe(address.getAddressDetails().getState()));
            pfAddress.setVillage(nullSafe(address.getAddressDetails().getVillage()));
            pfAddress.setRoad(nullSafe(address.getAddressDetails().getArea()));
            pfAddress.setLandmark(nullSafe(address.getAddressDetails().getLandmark()));
            pfAddress.setHouseNo(nullSafe(address.getAddressDetails().getHouseNo()));
        }
    }

    private static boolean checkForAppointeeBankDetails(PartyDetails partyDetails) {
        return Objects.nonNull(partyDetails.getAppointeeDetails())
                && Objects.nonNull(partyDetails.getAppointeeDetails().getAppointeeBankDetails())
                && !CollectionUtils.isEmpty(partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails())
                && Objects.nonNull(partyDetails.getAppointeeDetails().getAppointeeBankDetails().getBankDetails().get(0));
    }

    private static boolean checkForNomineeBankDetails(PartyDetails partyDetails) {
        return Objects.nonNull(partyDetails.getNomineeBankDetails())
                && !CollectionUtils.isEmpty(partyDetails.getNomineeBankDetails().getBankDetails())
                && Objects.nonNull(partyDetails.getNomineeBankDetails().getBankDetails().get(0));
    }

    private static void updatePfBankDetails(PfBankDetails pfBankDetails, BankDetails bankDetails) {
        pfBankDetails.setAccountNumber(nullSafe(bankDetails.getBankAccountNumber()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String bankAccOpeningDate = bankDetails.getBankAccOpeningDate() != null ?
                dateFormat.format(bankDetails.getBankAccOpeningDate()) : StringUtils.EMPTY;
        pfBankDetails.setBankingSince(nullSafe(bankAccOpeningDate));
        pfBankDetails.setHolderName(nullSafe(bankDetails.getAccountHolderName()));
        pfBankDetails.setIfsc(nullSafe(bankDetails.getIfsc()));
        pfBankDetails.setMicr(nullSafe(bankDetails.getMicr()));
        pfBankDetails.setNameAndBranch(nullSafe(bankDetails.getBankName()) + " / " + nullSafe(bankDetails.getBankBranch()));
        pfBankDetails.setType(nullSafe(bankDetails.getTypeOfAccount()));
    }

    public static void initializePfDetails(List<NomineeAppointeePfDetails> pfDetails) {
        for(int i = 0; i< NUMBER_OF_DEFAULT_NOMINEES; i++) {
            pfDetails.add(new NomineeAppointeePfDetails());
            pfDetails.get(i).setAppointeePfDetails(new PfDetails());
            pfDetails.get(i).setNomineePfDetails(new PfDetails());
            pfDetails.get(i).getAppointeePfDetails().setBankDetails(new PfBankDetails("","","","","","",""));
            pfDetails.get(i).getNomineePfDetails().setBankDetails(new PfBankDetails("","","","","","",""));
            pfDetails.get(i).getAppointeePfDetails().setCommunicationAddress(new PfCommunicationAddress("","","","","","","","","",""));
            pfDetails.get(i).getNomineePfDetails().setCommunicationAddress(new PfCommunicationAddress("","","","","","","","","",""));
            pfDetails.get(i).getAppointeePfDetails().setPermanentAddress(new PfPermanentAddress("","","","","","","",""));
            pfDetails.get(i).getNomineePfDetails().setPermanentAddress(new PfPermanentAddress("","","","","","","",""));
        }
    }
}
