package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.RestaurantBasicDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantsBasicoDTO")
@Data
public class RestaurantsBasicDTOOpenApi {

	private RestaurantsEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("RestaurantsEmbeddedDTOOpenApi")
    @Data
    public class RestaurantsEmbeddedDTOOpenApi {
        
        private List<RestaurantBasicDTO> restaurants;
        
    }
	
}
