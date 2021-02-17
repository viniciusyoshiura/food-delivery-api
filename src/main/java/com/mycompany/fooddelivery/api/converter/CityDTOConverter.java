package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.CityController;
import com.mycompany.fooddelivery.api.model.dto.CityDTO;
import com.mycompany.fooddelivery.domain.model.City;

@Component
public class CityDTOConverter extends RepresentationModelAssemblerSupport<City, CityDTO> {
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public CityDTOConverter() {
		super(CityController.class, CityDTO.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CityDTO toModel(City city) {
		// ---------- createModelWithId - creates link with self
		CityDTO cityDTO = createModelWithId(city.getId(), city);

		modelMapper.map(city, cityDTO);
		
		// ---------- Creating links from class method (hateoas)
		cityDTO.add(hateoasLinks.linkToCities("cities"));

		cityDTO.getState()
				.add(hateoasLinks.linkToState(cityDTO.getState().getId()));

		return cityDTO;

	}

	@Override
	public CollectionModel<CityDTO> toCollectionModel(Iterable<? extends City> entities) {
		return super.toCollectionModel(entities).add(hateoasLinks.linkToCities());
	}

//    public List<CityDTO> toCollectionModel(List<City> cities) {
//        return cities.stream()
//                .map(city -> toModel(city))
//                .collect(Collectors.toList());
//    }

}
