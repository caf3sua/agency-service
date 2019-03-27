package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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

import com.baoviet.agency.domain.MotoHondaCat;
import com.baoviet.agency.domain.MotoHondaRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.repository.MotoHondaCatRepository;
import com.baoviet.agency.repository.MotoHondaRateRepository;
import com.baoviet.agency.service.ProductHondaService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumMotoHondaVM;
import com.baoviet.agency.web.rest.vm.ProductHondaVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency MOTO resource.
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/honda")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/honda")
public class ProductHondaResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductHondaResource.class);

    private static final String ENTITY_NAME = "honda";

    @Autowired
    private ProductHondaService productHondaService;
    
    @Autowired
    private MotoHondaCatRepository motoHondaCatRepository;
    
    @Autowired
    private MotoHondaRateRepository motoHondaRateRepository;
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductHondaVM> createPolicy(@Valid @RequestBody ProductHondaVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductHondaVM data = productHondaService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductHondaVM> update(@Valid @RequestBody ProductHondaVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductHondaVM data = productHondaService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/get-mau-xe/{id}")
    @Timed
    @ApiOperation(value="getMauXe", notes="Lấy danh sách mẫu xe theo năm")
    public ResponseEntity<List<MotoHondaCat>> getMauXe(@PathVariable String id) throws URISyntaxException, AgencyBusinessException {
				
    	List<MotoHondaCat> lstMotoHondaCat = motoHondaCatRepository.findBySoNam(id);
    	
    	if (lstMotoHondaCat != null && lstMotoHondaCat.size() > 0) {
    		// Return data
            return new ResponseEntity<>(lstMotoHondaCat, HttpStatus.OK);	
    	}

		// Return data
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/get-gia-tri-xe/{id}")
    @Timed
    @ApiOperation(value="getGiaTriXe", notes="Lấy giá trị xe")
    public ResponseEntity<MotoHondaCat> getGiaTriXe(@PathVariable String id) throws URISyntaxException, AgencyBusinessException {
				
    	MotoHondaCat motoHondaCat = motoHondaCatRepository.findOne(id);
    	
    	if (motoHondaCat != null) {
    		// Return data
            return new ResponseEntity<>(motoHondaCat, HttpStatus.OK);	
    	}

		// Return data
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm xe máy.")
    public ResponseEntity<PremiumMotoHondaVM> getPremium(@Valid @RequestBody PremiumMotoHondaVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		MotoHondaCat motoHondaCat = motoHondaCatRepository.findOne(param.getIdModel());
		if (motoHondaCat != null && motoHondaCat.getGiaTriXe() > 0) {
			if (motoHondaCat.getSoNam().equals("3") || motoHondaCat.getSoNam().equals("2")) {
				// Call service
				MotoHondaRate data = motoHondaRateRepository.findBySotienFromLessThanEqualAndSotienToGreaterThanEqualAndSoNamAndGoi(motoHondaCat.getGiaTriXe(), motoHondaCat.getGiaTriXe(), param.getNamSd(), "1");
				if (data != null) {
					param.setSoTienBh(motoHondaCat.getGiaTriXe());
					param.setTongPhi1(data.getPhi());
				}	
			} else {
				// Call service
				MotoHondaRate data1 = motoHondaRateRepository.findBySotienFromLessThanEqualAndSotienToGreaterThanEqualAndSoNamAndGoi(motoHondaCat.getGiaTriXe(), motoHondaCat.getGiaTriXe(), param.getNamSd(), "1");
				MotoHondaRate data2 = motoHondaRateRepository.findBySotienFromLessThanEqualAndSotienToGreaterThanEqualAndSoNamAndGoi(motoHondaCat.getGiaTriXe(), motoHondaCat.getGiaTriXe(), param.getNamSd(), "2");
				MotoHondaRate data3 = motoHondaRateRepository.findBySotienFromLessThanEqualAndSotienToGreaterThanEqualAndSoNamAndGoi(motoHondaCat.getGiaTriXe(), motoHondaCat.getGiaTriXe(), param.getNamSd(), "3");
				if (data1 != null) {
					param.setTongPhi1(data1.getPhi());
				}
				if (data2 != null) {
					param.setTongPhi2(data2.getPhi());
				}
				if (data3 != null) {
					param.setTongPhi3(data3.getPhi());
				}
				param.setSoTienBh(motoHondaCat.getGiaTriXe());
			}
		}
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
    
}
