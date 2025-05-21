package com.mli.mpro.productRestriction.models.proposalFormResponseModels;


import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class Header {

	    private String soaCorrelationId;
	    private String soaAppId;

	    public String getSoaCorrelationId() {
		return soaCorrelationId;
	    }

	    public void setSoaCorrelationId(String soaCorrelationId) {
		this.soaCorrelationId = soaCorrelationId;
	    }

	    public String getSoaAppId() {
		return soaAppId;
	    }

	    public void setSoaAppId(String soaAppId) {
		this.soaAppId = soaAppId;
	    }

	    @Override
	    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "Header [soaCorrelationId=" + soaCorrelationId + ", soaAppId=" + soaAppId + "]";
	    }


}
