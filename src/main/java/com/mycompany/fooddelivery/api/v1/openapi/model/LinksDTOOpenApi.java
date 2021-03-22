package com.mycompany.fooddelivery.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinksDTOOpenApi {

	private LinkDTO rel;

	@Setter
	@Getter
	@ApiModel("Link")
	private class LinkDTO {

		private String href;
		private boolean templated;

	}

}
