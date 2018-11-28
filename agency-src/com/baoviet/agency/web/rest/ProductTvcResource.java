package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.AgentDiscount;
import com.baoviet.agency.domain.BenifitTravel;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcImportResponseDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.repository.AgentDiscountRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ExcelService;
import com.baoviet.agency.service.ProductTvcService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumTvcVM;
import com.baoviet.agency.web.rest.vm.ProductTvcVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementTvcVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvc")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvc", description = "<a href=\"/content/extra_doc/tvc.html\" target=\"_blank\">External document</a>")
public class ProductTvcResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);

    private static final String ENTITY_NAME = "tvc";

    @Autowired
    private ProductTvcService productTvcService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
    private ExcelService excelService;
    
    @Autowired
    private AgentDiscountRepository agentDiscountRepository;
    
    @GetMapping("/get-benefit-areaId/{areaId}")
    @Timed
    @ApiOperation(value="getAllBenefit", notes="Lấy thông tin bảng quyền lợi bảo hiểm, areaId='2,3,4' 2: ASEAN, 3: Châu Á - TBD, 4: Toàn cầu ")
    public ResponseEntity<List<BenifitTravel>> getAllBenefit(@PathVariable String areaId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllBenefit");
	
		// Call service
		List<BenifitTravel> data = productTvcService.getBenefitByAreaId(areaId);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
//    {
//    	  "destination": "3",	// địa điểm đến
//    	  "ngayDi": "13/04/2018",
//    	  "ngayVe": "20/04/2018",
//    	  "numberOfPerson": 1,	// gói bảo hiểm : 1 cá nhân: 
//    	  "planId": "2",
//    	  "premiumDiscount": 0,
//    	  "premiumNet": 0,
//    	  "premiumPackage": "1",
//    	  "premiumTvc": 0,
//    	  "songay": 0
//    }    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính tổng phí bảo hiểm du lịch quốc tế/International travel insurance premiums")
    public ResponseEntity<PremiumTvcVM> getPremium(@Valid @RequestBody PremiumTvcVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgentDiscount agentDiscount = agentDiscountRepository.findByAgencyIdAndLineId(currentAgency.getId(), "TVC");
		if (agentDiscount != null) {
			if (agentDiscount.getDiscount() != null && agentDiscount.getDiscount() > 0) {
				param.setPremiumDiscount(agentDiscount.getDiscount());
			}
		}

		// Call service
		PremiumTvcVM data = productTvcService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu bảo hiểm du lịch quốc tế/Create international travel insurance claim")
    public ResponseEntity<ProductTvcVM> createPolicy(@Valid @RequestBody ProductTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgentDiscount agentDiscount = agentDiscountRepository.findByAgencyIdAndLineId(currentAgency.getId(), "TVC");
		if (agentDiscount != null) {
			if (agentDiscount.getDiscount() != null && agentDiscount.getDiscount() > 0) {
				param.setChangePremium(agentDiscount.getDiscount());
			}
		}
		
		// Call service
		ProductTvcVM data = productTvcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu bảo hiểm du lịch quốc tế/Create international travel insurance claim")
    public ResponseEntity<ProductTvcVM> update(@Valid @RequestBody ProductTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
				
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductTvcVM data = productTvcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu bảo hiểm du lịch quốc tế/Update international travel insurance claim")
    @Deprecated
    public ResponseEntity<UpdateAgreementTvcVM> updatePolicy(@Valid @RequestBody UpdateAgreementTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		//TvicareBaseVM data = productTvcService.updatePolicy(param);
		Boolean result = agreementService.updateAgreementTvcTravelCare(param.getAgreement(), param.getTravelcare(), param.getLstTravelCareAdd());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
    
    // Import file excel
    @PostMapping("/import-excel")
    @Timed
    @ApiOperation(value="importExcel", notes="Import excel")
    public ResponseEntity<ProductTvcImportResponseDTO> importExcel(@Valid @RequestBody ProductImportDTO param) throws Exception {

    	log.debug("REST request to importExcel : {}", param);
		
		// Call service
    	ProductTvcImportResponseDTO result = excelService.processImportTVC(param);
		
		// Return data
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

}
