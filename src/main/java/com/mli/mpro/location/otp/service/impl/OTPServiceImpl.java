package com.mli.mpro.location.otp.service.impl;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.otp.exception.InvalidRequestException;
import com.mli.mpro.location.otp.exception.RateLimitException;
import com.mli.mpro.location.otp.exception.TransactionDetailsNotAvailableException;
import com.mli.mpro.location.otp.models.*;
import com.mli.mpro.location.otp.service.OTPService;
import com.mli.mpro.location.otp.service.OtpFlowFactory;
import com.mli.mpro.location.otp.service.OtpUtility;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    private OtpUtility otpUtility;
    @Autowired
    private OtpFlowFactory otpFlowFactory;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${sendotp.ratelimit}")
    private int otpRateLimit;
    @Value("${sendotp.duration}")
    private long otpDuration;
    @Value("${sendotp.ratelimit.expiration}")
    private long otpExpiration;
    @Value("${sendotp.generation.length}")
    private String otpGenerationLength;
    @Value("${sendotp.default.value}")
    private String otpDefaultValue;
    @Value("${sendotp.max.attempt.perday}")
    private int otpMaxAttemptPerday;
	@Value("${shorterjourney.otp.duration}")
	private long shorterjourneyOtpDuration;

    

    private final static String RATE_LIMIT_PREFIX = "ratelimit";
    private final static String OTP_PREFIX = "otp";
    private final static String DAILY_OTP_PREFIX = "otpcount";
    private static final Logger logger= LoggerFactory.getLogger(OTPServiceImpl.class);
    @Autowired
    public OTPServiceImpl(OtpUtility otpUtility) {
        this.otpUtility = otpUtility;
    }

    @Override
    public ResponseEntity<Object> sendOTP(OtpInputRequest inputRequest) {

        try {
            String otp;
            String flowType = inputRequest.getRequest().getRequestData().getPayload().getFlowType();
            long transactionId = inputRequest.getRequest().getRequestData().getPayload().getTransactionId();
            String rateLimitKey = flowType + "_" + RATE_LIMIT_PREFIX + "_" + transactionId;//for storing/retrieving RateLimit value
            String otpKey = flowType + "_" + OTP_PREFIX + "_" + transactionId;//for storing/retrieving OTP value
            String dailyOtpCountKey = flowType + "_" + DAILY_OTP_PREFIX + "_" + transactionId;
            String policyNumber = inputRequest.getRequest().getRequestData().getPayload().getPolicyNumber();
            boolean isShorterJourney = FlowType.SHORTERJOURNEY.name().equals(flowType);
            //Validate the transaction/policyNo is present im db or not
            isTransactionExistInDB(transactionId, policyNumber);
            //Fetch ProposerName from DB
            Query query = new Query();
            query.addCriteria(Criteria.where("applicationDetails.policyNumber").is(policyNumber).and("transactionId").is(transactionId));
            ProposalDetails proposalDetails = mongoOperations.findOne(query, ProposalDetails.class);
            String proposerName = Utility.getProposerName(proposalDetails);

           if(!isShorterJourney) {
        	   //check OTP Rate limit
               if (!isRateLimited(rateLimitKey)) {
                   logger.info("Rate-limit exceeded for transactionId {}", transactionId);
                   throwRateLimitException();
               }
               isRateLimitedPerDay(dailyOtpCountKey);
           }
            // Generate OTP
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DISABILITY_DECLARATION)) {
				otp = isShorterJourney ?AppConstants.SHORTER_JOURNEY_OTP_DEFAULT_VALUE:otpDefaultValue;
				} else {
                otp=isShorterJourney ? OtpUtility.generateRandomNumber(Integer.parseInt(AppConstants.SHORTER_JOURNEY_OTP_LENGTH)):OtpUtility.generateRandomNumber(Integer.parseInt(otpGenerationLength));            
                }
            FlowType type = FlowType.valueOf(inputRequest.getRequest().getRequestData().getPayload().getFlowType().toUpperCase());
            logger.info("OTP Generated for transactionId {}", transactionId);
            redisTemplate.opsForValue().set(otpKey, otp, Duration.ofMinutes(isShorterJourney ?shorterjourneyOtpDuration:otpDuration));
            String status = otpFlowFactory.getFlow(type).execute(inputRequest.getRequest().getRequestData().getPayload(), otp,proposerName);
            if (status.equalsIgnoreCase("Success")) {
                logger.error("OTP Sent successfully for transactionId {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId());
                return new ResponseEntity<>(new OtpOutputResponse.Builder()
                        .statusCode(HttpStatus.OK.value())
                        .response(new Response.Builder()
                                .responseData(new ResponseData.Builder()
                                        .payload(new Payload.Builder()
                                                .msg("OTP Generated Successfully")
                                                .otpKey(otpKey)
                                                .build())
                                        .build())
                                .build())
                        .build(), HttpStatus.OK);
            }else if(status.equalsIgnoreCase("Fail")){
                logger.info("Failed to send otp in sms for transactionId {}", transactionId);
                return new ResponseEntity<>(new OtpOutputResponse.Builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorResponse(new ErrorResponse.Builder()
                                .errorMessage("OTP not sent")
                                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .build())
                        .build(), HttpStatus.OK);
            }
        } catch (JedisConnectionException ex) {
            logger.error("JedisConnectionException occured for transactionId {} is {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId(), Utility.getExceptionAsString(ex));
            return new ResponseEntity<>(new OtpOutputResponse.Builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorResponse(new ErrorResponse.Builder()
                            .errorMessage(ex.getMessage())
                            .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build())
                    .build(), HttpStatus.OK);
        } catch (TransactionDetailsNotAvailableException ex) {
            logger.error("TransactionDetails not found in DB for transactionId {} is {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId(),  Utility.getExceptionAsString(ex));
            return new ResponseEntity<>(new OtpOutputResponse.Builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .errorResponse(new ErrorResponse.Builder()
                            .errorMessage(ex.getMessage())
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .build(), HttpStatus.OK);
        } catch (RateLimitException ex) {
            logger.error("RateLimit exceeded for transactionId {} is {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId(),  Utility.getExceptionAsString(ex));
            return new ResponseEntity<>(new OtpOutputResponse.Builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .errorResponse(new ErrorResponse.Builder()
                            .errorMessage(ex.getMessage())
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .build(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Exception occurred for transactionId {} is {}", inputRequest.getRequest().getRequestData().getPayload().getTransactionId(),  Utility.getExceptionAsString(ex));
            return new ResponseEntity<>(new OtpOutputResponse.Builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .errorResponse(new ErrorResponse.Builder()
                            .errorMessage(ex.getMessage())
                            .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build())
                    .build(), HttpStatus.OK);
        }
        return null;
    }
    private void isRateLimitedPerDay(String dailyOtpCountKey) throws RateLimitException {
        ValueOperations<String,String> valueOps= redisTemplate.opsForValue();
        //Check if the key exist
        Boolean isKeyPresent = redisTemplate.hasKey(dailyOtpCountKey);
        if (Boolean.FALSE.equals(isKeyPresent)){
            //Initialize the key with a value of "0" and set its expiry
            valueOps.set(dailyOtpCountKey,"0",Duration.ofDays(1));
            //Calculate and set the expire time for the  key to midnoght of the current day
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.MIDNIGHT);
            long secondUntillEndOfDay = Duration.between(now,endOfDay).getSeconds();
            redisTemplate.expire(dailyOtpCountKey,Duration.ofSeconds(secondUntillEndOfDay));
        }
        valueOps.increment(dailyOtpCountKey);
        String countStr = valueOps.get(dailyOtpCountKey);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);
        if (count > otpMaxAttemptPerday){
            throw new RateLimitException("OtpRateLimit", "OTP Rate limit exceeds For Day");
        }
    }

    @Override
    public ResponseEntity<Object> verifyOTP(OtpInputRequest inputRequest) {

        try {
            validateRequest(inputRequest);
            long transactionId = inputRequest.getRequest().getRequestData().getPayload().getTransactionId();
            logger.info("Verify OTP request received for transactionId {}",transactionId);
            String otpKey = inputRequest.getRequest().getRequestData().getPayload().getOtpKey();
            String otpValue = inputRequest.getRequest().getRequestData().getPayload().getOtpValue();
            String flowType = inputRequest.getRequest().getRequestData().getPayload().getFlowType();

            String rateLimitKey = flowType + "_" + RATE_LIMIT_PREFIX + "_" + transactionId;

            if (verifyOTP(otpKey, otpValue, rateLimitKey)) {
                return new ResponseEntity<>(new OtpOutputResponse.Builder()
                        .statusCode(HttpStatus.OK.value())
                        .response(new Response.Builder()
                                .responseData(new ResponseData.Builder()
                                        .payload(new Payload.Builder()
                                                .msg("OTP Verified Successfully")
                                                .build())
                                        .build())
                                .build())
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new OtpOutputResponse.Builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errorResponse(new ErrorResponse.Builder()
                                .errorMessage("Invalid OTP")
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .build())
                        .build(), HttpStatus.OK);
            }
        } catch (InvalidRequestException | TransactionDetailsNotAvailableException ex) {
            return new ResponseEntity<>(new OtpOutputResponse.Builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .errorResponse(new ErrorResponse.Builder()
                            .errorMessage(ex.getMessage())
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .build(), HttpStatus.OK);
        }
    }

    private void validateRequest(OtpInputRequest inputRequest) throws InvalidRequestException, TransactionDetailsNotAvailableException {
        if (inputRequest == null || inputRequest.getRequest() == null || inputRequest.getRequest().getRequestData() == null || inputRequest.getRequest().getRequestData().getPayload() == null || inputRequest.getRequest().getRequestData().getPayload().getOtpKey() == null || inputRequest.getRequest().getRequestData().getPayload().getOtpValue() == null) {
            throw new InvalidRequestException("ValidateOTP", "Invalid Request");
        }
        isTransactionExistInDB(inputRequest.getRequest().getRequestData().getPayload().getTransactionId(), inputRequest.getRequest().getRequestData().getPayload().getPolicyNumber());
    }

    private void isTransactionExistInDB(long transactionId, String policyNumber) throws TransactionDetailsNotAvailableException {
        try {
            logger.info("validating transactionId/policyNumber in db for transactionId {}", transactionId);
            Query query = new Query();
            query.addCriteria(Criteria.where("applicationDetails.policyNumber").is(policyNumber).and("transactionId").is(transactionId));
            if (!mongoOperations.exists(query, ProposalDetails.class)) {
                logger.info("TransactionID or PolicyNumber not found in DB {}, {}",transactionId, policyNumber);
                throw new TransactionDetailsNotAvailableException("TransactionNotAvailable", "TransactionId/PolicyNumber not found in DB");
            }
        } catch (Exception ex) {
            logger.error("Exception occured while validating transactionId/policyNumber into DB {} is {}",transactionId, Utility.getExceptionAsString(ex));
            throw new TransactionDetailsNotAvailableException("TransactionNotAvailable", ex.getMessage());
        }
    }

    private boolean isRateLimited(String rateLimitKey) {
        logger.info("Checking rate-limit for rate-limitKey {}", rateLimitKey);
        Long currentCount = redisTemplate.opsForValue().increment(rateLimitKey, 1);
        if (currentCount == 1) {
            redisTemplate.expire(rateLimitKey, otpExpiration, TimeUnit.MINUTES);
        }
        return currentCount <= otpRateLimit;
    }

    private void throwRateLimitException() throws RateLimitException {
        throw new RateLimitException("OtpRateLimit", "OTP Rate limit exceeds Please try after sometime");
    }

    public boolean verifyOTP(String otpKey, String otpCode, String rateLimitKey) throws InvalidRequestException {
        logger.info("validating otp for otpKey {}", otpKey);
        String storedOTP = redisTemplate.opsForValue().get(otpKey);
        if (storedOTP != null && storedOTP.equals(otpCode)) {
            redisTemplate.delete(otpKey);
            redisTemplate.delete(rateLimitKey);
            logger.info("OTP validated successfully for otpKey {}", otpKey);
            return true;
        }else if(storedOTP == null){
            logger.info("OTP not found for otpKey {}", otpKey);
            throw new InvalidRequestException("OtpNotFound","OTP not found, please regenerate");
        }
        logger.info("Invalid OTP for otpKey {}", otpKey);
        return false;
    }
}
