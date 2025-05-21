/**
 * 
 */
package com.mli.mpro.document.utils;

import com.mli.mpro.productRestriction.util.AppConstants;
import static com.mli.mpro.productRestriction.util.AppConstants.FORM_VERSION;

/**
 * @author akshom4375
 * enum values fo Proposal Form Channel version association
 */
public enum ProposalFormChannelVersions {

	A(AppConstants.PRAPOSAL_FORM_VERSION),
	F(AppConstants.PRAPOSAL_FORM_VERSION),
	T(AppConstants.PRAPOSAL_FORM_VERSION),
    K("0119/"),
    BY(FORM_VERSION),
    X(FORM_VERSION),
	B("0719/"),
	B2("0719/"),
	NEO("1117/"), // TODO: Confirmation with MLI
	AGGREGATOR("1117/"),// TODO: Confirmation with MLI
	P(FORM_VERSION),
	LX(FORM_VERSION);

	private String channelVersion;
	
	ProposalFormChannelVersions(String channelVersion){
		this.channelVersion = channelVersion;
	}
	
	public String getChannelVersion() {
		return channelVersion;
	}
	
}
