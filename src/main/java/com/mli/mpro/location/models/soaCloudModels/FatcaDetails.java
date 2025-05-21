package com.mli.mpro.location.models.soaCloudModels;
import com.fasterxml.jackson.annotation.JsonProperty;
public class FatcaDetails {
        @JsonProperty("psprtNum")
        private String passportNumber;

        @JsonProperty("psprtIssCntry")
        private String passportIssuingCountry;

        @JsonProperty("cliOverseasAddrTyp")
        private String clientOverseasAddressType;

        @JsonProperty("cliOverseasAddr")
        private String clientOverseasAddress;

        @JsonProperty("crntCntryRsdnc")
        private String currentCountryOfResidence;

        @JsonProperty("foreignTaxId")
        private String foreignTaxId;

        @JsonProperty("ftinIssueCntry")
        private String ftinIssuingCountry;

        @JsonProperty("clientResCntry")
        private String clientResidenceCountry;

        @JsonProperty("clientCitizenship")
        private String clientCitizenship;

        @JsonProperty("clientBirthCntry")
        private String clientBirthCountry;

        @JsonProperty("psprtIssDt")
        private String passportIssueDate;

        @JsonProperty("cliVsaTyp")
        private String clientVisaType;

        @JsonProperty("vsaVldDt")
        private String visaValidDate;

        @JsonProperty("cliOthrTxId")
        private String clientOtherTaxId;

        @JsonProperty("cliOthrTxNum")
        private String clientOtherTaxNumber;

        @JsonProperty("cliOvrUid")
        private String clientOverseasUniqueId;

        @JsonProperty("cliOvrUidNum")
        private String clientOverseasUniqueIdNumber;

        @JsonProperty("cliOvrUidCnty")
        private String clientOverseasUniqueIdCountry;

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportIssuingCountry() {
        return passportIssuingCountry;
    }

    public void setPassportIssuingCountry(String passportIssuingCountry) {
        this.passportIssuingCountry = passportIssuingCountry;
    }

    public String getClientOverseasAddressType() {
        return clientOverseasAddressType;
    }

    public void setClientOverseasAddressType(String clientOverseasAddressType) {
        this.clientOverseasAddressType = clientOverseasAddressType;
    }

    public String getClientOverseasAddress() {
        return clientOverseasAddress;
    }

    public void setClientOverseasAddress(String clientOverseasAddress) {
        this.clientOverseasAddress = clientOverseasAddress;
    }

    public String getCurrentCountryOfResidence() {
        return currentCountryOfResidence;
    }

    public void setCurrentCountryOfResidence(String currentCountryOfResidence) {
        this.currentCountryOfResidence = currentCountryOfResidence;
    }

    public String getForeignTaxId() {
        return foreignTaxId;
    }

    public void setForeignTaxId(String foreignTaxId) {
        this.foreignTaxId = foreignTaxId;
    }

    public String getFtinIssuingCountry() {
        return ftinIssuingCountry;
    }

    public void setFtinIssuingCountry(String ftinIssuingCountry) {
        this.ftinIssuingCountry = ftinIssuingCountry;
    }

    public String getClientResidenceCountry() {
        return clientResidenceCountry;
    }

    public void setClientResidenceCountry(String clientResidenceCountry) {
        this.clientResidenceCountry = clientResidenceCountry;
    }

    public String getClientCitizenship() {
        return clientCitizenship;
    }

    public void setClientCitizenship(String clientCitizenship) {
        this.clientCitizenship = clientCitizenship;
    }

    public String getClientBirthCountry() {
        return clientBirthCountry;
    }

    public void setClientBirthCountry(String clientBirthCountry) {
        this.clientBirthCountry = clientBirthCountry;
    }

    public String getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(String passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getClientVisaType() {
        return clientVisaType;
    }

    public void setClientVisaType(String clientVisaType) {
        this.clientVisaType = clientVisaType;
    }

    public String getVisaValidDate() {
        return visaValidDate;
    }

    public void setVisaValidDate(String visaValidDate) {
        this.visaValidDate = visaValidDate;
    }

    public String getClientOtherTaxId() {
        return clientOtherTaxId;
    }

    public void setClientOtherTaxId(String clientOtherTaxId) {
        this.clientOtherTaxId = clientOtherTaxId;
    }

    public String getClientOtherTaxNumber() {
        return clientOtherTaxNumber;
    }

    public void setClientOtherTaxNumber(String clientOtherTaxNumber) {
        this.clientOtherTaxNumber = clientOtherTaxNumber;
    }

    public String getClientOverseasUniqueId() {
        return clientOverseasUniqueId;
    }

    public void setClientOverseasUniqueId(String clientOverseasUniqueId) {
        this.clientOverseasUniqueId = clientOverseasUniqueId;
    }

    public String getClientOverseasUniqueIdNumber() {
        return clientOverseasUniqueIdNumber;
    }

    public void setClientOverseasUniqueIdNumber(String clientOverseasUniqueIdNumber) {
        this.clientOverseasUniqueIdNumber = clientOverseasUniqueIdNumber;
    }

    public String getClientOverseasUniqueIdCountry() {
        return clientOverseasUniqueIdCountry;
    }

    public void setClientOverseasUniqueIdCountry(String clientOverseasUniqueIdCountry) {
        this.clientOverseasUniqueIdCountry = clientOverseasUniqueIdCountry;
    }

    @Override
    public String toString() {
        return "FatcaDetails{" +
                "passportNumber='" + passportNumber + '\'' +
                ", passportIssuingCountry='" + passportIssuingCountry + '\'' +
                ", clientOverseasAddressType='" + clientOverseasAddressType + '\'' +
                ", clientOverseasAddress='" + clientOverseasAddress + '\'' +
                ", currentCountryOfResidence='" + currentCountryOfResidence + '\'' +
                ", foreignTaxId='" + foreignTaxId + '\'' +
                ", ftinIssuingCountry='" + ftinIssuingCountry + '\'' +
                ", clientResidenceCountry='" + clientResidenceCountry + '\'' +
                ", clientCitizenship='" + clientCitizenship + '\'' +
                ", clientBirthCountry='" + clientBirthCountry + '\'' +
                ", passportIssueDate='" + passportIssueDate + '\'' +
                ", clientVisaType='" + clientVisaType + '\'' +
                ", visaValidDate='" + visaValidDate + '\'' +
                ", clientOtherTaxId='" + clientOtherTaxId + '\'' +
                ", clientOtherTaxNumber='" + clientOtherTaxNumber + '\'' +
                ", clientOverseasUniqueId='" + clientOverseasUniqueId + '\'' +
                ", clientOverseasUniqueIdNumber='" + clientOverseasUniqueIdNumber + '\'' +
                ", clientOverseasUniqueIdCountry='" + clientOverseasUniqueIdCountry + '\'' +
                '}';
    }
}
