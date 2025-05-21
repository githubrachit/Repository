package com.mli.mpro.onboarding.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

import static java.util.Objects.isNull;


public class ApplicationUtils {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

	private ApplicationUtils() {
	}

	public static String toString(Object obj) {
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("{");
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				ReflectionUtils.makeAccessible(field);
				Object value = ReflectionUtils.getField(field, obj);
				if (isLoggerField(value)) {
					continue;
				}
				if (isNull(value)) {
					value = "null";
				} else {
					value = field.isAnnotationPresent(Sensitive.class)
							? getMaskedValue(Objects.requireNonNull(value),
									field.getAnnotation(Sensitive.class).value())
							: value;
				}
				prepareStringBuilder(builder, value, field, obj);
			}
			builder = new StringBuilder(builder.substring(0, builder.length() - 2));
			builder.append("}");
			return builder.toString();
		}  catch (Exception ex) {
			logger.info("Exception occur at masking {}", Utility.getExceptionAsString(ex));
			return builder.append(" **** ").toString();
		}
	}

	public static void prepareStringBuilder(StringBuilder builder, Object fieldValue, Field field, Object originalObject) {
		try {
			String fieldName = isNull(field.getAnnotation(JsonProperty.class)) ? field.getName() : field.getAnnotation(JsonProperty.class).value();
			builder.append("\"" + fieldName + "\"").append(":");
			if (isNull(ReflectionUtils.getField(field, originalObject)) || isNonPrimitiveType(ReflectionUtils.getField(field, originalObject))) {
				builder.append(fieldValue).append(", ");
			} else {
				Object val = fieldValue.getClass().equals(Date.class) ? Utility.getFormattedDateString((Date)fieldValue, AppConstants.UTC_DATE_FORMAT) : fieldValue;
				val = val.getClass().equals(String.class) ? "\"" + val + "\"" : val;
				builder.append(val).append(", ");
			}
		} catch (Exception ex) {
			logger.error("Error while prepareStringbuilder {}", Utility.getExceptionAsString(ex));
		}
	}

	public static String getMaskedValue(Object input, MaskType maskType) {
		try {
			String stringToBeMasked = input.toString();
			return stringToBeMasked.replaceAll(maskType.getValue(), "x").replaceAll(AppConstants.MASKED_REGEX, AppConstants.DEFAULT_MASK);
		}catch(Exception ex) {
			return "null";
		}
	}

	private static boolean isNonPrimitiveType(Object value) {
		boolean flag = true;
		if (value.getClass().equals(String.class) ||
				(Objects.nonNull(value.getClass().getSuperclass()) && value.getClass().getSuperclass().equals(Number.class))
					|| value.getClass().equals(Boolean.class)
					|| value.getClass().equals(Date.class)) {
			flag = false;
		}
		return flag;
	}

	private static boolean isLoggerField(Object val) {
		return val instanceof Logger;
	}
}
