package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "SEARCH_KITCHENS")
    private String name;
	
	@ApiModelProperty(example = "Permission to search kitchens")
    private String description;
	
}
