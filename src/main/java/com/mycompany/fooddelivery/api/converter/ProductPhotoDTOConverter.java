package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.ProductPhotoDTO;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;

@Component
public class ProductPhotoDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProductPhotoDTO toModel(ProductPhoto productPhoto) {
		return modelMapper.map(productPhoto, ProductPhotoDTO.class);
	}
	
}
