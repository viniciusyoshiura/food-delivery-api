package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.CityDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.CityInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.CityDTO;
import com.mycompany.fooddelivery.api.model.input.CityInput;
import com.mycompany.fooddelivery.api.openapi.controller.CityControllerOpenApi;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.StateNotFoundException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.repository.CityRepository;
import com.mycompany.fooddelivery.domain.service.CityRegistrationService;


@RestController
@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi{

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityDTOConverter cityDTOConverter;

	@Autowired
	private CityInputDeconverter cityInputDeconverter;

	@GetMapping
	public List<CityDTO> list() {
		List<City> cities = cityRepository.findAll();
		return cityDTOConverter.toCollectionModel(cities);
	}

	@GetMapping("/{cityId}")
	public CityDTO search(@PathVariable Long cityId) {
		City city = cityRegistrationService.searchOrFail(cityId);

		return cityDTOConverter.toModel(city);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityDTO insert(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityInputDeconverter.toDomainObject(cityInput);

			city = cityRegistrationService.save(city);

			return cityDTOConverter.toModel(city);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cityId}")
	public CityDTO update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {

		try {
			City currentCity = cityRegistrationService.searchOrFail(cityId);

			cityInputDeconverter.copyToDomainObject(cityInput, currentCity);

			currentCity = cityRegistrationService.save(currentCity);

			return cityDTOConverter.toModel(currentCity);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cityId}")
	public void remove(@PathVariable Long cityId) {
		cityRegistrationService.remove(cityId);
	}

}
