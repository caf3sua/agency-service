package com.baoviet.agency.payment.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.baoviet.agency.common.ProductType;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.config.ApplicationProperties;
import com.baoviet.agency.config.GateWay123PayConfig;
import com.baoviet.agency.config.GateWayMomoConfig;
import com.baoviet.agency.config.GateWayViettelPayConfig;
import com.baoviet.agency.config.GateWayVnPayConfig;
import com.baoviet.agency.config.PaymentConfig;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.domain.Bvp;
import com.baoviet.agency.domain.Car;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.GrabGiftCode;
import com.baoviet.agency.domain.Home;
import com.baoviet.agency.domain.Kcare;
import com.baoviet.agency.domain.Moto;
import com.baoviet.agency.domain.Pa;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.domain.PayOrder;
import com.baoviet.agency.domain.PromotionBank;
import com.baoviet.agency.domain.SppCompany;
import com.baoviet.agency.domain.Tl;
import com.baoviet.agency.domain.Travelcare;
import com.baoviet.agency.domain.Tvicare;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.GiftCodeDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.payment.common.GycbhStatus;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentStatus;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.common.ReceiveMethod;
import com.baoviet.agency.payment.common.StatusCardType;
import com.baoviet.agency.payment.common.VisaBankType;
import com.baoviet.agency.payment.domain.CodeMan;
import com.baoviet.agency.payment.domain.PaymentBank;
import com.baoviet.agency.payment.gateway.impl.PaymentGateway123Pay;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.repository.AnchiRepository;
import com.baoviet.agency.repository.BVPRepository;
import com.baoviet.agency.repository.CarRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.GiftCodeRepository;
import com.baoviet.agency.repository.GrabGiftCodeRepository;
import com.baoviet.agency.repository.HomeRepository;
import com.baoviet.agency.repository.KcareRepository;
import com.baoviet.agency.repository.MotoRepository;
import com.baoviet.agency.repository.PaRepository;
import com.baoviet.agency.repository.PayActionRepository;
import com.baoviet.agency.repository.PayOrderRepository;
import com.baoviet.agency.repository.PaymentRepository;
import com.baoviet.agency.repository.PromotionBankRepository;
import com.baoviet.agency.repository.SppCompanyRepository;
import com.baoviet.agency.repository.TlRepository;
import com.baoviet.agency.repository.TravelcareRepository;
import com.baoviet.agency.repository.TvicareRepository;
import com.baoviet.agency.service.AnchiService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

public abstract class AbstractPaymentGateway implements PaymentGateway {

	private final Logger log = LoggerFactory.getLogger(PaymentGateway123Pay.class);

	@Autowired
	protected ApplicationProperties applicationProperties;

	@Autowired
	protected PaymentRepository paymentRepository;

	@Autowired
	protected CarRepository carRepository;

	@Autowired
	protected MotoRepository motoRepository;

	@Autowired
	protected AgreementRepository agreementRepository;

	@Autowired
	protected PayActionRepository payActionRepository;

	@Autowired
	protected PromotionBankRepository promotionBankRepository;

	@Autowired
	protected HomeRepository homeRepository;

	@Autowired
	protected TravelcareRepository travelcareRepository;

	@Autowired
	protected TvicareRepository tvicareRepository;

	@Autowired
	protected PaRepository paRepository;

	@Autowired
	protected TlRepository tlRepository;

	@Autowired
	protected KcareRepository kcareRepository;

	@Autowired
	protected BVPRepository bvpRepository;

	@Autowired
	protected SppCompanyRepository sppCompanyRepository;

	@Autowired
	protected PayOrderRepository payOrderRepository;

	@Autowired
	protected GiftCodeRepository giftCodeRepository;

	@Autowired
	protected GrabGiftCodeRepository grabGiftCodeRepository;

	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	private AnchiService anchiService;
	
	@Autowired
	private AnchiRepository anchiRepository;
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<PaymentBank> getBanksByPaymentCode(String paymentCode) {
		return paymentRepository.getBanksByPaymentCode(paymentCode);
	}

	protected PaymentResponseType processPayment(PaymentConfig config, PayAction payAction, AgencyDTO currentAgency,
			PaymentProcessRequestVM param, List<Agreement> agreements) {
		// Get all promotion bank
		List<PromotionBank> promotionBanks = promotionBankRepository.findByActive("1");

		// Set initialize status
		payAction.setStatusCard(StatusCardType.NOT_CONFIRMED.getValue());
		if (StringUtils.equals(param.getPaymentType(), "VnPay")) {
			payAction.setAmount(param.getPaymentFee()/100);	
		} else {
			payAction.setAmount(param.getPaymentFee());
		}

		// Check coupon code valid
		if (!StringUtils.isEmpty(param.getCouponCode())) {
			GiftCodeDTO giftCodeDTO = paymentRepository.getGiftCodeByCodeAndEmail(param.getCouponCode(),
					currentAgency.getEmail());

			if (giftCodeDTO == null) {
				return PaymentResponseType.GIFT_CODE_INVALID;
			}
		}

		payAction.setGif3Code(param.getCouponCode());

		// Process refund amount TODO
		payAction.setRefundAmount((int) (param.getPaymentFee() - calculateSumTotalPremium(promotionBanks, config,
				param.getPaymentFee(), param.getBankCode(), agreements)));

		// TODO: Process giftcodes

		log.info("--- Process payment ---");
		// Get process code
		CodeMan codeMan = paymentRepository.getCode(config.getCid(), config.getDid(), config.getCid(), config.getDid(),
				DateUtils.getCurrentYear2Digits());
		payAction.setMciAddId(codeMan.getIssueNumber());

		String policyNumbers = "";
		for (Agreement agreement : agreements) {
			policyNumbers += agreement.getPolicyNumber() + ";";

			agreement.setPolicySendDate(new Date());
			agreement.setMciAddId(codeMan.getIssueNumber());
			agreement.setCouponsCode("to do to do");

			if (agreement.getStandardPremium() == null || agreement.getStandardPremium() == 0) {
				agreement.setStandardPremium(agreement.getNetPremium());
			}

			if (agreement.getNetPremium() != calculateTotalPremium(promotionBanks, config, agreement.getNetPremium(),
					agreement.getLineId(), param.getBankCode(), agreement.getGycbhId())) {
				agreement.setPayDiscount(
						checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), param.getBankCode()));
			}

			agreement.setStatusGycbhId(GycbhStatus.PENDING.name());
			agreement.setStatusGycbhName(GycbhStatus.PENDING.getValue());
			if (config instanceof GateWay123PayConfig) {
				agreement.setPaymentGateway(PaymentType.l23Pay.getValue());
			} else if (config instanceof GateWayMomoConfig) {
				agreement.setPaymentGateway(PaymentType.Momo.getValue());
			} else if (config instanceof GateWayViettelPayConfig) {
				agreement.setPaymentGateway(PaymentType.ViettelPay.getValue());
			} else if (config instanceof GateWayVnPayConfig) {
				agreement.setPaymentGateway(PaymentType.VnPay.getValue());
			}

			// Trừ phí TNDSBB xe ô tô, TNDSBB xe máy
			// duclm comment 04/12/2018
			// calculateTotalPremiumForSpecificAgreement(promotionBanks, config, agreement, param.getBankCode());

			// TODO : user agent
			agreement.setUserAgent("");
		}

		// Assign policy number
		if (policyNumbers.length() > 0) {
			payAction.setPolicyNumbers(policyNumbers.substring(0, policyNumbers.length() - 1));
		}

		// Update all agreements
		agreementRepository.save(agreements);

		return PaymentResponseType.SUCCESS;
	}

	protected Double calculateSumTotalPremium(List<PromotionBank> promotionBanks, PaymentConfig config,
			Double netPremium, String bankCode, List<Agreement> agreements) {
		Double discount = 0d;
		String listIDNH = "";
		String listIDSP = "";
		Double result = netPremium;
		Date now = new Date();
		for (PromotionBank promotionBank : promotionBanks) {
			if (promotionBank.getFromDate().compareTo(now) <= 0 && promotionBank.getToDate().compareTo(now) >= 0) {
				if (config instanceof GateWayMomoConfig && !promotionBank.getIdType().equals("PAYMENTGATEWAY")) {
					continue;
				} else {
					listIDNH = promotionBank.getLineListId();
					discount = promotionBank.getDiscount();
					listIDSP = promotionBank.getTitle();
				}
			}
		}

		Double sumCar = 0d;
		Double sumMo = 0d;
		Double sumAuto = 0d;

		if (!listIDNH.equals("")) {
			if (!listIDNH.equals("MOMO") && bankCode != null) {
				String[] arrIDVISA = {};
				try {
					arrIDVISA = VisaBankType.valueOf(bankCode).getValue().split(",");
				} catch (Exception e) {
				}
				String[] arrID = listIDNH.split(";");

				List<String> listIDVISA = new ArrayList<String>(Arrays.asList(arrIDVISA));
				List<String> listID = new ArrayList<String>(Arrays.asList(arrID));
				listID.retainAll(listIDVISA);
				if (listIDNH.contains(bankCode) || listID.size() > 0) {
					for (Agreement agreement : agreements) {
						if (listIDSP.contains(agreement.getLineId())
								&& !((agreement.getLineId() == ProductType.BVP.name()
										|| agreement.getLineId() == ProductType.KCR.name())
										&& (bankCode == "MRTB" || VisaBankType.valueOf(bankCode)
												.getValue() == "511409,510995,521976,510995,532451,516294,430389"))) {
							if (agreement.getLineId().equals(ProductType.CAR.name())) {
								// Trừ phí TNDSBB xe ô tô
								Car car = carRepository.findOne(agreement.getGycbhId());
								sumCar += agreement.getNetPremium() - car.getPhysicalDamagePremium() * (discount / 100);
							} else if (agreement.getLineId().equals(ProductType.MOT.name())) {
								// Trừ phí TNDSBB xe máy
								Moto moto = motoRepository.findOne(agreement.getGycbhId());
								sumMo += agreement.getNetPremium()
										- (agreement.getNetPremium() - moto.getTndsBbPhi()) * (discount / 100);
							} else {
								sumAuto += agreement.getNetPremium() - (agreement.getNetPremium() * discount / 100);
							}
						} else {
							sumAuto += agreement.getNetPremium();
						}
					}
					result = sumCar + sumMo + sumAuto;
				}
			} else {
//				Double tongPhiGiamMomo = 0d;
				for (Agreement agreement : agreements) {
					if (listIDSP.contains(agreement.getLineId())) {
						if (agreement.getLineId().equals(ProductType.CAR.name())) {
							// Trừ phí TNDSBB xe ô tô
							Car car = carRepository.findOne(agreement.getGycbhId());
//							tongPhiGiamMomo += car.getPhysicalDamagePremium() * (discount / 100);
							if(car == null) {
								sumCar += agreement.getNetPremium();
							} else {
								sumCar += agreement.getNetPremium() - car.getPhysicalDamagePremium() * (discount / 100);
							}
						} else if (agreement.getLineId().equals(ProductType.MOT.name())) {
							// Trừ phí TNDSBB xe máy
							Moto moto = motoRepository.findOne(agreement.getGycbhId());
							if(moto == null) {
								sumMo += agreement.getNetPremium();
							} else {
								sumMo += agreement.getNetPremium()
										- (agreement.getNetPremium() - moto.getTndsBbPhi()) * (discount / 100);
							}
//							tongPhiGiamMomo += (agreement.getNetPremium() - moto.getTndsBbPhi()) * (discount / 100);
						} else {
//							tongPhiGiamMomo += agreement.getNetPremium() * (discount / 100);
							sumAuto += agreement.getNetPremium() - (agreement.getNetPremium() * discount / 100);
						}
					} else {
						sumAuto += agreement.getNetPremium();
					}
				}
				result = sumCar + sumMo + sumAuto;
			}
		}

		return result;
	}

	protected Double calculateTotalPremium(List<PromotionBank> promotionBanks, PaymentConfig config, Double netPremium,
			String lineId, String bankCode, String gycbhId) {
		Double discount = 0d;
		String listIDNH = "";
		String listIDSP = "";
		Double result = netPremium;
		Date now = new Date();
		for (PromotionBank promotionBank : promotionBanks) {
			if (promotionBank.getFromDate().compareTo(now) <= 0 && promotionBank.getToDate().compareTo(now) >= 0) {
				if (config instanceof GateWayMomoConfig && !promotionBank.getIdType().equals("PAYMENTGATEWAY")) {
					continue;
				} else {
					listIDNH = promotionBank.getLineListId();
					discount = promotionBank.getDiscount();
					listIDSP = promotionBank.getTitle();
				}
			}
		}

		Double totalPromotion = netPremium * discount / 100;

		if (!listIDNH.equals("")) {
			if (!listIDNH.equals("MOMO") && bankCode != null) {
				String[] arrIDVISA = {};
				try {
					arrIDVISA = VisaBankType.valueOf(bankCode).getValue().split(",");
				} catch (Exception e) {
				}
				String[] arrID = listIDNH.split(";");

				List<String> listIDVISA = new ArrayList<String>(Arrays.asList(arrIDVISA));
				List<String> listID = new ArrayList<String>(Arrays.asList(arrID));
				listID.retainAll(listIDVISA);
				if (listIDNH.contains(bankCode) || listID.size() > 0) {
					if (listIDSP.contains(lineId) && !((lineId == ProductType.CAR.name()
							|| lineId == ProductType.HOM.name())
							&& (bankCode == "SCB" || VisaBankType.valueOf(bankCode)
									.getValue() == "554627,545579,510235,489518,489517,489516,550796,550796,970429"))) {
						if (lineId.equals(ProductType.CAR.name())) {
							// Trừ phí TNDSBB xe ô tô
							Car car = carRepository.findOne(gycbhId);
							result -= car.getPhysicalDamagePremium() * (discount / 100);
						} else if (lineId.equals(ProductType.MOT.name())) {
							// Trừ phí TNDSBB xe máy
							Moto moto = motoRepository.findOne(gycbhId);
							result -= (result - moto.getTndsBbPhi()) * (discount / 100);
						} else {
							result -= totalPromotion;
						}
					}
				}
			} else {
				if (listIDSP.contains(lineId)) {
					result -= totalPromotion;
				}
			}
		}

		return result;
	}

	protected Double checkGiftCodePercent(List<PromotionBank> promotionBanks, PaymentConfig config, String lineId,
			String bankCode) {
		Double discount = 0d;
		String listIDNH = "";
		String listIDSP = "";
		Double result = 0d;
		Date now = new Date();
		for (PromotionBank promotionBank : promotionBanks) {
			if (promotionBank.getFromDate().compareTo(now) <= 0 && promotionBank.getToDate().compareTo(now) >= 0) {
				if (config instanceof GateWayMomoConfig && !promotionBank.getIdType().equals("PAYMENTGATEWAY")) {
					continue;
				} else {
					listIDNH = promotionBank.getLineListId();
					discount = promotionBank.getDiscount();
					listIDSP = promotionBank.getTitle();
				}
			}
		}

		if (!listIDNH.equals("")) {
			if (!listIDNH.equals("MOMO") && bankCode != null) {
				String[] arrIDVISA = {};
				try {
					arrIDVISA = VisaBankType.valueOf(bankCode).getValue().split(",");
				} catch (Exception e) {
				}
				String[] arrID = listIDNH.split(";");

				List<String> listIDVISA = new ArrayList<String>(Arrays.asList(arrIDVISA));
				List<String> listID = new ArrayList<String>(Arrays.asList(arrID));
				listID.retainAll(listIDVISA);
				if (listIDNH.contains(bankCode) || listID.size() > 0) {
					if (listIDSP.contains(lineId)
							&& !((lineId == ProductType.BVP.name() || lineId == ProductType.KCR.name())
									&& (bankCode == "MRTB" || VisaBankType.valueOf(bankCode)
											.getValue() == "511409,510995,521976,510995,532451,516294,430389"))) {
						result = discount;
					}
				}
			} else {
				if (listIDSP.contains(lineId)) {
					return discount;
				}
			}
		}

		return result;
	}

	protected void calculateTotalPremiumForSpecificAgreement(List<PromotionBank> promotionBanks, PaymentConfig config,
			Agreement agreement, String bankCode) {
		Double totalPremium = 0d;
		if (agreement.getLineId().equals(ProductType.CAR.name())) {
			// Trừ phí TNDSBB xe ô tô
			if (agreement.getGycbhId() != null) {
				Car car = carRepository.findOne(agreement.getGycbhId());
				if (car != null) {
					totalPremium = agreement.getNetPremium() - car.getPhysicalDamagePremium()
							* (checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), bankCode) / 100);	
				}
			} else {
				totalPremium = agreement.getNetPremium() - (agreement.getNetPremium()
						* (checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), bankCode) / 100));
			}
		} else if (agreement.getLineId().equals(ProductType.MOT.name())) {
			// Trừ phí TNDSBB xe máy
			if (agreement.getGycbhId() != null) {
				Moto moto = motoRepository.findOne(agreement.getGycbhId());
				if (moto != null) {
					totalPremium = agreement.getNetPremium() - (agreement.getNetPremium() - moto.getTndsBbPhi())
							* (checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), bankCode) / 100);	
				}
			} else {
				totalPremium = agreement.getNetPremium() - (agreement.getNetPremium()
						* (checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), bankCode) / 100));
			}
		} else {
			totalPremium = agreement.getNetPremium() - (agreement.getNetPremium()
					* (checkGiftCodePercent(promotionBanks, config, agreement.getLineId(), bankCode) / 100));
		}
		agreement.setTotalPremium(totalPremium);
	}

	protected void insertSaleCode(PayAction payAction, String redirectUrl, String bankCode, PaymentType paymentType) {
		payAction.setTransactionDate(new Date());
		payAction.setPayStartDate(new Date());
		payAction.setBankcode(bankCode);
		payAction.setPayLog("[Request]: " + redirectUrl);
		payAction.setPaymentGateway(paymentType.getValue());

		payAction.setStatusEmailFrom(0);
		payAction.setStatusEmailTo(0);
		payAction.setStatusPayFrom(0);
		payAction.setStatusEmailPayFrom(0);
		payAction.setStatus(90);

		payActionRepository.save(payAction);
	}

	protected void updatePaymentResult(PaymentStatus paymentStatus, String mciAddId, String transactionID,
			PayAction payAction) {

		List<Agreement> agreements = agreementRepository.findByMciAddId(mciAddId);

		if (paymentStatus == PaymentStatus.SUCCESSFUL) {
			// duclm add 10.08.2018 start
			// update anchi đã sử dụng
			for (Agreement data : agreements) {
				if (StringUtils.equals(data.getCreateType().toString(), "2")) {
					Anchi anchi = anchiRepository.findOne(data.getGycbhId());
					if (anchi != null) {
						AgreementAnchiVM obj = new AgreementAnchiVM();
						obj.setSoAnchi(anchi.getAchiSoAnchi());
						obj.setTongTienTT(anchi.getAchiStienvn());
						obj.setNgayHieulucTu(DateUtils.date2Str(anchi.getAchiTungay()));
						obj.setNgayHieulucDen(DateUtils.date2Str(anchi.getAchiDenngay()));
						Contact co = contactRepository.findOne(anchi.getContactId());
						obj.setContactCode(co.getContactCode());
						
						try {
							anchiService.wsUpdateAnChi(obj);
						} catch (AgencyBusinessException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			// duclm add 10.08.2018 end
			Date now = new Date();

			for (Agreement data : agreements) {
				if (data.getGycbhId() != null) {	// check vì đơn offline ko có gycbhId duclm add 04/12/2018
					// Forward to company
					// forwardPolicyToCompany(data);

					switch (ProductType.valueOf(data.getLineId())) {
					case HOM:
						Home home = homeRepository.findOne(data.getGycbhId());
						home.setPayment_date(now);
						homeRepository.save(home);
						break;
					case TVC:
						Travelcare travelcare = travelcareRepository.findOne(data.getGycbhId());
						travelcare.setDateOfPayment(now);
						travelcareRepository.save(travelcare);
						break;
					case TVI:
						Tvicare tvicare = tvicareRepository.findOne(data.getGycbhId());
						tvicare.setDateOfPayment(now);
						tvicareRepository.save(tvicare);
						break;
					case CAR:
						Car car = carRepository.findOne(data.getGycbhId());
						car.setDateOfPayment(now);
						carRepository.save(car);
						break;
					case MOT:
						Moto moto = motoRepository.findOne(data.getGycbhId());
						moto.setNgayNopPhi(now);
						motoRepository.save(moto);
						break;
					case TNC:
						Pa pa = paRepository.findOne(data.getGycbhId());
						pa.setDateOfPayment(now);
						paRepository.save(pa);
						break;
					case KHC:
						Tl tl = tlRepository.findOne(data.getGycbhId());
						tl.setDateOfPayment(now);
						tlRepository.save(tl);
						break;
					case KCR:
						Kcare kcare = kcareRepository.findOne(data.getGycbhId());
						kcare.setDateOfPayment(now);
						kcareRepository.save(kcare);
						break;
					case BVP:
						Bvp bvp = bvpRepository.findOne(data.getGycbhId());
						bvp.setDateOfPayment(now);
						bvpRepository.save(bvp);
						break;
					}
				}
			}
			
			// add 19/12/2018: thanh toán thành công chỉ cần set lại cái list được tìm trên kia. 
			// Update agreement
			for (Agreement agreement : agreements) {
				agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_HOANTHANH);
				agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_HOANTHANH);
				agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_HOANTHANH);
				agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_HOANTHANH);
				agreement.setPaymentMethod("1");
				
				if (StringUtils.isNotEmpty(agreement.getUrlPolicy())) {
					agreement.setSendEmail(1);
					agreement.setSendSms(1);					
				}
				
				agreement.setDateOfPayment(new Date());
				agreement.setPaymentTransactionId(transactionID);
				if (agreement.getChangePremium() != null && agreement.getChangePremium() > 0) {
					agreement.setTotalPremium(agreement.getNetPremium() - agreement.getChangePremium());
				} else {
					agreement.setTotalPremium(agreement.getNetPremium());
				}
				agreementRepository.save(agreement);
			}
			

			// Update order
			PayOrder payOrder = payOrderRepository.findByMciAddId(mciAddId);
			if (payOrder != null) {
				payOrder.setPolicyStatusId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
				payOrder.setPolicyStatusName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
				payOrderRepository.save(payOrder);
			}

			// Update giftcode
			if (payAction.getGif3Code() != null && !payAction.getGif3Code().isEmpty()) {
				giftCodeRepository.updateGiftCodesByIds(payAction.getGif3Code());

				// update data to GRAB_GIFTCODE table
				int count = 0;
				if (payAction.getAmount() > 2000000 && payAction.getAmount() < 4000000) {
					count = 10;
				} else if (payAction.getAmount() > 4000000) {
					count = 20;
				}

				List<GrabGiftCode> grabGiftCodes = grabGiftCodeRepository.getGrabGiftCodeByCount(count);
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 90);
				date = c.getTime();
				for (GrabGiftCode grabGiftCode : grabGiftCodes) {
					grabGiftCode.setGiftcodeCode(payAction.getGif3Code());
					grabGiftCode.setToDate(date);
					grabGiftCode.setContactId(payAction.getToContactId());
					grabGiftCode.setContactPhone(payAction.getToPhone());
					grabGiftCode.setContactName(payAction.getToContactName());
					grabGiftCode.setMciAddId(mciAddId);
				}
				grabGiftCodeRepository.save(grabGiftCodes);
				payAction.setStatusEmailFrom(3);
			}
			
		} else {
			for (Agreement data : agreements) {
				if (data.getStatusGycbhId().equals(GycbhStatus.PENDING.name())) {
					data.setStatusGycbhId("");
					data.setPayDiscount(0d);
					data.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
					data.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);

				}
				data.setPaymentTransactionId(transactionID);
			}
			agreementRepository.save(agreements);

			// Gửi email thông báo thanh toán không thành công

			PayOrder payOrder = payOrderRepository.findByMciAddId(mciAddId);
			if (payOrder != null) {
				if (payOrder.getPolicyStatusId() == GycbhStatus.PENDING.name()) {
					payOrder.setPolicyStatusId("");
					payOrderRepository.save(payOrder);
				}	
			}
		}
	}

	@Override
	public PaymentResponseType checkGiftCode(String giftCode, String email, List<Agreement> agreements) {
		GiftCodeDTO giftCodeDTO = paymentRepository.getGiftCodeByCodeAndEmail(giftCode, email);

		if (giftCodeDTO == null) {
			return PaymentResponseType.GIFT_CODE_INVALID;
		}

		Double totalCAR = 0d;
		Boolean isCar = false;
		Boolean isKcr = false;
		Double totalKCR = 0d;

		for (Agreement agreement : agreements) {
			if (agreement.getLineId() == ProductType.CAR.name()) {
				isCar = true;
				totalCAR = totalCAR + agreement.getTotalPremium();
			}
			if (agreement.getLineId() == ProductType.KCR.name()) {
				isKcr = true;
				totalKCR = totalKCR + agreement.getTotalPremium();
			}
		}

		if (!isCar && !isKcr) {
			return PaymentResponseType.GIFT_CODE_ONLY_CAR_KCR;
		}
		
		if ((isCar && totalCAR < 2000000) || (isKcr && totalKCR < 2000000)) {
			return PaymentResponseType.GIFT_CODE_BELOW_LIMIT;
		}

		return PaymentResponseType.SUCCESS;
	}

	private void forwardPolicyToCompany(Agreement agreement) {
		boolean isUpdateCompany = false;
		SppCompany company = sppCompanyRepository.findByCompanyCode("TruSoChinh");
		if (agreement.getReceiveMethod().equals(ReceiveMethod.HardCopy.getValue())) {
			if (agreement.getLineId().equals(ProductType.CAR.name())) {
				Car car = carRepository.findBySoGycbh(agreement.getPolicyNumber());
				if (car.getPhysicalDamagePremium() <= 0) {
					isUpdateCompany = true;
				}
			} else {
				isUpdateCompany = true;
			}

			if (isUpdateCompany) {
				agreement.setBaovietCompanyName(company.getCompanyName());
				agreement.setBaovietCompanyId(company.getCompanyId());
				agreementRepository.save(agreement);
			}
		} else {
			agreement.setBaovietCompanyName(company.getCompanyName());
			agreement.setBaovietCompanyId(company.getCompanyId());
			agreement.setStatusPolicyId("100");
			agreement.setStatusPolicyName("Hoàn thành");
			agreementRepository.save(agreement);
		}
	}
}
