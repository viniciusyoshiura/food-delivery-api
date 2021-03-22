package com.mycompany.fooddelivery.api.v1.model.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantBasicDTO extends RepresentationModel<RestaurantBasicDTO> {

	@ApiModelProperty(example = "1")
    private Long id;
    
    @ApiModelProperty(example = "Thai Gourmet")
    private String name;
    
    @ApiModelProperty(example = "12.00")
    private BigDecimal shippingFee;
    
    private KitchenDTO kitchen;
	
}
