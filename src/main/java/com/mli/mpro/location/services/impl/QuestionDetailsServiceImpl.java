package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.common.models.MsgInfo;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.journeyQuestions.QuestionDetails;
import com.mli.mpro.location.models.journeyQuestions.Questions;
import com.mli.mpro.location.models.journeyQuestions.UtmLogic;
import com.mli.mpro.location.models.questionModels.InputRequest;
import com.mli.mpro.location.models.questionModels.OutPutResponse;
import com.mli.mpro.location.models.questionModels.Response;
import com.mli.mpro.location.models.questionModels.UTMForQuestions;
import com.mli.mpro.location.repository.QuestionDetailsRepository;
import com.mli.mpro.location.services.QuestionDetailsService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.Y;

@Service
public class QuestionDetailsServiceImpl implements QuestionDetailsService {
    private static final Logger logger= LoggerFactory.getLogger(QuestionDetailsServiceImpl.class);

    private QuestionDetailsRepository questionRepository;
    private RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper objectMapper;

    @Value("${questionDetails.redisUtmExpireTime}")
    private String questionUtmExpireTime;

    @Value("#{'${questionDetails.all.productsList}'.split(',')}")
    List<String> allProducts;
    /**
     * Apply UI UTM Logic
     * This list has question ids which are to be removed from response list if the above condition matches.
     */
    @Value("#{'${questionDetails.utm.appliedQuestionIds}'.split(',')}")
    List<String> utmAppliedQuestionIds;

    @Autowired
    public QuestionDetailsServiceImpl(QuestionDetailsRepository questionRepository, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public OutPutResponse<String> saveRecords(InputRequest<QuestionDetails> request) {
        if(Utility.isAnyObjectNull(request,
                request.getRequest(),
                request.getRequest().getRequestData(),
                request.getRequest().getRequestData().getRequestPayload())){
            return returnResponse(null,request.getRequest().getMetadata(),new UserHandledException(List.of("Invalid Request"), HttpStatus.BAD_REQUEST));
        }
        List<QuestionDetails> questionDetails = request.getRequest().getRequestData().getRequestPayload();
        questionDetails.stream().forEach(questionDetails1 -> {
            questionDetails1.setProducts(questionDetails1.getProducts().isEmpty()?List.of("All"):questionDetails1.getProducts());
            questionDetails1.setChannels(questionDetails1.getChannels().isEmpty()?List.of("All"):questionDetails1.getChannels());
        });
        List<QuestionDetails> savedDetails = questionRepository.saveAll(questionDetails);
        if(savedDetails !=null){
            logger.info("Record save successfully into DB ...");
        }
        return returnResponse(savedDetails,request.getRequest().getMetadata(),null);
    }
    @Override
    public OutPutResponse<List<QuestionDetails>> getQuestionDetails(InputRequest<UTMForQuestions> request) {
        long startTime = System.currentTimeMillis();
        OutPutResponse<List<QuestionDetails>> responseList;
        if(Utility.isAnyObjectNull(request,
                request.getRequest(),
                request.getRequest().getRequestData(),
                request.getRequest().getRequestData().getUtm(),
                request.getRequest().getRequestData().getUtm().getRequired(),
                request.getRequest().getRequestData().getUtm().getIsJointLife())){
            return returnResponse(null,request.getRequest().getMetadata(),new UserHandledException(List.of("Invalid Request"), HttpStatus.BAD_REQUEST));
        }
        if(!allProducts.contains(request.getRequest().getRequestData().getUtm().getProduct())){
            return returnResponse(null,request.getRequest().getMetadata(),new UserHandledException(List.of(request.getRequest().getRequestData().getUtm().getProduct() + " is not a valid product ID."), HttpStatus.BAD_REQUEST));
        }
        logger.info("Initiate get request for questions request is {}",Utility.printJsonRequest(request));
        UTMForQuestions utm = request.getRequest().getRequestData().getUtm();
        boolean isRedisEnable = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.FEATURE_FLAG_JOURNEY_QUESTIONS_VIA_REDIS);
        if(isRedisEnable){
            logger.info("Redis enable process via redis search...");
            responseList =  getQuestionDetailsViaRedis(utm, request,isRedisEnable);
        } else {
            logger.info("Redis not enable process via DB search...");
            responseList = getQuestionDetailsViaDB(utm,request,isRedisEnable);
        }
        logger.info("Question details filtration process took {} sec", System.currentTimeMillis() - startTime);
        return responseList;
    }
    private List<QuestionDetails> getQuestionDetailsBasedOnChannelAndProduct(UTMForQuestions utm) {
        String productSearch = utm.getProduct();
        if(StringUtils.isEmpty(utm.getChannel())){
            utm.setChannel("All");
        }
        if(StringUtils.isEmpty(utm.getProduct()) || !AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(utm.getProduct())){
            productSearch = "All";
        }
        if(!utm.getRequired().contains("health_In") && !AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(utm.getProduct())){
            productSearch = "HP_NON_CIP";
        }
        return questionRepository.findByChannelsAndProducts(utm.getChannel(), productSearch);
    }
    private OutPutResponse<List<QuestionDetails>> getQuestionDetailsViaRedis(UTMForQuestions utm, InputRequest<UTMForQuestions> request, boolean isRedisEnable){
        UUID uuid = utm.generateUUID();
        List<QuestionDetails> questionDetails = getUtmFormRedis(uuid.toString());
        if(questionDetails==null){
            logger.info("Redis records not found trying to search via DB");
            return getQuestionDetailsViaDB(utm,request,isRedisEnable);
        }
        logger.info("Records found from redis call {}",questionDetails);
        return returnResponse(questionDetails,request.getRequest().getMetadata(),null);
    }
    private OutPutResponse<List<QuestionDetails>> getQuestionDetailsViaDB(UTMForQuestions utm, InputRequest<UTMForQuestions> request, boolean isRedisEnable){
        List<QuestionDetails> questionDetails;
        UUID uuid = utm.generateUUID();
        logger.info("For the given utm uuid is {}",uuid);
        try {
            questionDetails = getQuestionDetailsBasedOnChannelAndProduct(utm);
            if(questionDetails != null){
                logger.info("record found in DB");
                questionDetails = filterOutDataBaseOnRequiredUtm(questionDetails, utm);
            } else {
                return returnResponse(null,request.getRequest().getMetadata(),new UserHandledException(List.of("Records not found in DB"), HttpStatus.NOT_FOUND));
            }
        } catch (UserHandledException ex){
            logger.error("Exception during getQuestionDetailsViaDB ", ex);
            return returnResponse(null,request.getRequest().getMetadata(),ex);
        }
        // Setting UTM into Redis for the given time period
        if(isRedisEnable){
            setUtmForQuestions(questionDetails,Integer.valueOf(questionUtmExpireTime),uuid.toString());
        }
        return returnResponse(questionDetails,request.getRequest().getMetadata(),null);
    }
    public String setUtmForQuestions(Object value, int expire, String redisKey) {
        try{
            if(org.springframework.util.StringUtils.hasText(redisKey)){
                logger.info("Service initiate to save utm into redis for next {}sec", expire);
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
    public List<QuestionDetails> getUtmFormRedis(String key) {
        logger.info("Service initiate to get utm from redis");
        try {
            Object utm = redisTemplate.opsForValue().get(key);
            if (utm != null){
                return objectMapper.readValue((String)utm,List.class);
            }
        }catch (Exception ex){
            logger.error("Exception during reading redis token ", ex);
        }
        return null;
    }
    public String removePostFixFromRequiredObj(String data){
        String postfix = "_";
        if (data != null && data.contains(postfix)) {
            return data.substring(0, data.indexOf(postfix));
        }
        return data;
    }
    public List<QuestionDetails> filterOutDataBaseOnRequiredUtm(List<QuestionDetails> questionDetails, UTMForQuestions utm) throws UserHandledException{
        List<String> required = utm.getRequired();
        logger.info("Filter out the data based on this UTM {}",required);
        final String postFixPR = "_Pr";
        final String postFixIN = "_In";
        try {

            List<String> requiredCategories = required.stream()
                    .map(this::removePostFixFromRequiredObj)
                    .collect(Collectors.toList());

            questionDetails.stream()
                    .flatMap(questionDetails1 -> questionDetails1.getCategory().stream())
                    .forEach(category -> {
                        String categoryName = category.getCategoryName();
                        if (requiredCategories.contains(categoryName)) {
                            if (!required.contains(categoryName + postFixPR)) {
                                category.setProposerPartyType(null);
                            } else if (!required.contains(categoryName + postFixIN)) {
                                category.setInsuredPartyType(null);
                            }
                        } else {
                            category.setProposerPartyType(null);
                            category.setInsuredPartyType(null);
                        }
                    });
        } catch (Exception ex){
            throw new UserHandledException(List.of("Exception during filterOutDataBaseOnRequiredUtm",ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        productValidationFilter(questionDetails, utm);
        logger.info("Questions filter out and set successfully");
        return questionDetails;
    }

    private void productValidationFilter(List<QuestionDetails> questionDetails, UTMForQuestions utm){
        logger.info("Filter out the question based on UTM for product specific SSP,STEP,SEWA");
        questionDetails.stream().flatMap(questionDetails1 -> questionDetails1.getCategory().stream())
                .forEach(category -> {
                    if(category.getProposerPartyType()!=null){
                        category.setProposerPartyType(category.getProposerPartyType().stream().filter(questions -> isQuestionRemoveBasedOnUtmLogic(questions, utm))
                                .collect(Collectors.toList()));
                    }
                    if(category.getInsuredPartyType() != null){
                        category.setInsuredPartyType(category.getInsuredPartyType().stream().filter(questions -> isQuestionRemoveBasedOnUtmLogic(questions,utm))
                                .collect(Collectors.toList()));
                    }
                });
    }

    private boolean isQuestionRemoveBasedOnUtmLogic(Questions questions, UTMForQuestions utm) {
        updateQuestionLevelProductBased(questions,utm);
        boolean isHPNonCip = !utm.getRequired().contains("health_In") && !AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(utm.getProduct());
        if(utmAppliedQuestionIds.contains(questions.getQuestionID())){
            UtmLogic utmLogic = new UtmLogic(utm.getProduct(),utm.getIsJointLife(),isHPNonCip, Y.equalsIgnoreCase(utm.isSspSwissReCase));
            logger.info("Apply validation logic for question ID {}", questions.getQuestionID());
            return UtmLogic.validateUtmLogic(utmLogic, questions.getQuestionID());
        }
        return true;
    }

    private void updateQuestionLevelProductBased(Questions questions, UTMForQuestions utm){
        try {
            if("H36C".equalsIgnoreCase(questions.getQuestionID()) ||
                    ("H12C".equalsIgnoreCase(questions.getQuestionID()) &&
                            (utm.getRequired().contains("health_In") && !AppConstants.CIP_PRODUCT_ID.equalsIgnoreCase(utm.getProduct())))){
                questions.setOrder(Y.equalsIgnoreCase(utm.getIsSspSwissReCase()) ? "14.":"16.");
            }
            if("H19".equalsIgnoreCase(questions.getQuestionID())){
                questions.getSubQuestion().get(1).getSubQuestion().get(0).setLabel(AppConstants.SEWA.equalsIgnoreCase(utm.getProduct()) ? "Tobacco Sachet/Gutka/Flavoured Pan Masala/Mawa" : "Tobacco Sachet / Gutka / Falvoured Pan Masala");
            }
        } catch (Exception e) {
            logger.info("Exception During updating H19ii And H36C level based on SEWA product");
        }
    }

    private OutPutResponse  returnResponse(Object obj, Metadata metadata, UserHandledException ex){
        Response<Object> response = null;
        if(Utility.isAnyObjectNull(ex)){
            response = new Response<>(metadata,returnMsgInfoResponse(HttpStatus.OK, null),obj);
        }else {
            response = new Response<>(metadata,returnMsgInfoResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex),null);
        }
        logger.info("Response for journey questions {}",response);
        return new OutPutResponse<>(response);
    }
    public MsgInfo returnMsgInfoResponse(HttpStatus msgInfoType,UserHandledException ex){
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setMsgDescription(!Utility.isAnyObjectNull(ex) ? ex.getErrorMessages().get(0) : "Successfully response generated");
        msgInfo.setMsgCode((String.valueOf(msgInfoType.value())));
        msgInfo.setMsg(msgInfoType.name());
        return msgInfo;
    }
    @Override
    public OutPutResponse<String> removeRecord(InputRequest<QuestionDetails> request) {
        return null;
    }

    @Override
    public OutPutResponse<String> updateRecord(InputRequest<QuestionDetails> request) {
        return null;
    }
}
