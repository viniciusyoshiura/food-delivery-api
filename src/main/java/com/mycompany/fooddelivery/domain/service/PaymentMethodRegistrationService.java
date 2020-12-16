package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.PaymentMethodNotFoundException;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.repository.PaymentMethodRepository;

@Service
public class PaymentMethodRegistrationService {

	
	private static final String MSG_PAYMENT_METHOD_IN_USE = "Payment method with code %d could not be removed, since it is in use";
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Transactional
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}

	@Transactional
	public void remove(Long paymentMethodId) {
		try {
			paymentMethodRepository.deleteById(paymentMethodId);
			paymentMethodRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PaymentMethodNotFoundException(paymentMethodId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_PAYMENT_METHOD_IN_USE, paymentMethodId));
		}
	}

	public PaymentMethod searchOrFail(Long paymentMethodId) {
		return paymentMethodRepository.findById(paymentMethodId)
				.orElseThrow(() -> new PaymentMethodNotFoundException(paymentMethodId));
	}

}
