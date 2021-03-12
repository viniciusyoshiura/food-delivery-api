package com.mycompany.fooddelivery.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.controller.CityController;
import com.mycompany.fooddelivery.api.controller.GroupingeController;
import com.mycompany.fooddelivery.api.controller.GroupingePermissionController;
import com.mycompany.fooddelivery.api.controller.KitchenController;
import com.mycompany.fooddelivery.api.controller.PaymentMethodController;
import com.mycompany.fooddelivery.api.controller.PermissionController;
import com.mycompany.fooddelivery.api.controller.PurchaseOrderController;
import com.mycompany.fooddelivery.api.controller.PurchaseOrderStatusFlowController;
import com.mycompany.fooddelivery.api.controller.RestaurantController;
import com.mycompany.fooddelivery.api.controller.RestaurantPaymentMethodController;
import com.mycompany.fooddelivery.api.controller.RestaurantProductController;
import com.mycompany.fooddelivery.api.controller.RestaurantProductPhotoController;
import com.mycompany.fooddelivery.api.controller.RestaurantResponsibleUserController;
import com.mycompany.fooddelivery.api.controller.StateController;
import com.mycompany.fooddelivery.api.controller.StatisticsController;
import com.mycompany.fooddelivery.api.controller.UserController;
import com.mycompany.fooddelivery.api.controller.UserGroupingeController;

// ---------- Class to handle Hateoas links
@Component
public class HateoasLinks {

	public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables FIELDS_VARIABLES = new TemplateVariables(
			new TemplateVariable("fields", VariableType.REQUEST_PARAM));     
	
	public Link linkToPurchaseOrders(String rel) {
		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("clientId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataRegisterStart", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataRegisterEnd", VariableType.REQUEST_PARAM));
		
		String urlPurchaseOrders = linkTo(PurchaseOrderController.class).toUri().toString();
		
		return new Link(UriTemplate.of(urlPurchaseOrders, 
				PAGE_VARIABLES.concat(filterVariables)), rel);
	}
	
	public Link linkToPurchaseOrderConfirmation(String purchaseOrderUuid, String rel) {
		return linkTo(methodOn(PurchaseOrderStatusFlowController.class)
				.confirm(purchaseOrderUuid)).withRel(rel);
	}
	
	public Link linkToPurchaseOrderDelivery(String purchaseOrderUuid, String rel) {
		return linkTo(methodOn(PurchaseOrderStatusFlowController.class)
				.deliver(purchaseOrderUuid)).withRel(rel);
	}
	
	public Link linkToPurchaseOrderCancellation(String purchaseOrderUuid, String rel) {
		return linkTo(methodOn(PurchaseOrderStatusFlowController.class)
				.cancel(purchaseOrderUuid)).withRel(rel);
	}
	
	public Link linkToRestaurant(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .search(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurant(Long restaurantId) {
	    return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurants(String rel) {
		String restaurantsUrl = linkTo(RestaurantController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(restaurantsUrl, FIELDS_VARIABLES), rel);
	}

	public Link linkToRestaurants() {
	    return linkToRestaurants(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantPaymentMethod(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantPaymentMethodController.class)
	            .list(restaurantId)).withRel(rel);
	}
	
	public Link linkToRestaurantPaymentMethod(Long restaurantId) {
	    return linkToRestaurantPaymentMethod(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantPaymentMethodDisassociation(
			Long restaurantId, Long paymentMethodId, String rel) {
		
		return linkTo(methodOn(RestaurantPaymentMethodController.class)
				.disassociate(restaurantId, paymentMethodId)).withRel(rel);
	}
	
	public Link linkToRestaurantPaymentMethodAssociation(
			Long restaurantId, String rel) {
		
		return linkTo(methodOn(RestaurantPaymentMethodController.class)
				.associate(restaurantId, null)).withRel(rel);
	}

	public Link linkToRestaurantResponsibleDisassociation(
		Long restaurantId, Long userId, String rel) {

	    return linkTo(methodOn(RestaurantResponsibleUserController.class)
	            .disassociate(restaurantId, userId)).withRel(rel);
	}

	public Link linkToRestaurantResponsibleAssociation(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantResponsibleUserController.class)
	            .associate(restaurantId, null)).withRel(rel);
	}
	
	public Link linkToRestaurantResponsibleUser(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantResponsibleUserController.class)
	            .list(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurantResponsibleUser(Long restaurantId) {
	    return linkToRestaurantResponsibleUser(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantOpenning(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .open(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurantClosure(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .close(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurantDeactivation(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .deactivate(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurantActivation(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .activate(restaurantId)).withRel(rel);
	}
	
	public Link linkToKitchen(Long kitchenId, String rel) {
	    return linkTo(methodOn(KitchenController.class)
	            .search(kitchenId)).withRel(rel);
	}

	public Link linkToKitchen(Long kitchenId) {
	    return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
	}  
	
	public Link linkToUser(Long userId, String rel) {
	    return linkTo(methodOn(UserController.class)
	            .search(userId)).withRel(rel);
	}

	public Link linkToUser(Long userId) {
	    return linkToUser(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsers(String rel) {
	    return linkTo(UserController.class).withRel(rel);
	}

	public Link linkToUsers() {
	    return linkToUsers(IanaLinkRelations.SELF.value());
	}

	public Link linkToUserGroupinge(Long userId, String rel) {
	    return linkTo(methodOn(UserGroupingeController.class)
	            .list(userId)).withRel(rel);
	}

	public Link linkToUserGroupinge(Long userId) {
	    return linkToUserGroupinge(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToPaymentMethod(Long paymentMethodId, String rel) {
	    return linkTo(methodOn(PaymentMethodController.class)
	            .search(paymentMethodId, null)).withRel(rel);
	}

	public Link linkToPaymentMethod(Long paymentMethodId) {
	    return linkToPaymentMethod(paymentMethodId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCity(Long cityId, String rel) {
	    return linkTo(methodOn(CityController.class)
	            .search(cityId)).withRel(rel);
	}

	public Link linkToCity(Long cityId) {
	    return linkToCity(cityId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCities(String rel) {
	    return linkTo(CityController.class).withRel(rel);
	}

	public Link linkToCities() {
	    return linkToCities(IanaLinkRelations.SELF.value());
	}

	public Link linkToState(Long stateId, String rel) {
	    return linkTo(methodOn(StateController.class)
	            .search(stateId)).withRel(rel);
	}

	public Link linkToState(Long stateId) {
	    return linkToState(stateId, IanaLinkRelations.SELF.value());
	}

	public Link linkToStates(String rel) {
	    return linkTo(StateController.class).withRel(rel);
	}

	public Link linkToStates() {
	    return linkToStates(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduct(Long restaurantId, Long productId, String rel) {
	    return linkTo(methodOn(RestaurantProductController.class)
	            .search(restaurantId, productId))
	            .withRel(rel);
	}

	public Link linkToProduct(Long restaurantId, Long productId) {
	    return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
	}

	public Link linkToProducts(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantProductController.class)
	            .list(restaurantId, null)).withRel(rel);
	}

	public Link linkToProducts(Long restaurantId) {
	    return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProductPhoto(Long restaurantId, Long productId, String rel) {
	    return linkTo(methodOn(RestaurantProductPhotoController.class)
	            .search(restaurantId, productId)).withRel(rel);
	}

	public Link linkToProductPhoto(Long restaurantId, Long productId) {
	    return linkToProductPhoto(restaurantId, productId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToKitchens(String rel) {
	    return linkTo(KitchenController.class).withRel(rel);
	}

	public Link linkToKitchens() {
	    return linkToKitchens(IanaLinkRelations.SELF.value());
	}

	public Link linkToPaymentMethods(String rel) {
	    return linkTo(PaymentMethodController.class).withRel(rel);
	}

	public Link linkToPaymentMethods() {
	    return linkToPaymentMethods(IanaLinkRelations.SELF.value());
	} 
	
	public Link linkToGroupinges(String rel) {
	    return linkTo(GroupingeController.class).withRel(rel);
	}

	public Link linkToGroupinges() {
	    return linkToGroupinges(IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupingePermissions(Long groupingeIde, String rel) {
	    return linkTo(methodOn(GroupingePermissionController.class)
	            .list(groupingeIde)).withRel(rel);
	}
	
	public Link linkToPermissions(String rel) {
	    return linkTo(PermissionController.class).withRel(rel);
	}

	public Link linkToPermissions() {
	    return linkToPermissions(IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupingePermissions(Long groupId) {
	    return linkToGroupingePermissions(groupId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupingePermissionAssociation(Long groupId, String rel) {
	    return linkTo(methodOn(GroupingePermissionController.class)
	            .associate(groupId, null)).withRel(rel);
	}

	public Link linkToGroupingePermissionDisassociation(Long groupId, Long permissionId, String rel) {
	    return linkTo(methodOn(GroupingePermissionController.class)
	            .disassociate(groupId, permissionId)).withRel(rel);
	}
	
	public Link linkToUserGroupingeAssociation(Long userId, String rel) {
	    return linkTo(methodOn(UserGroupingeController.class)
	            .associate(userId, null)).withRel(rel);
	}

	public Link linkToUserGroupingeDisassociation(Long userId, Long groupId, String rel) {
	    return linkTo(methodOn(UserGroupingeController.class)
	            .disassociate(userId, groupId)).withRel(rel);
	}
	
	public Link linkToStatistics(String rel) {
	    return linkTo(StatisticsController.class).withRel(rel);
	}

	public Link linkToStatisticsDailySales(String rel) {
	    TemplateVariables filterVariables = new TemplateVariables(
	            new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataRegisterStart", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataRegisterEnd", VariableType.REQUEST_PARAM),
	            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	    
	    String urlOrders = linkTo(methodOn(StatisticsController.class)
	            .queryDailySales(null, null)).toUri().toString();
	    
	    return new Link(UriTemplate.of(urlOrders, filterVariables), rel);
	}
}
