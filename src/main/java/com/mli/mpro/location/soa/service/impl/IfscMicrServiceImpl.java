package com.mli.mpro.location.soa.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.mli.mpro.bankdetails.model.BankDetails;
import com.mli.mpro.bankdetails.model.BankResponsePayload;
import com.mli.mpro.bankdetails.model.SOABankResponse;
import com.mli.mpro.bankdetails.service.impl.BankDetailsManager;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.location.ifsc.model.IfscMicrPayload;
import com.mli.mpro.location.ifsc.model.IfscMicrRequest;
import com.mli.mpro.location.ifsc.model.IfscMicrSoaResponse;
import com.mli.mpro.location.ifsc.model.IfscMicrSoaResult;
import com.mli.mpro.location.ifsc.model.OutputResponse;
import com.mli.mpro.location.ifsc.model.RequestData;
import com.mli.mpro.location.ifsc.model.Result;
import com.mli.mpro.location.ifsc.model.Transaction;
import com.mli.mpro.location.ifsc.model.Response;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.service.IfscMicrService;
import com.mli.mpro.utils.Utility;

@Service
public class IfscMicrServiceImpl implements IfscMicrService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${urlDetails.ifscmicr}")
	private String url;
	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;

	@Autowired
	BankDetailsManager bankDetailsManager;


	private static final Logger logger = LoggerFactory.getLogger(IfscMicrServiceImpl.class);

	@Override
	public ResponseEntity<Object> executeIfscMicrDataService(IfscMicrRequest ifscMicrRequest) {
		try {
			OutputResponse outputRspse = new OutputResponse();
            Result rslt=new Result();
			Response rspnse=new Response();
			MsgInfo msgInf=new MsgInfo();
			rspnse.setMsginfo(msgInf);
			rslt.setResponse(rspnse);
			outputRspse.setResult(rslt);
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(ifscMicrRequest);
			Set<ErrorResponse> errors= utility.validateJson(jsonString,AppConstants.IFSC_MICR);
			if(ifscMicrRequest.getRequest() != null & ifscMicrRequest.getRequest().getData() != null )
				utility.validateTransactionId(ifscMicrRequest.getRequest().getData().getTransactionId(),errors,AppConstants.IFSC_MICR_TRANSACTION_ID_PATH);
			if(!CollectionUtils.isEmpty(errors)){
				outputRspse.getResult().getResponse().getMsginfo().setMsgCode(AppConstants.BAD_REQUEST_CODE);
				outputRspse.getResult().getResponse().getMsginfo().setErrors(errors);
				return new ResponseEntity<>(new Gson().toJson(outputRspse), HttpStatus.BAD_REQUEST);
			}
			logger.info("calling ifsc-micr soa service with request {}", ifscMicrRequest);
			if(checkIfscMicrCondition(ifscMicrRequest)) {
				RequestData bankDetailsRequest = ifscMicrRequest.getRequest().getData();
				logger.info("Calling IFSC MICR method for transactionId {}",bankDetailsRequest.getTransactionId());
				SOABankResponse bankResponse = bankDetailsManager.getBankDetailsByIFSCAndMICR(bankDetailsRequest);
				OutputResponse outputResponse = new OutputResponse();
				Result result = new Result();
				Response response = new Response();
				MsgInfo msgInfo = new MsgInfo();
				Header header = new Header();
				if (null != bankResponse) {
					setMsgInfo(bankResponse,msgInfo);
					msgInfo.setMsgCode(String.valueOf(bankResponse.getMsgInfo().getMsgCode()));
					response.setPayload(convertToTransaction(bankResponse.getPayload()));
					header.setSoaAppId(bankResponse.getHeader().getSoaAppId());
					header.setSoaCorrelationId(bankResponse.getHeader().getSoaCorrelationId());
				}
				response.setMsginfo(msgInfo);
				response.setHeader(header);
				result.setResponse(response);
				outputResponse.setResult(result);
				return new ResponseEntity<>(outputResponse, HttpStatus.OK);

			}
			else {
				RequestData data = ifscMicrRequest.getRequest().getData();
				if (data == null) {
					MsgInfo msgInfo = new MsgInfo();
					msgInfo.setMsgCode("500");
					msgInfo.setMsgDescription(SoaConstants.INVALID_OBJECT);
					msgInfo.setMsg("Something went wrong");
					return buildeErrorResponse(msgInfo);
				}
				String ifscUrl = url + "ifsccode/" + data.getIfsc() + "/ifscvalidation/v2?" + "micrCode=" + data.getMicr()
						+ "&soaMsgVersion=1.0&" + "client_id=" + cleintID + "&client_secret=" + secretKey;
				HttpHeaders headers = new HttpHeaders();
				headers.set("soaCorrelationId", UUID.randomUUID().toString());
				headers.set("soaAppId", SoaConstants.FULFILLMENT);
				HttpEntity<String> entity = new HttpEntity<>(headers);
				ResponseEntity<IfscMicrSoaResult> soaResponse = restTemplate.exchange(ifscUrl, HttpMethod.GET, entity,
						IfscMicrSoaResult.class);
				logger.info("response from soa for ifsc-micr {}", soaResponse);
				if (soaResponse.getStatusCode() == HttpStatus.OK) {
					Transaction transaction = Optional.ofNullable(soaResponse).map(ResponseEntity::getBody)
							.map(IfscMicrSoaResult::getResponse).map(IfscMicrSoaResponse::getPayload)
							.map(IfscMicrPayload::getTransactions).filter(transactions -> !transactions.isEmpty())
							.map(transactions -> transactions.get(0)).orElse(null);
					if (transaction != null) {
						OutputResponse outputResponse = new OutputResponse();
						Result result = new Result();
						Response response = new Response();
						response.setHeader(new Header());
						MsgInfo msgInfo = new MsgInfo();
						msgInfo.setMsgCode(String.valueOf(soaResponse.getStatusCode().value()));
						msgInfo.setMsgDescription("SUCCESS");
						msgInfo.setMsg("IFSC Details found");
						response.setMsginfo(msgInfo);
						response.setPayload(transaction);
						result.setResponse(response);
						outputResponse.setResult(result);
						return new ResponseEntity<>(outputResponse, HttpStatus.OK);
					} else {
						Optional<IfscMicrSoaResponse> response = Optional.ofNullable(soaResponse)
								.map(ResponseEntity::getBody).map(IfscMicrSoaResult::getResponse);
						if (response.isPresent()) {
							MsgInfo msginfo = response.get().getMsginfo();
							return buildeErrorResponse(msginfo);
						}
					}
				} else {
					MsgInfo msgInfo = new MsgInfo();
					msgInfo.setMsgCode("500");
					msgInfo.setMsgDescription(SoaConstants.FAILURE);
					msgInfo.setMsg("Something went wrong");
					return buildeErrorResponse(msgInfo);
				}
			}
		} catch (HttpServerErrorException e) {
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setMsgCode("500");
				msgInfo.setMsgDescription(SoaConstants.UNKNOWN_ERROR);
				msgInfo.setMsg("Internal Server Error");
				return buildeErrorResponse(msgInfo);
			}
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsgCode("500");
			msgInfo.setMsgDescription(SoaConstants.FAILURE);
			msgInfo.setMsg("TimeOut from server side");
			return buildeErrorResponse(msgInfo);
		} catch (Exception e) {
			logger.error("error in data/ifsc api {}", Utility.getExceptionAsString(e));
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsgCode("500");
			msgInfo.setMsgDescription(SoaConstants.FAILURE);
			msgInfo.setMsg("TimeOut from server side");
			return buildeErrorResponse(msgInfo);
		}
		return null;
	}

	private ResponseEntity<Object> buildeErrorResponse(MsgInfo msgInfo) {
		OutputResponse outputResponse = new OutputResponse();
		Result result = new Result();
		Response response = new Response();
		response.setMsginfo(msgInfo);
		response.setPayload(new Transaction());
		result.setResponse(response);
		outputResponse.setResult(result);
		String json = new Gson().toJson(outputResponse);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private Transaction convertToTransaction(BankResponsePayload payload) {
		Transaction transaction = new Transaction();
		if (null != payload && null != payload.getBankDetails()) {
			int lastIndex = payload.getBankDetails().size()-1;
			BankDetails bankDetails = payload.getBankDetails().get(lastIndex);
			transaction.setIfscCode(bankDetails.getBnkIfscCode());
			transaction.setMicrBankName(bankDetails.getBnkName());
			transaction.setMicrBranchName(bankDetails.getBnkBranchName());
			transaction.setMicroCode(bankDetails.getBnkmicrCode());
		}
		return transaction;
	}

	private void setMsgInfo(SOABankResponse bankResponse,MsgInfo msgInfo){
		if (null != bankResponse.getMsgInfo() && bankResponse.getMsgInfo().getMsgCode().equals("200")) {
			msgInfo.setMsgDescription(AppConstants.SUCCESS);
			msgInfo.setMsg(AppConstants.IFSC_MICR_MESSAGE);
		} else if (null != bankResponse.getMsgInfo()) {
			msgInfo.setMsgDescription(bankResponse.getMsgInfo().getMsgDescription());
			msgInfo.setMsg(bankResponse.getMsgInfo().getMsg());
		}
	}

	private boolean checkIfscMicrCondition(IfscMicrRequest ifscMicrRequest) {
		return Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_IFSC_MICR_MIGRATION)) && null != ifscMicrRequest.getRequest().getData() && !StringUtils.isEmpty(ifscMicrRequest.getRequest().getData().getChannel());
	}
}
