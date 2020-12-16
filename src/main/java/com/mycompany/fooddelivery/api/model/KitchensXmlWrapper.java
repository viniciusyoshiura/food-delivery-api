package com.mycompany.fooddelivery.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.mycompany.fooddelivery.domain.model.Kitchen;

import lombok.Data;
import lombok.NonNull;

@JsonRootName("kitchens")
@Data
public class KitchensXmlWrapper {
	
	@JsonProperty("kitchen")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Kitchen> kitchens;
	
}
