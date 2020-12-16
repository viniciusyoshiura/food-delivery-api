package com.mycompany.fooddelivery.domain.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.Restaurant;
import com.mycompany.fooddelivery.domain.repository.KitchenRepository;
import com.mycompany.fooddelivery.domain.repository.RestaurantRepository;
import com.mycompany.fooddelivery.util.DatabaseCleaner;
import com.mycompany.fooddelivery.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantControllerIT {

	private static final String BUSINESS_PROBLEM_TYPE_VIOLATION = "Business rule violation";

	private static final String INVALID_DATA = "Invalid data";

	private static final int NONEXISTENT_RESTAURANT_ID = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	private String jsonRestaurantCorrect;
	private String jsonRestaurantWithoutShippingFee;
	private String jsonRestaurantWithoutKitchen;
	private String jsonRestaurantWithNonExistentKitchen;
	
	private Restaurant burgerTopRestaurant;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurants";

		jsonRestaurantCorrect = ResourceUtils.getContentFromResource(
				"/json/new-york-barbecue-restaurant-correct.json");
		
		jsonRestaurantWithoutShippingFee = ResourceUtils.getContentFromResource(
				"/json/new-york-barbecue-restaurant-without-shippingfee.json");
		
		jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource(
				"/json/new-york-barbecue-restaurant-without-kitchen.json");
		
		jsonRestaurantWithNonExistentKitchen = ResourceUtils.getContentFromResource(
				"/json/new-york-barbecue-restaurnt-with-nonexistent-kitchen.json");
		
		databaseCleaner.clearTables();
		setData();
	}
	
	@Test
	public void registerRestaurantStatus201() {
		given()
			.body(jsonRestaurantCorrect)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void registerRestaurantWithoutShippingFeeStatus400() {
		given()
			.body(jsonRestaurantWithoutShippingFee)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(INVALID_DATA));
	}

	@Test
	public void registerRestaurantWithoutKitchenStatus400() {
		given()
			.body(jsonRestaurantWithoutKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(INVALID_DATA));
	}
	
	@Test
	public void registerRestaurantWithNonExistingKitchenStatus404() {
		given()
			.body(jsonRestaurantWithNonExistentKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(BUSINESS_PROBLEM_TYPE_VIOLATION));
	}
	
	@Test
	public void searchRestaurantsStatus200() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void searchExistingRestaurantStatus200() {
		given()
			.pathParam("restaurantId", burgerTopRestaurant.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(burgerTopRestaurant.getName()));
	}
	
	@Test
	public void searchNonExistingRestaurantStatus404() {
		given()
			.pathParam("restaurantId", NONEXISTENT_RESTAURANT_ID)
			.accept(ContentType.JSON)
		.when()
			.get("/{restaurantId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void setData() {
		Kitchen brazilianKitchen = new Kitchen();
		brazilianKitchen.setName("Brazilian");
		kitchenRepository.save(brazilianKitchen);

		Kitchen americanKitchen = new Kitchen();
		americanKitchen.setName("American");
		kitchenRepository.save(americanKitchen);
		
		burgerTopRestaurant = new Restaurant();
		burgerTopRestaurant.setName("Burger Top");
		burgerTopRestaurant.setShippingFee(new BigDecimal(10));
		burgerTopRestaurant.setKitchen(americanKitchen);
		restaurantRepository.save(burgerTopRestaurant);
		
		Restaurant mineiraFoodRestaurante = new Restaurant();
		mineiraFoodRestaurante.setName("Mineira food");
		mineiraFoodRestaurante.setShippingFee(new BigDecimal(10));
		mineiraFoodRestaurante.setKitchen(brazilianKitchen);
		restaurantRepository.save(mineiraFoodRestaurante);
	}
	
}
