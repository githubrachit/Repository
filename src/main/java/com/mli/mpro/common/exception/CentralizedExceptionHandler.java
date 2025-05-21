package com.mli.mpro.common.exception;

import java.util.Date;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.productRestriction.util.ProposalUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mli.mpro.common.models.ErrorResponse;
import com.mli.mpro.common.models.OutputResponse;
import com.mli.mpro.common.models.Response;

/**
 * @author arpita
 * <p>
 * The global exception handler class which handles the exceptions arising in
 * application and gives the response containing relevant error message to the
 * client request
 */

@ControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {

    // this method handles the UserHandledException thrown in application
    @ExceptionHandler(UserHandledException.class)
    public ResponseEntity<OutputResponse> handleBaseExceptions(UserHandledException exception) {

	ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getHttpstatus().value(), exception.getErrorMessages());
	if(exception.getResponse()==null){
		exception.setResponse(new Response());
	}
	exception.getResponse().setErrorResponse(errorResponse);
	Response response = exception.getResponse();
	if(!errorResponse.getErrorMessages().isEmpty()){
		response.setMsgInfo(ProposalUtil.setMsgInfoForGetProposal(String.valueOf(exception.getHttpstatus().value()), errorResponse.getErrorMessages().get(0)));
	} else {
		response.setMsgInfo(ProposalUtil.setMsgInfoForGetProposal(String.valueOf(exception.getHttpstatus().value())));
	}
	OutputResponse outputResponse = new OutputResponse();
	outputResponse.setResponse(response);
	if(exception.isStatusCodeOK()){
		return new ResponseEntity<>(outputResponse, HttpStatus.OK);
	}
	return new ResponseEntity<>(outputResponse, exception.getHttpstatus());

    }

}
