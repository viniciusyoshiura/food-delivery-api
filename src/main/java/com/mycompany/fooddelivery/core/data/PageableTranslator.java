package com.mycompany.fooddelivery.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

	public static Pageable convert(Pageable pageable, Map<String, String> fieldsMapping) {
		var orders = pageable.getSort().stream()
			// ---------- Filter only existent fields
			.filter(
				order -> fieldsMapping.containsKey(order.getProperty())
			)
			.map(
				order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty()))
			)
			.collect(Collectors.toList());
							
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(orders));
	}
	
}
