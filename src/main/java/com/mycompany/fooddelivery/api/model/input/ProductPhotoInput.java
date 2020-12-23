package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.fooddelivery.core.validation.FileContentType;
import com.mycompany.fooddelivery.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoInput {
	
	@NotNull
	@FileSize(max = "5000KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile file;
	
	@NotBlank
	private String description;
	
}
