package com.mli.mpro.location.services.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.mli.mpro.location.models.PincodeMasterDynamoDb;
import com.mli.mpro.location.models.PincodeMasterResolver;
import com.mli.mpro.location.services.PincodeMasterService;
import com.mli.mpro.productRestriction.util.AppConstants;

@Service
public class PincodeMasterServiceImpl implements PincodeMasterService {

	private static final Logger logger = LoggerFactory.getLogger(PincodeMasterServiceImpl.class);
	
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	@Override
	public HashMap<String, String> getStatesAndCitiesByPincode(String pincode) {
		HashMap<String, String> stateCityList = new HashMap<>();
		try {
			logger.info("calling the getStatesAndCitiesByPincode with pincode{}", pincode);
			DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB,
				      new DynamoDBMapperConfig(new PincodeMasterResolver()));
			PincodeMasterDynamoDb stateAndcitydetails = mapper.load(PincodeMasterDynamoDb.class, pincode);

			logger.info("calling the getStatesAndCitiesByPincode stateAndcitydetails {}", stateAndcitydetails);
			if (!StringUtils.isEmpty(stateAndcitydetails)) {
				stateCityList.put(AppConstants.PINCODE_STATE, stateAndcitydetails.getState());
				stateCityList.put(AppConstants.PINCODE_CITY, stateAndcitydetails.getCity());
			}
		} catch (Exception e) {
			logger.info("Error occurred while fetching state and city based on pincode is {}", e.getMessage());
		}
		return stateCityList;
	}

}
