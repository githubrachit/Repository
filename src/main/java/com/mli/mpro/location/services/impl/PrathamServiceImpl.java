package com.mli.mpro.location.services.impl;

import java.net.URI;
import java.util.UUID;

import com.mli.mpro.emailsms.notification.NotificationSenderService;
import com.mli.mpro.oauthToken.Service.OauthTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.email.models.EmailDetails;
import com.mli.mpro.email.repository.EmailRepository;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.location.services.PrathamService;
import com.mli.mpro.oauthToken.Service.OauthTokenService;
import com.mli.mpro.pratham.email.models.Header;
import com.mli.mpro.pratham.email.models.Request;
import com.mli.mpro.pratham.email.models.RequestData;
import com.mli.mpro.pratham.models.InputRequest;
import com.mli.mpro.pratham.models.MessageInputRequest;
import com.mli.mpro.pratham.models.OutputResponse;
import com.mli.mpro.pratham.models.UpdatePrathamStatus;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

@Service
public class PrathamServiceImpl implements PrathamService {

	@Value("${urlDetails.authorization.username}")
	private String authUserName;
	@Value("${urlDetails.authorization.password}")
	private String authPassword;
	@Value("${urlDetails.pratham}")
	private String pratham;
	@Value("${urlDetails.updatePrathamStatus}")
	private String updatePrathamUrl;
	@Value("${urlDetails.email}")
	private String email;
	@Value("${redis.oauthkey}")
	private String oauthkey;
	@Value("${pratham.emailTo}")
	private String emailTO;

	private static final Logger log = LoggerFactory.getLogger(PrathamServiceImpl.class);
	private URI Updateurl;

	private OauthTokenService oauthTokenService;
	private AuditService auditService;
	private EmailRepository emailRepo;
	private OauthTokenRepository oauthTokenRepo;
	private NotificationSenderService notificationSenderService;

	@Autowired
	public PrathamServiceImpl(OauthTokenServiceImpl oauthTokenService, AuditService auditService, EmailRepository emailRepo,
							  OauthTokenRepository oauthTokenRepo, NotificationSenderService notificationSenderService) {
		super();
		this.oauthTokenService = oauthTokenService;
		this.auditService = auditService;
		this.emailRepo = emailRepo;
		this.oauthTokenRepo = oauthTokenRepo;
		this.notificationSenderService = notificationSenderService;
	}

	@Override
	public String executePrathamService(MessageInputRequest inputRequest) throws UserHandledException {
		long transactionId = inputRequest.getTransactionId();
		InputRequest pratahmRequest = inputRequest.getInputRequest();
		String policyNo = pratahmRequest.getRequest().getPayload().getPolicyNo();
		int retryCount = inputRequest.getRetryCount();
		String status = AppConstants.FAIL_STATUS;
		if (retryCount < 4) {
			AuditingDetails auditDetails = new AuditingDetails();
			OutputResponse prathamResponse = new OutputResponse();
			UpdatePrathamStatus updateStatus = new UpdatePrathamStatus();
			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				log.info("Request initiated for PrathamService api for transactionId {}", transactionId);
				String token = getTokenFromRedis(transactionId);
				if (!StringUtils.isEmpty(token)) {
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.add(AppConstants.AUTH, "Bearer " + token);
					headers.add("X-IBM-Client-Id", authUserName);
					headers.add("X-IBM-Client-Secret", authPassword);
					HttpEntity<com.mli.mpro.pratham.models.InputRequest> httpEntity = new HttpEntity<>(pratahmRequest,
							headers);
					prathamResponse = restTemplate.postForObject(pratham, httpEntity, OutputResponse.class);
				}
				status = getprathamServiceStatus(prathamResponse);
				auditDetails.setAdditionalProperty("request", inputRequest);
				ResponseObject respoObject = new ResponseObject();
				respoObject.setAdditionalProperty("response", prathamResponse);
				auditDetails.setResponseObject(respoObject);
				auditDetails.setServiceName(AppConstants.PRATHAM_SERVICE_NAME);
				auditDetails.setTransactionId(transactionId);

				updateStatus.setPrathamStatus(status);
				updateStatus.setPolicyNumber(policyNo);
				updateStatus.setTransactionId(transactionId);
			} catch (Exception e) {
				log.error("Exception while PrathamService api and message is {} ", e.getMessage());
				auditService.saveAuditTransactionDetails(auditDetails);
				return AppConstants.FAIL_STATUS;
			}
			callUpdateStatus(updateStatus);
			auditService.saveAuditTransactionDetails(auditDetails);
		} else {
			sendEmail(policyNo, transactionId);
		}
		return status;
	}

	String getprathamServiceStatus(OutputResponse prathamResponse) {
		String status = AppConstants.FAIL_STATUS;
		if (prathamResponse != null && !StringUtils.isEmpty(prathamResponse.getResponse().getMsgInfo().getMsgCode())
				&& prathamResponse.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase("200")) {
			status = AppConstants.SUCCESS;
		}
		return status;
	}

	private String callUpdateStatus(UpdatePrathamStatus updateStatus) {
		String status = AppConstants.FAIL_STATUS;
		try {
			Updateurl = new URI(updatePrathamUrl);
			log.info("called update service for transactionId {}", updateStatus.getTransactionId());
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UpdatePrathamStatus> request = new HttpEntity<>(updateStatus, httpHeaders);
			status = new RestTemplate().postForObject(Updateurl, request, String.class);
		} catch (Exception ex) {
			log.info("Exception call update service for transactionId and message {} {}", updateStatus.getTransactionId(),
					Utility.getExceptionAsString(ex));
		}
		return status;
	}

	@Override
	public String sendEmail(String proposalNo, long transactionId) throws UserHandledException {

		String emailSubject = "SCOPP API has failed for Proposal no. " + proposalNo + "";
		String emailStatus = null;
		log.info("Email service initaited pratham fail for transactionId {}",transactionId);
		OutputResponse outputResponse = null;
		try {
			//TODO for sending CCMS notification set EmailDetails Object in NotificationSenderService.
			notificationSenderService = notificationSenderService.getInstance(false);
			emailStatus = notificationSenderService.sendEmail(initInputRequest(proposalNo,emailSubject));
		} catch (Exception ex) {
			log.info("Exception email  pratham fail for transactionId {} is {}",transactionId, Utility.getExceptionAsString(ex));
		}
		return emailStatus;

	}

	private com.mli.mpro.pratham.email.models.InputRequest initInputRequest(String proposalNo, String emailSubject){
		com.mli.mpro.pratham.email.models.InputRequest inputRequest = new com.mli.mpro.pratham.email.models.InputRequest();
		Header header = new Header();
		header.setSoaAppId("FULFILLMENT");
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		Request request = new Request();
		RequestData requestData = new RequestData();
		requestData.setConsolidate(false);
		requestData.setFileAttached(false);
		requestData.setFromEmail(AppConstants.EMAIL_FROM);
		requestData.setFromName(AppConstants.FROM_NAME);
		EmailDetails emailData = emailRepo.findByDocumentType(AppConstants.PRATHAM_SERVICE_NAME);
		String emailBody = emailData.getMailBody();
		String emailContent = emailBody.replace("PROPOSALNO", proposalNo);
		requestData.setMailIdTo(emailTO);
		requestData.setMailBody(emailContent);
		requestData.setMailSubject(emailSubject);
		request.setHeader(header);
		request.setRequestData(requestData);
		inputRequest.setRequest(request);
		return inputRequest;
	}

	private String getTokenFromRedis(long transactionId) {
		String accessToken = "";
		try {
			accessToken = oauthTokenRepo.getToken(oauthkey);
			if (StringUtils.isEmpty(accessToken)) {
				log.info("oauth token service initiated  to generate token");
				accessToken = oauthTokenService.getAccessToken();
			}
		} catch (Exception e) {
			log.info(" oauth token service initiated to generate Redis token, is failed for transactionId {} ",
					transactionId);
			log.info("get token from redis exception {} ", Utility.getExceptionAsString(e));
			log.error(Utility.getExceptionAsString(e));
			accessToken = oauthTokenService.getAccessToken();

		}
		return accessToken;
	}

}
