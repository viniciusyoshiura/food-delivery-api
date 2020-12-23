package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.model.dto.DailySaleDTO;
import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;
import com.mycompany.fooddelivery.domain.service.SaleQueryService;
import com.mycompany.fooddelivery.domain.service.SalesReportService;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private SaleQueryService saleQueryService;

	@Autowired
	private SalesReportService salesReportService;

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySaleDTO> queryDailySales(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		// --------- Implementation of queryDailySales uses CriteriaBuilder
		return saleQueryService.queryDailySales(filter, timeOffset);
	}

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		byte[] bytesPdf = salesReportService.issueDailySales(filter, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_PDF)
			.headers(headers)
			.body(bytesPdf);
	}

}
