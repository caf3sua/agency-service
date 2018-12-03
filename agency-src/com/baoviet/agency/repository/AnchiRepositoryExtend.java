package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AnchiDTO;
import com.baoviet.agency.web.rest.vm.SearchPrintedPaperVM;


/**
 * Spring Data JPA repository for the Anchi module.
 */
@Repository
public interface AnchiRepositoryExtend {
	
	List<AnchiDTO> search(SearchPrintedPaperVM param, String type); 
}