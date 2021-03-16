package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.model.dto.StateDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("StatesDTO")
@Data
public class StatesDTOOpenApi {

	private StatesEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("StatesEmbeddedDTOOpenApi")
    @Data
    public class StatesEmbeddedDTOOpenApi {
        
        private List<StateDTO> states;
        
    }
	
}
