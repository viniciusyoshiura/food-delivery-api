package com.mycompany.fooddelivery.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.KitchenDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.KitchenInputDeconverter;
import com.mycompany.fooddelivery.api.model.KitchensXmlWrapper;
import com.mycompany.fooddelivery.api.model.dto.KitchenDTO;
import com.mycompany.fooddelivery.api.model.input.KitchenInput;
import com.mycompany.fooddelivery.api.openapi.controller.KitchenControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.repository.KitchenRepository;
import com.mycompany.fooddelivery.domain.service.KitchenRegistrationService;

@RestController
@RequestMapping(path = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi{

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;
	
	@Autowired
	private KitchenDTOConverter kitchenDTOConverter;

	@Autowired
	private KitchenInputDeconverter kitchenInputDeconverter;    
	
	@Autowired
	private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public PagedModel<KitchenDTO> list(@PageableDefault(size = 10) Pageable pageable) {
		Page<Kitchen> pageKitchens = kitchenRepository.findAll(pageable);
	    
		PagedModel<KitchenDTO> pagedModelKitchens = pagedResourcesAssembler.toModel(pageKitchens, kitchenDTOConverter);
		
		return pagedModelKitchens;
		
//		List<KitchenDTO> kitchenDTOs = kitchenDTOConverter.toCollectionModel(pageKitchens.getContent());
//		
//		Page<KitchenDTO> kitchenDTOsPage = new PageImpl<>(kitchenDTOs, pageable, pageKitchens.getTotalElements());
//		
//		return kitchenDTOsPage;
	}

	@GetMapping(value = "/{listXml}", produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper listXml() {
		return new KitchensXmlWrapper(kitchenRepository.findAll());
	}

//	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{kitchenId}")
	public KitchenDTO search(@PathVariable Long kitchenId) {

		Kitchen kitchen = kitchenRegistrationService.searchOrFail(kitchenId);
	    
	    return kitchenDTOConverter.toModel(kitchen);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenDTO insert(@RequestBody @Valid KitchenInput kitchenInput) {
		
		Kitchen kitchen = kitchenInputDeconverter.toDomainObject(kitchenInput);
		kitchen = kitchenRegistrationService.save(kitchen);
	    
	    return kitchenDTOConverter.toModel(kitchen);
	}

//	@PutMapping("/{kitchenId}")
//	public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
//		
//		Optional<Kitchen> currentKitchen = kitchenRepository.findById(kitchenId);
//
//		if (currentKitchen.isPresent()) {
//			// Copy right object values to left object
//			// Ignore copy from attribute id
//			BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id");
//
//			Kitchen savedKitchen = kitchenRegistrationService.save(currentKitchen.get());
//			return ResponseEntity.ok(savedKitchen);
//		}
//
//		return ResponseEntity.notFound().build();
//	}
	
	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
		
		Kitchen currentKitchen = kitchenRegistrationService.searchOrFail(kitchenId);
		
		kitchenInputDeconverter.copyToDomainObject(kitchenInput, currentKitchen);
		currentKitchen = kitchenRegistrationService.save(currentKitchen);
	    
	    return kitchenDTOConverter.toModel(currentKitchen);
	}

//	@DeleteMapping("/{kitchenId}")
//	public ResponseEntity<Kitchen> remove(@PathVariable Long kitchenId) {
//		try {
//			kitchenRegistrationService.remove(kitchenId);	
//			return ResponseEntity.noContent().build();
//			
//		} catch (EntityNotFoundException e) {
//			return ResponseEntity.notFound().build();
//			
//		} catch (EntityInUseException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}

	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long kitchenId) {
		kitchenRegistrationService.remove(kitchenId);
	}

}
