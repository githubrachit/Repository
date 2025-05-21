package com.mli.mpro.productRestriction.service.impl;

import com.mli.mpro.productRestriction.models.InputPayload;
import com.mli.mpro.productRestriction.models.OutputPayload;
import com.mli.mpro.productRestriction.models.ProductRestrictionMasterData;
import com.mli.mpro.productRestriction.repository.ProductRestrictionRepository;
import com.mli.mpro.productRestriction.service.PinCodeValidationService;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mli.mpro.productRestriction.util.AppConstants.STATUS_SUCCESS;

@Service
public class PinCodeValidationServiceImpl implements PinCodeValidationService {

    private static final Logger logger = LoggerFactory.getLogger(PinCodeValidationServiceImpl.class);
    @Autowired
    private ProductRestrictionRepository productRestrictionRepository;

    @Override
    public OutputPayload getAllowedSumAssured(InputPayload requestPayload) {
        try {
            logger.info("Performing pincode validations for Pincode: {}", requestPayload.getPinCode());
            String pinCode = requestPayload.getPinCode();
            ProductRestrictionMasterData data = productRestrictionRepository.findByType(AppConstants.PIN_CODE);
            logger.info("Data has been fetched from collection");
            if (data.getDataMap().get(pinCode) != null) {
                logger.info("performing pincode validations for blocked pincode: {}", requestPayload.getPinCode());
                return new OutputPayload(0, STATUS_SUCCESS);
            }
            data = productRestrictionRepository.findByType(AppConstants.PIN_CODE_1CR_SUM_ASSURED);
            if (data.getDataMap().get(pinCode) != null) {
                logger.info("Performing pincode validations for 1cr: {}", requestPayload.getPinCode());
                return new OutputPayload(1, STATUS_SUCCESS);
            }
            data = productRestrictionRepository.findByType(AppConstants.PIN_CODE_2CR_SUM_ASSURED);
            if (data.getDataMap().get(pinCode) != null) {
                logger.info("Performing pincode validations for 2cr: {}", requestPayload.getPinCode());
                return new OutputPayload(2, STATUS_SUCCESS);
            }
            logger.info("Sending default sumAssured values for pincode: {}", pinCode);
            return new OutputPayload(3, STATUS_SUCCESS);
        } catch (Exception e) {
            logger.error("error occured while perfoming pincode validations" + e.getMessage());
            return new OutputPayload(99, "Internal Server Error");
        }
    }
}
