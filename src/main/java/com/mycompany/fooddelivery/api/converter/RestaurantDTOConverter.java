package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.RestaurantController;
import com.mycompany.fooddelivery.api.model.dto.RestaurantDTO;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantDTOConverter extends RepresentationModelAssemblerSupport<Restaurant, RestaurantDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public RestaurantDTOConverter() {
        super(RestaurantController.class, RestaurantDTO.class);
    }
	
	public RestaurantDTO toModel(Restaurant restaurant) {
		System.out.println("restaurant Id: " + restaurant.getId());
		RestaurantDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
		modelMapper.map(restaurant, restaurantDTO);
		
		restaurantDTO.add(hateoasLinks.linkToRestaurants("restaurants"));
        
        restaurantDTO.getKitchen().add(
                hateoasLinks.linkToKitchen(restaurant.getKitchen().getId()));
        
        restaurantDTO.getAddress().getCity().add(
                hateoasLinks.linkToCity(restaurant.getAddress().getCity().getId()));
        
        restaurantDTO.add(hateoasLinks.linkToRestaurantePaymentMethod(restaurant.getId(), 
                "payment-methods"));
        
        restaurantDTO.add(hateoasLinks.linkToRestaurantResponsibleUser(restaurant.getId(), 
                "responsibles"));
		
        return restaurantDTO;
	}
	
	@Override
	public CollectionModel<RestaurantDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
//		return restaurants.stream()
//				.map(restaurant -> toModel(restaurant))
//				.collect(Collectors.toList());
		return super.toCollectionModel(entities)
                .add(hateoasLinks.linkToRestaurants());
	}
	
}
