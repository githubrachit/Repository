package com.mli.mpro.onboarding.partner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.onboarding.partner.service.ValidationService;
import com.mli.mpro.onboarding.partner.validation.Validation;
import com.mli.mpro.onboarding.partner.validation.schema.Schema;
import com.mli.mpro.onboarding.partner.validation.schema.SchemaValidationFactory;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("proposalNumberAPIValidation")
public class ProposalNumberValidationServiceImpl implements ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ProposalNumberValidationServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SchemaValidationFactory schemaFactory;
    @Override
    public Validation validateRequest(String inputRequest) throws JsonProcessingException {
        logger.info("Inside validate method of proposal number");
        Validation validation = new Validation();
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readTree(inputRequest);
        Schema schema = schemaFactory.getSchema(AppConstants.PROPOSAL_NUMBER);
        JsonSchema jsonSchema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(objectMapper.writeValueAsString(schema.getSchemaObject()));
        Set<ValidationMessage> errorSets= jsonSchema.validate(jsonNode);
        Map<String, List<String>> customErrorList = schema.getErrorList();
        Set<ErrorResponse> errors = setErrorResponse(errorSets, customErrorList);
        if (!errors.isEmpty()) {
            logger.info("Error of proposal number json validation schema is {}",errors);
            validation.setIsValid(false);
            validation.setErrors(errors);
        }
        return validation;
    }

    private static Set<ErrorResponse> setErrorResponse(Set<ValidationMessage> errorSets, Map<String, List<String>> customErrorList) {
        Set<ErrorResponse> errors = new HashSet<>();
        for (ValidationMessage e : errorSets) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(customErrorList.get(e.getMessage().split(":")[0].substring(2)).get(0));
            errorResponse.setMessage(customErrorList.get(e.getMessage().split(":")[0].substring(2)).get(1));
            logger.error("Json validation errors {}", e.getMessage());
            errors.add(errorResponse);
        }
        return errors;
    }
}
