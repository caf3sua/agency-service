package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.BenifitTvi;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductTviService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumTviVM;
import com.baoviet.agency.web.rest.vm.ProductTviVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementTviVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVI resource.
 * 
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvi")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvi", description = "<a href=\"/content/extra_doc/tvi.html\" target=\"_blank\">External document</a>")
public class ProductTviResource extends AbstractAgencyResource{

	private final Logger log = LoggerFactory.getLogger(ProductTviResource.class);

	private static final String ENTITY_NAME = "tvi";

	@Autowired
	private ProductTviService productTviService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AgreementService agreementService;

	
	@GetMapping("/benefit/all")
    @Timed
    public ResponseEntity<List<BenifitTvi>> getAllBenefit() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllBenefit");
	
		// Call service
		List<BenifitTvi> data = productTviService.getAllBenefit();
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVI_VIEW')")
	@PostMapping("/premium")
	@Timed
	@ApiOperation(value = "getPremium", notes = "Hàm tính tổng phí Bảo hiểm du lịch Việt Nam/Vietnam travel insurance premiums")
	public ResponseEntity<PremiumTviVM> getPremium(@Valid @RequestBody PremiumTviVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);

		// Call service
		PremiumTviVM data = productTviService.calculatePremium(param);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVI_CREATE')")
	@PostMapping("/createPolicy")
	@Timed
	@ApiOperation(value = "createPolicy", notes = "Tạo yêu cầu bảo hiểm du lịch Việt Nam/Create Vietnam travel insurance claim")
	public ResponseEntity<ProductTviVM> createPolicy(@Valid @RequestBody ProductTviVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);

		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductTviVM data = productTviService.createOrUpdatePolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVI_EDIT')")
	@PostMapping("/update")
	@Timed
	@ApiOperation(value = "update", notes = "Cập nhật yêu cầu bảo hiểm du lịch Việt Nam/Create Vietnam travel insurance claim")
	public ResponseEntity<ProductTviVM> update(@Valid @RequestBody ProductTviVM param)
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
		ProductTviVM data = productTviService.createOrUpdatePolicy(param, currentAgency);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
    
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVI_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu bảo hiểm du lịch Việt Nam/Update Vietnam travel insurance claim")
    @Deprecated
    public ResponseEntity<UpdateAgreementTviVM> updatePolicy(@Valid @RequestBody UpdateAgreementTviVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);

		// Call service
		boolean result = agreementService.updateAgreementTvicare(param.getAgreement(), param.getTvicare(), param.getLstTviCareAdd());
		param.setResult(result);

		// Return data
		return new ResponseEntity<>(param, HttpStatus.OK);
	}
}
