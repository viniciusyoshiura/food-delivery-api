package com.mycompany.fooddelivery.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageDTO")
@Getter
@Setter
public class PageDTOOpenApi {
	
	@ApiModelProperty(example = "10", value = "Number of registers per page")
	private Long size;
	
	@ApiModelProperty(example = "50", value = "Total registers")
	private Long totalElements;
	
	@ApiModelProperty(example = "5", value = "Total pages")
	private Long totalPages;
	
	@ApiModelProperty(example = "0", value = "Page number (starts with 0)")
	private Long number;

}
