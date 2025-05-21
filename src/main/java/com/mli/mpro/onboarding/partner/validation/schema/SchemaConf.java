package com.mli.mpro.onboarding.partner.validation.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SchemaConf {
    private static final Logger logger = LoggerFactory.getLogger(SchemaConf.class);
    public static Map<String, Schema> schemaMap = new HashMap<>();

//    @Autowired
//    private ValidationRepository schemaRepository;
    
//    @Autowired
//    private ObjectMapper objectMapper;
    
    public SchemaConf(ValidationRepository schemaRepository, ObjectMapper objectMapper) {
        try {
            List<Schema> schemaList = schemaRepository.findAll();
            for (Schema schemaData: schemaList) {
            	Schema schema = new Schema();
            	schema.setKey(schemaData.getKey());
            	schema.setErrorList(schemaData.getErrorList());
            	schema.setSchemaObject(schemaData.getSchemaObject());
//                Schema schema = new Schema(schemaData.getKey(), 
//            	JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(objectMapper.writeValueAsString(schemaData.getSchemaObject())), schemaData.getErrorList());
                schemaMap.put(schemaData.getKey(), schema);
            }
            logger.info("Onboarding Schema loaded successfully");
        } catch (Exception e) {
            logger.error("Error occurred while loading schema: {}", e.getMessage());
        }
    }
    
    

}
