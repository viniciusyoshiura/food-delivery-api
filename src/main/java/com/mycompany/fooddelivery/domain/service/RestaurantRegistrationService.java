package com.mycompany.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.fooddelivery.domain.exception.RestaurantNotFoundException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
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
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}
	
	@Transactional
	public void deactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::deactivate);
	}
	
	@Transactional
	public void open(Long restaurantId) {
	    Restaurant currentRestaurant = searchOrFail(restaurantId);
	    
	    currentRestaurant.open();
	}

	@Transactional
	public void close(Long restaurantId) {
	    Restaurant currentRestaurant = searchOrFail(restaurantId);
	    
	    currentRestaurant.close();
	}  
	
	@Transactional
	public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = searchOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);
		
		restaurant.removePaymentMethod(paymentMethod);
	}
	
	@Transactional
	public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = searchOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);
		
		restaurant.insertPaymentMethod(paymentMethod);
	}
	
	@Transactional
	public void disassociateResponsible(Long restaurantId, Long userId) {
	    Restaurant restaurant = searchOrFail(restaurantId);
	    User user = userRegistrationService.searchOrFail(userId);
	    
	    restaurant.removeResponsible(user);
	}

	@Transactional
	public void associateResponsible(Long restaurantId, Long userId) {
	    Restaurant restaurant = searchOrFail(restaurantId);
	    User user = userRegistrationService.searchOrFail(userId);
	    
	    restaurant.addResponsible(user);
	}
	
}
