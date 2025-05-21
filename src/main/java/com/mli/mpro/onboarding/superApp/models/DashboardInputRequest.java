package com.mli.mpro.onboarding.superApp.models;



import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class DashboardInputRequest {
    private Request request;

    public DashboardInputRequest() {

    }

    public DashboardInputRequest(Request request) {
	super();
	this.request = request;
    }

    public Request getRequest() {
	return request;
    }

    public void setRequest(Request request) {
	this.request = request;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "InputRequest [request=" + request + "]";
    }
}
