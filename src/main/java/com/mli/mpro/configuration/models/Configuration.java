package com.mli.mpro.configuration.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.location.models.DisableProduct;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*This class has all the fields which are going to save in DB as a document*/
@Document(collection = "configuration")
public class Configuration {
    @Id
    private String id;
    private long sequenceNumber;
    /* key will be like INCOME, AGE etc. as per requirment */
    private String key;
    /* value will be the value with respect INCOME, AGE etc. */
    private String value;
    /* type will be used to show the use of object for, like UI, Server etc. */
    private String type;
    private Date createdDateForAdditionalLogic;
    private Date createdDate;
    //FUL2-11834 Branch code for Ybl telesales agents
    @Sensitive(MaskType.BRANCH_CODE)
    private List<String> branchCodes;

    private List<String> riderCodeList;
    private List<String> riderStatusCodes;
    private List<CIRiderAgeSA> ageGroupMaxSumAssured;
    private List<String> customerCodeList;

    @Sensitive(MaskType.AMOUNT)
    private double maxSumAssured;
    @Sensitive(MaskType.AMOUNT)
    private double minSumAssured;

    //PASA feature Flag
    private List<String> pasaChannelList;

    // FUL2-28268 videoConfig 
    private List<String> channel;
    private List<String> goCode;
    private List<String> productId;
    private Date ocrStartDate;
    private List<String> docProductList;
    private List<String> docChannelList;
    private Map<String, String> ocrDoc;
    private List<String> products;
    private Map<String, List<String>> teleSalesBranchCode;
    private List<String> schemeCode;
    private List<String> solId;
    private Object nonEditableFields;
    private List<DisableProduct> posDisableProductList;
    private List<DisableProduct> nonPosDisableProductList;
    private Map<String, String> channelRegex;
    private List<String> imfGoCodes;
    private List<String> policyStatusChannelList;
    private String agentId;

    private List<String> agentSegmentDesc;

    private Boolean enablePaymentRevamp;

    public Boolean getEnablePaymentRevamp() {
        return enablePaymentRevamp;
    }

    public void setEnablePaymentRevamp(Boolean enablePaymentRevamp) {
        this.enablePaymentRevamp = enablePaymentRevamp;
    }

    private List<String> sourceChannelForDisableFieldApi;
    private Map<String, HashingConfig> hashingConfig;

    public Map<String, HashingConfig> getHashingConfig() {
        return hashingConfig;
    }

    public void setHashingConfig(Map<String, HashingConfig> hashingConfig) {
        this.hashingConfig = hashingConfig;
    }

    public List<String> getAgentSegmentDesc() {
        return agentSegmentDesc;
    }

    public void setAgentSegmentDesc(List<String> agentSegmentDesc) {
        this.agentSegmentDesc = agentSegmentDesc;
    }

    public List<String> getImfGoCodes() {
        return imfGoCodes;
    }
    public void setImfGoCodes(List<String> imfGoCodes) {
        this.imfGoCodes = imfGoCodes;
    }

    public Map<String, String> getChannelRegex() {
        return channelRegex;
    }

    public void setChannelRegex(Map<String, String> channelRegex) {
        this.channelRegex = channelRegex;
    }

    public List<DisableProduct> getPosDisableProductList() {
        return posDisableProductList;
    }

    public void setPosDisableProductList(List<DisableProduct> posDisableProductList) {
        this.posDisableProductList = posDisableProductList;
    }

    public List<DisableProduct> getNonPosDisableProductList() {
        return nonPosDisableProductList;
    }

    public void setNonPosDisableProductList(List<DisableProduct> nonPosDisableProductList) {
        this.nonPosDisableProductList = nonPosDisableProductList;
    }

    public Object getNonEditableFields() { return nonEditableFields; }

    public void setNonEditableFields(Object nonEditableFields) { this.nonEditableFields = nonEditableFields; }

    public List<String> getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(List<String> schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<String> getSolId() {
        return solId;
    }

    public void setSolId(List<String> solId) {
        this.solId = solId;
    }
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public long getSequenceNumber() {
	return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Date getCreatedDateForAdditionalLogic() {
        return createdDateForAdditionalLogic;
    }

    public void setCreatedDateForAdditionalLogic(Date createdDateForAdditionalLogic) {
        this.createdDateForAdditionalLogic = createdDateForAdditionalLogic;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public List<String> getBranchCodes() {
        return branchCodes;
    }

    public void setBranchCodes(List<String> branchCodes) {
        this.branchCodes = branchCodes;
    }
    public List<String> getChannel() {
		return channel;
	}

	public void setChannel(List<String> channel) {
		this.channel = channel;
	}

	public List<String> getGoCode() {
		return goCode;
	}

	public void setGoCode(List<String> goCode) {
		this.goCode = goCode;
	}

    public Date getOcrStartDate() {
        return ocrStartDate;
    }

    public void setOcrStartDate(Date ocrStartDate) {
        this.ocrStartDate = ocrStartDate;
    }

    public List<String> getDocProductList() {
        return docProductList;
    }

    public void setDocProductList(List<String> docProductList) {
        this.docProductList = docProductList;
    }

    public List<String> getDocChannelList() {
        return docChannelList;
    }

    public void setDocChannelList(List<String> docChannelList) {
        this.docChannelList = docChannelList;
    }

    public Map<String, String> getOcrDoc() {
        return ocrDoc;
    }

    public void setOcrDoc(Map<String, String> ocrDoc) {
        this.ocrDoc = ocrDoc;
    }

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

    public Map<String, List<String>> getTeleSalesBranchCode() {
        return teleSalesBranchCode;
    }

    public void setTeleSalesBranchCode(Map<String, List<String>> teleSalesBranchCode) {
        this.teleSalesBranchCode = teleSalesBranchCode;
    }

    public List<String> getRiderCodeList() { return riderCodeList; }

    public void setRiderCodeList(List<String> riderCodeList) { this.riderCodeList = riderCodeList; }

    public List<String> getRiderStatusCodes() { return riderStatusCodes; }

    public void setRiderStatusCodes(List<String> riderStatusCodes) { this.riderStatusCodes = riderStatusCodes; }

    public List<CIRiderAgeSA> getAgeGroupMaxSumAssured() { return ageGroupMaxSumAssured; }

    public void setAgeGroupMaxSumAssured(List<CIRiderAgeSA> ageGroupMaxSumAssured) { this.ageGroupMaxSumAssured = ageGroupMaxSumAssured; }

    public double getMaxSumAssured() { return maxSumAssured; }

    public void setMaxSumAssured(double maxSumAssured) { this.maxSumAssured = maxSumAssured; }

    public double getMinSumAssured() { return minSumAssured; }

    public void setMinSumAssured(double minSumAssured) { this.minSumAssured = minSumAssured; }
    
    public List<String> getCustomerCodeList() {
        return customerCodeList;
    }

    public void setCustomerCodeList(List<String> customerCodeList) {
        this.customerCodeList = customerCodeList;
    }
    public List<String> getPasaChannelList() {
        return pasaChannelList;
    }

    public void setPasaChannelList(List<String> pasaChannelList) {
        this.pasaChannelList = pasaChannelList;
    }

    public List<String> getProductId() {
        return productId;
    }

    public void setProductId(List<String> productId) {
        this.productId = productId;
    }

    public List<String> getSourceChannelForDisableFieldApi() {
        return sourceChannelForDisableFieldApi;
    }

    public void setSourceChannelForDisableFieldApi(List<String> sourceChannelForDisableFieldApi) {
        this.sourceChannelForDisableFieldApi = sourceChannelForDisableFieldApi;
    }

    public List<String> getPolicyStatusChannelList() {
        return policyStatusChannelList;
    }

    public void setPolicyStatusChannelList(List<String> policyStatusChannelList) {
        this.policyStatusChannelList = policyStatusChannelList;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "Configuration{" +
                "id='" + id + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", createdDate=" + createdDate +
                ", branchCodes=" + branchCodes +
                ", riderCodeList=" + riderCodeList +
                ", riderStatusCodes=" + riderStatusCodes +
                ", ageGroupMaxSumAssured=" + ageGroupMaxSumAssured +
                ", maxSumAssured=" + maxSumAssured +
                ", minSumAssured=" + minSumAssured +
                ", channel=" + channel +
                ", goCode=" + goCode +
                ", products=" + products +
                ", teleSalesBranchCode=" + teleSalesBranchCode +
                ", schemeCode=" + schemeCode +
                ", solId=" + solId +
                ", nonEditableFields=" + nonEditableFields +
                ", posDisableProductList=" + posDisableProductList +
                ", nonPosDisableProductList=" + nonPosDisableProductList +
                ", channelRegex=" + channelRegex +
                ", productId=" + productId +
                ", agentId=" + agentId +
                '}';
    }
}
