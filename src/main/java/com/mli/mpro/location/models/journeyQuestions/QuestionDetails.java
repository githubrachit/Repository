package com.mli.mpro.location.models.journeyQuestions;

import com.fasterxml.jackson.annotation.JsonInclude;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "questionDetails")
public class QuestionDetails {
    @Id
    private String id;
    List<String> products;
    List<String> channels;
    List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "QuestionDetails{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", channels=" + channels +
                ", category=" + category +
                '}';
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

}
