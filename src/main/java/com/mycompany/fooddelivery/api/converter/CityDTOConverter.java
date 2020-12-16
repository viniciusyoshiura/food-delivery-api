package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.CityDTO;
import com.mycompany.fooddelivery.domain.model.City;

@Component
public class CityDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public CityDTO toModel(City city) {
        return modelMapper.map(city, CityDTO.class);
    }
    
    public List<CityDTO> toCollectionModel(List<City> cities) {
        return cities.stream()
                .map(city -> toModel(city))
                .collect(Collectors.toList());
    }
	
}
