package com.mycompany.fooddelivery.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.fooddelivery.api.converter.ProductPhotoDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.ProductPhotoDTO;
import com.mycompany.fooddelivery.api.model.input.ProductPhotoInput;
import com.mycompany.fooddelivery.api.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import com.mycompany.fooddelivery.domain.exception.EntityNotFoundException;
import com.mycompany.fooddelivery.domain.model.Product;
import com.mycompany.fooddelivery.domain.model.ProductPhoto;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService;
import com.mycompany.fooddelivery.domain.service.PhotoStorageService.RetrievedPhoto;
import com.mycompany.fooddelivery.domain.service.ProductPhotoService;
import com.mycompany.fooddelivery.domain.service.ProductRegistrationService;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/products/{productId}/photo", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi{

	@Autowired
	private ProductRegistrationService productRegistrationService;

	@Autowired
	private ProductPhotoService productPhotoService;

	@Autowired
	private ProductPhotoDTOConverter productPhotoDTOConverter;

	@Autowired
	private PhotoStorageService photoStorageService;
	
	
	// ---------- @RequestPart - used in Swagger
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoDTO updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid ProductPhotoInput productPhotoInput, @RequestPart(required = true) MultipartFile file) throws IOException {

		Product product = productRegistrationService.searchOrFail(restaurantId, productId);

//		MultipartFile file = productPhotoInput.getFile();

		ProductPhoto productPhoto = new ProductPhoto();
		productPhoto.setProduct(product);
		productPhoto.setDescription(productPhotoInput.getDescription());
		productPhoto.setContentType(file.getContentType());
		productPhoto.setSize(file.getSize());
		productPhoto.setFileName(file.getOriginalFilename());

		ProductPhoto productPhotoSaved = productPhotoService.save(productPhoto, file.getInputStream());

		return productPhotoDTOConverter.toModel(productPhotoSaved);

	}

	@GetMapping
	public ProductPhotoDTO search(@PathVariable Long restaurantId, @PathVariable Long productId) {
		ProductPhoto productPhoto = productPhotoService.searchOrFail(restaurantId, productId);

		return productPhotoDTOConverter.toModel(productPhoto);
	}

	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> getPhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			ProductPhoto productPhoto = productPhotoService.searchOrFail(restaurantId, productId);

			MediaType photoMediaType = MediaType.parseMediaType(productPhoto.getContentType());
			List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);

			checkMediaTypeCompatibility(photoMediaType, acceptedMediaTypes);

			RetrievedPhoto retrievedPhoto = photoStorageService.retrieve(productPhoto.getFileName());
			
			if (retrievedPhoto.hasUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, retrievedPhoto.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(photoMediaType)
						.body(new InputStreamResource(retrievedPhoto.getInputStream()));
			}
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long restaurantId, @PathVariable Long productId) {
		productPhotoService.remove(restaurantId, productId);
	}  

	private void checkMediaTypeCompatibility(MediaType photoMediaType, List<MediaType> acceptedMediaTypes)
			throws HttpMediaTypeNotAcceptableException {

		boolean compatible = acceptedMediaTypes.stream()
				.anyMatch(acceptedMediaType -> acceptedMediaType.isCompatibleWith(photoMediaType));

		if (!compatible) {
			throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
		}
	}

}
