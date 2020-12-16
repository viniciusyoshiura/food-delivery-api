package com.mycompany.fooddelivery.core.utils;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectMergeUtils {

	public void merge(Map<String, Object> originFields, Object destinyObject, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			// ---------- Configuring validations of object
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			// ---------- Converts map to object Restaurant, including bigDeciaml attribute
			// ---------- (shippingFee)
			Object originObject = objectMapper.convertValue(originFields, destinyObject.getClass());

			originFields.forEach((name, value) -> {
				Field field = ReflectionUtils.findField(destinyObject.getClass(), name);
				// ---------- Allows the access of a private field
				field.setAccessible(true);

				Object newValue = ReflectionUtils.getField(field, originObject);

				System.out.println(name + " = " + value + " = " + newValue);

				ReflectionUtils.setField(field, destinyObject, newValue);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
}
