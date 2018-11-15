package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.AdminUserBu;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.domain.MvAgentAgreement;
import com.baoviet.agency.domain.MvClaOutletLocation;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.ConversationDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AdminUserBuRepository;
import com.baoviet.agency.repository.AdminUserRepository;
import com.baoviet.agency.repository.AgreementHisRepository;
import com.baoviet.agency.repository.MvAgentAgreementRepository;
import com.baoviet.agency.repository.MvClaOutletLocationRepository;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ConversationService;
import com.baoviet.agency.service.MailService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.util.PaginationUtil;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;
import com.baoviet.agency.web.rest.vm.AgencyChangePasswordVM;
import com.baoviet.agency.web.rest.vm.ConversationVM;
import com.baoviet.agency.web.rest.vm.ForgotPasswordVM;
import com.baoviet.agency.web.rest.vm.OrderAdminVM;
import com.baoviet.agency.web.rest.vm.OrderCancelVM;
import com.baoviet.agency.web.rest.vm.OrderInfoVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for Agreement/Cart resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/adminUser")
public class AdminResource extends AbstractAgencyResource {

	private final Logger log = LoggerFactory.getLogger(AdminResource.class);
	
	private static final String ENTITY_NAME = "adminUser";

	@Autowired
	private AgreementService agreementService;
	
	@Autowired
    private AdminUserService adminUserService;
	
	@Autowired
    private AdminUserRepository adminUserRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private AdminUserBuRepository adminUserBuRepository;
	
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;
	
	@Autowired
	private MvAgentAgreementRepository mvAgentAgreementRepository;
	
	@Autowired
	private AgreementHisRepository agreementHisRepository;
	
	@PostMapping("/search-admin-cart")
	@Timed
	@ApiOperation(value = "searchOrder", notes = "Hàm tra cứu danh sách giỏ hàng.")
	public ResponseEntity<List<AgreementDTO>> searchCart(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchCart : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchCartAdmin(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/adminUser/search-admin-cart");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_DELETE')")
	@PostMapping("/admin-cancel-order")
	@Timed
	@ApiOperation(value = "cancelOrder", notes = "Từ chối đơn hàng.")
	public ResponseEntity<AgreementDTO> cancelOrder(@RequestBody OrderAdminVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to cancelOrder : {}", param.getGycbhNumber());

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(currentAgency.getId());
		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
			AgreementDTO agreementResult = new AgreementDTO();
			for (AdminUserBu item : lstadminUserBu) {
				AgreementDTO agreement = agreementService.findByGycbhNumberAndDepartmentId(param.getGycbhNumber(), item.getBuId());

				if (agreement != null) {
					// chỉ cho hủy đơn hàng có mã là 93
					if (agreement.getStatusPolicyId().equals("93")) {
						agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_BV_TUCHOI);
						agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_BV_TUCHOI);
						if (!StringUtils.isEmpty(param.getConversationContent())) {
							agreement.setStatusRenewalsId1(param.getConversationContent());
						}
						agreementResult = agreementService.save(agreement);
						// Return data
						return new ResponseEntity<>(agreementResult, HttpStatus.OK);
					} 
				}
			}
			if (StringUtils.isEmpty(agreementResult.getGycbhNumber())) {
				throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không có dữ liệu với đơn hàng " + param.getGycbhNumber());
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/admin-access-order")
	@Timed
	@ApiOperation(value = "accessOrder", notes = "Đồng ý cấp đơn hàng.")
	public ResponseEntity<AgreementDTO> accessOrder(@RequestBody OrderAdminVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to accessOrder : {}", param.getGycbhNumber());

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(currentAgency.getId());
		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
			AgreementDTO agreementResult = new AgreementDTO();
			for (AdminUserBu item : lstadminUserBu) {
				AgreementDTO agreement = agreementService.findByGycbhNumberAndDepartmentId(param.getGycbhNumber(), item.getBuId());

				if (agreement != null) {
					// chỉ cho cấp đơn hàng có mã là 93
					if (agreement.getStatusPolicyId().equals("93")) {
						agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
						agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
						if (!StringUtils.isEmpty(param.getConversationContent())) {
							agreement.setStatusRenewalsId1(param.getConversationContent());
						}
						agreementResult = agreementService.save(agreement);
						// Return data
						return new ResponseEntity<>(agreementResult, HttpStatus.OK);
					} 
				}
			}
			if (StringUtils.isEmpty(agreementResult.getGycbhNumber())) {
				throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không có dữ liệu với đơn hàng " + param.getGycbhNumber());
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-admin-order")
	@Timed
	@ApiOperation(value = "searchOrderBVWait", notes = "Hàm tra cứu danh sách đơn hàng chờ Bảo Việt giải quyết")
	public ResponseEntity<List<AgreementDTO>> searchOrderBVWait(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderBVWait : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderBVWait(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/adminUser/search-admin-order");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PostMapping("/search-order-transport")
	@Timed
	@ApiOperation(value = "searchOrderTransport", notes = "Hàm tra cứu danh sách vận đơn")
	public ResponseEntity<List<AgreementDTO>> searchOrderTransport(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderTransport : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderTransport(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/adminUser/search-order-transport");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PostMapping("/update-order-transport")
	@Timed
	@ApiOperation(value = "updateOrderTransport", notes = "Hàm cập nhật danh sách vận đơn")
	public ResponseEntity<List<AgreementDTO>> updateOrderTransport(@Valid @RequestBody OrderInfoVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updateOrderTransport : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		List<AgreementDTO> data = agreementService.updateOrderTransport(param, currentAgency.getMa());
		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping("/admin-forgotPassword")
	@Timed
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordVM param) throws URISyntaxException {
		log.debug("REST request to forgotPassword : {}", param.getEmail());

		AgencyDTO account = adminUserService.findByEmail(param.getEmail());

		if (account != null) {
			String newPass = AgencyUtils.generateRandomPassword();

			boolean result = adminUserService.changePassword(param.getEmail(), newPass);
			if (result == false) {
				return new ResponseEntity<>("Lỗi khi đổi mật khẩu", HttpStatus.NOT_FOUND);
			}
			// Gửi email
			mailService.sendForgotPasswordMail(account, newPass);
		} else {
			return new ResponseEntity<>("Không tìm thấy địa chỉ email", HttpStatus.NOT_FOUND);
		}

		// Return data
		return new ResponseEntity<>(AppConstants.SUCCESS, HttpStatus.OK);
	}
    
	@PostMapping(path = "/admin-change-password")
	@Timed
	public ResponseEntity<AgencyChangePasswordVM> changePassword(@Valid @RequestBody AgencyChangePasswordVM param) throws AgencyBusinessException{
		final String userLogin = SecurityUtils.getCurrentUserLogin();

		if (!checkOldLength(userLogin, param.getOldPassword())) {
			throw new AgencyBusinessException("oldPassword", ErrorCode.INVALID , "Mật khẩu cũ không chính xác.");
		}

		boolean result = adminUserService.changePassword(userLogin, param.getNewPassword());

		param.setResult(result);
		return new ResponseEntity<>(param, HttpStatus.OK);
	}
	
	@PostMapping("/admin-createCommunication")
    @Timed
    @ApiOperation(value="createCommunication", notes="Tạo hội thoại trao đổi thông tin")
    public ResponseEntity<ConversationDTO> createCommunication(@Valid @RequestBody ConversationVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createCommunication : {}", param);
		
		final String userLogin = SecurityUtils.getCurrentUserLogin();
    	if (SecurityUtils.isCurrentUserInRole(AgencyConstants.ROLE_BAOVIET)) {
    		AgencyDTO existingUser = adminUserService.findByEmail(userLogin);
    		
    		ConversationDTO result = new ConversationDTO();
    		if (existingUser == null) {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(existingUser.getId());
    		List<String> lstDepartment = new ArrayList<>();
    		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
    			for (AdminUserBu item : lstadminUserBu) {
    				lstDepartment.add(item.getBuId());
    			}
    		}
    		if (lstDepartment != null && lstDepartment.size() > 0) {
    			AgreementDTO agreement = agreementService.findByGycbhNumberAndDepartmentId(param.getGycbhNumber(), lstDepartment.get(0));
				if (agreement != null) {
					// Call service
					result = conversationService.saveAdmin(param, existingUser, agreement);
				}
				
				if (result != null && !StringUtils.isEmpty(result.getConversationId())) {
					// Return data
			        return new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không có dữ liệu với đơn hàng " + param.getGycbhNumber());
				}
    		}
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/get-all-agency")
    @Timed
    public ResponseEntity<AgencyDTO> getAllAgency() {
    	final String userLogin = SecurityUtils.getCurrentUserLogin();
//    	if (SecurityUtils.isCurrentUserInRole(AgencyConstants.ROLE_BAOVIET)) {
    		AgencyDTO existingUser = adminUserService.findByEmail(userLogin);
    		
    		if (existingUser == null) {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(existingUser.getId());
    		List<DepartmentDTO> lstDepartment = new ArrayList<>();
    		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
    			for (AdminUserBu item : lstadminUserBu) {
    				// Load department
    				List<DepartmentDTO> lstDepar = loadDepartmentAndAgency(item.getBuId());
    				lstDepartment.addAll(lstDepar);
    			}
    		}
    		if (lstDepartment != null && lstDepartment.size() > 0) {
    			existingUser.setLstDepartment(lstDepartment);
    		}
    		
    		return new ResponseEntity<>(existingUser, HttpStatus.OK);
//    	}
//		return null; 
    }
	
	@PostMapping("/search-agency")
    @Timed
    public ResponseEntity<List<AgencyDTO>> searchAgency(@RequestBody AdminSearchAgencyVM param) throws AgencyBusinessException{
		List<AgencyDTO> lstAgency = new ArrayList<>();
		List<AgencyDTO> result = new ArrayList<>();

		final String userLogin = SecurityUtils.getCurrentUserLogin();
		AgencyDTO existingUser = adminUserService.findByEmail(userLogin);
		
		if (existingUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		lstAgency = adminUserService.searchAgency(param, existingUser.getId());
		
		return new ResponseEntity<>(lstAgency, HttpStatus.OK);
	}
	
	@PostMapping("/admin-get-orderHis-by-gycbhNumber")
    @Timed
    @ApiOperation(value = "getOrderHistoryByGycbhNumber", notes = "Hàm lấy lịch sử đơn hàng")
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByGycbhNumber(@RequestBody OrderCancelVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getOrderHistoryByGycbhNumber");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		List<AgreementHis> lstAgreementHis = new ArrayList<>();
		
		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(currentAgency.getId());
		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
			for (AdminUserBu admin : lstadminUserBu) {
				List<AgreementHis> lstAg = agreementHisRepository.findByGycbhNumberAndBaovietDepartmentId(param.getGycbhNumber(), admin.getBuId());
				if (lstAg != null && lstAg.size() > 0) {
					lstAgreementHis.addAll(lstAg);
				}
			}
		}
		
		if (lstAgreementHis != null && lstAgreementHis.size() == 0) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + param.getGycbhNumber());
		}
		
		List<OrderHistoryDTO> data = agreementService.getOrderHistoryByGycbhNumber(lstAgreementHis);
		if (data != null && data.size() > 0) {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		// Return data
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	// Đơn hàng chờ BV giải quyết: Chờ bảo việt giám định StautusPolicy: 93; Chờ bảo việt cấp đơn StautusPolicy: 91; Chờ Bảo Việt cấp GCNBH (bản cứng): 92
	@GetMapping("/get-wait-agreement")
	@Timed
	@ApiOperation(value = "getWaitAgreement", notes = "Hàm lấy tất cả danh sách hợp đồng chờ bảo việt giải quyết của Admin")
	public ResponseEntity<List<AgreementDTO>> getWaitAgreement(@ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getWaitAgreement");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		Page<AgreementDTO> page = agreementService.getWaitAgreementAdmin(currentAgency.getMa(), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/adminUser/get-wait-agreement");

		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	// Đơn hàng chờ đại lý giải quyết : chờ OTP. StautusPolicy: 81; Yêu cầu bổ sung thông tin: 83; 80: Đang soạn
	@GetMapping("/get-wait-agency")
	@Timed
	@ApiOperation(value = "getWaitAgency", notes = "Hàm lấy tất cả danh sách hợp đồng chờ đại lý giải quyết của Admin")
	public ResponseEntity<List<AgreementDTO>> getWaitAgency(@ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getWaitAgency");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		Page<AgreementDTO> page = agreementService.getWaitAgencyAdmin(currentAgency.getMa(), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/adminUser/get-wait-agency");

		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	private boolean checkOldLength(String userLogin, String oldPassword) {
		AgencyDTO account = adminUserService.findByEmail(userLogin);
		if (null == account) {
			return false;
		}

		// Get oldpass and compare
		String md5Password = DigestUtils.md5DigestAsHex(oldPassword.getBytes());

		// compare password
		if (!StringUtils.equals(md5Password, account.getMatkhau())) {
			log.warn("Old password incorrect, {}", oldPassword);
			return false;
		}

		return true;
	}
	
	private List<DepartmentDTO> loadDepartment(String id) {
		List<DepartmentDTO> lstDepartment = new ArrayList<>();
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(id);
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			for (MvClaOutletLocation item : lstMvClaOutletLocation) {
				DepartmentDTO department = new DepartmentDTO();
				department.setDepartmentId(item.getPrOutletAmsId());
				department.setDepartmentName(item.getPrOutletName());
				lstDepartment.add(department);
			}
		}
		
		List<MvAgentAgreement> lstMvAgentAgreement = mvAgentAgreementRepository.findByAgentCode(id);
		if (lstMvAgentAgreement != null && lstMvAgentAgreement.size() > 0) {
			for (MvAgentAgreement item : lstMvAgentAgreement) {
				DepartmentDTO department = new DepartmentDTO();
				department.setDepartmentId(item.getDepartmentCode());
				department.setDepartmentName(item.getDepartmentName());
				lstDepartment.add(department);
			}
		}
		
		if (lstDepartment != null && lstDepartment.size() > 0) {
			return lstDepartment;
		}
		return null;
	}
	
	private List<DepartmentDTO> loadDepartmentAndAgency(String id) {
		List<DepartmentDTO> lstDepartment = new ArrayList<>();
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(id);
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			for (MvClaOutletLocation item : lstMvClaOutletLocation) {
				DepartmentDTO department = new DepartmentDTO();
				department.setDepartmentId(item.getPrOutletAmsId());
				department.setDepartmentName(item.getPrOutletName());
				
				// lay danh sach dai ly
				List<MvClaOutletLocation> lstLocation = mvClaOutletLocationRepository.findByPrOutletAmsId(item.getPrOutletAmsId());
				List<AgencyDTO> lstAgency = new ArrayList<>();
				if (lstLocation != null && lstLocation.size() > 0) {
					for (MvClaOutletLocation loc : lstLocation) {
						AgencyDTO agency = new AgencyDTO();
						agency.setMa(loc.getOutletAmsId());
						agency.setTen(loc.getOutletName());
						lstAgency.add(agency);
					}
				}
				if (lstAgency != null && lstAgency.size() > 0) {
					department.setLstAgency(lstAgency);
				}
				
				lstDepartment.add(department);
			}
		}
		
		List<MvAgentAgreement> lstMvAgentAgreement = mvAgentAgreementRepository.findByAgentCode(id);
		if (lstMvAgentAgreement != null && lstMvAgentAgreement.size() > 0) {
			for (MvAgentAgreement item : lstMvAgentAgreement) {
				DepartmentDTO department = new DepartmentDTO();
				department.setDepartmentId(item.getDepartmentCode());
				department.setDepartmentName(item.getDepartmentName());
				
				// lay danh sach dai ly
				List<MvAgentAgreement> lstLocation = mvAgentAgreementRepository.findByDepartmentCode(item.getDepartmentCode());
				List<AgencyDTO> lstAgency = new ArrayList<>();
				if (lstLocation != null && lstLocation.size() > 0) {
					for (MvAgentAgreement loc : lstLocation) {
						AgencyDTO agency = new AgencyDTO();
						agency.setMa(loc.getAgentCode());
						agency.setTen(loc.getAgentName());
						lstAgency.add(agency);
					}
				}
				if (lstAgency != null && lstAgency.size() > 0) {
					department.setLstAgency(lstAgency);
				}
				
				lstDepartment.add(department);
			}
		}
		
		if (lstDepartment != null && lstDepartment.size() > 0) {
			return lstDepartment;
		}
		return null;
	}
	
	
}
