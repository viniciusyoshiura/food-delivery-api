package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.RestaurantProductController;
import com.mycompany.fooddelivery.api.v1.model.dto.ProductDTO;
import com.mycompany.fooddelivery.domain.model.Product;

@Component
public class ProductDTOConverter extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public ProductDTOConverter() {
        super(RestaurantProductController.class, ProductDTO.class);
    }
	
    public ProductDTO toModel(Product product) {
//        return modelMapper.map(product, ProductDTO.class);
        
        ProductDTO productDTO = createModelWithId(
                product.getId(), product, product.getRestaurant().getId());
        modelMapper.map(product, productDTO);
        
        productDTO.add(hateoasLinks.linkToProducts(product.getRestaurant().getId(), "products"));
        productDTO.add(hateoasLinks.linkToProductPhoto(
        		product.getRestaurant().getId(), product.getId(), "photo"));
        
        return productDTO;
    }
    
//    public List<ProductDTO> toCollectionModel(List<Product> products) {
//        return products.stream()
//                .map(product -> toModel(product))
//                .collect(Collectors.toList());
//    }  
	
}
