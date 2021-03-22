package com.mycompany.fooddelivery.api.v1.converter;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.RestaurantProductPhotoController;
import com.mycompany.fooddelivery.api.v1.model.dto.ProductPhotoDTO;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;

@Component
public class ProductPhotoDTOConverter extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public ProductPhotoDTOConverter() {
		super(RestaurantProductPhotoController.class, ProductPhotoDTO.class);
	}
	
	public ProductPhotoDTO toModel(ProductPhoto productPhoto) {
		
		ProductPhotoDTO productPhotoDTO = modelMapper.map(productPhoto, ProductPhotoDTO.class);
        
		productPhotoDTO.add(hateoasLinks.linkToProductPhoto(
				productPhoto.getRestaurantId(), productPhoto.getProduct().getId()));
        
		productPhotoDTO.add(hateoasLinks.linkToProduct(
				productPhoto.getRestaurantId(), productPhoto.getProduct().getId(), "product"));
        
        return productPhotoDTO;
		
		//		return modelMapper.map(productPhoto, ProductPhotoDTO.class);
	}
	
}
