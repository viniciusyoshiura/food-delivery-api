package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.ProductDTO;
import com.mycompany.fooddelivery.domain.model.Product;

@Component
public class ProductDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public ProductDTO toModel(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
    
    public List<ProductDTO> toCollectionModel(List<Product> products) {
        return products.stream()
                .map(product -> toModel(product))
                .collect(Collectors.toList());
    }  
	
}
