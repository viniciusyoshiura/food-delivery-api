package com.mycompany.fooddelivery.domain.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
import com.mycompany.fooddelivery.domain.repository.KitchenRepository;
import com.mycompany.fooddelivery.util.DatabaseCleaner;
import com.mycompany.fooddelivery.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// ---------- TestPropertySource - indicates application.properties for test, including database
@TestPropertySource("/application-test.properties")
public class KitchenControllerIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	private static final int NON_EXISTENT_KITCHEN_ID = 100;
	private Kitchen americanKitchen;
	private int numberOfRegisteredKitchens;
	private String correctChineseKitchenJson;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/kitchens";
		
		correctChineseKitchenJson = ResourceUtils.getContentFromResource(
				"/json/chinese-kitchen.json");
		
		// ---------- Clear database before every test
		databaseCleaner.clearTables();
		setData();
		
	}
	
	@Test
	public void searchKitchensStatus200() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void searchNumberOfRegisteredKitchensStatus200() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("name", hasItems("Thai", "American"))
			.body("", hasSize(numberOfRegisteredKitchens));
	}
	
	@Test
	public void searchExistingKitchenStatus200() {
		given()
			.pathParam("kitchenId", americanKitchen.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo("American"));
	}
	
	@Test
	public void searchNonExistingKitchenStatus404() {
		given()
			.pathParam("kitchenId", NON_EXISTENT_KITCHEN_ID)
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void registerKitchenStatus201() {
		given()
			.body(correctChineseKitchenJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	private void setData() {
		
		Kitchen thaiKitchen = new Kitchen();
		thaiKitchen.setName("Thai");
		kitchenRepository.save(thaiKitchen);

		americanKitchen = new Kitchen();
		americanKitchen.setName("American");
		kitchenRepository.save(americanKitchen);
		
		numberOfRegisteredKitchens = (int) kitchenRepository.count();
	}
	
}
