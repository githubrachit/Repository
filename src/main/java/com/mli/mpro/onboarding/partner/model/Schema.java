package com.mli.mpro.onboarding.partner.model;

import com.networknt.schema.JsonSchema;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "onboarding-validation")
public class Schema {

    private String key;
    private Object schemaObject;
    private JsonSchema jsonSchema;
    private Map<String, List<String>> errorList;

    public Schema() {
    }

    public Schema(String key, JsonSchema jsonSchema, Map<String, List<String>> errorList) {
        this.key = key;
        this.jsonSchema = jsonSchema;
        this.errorList = errorList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getSchemaObject() {
        return schemaObject;
    }

    public void setSchemaObject(Object schemaObject) {
        this.schemaObject = schemaObject;
    }

    public JsonSchema getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    public Map<String, List<String>> getErrorList() {
        return errorList;
    }

    public void setErrorList(Map<String, List<String>> errorList) {
        this.errorList = errorList;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "key='" + key + '\'' +
                ", schemaObject=" + schemaObject +
                ", jsonSchema=" + jsonSchema +
                ", errorList=" + errorList +
                '}';
    }
}
