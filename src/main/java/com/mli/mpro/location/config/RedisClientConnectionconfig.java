package com.mli.mpro.location.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.mli.mpro.productRestriction.util.AppConstants;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * @author Tarun Pal
 */

@Configuration
@DependsOn("propertyConfigLoader")
public class RedisClientConnectionconfig {

	private static final Logger log = LoggerFactory.getLogger(RedisClientConnectionconfig.class);

	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;
	@Value("${spring.profiles.active}")
	private String environment;
	@Value("${redis.poolingRequired}")
	private boolean isPoolingRequired;
	@Value("${redis.readTimeout}")
	private int readTimeout;
	@Value("${redis.connectionTimeout}")
	private int connectionTimeout;
	@Value("${redis.maxConnections}")
	private int maxConnections;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.minIdle}")
	private int minIdle;
	@Value("${redis.maxRedirects}")
	private int maxRedirects;
	@Value("${redis.nodes}")
	private String clusterNodes;
	@Value("${redis.clusterEnabled}")
	private String clusterEnabled;

	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		JedisClientConfiguration.JedisClientConfigurationBuilder jedisBuilder = JedisClientConfiguration.builder()
				.readTimeout(Duration.ofMillis(readTimeout))
				.connectTimeout(Duration.ofMillis(connectionTimeout));

		if (isPoolingRequired) {
			jedisBuilder.usePooling().poolConfig(jedisPoolConfig());
		}

		if (AppConstants.YES.equalsIgnoreCase(clusterEnabled) && StringUtils.hasText(clusterNodes)) {
			List<String> nodes = Arrays.asList(clusterNodes.split(","));
			RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(nodes);
			redisClusterConfiguration.setMaxRedirects(maxRedirects);

			if (environment.equals(AppConstants.LOCAL) || environment.equals(AppConstants.DEV)) {
				log.info("Authentication configuration loading for RedisClusterConnection with pooling");
			} else {
				log.info("Authentication configuration loading for RedisClusterConnection with pooling and ssl");
				jedisBuilder.useSsl();
			}

			return getJedisConnectionFactory(redisClusterConfiguration, null, jedisBuilder.build());
		} else {
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
			redisStandaloneConfiguration.setHostName(host);
			redisStandaloneConfiguration.setPort(port);

			if (environment.equals(AppConstants.LOCAL) || environment.equals(AppConstants.DEV)) {
				log.info("Authentication configuration loading for RedisStandaloneConnection with pooling");
			} else {
				log.info("Authentication configuration loading for RedisStandaloneConnection with pooling and ssl");
				jedisBuilder.useSsl();
			}

			return getJedisConnectionFactory(null, redisStandaloneConfiguration, jedisBuilder.build());
		}

	}

	private JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,RedisStandaloneConfiguration redisStandaloneConfiguration,
															 JedisClientConfiguration jedisClientConfiguration)  {
		GenericObjectPoolConfig genericObjectPoolConfig = jedisClientConfiguration.getPoolConfig().get();
		log.info("MaxIdle is {} and MinIdle is {} and MaxTotal is {} and ConnectionTimeOut is {} and ReadTimeout is {}",
				genericObjectPoolConfig.getMaxIdle(), genericObjectPoolConfig.getMinIdle(),
				genericObjectPoolConfig.getMaxTotal(), jedisClientConfiguration.getConnectTimeout().getSeconds(),
				jedisClientConfiguration.getReadTimeout().getSeconds());
		JedisConnectionFactory jedisConnectionFactory = Objects.nonNull(redisClusterConfiguration) ?
				new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfiguration) :
				new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}

	@Bean
	@Primary
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setConnectionFactory(getJedisConnectionFactory());
		return template;
	}
	@Bean
	GenericObjectPoolConfig jedisPoolConfig() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(maxConnections);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		return poolConfig;
	}

}

