package com.mli.mpro.location.config;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.location.services.impl.DecryptionPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ravishankar
 *
 */
@Configuration
public class DBClientConnectionConfig {

	private static final String ENVIRONMENT = System.getenv("env");
    private static final Logger log = LoggerFactory.getLogger(DBClientConnectionConfig.class);
	private static final String PREPROD = "preprod";
	public String pincodeMasterTableName;
	private static String DYNAMODBPINCODEMASTERTABLE;

	@Value("${dynamodb.pincodeMasterTable}")
	public void setPincodeMasterTableName(String pincodeMasterTableName) {
		DBClientConnectionConfig.DYNAMODBPINCODEMASTERTABLE = pincodeMasterTableName;
	}
	 
    @Value("${mongodb.primary.host}")
    private String primaryHost;

    @Value("${mongodb.secondary.host}")
    private String secondaryHost;

    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.primary.port}")
    private String primaryPort;

    @Value("${mongodb.secondary.port}")
    private String secondaryPort;

    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.authentication-database}")
    private String authenticationDatabase;

    @Value("${mongodb.replicaSet}")
    private String replicaSet;

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate(ApplicationContext applicationContext) throws Exception {
	log.info("Connection to MongoDB Database Initiated");
	DecryptionPropertyService propertyServiceForJasyptStarter = applicationContext.getBean(DecryptionPropertyService.class);
	MongoProperties mongoProperties = new MongoProperties();
	String connectionStringURI = null;

	if (ENVIRONMENT.equals("dev") || ENVIRONMENT.equals("uat") || ENVIRONMENT.equals(PREPROD) || ENVIRONMENT.equals("qa") || ENVIRONMENT.equals("neodev")) {
	    connectionStringURI = String.format("mongodb://%s:%s@%s:%s/%s?authSource=%s", username, propertyServiceForJasyptStarter.getDecryptedKey(),
		    primaryHost, primaryPort, database, authenticationDatabase);
	} else if (ENVIRONMENT.equals("prod")) {
	    connectionStringURI = String.format("mongodb://%s:%s@%s:%s,%s:%s/%s?replicaSet=%s&authSource=%s", username,
		    propertyServiceForJasyptStarter.getDecryptedKey(), primaryHost, primaryPort, secondaryHost, secondaryPort, database, replicaSet,
		    authenticationDatabase);
	} else if (ENVIRONMENT.equals("local")) {
	    connectionStringURI = String.format("mongodb://%s:%s/%s", primaryHost, primaryPort, database);
	} else {
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Environment Variable does not matched");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.NOT_FOUND);
	}
	//log.info("connectionStringURI:" + connectionStringURI);
	mongoProperties.setUri(connectionStringURI);
	return new MongoTemplate(primaryFactory(mongoProperties));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory primaryFactory(final MongoProperties mongo) throws Exception {
	try {
	    return new SimpleMongoClientDatabaseFactory(mongo.getUri());
	} catch (Exception e) {
        log.error("Exception in primaryFactory method:",e);
	}
	return null;
    }

	public static String getPincodeMasterTableName() {
		log.info("table name based on environment {} ", DYNAMODBPINCODEMASTERTABLE);
		return DYNAMODBPINCODEMASTERTABLE;
	}
}
