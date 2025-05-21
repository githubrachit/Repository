package com.mli.mpro.psm.config;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class SchemaConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchemaConfig.class);
    public static JsonSchema schema;

    public SchemaConfig() {
        try {
            schema = loadSchema("psm/validation-schema.json");
            
        } catch (Exception e) {
            logger.error("Error occurred while loading schema: {}", e.getMessage());
        }
    }

    private JsonSchema loadSchema(String schemaName) {
        try {
            InputStream schemaStream = getClass().getClassLoader().getResourceAsStream(schemaName);
            return JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaStream);
        } catch (Exception e) {
            logger.error("Schema: {} failed to load with error: {}", schemaName, e.getMessage());
        }
        return null;
    }

}

