package com.mli.mpro.productRestriction.util;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.google.common.collect.ImmutableMap;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppConstants {

    // Product Restriction Fields
    public static final String ACTIVATE = "activate";
    public static final String DEACTIVATE = "deactivate";
    public static final String ENC_KEY = "encKey";
    public static final String AGENT_CODE = "AGENT_CODE";
    public static final String COUNTRY_WITH_WOP_RIDER = "COUNTRY_WITH_WOP_RIDER";
    public static final String COUNTRY_WITH_ADB_RIDER = "COUNTRY_WITH_ADB_RIDER";
    public static final String PIN_CODE = "PIN_CODE";
    public static final String PIN_CODE_1CR_SUM_ASSURED = "PIN_CODE_1CR_SUM_ASSURED";
    public static final String PIN_CODE_2CR_SUM_ASSURED = "PIN_CODE_2CR_SUM_ASSURED";
    public static final String SPP_NRI_COUNTRY = "SPP_NRI_COUNTRY";
    public static final String FATF_COUNTRY = "FATF_COUNTRY";
    public static final String INDIVIDUAL_POLICY = "INDIVIDUAL POLICY";
    public static final String PIN_CODE_SJB_5L_SUM_ASSURED = "PIN_CODE_SJB_5L_SUM_ASSURED";
    public static final String LOCATION_BASED_RESTRICTION = "LOCATION_BASED_RESTRICTION";
    public static final String SSP_NRI_MSG = "Axis Max Life Smart Secure Plus plan is not applicable for NRI Customers";
    public static final String CITY = "CITY";
    public static final String COUNTRY = "COUNTRY";
    public static final String COUNTRY_WITH_CI_RIDER = "COUNTRY_WITH_CI_RIDER";
    public static final String EDUCATION = "EDUCATION";
    public static final String PAN_NUMBER = "PAN_NUMBER";
    public static final String OCCUPATION = "OCCUPATION";
    public static final String CI_RIDER_LEVEL = "TCLCIB";
    public static final String CI_RIDER_INCREASING = "TCICIB";
    public static final String ADDRESS_CURRENT = "Current";
    public static final String FIVE_LAC = "500000";
    public static final String INFLUENCER_41 = "41";

	/*===== Product Codes =====*/
    public static final String FYPP_PRODUCT_ID="24";
    public static final String LPPS_PRODUCT_ID = "27";
    public static final String WLS_PRODUCT_ID="30";
    public static final String PLANID_PW="78"; // Max Life Platinum Wealth Plan
    public static final String FGEP_PRODUCT_ID = "81";
    public static final String CIP_PRODUCT_ID="86";
    public static final String GIP_PRODUCT_ID="94";
    public static final String SAP_PRODUCT_ID = "101";
    public static final String TIS_PRODUCT_ID ="107";
    public static final String SMTP_PRODUCT_ID = "108";
    public static final String OTP_PRODUCT_ID = "116";
    public static final String GLIP_PRODUCT_ID = "127";
    public static final String PLANID_FWP = "132";	// Max Life Flexi Wealth Plus Plan
    public static final String SWP_PRODUCT_ID = "133";
    public static final String SSP_PRODUCT_ID = "154"; // Max Life Smart Secure Plus Plan
    public static final String SJB_PRODUCT_ID = "160";
    public static final String SPP_ID = "164";
    public static final String SWIP = "168";
    public static final String PLANID_FWA = "173";	// Max Life Flexi Wealth Advantage Plan
    public static final String SGPP_ID = "176"; // Max Life Smart Guaranteed Pension Plan
    public static final String CAPITAL_GUARANTEE_SOLUTION = "178";
    public static final String PLANID_SFPS = "184"; // Max Life Smart Flexi Protect Solution Plan
    public static final String SWAG = "186";
    public static final String SWAG_PAR = "194";
    public static final String STEP = "205"; // Max Life Smart Total Elite Protection Plan
    public static final String SWAG_PP_PRODUCT_ID="210"; // Max Life Smart Wealth Annuity Guaranteed Protection Plan

    public static final String GLIPPLANCODE = "IASRC";
    public static final String STPP_PRODUCT_ID = "501";
    public static final String FALCON_YES = "Yes";

    /*===== Product Types with Product Codes =====*/
    public static final List<String> TERM_PRODUCTS = Collections.unmodifiableList(Arrays.asList(SSP_PRODUCT_ID,STEP,STPP_PRODUCT_ID));
    public static final List<String> ANNUITY_PRODUCTS=Collections.unmodifiableList(Arrays.asList(GLIP_PRODUCT_ID,SPP_ID,SGPP_ID,SWAG_PP_PRODUCT_ID));

    /*===== Product Types classification by feature =====*/
    public static final List<String> SWISS_RE_PRODUCTS = Collections.unmodifiableList(Arrays.asList(SSP_PRODUCT_ID,STEP,STPP_PRODUCT_ID));

    /*===== Rider Names =====*/
    public static final String RIDER_SUP = "Axis Max Life Smart Ultra Protect Rider";

    /*===== Rider Variant Names =====*/
    public static final String RIDERVAR_TBATI = "Term Booster with Accelerated TI";
    public static final String RIDERVAR_ADB = "Accidental Death Benefit";
    public static final String RIDERVAR_ATPD = "Accidental Total & Permanent Disability";
    public static final String RIDERVAR_PB= "Payor Benefit";

    /*===== Rider Variants by Rider =====*/
    public static final List<String> RIDERVARS_SUP = Collections.unmodifiableList(Arrays.asList(RIDERVAR_TBATI,RIDERVAR_ADB,RIDERVAR_ATPD,RIDERVAR_PB));

    /*===== Rider Applicable Products =====*/
    public static final List<String> RIDERPRODUCTS_SUP = Collections.unmodifiableList(Arrays.asList(PLANID_SFPS, PLANID_PW, PLANID_FWA, PLANID_FWP));

    /*===== Feature Flags =====*/
    public static final String FEATURE_PF_TERM = "enablePfTermProducts";


    public static final String COMMUNICATION_COUNTRY = "Communication Country | ";
    public static final String PERMANENT_COUNTRY = "Permanent Country | ";
    public static final List<String> productSet=Collections.unmodifiableList(Arrays.asList(SMTP_PRODUCT_ID,CIP_PRODUCT_ID,OTP_PRODUCT_ID,WLS_PRODUCT_ID,GIP_PRODUCT_ID,SJB_PRODUCT_ID,SWP_PRODUCT_ID,SSP_PRODUCT_ID,AppConstants.FPS_PRODUCT_ID,
            AppConstants.SWAG, SWIP, SAP_PRODUCT_ID, FGEP_PRODUCT_ID, LPPS_PRODUCT_ID, TIS_PRODUCT_ID, AppConstants.SWAG_PAR));
    public static final List<String> NATIONALITY_SET=Collections.unmodifiableList(Arrays.asList("Indian", "NRI", "PIO/OCI"));



    public static final String TSGMB = "TSGMB";
    public static final String TSGLB = "TSGLB";
    public static final String ANNUITY_OPT_SELF = "Single Life";
    public static final String ANNUITY_OPT_JOINT = "Joint Life";
    public static final String SELF = "self";
    public static final String CHANNEL_SPARC = "K";
    public static final String CHANNEL_AGENCY = "A";
    public static final String CHANNEL_YBL = "BY";
    public static final String CHANNEL_AXIS = "X";
    public static final String CHANNEL_UCB = "B2";
    public static final String YBL = "YBL";
    public static final String CHANNEL_IMF="F";
    public static final String CHANNEL_PEERLESS = "P";
    public static final String CHANNEL_ASL = "LX";
    public static final String DEPENDENT = "dependent";
    public static final String DOCUMENT_TYPE = "pdf";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String VERSION_INVALID = "VERSION_INVALID";
    public static final String FEATURE_DISABLED = "FEATURE DISABLED";
    public static final String DOCUMENT_UPLOAD_FAILED =	 "DOCUMENT_UPLOAD_FAILED";
    public static final String DOCUMENT_GENERATION_FAILED = "DOCUMENT_GENERATION_FAILED";
    public static final String DATA_MISSING_FAILURE = "DATA_MISSING_FAILURE";
    public static final String TECHNICAL_FAILURE = "TECHNICAL_FAILURE";
    public static final String PROPOSER_PARTY_TYPE = "PROPOSER";
    public static final String CURRENT_ADDRESS = "CURRENT";
    public static final String PERMANENT_ADDRESS = "PERMANENT";
    public static final String INDIAN_NATIONALITY = "INDIAN";
    public static final String INDIA_COUNTRY = "INDIA";
    public static final String NRI = "NRI";
    public static final String FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION="enableNRIGstWaiverDeclaration";
    public static final String BLANK = "";
    public static final String FALIURE = "FAILURE";

    public static final String MEDICAL_SCHEDULE_SERVICE_NAME = "Medical Schedule";
    public static final String MEDICAL_ALREADY_EXIST = "Already Exist ProposalNo:";
    public static final String MEDICAL_STATUS_FAILURE_CODE = "500";
    public static final String DATEFORMAT_WITHOUT_TIME_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String IST_TIME_ZONE = "Asia/Kolkata";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String HH_MM_A ="hh:mm a";
    public static final String HOME_VISIT = "Home Visit";
    public static final String MDI="MDI";
    public final static String MDI_STATIC_LAB_ID = "3952";

    public static final String CENTER_VISIT = "Centre Visit";
    public static final String HIGH_SCHOOL = "High School";
    public static final String SALARIED = "Salaried";
    public static final String SELF_EMPLOYED = "Self Employed";
    public static final String DEDUPE_EX = "EX";
    public static final int ONE_CRORE = 10000000;
    public static final int TWO_CRORE = 20000000;

    // rider names
    public static final String ACD = "Max Life Accidental Death And Dismemberment Rider";
    public static final String AXIS_ACD = "Axis Max Life Accidental Death And Dismemberment Rider";
    public static final String TERM = "Max Life Term Plus Rider";
    public static final String AXIS_TERM = "Axis Max Life Term Plus Rider";

    public static final String WOP = "Max Life WOP Plus Rider";
    public static final String AXIS_WOP = "Axis Max Life WOP Plus Rider";
    public static final String PARTNER = "Max Life PARTNER CARE RIDER";
    public static final String AXIS_PARTNER = "Axis Max Life PARTNER CARE RIDER";
    public static final String CI = "Max Life Accelerated Critical Illness Rider";
    public static final String AXIS_CI = "Axis Max Life Accelerated Critical Illness Rider";
    public static final String ACD_COVER = "Max Life Accidental Cover Rider";
    public static final String AXIS_ACD_COVER = "Axis Max Life Accidental Cover Rider";
    public static final String CPS = "Max Life Comprehensive Protection Secure Rider";
    public static final String TNCPAB = "TNCPAB";
    public static final String TNCIB = "TNCIB";
    public static final String VN04 = "VN04";
    public static final String TCCIB = "TCCIB";
    public static final String VN05 = "VN05";
    public static final String TCCPAB = "TCCPAB";
    public static final String TCPADB = "TCPADB";
    public static final String TCLCIB = "TCLCIB";
    public static final String TCICIB = "TCICIB";
    public static final String VN06 = "VN06";
    public static final String TNPADB = "TNPADB";
    public static final String TCLNIB = "TCLNIB";
    public static final String TCINIB = "TCINIB";
    public static final String VN07 = "VN07";
    public static final String COVID = "COVID 19 One Year Term Rider";
    public static final String RC19T1 = "RC19T1";
    public static final String PP02 = "PP02";
    public static final String RPPT2 = "RPPT2";
    public static final String VP02 = "VP02";
    public static final String VPWOP = "VPWOP";
    public static final String CI_RIDER = "Critical Illness and Disability Rider";
    //FUL2-30525 CIDR- Rider Name Change for ULIP products
    public static final String CI_RIDER_ULIP = "Max Life Critical Illness & Disability- Secure Rider";
    public static final String AXIS_CI_RIDER_ULIP = "Axis Max Life Critical Illness & Disability- Secure Rider";
    public static final List<String> CI_RIDERS = Collections.unmodifiableList(Arrays.asList(CI_RIDER,CI_RIDER_ULIP,AXIS_CI_RIDER_ULIP));
    public static final String TCIGR = "TCIGR";
    public static final String TCIGPR = "TCIGPR";
    public static final String TCIPR = "TCIPR";
    public static final String TCIPPR = "TCIPPR";
    public static final String TCIPDR = "TCIPDR";
    public static final String TCIGL = "TCIGL";
    public static final String TCIGPL = "TCIGPL";
    public static final String TCIPL = "TCIPL";
    public static final String TCIPPL = "TCIPPL";
    public static final String TCIPDL = "TCIPDL";
    public static final String VDWOP = "VDWOP";
    public static final String VEWOP = "VEWOP";
    public static final String TCADB = "TCADB";
    public static final String TCILB = "TCILB";
    public static final String TSPJS = "TSPJS";
    public static final String TSPJR = "TSPJR";
    public static final String TSPJL = "TSPJL";
    public static final String TSPJ6 = "TSPJ6";
    public static final String TSRJS = "TSRJS";
    public static final String TSRJR = "TSRJR";
    public static final String TSRJL = "TSRJL";
    public static final String TSRJ6 = "TSRJ6";
    public static final String TMCCR = "TMCCR";
    public static final String PP03 = "PP03";

    /** Application Timezone Constant */
    public static final String APP_TIMEZONE = "Asia/Kolkata";
    public static final String CSG_STRING = "CSG";

    /** Height Unit Constant */
    public static final String HEIGHT_UNIT = "cm";

    // industry Types
    public static final String AIR_FORCE = "Aviation/AirForce";
    public static final String NAVY = "Merchant Marine/Navy";
    public static final String DEFENCE = "Defence";
    public static final String DIVING = "Diving";
    public static final String OIL = "Oil & Natural Gas";
    public static final String MINING = "Mining";
    public static final String CRPF = "CRPF";
    public static final String MERCHANT_MARINE = "Merchant Marine";
    public static final String OTHERS_INDUSTRY = "Others";

    // PF version
    //FUL2-75012 changing version from TRAD/mPro/ to TRAD_STD_0922_5.9 for trad pf
    public static final String PF_VERSION_START = "TRAD_STD_";
    public static final String PF_VERSION_START_TERM = "TERM_STD_";
    public static final String PF_VERSION_START_HSA = "HSA_STD_";
    public static final String PF_VERSION = "Ver5.2";
    //Version Code
    public static final String PDF_VERSION = "0720/";
    //FUL2-75012 changing version from 1021_ to TRAD_STD_0922_5.9 for trad pf
    //FUL2-144591 changed PDF_VERSION_TRAD from 0922_ to 0523_
    public static final String PDF_VERSION_TRAD = "0125_";
    public static final String PDF_VERSION_TERM = "0125_";
    public static final String PDF_VERSION_HSA = "0125_"; //FUL2-104734/135422 changed from "0623_" to "0923_"
    //FUL2-75012 changed version to 5.9 from 5.7
    //FUL2-144591 changed PF_VERSION_TRAD from 5.9 to 5.10
    public static final String PF_VERSION_TRAD = "5.15";
    public static final String PF_VERSION_TERM = "1.3";
    public static final String PF_VERSION_HSA = "1.4";
    //public static final String GLIP_PF_VERSION = "ANNUITY/STD/0924/2.9";
    public static final String GLIP_PF_VERSION = "ANNUITY/STD/0125/2.10";
    public static final String CIP_AXIS_PFVERSION = "Cancer Insurance/mPro/X/0619/Ver1.0";
    public static final String ULIP_PF_VERSION = "ULIP_STD_0125_5.16";
    public static final String NEW_ULIP_PF_VERSION = "ULIP_STD_0325_5.17";

    // premium modes
    public static final String ONLINE = "online";
    public static final String CHEQUE = "cheque";
    public static final String DIRECTDEBIT = "directdebit";
    public static final String DIRECTDEBITWITHRENEWALS = "directDebitWithRenewals";
    public static final String DEMAND_DRAFT = "demanddraft";
    public static final String PAY_LATER = "payLater";
    public static final String DIRECT_DEBIT = "Direct Debit";
    public static final String DIRECT_DEBIT_IVR = "DIRECT DEBIT (IVR)";
    public static final String EASY_PAY = "Easy Pay";
    /* FUL2-144685 start*/
    public static final String SINGLE_PAY = "Single Pay";
    /* FUL2-144685 end*/
    public static final String LIMITED_PAY = "Limited Pay";

    public static final String CAB = "Max Life Comprehensive Accident Benefit Rider";
    public static final String AXIS_CAB = "Axis Max Life Comprehensive Accident Benefit Rider";
    public static final String APPNAME = "MPRO";
    public static final String RESPONSE_FORM = "CRIF";
    public static final String RESPONSE_FORM_NSDL = "NSDL";

    /** Constant for Investor Risk Profile document generation */
    public static final String INVESTOR_RISK_PROFILE = "Investor risk profile";
    public static final String IRP_MPRO_DOCUMENTID = "IRP_Pr";
    public static final String IRP_YEAR = "February2025";

    public static final String NA = "NA";
    public static final String AXIS_STRING = "AXIS";
    public static final String PRODUCT_TYPE_ULIP = "ULIP";
    public static final String PANDOB_DOCUMENT = "Copy of PAN card- Proposer";
    public static final String PANDOB_DOCUMENT_IN = "Copy of PAN card- Second Annuitant";
    public static final String DOB_DOCUMENT = "Identity Proof or DOB proof- Proposer";
    public static final String DOB_DOCUMENT_IN = "Identity Proof or DOB proof- Second Annuitant";
    public static final String PAYER_PANDOB_DOCUMENT = "PAN Declaration Required for Payor";
    public static final String PAN_DOCUMENTID = "PAN_F60_Pr";
    public static final String PAN_DOCUMENTID_IN = "PAN_F60_In";
    public static final String DOB_DOCUMENTID = "ID_Pr";
    public static final String DOB_DOCUMENTID_IN = "ID_In";
    public static final String PAYOR_DOCUMENTID = "PAN_F60_Pa";
    public static final String PROPOSAL_FORM_DOCUMENT = "PROPOSAL_FORM";
    public static final String ACR_MHR_DOCUMENT = "ACR_MHR_DOCUMENT";
    public static final String COMBO_PROPOSAL_FORM_DOCUMENT = "COMBO_PROPOSAL_FORM";
    public static final String PROPOSAL_FORM_DOCUMENTID = "PROPOSAL_FORM_PDF";
    public static final String ULIP = "ULIP";
    public static final String HSA = "HSA";
    public static final String TRADITIONAL = "TRADITIONAL";
    public static final String CKYC_DOCUMENT = "CKYC";
    public static final String CKYC_DOCUMENTID = "CKYC_PDF";
    public static final String AWP = "AWP";
    public static final String SWP = "SWP";
    public static final String SSP = "SSP";

    public static final String STEP_NEO= "STE";
    public static final String SWPJL = "SWPJL";
    public static final String SOFD_POS = "SOFD_POS";
    public static final String SOFD_NONPOS = "SOFD_NON_POS";
    public static final String SWAG_POS = "SWAG_POS";
    public static final String SWAG_NON_POS = "SWAG_NON_POS";
    public static final String SGPP = "sgpp";
    public static final String SGPPJL = "sgppjl";
    public static final String GLIP_ID = "127";
    public  static final String SWAG_ELITE_PRODUCT_ID="230";
    public static final String SEWA_PRODUCT_ID = "200";
    public static final String STAR_PRODUCT_ID ="502";
    public static final List<String> CIS_SUM_ASSURED_DEATH_PRODUCT = Collections.unmodifiableList(Arrays.asList(SWAG_PAR,SWP_PRODUCT_ID,STEP,SSP_PRODUCT_ID,SWAG,SWAG_ELITE_PRODUCT_ID));
    public static final List<String> CIS_PRODUCT_TOTAL_REQUIRED_MODAL_PREMIUM = Collections.unmodifiableList(Arrays.asList(SWIP,SWAG_PAR,SWP_PRODUCT_ID,STEP,SSP_PRODUCT_ID,SWAG,SWAG_ELITE_PRODUCT_ID,SAP_PRODUCT_ID,SEWA_PRODUCT_ID,STPP_PRODUCT_ID,STAR_PRODUCT_ID));
    public static final String STP = "TRAD";
    public static final String PAN_VALIDATE_DOCUMENT_NAME = "PAN_DOCUMENT";
    public static final String PAN_VALIDATE_DOCUMENT_ID = "PAN_DOCUMENT_PDF";
    public static final String YEARS = " Year(s)";
    public static final String FORM2 = "form2";

    public static final String FORM1 = "form1";
    public static final String IS_AXIS_JOURNEY = "01";


    public static final String ANNUITY_DOCUMENTID = "AF_Pr";
    public static final String ANNUITY_DOCUMENTNAME = "Annuity Form required";
    public static final String ANNUITY_DOCUMENT = "ANNUITY_DOCUMENT";
    public static final String FORM60_DOCUMENT = "FORM60";
    public static final String FORM60_DOCUMENTID = "FORM60_PDF";
    public static final String FORM60_DOCUMENT_IR = "FORM60- Second Annuitant";
    public static final String FORM60_DOCUMENTID_IR = "FORM60_PDF- Second Annuitant";
    public static final String EIA_DOCUMENT = "EIA FORM";
    public static final String EIA_DOCUMENTID = "EIA_Pr";
    public static final String PAYMENT_DOCUMENT = "PAYMENT_RECEIPT";
    public static final String PAYMENT_DOCUMENT_2 = "PAYMENT_RECEIPT_2";
    public static final String PAYMENT_DOCUMENTID = "PAYMENT_RECEIPT_PDF";
    public static final String PAYMENT_DOCUMENTID_2 = "PAYMENT_RECEIPT_PDF_2";
    public static final String DIGITAL_CONSENT_DOCUMENT = "DIGITALCONSENT";
    public static final String DIGITAL_CONSENT_DOCUMENTID = "DC_Pr";
    public static final String MWPA_DOCUMENT = "Addendum MWPA Required";
    public static final String MWPA_DOCUMENTID = "MWPA_Add_Pr";
    public static final String SINGLE_LIFE_ANNUITY_OPTION = "SINGLE LIFE";
    public static final String JOINT_LIFE_ANNUITY_OPTION = "JOINT LIFE";
    public static final String DEFERRED_ANNUITY_OPTION = "DEFERRED ANNUITY";
    public static final String IMMEDIATE_ANNUITY_OPTION = "IMMEDIATE ANNUITY";
    public static final String OBJECTIVETYPE_MWPA = "MWPA";
    public static final String THANOS_DOCUMENT = "THANOS";
    public static final String THANOS_CHANNEL = "Thanos";
    public static final String EBCC_DOCUMENT = "EBCC";
    public static final String EBCC = "eBCC";
    public static final String MHR_DOCUMENT = "MORAL_HAZARD_REPORT";
    public static final String MHR_DOCUMENT_GENERATION_FAILED = "DOCUMENT_GENERATION_FAILED";
    public static final String MHR_DOCUMENT_ID = "MORAL_HAZARD_REPORT_DOCUMENT_PDF";
    public static final String PROPOSAL_E2E_TRANSFORMATION = "proposalE2eTransformation";

    /**Plans*/
    public static final String FOREVER_YOUNG_PENSION_PLAN = "FOREVER YOUNG PENSION PLAN";

    /** Weight Unit Constant */
    public static final String WEIGHT_UNIT = "kg";
    public static final String WHITE_SPACE = " ";
public static final String COMMA_WITH_SPACE = ", ";

    /**Proposal Form Types */
    public static final String PROPOSAL_FORM_TRAD = "PROPOSAL_FORM_TRAD";
    public static final String PROPOSAL_FORM_TERM = "PROPOSAL_FORM_TERM";
    public static final String PROPOSAL_FORM_GLIP = "PROPOSAL_FORM_GLIP";
    public static final String PROPOSAL_FORM_CIP = "PROPOSAL_FORM_CIP";
    public static final String PROPOSAL_FORM_ULIP = "PROPOSAL_FORM_ULIP";
    public static final String PROPOSAL_FORM_HSA = "PROPOSAL_FORM_HSA";
    public static final String PROPOSAL_FORM_POS = "PROPOSAL_FORM_POS";

    //Branch Code Status
    //F21-262
    public static final Integer ACTIVE=1;
    public static final Integer DACTIVE=0;
    public static final Integer PAGELIMIT=2000;

    /**Validation Constatnts*/
    public static final String VALIDATED = "Validated";
    public static final String NOT_VALIDATED = "Not Validated";

    /**Yes/No */
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String CAMEL_YES = "Yes";
    public static final String CAMEL_NO = "No";

    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";

    public static final String DEV = "dev";
    public static final String LOCAL = "local";

    public static final String ISSUER_CONFIRMATION = "ISSUER_CONFIRMATION";
    public static final String ISSUER_CONFIRMATION_ADD = "Communication Address Proof";
    public static final String ISSUER_CONFIRMATION_ID = "Identity Proof or DOB proof- Proposer";
    public static final String FIELD_MODIFIED = "Modified";
    public static final String FIELD_NOT_MODIFIED = "Not_Modified";
    public static final String ECS_MANDATE = "ECS Mandate Form";

    //Payment Renew Mode
    public static final String ENACH_PAYMENT_RENEW_MODE = "ENACH";
    public static final String ECS_PAYMENT_RENEW_MODE = "ECS";
    public static final String CHEQUE_PAYMENT_RENEW_MODE = "CHEQUE";

    public static final String EXACT_MATCH = "EX";
    public static final String CAT = "CAT";

    public static final String CI_RIDER_CODES ="CI_RIDER_CODES";
    public static final String CI_RIDER_SUM_ASSURED_INCOME= "Sum Assured for Critical Illness Rider is outside the applicable limits of |";
    public static final String CI_RIDER_SUM_ASSURED="Client level maximum CI Rider sum assured limit breached. Please select CI Rider SA upto |";
    public static final String CI_RIDER_MIN_SUM_ASSURED="Basis the information provided, customer is not eligible for Critical Illness rider | ";
     /** FUL2-147357**/
    public static final String COUNTRY_MESSAGE= "Country with WOP rider restriction data not available in DB";

    /**PANDOB Document kEY Constants*/
    public static final String PANDOB_APPNAME = "appName";
    public static final String PANDOB_CREDIT_SCORE = "creditScore";
    public static final String PANDOB_DOB = "dob";
    public static final String PANDOB_DOBFLAG = "dobFlag";
    public static final String PANDOB_INCOME_RANGE = "incomeRange";
    public static final String PANDOB_IS_PAYOR = "isPayor";
    public static final String PANDOB_IS_INSURED = "isInsured";
    public static final String PANDOB_PANNAME = "panName";
    public static final String PANDOB_PANNUMBER = "panNumber";
    public static final String PANDOB_PANVALIDATED = "panValidated";
    public static final String PANDOB_PROPOSALNUMBER = "proposalNumber";
    public static final String PANDOB_RESPONSEFROM = "responseFrom";
    public static final String PANDOB_TIMESTAMP = "timeStamp";

    /** Aadhaar Constants */
    public static final String AADHAAR = "Aadhaar";

    public static final String NONE = "None";

    public static final String CIBIL = "CIBIL";
    public static final String CRIF = "CRIF";
    public static final String CIBIL_DOCUMENT = "CIBIL";
    public static final String CIBIL_DOCUMENTID = "CIBIL PDF";
    public static final String CRIF_DOCUMENT = "CRIF";
    public static final String CRIF_DOCUMENTID = "CRIF PDF";
    public static final String CIBIL_CREDIT_SCORE_NAME = "CIBILTUSC3";
    public static final String CIBIL_ESTIMATED_INCOME_NAME = "CIBILTUIE1";
    public static final String CRIF_MATCHED_STATUS = "Matched";
    public static final String MATCHED_STATUS_TRUE = "Y";
    public static final String MATCHED_STATUS_FALSE = "N";
    public static final String OVD = "OVD DOCUMENT";
    public static final String OVD_DOCUMENT_ID = "OVD DOCUMENT PDF";
    public static final String BSE500 = "BSE500 DOCUMENT";
    public static final String BSE500_DOCUMENT_ID = "BSE500 DOCUMENT PDF";
    public static final String BSE500_DOCUMENT_CONTENT = "BSE 500 Case";
    public static final String AWP_PROPOSAL_FORM = "AWP PROPOSAL FORM";

    //payment renewal option
    public static final String CREDIT_CARD_RENEWAL = "Credit Card";

    //Flexi Wealth Plus Plan ProductCode
    public static final String FWP_PRODUCTCODE = "132";
    public static final String FWP_PRODUCTNAME = "Flexi Wealth Plus Plan";

    // CIBIL-CRIF Document Related Constants
    public static final String CB_FIELD_NAME = "name";
    public static final String CB_FIELD_APP_NAME = "appName";
    public static final String CB_FIELD_RESPONSE_NAME = "responseName";
    public static final String CB_FIELD_PROPOSAL_NUMBER = "proposalNumber";
    public static final String CB_FIELD_DOB = "dob";
    public static final String CB_FIELD_PAN_NUMBER = "panNumber";
    public static final String CB_FIELD_ADDRESS = "address";
    public static final String CB_FIELD_PINCODE = "pincode";
    public static final String CB_FIELD_MOBILE_NUMBER = "mobileNumber";
    public static final String CB_FIELD_EMAIL_ID = "emailId";
    public static final String CB_FIELD_OCCUPATION = "occupation";
    public static final String CB_FIELD_CREDIT_SCORE = "creditScore";
    public static final String CB_FIELD_ESTIMATED_INCOME = "estimatedIncome";
    public static final String CB_FIELD_TRL_SCORE = "trlScore";
    public static final String CB_FIELD_FORMAT = "format";
    public static final String CB_FIELD_TIMESTAMP = "timestamp";
    public static final String CB_FIELD_DOB_VALIDATED = "dobValidated";
    public static final String CB_FIELD_NAME_VALIDATED = "nameValidated";
    public static final String CB_FIELD_PAN_NUMBER_VALIDATED = "panNumberValidated";
    public static final String CB_FIELD_ADDRESS_VALIDATED = "addressValidated";
    public static final String CB_FIELD_PINCODE_VALIDATED = "pincodeValidated";
    public static final String CB_FIELD_MOBILE_NUMBER_VALIDATED = "mobileNumberValidated";
    public static final String CB_FIELD_EMAIL_ID_VALIDATED = "emailIdValidated";
    public static final String CB_FIELD_OCCUPATION_VALIDATED = "occupationValidated";
    public static final String CB_FIELD_CREDIT_SCORE_VALIDATED = "creditScoreValidated";
    public static final String CB_FIELD_ESTIMATED_INCOME_VALIDATED = "estimatedIncomeValidated";
    public static final String CB_FIELD_TRL_SCORE_VALIDATED = "trlScoreValidated";
    public static final String HYPHEN = "-";
    public static final String CIBIL_FIELD_NAME_VALIDATED = "nameValidatedcibil";
    public static final String CIBIL_FIELD_DOB_VALIDATED = "dobValidatedcibil";
    public static final String CIBIL_FIELD_PAN_NUMBER_VALIDATED = "panNumberValidatedcibil";

    //CKYC Constant For NEO
    public static final String CHANNEL_NEO = "NEO";
    public static final String CHANNEL_AGGREGATOR = "AGGREGATOR";
    public static final String TITLE = "title";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String MAIDEN_NAME = "maidenName";
    public static final String PROPOSER_NAME = "proposerName";
    public static final String MOTHER_NAME = "motherName";
    public static final String FATHER_NAME = "fatherName";
    public static final String GENDER = "gender";
    public static final String CKYC_DOB = "dob";
    public static final String NATIONALITY = "nationality";
    public static final String MARITIAL_STATUS = "maritialStatus";
    public static final String RESIDENTAL_STATUS = "residentalStatus";
    public static final String CKYC_OCCUPATION = "occupation";

    public static final String ADDRESS_PROOF_TYPE = "addressProofType";
    public static final String HOUSE_NUMBER = "houseNumber";
    public static final String AREA = "area";
    public static final String LANDMARK = "landmark";
    public static final String CKYC_CITY = "city";
    public static final String STATE = "state";
    public static final String CKYC_PIN_CODE = "pinCode";
    public static final String CKYC_COUNTRY = "country";

    public static final String OVERSEAS_ADDRESS = "overseasAddress";
    public static final String EMAIL = "email";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String NAME_OF_APPLICANT = "nameOfApplicant";
    public static final String PLACE = "place";
    public static final String CURRENT_DATE = "currentDate";

    public static final String TAX_IDENTIFICATION_NUMBERS = "taxIdentificationNumberList";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String CITY_OF_BIRTH = "cityOfBirth";
    public static final String COUNTRY_CODE_OF_BIRTH = "countryCodeOfBirth";
    public static final String KYC_NUMBER = "kycNumber";
    public static final String ID_PROOF_NAME = "idProofName";
    public static final String ID_PROOF_NUMBER = "idProofNumber";
    public static final String ID_PROOF_EXPIRY = "idProofExpiry";
    public static final String ADDRESS_PROOF_NAME = "addressProofName";
    public static final String ADDRESS_PROOF_EXPIRY = "addressProofExpiry";
    public static final String APPLICATION_TYPE = "applicationType";
    public static final String CKYC_EXISTING = "EXISTING";
    public static final String CKYC_NEW = "NEW";
    public static final String FTIN_EXIST = "FTINExist";
    public static final String COUNTRY_OF_RESIDENCE = "countryOfResidence";
    public static final String FTIN_OR_PAN = "ftinOrPan";
    public static final String COUNTRY_OF_RESIDENCE_AS_PER_TAX_LAW = "countryOfResidenceAsPerTaxLaw";


    // Proposal Form Annexure Constant
    public static final String INDUSTRY_TYPE = "industryType";
    public static final String INDUSTRY_TYPE_OTHER = "industryTypeOthers";
    public static final String NATURE_OF_DUTIES = "natureOfDuties";
    public static final String DEFENCE_REFLEXIVE_1 = "defenceReflexive1";
    public static final String DEFENCE_REFLEXIVE_2 = "defenceReflexive2";
    public static final String AVIATION_REFLEXIVE_1 = "aviationReflexive1";
    public static final String AVIATION_REFLEXIVE_2 = "aviationReflexive2";
    public static final String DIVING_REFLEXIVE_1 = "divingReflexive1";
    public static final String DIVING_REFLEXIVE_2 = "divingReflexive2";
    public static final String OIL_REFLEXIVE_1 = "oilReflexive1";
    public static final String NAVY_REFLEXIVE_1 = "navyReflexive1";
    public static final String MINING_REFLEXIVE_1 = "miningReflexive1";
    public static final String MINING_REFLEXIVE_2 = "miningReflexive2";
    public static final String JOB_TYPE = "jobType";
    public static final String OFFICIAL_EMAIL = "officialEmail";
    public static final String TENURE_OF_JOB = "tenureOfJob";

    public static final String POLITICALLY_EXPOSED = "politicallyExposed";
    public static final String PEP_PERSON = "pepPerson";
    public static final String IS_LIFE_INSURED_PEP = "isLIPep";
    public static final String IS_FAMILY_MEMBER_PEP = "isFamilyPep";
    public static final String SPECIFY_FAMILY_MEMBERS = "specifyFamilyMembers";
    public static final String POLITICAL_EXPERIENCE = "politicalExperience";
    public static final String PARTY_AFFILATIONS = "partyAffilations";
    public static final String PEP_PORTFOLIO_HANDLED = "pepPortfolioHandled";
    public static final String ROLE_IN_PARTY = "roleInParty";
    public static final String ROLE_IN_PARTY_OTHERS = "roleInPartyOthers";
    public static final String PARTY_IN_POWER = "partyInPower";
    public static final String PEP_POSTED_IN_FOREIGN_OFFICE = "pepPostedInForeignOffice";
    public static final String PEP_POSTED_FOREIGN_OFFICE_DETAILS = "pepPostedForeignOfficeDetails";
    public static final String PEP_INCOME_SOURCES = "pepIncomeSources";
    public static final String PEP_EVER_CONVICTED = "pepEverConvicted";
    public static final String PEP_CONVICTED_DETAILS = "pepConvictedDetails";

    public static final String TRAVEL_OR_RESIDE_ABROAD = "travelOrResideAbroad";
    public static final String TRAVEL_COUNTRY_CODE = "travelCountryCode";
    public static final String TRAVEL_CITIES = "travelCities";
    public static final String TRAVEL_PURPOSE = "travelPurpose";
    public static final String DURATION_OF_STAY = "durationOfStay";
    public static final String COUNTRIES_TO_BE_VISITED = "countriesToBeVisited";

    public static final String IS_TOBACCO_ALCOHOL_DRUGS_CONSUMED = "isTobaccoAlcoholDrugsConsumed";
    public static final String CONSUMPTIONS_DETAILS = "specifyConsDetails";
    public static final String NEVER_BEEN_DIAGNOSED = "neverBeenDiagnosed";

    public static final String NEW_DIABETIC_QUESTION = "newDiabeticQuestion";

    public static final String NEW_HYPERTENSION_QUESTION = "newHypertensionQuestion";

    public static final String NEW_RESPIRATORY_QUESTION = "newRespiratoryQuestion";
    public static final String MEDICAL_QUESTIONS = "medicalQuestions";
    public static final String AGGREGATOR_MEDICAL_QUESTIONS = "AGGREGATOR_MEDICAL_QUESTIONS";
    public static final String FAMILY_DIAGNOSED_WITH_DISEASES = "familyDiagnosedWithDiseases60";
    public static final String HIV_CANCER_HISTORY = "isHivCancerHistoryApplicable";
    public static final String HIV_CANCER_TUMOR_HISTORY = "hivCancerTumorHistory";
    public static final String FAMILY_DIAGNOSE_WITH_DISEASE_DETAILS = "familyDiagnosedWithDiseasesDetails";
    public static final String EVER_BEEN_HOSPITALIZED = "everBeenHospitalized";
    public static final String IS_ULTRASOUND = "isUltrasound";
    public static final String IS_MRI_SCAN = "isMriScan";
    public static final String ISNEWFAMILYHISAPPLICABLE = "isNewFamilyHisApplicable";

    public static final String DRUGS_CONSUMED = "drugsConsumed";
    public static final String DRUGS_CONSUMED_DETAILS_LIST = "drugsConsumptionDetailsList";

    public static final String TOBACCO_CONSUMED = "tobaccoConsumed";
    public static final String TOBACCO_CONSUMED_DETAILS_LIST = "tobaccoConsumptionDetailsList";

    public static final String ALCOHOL_CONSUMED = "alcoholConsumed";
    public static final String ALCOHOL_CONSUMED_DETAILS_LIST = "alcoholConsumptionDetailsList";

    public static final String NEO_YES = "01";
    public static final String NEO_NO = "02";
    public static final String NEO_Y = "Y";
    public static final String NEO_N = "N";
    public static final String DECLARATION_VERSION_DATE = "declarationVersionDate";

    public static final String AADHAR = "Aadhar";
    public static final String PROPOSER_HEIGHT_CM = "proposerHeightCm";
    public static final String PROPOSER_HEIGHT_FT = "proposerHeightFt";
    public static final String PROPOSER_HEIGHT_IN = "proposerHeightIn";
    public static final String INSURED_HEIGHT = "insuredHeight";
    public static final String PROPOSER_WEIGHT = "proposerWeight";
    public static final String INSURED_WEIGHT = "insuredWeight";


    public static final String EVER_LI_ISSUED_PENDING_LAPSED = "everLIIssuedPendingLapsed";
    public static final String EVER_LI_REJECTED_PENDING = "everLIRejectedPostponed";
    public static final String EVER_LI_ISSUED_PENDING_LAPSED_LIST = "everLIIssuedPendingLapsedDetailsList";
    public static final String EVER_LI_REJECTED_PENDING_LIST = "everLIRejectedPostponedDetailsList";
    public static final String TOTAL_SUM_ASSURED_LI = "totalSumAssuredLifeIns";
    public static final String TOTAL_SUM_ASSURED_CI = "totalSumAssuredCriticalIllness";
    public static final String EVER_LI_ISSUEDPENDING_OR_REJECTEDPOSTPONED = "everLIIssuedPendingOrRejectedPostponed";

    // For party role conversion
    public static final String SOCIAL_WORKER = "SocialWorker";
    public static final String MLA = "MLA";
    public static final String MP = "MP";
    public static final String ROLE_ONE = "01";
    public static final String ROLE_TWO = "02";
    public static final String ROLE_THREE = "03";
    public static final String ROLE_FOUR = "04";

    // repositary name
    public static final String NDM_REPO_NAME = "NSDL Database Management Limited";
    public static final String SCH_REPO_NAME = "SHCILIR";
    public static final String CIR_REPO_NAME = "Central Insurance Repository Limited";
    public static final String KVY_REPO_NAME = "Karvy Insurance Repository Limited";
    public static final String CAM_REPO_NAME = "CAMS Repository Services Limited";

    public static final String MODE_OF_PAYMENT = "modeOfPayment";
    public static final String PSM_SERVICE = "Onboarding PSM";
    public static final String PRODUCT_ID= "productId";

    //Smart Wealth Plan ProductCode
    public static final String SWP_PRODUCTCODE = "133";
    public static final String LONGTERM_INCOME = "Long Term Income";
    public static final String SHORTTERM_INCOME = "Short Term Income";
    public static final String WHOLE_LIFE_INCOME = "Whole Life Income";
    public static final String EARLY_WEALTH = "Early Wealth";
    public static final String REGULAR_WEALTH = "Regular Wealth";
    public static final String WEALTH_FOR_MILESTONES = "Wealth for Milestones";

    public static final String DD_MMMM_YYYY_SPACE = "dd MMMM yyyy";
    public static final String DD_MM_YYYY_SPACE = "dd MMM yyyy";
    public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
    public static final String DD_MM_YYYY_HYPHEN = "dd-MM-yyyy";
    public static final String DD_MM_YYYY_HH_MM_SS_HYPHEN = "dd-MM-yyyy HH:mm:ss";
    public static final String DD_MM_YYYY_HH_MM_SS_HYPHEN_A = "dd-MM-yyyy HH:mm:ss a";
    public static final String YYYY_MM_DD_HH_MM_SS_HYPHEN_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String MMM_DD_YYYY_HH_MM_SS_HYPHEN_A = "MMM-dd-yyyy HH:mm:ss a";
    public static final String E_MMM_DD_YYYY_HH_MM_SS_GMT = "E MMM dd yyyy HH:mm:ss 'GMT'z";
    public static final String E_MMM_DD_YYYY_HH_MM_SS_Z = "E MMM dd HH:mm:ss Z yyyy";
    public static final String DD_MM_YYYY_HH_MM_SS_AA_SLASH = "dd/MM/yyyy hh:mm:ss aa";
    public static final String PAYMENT_DATE_CONSTANT = "10-07-2020 23:59:59";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS_Z_CHAR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String YYYY_MM_DD_HH_MM_SSS_SSSS  ="yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS"; //FUL2-98649 _ Axis Bank Opening Date check for VPOSV
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    //FUL2-7386- Declaring constants
    public static final String DOCUMENT_STATUS = "documentStatus";
    public static final String PAYOR = "payor";
    public static final String SECOND_ANNUITANT = "secondAnnuitant";
    public static final String S3PATH = "NB/B2B/mPRO/";
    public static final String OTHER_INCOME = "Other Income";
    public static final String TRANSACTION_NUMBER = "transactionNumber";
    public static final String PRAPOSAL_FORM_VERSION = "1218/";
    public static final String NRI_QUESTIONAIRE = "NRI Questionaire";
    public static final String NRI_QUESTIONARE = "NRI Questionare";
    public static final String ERROR_MESSAGE = "Error Msg";
    public static final String NOT_APPLICABLE = "Not Applicable";
    public static final String NRI_GST_WAIVER = "NRI_GST_WAIVER";
    public static final String NRI_GST_WAIVER_DECLARATION = "NRI_GST_WAIVER_DECLARATION";
    public static final String ANNUAL = "Annual";
    public static final String SEMI_ANNUAL = "Semi-Annual";
    public static final String MONTHLY = "Monthly";
    public static final String QUARTERLY = "Quarterly";

    //Declaring Constants for Date Format
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String OTHERS = "others";
    public static final String OTHER = "other";
    public static final String SOURCE_OF_FUND_FWP_OTHERS = "Others";

    // Numeric Constants
    public static final String ZERO = "0";
    public static final String ONE = "1";

    //Channel Names
    public static final String PEERLESS = "Peerless";
    public static final String ASL = "ASL";
    public static final String CATAXIS= "CatAxis";
    public static final String CATAXISB= "CatAxisB";
    public static final String BROKER = "Broker";
	public static final String TELEDIY = "TeleDIY";
    public static final String ONBOARDING = "onboarding";
    public static final String AUBANK = "aubank";
    public static final String BRMS_BROKER_DIY_JOURNEY = "DIY";
    public static final String TURTLEMINT = "Turtlemint";
    public static final String MotilalOswal = "Motilal Oswal";
    public static final String MIB = "Mahindra Insurance";
    //FULL2-8762 OTP and Smtp Stop Rules
    public static final String OCCUPATION_HOUSE_WIFE="Housewife";

    /*FUL2-11549 Payment acknowledgement for all channels*/
    public static final String PROPOSER = "Proposer";
    public static final String MALE = "M";
    public static final String FEMALE = "F";
    public static final String MR = "Mr.";
    public static final String MS = "Ms.";
    public static final String MX = "MX.";


    public static final String EBCC_AXIS = "EBCC_AXIS";
    public static final String TELE_MED = "Tele_Med";

    public static final String FACT_FINDER ="Fact Finder";
    //FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
    public static final String DIRECT_DEBIT_MANDATE = "Direct Debit Mandate";
    public static final String SINGLE = "Single";
    public static final String CKYC_DOWNLOAD_DOCUMENT = "CKYC_DOWNLOAD" ;

    //SWP LUMPSUM
    public static final String TSWPPL = "TSWPPL";
    public static final String TSWPPR = "TSWPPR";
    public static final String TSWVTL = "TSWVTL";
    public static final String TSWVTS="TSWVTS";
    public static final String TSWPVR="TSWPVR";
    public static final String TSWPVL="TSWPVL";


    public static final String LOCATION_TYPE_COUNTRY = "Country";
    public static final String LOCATION_TYPE_STATE = "State";
    public static final String LOCATION_TYPE_CITY = "City";

    //NR-765 Rider PPT name
    public static final String RIDER_PPT = "Same as Rider Coverage Term";

    public static final String NEO_SUMMARY_DOCUMENT = "SUMMARY_DOCUMENT";
    public static final String PERMANENT_ADDRESS_NEO = "PRA";
    public static final String CURRENT_ADDRESS_NEO = "CRA";
    public static final String SHIELD_PRO_RISK_DOCUMENT = "SHIELD_PRO_RISK";
    public static final String PHOTO_WAIVER_DOCUMENT = "PHOTO_WAIVER_DOCUMENT";

    public static final List<String> otpProductCodes = Arrays.asList("TCOTP2","TNOTP2","TCOT60","TNOT60");
    public static final String DATE_FORMAT_MMMM_D = "MMMM d'";
    public static final String DATE_FORMAT_YYYY_HH_MM_SS = "' yyyy',' hh:mm:ss a";
    public static final String MONTH_FORMAT = "dd-MMMM-yyyy";

    //NEO QUESTIONNAIRE TYPE
    public static final String DIABETIC_QUESTIONNAIRE = "DIABETIC";

    public static final String AXIS_TELESALES_REQUEST = "AXISR";

    public static final String AADHAAR_OTP_AUTH = "AadhaarOTPAuth";
    public static final List<String>  AADHAAR_ERROR_MESSAGE= Arrays.asList("Maximum number of attempts for OTP match is exceeded or OTP is not generated. Please generate a fresh OTP and try to authenticate again","Invalid Aadhar Number","Invalid OTP value","Invalid Aadhaar Number/Virtual ID/ANCS/UID TOKEN");
    public static final String TECHNICAL_ERROR = "Technical";


    //Ful2-14118
    public static final String PINCODE_STATE = "state";
    public static final String PINCODE_CITY = "city";
    public static final String OAUTH_KEY = "SoaOauthKey";
    public static final String AUTH = "Authorization";
    public static final String FAIL_STATUS = "Fail";
    public static final String PRATHAM_SERVICE_NAME = "pratham";
    public static final String EKYC_SERVICE_NAME = "EKYC Service";
    public static final String EKYC_DETAILS_MISSING = "Required EKYC details are missing or not valid";
    public static final String EKYC_AADHAAR_REGEX = ".(?=.{4})";

    public static final String EMAIL_FROM = "DoNotReply@axismaxlife.com";
    public static final String FROM_NAME = "Axis Max Life Insurance";
    public static final String HIGH_BLOOD_PRESSURE_QUESTIONNAIRE = "HIGH BLOOD PRESSURE";
    public static final String RESPIRATORY_DISORDER_QUESTIONNAIRE = "RESPIRATORY DISORDER";

    //Ful2 -14812
    public static final String LUMP_SUM = "Lump Sum";
    //FUL2-14592
    public static final String INSURED = "Insured";
    public static final String INSURER = "Insurer";
    public static final String ILLITERATE="Illiterate";
    public static final String MONTHS = " months";
    public static final String PANDOB_DOCUMENT_GEN_MSG_PR = "Generating Proposer PANDOB Document for transactionId {}";
    public static final String PANDOB_DOCUMENT_GEN_MSG_IN = "Generating Insured PANDOB Document for transactionId {}";
    public static final String PANDOB_DOCUMENT_GEN_MSG_AS = "Generating Authorized Signatory PANDOB Document for transactionId {}";
    public static final String PANDOB_DOCUMENT_GEN_FAIL_MSG_AS = "Authorized Signatory's Document generation is failed so updating in DB for transactionId {}";
    public static final String PANDOB_DOCUMENT_GEN_FAIL_MSG_PR = "Proposer's Document generation is failed so updating in DB for transactionId {}";
    public static final String PANDOB_DOCUMENT_GEN_FAIL_MSG_IN = "Insured Document generation is failed so updating in DB for transactionId {}";

    public static final String FORM60_DOCUMENT_GEN_MSG_PR = "Generating Proposer FORM60 Document for transactionId {}";
    public static final String FORM60_DOCUMENT_GEN_MSG_IN = "Generating Insured FORM60 Document for transactionId {}";
    public static final String FORM60_DOCUMENT_GEN_FAIL_MSG_PR = "Proposer's Document generation is failed so updating in DB for transactionId {}";
    public static final String FORM60_DOCUMENT_GEN_FAIL_MSG_IN = "Insured Document generation is failed so updating in DB for transactionId {}";

    //Ful2-14801 Proposal Rejection
    public static final String PROPOSAL_REJECTION = "proposalrejection";
    public static final String SELLER = "SELLER";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String FULFILLMENT = "FULFILLMENT";

    //Constant for Relationship Type
    public static final String OTHER_RELATIONSHIP="Others";

    public static final String CREDIT_CARD = "Credit Card";
    public static final String DEBIT_CARD = "Debit card";
    public static final String CASH = "Cash";
    public static final String CARDS = "Cards";

    public static final String E_INC_ACC_NO_START_WITH_1 = "1";
    public static final String E_INC_ACC_NO_START_WITH_2 = "2";
    public static final String E_INC_ACC_NO_START_WITH_3 = "3";
    public static final String E_INC_ACC_NO_START_WITH_4 = "4";
    public static final String E_INC_ACC_NO_START_WITH_5 = "5";
  //FUL2-19749
    public static final String COVID_QUESTIONAIRE = "Covid Questionaire";

    //FUL2-116960
    public static final String ANNEXURE_MEDICAL = "Annexure Medical";

    //FUL2-20590
    public static final List<String> LOG_TYPE_LIST = Arrays.asList("org.slf4j.helpers", "Utility");
    public static final String MASKED_REGEX = "[x]+";
    public static final String DEFAULT_MASK = "xxxx";
    public static final String SHOW_LAST_TWO_DIGITS_REGEX = ".(?=.{2})";
    public static final String SHOW_FIRST_LAST_DIGITS_REGEX = "(?<=.).(?=.)";
    public static final String SHOW_FIRST_DIGIT_REGEX = "(?<=.).";
    public static final String SHOW_LAST_DIGIT_REGEX = ".(?=.)";
    public static final String SHOW_LAST_FOUR_DIGITS_REGEX = ".(?=.{4})";
    public static final String MASK_ALL_REGEX = ".";
    public static final String FOLLOWUPS = "followUps";
    public static final String FOLLOWUPSOTHER = "followUpsOther";
    public static final String MEDICATIONDETAILS = "medicationDetails";
    public static final String SENIOR_SCHOOL="SENIOR SCHOOL";

    /** Added for FUL2-21156*/
    public static final String SELLER_DECLARATION_DOCUMENT = "SELLER_DECLARATION_DOCUMENT";
    public static final String SELLER_DECLARATION_DOCUMENT_ID = "SELLER_DECLARATION_DOCUMENT_PDF";
    public static final String SP_NAME = "spName";
    public static final String PERIOD_DURATION = "periodDuration";
    public static final String SELLER_CODE = "sellerCode";
    public static final String DATE_TIME = "dateTime";
    public static final String SELLER_DISCLOSURE = "sellerDisclosure";
    public static final String SELLER_NAME = "sellerName";
    public static final String CUSTOMER_NUMBER = "customerNumber";
    public static final String SP_AGENT_NAME = "spAgentName";
    public static final String SP_AGENT_CODE = "spAgentCode";
    public static final String SELLER_CONFIRMATION = "sellerConfirmation";
    public static final String ANSWER_AS_YES = "AnsAsYes";
    public static final String GRADUATE="GRADUATE";
    public static final String POST_GRADUATE="POST GRADUATE";
    public static final String PROFESSIONAL="PROFESSIONAL";

    public static final List<String> OCCUPATIONS=Collections.unmodifiableList(Arrays.asList(SALARIED,SELF_EMPLOYED));

    public static final String REQUEST_SOURCE_THANOS2 = "Thanos 2";
    public static final String REQUEST_SOURCE_TELESALES = "telesales";
	public static final String S3_SERVICE_NAME = "s3Delete";

	public static final String SELLER_CONSENT_STATUS = "COMPLETED";

    public static final String HIGH_GROWTH_FUND = "highGrowthFund";
    public static final String GROWTH_SUPER_FUND = "growthSuperFund";
    public static final String GROWTH_FUND = "growthFund";
    public static final String BALANCED_FUND = "balancedFund";
    public static final String CONSERVATIVE_FUND = "conservativeFund";
    public static final String SUSTAINABLE_EQUITY_FUND = "sustainableEquityFund";
    public static final String PURE_GROWTH_FUND = "pureGrowthFund";
    public static final String SECURE_FUND = "secureFund";
    public static final String DIVERSIFIED_EQUITY_FUND = "diversifiedEquityFund";
    public static final String DYNAMIC_BOND_FUND = "dynamicBondFund";
    public static final String MONEY_MARKET_FUND_II = "moneyMarketFundII";
    public static final String PENSION_PRESERVER_OPTION = "Pension Preserver Option";
    public static final String PENSION_MAXIMISER_OPTION = "Pension Maximiser Option";
    public static final String PENSION_PRESERVER_FUND = "pensionPreserverFund";
    public static final String PENSION_MAXIMISER_FUND = "pensionMaximiserFund";
    public static final String DOCUMENT_GENERATION_FAILED_LOG= "Document generation is failed so updating in DB for transactionId {}";
    public static final String IS_NOT_YBL_PROPOSAL = "isNotYBLProposal";
    public static final String POLICY_NUMBER = "PolicyNumber";
    public static final String SELLER_DISCLOSURE_THANOS = "isThanosSellerDisclosed";
    public static final String PANAADHAR_LINKSTATUS = "panAadhaarLinkStatus";
    public static final String DOC_TYPE = "document";
    public static final String BANK_JOURNEY_YBL = "ybl";
    public static final String URMU_MODEL = "URMU Model";
    public static final String PERSISTENCY_MODEL = "Persistency Model";
    public static final String BOTH_MODEL = "URMU & Persistency Both Models";
    public static final String PAN_2_DOCUMENT = "PAN_2_DOCUMENT";
    public static final String COMMA = ",";
    public static final List<String> MPRO_CHANNELS = Arrays.asList("K", "F", "A", "X", "T", "BY", "B", "B2", "P", "LX", "J");

    public static final String AGENT_ADVISOR_NAME = "Policybazaar Insurance Brokers Pvt Ltd.";
    public static final String INFLUENCER_CHANNEL_38 = "38";
    public static final String SECURE_PLUS_FUND = "securePlusFund";


    public static final List<String> TermProductSet=Collections.unmodifiableList(Arrays.asList(SJB_PRODUCT_ID,SSP_PRODUCT_ID));
    public static final List<String> SELLERDECLARATION_NOTAPPLICABLE_TERMPRODUCTSSET=Collections.unmodifiableList(Arrays.asList(CIP_PRODUCT_ID,GLIP_ID,SPP_ID));

    //FUL2-100753 added 8 new roles for CAT channel- SOT,AXA,DMX,RXM,STR,BRT,TMT,ZMT
    public static final List<String> catAgentRoles = Collections.unmodifiableList(Arrays.asList("CAD","CSO","CRM","ACA","RMT","ESS","EAS","EAD","ERC","ESC","MBM","SBI","SOT","AXA","DMX","RXM","STR","BRT","TMT","ZMT","ICL","SPT"));

    public static final String ALARM_RISK = "01) Alarming Risk";
    public static final String HIGH_RISK = "02) High Risk";
    public static final String MEDIUM_RISK = "03) Medium Risk";
    public static final String PERS_ALARM_RISK = "1) Alarming Risk";
    public static final String VERY_HIGH_RISK = "2) Very High Risk";
    public static final String PERS_HIGH_RISK = "3) High Risk";
    public static final String VIDEO_POSV = "VideoPOSV";
    public static final String STANDARD_POSV = "StandardPOSV";
    public static final String HANDLER_TYPE = "LOCATION_SERVICE";
    public static final String PASA_HANDLER = "PASA_SERVICE";
    public static final String ALL = "ALL";

    public static final String MLIC_CKYC = "MLIC_CKYC_DOCUMENT";
    public static final List<String> FDRD_SWAG_SWP_POS_PRODUCTS = Collections.unmodifiableList(Arrays.asList(TSWVTL,TSWVTS,TSWPVR,TSWPVL, "TSWRP1","TGBP5","TPFDGS","TPFDPL","TPFDPR","TPFDTL","TPFDTR","TPSGMS","TPSGML"
            ,"TPSGMB","TPSGRL","TPSGRB","TPSGEL","TPSGER","TPSELB","TPSERB"));
    //constants
    public static final String LIPEP = "LIPep";
    public static final String PROPOSERPEP = "proposerPep";
    public static final String NOMINEEPEP = "nomineePep";
    public static final String FAMILY_MEMBERPEP = "familyMemberPep";
    public static final String PAYORPEP = "payorPep";
    public static final String PORTFOLIO = "portfolio";
    public static final String NRI_DOCUMENT = "NRI";
    public static final String NRI_DOCUMENTID = "NRI_PDF";
    public static final double POS_TRANSGENDER_MAX_SA = 250000;
    public static final double POS_MAX_SA = 2500000;
    public static final double TWO_LACS = 2.5;
    public static final int TWENTYFIVE_LACS = 25;
    public static final String MSG_POS_TRANSGENDER_MAX_SA = "Maximum Sum assured allowed is " + AppConstants.TWO_LACS + " Lacs";
    public static final String MSG_POS_MAX_SA = "Maximum Sum assured allowed is " + AppConstants.TWENTYFIVE_LACS + " Lacs";
    public static final List<String> POS_PRODUCTS =  Collections.unmodifiableList(Arrays.asList(TSWVTL,TSWVTS,TSWPVR,TSWPVL));
    public static final String FORM3 = "businessInsurance";
    public static final String COMPANY = "Company";
    public static final String AUTHORIZED_SIGNATORY = "Authorized Signatory";
    public static final String CEIP = "CEIP";
    public static final String EMPLOYER_EMPLOYEE = "Employer - Employee";
    public static final String MEDICAL_SCHEDULE_DOCUMENT = "Medical Schedule Document";
    public static final String MEDICAL_SCHEDULE_DOCUMENT_ID = "Medical_Schedule_Document_PDF";
    public static final String IS_PINCODE_CHANGED = "01";
    public static final String CIBIL_INCOME = "cibilIncome";
    public static final String CIBILTUSC1 = "CIBILTUIE1";
    public static final String PAYU_DOCUMENT = "PayU Document";
    public static final String PAYU_DOCUMENT_ID = "Regular Income Proofs";

    public static final String DOB_DOCUMENT_AS = "Identity Proof or DOB Proof";
    public static final String PAN_DOCUMENT_AS = "Copy of PAN Card - Authorized Signatory";
    public static final String PAN_AS = "PAN_F60_As";
    public static final String DOB_AS = "Dob_As";
    public static final String AXIS_AGE = "AXIS CHANNEL AND AGE GREATER THAN OR EQUAL 60";
    public static final String FORM_TYPE = "businessInsurance";

    //FUL2-45078
	public static final List<String> OBJECTIVE_OF_INSURANCE = Collections.unmodifiableList(Arrays.asList(CEIP, EMPLOYER_EMPLOYEE));
	public static final String FORM_TYPE_SELF = "self";
    public static final String NEED_OF_INSURANCE = "Wealth accumulation/Investment";
    //FUL2-49759 SSP - Pincode Logic Change - Agent level Based
    public static final int FIVE = 5;
    public static final String AND = " & ";

    public static final String JOINT = "JOINT";
    public static final String JOINT_PROPOSER = "JOINT_PROPOSER";
    public static final String JOINT_INSURED = "JOINT_INSURED";

    //FUL2-46299
    public static final String EXISTING_POLICY_NUMBER_KEY = "existingPolicyNumber";
    public static final String PREVIOUS_POLICY_NUMBER_KEY = "previousPolicyNumber";
    public static final String COMBO_PROPOSAL_NUMBER = "comboProposalNumber";

    public static final String INSTA_COI_DOCUMENT = "INSTA_COI_DOCUMENT";
    public static final String INSTA_COI_DOCUMENT_PDF = "INSTA_COI_DOCUMENT_PDF";
    public static final String BLACKLISTED_COUNTRIES = "Blacklisted countries not applicable";
    //FUL2-54076-54297_Broker-Channel-GoCode
    public static final String CHANNEL_BROKER = "J";
    public static final List<String> VALID_GO_CODES = List.of("ZHO01", "ZHO02");
    public static final List<String> ACCEPTABLE_SOURCE_CHANNEL = Collections.unmodifiableList(Arrays.asList(CHANNEL_BROKER));
    public static final String PRODUCT_REDIRECTION_PATH = "productRestrictionPayload";
    public static final String PRAN_MSG = "PRAN_DUPLICATE_FOUND";
    public static final String SOAAPP_ID = "FULFILLMENT";
    public static final String PRAN_MSG_CODE = "410";
    public static final String EXISTS= "Exist";
    public static final String PRAN_DESC = "The provided PRAN already exists in the system.";
    public static final String DOB_PATH = "request.requestData.requestPayload.productRestrictionPayload.dateOfBirth";
    public static final List<String> CHANNEL_BROKER_GO_CODE_START_WITH = Collections.unmodifiableList(Arrays.asList("Q", "U"));
    public static final String PRODUCT_PATH = "request.requestData.requestPayload.productRestrictionPayload.productId";
    public static final String COMM_PINCODE_PATH = "request.requestData.requestPayload.productRestrictionPayload.communicationPinCode";
    public static final String PT_PATH = "request.requestData.requestPayload.productRestrictionPayload.policyTerm";
    public static final String PPT_PATH = "request.requestData.requestPayload.productRestrictionPayload.premiumPaymentTerm";
    public static final String CI_RIDER_SUM_PATH = "request.requestData.requestPayload.productRestrictionPayload.currentCIRiderSumAssured";
    public static final String ACI_RIDER_SUM_PATH = "request.requestData.requestPayload.productRestrictionPayload.currentACIRiderSumAssured";
    public static final String ACO_RIDER_SUM_PATH = "request.requestData.requestPayload.productRestrictionPayload.currentACORiderSumAssured";
    public static final String TERMPLUS_RIDER_SUM_PATH = "request.requestData.requestPayload.productRestrictionPayload.termPlusAddAmount";
    public static final String PANNUMBER_PATH = "request.requestData.requestPayload.productRestrictionPayload.panNumber";
    public static final String INDIA_PINCODE_REGEX = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
    public static final String NON_INDIA_PINCODE_REGEX = "^[a-zA-Z0-9]{1,10}$";
    public static final String AADHAR_FORMAT_REGEX="^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$";
    public static final String VID_FORMAT_REGEX="^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}[0-9]{4}$";
    public static final String BAD_REQUEST_TEXT = "badRequest";
    public static final String SSESPRODUCT = "Yes";
    public static final String SSESSOLVEOPTION = "EASY";
    public static final String INSTA_PROTECT_PIN_CODE = "INSTA_PROTECT_PIN_CODE";
    public static final String INSTA_PROTECT_AGENT_CODE = "INSTA_PROTECT_AGENT_CODE";

    public static final String OAUTH_AGENT360_TOKEN_SUBJECT = "OauthAgentToken";
    public static final String NJ_SOURCE = "NJ";
    public static final String ACTIVE_AGENT = "Active";
    public static final String X_IBM_CLIENT_ID = "X-IBM-Client-Id";
    public static final String X_IBM_CLIENT_SECRET = "X-IBM-Client-Secret";
    public static final String SUCCESS_RESPONSE = "200";
    public static final String INTERNAL_SERVER_ERROR_CODE= "500";
    public static final String BAD_REQUEST_CODE="400";
    public static final String OTP_FLOODING_CODE = "429";
    public static final String OTP_VERIFIED = "409";
    public static final String BROKER_CHANNEL_TYPE= "BROKER";
    public static final String BRANCH_ID_Q = "Q";
    public static final String BRANCH_ID_U = "U";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String UPLOADED = "Uploaded";
    public static final String REQUIRED = "Required";
    public static final String POSV_RECEIVED = "POSV_RECEIVED";

    //FUL2-64466
    public static final String FLEXI_PROTECT_RESTRICTED_PIN_CODE = "FLEXI_PROTECT_PIN_CODE";
    public static final String FPS_PRODUCT_ID = "184";//Max Life Smart Flexi Protect Solution
    public static final String FPS_ERROR_MSG = "Flexi Protect Pin Code restriction data not available in DB";
    public static final String FPS_NRI_MSG = "Axis Max Life Smart Flexi Protect Solution is not applicable for NRI Customers";
    public static final String SFPS_PRODUCT_NAME = "Max Life Smart Flexi Protect Solution";
    public static final String AXIS_SFPS_PRODUCT_NAME = "Axis Max Life Smart Flexi Protect Solution";
    public static final String SFPS_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER = "Axis Max Life Smart Flexi Protect Solution (Flexi Wealth Plus Plan + Critical Illness and Disability Secure Rider)";
    public static final String SSES_PRODUCT_NAME = "Max Life Smart Secure Easy Solution";
    public static final String AXIS_SSES_PRODUCT_NAME = "Axis Max Life Smart Secure Easy Solution";
    public static final String SSES_PRODUCT_NAME_WITH_BASE_PRODUCT_AND_RIDER = "Axis Max Life Smart Secure Easy Solution (Smart Secure Plus Plan + Accidental Cover Option + Critical Illness and Disability Rider)";
    public static final String DIPLOMA = "Diploma";
    public static final List<String> RESTRICTED_PROPOSER_EDUCATIONS = Collections.unmodifiableList(Arrays.asList(HIGH_SCHOOL.toUpperCase(), SENIOR_SCHOOL.toUpperCase(), DIPLOMA.toUpperCase()));
    public static final String SWP_UIN = "SWP_UIN";

    //FUL2-GLIP POS PF Version
//    public static final String GLIP_POS_PF_VERSION = "ANNUITY/POS/0924/1.3";
    public static final String GLIP_POS_PF_VERSION = "ANNUITY/POS/0125/1.4";
    ////FUL2-74378_Check-TMB-Bank
    public static final String CHANNEL_TMB = "B";
    public static final List<String> CHANNEL_TMB_GO_CODE_START_WITH = Collections.unmodifiableList(Arrays.asList("B9"));
	public static final String FORM60_CATEGORY = "form60_thanos";
	public static final String REQUEST_SOURCE_THANOS1 = "Thanos 1";
    public static final String J3_JOURNEY = "yes";

    // FUL2-64684 YBL Telesales CIP
	public static final String AA = "AA";//Agent Assisted
	public static final String AGENT_ASSISTED_KEY = "agentAssisted";
	public static final String NAA = "NAA";//Non-Agent Assisted
    public static final String N = "N";

    //FUL2-74120 PTF changes
    public static final String PTF_PAYMENT = "PTF";

    // FUL2-64684 YBL Telesales CIP
    public static final String OBJ_OF_INSURANCE_CIP = "CIP";
    public static final String OBJ_OF_INSURANCE_HEALTH = "Health";

    public static final String Y = "Y";
    //FUL2-46153 COVID Questionnaire : New Requirement
    public static final String APPLICATION_NUMBER = "applicationNumber";
    public static final String LIFE_INSURED_NAME = "lifeInsuredName";
    public static final String PROPOSED_POLICY_HOLDER = "proposedPolicyHolder";
    public static final String COVID_POSITIVE_CHECK_PROPOSER = "covidTestPositiveCheck";
    public static final String FULL_FUNCTIONAL_RECOVERY_PROPOSER = "fullFunctionalRecovery";
    public static final String LAST_POSITIVE_DIAGNOSIS_PROPOSER = "lastPositiveDiagnosis";
    public static final String HOSPITALIZED_PROPOSER = "hospitalized";
    public static final String HOME_QUARANTINE_PROPOSER = "homeQuarantine";
    public static final String RECOVERY_PERIOD_PROPOSER = "recoveryPeriod";
    public static final String SUFFER_FROM_COVID19_PROPOSER = "sufferFromCovid19";
    public static final String PROVIDE_DETAILS_PROPOSER = "provideDetails";
    public static final String VACCINATED_FOR_COVID19_PROPOSER = "vaccinatedForCovid19";
    public static final String DATE_PROPOSER = "date";
    public static final String COVID_POSITIVE_CHECK_INSUED = "covidTestPositiveCheckInsured";
    public static final String FULL_FUNCTIONAL_RECOVERY_INSURED = "fullFunctionalRecoveryInsured";
    public static final String LAST_POSITIVE_DIAGNOSIS_INSURED = "lastPositiveDiagnosisInsured";
    public static final String HOSPITALIZED_INSURED = "hospitalizedInsured";
    public static final String HOME_QUARANTINE_INSURED = "homeQuarantineInsured";
    public static final String RECOVERY_PERIOD_INSURED = "recoveryPeriodInsured";
    public static final String SUFFER_FROM_COVID19_INSURED = "sufferFromCovid19Insured";
    public static final String PROVIDE_DETAILS_INSURED = "provideDetailsInsured";
    public static final String VACCINATED_FOR_COVID19_INSURED = "vaccinatedForCovid19Insured";
    public static final String DATE_INSURED = "date";
    public static final String HC16 = "HC16";
    public static final String HC16A = "HC16A";
    public static final String HC16B = "HC16B";
    public static final String HC16C = "HC16C";
    public static final String HC16D = "HC16D";
    public static final String HC16E = "HC16E";
    public static final String HC16F = "HC16F";
    public static final String HC16G = "HC16G";
    public static final String HC17 = "HC17";
    public static final String HC40 = "HC40";
    public static final String HC40A = "HC40A";
    public static final String HC40B = "HC40B";
    public static final String HC40C = "HC40C";
    public static final String HC40D = "HC40D";
    public static final String HC40E = "HC40E";
    public static final String HC40F = "HC40F";
    public static final String HC40G = "HC40G";
    public static final String HC41 = "HC41";
    public static final String MONTH = " month";
    //FUL2-83504 CIP - Stop Rules
    public static final String CIP_COUNTRY = "CIP_COUNTRY";
    public static final String CIP_RESTRICTION_MSG = "Basis the information provided in the journey, we are currently unable to offer this plan.";
    public static final List<String> MEDICAL_QUES_CIP = Collections.unmodifiableList(Arrays.asList("H13Ai","H13Bi","H13C","H13D","H13E","H13G","H13fi","H13F","H13Fii"));
    public static final String TWO = "2";
    public static final String MM_YYYY_SLASH = "MM/yyyy";
    public static final List<String> productList_VPosv = Collections.unmodifiableList(Arrays.asList("154","160"));
    public static final List<String> productType_List = Collections.unmodifiableList(Arrays.asList("Traditional","Annuity","Ulip","Combo"));
    public static final String RURAL = "Rural";

    public static final String PHYSICAL_JOURNEY = "physical journey";
    public static final String ENABLEVIDEOPOSV = "enableVPosvAxis";

    public static final String LIFE_INSURED = "LifeInsured";
    //FUL2-104019_Check-Ujjivan-Bank
    public static final String CHANNEL_UJJIVAN = "B";
    public static final List<String> CHANNEL_UJJIVAN_GO_CODE_START_WITH = Collections.unmodifiableList(Arrays.asList("BU"));

    //Ful2-84428 PASA 2.0
    public static final String ENABLEPASATAGS = "enablePASATags";
    public static final String TYPE_CITY = "City";
    public static final String COMPLETED = "COMPLETED";
    public static final String OAS_DOCUMENT = "OAS Approval Document"; // FUL2-76970
    public static final String AUTO_GENERATE_OAS_DOCUMENT = "OAS_DOCUMENT";
    public static final String AND_IN_WORDS = "AND";
    public static final String PHONE_CALL = "phoneCall";
    public static final String MET = "met";
    public static final String SPOKEN = "spoken";
    public static final String ERR_MSG_FOR_DATE_FORMATTER="Converting Date into formatter failed {}";
    public static final String MM_YYYY_SLASH_REGEX = "\\d{1,2}/\\d{4}";
    public static final String GST_WAIVER_STATUS = "GST Waiver Status";
    public static final String GST_WAIVER_STATUS_ID = "GST_Waiver_Pr";
    public static final String GST_WAIVER = "GST_WAIVER";
    public static final List<String> POS_PRODCUTS_LIST = Collections.unmodifiableList(Arrays.asList("Max Life Smart Wealth Advantage Guarantee Plan","Axis Max Life Smart Wealth Advantage Guarantee Plan",
            "Max Life Guaranteed Lifetime Income Plan","Axis Max Life Guaranteed Lifetime Income Plan", "Max Life Smart Wealth Plan","Axis Max Life Smart Wealth Plan"));

    // FUL2-60217 Scheme A&B introduce
    public static final String SCHEME_B = "SchemeB";
    public static final String AUTO_CANCELLATION_DOCUMENT = "AUTO_CANCELLATION_DOCUMENT";
    public static final String AUTO_CANCELLATION_DOCUMENT_PDF = "AUTO_CANCELLATION_DOCUMENT_PDF";
    public static final String EKYC_DOCUMENT = "EKYC";
    public static final String EKYC_DOCUMENTID = "EKYC_PDF";
    public static final String DOB_DOCID_IN = "DOB_In";
    public static final String DOB_PROOF_IN = "DOB proof- Insured";
    public static final String DOB_DOCUMENT_INSURED="DOB_DOCUMENT_INSURED";

    public static final String JOINT_LIFE_BENEFIT = "Joint Life Benefit";

    public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String PHYSICAL_JOURNEY_FEATURE_FLAG =  "enablePhysicalJourney";

    public static final String  CONVERTING_DATE_FORMAT_FAILED =  "Converting Date into formatter failed:";
    //FUL2-61084 Agent Self Phase 1
    public static final String ENABLEAGENTSELFAPI = "enableAgentSelfApi";
    public static final String EMPLOYEE = "Employee";
    public static final String PARTIALMATCH = "PR";
    public static final String AGNTSLF_X_APIID = "x-apigw-api-id";
    public static final String AGNTSLF_X_APIKEY = "x-api-key";
    //FUL2-160608 AgentSelf Encryption
    public static final String AGNTSLF_X_APPID = "appId";
    public static final String BEARER="Bearer ";
    public static final String PANMATCHLOG = "Value of panMatch for transactionId{} is {}";
    public static final String PHONEMATCHLOG = "Value of phoneNumMatch for transactionId{} is {}";
    public static final String EMAILMATCHLOG = "Value of emailMatch for transactionId{} is {}";
    public static final String DOBMATCHLOG = "Value of dobMatch for transactionId{} is {}";

    public static final String DATE_MONTH_YEAR = "dd/MM/yyyy";
    public static final String YEAR_MONTH_DATE = "yyyy/MM/dd";
    public static final String AGENT_SELF = "Agent Self";

    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT_WITHOUT_TIME_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";
    public static final String TRANSACTIONID = "transactionId";

    //FUL2-116960
    public static final String TOBACCO_GUTKA_PANMASALA = "H19ii";
    public static final String BEER = "H20i";
    public static final String WINE = "H20ii";
    public static final String HARD_LIQUOR = "H20iii";
    public static final String DRUG_DETAILS = "H12Ci";
    // FUL2-136867
    public static final String TOBACCO_GUTKA_PANMASALA_INSURED = "H42ii";
    public static final String BEER_INSURED = "H43i";
    public static final String WINE_INSURED = "H43ii";
    public static final String HARD_LIQUOR_INSURED = "H43iii";

    //FUL2-123815
    public static final String CHANNEL_CAT = "E";
    public static final String NEO_GST_WAIVER_DOC = "Neo GstWaiver Doc";

    //FUL2-135547_Setup_of_DCB_Bank_in_Mpro
    public static final String CHANNEL_DCB = "B";
    public static final String CHANNEL_STOCK_HOLDING = "C";
    public static final String DCB_GOCODE = "^[Bb]{1}[Ww]{1}(00[6-9]|0[1-9][0-9]|[1-9][0-9]{2})";
    public static final List<String>  STOCK_HOLDING_GOCODE= Collections.unmodifiableList(Arrays.asList("C2"));

    public static final String VERIFIED = "Verified";
    public static final String JOURNEY_TYPE_ONBOARDING = "onboarding";
    //FUL2-116428 NPS via PRAN
    public static final String  STAGE_ONE =  "1";
    public static final String  ALTERNATE_MOBILE_NUM =  "alternateMobileNo";
    public static final String  ALTERNATE_LANDLINE_NUM =  "alternateLandlineNo";
    public static final String  NPS_API_ID =  "x-apigw-api-id";
    public static final String  NPS_API_KEY =  "x-api-key";
    public static final String  ENABLENPSAPI =  "enableNPSApi";
    public static final String  STATUS_SUCCESS = "Success";
    public static final String  STATUS_FAILURE = "Failure";
    public static final String  MARRIED = "Married";
    public static final String  GRADUATE_DB_CASE= "Graduate";
    public static final String  NPS= "NPS";
    public static final String NPS_JOURNEY="NPS_JOURNEY";
    public static final String ERROR_MSG="Error occured while processing the request ";

    public static final String TENTH_PASS = "10th pass";
    public static final String TENTH_PLUS_TWO_PASS = "10+2 Pass";
    public static final String ENABLEOAUTHBROKERAGENT360_V3 = "enableOauthBrokerAgent360_v3";

    public static final String ENABLE_POSV_VIA_BRMS = "enablePosvBrms";
    public static final String THREE = "3";
    public static final String SPOSV = "SPOSV";
    public static final String HPOSV = "HPOSV";
    public static final String J1 = "J1";
    public static final String J2 = "J2";
    public static final String J3 = "J3";
    public static final String THANOS_1 = "Thanos 1";
    public static final String THANOS_2 = "Thanos 2";
    public static final String FAIL = "FAIL";
    public static final String PASS = "PASS";
    public static final String E = "E";
    public static final String URMU_API_FAILURE_CODE = "999";
    //Ful-144865 motilal oswal
    public static final String CHANNEL_MOTILAL_OSWAL="C";
    public static final List<String>  MOTILAL_OSWAL_GOCODE= Collections.unmodifiableList(Arrays.asList("C1"));
    public static final String ENABLE_CSFBTAGS = "enableCSFBTags";
    public static final String FormC_ALL = "FormC_All";
    public static final String NEO_PENNY_DROP_DOC = "NEO_PENNY_DROP_DOC";
    public static final String BANK_ACCOUNT_NUMBER = "bankAccountNumber";
    public static final String MICR_CODE = "micrCode";
    public static final String IFSC_CODE = "ifscCode";
    public static final String BRANCH_NAME = "branchName";
    public static final String IS_PENNYDROP_VERIFIED = "isPennyDropVerified";
    public static final String DATE = "date";
    public static final String FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST = "field validation error {} during bad request {}";

    //FUL2-147943
    public static final List<String> JOINTLIFE_NATIONALITY = Arrays.asList(NRI, "PIO", INDIAN_NATIONALITY);

    public static final String OTHER_AGENT = "otherAgent";
    public static final String FAILED_CAMELCASE = "Failed";
    public static final String FIELD_VALIDATION_ERROR = "field validation error {}";
    public static final String FORM_VERSION = "0419/";
public static final String EXCEPTION_OCCURED_WHILE_PROCESSING_REQUEST = "Exception Occured while processing request: {}";
    public static final String EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN = "Exception occurred during Accessing Oauth Token";
    public static final String INITIATE_OAUTHBASEDAGENT360CALL = "Initiate oauthBasedAgent360Call for agentID {}";
    public static final String EXCEPTION_DURING_CALL = "oauthBasedAgent360Call - Exception during call oauth based agent360 for agentID {}";
    public static final String EXCEPTION_DURING_GENERATING = "oauthBasedAgent360Call - Exception during during generating jwt Token or agentId validation for agentID {}";
    public static final String EXCEPTION_DURING_PARSING = "oauthBasedAgent360Call - Exception during parsing String to integer for agentID {}";
    public static final String HIGH_SCHOOL_CAPITAL = "HIGH SCHOOL";
    //CCMS Tags
    public static final String TRIGGER_TYPE_SMS = "SMS";
    public static final String TRIGGER_TYPE_EMAIL = "Email";
    public static final String INPUT_REQUEST_TYPE = "CustomerEmail";
    public static final String INPUT_REQUEST_TYPE_AGENT = "AgentEmail";
    public static final String DD_MMM_YYYY_DATE_FORMAT = "dd-MMM-yyyy";
    public static final String DOC_VERSION = "V4.2 21062021";
    public static final String CCMS_SMS_FEATURE_FLAG = "enableSmsCcmsApi";

    public static final String SCHEME_A = "schemeA";
    public static final String CLOUD_X_API_KEY = "cloud_x-api-key";
    public static final String CLOUD_X_APIGW_API_ID = "cloud_x-apigw-api-id";

    // FUL2-133676 - YBL Replacement Constants
    public static final String YBLNIS_API_KEY_TEXT = "APIkey";
    public static final String REPLACEMENT_API_COOKIE= "adb=0; isUtmParamsChange=false; ufi=1;";
    public static final String YBLNIS_XIBM_CLIENT_KEY = "X-IBM-Client-Id";
    public static final String YBLNIS_XIBM_CLIENT_SECRET = "X-IBM-Client-Secret";
    public static final String YBL_REPLACEMENTSALE ="YBL_REPLACEMENTSALE";
    public static final String  ENABLEYBLREPLACEMENTAPI =  "enableYblReplacementSaleApi";
    public static final String JKS = "jks";
    public static final String SSPJL_RIDERS_NAME ="Max Life Smart Secure Plus Joint Life Cover";
    public static final String AXIS_SSPJL_RIDERS_NAME ="Axis Max Life Smart Secure Plus Joint Life Cover";
    public static final String RESPONSE_FAILURE = "Response Not Generated Successfully";
    public static final String NIFTY_SMALLCAP_QUALITY_INDEX_FUND = "niftySmallcapIndexFund";
    // FUL2-186954 New Fund Addition
    public static final String MIDCAP_MOMENTUM_FUND = "midcapMomentumIndexFund";
    public static final String NIFTY_INDEX_FUND_KEY ="NIFTY_INDEX";
    public static final String MIDCAP_MOMENTUM_INDEX_FUND ="Midcap Momentum Index Fund";
    public static final String NIFTY_ALPHA_FUND_KEY = "niftyAlphaFund";
    public static final String NIFTY_MOMENTUM_FUND = "niftyMomentumFund";
    public static final String NIFTY_MOMENTUM_QUALITY_FUND = "niftyMomentumQualityFund";

    public static final String FAST_TRACK_SUPER_PLAN_PRODUCT_ID="34";
	public static final List<String> NIFTY_FUND_PRODUCTS = Arrays.asList(FAST_TRACK_SUPER_PLAN_PRODUCT_ID);
	public static final String NIFTYFUND_FEATURE_KEY = "niftyFund";
    public static final String SOA_ALM_TRAINING_DL_FLAG = "SoaAMLTrainingEnableDL";
    public static final String DUMMY_EMAIL_ID = "dummyemail1234@maxlifeDummy.com";
    public static final String POLICYSTATUS_SERVICE = "VIEW POLICY STATUS SERVICE";
    public static final String AUTH_GRANT_TYPE = "grant_type";
    public static final String AUTH_GRANT_TYPE_VALUE = "client_credentials";
    public static final String AUTH_SCOPE = "scope";
    public static final String AUTH_SCOPE_VALUE = "admin/read";
    public static final String X_API_KEY = "x-api-key";
    public static final String X_APIGW_API_ID = "x-apigw-api-id";
    public static final String SOA_CLOUD_BANK_API =  "SOACloudIFSCMICR";
    public static final String HEADER_APP_ID="appId";
    public static final String APP_ID_VALUE = "FULFILLMENT";
    public static final String SOA_STAG_CDC_ENABLE_FLAG =  "SOA Phase 1";
    public static final String SOA_STAG_CDC_ENABLE_FLAG_LOGIN =  "SOAPhase1Login";

    public static final String KINESIS = "kinesis";
    public static final String KINESIS_FOR_ALL = "kinesisForAll";
    public static final String KINESIS_FOR_PROPOSAL = "kinesisForProposal";
    public static final String IPC = "ipc";
    public static final String RETAIL = "retail";
    //FUL2-610608 AgentSelf Encryption
    public static final String ENCRYPT_AGENTSELF="encryptAgentSelf";
    public static final String EXCEPTION_OCCURRED_WHILE_PROCESSING_REQUEST = "Exception occurred while processing request: {}";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API = "Exception during Agent API ";
    public static final String FORWARDED = "forwarded";
    public static final String END = "END";
    public static final String NULL = "null";
    public static final String BANKING_SINCE = "bankingSince";
    public static final String ENABLE_BRANCHCODE_EDITABLE = "enableBranchcodeEditable";


    public static final String BAD_REQUEST_MESSAGE="Request object is invalid !!";

    public static final String TMB_API_NOT_WORKING ="Service not responding. Please try again later";
    public static final String PRODUCT_NOT_ELIGIBLE ="Product Validator: Product is not Elgible";
    public static final String PRODUCT_VALIDATOR_PAYLOAD_INVALID ="Product Validator: Request object is invalid";
    public static final String PRODUCT_VALIDATOR_BAD_REQUEST ="Product Validator: BAD REQUEST";

    public static final String SOMETHING_WENT_WRONG="Something went wrong";
    public static final String ENABLE_IFSC_MICR_MIGRATION = "enable_ifsc_micr_migration";
    public static final String IFSC_MICR_MESSAGE = "IFSC Details found";
    public static final String GRANT_TYPE = "grant_type";
    public static final String SCOPE = "scope";
    public static final String SCOPE_VALUE = "CustomerServicing";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ERR_MSG = "Something went wrong";
    public static final String STATIC_AGENT_ID="42035B";
    public static final String STATIC_AGENT_NAME="Axis Bank Limited";
    public static final String TELE_DIY = "TeleDIY";
    public static final String TELE_STATIC_AGENT_NAME = "Axis Bank Limited";
    public static final String TELE_STATIC_AGENT_ID = "97111B";
    public static final Map<String, List<String>> DIY_AGENT_MAP = ImmutableMap.<String, List<String>>builder()
            .put(CATAXISB, Arrays.asList(STATIC_AGENT_ID, STATIC_AGENT_NAME))
            .put(CATAXIS, Arrays.asList(STATIC_AGENT_ID, STATIC_AGENT_NAME))
            .put(TELE_DIY, Arrays.asList(TELE_STATIC_AGENT_ID,TELE_STATIC_AGENT_NAME))
            .build();
    public static final List<String> SOURCE_CHANNEL_LIST = Arrays.asList(CATAXISB,CATAXIS,TELE_DIY);
    public static final String LOGS_SUCCESS_RESPONSE = "SOA Api response successfully get with result {}";
    //FUL2-144984 All Products
    public static final String DISABLESTOPRULE = "disableStopRule";
    public static final String REPLACEMENT_SALE = "ReplacementSale";
    public static final String CLIENTID = "client_id";
    //FUL2-175988
    public static final String ENABLEYBLPASA  = "enableYblPasa";
    public static final String SUCCCESSMSG  = "Successful";
    public static final List<String> SWAG_POS_VARIANT_LIST = Arrays.asList(EARLY_WEALTH,"Wealth of milestones",REGULAR_WEALTH);
    public static final String VARIANT = "request.requestData.requestPayload.productRestrictionPayload.variant";
    public static final String ENABLE_REPLACEMENTSALE_EXPOSED = "enableReplacementSaleExposed";
    public static final String SERVICE_UNAVAILABLE = "Service Unavailable";
    public static final String SERVICE_UNAVAILABLE_CODE = "503";
    public static final String SERVICE_UNAVAILABLE_DESC = "Requested service is unavailable";
    public static final String BRMS_BROKER = "Brms Broker";
    public static final String STAGE_THREE = "3";
    public static final String BI_FINALISED = "Quote Generated";
    public static final String POSV_BACKFLOW_MESSAGE = "POSV_RECEIVED";
    public static final String POSV_PENDING = "Pre-issuance verification pending";
    public static final String DOCUMENT_TYPE_FINANCIAL = "financial";

    public static final String SMART_SECURE_PLUS_PLAN = "154";
    public static final String FINANCIAL = "financial";
    public static final String SALARY_SLIP_DOC_ID =  "Sal_Pr";
    public static final String CREDIT_BANK_STATEMENT_DOC_ID =  "Aip_Pr";
    public static final String SELLER_DECLARATION_PENDING = "Seller declaration pending";
    public static final String DOCUMENT_UPLOAD_PENDING = "Documents pending";
    public static final String UNDERWRITING_STATUS_REQUIREDDOCUMENT_TYPE = "StandardDoc_NonMandatory";
    public static final String PREMIUM_MODE_ONLINE = "ONLINE";
    public static final String PREMIUM_MODE_CHEQUE = "CHEQUE";
    public static final String PREMIUM_MODE_DEMAND_DRAFT = "DEMANDDRAFT";
    public static final String PREMIUM_MODE_DIRECT_DEBIT_CAPS = "DIRECTDEBIT";
    public static final String PREMIUM_MODE_DIRECT_DEBIT_WITH_RENEWAL = "DIRECTDEBITWITHRENEWAL";
    public static final String PREMIUM_MODE_PAY_LATER = "PAYLATER";
    public static final String PREMIUM_MODE_IVR = "IVR";
    public static final String PAYMENT_PENDING = "Payment Pending";
    public static final String SMART_TERM = "108";
    public static final String ONLINE_TERM_PLAN = "116";
    public static final String SJB = "160";
    public static final String SMART_WEALTH_PLAN="133";
    public static final String WHOLE_LIFE_VARIANT = "Whole Life Income";
    public static final String CAT_MEDICAL_CATEGORY = "CAT";
    public static final String INDIA = "India";
    public static final List<String> MEDICAL_SCHEDULING_ENABLED_PRODUCT_IDS = Collections.unmodifiableList(Arrays.asList(SMART_TERM, ONLINE_TERM_PLAN, SJB, SMART_SECURE_PLUS_PLAN));
    public static final String MEDICAL_SUCCESS_MGS = "Record Saved Sucessfully";

    public static final String MEDICAL_SUCCESS_CODE = "200";
    public static final String RESPONSE_GENERATED_SUCCESSFULLY="Response Generated Successfully";
    public static final String APPOINTMENT_BOOKED_SUCCESSFULLY="Appointment Booked successfully";
    public static final String APPOINTMENT_ALREADY_CONFIRMED="Our executive already confirmed Appointment with Customer!";
    public static final List<String> MEDICAL_SCHEDULING = Collections.unmodifiableList(Arrays.asList(MEDICAL_SUCCESS_MGS, RESPONSE_GENERATED_SUCCESSFULLY, APPOINTMENT_ALREADY_CONFIRMED));

    public static final String MEDICAL_PENDING = "Medicals Pending";
    public static final String ALL_STAGE_COMPLETED = "Final submit pending";
    public static final String ASIA_KOLKATA_ZONE = "Asia/Kolkata";
    public static final String DATE_FORMAT_MM_DD_YYYY_HH_MM_SS_GMT = "E MMM dd yyyy HH:mm:ss 'GMT+0530'";
    public static final String DATA_FOUND = "Data Found Successfully";
    public static final String DOCUMENT_TYPE_MEDICAL = "Medical";
    public static final String ISMS_DOCUMENT = "ISMS Confirmatory Emailer";

    public static final String TPP_REQUIREMENT_DOC_TYPE = "TPPRequirement";
    public static final String HASHPASS_STATUS = "#PASS";
    public static final String NOT_INITIATED = "NOT_INITIATED";
    public static final String REQUEST_SOURCE_AXIS = "AXIS";
    public static final String POSV_PUSHED_TO_STREAM_STATUS_MESSAGE = "PUSHED_TO_STREAM";
    public static final String POSV_PUSHED_TO_POSV_STATUS_MESSAGE = "PUSHED_TO_POSV";
    public static final List<String> POSV_TRIGGERED_STATUS_LIST = Arrays.asList(POSV_PUSHED_TO_STREAM_STATUS_MESSAGE, POSV_PUSHED_TO_POSV_STATUS_MESSAGE);
    public static final List<String> SEWA_VARIANT_LIST = Arrays.asList("Elite","Lite");

    public static final String DEDUPE_EXACT_MATCH = "EXACT";
    public static final String DEDUPE_NEW_MATCH = "NEW";
    public static final String DEDUPE_API = "dedupe-api";

    public static final String ENABLE_DEDUPE_API_EXPOSED = "enableDedupeAPIExposed";
    public static final String ACTIVE_POLICY_STATUS = "1";
    public static final String DEDUPE_CPD_API = "dedupeCpdAPI";
    public static final String REPLACEMENT_SALE_SERVICE_NAME = "ReplacementSaleAPI";
    public static final String CHANNEL_SOURCE = "channelSource";
    public static final String SWITCH_OFF_CHANNEL = "SELLER_DECLARATION_SWITCH_OFF_CHANNEL";
    public static final String PENDING = "PENDING";
    public static final String STAGE_SEVEN = "7";
    public static final String STAGE_A = "A";
    public static final String STAGE_B = "B";
    public static final String STAGE_C = "C";
    public static final String ADDITIONAL_INFORMATION = "Additional information";
    public static final String ISSUED = "Issued";
    public static final String DISCREPANT = "Discrepant";
    public static final String REJECTED = "Rejected";
    public static final String COUNTER_OFFER ="counter offer";
    public static final String STAGE_8 = "8";
    public static final String STAGE_9 = "9";
    public static final String PB_SOURCE = "PB";
    public static final String POLICY_BAZAAR = "Policy Bazaar";
    public static final String RECORD_FOUND = "Record Found";
    public static final String PROPOSAL_NUMBER = "ProposalNumber";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String PROPOSAL_NUMBER_API = "ProposalNumberAPI";
    public static final String ENABLE_PROPOSAL_NUMBER_API = "enableProposalNumberApi";
    public static final String SEWA = "200";
    //FUL2-170700 Brms Broker Integration
    public static final String CDF_JOURNEY = "CDF";
    public static final String ENABLE_BRMS_BROKER = "EnableBrmsBroker";

    public static final String ERROR = "error";
    public static final List<String> BLOCKED_INDUSTRY_TYPE = Arrays.asList(CRPF, DEFENCE, MERCHANT_MARINE, MINING, OIL);
    public static final String FORM_3 = "businessinsurance";
    public static final Map<String, String> FORM_TYPE_MAP = ImmutableMap.<String, String>builder()
            .put(FORM_TYPE_SELF, ONE).put(DEPENDENT, TWO).put(FORM_3, THREE).build();
    public static final Map<String, String> AGENTIDLOGS = ImmutableMap.<String, String>builder()
            .put("info", "AgentId set for transactionId {} is {}").put(ERROR, "Error while setting the agentId for transactionId{} with exception {}").build();
    public static final String ENV_UAT = "uat";
    public static final String ENV_DEV = "dev";
    public static final String ENV_QA = "qa";
    public static final String ENV_PRE_PROD = "PRE_PROD";
    public static final String ENV_PREPROD = "preprod";
    public static final String ENV_PROD = "prod";
    public static final String RESEND_LINK_DELIMITER = "_";
    public static final String JKS_EXTENTION = ".jks";
    public static final String TERM_BOOSTER_RIDER = "termBoosterRider";
    public static final String ADB_RIDER = "adbRider";
    public static final String ATPD_RIDER = "atpdRider";
    public static final String PAYOR_BENEFIT_RIDER = "payorBenefitRider";
    public static final List<String> SMART_ULTRA_PROTECT_RIDER = Arrays.asList(TERM_BOOSTER_RIDER, ADB_RIDER, ATPD_RIDER, PAYOR_BENEFIT_RIDER);
    public static final String WOP_PLUS_RIDER = "wopPlusRider";
    public static final Map<String, String> UNIFIED_PAYMENT_ENV= ImmutableMap.<String, String>builder()
            .put(ENV_DEV, ENV_DEV.toUpperCase()).put(ENV_UAT, ENV_UAT.toUpperCase()).put(ENV_QA, ENV_QA.toUpperCase()).put(ENV_PREPROD, ENV_PRE_PROD).put(ENV_PROD, ENV_PROD.toUpperCase()).build();

    //FUL2-159429_SWAG_PP_Annuity_Product
    public static final String IA_LAST_SURVIVOR = "IA with Chosen Proportion of Annuity to Last Survivor";
    public static final String IA_EARLY_ROP  ="IA with Early Rop";
    public static final String IA_LIFE_THEREAFTER ="IA for Guaranteed Period and Life Thereafter";
    public static final String INCREASING_IA ="Increasing IA";
    public static final String FOR_LIFE ="For Life";
    public static final String TILL_DEFERMENT_PERIOD ="Till Deferment Period";
    public static final String STAGE_TWO = "2";
    public static final String NO_OF_MONTHS = " Months(s)";
    public static final String NO_POLICY_EXIST = "No policy exists";
    public static final String SPACE = " ";
    public static final String TXN_SUCCESS = "TXN_SUCCESS";
    public static final String TXN_FAILURE = "TXN_FAILURE";
    public static final String TXN_PENDING = "PENDING";
    public static final String CURRENCY = "INR";
    public static final String PAYMENT_TYPE = "Unified";
    public static final String UNIFIED_SUCCESS_STATUS_AUTH_CODE = "0300";
    public static final String UNIFIED_PENDING_STATUS_AUTH_CODE_0002 = "0002";
    public static final String UNIFIED_FAILED_STATUS_AUTH_CODE_0399 = "0399";
    public static final String UNIFIED_PAYMENT_SUBMITTED  = "submitted";
    public static final String POLICY_NOT_FOUND = "Policy not found";
    public static final String ENV_PARAM_NAME = "&env=";
    public static final String UNIFIED_PAYMENT_ENABLE = "unifiedFeatureFlag";
    public static final String UNIFIED_PAYMENT_URL_CONFIG = "unified-qa-url-enable";
    public static final String INITIAL = "Initial";
    public static final String INITIAL_AND_RENEWAL = "InitialAndRenewal";
    public static final String SYSTEM_ENV = "env";
    public static final String NPCI_CODE = "I001";
    public static final String UTILITY_CODE = "ICIC00261000001992";
    public static final String SERVICE_PROVIDER_NAME = "Ingenico ePayments India Pvt. Ltd";
    public static final String RECURRING_FREQUENCY = "As and when Presented";
    public static final String AMOUNT_TYPE = "Variable";
    public static final String SPONSOR_BANK = "ICICI Bank Ltd";
    public static final String SPONSOR_BANK_CODE = "ICIC0TREA00";
    public static final String NPCI_CATEGORY_NAME = "Insurance Premium";
    public static final double RENEWAL_PERCENTAGE= 1.1;
    public static final double RENEWAL_PAYMENT_MONTHLY = 0.5;
    public static final String INVALID_POLICY_NO = "Please provide a valid policy number";
    public static final String POLICY_NO_REGEX = "^\\d+$";


    public static final String BAJAJ_GO_CODE= "QH";
    public static final String JOURNEY_TYPE_BROKER = "Broker";

    public static final String BAJAJ_HOP_JOURNEY_SOURCE_CHANNEL = "Broker";
    public static final String BAJAJ_HOP_JOURNEY_PARTNER_SOURCE_CODE = "QH";
    public static final String  SWAGPP="210";
    public static final String SOA_CLOUD_AUTH_TOKEN_REDIS_KEY = "SoaCloudAuthRedisKey";

    // PF modification String prefix and suffix
    public static final String WITHROP = " - with ROP";
    public static final String WITHOUTROP = " - without ROP";
    public static final String VALIDATE_OTP_TYPE = "validateOtp";
    public static final String GENERATE_OTP_TYPE = "generateOtp";

    public static final String DIY_JOURNEY_TYPE="DIY";
    public static final String MSG_CODE_OPT_VALIDATE = "601";
    public static final String J2_OTP_SUBJECT = "One Time Password";
    public static final String J2_EMAIL_BODY = "@OTP is the One Time Password Email for verification of your application with Axis Max Life Insurance. Valid for 30 minutes. Please do not share with anyone.";
    public static final String J2_SMS_BODY = "@OTP is the One Time Password for verification of your service transaction with Axis Max Life Insurance. Valid for 30 minutes. Please do not share with anyone. @www.maxlifeinsurance.com #@OTP";
    public static final String J2_MASK_MSG = "A 4 digit verification code is sent to your  mobile number ";
    public static final String J2_EMAIL_REGEX = "([a-zA-Z0-9]{3})[a-zA-Z0-9.]*@([a-zA-Z0-9]{0})[a-zA-Z0-9]*(\\.[a-zA-Z]{2,})";
    public static final String J2_MOB_REGEX = "(\\d{2})\\d{6}(\\d{2})";
    public static final String J2_OTP_TOKEN_SUBJECT = "J2OtpToken";
    public static final String DATA_FETCH_SUCCESS = "Data fetched successfully";
    public static final String TIME_Z_ASIA = "Asia/Kolkata";
    public static final String ENABLE_REDIS_SCAN = "enableRedisScan";
    public static final String DOB_ISSUE_MSG = "*Please enter the correct Date of Birth";


    public static final String NO_DATA_FOUND = "No Data Found";
    public static final String PATH = "B2B/Quotes/";
    public static final String PATH_DELIMITER = "/";
    public static final String ILLUSTRATION_PATH = "Illustration_1";
    public static final String S = "S";
    public static final String RESPONSE_PARAM = "responseParam1";
    public static final Double SSP_CODE_DOUBLE = 154.0;
    public static final String ENABLE_EKYC_STATUS_MAIL = "enableEKYCStatusMail";
    public static final String VALIDATE_POLICY_SERVICE_ERROR = "Error Occurred in Policy Validate API" ;
    public static final String LOGIN_FAILURE_DETAILS = "loginFailureDetails_";
    public static final String ENABLE_AGENT_LOGIN_REVAMP = "enableAgentLoginRevamp";
    public static final String RA_LOGIN = "RA LOGIN";
    public static final String SHORT_TERM_WEALTH = "shortTermWealth";
    // public static final BeanPropertyMap DECLARANT_OTP_SMS_BODY = ;


    public static String BANK_JOURNEY_AU = "au";
    
    public static final String STS = "Strict-Transport-Security";
    public static final String HEADER_CSPH = "Content-Security-Policy";
    public static final String HEADER_X_XSS_P = "X-XSS-Protection";
    public static final String HEADER_ACAO = "Access-Control-Allow-Origin";
    public static final String HEADER_RP = "Referrer-Policy";
    //FUL2-196217_SwagElite_pf_changes
    public static final String WHOLE_LIFE ="Whole Life";
    public static final String DASHBOARD_ERR_MSG ="Empty response from Dashboard MS";
    public static final String DASHBOARD_ERR_DSC ="Issue wile connecting to dashboard MS";
    public static final String UNAUTHORISED_ERR_CODE ="403";
    public static final String UNAUTHORISED_ERR_MSG ="channel or source mismatch";
    public static final String UNAUTHORISED_ERR_DSC ="unauthorized access from foreign channel or source";
    public static final String API_CLIENT_SECRET ="api_client_secret";
    public static final String DASHBOARD_PATH_RESPONSE ="response";
    public static final String DASHBOARD_PATH_RESPONSEDATA ="responseData";
    public static final String DASHBOARD_PATH_RESPONSEPAYLOAD ="responsePayload";
    public static final String DASHBOARD_PATH_DASHBOARDDATA ="dashboardData";
    public static final String DASHBOARD_PATH_STAGEINFODATA ="stageInfoData";
    public static final String DASHBOARD_PATH_STAGECOUNT="stageCount";
    public static final String STAGE_A_DESC ="Additional Info Required";
    public static final String STAGE_B_DESC ="Discrepancy";
    public static final String STAGE_C_DESC ="Counter Offer";
    public static final String STAGE_PAYMENT_PENDING_DESC ="Payment Pending";
    public static final String STAGE_POSV_PENDING_DESC ="Posv Pending";
    public static final String SUPERAPP ="SuperApp";
    public static final String CONNECTION_ERROR="connection error";
    public static final String AGENT360_CONNECTION_ERROR="Error in response from agent 360";
    public static final String TRANSACTIONID_GENERATION_ERROR="Error received while generating transactionID ";
    public static final String SUPERAPP_FEATURE_FLAG="enableSuperAppIntegration";
    //FUL2-196166 Persistency Tag Mapping
    public  static final String DEFAULT_JOINING_DATE = "2000-01-01";

    public static final String LOGIN_DATA = "loginData";
    public static final String VALIDATE_DATA = "validateData";
    public static final String VALIDATE_DATA_INSURER_DOB_PATH= "request.requestData.requestPayload.productRestrictionPayload.insurerDateOfBirth";
    public static final String PAN_DOB_VERIFICATION= "panDobVerification";
    public static final String SEND_OR_VALIDATE_OTP= "sendOrValidateOtp";
    public static final String EKYC_AADHAAR_DETAILS_PATH= "additionalFlags.ekycAadhaarDetails.";
    public static final String IFSC_MICR= "ifsc-micr";
    public static final String FATF_COUNTRY_FLAG_PATH= "request.requestData.requestPayload.productRestrictionPayload.fatfCountryFlag";
    public static final String IS_PASA_ELIGIBLE_PATH= "request.requestData.requestPayload.productRestrictionPayload.isPasaEligible";
    public static final String EDUCATION_PATH= "request.requestData.requestPayload.productRestrictionPayload.education";
    public static final List<String>  EDUCATION_LIST= Collections.unmodifiableList(Arrays.asList("High School","Graduate","Post Graduate","Professional","Diploma","Illiterate","Primary School","Senior School"));
    public static final long MIN_TRANSACTION_ID = 1000000000L;
    public static final long MAX_TRANSACTION_ID = 9999999999L;
    public static final String IFSC_MICR_TRANSACTION_ID_PATH= "request.data.transactionId";
    public static final String GET_COUNTRIES= "getcountries";
    public static final String SPCODE_VALIDATION_CLOUD_SERVICE= "spCodeValidation-cloud-service";
    public static final String CHECK_BAJAJ_SELLER_APPLICABILITY= "validateBajajSellerData";
    public static final String PANDOB_VERIFICATION_DOB_PATH="request.data.dob";
    public static final String VALIDATE_DATA_TRANSACTIONID_PATH= "request.requestData.requestPayload.productRestrictionPayload.transactionId";
    public static final String GET_LABLIST="getlabslist";
    public static final String TRAINING="training";
    public static final String NEW_APPLICATION= "isNewApplicationEnabled";
    public static final String GET_PRODUCT_RECOMMENDATIONS="getProductRecommendations";
    public static final String GET_CLIENT_POLICY_DETAILS="getClientAllPolicyDetails";
    public static final String CALL_BRMS_BROKER="callBrmsBroker";
    public static final String OTP_VALIDATION="otpValidation";
    public static final String DISABILITY_DECLARATION_FLOWTYPE= "DISABILITYDECLARATION";
    public static final String YBL_PASADATA="getyblpasadata";
    public static final String AGENT360_CLOUD="agent360-cloud-service";
    public static final String GET_STATE_CITY="getstateandcityByPincode";
    public static final String GET_COMPANY_LIST= "getCompanyList";
    public static final String AGENT_CHECK="agentcheck";
    public static final String GET_STATES= "getstates";
    public static final String YBL_REPLACEMENT="yblReplacementSale";
    public static final List<String>  OCCUPATION_LIST= Collections.unmodifiableList(Arrays.asList("Salaried","Self Employed","Student","Self Employed From Home","Agriculture","Housewife","Retired","Others"));
    public static final String OCCUPATION_PATH= "request.requestData.requestPayload.productRestrictionPayload.occupation";
    public static final String INCOME_REGEX= "[1-9][0-9]{0,8}";
    public static final String INCOME_PATH= "request.requestData.requestPayload.productRestrictionPayload.income";
    public static final List<String>  DEDUPE_FLAG_LIST= Collections.unmodifiableList(Arrays.asList("NC","EX","PR"));
    public static final String DEDUPE_FLAG_PATH= "request.requestData.requestPayload.productRestrictionPayload.dedupeFlag";

    public static final String CLIENT_ID = "X-IBM-Client-Id";
    public static final String CLIENT_SECRET = "X-IBM-Client-Secret";
    public static final String SOA_APPID = "FULFILLMENT";
    public static final String CATEGORY = "lab";
    public static final String REQUEST_TYPE = "labAppointmentupdate";

    public static final String ERROR_POLICYNOTFOUND="request is not valid due to policyno and quoteId not found";

    public static final String ERROR_PROPOSALDETAILS="No proposal details found for this policy";
    public static final String ERROR_MEDICAL_LIST="No Lab List details found for this policy";

    public static final String ERROR_AGENTID="No Agent/transactionId found for this policy";

    public static final String ERROR_MEDICAL_REQUEST_NOTVALID="Medical Schedule request not valid";
    public static final String DASHBOARD = "dashboard";
    public static final String SCREEN1 = "New Application";
    public static final String INVALID = "Invalid";
    public static final String AML_ULIP_DESC = "Aml and Ulip training not completed. Cannot log new policy";
    public static final String ULIP_DESC = "Ulip training not completed. Cannot log Ulip product policy";
    public static final String AML_DESC = "Aml training not completed. Cannot log new policy";
    public static final String RESULT = "result";
    public static final String PAYLOAD = "payload";
    public static final String AML = "aml";
    public static final String PREFILL = "Prefill";
    public static final String REDIRECTTRANSACTION = "Redirect with transaction id";
    public static final String NEWAPPLICATION = "_newApplication";
    public static final String NEWAPPLICATION_HITCOUNT = "_newApplication_hitCount";
    public static final String DEFAULT = "DEFAULT";
    public static final String RENEWAL = "Renewal";

    public static final String DIY_OTP_VALIDATION_ERROR_MSG="Maximum number of OTP requests have been exhausted. Please try again later.";
    public static final String FINAL_SUBMIT_OTP_REQUEST_TYPE = "2";

    public static final String DUMMY_BLANK_IMAGE_PATH = "classpath:static/Default_white.png";

    public enum TimeUnit {SECONDS,MINUTES,HOURS}

    public static final String H6D = "H6D";
    public static final String CLIENT_POLICY_DETAILS_FEATURE_FLAG = "SoaDataLakeCPDEnable";

    public static final String DATALAKE_AGNTSLF = "enableDataLakeAgntslf";
    public static final String CORRELATIONID = "2547";

    public static final String ENABLE_SCHEMA_VALIDATION_FEATURE = "enableSchemaValidation";
    //FUL2-202646 Go Green PF
    public static final String PHYSICAL_POLICY="physicalPolicy";
    public static final String STAGE_SIX = "6";
    public static final String STAGE_FIVE = "5";
    public static final String FINAL_STAGE = "finalStage";
    public static final String ZH = "ZH";

    public static final String PAN_DOB_VALIDATION_SIMULATION = "enableMockPanDobSimulation";
    public static final String AGENT_PERSONAL_DETAILS_TYPE = "DetailsBySerials";

    public static final List<String> IRDA_CIS_KEY = Collections.unmodifiableList(Arrays.asList("disability", "vernacular", "cis"));
    public static final String CIS_DOCUMENT= "Customer Information Sheet";
    public static final String FWAP = "173";
    public static final String FTS ="34";
    public static final String OSP = "100";
    public static final String TYPE_OF_INSURANCE ="A Unit Linked Non-Participating Individual Life Insurance Plan";
    public static final String CIS_DOCUMENT_ID = "CIS_DOCUMENT_PDF";
    public static final String MWPA_DOCUMENT_ID = "MWPA_DOCUMENT_PDF";
    public static final String TAT_DAYS = "30";
    public static final String GRACE_PERIOD_MONTHLY = "15";
    public static final String GRACE_PERIOD = "30";

    public static final String DECLARANT_OTP_SMS_BODY = "Dear [Declarant Name], the OTP to review %26 confirm the details of Max Life proposal form <Proposal number> of <Customer Name> is <otp>";
    public static final String DISABILITY_DECLARATION = "DISABILITY_DECLARATION";

    public static final String FEATURE_FLAG_JOURNEY_QUESTIONS_VIA_REDIS="journeyQuestionsViaRedis";

    public static final String UJJIVAN = "Ujjivan";

    public static final String CBC_ALGO = "AES/CBC/PKCS5Padding";
    public static final String CBC_ALGO_PADDING1 = "RSA/ECB/PKCS1Padding";
    public static final String ALGORITHM = "AES";

    public static final LocalDate currentLocalDate = LocalDate.now();
    public static final int currentFinancialYear = currentLocalDate.getMonthValue() > 2 ? LocalDate.now().getYear() : LocalDate.now().getYear() - 1;

    /**
     * Apply UI UTM Logic - {!(props.productId === SSP_PRODUCTID && props.isJointLife && props.isJointLife === 'yes')}
     * This list has question ids which are to be removed from response list if the above condition matches.
     */
    public static final List<String> QUESTION_ID_FILTER_SSP_LIST = Collections.unmodifiableList(Arrays.asList("H14","H15","H12A","H12B","H38","H39","H36A","H36B","H42","H19","H20"));
    public static final String SPS_PRODUCT_ID ="36";
    public static final String STAGE_7 = "7";
    //S2-26 saral dashboard
    public static final String ABHI = "abhi";
    public static final String VERSION = "1.0";

    public static final String SEND="send";
    public static final String RESEND="resend";
    public static final String INSURANCE_LINK="insurancelink";
    public static final String INQUIRY="inquiry";
    public static final String OTP="OTP";

    public static final String INDIA_SMALL="india";
    public static final String NRI_SMALL="nri";
    public static final String OUTSIDE_INDIA="outsideIndia";
    public static final String INDIAN_RESIDENTIAL_STATUS="indian";
    public static final String PERMANENT = "Permanent";
    public static final String INITIATED_MSG = "Initiated";
    public static final String HALF="half";
    public static final String EMPTY_SCREEN="empty";
    public static final String REQUEST_SOURCE_TMB="TMB";
    public static final String YES_LOWERCASE = "yes";
    public static final String EMPTY_STRING = "";

    public static final String LAST_2_MONTHS = "that is, for the month of "+ currentLocalDate.getMonth().minus(2) +" "+currentLocalDate.getYear()+" and "+currentLocalDate.getMonth().minus(1)+" "+currentLocalDate.getYear();
    public static final String LAST_3_MONTHS = "that is, for the month of "+ currentLocalDate.getMonth().minus(3) +" "+currentLocalDate.getYear()+" to "+currentLocalDate.getMonth().minus(1)+" "+currentLocalDate.getYear();
    public static final String LAST_6_MONTHS = "that is, for the month of "+ currentLocalDate.getMonth().minus(6) +" "+currentLocalDate.getYear()+" to "+currentLocalDate.getMonth().minus(1)+" "+currentLocalDate.getYear();
    public static final String LAST_1_YEAR = "that is, for financial year "+(currentFinancialYear - 1)+"-"+(currentFinancialYear);
    public static final String LAST_2_YEAR = "that is, for financial year "+(currentFinancialYear - 2)+"-"+(currentFinancialYear - 1)+" and "+(currentFinancialYear - 1)+"-"+(currentFinancialYear);
    public static final String LAST_3_YEAR = "that is, for financial year "+(currentFinancialYear - 3)+"-"+(currentFinancialYear - 1)+" to "+(currentFinancialYear - 1)+"-"+(currentFinancialYear);
    public static final int THIS_YEAR = currentLocalDate.getYear();
    public static final String LAST_1_YEAR_DISCREPANCY = THIS_YEAR - 1+" - "+THIS_YEAR;
    public static final String LAST_2_YEARS_DISCREPANCY = THIS_YEAR - 2+" - "+THIS_YEAR;
    public static final String LAST_3_YEARS_DISCREPANCY = THIS_YEAR - 3+" - "+THIS_YEAR;
    public static final Month THIS_MONTH = currentLocalDate.getMonth();
    public static final String LAST_1_MONTH_DISCREPANCY = THIS_MONTH.minus(1)+" to "+THIS_MONTH;
    public static final String LAST_2_MONTH_DISCREPANCY = THIS_MONTH.minus(2)+" to "+THIS_MONTH;
    public static final String LAST_3_MONTH_DISCREPANCY = THIS_MONTH.minus(3)+" to "+THIS_MONTH;
    public static final String LAST_4_MONTH_DISCREPANCY = THIS_MONTH.minus(4)+" to "+THIS_MONTH;
    public static final String LAST_5_MONTH_DISCREPANCY = THIS_MONTH.minus(5)+" to "+THIS_MONTH;
    public static final String LAST_6_MONTH_DISCREPANCY = THIS_MONTH.minus(6)+" to "+THIS_MONTH;

    public static final String DECLARANT_OTHERS = "Others";
    public static final String NEO_CIS_DOCUMENT = "CIS DOCUMENT";
    public static final String NEO_MWPA_DOCUMENT = "MWPA DOCUMENT";
    public static final String TRUN_AROUND_TIME = "15";
    public static final String SINGLE_LIFE = "Single Life";
    public static final String JOINT_LIFE = "Joint Life";
    public static final String SINGLE_LIFE_PT = "till the death of the annuitant";
    public static final String JOINT_LIFE_PT = "till the death of last survivor";
    public static final String FWAP_CIS = "FWAP";
    public static final String FWP_CIS = "FWP";
    public static final String OSP_CIS = "OSP";
    public static final String SFRD_CIS = "SFRD";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String SUM_ASSURED = "sumAssured";
    public static final String PAYU_INCOME = "payUIncome";
    public static final String CIBIL_SCORE = "cibilScore";
    public static final String CIBIL_STATUS = "cibilStatus";
    public static final String CIBILTUSC3 = "CIBILTUSC3";
    public static final String SJB_CIS = "SJB";
    public static final String SSP_CIS = "SSP";
    public static final String SWP_CIS = "SWP";
    public static final String SWPV1_CIS = "SWPV1";
    public static final String SWPV2_CIS = "SWPV2";
    public static final String SWPV3_CIS = "SWPV3";
    public static final String PASAELIGIBLE_CHANNELLIST = "PASAELIGIBLE_CHANNELLIST";
    public static final String BLOCKEDAGENTS = "enableAgentBlocking";
    public static final String STEP_CIS = "ste";

    public static final String EKYC_TYPE ="ekycType";
    public static final String DUMMYAADHAAR1 = "222222444444";
    public static final String DUMMYAADHAAR2 = "666666888888";
    public static final String EKYC_AADHAAR_MOCK = "ekycAadhaarMock";
    public static final List<String> BANNED_AGENT_RESPONSES = Collections.unmodifiableList(Arrays.asList("agent is blocked","agent is blocked from mpro login. Please redirect to mSpace"));

    public static final String RESUME_DIY_TOKEN_SUBJECT = "ResumeDIYToken";
    public static final List<String> STAGES_BEFORE_DOLPHIN_PUSH = Collections.unmodifiableList(Arrays.asList("1", "2", "3", "4", "5", "6"));
    public static final String PUSHED_TO_DOLPHIN = "Pushed to Dolphin";
    public static final String URL_NEO_DOMAIN = "neoDomain";
    public static final String DATALAKE_AXIS_BRANCH_DETAILS = "enableDataLakeAxisBranchDetails";
    public static final String CIS_RIDER_DOCUMENT= "Customer Information Sheet Rider";
    public static final String ADD_RIDER = "cisRiderADD";
    public static final String TERM_RIDER = "cisRiderTERM";
    public static final String WOP_RIDER= "cisRiderWOP";
    public static final String SUPR_RIDER="cisRiderSUPR";
    public static final String CIDS_RIDER = "cisRiderCIDSR";
    public static final String CID_RIDER = "cisRiderCIDR";
    public static final String CIS_RIDER_DOCUMENT_ID ="CIS_RIDER_PDF";
    public static final String ADD_RIDER_NAME = "Axis Max Life Accidental Death And Dismemberment Rider";
    public static final String TERM_RIDER_NAME= "Axis Max Life Term Plus Rider";
    public static final String WOP_RIDER_NAME = "Axis Max Life Waiver of Premium Plus Rider";
    public static final String SUPR_RIDER_NAME= "Max Life Smart Ultra Protect Rider";
    public static final String AXIS_SUPR_RIDER_NAME= "Axis Max Life Smart Ultra Protect Rider";
    public static final String CIS_RIDER_DOCUMENT_NAME = "Customer Information Sheet - Rider";
    public static final String CIDS_RIDER_NAME = "Axis Max Life Critical Illness and Disability- Secure Rider";
    public static final String NEO_CIS_RIDER_DOCUMENT_NAME = "Customer Information Sheet - Rider";
    public static final String CID_RIDER_NAME = "Axis Max Life Critical Illness and Disability Rider";


    public static final String URL_SHORTNER_SERVICE_URL = "urlshortner.serviceUrl";
    public static final String URL_SHORTNER_XAPIGW = "urlshortner.xapigw";
    public static final String URL_SHORTNER_XAPIKEY = "urlshortner.xapikey";
    public static final String URL_SHORTNER_SOAAPPID = "urlshortner.soaappid";
    public static final String URL_SHORTNER_BEARER = "urlshortner.bearer";
    public static final String URL_SHORTNER_OAUTH_URL = "urlshortner.oauthUrl";
    public static final Integer TOKEN_DELAY=10;
    public static final String PROPOSAL_DATA_FETCH = "pdf";
    public static final String INSURED_DATA_FETCH = "idf";
    public static final List<String>  DATA_FETCH = Collections.unmodifiableList(Arrays.asList(PROPOSAL_DATA_FETCH,INSURED_DATA_FETCH));
    public static final List<String> WOP_RIDER_LIST = Collections.unmodifiableList(Arrays.asList("VN06", "VP02", "VN07", "VN04", "VN05"));
    public static final List<String> CI_RIDER_LIST = Collections.unmodifiableList(Arrays.asList(TCIGR, TCIGPR, TCIPR, TCIPPR, TCIGL, TCIGPL, TCIPL, TCIPPL,TCLCIB, TCICIB, TCLNIB, TCINIB, TNCIB, TCCIB, TCIPDR, TCIPDL));
    public static final List<String> ADD_RIDER_LIST = Collections.unmodifiableList(Arrays.asList("PP02", "PP03"));
    public static final String SWAG_CIS = "SWAG";
    public static final String LONG_TERM_WEALTH = "longTermWealth";
    public static final String WEALTH_FOR_MILESTONE = "wealthForMilestone";
    //ivc phase 1
    public static final String IVC = "IVC";
    public static final String MPRO = "mpro";
    public static final String POLICYNUM_PATH = "applicationDetails.policyNumber";
    public static final String OVERALLPRODSTATUS_PATH = "posvDetails.posvStatus.overallProductStatus";
    public static final String POSV_JOURNEY_STATUS = "applicationDetails.posvJourneyStatus";
    public static final String IVC_RETRIGGERED_PATH = "posvDetails.isIvcRetriggered";
    public static final String POSV = "POSV";
    public static final String CIDS_RIDER_DB_NAME ="Axis Max Life Critical Illness & Disability- Secure Rider";
    public static final String DUMMY_AADHAAR_3 = "211111111122";
    public static final String DUMMY_AADHAAR_4 = "222222222233";
    public static final String DUMMY_AADHAAR_5 = "333333333344";
    public static final String DUMMY_AADHAAR_6 = "444444444455";
    public static final String DUMMY_AADHAAR_7 = "555555555566";
    public static final String DUMMY_VIRTUAL_1 = "5555555555666666";
    public static final String DUMMY_VIRTUAL_2 = "4444444444555555";
    public static final String DUMMY_VIRTUAL_3 = "2111111111222222";
    public static final String DUMMY_VIRTUAL_4 = "2222222222333333";
    public static final String DUMMY_VIRTUAL_5 = "6666668888888888";
    public static final String DUMMY_VIRTUAL_6 = "2222224444444444";
    public static final String NEW_SMS_API = "newDataLakeSMSAPI";
    public static final String YES_MEDICAL_SKIP = "Yes-Medical Skip";
    public static final String YES_CANCEL = "Yes-Cancel";
    public static final String STATUS_OTHER_THAN_01_02 = "03";
    public static final String ENABLE_POST_DOLPHINE_POLICY_STATUS ="enablePostDolphinPolicyStatus";
    public static final String PUSHED_TO_TPP = "PUSHED_TO_TPP";
    public static final String POLICYSTATUS_CHANNELLIST = "POLICYSTATUS_CHANNELLIST";
    public static final String POLICY_STAGE_CHECK_REGEX = "7|8|9|A|B|C";
    public static final String PAYMENT_RENEWED_BY_ENACH = "Enach";
    public static final String PAYMENT_RENEWED_BY_UPI = "UPI";
    public static final String PAYMENT_RENEWED_BY_CC = "Credit Card / Debit Card";
    public static final String PB_INFLUENCER_CHANNEL_ID = "38";
    // FUL2-224962 financial frictionless
    public static final String ENABLE_ALTFIN_CS1 = "enableAltfinCS1";
    public static final String NO_CACHE = "no-cache";
    public static final String X_CLIENT_ID = "x-client-id";
    public static final String USER_POOL_ID = "user-pool-id";
    public static final String APPID = "s";
    public static final String NIFTY_FUND_FEATURE_FLAG = "isFeatureFlagNIFTYFund";
    public static final String NIFTY_SUSTAINABLE_FUND = "axisMaxLifeSustainableWealthFund";
    public static final String SMART_INNOVATION_FUND = "smartInnovationFund";
    //Seller Supervisor Status
    public static final String APPROVED = "Approved";
    public static final String NIFTY_SUSTAINABLE_WEALTH_FUND = "niftySustainableWealthFund";
    public static final String MATERNITY_COVER = "Max Life Maternity Cover";
    public static final String AXIS_MATERNITY_COVER = "Axis Max Life Maternity Cover";
    public static final String BRMS_EKYC_UTM_PREFIX = "BRMS_EKYC_UTM_";
    //FUL2-224962 financial frictionless
    public static final String CENTRAL_SERVICE_RESULT_PATH = "underwritingServiceDetails.centralServiceResult";
    public static final String NEO_STPP_PRODUCT_TYPE = "stpp";
    public static final List<String> STPP_PRODUCT_PREMIUM_BACK_OPTION = Collections.unmodifiableList(Arrays.asList("TSTERS", "TSTERL", "TSTER6", "TSHERS", "TSHERL", "TSHER6", "TSTRPS", "TSTRPR", "TSTRPL", "TSTRP6", "TSHRPS", "TSHRPR", "TSHRPL", "TSHRP6", "TPSRPS", "TPSRPR", "TPSRPL", "TPSRP6"));
    public static final String OTPONLY_POSVTYPE = "OTPonly";
    public static final String PRAN_NUMBER_FLAG = "enablePranNumberRestriction";

  //FUL2-238597
    public static final String SHORTER_JOURNEY_OTP_MSG_BODY="Dear Customer, <otp> is your consent code to retrieve details from your previous Axis Max Life Insurance policy for processing the issuance of your current policy. This code is valid for 3 hours.";
    public static final String SHORTER_JOURNEY_OTP_LENGTH="6";
    public static final String SHORTER_JOURNEY_OTP_DEFAULT_VALUE="123456";

    //FUL2-238597
    public static final String SHORTER_JOURNEY = "shorterJourney";
    public static final String SHORTER_JOURNEY_FEATURE_FLAG = "isShorterJourneyEnabled";
    public static final String ENABLE_AUDITING_FOR_AGENT_SELF_CHECK_API = "enableAuditingAgentSelfApi";
    public static final Map<String, String> PF_IRP_CONFIG = ImmutableMap.<String, String>builder()
            .put("dev","https://mprodev.maxlifeinsurance.com/fulfillment/api/dpc/funds/pfIRPConfig")
            .put("uat","https://mprouat.maxlifeinsurance.com/fulfillment/api/dpc/funds/pfIRPConfig")
            .put("qa","https://mproqa.maxlifeinsurance.com/fulfillment/api/dpc/funds/pfIRPConfig")
            .put("preprod","https://mpropp.maxlifeinsurance.com/fulfillment/api/dpc/funds/pfIRPConfig")
            .put("prod","https://mpro.maxlifeinsurance.com/fulfillment/api/dpc/funds/pfIRPConfig").build();
    public static final String ENABLE_IRP_PSM_MPRO_FEATURE = "enableIrpNewTemplateFeature";
    public static final String IRP_NEW_DATE="IRP_MPRO_WIP_CASE_DATE";

    public static final String PSM_FEATURE_FLAG_NAME_MPRO = "enablePSMMpro";
    public static final String PSM_MPRO_WIP_CASE_DATE = "PSM_MPRO_WIP_CASE_DATE";
    public static final String UTC ="UTC";
    public static final String DOCUMENT_GENERATION_BY_VERSION = "DOCUMENT_GENERATION_BY_VERSION";

    public static final String AML_ULIP_MOCK = "amlUlipMock";
    public static final String LIFE_LONG_WEALTH = "Lifelong Wealth";
    public static final String PAYMENT_FIRST_CONSENT = "enablePaymentFirstConsent";
    public static final String PAYMENT_FIRST_WIP_CASE = "PAYMENT_FIRST_WIP_CONFIG";
    public static final String IS_PAYMENT_FIRST_NON_WIP_CASE="isPaymentFirstNonWipCase";

    public static final int NUMBER_OF_DEFAULT_NOMINEES = 3;

    public static final String SP_CODE_VALIDATION_MOCK = "spCodeValidationMock";
    public static final List<String> NOMINEE_APPOINTEE_TYPES = List.of("Nominee1","Nominee2","Nominee3","Appointee1","Appointee2","Appointee3");
}