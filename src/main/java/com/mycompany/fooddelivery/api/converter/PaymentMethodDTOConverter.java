package com.mycompany.fooddelivery.api.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;

@Component
public class PaymentMethodDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PaymentMethodDTO toModel(PaymentMethod paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }
    
    // ---------- @Param collections accepts List and Set
    public List<PaymentMethodDTO> toCollectionModel(Collection<PaymentMethod> paymentMethods) {
        return paymentMethods.stream()
                .map(paymentMethod -> toModel(paymentMethod))
                .collect(Collectors.toList());
    }
	
}
