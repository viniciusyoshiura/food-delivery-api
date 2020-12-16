package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.input.RestaurantInput;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantInputConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantInput toRestaurantInput(Restaurant restaurant) {
//		RestaurantInput restaurantInput = new RestaurantInput();
//		restaurantInput.setName(restaurant.getName());
//		restaurantInput.setShippingFee(restaurant.getShippingFee());
//		
//		Kitchen kitchen = new Kitchen();
//		kitchen.setId(restaurant.getKitchen().getId());
//		
//		restaurantInput.setKitchen(kitchenInputConverter.toKitchenInput(kitchen));
//		
//		return restaurantInput;
		
		return modelMapper.map(restaurant, RestaurantInput.class);
	}
	
}
