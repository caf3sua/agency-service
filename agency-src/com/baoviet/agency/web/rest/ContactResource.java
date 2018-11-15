package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.CategoryReminder;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgentReminderDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgentReminderRepository;
import com.baoviet.agency.repository.CategoryReminderRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.AgentReminderService;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.mapper.AgentReminderMapper;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ContactCodeSearchVM;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;
import com.baoviet.agency.web.rest.vm.ContactUpdateVM;
import com.baoviet.agency.web.rest.vm.ReminderCreateVM;
import com.baoviet.agency.web.rest.vm.ReminderSearchVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "contact")
public class ContactResource extends AbstractAgencyResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    private static final String ENTITY_NAME = "contact";

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private AgentReminderService agentReminderService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private CategoryReminderRepository categoryReminderRepository;
    
    @Autowired
    private AgentReminderRepository agentReminderRepository;
    
    @Autowired
    private AgentReminderMapper agentReminderMapper;
    
//    @PostMapping("/add")
//    @Timed
//    @ApiResponses({
//    	@ApiResponse(code = 403, message ="Token invalids")
//    	, @ApiResponse(code = 406, message ="psid not exits")
//    	, @ApiResponse(code = 403, message ="TokenKey is invalid")
//    })
//    @ApiOperation(value="addContact", notes="Hàm tạo mới khách hàng, truyền vào object Contact. Thành công thì trả lại object contact")
//    public ResponseEntity<ContactDTO> addContact(@Valid @RequestBody ContactVM param) throws URISyntaxException, AgencyBusinessException {
//		log.debug("REST request to addContact : {}", param);
//		
//		// Check param input
//		if (param.getContactId() != null) {
//            return ResponseEntity.badRequest()
//                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
//                .body(null);
//		}
//		// Call service
//		ContactDTO data = contactService.save(getContactInfo(param));
//		
//		// Return data
//        return new ResponseEntity<>(data, HttpStatus.OK);
//    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CONTACT_CREATE')")
    @PostMapping("/create")
    @Timed
    @ApiResponses({
    	@ApiResponse(code = 403, message ="Token invalids")
    	, @ApiResponse(code = 406, message ="psid not exits")
    	, @ApiResponse(code = 403, message ="TokenKey is invalid")
    })
    @ApiOperation(value="createContact", notes="Hàm tạo mới khách hàng, truyền vào object Contact. Thành công thì trả lại object contact")
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactCreateVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to addContact : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Validate Số điện thoại, Số CMT/MST, Email
		Contact contactTmp = null;
		contactTmp = contactRepository.findOneByPhoneAndType(param.getPhone(), currentAgency.getMa());
		if (contactTmp != null) {
			throw new AgencyBusinessException("phone", ErrorCode.INVALID, "Số điện thoại " + param.getPhone() + " đã được sử dụng!");
		}
		
		contactTmp = contactRepository.findOneByIdNumberAndType(param.getIdNumber(), currentAgency.getMa());
		if (contactTmp != null) {
			throw new AgencyBusinessException("idNumber", ErrorCode.INVALID, "Số CMT " + param.getIdNumber() + " đã được sử dụng!");
		}
		
		contactTmp = contactRepository.findOneByEmailAndType(param.getEmail(), currentAgency.getMa());
		if (contactTmp != null) {
			throw new AgencyBusinessException("email", ErrorCode.INVALID, "Địa chỉ " + param.getEmail() + " đã được sử dụng!");
		}
		
		// Call service
		ContactDTO data = contactService.create(getContactCreate(param), param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CONTACT_CREATE')")
    @GetMapping("/delete-contact/{contactId}")
    @Timed
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to deleteContact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// Get one
		ContactDTO contactDTO = contactService.findOne(contactId, currentAgency.getMa());
		if (contactDTO == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Không tìm thấy khách hàng");
		}
		
		// Check khách hàng cấp đơn hay chưa?
		Pageable pageable = new PageRequest(0, 5, null);
		Page<AgreementDTO> page = agreementService.findAllByContactId(contactId, currentAgency.getMa(), pageable);
		if (page.getContent().size() > 0) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Khách hàng đã được cấp đơn, không thể xóa");
		}
		
		// delete
		contactService.delete(contactId);

		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private ContactDTO getContactCreate(ContactCreateVM param) throws AgencyBusinessException {
    	// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
    	
		String contactCode = contactService.generateContactCode(currentAgency.getMa());
		
    	ContactDTO pa = new ContactDTO();
		pa.setContactName(param.getContactName());
		pa.setContactCode(contactCode);
		pa.setContactSex(param.getContactSex());
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
		pa.setOccupation(param.getOccupation());
		// khi thêm mới mặc định là KH tiềm năng không cho truyền loại khách hàng vào
		pa.setGroupType("POTENTIAL");
		if(!StringUtils.isEmpty(param.getFacebookId())) {
			pa.setFacebookId(param.getFacebookId());
		}
		return pa;
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CONTACT_EDIT')")
    @PostMapping("/update")
    @Timed
    public ResponseEntity<ContactDTO> updateContact(@Valid @RequestBody ContactUpdateVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getInfoGYCBH : {}", param);
		
		// Check param input
		if (param.getContactId() == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Id khách hàng không được để trống");
		}

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Validate Số điện thoại, Số CMT/MST, Email
		Contact contactTmp = null;
		contactTmp = contactRepository.findOneByPhoneAndType(param.getPhone(), currentAgency.getMa());
		if (contactTmp != null && !StringUtils.equals(param.getContactId(), contactTmp.getContactId())) {
			throw new AgencyBusinessException("phone", ErrorCode.INVALID, "Số điện thoại " + param.getPhone() + " đã được sử dụng!");
		}
		
		contactTmp = contactRepository.findOneByIdNumberAndType(param.getIdNumber(), currentAgency.getMa());
		if (contactTmp != null && !StringUtils.equals(param.getContactId(), contactTmp.getContactId())) {
			throw new AgencyBusinessException("idNumber", ErrorCode.INVALID, "Số CMT " + param.getIdNumber() + " đã được sử dụng!");
		}
		
		contactTmp = contactRepository.findOneByEmailAndType(param.getEmail(), currentAgency.getMa());
		if (contactTmp != null && !StringUtils.equals(param.getContactId(), contactTmp.getContactId())) {
			throw new AgencyBusinessException("email", ErrorCode.INVALID, "Địa chỉ " + param.getEmail() + " đã được sử dụng!");
		}
		
		
		// Call service
		ContactDTO data = contactService.update(param, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/create-reminder")
    @Timed
    @ApiResponses({
    	@ApiResponse(code = 403, message ="Token invalids")
    	, @ApiResponse(code = 406, message ="psid not exits")
    	, @ApiResponse(code = 403, message ="TokenKey is invalid")
    })
    @ApiOperation(value="createReminder", notes="Hàm tạo mới thông báo cho khách hàng")
    public ResponseEntity<AgentReminderDTO> createReminder(@Valid @RequestBody ReminderCreateVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createReminder : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		AgentReminderDTO data = agentReminderService.create(param, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/get-all-contact-reminder")
    @Timed
    public ResponseEntity<List<AgentReminderDTO>> getAllReminder() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllReminder contact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get all contact
		List<AgentReminderDTO> data = agentReminderService.getAll(currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/get-count-all-contact-reminder")
    @Timed
    public ResponseEntity<Integer> getCountAllReminder() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllReminder contact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get all contact
		List<AgentReminderDTO> data = agentReminderService.getAll(currentAgency.getMa());
		int numberRemiber = 0;
		if (data != null && data.size() > 0) {
			numberRemiber = data.size();
		}
		
		// Return data
        return new ResponseEntity<>(numberRemiber, HttpStatus.OK);
    }
    
    @GetMapping("/get-all-reminder-by-contact/{contactId}")
    @Timed
    public ResponseEntity<List<AgentReminderDTO>> getAllReminderByContact(@PathVariable String contactId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllReminderByContact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get all contact
		List<AgentReminderDTO> data = agentReminderService.findByContactId(contactId, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/get-reminder-by-id/{reminderId}")
    @Timed
    public ResponseEntity<AgentReminderDTO> getReminderById(@PathVariable String reminderId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllReminderByContact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get all contact
		AgentReminderDTO data = agentReminderService.findByReminderId(reminderId, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CONTACT_VIEW')")
    @PostMapping("/search-reminder")
    @Timed
    public ResponseEntity<List<AgentReminderDTO>> searchReminder(@Valid @RequestBody ReminderSearchVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchContact : {}", param);
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		List<AgentReminderDTO> data = agentReminderService.searchReminder(param, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/get-all-ower")
    @Timed
    public ResponseEntity<List<ContactDTO>> getAllOwner() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAll contact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get all contact
		List<ContactDTO> data = contactService.findAllByType(currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    
    @PostMapping("/search")
    @Timed
    public ResponseEntity<List<ContactDTO>> searchContact(@Valid @RequestBody ContactSearchVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchContact : {}", param);
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		List<ContactDTO> data = contactService.searchContact(param, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/get-by-code")
    @Timed
    public ResponseEntity<ContactDTO> getByContactCode(@Valid @RequestBody ContactCodeSearchVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getByContactCode");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service to get menu
		ContactDTO contactDTO = contactService.findOneByContactCodeAndType(param.getContactCode(), currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(contactDTO, HttpStatus.OK);
    }
    
    @GetMapping("/get-by-id/{id}")
    @Timed
    public ResponseEntity<ContactUpdateVM> getById(@PathVariable Long id) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getById");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service to get menu
		ContactDTO contact = contactService.findOne(String.valueOf(id), currentAgency.getMa());
		if (contact == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Không tồn tại mã contactId");
		}
		
		// Convert to ContactUpdateVM
		ContactUpdateVM result = contactService.convertContactToVM(contact, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/get-by-phone/{phoneNumber}")
    @Timed
    public ResponseEntity<ContactDTO> getByContactPhoneNumber(@PathVariable String phoneNumber) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getByContactPhoneNumber");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service to get menu
		ContactDTO contactDTO = contactService.findOneByPhoneAndType(phoneNumber, currentAgency.getMa());
		
		// Return data
        return new ResponseEntity<>(contactDTO, HttpStatus.OK);
    }
    
    @GetMapping("/get-count-contact-reminder/{numberDay}")
    @Timed
    public ResponseEntity<Integer> getCountReminder(@PathVariable Integer numberDay) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getCountReminder contact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		int count = 0;		
		// Get all contact
		List<AgentReminderDTO> data = agentReminderService.getCountReminder(currentAgency.getMa(), numberDay);
		if (data != null && data.size() > 0) {
			count = data.size();
		}
		// Return data
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/delete-reminder/{id}")
    @ApiOperation(value="deleteReminder", notes="Xóa nhắc nhở")
    @Timed
    public ResponseEntity<Integer> deleteReminder(@PathVariable String id) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to deleteReminder contact");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// delete
		agentReminderService.delete(currentAgency.getMa(), id);

		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/getCategoryReminder")
	@ApiOperation(value="getCategoryReminder", notes="Lấy tất cả danh mục nhắc nhở")
	@Timed
	public ResponseEntity<List<CategoryReminder>> getCategoryReminder() throws URISyntaxException {
		log.debug("REST request to getAddress");

		List<CategoryReminder> data = categoryReminderRepository.findAll();

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
