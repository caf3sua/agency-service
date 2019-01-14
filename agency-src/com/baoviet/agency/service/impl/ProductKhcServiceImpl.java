package com.baoviet.agency.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.TlAnnualRate;
import com.baoviet.agency.domain.TlRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TlAddDTO;
import com.baoviet.agency.dto.TlDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.TlAddRepository;
import com.baoviet.agency.repository.TlAnnualRateRepository;
import com.baoviet.agency.repository.TlRateRepository;
import com.baoviet.agency.service.ProductKhcService;
import com.baoviet.agency.service.TlAddService;
import com.baoviet.agency.service.TlService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.KhcResultVM;
import com.baoviet.agency.web.rest.vm.PremiumKhcPersonVM;
import com.baoviet.agency.web.rest.vm.PremiumKhcVM;
import com.baoviet.agency.web.rest.vm.ProductKhcVM;

/**
 * Service Implementation for managing Khc.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductKhcServiceImpl extends AbstractProductService implements ProductKhcService {

	private final Logger log = LoggerFactory.getLogger(ProductKhcServiceImpl.class);
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private TlAnnualRateRepository tlAnnualRateRepository;

	@Autowired
	private TlRateRepository tlRateRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private TlService tlService;

	@Autowired
	private TlAddService tlAddService;
	
	@Autowired
	private TlAddRepository tlAddRepository;

	private static final float LARGER_THAN_100_LESS_THAN_300_RATE = 0.2f;
	private static final float LARGER_THAN_300_RATE = 0.25f;
	public static final int CURRENCY_SCALE = 10000000;

	@Override
	public PremiumKhcVM calculatePremium(PremiumKhcVM obj) throws AgencyBusinessException {
		log.debug("START calculatePremium, obj: {}", obj);
		validDataCalculate(obj);

		// Xu ly nghiep vu
		obj.setNumberMonth(12); // set mặc định
		double phi = 0;
		double giamPhi = 0;

		Date date = new Date();
		String strCurrentDate = dateFormat.format(date);
		Date currentDate = DateUtils.str2Date(strCurrentDate);
		Date ngayThamGia;

		int sn = currentDate.compareTo(DateUtils.str2Date(obj.getInsuranceStartDate()));
		if (sn == 0) {
			ngayThamGia = currentDate;
		} else {
			ngayThamGia = DateUtils.str2Date(obj.getInsuranceStartDate());
		}

		for (PremiumKhcPersonVM item : obj.getPremiumKhcList()) {
			if (item.getDob().equals("0")) {
				throw new AgencyBusinessException(item.getDob() + " has no DOB", ErrorCode.INVALID);
			}

			int tuoi = DateUtils.countYears(DateUtils.str2Date(item.getDob()), ngayThamGia);
			if (tuoi < 16 || tuoi > 70) {
				throw new AgencyBusinessException("dob", ErrorCode.INVALID, "Tuổi không phù hợp");
			}
			double perCost = obj.getPremiumPackage() * getRateByMonthsKHC(obj.getNumberMonth()) * getRateByAge(tuoi);
			phi += perCost;
			item.setPremium(perCost);
		}

		// tinh phi duoc giam theo so nguoi

		if (obj.getNumberPerson() >= 100 && obj.getNumberPerson() <= 300) {
			giamPhi = phi * LARGER_THAN_100_LESS_THAN_300_RATE;
		} else if (obj.getNumberPerson() > 300) {
			giamPhi = phi * LARGER_THAN_300_RATE;
		}

		phi -= giamPhi;

		// KIEM TRA DISCOUNT (theo %) VA TINH TONG PHI BH
		obj.setPremiumNet(phi);

		double discount = 0.0;
		if(obj.getPremiumDiscount() != null) {
			discount = obj.getPremiumDiscount() != 0 ? obj.getPremiumNet() * obj.getPremiumDiscount() / 100 : 0;
		}
		obj.setPremiumKhc(obj.getPremiumNet() - discount);

		return obj;
	}

	@Override
	public KhcResultVM createOrUpdatePolicy(ProductKhcVM objKHC, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("REST request to createOrUpdatePolicy : ProductKhcVM{}", objKHC);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(objKHC.getAgreementId())) {
			validateGycbhNumber(objKHC.getGycbhNumber(), currentAgency.getMa());	
		}
		
		// kiem tra truoc khi update
		if (StringUtils.isNotEmpty(objKHC.getAgreementId())) {
			AgreementDTO data = agreementService.findById(objKHC.getAgreementId());
			if (data == null) {
				throw new AgencyBusinessException(objKHC.getAgreementId(), ErrorCode.INVALID, "Không tồn tại dữ liệu với agrementId = " + objKHC.getAgreementId());
			}			
		}
				
		Contact co = contactRepository.findOneByContactCodeAndType(objKHC.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Không tồn tại dữ liệu");
		}
		
		// Validate data
		validateDataPolicy(objKHC);
		
		//Tính phí và validate tính phí
		PremiumKhcVM khcPremium = getPremiumCreate(objKHC);

		// insert TL
		TlDTO khc = getObjectProduct(objKHC, co, khcPremium, currentAgency);
		
		String strTlid = tlService.insertTl(khc);
		log.debug("Result of save tl, {}", strTlid);

		// xóa TL_ADD khi update
		List<TlAddDTO> tlAdds = getTlADDFromForm(objKHC, strTlid);
		if (StringUtils.isNotEmpty(objKHC.getGycbhId())) {
			// xóa danh sách tlAdd trường hợp update
			tlAddRepository.deleteByTlId(objKHC.getGycbhId());
		}
		// insert TL_ADD
		for (TlAddDTO item : tlAdds) {
			String result = tlAddService.insertTlAdd(item);
			log.debug("Result of save tlAdd, {}", result);
		}

		// insert AGREEMENT
		AgreementDTO agreementDTO = getObjectAgreement(strTlid, objKHC, co, khcPremium, currentAgency);
		AgreementDTO agreementDTOSave = agreementService.save(agreementDTO);
		log.debug("Result of save agreement, {}", agreementDTOSave);

		// check TH thêm mới: 0, update: 1 để gửi sms
//        if (StringUtils.isEmpty(objKHC.getAgreementId())) {
        	// pay_action
         	sendSmsAndSavePayActionInfo(co, agreementDTOSave, "0");	
//        } else {
//        	sendSmsAndSavePayActionInfo(co, agreementDTOSave, "1");
//        }
		
		KhcResultVM objResultKhc = new KhcResultVM();
		objResultKhc.setTlDTO(khc);
		objResultKhc.setAgreementDTO(agreementDTOSave);
		objResultKhc.setTlAddDTO(tlAdds);
		objResultKhc.setKhcBaseVM(objKHC);
		return objResultKhc;
	}
	
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	
	private PremiumKhcVM getPremiumCreate(ProductKhcVM objKHC) throws AgencyBusinessException {
		log.debug("REST request to getPremiumCreate : ProductKhcVM{}", objKHC);
		PremiumKhcVM khcPremium = new PremiumKhcVM();
		khcPremium.setInsuranceStartDate(objKHC.getInceptionDate());
		khcPremium.setNumberMonth(12);
		khcPremium.setNumberPerson(objKHC.getPermanentTotalDisablement());
		double premiumPackage = objKHC.getPlan() * CURRENCY_SCALE;
		khcPremium.setPremiumPackage(premiumPackage);

		List<PremiumKhcPersonVM> listPremiumKHC = new ArrayList<PremiumKhcPersonVM>();
		for (TlAddDTO item : objKHC.getTlAddcollections()) {
			PremiumKhcPersonVM premiumKhcPersonVM = new PremiumKhcPersonVM();
			premiumKhcPersonVM.setDob(DateUtils.date2Str(item.getDob()));
			premiumKhcPersonVM.setInsuredName(item.getInsuredName());
			premiumKhcPersonVM.setPremium(item.getPremium());
			listPremiumKHC.add(premiumKhcPersonVM);
		}

		khcPremium.setPremiumKhcList(listPremiumKHC);
		khcPremium = calculatePremium(khcPremium);
		return khcPremium;
	}

	// Valid Data Passed From Client
	private void validDataCalculate(PremiumKhcVM obj) throws AgencyBusinessException {
		log.debug("REST request to validDataCalculate : PremiumKhcVM{}", obj);
		if (obj.getNumberPerson() != obj.getPremiumKhcList().size()) {
			throw new AgencyBusinessException("numberPerson", ErrorCode.INVALID);
		}

		if (obj.getNumberPerson() > 20) {
			throw new AgencyBusinessException("numberPerson", ErrorCode.OUT_OF_RANGER, "NumberPerson not over 20");
		}

		if (obj.getPremiumPackage() > 50000000) {
			throw new AgencyBusinessException("premiumPackage", ErrorCode.OUT_OF_RANGER, "Package not over 50.000.000");
		}
	}

	private double getRateByAge(int age) {
		log.debug("getRateByAge, age: {}", age);
		// Hiện tại lấy thằng đầu tiên vì data base sửa thêm dữ liệu
		TlAnnualRate tlAnnualRate = tlAnnualRateRepository.findFirstByAgeFromLessThanEqualAndAgeToGreaterThanEqual(age, age);
		return tlAnnualRate.getRate() / 100;
	}

	private double getRateByMonthsKHC(int months) {
		log.debug("getRateByMonthsKHC, months: {}", months);
		TlRate tlRate = tlRateRepository.findByFrommonthLessThanEqualAndTomonthGreaterThanEqual(months, months);
		return tlRate.getRate();
	}

	private void validateDataPolicy(ProductKhcVM objKHC) throws AgencyBusinessException {
		log.debug("REST request to validateDataPolicy : ProductKhcVM{}", objKHC);
		// Validate thông tin người nhận đơn bảo hiểm, gtgt
		validateInvoiceInfo(objKHC.getInvoiceInfo());
		
		Date currentDate = new Date();
		if (!DateUtils.isValidDate(objKHC.getInceptionDate(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (DateUtils.str2Date(objKHC.getInceptionDate()).before(currentDate)) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID, "Thời hạn BH phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}
		
		if (!ValidateUtils.isPhone(objKHC.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}
		
		// Validate thông tin người tham gia bảo hiểm
		for (TlAddDTO item : objKHC.getTlAddcollections()) {

			if (StringUtils.isEmpty(item.getInsuredName())) {
				throw new AgencyBusinessException("insuredName", ErrorCode.NULL_OR_EMPTY);
			}

			if (StringUtils.isEmpty(DateUtils.date2Str(item.getDob()))) {
				throw new AgencyBusinessException("dob", ErrorCode.NULL_OR_EMPTY);
			}

			if (StringUtils.isEmpty(item.getIdPasswport())) {
				throw new AgencyBusinessException("idPasswport", ErrorCode.NULL_OR_EMPTY);
			}

		}
	}

	private TlDTO getObjectProduct(ProductKhcVM objKHC, Contact co, PremiumKhcVM khcPremium, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductKhcVM{}, Contact{}, PremiumKhcVM{}, AgencyDTO{}", objKHC, co, khcPremium, currentAgency);
		TlDTO khc = new TlDTO();
		
		if (StringUtils.isNotEmpty(objKHC.getGycbhId())) {
			khc.setTlId(objKHC.getGycbhId());
		}
		khc.setStatusId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		khc.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(khc.getTlId())) {
			khc.setSoGycbh(objKHC.getGycbhNumber());
			khc.setPolicyNumber(objKHC.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(objKHC.getAgreementId());
			
			if (data != null) {
				khc.setSoGycbh(data.getGycbhNumber());
				khc.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		khc.setOldGycbhNumber(objKHC.getOldGycbhNumber());
		khc.setOldPolicyNumber(objKHC.getOldGycbhNumber());
		khc.setAgentId(currentAgency.getMa());
		khc.setAgentName(currentAgency.getTen());
		
		double death = objKHC.getPlan() * CURRENCY_SCALE;
		khc.setDeath(death);
		khc.setInceptionDate(DateUtils.str2Date(objKHC.getInceptionDate()));
		khc.setExpiredDate(DateUtils.getCurrentYearPrevious(khc.getInceptionDate(), -1)); 
		khc.setPermanentTotalDisablement(objKHC.getPermanentTotalDisablement());
		khc.setPlan(String.valueOf(objKHC.getPlan() * CURRENCY_SCALE));
		khc.setTotalBasicPremium(khcPremium.getPremiumNet());
		khc.setTotalNetPremium(khcPremium.getPremiumNet());
		khc.setTotalPremium(khcPremium.getPremiumKhc());

		khc.setReceiverName(objKHC.getReceiverUser().getName());
		khc.setReceiverAddress(objKHC.getReceiverUser().getAddress());
		khc.setReceiverMobile(objKHC.getReceiverUser().getMobile());
		
		if(objKHC.getInvoiceInfo() != null) {
			khc.setVoiceCheck(1);
			khc.setInvoceNumber(objKHC.getInvoiceInfo().getTaxNo());
			khc.setInvoiceAddress(objKHC.getInvoiceInfo().getAddress());
			khc.setInvoiceCompany(objKHC.getInvoiceInfo().getCompany());
		} else {
			khc.setVoiceCheck(0);
		}

		khc.setBeneficiaryAddress(co.getHomeAddress());
		khc.setBeneficiaryIdNumber(co.getIdNumber());
		khc.setBeneficiaryName(co.getContactName());
		khc.setContactName(co.getContactName());
		khc.setContactEmail(co.getEmail());
		khc.setContactGoitinhId(co.getContactSex());
		khc.setContactGoitinhName(co.getContactSexName());
		khc.setContactMobilePhone(co.getHandPhone());
		khc.setContactPhone(co.getPhone());
		khc.setDateOfBirth(co.getDateOfBirth());
		khc.setContactId(co.getContactId());
		khc.setContactCode(co.getContactCode());
		
		return khc;
	}

	private List<TlAddDTO> getTlADDFromForm(ProductKhcVM objKHC, String tlId) {
		log.debug("REST request to getTlADDFromForm : ProductKhcVM{}, tlId{}", objKHC, tlId);
		List<TlAddDTO> listtlAdd = new ArrayList<TlAddDTO>();
		Date dateDob = new Date();
		for (TlAddDTO item : objKHC.getTlAddcollections()) {
			TlAddDTO khcAdd = new TlAddDTO();
			khcAdd.setTlId(tlId);
			khcAdd.setInsuredName(item.getInsuredName());
			khcAdd.setIdPasswport(item.getIdPasswport());
			khcAdd.setTitle(item.getTitle());
			if (StringUtils.isEmpty(DateUtils.date2Str(item.getDob()))) {
				khcAdd.setDob(dateDob);
			} else {
				khcAdd.setDob(item.getDob());
			}
			khcAdd.setPremium(item.getPremium());
			listtlAdd.add(khcAdd);
		}
		return listtlAdd;
	}

	private AgreementDTO getObjectAgreement(String gycbhId, ProductKhcVM objKHC,
			Contact co, PremiumKhcVM khcPremium, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : gycbhId{}, ProductKhcVM{}, Contact{}, PremiumKhcVM{}, AgencyDTO{}", gycbhId, objKHC, co, khcPremium, currentAgency);
		AgreementDTO voAg = new AgreementDTO();
		// Insert common data
		insertAgreementCommonInfo("KHC", voAg, co, currentAgency, objKHC);
		
		voAg.setGycbhId(gycbhId);
		voAg.setInceptionDate(DateUtils.str2Date(objKHC.getInceptionDate()));
		voAg.setExpiredDate(DateUtils.getCurrentYearPrevious(voAg.getInceptionDate(), -1));
		voAg.setUserAgent(objKHC.getUserAgent());
		voAg.setStandardPremium(khcPremium.getPremiumNet());// txtTongPhiBaoHiem.ValueDecimal;
		if(khcPremium.getPremiumDiscount() != null && khcPremium.getPremiumNet() != null) {
			voAg.setChangePremium(khcPremium.getPremiumDiscount() != 0 ? khcPremium.getPremiumNet() - khcPremium.getPremiumDiscount() : 0);
		} else {
			voAg.setChangePremium(0d);
		}
		
		if(voAg.getStandardPremium() != null) {
			voAg.setNetPremium(voAg.getStandardPremium() - voAg.getChangePremium());
		} 
		voAg.setTotalVat(0.0);
		voAg.setTotalPremium(voAg.getNetPremium()); // chưa thanh toán nên phí total = phí net
		voAg.setTeamId(String.valueOf(objKHC.getPermanentTotalDisablement()));
		return voAg;
	}

}
