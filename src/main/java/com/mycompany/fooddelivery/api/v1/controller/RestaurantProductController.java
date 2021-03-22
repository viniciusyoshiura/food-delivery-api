package com.mycompany.fooddelivery.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.converter.ProductDTOConverter;
import com.mycompany.fooddelivery.api.v1.deconverter.ProductInputDeconverter;
import com.mycompany.fooddelivery.api.v1.model.dto.ProductDTO;
import com.mycompany.fooddelivery.api.v1.model.input.ProductInput;
import com.mycompany.fooddelivery.api.v1.openapi.controller.RestaurantProductControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.repository.ProductRepository;
import com.mycompany.fooddelivery.domain.service.ProductRegistrationService;
import com.mycompany.fooddelivery.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerOpenApi{

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

	@Autowired
	private HateoasLinks hateoasLinks;
	
	@GetMapping
	public CollectionModel<ProductDTO> list(@PathVariable Long restaurantId,
			@RequestParam(required = false) Boolean includeInactives) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		List<Product> allProducts = null;
		
		if (includeInactives == null || includeInactives) {
			allProducts = productRepository.findByRestaurant(restaurant);
		} else {
			allProducts = productRepository.findActivesByRestaurant(restaurant);
		}

		return productDTOConverter.toCollectionModel(allProducts)
	            .add(hateoasLinks.linkToProducts(restaurantId));
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
