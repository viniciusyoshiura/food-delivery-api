package com.mycompany.fooddelivery.api.v1.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.ProductInput;
import com.mycompany.fooddelivery.domain.model.Product;

@Component
public class ProductInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public Product toDomainObject(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }
    
    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }  
	
}
