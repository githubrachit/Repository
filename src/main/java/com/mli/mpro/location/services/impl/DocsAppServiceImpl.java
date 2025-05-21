package com.mli.mpro.location.services.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.mli.mpro.common.models.RequestData;
import com.mli.mpro.location.models.RequestPayload;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.proposal.models.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.isEmpty;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.OutputResponse;
import com.mli.mpro.docsApp.models.AddProposal;
import com.mli.mpro.docsApp.models.DocsappRequest;
import com.mli.mpro.docsApp.models.DocsappResponse;
import com.mli.mpro.docsApp.models.Header;
import com.mli.mpro.docsApp.models.MsgInfo;
import com.mli.mpro.docsApp.models.Payload;
import com.mli.mpro.docsApp.models.Request;
import com.mli.mpro.docsApp.models.Response;
import com.mli.mpro.location.services.DocsAppService;
import com.mli.mpro.productRestriction.util.AppConstants;

@Service
public class DocsAppServiceImpl implements DocsAppService {

	private static final Logger logging = LoggerFactory.getLogger(DocsAppServiceImpl.class);

	@Value("${urlDetails.docsAppService}")
	private String docsAppUrl;
	@Value("${docsApp.soaAppId}")
	private String soaAppId;
	@Value("${docsApp.client.secret.key}")
	private String soaClientSecretKey;
	@Value("${docsApp.client.secret.key.value}")
	private String soaClientSecretKeyValue;
	@Value("${docsApp.soaCorrelationId}")
	private String soaCorrelationId;
	@Value("${docsApp.client.id}")
	private String soaClientId;
	@Value("${docsApp.client.id.value}")
	private String soaClientIdValue;

	@Value("${urlDetails.proposalService}")
	private String getProposalData;

	@Value("${urlDetails.upateProposalDataTelemerStatus}")
	private String upateProposalDataTelemerStatus;

	@Value("${ext.api.read.timeout}")
	private int readTimeOut;

	@Value("${ext.api.connection.timeout}")
	private int connTimeOut;

	@Value("${bypass.header.encrypt.value}")
	private String api_client_secret;

	private String dateFormat="yyyy-MM-dd";

	@Override
	public DocsappResponse callDocsAppService(InputRequest inputRequest) throws UserHandledException {
		String isteleMERStatus = AppConstants.NO;
		String isvideoMERStatus = AppConstants.NO;
		ProposalDetails proposalDetails = inputRequest.getRequest().getRequestData().getRequestPayload()
				.getProposalDetails();

		Long transactionId = proposalDetails.getTransactionId();
		logging.info("For transactionId {} Calling docsApp Api ", transactionId);
		Request request = new Request();
		Payload payload = new Payload();
		Header header = new Header();
		DocsappResponse docsAppResponse = new DocsappResponse();
		DocsappRequest docsappRquest = new DocsappRequest();
		//FUL2-6218 FIP UW - Added new gridName VideoMer
		boolean isteleMERFlag = Boolean.FALSE;
		boolean isvideoMERFlag = Boolean.FALSE;
		try {
			proposalDetails = getProposalData(inputRequest);
			UnderwritingServiceDetails underwritingServiceDetails = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails().getUnderwritingServiceDetails();
			if(!isEmpty(underwritingServiceDetails)){
				proposalDetails.getUnderwritingServiceDetails().setMedicalGridDetails(underwritingServiceDetails.getMedicalGridDetails());
			}
			payload.setEquoteNumber("JOVR2928");
			payload.setStatusCodekey("123");
			payload.setStatusCodeDisplayText("New Lead");
			header.setSoaCorrelationId(soaCorrelationId);
			header.setSoaAppId(soaAppId);
			request.setHeader(header);
			request.setPayload(payload);
			AddProposal addProposal = setRquestData(proposalDetails, transactionId);
			payload.setAddProposal(addProposal);
			docsappRquest.setRequest(request);
	                logging.debug("For transactionId {} , docs app request generated is {}", transactionId, docsappRquest);
					logging.info("For transactionId {} , docs app request is generated. {}", transactionId, docsappRquest);
	                //code to have timeout in docs api
	                HttpComponentsClientHttpRequestFactory connectionOutFactory = new HttpComponentsClientHttpRequestFactory();
	                connectionOutFactory.setReadTimeout(readTimeOut * 1000);
	                connectionOutFactory.setConnectTimeout(connTimeOut * 1000);


	                RestTemplate restTemplate = new RestTemplate(connectionOutFactory);
	                MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
			httpHeaders.add(soaClientSecretKey,soaClientSecretKeyValue);
			httpHeaders.add(soaClientId,soaClientIdValue);
			HttpEntity<?> httpEntity = new HttpEntity(docsappRquest,httpHeaders);
			docsAppResponse = restTemplate.postForObject(docsAppUrl, httpEntity, DocsappResponse.class);
			String msgCode = docsAppResponse.getResponse().getMsgInfo().getMsgCode();
			isteleMERFlag = proposalDetails.getAdditionalFlags().isIsteleMERFlag();
			isvideoMERFlag = proposalDetails.getAdditionalFlags().isIsvideoMERFlag();
			//FUL2-6218 FIP UW - Added new gridName VideoMer
			if (msgCode.equals("200")) {
				if(isteleMERFlag) {
					isteleMERStatus = AppConstants.YES;
					proposalDetails.getAdditionalFlags().setIsteleMERStatus(isteleMERStatus);
					inputRequest.getRequest().getRequestData().getRequestPayload().setProposalDetails(proposalDetails);
					updateTeleMerStatus(inputRequest, transactionId);
				}else if(isvideoMERFlag) {
					isvideoMERStatus = AppConstants.YES;
					proposalDetails.getAdditionalFlags().setIsvideoMERStatus(isvideoMERStatus);
					inputRequest.getRequest().getRequestData().getRequestPayload().setProposalDetails(proposalDetails);
					updateTeleMerStatus(inputRequest, transactionId);
				}
			}
			logging.info("For transactionId {} docs app response is generated: {} ", transactionId, docsAppResponse);
		} catch (Exception exception) {
			logging.error("While error occurs for transactionId {}, docs app request generated is {}", transactionId, docsappRquest);
			logging.error("While error occurs for transactionId {}, docs app response generated is {} ", transactionId, docsAppResponse);
			logging.error("Exception while calling docsApp Api and message is: {}", Utility.getExceptionAsString(exception));
			throw new UserHandledException();
		}
		return docsAppResponse;

	}

	private AddProposal setRquestData(ProposalDetails proposalDetails, Long transactionId) throws UserHandledException {
		List<String> errorMsg = new ArrayList<>();
		logging.info("setting request data for docsApp API for transactionId {}", transactionId);

		AddProposal addProposal = new AddProposal();
		try {
            addPolicyAndProductDetails(addProposal, proposalDetails);

            addformData(addProposal, proposalDetails);

            setMedicalInfo(addProposal, proposalDetails);

        }catch (Exception exception) {
		    errorMsg.add(exception.getMessage());
		    logging.error("Error occured while setting  data for docsSpp API: {}", Utility.getExceptionAsString(exception));
		    throw new UserHandledException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return addProposal;
	}

	@Override
	public DocsappResponse getDocsApiResponseFallback(UserHandledException e, InputRequest inputRequest) {
	    logging.info("All retries completed, so Fallback method called!!! : {} ", e.getErrorMessages());
        DocsappResponse docsappResponse = new DocsappResponse();
		Response response = new Response();
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsg("Failed");
		msgInfo.setMsgCode("500");
		msgInfo.setMsgDescription("Failed to call DocsApp Service");
		response.setMsgInfo(msgInfo);
		docsappResponse.setResponse(response);
		return docsappResponse;
	}

    private void updateTeleMerStatus(InputRequest inputRequest, Long transactionId) {
        logging.info("for  transactionId {} updating teleMER status in database ", transactionId);
        OutputResponse response=null;
        try {
            RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
			HttpEntity<InputRequest> httpEntityRequest = new HttpEntity<>(inputRequest, httpHeaders);
            response = restTemplate.postForObject(upateProposalDataTelemerStatus, httpEntityRequest, OutputResponse.class);
            logging.info("For transactionId {} ,response is received while updating teleMER status in database {}", transactionId, response);

        } catch (Exception exception) {
            logging.error("For  transactionId {} request send while updating teleMER status in database failed : {}", transactionId, inputRequest);
            logging.error("For  transactionId {} response received while updating teleMER status in database failed : {}", transactionId, response);
            logging.error("Exception occurred while updating teleMER status  in database:{}", Utility.getExceptionAsString(exception));

        }
    }

    public ProposalDetails getProposalData(InputRequest inputRequest) throws UserHandledException {
        Long transactionId = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails()
                .getTransactionId();
        logging.info("For  transactionId {} calling get proposal data for docsApp Api ", transactionId);
        logging.info("Loading getProposal api from configuration file {}",getProposalData);
        ProposalDetails proposalDetails = new ProposalDetails();
        try {
        	RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
			HttpEntity<InputRequest> httpEntityRequest = new HttpEntity<>(inputRequest, httpHeaders);
            OutputResponse response = restTemplate.postForObject(getProposalData, httpEntityRequest, OutputResponse.class);
            proposalDetails = response.getResponse().getResponseData().getResponsePayload().getProposalDetails();
        } catch (Exception exception) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(exception.getMessage());
            logging.error("Exception while calling getProposal Data Api and message is:", exception);
            throw new UserHandledException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return proposalDetails;
    }


	public static String formatDateToString(Date date, String format,
			String timeZone) {

		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(date);
	}

	private void addPolicyAndProductDetails(AddProposal addProposal,ProposalDetails proposalDetails){
		String productType = proposalDetails.getProductDetails().get(0).getProductType();
		List<String> policyNumberList = new ArrayList<String>();
		List<String> planCodeList = new ArrayList<String>();
		SalesStoriesProductDetails salesStoriesProductDetails = proposalDetails.getSalesStoriesProductDetails();
		String salesReferenceId = null;
		if (salesStoriesProductDetails != null) {
			salesReferenceId = salesStoriesProductDetails.getSalesReferenceId();
		}
		if (Utility.verifyString(salesReferenceId)) {
			policyNumberList.add(salesStoriesProductDetails.getPrimaryPolicyNumber());
			policyNumberList.add(salesStoriesProductDetails.getSecondaryPolicyNum());
			planCodeList.add(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCode());
			planCodeList.add(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCodeTPP());
		} else if (!isEmpty(productType) && AppConstants.ULIP.equalsIgnoreCase(productType)) {
			policyNumberList.add(proposalDetails.getApplicationDetails().getPolicyNumber());
			planCodeList.add(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCodeTPP());
		} else {
			policyNumberList.add(proposalDetails.getApplicationDetails().getPolicyNumber());
			planCodeList.add(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCode());
		}
		addProposal.setPolicyNumber(policyNumberList);
		addProposal.setProductCode(planCodeList);

	}

	private void addformData(AddProposal addProposal,ProposalDetails proposalDetails){
		PartyInformation commonPartyInformation = new PartyInformation();
		PartyDetails commonPartyDetails = new PartyDetails();
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		String gender = null;
		if (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType,schemeType)) {
			commonPartyInformation = proposalDetails.getPartyInformation().get(0);
			commonPartyDetails = proposalDetails.getNomineeDetails().getPartyDetails().get(0);
		}
		else {
			commonPartyInformation = proposalDetails.getPartyInformation().get(1);
			commonPartyDetails = proposalDetails.getNomineeDetails().getPartyDetails().get(0);
		}
		gender = commonPartyInformation.getBasicDetails().getGender().toUpperCase();
		switch (gender) {
			case "M":
				gender = "MALE";
				break;
			case "F":
				gender = "FEMALE";
				break;
			case "OTHER":
				gender = "OTHER";
				break;
			default:
				logging.info(" Did not received Gender");
		}
		addProposal.setGender(gender.toUpperCase());
		BasicDetails basicDetails=commonPartyInformation.getBasicDetails();
		String name=(!isEmpty(basicDetails.getMiddleName()))?basicDetails.getFirstName()+" "+basicDetails.getMiddleName()+""+basicDetails.getLastName():basicDetails.getFirstName()+" "+basicDetails.getLastName();
		addProposal.setFirstName(name);
		addProposal.setNomineeName(commonPartyDetails.getFirstName()+" "+commonPartyDetails.getLastName());
		Date nomanieeDob = commonPartyDetails.getDob();
		String nomanieeDobIst=formatDateToString(nomanieeDob,dateFormat, "IST");
		addProposal.setNomineeDob(nomanieeDobIst);
		boolean isSwpWLIVariant = AppConstants.SWIP.equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
						|| (proposalDetails.getProductDetails().get(0).getProductInfo().getProductId().equals(AppConstants.SWP_PRODUCTCODE) && proposalDetails.getProductDetails().get(0).getProductInfo().getVariant().equals(AppConstants.WHOLE_LIFE_INCOME));
		//FUL2-14338 SWP-UW Changes : Setting Proposer Pan if we are not saving insured Pan for SWP WLI Variant
		addProposal.setPanCard(getPanCard(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		Date dob = commonPartyInformation.getBasicDetails().getDob();
		String proposerDob=formatDateToString(dob,dateFormat, "IST");
		addProposal.setDob(proposerDob);
		//FUL2-14338 SWP-UW Changes : Setting Proposer Mobile Number in case of SWP WLI
		addProposal.setMobileNumber(getMobileNumber(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		//FUL2-14338 SWP-UW Changes : Setting Proposer Email Id and Address if we are not saving insured Email Id and Address for SWP WLI Variant
		addProposal.setEmailId(getEmailId(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		addProposal.setCommHouseNo(getCommHouseNo(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		addProposal.setCommCity(getCommCity(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		addProposal.setCommState(getCommState(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		addProposal.setCommPinCode(getCommPinCode(commonPartyInformation,proposalDetails,isSwpWLIVariant));
		addProposal.setIsMedicalRequired("");
		addProposal.setTobaccoConsumption(proposalDetails.getProductDetails().get(0).getProductInfo().isSmoker());
			//F21-560 As per discussion with Krishna
			addProposal.setAmount("0");

	}

    private String getPanCard(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getPersonalIdentification().getPanDetails().getPanNumber()) && isSwpWLIVariant ? proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber() :
                commonPartyInformation.getPersonalIdentification().getPanDetails().getPanNumber();
    }

    private String getCommPinCode(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode()) && isSwpWLIVariant ?
                proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode();
    }

    private String getCommState(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getState()) && isSwpWLIVariant ?
                proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getState() : commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getState();
    }

    private String getCommCity(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getCity()) && isSwpWLIVariant ?
                proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getCity();
    }

    private String getCommHouseNo(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo()) && isSwpWLIVariant ?
                proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo() : commonPartyInformation.getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo();
    }

    private String getEmailId(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
	    return isEmpty(commonPartyInformation.getPersonalIdentification().getEmail()) && isSwpWLIVariant ? proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getEmail() :
                commonPartyInformation.getPersonalIdentification().getEmail();
    }

    private String getMobileNumber(PartyInformation commonPartyInformation, ProposalDetails proposalDetails, boolean isSwpWLIVariant) {
        return isEmpty(commonPartyInformation.getPersonalIdentification().getPhone().get(0).getPhoneNumber()) && isSwpWLIVariant ?
                proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone().get(0).getPhoneNumber() : commonPartyInformation.getPersonalIdentification().getPhone().get(0).getPhoneNumber();
    }

    private void setMedicalInfo(AddProposal addProposal,ProposalDetails proposalDetails){
		MedicalGridDetails medicalGridDetails = proposalDetails.getUnderwritingServiceDetails()
				.getMedicalGridDetails();
		if (!isEmpty(medicalGridDetails)) {
			String packageName = medicalGridDetails.getCategory();
			addProposal.setPackageName(!isEmpty(packageName) ? packageName : "");
			//F21-560 TeleMER DocsAPP API Integration : Passing TeleMED
			String testName = medicalGridDetails.getResult();
			addProposal.setTestName(Arrays.asList(testName.split(",")));

			//FUL2-6218 FIP UW - Added new gridName VideoMer
			List<String> medicalGridName = Arrays.asList(testName.split(","));
			setMedicalCategoryAndTeleAndVideoMERFlags(medicalGridName,addProposal, proposalDetails);
		}
		addProposal.setMedicalCenter("");

		MedicalShedulingDetails medicalShedulingDetails = proposalDetails.getUnderwritingServiceDetails()
				.getMedicalShedulingDetails();
		if (!isEmpty(medicalShedulingDetails)) {
			Date preferredDate = medicalShedulingDetails.getPreferredDate();
			String preferredDateIst=formatDateToString(preferredDate,dateFormat, "IST");
			addProposal.setMedicalDate(preferredDateIst);
			String visitType = medicalShedulingDetails.getVisitType();
			addProposal.setVisitType((!isEmpty(visitType) ? visitType : ""));
		}
		addProposal.setMedicalTime("");
	}

	//Code Refactoring to solve cognitive complexity
    private void setMedicalCategoryAndTeleAndVideoMERFlags(List<String> medicalGridName, AddProposal addProposal, ProposalDetails proposalDetails) {
        for (String gridName : medicalGridName) { //"TELE MER"
            if (Utility.stringEqualCheck(gridName, "TELE MER")
                    || gridName.equalsIgnoreCase("TELE-MER")) {
                addProposal.setMedicalCategory(AppConstants.TELE_MED);
                proposalDetails.getAdditionalFlags().setIsteleMERFlag(Boolean.TRUE);
            } else if (!StringUtils.isEmpty(gridName) && gridName.equalsIgnoreCase("VIDEO MER")) {
                //FUL2-11848 OTP SmTP PinCode Restriction : updated medical category as Tele_Med for VIDEOMER
                addProposal.setMedicalCategory(AppConstants.TELE_MED);
                addProposal.setPackageName("VIDEOMER");
                proposalDetails.getAdditionalFlags().setVideoMERFlag(Boolean.TRUE);
            }
        }
        //FUL2-11848 OTP SmTP PinCode Restriction : DocsApps VIDEOMER issue fix
        if(isEmpty(addProposal.getMedicalCategory())) {
            addProposal.setMedicalCategory("");
        }
    }

    //FUL2-11549 Payment acknowledgement for all channels
	//Calling getProposal api from docsAppService
	public ProposalDetails getProposalDetails(ProposalDetails proposalDetails) {
		InputRequest inputRequest = new InputRequest();
		com.mli.mpro.common.models.Request request = new com.mli.mpro.common.models.Request();
		com.mli.mpro.common.models.RequestData requestData = new RequestData();
		com.mli.mpro.location.models.RequestPayload requestPayload = new RequestPayload();
		ProposalDetails proposalDetails2 = new ProposalDetails();
		SourcingDetails sourcingDetails = new SourcingDetails();
		ApplicationDetails applicationDetails = new ApplicationDetails();
		String agentId = proposalDetails.getSourcingDetails().getAgentId();
		String flag = "docsApi";
		try {
			logging.info("In getProposalDetails for transaction {} PrimaryTransactionId {}",proposalDetails.getTransactionId(),
					proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId());
			sourcingDetails.setAgentId(agentId+flag);
			proposalDetails2.setTransactionId(proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId());
			applicationDetails.setPolicyNumber("");
			proposalDetails2.setApplicationDetails(applicationDetails);
			proposalDetails2.setSourcingDetails(sourcingDetails);
			requestPayload.setProposalDetails(proposalDetails2);
			requestData.setRequestPayload(requestPayload);
			request.setRequestData(requestData);
			inputRequest.setRequest(request);
			proposalDetails2 = getProposalData(inputRequest);

		}catch(Exception ex) {
			logging.error("Exception while creating getProposal request ",ex);
		}
		return proposalDetails2;
	}

	/**
	 * @param proposalDetails
	 */
	//FUL2-11549 Payment acknowledgement for all channels
	//Fetching receipt data for Secondary policy using getProposal api from proposal service - To fetch date for the salesstory secondary policy.
	//Below method will call for EazyPay , Online, Paylater as because we are not maintaning date in secondary policy.
	public ProposalDetails setSecondPolicyReceipt(ProposalDetails proposalDetails) {
		ProposalDetails details;
		try {
			logging.info("In setSecondPolicyReceipt for transaction {} ",proposalDetails.getTransactionId());
			String paymentType = proposalDetails.getPaymentDetails().getReceipt().get(0).getPremiumMode();
			String channel = proposalDetails.getChannelDetails().getChannel();
			if(proposalDetails.getSalesStoriesProductDetails() != null
					&& AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())
					&& proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() == 0L
					&& ((paymentType.equalsIgnoreCase("directDebit") && AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel))
					|| (paymentType.equalsIgnoreCase("online"))
					|| (paymentType.equalsIgnoreCase("payLater")))) {
				details = getProposalDetails(proposalDetails);
				proposalDetails.getPaymentDetails().setReceipt(details.getPaymentDetails().getReceipt());
			}
		}catch(Exception e) {
			logging.error("Exception in setSecondPolicyReceipt", e);
		}

		return proposalDetails;
	}
}
