package com.mli.mpro.location.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.location.auth.filter.AuthorizationException;
import com.mli.mpro.location.auth.filter.ErrorResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Component
public class ContextPathInterceptor implements HandlerInterceptor {
    @Value("${custom.context.path}")
    private List<String> customContextPath;

    private static final Logger log = LoggerFactory.getLogger(ContextPathInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String requestURI = request.getRequestURI();
            if (Boolean.TRUE.equals(request.getAttribute(AppConstants.FORWARDED))) {
                return true;
            }
            Optional<String> currentContextPath = customContextPath.stream().filter(requestURI::contains).findFirst();
            if (request.getAttribute(AppConstants.FORWARDED) == null) {
                if (currentContextPath.isPresent()) {
                    String modifiedURI = requestURI.replace(currentContextPath.get(), "");
                    request.setAttribute(AppConstants.FORWARDED, true);
                    request.getRequestDispatcher(modifiedURI).forward(request, response);
                    return false;
                }
                throw new AuthorizationException("Not a valid url: "+ requestURI);
            }
        } catch (AuthorizationException e) {
            log.error("Authorization error occurred during context path resolving: {}", e.getMessage());
            buildErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        } catch (Exception e) {
            log.error("Unknown Error occurred during context path resolving {}", Utility.getExceptionAsString(e));
            buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return false;
    }

    private void buildErrorResponse(HttpServletResponse response, int status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(message);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(new ObjectMapper().writeValueAsBytes(errorResponse));
            outputStream.flush();
        } catch (IOException e) {
            log.error("Error writing error response: {}", e.getMessage());
        }
    }
}