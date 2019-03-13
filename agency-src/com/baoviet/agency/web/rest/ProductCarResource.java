package com.baoviet.agency.web.rest;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.SppCarDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductCARService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;
import com.baoviet.agency.web.rest.vm.ProductCarImageVM;
import com.baoviet.agency.web.rest.vm.ProductCarVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementCarVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/car")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/car", description = "<a href=\"/content/extra_doc/car.html\" target=\"_blank\">External document</a>")
public class ProductCarResource extends AbstractAgencyResource {

    private final Logger log = LoggerFactory.getLogger(ProductCarResource.class);

    private static final String ENTITY_NAME = "car";

    @Autowired
	private HttpServletRequest request;
    @Autowired
    private ProductCARService productCARService;
    @Autowired
    private AgreementService agreementService;
        
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_CAR_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính tổng phí Bảo hiểm ô tô Car.")
    public ResponseEntity<PremiumCARVM> getPremium(@Valid @RequestBody PremiumCARVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumCARVM data = productCARService.calculatePremium(param,param.getAgencyRole());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_CAR_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm ô tô Car.")
    public ResponseEntity<ProductCarVM> createPolicy(@Valid @RequestBody ProductCarVM obj) throws URISyntaxException, AgencyBusinessException, IOException{
		log.debug("REST request to createPolicy : {}", obj);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		obj.setUserAgent(userAgent);
		
		// Call service
		ProductCarVM data = productCARService.createOrUpdatePolicy(obj, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_CAR_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm ô tô Car.")
    public ResponseEntity<ProductCarVM> update(@Valid @RequestBody ProductCarVM obj) throws URISyntaxException, AgencyBusinessException, IOException{
		log.debug("REST request to update : {}", obj);
		// validate 
		validateUpdateProduct(obj);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		obj.setUserAgent(userAgent);
		
		// Call service
		ProductCarVM data = productCARService.createOrUpdatePolicy(obj, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_CAR_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm ô tô Car.")
    @Deprecated
    public ResponseEntity<UpdateAgreementCarVM> updatePolicy(@Valid @RequestBody UpdateAgreementCarVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean results = agreementService.updateAgreementCar(param.getAgreement(), param.getCar());
		param.setResult(results);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
    
    @GetMapping("/getMinManufactureYear")
    @Timed
    @ApiOperation(value="getMinManufactureYear", notes="Trả về năm sản xuất min của chiếc xe.")
    public ResponseEntity<String> getMinManufactureYear(@RequestParam(value = "carId") String carId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", carId);
		
		// Call service
		String data = productCARService.getMinManufactureYear(carId);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/getMaxManufactureYear")
    @Timed
    @ApiOperation(value="getMaxManufactureYear", notes="Trả về năm sản xuất max của chiếc xe.")
    public ResponseEntity<String> getMaxManufactureYear(@RequestParam(value = "carId") String carId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", carId);
		
		// Call service
		String data = productCARService.getMaxManufactureYear(carId);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/getCarPriceWithYear")
    @Timed
    @ApiOperation(value="getCarPriceWithYear ", notes="Trả về giá trị của chiếc xe với đầu vào là năm sản xuất.")
    public ResponseEntity<String> getCarPriceWithYear (@RequestParam(value = "carId") String carId, @RequestParam(value = "year") Integer year) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", carId);
		
		// Call service
		String data = productCARService.getCarPriceWithYear (carId,year);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/getCarInfo")
    @Timed
    @ApiOperation(value="getCarInfo", notes="Trả về thông tin CAR (Hãng, Model,...). Json kết quả là List<SppCarDTO>")
    public ResponseEntity<List<SppCarDTO>> getCarInfo() throws URISyntaxException, AgencyBusinessException {
		//log.debug("REST request to getPremium : {}", carId);
		
		// Call service
		List<SppCarDTO> data = productCARService.getCarInfo();
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/getCarMakes")
    @Timed
    @ApiOperation(value="getCarMakes", notes="Lấy danh sách hãng xe ô tô")
    public ResponseEntity<List<String>> getCarMakes() throws URISyntaxException, AgencyBusinessException {
		//log.debug("REST request to getPremium : {}", carId);
		
		// Call service
		List<String> data = productCARService.getCarMakes();
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/getCarModel")
    @Timed
    @ApiOperation(value="getCarModel ", notes="Lấy danh sách Model xe theo hãng xe")
    public ResponseEntity<List<SppCarDTO>> getCarModel (@RequestParam(value = "model") String model) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getCarModel : {}", model);
		
		// Call service
		List<SppCarDTO> data = productCARService.getCarModel(model);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/getAllYear")
    @Timed
    @ApiOperation(value="getAllYear", notes="Trả về năm sản xuất của tất cả loại xe")
    public ResponseEntity<List<String>> getAllYear() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllYear : {}");
		
		// Call service
		List<String> data = productCARService.getAllYear();
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/updateImagesPolicy")
    @Timed
    @ApiOperation(value="updateImagesPolicy", notes="Cập nhật ảnh giám định Bảo hiểm ô tô Car.")
    public ResponseEntity<ProductCarImageVM> updateImagesPolicy(@Valid @RequestBody ProductCarImageVM obj) throws URISyntaxException, AgencyBusinessException, IOException{
		log.debug("REST request to createPolicy : {}", obj);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductCarImageVM data = productCARService.updateImagesPolicy(obj, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
