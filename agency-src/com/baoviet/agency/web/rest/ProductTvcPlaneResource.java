package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
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

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.ProductTvcPlaneService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumTvcPlaneVM;
import com.baoviet.agency.web.rest.vm.ProductTvcPlaneVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementHomeVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVI resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvcPlane")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX
		+ "product/tvcPlane", description = "<a href=\"/content/extra_doc/tvcPlane.html\" target=\"_blank\">External document</a>")
public class ProductTvcPlaneResource extends AbstractAgencyResource{

	private final Logger log = LoggerFactory.getLogger(ProductTvcPlaneResource.class);

	private static final String ENTITY_NAME = "tvcPlane";

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProductTvcPlaneService productTvcPlaneService;

	@PostMapping("/premium")
	@Timed
	@ApiOperation(value = "getPremium", notes = "Hàm tính phí bảo hiểm máy bay/Plane insurance premiums")
	public ResponseEntity<PremiumTvcPlaneVM> getPremium(@Valid @RequestBody PremiumTvcPlaneVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);

		// Call service
		PremiumTvcPlaneVM data = productTvcPlaneService.calculatePremium(param);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/createPolicy")
	@Timed
	@ApiOperation(value = "createPolicy", notes = "Tạo yêu cầu bảo hiểm máy bay/Create plane insurance claim")
	public ResponseEntity<ProductTvcPlaneVM> createPolicy(@Valid @RequestBody ProductTvcPlaneVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);

		// Get user agent
		 String userAgent = request.getHeader("user-agent");
		 param.setUserAgent(userAgent);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// Call service
		ProductTvcPlaneVM data = productTvcPlaneService.createOrUpdatePolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	@Timed
	@ApiOperation(value = "update", notes = "Cập nhật yêu cầu bảo hiểm máy bay/Create plane insurance claim")
	public ResponseEntity<ProductTvcPlaneVM> update(@Valid @RequestBody ProductTvcPlaneVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);

		// validate 
		validateUpdateProduct(param);
				
		// Get user agent
		 String userAgent = request.getHeader("user-agent");
		 param.setUserAgent(userAgent);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// Call service
		ProductTvcPlaneVM data = productTvcPlaneService.createOrUpdatePolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/updatePolicy")
	@Timed
	@ApiOperation(value = "updatePolicy", notes = "Cập nhật yêu cầu bảo hiểm máy bay/Update plane insurance claim")
	@Deprecated
	public ResponseEntity<UpdateAgreementHomeVM> updatePolicy(@Valid @RequestBody UpdateAgreementHomeVM param)
			throws URISyntaxException, AgencyBusinessException {
//		log.debug("REST request to updatePolicy : {}", param);
//
//		// Call service
//		boolean result = agreementService.updateAgreementHome(param.getAgreement(), param.getHome());
//		param.setResult(result);
//
//		// Return data
//		return new ResponseEntity<>(param, HttpStatus.OK);
		return null;
	}
}
