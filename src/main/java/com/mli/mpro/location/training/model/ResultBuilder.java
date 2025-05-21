package com.mli.mpro.location.training.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.utils.Utility;

public class ResultBuilder {
	private ResultBuilder() {}
	
	 public static Result failure(HttpStatus status, List<Object> errors) {
		    MsgInfo msgInfo = new MsgInfo(String.valueOf(status.value()), "", "");
		    return new Result(new Response(msgInfo));
		  }
} 
