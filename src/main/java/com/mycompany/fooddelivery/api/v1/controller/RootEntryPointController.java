package com.mycompany.fooddelivery.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path="/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private HateoasLinks hateoasLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(hateoasLinks.linkToKitchens("kitchens"));
		rootEntryPointModel.add(hateoasLinks.linkToPurchaseOrders("orders"));
		rootEntryPointModel.add(hateoasLinks.linkToRestaurants("restaurants"));
		rootEntryPointModel.add(hateoasLinks.linkToGroupinges("groups"));
		rootEntryPointModel.add(hateoasLinks.linkToUsers("users"));
		rootEntryPointModel.add(hateoasLinks.linkToPermissions("permissions"));
		rootEntryPointModel.add(hateoasLinks.linkToPaymentMethods("payment-methods"));
		rootEntryPointModel.add(hateoasLinks.linkToStates("states"));
		rootEntryPointModel.add(hateoasLinks.linkToCities("cities"));
		rootEntryPointModel.add(hateoasLinks.linkToStatistics("statistics"));
		
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}
