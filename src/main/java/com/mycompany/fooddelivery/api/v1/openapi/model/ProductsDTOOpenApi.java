package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.ProductDTO;

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
