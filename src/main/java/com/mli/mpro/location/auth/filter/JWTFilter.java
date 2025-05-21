package com.mli.mpro.location.auth.filter;

import static com.mli.mpro.location.auth.filter.AuthenticationConstants.WHITELIST_ROUTES;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.LoginConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
@DependsOn("propertyConfigLoader")
public class JWTFilter extends OncePerRequestFilter {

	@Value("${jwt.secret.key}")
	private String jwtTokenSecretKey;

	@Value("${nj.token.secret.key}")
	private String njTokenSecretKey;
	
	@Autowired
	private DecryptService decryptService;

	private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private Map<String, String> headerMap = new HashMap<>();

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		String hostName = request.getHeader("Host");
		log.info("Domain name Location service for agentId {} {}",request.getHeader("Agentid"), hostName);
		String requestUrl = request.getRequestURI();
		log.info("Request URL: {}", requestUrl);
		String byPassHeader = request.getHeader(AuthenticationConstants.API_CLIENT_SECRET);
		return ((request.getRequestURI().contains(AuthenticationConstants.API_PATTERN))
				&& ((Objects.nonNull(requestUrl) && WHITELIST_ROUTES.values().stream().anyMatch(requestUrl::equals))
				|| (Objects.nonNull(byPassHeader) && decryptService.validateHeader(byPassHeader))));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("inside the jwtFilter");
		try {
			if (!isApiRequest(request)) {
				throw new AuthorizationException("Not a valid url");
			}
			String apiToken = request.getHeader("Apitoken");
			String agentId = request.getHeader("Agentid");
			validateApiTokenAndAgentId(apiToken, agentId);
			verifyTokenValidity(apiToken);
			String secretKey = setSecret(apiToken);
			Map<String, String> jwtDecodeMap = jwtDecodeMap(apiToken, secretKey);
			validateToken(jwtDecodeMap);
			isValidAgent(jwtDecodeMap, agentId);
			headerMap.put(LoginConstants.AGENTID, jwtDecodeMap.get("responseAgent"));
			filterChain.doFilter(request, response);
		} catch (AuthorizationException | TokenValidationException e) {
			log.error("Error occurred during token authentication {}", Utility.getExceptionAsString(e));
			buildErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		} catch (Exception e) {
			log.error("error occurred {}", Utility.getExceptionAsString(e));
			buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	private String setSecret(String apiToken) {
		String token = null;
		try {
			String[] jwtParts = apiToken.split("\\.");
			String payload = new String(java.util.Base64.getUrlDecoder().decode(jwtParts[1]));
			JsonObject jsonObject = new Gson().fromJson(payload, JsonObject.class);
			JsonElement payloadElement = jsonObject.get("payload");
			Map<String, Object> payloadMap = new Gson().fromJson(payloadElement, Map.class);
			if (payloadMap.containsKey("source") && (payloadMap.containsValue("NJ") || payloadMap.containsValue("PB")
					|| payloadMap.containsValue("SuperApp")
					|| payloadMap.containsValue(AppConstants.CATAXIS)
					|| payloadMap.containsValue(AppConstants.CATAXISB)
					|| payloadMap.containsValue(AppConstants.TELEDIY)
					|| payloadMap.containsValue(AppConstants.BROKER)
					|| payloadMap.containsValue(AppConstants.TURTLEMINT)
					|| payloadMap.containsValue(AppConstants.MotilalOswal)
					|| payloadMap.containsValue(AppConstants.AUBANK)
					|| payloadMap.containsValue(AppConstants.MIB)
					|| payloadMap.containsValue(AppConstants.BRMS_BROKER_DIY_JOURNEY))) {
				token = njTokenSecretKey;
			} else {
				token = jwtTokenSecretKey;
			}
			logger.info("set secret successfully");
		} catch (Exception e) {
			log.error("exception while getting jwt token paylaod {}", Utility.getExceptionAsString(e));
		}
		return token;
	}

	private void validateToken(Map<String, String> jwtDecodeMap) {
		if (jwtDecodeMap.containsKey(AuthenticationConstants.ERROR)) {
			throw new TokenValidationException(jwtDecodeMap.get(AuthenticationConstants.ERROR));
		}
	}

	private void validateApiTokenAndAgentId(String apiToken, String agentId) {
		if (StringUtils.isBlank(apiToken) || StringUtils.isBlank(agentId)) {
			throw new AuthorizationException("No token provided");
		}
	}

	private void verifyTokenValidity(String apiToken) throws TokenValidationException {
		if (isTokenBlacklisted(apiToken)) {
			throw new AuthorizationException("Token BlackListed");
		}
	}

	private void buildErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse(message);
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

	private boolean isTokenBlacklisted(String apiToken) throws TokenValidationException {
		try {
			logger.info("checking the token validity from redis");
			ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
			Long ttlForKey = valueOps.getOperations().getExpire(apiToken);
			return ttlForKey > 0;
		} catch (Exception e) {
			throw new TokenValidationException(e.getMessage());
		}
	}

	private boolean isApiRequest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri.contains(AuthenticationConstants.API_PATTERN);
	}

	private boolean isValidAgent(Map<String, String> jwtDecodeMap, String agentId) {
		if (agentId.equals(jwtDecodeMap.get("user"))
				|| agentId.equals(jwtDecodeMap.get(AuthenticationConstants.RESPONSE_AGENT))) {
			return true;
		} else {
			throw new AuthorizationException("Invalid Token: Token owner mismatch");
		}
	}

	public static Map<String, String> jwtDecodeMap(String token, String secret) {
		Map<String, String> jwtDecodeMap = new HashMap<>();
		try {
			Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
			Map<String, String> payload = claims.get("payload", Map.class);
			String user = payload.get("user");
			String responseAgent = payload.get(AuthenticationConstants.RESPONSE_AGENT);

			if (user != null) {
				jwtDecodeMap.put("user", user);
			}
			if (responseAgent != null) {
				jwtDecodeMap.put(AuthenticationConstants.RESPONSE_AGENT, responseAgent);
			}
			log.info("token decoded successfully for user: {}", user);
		} catch (SignatureException e) {
			jwtDecodeMap.put(AuthenticationConstants.ERROR, "Invalid Token: invalid signature");
		} catch (ExpiredJwtException e) {
			jwtDecodeMap.put(AuthenticationConstants.ERROR, "JWT token has expired");
		} catch (MalformedJwtException e) {
			jwtDecodeMap.put(AuthenticationConstants.ERROR, "JWT toekn  has malformed");
		} catch (JwtException e) {
			jwtDecodeMap.put(AuthenticationConstants.ERROR, "Invalid JWT token");
		}
		return jwtDecodeMap;
	}
}
