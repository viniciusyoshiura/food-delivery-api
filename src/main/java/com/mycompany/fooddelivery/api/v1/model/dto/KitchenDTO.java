package com.mycompany.fooddelivery.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "kitchens")
@Setter
@Getter
public class KitchenDTO extends RepresentationModel<KitchenDTO> {
	
	@ApiModelProperty(example = "1")
//	@JsonView({ RestaurantView.Summary.class})
	private Long id;
	
	@ApiModelProperty(example = "Brazilian")
//	@JsonView({ RestaurantView.Summary.class})
	private String name;
	
}
