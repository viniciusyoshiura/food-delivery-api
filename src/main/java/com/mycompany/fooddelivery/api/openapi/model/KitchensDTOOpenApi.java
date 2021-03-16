package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.model.dto.KitchenDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("KitchensDTO")
@Setter
@Getter
public class KitchensDTOOpenApi {

	private KitchensEmbeddedDTOOpenApi _embedded;
	private Links _links;
	private PageDTOOpenApi page;
	
	@ApiModel("KitchensEmbeddedDTOOpenApi")
	@Data
	public class KitchensEmbeddedDTOOpenApi {
		
		private List<KitchenDTO> kitchens;
		
	}
	
}
