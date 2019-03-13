package com.baoviet.agency.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TmpMomoCarDTO;
import com.baoviet.agency.dto.momo.BookingInfoDTO;
import com.baoviet.agency.dto.momo.ExtraDataDTO;
import com.baoviet.agency.dto.momo.PaymentInfoDTO;
import com.baoviet.agency.dto.momo.component.MomoComponent;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.PayActionRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.MomoCarService;
import com.baoviet.agency.service.PayActionService;
import com.baoviet.agency.service.ProductCARService;
import com.baoviet.agency.service.TmpMomoCarService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.ComponentUtils;
import com.baoviet.agency.utils.logging.RequestLogger;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;
import com.baoviet.agency.web.rest.vm.ProductCarVM;
import com.baoviet.agency.web.rest.vm.RequestMomoForm;
import com.baoviet.agency.web.rest.vm.ResponseMomoForm;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "momo/product/car")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "momo/product/car", description = "<a href=\"/content/extra_doc/car.html\" target=\"_blank\">External document</a>")
public class ProductMomoCarResource extends AbstractAgencyResource {

    private final Logger log = LoggerFactory.getLogger(ProductMomoCarResource.class);

    // TCT Ban Kinh doanh bảo hiểm Phi hàng hải
    private final static String DEPARTMENT_ID_MOMO = "A000009218";
    private final static String AGENT_ID_MOMO = "T000080696";
    private final static String AGENT_NAME_MOMO = "MOMO";
    
    @Autowired
    private MomoCarService momoCarService;
    
    @Autowired
    private ProductCARService productCARService;
    
    @Autowired
    private TmpMomoCarService tmpMomoCarService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
    private PayActionRepository payActionRepository;
    
    @Autowired
	private PayActionService payActionService;
    
    @Value("${spring.application.vendor.momo.secret-key}")
	private String secretKey;
    
    @PostMapping("/form-data")
    @Timed
    @ApiOperation(value="doFormData", notes="Service URL để Momo sử dụng cho việc getForm và submitForm.")
    @RequestLogger
    public ResponseEntity<ResponseMomoForm> doFormData(HttpServletRequest request, @Valid @RequestBody RequestMomoForm param) throws URISyntaxException, AgencyBusinessException, IOException {
		log.debug("REST request to get/submit form data : {}", param);
		List<MomoComponent> form = null;
		
		ResponseMomoForm response = new ResponseMomoForm();
		response.setPartnerCode(param.getPartnerCode());
		response.setServiceId(param.getServiceId());
		response.setRequestId(param.getRequestId());
		response.setRequestType(param.getRequestType());

		if (StringUtils.equals(param.getRequestType(), "getForm")) {
			response.setCurrentStep(param.getCurrentStep());
			response.setResponseType("render");		
			form = momoCarService.buildFormDataStep1();
		} else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "1")) {
			response.setResponseType("render");
			
			// Validate
			boolean isValid = momoCarService.validateDataStep1(param.getFormData());
			if (isValid == false) {
				form = param.getFormData();
				response.setCurrentStep("1");
			} else {
				// Save data from step1 to temp table
				momoCarService.saveFormDataStep1(param.getRequestId(), param.getFormData());

				TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(param.getRequestId());
				if (dto == null) {
					throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
				}
				// Nếu chỉ có bh tnds thì view ra màn phí luôn
				if (dto.getTndsbbCheck() > 0 && dto.getTndstnCheck() == 0 && dto.getNntxCheck() == 0 && dto.getVcxCheck() == 0) {
						momoCarService.saveFormDataStep2(param.getRequestId(), param.getFormData());
						form = momoCarService.buildFormDataStepPhi(param.getRequestId());
						response.setCurrentStep("3");	
				} else {
					form = momoCarService.buildFormDataStep2(param.getRequestId(), param.getFormData());
					response.setCurrentStep("2");
				}
			}
		} else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "2")) {
			response.setResponseType("render");
			
			// Validate
			boolean isValid = momoCarService.validateDataStep2(param.getRequestId(), param.getFormData());
			if (isValid == false) {
				form = param.getFormData();
				response.setCurrentStep("2");
			} else {
				// Get data from step2
				momoCarService.saveFormDataStep2(param.getRequestId(), param.getFormData());
				response.setCurrentStep("3");
				form = momoCarService.buildFormDataStepPhi(param.getRequestId());
			}
		}
			// step phi
		 else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "3")) {
				response.setResponseType("render");
				TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(param.getRequestId());
				if (dto == null) {
					throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
				}
				if(StringUtils.equals(dto.getCheckTaituc(), "1")) { 
					form = momoCarService.buildFormDataStep3(param.getRequestId(), "1"); // có gycbh
					response.setCurrentStep("4");
				} else {	// ko có gycbh
					form = momoCarService.buildFormDataStep3(param.getRequestId(), "0"); // không gycbh
					response.setCurrentStep("4");
				}
				
			}
			// Step 4
		 else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "4")) {
			response.setResponseType("render");
			// Validate
			boolean isValid = momoCarService.validateDataStep3(param.getFormData());
			if (isValid == false) {
				form = param.getFormData();
				response.setCurrentStep("4");
			} else {
				// Get data from step3
				momoCarService.saveFormDataStep3(param.getRequestId(), param.getFormData());
				response.setCurrentStep("5");
				form = momoCarService.buildFormDataStep4(param.getRequestId(), param.getFormData());
			}
		} else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "5")) {
			response.setResponseType("render");
			// Validate
			boolean isValid = momoCarService.validateDataStep4(param.getFormData());
			if (isValid == false) {
				form = param.getFormData();
				response.setCurrentStep("5");
			} else {
				// Get data from step4
				momoCarService.saveFormDataStep4(param.getRequestId(), param.getFormData());
				// kiểm tra xem có check GTGT không
				Boolean idCheckGTGT = (Boolean) ComponentUtils.getComponentValue(param.getFormData(), "idCheckGTGT");
				if (idCheckGTGT != null && idCheckGTGT) {
					form = momoCarService.buildFormDataStepGTGT(param.getFormData());
					response.setCurrentStep("6");
				} else {
					response.setCurrentStep("7");
					form = momoCarService.buildFormDataStep5(param.getRequestId());
				}
			}
		} else if (StringUtils.equals(param.getRequestType(), "submitForm") 
				&& StringUtils.equals(param.getCurrentStep(), "6")) {
			response.setResponseType("render");
			// Validate
			boolean isValid = momoCarService.validateDataStepGTGT(param.getFormData());
			if (isValid == false) {
				form = param.getFormData();
				response.setCurrentStep("6");
			} else {
				// Get data from stepGTGT
				momoCarService.saveFormDataStepGTGT(param.getRequestId(), param.getFormData());
				response.setCurrentStep("7");
				form = momoCarService.buildFormDataStep5(param.getRequestId());
			}
		} else {
			// Validate data
			if (param.getRequestId() != null) {
				TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(param.getRequestId());
				if (dto == null) {
					throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
				}

				// Tính Phí
				PremiumCARVM objPremium = momoCarService.getValuePremiumCar(dto);
				// calculatePremium
				PremiumCARVM premiumCar = productCARService.calculatePremium(objPremium, "");
				
				ProductCarVM objGet = momoCarService.getValueTmpMomoCar(dto, premiumCar);
				
				// Get current agency
				AgencyDTO currentAgency = new AgencyDTO();
				currentAgency.setMa(AGENT_ID_MOMO);
				currentAgency.setId(AGENT_ID_MOMO);
				currentAgency.setTen(AGENT_NAME_MOMO);

				// Create policy
				// Fix departmentID for momo
				objGet.setDepartmentId(DEPARTMENT_ID_MOMO);
				ProductCarVM objSave = productCARService.createOrUpdatePolicy(objGet, currentAgency);
			
				// update status TmpMomoCar
				dto.setStatus("CREATED");
				dto.setGycbhNumber(objSave.getGycbhNumber());
				tmpMomoCarService.save(dto);
				
				// Payment info
				PaymentInfoDTO pInfo = new PaymentInfoDTO();
				long amount = (long) objSave.getTotalPremium();	// phải là kiểu long 
				pInfo.setAmount(String.valueOf(amount));
				pInfo.setOrderId(objSave.getGycbhNumber());
				pInfo.setDescription("Thanh toán bảo hiểm Ô tô");
				
				// ExtraData
				try {
					List<BookingInfoDTO> lstBookingInfo = new ArrayList();
					BookingInfoDTO bookingInfo = new BookingInfoDTO();
					bookingInfo.setPolicyNumber(objSave.getGycbhNumber());
					bookingInfo.setStandardPremium(String.valueOf(objSave.getPremium()));
					bookingInfo.setTotalPremium(String.valueOf(objSave.getTotalPremium()));
					lstBookingInfo.add(bookingInfo);
					ExtraDataDTO extraData = new ExtraDataDTO();
					extraData.setBookingInfo(lstBookingInfo);
					pInfo.setExtraData(extraData);
				} catch (Exception e) {
					log.debug("REST response to extraData : {}", pInfo.getExtraData());
				}
				
				// Payment
				response.setCurrentStep("7");
				response.setResponseType("payment");		
				response.setPaymentInfo(pInfo);
				log.debug("REST response to PaymentInfoDTO : {}", pInfo);
			}
		}
		
		response.setFormData(form);
		// Return data
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value="/notify-payment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    		, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Timed
    @ApiOperation(value="notifyPayment", notes="Service URL để Momo sử dụng cho việc gửi kết quả thanh toán.")
    public MultiValueMap<String, String> notifyPayment(@RequestBody final MultiValueMap<String, String> paramMap) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to notifyPayment : {}", paramMap);
		
		Map<String, String> map = AgencyUtils.convertMultiToRegularMap(paramMap);
		
		// Get param from request
		String partner_code = map.get("partner_code");
		String access_key = map.get("access_key"); 
		String order_id = map.get("order_id"); 
		String status_code = map.get("status_code"); 
		String message = map.get("message");
		String transaction_id = map.get("transaction_id");
		
		
		// Success
		String messageBaoviet = "";
		String statusCodeBaoviet = "";
		if (StringUtils.equals(status_code, "0")) {
			if (!StringUtils.isEmpty(order_id)) {
				AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(order_id, AGENT_ID_MOMO);
				if (agreement != null) {
					agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_HOANTHANH);
					agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_HOANTHANH);
					agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_HOANTHANH);
					agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_HOANTHANH);
					agreement.setSendSms(1);	// gửi sms
					agreement.setSendEmail(1);	// gửi mail
					if(StringUtils.isNotEmpty(transaction_id)) {
						agreement.setPaymentTransactionId(transaction_id);
					}
					
					AgreementDTO agreementSave = agreementService.save(agreement);
					
					TmpMomoCarDTO dto = tmpMomoCarService.findByGycbhNumber(order_id);
					if (dto == null) {
						throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "GycbhNumber không tồn tại");
					}
					// update Status TmpMomoCar
					dto.setStatus("DONE");
					tmpMomoCarService.save(dto);
					log.debug("REST request StatusPolicyId: {}", agreementSave.getStatusPolicyId());
					
					if(StringUtils.isNotEmpty(transaction_id)) {
						PayAction payAction = payActionRepository.findByPolicyNumbers(order_id);
						if (payAction == null) {
							throw new AgencyBusinessException("order_id", ErrorCode.INVALID, "Không tồn tại thông tin thanh toán");
						}
						// update payment
						payAction.setTransactionId(transaction_id);
						payAction.setStatus(91);
						payActionService.save(payAction);
					}
					messageBaoviet = "Đã thanh toán";
					statusCodeBaoviet = "0";		
				} else {
					messageBaoviet = "Thanh toán thất bại - Không tìm thấy đơn hàng";
					statusCodeBaoviet = "1";
				}
			} else {
				messageBaoviet = "Không tìm thấy đơn hàng";
				statusCodeBaoviet = "1";
			}
		} else {
			if (!StringUtils.isEmpty(order_id)) {
				AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(order_id, AGENT_ID_MOMO);
				if (agreement != null) {
					agreement.setStatusPolicyId("93");
					agreement.setStatusPolicyName("Chờ BV giám định");
					agreement.setStatusGycbhId("93");
					agreement.setStatusGycbhName("Chờ BV giám định");
					
					AgreementDTO agreementSave = agreementService.save(agreement);
					log.debug("REST request StatusPolicyId: {}", agreementSave.getStatusPolicyId());
				
					messageBaoviet = "Chờ BV giám định";
					statusCodeBaoviet = "93";			
				} else {
					messageBaoviet = "Không tìm thấy đơn hàng";
					statusCodeBaoviet = "1";
				}
			} else {
				messageBaoviet = "Không tìm thấy đơn hàng";
				statusCodeBaoviet = "1";
			}
		}
		
		
		// Build signature 
		// partner_code=$partner_code&access_key=$access_key&order_id=$order_id&message=$message&status_code=$status_code
		String content = String.format("partner_code=%s&access_key=%s&order_id=%s&message=%s&status_code=%s"
				, partner_code, access_key, order_id, messageBaoviet, statusCodeBaoviet);
		
		String signature = AgencyUtils.signatureHmacSHA256(secretKey, content);

		// Create response MultiValueMap<String, String>
		MultiValueMap<String, String> responseMap = new LinkedMultiValueMap<>();
		responseMap.add("partner_code", partner_code);
		responseMap.add("access_key", access_key);
		responseMap.add("order_id", order_id);
		responseMap.add("status_code", status_code);
		responseMap.add("message", messageBaoviet);
		responseMap.add("signature", signature);
		
		// Return data
        return responseMap;
    }
}
