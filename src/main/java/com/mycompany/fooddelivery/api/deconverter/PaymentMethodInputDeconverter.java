package com.mycompany.fooddelivery.api.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.input.PaymentMethodInput;
import com.mycompany.fooddelivery.domain.model.PaymentMethod;

@Component
public class PaymentMethodInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PaymentMethod toDomainObject(PaymentMethodInput paymentMethodInput) {
        return modelMapper.map(paymentMethodInput, PaymentMethod.class);
    }
    
    public void copyToDomainObject(PaymentMethodInput paymentMethodInput, PaymentMethod paymentMethod) {
        modelMapper.map(paymentMethodInput, paymentMethod);
    } 
	
}
