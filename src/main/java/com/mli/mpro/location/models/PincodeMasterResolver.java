package com.mli.mpro.location.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.mli.mpro.location.config.DBClientConnectionConfig;

public class PincodeMasterResolver extends DynamoDBMapperConfig.DefaultTableNameResolver {

	@Override
	public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
		String tableName = DBClientConnectionConfig.getPincodeMasterTableName();
		String rawTableName = super.getTableName(clazz, config);
		return rawTableName.replace("PincodeMaster", tableName);
	}
}
