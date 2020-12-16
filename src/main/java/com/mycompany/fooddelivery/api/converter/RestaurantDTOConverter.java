package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.RestaurantDTO;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantDTO toModel(Restaurant restaurant) {
//		KitchenDTO kitchenDTO = new KitchenDTO();
//		kitchenDTO.setId(restaurant.getKitchen().getId());
//		kitchenDTO.setName(restaurant.getKitchen().getName());
//		
//		RestaurantDTO restaurantDTO = new RestaurantDTO();
//		restaurantDTO.setId(restaurant.getId());
//		restaurantDTO.setName(restaurant.getName());
//		restaurantDTO.setShippingFee(restaurant.getShippingFee());
//		restaurantDTO.setKitchen(kitchenDTO);
//		return restaurantDTO;
		return modelMapper.map(restaurant, RestaurantDTO.class);
	}
	
	public List<RestaurantDTO> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}
	
}
