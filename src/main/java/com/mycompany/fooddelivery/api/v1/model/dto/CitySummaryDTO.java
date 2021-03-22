package com.mycompany.fooddelivery.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cities")
@Setter
@Getter
public class CitySummaryDTO extends RepresentationModel<CitySummaryDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Curitiba")
	private String name;
	
	@ApiModelProperty(example = "Paraná")
	private String state;
	
}
