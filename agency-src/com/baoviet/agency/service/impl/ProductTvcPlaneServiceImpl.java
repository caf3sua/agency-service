package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.PremiumTvcGotadi;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TvcPlaneAddDTO;
import com.baoviet.agency.dto.TvcPlaneDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.PremiumTvcGotadiRepository;
import com.baoviet.agency.repository.TvcPlaneAddRepository;
import com.baoviet.agency.service.ProductTvcPlaneService;
import com.baoviet.agency.service.TvcPlaneAddService;
import com.baoviet.agency.service.TvcPlaneService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumTvcPlaneVM;
import com.baoviet.agency.web.rest.vm.ProductTvcPlaneVM;
import com.baoviet.agency.web.rest.vm.TvcPlaneAddBaseVM;

/**
 * Service Implementation for managing TvcPlane.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductTvcPlaneServiceImpl extends AbstractProductService implements ProductTvcPlaneService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcPlaneServiceImpl.class);

	@Autowired
	private PremiumTvcGotadiRepository premiumTvcGotadiRepository;

	@Autowired
	private TvcPlaneService tvcPlaneService;
	
	@Autowired
	private TvcPlaneAddService tvcPlaneAddService;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private TvcPlaneAddRepository tvcPlaneAddRepository;
	
	@Override
	public PremiumTvcPlaneVM calculatePremium(PremiumTvcPlaneVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculatePremium : {}", obj);

		// valid data
		validCalculatePremium(obj);
		int pDay = 1;
		if (obj.getPlanId().equals("2")) {
			if (StringUtils.isEmpty(obj.getDateTo())) {
				throw new AgencyBusinessException("dateTo", ErrorCode.NULL_OR_EMPTY);
			} else {
				if (!DateUtils.isValidDate(obj.getDateTo(), "dd/MM/yyyy")) {
					throw new AgencyBusinessException("dateTo", ErrorCode.FORMAT_DATE_INVALID);
				}
			}
			// tinh so ngay
			int sn = DateUtils.getNumberDaysBetween2DateStr(obj.getDateFrom(), obj.getDateTo());
			pDay = sn + 1;
		}

		PremiumTvcGotadi data = premiumTvcGotadiRepository
				.findByAreaIdAndPlanIdAndTypeOfAgencyAndFromDateLessThanEqualAndToDateGreaterThanEqual(obj.getAreaId(),
						obj.getPlanId(), obj.getTypeOfAgency(), pDay, pDay);

		if (data != null) {
			obj.setPremium(data.getPremium());
		}

		return obj;
	}

	@Override
	public ProductTvcPlaneVM createOrUpdatePolicy(ProductTvcPlaneVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to createTvcPlanePolicy : {}", obj);
		
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());	
		}

		if (obj.getLstTvcPlane() == null) {
			throw new AgencyBusinessException("lstTvcPlane", ErrorCode.NULL_OR_EMPTY);
		}
		
		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		// valid data
		validcreateTvcPlanePolicy(obj);

		// tính phí bảo hiểm
		PremiumTvcGotadi data = getPremiumCreate(obj);

		// get du lieu
		TvcPlaneDTO vo = getObjectProduct(obj, co, currentAgency);
		// insert HD TVC
		int kq = tvcPlaneService.insert(vo);
		if (kq > 0) {
			// create agreement travel
			AgreementDTO voAg = getObjectAgreement(obj, co,currentAgency);
			voAg.setGycbhId(String.valueOf(kq));
			// insert Ag
			AgreementDTO voAgSave = agreementService.save(voAg);
			if (StringUtils.isNotEmpty(obj.getGycbhId())) {
				// xóa ds bảo hiểm khi update
				tvcPlaneAddRepository.deleteByTvcPlaneId(obj.getGycbhId());
			}
			
			// set du lieu nguoi duoc bao hiem
			for (TvcPlaneAddBaseVM a : obj.getLstTvcPlane()) {
				TvcPlaneAddDTO ad = new TvcPlaneAddDTO();
				
				ad.setTvcPlaneAddId("");
				ad.setSexId(a.getSexId());
				if (a.getSexId().equals("1")) {
					ad.setSexName("Nam");
					ad.setTitle("Ông");
				} else {
					ad.setSexName("Nữ");
					ad.setTitle("Bà");
				}
				ad.setCity("");
				ad.setHomePhone("");
				ad.setRelationshipId("");
				ad.setRelationshipName("");
				ad.setNetPremium(0d);
				ad.setChangePremium(0d);
				ad.setTotalPremium(0d);
				ad.setAge(0);
				if (a.getDob() != null && DateUtils.isValidDate(a.getDob(), "dd/MM/yyyy")) {
					int tuoi = DateUtils.countYears(DateUtils.str2Date(a.getDob()), obj.getInceptionDate());
					if (tuoi < 2) {
						ad.setNetPremium(0d);
						ad.setChangePremium(0d);
						ad.setTotalPremium(0d);
					} else {
						ad.setNetPremium(data.getPremium());
						ad.setChangePremium(0d);
						ad.setTotalPremium(ad.getNetPremium() - ad.getChangePremium());
					}
					ad.setDob(DateUtils.str2Date(a.getDob()));
					ad.setAge(tuoi); 
				} else {
					ad.setNetPremium(data.getPremium());
					ad.setChangePremium(0d);
					ad.setTotalPremium(ad.getNetPremium() - ad.getChangePremium());
				}
				ad.setTvcPlaneId(String.valueOf(kq));

				ad.setInsuredName(a.getInsuredName());
				ad.setIdPasswport(a.getIdPasswport());
				ad.setAddress("");
				ad.setCellPhone("");
				ad.setEmailAdress("");
				tvcPlaneAddService.insert(ad);
			}
			// check TH thêm mới: 0, update: 1 để gửi sms
	        if (StringUtils.isEmpty(obj.getAgreementId())) {
	        	// pay_action
	         	sendSmsAndSavePayActionInfo(co, voAgSave, "0");	
	        } else {
	        	sendSmsAndSavePayActionInfo(co, voAgSave, "1");
	        }
		}

		return obj;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	
	private PremiumTvcGotadi getPremiumCreate(ProductTvcPlaneVM obj) throws AgencyBusinessException{
		log.debug("REST request to getPremiumCreate : {}", obj);
		int pDay = 1;
		if (obj.getPlanId().equals("2")) {
			if (StringUtils.isEmpty(DateUtils.date2Str(obj.getExpiredDate()))) {
				throw new AgencyBusinessException("expiredDate", ErrorCode.NULL_OR_EMPTY);
			} else {
				if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getExpiredDate()), "dd/MM/yyyy")) {
					throw new AgencyBusinessException("expiredDate", ErrorCode.FORMAT_DATE_INVALID);
				}
			}

			if (obj.getExpiredDate().before(obj.getInceptionDate())) {
				throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID,
						"Ngày khởi hành không được lớn hơn ngày trở về");
			}

			// tinh so ngay
			int sn = DateUtils.getNumberDaysBetween2Date(obj.getInceptionDate(), obj.getExpiredDate());
			pDay = sn + 1;
		}

		int songuoikhongtinhphi = 0;
		int adult = 0;
		int children = 0;
		for (TvcPlaneAddBaseVM item : obj.getLstTvcPlane()) {
			if (item.getDob() != null || DateUtils.isValidDate(item.getDob(), "dd/MM/yyyy")) {
				int tuoi = DateUtils.countYears(DateUtils.str2Date(item.getDob()), obj.getInceptionDate());
				if (tuoi < 2) {
					songuoikhongtinhphi = songuoikhongtinhphi + 1;
				} else {
					if (tuoi < 13) {
						children = children + 1;
					} else {
						adult = adult + 1;
					}
				}
			} else {
				adult = adult + 1;
			}
		}

		if (songuoikhongtinhphi > 0 && adult < 1) {
			throw new AgencyBusinessException("lstTvcPlane", ErrorCode.INVALID,
					"Danh sách tham gia phải có người lớn đi kèm");
		}

		// kiem tra lai phi bao hiem
		int songuoitinhphi = obj.getLstTvcPlane().size() - songuoikhongtinhphi;

		PremiumTvcGotadi data = premiumTvcGotadiRepository
				.findByAreaIdAndPlanIdAndTypeOfAgencyAndFromDateLessThanEqualAndToDateGreaterThanEqual(obj.getAreaId(),
						obj.getPlanId(), obj.getAgencyType().toUpperCase(), pDay, pDay);
		if (data != null) {
			double premium = data.getPremium();

			if (obj.getNetPremium() != (premium * songuoitinhphi)) {
				throw new AgencyBusinessException("netPremium", ErrorCode.INVALID, "Sai phí bảo hiểm");
			}
		}

		return data;
	}
	
	private void validCalculatePremium(PremiumTvcPlaneVM obj) throws AgencyBusinessException {
		log.debug("REST request to validCalculatePremium : {}", obj);
		if (!obj.getAreaId().equals("1") && !obj.getAreaId().equals("2")) {
			throw new AgencyBusinessException("areaId", ErrorCode.INVALID);
		}

		if (!obj.getPlanId().equals("1") && !obj.getPlanId().equals("2")) {
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}

		if (!DateUtils.isValidDate(obj.getDateFrom(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("dateFrom", ErrorCode.FORMAT_DATE_INVALID);
		}
	}

	private void validcreateTvcPlanePolicy(ProductTvcPlaneVM obj) throws AgencyBusinessException {
		log.debug("REST request to validcreateTvcPlanePolicy : {}", obj);
		if (!obj.getAreaId().equals("1") && !obj.getAreaId().equals("2")) {
			throw new AgencyBusinessException("areaId", ErrorCode.INVALID);
		}

		if (!obj.getPlanId().equals("1") && !obj.getPlanId().equals("2")) {
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}

		if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getInceptionDate()), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		}

		if (!ValidateUtils.isPhone(obj.getCustomerPhone())) {
			throw new AgencyBusinessException("customerPhone", ErrorCode.INVALID);
		}

		validateInvoiceInfo(obj.getInvoiceInfo());

		if (obj.getLstTvcPlane().size() < 1) {
			throw new AgencyBusinessException("lstTvcPlane", ErrorCode.NULL_OR_EMPTY,
					"Số người tham gia không được nhỏ hơn 1");
		}
	}

	private AgreementDTO getObjectAgreement(ProductTvcPlaneVM obj, Contact co, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductTvcPlaneVM{}, Contact{}, AgencyDTO{} :", obj, co, currentAgency);
		AgreementDTO voAg = new AgreementDTO();
		if (obj.getAreaId().equals("1")) {
			voAg.setLineId("TVI");
		} else {
			voAg.setLineId("TVC");
		}
		
		// Insert common data
		insertAgreementCommonInfo(voAg.getLineId(), voAg, co, currentAgency, obj);
		
		voAg.setUserAgent(obj.getUserAgent());
		if (co.getType().equals("ONL")) {
			voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
			voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		} else {
			voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
			voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
		}
		voAg.setStatusGycbhName("Thanh toán qua đối tác");
		voAg.setInceptionDate(obj.getInceptionDate());
		voAg.setExpiredDate(obj.getExpiredDate());
		voAg.setStandardPremium(obj.getNetPremium());
		voAg.setChangePremium(obj.getChangePremium());
		voAg.setNetPremium(obj.getNetPremium());
		voAg.setTotalVat(0d);
		voAg.setTotalPremium(obj.getTotalPremium());
		voAg.setStatusRenewalsName(co.getContactName());
		if (co.getDateOfBirth() != null) {
			voAg.setContactDob(co.getDateOfBirth());
		}

		return voAg;
	}

	private TvcPlaneDTO getObjectProduct(ProductTvcPlaneVM obj, Contact co, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductTvcPlaneVM{}, Contact{}, AgencyDTO{} :", obj, co, currentAgency);
		TvcPlaneDTO vo = new TvcPlaneDTO();
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			vo.setTvcPlaneId(obj.getGycbhId());
		} else {
			vo.setTvcPlaneId("");
		}
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(vo.getTvcPlaneId())) {
			vo.setSoGycbh(obj.getGycbhNumber());
			vo.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			
			if (data != null) {
				vo.setSoGycbh(data.getGycbhNumber());
				vo.setPolicyNumber(data.getGycbhNumber());
			}
		}
		vo.setAreaId(obj.getAreaId());
		if (obj.getAreaId().equals("1")) {
			vo.setAreaName("Nội địa");
		} else {
			vo.setAreaName("Quốc tế");
		}
		vo.setPlanId(obj.getPlanId());
		if (obj.getPlanId().equals("1")) {
			vo.setPlanName("Một chiều");
		} else {
			vo.setPlanName("Khứ hồi");
		}

		vo.setSexId(obj.getSexId());
		vo.setSexName("");
		if (obj.getSexId().equals("1")) {
			vo.setSexName("Nam");
		} else {
			vo.setSexName("Nữ");
		}

		vo.setStatusId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		vo.setStatusName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		vo.setPlaceFrom(obj.getPlaceFrom());
		vo.setPlaceTo(obj.getPlaceTo());
		vo.setInceptionDate(obj.getInceptionDate());
		if (obj.getExpiredDate() != null) {
			vo.setExpiredDate(obj.getExpiredDate());
		}

		int songuoikhongtinhphi = 0;
		int adult = 0;
		int children = 0;
		for (TvcPlaneAddBaseVM item : obj.getLstTvcPlane()) {
			if (item.getDob() != null || DateUtils.isValidDate(item.getDob(), "dd/MM/yyyy")) {
				int tuoi = DateUtils.countYears(DateUtils.str2Date(item.getDob()), obj.getInceptionDate());
				if (tuoi < 2) {
					songuoikhongtinhphi = songuoikhongtinhphi + 1;
				} else {
					if (tuoi < 13) {
						children = children + 1;
					} else {
						adult = adult + 1;
					}
				}
			} else {
				adult = adult + 1;
			}
		}
		
		vo.setAdults(adult);
		vo.setChildren(children);
		vo.setBaby(songuoikhongtinhphi);
		if (vo.getAdults() + vo.getChildren() + vo.getBaby() > 2) {
			vo.setTravelWithId("3");
			vo.setTravelWithName("Khách đoàn");
		} else {
			vo.setTravelWithId("1");
			vo.setTravelWithName("Cá nhân");
		}
		vo.setCustomerCmt(obj.getCustomerCmt());
		vo.setCustomerName(obj.getCustomerName());
		vo.setCustomerAddress(obj.getCustomerAddress());
		vo.setCustomerPhone(obj.getCustomerPhone());
		vo.setCustomerEmail(obj.getCustomerEmail());

		vo.setCustomerId(co.getContactId());
		vo.setCustomerCode(co.getContactCode());

		vo.setInvoiceCheck(Integer.parseInt(obj.getInvoiceInfo().getCheck()));
		vo.setInvoiceName(obj.getInvoiceInfo().getName());
		vo.setInvoiceAddress(obj.getInvoiceInfo().getAddress());
		vo.setInvoiceTax(obj.getInvoiceInfo().getTaxNo());

		vo.setReceiverName(obj.getReceiverUser().getName());
		vo.setReceiverAddress(obj.getReceiverUser().getAddress());
		vo.setReceiverEmail(obj.getReceiverUser().getEmail());
		vo.setReceiverPhone(obj.getReceiverUser().getMobile());

		vo.setNetPremium(obj.getNetPremium());
		vo.setChangePremium(obj.getChangePremium());
		vo.setTotalPremium(obj.getTotalPremium());
		vo.setAgencyType(obj.getAgencyType());

		return vo;
	}
}
