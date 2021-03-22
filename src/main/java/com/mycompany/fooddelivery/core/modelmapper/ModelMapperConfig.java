package com.mycompany.fooddelivery.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mycompany.fooddelivery.api.v1.model.dto.AddressDTO;
import com.mycompany.fooddelivery.api.v1.model.input.ItemPurchaseOrderInput;
import com.mycompany.fooddelivery.domain.model.Address;
import com.mycompany.fooddelivery.domain.model.ItemPurchaseOrder;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		var addressToAddressDTOTypeMap = modelMapper.createTypeMap(
				Address.class, AddressDTO.class);
		
		addressToAddressDTOTypeMap.<String>addMapping(
				addressSrc -> addressSrc.getCity().getState().getName(),
				(addressDTODest, value) -> addressDTODest.getCity().setState(value));
		
		// ---------- Skip ItemPurchaseOrder setId
		modelMapper.createTypeMap(ItemPurchaseOrderInput.class, ItemPurchaseOrder.class)
	    .addMappings(mapper -> mapper.skip(ItemPurchaseOrder::setId)); 
		
		return modelMapper;
	}
	
	
}
