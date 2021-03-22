package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.PaymentMethodController;
import com.mycompany.fooddelivery.api.v1.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;

@Component
public class PaymentMethodDTOConverter extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
    private HateoasLinks hateoasLinks;
	
	public PaymentMethodDTOConverter() {
        super(PaymentMethodController.class, PaymentMethodDTO.class);
    }
	
    public PaymentMethodDTO toModel(PaymentMethod paymentMethod) {
    	
    	PaymentMethodDTO paymentMethodDTO = 
                createModelWithId(paymentMethod.getId(), paymentMethod);
        
        modelMapper.map(paymentMethod, paymentMethodDTO);
        
        paymentMethodDTO.add(hateoasLinks.linkToPaymentMethods("payment-methods"));
        
        return paymentMethodDTO;
    }
    
    // ---------- @Param collections accepts List and Set
    public CollectionModel<PaymentMethodDTO> toCollectionModel(Iterable<? extends PaymentMethod> entities) {
    	
    	return super.toCollectionModel(entities)
                .add(hateoasLinks.linkToPaymentMethods());
    	
//        return paymentMethods.stream()
//                .map(paymentMethod -> toModel(paymentMethod))
//                .collect(Collectors.toList());
    }
	
}
