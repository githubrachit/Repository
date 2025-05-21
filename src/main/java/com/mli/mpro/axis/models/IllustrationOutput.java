
package com.mli.mpro.axis.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
public class IllustrationOutput {

    
    @JsonProperty("addOnDetails")
    private AddOnDetails addOnDetails;
    @JsonProperty("illustrationId")
    private String illustrationId;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("illustrationData")
    private Map<String, Object> illustrationData;

    public IllustrationOutput() {
    }

    

    public IllustrationOutput(AddOnDetails addOnDetails, String illustrationId, Map<String, Object> illustrationData) {
	super();
	this.addOnDetails = addOnDetails;
	this.illustrationId = illustrationId;
	this.illustrationData = illustrationData;
    }



    public String getIllustrationId() {
	return illustrationId;
    }

    public void setIllustrationId(String illustrationId) {
	this.illustrationId = illustrationId;
    }

    public Map<String, Object> getIllustrationData() {
	return illustrationData;
    }

    public void setIllustrationData(Map<String, Object> illustrationData) {
	this.illustrationData = illustrationData;
    }
    

    public AddOnDetails getAddOnDetails() {
        return addOnDetails;
    }

    public void setAddOnDetails(AddOnDetails addOnDetails) {
        this.addOnDetails = addOnDetails;
    }



    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "IllustrationOutput [addOnDetails=" + addOnDetails + ", illustrationId=" + illustrationId + ", illustrationData=" + illustrationData + "]";
    }

    

}
