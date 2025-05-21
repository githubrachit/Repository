package com.mli.mpro.location.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author ravishankar
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.mli.mpro", mongoTemplateRef = "primaryMongoTemplate")
public class DBClientConfigHandler {

}
