package com.mli.mpro.productRestriction.service.loader;

import com.mli.mpro.productRestriction.config.SchemaConfiguration;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.networknt.schema.JsonSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchemaFactory {

    private static final Logger logger = LoggerFactory.getLogger(SchemaFactory.class);

    public JsonSchema getSchema(String productId,String source) {
        return SchemaConfiguration.getProductBrokerSchema(productId,source);
    }
}

