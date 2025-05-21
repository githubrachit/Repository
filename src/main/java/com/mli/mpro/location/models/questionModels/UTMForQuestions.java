package com.mli.mpro.location.models.questionModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UTMForQuestions {

    @Pattern(regexp = "^(?!\\s)[a-z-A-Z0-9 ]{0,10}$", message = "Invalid Channel")
    public String channel;
    @Pattern(regexp = "^[0-9]{0,5}$", message = "Invalid product Id ")
    public String product;
    public List<String> required;
    @Pattern(regexp = "^(?!\\s)[a-z-A-Z ]{0,10}$", message = "Invalid isJointLife")
    public String isJointLife;
    @Pattern(regexp = "^(?!\\s)[a-z-A-Z ]{0,10}$", message = "Invalid isSspSwissReCase")
    public String isSspSwissReCase;

    public UTMForQuestions(String channel, String product, List<String> required, String isJointLife, String isSspSwissReCase) {
        this.channel = channel;
        this.product = product;
        this.required = required;
        this.isJointLife = isJointLife;
        this.isSspSwissReCase = isSspSwissReCase;
    }

    public UTMForQuestions() {
    }

    public String getIsJointLife() {
        return isJointLife;
    }

    public void setIsJointLife(String isJointLife) {
        this.isJointLife = isJointLife;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getIsSspSwissReCase() {
        return isSspSwissReCase;
    }

    public void setIsSspSwissReCase(String isSspSwissReCase) {
        this.isSspSwissReCase = isSspSwissReCase;
    }

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UTMForQuestions utmForQuestions = (UTMForQuestions) o;
        return Objects.equals(channel, utmForQuestions.channel)
                && Objects.equals(product,utmForQuestions.product)
                && Objects.equals(required, utmForQuestions.required)
                && Objects.equals(isJointLife, utmForQuestions.isJointLife)
                && Objects.equals(isSspSwissReCase, utmForQuestions.isSspSwissReCase);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(channel,product,required,isJointLife,isSspSwissReCase);
    }

    public UUID generateUUID() {
        String uniqueString = channel+product+Utility.printJsonRequest(required)+isJointLife+isSspSwissReCase;
        return UUID.nameUUIDFromBytes(uniqueString.getBytes());
    }

    @Override
    public String toString() {
        return "UTMForQuestions{" +
                "channel='" + channel + '\'' +
                ", product='" + product + '\'' +
                ", required=" + required +
                ", isJointLife='" + isJointLife + '\'' +
                ", isSspSwissReCase='" + isSspSwissReCase + '\'' +
                '}';
    }
}
