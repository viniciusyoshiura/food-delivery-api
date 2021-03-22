package com.mycompany.fooddelivery.api.v1.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.RestaurantInput;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantInputDeconverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
//		Restaurant restaurant = new Restaurant();
//		restaurant.setName(restaurantInput.getName());
//		restaurant.setShippingFee(restaurantInput.getShippingFee());
//		
//		Kitchen kitchen = new Kitchen();
//		kitchen.setId(restaurantInput.getKitchen().getId());
//		
//		restaurant.setKitchen(kitchen);
//		
//		return restaurant;
		
		return modelMapper.map(restaurantInput, Restaurant.class);
	}
	
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		// ---------- In order to avoid org.hibernate.HibernateException: identifier of an instance of 
		// ---------- com.mycompany.fooddelivery.domain.model.Kitchen was altered from 1 to 2
		restaurant.setKitchen(new Kitchen());
		
		if (restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}
		
		modelMapper.map(restaurantInput, restaurant);
	}
	
}
