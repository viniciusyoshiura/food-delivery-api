package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// ---------- Class that represents response of a pagination
@Getter
@Setter
public class PagedModelOpenApi<T> {

	private List<T> content;

	@ApiModelProperty(example = "10", value = "Total elemenst by page")
	private Long size;

	@ApiModelProperty(example = "50", value = "Total elements")
	private Long totalElements;

	@ApiModelProperty(example = "5", value = "Total pages")
	private Long totalPages;

	@ApiModelProperty(example = "0", value = "Page number")
	private Long number;

}
