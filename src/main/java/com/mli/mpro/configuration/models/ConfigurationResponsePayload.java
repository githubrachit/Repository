package com.mli.mpro.configuration.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class ConfigurationResponsePayload {

    private List<Configuration> configurations;

    private MultiSelectData multiSelectData;
    private Boolean isTelesalesCase;

    public ConfigurationResponsePayload() {
    }

    public ConfigurationResponsePayload(List<Configuration> configurations) {
	this.configurations = configurations;
    }

    public MultiSelectData getMultiSelectData() {
        return multiSelectData;
    }

    public void setMultiSelectData(MultiSelectData multiSelectData) {
        this.multiSelectData = multiSelectData;
    }

    public List<Configuration> getConfigurations() {
	return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
	this.configurations = configurations;
    }

    public Boolean getTelesalesCase() {
        return isTelesalesCase;
    }

    public void setTelesalesCase(Boolean telesalesCase) {
        isTelesalesCase = telesalesCase;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ConfigurationResponsePayload{" +
                "configurations=" + configurations +
                ", multiSelectData=" + multiSelectData +
                ", isTelesalesCase=" + isTelesalesCase +
                '}';
    }
}
