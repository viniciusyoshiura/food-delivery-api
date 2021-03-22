package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.CityDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CitiesDTO")
@Data
public class CitiesDTOOpenApi {

	private CitiesEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CitiesEmbeddedDTOOpenApi")
	@Data
	public class CitiesEmbeddedDTOOpenApi {
		
		private List<CityDTO> cities;
		
	}
	
}
