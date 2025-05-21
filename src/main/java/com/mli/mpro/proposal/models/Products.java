package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "products")
public class Products {

    private String channel ;
    private String nationality;
    private String formType ;
    private List<String> productsList ;

    public Products(String channel, String nationality, String formType, List<String> productsList) {
        this.channel = channel;
        this.nationality = nationality;
        this.formType = formType;
        this.productsList = productsList;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public List<String> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<String> productsList) {
        this.productsList = productsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return channel.equals(products.channel) &&
                nationality.equals(products.nationality) &&
                formType.equals(products.formType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, nationality, formType);
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "Products{" +
                "channel='" + channel + '\'' +
                ", nationality='" + nationality + '\'' +
                ", formType='" + formType + '\'' +
                ", productsList=" + productsList +
                '}';
    }
}
