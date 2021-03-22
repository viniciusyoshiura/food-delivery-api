package com.mycompany.fooddelivery.api.v1.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.CityInput;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.State;

@Component
public class CityInputDeconverter {

	@Autowired
	private ModelMapper modelMapper;

	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInput cidadeInput, City city) {
		// ---------- In order to avoid org.hibernate.HibernateException: identifier of an instance of
		// ---------- com.mycompany.fooddelivery.domain.model.State was altered from 1 to 2
		city.setState(new State());

		modelMapper.map(cidadeInput, city);
	}

}
