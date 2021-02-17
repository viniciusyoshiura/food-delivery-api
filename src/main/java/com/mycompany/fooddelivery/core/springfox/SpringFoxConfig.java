package com.mycompany.fooddelivery.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.KitchenDTO;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.api.openapi.model.KitchenDTOOpenApi;
import com.mycompany.fooddelivery.api.openapi.model.PageableModelOpenApi;
import com.mycompany.fooddelivery.api.openapi.model.PurchaseOrdersDTOOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


// ----------- BeanValidatorPluginsConfiguration - bean validation with Swagger
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
	
	// ---------- Produces an instance of docket (summary)
	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();
		
		// ---------- globalResponses - set the endpoints global responses
		// ---------- additionalModels - add models (or schemas), e.g., Problem.class
		// ---------- directModelSubstitute - substitute models (for documentation purposes)
		// ---------- alternateTypeRules - adds model substitution rules
		// ---------- ignoredParameterTypes - Ignore Parameter type
		// ---------- globalOperationParameters - Configure global parameters, e.g., Squiggly parameters in PurchaseOrderController
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
				.apis(RequestHandlerSelectors.basePackage("com.mycompany.fooddelivery.api"))
				.paths(PathSelectors.any())
			.build()
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, globalGetResponse())
			.globalResponseMessage(RequestMethod.POST, globalPostPutResponse())
			.globalResponseMessage(RequestMethod.PUT, globalPostPutResponse())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponse())
			.additionalModels(typeResolver.resolve(Problem.class))
			.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, KitchenDTO.class),
					KitchenDTOOpenApi.class))
			.alternateTypeRules(AlternateTypeRules.newRule(
                    typeResolver.resolve(Page.class, PurchaseOrderSummaryDTO.class),
                    PurchaseOrdersDTOOpenApi.class))
			.ignoredParameterTypes(ServletWebRequest.class, URL.class,
					URI.class, File.class, Resource.class, URLStreamHandler.class, InputStream.class)
//			.globalOperationParameters(Arrays.asList(
//					new ParameterBuilder()
//						.name("filters")
//						.description("Property names to filter in the response, separated by commas")
//						.parameterType("query")
//						.modelRef(new ModelRef("string"))
//						.build()))
			.apiInfo(apiInfo())
			.tags(new Tag("Cities", "Cities management"),
					new Tag("Groups", "User groups management"),
			        new Tag("Kitchens", "Kitchens management"), 
			        new Tag("Payment methods", "Payment methods management"),
			        new Tag("Orders", "Orders management"),
			        new Tag("Restaurants", "Restaurants management"),
			        new Tag("States", "States management"),
			        new Tag("Products", "Products management"),
			        new Tag("Users", "Users management"),
			        new Tag("Statistics", "FoodDelivery Statistics"));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Food Delivery API")
			.description("Food delivery API for clients and restaurants")
			.version("1")
			.contact(new Contact("MyCompany", "https://www.mycompany.com", "contact@mycompany.com"))
			.build();
	}
	
	private List<ResponseMessage> globalGetResponse() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Internal Server Error")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Resource has no representation that could be accepted by the consumer")
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostPutResponse() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Invalid request (client error)")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.responseModel(new ModelRef("Problem"))
					.message("Internal server error")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Resource has no representation that could be accepted by the consumer")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Request declined because the body is in an unsupported format")
					.responseModel(new ModelRef("Problem"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponse() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Invalid request (client error)")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Internal server error")
					.responseModel(new ModelRef("Problem"))
					.build()
			);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	// ---------- For Swagger 3.0
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("index.html")
//        	.addResourceLocations("classpath:/META-INF/resources/");
//	}
	
}
