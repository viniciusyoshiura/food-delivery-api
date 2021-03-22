package com.mycompany.fooddelivery.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.converter.PurchaseOrderDTOConverter;
import com.mycompany.fooddelivery.api.v1.converter.PurchaseOrderSummaryDTOConverter;
import com.mycompany.fooddelivery.api.v1.deconverter.PurchaseOrderInputDeconverter;
import com.mycompany.fooddelivery.api.v1.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.api.v1.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.api.v1.model.input.PurchaseOrderInput;
import com.mycompany.fooddelivery.api.v1.openapi.controller.PurchaseOrderControllerOpenApi;
import com.mycompany.fooddelivery.core.data.PageWrapper;
import com.mycompany.fooddelivery.core.data.PageableTranslator;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.EntityNotFoundException;
import com.mycompany.fooddelivery.domain.filter.PurchaseOrderFilter;
import com.mycompany.fooddelivery.domain.infrastructure.specs.PurchaseOrderSpecs;
import com.mycompany.fooddelivery.domain.model.PurchaseOrder;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.PurchaseOrderRepository;
import com.mycompany.fooddelivery.domain.service.PurchaseOrderIssuanceService;

@RestController
@RequestMapping(path = "/v1/purchase-orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderController implements PurchaseOrderControllerOpenApi {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private PurchaseOrderIssuanceService purchaseOrderIssuanceService;

	@Autowired
	private PurchaseOrderDTOConverter purchaseOrderDTOConverter;

	@Autowired
	private PurchaseOrderSummaryDTOConverter purchaseOrderSummaryDTOConverter;

	@Autowired
	private PurchaseOrderInputDeconverter purchaseOrderInputDeconverter;
	
	@Autowired
	private PagedResourcesAssembler<PurchaseOrder> pagedResourcesAssembler;
	
	// ---------- See SquigglyConfig.squigglyRequestFilter, which limits the JSON return fields
	// ---------- See PageJsonSerializer, which sets the Pageable return object
	// ---------- PurchaseOrderFilter gets the parameter filters
	// ---------- Pageable includes pagination parameters
	@GetMapping
	public PagedModel<PurchaseOrderSummaryDTO> listWithFilters(PurchaseOrderFilter filter,
			@PageableDefault(size = 10) Pageable pageable) {
		
		// ---------- Convert Pageable of parameter to a domain Pageable
		// ---------- e.g. map -> sort = userName to user.name
		Pageable translatedPageable = convertPageable(pageable);
		
		// ---------- PurchaseOrderSpecs applies filters in query
		Page<PurchaseOrder> purchaseOrdersPage = purchaseOrderRepository.findAll(PurchaseOrderSpecs.usingFilter(filter),
				translatedPageable);
		
		purchaseOrdersPage = new PageWrapper<>(purchaseOrdersPage, pageable);
		
		return pagedResourcesAssembler.toModel(purchaseOrdersPage, purchaseOrderSummaryDTOConverter);
		
//		List<PurchaseOrderSummaryDTO> purchaseOrderSummaryDTOs = purchaseOrderSummaryDTOConverter
//				.toCollectionModel(purchaseOrdersPage.getContent());
//		
//		// ---------- Instantiate new Page object in order to return
//		Page<PurchaseOrderSummaryDTO> purchaseOrderSummaryDTOPage = new PageImpl<>(purchaseOrderSummaryDTOs, pageable,
//				purchaseOrdersPage.getTotalElements());
//
//		return purchaseOrderSummaryDTOPage;

	}
	
	@GetMapping("/{purchaseOrderUuid}")
	public PurchaseOrderDTO search(@PathVariable String purchaseOrderUuid) {
		PurchaseOrder purchaseOrder = purchaseOrderIssuanceService.searchOrFail(purchaseOrderUuid);

		return purchaseOrderDTOConverter.toModel(purchaseOrder);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PurchaseOrderDTO insert(@Valid @RequestBody PurchaseOrderInput purchaseOrderInput) {
		try {
			PurchaseOrder newPurchaseOrder = purchaseOrderInputDeconverter.toDomainObject(purchaseOrderInput);

			// TODO get user authenticated
			newPurchaseOrder.setUser(new User());
			newPurchaseOrder.getUser().setId(1L);

			newPurchaseOrder = purchaseOrderIssuanceService.save(newPurchaseOrder);

			return purchaseOrderDTOConverter.toModel(newPurchaseOrder);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	// ---------- Convert Pageable of input to a domain Pageable
	private Pageable convertPageable(Pageable pageable) {
		var mapping = Map.of(
				"uuid", "uuid",
				"restaurantName", "restaurant.name",
				"userName", "user.name",
				"totalValue", "totalValue",
				"stauts", "status",
				"dateRegister", "dateRegister",
				"shippingFee", "shippingFee"
			);
		
		return PageableTranslator.convert(pageable, mapping);
	}
	
}
