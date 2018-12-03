package com.baoviet.agency.service;

import java.io.IOException;
import java.util.List;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AnchiDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperTypeDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.SearchPrintedPaperVM;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Service Interface for managing Anchi.
 */
public interface AnchiService {
	
	List<AnchiDTO> search(SearchPrintedPaperVM param, String type);
	
	List<PrintedPaperTypeDTO> getLoaiAnchi(String agencyType) throws JsonParseException, JsonMappingException, IOException;
	
	List<PrintedPaperDTO> searchNew(SearchPrintedPaperVM param, String agencyType) throws JsonParseException, JsonMappingException, IOException;
	
	String wsUpdateAnChi(AgreementAnchiVM obj) throws AgencyBusinessException;
	
	String save(AnchiDTO param);
	
	Anchi getBySoAnchi(String soAnchi, String type);
}

