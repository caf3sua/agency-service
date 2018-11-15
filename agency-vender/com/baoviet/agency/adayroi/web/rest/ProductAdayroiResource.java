package com.baoviet.agency.adayroi.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.adayroi.dto.Order;
import com.baoviet.agency.adayroi.service.AdayroiService;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.AbstractAgencyResource;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Adayroi.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "adayroi/product/bvp")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "adayroi/product/bvp")
public class ProductAdayroiResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductAdayroiResource.class);
    
    @Autowired
	private AdayroiService adayroiService;
    
    @PostMapping(value="/token")
    @Timed
    @ApiOperation(value="getAccessToken", notes="Service getToken đăng nhập")
    public String getAccessToken() throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to getAccessToken : {}");
		String token = adayroiService.getAccessToken();
		
        return token;
    }
    
    @GetMapping("/merchant/orders")
	@Timed
	@ApiOperation(value = "getAllBvp", notes = "Lấy danh sách bảo hiểm BVP")
	public ResponseEntity<List<Order>> getAllBvp() throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException {
		log.debug("REST request to getAllBvp");

		String accessToken = adayroiService.getAccessToken();
		
		// call service
		List<Order> result = adayroiService.getBvpProduct(accessToken);
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    @GetMapping("/createBvp")
	@Timed
	@ApiOperation(value = "createBvp", notes = "Tạo đơn bảo hiểm BVP")
	public ResponseEntity<List<Order>> createBvp() throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException {
		log.debug("REST request to createBvp");

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get accessToken
		String accessToken = adayroiService.getAccessToken();
		
		// call service
		List<Order> result = adayroiService.getBvpProduct(accessToken);
		
		if (result != null && result.size() > 0) {
			adayroiService.createBvpProduct(result, currentAgency, accessToken);
		}
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
