package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.service.TVVService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.TVVBaseVM;
import com.baoviet.agency.web.rest.vm.TVVDanhSachVM;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "agency-tvv")
public class TVVResource {

    private final Logger log = LoggerFactory.getLogger(TVVResource.class);

    private static final String ENTITY_NAME = "agency-tvv";

    @Autowired
    private TVVService tVVService;
    
    
    @PostMapping("/getDoanhThuTheoSanPham")
    @Timed
    public ResponseEntity<List> getDoanhThuTheoSanPham(@Valid @RequestBody TVVBaseVM param) throws URISyntaxException {
		log.debug("REST request to getDoanhThuTheoSanPham : {}", param);
		
		// Call service
		List data = tVVService.getDoanhThuTheoSanPham(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
//    {
//	  "kenhPhanPhoi": "TVV"
//	}
//    lấy danh sách tin tức hiển thị khi load trang theo agency.Kenh_phan_phoi
    @PostMapping("/getDefaultNews")
    @Timed
    public ResponseEntity<List<ProductGenInfo>> getDefaultNews(@Valid @RequestBody TVVDanhSachVM param) throws URISyntaxException {
		log.debug("REST request to getDefaultNews : {}", param);
		
		// Call service
		List<ProductGenInfo> data = tVVService.getDefaultNews(param.getKenhPhanPhoi());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/getHighlightNews")
    @Timed
    public ResponseEntity<List<ProductGenInfo>> getHighlightNews(@Valid @RequestBody TVVDanhSachVM param) throws URISyntaxException {
		log.debug("REST request to getHighlightNews : {}", param);
		
		// Call service
		List<ProductGenInfo> data = tVVService.getHighlightNews(param.getKenhPhanPhoi());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
