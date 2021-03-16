package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.model.dto.ProductDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProductsDTO")
@Data
public class ProductsDTOOpenApi {

	private ProductsEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("ProductsEmbeddedDTOOpenApi")
    @Data
    public class ProductsEmbeddedDTOOpenApi {
        
        private List<ProductDTO> products;
        
    }    
	
}
