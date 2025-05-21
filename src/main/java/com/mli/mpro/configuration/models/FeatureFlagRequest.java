package com.mli.mpro.configuration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.validation.constraints.*;

public class FeatureFlagRequest {

	@JsonProperty("featureName")
	private String featureName;

	@JsonProperty("enabled")
	private Boolean enabled;

	@JsonProperty("jiraId")
	private String jiraId;

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
		return "FeatureFlagRequest [featureName=" + featureName + ", enabled=" + enabled + ", jiraId=" + jiraId + "]";
	}

}
