package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.StateNotFoundException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.repository.CityRepository;
import com.mycompany.fooddelivery.domain.service.CityRegistrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

// ---------- @Api associates with created Tag in SpringFoxConfig.apiDocket
@Api(tags = "Cities")
@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityDTOConverter cityDTOConverter;

	@Autowired
	private CityInputDeconverter cityInputDeconverter;

	// ---------- @ApiOperation - describes the endpoint in Swagger
	@ApiOperation("List all cities")
	@GetMapping
	public List<CityDTO> list() {
		List<City> cities = cityRepository.findAll();
		return cityDTOConverter.toCollectionModel(cities);
	}

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	@ApiOperation("Search a city by ID")
	@GetMapping("/{cityId}")
	public CityDTO search(@ApiParam(value = "City ID", example = "1") @PathVariable Long cityId) {
		City city = cityRegistrationService.searchOrFail(cityId);

		return cityDTOConverter.toModel(city);
	}

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	@ApiOperation("Register a new city")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new city") @RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityInputDeconverter.toDomainObject(cityInput);

			city = cityRegistrationService.save(city);

			return cityDTOConverter.toModel(city);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	@ApiOperation("Updates a city by ID")
	@PutMapping("/{cityId}")
	public CityDTO update(@ApiParam(value = "City ID", example = "1") @PathVariable Long cityId,
			@ApiParam(name = "body", value = "JSON representation of a city with new data") @RequestBody @Valid CityInput cityInput) {

		try {
			City currentCity = cityRegistrationService.searchOrFail(cityId);

			cityInputDeconverter.copyToDomainObject(cityInput, currentCity);

			currentCity = cityRegistrationService.save(currentCity);

			return cityDTOConverter.toModel(currentCity);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	@ApiOperation("Removes a city by ID")
	@DeleteMapping("/{cityId}")
	public void remove(@ApiParam(value = "City ID", example = "1") @PathVariable Long cityId) {
		cityRegistrationService.remove(cityId);
	}

}
