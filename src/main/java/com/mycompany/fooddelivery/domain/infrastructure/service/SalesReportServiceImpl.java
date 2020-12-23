package com.mycompany.fooddelivery.domain.infrastructure.service;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;
import com.mycompany.fooddelivery.domain.infrastructure.service.exception.ReportException;
import com.mycompany.fooddelivery.domain.service.SaleQueryService;
import com.mycompany.fooddelivery.domain.service.SalesReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SalesReportServiceImpl implements SalesReportService {
	
	@Autowired
	private SaleQueryService saleQueryService;
	
	@Override
	public byte[] issueDailySales(DailySaleFilter filter, String timeOffset) {
		try {
			// ---------- Gets inputStream from Jaser report file
			var inputStream = this.getClass().getResourceAsStream(
					"/reports/daily-sales.jasper");
			
			// ---------- Sets report parameters
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			// ---------- Gets the report from service (jasper datasource)
			var dailySales = saleQueryService.queryDailySales(filter, timeOffset);
			
			// ---------- Datasource that will fullfill the report
			// ---------- JRBeanCollectionDataSource is a bean collection
			var dataSource = new JRBeanCollectionDataSource(dailySales);
			
			// ---------- jasperPrint is an object that represents the report with parameters and datasource
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			
			// ---------- exports jasperPrint in PDF format
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("It was not possible to issue the daily sales report", e);
		}
	}
}
