package com.mycompany.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.converter.UserDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.UserDTO;
import com.mycompany.fooddelivery.api.openapi.controller.RestaurantResponsibleUserControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/responsibles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private UserDTOConverter userDTOConverter;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	@GetMapping
	public CollectionModel<UserDTO> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		return userDTOConverter.toCollectionModel(restaurant.getResponsibles())
	            .removeLinks()
	            .add(hateoasLinks.linkToRestaurantResponsibleUser((restaurantId)));
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.disassociateResponsible(restaurantId, userId);
	}

	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.associateResponsible(restaurantId, userId);
	}

}
