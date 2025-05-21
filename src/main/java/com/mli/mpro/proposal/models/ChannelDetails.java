
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "branchCode", "branchName", "channel" })
public class ChannelDetails {

    @Sensitive(MaskType.BRANCH_CODE)
    @JsonProperty("branchCode")
    private String branchCode;

    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("branchName")
    private String branchName;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("channelId")
    private String channelId;

    @JsonProperty("specifiedPersonChannel")
    private SpecifiedPersonChannel specifiedPersonChannel;

    private String influencerChannel;

    @JsonProperty("deoChannel")
    private String deoChannel;
    
    @JsonProperty("channelType")
    private String channelType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChannelDetails() {
    }

    public ChannelDetails(String branchCode, String branchName, String channel, String channelId) {
	super();
	this.branchCode = branchCode;
	this.branchName = branchName;
	this.channel = channel;
	this.channelId = channelId;
    }

    public ChannelDetails(ChannelDetails channelDetails) {
	if(channelDetails!=null)
	{
	this.branchCode = channelDetails.branchCode;
	this.branchName = channelDetails.branchName;
	this.channel = channelDetails.channel;
	this.channelId = channelDetails.channelId;
	this.deoChannel = channelDetails.deoChannel;
	this.channelType = channelDetails.channelType;
	}
	
    }

    @JsonProperty("branchCode")
    public String getBranchCode() {
	return branchCode;
    }

    @JsonProperty("branchCode")
    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getBranchName() {
	return branchName;
    }

    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    public String getChannelId() {
	return channelId;
    }

    public void setChannelId(String channelId) {
	this.channelId = channelId;
    }
    

    public SpecifiedPersonChannel getSpecifiedPersonChannel() {
        return specifiedPersonChannel;
    }

    public void setSpecifiedPersonChannel(SpecifiedPersonChannel specifiedPersonChannel) {
        this.specifiedPersonChannel = specifiedPersonChannel;
    }

    public String getInfluencerChannel() {
        return influencerChannel;
    }

    public void setInfluencerChannel(String influencerChannel) {
        this.influencerChannel = influencerChannel;
    }

    public String getDeoChannel() {
        return deoChannel;
    }

    public void setDeoChannel(String deoChannel) {
        this.deoChannel = deoChannel;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ChannelDetails [branchCode=" + branchCode + ", branchName=" + branchName + ", channel=" + channel 
	        + ", channelId=" + channelId + ", deoChannel=" + deoChannel 
	        + ", channelType=" + channelType + "]";
    }

}
