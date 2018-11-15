package com.baoviet.agency.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.CarRate;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.CarDTO;
import com.baoviet.agency.dto.PurposeOfUsageDTO;
import com.baoviet.agency.dto.SppCarDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.CarRateService;
import com.baoviet.agency.service.CarService;
import com.baoviet.agency.service.ProductCARService;
import com.baoviet.agency.service.PurposeOfUsageService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.ProductCarVM;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;

/**
 * Service Implementation for managing CAR.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductCARServiceImpl extends AbstractProductService implements ProductCARService {

	private final Logger log = LoggerFactory.getLogger(ProductCARServiceImpl.class);

	@Autowired
	private PurposeOfUsageService purposeOfUsageService;
	@Autowired
	private CarRateService carRateService;
	@Autowired
	private CarService carService;

	@Autowired
	private ContactRepository contactRepository;
	
	public int seatNumberMin = 0;
	public int seatNumberMax = 0;

	public ProductCarVM createOrUpdatePolicy(ProductCarVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("Result of createPolicy CARBaseVM, {}", obj);
		
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}
		
		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}
		
		// Check validate data
		validateDataPolicy(obj, currentAgency);

		// kiem tra phi BH
		validatePremium(obj);
		
		// thuc hien luu du lieu
		CarDTO carinfo = getObjectProduct(obj, co, currentAgency);
		
		AgreementDTO agreementInfo = getObjectAgreement(carinfo, co, obj, currentAgency);
		if (carinfo != null && agreementInfo != null) {
			String carId = carService.InsertCar(carinfo);
			if (carId != null) {
				agreementInfo.setGycbhId(carId);
				AgreementDTO agreementSave = agreementService.save(agreementInfo);
				log.debug("Result of saveAgrement, {}", agreementSave.getAgreementId());
				obj.setAgreementId(agreementSave.getAgreementId());
			}
			
			// pay_action
			sendSmsAndSavePayActionInfo(co, agreementInfo);
		}
		
		return obj;
	}

	@Override
	public PremiumCARVM calculatePremium(PremiumCARVM param, String agencyRole) throws AgencyBusinessException {
		log.debug("Result of calculatePremium PremiumCARVM, {}", param);
		// validate data
		validateData(param);
		// calculate
		// tam thoi chua lay phi theo tung agency voi nen set agency role = null
		// agencyRole = "";
		// 1. TNDS
		param = CalPremiumTNDS(param, agencyRole, purposeOfUsageService, carRateService);

		// 2. TNDSTN
		param = CalPremiumTNDSTN(param, agencyRole, purposeOfUsageService, carRateService);

		// 3. NNTX
		param = CalPremiumNNTX(param);

		// 4. VCX
		param = CalPremiumVCX(param);

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		param.setPremium(param.getTndsbbPhi() + param.getTndstnPhi() + param.getNntxPhi() + param.getVcxPhi());

		if (param.getChangePremium() > 0) {
			param.setTotalPremium(param.getPremium() -  (param.getPremium() * param.getChangePremium()/100));
		} else {
			param.setTotalPremium(param.getPremium());
		}
		//// tổng phí bảo hiểm bang phi NET - DISCOUNT
		// obj.premiumCar = obj.PremiumNet - discount;

		return param;
	}

	@Override
	public String getMinManufactureYear(String carId) {
		log.debug("Result of getMinManufactureYear , {}", carId);
		return carService.getMinManufactureYear(carId);
	}

	@Override
	public String getMaxManufactureYear(String carId) {
		log.debug("Result of getMaxManufactureYear , {}", carId);
		return carService.getMaxManufactureYear(carId);
	}

	@Override
	public String getCarPriceWithYear(String carId, Integer year) {
		log.debug("Result of getMaxManufactureYear, carId{}, year{}", carId, year);
		return carService.getCarPriceWithYear(carId, year);
	}

	@Override
	public List<SppCarDTO> getCarInfo() {
		return carService.getCarInfo();
	}

	@Override
	public List<String> getCarMakes() {
		return carService.getCarMakes();
	}

	@Override
	public List<SppCarDTO> getCarModel(String model) throws AgencyBusinessException {
		return carService.getCarModel(model.toUpperCase());
	}

	@Override
	public List<String> getAllYear() throws AgencyBusinessException {
		return carService.getAllYear();
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private AgreementDTO getObjectAgreement(CarDTO carDTO, Contact co, ProductCarVM obj, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("Request to getObjectAgreement, CarDTO{}, Contact{}, ProductCarVM{}, AgencyDTO{} :", carDTO, co, obj, currentAgency);
		AgreementDTO agreementInfo = new AgreementDTO();
//		// NamNH: 18/06/2018
//		if (StringUtils.isNotEmpty(obj.getAgreementId())) {
//			agreementInfo.setAgreementId(obj.getAgreementId());
//		}
		// Insert common data
		insertAgreementCommonInfo("CAR", agreementInfo, co, currentAgency, obj);
		
		agreementInfo.setUserAgent(obj.getUserAgent());
		agreementInfo.setInceptionDate(carDTO.getInceptionDate());
		agreementInfo.setExpiredDate(carDTO.getExpiredDate());

		agreementInfo.setStandardPremium(obj.getThirdPartyPremium() + obj.getTndstnPhi()
				+ obj.getPassengersAccidentPremium() + obj.getPhysicalDamagePremium());
		// giam phi
		agreementInfo.setChangePremium(obj.getChangePremium());
		agreementInfo.setTotalVat(
				(obj.getThirdPartyPremium() / 11) + (obj.getPhysicalDamagePremium() / 11) + (obj.getTndstnPhi() / 11));
		// phi BH chua giam bao gom VAT
		agreementInfo.setNetPremium(obj.getTotalPremium() - agreementInfo.getTotalVat());
		// tong tien thanh toan
		agreementInfo.setTotalPremium(obj.getTotalPremium());
		
		if(co.getContactName() != null) {
			agreementInfo.setStatusRenewalsName(co.getContactName());
		}
		
		return agreementInfo;
	}

	private CarDTO getObjectProduct(ProductCarVM obj, Contact co, AgencyDTO currentAgency) {
		log.debug("Request to getObjectProduct, ProductCarVM{}, Contact{}, AgencyDTO{} :", obj, co, currentAgency);
		CarDTO carInfo = new CarDTO();
		// NamNH: 18/06/2018
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			carInfo.setCarId(obj.getGycbhId());
		}
		
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(carInfo.getCarId())) {
			carInfo.setSoGycbh(obj.getGycbhNumber());
			carInfo.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				carInfo.setSoGycbh(data.getGycbhNumber());
				carInfo.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		carInfo.setInceptionDate(DateUtils.str2Date(obj.getThoihantu()));// Thời hạn bảo hiểm: tu
		Calendar cal = Calendar.getInstance();
		cal.setTime(carInfo.getInceptionDate());
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		carInfo.setExpiredDate(cal.getTime());// Thời hạn bảo hiểm: den
		
		if (obj.getInvoiceInfo() != null) {
			carInfo.setInvoiceCheck(1.0);	
			carInfo.setInvoiceCompany(obj.getInvoiceInfo().getCompany());
			carInfo.setInvoiceAddress(obj.getInvoiceInfo().getAddress());
			carInfo.setInvoiceNumber(obj.getInvoiceInfo().getTaxNo());	// MST	
		} else {
			carInfo.setInvoiceCheck(0d);	
		}
		
		if (co.getDateOfBirth() != null) {
			carInfo.setDateOfBirth(co.getDateOfBirth());	
		} else {
			carInfo.setDateOfBirth(DateUtils.str2Date("01/01/0001"));
		}
		// Thong tin xe bao hiem
		carInfo.setBankId(obj.getInsuredName());// Tên chủ xe(theo đăng ký xe):
		carInfo.setBankName(obj.getInsuredAddress());// Địa chỉ(theo đăng ký xe):
		carInfo.setRegistrationNumber(obj.getRegistrationNumber().toUpperCase());// Biển kiểm soát:
		carInfo.setChassisNumber(obj.getChassisNumber().toLowerCase());// Số khung:
		carInfo.setEngineNumber(obj.getEngineNumber().toUpperCase());// Số máy:
		if (!StringUtils.isEmpty(obj.getOldGycbhNumber())) {
			carInfo.setOldPolicyNumber(obj.getOldGycbhNumber());	
		} 
		if (!StringUtils.isEmpty(obj.getOldGycbhNumber())) {
			carInfo.setOldGycbhNumber(obj.getOldGycbhNumber());
		} 
		// dong xe
		carInfo.setModelId(obj.getModelId());// Dòng xe
		carInfo.setModelName(obj.getModelName());// Dòng xe
		// hang xe
		carInfo.setMakeId(obj.getMakeId());// Hãng xe
		carInfo.setMakeName(obj.getMakeName());// Hãng xe
		if (obj.getTndsbbCheck() || obj.getTndstnCheck()) {
			carInfo.setTypeOfCarid(obj.getTndsSoCho());// Chọn số chỗ ngồi / tải trọng xe: id
			switch (carInfo.getTypeOfCarid()) {
			case "1":
				carInfo.setTypeOfCarname("Loại xe dưới 6 chỗ ngồi");
				break;
			case "2":
				carInfo.setTypeOfCarname("Loại xe từ 6 đến 11 chỗ ngồi");
				break;
			case "3":
				carInfo.setTypeOfCarname("Loại xe từ 12 đến 24 chỗ ngồi");
				break;
			case "4":
				carInfo.setTypeOfCarname("Loại xe trên 24 chỗ ngồi");
				break;
			case "5":
				carInfo.setTypeOfCarname("Xe vừa chở người vừa chở hàng(Pickup, Minivan)");
				break;
			default:
				carInfo.setTypeOfCarname("");
				break;
			}
		}
		carInfo.setPurposeOfUsageId(obj.getPurposeOfUsageId()); // Chọn nhóm loại xe: id
		carInfo.setPurposeOfUsageName("Xe ô tô chở người không kinh doanh vận tải"); // Chọn nhóm loại xe: ten
		if (obj.getVcxCheck()) {
			// gia tri xe
			carInfo.setActualValue(obj.getActualValue());// Giá trị xe theo thị trường
			carInfo.setYearOfMake(obj.getYearOfMake());
			// nam san xuat
			if (!StringUtils.isEmpty(obj.getYearOfMake())) {
				carInfo.setYearOfMake(obj.getYearOfMake());// Năm sản xuất
				Integer namsd = year - Integer.parseInt(obj.getYearOfMake());
				if (namsd == 0)
					namsd = 1;
				carInfo.setYearOfUse(namsd.toString());
			}
			// vat chat xe
			carInfo.setPhysicalDamageSi(obj.getPhysicalDamageSi());// Giá trị xe tham gia bảo hiểm
			carInfo.setPhysicalDamagePremium(obj.getPhysicalDamagePremium());// Phí bảo hiểm Vật chất xe tạm tính (đã bao gồm VAT)

			// dkbs
			if (obj.getKhauTruCheck())
				carInfo.setKhauTru(1.0);// Điều khoản không áp dụng mức khấu trừ
			if (obj.getKhaoHaoCheck())
				carInfo.setKhaoHao(1.0);// Điều khoản không khấu hao thay mới
			if (obj.getNgapNuocCheck())
				carInfo.setNgapNuoc(1.0); // Điều khoản ngập nước
			if (obj.getMatCapCheck())
				carInfo.setMatCap(1.0);// Điều khoản mất cắp bộ phận
			if (obj.getGarageCheck())
				carInfo.setGarage(1.0);// Điều khoản lựa chọn garage sửa chữa
		}
		if (obj.getTndstnCheck()) {
			carInfo.setMatCapBoPhanRate(obj.getTndstnSotien()); // Bảo hiểm trách nhiệm dân sự tự nguyện:Số tiền tham
																// gia bảo hiểm
			carInfo.setMatCapBoPhanPremium(obj.getTndstnPhi());// Phí bảo hiểm trách nhiệm dân sự tự nguyện (đã bao gồm
																// VAT)
		}
		carInfo.setChangePremiumPremium(obj.getChangePremium());
		// NNTX
		if (obj.getNntxCheck()) {
			carInfo.setPassengersAccidentPremium(obj.getPassengersAccidentPremium());// Phí bảo hiểm người ngồi trên xe
			carInfo.setPassengersAccidentNumber(obj.getPassengersAccidentNumber());// Số người tham gia: NNTX
			carInfo.setPassengersAccidentSi(obj.getPassengersAccidentSi());// Số tiền tham gia bảo hiểm: NNTX
		}
		// TNDSBB
		if (obj.getTndsbbCheck()) {
			carInfo.setThirdPartySiCn(100000000.0); // Muc TNDSBB ve nguoi
			carInfo.setThirdPartySiTs(100000000.0); // Muc TNDSBB ve tai san
			carInfo.setThirdPartyPremium(obj.getThirdPartyPremium());// Phí bảo hiểm trách nhiệm dân sự (đã bao gồm VAT)
		}
		
		carInfo.setPremiumVat((obj.getThirdPartyPremium() / 11) + (obj.getPhysicalDamagePremium() / 11)
				+ (obj.getTndstnPhi() / 11) - (obj.getChangePremium() / 11)); // Thue VAT
		carInfo.setTotalBasicPremium(obj.getTotalPremium() - carInfo.getPremiumVat());// tong phi chua VAT
		carInfo.setTotalPremium(obj.getTotalPremium());// Tổng phí thanh toán (đã bao gồm VAT)

		carInfo.setAgentId(currentAgency.getMa());
		carInfo.setAgentName(currentAgency.getTen());

		carInfo.setContactId(co.getContactId()); // Ma khach hang
		if (!StringUtils.isEmpty(co.getContactName())) {
			carInfo.setContactName(co.getContactName());	
		}
		carInfo.setContactCode(co.getContactCode());// user dang nhap
		carInfo.setTaxIdNumber(co.getIdNumber());
		carInfo.setContactEmail(co.getEmail());
		carInfo.setContactPhone(co.getHandPhone());
		carInfo.setContactMobilePhone(co.getHandPhone());
		carInfo.setContactGioitinhId(co.getContactSex());// gioi tinh id
		carInfo.setContactGioitinhName(co.getContactSexName());// gioi tinh: ten

		carInfo.setReceiverName(obj.getReceiverUser().getName()); // Họ và tên người nhận:
		carInfo.setReceiverAddress(obj.getReceiverUser().getAddress());// Địa chỉ người nhận
		carInfo.setReceiverEmail(obj.getReceiverUser().getEmail());// Email người nhận
		carInfo.setReceiverMoible(obj.getReceiverUser().getMobile());// Số điện thoại liên hệ
		
		carInfo.setStatusId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		carInfo.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		carInfo.setPolicyStatus(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		carInfo.setPolicyStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);

		return carInfo;
	}

	private void validateDataPolicy(ProductCarVM obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("Request to validateDataPolicy, ProductCarVM{}, AgencyDTO{} :", obj, currentAgency);
		Date currentDate = new Date();
		Date thoiGianTu = DateUtils.str2Date(obj.getThoihantu());
		if (!DateUtils.isValidDate(obj.getThoihantu(), "dd/MM/yyy")) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.FORMAT_DATE_INVALID, "Error format");
		}
		if (thoiGianTu.before(currentDate)) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.INVALID, "Thời hạn BH phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}

		if (!obj.getPurposeOfUsageId().equals("15")) {
			throw new AgencyBusinessException("purposeOfUsageId", ErrorCode.INVALID);
		}

		if (obj.getTndsbbCheck() || obj.getTndstnCheck()) {
			if (StringUtils.isEmpty(obj.getTndsSoCho()))
				throw new AgencyBusinessException("tndsSocho", ErrorCode.NULL_OR_EMPTY, "Cần chọn số chỗ/trọng tải xe");
			else {
				if (!obj.getTndsSoCho().equals("1") && !obj.getTndsSoCho().equals("2")
						&& !obj.getTndsSoCho().equals("3") && !obj.getTndsSoCho().equals("4")
						&& !obj.getTndsSoCho().equals("5"))
					throw new AgencyBusinessException("tndsSocho", ErrorCode.INVALID, "Cần chọn số chỗ/trọng tải xe");
			}
		}

		if (obj.getTndsbbCheck()) {
			if (obj.getThirdPartyPremium() <= 0)
				throw new AgencyBusinessException("thirdPartyPremium", ErrorCode.INVALID, "Cần nhập phí TNDSBB");
		}

		if (obj.getTndstnCheck()) {
			if (obj.getTndstnSotien() <= 0)
				throw new AgencyBusinessException("tndstnSotien", ErrorCode.INVALID, "Cần nhập mức trách nhiệm TNDSTN");
			if (obj.getTndstnPhi() <= 0)
				throw new AgencyBusinessException("tndstnPhi", ErrorCode.INVALID, "Cần nhập phí BH TNDSTN");
		}

		if (obj.getNntxCheck()) {
			if (obj.getPassengersAccidentNumber() <= 0)
				throw new AgencyBusinessException("passengersAccidentNumber", ErrorCode.INVALID,
						"Cần nhập số người tham gia BH");
			if (obj.getPassengersAccidentSi() <= 0)
				throw new AgencyBusinessException("passengersAccidentSi", ErrorCode.INVALID,
						"Cần nhập mức trách nhiệm NNTX");
		}

		if (obj.getVcxCheck()) {
			if (StringUtils.isEmpty(obj.getYearOfMake()))
				throw new AgencyBusinessException("yearOfMake", ErrorCode.NULL_OR_EMPTY);
			else {
				try {
					Integer.parseInt(obj.getYearOfMake());
				} catch (Exception Ex) {
					throw new AgencyBusinessException("yearOfMake", ErrorCode.INVALID);
				}
			}

			if (StringUtils.isEmpty(obj.getMakeId()))
				throw new AgencyBusinessException("makeId", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(obj.getModelId()))
				throw new AgencyBusinessException("modelId", ErrorCode.NULL_OR_EMPTY);

			if (obj.getPhysicalDamageSi() <= 0)
				throw new AgencyBusinessException("physicalDamageSi", ErrorCode.INVALID, "Cần nhập số tiền BH VCX");
			if (obj.getPhysicalDamagePremium() <= 0)
				throw new AgencyBusinessException("physicalDamagePremium", ErrorCode.INVALID, "Cần nhập phí BH VCX");
		}

		if (obj.getTotalPremium() <= 0)
			throw new AgencyBusinessException("totalPremium", ErrorCode.INVALID, "Cần nhập tổng tiền thanh toán");

		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}
		
		if (StringUtils.isEmpty(currentAgency.getMa())) {
			throw new AgencyBusinessException("type", ErrorCode.NULL_OR_EMPTY);
		}
	}

	private void validateData(PremiumCARVM obj) throws AgencyBusinessException {
		log.debug("Request to validateData, PremiumCARVM{} :", obj);
		if (!obj.getPurposeOfUsageId().equals("15"))
			throw new AgencyBusinessException("purposeOfUsageId", ErrorCode.FORMAT_DATE_INVALID);

		if (obj.getTndsbbCheck() || obj.getTndstnCheck()) {
			if (!(obj.getTndsSoCho().equals("1") || obj.getTndsSoCho().equals("2") || obj.getTndsSoCho().equals("3")
					|| obj.getTndsSoCho().equals("4") || obj.getTndsSoCho().equals("5")))
				throw new AgencyBusinessException("tndsSoCho", ErrorCode.INVALID, "Cần chọn số chỗ/trọng tải xe");
		}
	}
	
	private void validatePremium(ProductCarVM obj) throws AgencyBusinessException {
		log.debug("Request to validatePremium, ProductCarVM{} :", obj);
		PremiumCARVM preCar = new PremiumCARVM();
		preCar.setPurposeOfUsageId(obj.getPurposeOfUsageId());
		
		preCar.setTndsbbCheck(obj.getTndsbbCheck());
		preCar.setTndsSoCho(obj.getTndsSoCho());
		preCar.setTndstnCheck(obj.getTndstnCheck());
		preCar.setTndstnSoTien(obj.getTndstnSotien());
		preCar.setNntxCheck(obj.getNntxCheck());
		preCar.setNntxSoTien(obj.getPassengersAccidentSi());
		if (obj.getPassengersAccidentNumber() > 0) {
			preCar.setNntxSoCho(obj.getPassengersAccidentNumber());
		} else {
			preCar.setNntxSoCho(0d);
		}
		preCar.setVcxCheck(obj.getVcxCheck());
		preCar.setVcxSoTien(obj.getPhysicalDamageSi());
		if (obj.getVcxCheck())
			preCar.setNamSX(Integer.parseInt(obj.getYearOfMake()));
		preCar.setKhauTru(obj.getKhauTruCheck());
		preCar.setKhauHao(obj.getKhaoHaoCheck());
		preCar.setMatCap(obj.getMatCapCheck());
		preCar.setNgapNuoc(obj.getNgapNuocCheck());
		preCar.setGarage(obj.getGarageCheck());
		if (obj.getChangePremium() > 0) {
			preCar.setChangePremium(obj.getChangePremium());
		} else {
			preCar.setChangePremium(0d);
		}
		// tam thoi chua get phi theo agency nen set tam agency = null
		preCar = calculatePremium(preCar, "");
		if (!StringUtils.equals(String.valueOf(preCar.getTotalPremium()), String.valueOf(obj.getTotalPremium()))) {
			 throw new AgencyBusinessException("",ErrorCode.INVALID,"Sai phí bảo hiểm "+ preCar.getTotalPremium() + " <> " + obj.getTotalPremium());	 
		}
		 
	}

	private PremiumCARVM CalPremiumTNDS(PremiumCARVM obj, String agencyRole, PurposeOfUsageService bllp,
			CarRateService dalrate) throws AgencyBusinessException {
		log.debug("Request to CalPremiumTNDS, PremiumCARVM{} :", obj);
		obj.setTndsbbPhi(0.0);

		if (obj.getTndsbbCheck()) {
			if (!(obj.getTndsSoCho().equals("1") || obj.getTndsSoCho().equals("2") || obj.getTndsSoCho().equals("3")
					|| obj.getTndsSoCho().equals("4") || obj.getTndsSoCho().equals("5")))
				throw new AgencyBusinessException("tndsSoCho", ErrorCode.INVALID, "Cần chọn số chỗ/trọng tải xe");

			PurposeOfUsageDTO p = new PurposeOfUsageDTO();
			p = bllp.getPurposeOfUsageById(obj.getTndsSoCho());

			seatNumberMin = p.getSeatNumber();
			seatNumberMax = p.getSeatNumberTo();

			CarRate rate = new CarRate();
			Integer socho = p.getSeatNumber();
			if (socho != 26) {
				if (!StringUtils.isEmpty(agencyRole))
					rate = dalrate.getCarRateByParam(obj.getPurposeOfUsageId(), socho, "2", agencyRole);
				else
					rate = dalrate.getCarRateByParam(obj.getPurposeOfUsageId(), socho, "2");

				if (rate != null) {
					double tndsbbPhi = rate.getGrossPremium();
					obj.setTndsbbPhi(tndsbbPhi);
				}
			}
		}

		return obj;
	}

	private PremiumCARVM CalPremiumTNDSTN(PremiumCARVM obj, String agencyRole, PurposeOfUsageService bllp,
			CarRateService dalrate) throws AgencyBusinessException {
		log.debug("Request to CalPremiumTNDSTN, PremiumCARVM{} :", obj);
		obj.setTndstnPhi(0.0);
		if (obj.getTndstnCheck()) {
			if (obj.getTndstnSoTien() == 0)
				throw new AgencyBusinessException("tndstnSoTien", ErrorCode.INVALID, "Cần lựa chọn số tiền bảo hiểm");

			PurposeOfUsageDTO p = new PurposeOfUsageDTO();
			p = bllp.getPurposeOfUsageById(obj.getTndsSoCho());
			seatNumberMin = p.getSeatNumber();
			seatNumberMax = p.getSeatNumberTo();

			CarRate rate = new CarRate();
			int socho = p.getSeatNumber();
			if (!StringUtils.isEmpty(agencyRole))
				rate = dalrate.getCarRateByParam(obj.getPurposeOfUsageId(), socho, "4", agencyRole);
			else
				rate = dalrate.getCarRateByParam(obj.getPurposeOfUsageId(), socho, "4");

			obj.setTndstnPhi(obj.getTndstnSoTien() * rate.getGrossPremium() / 100);
		}

		return obj;
	}

	private PremiumCARVM CalPremiumNNTX(PremiumCARVM obj) throws AgencyBusinessException {
		log.debug("Request to CalPremiumNNTX, PremiumCARVM{} :", obj);
		obj.setNntxPhi(0.0);

		if (obj.getNntxCheck()) {
			if (obj.getNntxSoTien() == 0)
				throw new AgencyBusinessException("nntxSoTien", ErrorCode.INVALID, "Cần lựa chọn số tiền bảo hiểm");
			if (obj.getNntxSoCho() == 0)
				throw new AgencyBusinessException("nntxSoCho", ErrorCode.INVALID, "Cần nhập số chỗ bảo hiểm");
			else {
				if (obj.getNntxSoCho() > seatNumberMax)
					throw new AgencyBusinessException("nntxSoCho", ErrorCode.INVALID,
							"Vượt quá số chỗ của xe");
			}
			obj.setNntxPhi(obj.getNntxSoTien() * 1 / 1000 * obj.getNntxSoCho());
		}

		return obj;
	}

	private PremiumCARVM CalPremiumVCX(PremiumCARVM obj) throws AgencyBusinessException {
		log.debug("Request to CalPremiumVCX, PremiumCARVM{} :", obj);
		obj.setVcxPhi(0.0);
		if (!obj.getVcxCheck())
			return obj;

		// nam san xuat
		int namsd = 0;
		if (obj.getNamSX() == 0)
			throw new AgencyBusinessException("namSX", ErrorCode.INVALID, "namSX not equal 0");
		else {
			namsd = Calendar.YEAR - obj.getNamSX();
			if (namsd == 0)
				namsd = 1;
			if (namsd > 20)
				throw new AgencyBusinessException("namSX", ErrorCode.INVALID, "Xe tren 20 nam");
		}

		if (obj.getVcxSoTien() == 0)
			throw new AgencyBusinessException("vcxSoTien", ErrorCode.INVALID, "Chưa điền giá trị xe tham gia bảo hiểm!");

		CarRate rate = new CarRate();
		Double giaTriXe = obj.getVcxSoTien();
		Long phiVCX = 0L;
		rate = carRateService.getCarRateByParam(obj.getPurposeOfUsageId(), 1, "1");
		Double phivc = rate.getNetPremium() / 100 * giaTriXe;

		// dk muc khau tru
		if (obj.getKhauTru()) {
			phivc = phivc + (rate.getNetPremium() / 100 * giaTriXe * 5 / 100);
		}

		// dk khau hao
		if (obj.getKhauHao()) {
			if (namsd > 3 && namsd <= 6)
				phivc = phivc + giaTriXe * 2 / 1000;
			if (namsd > 6 && namsd <= 10)
				phivc = phivc + giaTriXe * 3 / 1000;
			if (namsd > 10 && namsd <= 20)
				phivc = phivc + giaTriXe * 4 / 1000;
		}

		// dk mat cap bo phan
		if (obj.getMatCap())
			phivc = phivc + giaTriXe * 2 / 1000;

		// dk ngap nuoc
		if (obj.getNgapNuoc())
			phivc = phivc + giaTriXe * 1 / 1000;

		// dk gara tu chon
		if (obj.getGarage())
			phivc = phivc + giaTriXe * 1 / 1000;

		phivc = phivc * 110 / 100;
		phiVCX = Math.round(phivc);

		// carInfo.PHYSICAL_DAMAGE_SI - Giá trị xe tham gia bảo hiểm
		obj.setVcxPhi(Double.parseDouble(phiVCX.toString()));// Phí bảo hiểm Vật chất xe tạm tính (đã bao gồm VAT)

		return obj;
	}
}
