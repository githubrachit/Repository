//package com.mli.mpro.filter;
//
//import com.mli.mpro.productRestriction.util.AppConstants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(1)
//public class AdditionalResponseHeaderFilter extends GenericFilterBean {
//    Logger log = LoggerFactory.getLogger(AdditionalResponseHeaderFilter.class);
//
//    @Value("${strict.transport.security.value}")
//    private String strictTransportSecurity;
//
//    @Value("${content.security.policy}")
//    private String contentHeader;
//
//    @Value("${x.xss.protection}")
//    private String protectionHeader;
//
//    @Value("${access.control.origin}")
//    private String accessControlHeader;
//
//    @Value("${referrer-policy}")
//    private String referrerHeader;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        httpResponse.setHeader(AppConstants.STS, strictTransportSecurity);
//        httpResponse.setHeader(AppConstants.HEADER_CSPH, contentHeader);
//        httpResponse.setHeader(AppConstants.HEADER_X_XSS_P, protectionHeader);
//        httpResponse.setHeader(AppConstants.HEADER_ACAO, accessControlHeader);
//        httpResponse.setHeader(AppConstants.HEADER_RP, referrerHeader);
//
//        try {
//            chain.doFilter(servletRequest, servletResponse);
//        } finally {
//            log.info("Response status {} and added additional headers {}", httpResponse.getStatus(), httpResponse.getHeader(AppConstants.STS));
//        }
//    }
//
//
//    @Override
//    public void destroy() {
//        // destroy part
//    }
//}