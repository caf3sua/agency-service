package com.baoviet.agency.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.KcareBenefit;
import com.baoviet.agency.domain.KcareRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.KcareBenefitRepository;
import com.baoviet.agency.repository.KcareRateRepository;
import com.baoviet.agency.repository.TinhtrangSkRepository;
import com.baoviet.agency.service.KcareService;
import com.baoviet.agency.service.ProductKcareService;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumKcareVM;
import com.baoviet.agency.web.rest.vm.ProductKcareVM;

/**
 * Service Implementation for managing Kcare.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@CacheConfig(cacheNames = "product")
@Transactional
public class ProductKcareServiceImpl extends AbstractProductService implements ProductKcareService {

	private final Logger log = LoggerFactory.getLogger(ProductKcareServiceImpl.class);

	@Autowired
	private ProductKcareService productKcareService;

	@Autowired
	private KcareBenefitRepository kcareBenefitRepository;

	@Autowired
	private KcareRateRepository kcareRateRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private KcareService kcareService;

	@Autowired
	private TinhtrangSkService tinhtrangSkService;
	
	@Autowired
	private TinhtrangSkRepository tinhtrangSkRepository;

	@Override
	@Cacheable
	public List<KcareBenefit> getAllBenefit() {
		log.debug("Service request to getAllBenefit");

		// Call service
		List<KcareBenefit> data = kcareBenefitRepository.findAll();
		return data;
	}

	@Override
	public KcareRate getAllKcareRateByParams(int age, String sex, String program) throws AgencyBusinessException {
		log.debug("Request to getAllByParams : age {},  sex {}, program {} ", age, sex, program);

		List<KcareRate> data = kcareRateRepository.findAllByAgeAndSexAndProgram(age, sex, program);

		if (data != null && data.size() > 0) {
			return data.get(0);
		}
		return null;
	}

	public PremiumKcareVM calculatePremium(PremiumKcareVM param) throws AgencyBusinessException {
		log.debug("Request to calculatePremium : param {}", param);

		// Check param input
		int age = DateUtils.countYears(DateUtils.str2Date(param.getNgaySinh()),
				DateUtils.str2Date(param.getNgayBatDau()));
		String sex = StringUtils.equals(param.getGioiTinh(), "1") ? "Nam" : "Nu";

		if (age < 16 || age > 55) {
			throw new AgencyBusinessException("ngaySinh", ErrorCode.INVALID, "Tuổi người được BH nhỏ hơn 16");
		}

		// Call service
		KcareRate data = productKcareService.getAllKcareRateByParams(age, sex, param.getTypeOfKcare());
		param.setTypeOfKcare(data.getProgram());
		param.setPremiumNet(data.getPremium());

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		double discount = param.getPremiumDiscount() != 0 ? param.getPremiumNet() * param.getPremiumDiscount() / 100
				: 0;

		param.setPremiumKCare(param.getPremiumNet() - discount);
		return param;
	}

	@Override
	public ProductKcareVM createOrUpdatePolicy(AgencyDTO currentAgency, ProductKcareVM kcare) throws AgencyBusinessException {
		log.debug("REST request to createOrUpdatePolicy : {}", kcare);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(kcare.getAgreementId())) {
			validateGycbhNumber(kcare.getGycbhNumber(), currentAgency.getMa());	
		}
		
		// Validate data
		validateDataPolicy(kcare);

		Contact co = contactRepository.findOneByContactCodeAndType(kcare.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		// gan du lieu vao obj de insert vao db region KCR
		KcareDTO kCareDTO = getObjectProduct(kcare, co, currentAgency);

		AgreementDTO voAg = getObjectAgreement(kcare, co, kCareDTO, currentAgency);
		
		// thuc hien luu du lieu
		KcareDTO kcareDTOResult = kcareService.save(kCareDTO);
		if (StringUtils.isNotBlank(kcareDTOResult.getKId())) {
			// luu agreement
			voAg.setGycbhId(kcareDTOResult.getKId());
			AgreementDTO voAgSave = agreementService.save(voAg);
			
			if (StringUtils.isNotEmpty(kcare.getGycbhId())) {
				if (kcare.getLstTinhtrangSKs() != null && kcare.getLstTinhtrangSKs().size() > 0) {
					// xóa tình trạng sức khỏe khi update
					tinhtrangSkRepository.deleteByIdThamchieu(kcare.getGycbhId());
				}
			}
			
			// luu tinh trang suc khoe
			if (kcare.getQ4().equals("1")) {
				for (TinhtrangSkDTO sk : kcare.getLstTinhtrangSKs()) {
					sk.setMasanpham("KCR");
					sk.setIdThamchieu(kcareDTOResult.getKId());
					sk.setQuestionThamchieu("4");
					sk.setSotienbh(0d);
					sk.setSotienycbt(0d);
					tinhtrangSkService.save(sk);
				}
			}
			
			// check TH thêm mới: 0, update: 1 để gửi sms
//	        if (StringUtils.isEmpty(kcare.getAgreementId())) {
	        	// pay_action
	         	sendSmsAndSavePayActionInfo(co, voAgSave, "0");	
//	        } else {
//	        	sendSmsAndSavePayActionInfo(co, voAgSave, "1");
//	        }
	        kcare.setAgreementId(voAgSave.getAgreementId());
		}
		return kcare;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private KcareDTO getObjectProduct(ProductKcareVM kcare, Contact co, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductKcareVM{}, Contact{}, AgencyDTO{}", kcare, co, currentAgency);
		KcareDTO kCareDTO = new KcareDTO();
		if (StringUtils.isNotEmpty(kcare.getGycbhId())) {
			kCareDTO.setKId(kcare.getGycbhId());
		}
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(kCareDTO.getKId())) {
			kCareDTO.setSoGycbh(kcare.getGycbhNumber());
			kCareDTO.setPolicyNumber(kcare.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(kcare.getAgreementId());
			
			if (data != null) {
				kCareDTO.setSoGycbh(data.getGycbhNumber());
				kCareDTO.setPolicyNumber(data.getGycbhNumber());
			}
		}

		kCareDTO.setBeneficiaryIdNumber(kcare.getBeneficiaryIdNumber());
		kCareDTO.setBeneficiaryIdNumberD(kcare.getBeneficiaryIdNumberD());
		kCareDTO.setBeneficiaryName(kcare.getBeneficiaryName());
		kCareDTO.setBeneficiaryNameD(kcare.getBeneficiaryNameD());
		kCareDTO.setBeneficiaryRelationship(kcare.getBeneficiaryRelationship());
		kCareDTO.setBeneficiaryRelationshipD(kcare.getBeneficiaryRelationshipD());
		if (!StringUtils.isEmpty(kcare.getBeneficiaryNgaysinh())) {
			kCareDTO.setBeneficiaryDob(DateUtils.str2Date(kcare.getBeneficiaryNgaysinh(), "dd/MM/yyyy"));
		}
			
		if (!StringUtils.isEmpty(kcare.getBeneficiaryNgaysinhD())) {
			kCareDTO.setBeneficiaryDobD(DateUtils.str2Date(kcare.getBeneficiaryNgaysinhD(), "dd/MM/yyyy"));
		}
			
		kCareDTO.setContactEmail(co.getEmail());
		kCareDTO.setContactGoitinhId(co.getContactSex());
		kCareDTO.setContactGoitinhName(co.getContactSexName());
		kCareDTO.setContactId(co.getContactId());
		kCareDTO.setContactCode(co.getContactCode());
		kCareDTO.setContactMobilePhone(co.getHandPhone());
		kCareDTO.setContactName(co.getContactName());
		kCareDTO.setContactPhone(co.getPhone());
		kCareDTO.setDateOfBirth(co.getDateOfBirth());
		kCareDTO.setInceptionDate(DateUtils.str2Date(kcare.getThoihantu(), "dd/MM/yyyy"));
		Date inceptionDate = DateUtils.addYear(kCareDTO.getInceptionDate(), 5);
		inceptionDate = DateUtils.addDay(inceptionDate, -1);
		kCareDTO.setExpiredDate(inceptionDate);
		kcare.setThoihanden(DateUtils.date2Str(kCareDTO.getExpiredDate()));
		kCareDTO.setInsuredDob(kcare.getInsuredNgaysinh());
		kCareDTO.setInsuredIdNumber(kcare.getInsuredIdNumber());
		kCareDTO.setInsuredName(kcare.getInsuredName());
		kCareDTO.setInsuredSex(kcare.getInsuredSex());
		kCareDTO.setNote(kcare.getInsuredRelationship()); // quan hệ với người yêu cầu bảo hiểm
		kCareDTO.setNetPremium(kcare.getNetPremium());
		kCareDTO.setPlanId(kcare.getPlanId());
		if (kcare.getPlanId().equals("PGM1"))
			kCareDTO.setPlanName("Chương trình 1");

		if (kcare.getPlanId().equals("PGM2"))
			kCareDTO.setPlanName("Chương trình 2");

		if (kcare.getPlanId().equals("PGM3"))
			kCareDTO.setPlanName("Chương trình 3");

		kCareDTO.setPolicyDeliver(new Date());
		kCareDTO.setTotalPremium(kcare.getTotalPremium());
		kCareDTO.setChangePremium(kcare.getChangePremium());

		kCareDTO.setQResultCan(kcare.getQresultCan());
		kCareDTO.setQResultTre(kcare.getQresultTre());
		kCareDTO.setQTreatment(kcare.getQtreatment());
		kCareDTO.setQTypeCancer(kcare.getQtypeCancer());
		kCareDTO.setQ1(kcare.getQ1());
		kCareDTO.setQ2(kcare.getQ2());
		kCareDTO.setQ3(kcare.getQ3());
		kCareDTO.setQ4(kcare.getQ4());
		kCareDTO.setQ5(kcare.getQ5());
		kCareDTO.setReceiverAddress(kcare.getReceiverUser().getAddress());
		kCareDTO.setReceiverEmail(kcare.getReceiverUser().getEmail());
		kCareDTO.setReceiverMoible(kcare.getReceiverUser().getMobile());
		kCareDTO.setReceiverName(kcare.getReceiverUser().getName());

		if (kcare.getInvoiceInfo() != null) {
			if (StringUtils.equals(kcare.getInvoiceInfo().getCheck(), "1")) {
				kCareDTO.setInvoiceCheck(1);
				kCareDTO.setInvoiceBuyer(kcare.getInvoiceInfo().getName());
				kCareDTO.setInvoiceAccountNo(kcare.getInvoiceInfo().getAccountNo());
				kCareDTO.setInvoiceAddress(kcare.getInvoiceInfo().getAddress());
				kCareDTO.setInvoiceCompany(kcare.getInvoiceInfo().getCompany());
				kCareDTO.setInvoiceTaxNo(kcare.getInvoiceInfo().getTaxNo());
			}	
		}
		
		kCareDTO.setStatusId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN); 
		kCareDTO.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		
		kCareDTO.setAgentId(currentAgency.getMa());
		kCareDTO.setAgentName(currentAgency.getTen());
		
		return kCareDTO;
	}

	private AgreementDTO getObjectAgreement(ProductKcareVM kcare, Contact co, KcareDTO kCareDTO, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductKcareVM{}, Contact{}, KcareDTO{}, AgencyDTO{}", kcare, co, kCareDTO, currentAgency);
		AgreementDTO voAg = new AgreementDTO();
		
		// Insert common data
		insertAgreementCommonInfo("KCR", voAg, co, currentAgency, kcare);
		
		voAg.setInceptionDate(kCareDTO.getInceptionDate());
		voAg.setExpiredDate(kCareDTO.getExpiredDate());
		voAg.setStandardPremium(kcare.getNetPremium());
		voAg.setNetPremium(kcare.getNetPremium());
		voAg.setChangePremium(kcare.getChangePremium());
		voAg.setTotalPremium(kcare.getTotalPremium());
		voAg.setTotalVat(0d);
		
		if (co.getContactName() != null) {
			voAg.setStatusRenewalsName(co.getContactName().toUpperCase());	
		}
		return voAg;
	}
	
	private void validateDataPolicy(ProductKcareVM obj) throws AgencyBusinessException {
		log.debug("REST request to validateDataPolicy : ProductKcareVM{}", obj);
		if (!DateUtils.isValidDate(obj.getThoihantu(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.FORMAT_DATE_INVALID);
		}
		Date currentDate = new Date();
		if (DateUtils.str2Date(obj.getThoihantu()).before(currentDate)) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.INVALID, "Thời hạn BH phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}
		if (!obj.getInsuredSex().equals("1") && !obj.getInsuredSex().equals("2")) {
			throw new AgencyBusinessException("insuredSex", ErrorCode.INVALID);
		}
		if (!DateUtils.isValidDate(obj.getInsuredNgaysinh(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("insuredNgaysinh", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (!obj.getInsuredRelationship().equals("30") && !obj.getInsuredRelationship().equals("31")
				&& !obj.getInsuredRelationship().equals("32") && !obj.getInsuredRelationship().equals("33")
				&& !obj.getInsuredRelationship().equals("34")) {
			throw new AgencyBusinessException("insuredRelationship", ErrorCode.INVALID);
		}
		if (!obj.getPlanId().equals("PGM1") && !obj.getPlanId().equals("PGM2") && !obj.getPlanId().equals("PGM3")) {
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}
		if (!obj.getQ1().equals("0") && !obj.getQ1().equals("1")) {
			throw new AgencyBusinessException("q1", ErrorCode.INVALID);
		}
		if (!obj.getQ2().equals("0") && !obj.getQ2().equals("1")) {
			throw new AgencyBusinessException("q2", ErrorCode.INVALID);
		}
		if (!obj.getQ3().equals("0") && !obj.getQ3().equals("1")) {
			throw new AgencyBusinessException("q3", ErrorCode.INVALID);
		}
		if (!obj.getQ4().equals("0") && !obj.getQ4().equals("1")) {
			throw new AgencyBusinessException("q4", ErrorCode.INVALID);
		}
		if (!obj.getQ5().equals("0") && !obj.getQ5().equals("1")) {
			throw new AgencyBusinessException("q5", ErrorCode.INVALID);
		}
		if (!obj.getQtreatment().equals("0") && !obj.getQtreatment().equals("1")) {
			throw new AgencyBusinessException("qTreatment", ErrorCode.INVALID);
		}
		if (!obj.getQresultTre().equals("0") && !obj.getQresultTre().equals("1")) {
			throw new AgencyBusinessException("qResultTre", ErrorCode.INVALID);
		}
		if (!obj.getQtypeCancer().equals("0") && !obj.getQtypeCancer().equals("1")) {
			throw new AgencyBusinessException("qTypeCancer", ErrorCode.INVALID);
		}
		if (!obj.getQresultCan().equals("0") && !obj.getQresultCan().equals("1")) {
			throw new AgencyBusinessException("qResultCan", ErrorCode.INVALID);
		}
		
		// kiem tra logic nghiep vu
		// kiem tra tuoi >=16
		int tuoi = DateUtils.countYears(DateUtils.str2Date(obj.getInsuredNgaysinh()),
				DateUtils.str2Date(obj.getThoihantu()));
		if (tuoi < 16)
			throw new AgencyBusinessException("insuredNgaysinh", ErrorCode.INVALID,
					"Tuổi người được BH nhỏ hơn 16");
		// kiem tra phi bh
		PremiumKcareVM k_cal = new PremiumKcareVM();
		k_cal.setNgayBatDau(obj.getThoihantu());
		k_cal.setNgaySinh(obj.getInsuredNgaysinh());
		k_cal.setGioiTinh(obj.getInsuredSex());
		k_cal.setTypeOfKcare(obj.getPlanId());
		k_cal.setPremiumDiscount(obj.getChangePremium());
		k_cal = calculatePremium(k_cal);
		if (k_cal.getPremiumKCare() != obj.getTotalPremium())
			throw new AgencyBusinessException("totalPremium", ErrorCode.INVALID,
					k_cal.getPremiumKCare() + " <> " + obj.getTotalPremium().toString());

		// tra loi co o cau 3
		if (obj.getQ3().equals("1")) {
			if (obj.getQtreatment().equals("0") && obj.getQresultTre().equals("0")
					&& obj.getQtypeCancer().equals("0") && obj.getQresultCan().equals("0"))
				throw new AgencyBusinessException("q3", ErrorCode.INVALID, "Nhập câu trả lời cho câu hỏi 3");
		}
		// tra loi co cau hoi 4
		if (obj.getQ4().equals("0")) {

		} else {
			// Trinhtrangsuckhoe
			if (obj.getLstTinhtrangSKs() == null || obj.getLstTinhtrangSKs().size() == 0) {
				throw new AgencyBusinessException("lstTinhtrangSKs", ErrorCode.NULL_OR_EMPTY,
						"Thiếu thông tin tình trạng sức khỏe ");
			} else {
				for (TinhtrangSkDTO sk : obj.getLstTinhtrangSKs()) {
					if (sk.getNgaydieutri().after(new Date()))
						throw new AgencyBusinessException("ngaydieutri", ErrorCode.INVALID,
								"Ngày khám/điều trị không được lớn hơn ngày hiện tại");
					if (StringUtils.isEmpty(sk.getChuandoan()))
						throw new AgencyBusinessException("chuandoan", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin chuẩn đoán");
					if (StringUtils.isEmpty(sk.getChitietdieutri()))
						throw new AgencyBusinessException("chitietdieutri", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin chi tiết điều trị");
					if (StringUtils.isEmpty(sk.getKetqua()))
						throw new AgencyBusinessException("ketqua", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin kết quả điều trị");
				}
			}
		}

		// Nguoi thu huong
		if (StringUtils.isEmpty(obj.getBeneficiaryName()) && StringUtils.isEmpty(obj.getBeneficiaryRelationship())
				&& StringUtils.isEmpty(obj.getBeneficiaryIdNumber())
				&& StringUtils.isEmpty(obj.getBeneficiaryNgaysinh())) {
		} else {
			if(obj.getBeneficiaryNgaysinh() != null && !StringUtils.equals(obj.getBeneficiaryNgaysinh(), "01/01/0001")) {
				if (StringUtils.isEmpty(obj.getBeneficiaryName())) {
					throw new AgencyBusinessException("beneficiaryName", ErrorCode.NULL_OR_EMPTY, "Thiếu tên người thụ hưởng");
				}

				if (StringUtils.isEmpty(obj.getBeneficiaryRelationship())) {
					throw new AgencyBusinessException("beneficiaryRelationship", ErrorCode.NULL_OR_EMPTY,
							"Thiếu quan hệ người thụ hưởng");
				} else {
					if (obj.getBeneficiaryRelationship().equals("31") || obj.getBeneficiaryRelationship().equals("32")
							|| obj.getBeneficiaryRelationship().equals("33")) {

					} else {
						throw new AgencyBusinessException("beneficiaryRelationship", ErrorCode.INVALID,
								"Lỗi tham số quan hệ người thụ hưởng");
					}
				}

				if (StringUtils.isEmpty(obj.getBeneficiaryIdNumber())) {
					throw new AgencyBusinessException("beneficiaryIdNumber", ErrorCode.NULL_OR_EMPTY,
							"Thiếu số CMND/HC/GKS người thụ hưởng");
				}

				if (obj.getBeneficiaryNgaysinh() != null) {
					if (!DateUtils.isValidDate(obj.getBeneficiaryNgaysinh(), "dd/MM/yyyy") || DateUtils.str2Date(obj.getBeneficiaryNgaysinh(), "dd/MM/yyyy").after(new Date())) {
						throw new AgencyBusinessException("beneficiaryNgaysinh", ErrorCode.FORMAT_DATE_INVALID,
								"Lỗi tham số Ngày sinh người thụ hưởng");
					}	
				}
			}
		}

		if (StringUtils.isEmpty(obj.getBeneficiaryNameD()) && StringUtils.isEmpty(obj.getBeneficiaryRelationshipD())
				&& StringUtils.isEmpty(obj.getBeneficiaryIdNumberD())
				&& StringUtils.isEmpty(obj.getBeneficiaryNgaysinhD())) {
		} else {
			if (obj.getBeneficiaryNgaysinhD() != null && !StringUtils.equals(obj.getBeneficiaryNgaysinhD(), "01/01/0001")) {
				if (StringUtils.isEmpty(obj.getBeneficiaryNameD())) {
					throw new AgencyBusinessException("beneficiaryNameD", ErrorCode.NULL_OR_EMPTY,
							"Thiếu tên người nhận tiền");
				}

				if (StringUtils.isEmpty(obj.getBeneficiaryRelationshipD())) {
					throw new AgencyBusinessException("beneficiaryRelationshipD", ErrorCode.NULL_OR_EMPTY,
							"Thiếu quan hệ người nhận tiền");
				} else {
					if (obj.getBeneficiaryRelationshipD().equals("31") || obj.getBeneficiaryRelationshipD().equals("32")
							|| obj.getBeneficiaryRelationshipD().equals("33")) {

					} else {
						throw new AgencyBusinessException("beneficiaryRelationshipD", ErrorCode.INVALID,
								"Lỗi tham số quan hệ người nhận tiền");
					}
				}

				if (StringUtils.isEmpty(obj.getBeneficiaryIdNumberD())) {
					throw new AgencyBusinessException("beneficiaryIdNumberD", ErrorCode.NULL_OR_EMPTY,
							"Thiếu số CMND/HC/GKS người nhận tiền");
				}

				if (!DateUtils.isValidDate(obj.getBeneficiaryNgaysinhD(), "dd/MM/yyyy")
						|| DateUtils.str2Date(obj.getBeneficiaryNgaysinhD(), "dd/MM/yyyy").after(new Date())) {
					throw new AgencyBusinessException("beneficiaryNgaysinhD", ErrorCode.FORMAT_DATE_INVALID,
							"Lỗi tham số Ngày sinh người nhận tiền");
				} else {
					// thuc hien kiem tra tuoi xem > 18
					int age = DateUtils.countYears(DateUtils.str2Date(obj.getBeneficiaryNgaysinhD()),
							DateUtils.str2Date(obj.getThoihantu()));
					if (age < 18) {
						throw new AgencyBusinessException("beneficiaryNgaysinhD", ErrorCode.INVALID,
								"Người nhận tiền chưa đủ 18 tuổi");
					}
				}
			}
		}

		// thong tin hoa don
		validateInvoiceInfo(obj.getInvoiceInfo());
		
		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}
	}
}
