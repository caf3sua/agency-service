package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.GoodsBasicRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.GoodsDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.GoodsBasicRateRepository;
import com.baoviet.agency.service.ExchangeRateService;
import com.baoviet.agency.service.GoodsService;
import com.baoviet.agency.service.ProductHHVCService;
import com.baoviet.agency.web.rest.vm.PremiumHHVCVM;
import com.baoviet.agency.web.rest.vm.ProductHhvcVM;

/**
 * Service Implementation for managing TVI.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductHHVCServiceImpl extends AbstractProductService implements ProductHHVCService {

	private final Logger log = LoggerFactory.getLogger(ProductHHVCServiceImpl.class);

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private GoodsBasicRateRepository goodsBasicRateRepository;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public ProductHhvcVM createOrUpdateHhvcPolicy(ProductHhvcVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("Request to createOrUpdateHhvcPolicy ProductHhvcVM, {}", obj);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}
		
		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}
		
		GoodsDTO voGoods = getGOODSFromForm(obj, currentAgency);
		int result = goodsService.insertGoods(voGoods);
		if (result > 0) {
			String ot_id = String.valueOf(result);
			// set du lieu Agreement
			AgreementDTO ag = getAGREEMENTFromForm(obj, currentAgency);

			ag.setGycbhId(ot_id);
			if (obj.getPaymentMenthod().equals("0")) {
				ag.setPaymentMethod("0");
				ag.setStatusPolicyId("92");
				ag.setStatusPolicyName("Chờ Bảo Việt cấp GCNBH (bản cứng)");
			}

			// ghi agreement
			AgreementDTO agreementDTOSave = agreementService.save(ag);
			int kqag = Integer.parseInt(agreementDTOSave.getAgreementId());
			if (kqag > 0) {
				// TODO: Session
				// CONTACT co = new CONTACT();
				// co = (CONTACT)Session["customerinfo"];
				//
				// if (!StringUtils.isEmpty(receiveusContent.AddressTTNYCBH) &&
				// StringUtils.isEmpty(co.HOME_ADDRESS))
				// co.HOME_ADDRESS = receiveusContent.AddressTTNYCBH;
				// if (!StringUtils.isEmpty(receiveusContent.AddressNYCBH) &&
				// StringUtils.isEmpty(co.HOME_ADDRESS_MAIL))
				// co.HOME_ADDRESS_MAIL = receiveusContent.AddressNYCBH;
				// if (!StringUtils.isEmpty(receiveusContent.MobileNYCBH) &&
				// StringUtils.isEmpty(co.HAND_PHONE))
				// co.HAND_PHONE = receiveusContent.MobileNYCBH;
				// if (!StringUtils.isEmpty(receiveusContent.PassportNYCBH) &&
				// StringUtils.isEmpty(co.ID_NUMBER))
				// co.ID_NUMBER = receiveusContent.PassportNYCBH;
				// if (!StringUtils.isEmpty(receiveusContent.PassportNYCBH) &&
				// StringUtils.isEmpty(co.ID_NUMBER))
				// co.ID_NUMBER = receiveusContent.PassportNYCBH;
				// if (!StringUtils.isEmpty(receiveusContent.EmailNguoiNhan) &&
				// StringUtils.isEmpty(co.EMAIL))
				// co.EMAIL = receiveusContent.EmailNguoiNhan;
				// BLL_CONTACT bllcontac = new BLL_CONTACT();
				// bllcontac.UpdateCONTACT(co);
				// Session["customerinfo"] = co;
				//
				// return result;
				// check TH thêm mới: 0, update: 1 để gửi sms
		        if (StringUtils.isEmpty(obj.getAgreementId())) {
		        	// pay_action
		         	sendSmsAndSavePayActionInfo(co, agreementDTOSave, "0");	
		        } else {
		        	sendSmsAndSavePayActionInfo(co, agreementDTOSave, "1");
		        }
			}
			return obj;
		}
		return null;
	}

	/// <summary>
	/// Service Tinh phi bao Hàng Hóa Vận Chuyển
	/// Tổng phí = phí NET - phí DISCOUNT (nếu có).
	/// </summary>
	/// YEAR_BUILD_CODE: năm bảo hiểm
	/// SI: Giới hạn bồi thường
	/// SIIN: Giới hạn bồi thường bên trong ngôi nhà
	/// Premium_Discount: Giảm phí (nếu không có truyền 0)
	/// Premium_net: mac dinh 0
	/// Premium_home: mặc định 0

	@Override
	public PremiumHHVCVM calculatePremium(PremiumHHVCVM obj) throws AgencyBusinessException {
		log.debug("START calculatePremium, obj: {}", obj);
		validDataCalculate(obj);

		// Xu ly nghiep vu
		int goodsID = obj.getTenHangHoa();

		String paymentCurrencyHangHoa = obj.getLoaiTienTeHangHoa();
		String paymentCurrency = obj.getLoaiTienTe();

		// Call exchange service to get current rate
		double currencyRate = exchangeRateService.getCurrentRate(paymentCurrencyHangHoa, paymentCurrency);

		if ((Integer.toString(obj.getLoaiHangHoa()).equals("14") && isContainerized(obj) != true)
				|| validCaseForContainerized(obj)) {
			// Have no premium for this condition.<See Cargo Category_Rate_Inland
			// Transit_ban luu tru_gốc_trinh duyet.xls>
			// SetDefaultPremium();
			return obj;
		}

		double rate = getPremiumRate(obj);
		double loadingRate = getLoadingRate(obj);
		double totalGoodsCost = obj.getGiaTriHang() + obj.getCuocPhi();
		double basicPremium = totalGoodsCost * rate;
		double loading = loadingRate * basicPremium;
		double totalPremium = basicPremium + loading;

		if (obj.getLaiUocTinh()) {
			totalPremium = totalPremium + (0.1 * totalPremium);
		}
		if (currencyRate > 0) {
			totalPremium = totalPremium * currencyRate;
		}
		double tongPhiVAT = (totalPremium * 1.1);

		double phibhVND = tongPhiVAT * exchangeRateService.getCurrentRate(paymentCurrency, "VND");

		if (phibhVND < 200000) {
			tongPhiVAT = 200000 * exchangeRateService.getCurrentRate("VND", paymentCurrency);
			phibhVND = 200000;
		}

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		obj.setPremiumNet(tongPhiVAT);
		double discount = obj.getPremiumDiscount() != 0 ? obj.getPremiumNet() * obj.getPremiumDiscount() / 100 : 0;
		obj.setPremiumHHVC(obj.getPremiumNet() - discount);
		return obj;

	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private double getLoadingRate(PremiumHHVCVM obj) {
		log.debug("Request to getLoadingRate PremiumHHVCVM, {}", obj);
		double loadingRate = 0;
		String loaiHangHoa = Integer.toString(obj.getLoaiHangHoa());
		// Just 4 categories has loading rate
		if (loaiHangHoa.equals("14") || loaiHangHoa.equals("15") || loaiHangHoa.equals("18")
				|| loaiHangHoa.equals("19")) {
			if (obj.getHanhTrinhVanChuyen() == 1) {
				loadingRate += 5;
			}

			if (obj.getPhuongTienVanChuyen() != 0) {
				int phuongTienVanChuyen = obj.getPhuongTienVanChuyen();
				if (phuongTienVanChuyen == 1) {
					loadingRate += 5;
				}
				if (phuongTienVanChuyen == 2) {
					loadingRate += 5;
				}
				if (phuongTienVanChuyen == 3) {
					loadingRate += 9;
				}
				if (phuongTienVanChuyen == 4) {
					loadingRate += 15;
				}
			}
		}
		return loadingRate / 100;
	}

	private double getPremiumRate(PremiumHHVCVM obj) {
		log.debug("Request to getPremiumRate PremiumHHVCVM, {}", obj);
		double rate = 0;
		int categoryID = obj.getLoaiHangHoa();
		int packedType = obj.getPhuongThucDongGoi();
		int transportId = obj.getPhuongTienVanChuyen();
		int transportRange = obj.getHanhTrinhVanChuyen();

		log.debug("getPremiumRate_GoodsBasicRate, categoryID: {}", obj);
		GoodsBasicRate goodsBasicRate = goodsBasicRateRepository
				.findByCategoryAndPackedTypeAndTransportAndOver500km(categoryID, packedType, transportId,
						transportRange);
		rate = goodsBasicRate.getRate();
		log.debug("getPremiumRate_GoodsBasicRate, rate: {}", rate);

		return rate / 100;
	}

	// Valid Data Passed From Client
	private void validDataCalculate(PremiumHHVCVM obj) throws AgencyBusinessException {
		log.debug("Request to validDataCalculate PremiumHHVCVM, {}", obj);
		if (obj.getPremiumDiscount() != 0 && obj.getPremiumDiscount() > 30) {
			throw new AgencyBusinessException("premiumDiscount", ErrorCode.OUT_OF_RANGER,
					"PremiumDiscount must less than 30%");
		}

		if (StringUtils.equals(Integer.toString(obj.getLoaiHangHoa()), "15")
				|| StringUtils.equals(Integer.toString(obj.getLoaiHangHoa()), "18")
				|| StringUtils.equals(Integer.toString(obj.getLoaiHangHoa()), "19")) {
			throw new AgencyBusinessException("loaiHangHoa", ErrorCode.INVALID);
		}

		if (validCondition(Integer.toString(obj.getDieuKhoanBaoHiem()))) {
			throw new AgencyBusinessException("dieuKhoanBaoHiem", ErrorCode.INVALID);
		}

		if (validCaseForContainerized(obj)) {
			throw new AgencyBusinessException("phuongThucDongGoi", ErrorCode.INVALID);
		}

	}

	private boolean isContainerized(PremiumHHVCVM obj) {
		log.debug("Request to isContainerized PremiumHHVCVM, {}", obj);
		// 0: Default value
		// 1: Non-containerized
		// 2: Containerized
		String phuongThucDongGoi = Integer.toString(obj.getPhuongThucDongGoi());
		switch (phuongThucDongGoi) {
		case "0":
			return false;
		case "1":
			return false;
		case "2":
			return true;
		default:
			return false;
		}
	}

	private static String validConditionIds[] = { "23", "22", "18", "17", "7", "6", "24" };

	private boolean validCondition(String dieuKhoanBaoHiem) {
		for (String item : validConditionIds) {
			if (dieuKhoanBaoHiem.equals(item)) {
				return true;
			}
		}
		return false;
	}

	private boolean validCaseForContainerized(PremiumHHVCVM obj) {
		if ((Integer.toString(obj.getLoaiHangHoa()).equals("5") || Integer.toString(obj.getLoaiHangHoa()).equals("8"))
				&& (isContainerized(obj) == true))
			return true;
		return false;
	}

	private GoodsDTO getGOODSFromForm(ProductHhvcVM obj, AgencyDTO currentAgency) {
		log.debug("Request to getGOODSFromForm ProductHhvcVM, {}", obj);
		GoodsDTO voGoods = new GoodsDTO();
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			voGoods.setHhId(obj.getGycbhId());
		}

		// Khi update ko update gycbhNumber
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			voGoods.setPolicyNumber(obj.getGycbhNumber());
			voGoods.setSoGycbh(obj.getGycbhNumber());
		}
		voGoods.setAgentId(currentAgency.getMa());
		voGoods.setAgentName(currentAgency.getTen());
		
		voGoods.setStatusId("90");
		voGoods.setStatusName("Chờ thanh toán");
		voGoods.setInceptionDate(obj.getInceptionDate());
		voGoods.setExpiredDate(obj.getExpiredDate());
		voGoods.setPolicyStatus("90");
		voGoods.setPolicyStatusName("Chờ thanh toán");
		voGoods.setGoodsCategoryId(obj.getGoodsCategoryId());
		voGoods.setGoodsId(obj.getGoodsId());
		voGoods.setConditionId(obj.getConditionId());
		voGoods.setGoodsPaymentMethod(obj.getGoodsPaymentMethod());
		voGoods.setGoodsPackingMethod(obj.getGoodsPackingMethod());
		voGoods.setGoodsTransportationMethod(0);
		voGoods.setGoodsJourney(Integer.parseInt(obj.getHanhTrinhVanChuyen()));
		voGoods.setGoodsMeanOfTransportation(Integer.parseInt(obj.getPhuongTienVanChuyen()));
		voGoods.setGoodsCost(obj.getGoodsCost());
		voGoods.setGoodsPostage(obj.getGoodsPostage());
		voGoods.setGoodsCurrency(obj.getGoodsCurrency());
		if (obj.getChkThemLaiUocTinh()) {
			voGoods.setIsAdding10Percent(1);
		} else {
			voGoods.setIsAdding10Percent(0);
		}
		voGoods.setGoodsPaymentCurrency(obj.getGoodsPaymentCurrency());
		voGoods.setField5(obj.getField5());
		
//		voGoods.setDateOfRequirement(null);
//		voGoods.setRequirementDob(null);
		voGoods.setRequirementName(obj.getNameNYCBH());
		voGoods.setRequirementAddr(obj.getAddressNYCBH());
		voGoods.setRequirementPhone(obj.getMobileNYCBH());
		voGoods.setRequirementEmail(obj.getEmailNguoiYCBH());
		voGoods.setRequirementIdentity(obj.getPassportNYCBH());
		voGoods.setRequirementMst(obj.getMaSoThueNYCBH());
		
		voGoods.setInsuredName(obj.getInsuredName());
		voGoods.setInsuredIdentity(obj.getInsuredIdentity());
		voGoods.setInsuredDob(null);
		voGoods.setInsuredAddr(obj.getInsuredAddr());
		voGoods.setInsuredPhone(obj.getInsuredPhone());
		voGoods.setInsuredEmail(obj.getInsuredEmail());
		voGoods.setInsuredMst(obj.getInsuredMst());
		
		voGoods.setBeneficiaryName(obj.getBeneficiaryName());
		voGoods.setBeneficiaryIdentity(obj.getBeneficiaryIdentity());
//		voGoods.setBeneficiaryDob(null);
		voGoods.setBeneficiaryAddr(obj.getBeneficiaryAddr());
		voGoods.setBeneficiaryPhone(obj.getBeneficiaryPhone());
		voGoods.setBeneficiaryEmail(obj.getBeneficiaryEmail());
		voGoods.setBeneficiaryMst(obj.getBeneficiaryMst());
		
		voGoods.setContactName(obj.getContactName());
		voGoods.setContactIdentity("");
//		voGoods.setContactDob(null);
		voGoods.setContactAddr(obj.getContactAddr());
		voGoods.setContactPhone(obj.getContactPhone());
		voGoods.setContactEmail(obj.getContactEmail());
		
		voGoods.setGoodsDesc(obj.getGoodsDesc());
		voGoods.setGoodsCode(obj.getGoodsCode());
		voGoods.setPackagingMethod(obj.getPackagingMethod());
		voGoods.setGoodsWeight(obj.getGoodsWeight());
		voGoods.setField1(obj.getField1());
		voGoods.setGoodsQuantities(obj.getGoodsQuantities());
		
		voGoods.setField2(obj.getField2());
		voGoods.setContainerSerialNo(obj.getContainerSerialNo());
		voGoods.setMeanOfTransportation(obj.getMeanOfTransportation());
		voGoods.setJourneyNo(obj.getJourneyNo());
		voGoods.setNote(obj.getNote());
		voGoods.setBlAwbNo(obj.getBlAwbNo());
		voGoods.setStartingGate(obj.getStartingGate());
		voGoods.setDestination(obj.getDestination());
		voGoods.setContractNo(obj.getContractNo());
		voGoods.setLoadingPort(obj.getLoadingPort());
		voGoods.setUnloadingPort(obj.getUnloadingPort());
		voGoods.setLcNo(obj.getLcNo());
		voGoods.setDepartureDay(obj.getDepartureDay());
		voGoods.setConvey(obj.getConvey());
		voGoods.setExpectedDate(obj.getExpectedDate());
		voGoods.setPlaceOfPaymentIndemnity(obj.getPlaceOfPaymentIndemnity());
		voGoods.setGycbhEnVi(obj.getGycbhEnVi());
		voGoods.setTotalNetPremium(obj.getTotalNetPremium());
		voGoods.setTotalBasicPremium(obj.getTotalbasicPremium());
		voGoods.setPremiumVat(obj.getPremiumVat());
		voGoods.setTotalPremium(obj.getTotalPremium());
		voGoods.setOldPolicyNumber(obj.getOldGycbhNumber());
		voGoods.setOldGycbhNumber(obj.getOldGycbhNumber());		
		voGoods.setNote(obj.getNote());
		if (!StringUtils.isEmpty(obj.getPhuongThucThanhToan())) {
			voGoods.setPhuongThucThanhToan(obj.getPhuongThucThanhToan());
		}
		if (!obj.getPhuongThucDongGoi().equals("0")) {
			voGoods.setPhuongThucDongGoi(obj.getPhuongThucDongGoi());
		}
		if (!obj.getHanhTrinhVanChuyen().equals("0")) {
			voGoods.setHanhTrinhVanChuyen(obj.getHanhTrinhVanChuyen());
		}
		if (!obj.getPhuongTienVanChuyen().equals("0")) {
			voGoods.setPhuongTienVanChuyen(obj.getPhuongTienVanChuyen());
		}
		voGoods.setProductName(obj.getProductName());
		voGoods.setMucChungTu(obj.getMucChungTu());
		
//		voGoods.setSendDate(null);
//		voGoods.setResponseDate(null);
//		voGoods.setDateOfPayment(null);
//		voGoods.setLoadingRateJourney(0.0);
//		voGoods.setLoadingRateTransportation(0.0);
//		voGoods.setBasicRate(0.0);
//		voGoods.setOtherInsuranceAgreements("");
//		voGoods.setChangePremiumId("");
//		voGoods.setChangePremiumContent("");
//		voGoods.setChangePremiumRate(0.0);
//		voGoods.setChangePremiumPremium(0.0);
//		voGoods.setBankId("");
//		voGoods.setBankName("");
//		voGoods.setTeamId("");
//		voGoods.setTeamName("");
//		voGoods.setBaovietUserId("");
//		voGoods.setBaovietUserName("");
//		voGoods.setBaovietCompanyId("");
//		voGoods.setBaovietCompanyName("");
//		voGoods.setBaovietDepartmentId("");
//		voGoods.setBaovietDepartmentName("");
//		voGoods.setStatusRenewalsId("");
//		voGoods.setStatusRenewalsName("");
//		voGoods.setRenewalsRate(0.0);
//		voGoods.setRenewalsPremium(0.0);
//		voGoods.setRenewalsReason("");
//		voGoods.setLoanAccount("");
//		voGoods.setChangePremiumPaId("");
//		voGoods.setChangePremiumPaContent("");
//		voGoods.setChangePremiumPaRate(0.0);
//		voGoods.setChangePremiumPaPremium(0.0);
//		voGoods.setInvoiceCompany("");
//		voGoods.setInvoiceAddress("");
//		voGoods.setInvoiceNumber("");
//		voGoods.setInvoiceCheck(0);
//		voGoods.setFeeReceive(0.0);
//		voGoods.setPolicySendDate(null);
//		voGoods.setReceiverEmail("");
//		voGoods.setReceiverMoible("");
//		voGoods.setCouponsCode("");
//		voGoods.setCouponsValue(0.0);

		return voGoods;
	}

	private AgreementDTO getAGREEMENTFromForm(ProductHhvcVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("Request to getAGREEMENTFromForm ProductHhvcVM, {}", obj);
		AgreementDTO pAGREEMENT = new AgreementDTO();
		if (StringUtils.isNotEmpty(obj.getAgreementId())) {
			pAGREEMENT.setAgreementId(obj.getAgreementId());
		}
		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}
		
		// Insert common data
		insertAgreementCommonInfo("HHV", pAGREEMENT, co, currentAgency, obj);
		
		pAGREEMENT.setUserAgent(obj.getUserAgent());
		pAGREEMENT.setInceptionDate(obj.getInceptionDate());
		pAGREEMENT.setExpiredDate(obj.getExpiredDate());

		// giam phi
		pAGREEMENT.setChangePremium(0.0);
		if (obj.getChangePremiumPremium() > 0) {
			pAGREEMENT.setChangePremium(obj.getChangePremiumPremium());
		}
		pAGREEMENT.setTotalVat(0.0);
		if (obj.getCurrencyRate().equals("VND")) {
			pAGREEMENT.setStandardPremium(obj.getStandardPremium());
			pAGREEMENT.setNetPremium(obj.getNetPremium());
			pAGREEMENT.setTotalPremium(obj.getTotalPremium());
		} else {
			pAGREEMENT.setStandardPremium(obj.getPremiumVat());
			pAGREEMENT.setNetPremium(obj.getPremiumVat());
			pAGREEMENT.setTotalPremium(obj.getPremiumVat());
		}
		pAGREEMENT.setTotalVat(obj.getTotalVat());
		pAGREEMENT.setCouponsCode(obj.getCouponsCode());
		if (StringUtils.isEmpty(obj.getIsAdding10Percent().toString())) {
			pAGREEMENT.setCouponsValue(0.0);
		} else {
			pAGREEMENT.setCouponsValue(obj.getIsAdding10Percent());
		}
		pAGREEMENT.setStatusRenewalsId("");
		if (obj.getStatusRenewalsName() != null) {
			pAGREEMENT.setStatusRenewalsName(obj.getStatusRenewalsName().toUpperCase());	
		}
		
		return pAGREEMENT;
	}
}