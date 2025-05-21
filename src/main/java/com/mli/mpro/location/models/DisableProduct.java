package com.mli.mpro.location.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Document(collection = "disableProducts")
public class DisableProduct {
    private String channelName;
    private List<String> products;

    public DisableProduct() {
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "DisableProduct{" +
                "channelName='" + channelName + '\'' +
                ", products=" + products +
                '}';
    }
}