package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableDTOOpenApi {

	@ApiModelProperty(example = "0", value = "Page number (starts with 0)")
	private int page;
	
	@ApiModelProperty(example = "10", value = "Total elements by page")
	private int size;
	
	@ApiModelProperty(example = "name,asc", value = "Property name for ordering")
	private List<String> sort;
	
}
