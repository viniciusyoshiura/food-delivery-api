package com.mycompany.fooddelivery.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.mycompany.fooddelivery.api.converter.PaymentMethodDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.PaymentMethodInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.api.model.input.PaymentMethodInput;
import com.mycompany.fooddelivery.api.openapi.controller.PaymentMethodControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;
import com.mycompany.fooddelivery.domain.repository.PaymentMethodRepository;
import com.mycompany.fooddelivery.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping(path = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi{

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@Autowired
	private PaymentMethodDTOConverter paymentMethodDTOConverter;

	@Autowired
	private PaymentMethodInputDeconverter paymentMethodInputDeconverter;

	@GetMapping
	public ResponseEntity<List<PaymentMethodDTO>> list(ServletWebRequest request) {
		
		// ---------- Disabling shallowEtag in order to use Deep ETag (see WebConfig)
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime lastDateUpdate = paymentMethodRepository.getLastDateUpdate();
		
		if (lastDateUpdate != null) {
			// ---------- toEpochSecond - converts offsetDateTime to seconds
			eTag = String.valueOf(lastDateUpdate.toEpochSecond());
		}
		
		// ---------- checkNotModified compares the request header If-None-Match with eTag 
		if (request.checkNotModified(eTag)) {
			// ---------- If-Non-Match and Etag are equal, than return null
			return null;
		}
		
		
		List<PaymentMethod> allPaymentMethods = paymentMethodRepository.findAll();
//
//		return paymentMethodDTOConverter.toCollectionModel(allPaymentMethods);
//		
		List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodDTOConverter
				.toCollectionModel(allPaymentMethods);
		
		return ResponseEntity.ok()
				// ---------- MaxAge sets the max age of cache
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				
				// ---------- noCache: validation request of cache (ETag) is mandatory, as the ETag is stale (expired)
				// ---------- OBSERVATION: to force the request directly from server,use the request header 'Cache-control': 'no-cache'
//				.cacheControl(CacheControl.noCache())
				// ---------- noStore: there is no cache
//				.cacheControl(CacheControl.noStore())
				.eTag(eTag)
				.body(paymentMethodDTOs);
		
	}

	@GetMapping("/{paymentMethodId}")
	public ResponseEntity<PaymentMethodDTO> search(@PathVariable Long paymentMethodId, ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
	    
	    String eTag = "0";
	    
	    OffsetDateTime dateUpdate = paymentMethodRepository.getDateUpdateById(paymentMethodId);
	    
	    if (dateUpdate != null) {
	        eTag = String.valueOf(dateUpdate.toEpochSecond());
	    }
	    
	    if (request.checkNotModified(eTag)) {
	        return null;
	    }
		
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);
		  
		PaymentMethodDTO paymentMethodDTO =  paymentMethodDTOConverter.toModel(paymentMethod);
		  
		return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .eTag(eTag)
			.body(paymentMethodDTO);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodDTO insert(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod paymentMethod = paymentMethodInputDeconverter.toDomainObject(paymentMethodInput);

		paymentMethod = paymentMethodRegistrationService.save(paymentMethod);

		return paymentMethodDTOConverter.toModel(paymentMethod);
	}

	@PutMapping("/{paymentMethodId}")
	public PaymentMethodDTO update(@PathVariable Long paymentMethodId,
			@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		paymentMethodInputDeconverter.copyToDomainObject(paymentMethodInput, currentPaymentMethod);

		currentPaymentMethod = paymentMethodRegistrationService.save(currentPaymentMethod);

		return paymentMethodDTOConverter.toModel(currentPaymentMethod);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long paymentMethodId) {
		paymentMethodRegistrationService.remove(paymentMethodId);
	}

}
