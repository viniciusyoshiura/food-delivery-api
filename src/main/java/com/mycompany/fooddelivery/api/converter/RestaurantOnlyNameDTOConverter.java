package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.RestaurantController;
import com.mycompany.fooddelivery.api.model.dto.RestaurantOnlyNameDTO;
import com.mycompany.fooddelivery.domain.model.Restaurant;

@Component
public class RestaurantOnlyNameDTOConverter extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOnlyNameDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private HateoasLinks hateoasLinks;
    
    public RestaurantOnlyNameDTOConverter() {
        super(RestaurantController.class, RestaurantOnlyNameDTO.class);
    }
    
    @Override
    public RestaurantOnlyNameDTO toModel(Restaurant restaurant) {
        RestaurantOnlyNameDTO restaurantDTO = createModelWithId(
                restaurant.getId(), restaurant);
        
        modelMapper.map(restaurant, restaurantDTO);
        
        restaurantDTO.add(hateoasLinks.linkToRestaurants("restaurants"));
        
        return restaurantDTO;
    }
    
    @Override
    public CollectionModel<RestaurantOnlyNameDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(hateoasLinks.linkToRestaurants());
    }   
	
}
