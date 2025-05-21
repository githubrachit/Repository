package com.mli.mpro.location.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import com.mli.mpro.utils.Utility;

//@Component
public class RequestValidationFilter extends OncePerRequestFilter
{
	
	private static final Logger log = LoggerFactory.getLogger(RequestValidationFilter.class);


	private Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> isValidSchema(HttpServletRequest request) {

		String requestUrl = request.getRequestURL().toString();
		String requestBody = getRequestBody(request);
		Utility utility=new Utility();
		String[] urlSplitArray=requestUrl.split("/");
		String apiName = urlSplitArray[urlSplitArray.length - 1];
		return utility.validateJson(requestBody, apiName); // Placeholder, replace with actual validation logic
	}

	private String getRequestBody(HttpServletRequest request) {
		StringBuilder requestBodyBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				requestBodyBuilder.append(line);
			}
		} catch (IOException e) {
			log.error("Request Validation Filter :Error in getting request body ");
		}

		return requestBodyBuilder.toString();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors=isValidSchema(request);
		if (CollectionUtils.isEmpty(errors)) {
			// Request meets schema validation criteria, proceed with the chain
			try {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				filterChain.doFilter(request, response);
			}catch (AuthorizationException | TokenValidationException ex){
				log.error("Error writing error response in doFilterInternal() RequestValidationFilter.class : {}", ex.getMessage());
			}
			catch (Exception e) {
				log.error("error occurred {}", Utility.getExceptionAsString(e));
				buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			}
		}
		else {
			com.mli.mpro.location.auth.filter.ErrorResponse errorResponse = new com.mli.mpro.location.auth.filter.ErrorResponse("Invalid Request",errors);
			// Request does not meet schema validation criteria, reject it
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			try (OutputStream outputStream = response.getOutputStream()) {
				log.error("Authentication Error: {}", errorResponse.getMessage());
				outputStream.write(new ObjectMapper().writeValueAsBytes(errorResponse));
				outputStream.flush();
			} catch (IOException e) {
				log.error("Error writing error response: {}", e.getMessage());
			}
		}

	}
	private void buildErrorResponse(HttpServletResponse response, int status, String message)  {
		com.mli.mpro.location.auth.filter.ErrorResponse errorResponse = new com.mli.mpro.location.auth.filter.ErrorResponse(message);
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try (OutputStream outputStream = response.getOutputStream()) {
			log.error("Authentication Error: {}", errorResponse.getMessage());
			outputStream.write(new ObjectMapper().writeValueAsBytes(errorResponse));
			outputStream.flush();
		} catch (IOException e) {
			log.error("Error writing error response: {}", e.getMessage());
		}
	}

}
