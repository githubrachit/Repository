package com.mli.mpro.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginConstants {
	private LoginConstants() {

	}
	public static final String TOKEN_SUCCESSFULLY_DECRYPT = "Token decrypt successfully !!";
	public static final String TOKEN_DECRYPT_FAILURE = "Token decryption failure !!";
	public static final String EMPTY_REQUEST = " Request is empty !!";
	public static final String TOKEN_INVALID_MESSAGE = "Invalid Token !!";
	public static final String UNKNOWN_ERROR = "Service not responding, please try again later !!";
	public static final String API_TOKEN_NOT_PRESENT = "No token provided !!";
	public static final String AGENT_INACTIVE = "Invalid Agent id";
	public static final String USER_NOT_AUTHENTICATE = "Incorrect userId or Password !!";
	public static final String INVALID_OBJECT = "Request object is invalid !!";
	public static final String LOGOUT = "User logout successfully !!";
	public static final String LOGIN_SUCCESS = "Success response in login api";
	public static final String REQUIRED_KEY = "Required key is missing !!";
	public static final String TRAINING_FAILURE = "Sorry, the system could not get your training status, please click on the Retry button.";
	public static final String NON_COMMISSIONABLE_USER = "This User ID is not valid to log any policy in mPRO, please retry using the correct User ID";
	public static final List<String> RAROLES = Collections.unmodifiableList(Arrays.asList("RQK", "ABK", "FQX", "KAM",
			"FQR", "RAQ", "BBS", "BRW", "BRS", "CAQ", "FQB", "SRN", "BRM", "FEE", "CRS", "FQN", "FQO", "BAP", "FQH",
			"FQI", "CMJ", "CRB", "SNB", "ABL", "SFI", "BBA", "BBF", "FSR", "RCM", "FQY", "FQZ", "FAR", "FBB", "FQA",
			"CCB", "FQP", "FQQ", "FCC", "MAF", "CUC", "FRQ", "SRP", "BRP", "FQG", "FQK", "FDD", "FUB", "FSC", "RCO",
			"RAJ", "ABO", "BAS", "BRE", "SMW", "MWB", "QAS", "FSB", "RQJ", "YAS", "YFS", "YSM", "CBB", "BRA", "DAD",
			"DBS", "DRR", "RQN", "ABQ", "BRU", "WRA", "WFS", "ABN", "BBR", "ASN", "AAQ", "FAQ", "WSF", "FQJ", "SFQ",
			"QAM", "FQM", "FQW", "CMB", "WAS", "RBB", "MAB", "YRA"));

	public static final List<String> PHYSICALJOURNEYROLE = Collections
			.unmodifiableList(Arrays.asList("OTE", "AEO", "DIO", "IEO", "IDO", "KDO", "CAN", "DOK", "PHY"));

	public static final String NJ_REQUEST_SOURCE = "NJ";
	public static final String AGENTID = "agentid";
}
