package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.PaymentMethodDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.api.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi{

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private PaymentMethodDTOConverter paymentMethodDTOConverter;
	
	@GetMapping
	public List<PaymentMethodDTO> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		return paymentMethodDTOConverter.toCollectionModel(restaurant.getPaymentMethods());
	}
	
	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.disassociatePaymentMethod(restaurantId, paymentMethodId);
	}
	
	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.associatePaymentMethod(restaurantId, paymentMethodId);
	}
	
}
