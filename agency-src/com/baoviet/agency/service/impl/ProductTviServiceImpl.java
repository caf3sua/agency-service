package com.baoviet.agency.service.impl;

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

import com.baoviet.agency.domain.BenifitTvi;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.dto.TvicareDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.BenifitTviRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.TviCareAddRepository;
import com.baoviet.agency.service.ProductTviService;
import com.baoviet.agency.service.TviCareAddService;
import com.baoviet.agency.service.TvicareService;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.PremiumTviVM;
import com.baoviet.agency.web.rest.vm.ProductTviVM;

/**
 * Service Implementation for managing TVI.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductTviServiceImpl extends AbstractProductService implements ProductTviService {

	private final Logger log = LoggerFactory.getLogger(ProductTviServiceImpl.class);

	@Autowired
	private BenifitTviRepository benifitTviRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private TvicareService tviService;

	@Autowired
	private TviCareAddService tviCareAddService;
	
	@Autowired
	private TviCareAddRepository tviCareAddRepository;

	@Override
	public ProductTviVM createOrUpdatePolicy(ProductTviVM objTVI, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to createTvcPlanePolicy : ProductTviVM{}", objTVI);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(objTVI.getAgreementId())) {
			validateGycbhNumber(objTVI.getGycbhNumber(), currentAgency.getMa());	
		}
		
		Contact contact = contactRepository.findOneByContactCodeAndType(objTVI.getContactCode(), currentAgency.getMa());
		if (contact == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Không tồn tại dữ liệu");
		}

		// kiem tra phi BH
		validatePremium(objTVI);

		objTVI.setPolicyNumber(objTVI.getGycbhNumber());
		objTVI.setSoGycbh(objTVI.getGycbhNumber());
		
		// create TVIcare
		TvicareDTO pTVICARE = getObjectProduct(objTVI, currentAgency, contact);
		int idTVICare = tviService.insertTVI(pTVICARE);
		if (StringUtils.isNotEmpty(objTVI.getGycbhId())) {
			// xóa ds người bh khi update
			tviCareAddRepository.deleteByTravaelcareId(objTVI.getGycbhId());
		}
		
		// create TVI_CARE_ADD
		List<TviCareAddDTO> listTviCareAdd = new ArrayList<TviCareAddDTO>();
		for (TviCareAddDTO item : objTVI.getListTviAdd()) {
			TviCareAddDTO tvcd = new TviCareAddDTO();
			tvcd.setTravaelcareId(String.valueOf(idTVICare));
			tvcd.setIdPasswport(item.getIdPasswport());
			tvcd.setInsuredName(item.getInsuredName());
			tvcd.setDob(item.getDob());
			tvcd.setRelationship(item.getRelationshipId());
			tvcd.setRelationshipId(item.getRelationshipId());
			switch (tvcd.getRelationshipId()) {
			case "30":
				tvcd.setRelationshipName("Bản thân"); // Quan he
				break;
			case "31":
				tvcd.setRelationshipName("Vợ/Chồng"); // Quan he
				break;
			case "32":
				tvcd.setRelationshipName("Con"); // Quan he
				break;
			case "33":
				tvcd.setRelationshipName("Bố/Mẹ");
				break;
			case "34":
				tvcd.setRelationshipName("Bố mẹ của vợ/chồng");
				break;
			case "35":
				tvcd.setRelationshipName("Anh chị em ruột");
				break;
			case "36":
				tvcd.setRelationshipName("Anh chị em ruột của vợ/chồng");
				break;
			case "37":
				tvcd.setRelationshipName("Ông bà");
				break;
			case "38":
				tvcd.setRelationshipName("Cháu");
				break;
			case "39":
				tvcd.setRelationshipName("Thành viên đoàn"); // Quan he
				break;
			default:
				throw new AgencyBusinessException("relationshipId", ErrorCode.INVALID);
			}

			String tviAddId = tviCareAddService.insertTviCareAdd(tvcd);
			tvcd.setTviAddId(tviAddId);
			listTviCareAdd.add(tvcd);
		}

		// create agreement
		AgreementDTO voAg = getObjectAgreement(pTVICARE, contact, currentAgency, objTVI);
		voAg.setGycbhId(String.valueOf(idTVICare));
		pTVICARE.setTvicareId(String.valueOf(idTVICare));

		AgreementDTO agreementDTOSave = agreementService.save(voAg);
		log.debug("Result of save agreement, {}", agreementDTOSave);
		voAg.setAgreementId(agreementDTOSave.getAgreementId());

		// check TH thêm mới: 0, update: 1 để gửi sms
//        if (StringUtils.isEmpty(objTVI.getAgreementId())) {
        	// pay_action
         	sendSmsAndSavePayActionInfo(contact, agreementDTOSave, "0");	
//        } else {
//        	sendSmsAndSavePayActionInfo(contact, agreementDTOSave, "1");
//        }
		
		objTVI.setAgreementId(agreementDTOSave.getAgreementId());
		
		return objTVI;
	}

	@Override
	public PremiumTviVM calculatePremium(PremiumTviVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculatePremium : PremiumTviVM{}", obj);
		int sn = DateUtils.getNumberDaysBetween2DateStr(obj.getInceptionDate(), obj.getExpiredDate());
		int songay = sn + 1;
		obj.setNumberOfDay(songay);
		calculateValidData(obj);

		// Truyen mac dinh obj.package = 1
		// Neu package = 3 thi tinh them discount
		BenifitTvi vo = benifitTviRepository.getByParam(obj.getNumberOfDay(), "1", "", obj.getPlanId());

		// kiem tra xem neu la khach doan (obj.Package == "3") thi tinh them discount
		if (obj.getPremiumPackage().equals("3")) {
			if (obj.getNumberOfPerson() <= 1)
				throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID,
						"Gói khách đoàn yêu cầu nhiều hơn 1 khách hàng");

			if (obj.getNumberOfPerson() >= 10 && obj.getNumberOfPerson() <= 20) {
				obj.setPremiumDiscount(vo.getPremium() * obj.getNumberOfPerson() * 5 / 100);
				obj.setPremiumNet(vo.getPremium() * obj.getNumberOfPerson() - obj.getPremiumDiscount());
			} else if (obj.getNumberOfPerson() >= 21 && obj.getNumberOfPerson() <= 50) {
				obj.setPremiumDiscount(vo.getPremium() * obj.getNumberOfPerson() * 10 / 100);
				obj.setPremiumNet(vo.getPremium() * obj.getNumberOfPerson() - obj.getPremiumDiscount());
			} else if (obj.getNumberOfPerson() >= 51 && obj.getNumberOfPerson() <= 100) {
				obj.setPremiumDiscount(vo.getPremium() * obj.getNumberOfPerson() * 15 / 100);
				obj.setPremiumNet(vo.getPremium() * obj.getNumberOfPerson() - obj.getPremiumDiscount());
			} else if (obj.getNumberOfPerson() > 100) {
				obj.setPremiumDiscount(vo.getPremium() * obj.getNumberOfPerson() * 20 / 100);
				obj.setPremiumNet(vo.getPremium() * obj.getNumberOfPerson() - obj.getPremiumDiscount());
			} else {
				obj.setPremiumDiscount(0d);
				double premiumNet = vo != null ? vo.getPremium() * obj.getNumberOfPerson() - obj.getPremiumDiscount()
						: 0;
				obj.setPremiumNet(premiumNet);
			}
		} else if (obj.getPremiumPackage().equals("1")) {
			if (obj.getNumberOfPerson() != 1)
				throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID,
						"numberOfPerson - Gói cá nhân chỉ chấp nhận 1 khách hàng");
			vo = benifitTviRepository.getByParam(obj.getNumberOfDay(), obj.getPremiumPackage(), "1", obj.getPlanId());
			obj.setPremiumNet(vo.getPremium());
		} else {
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID);
		}

		// kiem tra discount va tinh tong phi
		double discount = 0.0;
		if(obj.getPremiumPercentDiscount() != null) {
			discount = obj.getPremiumPercentDiscount() != 0
					? obj.getPremiumNet() * obj.getPremiumPercentDiscount() / 100
					: 0;	
		}
		
		obj.setPremiumDiscount(discount);
		obj.setPremiumTvi(obj.getPremiumNet() - discount);

		return obj;
	}

	@Override
	public List<BenifitTvi> getAllBenefit() {
		List<BenifitTvi> data = benifitTviRepository.findAll();
		return data;
	}
	
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private void validatePremium(ProductTviVM objTVI) throws AgencyBusinessException {
		log.debug("REST request to validatePremium : ProductTviVM{}", objTVI);
		Date dateNow = new Date();
		if (!DateUtils.isValidDate(objTVI.getInceptionDate(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (DateUtils.str2Date(objTVI.getInceptionDate()).before(dateNow)) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID, "Thời hạn BH phải > ngày hiện tại");
		}
		PremiumTviVM preTVI = new PremiumTviVM();

		preTVI.setPremiumPackage(objTVI.getTravelWithId());
		preTVI.setPlanId(objTVI.getPlanId());
		preTVI.setInceptionDate(objTVI.getInceptionDate());
		preTVI.setExpiredDate(objTVI.getExpiredDate());
		preTVI.setNumberOfPerson(objTVI.getListTviAdd().size());
		preTVI.setPremiumPercentDiscount(objTVI.getChangePremium());
		preTVI = calculatePremium(preTVI);

		if (!preTVI.getPremiumTvi().equals(objTVI.getPremium())) {
			throw new AgencyBusinessException("premium", ErrorCode.INVALID, "Sai số tiền bảo hiểm " + preTVI.getPremiumTvi());
		}
	}
	
	private void calculateValidData(PremiumTviVM obj) throws AgencyBusinessException {
		log.debug("REST request to calculateValidData : PremiumTviVM{}", obj);
		if (obj.getNumberOfDay() < 1)
			throw new AgencyBusinessException("numberOfDay", ErrorCode.INVALID);

		if (!obj.getPremiumPackage().equals("3") && !obj.getPremiumPackage().equals("1"))
			throw new AgencyBusinessException("premiumPackage", ErrorCode.INVALID,
					"Gói bảo hiểm chấp nhận cá nhân (1) và khách nhóm (3)");

		if (!obj.getPlanId().equals("2") && !obj.getPlanId().equals("3") && !obj.getPlanId().equals("4") && !obj.getPlanId().equals("1") && !obj.getPlanId().equals("5"))
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
	}

	private TvicareDTO getObjectProduct(ProductTviVM obj, AgencyDTO currentAgency, Contact contact) throws AgencyBusinessException {
		log.debug("REST request to getObjectProduct : ProductTviVM{}", obj);
		TvicareDTO objOut = new TvicareDTO();
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			objOut.setTvicareId(obj.getGycbhId());
		}
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(objOut.getTvicareId())) {
			objOut.setSoGycbh(obj.getSoGycbh());
			objOut.setPolicyNumber(obj.getPolicyNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			
			if (data != null) {
				objOut.setSoGycbh(data.getGycbhNumber());
				objOut.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		objOut.setAgentId(currentAgency.getMa());
		objOut.setAgentName(currentAgency.getTen());
		if (obj.getUserAgent() != null) {
			objOut.setUserAgent(obj.getUserAgent());	
		}
		
		objOut.setPlanId(obj.getPlanId());
		switch (obj.getPlanId()) {
		case "1":
			objOut.setPlanName("Chương trình bảo hiểm Đồng");
			break;
		case "2":
			objOut.setPlanName("Chương trình bảo hiểm Bạc");
			break;
		case "3":
			objOut.setPlanName("Chương trình bảo hiểm Vàng");
			break;
		case "4":
			objOut.setPlanName("Chương trình bảo hiểm Bạch Kim");
			break;
		case "5":
			objOut.setPlanName("Chương trình bảo hiểm Kim Cương");
			break;
		default:
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}

		if (objOut.getTeamId() != null && objOut.getTeamId().equals("BM")) {
			objOut.setTeamName("Bản Mềm");
		} else if (objOut.getTeamId() != null && objOut.getTeamId() == "BC") {
			objOut.setTeamName("Bản Cứng");
		} else {
			objOut.setTeamName(" ");
		}

		if (objOut.getStatusId() != null && objOut.getStatusId().equals("TM")) {
			objOut.setStatusName("Tiền mặt");
		} else if (objOut.getStatusId() != null && objOut.getStatusId().equals("TT")) {
			objOut.setStatusName("Thanh toán trực tuyến");
		} else {
			objOut.setStatusName(" ");
		}

		objOut.setTravelWithId(obj.getTravelWithId());
		if (objOut.getTravelWithId().equals("1")) {
			objOut.setTravelWithName("Cá nhân");
		} else if (objOut.getTravelWithId().equals("2")) {
			objOut.setTravelWithName("Gia đình");
		} else if (objOut.getTravelWithId().equals("3")) {
			objOut.setTravelWithName("Khách đoàn");
		} else {
			throw new AgencyBusinessException("travelWithId", ErrorCode.INVALID);
		}
		if(StringUtils.isNotEmpty(obj.getDepartureId())) {
			objOut.setDepartureId(obj.getDepartureId());	 
		}
		objOut.setBankId(obj.getBankId());

		objOut.setChangePremium(obj.getChangePremium());
		if(obj.getDateOfPayment() != null) {
			objOut.setDateOfPayment(DateUtils.str2Date(obj.getDateOfPayment()));	
		}
		objOut.setDestinationId(obj.getDestinationId());
		objOut.setExpiredDate(DateUtils.str2Date(obj.getExpiredDate()));
		objOut.setInceptionDate(DateUtils.str2Date(obj.getInceptionDate()));
		objOut.setNetPremium(obj.getNetPremium());
		objOut.setPaymentMethod(obj.getPaymentMethod());

		objOut.setPremium(obj.getPremium());
		objOut.setPropserAddress(contact.getHomeAddress());
		objOut.setPropserCellphone(contact.getHandPhone());
		objOut.setPropserCmt(contact.getIdNumber());
		objOut.setPropserEmail(contact.getEmail());
		objOut.setPropserHomephone(contact.getPhone());
		objOut.setPropserId(contact.getContactId());
		objOut.setPropserName(contact.getContactName());
		
		objOut.setReceiverAddress(obj.getReceiverUser().getAddress());
		objOut.setReceiverEmail(obj.getReceiverUser().getEmail());
		objOut.setReceiverMoible(obj.getReceiverUser().getMobile());
		objOut.setReceiverName(obj.getReceiverUser().getName());
		
		objOut.setStatusPolicyId(obj.getStatusPolicyId());
		objOut.setTeamId(obj.getTeamId());
		objOut.setFeeReceive(obj.getFeeReceive());

		return objOut;
	}

	private AgreementDTO getObjectAgreement(TvicareDTO obj, Contact objContact, AgencyDTO currentAgency, ProductTviVM objTVI) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : TvicareDTO{}, Contact{}, AgencyDTO{}, ProductTviVM{}", obj, objContact, currentAgency, objTVI);
		AgreementDTO voAg = new AgreementDTO();
		// Insert common data
		insertAgreementCommonInfo("TVI", voAg, objContact, currentAgency, objTVI);
				
		voAg.setUserAgent(obj.getUserAgent());
		voAg.setInceptionDate(obj.getInceptionDate());
		voAg.setExpiredDate(obj.getExpiredDate());
		voAg.setStandardPremium(obj.getPremium());
		voAg.setChangePremium(obj.getChangePremium());
		voAg.setNetPremium(obj.getNetPremium());
		voAg.setTotalVat(0.0);
		voAg.setTotalPremium(obj.getPremium());
		voAg.setFeeReceive(obj.getFeeReceive());
		
		return voAg;
	}

}

