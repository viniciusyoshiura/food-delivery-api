package com.mycompany.fooddelivery.api.controller.exception.handler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

// ---------- Response according to RFC 7807
@ApiModel("Problem")
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "https://mycompany.com/invalid-data", position = 10)
	private String type;

	@ApiModelProperty(example = "Invalid data", position = 15)
	private String title;

	@ApiModelProperty(example = "One or more fields are invalid. Fill in correctly and try again.", position = 20)
	private String detail;

	@ApiModelProperty(example = "One or more fields are invalid. Fill in correctly and try again.", position = 25)
	private String userMessage;

	@ApiModelProperty(example = "2021-01-01T00:00:00.00001Z", position = 5)
	private OffsetDateTime timestamp;

	@ApiModelProperty(value = "List of objects or fields that generated the error (optional)", position = 30)
	private List<Field> fields;
	
	@ApiModel("ProblemField")
	@Getter
	@Builder
	public static class Field {
		
		@ApiModelProperty(example = "price")
		private String name;
		
		@ApiModelProperty(example = "The price is required")
		private String userMessage;
	}

}
