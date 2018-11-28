package com.baoviet.agency.service;

import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcImportResponseDTO;
import com.baoviet.agency.exception.AgencyBusinessException;

/**
 * Service Interface for managing Agency.
 */
public interface ExcelService {
	ProductTvcImportResponseDTO processImportTVC(ProductImportDTO param) throws AgencyBusinessException;
}

