package com.mycompany.fooddelivery.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
		return new Docket(DocumentationType.OAS_30)
			.select()
				.apis(RequestHandlerSelectors.basePackage("com.mycompany.fooddelivery.api"))
				.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo())
			.tags(new Tag("Cities", "Cities management"));
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Food Delivery API")
			.description("Food delivery API for clients and restaurants")
			.version("1")
			.contact(new Contact("MyCompany", "https://www.mycompany.com", "contact@mycompany.com"))
			.build();
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("index.html")
        	.addResourceLocations("classpath:/META-INF/resources/");
	}
	
}
