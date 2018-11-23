package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.bean.AgreementNophiDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AgreementSearchDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgreementHisRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.service.AgreementNoPhiService;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.AnchiService;
import com.baoviet.agency.service.ProductCommonService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.util.PaginationUtil;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.AgreementNoPhiVM;
import com.baoviet.agency.web.rest.vm.AgreementYcbhOfflineVM;
import com.baoviet.agency.web.rest.vm.OrderCancelVM;
import com.baoviet.agency.web.rest.vm.OtpVM;
import com.baoviet.agency.web.rest.vm.ProductBaseVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for Agreement/Cart resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/agreement")
public class AgreementResource extends AbstractAgencyResource {

	private final Logger log = LoggerFactory.getLogger(AgreementResource.class);
	
	private static final String ENTITY_NAME = "agreement";

	@Autowired
	private AgreementService agreementService;
	
	@Autowired
	private ProductCommonService productCommonService;
	
	@Autowired
	private AgreementNoPhiService agreementNoPhiService;
	
	@Autowired
	private AnchiService anchiService;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private AgreementHisRepository agreementHisRepository;
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-order")
	@Timed
	@ApiOperation(value = "searchOrder", notes = "Hàm tra cứu danh sách đơn hàng.")
	public ResponseEntity<List<AgreementDTO>> searchOrder(@Valid @RequestBody SearchAgreementVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchAgreement : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.search(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@GetMapping("/get-all-order")
	@Timed
	@Deprecated
	@ApiOperation(value = "getAllOrder", notes = "Hàm lấy tất cả danh sách đơn hàng đã hoàn thành.")
	public ResponseEntity<List<Agreement>> getAllOrder() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAll");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		List<Agreement> data = agreementService.findAllByAgentId(currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@GetMapping("/get-by-id/{agreementId}")
    @Timed
    public <T extends ProductBaseVM> ResponseEntity<T> getById(@PathVariable String agreementId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getById");
	
		AgreementSearchDTO result = new AgreementSearchDTO();
		
		// Get current agency
		AgreementDTO data = null;
		
		// For admin of baoviet
		if (SecurityUtils.isCurrentUserInRole(AgencyConstants.ROLE_BAOVIET)) {
			data = agreementService.findById(agreementId);
		} else {
			// For agency
			AgencyDTO currentAgency = getCurrentAccount();
			data = agreementService.findById(agreementId, currentAgency.getMa());
		}
		
		
		if (data == null) {
			throw new AgencyBusinessException(agreementId, ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + agreementId);
		}
		
		// add product concurrent object
		result.setObjAgreement(data);
		productCommonService.addProductObject(data.getLineId(), data.getGycbhId(), result);
		
		// Convert to T
		T resultData = productCommonService.convertProductObjectToVM(result, "0");
		
		// Return data
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_DELETE')")
	@PostMapping("/cancel")
	@Timed
	@ApiOperation(value = "cancelAgreement", notes = "Hủy đơn hàng.")
	public ResponseEntity<AgreementDTO> cancelAgreement(@RequestBody OrderCancelVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to cancelOrder : {}", param.getGycbhNumber());

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());

		if (agreement == null) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng với mã: " + param.getGycbhNumber());
		}

		// chỉ cho hủy đơn hàng có mã là 90 hoặc 93
		if (agreement.getStatusPolicyId().equals("90") || agreement.getStatusPolicyId().equals("93")) {
			agreement.setStatusPolicyId("89");
			agreement.setStatusPolicyName("Đại lý, Khách hàng hủy đơn");
			String khachhanghuy = currentAgency.getMa() + " hủy đơn hàng";
			agreement.setStatusRenewalsId1(khachhanghuy);
			AgreementDTO agreementSave = agreementService.save(agreement);
			// Return data
			return new ResponseEntity<>(agreementSave, HttpStatus.OK);
		} else {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không hủy được đơn hàng với mã: " + param.getGycbhNumber());
		}
	}
	
	@PostMapping("/get-by-gycbhNumber")
    @Timed
    public ResponseEntity<AgreementDTO> getAgreementByGycbhNumber(@RequestBody OrderCancelVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAgreementByGycbhNumber");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		AgreementDTO data = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());
		
		if (data == null) {
			throw new AgencyBusinessException(param.getGycbhNumber(), ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + param.getGycbhNumber());
		}
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@GetMapping("/get-by-contactId/{contactId}")
    @Timed
    public ResponseEntity<List<AgreementDTO>> getBycontactId(@PathVariable String contactId, @ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getById");
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		Page<AgreementDTO> page = agreementService.findAllByContactId(contactId, currentAgency.getMa(), pageable);
		// lưu tổng trên headers
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/get-by-contactId");
		if (page == null) {
			throw new AgencyBusinessException(contactId, ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + contactId);
		}

		// Return data
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
	
	/*
	 * ------------------------------------------------- An chi
	 * ----------------- -------------------------------------------------
	 */
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_ANCHI_CREATE')")
	@PostMapping("/createYcbhAnchi")
    @Timed
    @ApiOperation(value="createYcbhAnchi", notes="Tạo yêu cầu bảo hiểm bởi ấn chỉ")
    public ResponseEntity<AgreementAnchiVM> createYcbhAnchi(@Valid @RequestBody AgreementAnchiVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createYcbhAnchi : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Anchi anchi = anchiService.getBySoAnchi(param.getSoAnchi(), currentAgency.getMa());
		if (anchi != null) {
			// TH update không check anchi
			if (!StringUtils.equals(anchi.getAnchiId().trim(), param.getAnchiId().trim())) {
				throw new AgencyBusinessException("soAnchi", ErrorCode.INVALID, "Ấn chỉ đang được sử dụng với số ấn chỉ: " + param.getSoAnchi());					
			}
		}	
		
		// Call service
		AgreementAnchiVM data = agreementService.createOrUpdateYcbhAnchi(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@PostMapping("/getAnchi-by-gycbhNumber")
    @Timed
    public ResponseEntity<AgreementAnchiVM> getAnchiByGycbhNumber(@RequestBody OrderCancelVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAgreementByGycbhNumber");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		AgreementDTO data = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());
		
		if (data == null) {
			throw new AgencyBusinessException(param.getGycbhNumber(), ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + param.getGycbhNumber());
		}
		
		AgreementAnchiVM anchi = agreementService.getAnchiByGycbhNumber(data, currentAgency);
		
		// Return data
        return new ResponseEntity<>(anchi, HttpStatus.OK);
    }
	
	/*
	 * ------------------------------------------------- Offline
	 * ----------------- -------------------------------------------------
	 */
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_OFFLINE_CREATE')")
	@PostMapping("/createYcbhOffline")
    @Timed
    @ApiOperation(value="createYcbhOffline", notes="Tạo yêu cầu bảo hiểm Offline")
    public ResponseEntity<AgreementYcbhOfflineVM> createYcbhOffline(@Valid @RequestBody AgreementYcbhOfflineVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createYcbhOffline : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		AgreementYcbhOfflineVM data = agreementService.createOrUpdateYcbhOffline(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@PostMapping("/getYcbhOffline-by-gycbhNumber")
    @Timed
    public ResponseEntity<AgreementYcbhOfflineVM> getYcbhOfflineByGycbhNumber(@RequestBody OrderCancelVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getYcbhOfflineByGycbhNumber");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		AgreementDTO data = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());
		
		if (data == null) {
			throw new AgencyBusinessException(param.getGycbhNumber(), ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + param.getGycbhNumber());
		}
		
		AgreementYcbhOfflineVM offline = agreementService.getYcbhOfflineByGycbhNumber(data, currentAgency);
		
		// Return data
        return new ResponseEntity<>(offline, HttpStatus.OK);
    }
	
	/*
	 * -------------------------------------------------  Nợ Phí
	 * ----------------- -------------------------------------------------
	 */
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_NOPHI_VIEW')")
	@PostMapping("/search-order-nophi")
	@Timed
	@ApiOperation(value = "searchOrderNophi", notes = "Hàm tra cứu danh sách đơn hàng nợ phí.")
	public ResponseEntity<List<AgreementNophiDTO>> searchOrderNophi(@Valid @RequestBody SearchAgreementVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchAgreement : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementNophiDTO> page = agreementService.searchNophi(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order-nophi");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_NOPHI_CREATE')")
	@PostMapping("/create-agreement-nophi")
    @Timed
    @ApiOperation(value="createAgrementNoPhi", notes="Tạo nợ phí cho đơn hàng")
    public ResponseEntity<AgreementNoPhiVM> createAgrementNoPhi(@Valid @RequestBody AgreementNoPhiVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createAgrementNoPhi : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		AgreementNoPhiVM data = agreementService.createOrUpdateNoPhi(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_NOPHI_EDIT')")
	@PostMapping("/update-agreement-nophi")
    @Timed
    @ApiOperation(value="updateAgrementNoPhi", notes="Cập nhật nợ phí cho đơn hàng")
    public ResponseEntity<AgreementNoPhiVM> updateAgrementNoPhi(@Valid @RequestBody AgreementNoPhiVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updateAgrementNoPhi : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		AgreementNoPhiVM data = agreementService.createOrUpdateNoPhi(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_NOPHI_DELETE')")
	@GetMapping("/delete-agreement-nophi/{id}")
    @Timed
    @ApiOperation(value="deleteAgrementNoPhi", notes="Xóa nợ phí cho đơn hàng")
    public ResponseEntity<Integer> deleteAgrementNoPhi(@PathVariable String id) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to deleteAgrementNoPhi {}", id);
	
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// delete
		agreementNoPhiService.delete(currentAgency.getMa(), id);

		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
	@PostMapping("/resend-confirm-email")
	@Timed
	@ApiOperation(value = "resend-confirm-email", notes = "Gửi lại email xác nhận đơn hàng.")
	public ResponseEntity<AgreementDTO> resnedEmail(@RequestBody OrderCancelVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to resend-confirm-email : {}", param.getGycbhNumber());

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());

		if (agreement == null) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng với mã: " + param.getGycbhNumber());
		}

		// chỉ cho gửi lại email với các TH đã thanh toán : 91
		if (agreement.getStatusPolicyId().equals("91")) {
			agreement.setCancelPolicySupport3(1d);	// = 1 để gửi email. sau khi gửi xong sẽ = 0
			AgreementDTO agreementSave = agreementService.save(agreement);
			// Return data
			return new ResponseEntity<>(agreementSave, HttpStatus.OK);
		} else {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không xác nhận được Email của đơn hàng với mã: " + param.getGycbhNumber());
		}
	}
	
	@GetMapping("/create-agrement-taituc/{agreementId}")
    @Timed
    public <T extends ProductBaseVM> ResponseEntity<T> createAgrementTaiTuc(@RequestParam String agreementId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createAgrementTaiTuc", agreementId);
	
		AgreementSearchDTO result = new AgreementSearchDTO();
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		AgreementDTO data = agreementService.findById(agreementId, currentAgency.getMa());
		if (data == null) {
			throw new AgencyBusinessException(agreementId, ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + agreementId);
		}
		
		// check taituc
		if (StringUtils.equals(data.getStatusPolicyId(), "91") || StringUtils.equals(data.getStatusPolicyId(), "100")) {
			if (data.getExpiredDate() != null) {
				Date now = new Date();
				int sn = DateUtils.getNumberDaysBetween2Date(now, data.getExpiredDate());
				if (0 < sn && sn <= 30) {
					data.setCanTaituc(true);
				}
			}
		} else {
			data.setCanTaituc(false);
		}
		
		if (!data.getCanTaituc()) {
			throw new AgencyBusinessException(agreementId, ErrorCode.INVALID, "Không thỏa mãn điều kiện tái tục với đơn hàng mã " + agreementId);
		}
		
		// add product concurrent object
		productCommonService.addProductObject(data.getLineId(), data.getGycbhId(), result);
		result.setObjAgreement(data);
		
		// Convert to T
		T resultData = productCommonService.convertProductObjectToVM(result, "1");
		
		
		// Return data
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
	
	/*
	 * ------------------------------------------------- OTP
	 * ----------------- -------------------------------------------------
	 */
	@PostMapping("/check-OTP")
    @Timed
    @ApiOperation(value = "check-OTP", notes = "Kiểm tra OTP và cập nhật trạng thái đơn hàng")
    public ResponseEntity<AgreementDTO> checkOTP(@RequestBody OtpVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to checkOTP");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgreementDTO data = agreementService.findByGycbhNumberAndAgentIdAndOTP(param.getGycbhNumber(), currentAgency.getMa(), param.getOtp());
		
		if (data == null) {
			throw new AgencyBusinessException("OTP", ErrorCode.INVALID, "Mã xác thực của bạn không chính xác");
		}
		
		AgreementDTO dataUpdate = agreementService.updateOTP(data);
				
		// Return data
        return new ResponseEntity<>(dataUpdate, HttpStatus.OK);
    }	
	
	@PostMapping("/resend-confirm-otp")
	@Timed
	@ApiOperation(value = "resend-confirm-otp", notes = "Gửi lại otp xác nhận đơn hàng.")
	public ResponseEntity<AgreementDTO> resnedOtp(@RequestBody OrderCancelVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to resend-confirm-otp : {}", param.getGycbhNumber());

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());

		if (agreement == null) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng với mã: " + param.getGycbhNumber());
		}

		// chỉ cho gửi lại otp với các TH đang chờ otp : 81
		if (agreement.getStatusPolicyId().equals("81")) {
			// tạo otp mới
			String otp = AgencyUtils.getRandomOTP();
			
			agreement.setOtp(otp);
			AgreementDTO agreementSave = agreementService.save(agreement);
			
			Contact co = contactRepository.findOne(agreement.getContactId());
			if (co != null) {
				sendSms(co, agreementSave);
			}
			
			// Return data
			return new ResponseEntity<>(agreementSave, HttpStatus.OK);
		} else {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không xác nhận được Số điện thoại của đơn hàng với mã: " + param.getGycbhNumber());
		}
	}
	
	// Đơn hàng chờ đại lý giải quyết : chờ OTP. StautusPolicy: 81; Yêu cầu bổ sung thông tin: 83; 80: Đang soạn
	@GetMapping("/get-wait-agency")
	@Timed
	@ApiOperation(value = "getWaitAgency", notes = "Hàm lấy tất cả danh sách hợp đồng chờ đại lý giải quyết - chờ OTP")
	public ResponseEntity<List<AgreementDTO>> getWaitAgency(@ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getWaitAgency");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		Page<AgreementDTO> page = agreementService.getWaitAgency(currentAgency.getMa(), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/get-wait-agency");

		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	// Đơn hàng chờ BV giải quyết: Chờ bảo việt giám định StautusPolicy: 93; Chờ bảo việt cấp đơn StautusPolicy: 91; Chờ Bảo Việt cấp GCNBH (bản cứng): 92
	@GetMapping("/get-wait-agreement")
	@Timed
	@ApiOperation(value = "getWaitAgreement", notes = "Hàm lấy tất cả danh sách hợp đồng chờ bảo việt giải quyết")
	public ResponseEntity<List<AgreementDTO>> getWaitAgreement(@ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getWaitAgreement");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		Page<AgreementDTO> page = agreementService.getWaitAgreement(currentAgency.getMa(), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/get-wait-agreement");

		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-order-wait")
	@Timed
	@ApiOperation(value = "searchOrderWait", notes = "Hàm tra cứu danh sách đơn hàng chờ Bảo Việt giải quyết")
	public ResponseEntity<List<AgreementDTO>> searchOrderWait(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderWait : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderWait(param, currentAgency.getMa(), "0");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order-wait");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-order-agency")
	@Timed
	@ApiOperation(value = "searchOrderAgency", notes = "Hàm tra cứu danh sách đơn hàng chờ đại lý giải quyết")
	public ResponseEntity<List<AgreementDTO>> searchOrderAgency(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderAgency : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderWait(param, currentAgency.getMa(), "1");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order-agency");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	/* Đơn quá  hạn thanh toán : 84 (Thời hạn bảo hiểm quá hạn)
	 * Đại lý, Khách hàng hủy đơn StautusPolicy: 89
	 * Bảo Việt từ chối StautusPolicy: 99
	 */
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-order-other")
	@Timed
	@ApiOperation(value = "searchOrderWait", notes = "Hàm tra cứu danh sách đơn hàng bảo hiểm khác")
	public ResponseEntity<List<AgreementDTO>> searchOrderOther(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderOther : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderWait(param, currentAgency.getMa(), "2");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order-other");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	/* Đơn quá  hạn thanh toán : 84 (Thời hạn bảo hiểm quá hạn)
	 */
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_AGREEMENT_VIEW')")
	@PostMapping("/search-order-expire")
	@Timed
	@ApiOperation(value = "searchOrderExpire", notes = "Hàm tra cứu danh sách đơn hàng quá hạn")
	public ResponseEntity<List<AgreementDTO>> searchOrderExpire(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchOrderExpire : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchOrderWait(param, currentAgency.getMa(), "5");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-order-expire");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CART_VIEW')")
	@PostMapping("/search-cart")
	@Timed
	@ApiOperation(value = "searchCart", notes = "Hàm tra cứu danh sách hợp đồng trong giỏ hàng")
	public ResponseEntity<List<AgreementDTO>> searchCart(@Valid @RequestBody SearchAgreementWaitVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to searchCart : {}", param);

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		Page<AgreementDTO> page = agreementService.searchCart(param, currentAgency.getMa());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/search-cart");
		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_CART_VIEW')")
	@GetMapping("/get-cart")
	@Timed
	@Deprecated
	@ApiOperation(value = "getAllCart", notes = "Hàm lấy tất cả danh sách hợp đồng trong giỏ hàng")
	public ResponseEntity<List<AgreementDTO>> getAllCart(@ApiParam Pageable pageable) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllCart");

		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		List<String> lstStatus = new ArrayList<String>();
		lstStatus.add("90");
		lstStatus.add("92");
		
//		Page<AgreementDTO> page = agreementService.findAllByAgentIdAndStatus(currentAgency.getMa(), lstStatus, pageable);
		Page<AgreementDTO> page = agreementService.searchCart(currentAgency.getMa(), lstStatus, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "/product/agreement/get-cart");
		// 90: Chưa thanh toán; 91: Đã thanh toán; 92 Cần giám định ; 89 Hủy

		// Return data
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@PostMapping("/get-orderHis-by-gycbhNumber")
    @Timed
    @ApiOperation(value = "getOrderHistoryByGycbhNumber", notes = "Hàm lấy lịch sử đơn hàng")
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByGycbhNumber(@RequestBody OrderCancelVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getOrderHistoryByGycbhNumber");
	
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		List<AgreementHis> lstAgreementHis = agreementHisRepository.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());
		
		if (lstAgreementHis != null && lstAgreementHis.size() == 0) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng với mã " + param.getGycbhNumber());
		}
		
		List<OrderHistoryDTO> data = agreementService.getOrderHistoryByGycbhNumber(lstAgreementHis);
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
}
