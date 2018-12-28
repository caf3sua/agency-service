package com.baoviet.agency.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.dto.MotoDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.MotoService;
import com.baoviet.agency.service.ProductMotoService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumMotoVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;

/**
 * Service Implementation for managing Kcare.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductMotoServiceImpl extends AbstractProductService implements ProductMotoService {

	private final Logger log = LoggerFactory.getLogger(ProductMotoServiceImpl.class);

	// TCT Ban Kinh doanh bảo hiểm Phi hàng hải
    private final static String DEPARTMENT_ID_MOMO = "A000009218";
    private final static String AGENT_ID_MOMO = "T000080696";
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private CodeManagementService codeManagementService;

	@Autowired
	private MotoService motoService;
	
	@Autowired
    private ContactService contactService;
	
	@Override
	public ProductMotoVM createOrUpdatePolicy(ProductMotoVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to createOrUpdateMotoPolicy : {}", obj);
		
		// TH dùng cho MOMO
		if (currentAgency.getMa().equals(AGENT_ID_MOMO)) {
			validateAndSetValueContactMOMO(obj, currentAgency);
			
		}
		
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}
		
		// Check validate data
		validateDataPolicy(obj);

		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Không tồn tại dữ liệu");
		}

		// kiem tra logic nghiep vu
		// kiem tra phi BH
		validatePremium(obj);

		// gan du lieu vao obj de insert vao db
		MotoDTO pMOTO = getObjectProduct(obj, co, currentAgency);

		AgreementDTO pAGREEMENT = getObjectAgreement(obj, co, pMOTO, currentAgency);
		
		// thuc hien luu du lieu
		int motoId = motoService.insertMoto(pMOTO);
		if (motoId > 0) {
			pAGREEMENT.setGycbhId(String.valueOf(motoId));
			// luu agreement
			AgreementDTO agreementDTOSave = agreementService.save(pAGREEMENT);
			log.debug("Result of save agreement, {}", agreementDTOSave);
			obj.setAgreementId(agreementDTOSave.getAgreementId());
			// pay_action
			sendSmsAndSavePayActionInfo(co, agreementDTOSave);
		}

		return obj;
	}

	@Override
	public PremiumMotoVM calculatePremium(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculatePremium : {}", obj);
		// Trach nhiem dan su bat buoc
		calculateValidTNDSBB(obj);

		// trách nhiệm dân sự tự nguyện
		calculateValidTNDSTN(obj);

		// người ngồi trên xe
		calculateValidNNTX(obj);

		// cháy nổ
		calculateValidCN(obj);

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		double tongPhi = obj.getTndsbbPhi() + obj.getTndstnPhi() + obj.getNntxPhi() + obj.getChaynoPhi();
		obj.setTongPhi(tongPhi);
		// var discount = obj.PremiumDiscount != 0
		// ? obj.PremiumNet * obj.PremiumDiscount / 100
		// : 0;
		double discount = 0;
		// tổng phí bảo hiểm bang phi NET - DISCOUNT
		obj.setTongPhi(obj.getTongPhi() - discount);

		return obj;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private PremiumMotoVM calculateValidTNDSBB(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculateValidTNDSBB : {}", obj);
		// trách nhiệm dân sự bắt buộc
		if (StringUtils.equals(obj.getTypeOfMoto(), "1")) {
			double tndsbbPhi = 55000 + 55000 / 10;
			obj.setTndsbbPhi(tndsbbPhi);
		} else if (StringUtils.equals(obj.getTypeOfMoto(), "2")) {
			double tndsbbPhi = 60000 + 60000 / 10;
			obj.setTndsbbPhi(tndsbbPhi);
		} else {
			throw new AgencyBusinessException("typeOfMoto", ErrorCode.INVALID, "Loại xe không phù hợp");
		}
		return obj;
	}

	private PremiumMotoVM calculateValidTNDSTN(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculateValidTNDSTN : {}", obj);
		if (obj.getTndstnCheck()) {
			if (StringUtils.equals(obj.getTypeOfMoto(), "1")) {
				if (obj.getTndstnSotien() == 50000000) {
					obj.setTndstnPhi((double) 75000 + 75000 / 10);
				} else if (obj.getTndstnSotien() == 100000000) {
					obj.setTndstnPhi((double) 150000 + 150000 / 10);
				} else {
					throw new AgencyBusinessException("tndstnSotien", ErrorCode.INVALID, "Cần lựa chọn mức trách nhiệm");
				}
			} else {
				if (obj.getTndstnSotien() == 50000000) {
					obj.setTndstnPhi((double) 105000 + 105000 / 10);
				} else if (obj.getTndstnSotien() == 100000000) {
					obj.setTndstnPhi((double) 210000 + 210000 / 10);
				} else {
					throw new AgencyBusinessException("tndstnSotien", ErrorCode.INVALID, "Cần lựa chọn mức trách nhiệm");
				}
			}
		}
		return obj;
	}

	private PremiumMotoVM calculateValidNNTX(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculateValidNNTX : {}", obj);
		if (obj.getNntxCheck()) {
			if (obj.getNntxSoNguoi() != 1 && obj.getNntxSoNguoi() != 2)
				throw new AgencyBusinessException("nntxSoNguoi", ErrorCode.INVALID, "Vượt quá số lượng người cho phép!");

			if (obj.getNntxStbh() >= 3000000 && obj.getNntxStbh() <= 30000000) {
				double phi = obj.getNntxStbh() * obj.getNntxSoNguoi() * 10 / 10000;
				obj.setNntxPhi(phi);
			} else if (obj.getNntxStbh() > 30000000 && obj.getNntxStbh() <= 50000000) {
				double phi = obj.getNntxStbh() * obj.getNntxSoNguoi() * 15 / 10000;
				obj.setNntxPhi(phi);
			} else if (obj.getNntxStbh() > 50000000 && obj.getNntxStbh() <= 500000000) {
				double phi = obj.getNntxStbh() * obj.getNntxSoNguoi() * 20 / 10000;
				obj.setNntxPhi(phi);

			} else {
				throw new AgencyBusinessException("nntxStbh", ErrorCode.INVALID, "Số tiền bảo hiểm người ngồi trên xe tối thiểu từ 3 triệu đồng đến tối đa 500 triệu đồng");
			}
		}
		return obj;
	}

	private PremiumMotoVM calculateValidCN(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculateValidCN : {}", obj);
		if (obj.getChaynoCheck()) {
			if (obj.getChaynoStbh() >= 10000000 && obj.getChaynoStbh() <= 50000000) {
				obj.setChaynoPhi((double) obj.getChaynoStbh() * 30 / 10000);

			} else if (obj.getChaynoStbh() > 50000000 && obj.getChaynoStbh() <= 100000000) {
				obj.setChaynoPhi((double) obj.getChaynoStbh() * 45 / 10000);
			} else {
				throw new AgencyBusinessException("chaynoStbh", ErrorCode.INVALID, "Số tiền bảo hiểm cháy nổ trong khoảng từ 10 triệu đồng đến tối đa 100 triệu đồng");
			}
			obj.setChaynoPhi((double) obj.getChaynoPhi() * 110 / 100);
		}
		return obj;
	}

	private void validatePremium(ProductMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to validatePremium : {}", obj);
		PremiumMotoVM pMT = new PremiumMotoVM();
		pMT.setTypeOfMoto(obj.getTypeOfMoto());
		pMT.setTndsbbCheck(obj.getTndsbbCheck());
		pMT.setTndstnCheck(obj.getTndstnCheck());
		if (!obj.getTndstnCheck()) {
			pMT.setTndstnSotien(0d);
		} else {
			pMT.setTndstnSotien(obj.getTndstnSotien());	
		}
		
		pMT.setNntxCheck(obj.getNntxCheck());
		if (!obj.getNntxCheck()) {
			pMT.setNntxSoNguoi(0);
			pMT.setNntxStbh(0d);
		} else {
			pMT.setNntxSoNguoi(obj.getNntxSoNguoi());
			pMT.setNntxStbh(obj.getNntxStbh());	
		}
		
		pMT.setChaynoCheck(obj.getChaynoCheck());
		if (!obj.getChaynoCheck()) {
			pMT.setChaynoStbh(0d);	
		} else {
			pMT.setChaynoStbh(obj.getChaynoStbh());
		}
		
		// pMT.setPremiumDiscount = 0;
		pMT = calculate(pMT);

		if (pMT.getTongPhi() != obj.getTongPhi()) {
			throw new AgencyBusinessException("tongPhi", ErrorCode.INVALID, "Sai phí bảo hiểm");
		}
	}
	
	private void validateDataPolicy(ProductMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to validateDataPolicy : {}", obj);
		Date dateNow = new Date();
		if (!DateUtils.isValidDate(obj.getThoihantu(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (DateUtils.str2Date(obj.getThoihantu()).before(dateNow)) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.INVALID, "Thời hạn BH phải > ngày hiện tại");
		}
		if (!(obj.getTypeOfMoto().equals("1") || obj.getTypeOfMoto().equals("2"))) {
			throw new AgencyBusinessException("typeOfMotoId", ErrorCode.INVALID);
		}

		if (obj.getTndsbbCheck()) {
			if (obj.getTndsbbPhi() <= 0) {
				throw new AgencyBusinessException("tndsBbPhi", ErrorCode.INVALID, "Cần nhập phí TNDSBB");
			}
		}

		if (obj.getTndstnCheck()) {
			if (obj.getTndstnSotien() <= 0) {
				throw new AgencyBusinessException("tndsTnSotien", ErrorCode.INVALID, "Cần nhập mức trách nhiệm TNDSTN");
			}
			if (obj.getTndstnPhi() <= 0) {
				throw new AgencyBusinessException("tndsTnPhi", ErrorCode.INVALID, "Cần nhập phí BH TNDSTN");
			}
		}

		if (obj.getNntxCheck()) {
			if (obj.getNntxSoNguoi() <= 0) {
				throw new AgencyBusinessException("nntxSoNguoi", ErrorCode.INVALID, "Cần nhập số người tham gia BH");
			} else {
				if (obj.getNntxSoNguoi() > 2) {
					throw new AgencyBusinessException("nntxSoNguoi", ErrorCode.INVALID, "Số người tham gia lớn hơn 2");
				}
			}
			if (obj.getNntxStbh() <= 0) {
				throw new AgencyBusinessException("nntxStbh", ErrorCode.INVALID, "Cần nhập mức trách nhiệm NNTX");
			}
		}

		if (obj.getChaynoCheck()) {
			if (obj.getChaynoStbh() <= 0) {
				throw new AgencyBusinessException("chaynoStbh", ErrorCode.INVALID, "Cần nhập số tiền BH Cháy nổ");
			} else {
				if (obj.getChaynoStbh() < 10000000 || obj.getChaynoStbh() > 100000000) {
					throw new AgencyBusinessException("chaynoStbh", ErrorCode.INVALID,
							"Số tiền BH cháy nổ phải từ 10tr - 100tr");
				}
			}
		}

		if (obj.getTongPhi() <= 0) {
			throw new AgencyBusinessException("tongPhi", ErrorCode.INVALID, "Cần nhập phí BH");
		}

		if (StringUtils.isEmpty(obj.getRegistrationNumber())) {
			if (StringUtils.isEmpty(obj.getSokhung()) && StringUtils.isEmpty(obj.getSomay())) {
				throw new AgencyBusinessException("registrationNumber", ErrorCode.NULL_OR_EMPTY);
			}
		}

		if (obj.getChaynoCheck()) {
			if (StringUtils.isEmpty(obj.getSokhung())) {
				throw new AgencyBusinessException("sokhung", ErrorCode.NULL_OR_EMPTY);
			}
			if (StringUtils.isEmpty(obj.getSomay())) {
				throw new AgencyBusinessException("somay", ErrorCode.NULL_OR_EMPTY);
			}
			if (StringUtils.isEmpty(obj.getHieuxe())) {
				throw new AgencyBusinessException("hieuxe", ErrorCode.NULL_OR_EMPTY);
			}
		}

		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}

	}
	
	private void validateAndSetValueContactMOMO(ProductMotoVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to validateAndSetValueContactMOMO : {}", obj);

		if (StringUtils.isEmpty(obj.getContactPhone())) {
			throw new AgencyBusinessException("contactPhone", ErrorCode.NULL_OR_EMPTY, "Số điện thoại khách hàng không được để trống");
		} else {
			// kiem tra dinh dang so dien thoai
			if (!ValidateUtils.isPhone(obj.getContactPhone())) {
				throw new AgencyBusinessException("contactPhone", ErrorCode.INVALID, "Số điện thoại không đúng định dạng");
			}	
		}
		
		Contact contactTmp = contactRepository.findOneByPhoneAndType(obj.getContactPhone(), currentAgency.getMa());

		if (contactTmp != null) {
			obj.setContactCode(contactTmp.getContactCode());
		} else {
			if (StringUtils.isEmpty(obj.getContactName())) {
				throw new AgencyBusinessException("contactName", ErrorCode.NULL_OR_EMPTY, "Tên khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(obj.getContactIdNumber())) {
				throw new AgencyBusinessException("contactIdNumber", ErrorCode.NULL_OR_EMPTY, "CMT khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(obj.getContactEmail())) {
				throw new AgencyBusinessException("contactEmail", ErrorCode.NULL_OR_EMPTY, "Email khách hàng không được để trống");
			}
			if (StringUtils.isEmpty(obj.getContactDob())) {
				throw new AgencyBusinessException("contactDob", ErrorCode.NULL_OR_EMPTY, "Ngày sinh khách hàng không được để trống");
			} else {
				// Validate ngay sinh tu 18 - 85
				if (obj.getContactDob() != null) {
					int utageYCBH = DateUtils.countYears(DateUtils.str2Date(obj.getContactDob()), new Date());
					if (utageYCBH < 18 || utageYCBH > 85) {
						throw new AgencyBusinessException("dateOfBirth", ErrorCode.INVALID,
								"Khách hàng phải từ 18 đến 85 tuổi");
					}
				}
			}
			if (StringUtils.isEmpty(obj.getContactAddress())) {
				throw new AgencyBusinessException("contactAddress", ErrorCode.NULL_OR_EMPTY, "Địa chỉ khách hàng không được để trống");
			}
			
			ContactDTO data = contactService.create(getContactCreate(obj, currentAgency), null);
			if (data != null) {
				obj.setContactCode(data.getContactCode());
				if (!StringUtils.isEmpty(obj.getContactName())) {
					data.setContactName(obj.getContactName());
				}
				if (!StringUtils.isEmpty(obj.getContactIdNumber())) {
					data.setIdNumber(obj.getContactIdNumber());
				}
				if (!StringUtils.isEmpty(obj.getContactEmail())) {
					data.setEmail(obj.getContactEmail());
				}
				if (!StringUtils.isEmpty(obj.getContactDob())) {
					data.setDateOfBirth(DateUtils.str2Date(obj.getContactDob()));
				}
				if (!StringUtils.isEmpty(obj.getContactAddress())) {
					data.setHomeAddress(obj.getContactAddress());
				}
				// update contact
				contactService.save(data);
			}
		}
		CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("MOT");
		obj.setGycbhNumber(codeManagementDTO.getIssueNumber());
		obj.setDepartmentId(DEPARTMENT_ID_MOMO);
	}
	
	private ContactDTO getContactCreate(ProductMotoVM param, AgencyDTO currentAgency) throws AgencyBusinessException {
    	// Get current agency
		String contactCode = contactService.generateContactCode(currentAgency.getMa());
		
    	ContactDTO pa = new ContactDTO();
		pa.setContactName(param.getContactName());
		pa.setContactCode(contactCode);
		pa.setContactSex("1");// mặc định
		if (param.getContactDob() != null) {
			pa.setDateOfBirth(DateUtils.str2Date(param.getContactDob()));	
		} else {
			pa.setDateOfBirth(DateUtils.str2Date("01/01/0001"));
		}
		
		pa.setHomeAddress(param.getContactAddress());
		pa.setPhone(param.getContactPhone());
		pa.setEmail(param.getContactEmail());
		pa.setIdNumber(param.getContactIdNumber());
		pa.setType(currentAgency.getMa());
		// khi thêm mới mặc định là KH tiềm năng không cho truyền loại khách hàng vào
		pa.setGroupType(AgencyConstants.CONTACT_GROUP_TYPE.POTENTIAL);
		// Category : PERSON/ORGANIZATION. Tạm thời mặc định MOMO là cá nhân
		pa.setCategoryType(AgencyConstants.CONTACT_CATEGORY_TYPE.PERSON);
		
		return pa;
    }

	private PremiumMotoVM calculate(PremiumMotoVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculate : {}", obj);
		// Trach nhiem dan su bat buoc
		calculateValidTNDSBB(obj);

		// trách nhiệm dân sự tự nguyện
		calculateValidTNDSTN(obj);

		// người ngồi trên xe
		calculateValidNNTX(obj);

		// cháy nổ
		calculateValidCN(obj);

		// KIEM TRA DISCOUNT VA TINH TONG PHI BH
		double tongPhi = obj.getTndsbbPhi() + obj.getTndstnPhi() + obj.getNntxPhi() + obj.getChaynoPhi();
		
		obj.setTongPhi(tongPhi);

		return obj;
	}

	private AgreementDTO getObjectAgreement(ProductMotoVM obj, Contact co, MotoDTO pMOTO, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductMotoVM{}, Contact{}, MotoDTO{}, AgencyDTO{}", obj, co, pMOTO, currentAgency);
		AgreementDTO pAGREEMENT = new AgreementDTO();
		
		// Insert common data
		insertAgreementCommonInfo("MOT", pAGREEMENT, co, currentAgency, obj);
		
		pAGREEMENT.setUserAgent("");
		pAGREEMENT.setInceptionDate(pMOTO.getInceptionDate());
		pAGREEMENT.setExpiredDate(pMOTO.getExpiredDate());

		double tongPhi = obj.getTndsbbPhi() + obj.getTndstnPhi() + obj.getNntxPhi() + obj.getChaynoPhi();
		pAGREEMENT.setStandardPremium(tongPhi);
		pAGREEMENT.setChangePremium(0.0);
		pAGREEMENT.setNetPremium(tongPhi);
		pAGREEMENT.setTotalVat(0.0);
		pAGREEMENT.setTotalPremium(tongPhi);
		pAGREEMENT.setReceiveMethod(obj.getReceiveMethod());
		
        return pAGREEMENT;
    }
	
	private MotoDTO getObjectProduct(ProductMotoVM obj, Contact co, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductMotoVM{}, Contact{}, AgencyDTO{}", obj, co, currentAgency);
		MotoDTO pMOTO = new MotoDTO();

		// NamNH: 18/06/2018
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			pMOTO.setId(obj.getGycbhId());
		}
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(pMOTO.getId())) {
			pMOTO.setSoGycbh(obj.getGycbhNumber());
			pMOTO.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			
			if (data != null) {
				pMOTO.setSoGycbh(data.getGycbhNumber());
				pMOTO.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		// lay thong tin user dang nhap
		pMOTO.setContactId(co.getContactId()); // id cua khach hang
		pMOTO.setContactCode(co.getContactCode()); // ma khach hang
		pMOTO.setContactUsername(co.getContactUsername()); // username dang nhap
		pMOTO.setInsuredName(obj.getInsuredName()); // Tên chủ xe(theo đăng ký xe):
		pMOTO.setInsuredAddress(obj.getInsuredAddress()); // Địa chỉ(theo đăng ký xe):
		pMOTO.setRegistrationNumber(obj.getRegistrationNumber());// Biển kiểm soát:
		pMOTO.setSokhung(obj.getSokhung());// Số khung:
		pMOTO.setSomay(obj.getSomay());// Số máy:
		pMOTO.setTypeOfMotoId(obj.getTypeOfMoto()); // Chọn nhóm loại xe: id
		if (obj.getTypeOfMoto().equals("1")) {
			pMOTO.setTypeOfMotoName("Xe Mô tô 2 bánh dung tích từ 50cc trở xuống");// Chọn nhóm loại xe: name
		} else {
			pMOTO.setTypeOfMotoName("Xe Mô tô 2 bánh dung tích trên 50cc");
		}
		pMOTO.setInceptionDate(DateUtils.str2Date(obj.getThoihantu()));// Thời hạn bảo hiểm: tu
		pMOTO.setExpiredDate(DateUtils.getCurrentYearPrevious(DateUtils.str2Date(obj.getThoihantu()), -1)); // Thời hạn bảo hiểm: den
		obj.setThoihanden(DateUtils.date2Str(pMOTO.getExpiredDate()));
		pMOTO.setTndsBbPhi(obj.getTndsbbPhi()); // Phí bảo hiểm trách nhiệm dân sự bắt

		// TNDSTN
		pMOTO.setTndsTnNguoi(obj.getTndstnSotien());
		pMOTO.setTndsTnTs(obj.getTndstnSotien());
		pMOTO.setTndsTnPhi(obj.getTndstnPhi());
		// NNTX
		pMOTO.setTndsTnNntxPhi(obj.getNntxPhi());// Phí bảo hiểm tai nạn người trên
		pMOTO.setNntxStbh(obj.getNntxStbh());
		pMOTO.setNntxSoNguoi(obj.getNntxSoNguoi());
		// chay no
		pMOTO.setChaynoStbh(obj.getChaynoStbh());
		pMOTO.setChaynoPhi(obj.getChaynoPhi());
		pMOTO.setGhichu(obj.getHieuxe());// Nhãn hiệu xe:
		
		pMOTO.setTongPhi(obj.getTongPhi());// Tổng phí thanh toán (đã bao gồm VAT)
		
		pMOTO.setPolicyStatus(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		pMOTO.setPolicyStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		pMOTO.setStatus(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		pMOTO.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		pMOTO.setReceiverName(obj.getReceiverUser().getName()); // Họ và tên người nhận
		pMOTO.setReceiverAddress(obj.getReceiverUser().getAddress());// Địa chỉ người nhận:
		pMOTO.setReceiverEmail(obj.getReceiverUser().getEmail());// Email người nhận:
		pMOTO.setReceiverMoible(obj.getReceiverUser().getMobile());// Số điện thoại liên hệ:

		pMOTO.setCustomerName(obj.getReceiverUser().getName());// Họ và tên người yêu cầu bảo hiểm
		pMOTO.setCustomerAddress(obj.getReceiverUser().getAddress());// Địa chỉ
		pMOTO.setCustomerPhone(obj.getReceiverUser().getMobile());// Điện thoại liên hệ
		pMOTO.setCustomerEmail(obj.getReceiverUser().getEmail());// Địa chỉ Email
		
        return pMOTO;
    }
	
}
