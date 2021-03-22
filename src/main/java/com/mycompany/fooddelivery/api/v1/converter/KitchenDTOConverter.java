package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.KitchenController;
import com.mycompany.fooddelivery.api.v1.model.dto.KitchenDTO;
import com.mycompany.fooddelivery.domain.model.Kitchen;

@Component
public class KitchenDTOConverter extends RepresentationModelAssemblerSupport<Kitchen, KitchenDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public KitchenDTOConverter() {
		super(KitchenController.class, KitchenDTO.class);
	}
	
    public KitchenDTO toModel(Kitchen kitchen) {
    	KitchenDTO kitchenDTO = createModelWithId(kitchen.getId(), kitchen);
		modelMapper.map(kitchen, kitchenDTO);
		
		kitchenDTO.add(hateoasLinks.linkToKitchens("kitchens"));
		
		return kitchenDTO;
    }
    
//    public CollectionModel<KitchenDTO> toCollectionModel(List<Kitchen> kitchens) {
//        return kitchens.stream()
//                .map(kitchen -> toModel(kitchen))
//                .collect(Collectors.toList());
//    }  
	
}
