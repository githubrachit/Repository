package com.mli.mpro.productRestriction.service.loader;

import com.mli.mpro.productRestriction.config.JsonSchemaConfiguration;
import com.mli.mpro.productRestriction.models.Schema;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JsonSchemaFactory {

    private static final Logger logger = LoggerFactory.getLogger(SchemaFactory.class);

    public Schema getSchema(String sourceChannel) {
        if(StringUtils.isEmpty(sourceChannel))
            return null;
        switch (sourceChannel) {
            case AppConstants.PAN_DOB_VERIFICATION:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.PAN_DOB_VERIFICATION);
            case AppConstants.SEND_OR_VALIDATE_OTP:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.SEND_OR_VALIDATE_OTP);
            case AppConstants.VALIDATE_DATA:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.VALIDATE_DATA);
            case AppConstants.IFSC_MICR:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.IFSC_MICR);
            case AppConstants.GET_COUNTRIES:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_COUNTRIES);
            case AppConstants.SPCODE_VALIDATION_CLOUD_SERVICE:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.SPCODE_VALIDATION_CLOUD_SERVICE);
            case AppConstants.CHECK_BAJAJ_SELLER_APPLICABILITY:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.CHECK_BAJAJ_SELLER_APPLICABILITY);
            case AppConstants.GET_STATES:
                return  JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_STATES);
            case AppConstants.GET_CLIENT_POLICY_DETAILS:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_CLIENT_POLICY_DETAILS);
            case AppConstants.GET_COMPANY_LIST:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_COMPANY_LIST);
            case AppConstants.GET_LABLIST:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_LABLIST);
            case AppConstants.GET_PRODUCT_RECOMMENDATIONS:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_PRODUCT_RECOMMENDATIONS);
            case AppConstants.GET_STATE_CITY:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.GET_STATE_CITY);
            case AppConstants.AGENT360_CLOUD:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.AGENT360_CLOUD);
            case AppConstants.AGENT_CHECK:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.AGENT_CHECK);
            case AppConstants.TRAINING:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.TRAINING);
            case AppConstants.YBL_PASADATA:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.YBL_PASADATA);
            case AppConstants.NEW_APPLICATION:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.NEW_APPLICATION);
            case AppConstants.CALL_BRMS_BROKER:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.CALL_BRMS_BROKER);
            case AppConstants.YBL_REPLACEMENT:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.YBL_REPLACEMENT);
            case AppConstants.OTP_VALIDATION:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.OTP_VALIDATION);
            case AppConstants.DISABILITY_DECLARATION_FLOWTYPE:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.DISABILITY_DECLARATION_FLOWTYPE);
            case AppConstants.EKYC_TYPE:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.EKYC_TYPE);
            case AppConstants.LOGIN_DATA:
                return JsonSchemaConfiguration.schemaMap.get(AppConstants.LOGIN_DATA);
            default:
                logger.error("Returning default SchemaConfig");
                return JsonSchemaConfiguration.schemaMap.get("defaultSchema");
        }
    }
}

