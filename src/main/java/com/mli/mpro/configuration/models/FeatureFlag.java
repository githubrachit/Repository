package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "feature_flag")
public class FeatureFlag {

	@Id
	@JsonProperty("id")
	private String id;
	@JsonProperty("featureName")
	private String featureName;
	@JsonProperty("enabled")
	private Boolean enabled;
	@JsonProperty("jiraId")
	private String jiraId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getJiraId() {
		return jiraId;
	}
	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "FeatureFlag{" +
				"id='" + id + '\'' +
				", featureName='" + featureName + '\'' +
				", enabled=" + enabled +
				", jiraId='" + jiraId + '\'' +
				'}';
	}
}
