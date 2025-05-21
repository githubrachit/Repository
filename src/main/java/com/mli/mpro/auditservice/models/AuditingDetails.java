package com.mli.mpro.auditservice.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditingDetails {

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("createdTime")
    private Date createdTime;

    @JsonProperty("updatedTime")
    private Date updatedTime;

    @JsonProperty("transactionId")
    private long transactionId;

    @JsonProperty("agentId")
    private String agentId;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("auditId")
    private String auditId;

    @JsonProperty("httpStatusCode")
    private String httpStatusCode;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonIgnore
    private Map<String, Object> request = new HashMap<String, Object>();

    @JsonProperty("responseObject")
    private ResponseObject responseObject;

    public String getServiceName() {
	return serviceName;
    }

    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    public Date getCreatedTime() {
	return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
	this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
	return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
	this.updatedTime = updatedTime;
    }

    public long getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
    }

    public String getAgentId() {
	return agentId;
    }

    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    public String getRequestId() {
	return requestId;
    }

    public void setRequestId(String requestId) {
	this.requestId = requestId;
    }

    public String getAuditId() {
	return auditId;
    }

    public void setAuditId(String auditId) {
	this.auditId = auditId;
    }

    public String getHttpStatusCode() {
	return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
	this.httpStatusCode = httpStatusCode;
    }

    public String getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
	return this.request;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
	this.request.put(name, value);

	}

	public ResponseObject getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(ResponseObject responseObject) {
		this.responseObject = responseObject;
	}

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "AuditingDetails [serviceName=" + serviceName + ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + ", transactionId=" + transactionId + ", agentId=" + agentId + ", requestId=" + requestId
				+ ", auditId=" + auditId + ", httpStatusCode=" + httpStatusCode + ", statusCode=" + statusCode
				+ ", request=" + request + ", responseObject=" + responseObject + "]";
	}

}
