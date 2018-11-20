package com.baoviet.agency.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.Atcs;
import com.baoviet.agency.domain.Bvp;
import com.baoviet.agency.domain.Car;
import com.baoviet.agency.domain.Home;
import com.baoviet.agency.domain.Kcare;
import com.baoviet.agency.domain.Moto;
import com.baoviet.agency.domain.Pa;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.domain.SppCompany;
import com.baoviet.agency.domain.Tl;
import com.baoviet.agency.domain.Travelcare;
import com.baoviet.agency.domain.Tvicare;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.PaymentMsbDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.repository.AtcsRepository;
import com.baoviet.agency.repository.BVPRepository;
import com.baoviet.agency.repository.CarRepository;
import com.baoviet.agency.repository.HomeRepository;
import com.baoviet.agency.repository.KcareRepository;
import com.baoviet.agency.repository.MotoRepository;
import com.baoviet.agency.repository.PaRepository;
import com.baoviet.agency.repository.PayActionRepository;
import com.baoviet.agency.repository.SppCompanyRepository;
import com.baoviet.agency.repository.TlRepository;
import com.baoviet.agency.repository.TravelcareRepository;
import com.baoviet.agency.repository.TvicareRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.PaymentService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.NotifyPaymentVM;
import com.baoviet.agency.web.rest.vm.PaymentATCSVM;
import com.baoviet.agency.web.rest.vm.PaymentUpdateOTPVM;

/**
 * Service Implementation for managing Payment.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PaymentServiceImpl implements PaymentService {

	private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private AgreementRepository agreementRepository;

	@Autowired
	private AtcsRepository atcsRepository;

	@Autowired
	private PayActionRepository payActionRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private SppCompanyRepository sppCompanyRepository;

	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private TravelcareRepository travelcareRepository;

	@Autowired
	private TvicareRepository tvicareRepository;

	@Autowired
	private MotoRepository motoRepository;

	@Autowired
	private PaRepository paRepository;

	@Autowired
	private TlRepository tlRepository;

	@Autowired
	private KcareRepository kcareRepository;

	@Autowired
	private BVPRepository bVPRepository;

	@Autowired
    private AgreementService agreementService;
	
	private boolean isAuth;

	@Override
	public PaymentMsbDTO updatePaymentATCS(PaymentATCSVM param) {
		log.debug("Request to updatePaymentATCS {}", param);
		PaymentMsbDTO pay = new PaymentMsbDTO();
		pay.setTransactionId(param.getTransactionId());
		pay.setGycbhNumber(param.getGycbhNumber());
		pay.setFeeReceive(param.getFeePayment());

		try {
			if (StringUtils.isEmpty(param.getTransactionId())) {
				throw new AgencyBusinessException("transaction_id", ErrorCode.NULL_OR_EMPTY);
			}
			if (StringUtils.isEmpty(param.getGycbhNumber())) {
				throw new AgencyBusinessException("gycbh_number", ErrorCode.NULL_OR_EMPTY);
			}
			if (StringUtils.isEmpty(String.valueOf(param.getFeePayment()))) {
				throw new AgencyBusinessException("feePayment", ErrorCode.NULL_OR_EMPTY);
			} else {
				if (param.getFeePayment() <= 0) {
					throw new AgencyBusinessException("feePayment", ErrorCode.INVALID);
				}
			}

			// update agreement
			Agreement agreement = agreementRepository.getAgreementByGycbhNumber(param.getGycbhNumber().toUpperCase());
			if (agreement == null) {
				throw new AgencyBusinessException("gycbh_number", ErrorCode.INVALID, "Khong tim thay so YCBH");
			}

			pay.setPremium(agreement.getTotalPremium());

			agreement.setPaymentTransactionId(param.getTransactionId());
			agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON); 
			agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
			agreementRepository.save(agreement);

			// update ATCS
			Atcs atcs = atcsRepository.findBySoGycbh(param.getGycbhNumber());
			if (atcs != null) {
				atcs.setPolicyStatus(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
				atcs.setPolicyStatusName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
				atcsRepository.save(atcs);

				// update pay_action
				PayAction payAction = new PayAction();
				payAction.setTransactionId(param.getTransactionId());
				Date dateNow = new Date();
				payAction.setTransactionDate(dateNow);
				payAction.setMciAddId(param.getGycbhNumber());
				payAction.setStatus(91);
				payAction.setBankcode("MSB");
				payAction.setAmount(Double.parseDouble(String.valueOf(param.getFeePayment())));
				payActionRepository.save(payAction);
				return pay;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public boolean updatePaymentOTP(PaymentUpdateOTPVM param) {
		log.debug("Request to updatePaymentOTP {}", param);
		try {
			// kiểm tra gycbh
			Agreement agreement = agreementRepository.findByGycbhNumber(param.getGycbhNumber());
			if (agreement == null) {
				throw new AgencyBusinessException("gycbh_number", ErrorCode.INVALID, "Khong tim thay so YCBH");
			}

			// kiem tra thời hạn OTP, nếu timeout thì update trường status trong bảng agreement
			// TODO:
			// this.checkTimeoutOTP(_authTokenKeyHeader, agreement);
			if (!isAuth) {
				Date dateNow = new Date();
				// update Agreement
				agreementRepository.UpdateOTP(agreement.getGycbhNumber(), param.getOtp(), "0", DateUtils.date2Str(dateNow));
				return false;
			}

			// kiem tra hợp lệ của OTP
			if (agreement.getOtp() != null && agreement.getOtp().equals(param.getOtp())) {
				if (agreement.getOtpStatus().equals("2")) {
				} else {
					updatePaymentStatus(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON, AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON, "VTP", param.getGycbhNumber(), "1", param.getGycbhNumber());
					Date dateNow = new Date();
					// update Agreement
					agreementRepository.UpdateOTP(agreement.getGycbhNumber(), param.getOtp(), "2", DateUtils.date2Str(dateNow)); // status = 2 thanh toan thanh cong

					// init result data
					agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
					agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
				}
			}
			return true;
		} catch (AgencyBusinessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public NotifyPaymentVM updateAgrement(NotifyPaymentVM param, String agentId) {
		log.debug("Request to updateAgrement NotifyPaymentVM{}, agentId{} :", param, agentId);
		NotifyPaymentVM result = new NotifyPaymentVM();
		if (StringUtils.equals(param.getStatusPayment(), "1")) {
			AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), agentId);
			if (agreement != null) {
				agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
				agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
				agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
				agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
				agreement.setCancelPolicySupport3(1d);	 // gửi mail
				agreement.setCancelPolicyCommision3(1d); // gửi sms
				
				AgreementDTO agreementSave = agreementService.save(agreement);
				log.debug("REST request StatusPolicyId: {}", agreementSave.getStatusPolicyId());
				
				// thay đổi trạng thái trong PayAction
				if (!StringUtils.isEmpty(agreementSave.getMciAddId())) {
					PayAction payAction = payActionRepository.findByMciAddId(agreementSave.getMciAddId());
					if (payAction != null) {
						payAction.setStatus(91); // thành công
						payActionRepository.save(payAction);
					}
				}
				
				result.setGycbhNumber(param.getGycbhNumber());
				result.setMessage("Thanh toán thành công");
				result.setStatusPayment("1");
				result.setResult("1");
				return result;
			}
		} else {
			AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), agentId);
			if (agreement != null) {
				agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_HUYDON);
				agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_HUYDON);
				agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_HUYDON);
				agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_HUYDON);
				
				AgreementDTO agreementSave = agreementService.save(agreement);
				log.debug("REST request StatusPolicyId: {}", agreementSave.getStatusPolicyId());
				result.setGycbhNumber(param.getGycbhNumber());
				result.setMessage("Thanh toán thất bại");
				result.setStatusPayment("0");
				result.setResult("0");
				return result;
			}
		}
		return null;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private void updatePaymentStatus(String statusPolicyId, String statusPolicyName, String paymentMethod,
			String mciAddId, String cancelPolicySupport3, String paymentTransactionId) {
		log.debug("Request to updatePaymentStatus statusPolicyId{}, statusPolicyName{}, paymentMethod{}, mciAddId{}, cancelPolicySupport3{}, paymentTransactionId{} :", statusPolicyId, statusPolicyName, paymentMethod, mciAddId, cancelPolicySupport3, paymentTransactionId);
		Date dateNow = new Date();
		Agreement agreementUp = agreementRepository.findByMciAddIdAndStatusPolicyId(mciAddId, "90");
		if (agreementUp != null) {
			agreementUp.setStatusPolicyId(statusPolicyId);
			agreementUp.setStatusPolicyName(statusPolicyName);
			agreementUp.setStatusGycbhId(statusPolicyId);
			agreementUp.setStatusGycbhName(statusPolicyName);
			agreementUp.setPaymentMethod(paymentMethod);
			agreementUp.setCancelPolicySupport3(Double.parseDouble(cancelPolicySupport3));
			agreementUp.setCancelPolicyCommision3(Double.parseDouble(cancelPolicySupport3));
			agreementUp.setDateOfPayment(dateNow);
			agreementUp.setPaymentTransactionId(paymentTransactionId);

			// update agreement
			agreementRepository.save(agreementUp);
		}

		List<Agreement> lstAgreement = agreementRepository.findByMciAddId(mciAddId);
		if (lstAgreement.size() > 0) {
			for (Agreement agreement : lstAgreement) {

				forwardPolicyToCompany(agreement);

				switch (agreement.getLineId()) {
				case "HOM":
					Home home = homeRepository.findOne(agreement.getGycbhId());
					if (home != null) {
						home.setPayment_date(dateNow);
						// update
						homeRepository.save(home);
					}
					break;
				case "TVC":
					Travelcare travelcare = travelcareRepository.findOne(agreement.getGycbhId());
					if (travelcare != null) {
						travelcare.setDateOfPayment(dateNow);
						// update
						travelcareRepository.save(travelcare);
					}
					break;
				case "TVI":
					Tvicare tvicare = tvicareRepository.findOne(agreement.getGycbhId());
					if (tvicare != null) {
						tvicare.setDateOfPayment(dateNow);
						// update
						tvicareRepository.save(tvicare);
					}
					break;
				case "CAR":
					Car car = carRepository.findOne(agreement.getGycbhId());
					if (car != null) {
						car.setDateOfPayment(dateNow);
						// update
						carRepository.save(car);
					}
					break;
				case "MOT":
					Moto moto = motoRepository.findOne(agreement.getGycbhId());
					if (moto != null) {
						moto.setNgayNopPhi(dateNow);
						// update
						motoRepository.save(moto);
					}
					break;
				case "TNC":
					Pa pa = paRepository.findOne(agreement.getGycbhId());
					if (pa != null) {
						pa.setDateOfPayment(dateNow);
						// update
						paRepository.save(pa);
					}
					break;
				case "KHC":
					Tl tl = tlRepository.findOne(agreement.getGycbhId());
					if (tl != null) {
						tl.setDateOfPayment(dateNow);
						// update
						tlRepository.save(tl);
					}
					break;
				case "KCR":
					Kcare kcare = kcareRepository.findOne(agreement.getGycbhId());
					if (kcare != null) {
						kcare.setDateOfPayment(dateNow);
						// update
						kcareRepository.save(kcare);
					}
					break;
				case "BVP":
					Bvp bvp = bVPRepository.findOne(agreement.getGycbhId());
					if (bvp != null) {
						bvp.setDateOfPayment(dateNow);
						// update
						bVPRepository.save(bvp);
					}
					break;
				default:
					break;
				}

			}
		}

	}

	private void forwardPolicyToCompany(Agreement pAGREEMENT) {
		log.debug("Request to forwardPolicyToCompany, Agreement{} :",pAGREEMENT);
		/**
		 * Bản cứng Chuyển đơn đến phòng PHH - Gửi mail cho TTDVKH - Gửi Mail đến phòng
		 * PHH
		 **/
		if (pAGREEMENT.getReceiveMethod().equals("2")) {
			if (pAGREEMENT.getLineId().equals("CAR"))// Trường hợp giám định
			{
				Car pCar = carRepository.findBySoGycbh(pAGREEMENT.getPolicyNumber());
				if (pCar.getPhysicalDamagePremium() > 0) {
				} else {
					// Chuyển đơn đến phòng phi hàng hải
					SppCompany sppCompany = sppCompanyRepository.findByCompanyCode("TruSoChinh");
					if (sppCompany != null) {
						pAGREEMENT.setBaovietCompanyName(sppCompany.getCompanyName());
						pAGREEMENT.setBaovietCompanyId(sppCompany.getCompanyId());
						// update Agreement
						agreementRepository.save(pAGREEMENT);
					}

				}
			} else {
				// Chuyển đơn đến phòng phi hàng hải
				SppCompany sppCompany = sppCompanyRepository.findByCompanyCode("TruSoChinh");
				if (sppCompany != null) {
					pAGREEMENT.setBaovietCompanyName(sppCompany.getCompanyName());
					pAGREEMENT.setBaovietCompanyId(sppCompany.getCompanyId());
					// update Agreement
					agreementRepository.save(pAGREEMENT);
				}
			}

		} else {
			/**
			 * Bản mềm Chuyển đơn đến phòng PHH - Gửi Mail đến phòng PHH
			 **/
			// Chuyển đơn đến phòng PHH
			SppCompany sppCompany = sppCompanyRepository.findByCompanyCode("TruSoChinh");
			if (sppCompany != null) {
				pAGREEMENT.setBaovietCompanyName(sppCompany.getCompanyName());
				pAGREEMENT.setBaovietCompanyId(sppCompany.getCompanyId());
				pAGREEMENT.setStatusPolicyId("100");
				pAGREEMENT.setStatusPolicyName("Hoàn thành");
				// update Agreement
				agreementRepository.save(pAGREEMENT);
			}
		}
	}
}
