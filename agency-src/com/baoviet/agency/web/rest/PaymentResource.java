package com.baoviet.agency.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.config.ApplicationProperties;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.PaymentMsbDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.payment.PaymentFactory;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.domain.PaymentBank;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.dto.ViettelCheckOrderInfoRequest;
import com.baoviet.agency.payment.dto.ViettelCheckOrderInfoResponse;
import com.baoviet.agency.payment.dto.ViettelUpdateOrderStatusRequest;
import com.baoviet.agency.payment.dto.ViettelUpdateOrderStatusResponse;
import com.baoviet.agency.payment.dto.VnPayOrderStatusResponse;
import com.baoviet.agency.payment.gateway.PaymentGateway;
import com.baoviet.agency.payment.gateway.impl.PaymentGatewayViettelPay;
import com.baoviet.agency.payment.gateway.impl.PaymentGatewayVnPay;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.PaymentService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.NotifyPaymentVM;
import com.baoviet.agency.web.rest.vm.PaymentATCSVM;
import com.baoviet.agency.web.rest.vm.PaymentBanksReponseVM;
import com.baoviet.agency.web.rest.vm.PaymentGiftCodeRequestVM;
import com.baoviet.agency.web.rest.vm.PaymentGiftCodeResponseVM;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;
import com.baoviet.agency.web.rest.vm.PaymentProcessResponseVM;
import com.baoviet.agency.web.rest.vm.PaymentUpdateOTPVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "payment")
public class PaymentResource extends AbstractAgencyResource {

	private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentFactory paymentFactory;

	@Autowired
	private AgreementService agreementService;
	
	@Autowired
	protected ApplicationProperties applicationProperties;

	/// <summary>
	/// Service cập nhật trạng thái (STATUS_POLICY_ID) đơn ATCS
	/// - Cập nhật trạng thái trong bảng AGREMENT
	/// - Cập nhật trạng thái trong bảng ATCS
	/// - Insert bản ghi vào bảng PAY_ACTION
	/// </summary>
	/// <param name="transaction_id">Mã giao dịch đối tác truyền cho BV</param>
	/// <param name="gycbh_number">Số giấy ycbh của đơn ATCS</param>
	/// <param name="sotien">Só tiền thanh toán cuối cùng đối tác truyền cho
	/// BV</param>
	@PostMapping("/updatePayment")
	@Timed
	@ApiOperation(value = "updatePayment", notes = "Cập nhật trạng thái đơn ATCS")
	public ResponseEntity<PaymentMsbDTO> updatePaymentATCS(@Valid @RequestBody PaymentATCSVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updatePaymentATCS : {}", param);

		// Call service
		PaymentMsbDTO result = paymentService.updatePaymentATCS(param);

		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/updatePaymentOTP")
	@Timed
	@ApiOperation(value = "updatePaymentOTP", notes = "Cập nhật trạng thái đơn hàng")
	public ResponseEntity<PaymentUpdateOTPVM> updatePaymentOTP(@Valid @RequestBody PaymentUpdateOTPVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updatePaymentOTP : {}", param);

		// Call service
		boolean result = paymentService.updatePaymentOTP(param);
		param.setResult(result);

		// Return data
		return new ResponseEntity<>(param, HttpStatus.OK);
	}

	@PostMapping("/processPayment")
	@Timed
	@ApiOperation(value = "processPayment", notes = "Xử lý thanh toán đơn hàng")
	public ResponseEntity<PaymentProcessResponseVM> processPayment(@Valid @RequestBody PaymentProcessRequestVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to processPayment");
		log.info("Param : ", param.toString());

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// Get list agreement for payment
		List<Agreement> agreements = agreementService.findAllByAgreementIds(param.getAgreementIds());

		PaymentType paymentType = PaymentType.valueOf(param.getPaymentType());
		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(paymentType);
		PaymentResult paymentResult = paymentGateway.processPayment(currentAgency, param, agreements);

		PaymentProcessResponseVM processResponseVM = new PaymentProcessResponseVM();
		processResponseVM.setRedirectUrl(paymentResult.getRedirectUrl());

		log.info("END REST request to processPayment");
		// Return data
		return new ResponseEntity<>(processResponseVM, HttpStatus.OK);
	}

	@GetMapping("/getBanksByPaymentCode")
	@Timed
	@ApiOperation(value = "getBanksByPaymentCode", notes = "Trả về danh sách ngân hàng hỗ trợ phương thức thanh toán đã chọn.")
	public ResponseEntity<PaymentBanksReponseVM> getBanksByPaymentCode(
			@RequestParam(value = "paymentCode") String paymentCode)
			throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to getBanksByPaymentCode");
		log.info("paymentCode : ", paymentCode);

		if (paymentCode.equalsIgnoreCase("VnPay")) {
			paymentCode = paymentCode.toUpperCase();
		}
		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.Common);
		List<PaymentBank> paymentBanks = paymentGateway.getBanksByPaymentCode(paymentCode);

		PaymentBanksReponseVM banksReponseVM = new PaymentBanksReponseVM();
		banksReponseVM.setPaymentBanks(paymentBanks);

		log.info("END REST request to getBanksByPaymentCode");
		// Return data
		return new ResponseEntity<>(banksReponseVM, HttpStatus.OK);
	}

	@GetMapping("/checkGiftCodeValid")
	@Timed
	@ApiOperation(value = "checkGiftCodeValid", notes = "Kiểm tra mã khuyến mại có hợp lệ hay không.")
	public ResponseEntity<PaymentGiftCodeResponseVM> checkGiftCodeValid(
			@Valid @RequestBody PaymentGiftCodeRequestVM param) throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to checkGiftCodeValid");
		log.info("giftCode : ", param.getGiftCode());

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		// Get list agreement for payment
		List<Agreement> agreements = agreementService.findAllByAgreementIds(param.getAgreementIds());

		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.Common);
		PaymentResponseType responseType = paymentGateway.checkGiftCode(param.getGiftCode(), currentAgency.getEmail(),
				agreements);

		PaymentGiftCodeResponseVM codeResponseVM = new PaymentGiftCodeResponseVM();
		switch (responseType) {
		case SUCCESS:
			codeResponseVM.setMessage("Mã khuyến mại hợp lệ, được phép sử dụng.");
			break;

		case GIFT_CODE_INVALID:
			codeResponseVM.setMessage("Mã khuyến mại không hợp lệ!");
			break;

		case GIFT_CODE_ONLY_CAR_KCR:
			codeResponseVM.setMessage("Mã khuyến mại chỉ áp dụng cho bảo hiểm ô tô hoặc bảo hiểm bệnh ung thư!");
			break;

		case GIFT_CODE_BELOW_LIMIT:
			codeResponseVM.setMessage("Mã khuyến mại chỉ áp dụng cho bảo hiểm ô tô hoặc bảo hiểm bệnh ung thư có giá trị lớn hơn 2.000.000 vnđ!");
			break;
		}

		log.info("END REST request to checkGiftCodeValid");
		// Return data
		return new ResponseEntity<>(codeResponseVM, HttpStatus.OK);
	}

	// Da thanh toan : 91
	@PostMapping("/notify-payment")
	@Timed
	@ApiOperation(value = "notifyPayment", notes = "Xử lý thanh toán đơn hàng từ vendor")
	public ResponseEntity<NotifyPaymentVM> notifyPayment(@Valid @RequestBody NotifyPaymentVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to notifyPayment, {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();

		if (!StringUtils.isEmpty(param.getGycbhNumber())) {
			AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(),
					currentAgency.getMa());
			if (agreement == null) {
				throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID,
						"Không tồn tại dữ liệu với gycbhNumber " + param.getGycbhNumber());
			}
		}

		NotifyPaymentVM result = paymentService.updateAgrement(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//	@GetMapping("/return123Pay")
//	@Timed
//	@ApiOperation(value = "return123Pay", notes = "Xử lý thanh toán đơn hàng từ vendor 123Pay")
//	public ResponseEntity<HttpHeaders> return123Pay(@Valid @RequestBody NotifyPaymentVM param)
//			throws URISyntaxException, AgencyBusinessException {
//		log.info("START REST request to return123Pay, {}");
//
//		Map<String, String> paramMap = new LinkedHashMap<>();
//
//		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.l23Pay);
//		PaymentResult paymentResult = paymentGateway.processReturn(paramMap);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(URI.create(paymentResult.getRedirectUrl()));
//		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//	}

	@GetMapping("/returnMomo")
	@Timed
	@ApiOperation(value = "returnMomo", notes = "Xử lý thanh toán đơn hàng từ vendor Momo")
	public ResponseEntity<HttpHeaders> returnMomo(Device device, @RequestParam("partnerCode") String partnerCode,
			@RequestParam("accessKey") String accessKey, @RequestParam("requestId") String requestId,
			@RequestParam("amount") String amount, @RequestParam("orderId") String orderId,
			@RequestParam("orderInfo") String orderInfo, @RequestParam("orderType") String orderType,
			@RequestParam("transId") String transId, @RequestParam("message") String message,
			@RequestParam("localMessage") String localMessage, @RequestParam("responseTime") String responseTime,
			@RequestParam("errorCode") String errorCode, @RequestParam("payType") String payType,
			@RequestParam("extraData") String extraData, @RequestParam("signature") String signature)
			throws URISyntaxException, AgencyBusinessException, UnsupportedEncodingException {
		log.info("START REST request to returnMomo, {}");

		Map<String, String> paramMap = new LinkedHashMap<>();
		paramMap.put(Constants.MOMO_PARAM_PARTNER_CODE, URLDecoder.decode(partnerCode, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_ACCESS_KEY, URLDecoder.decode(accessKey, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_REQUEST_ID, URLDecoder.decode(requestId, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_AMOUNT, URLDecoder.decode(amount, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_ORDER_ID, URLDecoder.decode(orderId, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_ORDER_INFO, URLDecoder.decode(orderInfo, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_ORDER_TYPE, URLDecoder.decode(orderType, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_TRANS_ID, URLDecoder.decode(transId, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_MESSAGE, URLDecoder.decode(message, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_LOCAL_MESSAGE, URLDecoder.decode(localMessage, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_RESPONSE_TIME, URLDecoder.decode(responseTime, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_ERROR_CODE, URLDecoder.decode(errorCode, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_PAY_TYPE, URLDecoder.decode(payType, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_EXTRA_DATA, URLDecoder.decode(extraData, "UTF-8"));
		paramMap.put(Constants.MOMO_PARAM_SIGNATURE, URLDecoder.decode(signature, "UTF-8"));

		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.Momo);
		PaymentResult paymentResult = paymentGateway.processReturn(paramMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(getRedirectUrl(device)));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/returnVnPay")
	@Timed
	@ApiOperation(value = "returnVnPay", notes = "Xử lý thanh toán đơn hàng từ vendor VnPay")
	public ResponseEntity<HttpHeaders> returnVnPay(Device device, @RequestParam("vnp_Amount") String vnpAmount,
			@RequestParam("vnp_BankCode") String vnpBankCode, @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
			@RequestParam("vnp_CardType") String vnpCardType, @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
			@RequestParam("vnp_PayDate") String vnpPayDate, @RequestParam("vnp_ResponseCode") String vnpResponseCode,
			@RequestParam("vnp_TmnCode") String vnpTmnCode, @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
			@RequestParam("vnp_TxnRef") String vnpTxnRef, @RequestParam("vnp_SecureHash") String vnpSecureHash)
			throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to returnVnPay, {}");

		Map<String, String> paramMap = new LinkedHashMap<>();
		paramMap.put(Constants.VNPAY_PARAM_AMOUNT, vnpAmount);
		paramMap.put(Constants.VNPAY_PARAM_BANK_CODE, vnpBankCode);
		paramMap.put(Constants.VNPAY_PARAM_BANK_TRAN_NO, vnpBankTranNo);
		paramMap.put(Constants.VNPAY_PARAM_CARD_TYPE, vnpCardType);
		paramMap.put(Constants.VNPAY_PARAM_ORDER_INFO, vnpOrderInfo);
		paramMap.put(Constants.VNPAY_PARAM_PAY_DATE, vnpPayDate);
		paramMap.put(Constants.VNPAY_PARAM_RESPONSE_CODE, vnpResponseCode);
		paramMap.put(Constants.VNPAY_PARAM_TMN_CODE, vnpTmnCode);
		paramMap.put(Constants.VNPAY_PARAM_TRANSACTION_NO, vnpTransactionNo);
		paramMap.put(Constants.VNPAY_PARAM_TXN_REF, vnpTxnRef);
		paramMap.put(Constants.VNPAY_PARAM_SECURE_HASH, vnpSecureHash);

		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.VnPay);
		PaymentResult paymentResult = paymentGateway.processReturn(paramMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(getRedirectUrl(device)));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/checkVnPayOrderInfo")
	@Timed
	@ApiOperation(value = "checkVnPayOrderInfo", notes = "Xử lý thanh toán đơn hàng từ vendor VnPay")
	public ResponseEntity<VnPayOrderStatusResponse> checkvVnPayOrderInfo(@RequestParam("vnp_Amount") String vnpAmount,
			@RequestParam("vnp_BankCode") String vnpBankCode, @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
			@RequestParam("vnp_CardType") String vnpCardType, @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
			@RequestParam("vnp_PayDate") String vnpPayDate, @RequestParam("vnp_ResponseCode") String vnpResponseCode,
			@RequestParam("vnp_TmnCode") String vnpTmnCode, @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
			@RequestParam("vnp_TxnRef") String vnpTxnRef, @RequestParam("vnp_SecureHash") String vnpSecureHash)
			throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to checkvVnPayOrderInfo, {}");

		Map<String, String> paramMap = new LinkedHashMap<>();
		paramMap.put(Constants.VNPAY_PARAM_AMOUNT, vnpAmount);
		paramMap.put(Constants.VNPAY_PARAM_BANK_CODE, vnpBankCode);
		paramMap.put(Constants.VNPAY_PARAM_BANK_TRAN_NO, vnpBankTranNo);
		paramMap.put(Constants.VNPAY_PARAM_CARD_TYPE, vnpCardType);
		paramMap.put(Constants.VNPAY_PARAM_ORDER_INFO, vnpOrderInfo);
		paramMap.put(Constants.VNPAY_PARAM_PAY_DATE, vnpPayDate);
		paramMap.put(Constants.VNPAY_PARAM_RESPONSE_CODE, vnpResponseCode);
		paramMap.put(Constants.VNPAY_PARAM_TMN_CODE, vnpTmnCode);
		paramMap.put(Constants.VNPAY_PARAM_TRANSACTION_NO, vnpTransactionNo);
		paramMap.put(Constants.VNPAY_PARAM_TXN_REF, vnpTxnRef);
		paramMap.put(Constants.VNPAY_PARAM_SECURE_HASH, vnpSecureHash);

		PaymentGatewayVnPay paymentGateway = (PaymentGatewayVnPay) paymentFactory.getPaymentGateway(PaymentType.VnPay);
		VnPayOrderStatusResponse orderStatusResponse = paymentGateway.processVnPayOrder(paramMap);

		// Return data
		return new ResponseEntity<>(orderStatusResponse, HttpStatus.OK);
	}

	@GetMapping("/returnViettelPay")
	@Timed
	@ApiOperation(value = "returnViettelPay", notes = "Xử lý thanh toán đơn hàng từ vendor Viettel")
	public ResponseEntity<HttpHeaders> returnViettelPay(Device device, @RequestParam("amount") String amount,
			@RequestParam("command") String command, @RequestParam("merchant_code") String merchantCode,
			@RequestParam("merchant_trans_id") String merchantTransId,
			@RequestParam("response_code") String responseCode, @RequestParam("version") String version,
			@RequestParam("secure_hash") String secureHash) throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to returnViettelPay, {}");

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put(Constants.VIETTEL_PAY_PARAM_AMOUNT, amount);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_COMMAND, command);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_MERCHANT_CODE, merchantCode);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_MERCHANT_TRANS_ID, merchantTransId);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_RESPONSE_CODE, responseCode);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_VERSION, version);
		paramMap.put(Constants.VIETTEL_PAY_PARAM_SECURE_HASH, secureHash);

		PaymentGateway paymentGateway = paymentFactory.getPaymentGateway(PaymentType.ViettelPay);
		PaymentResult paymentResult = paymentGateway.processReturn(paramMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(getRedirectUrl(device)));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	private String getRedirectUrl(Device device) {
		// TODO : mobile : /landing
		// web : /cart
		boolean isMobile = false;
		if (device.isMobile() || device.isTablet()) {
			log.debug("Detect device type is Mobile : {} or tablet : {}", device.isMobile(), device.isTablet());
			isMobile = true;
	    }		
		
		String redirectUrl = applicationProperties.getPaymentReturnPage();
		if(isMobile) {
			redirectUrl = applicationProperties.getPaymentMobileReturnPage();
		}
		
		return redirectUrl;
	}
	
	@GetMapping("/checkViettelPayOrderInfo")
	@Timed
	@ApiOperation(value = "checkViettelPayOrderInfo", notes = "Xử lý thanh toán đơn hàng từ vendor Viettel")
	public ResponseEntity<ViettelCheckOrderInfoResponse> checkViettelPayOrderInfo(
			ViettelCheckOrderInfoRequest orderInfoRequest) throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to checkViettelPayOrderInfo, {}");

		PaymentGatewayViettelPay paymentGateway = (PaymentGatewayViettelPay) paymentFactory
				.getPaymentGateway(PaymentType.ViettelPay);
		ViettelCheckOrderInfoResponse checkOrderInfoResponse = paymentGateway.checkOrderInfor(orderInfoRequest);

		// Return data
		return new ResponseEntity<>(checkOrderInfoResponse, HttpStatus.OK);
	}

	@GetMapping("/updateViettelPayOrderStatus")
	@Timed
	@ApiOperation(value = "updateViettelPayOrderStatus", notes = "Xử lý thanh toán đơn hàng từ vendor Viettel")
	public ResponseEntity<ViettelUpdateOrderStatusResponse> updateViettelPayOrderStatus(
			ViettelUpdateOrderStatusRequest orderStatusRequest) throws URISyntaxException, AgencyBusinessException {
		log.info("START REST request to updateViettelPayOrderStatus, {}");

		PaymentGatewayViettelPay paymentGateway = (PaymentGatewayViettelPay) paymentFactory
				.getPaymentGateway(PaymentType.ViettelPay);
		ViettelUpdateOrderStatusResponse updateOrderStatusResponse = paymentGateway
				.updateOrderStatus(orderStatusRequest);

		// Return data
		return new ResponseEntity<>(updateOrderStatusResponse, HttpStatus.OK);
	}
}
