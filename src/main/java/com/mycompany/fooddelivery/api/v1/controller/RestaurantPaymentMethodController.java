package com.mycompany.fooddelivery.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.converter.PaymentMethodDTOConverter;
import com.mycompany.fooddelivery.api.v1.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.api.v1.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private PaymentMethodDTOConverter paymentMethodDTOConverter;

	@Autowired
	private HateoasLinks hateoasLinks;

	@GetMapping
	public CollectionModel<PaymentMethodDTO> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		CollectionModel<PaymentMethodDTO> paymentMethodDTOs = paymentMethodDTOConverter
				.toCollectionModel(restaurant.getPaymentMethods()).removeLinks()
				.add(hateoasLinks.linkToRestaurantPaymentMethod(restaurantId))
				.add(hateoasLinks.linkToRestaurantPaymentMethodAssociation(restaurantId, "associate"));

		paymentMethodDTOs.getContent().forEach(paymentMethodDTO -> {
			paymentMethodDTO.add(hateoasLinks.linkToRestaurantPaymentMethodDisassociation(restaurantId,
					paymentMethodDTO.getId(), "disassociate"));
		});

		return paymentMethodDTOs;
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.disassociatePaymentMethod(restaurantId, paymentMethodId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.associatePaymentMethod(restaurantId, paymentMethodId);
		return ResponseEntity.noContent().build();
	}

}
