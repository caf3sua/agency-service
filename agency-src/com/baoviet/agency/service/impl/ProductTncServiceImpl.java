package com.baoviet.agency.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.TncAddDTO;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.PaRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.PaAddDTO;
import com.baoviet.agency.dto.PaDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.PaAddRepository;
import com.baoviet.agency.repository.PaRateRepository;
import com.baoviet.agency.service.PaAddService;
import com.baoviet.agency.service.PaService;
import com.baoviet.agency.service.ProductTncService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.PremiumTncVM;
import com.baoviet.agency.web.rest.vm.ProductTncVM;

/**
 * Service Implementation for managing TNC.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductTncServiceImpl extends AbstractProductService implements ProductTncService {

	private final Logger log = LoggerFactory.getLogger(ProductTncServiceImpl.class);

	public static final Long CURRENCY_SCALE = 10000000l;
	public static final float DEFAULT_RATE = 0.0028f;
	public static final float LARGER_THAN_100_LESS_THAN_300_RATE = 0.2f;
	public static final float LARGER_THAN_300_RATE = 0.25f;
	public static final int SO_THANG_BAO_HIEN = 12; // Default Value

	@Autowired
	private PaRateRepository paRateRepository;

	@Autowired
	private PaService paService;
	
	@Autowired
	private PaAddService paAddService;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private PaAddRepository paAddRepository;

	@Override
	public HastableTNC createOrUpdatePolicy(ProductTncVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("request to createOrUpdatePolicy, ProductTncVM {}", obj);
		
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}
		
		String userAgent = request.getHeader("user-agent");

		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}
		
		// tính phí
		PremiumTncVM tnc = getPremiumCreate(obj);

		// Check validate data
		validateDataPolicy(obj);

		PaDTO painfo = getObjectProduct(currentAgency, obj, tnc, co);
		String tncId = paService.Insert(painfo);

		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			// xóa paAdd trường hợp update
			paAddRepository.deleteByPaId(obj.getGycbhId());
		}
		List<PaAddDTO> lstAdd = getPaADDFromForm(obj, tnc, tncId);
		for (PaAddDTO paAddDTO : lstAdd) {
			paAddService.Insert(paAddDTO);
		}

		AgreementDTO agreeInfo = getObjectAgreement(obj, tnc, userAgent, co, currentAgency);
		agreeInfo.setGycbhId(tncId);
		AgreementDTO agreeSave = agreementService.save(agreeInfo);
		agreeInfo.setAgreementId(agreeSave.getAgreementId());

		// check TH thêm mới: 0, update: 1 để gửi sms
        if (StringUtils.isEmpty(obj.getAgreementId())) {
        	// pay_action
         	sendSmsAndSavePayActionInfo(co, agreeSave, "0");	
        } else {
        	sendSmsAndSavePayActionInfo(co, agreeSave, "1");
        }
		
		HastableTNC has = new HastableTNC();
		has.setPa(painfo);
		has.setLstPaAdd(lstAdd);
		has.setAgreement(agreeInfo);

		return has;
	}

	@Override
	public PremiumTncVM calculatePremium(PremiumTncVM obj) throws AgencyBusinessException {
		log.debug("request to calculatePremium, PremiumTncVM {}", obj);
		validDataCalculate(obj);

		double soTienBaoHiem = 0;
		double phiBaoHiem = 0;
		double giamPhiBaoHiem = 0;
		int soNguoiThamGia = 0;

		// Xu ly nghiep vu
		soTienBaoHiem = obj.getPremiumPackage();// *CURRENCY_SCALE;
		soNguoiThamGia = obj.getNumberperson();

		// Tinh Phi Bảo Hiểm
		phiBaoHiem = soTienBaoHiem * DEFAULT_RATE * getRateByMonths(SO_THANG_BAO_HIEN);

		//soNguoiThamGia = obj.getNumberperson();
		phiBaoHiem = phiBaoHiem * soNguoiThamGia;

		if (soNguoiThamGia >= 100 && soNguoiThamGia <= 300)
			giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_100_LESS_THAN_300_RATE;
		else if (soNguoiThamGia > 300)
			giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_300_RATE;
		else
			giamPhiBaoHiem = 0;

		phiBaoHiem -= giamPhiBaoHiem;

		// tinh phi giam theo %
		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		obj.setPremiumnet(phiBaoHiem);
		double discount = obj.getPremiumdiscount() != 0 ? obj.getPremiumnet() * obj.getPremiumdiscount() / 100 : 0;
		obj.setPremiumtnc(obj.getPremiumnet() - discount);
		return obj;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */

	private void ValidDataCalculate(PremiumTncVM tnc, ProductTncVM objTNC) throws AgencyBusinessException{
		log.debug("request to ValidDataCalculate, PremiumTncVM {}, ProductTncVM {} :", tnc, objTNC);
		double prePackage = tnc.getPremiumPackage();
		if (tnc.getNumberperson() == 0)
			throw new AgencyBusinessException("numberperson", ErrorCode.INVALID, "Data InValid");
		else if (tnc.getNumberperson() > 20)
			throw new AgencyBusinessException("numberperson", ErrorCode.INVALID, "NumberPerson not over 20");

		Date startDate = objTNC.getInsurancestartdate();
		if (startDate.before(new Date()))
			throw new AgencyBusinessException("insurancestartdate", ErrorCode.INVALID,
					"Thời gian bắt đầu tham gia bảo hiểm không được nhỏ hơn ngày hiện tại");

		if (tnc.getPremiumPackage() == 0)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID, "InValid Data");
		else if (tnc.getPremiumPackage() > 100000000)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID, "Package not over 100.000.000");
		else if (prePackage != 20000000 && prePackage != 30000000 && prePackage != 40000000
				&& prePackage != 50000000 && prePackage != 60000000 && prePackage != 70000000
				&& prePackage != 80000000 && prePackage != 90000000 && prePackage != 100000000)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID, "Data InValid");

		if (tnc.getInsurancestartdate() == null || tnc.getInsurancestartdate() == null)
			throw new AgencyBusinessException("insurancestartdate", ErrorCode.NULL_OR_EMPTY);
		else if (tnc.getInsurancestartdate() == null)
			throw new AgencyBusinessException("insurancestartdate", ErrorCode.NULL_OR_EMPTY);
	}

	private void validateDataPolicy(ProductTncVM objTNC) throws AgencyBusinessException {
		log.debug("request to validateDataPolicy, ProductTncVM {} :", objTNC);
		Date dateNow = new Date();
		if (!DateUtils.isValidDate(DateUtils.date2Str(objTNC.getInsurancestartdate()), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("insurancestartdate", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (objTNC.getInsurancestartdate().before(dateNow)) {
			throw new AgencyBusinessException("insurancestartdate", ErrorCode.INVALID, "Thời hạn BH phải > ngày hiện tại");
		}
		
		//	Validate người được bảo hiểm
		if (objTNC.getListTncAdd().size() != objTNC.getNumberperson())
			throw new AgencyBusinessException("listTncAdd", ErrorCode.INVALID,
					"Danh sách người được bảo hiểm chưa đúng");

		for (TncAddDTO item : objTNC.getListTncAdd()) {
			if (StringUtils.isEmpty(item.getInsuredName()))
				throw new AgencyBusinessException("insuredName", ErrorCode.NULL_OR_EMPTY,
						"Tên người được bảo hiểm không được để trống");
			if (StringUtils.isEmpty(item.getIdPasswport()))
				throw new AgencyBusinessException("idPasswport", ErrorCode.NULL_OR_EMPTY,
						"Hộ chiếu người được bảo hiểm không được để trống");
		}
		
		// Validate thông tin người nhận
		// call abstract to validate InvoiceInfo
		validateInvoiceInfo(objTNC.getInvoiceInfo());

		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(objTNC.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}
	}

	private Double GetRateByMonths(Integer months) {
		log.debug("request to GetRateByMonths, {} :", months);
		PaRate rate = paRateRepository.findOneByFrommonthLessThanEqualAndTomonthGreaterThanEqual(months, months);
		return rate.getRate();
	}

	private PremiumTncVM Calculate(PremiumTncVM obj, ProductTncVM objTNC) throws AgencyBusinessException{
		log.debug("request to Calculate, PremiumTncVM{}, ProductTncVM{} :", obj, objTNC);
		ValidDataCalculate(obj, objTNC);

		Double soTienBaoHiem = 0.0;
		Double phiBaoHiem = 0.0;
		Double giamPhiBaoHiem = 0.0;
		Integer soNguoiThamGia = 0;

		// Xu ly nghiep vu
		try {
			soTienBaoHiem = Double.parseDouble(obj.getPremiumPackage().toString());// *CURRENCY_SCALE;
			soNguoiThamGia = obj.getNumberperson();

			// Tinh Phi Bảo Hiểm
			phiBaoHiem = soTienBaoHiem * DEFAULT_RATE * GetRateByMonths(12);

			soNguoiThamGia = obj.getNumberperson();
			phiBaoHiem = phiBaoHiem * soNguoiThamGia;

			if (soNguoiThamGia >= 100 && soNguoiThamGia <= 300)
				giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_100_LESS_THAN_300_RATE;
			else if (soNguoiThamGia > 300)
				giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_300_RATE;
			else
				giamPhiBaoHiem = 0.0;

			phiBaoHiem -= giamPhiBaoHiem;

			// tinh phi giam theo %
			// KIEM TRA DISCOUNT VA TINH TONG PHI BH
			obj.setPremiumnet(phiBaoHiem);
			double discount = obj.getPremiumdiscount() != 0 ? obj.getPremiumnet() * obj.getPremiumdiscount() / 100 : 0;
			obj.setPremiumtnc(obj.getPremiumnet() - discount);
		} catch (Exception ex) {
			// throw new System.ArgumentException(ex.Message.ToString(), "CalcPermium_TNC");
		}
		return obj;
	}

	private PaDTO getObjectProduct(AgencyDTO currentAgency, ProductTncVM tncBase,
			PremiumTncVM premiumTNC, Contact co) throws AgencyBusinessException {
		log.debug("REST request to getObjectProduct : AgencyDTO{}, ProductTncVM{}, PremiumTncVM{}, Contact{} : ", currentAgency, tncBase, premiumTNC, co);
		PaDTO pa = new PaDTO();
		
		if (StringUtils.isNotEmpty(tncBase.getGycbhId())) {
			pa.setPaId(tncBase.getGycbhId());
		} else {
			pa.setPaId("");
		}
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(pa.getPaId())) {
			pa.setSoGycbh(tncBase.getGycbhNumber());
			pa.setPolicyNumber(tncBase.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(tncBase.getAgreementId());
			
			if (data != null) {
				pa.setSoGycbh(data.getGycbhNumber());
				pa.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		pa.setAgentId(currentAgency.getMa());
		pa.setAgentName(currentAgency.getTen());

		pa.setBeneficiaryAddress(co.getHomeAddress());
		pa.setBeneficiaryIdNumber(co.getIdNumber());
		pa.setBeneficiaryName(co.getContactName());
		pa.setContactEmail(co.getEmail());
		pa.setContactGoitinhId(co.getContactSex());
		pa.setContactGoitinhName(co.getContactSexName());
		pa.setContactMobilePhone(co.getHandPhone());
		pa.setContactName(co.getContactName());
		pa.setContactPhone(co.getHandPhone());
		pa.setDateOfBirth(co.getDateOfBirth());
		pa.setContactCode(co.getContactCode());
		pa.setContactId(co.getContactId());
		
		pa.setInceptionDate(tncBase.getInsurancestartdate());
		pa.setExpiredDate(tncBase.getInsuranceexpireddate());
		pa.setOldGycbhNumber(tncBase.getOldGycbhNumber());
		pa.setOldPolicyNumber(tncBase.getOldGycbhNumber());
		pa.setPermanentPartDisablement(tncBase.getNumbermonth());
		pa.setPermanentTotalDisablement(tncBase.getNumberperson());
		pa.setStatusId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		pa.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		pa.setDeath((double)tncBase.getPremiumPackage());
		pa.setPlan(tncBase.getPremiumPackageplanid().toString());
		
		pa.setChangePremiumPremium(premiumTNC.getPremiumdiscount());
		pa.setTotalBasicPremium(premiumTNC.getPremiumnet());
		pa.setTotalNetPremium(premiumTNC.getPremiumtnc());
		pa.setTotalPremium(premiumTNC.getPremiumtnc());

		pa.setReceiverName(tncBase.getReceiverUser().getName());
		pa.setReceiverAddress(tncBase.getReceiverUser().getAddress());
		pa.setReceiverMobile(tncBase.getReceiverUser().getMobile());
		
		if(tncBase.getInvoiceInfo() != null) {
			pa.setVoiceCheck(1);
			pa.setInvoceNumber(tncBase.getInvoiceInfo().getTaxNo());
			pa.setInvoiceAddress(tncBase.getInvoiceInfo().getAddress());
			pa.setInvoiceCompany(tncBase.getInvoiceInfo().getCompany());
		} else {
			pa.setVoiceCheck(0);
		}
		
		return pa;
	}

	private Double getPremium(Double soTienBaoHiem, ProductTncVM tncBase) {
		log.debug("Result of getPremium, soTienBaoHiem{}, ProductTncVM{} :", soTienBaoHiem, tncBase);
		Integer soThangBaoHiem = 12; // Default Value
		Double phiBaoHiem = 0.0;
		Double giamPhiBaoHiem = 0.0;
		Integer soNguoiThamGia = 0;

		if (!StringUtils.isEmpty(tncBase.getNumberperson().toString()))
			soNguoiThamGia = tncBase.getNumberperson();

		// Tinh Phi Bảo Hiểm
		if (soThangBaoHiem != 0 && soTienBaoHiem != 0) {
			phiBaoHiem = soTienBaoHiem * DEFAULT_RATE * GetRateByMonths(soThangBaoHiem);
			if (soNguoiThamGia != 0) {
				if (soNguoiThamGia >= 100 && soNguoiThamGia <= 300)
					giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_100_LESS_THAN_300_RATE;
				else if (soNguoiThamGia > 300)
					giamPhiBaoHiem = phiBaoHiem * LARGER_THAN_300_RATE;
				else
					giamPhiBaoHiem = 0.0;
			}
		}
		return (phiBaoHiem - giamPhiBaoHiem);
	}

	private List<PaAddDTO> getPaADDFromForm(ProductTncVM tncBase, PremiumTncVM premiumTNC, String tncId) {
		log.debug("request to getPaADDFromForm, ProductTncVM{}, PremiumTncVM{}, tncId :", tncBase, premiumTNC, tncId);
		Double SoTienBaoHiem = premiumTNC.getPremiumnet();
		List<PaAddDTO> paAddCollection = new ArrayList<PaAddDTO>();

		for (TncAddDTO item : tncBase.getListTncAdd()) {
			PaAddDTO tncAdd = new PaAddDTO();
			tncAdd.setInsuredName(item.getInsuredName());
			tncAdd.setIdPasswport(item.getIdPasswport());
			tncAdd.setDob(DateUtils.str2Date(DateUtils.date2Str(item.getDob())));
			tncAdd.setCellPhone("");
			tncAdd.setCity("");
			tncAdd.setEmailAdress("");
			tncAdd.setHomePhone("");
			tncAdd.setRelationship("");
			tncAdd.setSi(SoTienBaoHiem);
			tncAdd.setTitle(item.getTitle());
			tncAdd.setPaId(tncId);
			tncAdd.setPaAddId("");
			tncAdd.setPremium(getPremium(SoTienBaoHiem, tncBase));
			paAddCollection.add(tncAdd);
		}
		return paAddCollection;
	}

	private AgreementDTO getObjectAgreement(ProductTncVM objTNC, PremiumTncVM premiumTNC,String userAgent,Contact co, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductTncVM{}, PremiumTncVM{}, userAgent{}, Contact{}, currentAgency{}", objTNC, premiumTNC, userAgent, co, currentAgency);
		AgreementDTO voAg = new AgreementDTO();
		// Insert common data
		insertAgreementCommonInfo("TNC", voAg, co, currentAgency, objTNC);
		
		voAg.setInceptionDate(objTNC.getInsurancestartdate());
		voAg.setExpiredDate(objTNC.getInsuranceexpireddate());
		voAg.setTotalVat(0.0);
		voAg.setStandardPremium(premiumTNC.getPremiumnet() * 1.0);
		voAg.setChangePremium(premiumTNC.getPremiumdiscount() * 1.0);
		voAg.setTotalPremium(premiumTNC.getPremiumtnc() * 1.0);
		voAg.setNetPremium(premiumTNC.getPremiumtnc() * 1.0);
		voAg.setUserAgent(userAgent);
		if (co.getContactName() != null) {
			voAg.setStatusRenewalsName(co.getContactName().toUpperCase());	
		}
		voAg.setTeamId(String.valueOf(objTNC.getNumberperson()));
		
		return voAg;
	}

	private void validDataCalculate(PremiumTncVM tnc) throws AgencyBusinessException {
		log.debug("REST request to validDataCalculate : PremiumTncVM{}", tnc);
		double premiumPackage = tnc.getPremiumPackage();
		if (tnc.getNumberperson() == 0)
			throw new AgencyBusinessException("numberperson", ErrorCode.INVALID);
		else if (tnc.getNumberperson() > 20)
			throw new AgencyBusinessException("numberperson", ErrorCode.OUT_OF_RANGER, "NumberPerson not over 20");

		if (tnc.getNumbermonth() == 0)
			throw new AgencyBusinessException("numbermonth", ErrorCode.INVALID);
		else if (tnc.getNumbermonth() > 12)
			throw new AgencyBusinessException("numbermonth", ErrorCode.OUT_OF_RANGER, "NumberMonth not over 12");

		if (tnc.getPremiumPackage() == 0)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID);
		else if (tnc.getPremiumPackage() > 100000000)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.OUT_OF_RANGER, "Package not over 100.000.000");
		else if (premiumPackage != 20000000 && premiumPackage != 30000000 && premiumPackage != 40000000
				&& premiumPackage != 50000000 && premiumPackage != 60000000 && premiumPackage != 70000000
				&& premiumPackage != 80000000 && premiumPackage != 90000000 && premiumPackage != 100000000)
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID);
	}

	private double getRateByMonths(int months) {
		log.debug("REST request to getRateByMonths : {}", months);
		PaRate paRate = paRateRepository.findOneByFrommonthLessThanEqualAndTomonthGreaterThanEqual(months, months);
		return paRate.getRate();
	}

	private PremiumTncVM getPremiumCreate(ProductTncVM obj) throws AgencyBusinessException {
		log.debug("REST request to getPremiumCreate : ProductTncVM{}", obj);
		PremiumTncVM tnc = new PremiumTncVM();
		tnc.setInsurancestartdate(obj.getInsuranceexpireddate());
		tnc.setNumbermonth(obj.getNumbermonth());
		tnc.setNumberperson(obj.getNumberperson());
		tnc.setPremiumPackage((double)obj.getPremiumPackage());
		tnc.setPremiumdiscount(0d);
		tnc.setPremiumtnc(0d);
		tnc.setPremiumnet(0d);
		Calculate(tnc, obj);
		
		return tnc;
	}

}
