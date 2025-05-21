package com.mli.mpro.productRestriction.service.loader;

import com.mli.mpro.productRestriction.service.ValidationService;
import com.mli.mpro.productRestriction.service.impl.ValidationServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationFactory {
    private static final Logger logger = LoggerFactory.getLogger(ValidationFactory.class);

    @Autowired
    private ValidationServiceImpl validationService;

    public ValidationService getValidationService() {
        return validationService;
    }
}
