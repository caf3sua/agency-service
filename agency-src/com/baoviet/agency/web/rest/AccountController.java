package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AdminUserBuRepository;
import com.baoviet.agency.repository.AdminUserRepository;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.repository.MvAgentAgreementRepository;
import com.baoviet.agency.repository.MvClaOutletLocationRepository;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.security.jwt.TokenProvider;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.MailService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.AgencyChangePasswordVM;
import com.baoviet.agency.web.rest.vm.ForgotPasswordVM;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "account")
public class AccountController extends AgencyBaseResource {

	private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Environment env;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private AgreementRepository agreementRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;
	
	@Autowired
	private MvAgentAgreementRepository mvAgentAgreementRepository;
	
	@Autowired
	private AdminUserBuRepository adminUserBuRepository;
	
	@GetMapping("/logout")
	@Timed
	public ResponseEntity<String> logOutAgency() throws URISyntaxException {
		log.debug("REST request to logOutAgency");

		// Check param input

		// Return data
		return new ResponseEntity<>(AppConstants.SUCCESS, HttpStatus.OK);
	}

	@PostMapping(path = "/register", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@Timed
	public ResponseEntity registerAccount(@Valid @RequestBody AgencyDTO agencyDTO) {

		HttpHeaders textPlainHeaders = new HttpHeaders();
		textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

		// Check exist
		agencyService.findByEmail(agencyDTO.getEmail());

		// register
		// Agency agency = agencyRepository
		// .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
		// managedUserVM.getFirstName(), managedUserVM.getLastName(),
		// managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
		// managedUserVM.getLangKey());

		// mailService.sendActivationEmail(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * GET /account : get the current user.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the current user in body,
	 *         or status 500 (Internal Server Error) if the user couldn't be
	 *         returned
	 */
//	@GetMapping("/info")
//	@Timed
//	public ResponseEntity<AgencyDTO> getAccountInfo() {
//		final String userLogin = SecurityUtils.getCurrentUserLogin();
//		AgencyDTO existingUser = agencyService.findByEmail(userLogin);
//		if (existingUser == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		// Load department
//		List<DepartmentDTO> lstDepartment = loadDepartment(existingUser.getMa());
//		if (lstDepartment != null && lstDepartment.size() > 0) {
//			existingUser.setLstDepartment(lstDepartment);
//		}
//		
//		return new ResponseEntity<>(existingUser, HttpStatus.OK);
//	}
	
//	@GetMapping("/info-admin")
//	@Timed
//	public ResponseEntity<AdminUserDTO> getAccountAdminInfo() {
//		final String adminLogin = SecurityUtils.getCurrentUserLogin();
//		AdminUserDTO existingUser = adminUserService.findByEmail(adminLogin);
//		
//		if (existingUser == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(existingUser.getId());
//		List<DepartmentDTO> lstDepartment = new ArrayList<>();
//		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
//			for (AdminUserBu item : lstadminUserBu) {
//				// Load department
//				List<DepartmentDTO> lstDepar = loadDepartment(item.getBuId());
//				lstDepartment.addAll(lstDepar);
//			}
//		}
//		if (lstDepartment != null && lstDepartment.size() > 0) {
//			existingUser.setLstDepartment(lstDepartment);
//		}
//		
//		return new ResponseEntity<>(existingUser, HttpStatus.OK);
//	}
	
	/**
	 * POST /account : update the current user information.
	 *
	 * @param userDTO
	 *            the current user information
	 * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request)
	 *         or 500 (Internal Server Error) if the user couldn't be updated
	 */
	@PostMapping("/update")
	@Timed
	public ResponseEntity updateAccount(@Valid @RequestBody AgencyDTO agencyDTO) {
		final String userLogin = SecurityUtils.getCurrentUserLogin();

		return new ResponseEntity<>(agencyDTO, HttpStatus.OK);
	}

	@PostMapping(path = "/change-password")
	@Timed
	public ResponseEntity<AgencyChangePasswordVM> changePassword(@Valid @RequestBody AgencyChangePasswordVM agencyDTO) throws AgencyBusinessException{
		final String userLogin = SecurityUtils.getCurrentUserLogin();

		if (!checkOldLength(userLogin, agencyDTO.getOldPassword())) {
			throw new AgencyBusinessException("oldPassword", ErrorCode.INVALID , "Mật khẩu cũ không chính xác.");
		}

		boolean result = agencyService.changePassword(userLogin, agencyDTO.getNewPassword());

		agencyDTO.setResult(result);
		return new ResponseEntity<>(agencyDTO, HttpStatus.OK);
	}

	@GetMapping("/reset-password/{email}")
	@Timed
	public ResponseEntity requestPassword(@PathVariable String contactCode) {
		// TODO:
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@PostMapping(path = "/reset_password/init", produces = MediaType.TEXT_PLAIN_VALUE)
//	@Timed
//	public ResponseEntity requestPasswordReset(@Valid @RequestBody AgencyDTO agencyDTO) {
//		// TODO:
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

//	@PostMapping(path = "/reset_password/finish", produces = MediaType.TEXT_PLAIN_VALUE)
//	@Timed
//	public ResponseEntity<String> finishPasswordReset(@Valid @RequestBody KeyAndPasswordVM keyAndPassword) {
//		// TODO:
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	private boolean checkOldLength(String userLogin, String oldPassword) {
		AgencyDTO account = agencyService.findByEmail(userLogin);
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

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}

	@PostMapping("/forgotPassword")
	@Timed
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordVM param) throws URISyntaxException {
		log.debug("REST request to forgotPassword : {}", param.getEmail());

		AgencyDTO account = agencyService.findByEmail(param.getEmail());

		if (account != null) {
			String newPass = AgencyUtils.generateRandomPassword();

			boolean result = agencyService.changePassword(param.getEmail(), newPass);
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

	@PostMapping("/confirmEmail")
	@Timed
	public ResponseEntity<String> confirmEmail(String agreementId, String contactId) throws URISyntaxException {
		log.debug("REST request to confirmEmail : {}", agreementId, contactId);

		Agreement agreement = agreementRepository.findByAgreementIdAndContactId(agreementId, contactId);
		if (agreement != null && !agreement.getStatusPolicyId().equals("90")
				&& agreement.getContactId().equals(contactId)) {
			
			// Gửi email thông báo thanh toán thành công
			agreement.setCancelPolicySupport3(1.0);
			agreementRepository.save(agreement);
			// msgResult = "/ConfirmPage?id=" + pAGREEMENT.MCI_ADD_ID + "&type=3";
		} else {
			// msgResult = "/CartList";
		}

		// Return data
		return new ResponseEntity<>(AppConstants.SUCCESS, HttpStatus.OK);
	}
	
}
