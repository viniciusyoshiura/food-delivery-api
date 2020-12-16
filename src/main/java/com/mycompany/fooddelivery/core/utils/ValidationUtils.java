package com.mycompany.fooddelivery.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.mycompany.fooddelivery.core.validation.exception.ValidationException;

@Component
public class ValidationUtils {

	// ---------- SmartValidator validate an object
	@Autowired
	private SmartValidator smartValidator;

	public void validate(Object object, String objectName) {

		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(object, objectName);

		smartValidator.validate(object, beanPropertyBindingResult);

		if (beanPropertyBindingResult.hasErrors()) {
			throw new ValidationException(beanPropertyBindingResult);
		}

	}

}
