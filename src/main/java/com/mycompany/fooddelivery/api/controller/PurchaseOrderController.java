package com.mycompany.fooddelivery.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.PurchaseOrderDTOConverter;
import com.mycompany.fooddelivery.api.converter.PurchaseOrderSummaryDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.PurchaseOrderInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.api.model.input.PurchaseOrderInput;
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
@RequestMapping(value = "/purchase-orders")
public class PurchaseOrderController {

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
	
	// ---------- See SquigglyConfig.squigglyRequestFilter, which limits the JSON return fields
	// ---------- See PageJsonSerializer, which sets the Pageable return object
	// ---------- PurchaseOrderFilter gets the parameter filters
	// ---------- Pageable includes pagination parameters
	@GetMapping
	public Page<PurchaseOrderSummaryDTO> listWithFilters(PurchaseOrderFilter filter,
			@PageableDefault(size = 10) Pageable pageable) {
		
		// ---------- Convert Pageable of parameter to a domain Pageable
		// ---------- e.g. map -> sort = userName to user.name
		pageable = convertPageable(pageable);
		
		// ---------- PurchaseOrderSpecs applies filters in query
		Page<PurchaseOrder> purchaseOrdersPage = purchaseOrderRepository.findAll(PurchaseOrderSpecs.usingFilter(filter),
				pageable);

		List<PurchaseOrderSummaryDTO> purchaseOrderSummaryDTOs = purchaseOrderSummaryDTOConverter
				.toCollectionModel(purchaseOrdersPage.getContent());
		
		// ---------- Instantiate new Page object in order to return
		Page<PurchaseOrderSummaryDTO> purchaseOrderSummaryDTOPage = new PageImpl<>(purchaseOrderSummaryDTOs, pageable,
				purchaseOrdersPage.getTotalElements());

		return purchaseOrderSummaryDTOPage;

	}

//    @GetMapping
//	public MappingJacksonValue list(@RequestParam(required = false) String fields) {
//		List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
//		List<PurchaseOrderSummaryDTO> purchaseOrderDTOs = purchaseOrderSummaryDTOConverter.toCollectionModel(purchaseOrders);  
//		
//		MappingJacksonValue purchaseOrdersWrapper = new MappingJacksonValue(purchaseOrderDTOs);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("filterPurchaseOrder", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(fields)) {
//			filterProvider.addFilter("filterPurchaseOrder", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//		}
//		
//		purchaseOrdersWrapper.setFilters(filterProvider);
//		
//		return purchaseOrdersWrapper;
//	}

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
				"restaurant.name", "restaurant.name",
				"userName", "user.name",
				"totalValue", "totalValue",
				"stauts", "status",
				"dateRegister", "dateRegister",
				"shippingFee", "shippingFee"
			);
		
		return PageableTranslator.convert(pageable, mapping);
	}
	
}
