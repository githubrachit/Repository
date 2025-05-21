package com.mli.mpro.common.models;

import com.mli.mpro.agentSelf.AgentSelfResponsePayload;
import com.mli.mpro.bajajCapital.models.BajajSellerDataResponsePayload;
import com.mli.mpro.brmsBroker.BrmsBrokerResponsePayload;
import com.mli.mpro.configuration.models.ConfigurationResponsePayload;
import com.mli.mpro.docsApp.models.DocsappResponse;
import com.mli.mpro.ekyc.model.EkycResponsePayload;
import com.mli.mpro.emailPolicyPack.EmailPolicyPackResponsePayload;
import com.mli.mpro.location.models.LocationResponsePayload;
import com.mli.mpro.location.models.Payload;
import com.mli.mpro.location.models.PensionPlansPayload;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import com.mli.mpro.pasa.models.PasaValidationResponsePayload;
import com.mli.mpro.proposal.models.ResponsePayload;
import com.mli.mpro.utils.Utility;

public class ResponseData {
	private YblPasaResponse yblPasaResponse;
    private LocationResponsePayload locationResponsePayload;
    private ConfigurationResponsePayload configurationResponsePayload;
     //FUL2-116428 NPS via PRAN
	private NpsResponsePayload npsResponsePayload;
	private EkycResponsePayload ekycResponse;
    private Payload payload;
    private DocsappResponse docsappResponse;
    //F21-411 Pasa Validation Api
    private PasaValidationResponsePayload pasaValidationResponse;

	private AgentSelfResponsePayload agentSelfResponsePayload;

	private BajajSellerDataResponsePayload bajajSellerDataResponsePayload;
	//FUL2-170700 BRMS broker Integration
	private BrmsBrokerResponsePayload brmsBrokerResponsePayload;
	//S2-26 saral dashboard
	private EmailPolicyPackResponsePayload emailPolicyPackResponsePayload;

	private ResponsePayload responsePayload;
    private PensionPlansPayload pensionPlansPayload;

	public EmailPolicyPackResponsePayload getEmailPolicyPackResponsePayload() {
		return emailPolicyPackResponsePayload;
	}

	public void setEmailPolicyPackResponsePayload(EmailPolicyPackResponsePayload emailPolicyPackResponsePayload) {
		this.emailPolicyPackResponsePayload = emailPolicyPackResponsePayload;
	}

	public ResponseData() {

    }

	public BrmsBrokerResponsePayload getBrmsBrokerResponsePayload() {
		return brmsBrokerResponsePayload;
	}

	public void setBrmsBrokerResponsePayload(BrmsBrokerResponsePayload brmsBrokerResponsePayload) {
		this.brmsBrokerResponsePayload = brmsBrokerResponsePayload;
	}

	public NpsResponsePayload getNpsResponsePayload() {
		return npsResponsePayload;
	}

	public void setNpsResponsePayload(NpsResponsePayload npsResponsePayload) {
		this.npsResponsePayload = npsResponsePayload;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public LocationResponsePayload getLocationResponsePayload() {
	return locationResponsePayload;
    }

    public void setLocationResponsePayload(LocationResponsePayload locationResponsePayload) {
	this.locationResponsePayload = locationResponsePayload;
    }

    public ConfigurationResponsePayload getConfigurationResponsePayload() {
	return configurationResponsePayload;
    }

    public void setConfigurationResponsePayload(ConfigurationResponsePayload configurationResponsePayload) {
	this.configurationResponsePayload = configurationResponsePayload;
    }

    public ResponseData(LocationResponsePayload locationResponsePayload) {
	super();
	this.locationResponsePayload = locationResponsePayload;
    }

    public ResponseData(ConfigurationResponsePayload configurationResponsePayload) {
	this.configurationResponsePayload = configurationResponsePayload;
    }

    public DocsappResponse getDocsappResponse() {
		return docsappResponse;
	}


	public void setDocsappResponse(DocsappResponse docsappResponse) {
		this.docsappResponse = docsappResponse;
	}

	public ResponsePayload getResponsePayload() {
		return responsePayload;
	}


	public void setResponsePayload(ResponsePayload responsePayload) {
		this.responsePayload = responsePayload;
	}



	public PasaValidationResponsePayload getPasaValidationResponse() {
		return pasaValidationResponse;
	}


	public void setPasaValidationResponse(PasaValidationResponsePayload pasaValidationResponse) {
		this.pasaValidationResponse = pasaValidationResponse;
	}

	public YblPasaResponse getYblPasaResponse() {
		return yblPasaResponse;
	}

	public void setYblPasaResponse(YblPasaResponse yblPasaResponse) {
		this.yblPasaResponse = yblPasaResponse;
	}

	public PensionPlansPayload getPensionPlansPayload() {
		return pensionPlansPayload;
	}

	public void setPensionPlansPayload(PensionPlansPayload pensionPlansPayload) {
		this.pensionPlansPayload = pensionPlansPayload;
	}

	public AgentSelfResponsePayload getAgentSelfResponsePayload() {
		return agentSelfResponsePayload;
	}

	public void setAgentSelfResponsePayload(AgentSelfResponsePayload agentSelfResponsePayload) {
		this.agentSelfResponsePayload = agentSelfResponsePayload;
	}

	public BajajSellerDataResponsePayload getBajajSellerDataResponsePayload() {
		return bajajSellerDataResponsePayload;
	}

	public void setBajajSellerDataResponsePayload(BajajSellerDataResponsePayload bajajSellerDataResponsePayload) {
		this.bajajSellerDataResponsePayload = bajajSellerDataResponsePayload;
	}

	public EkycResponsePayload getEkycResponse() {
		return ekycResponse;
	}

	public void setEkycResponse(EkycResponsePayload ekycResponse) {
		this.ekycResponse = ekycResponse;
	}


	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ResponseData{" +
				"locationResponsePayload=" + locationResponsePayload +
				", configurationResponsePayload=" + configurationResponsePayload +
				", npsResponsePayload=" + npsResponsePayload +
				", ekycResponse=" + ekycResponse +
				", payload=" + payload +
				", docsappResponse=" + docsappResponse +
				", pasaValidationResponse=" + pasaValidationResponse +
				", agentSelfResponsePayload=" + agentSelfResponsePayload +
				", bajajSellerDataResponsePayload=" + bajajSellerDataResponsePayload +
				", responsePayload=" + responsePayload +
				", pensionPlansPayload=" + pensionPlansPayload +
				", brmsBrokerResponsePayload=" + brmsBrokerResponsePayload +
				", emailPolicyPackResponsePayload=" + emailPolicyPackResponsePayload +
				'}';
	}
}