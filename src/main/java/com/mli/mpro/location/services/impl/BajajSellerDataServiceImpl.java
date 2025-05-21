package com.mli.mpro.location.services.impl;

import com.mli.mpro.bajajCapital.models.*;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.location.services.BajajSellerDataService;
import com.mli.mpro.location.services.PasaValidationService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BajajSellerDataServiceImpl implements BajajSellerDataService {
    private static final Logger logger= LoggerFactory.getLogger(PasaValidationService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BajajRetailSellerDetailsRepository bajajRetailSellerDetailsRepository;

    @Autowired
    BajajIPCSellerDetailsRepository bajajIPCSellerDetailsRepository;

    @Override
    public BajajSellerDataResponsePayload checkAgentApplicability(InputRequest inputRequest) throws UserHandledException {
        BajajSellerDataResponsePayload bajajSellerDataResponsePayload = new BajajSellerDataResponsePayload();
        String isAgentApplicable = AppConstants.NO;
        try {
            if(!ObjectUtils.isEmpty(inputRequest)){
                String sellerId = inputRequest.getRequest().getRequestData().getBajajSellerDataRequestPayload().getAgentId();
                String sellerType = inputRequest.getRequest().getRequestData().getBajajSellerDataRequestPayload().getSellerType();
                logger.info("inside checkAgentApplicability with sellerId {} and sellerType {}", sellerId, sellerType);
                if(StringUtils.hasText(sellerId) && StringUtils.hasText(sellerType)){
                    if(sellerType.equalsIgnoreCase(AppConstants.RETAIL)){
                        BajajRetailSellerDetails bajajRetailSellerDetails = new BajajRetailSellerDetails();
                        Query query = new Query();
                        query.addCriteria(Criteria.where("sellerId").is(sellerId));
                        List<BajajRetailSellerDetails> bajajRetailSellerDetailsList = mongoTemplate.find(query,BajajRetailSellerDetails.class);
                        if(!bajajRetailSellerDetailsList.isEmpty()){
                            List<String> listSellerLocations = bajajRetailSellerDetailsRepository.findAllLocations().stream().map(BajajRetailSellerDetails::getBranchName)
                                    .distinct()
                                    .collect(Collectors.toList());
                            bajajSellerDataResponsePayload.setLocations(listSellerLocations);
                            logger.info(String.valueOf(listSellerLocations));
                            bajajRetailSellerDetails = bajajRetailSellerDetailsList.get(0);
                            isAgentApplicable = (bajajRetailSellerDetails != null) ? AppConstants.YES : AppConstants.NO;
                        }else{
                            isAgentApplicable = AppConstants.NO;
                        }

                    }else if(sellerType.equalsIgnoreCase(AppConstants.IPC)){
                        BajajIPCSellerDetails bajajIPCSellerDetails = new BajajIPCSellerDetails();
                        Query query = new Query();
                        query.addCriteria(Criteria.where("sellerId").is(sellerId));
                        List<BajajIPCSellerDetails> bajajIPCSellerDetailsList = mongoTemplate.find(query,BajajIPCSellerDetails.class);
                        if(!bajajIPCSellerDetailsList.isEmpty()){
                            List<String> listSellerLocations = bajajIPCSellerDetailsRepository.findAllLocations().stream()
                                    .map(BajajRetailSellerDetails::getBranchName)
                                    .distinct()
                                    .collect(Collectors.toList());
                            bajajIPCSellerDetails = bajajIPCSellerDetailsList.get(0);
                            isAgentApplicable = (bajajIPCSellerDetails != null) ? AppConstants.YES : AppConstants.NO;
                            bajajSellerDataResponsePayload.setLocations(listSellerLocations);
                        }
                    }
                }else{
                    isAgentApplicable = AppConstants.NO;
                }
            }
            logger.info("isAgentApplicable flag is {} ", isAgentApplicable);
            bajajSellerDataResponsePayload.setIsAgentApplicable(isAgentApplicable);
            return bajajSellerDataResponsePayload;
        }catch(MongoException e){
            List<String> errorMessages = new ArrayList<>();
            logger.error("Error occurred while getting data from data base for request {} with exception: {}",inputRequest, Utility.getExceptionAsString(e));
            errorMessages.add(e.getMessage());
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (Exception e){
            List<String> errorMessages = new ArrayList<>();
            logger.error("Error occurred while validating Seller details for request {} with exception: {}",inputRequest,Utility.getExceptionAsString(e));
            errorMessages.add(e.getMessage());
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
