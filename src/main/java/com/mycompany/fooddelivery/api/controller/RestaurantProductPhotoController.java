package com.mycompany.fooddelivery.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.fooddelivery.api.converter.ProductPhotoDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.ProductPhotoDTO;
import com.mycompany.fooddelivery.api.model.input.ProductPhotoInput;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.service.ProductPhotoService;
import com.mycompany.fooddelivery.domain.service.ProductRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@Autowired
	private ProductPhotoService productPhotoService;
	
	@Autowired
	private ProductPhotoDTOConverter productPhotoDTOConverter;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoDTO updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid ProductPhotoInput productPhotoInput) {

		Product product = productRegistrationService.searchOrFail(restaurantId, productId);

		MultipartFile file = productPhotoInput.getFile();

		ProductPhoto productPhoto = new ProductPhoto();
		productPhoto.setProduct(product);
		productPhoto.setDescription(productPhotoInput.getDescription());
		productPhoto.setContentType(file.getContentType());
		productPhoto.setSize(file.getSize());
		productPhoto.setFileName(file.getOriginalFilename());

		ProductPhoto productPhotoSaved = productPhotoService.save(productPhoto);

		return productPhotoDTOConverter.toModel(productPhotoSaved);

	}

}
