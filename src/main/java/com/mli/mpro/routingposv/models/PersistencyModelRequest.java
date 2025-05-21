package com.mli.mpro.routingposv.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistencyModelRequest {

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("MLI_PROPOSAL_NUMBER")
    private String mLIPROPOSALNUMBER;
    @JsonProperty("CASE_APP_RECD_DT")
    private String cASEAPPRECDDT;
    @JsonProperty("MLI_OTHER_OBJECTIVES_ID")
    private String mLIOTHEROBJECTIVESID;
    @JsonProperty("MLI_OTHER_OBJECTIVES_DSC")
    private String mLIOTHEROBJECTIVESDSC;
    @JsonProperty("MLI_OBJ_OF_INSURANCE")
    private String mLIOBJOFINSURANCE;
    @JsonProperty("MLI_OBJ_OF_INS_TYPE")
    private String mLIOBJOFINSTYPE;
    @JsonProperty("IS_RURAL_IND")
    private String iSRURALIND;
    @JsonProperty("IS_SOCIAL")
    private String iSSOCIAL;
    @JsonProperty("MLI_CHANNEL_CD")
    private String mLICHANNELCD;
    @JsonProperty("MLI_CHANNEL_NAME")
    private String mLICHANNELNAME;
    @JsonProperty("MLI_GO_CD")
    private String mLIGOCD;
    @JsonProperty("GO_CD")
    private String gOCD;
    @JsonProperty("GO_DESC")
    private String gODESC;
    @JsonProperty("NBPROD_ID")
    private String nBPRODID;
    @JsonProperty("PROD_CD")
    private String pRODCD;
    @JsonProperty("PROD_NM")
    private String pRODNM;
    @JsonProperty("MLI_PREM_PAY_TERM_CD")
    private String mLIPREMPAYTERMCD;
    @JsonProperty("MLI_PREM_PAY_TER_DSC")
    private String mLIPREMPAYTERDSC;
    @JsonProperty("PAYMENTTYPE")
    private String pAYMENTTYPE;
    @JsonProperty("MLI_AFYP")
    private String mLIAFYP;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("MLI_SUM_ASSURED")
    private String mLISUMASSURED;
    @JsonProperty("MLI_COVERAGE_TERM")
    private String mLICOVERAGETERM;
    @JsonProperty("MLI_PREM_PAY_TERM_UNITS_CD")
    private String mLIPREMPAYTERMUNITSCD;
    @JsonProperty("MLI_INIT_PRE_PAY_DET_CD")
    private String mLIINITPREPAYDETCD;
    @JsonProperty("MLI_INIT_PRE_PAY_DET_DESC")
    private String mLIINITPREPAYDETDESC;
    @JsonProperty("MLI_PREM_PAYMT_MODE_ID")
    private String mLIPREMPAYMTMODEID;
    @JsonProperty("MLI_PREM_PAYMT_MODE_DSC")
    private String mLIPREMPAYMTMODEDSC;
    @JsonProperty("MLI_PREMIUMOF_PAYMENT")
    private String mLIPREMIUMOFPAYMENT;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("MLI_PAN_CARD_NUMBER")
    private String mLIPANCARDNUMBER;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("MLI_PAN_NUM")
    private String mLIPANNUM;
    @JsonProperty("IS_PROPOSER_PAYER")
    private String iSPROPOSERPAYER;
    @JsonProperty("MLI_AGENT_CD")
    private String mLIAGENTCD;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("AGENT_CITY")
    private String aGENTCITY;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("AGENT_ZIP")
    private String aGENTZIP;
    @JsonProperty("PCLI_BIRTH_DT(P)")
    private String pCLIBIRTHDTP;
    @JsonProperty("GNDR_CD(P)")
    private String gNDRCDP;
    @JsonProperty("MLI_MARITAL_STATUS(P)")
    private String mLIMARITALSTATUSP;
    @JsonProperty("MLI_MARITAL_STATUS_DESC(P)")
    private String mLIMARITALSTATUSDESCP;
    @JsonProperty("EDUCATION_CD(P)")
    private String eDUCATIONCDP;
    @JsonProperty("EDUCATION_DESC(P)")
    private String eDUCATIONDESCP;
    @JsonProperty("INDUSTRY_TYP_CD(P)")
    private String iNDUSTRYTYPCDP;
    @JsonProperty("INDUSTRY_TYP_DESC(P)")
    private String iNDUSTRYTYPDESCP;
    @JsonProperty("OCC_TYP_CD(P)")
    private String oCCTYPCDP;
    @JsonProperty("OCC_TYP_DSC(P)")
    private String oCCTYPDSCP;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("MLI_EXACT_INCOME(P)")
    private String mLIEXACTINCOMEP;
    @JsonProperty("MLI_DOB_PROOF_CD(P)")
    private String mLIDOBPROOFCDP;
    @JsonProperty("MLI_CRA_PROOF(P)")
    private String mLICRAPROOFP;
    @JsonProperty("MLI_ID_PROOF(P)")
    private String mLIIDPROOFP;
    @JsonProperty("PCLI_BIRTH_DT (I)")
    private String pCLIBIRTHDTI;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("GNDR_CD(I)")
    private String gNDRCDI;
    @JsonProperty("MLI_MARITAL_STATUS(I)")
    private String mLIMARITALSTATUSI;
    @JsonProperty("MLI_MARITAL_STATUS_DESC(I)")
    private String mLIMARITALSTATUSDESCI;
    @JsonProperty("EDUCATION_CD(I)")
    private String eDUCATIONCDI;
    @JsonProperty("EDUCATION_DESC(I)")
    private String eDUCATIONDESCI;
    @JsonProperty("INDUSTRY_TYP_CD (I)")
    private String iNDUSTRYTYPCDI;
    @JsonProperty("INDUSTRY_TYP_DESC(I)")
    private String iNDUSTRYTYPDESCI;
    @JsonProperty("OCC_TYP_CD(I)")
    private String oCCTYPCDI;
    @JsonProperty("OCC_TYP_DSC(I)")
    private String oCCTYPDSCI;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("MLI_EXACT_INCOME(I)")
    private String mLIEXACTINCOMEI;
    @JsonProperty("MLI_DOB_PROOF_CD(I)")
    private String mLIDOBPROOFCDI;
    @JsonProperty("MLI_CRA_PROOF(I)")
    private String mLICRAPROOFI;
    @JsonProperty("MLI_ID_PROOF(I)")
    private String mLIIDPROOFI;
    @JsonProperty("MLI_REL_WITH_NOMINEE")
    private String mLIRELWITHNOMINEE;
    @JsonProperty("PCLI_TYP_CD")
    private String pCLITYPCD;
    @JsonProperty("PCLADR_ZIP_CD(I PRA)")
    private String pCLADRZIPCDIPRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(I PRA)")
    private String cITYNAMEIPRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(I PRA)")
    private String sTATENAMEIPRA;
    @JsonProperty("PCLADR_ZIP_CD (I CRA)")
    @Sensitive(MaskType.ADDRESS)
    private String pCLADRZIPCDICRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(I CRA)")
    private String cITYNAMEICRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(I CRA)")
    private String sTATENAMEICRA;
    @JsonProperty("PCLADR_ZIP_CD(I WA)")
    private String pCLADRZIPCDIWA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(I WA)")
    private String cITYNAMEIWA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(I WA)")
    private String sTATENAMEIWA;
    @JsonProperty("PCLADR_ZIP_CD(P PRA)")
    private String pCLADRZIPCDPPRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(P PRA)")
    private String cITYNAMEPPRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(P PRA)")
    private String sTATENAMEPPRA;
    @JsonProperty("PCLADR_ZIP_CD(P CRA)")
    private String pCLADRZIPCDPCRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(P CRA)")
    private String cITYNAMEPCRA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(P CRA)")
    private String sTATENAMEPCRA;
    @JsonProperty("PCLADR_ZIP_CD(P WA)")
    private String pCLADRZIPCDPWA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("CITY_NAME(P WA)")
    private String cITYNAMEPWA;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("STATE_NAME(P WA)")
    private String sTATENAMEPWA;
    @JsonProperty("ADM Code")
    private String aDMCode;
    @JsonProperty("ADM Date of Joining")
    private String aDMDateOfJoining;
    @JsonProperty("Customer Segment")
    private String customerSegment;
    @JsonProperty("Agent Date of Joining")
    private String agentDateOfJoining;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("Agent Gender")
    private String agentGender;
    @JsonProperty("ABO/Non-ABO")
    private String aBONonABO;
    @JsonProperty("GOQC Date")
    private String gOQCDate;
    @JsonProperty("Proposer_CLI_ID")
    private String proposerCLIID;
    @JsonProperty("Insured_CLI_ID")
    private String insuredCLIID;
    @JsonProperty("LG code")
    private String lGCode;
    @JsonProperty("M-app flag")
    private String mAppFlag;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("LAs Mob 1")
    private String lAsMob1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("LAs Mob 2")
    private String lAsMob2;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("LAs Land Line1")
    private String lAsLandLine1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("LAs Land Line2")
    private String lAsLandLine2;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Proposer Mob 1")
    private String proposerMob1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Proposer Mob 2")
    private String proposerMob2;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Proposer Land Line1")
    private String proposerLandLine1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Proposer Land Line2")
    private String proposerLandLine2;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Seller Land Line1")
    private String sellerLandLine1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Seller Land Line2")
    private String sellerLandLine2;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Seller Mobile1")
    private String sellerMobile1;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("Seller Mobile2")
    private String sellerMobile2;
    @Sensitive(MaskType.DOB)
    @JsonProperty("AGENT_DOB")
    private String aGENTDOB;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("Proposer Bank Account Number")
    private String proposerBankAccountNumber;

    @JsonProperty("Axis Bank Customer ID")
    private String axisBankCustomerID;
    @JsonProperty("LA_NAME")
    private String lANAME;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("PROPOSER_NAME")
    private String pROPOSERNAME;
    @JsonProperty("SP_CODE")
    private String sPCODE;
    @JsonProperty("CUSTOMER_CLASSIFICATION_CODE")
    private String cUSTOMERCLASSIFICATIONCODE;
    @JsonProperty("RIDER_COUNT")
    private String riderCount;
    @JsonProperty("SSN_CD")
    private String ssnCd;
    @JsonProperty("CIBILTUSCR Score Value")
    private String cibiltuscrScoreValue;
    
    @JsonProperty("CIBILTUIE1")
    private String cibiltuie1;
    
    @JsonProperty("trlScore")
    private String trlScore;

    @JsonProperty("agentPersistency")
    private String agentPersistency;
    @JsonProperty("applicationSubmitDate")
    private String applicationSubmitDate;
    @JsonProperty("caseSubmitDate")
    private String caseSubmitDate;
    @JsonProperty("residentialStatus")
    private String residentialStatus;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("policyPremiumExpiryDate")
    private String policyPremiumExpiryDate;
    @JsonProperty("policyMaturityDate")
    private String policyMaturityDate;
    @JsonProperty("preferredLanguageOfCommunication")
    private String preferredLanguageOfCommunication;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("sumAssuredAvailable")
    private String sumAssuredAvailable;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("bankAccOpeningDate")
    private String bankAccOpeningDate;
    @JsonProperty("customerClassification")
    private String customerClassification;
    @JsonProperty("posvJourneyStatus")
    private String posvJourneyStatus;
    @JsonProperty("posvJourneyFlag")
    private String posvJourneyFlag;
    @JsonProperty("posvNegativeOrNot")
    private String posvNegativeOrNot;
    @JsonProperty("existingCustomerFlag")
    private String existingCustomerFlag;
    @JsonProperty("existingCustomerActivePolicies")
    private String existingCustomerActivePolicies;
    @JsonProperty("existingCustomerIssuedpolicies")
    private String existingCustomerIssuedpolicies;
    @JsonProperty("existingCustomerActiveAfyp")
    private String existingCustomerActiveAfyp;
    @JsonProperty("existingCustomerTotalAfyp")
    private String existingCustomerTotalAfyp;
    @JsonProperty("agentStatus")
    private String agentStatus;
    @JsonProperty("agentVintage")
    private String agentVintage;
    @JsonProperty("branchbersistency")
    private String branchbersistency;
    @JsonProperty("MODEL_TYPE")
    private String modelType;

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
 
    public String getAgentPersistency() {
        return agentPersistency;
    }

    public void setAgentPersistency(String agentPersistency) {
        this.agentPersistency = agentPersistency;
    }

    public String getApplicationSubmitDate() {
        return applicationSubmitDate;
    }

    public void setApplicationSubmitDate(String applicationSubmitDate) {
        this.applicationSubmitDate = applicationSubmitDate;
    }

    public String getCaseSubmitDate() {
        return caseSubmitDate;
    }

    public void setCaseSubmitDate(String caseSubmitDate) {
        this.caseSubmitDate = caseSubmitDate;
    }

    public String getResidentialStatus() {
        return residentialStatus;
    }

    public void setResidentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPolicyPremiumExpiryDate() {
        return policyPremiumExpiryDate;
    }

    public void setPolicyPremiumExpiryDate(String policyPremiumExpiryDate) {
        this.policyPremiumExpiryDate = policyPremiumExpiryDate;
    }

    public String getPolicyMaturityDate() {
        return policyMaturityDate;
    }

    public void setPolicyMaturityDate(String policyMaturityDate) {
        this.policyMaturityDate = policyMaturityDate;
    }

    public String getPreferredLanguageOfCommunication() {
        return preferredLanguageOfCommunication;
    }

    public void setPreferredLanguageOfCommunication(String preferredLanguageOfCommunication) {
        this.preferredLanguageOfCommunication = preferredLanguageOfCommunication;
    }

    public String getSumAssuredAvailable() {
        return sumAssuredAvailable;
    }

    public void setSumAssuredAvailable(String sumAssuredAvailable) {
        this.sumAssuredAvailable = sumAssuredAvailable;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccOpeningDate() {
        return bankAccOpeningDate;
    }

    public void setBankAccOpeningDate(String bankAccOpeningDate) {
        this.bankAccOpeningDate = bankAccOpeningDate;
    }

    public String getCustomerClassification() {
        return customerClassification;
    }

    public void setCustomerClassification(String customerClassification) {
        this.customerClassification = customerClassification;
    }

    public String getPosvJourneyStatus() {
        return posvJourneyStatus;
    }

    public void setPosvJourneyStatus(String posvJourneyStatus) {
        this.posvJourneyStatus = posvJourneyStatus;
    }

    public String getPosvJourneyFlag() {
        return posvJourneyFlag;
    }

    public void setPosvJourneyFlag(String posvJourneyFlag) {
        this.posvJourneyFlag = posvJourneyFlag;
    }

    public String getPosvNegativeOrNot() {
        return posvNegativeOrNot;
    }

    public void setPosvNegativeOrNot(String posvNegativeOrNot) {
        this.posvNegativeOrNot = posvNegativeOrNot;
    }

    public String getExistingCustomerFlag() {
        return existingCustomerFlag;
    }

    public void setExistingCustomerFlag(String existingCustomerFlag) {
        this.existingCustomerFlag = existingCustomerFlag;
    }

    public String getExistingCustomerActivePolicies() {
        return existingCustomerActivePolicies;
    }

    public void setExistingCustomerActivePolicies(String existingCustomerActivePolicies) {
        this.existingCustomerActivePolicies = existingCustomerActivePolicies;
    }

    public String getExistingCustomerIssuedpolicies() {
        return existingCustomerIssuedpolicies;
    }

    public void setExistingCustomerIssuedpolicies(String existingCustomerIssuedpolicies) {
        this.existingCustomerIssuedpolicies = existingCustomerIssuedpolicies;
    }

    public String getExistingCustomerActiveAfyp() {
        return existingCustomerActiveAfyp;
    }

    public void setExistingCustomerActiveAfyp(String existingCustomerActiveAfyp) {
        this.existingCustomerActiveAfyp = existingCustomerActiveAfyp;
    }

    public String getExistingCustomerTotalAfyp() {
        return existingCustomerTotalAfyp;
    }

    public void setExistingCustomerTotalAfyp(String existingCustomerTotalAfyp) {
        this.existingCustomerTotalAfyp = existingCustomerTotalAfyp;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAgentVintage() {
        return agentVintage;
    }

    public void setAgentVintage(String agentVintage) {
        this.agentVintage = agentVintage;
    }

    public String getBranchbersistency() {
        return branchbersistency;
    }

    public void setBranchbersistency(String branchbersistency) {
        this.branchbersistency = branchbersistency;
    }

    @JsonProperty("MLI_PROPOSAL_NUMBER")
    public String getMLIPROPOSALNUMBER() {
        return mLIPROPOSALNUMBER;
    }

    @JsonProperty("MLI_PROPOSAL_NUMBER")
    public void setMLIPROPOSALNUMBER(String mLIPROPOSALNUMBER) {
        this.mLIPROPOSALNUMBER = mLIPROPOSALNUMBER;
    }

    @JsonProperty("CASE_APP_RECD_DT")
    public String getCASEAPPRECDDT() {
        return cASEAPPRECDDT;
    }

    @JsonProperty("CASE_APP_RECD_DT")
    public void setCASEAPPRECDDT(String date) {
        this.cASEAPPRECDDT = date;
    }

    @JsonProperty("MLI_OTHER_OBJECTIVES_ID")
    public String getMLIOTHEROBJECTIVESID() {
        return mLIOTHEROBJECTIVESID;
    }

    @JsonProperty("MLI_OTHER_OBJECTIVES_ID")
    public void setMLIOTHEROBJECTIVESID(String mLIOTHEROBJECTIVESID) {
        this.mLIOTHEROBJECTIVESID = mLIOTHEROBJECTIVESID;
    }

    @JsonProperty("MLI_OTHER_OBJECTIVES_DSC")
    public String getMLIOTHEROBJECTIVESDSC() {
        return mLIOTHEROBJECTIVESDSC;
    }

    @JsonProperty("MLI_OTHER_OBJECTIVES_DSC")
    public void setMLIOTHEROBJECTIVESDSC(String mLIOTHEROBJECTIVESDSC) {
        this.mLIOTHEROBJECTIVESDSC = mLIOTHEROBJECTIVESDSC;
    }

    @JsonProperty("MLI_OBJ_OF_INSURANCE")
    public String getMLIOBJOFINSURANCE() {
        return mLIOBJOFINSURANCE;
    }

    @JsonProperty("MLI_OBJ_OF_INSURANCE")
    public void setMLIOBJOFINSURANCE(String mLIOBJOFINSURANCE) {
        this.mLIOBJOFINSURANCE = mLIOBJOFINSURANCE;
    }

    @JsonProperty("MLI_OBJ_OF_INS_TYPE")
    public String getMLIOBJOFINSTYPE() {
        return mLIOBJOFINSTYPE;
    }

    @JsonProperty("MLI_OBJ_OF_INS_TYPE")
    public void setMLIOBJOFINSTYPE(String mLIOBJOFINSTYPE) {
        this.mLIOBJOFINSTYPE = mLIOBJOFINSTYPE;
    }

    @JsonProperty("IS_RURAL_IND")
    public String getISRURALIND() {
        return iSRURALIND;
    }

    @JsonProperty("IS_RURAL_IND")
    public void setISRURALIND(String iSRURALIND) {
        this.iSRURALIND = iSRURALIND;
    }

    @JsonProperty("IS_SOCIAL")
    public String getISSOCIAL() {
        return iSSOCIAL;
    }

    @JsonProperty("IS_SOCIAL")
    public void setISSOCIAL(String iSSOCIAL) {
        this.iSSOCIAL = iSSOCIAL;
    }

    @JsonProperty("MLI_CHANNEL_CD")
    public String getMLICHANNELCD() {
        return mLICHANNELCD;
    }

    @JsonProperty("MLI_CHANNEL_CD")
    public void setMLICHANNELCD(String mLICHANNELCD) {
        this.mLICHANNELCD = mLICHANNELCD;
    }

    @JsonProperty("MLI_CHANNEL_NAME")
    public String getMLICHANNELNAME() {
        return mLICHANNELNAME;
    }

    @JsonProperty("MLI_CHANNEL_NAME")
    public void setMLICHANNELNAME(String mLICHANNELNAME) {
        this.mLICHANNELNAME = mLICHANNELNAME;
    }

    @JsonProperty("MLI_GO_CD")
    public String getMLIGOCD() {
        return mLIGOCD;
    }

    @JsonProperty("MLI_GO_CD")
    public void setMLIGOCD(String mLIGOCD) {
        this.mLIGOCD = mLIGOCD;
    }

    @JsonProperty("GO_CD")
    public String getGOCD() {
        return gOCD;
    }

    @JsonProperty("GO_CD")
    public void setGOCD(String gOCD) {
        this.gOCD = gOCD;
    }

    @JsonProperty("GO_DESC")
    public String getGODESC() {
        return gODESC;
    }

    @JsonProperty("GO_DESC")
    public void setGODESC(String gODESC) {
        this.gODESC = gODESC;
    }

    @JsonProperty("NBPROD_ID")
    public String getNBPRODID() {
        return nBPRODID;
    }

    @JsonProperty("NBPROD_ID")
    public void setNBPRODID(String nBPRODID) {
        this.nBPRODID = nBPRODID;
    }

    @JsonProperty("PROD_CD")
    public String getPRODCD() {
        return pRODCD;
    }

    @JsonProperty("PROD_CD")
    public void setPRODCD(String pRODCD) {
        this.pRODCD = pRODCD;
    }

    @JsonProperty("PROD_NM")
    public String getPRODNM() {
        return pRODNM;
    }

    @JsonProperty("PROD_NM")
    public void setPRODNM(String pRODNM) {
        this.pRODNM = pRODNM;
    }

    @JsonProperty("MLI_PREM_PAY_TERM_CD")
    public String getMLIPREMPAYTERMCD() {
        return mLIPREMPAYTERMCD;
    }

    @JsonProperty("MLI_PREM_PAY_TERM_CD")
    public void setMLIPREMPAYTERMCD(String mLIPREMPAYTERMCD) {
        this.mLIPREMPAYTERMCD = mLIPREMPAYTERMCD;
    }

    @JsonProperty("MLI_PREM_PAY_TER_DSC")
    public String getMLIPREMPAYTERDSC() {
        return mLIPREMPAYTERDSC;
    }

    @JsonProperty("MLI_PREM_PAY_TER_DSC")
    public void setMLIPREMPAYTERDSC(String mLIPREMPAYTERDSC) {
        this.mLIPREMPAYTERDSC = mLIPREMPAYTERDSC;
    }

    @JsonProperty("PAYMENTTYPE")
    public String getPAYMENTTYPE() {
        return pAYMENTTYPE;
    }

    @JsonProperty("PAYMENTTYPE")
    public void setPAYMENTTYPE(String pAYMENTTYPE) {
        this.pAYMENTTYPE = pAYMENTTYPE;
    }

    @JsonProperty("MLI_AFYP")
    public String getMLIAFYP() {
        return mLIAFYP;
    }

    @JsonProperty("MLI_AFYP")
    public void setMLIAFYP(String mLIAFYP) {
        this.mLIAFYP = mLIAFYP;
    }

    @JsonProperty("MLI_SUM_ASSURED")
    public String getMLISUMASSURED() {
        return mLISUMASSURED;
    }

    @JsonProperty("MLI_SUM_ASSURED")
    public void setMLISUMASSURED(String mLISUMASSURED) {
        this.mLISUMASSURED = mLISUMASSURED;
    }

    @JsonProperty("MLI_COVERAGE_TERM")
    public String getMLICOVERAGETERM() {
        return mLICOVERAGETERM;
    }

    @JsonProperty("MLI_COVERAGE_TERM")
    public void setMLICOVERAGETERM(String mLICOVERAGETERM) {
        this.mLICOVERAGETERM = mLICOVERAGETERM;
    }

    @JsonProperty("MLI_PREM_PAY_TERM_UNITS_CD")
    public String getMLIPREMPAYTERMUNITSCD() {
        return mLIPREMPAYTERMUNITSCD;
    }

    @JsonProperty("MLI_PREM_PAY_TERM_UNITS_CD")
    public void setMLIPREMPAYTERMUNITSCD(String mLIPREMPAYTERMUNITSCD) {
        this.mLIPREMPAYTERMUNITSCD = mLIPREMPAYTERMUNITSCD;
    }

    @JsonProperty("MLI_INIT_PRE_PAY_DET_CD")
    public String getMLIINITPREPAYDETCD() {
        return mLIINITPREPAYDETCD;
    }

    @JsonProperty("MLI_INIT_PRE_PAY_DET_CD")
    public void setMLIINITPREPAYDETCD(String mLIINITPREPAYDETCD) {
        this.mLIINITPREPAYDETCD = mLIINITPREPAYDETCD;
    }

    @JsonProperty("MLI_INIT_PRE_PAY_DET_DESC")
    public String getMLIINITPREPAYDETDESC() {
        return mLIINITPREPAYDETDESC;
    }

    @JsonProperty("MLI_INIT_PRE_PAY_DET_DESC")
    public void setMLIINITPREPAYDETDESC(String mLIINITPREPAYDETDESC) {
        this.mLIINITPREPAYDETDESC = mLIINITPREPAYDETDESC;
    }

    @JsonProperty("MLI_PREM_PAYMT_MODE_ID")
    public String getMLIPREMPAYMTMODEID() {
        return mLIPREMPAYMTMODEID;
    }

    @JsonProperty("MLI_PREM_PAYMT_MODE_ID")
    public void setMLIPREMPAYMTMODEID(String mLIPREMPAYMTMODEID) {
        this.mLIPREMPAYMTMODEID = mLIPREMPAYMTMODEID;
    }

    @JsonProperty("MLI_PREM_PAYMT_MODE_DSC")
    public String getMLIPREMPAYMTMODEDSC() {
        return mLIPREMPAYMTMODEDSC;
    }

    @JsonProperty("MLI_PREM_PAYMT_MODE_DSC")
    public void setMLIPREMPAYMTMODEDSC(String mLIPREMPAYMTMODEDSC) {
        this.mLIPREMPAYMTMODEDSC = mLIPREMPAYMTMODEDSC;
    }

    @JsonProperty("MLI_PREMIUMOF_PAYMENT")
    public String getMLIPREMIUMOFPAYMENT() {
        return mLIPREMIUMOFPAYMENT;
    }

    @JsonProperty("MLI_PREMIUMOF_PAYMENT")
    public void setMLIPREMIUMOFPAYMENT(String mLIPREMIUMOFPAYMENT) {
        this.mLIPREMIUMOFPAYMENT = mLIPREMIUMOFPAYMENT;
    }

    @JsonProperty("MLI_PAN_CARD_NUMBER")
    public String getMLIPANCARDNUMBER() {
        return mLIPANCARDNUMBER;
    }

    @JsonProperty("MLI_PAN_CARD_NUMBER")
    public void setMLIPANCARDNUMBER(String mLIPANCARDNUMBER) {
        this.mLIPANCARDNUMBER = mLIPANCARDNUMBER;
    }

    @JsonProperty("MLI_PAN_NUM")
    public String getMLIPANNUM() {
        return mLIPANNUM;
    }

    @JsonProperty("MLI_PAN_NUM")
    public void setMLIPANNUM(String mLIPANNUM) {
        this.mLIPANNUM = mLIPANNUM;
    }

    @JsonProperty("IS_PROPOSER_PAYER")
    public String getISPROPOSERPAYER() {
        return iSPROPOSERPAYER;
    }

    @JsonProperty("IS_PROPOSER_PAYER")
    public void setISPROPOSERPAYER(String iSPROPOSERPAYER) {
        this.iSPROPOSERPAYER = iSPROPOSERPAYER;
    }

    @JsonProperty("MLI_AGENT_CD")
    public String getMLIAGENTCD() {
        return mLIAGENTCD;
    }

    @JsonProperty("MLI_AGENT_CD")
    public void setMLIAGENTCD(String mLIAGENTCD) {
        this.mLIAGENTCD = mLIAGENTCD;
    }

    @JsonProperty("AGENT_CITY")
    public String getAGENTCITY() {
        return aGENTCITY;
    }

    @JsonProperty("AGENT_CITY")
    public void setAGENTCITY(String aGENTCITY) {
        this.aGENTCITY = aGENTCITY;
    }

    @JsonProperty("AGENT_ZIP")
    public String getAGENTZIP() {
        return aGENTZIP;
    }

    @JsonProperty("AGENT_ZIP")
    public void setAGENTZIP(String aGENTZIP) {
        this.aGENTZIP = aGENTZIP;
    }

    @JsonProperty("PCLI_BIRTH_DT(P)")
    public String getPCLIBIRTHDTP() {
        return pCLIBIRTHDTP;
    }

    @JsonProperty("PCLI_BIRTH_DT(P)")
    public void setPCLIBIRTHDTP(String date) {
        this.pCLIBIRTHDTP = date;
    }

    @JsonProperty("GNDR_CD(P)")
    public String getGNDRCDP() {
        return gNDRCDP;
    }

    @JsonProperty("GNDR_CD(P)")
    public void setGNDRCDP(String gNDRCDP) {
        this.gNDRCDP = gNDRCDP;
    }

    @JsonProperty("MLI_MARITAL_STATUS(P)")
    public String getMLIMARITALSTATUSP() {
        return mLIMARITALSTATUSP;
    }

    @JsonProperty("MLI_MARITAL_STATUS(P)")
    public void setMLIMARITALSTATUSP(String mLIMARITALSTATUSP) {
        this.mLIMARITALSTATUSP = mLIMARITALSTATUSP;
    }

    @JsonProperty("MLI_MARITAL_STATUS_DESC(P)")
    public String getMLIMARITALSTATUSDESCP() {
        return mLIMARITALSTATUSDESCP;
    }

    @JsonProperty("MLI_MARITAL_STATUS_DESC(P)")
    public void setMLIMARITALSTATUSDESCP(String mLIMARITALSTATUSDESCP) {
        this.mLIMARITALSTATUSDESCP = mLIMARITALSTATUSDESCP;
    }

    @JsonProperty("EDUCATION_CD(P)")
    public String getEDUCATIONCDP() {
        return eDUCATIONCDP;
    }

    @JsonProperty("EDUCATION_CD(P)")
    public void setEDUCATIONCDP(String eDUCATIONCDP) {
        this.eDUCATIONCDP = eDUCATIONCDP;
    }

    @JsonProperty("EDUCATION_DESC(P)")
    public String getEDUCATIONDESCP() {
        return eDUCATIONDESCP;
    }

    @JsonProperty("EDUCATION_DESC(P)")
    public void setEDUCATIONDESCP(String eDUCATIONDESCP) {
        this.eDUCATIONDESCP = eDUCATIONDESCP;
    }

    @JsonProperty("INDUSTRY_TYP_CD(P)")
    public String getINDUSTRYTYPCDP() {
        return iNDUSTRYTYPCDP;
    }

    @JsonProperty("INDUSTRY_TYP_CD(P)")
    public void setINDUSTRYTYPCDP(String iNDUSTRYTYPCDP) {
        this.iNDUSTRYTYPCDP = iNDUSTRYTYPCDP;
    }

    @JsonProperty("INDUSTRY_TYP_DESC(P)")
    public String getINDUSTRYTYPDESCP() {
        return iNDUSTRYTYPDESCP;
    }

    @JsonProperty("INDUSTRY_TYP_DESC(P)")
    public void setINDUSTRYTYPDESCP(String iNDUSTRYTYPDESCP) {
        this.iNDUSTRYTYPDESCP = iNDUSTRYTYPDESCP;
    }

    @JsonProperty("OCC_TYP_CD(P)")
    public String getOCCTYPCDP() {
        return oCCTYPCDP;
    }

    @JsonProperty("OCC_TYP_CD(P)")
    public void setOCCTYPCDP(String oCCTYPCDP) {
        this.oCCTYPCDP = oCCTYPCDP;
    }

    @JsonProperty("OCC_TYP_DSC(P)")
    public String getOCCTYPDSCP() {
        return oCCTYPDSCP;
    }

    @JsonProperty("OCC_TYP_DSC(P)")
    public void setOCCTYPDSCP(String oCCTYPDSCP) {
        this.oCCTYPDSCP = oCCTYPDSCP;
    }

    @JsonProperty("MLI_EXACT_INCOME(P)")
    public String getMLIEXACTINCOMEP() {
        return mLIEXACTINCOMEP;
    }

    @JsonProperty("MLI_EXACT_INCOME(P)")
    public void setMLIEXACTINCOMEP(String mLIEXACTINCOMEP) {
        this.mLIEXACTINCOMEP = mLIEXACTINCOMEP;
    }

    @JsonProperty("MLI_DOB_PROOF_CD(P)")
    public String getMLIDOBPROOFCDP() {
        return mLIDOBPROOFCDP;
    }

    @JsonProperty("MLI_DOB_PROOF_CD(P)")
    public void setMLIDOBPROOFCDP(String mLIDOBPROOFCDP) {
        this.mLIDOBPROOFCDP = mLIDOBPROOFCDP;
    }

    @JsonProperty("MLI_CRA_PROOF(P)")
    public String getMLICRAPROOFP() {
        return mLICRAPROOFP;
    }

    @JsonProperty("MLI_CRA_PROOF(P)")
    public void setMLICRAPROOFP(String mLICRAPROOFP) {
        this.mLICRAPROOFP = mLICRAPROOFP;
    }

    @JsonProperty("MLI_ID_PROOF(P)")
    public String getMLIIDPROOFP() {
        return mLIIDPROOFP;
    }

    @JsonProperty("MLI_ID_PROOF(P)")
    public void setMLIIDPROOFP(String mLIIDPROOFP) {
        this.mLIIDPROOFP = mLIIDPROOFP;
    }

    @JsonProperty("PCLI_BIRTH_DT (I)")
    public String getPCLIBIRTHDTI() {
        return pCLIBIRTHDTI;
    }

    @JsonProperty("PCLI_BIRTH_DT (I)")
    public void setPCLIBIRTHDTI(String pclibirthdti2) {
        this.pCLIBIRTHDTI = pclibirthdti2;
    }

    @JsonProperty("GNDR_CD(I)")
    public String getGNDRCDI() {
        return gNDRCDI;
    }

    @JsonProperty("GNDR_CD(I)")
    public void setGNDRCDI(String gNDRCDI) {
        this.gNDRCDI = gNDRCDI;
    }

    @JsonProperty("MLI_MARITAL_STATUS(I)")
    public String getMLIMARITALSTATUSI() {
        return mLIMARITALSTATUSI;
    }

    @JsonProperty("MLI_MARITAL_STATUS(I)")
    public void setMLIMARITALSTATUSI(String mLIMARITALSTATUSI) {
        this.mLIMARITALSTATUSI = mLIMARITALSTATUSI;
    }

    @JsonProperty("MLI_MARITAL_STATUS_DESC(I)")
    public String getMLIMARITALSTATUSDESCI() {
        return mLIMARITALSTATUSDESCI;
    }

    @JsonProperty("MLI_MARITAL_STATUS_DESC(I)")
    public void setMLIMARITALSTATUSDESCI(String mLIMARITALSTATUSDESCI) {
        this.mLIMARITALSTATUSDESCI = mLIMARITALSTATUSDESCI;
    }

    @JsonProperty("EDUCATION_CD(I)")
    public String getEDUCATIONCDI() {
        return eDUCATIONCDI;
    }

    @JsonProperty("EDUCATION_CD(I)")
    public void setEDUCATIONCDI(String eDUCATIONCDI) {
        this.eDUCATIONCDI = eDUCATIONCDI;
    }

    @JsonProperty("EDUCATION_DESC(I)")
    public String getEDUCATIONDESCI() {
        return eDUCATIONDESCI;
    }

    @JsonProperty("EDUCATION_DESC(I)")
    public void setEDUCATIONDESCI(String eDUCATIONDESCI) {
        this.eDUCATIONDESCI = eDUCATIONDESCI;
    }

    @JsonProperty("INDUSTRY_TYP_CD (I)")
    public String getINDUSTRYTYPCDI() {
        return iNDUSTRYTYPCDI;
    }

    @JsonProperty("INDUSTRY_TYP_CD (I)")
    public void setINDUSTRYTYPCDI(String iNDUSTRYTYPCDI) {
        this.iNDUSTRYTYPCDI = iNDUSTRYTYPCDI;
    }

    @JsonProperty("INDUSTRY_TYP_DESC(I)")
    public String getINDUSTRYTYPDESCI() {
        return iNDUSTRYTYPDESCI;
    }

    @JsonProperty("INDUSTRY_TYP_DESC(I)")
    public void setINDUSTRYTYPDESCI(String iNDUSTRYTYPDESCI) {
        this.iNDUSTRYTYPDESCI = iNDUSTRYTYPDESCI;
    }

    @JsonProperty("OCC_TYP_CD(I)")
    public String getOCCTYPCDI() {
        return oCCTYPCDI;
    }

    @JsonProperty("OCC_TYP_CD(I)")
    public void setOCCTYPCDI(String oCCTYPCDI) {
        this.oCCTYPCDI = oCCTYPCDI;
    }

    @JsonProperty("OCC_TYP_DSC(I)")
    public String getOCCTYPDSCI() {
        return oCCTYPDSCI;
    }

    @JsonProperty("OCC_TYP_DSC(I)")
    public void setOCCTYPDSCI(String oCCTYPDSCI) {
        this.oCCTYPDSCI = oCCTYPDSCI;
    }

    @JsonProperty("MLI_EXACT_INCOME(I)")
    public String getMLIEXACTINCOMEI() {
        return mLIEXACTINCOMEI;
    }

    @JsonProperty("MLI_EXACT_INCOME(I)")
    public void setMLIEXACTINCOMEI(String mLIEXACTINCOMEI) {
        this.mLIEXACTINCOMEI = mLIEXACTINCOMEI;
    }

    @JsonProperty("MLI_DOB_PROOF_CD(I)")
    public String getMLIDOBPROOFCDI() {
        return mLIDOBPROOFCDI;
    }

    @JsonProperty("MLI_DOB_PROOF_CD(I)")
    public void setMLIDOBPROOFCDI(String mLIDOBPROOFCDI) {
        this.mLIDOBPROOFCDI = mLIDOBPROOFCDI;
    }

    @JsonProperty("MLI_CRA_PROOF(I)")
    public String getMLICRAPROOFI() {
        return mLICRAPROOFI;
    }

    @JsonProperty("MLI_CRA_PROOF(I)")
    public void setMLICRAPROOFI(String mLICRAPROOFI) {
        this.mLICRAPROOFI = mLICRAPROOFI;
    }

    @JsonProperty("MLI_ID_PROOF(I)")
    public String getMLIIDPROOFI() {
        return mLIIDPROOFI;
    }

    @JsonProperty("MLI_ID_PROOF(I)")
    public void setMLIIDPROOFI(String mLIIDPROOFI) {
        this.mLIIDPROOFI = mLIIDPROOFI;
    }

    @JsonProperty("MLI_REL_WITH_NOMINEE")
    public String getMLIRELWITHNOMINEE() {
        return mLIRELWITHNOMINEE;
    }

    @JsonProperty("MLI_REL_WITH_NOMINEE")
    public void setMLIRELWITHNOMINEE(String mLIRELWITHNOMINEE) {
        this.mLIRELWITHNOMINEE = mLIRELWITHNOMINEE;
    }

    @JsonProperty("PCLI_TYP_CD")
    public String getPCLITYPCD() {
        return pCLITYPCD;
    }

    @JsonProperty("PCLI_TYP_CD")
    public void setPCLITYPCD(String pCLITYPCD) {
        this.pCLITYPCD = pCLITYPCD;
    }

    @JsonProperty("PCLADR_ZIP_CD(I PRA)")
    public String getPCLADRZIPCDIPRA() {
        return pCLADRZIPCDIPRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(I PRA)")
    public void setPCLADRZIPCDIPRA(String pCLADRZIPCDIPRA) {
        this.pCLADRZIPCDIPRA = pCLADRZIPCDIPRA;
    }

    @JsonProperty("CITY_NAME(I PRA)")
    public String getCITYNAMEIPRA() {
        return cITYNAMEIPRA;
    }

    @JsonProperty("CITY_NAME(I PRA)")
    public void setCITYNAMEIPRA(String cITYNAMEIPRA) {
        this.cITYNAMEIPRA = cITYNAMEIPRA;
    }

    @JsonProperty("STATE_NAME(I PRA)")
    public String getSTATENAMEIPRA() {
        return sTATENAMEIPRA;
    }

    @JsonProperty("STATE_NAME(I PRA)")
    public void setSTATENAMEIPRA(String sTATENAMEIPRA) {
        this.sTATENAMEIPRA = sTATENAMEIPRA;
    }

    @JsonProperty("PCLADR_ZIP_CD (I CRA)")
    public String getPCLADRZIPCDICRA() {
        return pCLADRZIPCDICRA;
    }

    @JsonProperty("PCLADR_ZIP_CD (I CRA)")
    public void setPCLADRZIPCDICRA(String pCLADRZIPCDICRA) {
        this.pCLADRZIPCDICRA = pCLADRZIPCDICRA;
    }

    @JsonProperty("CITY_NAME(I CRA)")
    public String getCITYNAMEICRA() {
        return cITYNAMEICRA;
    }

    @JsonProperty("CITY_NAME(I CRA)")
    public void setCITYNAMEICRA(String cITYNAMEICRA) {
        this.cITYNAMEICRA = cITYNAMEICRA;
    }

    @JsonProperty("STATE_NAME(I CRA)")
    public String getSTATENAMEICRA() {
        return sTATENAMEICRA;
    }

    @JsonProperty("STATE_NAME(I CRA)")
    public void setSTATENAMEICRA(String sTATENAMEICRA) {
        this.sTATENAMEICRA = sTATENAMEICRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(I WA)")
    public String getPCLADRZIPCDIWA() {
        return pCLADRZIPCDIWA;
    }

    @JsonProperty("PCLADR_ZIP_CD(I WA)")
    public void setPCLADRZIPCDIWA(String pCLADRZIPCDIWA) {
        this.pCLADRZIPCDIWA = pCLADRZIPCDIWA;
    }

    @JsonProperty("CITY_NAME(I WA)")
    public String getCITYNAMEIWA() {
        return cITYNAMEIWA;
    }

    @JsonProperty("CITY_NAME(I WA)")
    public void setCITYNAMEIWA(String cITYNAMEIWA) {
        this.cITYNAMEIWA = cITYNAMEIWA;
    }

    @JsonProperty("STATE_NAME(I WA)")
    public String getSTATENAMEIWA() {
        return sTATENAMEIWA;
    }

    @JsonProperty("STATE_NAME(I WA)")
    public void setSTATENAMEIWA(String sTATENAMEIWA) {
        this.sTATENAMEIWA = sTATENAMEIWA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P PRA)")
    public String getPCLADRZIPCDPPRA() {
        return pCLADRZIPCDPPRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P PRA)")
    public void setPCLADRZIPCDPPRA(String pCLADRZIPCDPPRA) {
        this.pCLADRZIPCDPPRA = pCLADRZIPCDPPRA;
    }

    @JsonProperty("CITY_NAME(P PRA)")
    public String getCITYNAMEPPRA() {
        return cITYNAMEPPRA;
    }

    @JsonProperty("CITY_NAME(P PRA)")
    public void setCITYNAMEPPRA(String cITYNAMEPPRA) {
        this.cITYNAMEPPRA = cITYNAMEPPRA;
    }

    @JsonProperty("STATE_NAME(P PRA)")
    public String getSTATENAMEPPRA() {
        return sTATENAMEPPRA;
    }

    @JsonProperty("STATE_NAME(P PRA)")
    public void setSTATENAMEPPRA(String sTATENAMEPPRA) {
        this.sTATENAMEPPRA = sTATENAMEPPRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P CRA)")
    public String getPCLADRZIPCDPCRA() {
        return pCLADRZIPCDPCRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P CRA)")
    public void setPCLADRZIPCDPCRA(String pCLADRZIPCDPCRA) {
        this.pCLADRZIPCDPCRA = pCLADRZIPCDPCRA;
    }

    @JsonProperty("CITY_NAME(P CRA)")
    public String getCITYNAMEPCRA() {
        return cITYNAMEPCRA;
    }

    @JsonProperty("CITY_NAME(P CRA)")
    public void setCITYNAMEPCRA(String cITYNAMEPCRA) {
        this.cITYNAMEPCRA = cITYNAMEPCRA;
    }

    @JsonProperty("STATE_NAME(P CRA)")
    public String getSTATENAMEPCRA() {
        return sTATENAMEPCRA;
    }

    @JsonProperty("STATE_NAME(P CRA)")
    public void setSTATENAMEPCRA(String sTATENAMEPCRA) {
        this.sTATENAMEPCRA = sTATENAMEPCRA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P WA)")
    public String getPCLADRZIPCDPWA() {
        return pCLADRZIPCDPWA;
    }

    @JsonProperty("PCLADR_ZIP_CD(P WA)")
    public void setPCLADRZIPCDPWA(String pCLADRZIPCDPWA) {
        this.pCLADRZIPCDPWA = pCLADRZIPCDPWA;
    }

    @JsonProperty("CITY_NAME(P WA)")
    public String getCITYNAMEPWA() {
        return cITYNAMEPWA;
    }

    @JsonProperty("CITY_NAME(P WA)")
    public void setCITYNAMEPWA(String cITYNAMEPWA) {
        this.cITYNAMEPWA = cITYNAMEPWA;
    }

    @JsonProperty("STATE_NAME(P WA)")
    public String getSTATENAMEPWA() {
        return sTATENAMEPWA;
    }

    @JsonProperty("STATE_NAME(P WA)")
    public void setSTATENAMEPWA(String sTATENAMEPWA) {
        this.sTATENAMEPWA = sTATENAMEPWA;
    }

    @JsonProperty("ADM Code")
    public String getADMCode() {
        return aDMCode;
    }

    @JsonProperty("ADM Code")
    public void setADMCode(String aDMCode) {
        this.aDMCode = aDMCode;
    }

    @JsonProperty("ADM Date of Joining")
    public String getADMDateOfJoining() {
        return aDMDateOfJoining;
    }

    @JsonProperty("ADM Date of Joining")
    public void setADMDateOfJoining(String aDMDateOfJoining) {
        this.aDMDateOfJoining = aDMDateOfJoining;
    }

    @JsonProperty("Customer Segment")
    public String getCustomerSegment() {
        return customerSegment;
    }

    @JsonProperty("Customer Segment")
    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    @JsonProperty("Agent Date of Joining")
    public String getAgentDateOfJoining() {
        return agentDateOfJoining;
    }

    @JsonProperty("Agent Date of Joining")
    public void setAgentDateOfJoining(String date) {
        this.agentDateOfJoining = date;
    }

    @JsonProperty("Agent Gender")
    public String getAgentGender() {
        return agentGender;
    }

    @JsonProperty("Agent Gender")
    public void setAgentGender(String agentGender) {
        this.agentGender = agentGender;
    }

    @JsonProperty("ABO/Non-ABO")
    public String getABONonABO() {
        return aBONonABO;
    }

    @JsonProperty("ABO/Non-ABO")
    public void setABONonABO(String aBONonABO) {
        this.aBONonABO = aBONonABO;
    }

    @JsonProperty("GOQC Date")
    public String getGOQCDate() {
        return gOQCDate;
    }

    @JsonProperty("GOQC Date")
    public void setGOQCDate(String gOQCDate) {
        this.gOQCDate = gOQCDate;
    }

    @JsonProperty("Proposer_CLI_ID")
    public String getProposerCLIID() {
        return proposerCLIID;
    }

    @JsonProperty("Proposer_CLI_ID")
    public void setProposerCLIID(String proposerCLIID) {
        this.proposerCLIID = proposerCLIID;
    }

    @JsonProperty("Insured_CLI_ID")
    public String getInsuredCLIID() {
        return insuredCLIID;
    }

    @JsonProperty("Insured_CLI_ID")
    public void setInsuredCLIID(String insuredCLIID) {
        this.insuredCLIID = insuredCLIID;
    }

    @JsonProperty("LG code")
    public String getLGCode() {
        return lGCode;
    }

    @JsonProperty("LG code")
    public void setLGCode(String lGCode) {
        this.lGCode = lGCode;
    }

    @JsonProperty("M-app flag")
    public String getMAppFlag() {
        return mAppFlag;
    }

    @JsonProperty("M-app flag")
    public void setMAppFlag(String mAppFlag) {
        this.mAppFlag = mAppFlag;
    }

    @JsonProperty("LAs Mob 1")
    public String getLAsMob1() {
        return lAsMob1;
    }

    @JsonProperty("LAs Mob 1")
    public void setLAsMob1(String lAsMob1) {
        this.lAsMob1 = lAsMob1;
    }

    @JsonProperty("LAs Mob 2")
    public String getLAsMob2() {
        return lAsMob2;
    }

    @JsonProperty("LAs Mob 2")
    public void setLAsMob2(String lAsMob2) {
        this.lAsMob2 = lAsMob2;
    }

    @JsonProperty("LAs Land Line1")
    public String getLAsLandLine1() {
        return lAsLandLine1;
    }

    @JsonProperty("LAs Land Line1")
    public void setLAsLandLine1(String lAsLandLine1) {
        this.lAsLandLine1 = lAsLandLine1;
    }

    @JsonProperty("LAs Land Line2")
    public String getLAsLandLine2() {
        return lAsLandLine2;
    }

    @JsonProperty("LAs Land Line2")
    public void setLAsLandLine2(String lAsLandLine2) {
        this.lAsLandLine2 = lAsLandLine2;
    }

    @JsonProperty("Proposer Mob 1")
    public String getProposerMob1() {
        return proposerMob1;
    }

    @JsonProperty("Proposer Mob 1")
    public void setProposerMob1(String proposerMob1) {
        this.proposerMob1 = proposerMob1;
    }

    @JsonProperty("Proposer Mob 2")
    public String getProposerMob2() {
        return proposerMob2;
    }

    @JsonProperty("Proposer Mob 2")
    public void setProposerMob2(String proposerMob2) {
        this.proposerMob2 = proposerMob2;
    }

    @JsonProperty("Proposer Land Line1")
    public String getProposerLandLine1() {
        return proposerLandLine1;
    }

    @JsonProperty("Proposer Land Line1")
    public void setProposerLandLine1(String proposerLandLine1) {
        this.proposerLandLine1 = proposerLandLine1;
    }

    @JsonProperty("Proposer Land Line2")
    public String getProposerLandLine2() {
        return proposerLandLine2;
    }

    @JsonProperty("Proposer Land Line2")
    public void setProposerLandLine2(String proposerLandLine2) {
        this.proposerLandLine2 = proposerLandLine2;
    }

    @JsonProperty("Seller Land Line1")
    public String getSellerLandLine1() {
        return sellerLandLine1;
    }

    @JsonProperty("Seller Land Line1")
    public void setSellerLandLine1(String sellerLandLine1) {
        this.sellerLandLine1 = sellerLandLine1;
    }

    @JsonProperty("Seller Land Line2")
    public String getSellerLandLine2() {
        return sellerLandLine2;
    }

    @JsonProperty("Seller Land Line2")
    public void setSellerLandLine2(String sellerLandLine2) {
        this.sellerLandLine2 = sellerLandLine2;
    }

    @JsonProperty("Seller Mobile1")
    public String getSellerMobile1() {
        return sellerMobile1;
    }

    @JsonProperty("Seller Mobile1")
    public void setSellerMobile1(String sellerMobile1) {
        this.sellerMobile1 = sellerMobile1;
    }

    @JsonProperty("Seller Mobile2")
    public String getSellerMobile2() {
        return sellerMobile2;
    }

    @JsonProperty("Seller Mobile2")
    public void setSellerMobile2(String sellerMobile2) {
        this.sellerMobile2 = sellerMobile2;
    }

    @JsonProperty("AGENT_DOB")
    public String getAGENTDOB() {
        return aGENTDOB;
    }

    @JsonProperty("AGENT_DOB")
    public void setAGENTDOB(String aGENTDOB) {
        this.aGENTDOB = aGENTDOB;
    }

    @JsonProperty("Proposer Bank Account Number")
    public String getProposerBankAccountNumber() {
        return proposerBankAccountNumber;
    }

    @JsonProperty("Proposer Bank Account Number")
    public void setProposerBankAccountNumber(String proposerBankAccountNumber) {
        this.proposerBankAccountNumber = proposerBankAccountNumber;
    }

    @JsonProperty("Axis Bank Customer ID")
    public String getAxisBankCustomerID() {
        return axisBankCustomerID;
    }

    @JsonProperty("Axis Bank Customer ID")
    public void setAxisBankCustomerID(String axisBankCustomerID) {
        this.axisBankCustomerID = axisBankCustomerID;
    }

    @JsonProperty("LA_NAME")
    public String getLANAME() {
        return lANAME;
    }

    @JsonProperty("LA_NAME")
    public void setLANAME(String lANAME) {
        this.lANAME = lANAME;
    }

    @JsonProperty("PROPOSER_NAME")
    public String getPROPOSERNAME() {
        return pROPOSERNAME;
    }

    @JsonProperty("PROPOSER_NAME")
    public void setPROPOSERNAME(String pROPOSERNAME) {
        this.pROPOSERNAME = pROPOSERNAME;
    }

    @JsonProperty("SP_CODE")
    public String getSPCODE() {
        return sPCODE;
    }

    @JsonProperty("SP_CODE")
    public void setSPCODE(String sPCODE) {
        this.sPCODE = sPCODE;
    }

    @JsonProperty("CUSTOMER_CLASSIFICATION_CODE")
    public String getCUSTOMERCLASSIFICATIONCODE() {
        return cUSTOMERCLASSIFICATIONCODE;
    }

    @JsonProperty("CUSTOMER_CLASSIFICATION_CODE")
    public void setCUSTOMERCLASSIFICATIONCODE(String cUSTOMERCLASSIFICATIONCODE) {
        this.cUSTOMERCLASSIFICATIONCODE = cUSTOMERCLASSIFICATIONCODE;
    }

    public String getRiderCount() {
        return riderCount;
    }

    public void setRiderCount(String riderCount) {
        this.riderCount = riderCount;
    }

    public String getSsnCd() {
        return ssnCd;
    }

    public void setSsnCd(String ssnCd) {
        this.ssnCd = ssnCd;
    }

    @JsonProperty("CIBILTUSCR Score Value")
    public String getCibiltuscrScoreValue() {
		return cibiltuscrScoreValue;
	}

    @JsonProperty("CIBILTUSCR Score Value")
	public void setCibiltuscrScoreValue(String cibiltuscrScoreValue) {
		this.cibiltuscrScoreValue = cibiltuscrScoreValue;
	}

	@JsonProperty("CIBILTUIE1")
	public String getCibiltuie1() {
		return cibiltuie1;
	}

	@JsonProperty("CIBILTUIE1")
	public void setCibiltuie1(String cibiltuie1) {
		this.cibiltuie1 = cibiltuie1;
	}

	public String getTrlScore() {
        return trlScore;
    }

    public void setTrlScore(String trlScore) {
        this.trlScore = trlScore;
    }

    @Override
    public String toString() {

           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PersistencyModelRequest{" +
                "mLIPROPOSALNUMBER='" + mLIPROPOSALNUMBER + '\'' +
                ", cASEAPPRECDDT='" + cASEAPPRECDDT + '\'' +
                ", mLIOTHEROBJECTIVESID='" + mLIOTHEROBJECTIVESID + '\'' +
                ", mLIOTHEROBJECTIVESDSC='" + mLIOTHEROBJECTIVESDSC + '\'' +
                ", mLIOBJOFINSURANCE='" + mLIOBJOFINSURANCE + '\'' +
                ", mLIOBJOFINSTYPE='" + mLIOBJOFINSTYPE + '\'' +
                ", iSRURALIND='" + iSRURALIND + '\'' +
                ", iSSOCIAL='" + iSSOCIAL + '\'' +
                ", mLICHANNELCD='" + mLICHANNELCD + '\'' +
                ", mLICHANNELNAME='" + mLICHANNELNAME + '\'' +
                ", mLIGOCD='" + mLIGOCD + '\'' +
                ", gOCD='" + gOCD + '\'' +
                ", gODESC='" + gODESC + '\'' +
                ", nBPRODID='" + nBPRODID + '\'' +
                ", pRODCD='" + pRODCD + '\'' +
                ", pRODNM='" + pRODNM + '\'' +
                ", mLIPREMPAYTERMCD='" + mLIPREMPAYTERMCD + '\'' +
                ", mLIPREMPAYTERDSC='" + mLIPREMPAYTERDSC + '\'' +
                ", pAYMENTTYPE='" + pAYMENTTYPE + '\'' +
                ", mLIAFYP='" + mLIAFYP + '\'' +
                ", mLISUMASSURED='" + mLISUMASSURED + '\'' +
                ", mLICOVERAGETERM='" + mLICOVERAGETERM + '\'' +
                ", mLIPREMPAYTERMUNITSCD='" + mLIPREMPAYTERMUNITSCD + '\'' +
                ", mLIINITPREPAYDETCD='" + mLIINITPREPAYDETCD + '\'' +
                ", mLIINITPREPAYDETDESC='" + mLIINITPREPAYDETDESC + '\'' +
                ", mLIPREMPAYMTMODEID='" + mLIPREMPAYMTMODEID + '\'' +
                ", mLIPREMPAYMTMODEDSC='" + mLIPREMPAYMTMODEDSC + '\'' +
                ", mLIPREMIUMOFPAYMENT='" + mLIPREMIUMOFPAYMENT + '\'' +
                ", mLIPANCARDNUMBER='" + mLIPANCARDNUMBER + '\'' +
                ", mLIPANNUM='" + mLIPANNUM + '\'' +
                ", iSPROPOSERPAYER='" + iSPROPOSERPAYER + '\'' +
                ", mLIAGENTCD='" + mLIAGENTCD + '\'' +
                ", aGENTCITY='" + aGENTCITY + '\'' +
                ", aGENTZIP='" + aGENTZIP + '\'' +
                ", pCLIBIRTHDTP='" + pCLIBIRTHDTP + '\'' +
                ", gNDRCDP='" + gNDRCDP + '\'' +
                ", mLIMARITALSTATUSP='" + mLIMARITALSTATUSP + '\'' +
                ", mLIMARITALSTATUSDESCP='" + mLIMARITALSTATUSDESCP + '\'' +
                ", eDUCATIONCDP='" + eDUCATIONCDP + '\'' +
                ", eDUCATIONDESCP='" + eDUCATIONDESCP + '\'' +
                ", iNDUSTRYTYPCDP='" + iNDUSTRYTYPCDP + '\'' +
                ", iNDUSTRYTYPDESCP='" + iNDUSTRYTYPDESCP + '\'' +
                ", oCCTYPCDP='" + oCCTYPCDP + '\'' +
                ", oCCTYPDSCP='" + oCCTYPDSCP + '\'' +
                ", mLIEXACTINCOMEP='" + mLIEXACTINCOMEP + '\'' +
                ", mLIDOBPROOFCDP='" + mLIDOBPROOFCDP + '\'' +
                ", mLICRAPROOFP='" + mLICRAPROOFP + '\'' +
                ", mLIIDPROOFP='" + mLIIDPROOFP + '\'' +
                ", pCLIBIRTHDTI='" + pCLIBIRTHDTI + '\'' +
                ", gNDRCDI='" + gNDRCDI + '\'' +
                ", mLIMARITALSTATUSI='" + mLIMARITALSTATUSI + '\'' +
                ", mLIMARITALSTATUSDESCI='" + mLIMARITALSTATUSDESCI + '\'' +
                ", eDUCATIONCDI='" + eDUCATIONCDI + '\'' +
                ", eDUCATIONDESCI='" + eDUCATIONDESCI + '\'' +
                ", iNDUSTRYTYPCDI='" + iNDUSTRYTYPCDI + '\'' +
                ", iNDUSTRYTYPDESCI='" + iNDUSTRYTYPDESCI + '\'' +
                ", oCCTYPCDI='" + oCCTYPCDI + '\'' +
                ", oCCTYPDSCI='" + oCCTYPDSCI + '\'' +
                ", mLIEXACTINCOMEI='" + mLIEXACTINCOMEI + '\'' +
                ", mLIDOBPROOFCDI='" + mLIDOBPROOFCDI + '\'' +
                ", mLICRAPROOFI='" + mLICRAPROOFI + '\'' +
                ", mLIIDPROOFI='" + mLIIDPROOFI + '\'' +
                ", mLIRELWITHNOMINEE='" + mLIRELWITHNOMINEE + '\'' +
                ", pCLITYPCD='" + pCLITYPCD + '\'' +
                ", pCLADRZIPCDIPRA='" + pCLADRZIPCDIPRA + '\'' +
                ", cITYNAMEIPRA='" + cITYNAMEIPRA + '\'' +
                ", sTATENAMEIPRA='" + sTATENAMEIPRA + '\'' +
                ", pCLADRZIPCDICRA='" + pCLADRZIPCDICRA + '\'' +
                ", cITYNAMEICRA='" + cITYNAMEICRA + '\'' +
                ", sTATENAMEICRA='" + sTATENAMEICRA + '\'' +
                ", pCLADRZIPCDIWA='" + pCLADRZIPCDIWA + '\'' +
                ", cITYNAMEIWA='" + cITYNAMEIWA + '\'' +
                ", sTATENAMEIWA='" + sTATENAMEIWA + '\'' +
                ", pCLADRZIPCDPPRA='" + pCLADRZIPCDPPRA + '\'' +
                ", cITYNAMEPPRA='" + cITYNAMEPPRA + '\'' +
                ", sTATENAMEPPRA='" + sTATENAMEPPRA + '\'' +
                ", pCLADRZIPCDPCRA='" + pCLADRZIPCDPCRA + '\'' +
                ", cITYNAMEPCRA='" + cITYNAMEPCRA + '\'' +
                ", sTATENAMEPCRA='" + sTATENAMEPCRA + '\'' +
                ", pCLADRZIPCDPWA='" + pCLADRZIPCDPWA + '\'' +
                ", cITYNAMEPWA='" + cITYNAMEPWA + '\'' +
                ", sTATENAMEPWA='" + sTATENAMEPWA + '\'' +
                ", aDMCode='" + aDMCode + '\'' +
                ", aDMDateOfJoining='" + aDMDateOfJoining + '\'' +
                ", customerSegment='" + customerSegment + '\'' +
                ", agentDateOfJoining='" + agentDateOfJoining + '\'' +
                ", agentGender='" + agentGender + '\'' +
                ", aBONonABO='" + aBONonABO + '\'' +
                ", gOQCDate='" + gOQCDate + '\'' +
                ", proposerCLIID='" + proposerCLIID + '\'' +
                ", insuredCLIID='" + insuredCLIID + '\'' +
                ", lGCode='" + lGCode + '\'' +
                ", mAppFlag='" + mAppFlag + '\'' +
                ", lAsMob1='" + lAsMob1 + '\'' +
                ", lAsMob2='" + lAsMob2 + '\'' +
                ", lAsLandLine1='" + lAsLandLine1 + '\'' +
                ", lAsLandLine2='" + lAsLandLine2 + '\'' +
                ", proposerMob1='" + proposerMob1 + '\'' +
                ", proposerMob2='" + proposerMob2 + '\'' +
                ", proposerLandLine1='" + proposerLandLine1 + '\'' +
                ", proposerLandLine2='" + proposerLandLine2 + '\'' +
                ", sellerLandLine1='" + sellerLandLine1 + '\'' +
                ", sellerLandLine2='" + sellerLandLine2 + '\'' +
                ", sellerMobile1='" + sellerMobile1 + '\'' +
                ", sellerMobile2='" + sellerMobile2 + '\'' +
                ", aGENTDOB='" + aGENTDOB + '\'' +
                ", proposerBankAccountNumber='" + proposerBankAccountNumber + '\'' +
                ", axisBankCustomerID='" + axisBankCustomerID + '\'' +
                ", lANAME='" + lANAME + '\'' +
                ", pROPOSERNAME='" + pROPOSERNAME + '\'' +
                ", sPCODE='" + sPCODE + '\'' +
                ", cUSTOMERCLASSIFICATIONCODE='" + cUSTOMERCLASSIFICATIONCODE + '\'' +
                ", riderCount='" + riderCount + '\'' +
                ", ssnCd='" + ssnCd + '\'' +
                ", CIBILTUSCRScoreValue='" + cibiltuscrScoreValue + '\'' +
                ", CIBILTUIE1='" + cibiltuie1 + '\'' +
                ", trlScore='" + trlScore + '\'' +
                ", agentPersistency='" + agentPersistency + '\'' +
                ", applicationSubmitDate='" + applicationSubmitDate + '\'' +
                ", caseSubmitDate='" + caseSubmitDate + '\'' +
                ", residentialStatus='" + residentialStatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", policyPremiumExpiryDate='" + policyPremiumExpiryDate + '\'' +
                ", policyMaturityDate='" + policyMaturityDate + '\'' +
                ", preferredLanguageOfCommunication='" + preferredLanguageOfCommunication + '\'' +
                ", sumAssuredAvailable='" + sumAssuredAvailable + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccOpeningDate='" + bankAccOpeningDate + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                ", posvJourneyStatus='" + posvJourneyStatus + '\'' +
                ", posvJourneyFlag='" + posvJourneyFlag + '\'' +
                ", posvNegativeOrNot='" + posvNegativeOrNot + '\'' +
                ", existingCustomerFlag='" + existingCustomerFlag + '\'' +
                ", existingCustomerActivePolicies='" + existingCustomerActivePolicies + '\'' +
                ", existingCustomerIssuedpolicies='" + existingCustomerIssuedpolicies + '\'' +
                ", existingCustomerActiveAfyp='" + existingCustomerActiveAfyp + '\'' +
                ", existingCustomerTotalAfyp='" + existingCustomerTotalAfyp + '\'' +
                ", agentStatus='" + agentStatus + '\'' +
                ", agentVintage='" + agentVintage + '\'' +
                ", branchbersistency='" + branchbersistency + '\'' +
                
                '}';
    }


}
