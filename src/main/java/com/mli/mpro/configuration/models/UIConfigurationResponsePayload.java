package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class UIConfigurationResponsePayload {

	private List<UIConfiguration> uiconfigurations;

	public UIConfigurationResponsePayload() {
	}

	public UIConfigurationResponsePayload(List<UIConfiguration> uiconfigurations) {
		this.uiconfigurations = uiconfigurations;
	}

	public List<UIConfiguration> getConfigurations() {
		return uiconfigurations;
	}

	public void setConfigurations(List<UIConfiguration> configurations) {
		this.uiconfigurations = configurations;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "UIConfigurationResponsePayload{" +
				"uiconfigurations=" + uiconfigurations +
				'}';
	}
}
