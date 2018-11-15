package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductHHVCService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.ProductHhvcVM;
import com.baoviet.agency.web.rest.vm.PremiumHHVCVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementHHVCVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency HHVC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/hhvc")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/hhvc", description = "<a href=\"/content/extra_doc/hhvc.html\" target=\"_blank\">External document</a>")
public class ProductHhvcResource extends AbstractAgencyResource{

	private final Logger log = LoggerFactory.getLogger(ProductHhvcResource.class);

	private static final String ENTITY_NAME = "hhvc";

	@Autowired
	private ProductHHVCService productHHVCService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AgreementService agreementService;

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HHV_VIEW')")
	@PostMapping("/premium")
	@Timed
	@ApiOperation(value = "getPremium", notes = "Hàm tính phí Bảo hiểm hàng hóa vận chuyển nội địa.")
	public ResponseEntity<PremiumHHVCVM> getPremium(@Valid @RequestBody PremiumHHVCVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);

		// Call service
		PremiumHHVCVM data = productHHVCService.calculatePremium(param);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// sogycbh
	// policyNumber
	// paymentMenthod
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HHV_CREATE')")
	@PostMapping("/createPolicy")
	@Timed
	@ApiOperation(value = "createPolicy", notes = "Tạo yêu cầu Bảo hiểm hàng hóa vận chuyển nội địa.")
	public ResponseEntity<ProductHhvcVM> createPolicy(@Valid @RequestBody ProductHhvcVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);

		// Call service
		ProductHhvcVM data = productHHVCService.createOrUpdateHhvcPolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HHV_EDIT')")
	@PostMapping("/update")
	@Timed
	@ApiOperation(value = "update", notes = "Cập nhật yêu cầu Bảo hiểm hàng hóa vận chuyển nội địa.")
	public ResponseEntity<ProductHhvcVM> update(@Valid @RequestBody ProductHhvcVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);

		// Call service
		ProductHhvcVM data = productHHVCService.createOrUpdateHhvcPolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HHV_EDIT')")
	@PostMapping("/updatePolicy")
	@Timed
	@ApiOperation(value = "updatePolicy", notes = "Cập nhật yêu cầu Bảo hiểm hàng hóa vận chuyển nội địa.")
	@Deprecated
	public ResponseEntity<UpdateAgreementHHVCVM> updatePolicy(@Valid @RequestBody UpdateAgreementHHVCVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updatePolicy : {}", param);

		// Call service
		boolean result = agreementService.updateAgreementHHVC(param.getAgreement(), param.getGoods());
		param.setResult(result);

		// Return data
		return new ResponseEntity<>(param, HttpStatus.OK);
	}
}
