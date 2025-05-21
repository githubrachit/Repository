package com.mli.mpro.location.auth.filter;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationConstants {
	
	public static final String API_PATTERN = "/";
	public static final String API_CLIENT_SECRET = "api_client_secret";
	public static final String PHSYICAL_JOURNEY_ENABLED = "physicalJourneyEnabled";
	public static final String ERROR = "error";
	public static final String RESPONSE_AGENT = "responseAgent";

	public static final Map<String, String> WHITELIST_ROUTES = new HashMap<>();
	static {
		WHITELIST_ROUTES.put("url", "/fulfillment/api/locationservices/login");
		WHITELIST_ROUTES.put("supervisorStatus1", "/fulfillment/api/locationservices/saveSellerSupervisorStatus");
		WHITELIST_ROUTES.put("supervisorStatus2", "/ob/fulfillment/api/locationservices/saveSellerSupervisorStatus");
		WHITELIST_ROUTES.put("supervisorStatus3", "/locationservices/saveSellerSupervisorStatus");
		WHITELIST_ROUTES.put("approvalLink1", "/fulfillment/api/locationservices/validateApprovalLink");
		WHITELIST_ROUTES.put("approvalLink2", "/locationservices/validateApprovalLink");
		WHITELIST_ROUTES.put("approvalLink3", "/ob/fulfillment/api/locationservices/validateApprovalLink");
		WHITELIST_ROUTES.put("locationHealth", "/fulfillment/api/locationservices/health");
		WHITELIST_ROUTES.put("dashboardHealth", "/fulfillment/api/dashboardservice/health");
		WHITELIST_ROUTES.put("proposalhealth", "/fulfillment/api/proposalservices/health");
		WHITELIST_ROUTES.put("proposalhealth_1", "/health");
		WHITELIST_ROUTES.put("url_1", "/locationservices/login");
		WHITELIST_ROUTES.put("health_1", "/locationservices/health");
		WHITELIST_ROUTES.put("url_2", "/ob/fulfillment/api/locationservices/login");
		WHITELIST_ROUTES.put("health_2", "/ob/fulfillment/api/locationservices/health");
		WHITELIST_ROUTES.put("channel", "/api/channels/continue-with-mpro");
		WHITELIST_ROUTES.put("payment", "/api/payments/billdesk");
		WHITELIST_ROUTES.put("enach", "/api/payments/enach");
		WHITELIST_ROUTES.put("enachRedirect", "/api/payments/enachredirect");
		WHITELIST_ROUTES.put("return", "/api/payments/return");
		WHITELIST_ROUTES.put("retryPf", "/api/generateproposalpdf");
		WHITELIST_ROUTES.put("servertoserver", "/api/payments/servertoserver");
		WHITELIST_ROUTES.put("samparkRedirect", "/api/bancas/axis-hop");
		WHITELIST_ROUTES.put("nisSamlRedirect", "URL_FOR_NIS_SAML_REDIRECT");
		WHITELIST_ROUTES.put("nisSamlHopUrl", "/api/nis-saml-hop");
		WHITELIST_ROUTES.put("mAppRedirect", "/api/mApp");
		WHITELIST_ROUTES.put("broker", "/api/broker/login");
		WHITELIST_ROUTES.put("updatePaymentStatus", "/fulfillment/api/locationservices/unified-payment-status-update");
		WHITELIST_ROUTES.put("updatePaymentStatus1", "/locationservices/unified-payment-status-update");
		WHITELIST_ROUTES.put("ekyc", "/fulfillment/api/locationservices/sendOrValidateOtp");
		WHITELIST_ROUTES.put("J2OnBoardingOtpUrl1", "/fulfillment/api/locationservices/onboarding-otpService");
		WHITELIST_ROUTES.put("J2OnBoardingOtpUrl2", "/locationservices/onboarding-otpService");
		WHITELIST_ROUTES.put("J2OnBoardingOtpUrl3", "/ob/fulfillment/api/locationservices/onboarding-otpService");
		WHITELIST_ROUTES.put("tmb1", "/fulfillment/api/locationservices/tmb/sendOtp");
		WHITELIST_ROUTES.put("tmb2", "/fulfillment/api/locationservices/tmb/verifyOtp");
		WHITELIST_ROUTES.put("tmb3", "/locationservices/tmb/sendOtp");
		WHITELIST_ROUTES.put("tmb4", "/locationservices/tmb/verifyOtp");
		WHITELIST_ROUTES.put("tmb5", "/ob/fulfillment/api/locationservices/tmb/sendOtp");
		WHITELIST_ROUTES.put("tmb6", "/ob/fulfillment/api/locationservices/tmb/verifyOtp");
	}

}