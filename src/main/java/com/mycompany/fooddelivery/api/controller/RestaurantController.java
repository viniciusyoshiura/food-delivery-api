package com.mycompany.fooddelivery.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.RestaurantDTOConverter;
import com.mycompany.fooddelivery.api.converter.RestaurantInputConverter;
import com.mycompany.fooddelivery.api.deconverter.RestaurantInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.RestaurantDTO;
import com.mycompany.fooddelivery.api.model.input.RestaurantInput;
import com.mycompany.fooddelivery.api.model.view.RestaurantView;
import com.mycompany.fooddelivery.core.utils.ObjectMergeUtils;
import com.mycompany.fooddelivery.core.utils.ValidationUtils;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.CityNotFoundException;
import com.mycompany.fooddelivery.domain.exception.KitchenNotFoundException;
import com.mycompany.fooddelivery.domain.exception.RestaurantNotFoundException;
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
		return restaurantDTOConverter.toCollectionModel(restaurantRepository.findAll());
	}
	
	// --------- JsonView manipulates the JSON (DTOs)
//	@JsonView(RestaurantView.Summary.class)
//	@GetMapping(params = "format=resumed")
//	public List<RestaurantDTO> listResumed() {
//		return restaurantDTOConverter.toCollectionModel(restaurantRepository.findAll());
//	}

	// --------- JsonView manipulates the JSON (DTOs)
//	@JsonView(RestaurantView.OnlyName.class)
//	@GetMapping(params = "format=only-name")
//	public List<RestaurantDTO> listOnlyNames() {
//		return restaurantDTOConverter.toCollectionModel(restaurantRepository.findAll());
//	}
	
//	@GetMapping("/formatted")
//	public MappingJacksonValue listByFormat(@RequestParam(required = false) String format) {
//		List<Restaurant> restaurants = restaurantRepository.findAll();
//		List<RestaurantDTO> restaurantsDTOs = restaurantDTOConverter.toCollectionModel(restaurants);
//		
//		MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantsDTOs);
//		
//		restaurantsWrapper.setSerializationView(RestaurantView.Summary.class);
//		
//		if ("only-name".equals(format)) {
//			restaurantsWrapper.setSerializationView(RestaurantView.OnlyName.class);
//		} else if ("complete".equals(format)) {
//			restaurantsWrapper.setSerializationView(null);
//		}
//		
//		return restaurantsWrapper;
//	}
	
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
	
	@PutMapping("/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activateMultiple(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivateMultiple(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.deactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@PathVariable Long restaurantId) {
		restaurantRegistrationService.open(restaurantId);
	}

	@PutMapping("/{restaurantId}/closure")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@PathVariable Long restaurantId) {
		restaurantRegistrationService.close(restaurantId);
	} 
}
