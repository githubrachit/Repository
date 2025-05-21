package com.mli.mpro.productRestriction.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.productRestriction.models.Schema;
import com.mli.mpro.productRestriction.repository.SchemaRepository;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JsonSchemaConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JsonSchemaConfiguration.class);
    public static Map<String, Schema> schemaMap = new HashMap<>();

    public JsonSchemaConfiguration(SchemaRepository schemaRepository, ObjectMapper objectMapper) {
        try {
            List<Schema> schemaList = schemaRepository.findAll();
            for (Schema schemaData: schemaList) {
                Schema schema = new Schema(schemaData.getKey(), JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
                        .getSchema(objectMapper.writeValueAsString(schemaData.getSchemaObject())), schemaData.getErrorList());
                schemaMap.put(schemaData.getKey(), schema);
            }
            logger.info("Schema loaded successfully");
        }  catch (Exception e) {
            logger.error("Error occurred while loading schema: {}", e.getMessage());
        }
    }
}
