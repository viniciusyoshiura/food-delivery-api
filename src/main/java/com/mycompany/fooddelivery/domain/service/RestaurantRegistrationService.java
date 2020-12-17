package com.mycompany.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.fooddelivery.domain.exception.RestaurantNotFoundException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	public Restaurant searchOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		
		Long kitchenId = restaurant.getKitchen().getId();
		Long cityId = restaurant.getAddress().getCity().getId();
		
		Kitchen kitchen = kitchenRegistrationService.searchOrFail(kitchenId);
		City city = cityRegistrationService.searchOrFail(cityId);
		
		restaurant.setKitchen(kitchen);
		restaurant.getAddress().setCity(city);

		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void activate(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		
		currentRestaurant.activate();
	}
	
	@Transactional
	public void deactivate(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		
		currentRestaurant.deactivate();
	}
	
}
