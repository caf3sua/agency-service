package com.baoviet.agency.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.AdminUserBu;
import com.baoviet.agency.domain.AgencyMap;
import com.baoviet.agency.domain.MvAgentAgreement;
import com.baoviet.agency.domain.MvClaOutletLocation;
import com.baoviet.agency.domain.User;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.CompanyDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.repository.AdminUserBuRepository;
import com.baoviet.agency.repository.AgencyMapRepository;
import com.baoviet.agency.repository.MvAgentAgreementRepository;
import com.baoviet.agency.repository.MvClaOutletLocationRepository;
import com.baoviet.agency.repository.UserRepository;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.MailService;
import com.baoviet.agency.service.UserService;
import com.baoviet.agency.service.dto.UserDTO;
import com.baoviet.agency.web.rest.util.HeaderUtil;
import com.baoviet.agency.web.rest.vm.KeyAndPasswordVM;
import com.baoviet.agency.web.rest.vm.ManagedUserVM;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AgencyService agencyService;
    
    @Autowired
    private AdminUserService adminUserService; 
    
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;
	
	@Autowired
	private MvAgentAgreementRepository mvAgentAgreementRepository;
	
	@Autowired
	private AdminUserBuRepository adminUserBuRepository;
	
	@Autowired
	private AgencyMapRepository agencyMapRepository;
    
    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping(path = "/register",
        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService
                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey());

                    mailService.sendActivationEmail(user);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
        );
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping(value= {"/account", "/agency/account/info"})
    @Timed
    public ResponseEntity<?> getAccount() {
    	final String userLogin = SecurityUtils.getCurrentUserLogin();
    	if (SecurityUtils.isCurrentUserInRole(AgencyConstants.ROLE_BAOVIET)) {
    		AgencyDTO existingUser = adminUserService.findByEmail(userLogin);
    		
    		if (existingUser == null) {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    		
    		// Set role default
    		if (existingUser.getAuthorities() != null) {
    			existingUser.getAuthorities().add(AgencyConstants.ROLE_BAOVIET);
    		}
    		
    		List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(existingUser.getId());
    		List<DepartmentDTO> lstDepartment = new ArrayList<>();
    		List<CompanyDTO> lstCompany = new ArrayList<>();
    		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
    			for (AdminUserBu item : lstadminUserBu) {
    				
    				// lấy tên phòng ban	// HOFF: tổng công ty; CTTV: công ty; VPDD: phòng ban
    				List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(item.getBuId());
    				if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
    					if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("VPDD")) {
    						CompanyDTO company = new CompanyDTO();
    						DepartmentDTO department = new DepartmentDTO();
    						department.setDepartmentId(item.getBuId());
    						department.setDepartmentName(lstMvClaOutletLocation.get(0).getOutletName());
    						company.setCompanyId(lstMvClaOutletLocation.get(0).getPrOutletAmsId());
        					company.setCompanyName(lstMvClaOutletLocation.get(0).getPrOutletName());
        					
        					lstDepartment.add(department);
        					lstCompany.add(company);
    					}
    					
    					if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("CTTV")) {
    						// nếu là cty thì tìm kiếm các phòng trực thuộc công ty
    						CompanyDTO company = new CompanyDTO();
    						company.setCompanyId(lstMvClaOutletLocation.get(0).getOutletAmsId());
        					company.setCompanyName(lstMvClaOutletLocation.get(0).getOutletName());
        					
        					List<MvClaOutletLocation> lstOutletDepartment = mvClaOutletLocationRepository.findByPrOutletAmsIdAndOutletTypeCode(item.getBuId(), "VPDD");
        					if (lstOutletDepartment != null && lstOutletDepartment.size() > 0) {
        						for (MvClaOutletLocation depar : lstOutletDepartment) {
        							DepartmentDTO department = new DepartmentDTO();
        							department.setDepartmentId(depar.getOutletAmsId());
            						department.setDepartmentName(depar.getOutletName());
            						lstDepartment.add(department);
								}
        					}
        					lstCompany.add(company);
    					}
    					
    					if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("HOFF")) {
    						// Tổng công ty thì tìm các phòng ban
    						List<MvClaOutletLocation> lstOutletComp = mvClaOutletLocationRepository.findByPrOutletAmsIdAndOutletTypeCode(item.getBuId(), "CTTV");
        					if (lstOutletComp != null && lstOutletComp.size() > 0) {
        						for (MvClaOutletLocation comp : lstOutletComp) {
        							CompanyDTO company = new CompanyDTO();
        							company.setCompanyId(comp.getOutletAmsId());
                					company.setCompanyName(comp.getOutletName());
            						lstCompany.add(company);
            						
            						// ứng với mỗi công ty tìm ra phòng ban
            						List<MvClaOutletLocation> lstOutletDepartment = mvClaOutletLocationRepository.findByPrOutletAmsIdAndOutletTypeCode(comp.getOutletAmsId(), "VPDD");
                					if (lstOutletDepartment != null && lstOutletDepartment.size() > 0) {
                						for (MvClaOutletLocation depar : lstOutletDepartment) {
                							DepartmentDTO department = new DepartmentDTO();
                							department.setDepartmentId(depar.getOutletAmsId());
                    						department.setDepartmentName(depar.getOutletName());
                    						lstDepartment.add(department);
        								}
                					}
								}
        					}
    					}
    					
    				} else {
    					List<MvAgentAgreement> lstMvAgentAgreement =mvAgentAgreementRepository.findByDepartmentCode(item.getBuId());
    					if (lstMvAgentAgreement != null && lstMvAgentAgreement.size() > 0) {
    						DepartmentDTO department = new DepartmentDTO();
    						department.setDepartmentId(item.getBuId());
    						department.setDepartmentName(lstMvAgentAgreement.get(0).getDepartmentName());
    					}
    				}
    				
    				
    			}
    		}
    		if (lstDepartment != null && lstDepartment.size() > 0) {
    			existingUser.setLstDepartment(lstDepartment);
    		}
    		
    		if (lstCompany != null && lstCompany.size() > 0) {
    			existingUser.setLstCompany(lstCompany);
    		}
    		
    		return new ResponseEntity<>(existingUser, HttpStatus.OK);
    	} else {
    		AgencyDTO existingUser = agencyService.findByEmail(userLogin);
    		if (existingUser == null) {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    		// Load department
    		if (StringUtils.equals(AgencyConstants.KENH_PHAN_PHOI_AGENCY, existingUser.getKenhPhanPhoi())) {
    			List<DepartmentDTO> lstDepartment = adminUserService.searchDepartment(existingUser.getMa());
        		if (lstDepartment != null && lstDepartment.size() > 0) {
        			existingUser.setLstDepartment(lstDepartment);
        		}	
    		} else {
    			if (StringUtils.isNotEmpty(existingUser.getKenhPhanPhoi())) {
    				if (StringUtils.isNotEmpty(existingUser.getMaDonVi())) {
    					List<AgencyMap> lstAgencyMap = agencyMapRepository.findByAgencyP3Id(existingUser.getMaDonVi());
        				if (lstAgencyMap != null && lstAgencyMap.size() > 0) {
        					List<DepartmentDTO> lstDepartment = new ArrayList<>();
        					for (AgencyMap agencyMap : lstAgencyMap) {
    							if (StringUtils.isNotEmpty(agencyMap.getBvId3()) && StringUtils.isNotEmpty(agencyMap.getBvName3())) {
    								DepartmentDTO depar = new DepartmentDTO();
    	    						depar.setDepartmentId(agencyMap.getBvId3());
    	    						depar.setDepartmentName(agencyMap.getBvName3());
    	    						lstDepartment.add(depar);
    							}
    						}
        					if (lstDepartment != null && lstDepartment.size() > 0) {
        	        			existingUser.setLstDepartment(lstDepartment);
        	        		}
        				}
    				}
    			}
    		}
    		
    		return new ResponseEntity<>(existingUser, HttpStatus.OK);
    	}
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
    
    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    public ResponseEntity saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
            .findOneByLogin(userLogin)
            .map(u -> {
                userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey(), userDTO.getImageUrl());
                return new ResponseEntity(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/account/change_password",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST   /account/reset_password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the email was sent, or status 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset_password/init",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity requestPasswordReset(@RequestBody String mail) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user);
                return new ResponseEntity<>("email was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("email address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset_password/finish",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
