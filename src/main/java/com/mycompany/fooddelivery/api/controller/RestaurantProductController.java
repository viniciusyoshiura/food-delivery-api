package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.ProductDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.ProductInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.ProductDTO;
import com.mycompany.fooddelivery.api.model.input.ProductInput;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.repository.ProductRepository;
import com.mycompany.fooddelivery.domain.service.ProductRegistrationService;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductRegistrationService productRegistrationService;

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private ProductDTOConverter productDTOConverter;

	@Autowired
	private ProductInputDeconverter productInputDeconverter;

	@GetMapping
	public List<ProductDTO> list(@PathVariable Long restaurantId,
			@RequestParam(required = false) boolean includeInactives) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		List<Product> allProducts = null;
		
		if (includeInactives) {
			allProducts = productRepository.findByRestaurant(restaurant);
		} else {
			allProducts = productRepository.findActivesByRestaurant(restaurant);
		}

		return productDTOConverter.toCollectionModel(allProducts);
	}

	@GetMapping("/{productId}")
	public ProductDTO search(@PathVariable Long restaurantId, @PathVariable Long productId) {
		Product product = productRegistrationService.searchOrFail(restaurantId, productId);

		return productDTOConverter.toModel(product);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO insert(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		Product product = productInputDeconverter.toDomainObject(productInput);
		product.setRestaurant(restaurant);

		product = productRegistrationService.save(product);

		return productDTOConverter.toModel(product);
	}

	@PutMapping("/{productId}")
	public ProductDTO update(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestBody @Valid ProductInput productInput) {
		Product currentProduct = productRegistrationService.searchOrFail(restaurantId, productId);

		productInputDeconverter.copyToDomainObject(productInput, currentProduct);

		currentProduct = productRegistrationService.save(currentProduct);

		return productDTOConverter.toModel(currentProduct);
	}

}
