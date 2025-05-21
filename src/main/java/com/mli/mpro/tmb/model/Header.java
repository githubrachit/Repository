package com.mli.mpro.tmb.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {
    @JsonProperty("source")
    String source;
    @JsonProperty("correlationId")
    String correlationId;

    @JsonProperty("value")
    String value;

    public Header() {

    }

    public Header(String source, String correlationId) {
        this.source = source;
        this.correlationId = correlationId;
    }

    public Header(String source, String value,String correlationId) {
        this.source = source;
        this.correlationId = correlationId;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "Header{" +
                "source='" + source + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
