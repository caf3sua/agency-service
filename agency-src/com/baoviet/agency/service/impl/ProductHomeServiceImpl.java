package com.baoviet.agency.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.HomeDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.HomeService;
import com.baoviet.agency.service.ProductHomeService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.ProductHomeVM;
import com.baoviet.agency.web.rest.vm.PremiumHomeVM;

/**
 * Service Implementation for managing TVI.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductHomeServiceImpl extends AbstractProductService implements ProductHomeService {

	private final Logger log = LoggerFactory.getLogger(ProductHomeServiceImpl.class);
	private static final double rate1 = 0.0009; // tỉ lệ phí 1 năm
	private static final double rate2 = 0.0015; // tỉ lệ phí 2 năm

	@Autowired
	private HomeService homeService;

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public ProductHomeVM createOrUpdatePolicy(ProductHomeVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to createHomePolicy : {}", obj);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}
		
		// Check validate data
		validateDataPolicy(obj);

		// tính phí bảo hiểm
		obj = getPremiumCreate(obj);
		int idHome = 0;

		Contact contact = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());

		if (contact == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Không tồn tại dữ liệu");
		}

		HomeDTO vohom = getObjectProduct(obj, contact, currentAgency);

		idHome = Integer.parseInt(homeService.insertHome(vohom));

		if (idHome > 0) {
			AgreementDTO voAg = getObjectAgreement(obj, contact, currentAgency);
			voAg.setGycbhId(String.valueOf(idHome));
			AgreementDTO agreementDTOSave = agreementService.save(voAg);
			
			log.debug("Result of save agreement, {}", agreementDTOSave);
			
			// check TH thêm mới: 0, update: 1 để gửi sms
//	        if (StringUtils.isEmpty(obj.getAgreementId())) {
	        	// pay_action
	         	sendSmsAndSavePayActionInfo(contact, agreementDTOSave, "0");	
//	        } else {
//	        	sendSmsAndSavePayActionInfo(contact, agreementDTOSave, "1");
//	        }
	        obj.setAgreementId(agreementDTOSave.getAgreementId());
		}
		return obj;
	}

	@Override
	public PremiumHomeVM calculatePremium(PremiumHomeVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculatePremium : {}", obj);
		validDataCalculate(obj);

		// Xu ly nghiep vu
		double rate1 = 0.0009; // tỉ lệ phí 1 năm
		double rate2 = 0.0015; // tỉ lệ phí 2 năm

		double totalpremium = 0;
		double plan_premium = 0;
		double con_premium = 0;

		if (StringUtils.equals(obj.getYearBuildCode(), "1")) {
			con_premium = Double.parseDouble(obj.getSi()) * rate1;
		} else if (StringUtils.equals(obj.getYearBuildCode(), "2")) {
			con_premium = Double.parseDouble(obj.getSi()) * rate2;
		}

		if (!StringUtils.isEmpty(obj.getSiin()) && obj.getSiin() != null) {
			if (StringUtils.equals(obj.getYearBuildCode(), "1")) {
				switch (obj.getSiin()) {
				case "100000000":
					plan_premium = 180000;
					break;
				case "300000000":
					plan_premium = 510000;
					break;
				case "500000000":
					plan_premium = 800000;
					break;
				case "750000000":
					plan_premium = 1125000;
					break;
				case "1000000000":
					plan_premium = 1400000;
					break;
				default:
					plan_premium = 0;
					break;
				}
			} else if (StringUtils.equals(obj.getYearBuildCode(), "2")) {
				switch (obj.getSiin()) {
				case "100000000":
					plan_premium = 300000;
					break;
				case "300000000":
					plan_premium = 860000;
					break;
				case "500000000":
					plan_premium = 1360000;
					break;
				case "750000000":
					plan_premium = 1910000;
					break;
				case "1000000000":
					plan_premium = 2380000;
					break;
				default:
					plan_premium = 0;
					break;
				}
			}
		}

		obj.setPremiumsi(con_premium);
		obj.setPremiumsiin(plan_premium);
		totalpremium = con_premium + plan_premium;

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		obj.setPremiumNet(totalpremium);
		Double discount = obj.getPremiumDiscount() != 0 ? obj.getPremiumNet() * obj.getPremiumDiscount() / 100 : 0;
		obj.setPremiumHome(obj.getPremiumNet() - discount);

		return obj;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	// Valid Data Passed From Client
	private void validDataCalculate(PremiumHomeVM obj) throws AgencyBusinessException {
		log.debug("REST request to validDataCalculate : {}", obj);
		if (obj.getSi() != null) {
			if (!StringUtils.isEmpty(obj.getSi())) {
				if (Double.parseDouble(obj.getSi()) < 300000000
						|| Double.parseDouble(obj.getSi()) > Double.parseDouble("5000000000")) {
					throw new AgencyBusinessException("si", ErrorCode.OUT_OF_RANGER);
				}
			}
		}

		// 100.000.000	// 300.000.000	// 500.000.000	// 750.000.000	// 1.000.000.000
		if (!StringUtils.isEmpty(obj.getSiin())) {
			if (!StringUtils.equals(obj.getSiin(), "100000000") && !StringUtils.equals(obj.getSiin(), "300000000")
					&& !StringUtils.equals(obj.getSiin(), "500000000") && !StringUtils.equals(obj.getSiin(), "750000000")
					&& !StringUtils.equals(obj.getSiin(), "1000000000"))
				throw new AgencyBusinessException("siin", ErrorCode.INVALID);	
		}
		
		if (!StringUtils.equals(obj.getYearBuildCode(), "1") && !StringUtils.equals(obj.getYearBuildCode(), "2")) {
			throw new AgencyBusinessException("yearBuildCode", ErrorCode.INVALID);
		}

	}

	private void validateDataPolicy(ProductHomeVM obj) throws AgencyBusinessException {
		log.debug("REST request to validateDataPolicy : {}", obj);
		if (Double.parseDouble(obj.getSi()) < 300000000
				|| Double.parseDouble(obj.getSi()) > Double.parseDouble("5000000000")) {
			throw new AgencyBusinessException("si", ErrorCode.INVALID, "300.000.000< SI < 5.000.000.000");
		}

		if (!StringUtils.isEmpty(obj.getSiin())) {
			if (Double.parseDouble(obj.getSiin()) != 100000000 && Double.parseDouble(obj.getSiin()) != 300000000
					&& Double.parseDouble(obj.getSiin()) != 500000000 && Double.parseDouble(obj.getSiin()) != 750000000
					&& Double.parseDouble(obj.getSiin()) != 1000000000) {
				throw new AgencyBusinessException("siin", ErrorCode.INVALID,
						"SIIN must in [100.000.000, 300.000.000, 500.000.000. 750.000.000. 1.000.000.000]");
			}
		}
		
		if (!obj.getBankId().equals("0") && !obj.getBankId().equals("1") && !obj.getBankId().equals("2")) {
			throw new AgencyBusinessException("bankId", ErrorCode.INVALID,
					"bankId must in [0: chủ sở hữu, 1: chủ sở hữu cho thuê nhà, 2: người thuê nhà]");
		}
		// step 2
		if (!obj.getLoaiHinh().equals("0") && !obj.getLoaiHinh().equals("1")) {
			throw new AgencyBusinessException("loaiHinh", ErrorCode.INVALID,
					"loaiHinh must in [0: chung cư, 1: nhà liền kề]");
		}

		Date currentDate = new Date();
		if (!DateUtils.isValidDate(obj.getInceptionDate(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (DateUtils.str2Date(obj.getInceptionDate()).before(currentDate)) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID, "Thời hạn BH phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}

		if (!obj.getWindowLocks().equals("0") && !obj.getWindowLocks().equals("1")) {
			throw new AgencyBusinessException("windowLocks", ErrorCode.INVALID, "windowLocks 0 = Có; 1 = Không");
		}

		if (!obj.getBars().equals("0") && !obj.getBars().equals("1")) {
			throw new AgencyBusinessException("bars", ErrorCode.INVALID, "bars 0 = Có; 1 = Không");
		}
		// step 3
		validateInvoiceInfo(obj.getInvoiceInfo());

		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}
	}

	private ProductHomeVM getPremiumCreate(ProductHomeVM homeBase) {
		log.debug("REST request to getPremiumCreate : {}", homeBase);
		double premiumNet = 0;
		double siin_premium = 0;
		double si_premium = 0;

		if (homeBase.getYearBuildCode().equals("1")) {
			si_premium = Double.parseDouble(homeBase.getSi()) * rate1;
			switch (homeBase.getSiin()) {
			case "100000000":
				siin_premium = 180000;
				break;
			case "300000000":
				siin_premium = 510000;
				break;
			case "500000000":
				siin_premium = 800000;
				break;
			case "750000000":
				siin_premium = 1125000;
				break;
			case "1000000000":
				siin_premium = 1400000;
				break;
			default:
				siin_premium = 0;
				break;
			}
		}

		else if (homeBase.getYearBuildCode().equals("2")) {
			si_premium = Double.parseDouble(homeBase.getSi()) * rate2;
			switch (homeBase.getSiin()) {
			case "100000000":
				siin_premium = 300000;
				break;
			case "300000000":
				siin_premium = 860000;
				break;
			case "500000000":
				siin_premium = 1360000;
				break;
			case "750000000":
				siin_premium = 1910000;
				break;
			case "1000000000":
				siin_premium = 2380000;
				break;
			default:
				siin_premium = 0;
				break;
			}
		}

		premiumNet = si_premium + siin_premium;
		homeBase.setSiPremium(si_premium);
		homeBase.setSiinPremium(siin_premium);
		homeBase.setPremiumNet(premiumNet);
		homeBase.setPremiumHome(
				homeBase.getPremiumDiscount() != 0 ? premiumNet - (premiumNet * homeBase.getPremiumDiscount() / 100)
						: premiumNet);

		return homeBase;
	}

	private HomeDTO getObjectProduct(ProductHomeVM homeBase, Contact co, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductHomeVM{}, Contact{}, AgencyDTO{}", homeBase, co, currentAgency);
		HomeDTO vohom = new HomeDTO();
		if (StringUtils.isNotEmpty(homeBase.getGycbhId())) {
			vohom.setHomeId(homeBase.getGycbhId());
		}
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(vohom.getHomeId())) {
			vohom.setSoGycbh(homeBase.getGycbhNumber());
			vohom.setPolicyNumber(homeBase.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(homeBase.getAgreementId());
			
			if (data != null) {
				vohom.setSoGycbh(data.getGycbhNumber());
				vohom.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		vohom.setConPremium(homeBase.getSiPremium()); // Phí bảo hiểm phần ngôi nhà:
		vohom.setPlanPremium(homeBase.getSiinPremium()); // Phí bảo hiểm phần tài sản bên trong:
		vohom.setAgentId(currentAgency.getMa());
		vohom.setAgentName(currentAgency.getTen());
		vohom.setOldPolicyNumber(homeBase.getOldGycbhNumber());
		vohom.setOldGycbhNumber(homeBase.getOldGycbhNumber());
		
		vohom.setContactId(co.getContactId()); // lưu ID của user đăng nhập
		vohom.setContactCode(co.getContactCode()); // lưu username đăng nhập
		vohom.setTaxIdNumber(co.getIdNumber());
		if (!StringUtils.isEmpty(co.getContactName())) {
			vohom.setContactName(co.getContactName());	
		}
		
		// Tab2
		vohom.setInceptionDate(DateUtils.str2Date(homeBase.getInceptionDate())); // Thời hạn bảo hiểm từ ngày: (*)
		vohom.setExpiredDate(DateUtils.getCurrentYearPrevious(DateUtils.str2Date(homeBase.getInceptionDate()), -1));

		int rdio = Integer.parseInt(homeBase.getBankId());
		vohom.setOwnership(rdio == 0 ? "1000" : (rdio == 1 ? "1001" : (rdio == 2 ? "1002" : "0"))); // Quyền sở hữu ngôi
																									// nhà:(*) lưu id
		vohom.setBankId(homeBase.getBankId().equals("0") ? "Chủ sở hữu"
				: (rdio == 1 ? "Chủ sở hữu cho thuê nhà" : (rdio == 2 ? "Người thuê nhà" : "0"))); // Quyền sở hữu ngôi
																									// nhà:(*) lưu tên
		vohom.setInsuredLocation(homeBase.getInsuredLocation()); // Địa chỉ ngôi nhà mua bảo hiểm(*)
		vohom.setTotalUsedArea(Double.parseDouble(homeBase.getTotalUsedArea())); // Tổng diện tích sử dụng:(*)
		vohom.setLoaiHinh(Integer.parseInt(homeBase.getLoaiHinh())); // Loại hình ngôi nhà:(*) nhận 0 là chung cư, 1 là
																		// liền kề

		// Nguoi duoc bao hiem
		vohom.setInvoiceCompany(homeBase.getInsuranceName()); // Họ và tên người Họ và tên người được bảo hiểm (*)
		vohom.setInvoceNumber(homeBase.getInvoceNumber()); // Số Chứng minh nhân dân/Hộ chiếu: (*) người được bảo hiểm
		vohom.setInvoiceAddress(homeBase.getInsuranceAddress()); // Địa chỉ: (*) người được bảo hiểm
		vohom.setByNight(homeBase.getByNight()); // Điện thoại người được bảo hiểm
		vohom.setWindowLocks(Integer.parseInt(homeBase.getWindowLocks())); // Cau hoi trac nghiem 1 Ngôi nhà được bảo
																			// hiểm là công trình xây dựng với mục đích
																			// để ở và phục vụ sinh hoạt gia đình? nhận
																			// giá trị 0 nếu trả lời là có và 1 nếu là
																			// trả lời 0
		vohom.setBars(Integer.parseInt(homeBase.getBars())); // Cau hoi trac nghiem 2 Ngôi nhà được bảo hiểm được xây
																// dưng bằng gạch, đá, xi măng? nhận giá trị 0 nếu có và
																// 1 nếu không

		// Tab3
		vohom.setReceiverName(homeBase.getReceiverUser().getName()); // Họ và tên người nhận: (*)
		vohom.setReceiverAddress(homeBase.getReceiverUser().getAddress()); // Địa chỉ người nhận: (*)
		vohom.setReceiverEmail(homeBase.getReceiverUser().getEmail()); // Email người nhận tạm thời trên web đang ẩn do chưa yêu
																// cầu chức năng phương thức nhận là bản điện tử
		vohom.setReceiverMoible(homeBase.getReceiverUser().getMobile()); // Số điện thoại liên hệ: (*)
		
		if (co.getContactName() != null) {
			vohom.setContactName(co.getContactName()); 	
		}
		if (co.getHomeAddress() != null) {
			vohom.setPermanentAddress(co.getHomeAddress()); 			
		}
		
		vohom.setConSi(homeBase.getPremiumsi()); // Giới hạn bồi thường phần ngôi nhà(*)
		vohom.setLlimitPerItem(homeBase.getPremiumsiin()); // Giới hạn bồi thường phần Tài sản bên trong
		vohom.setTotalBasicPremium(homeBase.getPremiumNet()); // Tổng phí bảo hiểm:
		vohom.setChangePremiumRate(homeBase.getPremiumDiscount());
		vohom.setChangePremiumPremium(vohom.getChangePremiumRate() * vohom.getTotalBasicPremium() / 100);
		vohom.setTotalNetPremium(vohom.getTotalBasicPremium() - vohom.getChangePremiumPremium()); // Tổng phí khách hàng
																									// cần thanh toán:

		String changePremiumContent = "Giảm " + vohom.getChangePremiumPremium() + "% phí bảo hiểm";
		vohom.setChangePremiumContent(changePremiumContent); // ty le phi thay doi
		vohom.setTotalPremium(vohom.getTotalNetPremium()); // Tổng phí khách hàng cần thanh toán
		vohom.setPolicyStatus(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		vohom.setPolicyStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		
		// 17/07/2018 add them
		vohom.setSi(Double.parseDouble(homeBase.getSi()));
		vohom.setSiin(Double.parseDouble(homeBase.getSiin()));
		vohom.setYearBuildCode(homeBase.getYearBuildCode());
		vohom.setPremiumHome(homeBase.getPremiumHome());

		return vohom;
	}

	private AgreementDTO getObjectAgreement(ProductHomeVM hBase, Contact co, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductHomeVM{}, Contact{}, AgencyDTO{}", hBase, co, currentAgency);
		AgreementDTO voAg = new AgreementDTO();
		// Insert common data
		insertAgreementCommonInfo("HOM", voAg, co, currentAgency, hBase);
		
		voAg.setUserAgent(hBase.getUserAgent());
		voAg.setInceptionDate(DateUtils.str2Date(hBase.getInceptionDate()));
		voAg.setExpiredDate(DateUtils.getCurrentYearPrevious(DateUtils.str2Date(hBase.getInceptionDate()), -1));
		if (hBase.getPremiumDiscount() > 0) {
			voAg.setChangePremium(hBase.getPremiumDiscount() * hBase.getPremiumHome() / 100);	
		} else {
			voAg.setChangePremium(0d);
		}
		
		voAg.setNetPremium(hBase.getPremiumHome());
		voAg.setTotalPremium(hBase.getPremiumNet());
		voAg.setTotalVat(0.0);

		if (hBase.getInsuranceName() != null) {
			voAg.setStatusRenewalsName(hBase.getInsuranceName());	
		}
		
		return voAg;
	}
}
