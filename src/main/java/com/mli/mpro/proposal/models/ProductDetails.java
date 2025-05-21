
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "needOfInsurance", "lifeStage", "objectiveOfInsurance", "productType", "nomineeUnderSection", "productInfo" })
public class ProductDetails {


    @JsonProperty("isUlipProduct")
    private String isUlipProduct;

    @JsonProperty("needOfInsurance")
    private String needOfInsurance;
    @JsonProperty("lifeStage")
    private String lifeStage;
    @JsonProperty("objectiveOfInsurance")
    private String objectiveOfInsurance;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("nomineeUnderSection")
    private String nomineeUnderSection;
    @JsonProperty("productInfo")
    private ProductInfo productInfo;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductDetails() {
    }

    public ProductDetails(String needOfInsurance, String lifeStage, String objectiveOfInsurance, String productType,
                          String nomineeUnderSection, ProductInfo productInfo) {
        this.needOfInsurance = needOfInsurance;
        this.lifeStage = lifeStage;
        this.objectiveOfInsurance = objectiveOfInsurance;
        this.productType = productType;
        this.nomineeUnderSection = nomineeUnderSection;
        this.productInfo = productInfo;
    }

    @JsonProperty("needOfInsurance")
    public String getNeedOfInsurance() {
        return needOfInsurance;
    }

    @JsonProperty("needOfInsurance")
    public void setNeedOfInsurance(String needOfInsurance) {
        this.needOfInsurance = needOfInsurance;
    }

    @JsonProperty("lifeStage")
    public String getLifeStage() {
        return lifeStage;
    }

    @JsonProperty("lifeStage")
    public void setLifeStage(String lifeStage) {
        this.lifeStage = lifeStage;
    }

    @JsonProperty("objectiveOfInsurance")
    public String getObjectiveOfInsurance() {
        return objectiveOfInsurance;
    }

    @JsonProperty("objectiveOfInsurance")
    public void setObjectiveOfInsurance(String objectiveOfInsurance) {
        this.objectiveOfInsurance = objectiveOfInsurance;
    }

    @JsonProperty("productType")
    public String getProductType() {
        return productType;
    }

    @JsonProperty("productType")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    @JsonProperty("nomineeUnderSection")
    public String getNomineeUnderSection() {
        return nomineeUnderSection;
    }

    @JsonProperty("nomineeUnderSection")
    public void setNomineeUnderSection(String nomineeUnderSection) {
        this.nomineeUnderSection = nomineeUnderSection;
    }

    @JsonProperty("productInfo")
    public ProductInfo getProductInfo() {
        return productInfo;
    }

    @JsonProperty("productInfo")
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getIsUlipProduct() {
        return isUlipProduct;
    }

    public void setIsUlipProduct(String isUlipProduct) {
        this.isUlipProduct = isUlipProduct;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ProductDetails{" +
                "needOfInsurance='" + needOfInsurance + '\'' +
                ", lifeStage='" + lifeStage + '\'' +
                ", objectiveOfInsurance='" + objectiveOfInsurance + '\'' +
                ", productType='" + productType + '\'' +
                ", nomineeUnderSection='" + nomineeUnderSection + '\'' +
                ", productInfo=" + productInfo +
                ", isUlipProduct=" + isUlipProduct +
                '}';
    }
}
