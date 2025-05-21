package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Quote {

    private QuoteInformation quoteInformation;
    private List<ProductDetail> productsDetails;

    public QuoteInformation getQuoteInformation() {
        return quoteInformation;
    }

    public void setQuoteInformation(QuoteInformation quoteInformation) {
        this.quoteInformation = quoteInformation;
    }

    public List<ProductDetail> getProductsDetails() {
        return productsDetails;
    }

    public void setProductsDetails(List<ProductDetail> productsDetails) {
        this.productsDetails = productsDetails;
    }
}
