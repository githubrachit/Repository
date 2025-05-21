
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.axis.models.BancaDetails;
import com.mli.mpro.common.models.TranformationFlags;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.location.labslist.models.ResponsePayload;
import com.mli.mpro.onboarding.brmsBroker.model.DiyBrmsFieldConfigurationDetails;
import com.mli.mpro.proposal.models.irpPsmForNeo.IrpTags;
import com.mli.mpro.proposal.models.irpPsmForNeo.Suitability;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "transactionId", "version", "stage", "applicationDetails", "sourcing Details", "channelDetails", "partyInformation",
	"productDetails", "medicalInfo", "employmentDetails", "nomineeDetails", "insuranceDetails", "bank", "payorDetails", "ckycDetails", "lifeStyleDetails",
	"irp", "paymentDetails", "underwritingServiceDetails", "csgDetails", "posvDetails", "policyProcessingBackflowDetails","brmsFieldConfigurationDetails","tranformationFlags","medicalListResponse" })
@Document(collection = "proposal")
public class ProposalDetails {
    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("leadId")
    private String leadId;
    @JsonProperty("secondaryLeadId")
    private String secondaryLeadId;
    @JsonProperty("equoteNumber")
    private String equoteNumber;
    @JsonProperty("secondaryEquoteNumber")
    private String secondaryEquoteNumber;
    @JsonProperty("transactionId")
    private long transactionId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("secondaryPolicyNumber")
    private String secondaryPolicyNumber;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("primaryPolicyNumber")
    private String primaryPolicyNumber;
    @JsonProperty("version")
    private String version;
    @JsonProperty("nMQuestionsLinkExpired")
    private Boolean nMQuestionsLinkExpired=false;
    @JsonProperty("nonMandatoryQuestionSubmitFlag")
    private Boolean nonMandatoryQuestionSubmitFlag;
    @JsonProperty("applicationDetails")
    private ApplicationDetails applicationDetails;
    @JsonProperty("sourcingDetails")
    private SourcingDetails sourcingDetails;
    @JsonProperty("channelDetails")
    private ChannelDetails channelDetails;
    @JsonProperty("partyInformation")
    private List<PartyInformation> partyInformation = null;
    @JsonProperty("productDetails")
    private List<ProductDetails> productDetails = null;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("medicalInfo")
    private MedicalInfo medicalInfo;
    @JsonProperty("employmentDetails")
    private EmploymentDetails employmentDetails;
    @JsonProperty("nomineeDetails")
    private NomineeDetails nomineeDetails;
    @JsonProperty("bank")
    private Bank bank;
    @JsonProperty("form60Details")
    private Form60Details form60Details;
    @JsonProperty("ckycDetails")
    private CkycDetails ckycDetails;
    @JsonProperty("ekycDetails")
    private EkycDetails ekycDetails;
    @JsonProperty("ekycResponse")
    private EkycResponse ekycResponse;
    @JsonProperty("lifeStyleDetails")
    private List<LifeStyleDetails> lifeStyleDetails;
    @JsonProperty("irp")
    private Irp irp;
    @JsonProperty("s3Upload")
    private List<S3Upload> s3Upload;
    @JsonProperty("paymentDetails")
    private PaymentDetails paymentDetails;
    @JsonProperty("additionalFlags")
    private AdditionalFlags additionalFlags;
    @JsonProperty("underwritingServiceDetails")
    private UnderwritingServiceDetails underwritingServiceDetails;
    @JsonProperty("csgDetails")
    private CSGDetails csgDetails;
    @JsonProperty("posvDetails")
    private PosvDetails posvDetails;
    @JsonProperty("policyProcessingBackflowDetails")
    private PolicyProcessingBackflowDetails policyProcessingBackflowDetails;
    @JsonProperty("bancaDetails")
    private BancaDetails bancaDetails;
    @JsonProperty("tranformationFlags")
    private TranformationFlags tranformationFlags;
    @JsonProperty("yblDetails")
    private YBLDetails yblDetails;
    @JsonProperty("partnerDetails")
    private PartnerDetails partnerDetails;
    @JsonProperty("salesStoriesProductDetails")
    private SalesStoriesProductDetails salesStoriesProductDetails;
    private PSMDetails psmDetails;
    private String bankJourney;
    private com.mli.mpro.proposal.models.BancaDetails axisBancaDetails;
    @JsonProperty("ckycDataResponse")
    private CkycDataResponse ckycDataResponse;
    @JsonProperty("sellerDeclaration")
    private com.mli.mpro.proposal.models.SellerDeclaration sellerDeclaration;
    private List<LifeStyleDetails> secondaryLifeStyleDetails;
    @JsonProperty("secondaryPEPDetails")
    private List<PEPDetails> secondaryPEPDetails;
    @JsonProperty("secondaryInsuranceDetails")
    private List<InsuranceDetails> secondaryInsuranceDetails;

    private SellerConsentDetails sellerConsentDetails;
    private boolean thanosDolphinIntegrationEnabled;
    @JsonProperty("ccFlag")
    private String ccFlag;
    private String isAxisJourney;

    @JsonProperty("physicalJourneyDetails")
    private PhysicalJourneyDetails physicalJourneyDetails;
    @JsonProperty("oasDetails")
    private OasDetails oasDetails;

    @JsonProperty("posvViaBrmsDetails")
    private PosvViaBrmsDetails posvViaBrmsDetails;

    @JsonProperty("brmsFieldConfigurationDetails")
    private BrmsFieldConfigurationDetails brmsFieldConfigurationDetails;

    @JsonProperty("diyBrmsFieldConfigurationDetails")
    private DiyBrmsFieldConfigurationDetails diyBrmsFieldConfigurationDetails;

    @JsonProperty("pasaDetails")
    private PasaDetails pasaDetails;

    private NonEditableNonMandatoryFields nonEditableNonMandatoryFields;

    @JsonProperty("medicalListResponse")
    private Map<String, com.mli.mpro.location.labslist.models.ResponsePayload> medicalListResponse;
    @JsonProperty("quotePayload")
    private QuotePayload quotePayload;
    @JsonProperty("consentDetails")
    private ConsentDetails consentDetails;
    @JsonProperty("medicalSuppress")
    private String medicalSuppress;
    @JsonProperty("reasonForSuppress")
    private String reasonForSuppress;
    @JsonProperty("isWipCaseForRateChange")
    private String isWipCaseForRateChange;

    @JsonProperty("suitability")
    private Suitability suitability;
    @JsonProperty("neoIrpDetails")
    private IrpTags neoIrp;

    public ConsentDetails getConsentDetails() {
        return consentDetails;
    }

    public void setConsentDetails(ConsentDetails consentDetails) {
        this.consentDetails = consentDetails;
    }
    public QuotePayload getQuotePayload() {
        return quotePayload;
    }

    public void setQuotePayload(QuotePayload quotePayload) {
        this.quotePayload = quotePayload;
    }

    /**
     * No args constructor for use in serialization
     *
     */

    public ProposalDetails() {
    }

    public ProposalDetails(ApplicationDetails applicationDetails) {
	super();
	this.applicationDetails = applicationDetails;
    }

    public ProposalDetails(String id, long transactionId, String version, ApplicationDetails applicationDetails, SourcingDetails sourcingDetails,
	    ChannelDetails channelDetails, List<PartyInformation> partyInformation, List<ProductDetails> productDetails, MedicalInfo medicalInfo,
	    EmploymentDetails employmentDetails, NomineeDetails nomineeDetails, Bank bank, Form60Details form60Details, CkycDetails ckycDetails,
	    List<LifeStyleDetails> lifeStyleDetails, Irp irp, List<S3Upload> s3Upload, PaymentDetails paymentDetails, AdditionalFlags additionalFlags,
	    UnderwritingServiceDetails underwritingServiceDetails, CSGDetails csgDetails, PosvDetails posvDetails,
	    PolicyProcessingBackflowDetails policyProcessingBackflowDetails, BancaDetails bancaDetails, TranformationFlags tranformationFlags,
	    YBLDetails yblDetails, SalesStoriesProductDetails salesStoriesProductDetails, PSMDetails psmDetails, ConsentDetails consentDetails) {
	super();
	this.id = id;
	this.transactionId = transactionId;
	this.version = version;
	this.applicationDetails = applicationDetails;
	this.sourcingDetails = sourcingDetails;
	this.channelDetails = channelDetails;
	this.partyInformation = partyInformation;
	this.productDetails = productDetails;
	this.medicalInfo = medicalInfo;
	this.employmentDetails = employmentDetails;
	this.nomineeDetails = nomineeDetails;
	this.bank = bank;
	this.form60Details = form60Details;
	this.ckycDetails = ckycDetails;
	this.lifeStyleDetails = lifeStyleDetails;
	this.irp = irp;
	this.s3Upload = s3Upload;
	this.paymentDetails = paymentDetails;
	this.additionalFlags = additionalFlags;
	this.underwritingServiceDetails = underwritingServiceDetails;
	this.csgDetails = csgDetails;
	this.posvDetails = posvDetails;
	this.policyProcessingBackflowDetails = policyProcessingBackflowDetails;
	this.bancaDetails = bancaDetails;
	this.tranformationFlags = tranformationFlags;
	this.yblDetails = yblDetails;
	this.salesStoriesProductDetails = salesStoriesProductDetails;
	this.psmDetails = psmDetails;
    this.consentDetails = consentDetails;
    }

    public ProposalDetails(ProposalDetails decryptedProposalDetails) {
	this.id = decryptedProposalDetails.id;
	this.version = decryptedProposalDetails.version;
	this.applicationDetails = decryptedProposalDetails.applicationDetails;
	this.sourcingDetails = decryptedProposalDetails.sourcingDetails;
	this.channelDetails = decryptedProposalDetails.channelDetails;
	this.partyInformation = decryptedProposalDetails.partyInformation;
	this.productDetails = decryptedProposalDetails.productDetails;
	this.medicalInfo = decryptedProposalDetails.medicalInfo;
	this.employmentDetails = decryptedProposalDetails.employmentDetails;
	this.nomineeDetails = decryptedProposalDetails.nomineeDetails;
	this.bank = decryptedProposalDetails.bank;
	this.form60Details = decryptedProposalDetails.form60Details;
	this.ckycDetails = decryptedProposalDetails.ckycDetails;
	this.lifeStyleDetails = decryptedProposalDetails.lifeStyleDetails;
	this.irp = decryptedProposalDetails.irp;
	this.s3Upload = decryptedProposalDetails.s3Upload;
	this.paymentDetails = decryptedProposalDetails.paymentDetails;
	this.additionalFlags = decryptedProposalDetails.additionalFlags;
	this.underwritingServiceDetails = decryptedProposalDetails.underwritingServiceDetails;
	this.csgDetails = decryptedProposalDetails.csgDetails;
	this.posvDetails = decryptedProposalDetails.posvDetails;
	this.policyProcessingBackflowDetails = decryptedProposalDetails.policyProcessingBackflowDetails;
	this.bancaDetails = decryptedProposalDetails.bancaDetails;
	this.tranformationFlags = decryptedProposalDetails.tranformationFlags;
	this.yblDetails = decryptedProposalDetails.yblDetails;

    }

    public ProposalDetails(ProposalDetails salesProposalDetails, int x) {
	this.transactionId = salesProposalDetails.salesStoriesProductDetails.getSecondaryTransactionId();
	this.id = salesProposalDetails.salesStoriesProductDetails.getSecondaryMongoId();
	this.version = salesProposalDetails.version;
	this.applicationDetails = new ApplicationDetails(salesProposalDetails.applicationDetails);
	this.sourcingDetails = new SourcingDetails(salesProposalDetails.sourcingDetails);
	this.channelDetails = new ChannelDetails(salesProposalDetails.channelDetails);

	List<PartyInformation> partyInformationList = salesProposalDetails.partyInformation;
	partyInformationList = (partyInformationList != null && partyInformationList.size() != 0) ? partyInformationList.stream().collect(Collectors.toList())
		: partyInformationList;
	this.partyInformation = partyInformationList;

	List<ProductDetails> productDetailsList = salesProposalDetails.productDetails;

	productDetailsList = (productDetailsList != null && productDetailsList.size() != 0) ? productDetailsList.stream().collect(Collectors.toList())
		: productDetailsList;
	Collections.reverse(productDetailsList);
	this.productDetails = productDetailsList;

	this.medicalInfo = new MedicalInfo(salesProposalDetails.medicalInfo);
	this.employmentDetails = salesProposalDetails.employmentDetails;
	this.nomineeDetails = new NomineeDetails(salesProposalDetails.nomineeDetails);
	this.bank = new Bank(salesProposalDetails.bank);
	this.form60Details = salesProposalDetails.form60Details;
	this.ckycDetails = salesProposalDetails.ckycDetails;

	List<LifeStyleDetails> lifeStyleDetailsList = salesProposalDetails.lifeStyleDetails;
	lifeStyleDetailsList = (lifeStyleDetailsList != null && lifeStyleDetailsList.size() != 0) ? lifeStyleDetailsList.stream().collect(Collectors.toList())
		: lifeStyleDetailsList;
	this.lifeStyleDetails = lifeStyleDetailsList;

	this.irp = new Irp(salesProposalDetails.irp);
	if (salesProposalDetails.paymentDetails != null) {
	    this.paymentDetails = new PaymentDetails(salesProposalDetails.paymentDetails);
	}
	this.additionalFlags = new AdditionalFlags(salesProposalDetails.additionalFlags);
	if (salesProposalDetails.csgDetails != null) {
	    this.csgDetails = new CSGDetails(salesProposalDetails.csgDetails);
	}
	this.bancaDetails = new BancaDetails(salesProposalDetails.bancaDetails);
	this.tranformationFlags = new TranformationFlags(salesProposalDetails.tranformationFlags);
	this.yblDetails = new YBLDetails(salesProposalDetails.yblDetails);
	this.salesStoriesProductDetails = new SalesStoriesProductDetails(salesProposalDetails.salesStoriesProductDetails);
    this.consentDetails = new ConsentDetails(salesProposalDetails.consentDetails);
    }

    @JsonProperty("id")
    public String getId() {
	return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
	this.id = id;
    }

    @JsonProperty("transactionId")
    public long getTransactionId() {
	return transactionId;
    }

    @JsonProperty("transactionId")
    public void setTransactionId(long l) {
	this.transactionId = l;
    }

    @JsonProperty("version")
    public String getVersion() {
	return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
	this.version = version;
    }

    @JsonProperty("applicationDetails")
    public ApplicationDetails getApplicationDetails() {
	return applicationDetails;
    }

    @JsonProperty("applicationDetails")
    public void setApplicationDetails(ApplicationDetails applicationDetails) {
	this.applicationDetails = applicationDetails;
    }

    @JsonProperty("sourcingDetails")
    public SourcingDetails getSourcingDetails() {
	return sourcingDetails;
    }

    @JsonProperty("sourcingDetails")
    public void setSourcingDetails(SourcingDetails sourcingDetails) {
	this.sourcingDetails = sourcingDetails;
    }

    @JsonProperty("channelDetails")
    public ChannelDetails getChannelDetails() {
	return channelDetails;
    }

    @JsonProperty("channelDetails")
    public void setChannelDetails(ChannelDetails channelDetails) {
	this.channelDetails = channelDetails;
    }

    @JsonProperty("partyInformation")
    public List<PartyInformation> getPartyInformation() {
	return partyInformation;
    }

    @JsonProperty("partyInformation")
    public void setPartyInformation(List<PartyInformation> partyInformation) {
	this.partyInformation = partyInformation;
    }

    @JsonProperty("productDetails")
    public List<ProductDetails> getProductDetails() {
	return productDetails;
    }

    @JsonProperty("productDetails")
    public void setProductDetails(List<ProductDetails> productDetails) {
	this.productDetails = productDetails;
    }

    @JsonProperty("medicalInfo")
    public MedicalInfo getMedicalInfo() {
	return medicalInfo;
    }

    @JsonProperty("medicalInfo")
    public void setMedicalInfo(MedicalInfo medicalInfo) {
	this.medicalInfo = medicalInfo;
    }

    @JsonProperty("employmentDetails")
    public EmploymentDetails getEmploymentDetails() {
	return employmentDetails;
    }

    @JsonProperty("employmentDetails")
    public void setEmploymentDetails(EmploymentDetails employmentDetails) {
	this.employmentDetails = employmentDetails;
    }

    @JsonProperty("nomineeDetails")
    public NomineeDetails getNomineeDetails() {
	return nomineeDetails;
    }

    @JsonProperty("nomineeDetails")
    public void setNomineeDetails(NomineeDetails nomineeDetails) {
	this.nomineeDetails = nomineeDetails;
    }

    @JsonProperty("bank")
    public Bank getBank() {
	return bank;
    }

    @JsonProperty("bank")
    public void setBank(Bank bank) {
	this.bank = bank;
    }

    @JsonProperty("ckycDetails")
    public CkycDetails getCkycDetails() {
	return ckycDetails;
    }

    @JsonProperty("ckycDetails")
    public void setCkycDetails(CkycDetails ckycDetails) {
	this.ckycDetails = ckycDetails;
    }

    @JsonProperty("lifeStyleDetails")
    public List<LifeStyleDetails> getLifeStyleDetails() {
	return lifeStyleDetails;
    }

    @JsonProperty("lifeStyleDetails")
    public void setLifeStyleDetails(List<LifeStyleDetails> lifeStyleDetails) {
	this.lifeStyleDetails = lifeStyleDetails;
    }

    @JsonProperty("irp")
    public Irp getIrp() {
	return irp;
    }

    @JsonProperty("irp")
    public void setIrp(Irp irp) {
	this.irp = irp;
    }

    @JsonProperty("paymentDetails")
    public PaymentDetails getPaymentDetails() {
	return paymentDetails;
    }

    @JsonProperty("paymentDetails")
    public void setPaymentDetails(PaymentDetails paymentDetails) {
	this.paymentDetails = paymentDetails;
    }

    @JsonProperty("form60Details")
    public Form60Details getForm60Details() {
	return form60Details;
    }

    @JsonProperty("form60Details")
    public void setForm60Details(Form60Details form60Details) {
	this.form60Details = form60Details;
    }

  public SellerConsentDetails getSellerConsentDetails() {
    return sellerConsentDetails;
  }

  public void setSellerConsentDetails(
      SellerConsentDetails sellerConsentDetails) {
    this.sellerConsentDetails = sellerConsentDetails;
  }

    public List<S3Upload> getS3Upload() {
	return s3Upload;
    }

    public void setS3Upload(List<S3Upload> s3Upload) {
	this.s3Upload = s3Upload;
    }

    public AdditionalFlags getAdditionalFlags() {
	return additionalFlags;
    }

    public void setAdditionalFlags(AdditionalFlags additionalFlags) {
	this.additionalFlags = additionalFlags;
    }

    public UnderwritingServiceDetails getUnderwritingServiceDetails() {
	return underwritingServiceDetails;
    }

    public void setUnderwritingServiceDetails(UnderwritingServiceDetails underwritingServiceDetails) {
	this.underwritingServiceDetails = underwritingServiceDetails;
    }

    public CSGDetails getCsgDetails() {
	return csgDetails;
    }

    public void setCsgDetails(CSGDetails csgDetails) {
	this.csgDetails = csgDetails;
    }

    public PosvDetails getPosvDetails() {
	return posvDetails;
    }

    public void setPosvDetails(PosvDetails posvDetails) {
	this.posvDetails = posvDetails;
    }

    public PolicyProcessingBackflowDetails getPolicyProcessingBackflowDetails() {
	return policyProcessingBackflowDetails;
    }

    public void setPolicyProcessingBackflowDetails(PolicyProcessingBackflowDetails policyProcessingBackflowDetails) {
	this.policyProcessingBackflowDetails = policyProcessingBackflowDetails;
    }

    public PasaDetails getPasaDetails() {
        return pasaDetails;
    }

    public void setPasaDetails(PasaDetails pasaDetails) {
        this.pasaDetails = pasaDetails;
    }

    public BancaDetails getBancaDetails() {
	return bancaDetails;
    }

    public void setBancaDetails(BancaDetails bancaDetails) {
	this.bancaDetails = bancaDetails;
    }

    public TranformationFlags getTranformationFlags() {
	return tranformationFlags;
    }

    public void setTranformationFlags(TranformationFlags tranformationFlags) {
	this.tranformationFlags = tranformationFlags;
    }

    public YBLDetails getYblDetails() {
	return yblDetails;
    }

    public void setYblDetails(YBLDetails yblDetails) {
	this.yblDetails = yblDetails;
    }

    public SalesStoriesProductDetails getSalesStoriesProductDetails() {
	return salesStoriesProductDetails;
    }

    public void setSalesStoriesProductDetails(SalesStoriesProductDetails SalesStoriesProductDetails) {
	this.salesStoriesProductDetails = SalesStoriesProductDetails;
    }


    public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getEquoteNumber() {
		return equoteNumber;
	}

	public void setEquoteNumber(String equoteNumber) {
		this.equoteNumber = equoteNumber;
	}

    public String getSecondaryPolicyNumber() {
        return secondaryPolicyNumber;
    }

    public String getPrimaryPolicyNumber() {
      return primaryPolicyNumber;
    }

    public void setPrimaryPolicyNumber(String primaryPolicyNumber) {
      this.primaryPolicyNumber = primaryPolicyNumber;
    }

  public String getSecondaryLeadId() {
        return secondaryLeadId;
    }

    public void setSecondaryLeadId(String secondaryLeadId) {
        this.secondaryLeadId = secondaryLeadId;
    }

    public String getSecondaryEquoteNumber() {
        return secondaryEquoteNumber;
    }

    public void setSecondaryEquoteNumber(String secondaryEquoteNumber) {
        this.secondaryEquoteNumber = secondaryEquoteNumber;
    }

    public void setSecondaryPolicyNumber(String secondaryPolicyNumber) {
        this.secondaryPolicyNumber = secondaryPolicyNumber;
    }

    public PSMDetails getPsmDetails() {
        return psmDetails;
    }

    public void setPsmDetails(PSMDetails psmDetails) {
        this.psmDetails = psmDetails;
    }

    public String getBankJourney() { return bankJourney; }

    public void setBankJourney(String bankJourney) { this.bankJourney = bankJourney; }

    @JsonProperty("nMQuestionsLinkExpired")
    public Boolean isnMQuestionsLinkExpired() { return nMQuestionsLinkExpired; }

    @JsonProperty("nMQuestionsLinkExpired")
    public void setnMQuestionsLinkExpired(Boolean nMQuestionsLinkExpired) {
        this.nMQuestionsLinkExpired = nMQuestionsLinkExpired;
    }

    public Boolean getNonMandatoryQuestionSubmitFlag() { return nonMandatoryQuestionSubmitFlag; }

    public void setNonMandatoryQuestionSubmitFlag(Boolean nonMandatoryQuestionSubmitFlag) {
        this.nonMandatoryQuestionSubmitFlag = nonMandatoryQuestionSubmitFlag;
    }

    public com.mli.mpro.proposal.models.BancaDetails getAxisBancaDetails() {
        return axisBancaDetails;
    }

    public void setAxisBancaDetails(com.mli.mpro.proposal.models.BancaDetails axisBancaDetails) {
        this.axisBancaDetails = axisBancaDetails;
    }

    public CkycDataResponse getCkycDataResponse() {
        return ckycDataResponse;
    }

    public ProposalDetails setCkycDataResponse(CkycDataResponse ckycDataResponse) {
        this.ckycDataResponse = ckycDataResponse;
        return this;
    }

    public EkycDetails getEkycDetails() {
        return ekycDetails;
    }

    public ProposalDetails setEkycDetails(EkycDetails ekycDetails) {
        this.ekycDetails = ekycDetails;
        return this;
    }


    public com.mli.mpro.proposal.models.SellerDeclaration getSellerDeclaration() {
        return sellerDeclaration;
    }

    public void setSellerDeclaration(com.mli.mpro.proposal.models.SellerDeclaration sellerDeclaration) {
        this.sellerDeclaration = sellerDeclaration;
    }

  public List<LifeStyleDetails> getSecondaryLifeStyleDetails() {
    return secondaryLifeStyleDetails;
  }

  public void setSecondaryLifeStyleDetails(
      List<LifeStyleDetails> secondaryLifeStyleDetails) {
    this.secondaryLifeStyleDetails = secondaryLifeStyleDetails;
  }

  public List<PEPDetails> getSecondaryPEPDetails() {
    return secondaryPEPDetails;
  }

  public void setSecondaryPEPDetails(
      List<PEPDetails> secondaryPEPDetails) {
    this.secondaryPEPDetails = secondaryPEPDetails;
  }

  public List<InsuranceDetails> getSecondaryInsuranceDetails() {
    return secondaryInsuranceDetails;
  }

  public void setSecondaryInsuranceDetails(
      List<InsuranceDetails> secondaryInsuranceDetails) {
    this.secondaryInsuranceDetails = secondaryInsuranceDetails;
  }

    public EkycResponse getEkycResponse() {
        return ekycResponse;
    }

    public ProposalDetails setEkycResponse(EkycResponse ekycResponse) {
        this.ekycResponse = ekycResponse;
        return this;
    }

  public boolean isThanosDolphinIntegrationEnabled() {
    return thanosDolphinIntegrationEnabled;
  }

  public void setThanosDolphinIntegrationEnabled(boolean thanosDolphinIntegrationEnabled) {
    this.thanosDolphinIntegrationEnabled = thanosDolphinIntegrationEnabled;
  }

    public String getCcFlag() {
        return ccFlag;
    }

    public void setCcFlag(String ccFlag) {
        this.ccFlag = ccFlag;
    }

    public PhysicalJourneyDetails getPhysicalJourneyDetails() {
        return physicalJourneyDetails;
    }

    public void setPhysicalJourneyDetails(PhysicalJourneyDetails physicalJourneyDetails) {
        this.physicalJourneyDetails = physicalJourneyDetails;
    }
    public OasDetails getOasDetails() {
        return oasDetails;
    }

    public void setOasDetails(OasDetails oasDetails) {
        this.oasDetails = oasDetails;
    }

    public String getIsAxisJourney() { return isAxisJourney; }

    public void setIsAxisJourney(String isAxisJourney) { this.isAxisJourney = isAxisJourney; }

    public PosvViaBrmsDetails getPosvViaBrmsDetails() {
        return posvViaBrmsDetails;
    }

    public void setPosvViaBrmsDetails(PosvViaBrmsDetails posvViaBrmsDetails) {
        this.posvViaBrmsDetails = posvViaBrmsDetails;
    }

    public BrmsFieldConfigurationDetails getBrmsFieldConfigurationDetails() {
        return brmsFieldConfigurationDetails;
    }

    public void setBrmsFieldConfigurationDetails(BrmsFieldConfigurationDetails brmsFieldConfigurationDetails) {
        this.brmsFieldConfigurationDetails = brmsFieldConfigurationDetails;
    }

    public DiyBrmsFieldConfigurationDetails getDiyBrmsFieldConfigurationDetails() {
        return diyBrmsFieldConfigurationDetails;
    }

    public void setDiyBrmsFieldConfigurationDetails(DiyBrmsFieldConfigurationDetails diyBrmsFieldConfigurationDetails) {
        this.diyBrmsFieldConfigurationDetails = diyBrmsFieldConfigurationDetails;
    }

    public Map<String, ResponsePayload> getMedicalListResponse() {
        return medicalListResponse;
    }

    public void setMedicalListResponse(Map<String, ResponsePayload> medicalListResponse) {
        this.medicalListResponse = medicalListResponse;
    }


    public NonEditableNonMandatoryFields getNonEditableNonMandatoryFields() {
        return nonEditableNonMandatoryFields;
    }

    public void setNonEditableNonMandatoryFields(NonEditableNonMandatoryFields nonEditableNonMandatoryFields) {
        this.nonEditableNonMandatoryFields = nonEditableNonMandatoryFields;
    }

    public PartnerDetails getPartnerDetails() {
        return partnerDetails;
    }

    public void setPartnerDetails(PartnerDetails partnerDetails) {
        this.partnerDetails = partnerDetails;
    }


    public String getMedicalSuppress() {
        return medicalSuppress;
    }

    public void setMedicalSuppress(String medicalSuppress) {
        this.medicalSuppress = medicalSuppress;
    }

    public String getReasonForSuppress() {
        return reasonForSuppress;
    }

    public void setReasonForSuppress(String reasonForSuppress) {
        this.reasonForSuppress = reasonForSuppress;
    }

    public String getIsWipCaseForRateChange() {
        return isWipCaseForRateChange;
    }

    public void setIsWipCaseForRateChange(String isWipCaseForRateChange) {
        this.isWipCaseForRateChange = isWipCaseForRateChange;
    }

    public Suitability getSuitability() {
        return suitability;
    }

    public void setSuitability(Suitability suitability) {
        this.suitability = suitability;
    }

    public IrpTags getNeoIrp() {
        return neoIrp;
    }

    public void setNeoIrp(IrpTags neoIrp) {
        this.neoIrp = neoIrp;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ProposalDetails [" +
                "id='" + id + '\'' +
                ", leadId='" + leadId + '\'' +
                ", equoteNumber='" + equoteNumber + '\'' +
                ", secondaryLeadId='" + secondaryLeadId + '\'' +
                ", secondaryEquoteNumber='" + secondaryEquoteNumber + '\'' +
                ", secondaryPolicyNumber='" + secondaryPolicyNumber + '\'' +
                ", transactionId=" + transactionId +
                ", version='" + version + '\'' +
                ", nMQuestionsLinkExpired=" + nMQuestionsLinkExpired +
                ", nonMandatoryQuestionSubmitFlag=" + nonMandatoryQuestionSubmitFlag +
                ", applicationDetails=" + applicationDetails +
                ", sourcingDetails=" + sourcingDetails +
                ", channelDetails=" + channelDetails +
                ", partyInformation=" + partyInformation +
                ", productDetails=" + productDetails +
                ", medicalInfo=" + medicalInfo +
                ", employmentDetails=" + employmentDetails +
                ", nomineeDetails=" + nomineeDetails +
                ", bank=" + bank +
                ", form60Details=" + form60Details +
                ", ckycDetails=" + ckycDetails +
                ", ekycDetails=" + ekycDetails +
                ", lifeStyleDetails=" + lifeStyleDetails +
                ", irp=" + irp +
                ", s3Upload=" + s3Upload +
                ", paymentDetails=" + paymentDetails +
                ", additionalFlags=" + additionalFlags +
                ", underwritingServiceDetails=" + underwritingServiceDetails +
                ", csgDetails=" + csgDetails +
                ", posvDetails=" + posvDetails +
                ", policyProcessingBackflowDetails=" + policyProcessingBackflowDetails +
                ", bancaDetails=" + bancaDetails +
                ", tranformationFlags=" + tranformationFlags +
                ", yblDetails=" + yblDetails +
                ", partnerDetails=" + partnerDetails +
                ", salesStoriesProductDetails=" + salesStoriesProductDetails +
                ", psmDetails=" + psmDetails +
                ", bankJourney='" + bankJourney + '\'' +
                ", axisBancaDetails=" + axisBancaDetails +
                ", ckycDataResponse=" + ckycDataResponse +
                ", sellerDeclaration=" + sellerDeclaration +
                ", sellerConsentDetails=" + sellerConsentDetails +
                ", physicalJourneyDetails=" + physicalJourneyDetails +
                ", oasDetails=" + oasDetails +
                ", consentDetails=" + consentDetails +
                ", isAxisJourney=" + isAxisJourney +
                ", posvViaBrmsDetails=" + posvViaBrmsDetails +
                ", posvViaBrmsDetails=" + posvViaBrmsDetails +
                ", pasaDetails=" + pasaDetails +
                ", brmsFieldConfigurationDetails=" + brmsFieldConfigurationDetails +
                ", medicalListResponse=" + medicalListResponse +
                ", nonEditableNonMandatoryFields=" + nonEditableNonMandatoryFields +
                ", medicalSuppress=" + medicalSuppress +
                ", reasonForSuppress=" + reasonForSuppress +
                ", isWipCaseForRateChange=" + isWipCaseForRateChange +
                ", suitability=" + suitability +
                ", neoIrp=" + neoIrp +
                ']';
    }
}
