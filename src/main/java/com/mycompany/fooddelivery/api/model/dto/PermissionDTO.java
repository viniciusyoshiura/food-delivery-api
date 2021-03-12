package com.mycompany.fooddelivery.api.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionDTO extends RepresentationModel<PermissionDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "SEARCH_KITCHENS")
    private String name;
	
	@ApiModelProperty(example = "Permission to search kitchens")
    private String description;
	
}
