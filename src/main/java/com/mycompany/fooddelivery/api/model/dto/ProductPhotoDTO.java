package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPhotoDTO {
	
	@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
	private String fileName;
	
	@ApiModelProperty(example = "Prime Rib")
	private String description;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "202912")
	private Long size;
	
}
