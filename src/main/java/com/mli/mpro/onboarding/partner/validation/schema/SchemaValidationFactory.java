package com.mli.mpro.onboarding.partner.validation.schema;

import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SchemaValidationFactory {

    private static final Logger logger = LoggerFactory.getLogger(SchemaValidationFactory.class);

    public Schema getSchema(String sourceChannel) {
        if(StringUtils.isEmpty(sourceChannel))
                return null;
        switch (sourceChannel) {
            case AppConstants.REPLACEMENT_SALE:
                return SchemaConf.schemaMap.get(AppConstants.REPLACEMENT_SALE);
            case AppConstants.DEDUPE_API:
                return SchemaConf.schemaMap.get(AppConstants.DEDUPE_API);
            case AppConstants.PROPOSAL_NUMBER:
                return SchemaConf.schemaMap.get(AppConstants.PROPOSAL_NUMBER);

            default:
                logger.error("Returning default SchemaConfig");
                return SchemaConf.schemaMap.get(AppConstants.REPLACEMENT_SALE);
        }
    }

}
