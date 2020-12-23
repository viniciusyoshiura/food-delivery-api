package com.mycompany.fooddelivery.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPhotoDTO {

	private String fileName;
	private String description;
	private String contentType;
	private Long size;
	
}
