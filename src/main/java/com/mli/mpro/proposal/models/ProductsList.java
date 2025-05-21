package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsList {

    private QuoteDetails quoteDetails;

    public QuoteDetails getQuoteDetails() {
        return quoteDetails;
    }

    public void setQuoteDetails(QuoteDetails quoteDetails) {
        this.quoteDetails = quoteDetails;
    }
}
