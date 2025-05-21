package com.mli.mpro.common.models;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agentSelf.AgentSelfRequestPayload;
import com.mli.mpro.bajajCapital.models.BajajSellerDataRequestPayload;
import com.mli.mpro.brmsBroker.BrmsBrokerRequestPayload;
import com.mli.mpro.emailPolicyPack.EmailPolicyPackRequestPayload;
import com.mli.mpro.location.models.RequestPayload;
import com.mli.mpro.location.YblPasa.Payload.YblInputRequest;
import com.mli.mpro.location.models.SarthiRequestPayload;
import com.mli.mpro.nps.model.NpsRequestPayload;
import com.mli.mpro.ekyc.model.EkycPayload;
import com.mli.mpro.pasa.models.PasaValidationRequestPayload;
import com.mli.mpro.utils.Utility;

public class RequestData {

    private RequestPayload requestPayload;

    private YblInputRequest payload;
    @JsonProperty("ekycPayload")
    private EkycPayload ekycPayload;

    @Valid
    @JsonProperty("iibRequestPayload")
    private IIBRequestPayload iibRequestPayload;
    //FUL2-116428 NPS via PRAN
    @Valid
    private NpsRequestPayload npsRequestPayload;

    @Valid
    private YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload;

    @Valid
    private AxisBranchDetailsPayload axisBranchDetailsPayload;

    @Valid
    private PasaValidationRequestPayload pasaValidationRequestInfo;

    private AgentSelfRequestPayload agentSelfRequestPayload;

    public AgentSelfRequestPayload getAgentSelfRequestPayload() {
        return agentSelfRequestPayload;
    }

    private BajajSellerDataRequestPayload bajajSellerDataRequestPayload;
    //FUL2-170700 BRMS broker Integration
    @Valid
    @JsonProperty("brmsBrokerRequestPayload")
    private BrmsBrokerRequestPayload brmsBrokerRequestPayload;

    @Valid
    private SarthiRequestPayload sarthirequestPayload;
    @JsonProperty("emailPolicyPackRequestPayload")
    private EmailPolicyPackRequestPayload emailPolicyPackRequestPayload;

    public EmailPolicyPackRequestPayload getEmailPolicyPackRequestPayload() {
        return emailPolicyPackRequestPayload;
    }

    public void setEmailPolicyPackRequestPayload(EmailPolicyPackRequestPayload emailPolicyPackRequestPayload) {
        this.emailPolicyPackRequestPayload = emailPolicyPackRequestPayload;
    }

    public SarthiRequestPayload getSarthirequestPayload() {
        return sarthirequestPayload;
    }

    public void setSarthirequestPayload(SarthiRequestPayload sarthirequestPayload) {
        this.sarthirequestPayload = sarthirequestPayload;
    }

    public BrmsBrokerRequestPayload getBrmsBrokerRequestPayload() {
        return brmsBrokerRequestPayload;
    }

    public void setBrmsBrokerRequestPayload(BrmsBrokerRequestPayload brmsBrokerRequestPayload) {
        this.brmsBrokerRequestPayload = brmsBrokerRequestPayload;
    }

    public void setAgentSelfRequestPayload(AgentSelfRequestPayload agentSelfRequestPayload) {
        this.agentSelfRequestPayload = agentSelfRequestPayload;
    }

    public RequestData() {

    }

    public RequestPayload getRequestPayload() {
	return requestPayload;
    }

    public void setRequestPayload(RequestPayload requestPayload) {
	this.requestPayload = requestPayload;
    }


	public PasaValidationRequestPayload getPasaValidationRequestInfo() {
		return pasaValidationRequestInfo;
	}

	public void setPasaValidationRequestInfo(PasaValidationRequestPayload pasaValidationRequestInfo) {
		this.pasaValidationRequestInfo = pasaValidationRequestInfo;
	}

    public NpsRequestPayload getNpsRequestPayload() {
        return npsRequestPayload;
    }

    public void setNpsRequestPayload(NpsRequestPayload npsRequestPayload) {
        this.npsRequestPayload = npsRequestPayload;
    }

    public YblReplacementSaleRequestPayload getYblReplacementSaleRequestPayload() {
        return yblReplacementSaleRequestPayload;
    }

    public void setYblReplacementSaleRequestPayload(YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload) {
        this.yblReplacementSaleRequestPayload = yblReplacementSaleRequestPayload;
    }

    public BajajSellerDataRequestPayload getBajajSellerDataRequestPayload() {
        return bajajSellerDataRequestPayload;
    }

    public void setBajajSellerDataRequestPayload(BajajSellerDataRequestPayload bajajSellerDataRequestPayload) {
        this.bajajSellerDataRequestPayload = bajajSellerDataRequestPayload;
    }

    public EkycPayload getEkycPayload() {
        return ekycPayload;
    }

    public void setEkycPayload(EkycPayload ekycPayload) {
        this.ekycPayload = ekycPayload;
    }

    public AxisBranchDetailsPayload getAxisBranchDetailsPayload() {
        return axisBranchDetailsPayload;
    }

    public void setAxisBranchDetailsPayload(AxisBranchDetailsPayload axisBranchDetailsPayload) {
        this.axisBranchDetailsPayload = axisBranchDetailsPayload;
    }

    public YblInputRequest getPayload() {
        return payload;
    }

    public void setPayload(YblInputRequest payload) {
        this.payload = payload;
    }

    public IIBRequestPayload getIibRequestPayload() { return iibRequestPayload; }

    public void setIibRequestPayload(IIBRequestPayload iibRequestPayload) { this.iibRequestPayload = iibRequestPayload; }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "RequestData [requestPayload=" + requestPayload + ", pasaValidationRequestInfo="
                + pasaValidationRequestInfo + ",agentSelfRequestPayload=" + agentSelfRequestPayload +
                ",npsRequestPayload=" + npsRequestPayload +"bajajSellerDataRequestPayload"+bajajSellerDataRequestPayload+
                "brmsBrokerRequestPayload"+brmsBrokerRequestPayload+ "emailPolicyPackRequestPayload"+emailPolicyPackRequestPayload+"]";
    }
}