package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductDTO extends RepresentationModel<ProductDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cupim")
    private String name;
	
	@ApiModelProperty(example = "Type of meat")
    private String description;
	
	@ApiModelProperty(example = "12.50")
    private BigDecimal price;
	
	@ApiModelProperty(example = "true")
    private Boolean active; 
	
}
