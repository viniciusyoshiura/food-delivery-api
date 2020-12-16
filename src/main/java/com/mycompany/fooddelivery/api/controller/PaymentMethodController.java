package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.PaymentMethodDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.PaymentMethodInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.api.model.input.PaymentMethodInput;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.repository.PaymentMethodRepository;
import com.mycompany.fooddelivery.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@Autowired
	private PaymentMethodDTOConverter paymentMethodDTOConverter;

	@Autowired
	private PaymentMethodInputDeconverter paymentMethodInputDeconverter;

	@GetMapping
	public List<PaymentMethodDTO> list() {
		List<PaymentMethod> allPaymentMethods = paymentMethodRepository.findAll();

		return paymentMethodDTOConverter.toCollectionModel(allPaymentMethods);
	}

	@GetMapping("/{paymentMethodId}")
	public PaymentMethodDTO search(@PathVariable Long paymentMethodId) {
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		return paymentMethodDTOConverter.toModel(paymentMethod);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodDTO insert(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod paymentMethod = paymentMethodInputDeconverter.toDomainObject(paymentMethodInput);

		paymentMethod = paymentMethodRegistrationService.save(paymentMethod);

		return paymentMethodDTOConverter.toModel(paymentMethod);
	}

	@PutMapping("/{paymentMethodId}")
	public PaymentMethodDTO update(@PathVariable Long paymentMethodId,
			@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		paymentMethodInputDeconverter.copyToDomainObject(paymentMethodInput, currentPaymentMethod);

		currentPaymentMethod = paymentMethodRegistrationService.save(currentPaymentMethod);

		return paymentMethodDTOConverter.toModel(currentPaymentMethod);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long paymentMethodId) {
		paymentMethodRegistrationService.remove(paymentMethodId);
	}

}
