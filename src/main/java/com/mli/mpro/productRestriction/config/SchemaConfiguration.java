package com.mli.mpro.productRestriction.config;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class SchemaConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SchemaConfiguration.class);
    public static JsonSchema productBrokerSchema;
    public static JsonSchema getProductBrokerSchema(String product_Id,String source) {
        if(Objects.isNull(source) || source.trim().isEmpty() ) {
            source = "broker";
        }
        logger.info("onboarding/{}-validation-schema_{}.json",source,product_Id);
        return loadSchema("onboarding/"+source+"-validation-schema_"+product_Id+".json");
    }

    private static JsonSchema loadSchema(String schemaName) {
        try (InputStream schemaStream = SchemaConfiguration.class.getClassLoader().getResourceAsStream(schemaName)) {
            if (schemaStream != null) {
                return JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaStream);
            } else {
                logger.error("Schema {} not found", schemaName);
            }
        } catch (IOException e) {
            logger.error("Error loading schema {}: {}", schemaName, e.getMessage());
        }
        return null;
    }

}

