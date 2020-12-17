package com.mycompany.fooddelivery.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.RestaurantDTOConverter;
import com.mycompany.fooddelivery.api.converter.RestaurantInputConverter;
import com.mycompany.fooddelivery.api.deconverter.RestaurantInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.RestaurantDTO;
import com.mycompany.fooddelivery.api.model.input.RestaurantInput;
import com.mycompany.fooddelivery.core.utils.ObjectMergeUtils;
import com.mycompany.fooddelivery.core.utils.ValidationUtils;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.CityNotFoundException;
import com.mycompany.fooddelivery.domain.exception.KitchenNotFoundException;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.repository.RestaurantRepository;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private RestaurantDTOConverter restaurantDTOConverter;
	
	@Autowired
	private RestaurantInputDeconverter restaurantInputDeconverter;
	
	@Autowired
	private RestaurantInputConverter restaurantInputConverter;
	
	@Autowired
	private ValidationUtils validationUtils;
	
	@Autowired
	private ObjectMergeUtils objectMergeUtils;
	
	@GetMapping
	public List<RestaurantDTO> list() {
//		return restaurantRepository.findAll();
		return restaurantDTOConverter.toCollectionModel(restaurantRepository.findAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantDTO search(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		return restaurantDTOConverter.toModel(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantDTO insert(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
//			return restaurantRegistrationService.save(restaurant);
			
			Restaurant restaurant = restaurantInputDeconverter.toDomainObject(restaurantInput);
			
			return restaurantDTOConverter.toModel(restaurantRegistrationService.save(restaurant));
		} catch (KitchenNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
		
		Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		restaurantInputDeconverter.copyToDomainObject(restaurantInput, currentRestaurant);
		
//		Restaurant restaurant = restaurantInputDeconverter.toDomainObject(restaurantInput);
		
		// ---------- Ignoring id and paymentMethods and address and dateRegister and
		// products
//		BeanUtils.copyProperties(restaurant, currentRestaurant, "id", "paymentMethods", "address", "dateRegister",
//				"products");
		try {
//			return restaurantRegistrationService.save(currentRestaurant);
			return restaurantDTOConverter.toModel(restaurantRegistrationService.save(currentRestaurant));
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PatchMapping("/{restaurantId}")
	public RestaurantDTO partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields,
			HttpServletRequest request) {
		Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		objectMergeUtils.merge(fields, currentRestaurant, request);
		
		validationUtils.validate(currentRestaurant, "restaurant");
		
		return update(restaurantId, restaurantInputConverter.toRestaurantInput(currentRestaurant));
	}
	
	
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.activate(restaurantId);
	}
	
	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.deactivate(restaurantId);
	}
	
}
