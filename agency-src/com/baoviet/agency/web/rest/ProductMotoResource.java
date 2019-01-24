package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.ProductMotoService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.PremiumMotoVM;
import com.baoviet.agency.web.rest.vm.ProductMotoMOMOVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementMotoVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency MOTO resource.
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/moto")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/moto", description = "<a href=\"/content/extra_doc/moto.html\" target=\"_blank\">External document</a>")
public class ProductMotoResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductMotoResource.class);

    private static final String ENTITY_NAME = "moto";

    @Autowired
    private ProductMotoService productMotoService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
	private ContactRepository contactRepository;
    
    @Autowired
	private CodeManagementService codeManagementService;
    
    private final static String DEPARTMENT_ID_MOMO = "A000009218";
    
    @Autowired
    private ContactService contactService;
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm xe máy.")
    public ResponseEntity<PremiumMotoVM> getPremium(@Valid @RequestBody PremiumMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumMotoVM data = productMotoService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductMotoVM> createPolicy(@Valid @RequestBody ProductMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductMotoVM data = productMotoService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_CREATE')")
    @PostMapping("/createPolicy-MoMo")
    @Timed
    @ApiOperation(value="createPolicyMoMo", notes="Tạo yêu cầu Bảo hiểm xe máy MoMo")
    public ResponseEntity<ProductMotoVM> createPolicyMoMo(@Valid @RequestBody ProductMotoMOMOVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		ProductMotoVM motoVM = convertToVM(currentAgency, param);
		
		// Call service
		ProductMotoVM data = productMotoService.createOrUpdatePolicyMOMO(motoVM, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductMotoVM> update(@Valid @RequestBody ProductMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductMotoVM data = productMotoService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm xe máy.")
    @Deprecated
    public ResponseEntity<UpdateAgreementMotoVM> updatePolicy(@Valid @RequestBody UpdateAgreementMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean result = agreementService.updateAgreementMoto(param.getAgreement(), param.getMoto());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
    
    private ProductMotoVM convertToVM(AgencyDTO currentAgency, ProductMotoMOMOVM param) throws AgencyBusinessException{
    	ProductMotoVM result = new ProductMotoVM();
    	
    	log.debug("REST request to validateAndSetValueContactMOMO : {}", param);

		if (StringUtils.isEmpty(param.getContactPhone())) {
			throw new AgencyBusinessException("contactPhone", ErrorCode.NULL_OR_EMPTY, "Số điện thoại khách hàng không được để trống");
		} else {
			// kiem tra dinh dang so dien thoai
			if (!ValidateUtils.isPhone(param.getContactPhone())) {
				throw new AgencyBusinessException("contactPhone", ErrorCode.INVALID, "Số điện thoại không đúng định dạng");
			}	
		}
		
		Contact contactTmp = contactRepository.findOneByPhoneAndType(param.getContactPhone(), currentAgency.getMa());

		if (contactTmp != null) {
			result.setContactCode(contactTmp.getContactCode());
		} else {
			if (StringUtils.isEmpty(param.getContactName())) {
				throw new AgencyBusinessException("contactName", ErrorCode.NULL_OR_EMPTY, "Tên khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(param.getContactIdNumber())) {
				throw new AgencyBusinessException("contactIdNumber", ErrorCode.NULL_OR_EMPTY, "CMT khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(param.getContactEmail())) {
				throw new AgencyBusinessException("contactEmail", ErrorCode.NULL_OR_EMPTY, "Email khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(param.getContactDob())) {
				throw new AgencyBusinessException("contactDob", ErrorCode.NULL_OR_EMPTY, "Ngày sinh khách hàng không được để trống");
			} else {
				// Validate ngay sinh tu 18 - 85
				if (param.getContactDob() != null) {
					int utageYCBH = DateUtils.countYears(DateUtils.str2Date(param.getContactDob()), new Date());
					if (utageYCBH < 18 || utageYCBH > 85) {
						throw new AgencyBusinessException("dateOfBirth", ErrorCode.INVALID,
								"Khách hàng phải từ 18 đến 85 tuổi");
					}
				}
			}
			if (StringUtils.isEmpty(param.getContactAddress())) {
				throw new AgencyBusinessException("contactAddress", ErrorCode.NULL_OR_EMPTY, "Địa chỉ khách hàng không được để trống");
			}
			
			ContactCreateVM contactVM = new ContactCreateVM();
			if (StringUtils.isNotEmpty(param.getContactName())) {
				contactVM.setContactName(param.getContactName());
	    	}
	    	if (StringUtils.isNotEmpty(param.getContactDob())) {
	    		contactVM.setDateOfBirth(DateUtils.str2Date(param.getContactDob()));
	    	}
	    	if (StringUtils.isNotEmpty(param.getContactPhone())) {
	    		contactVM.setPhone(param.getContactPhone());
	    	}
	    	if (StringUtils.isNotEmpty(param.getContactEmail())) {
	    		contactVM.setEmail(param.getContactEmail());
	    	}
	    	if (StringUtils.isNotEmpty(param.getContactIdNumber())) {
	    		contactVM.setIdNumber(param.getContactIdNumber());
	    	}
	    	contactVM.setCategoryType(AgencyConstants.CONTACT_CATEGORY_TYPE.PERSON);
	    	if (StringUtils.isNotEmpty(param.getContactAddress())) {
	    		contactVM.setHomeAddress(param.getContactAddress());
	    	}
			
			
			ContactDTO data = contactService.create(getContactCreate(contactVM, currentAgency), contactVM);
			if (data != null) {
				result.setContactCode(data.getContactCode());
				if (!StringUtils.isEmpty(param.getContactName())) {
					data.setContactName(param.getContactName());
				}
				if (!StringUtils.isEmpty(param.getContactIdNumber())) {
					data.setIdNumber(param.getContactIdNumber());
				}
				if (!StringUtils.isEmpty(param.getContactEmail())) {
					data.setEmail(param.getContactEmail());
				}
				if (!StringUtils.isEmpty(param.getContactDob())) {
					data.setDateOfBirth(DateUtils.str2Date(param.getContactDob()));
				}
				if (!StringUtils.isEmpty(param.getContactAddress())) {
					data.setHomeAddress(param.getContactAddress());
				}
				// update contact
				contactService.save(data);
			}
		}
		CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("MOT");
		result.setGycbhNumber(codeManagementDTO.getIssueNumber());
		result.setDepartmentId(DEPARTMENT_ID_MOMO);
    	
    	if (param.getReceiverUser() != null) {
    		result.setReceiverUser(param.getReceiverUser());
    	}
    	if (param.getInvoiceInfo() != null) {
    		result.setInvoiceInfo(param.getInvoiceInfo());
    	}
    	
    	if (StringUtils.isNotEmpty(param.getReceiveMethod())) {
    		result.setReceiveMethod(param.getReceiveMethod());
    	}
    	if (StringUtils.isNotEmpty(param.getOldGycbhNumber())) {
    		result.setOldGycbhNumber(param.getOldGycbhNumber());
    	}
    	if (StringUtils.isNotEmpty(param.getInsuredName())) {
    		result.setInsuredName(param.getInsuredName());
    	}
    	if (StringUtils.isNotEmpty(param.getInsuredAddress())) {
    		result.setInsuredAddress(param.getInsuredAddress());
    	}
    	if (StringUtils.isNotEmpty(param.getRegistrationNumber())) {
    		result.setRegistrationNumber(param.getRegistrationNumber());
    	}
    	if (StringUtils.isNotEmpty(param.getSokhung())) {
    		result.setSokhung(param.getSokhung());
    	}
    	if (StringUtils.isNotEmpty(param.getSomay())) {
    		result.setSomay(param.getSomay());
    	}
    	if (StringUtils.isNotEmpty(param.getHieuxe())) {
    		result.setHieuxe(param.getHieuxe());
    	}
    	if (StringUtils.isNotEmpty(param.getThoihantu())) {
    		result.setThoihantu(param.getThoihantu());
    	}
    	if (StringUtils.isNotEmpty(param.getTypeOfMoto())) {
    		result.setTypeOfMoto(param.getTypeOfMoto());
    	}
    	result.setTndsbbCheck(param.getTndsbbCheck());
    	if (param.getTndsbbPhi() > 0) {
    		result.setTndsbbPhi(param.getTndsbbPhi());
    	} 
    	result.setTndstnCheck(param.getTndstnCheck());
    	if (param.getTndstnSotien() > 0) {
    		result.setTndstnSotien(param.getTndstnSotien());
    	}
    	if (param.getTndstnPhi() > 0) {
    		result.setTndstnPhi(param.getTndstnPhi());
    	}
    	result.setNntxCheck(param.getNntxCheck());
    	if (param.getNntxStbh() > 0) {
    		result.setNntxStbh(param.getNntxStbh());
    	}
    	if (param.getNntxSoNguoi() > 0) {
    		result.setNntxSoNguoi(param.getNntxSoNguoi());
    	}
    	if (param.getNntxPhi() > 0) {
    		result.setNntxPhi(param.getNntxPhi());
    	}
    	result.setChaynoCheck(param.getChaynoCheck());
    	if (param.getChaynoStbh() > 0) {
    		result.setChaynoStbh(param.getChaynoStbh());
    	}
    	if (param.getChaynoPhi() > 0) {
    		result.setChaynoPhi(param.getChaynoPhi());
    	}
    	if (param.getTongPhi() > 0) {
    		result.setTongPhi(param.getTongPhi());
    	}
    	
    	return result;
    }
    
    private ContactDTO getContactCreate(ContactCreateVM param, AgencyDTO currentAgency) throws AgencyBusinessException {
    	// Get current agency
		String contactCode = contactService.generateContactCode(currentAgency.getMa());
		
    	ContactDTO pa = new ContactDTO();
		pa.setContactName(param.getContactName());
		pa.setContactCode(contactCode);
		pa.setContactSex("1");// mặc định
		if (param.getDateOfBirth() != null) {
			pa.setDateOfBirth(param.getDateOfBirth());	
		} else {
			pa.setDateOfBirth(DateUtils.str2Date("01/01/0001"));
		}
		
		pa.setHomeAddress(param.getHomeAddress());
		pa.setPhone(param.getPhone());
		pa.setEmail(param.getEmail());
		pa.setIdNumber(param.getIdNumber());
		pa.setType(currentAgency.getMa());
		// khi thêm mới mặc định là KH tiềm năng không cho truyền loại khách hàng vào
		pa.setGroupType(AgencyConstants.CONTACT_GROUP_TYPE.POTENTIAL);
		// Category : PERSON/ORGANIZATION. Tạm thời mặc định MOMO là cá nhân
		pa.setCategoryType(AgencyConstants.CONTACT_CATEGORY_TYPE.PERSON);
		
		return pa;
    }
}
