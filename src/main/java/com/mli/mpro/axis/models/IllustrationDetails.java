
package com.mli.mpro.axis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "illustrationOutput" })
public class IllustrationDetails {

    @JsonProperty("illustrationOutput")
    private List<IllustrationOutput> illustrationOutput;

    public IllustrationDetails() {
    }

    public IllustrationDetails(List<IllustrationOutput> illustrationOutput) {
	super();
	this.illustrationOutput = illustrationOutput;
    }

    public List<IllustrationOutput> getIllustrationOutput() {
	return illustrationOutput;
    }

    public void setIllustrationOutput(List<IllustrationOutput> illustrationOutput) {
	this.illustrationOutput = illustrationOutput;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "IllustrationDetails [illustrationOutput=" + illustrationOutput + "]";
    }

}
