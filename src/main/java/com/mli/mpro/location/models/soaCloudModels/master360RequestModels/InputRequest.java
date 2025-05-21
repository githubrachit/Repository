package com.mli.mpro.location.models.soaCloudModels.master360RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class InputRequest {
	
	@JsonProperty("request")
	private Request request;

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "OutputRequest [request=" + request + "]";
	}
	
	

}
