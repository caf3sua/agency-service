package com.baoviet.agency.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperTypeDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AnchiService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.SearchPrintedPaperVM;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "printed-paper")
public class PrintedPaperResource extends AbstractAgencyResource {

    private final Logger log = LoggerFactory.getLogger(PrintedPaperResource.class);

    @Autowired
    private AnchiService anchiService;
    
	@GetMapping("/get-type")
	@Timed
	@ApiOperation(value = "getType", notes = "Lấy danh sách loại ấn chỉ")
	public ResponseEntity<List<PrintedPaperTypeDTO>> getType()
			throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException {
		log.debug("REST request to getBU");

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// call service
		List<PrintedPaperTypeDTO> result = anchiService.getLoaiAnchi(currentAgency.getMa());
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    @PostMapping("/search-new")
    @Timed
    @ApiOperation(value="searchNew", notes="Lấy danh sách ấn chỉ chưa sử dụng theo mã đại lý, số URN")
    public ResponseEntity<List<PrintedPaperDTO>> searchNew(@Valid @RequestBody SearchPrintedPaperVM param) throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException{
		log.debug("REST request to searchNew");

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// call service
		List<PrintedPaperDTO> result = anchiService.searchNew(param, currentAgency.getMa());
    	
		// Return data
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/search-used")
    @Timed
    @ApiOperation(value="searchUsed", notes="Lấy danh sách ấn chỉ đã sử dụng theo mã đại lý, số URN")
    public ResponseEntity<List<Anchi>> searchUsed(@Valid @RequestBody SearchPrintedPaperVM param) throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException{
		log.debug("REST request to searchUsed");

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		List<Anchi> result = anchiService.search(param, currentAgency.getMa());
		// Return data
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
//    @PostMapping("/update")
//    @Timed
//    @ApiOperation(value="update", notes="Cập nhật thông tin ấn chỉ đã sử dụng")
//    public ResponseEntity<String> update(@Valid @RequestBody UpdatePrintedPaperVM param) throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException{
//		log.debug("REST request to search");
//
//		String result = anchiService.updateAnChi(param);
//		log.debug("Trạng thái cập nhật ấn chỉ: {}", result);
//		
//		if (StringUtils.equals(result, "-1")) {
//			throw new AgencyBusinessException("error", ErrorCode.PRINTED_PAPER_NOT_FOUND, "Không tìm thấy ấn chỉ trong hệ thống");
//		} else if (StringUtils.equals(result, "1")) {
//			throw new AgencyBusinessException("error", ErrorCode.INVALID, "Trạng thái cuối cùng của ấn chỉ đã được thay đổi");
//		} 
//		
//		if (!StringUtils.equals(result, "0")) {
//			throw new AgencyBusinessException("error", ErrorCode.UNKNOW_ERROR, "Cập nhật ấn chỉ không thành công");
//		}
//		
//		// Return data
//        return new ResponseEntity<>("1", HttpStatus.OK);
//    }
}
