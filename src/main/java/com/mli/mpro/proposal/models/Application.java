package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Application {

    private Quote quote;
    private CibilLeadSearchResponse cibilLeadSearchResponse;

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public CibilLeadSearchResponse getCibilLeadSearchResponse() {
        return cibilLeadSearchResponse;
    }

    public void setCibilLeadSearchResponse(CibilLeadSearchResponse cibilLeadSearchResponse) {
        this.cibilLeadSearchResponse = cibilLeadSearchResponse;
    }
}
