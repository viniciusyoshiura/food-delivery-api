package com.mycompany.fooddelivery.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// ---------- @Relation: changes the name of return array to 'cities' (hateoas)
@Relation(collectionRelation = "cities")
@Setter
@Getter
public class CityDTO extends RepresentationModel<CityDTO>{

	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Ribeirão Preto")
    private String name;
    
    private StateDTO state;
	
}
