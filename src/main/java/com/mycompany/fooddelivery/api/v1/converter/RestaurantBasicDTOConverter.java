package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.RestaurantController;
import com.mycompany.fooddelivery.api.v1.model.dto.RestaurantBasicDTO;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantBasicDTOConverter extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private HateoasLinks hateoasLinks;
    
    public RestaurantBasicDTOConverter() {
        super(RestaurantController.class, RestaurantBasicDTO.class);
    }
    
    @Override
    public RestaurantBasicDTO toModel(Restaurant restaurant) {
        RestaurantBasicDTO restaurantDTO = createModelWithId(
        		restaurant.getId(), restaurant);
        
        modelMapper.map(restaurant, restaurantDTO);
        
        restaurantDTO.add(hateoasLinks.linkToRestaurants("restaurants"));
        
        restaurantDTO.getKitchen().add(
                hateoasLinks.linkToKitchen(restaurant.getKitchen().getId()));
        
        return restaurantDTO;
    }
    
    @Override
    public CollectionModel<RestaurantBasicDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(hateoasLinks.linkToRestaurants());
    }   
	
}
