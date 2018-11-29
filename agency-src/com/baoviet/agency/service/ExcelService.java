package com.baoviet.agency.service;

import com.baoviet.agency.dto.excel.BasePathInfoDTO;
import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcExcelDTO;
import com.baoviet.agency.exception.AgencyBusinessException;

/**
 * Service Interface for managing Agency.
 */
public interface ExcelService {
	ProductTvcExcelDTO processImportTVC(ProductImportDTO param) throws AgencyBusinessException;
	
	BasePathInfoDTO processExportTVC(ProductTvcExcelDTO obj) throws AgencyBusinessException;
}

