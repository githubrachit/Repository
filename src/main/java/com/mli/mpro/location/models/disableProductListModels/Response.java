package com.mli.mpro.location.models.disableProductListModels;

import java.util.List;

public class Response {

    private List<String> productNames;

    public Response() {
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    @Override
    public String toString() {
        return "Response{" +
                "productNames=" + productNames +
                '}';
    }
}