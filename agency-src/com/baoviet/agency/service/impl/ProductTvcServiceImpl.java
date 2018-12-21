package com.baoviet.agency.service.impl;

import java.util.ArrayList;
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

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.BenifitTravel;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TravelCareAddDTO;
import com.baoviet.agency.dto.TravelcareDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.BenifitTravelRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.TravelCareAddRepository;
import com.baoviet.agency.service.ProductTvcService;
import com.baoviet.agency.service.TravelCareAddService;
import com.baoviet.agency.service.TravelcareService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumTvcVM;
import com.baoviet.agency.web.rest.vm.ProductTvcVM;
import com.baoviet.agency.web.rest.vm.TvcAddBaseVM;

/**
 * Service Implementation for managing TVC.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductTvcServiceImpl extends AbstractProductService implements ProductTvcService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcServiceImpl.class);

	@Autowired
	private BenifitTravelRepository benifitTravelRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private TravelcareService travelcareService;
	
	@Autowired
	private TravelCareAddService travelCareAddService;
	
	@Autowired
	private TravelCareAddRepository travelCareAddRepository;
	
	@Override
	public ProductTvcVM createOrUpdatePolicy(ProductTvcVM tvcBase, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("request to createOrUpdatePolicy, ProductTvcVM {}", tvcBase);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(tvcBase.getAgreementId())) {
			validateGycbhNumber(tvcBase.getGycbhNumber(), currentAgency.getMa());	
		}

        // type contact = agency_id
 		Contact co = contactRepository.findOneByContactCodeAndType(tvcBase.getContactCode(), currentAgency.getMa());
 		if (co == null) {
 			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Không tồn tại dữ liệu");
 		}
 		
 		// Check validate data
        validateDataPolicy(tvcBase, co);
        tvcBase.setPolicyNumber(tvcBase.getGycbhNumber());            
        
        // TRAVELCARE
        TravelcareDTO pTRAVELCARE = getObjectProduct(tvcBase, co, currentAgency);
        int idTravelCare = travelcareService.insertTravelcare(pTRAVELCARE);

        //  AGREEMENT
        AgreementDTO voAg = getObjectAgreement(tvcBase, co, currentAgency);
        voAg.setGycbhId(String.valueOf(idTravelCare));            
        AgreementDTO agreementDTOSave = agreementService.save(voAg);
        voAg.setAgreementId(agreementDTOSave.getAgreementId());

        List<Integer> lstTravelCareAddDTO = new ArrayList<Integer>();
        if (StringUtils.isNotEmpty(tvcBase.getGycbhId())) {
        	// xóa ds bảo hiểm
        	travelCareAddRepository.deleteByTravaelcareId(tvcBase.getGycbhId());
        }
        
        for (TvcAddBaseVM item : tvcBase.getListTvcAddBaseVM())
        {
        	TravelCareAddDTO tvcad = new TravelCareAddDTO();
        	tvcad.setTvcAddId("");
            tvcad.setTravaelcareId(String.valueOf(idTravelCare));
            tvcad.setInsuredName(item.getInsuredName());
            if (StringUtils.isNotEmpty(item.getDob())) {
            	tvcad.setDob(DateUtils.str2Date(item.getDob()));
            }
            tvcad.setDateofbỉth(DateUtils.str2Date(item.getDob()));
            tvcad.setIdPasswport(item.getIdPasswport());
            tvcad.setRelationshipId(item.getRelationship());                
            
            switch (item.getRelationship())
            {
                case "30":
                    tvcad.setRelationship("30");
                    tvcad.setRelationshipName("bản thân");
                    break;
                case "31":
                    tvcad.setRelationship("31");
                    tvcad.setRelationshipName("vợ/chồng");
                    break;
                case "32":
                    tvcad.setRelationship("32");
                    tvcad.setRelationshipName("con");
                    break;
                case "33":
                    tvcad.setRelationship("33");
                    tvcad.setRelationshipName("bố/mẹ");
                    break;
                case "34":
                    tvcad.setRelationship("34");
                    tvcad.setRelationshipName("bố mẹ của vợ/chồng");
                    break;
                case "35":
                    tvcad.setRelationship("35");
                    tvcad.setRelationshipName("anh chị em ruột");
                    break;
                case "36":
                    tvcad.setRelationship("36");
                    tvcad.setRelationshipName("anh chị em ruột của vợ/chồng");
                    break;
                case "37":
                    tvcad.setRelationship("37");
                    tvcad.setRelationshipName("ông bà");
                    break;
                case "38":
                    tvcad.setRelationship("38");
                    tvcad.setRelationshipName("cháu");
                    break;
                case "39":
                    tvcad.setRelationship("39");
                    tvcad.setRelationshipName("Khách đoàn");
                    break;
                case "99":
                    tvcad.setRelationship("99");
                    tvcad.setRelationshipName("Khác");
                    break;
                default:
                    tvcad.setRelationship("");
                    tvcad.setRelationshipName("");
                    break;
            }

            tvcad.setTitle("");
            tvcad.setJob("");
            tvcad.setCity("");
            tvcad.setAddress("");
            tvcad.setHomePhone("");
            tvcad.setCellPhone("");
            tvcad.setEmailAdress("");                
            tvcad.setDiagnose("");
            
            Date dateNow = new Date();
            tvcad.setDayTreatment(dateNow);
            tvcad.setDetailTreatment("");
            tvcad.setResultTreatment("");
            tvcad.setNameDoctor("");

            int tvcAddId =  travelCareAddService.insertTravelCareAdd(tvcad);
            lstTravelCareAddDTO.add(tvcAddId);
        }
        
        // pay_action
     	sendSmsAndSavePayActionInfo(co, agreementDTOSave);
        
        tvcBase.setNetPremium(voAg.getNetPremium());
        tvcBase.setPremium(voAg.getStandardPremium());
        tvcBase.setChangePremium(voAg.getChangePremium());
        tvcBase.setAgreementId(voAg.getAgreementId());
        tvcBase.setTravelCareId(idTravelCare);
        return tvcBase;
	}

	@Override
	public PremiumTvcVM calculatePremium(PremiumTvcVM obj) throws AgencyBusinessException {
		log.debug("START calculatePremium PremiumTvcVM, obj: {}", obj);
		// Validate data
		validateDataPremium(obj);

		// Call database
		BenifitTravel vo = new BenifitTravel();
		// neu package = 3, tinh phi nhu ca nhan * so luong nguoi
        if (obj.getPremiumPackage().equals("3")) {
        	vo = benifitTravelRepository.getByParam(obj.getSongay(), "1",
    				obj.getDestination(), obj.getPlanId());
        } else {
        	vo = benifitTravelRepository.getByParam(obj.getSongay(), obj.getPremiumPackage(),
        			obj.getDestination(), obj.getPlanId());
        }

		if (vo == null)
			throw new AgencyBusinessException("planId", ErrorCode.INVALID, "Chưa có dữ liệu phù hợp để tính phí");

		// kiem tra discount va tinh tong phi
        if (obj.getPremiumPackage().equals("2")) { // gói gia đình thì phí ko nhân với số người
        	obj.setPremiumNet(vo.getPremium());
        } else {
        	obj.setPremiumNet(vo.getPremium() * obj.getNumberOfPerson());
        }

        double discount = (obj.getPremiumDiscount() != null && obj.getPremiumDiscount() != 0)
				? obj.getPremiumNet() * obj.getPremiumDiscount() / 100
				: 0;
		obj.setPremiumTvc(obj.getPremiumNet() - discount);

		return obj;
	}

	@Override
	public List<BenifitTravel> getBenefitByAreaId(String areaId) throws AgencyBusinessException {
		log.debug("request to getBenefitByAreaId, areaId {}", areaId);
		List<BenifitTravel> data = benifitTravelRepository.findByAreaId(areaId);
		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private void validateDataPremium(PremiumTvcVM obj) throws AgencyBusinessException {
		log.debug("request to validateDataPremium, PremiumTvcVM {}", obj);
		if (!DateUtils.isValidDate(obj.getNgayDi(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("ngayDi", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (!DateUtils.isValidDate(obj.getNgayVe(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.FORMAT_DATE_INVALID);
		}

		Date inceptionDate = DateUtils.str2Date(obj.getNgayDi());
		Date expiredDate = DateUtils.str2Date(obj.getNgayVe());

		if (expiredDate.before(inceptionDate)) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.INVALID,
					"Ngày khởi hành không được lớn hơn ngày trở về");
		}

		// Tao new date + 180 ngay
		Calendar cal = Calendar.getInstance();
		cal.setTime(inceptionDate);
		cal.add(Calendar.DAY_OF_YEAR, 180);
		Date newDate = cal.getTime();

		if (expiredDate.after(newDate)) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.INVALID, "Thời gian du lịch quá 180 ngày");
		}

		int sn = DateUtils.getNumberDaysBetween2Date(inceptionDate, expiredDate);
		int songay = sn + 1;
		obj.setSongay(songay);

		if (obj.getNumberOfPerson() < 1) {
			throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người đi du lịch phải lớn hơn 1");
		}

		if (!obj.getDestination().equals("1") && !obj.getDestination().equals("2") && !obj.getDestination().equals("3")
				&& !obj.getDestination().equals("4")) {
			throw new AgencyBusinessException("destination", ErrorCode.INVALID, "Nơi đến không có trong danh sách");

		}

		if (!obj.getPlanId().equals("1") && !obj.getPlanId().equals("2") && !obj.getPlanId().equals("3") && !obj.getPlanId().equals("4")) {
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}

		if (obj.getPremiumPackage().equals("3")) {
			if (obj.getNumberOfPerson() < 1) { // || obj.getNumberOfPerson() > 20) {
				throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người đi du lịch theo đoàn phải lớn hơn 1");
			}
		} else if (obj.getPremiumPackage().equals("2")) {
			if (obj.getNumberOfPerson() < 2 || obj.getNumberOfPerson() > 5) {
				throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người đi du lịch theo gia đình phải từ 2-5 người");
			}
		} else if (obj.getPremiumPackage().equals("1")) {
			if (obj.getNumberOfPerson() > 1) {
				throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người đi du lịch cá nhân không được lớn hơn 1");
			}
		}
	}

	private void validateDataPolicy(ProductTvcVM objTravel, Contact co) throws AgencyBusinessException {
		log.debug("request to validateDataPolicy, ProductTvcVM {}", objTravel);
		
		// check type of ContactCode
		if (!StringUtils.equals(co.getCategoryType(), AgencyConstants.CONTACT_CATEGORY_TYPE.ORGANIZATION)) {
			if (!DateUtils.isValidDate(objTravel.getPropserNgaysinh(), "dd/MM/yyyy")) {
				throw new AgencyBusinessException("propserNgaysinh", ErrorCode.FORMAT_DATE_INVALID);
			}

			int utageYCBH = DateUtils.countYears(DateUtils.str2Date(objTravel.getPropserNgaysinh()),
					DateUtils.str2Date(objTravel.getInceptionDate()));

			if (utageYCBH < 18 || utageYCBH > 85) {
				throw new AgencyBusinessException("propserNgaysinh", ErrorCode.INVALID,
						"Người yêu cầu bảo hiểm phải từ 18 đến 85 tuổi");
			}
		}
				
		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(objTravel.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}

		validateInvoiceInfo(objTravel.getInvoiceInfo());
		
		if (!objTravel.getDestinationId().equals("1") && !objTravel.getDestinationId().equals("2")
				&& !objTravel.getDestinationId().equals("3") && !objTravel.getDestinationId().equals("4")) {
			throw new AgencyBusinessException("destinationId", ErrorCode.INVALID, "Nơi đến không có trong danh sách");
		}
		if (!objTravel.getTravelWithId().equals("1") && !objTravel.getTravelWithId().equals("2")
				&& !objTravel.getTravelWithId().equals("3")) {
			throw new AgencyBusinessException("travelWithId", ErrorCode.INVALID);
		}
		if (objTravel.getSoNguoiThamGia() <= 0) {
			throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người tham gia phải > 0");
		} else {
			// Ca nhan
			if (objTravel.getTravelWithId().equals("1")) {
				if (objTravel.getSoNguoiThamGia() > 1) {
					throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID,
							"Số người tham gia phải = 1");
				}
			}
			// Gia dinh
			if (objTravel.getTravelWithId().equals("2")) {
				if (objTravel.getSoNguoiThamGia() < 2 || objTravel.getSoNguoiThamGia() > 5) {
					throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID, "Số người đi du lịch theo gia đình phải từ 2-5 người");
				}
			}
			// Khach doan
			if (objTravel.getTravelWithId().equals("3")) {
				if (objTravel.getSoNguoiThamGia() < 1) { // || objTravel.getSoNguoiThamGia() > 20) {
					throw new AgencyBusinessException("numberOfPerson", ErrorCode.INVALID,
							"Số người tham gia phải từ 1 người");
				}
			}
		}

		Date dateNow = new Date();
		Date inceptionDate = DateUtils.str2Date(objTravel.getInceptionDate());
		Date expiredDate = DateUtils.str2Date(objTravel.getExpiredDate());

		// Tao new date + 180 ngay
		Calendar cal = Calendar.getInstance();
		cal.setTime(inceptionDate);
		cal.add(Calendar.DAY_OF_YEAR, 180);
		Date newDate = cal.getTime();

		if (!DateUtils.isValidDate(DateUtils.date2Str(inceptionDate), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		} else if (inceptionDate.before(dateNow)) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID,
					"Ngày khởi hành phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}

		if (!DateUtils.isValidDate(DateUtils.date2Str(expiredDate), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.FORMAT_DATE_INVALID);
		} else if (expiredDate.before(inceptionDate)) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.INVALID,
					"Ngày khởi hành không được lớn hơn ngày trở về");
		} else if (expiredDate.after(newDate)) {
			throw new AgencyBusinessException("expiredDate", ErrorCode.INVALID, "Thời gian du lịch quá 180 ngày");
		}

		if (!objTravel.getPlanId().equals("1") && !objTravel.getPlanId().equals("2") && !objTravel.getPlanId().equals("3")
				&& !objTravel.getPlanId().equals("4")) {
			throw new AgencyBusinessException("planId", ErrorCode.INVALID);
		}
		
		// thong tin travelcare add
		if (objTravel.getListTvcAddBaseVM().size() == 0 || objTravel.getListTvcAddBaseVM() == null) {
			throw new AgencyBusinessException("listTvcAddBaseVM", ErrorCode.NULL_OR_EMPTY,
					"Thiếu danh sách người được bảo hiểm");
		} else {
			// Ca nhan
			if (objTravel.getTravelWithId().equals("1")) {
				if (objTravel.getListTvcAddBaseVM().size() > 1) {
					throw new AgencyBusinessException("listTvcAddBaseVM", ErrorCode.INVALID,
							"Số người tham gia phải = 1");
				}
			}
			// Gia dinh
			if (objTravel.getTravelWithId().equals("2")) {
				if (objTravel.getListTvcAddBaseVM().size() < 2 || objTravel.getListTvcAddBaseVM().size() > 5) {
					throw new AgencyBusinessException("listTvcAddBaseVM", ErrorCode.INVALID, "Số người đi du lịch theo gia đình phải từ 2-5 người");
				}
			}
			// Khach doanh
			if (objTravel.getTravelWithId().equals("3")) {
				if (objTravel.getListTvcAddBaseVM().size() < 1) { // || objTravel.getListTvcAddBaseVM().size() > 20) {
					throw new AgencyBusinessException("listTvcAddBaseVM", ErrorCode.INVALID,
							"Số người tham gia phải từ 1 người");
				}
			}
			int banthan = 0;
			int vochong = 0;
			int duoi11tuoi = 0;
			int nguoilon = 0;
			int tu12den16 = 0;
			int checkbanthan = 0;
			for (TvcAddBaseVM tvcAd : objTravel.getListTvcAddBaseVM()) {
				if (StringUtils.isEmpty(tvcAd.getInsuredName())) {
					throw new AgencyBusinessException("insuredName", ErrorCode.NULL_OR_EMPTY);
				}
				if (StringUtils.isEmpty(tvcAd.getIdPasswport()) && StringUtils.isEmpty(tvcAd.getDob())) {
					throw new AgencyBusinessException("idPasswport", ErrorCode.NULL_OR_EMPTY, "Thiếu thông tin ngày sinh hoặc CMND/Hộ chiếu");
				}
				
				if (StringUtils.isEmpty(tvcAd.getRelationship())) {
					throw new AgencyBusinessException("relationship", ErrorCode.NULL_OR_EMPTY, "Thiếu thông tin quan hệ");
				} else {
					if (!tvcAd.getRelationship().equals("30") && !tvcAd.getRelationship().equals("31")
							&& !tvcAd.getRelationship().equals("32") && !tvcAd.getRelationship().equals("33")
							&& !tvcAd.getRelationship().equals("34") && !tvcAd.getRelationship().equals("39")
							&& !tvcAd.getRelationship().equals("99")) {
						throw new AgencyBusinessException("relationship", ErrorCode.INVALID, "Quan hệ không hợp lệ");
					}

					if (objTravel.getTravelWithId().equals("3")) {
						if (!(tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.KHACH_DOAN) 
								|| tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.BAN_THAN))) {
							throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
									"Du lịch theo đoàn thì quan hệ phải là: Thành viên đoàn hoặc Bản thân");
						}
					} else if (objTravel.getTravelWithId().equals("2")) {
						if (!tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.BO_ME) 
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.VO_CHONG)
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.CON) 
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.BAN_THAN) ) {
							throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
									"Du lịch theo gia đình chỉ có 4 mối quan hệ : Bố/mẹ, vợ chồng, con cái, bản thân");
						}
					} else if (objTravel.getTravelWithId().equals("1")) {
						if (!tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.BAN_THAN) 
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.VO_CHONG)
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.CON)
								&& !tvcAd.getRelationship().equals(AgencyConstants.RELATIONSHIP.BO_ME) ) {
							throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
									"Du lịch cá nhân chỉ có 4 mối quan hệ : Bố/mẹ, vợ chồng, con cái, bản thân");
						}
					} else {
						if (tvcAd.getRelationship().equals("39")) {
							throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
									"Quan hệ là: Thành viên đoàn chỉ áp dụng cho khách du lịch theo đoàn");
						}
					}
				}
				if (StringUtils.isEmpty(tvcAd.getDob())) {
//					throw new AgencyBusinessException("dob", ErrorCode.NULL_OR_EMPTY,
//							"Thiếu ngày sinh người được bảo hiểm");
				} else {
					if (!DateUtils.isValidDate(tvcAd.getDob(), "dd/MM/yyyy")) {
						throw new AgencyBusinessException("dob", ErrorCode.FORMAT_DATE_INVALID);
					}

					int utageNDBHYear = DateUtils.countYears(DateUtils.str2Date(tvcAd.getDob()),
							DateUtils.str2Date(objTravel.getInceptionDate()));
					int utageNDBHMonth = DateUtils.getNumberMonthsBetween2Date(DateUtils.str2Date(tvcAd.getDob()),
							DateUtils.str2Date(objTravel.getInceptionDate()));

					if (utageNDBHYear == 0 && utageNDBHMonth < 6) {
						throw new AgencyBusinessException("dob", ErrorCode.INVALID,
								"Người được bảo hiểm phải từ 6 tháng tuổi");
					}
					if (utageNDBHYear > 85) {
						throw new AgencyBusinessException("dob", ErrorCode.INVALID,
								"Người được bảo hiểm phải <= 85 tuổi");
					}

					// duoi12tuoi
					if (utageNDBHYear < 12)
						duoi11tuoi++;

					// 12 den 16 tuoi
					if (utageNDBHYear < 12 && utageNDBHYear < 17)
						tu12den16++;

					if (utageNDBHYear >= 17)
						nguoilon++;

					if (tvcAd.getRelationship().equals("30")) {
						banthan++;
						int checkDate = DateUtils.str2Date(objTravel.getPropserNgaysinh())
								.compareTo(DateUtils.str2Date(tvcAd.getDob()));

						if (!objTravel.getPropserName().equals(tvcAd.getInsuredName()) || checkDate != 0)
							checkbanthan++;
					}
					
					if (tvcAd.getRelationship().equals("31"))
						vochong++;
					
				}
			}

			// kiem tra phi bao hiem
			// tam thoi chua check lai phi bao hiem
			// DALTRAVELCARE daltvc = new DALTRAVELCARE();
			// PREMIUMTVC phitvc = daltvc.GetBENIFITTRAVELByPram(obj.NumberOfDay,
			// obj.Package, obj.Destination, obj.PlanId);
			// obj.PremiumTvc = vo.PREMIUM * obj.NumberOfPerSon;

			// kiem tra logic nghiep vu
			if (banthan > 1) {
				throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
						"Danh sách NĐBH tồn tại hơn 1 người có quan hệ là Bản thân");
			}
			if (checkbanthan != 0) {
				throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
						"Thông tin người được bảo hiểm khác với thông tin người yêu cầu bảo hiểm có quan hệ là bản thân");
			}
			if (vochong > 1) {
				throw new AgencyBusinessException("relationship", ErrorCode.INVALID,
						"Danh sách NĐBH tồn tại hơn 1 người có quan hệ là vợ/chồng");
			}
			if (objTravel.getTravelWithId().equals("1")) {
				if (duoi11tuoi > 0) {
					throw new AgencyBusinessException("dob", ErrorCode.INVALID,
							"Trẻ em dưới 12 tuổi không được đi một mình");
				}
			} else {
				if (duoi11tuoi > 0 && nguoilon == 0) {
					throw new AgencyBusinessException("dob", ErrorCode.INVALID,
							"Người được bảo hiểm dưới 12 tuổi phải có người lớn đi kèm");
				}
			}
		}
	}

	private TravelcareDTO getObjectProduct(ProductTvcVM obj, Contact co, AgencyDTO currentAgency) {
		log.debug("request to getObjectProduct, ProductTvcVM {}, Contact{} ", obj, co);
		TravelcareDTO tvc = new TravelcareDTO();
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			tvc.setTravelcareId(obj.getGycbhId());
			// update version nếu là thanh toán sau
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (StringUtils.equals(AgencyConstants.PAYMENT_METHOD_LATER, data.getPaymentMethod()) && data.getStatusPolicyId().equals(AgencyConstants.AgreementStatus.DA_THANH_TOAN)) {
				TravelcareDTO travelcare = travelcareService.getById(obj.getGycbhId());
				if (travelcare.getVersion() != null) {
					tvc.setVersion(travelcare.getVersion() + 1);
				} else {
					tvc.setVersion(1);
				}
			}
		} else {
			tvc.setTravelcareId("");
		}
		
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(tvc.getTravelcareId())) {
			tvc.setSoGycbh(obj.getGycbhNumber());
			tvc.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			
			if (data != null) {
				tvc.setSoGycbh(data.getGycbhNumber());
				tvc.setPolicyNumber(data.getGycbhNumber());
			}
		}
		
		tvc.setTravelWithId(obj.getTravelWithId());
		tvc.setTravelWithName("");
		if (obj.getTravelWithId().equals("1")) {
			tvc.setTravelWithName("Cá nhân");
		}

		if (obj.getTravelWithId().equals("2")) {
			tvc.setTravelWithName("Gia đình");
		}

		if (obj.getTravelWithId().equals("3")) {
			tvc.setTravelWithName("Khách đoàn");
		}

		tvc.setInceptionDate(DateUtils.str2Date(obj.getInceptionDate()));
		tvc.setExpiredDate(DateUtils.str2Date(obj.getExpiredDate()));
		tvc.setPlanId(obj.getPlanId());
		switch (obj.getPlanId()) {
		case "2":
			tvc.setPlanId("2");
			tvc.setPlanName("Chương trình bảo hiểm Bạc");
			break;
		case "3":
			tvc.setPlanId("3");
			tvc.setPlanName("Chương trình bảo hiểm Vàng");
			break;
		case "4":
			tvc.setPlanId("4");
			tvc.setPlanName("Chương trình bảo hiểm Kim Cương");
			break;
		case "1":
			tvc.setPlanId("1");
			tvc.setPlanName("Chương trình bảo hiểm Đồng");
			break;
		default:
			tvc.setPlanId("");
			tvc.setPlanName("");
			break;
		}

		tvc.setDestinationId(obj.getDestinationId());
		tvc.setDestinationName("");
		switch (obj.getDestinationId()) {
		case "1":
			tvc.setDestinationName("Việt Nam");
			break;
		case "2":
			tvc.setDestinationName("ASEAN");
			break;
		case "3":
			tvc.setDestinationName("Châu Á - Thái Bình Dương");
			break;
		case "4":
			tvc.setDestinationName("Toàn cầu");
			break;
		default:
			tvc.setDestinationName("");
			break;
		}

		tvc.setPropserName(obj.getPropserName());
		tvc.setPropserCellphone(obj.getPropserCellphone());
		tvc.setPropserNgaysinh(DateUtils.str2Date(obj.getPropserNgaysinh()));

		if (co != null) {
			tvc.setPropserId(co.getContactId());
			tvc.setPropserTitle(co.getContactSex() == "1" ? "Ông" : "Bà");
			tvc.setAccountName(co.getContactSex() == "1" ? "Nam" : "Nữ");
		}
		tvc.setBankId(obj.getLoaitien());
		if (co.getType().equals("ONL")) {
			tvc.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
			tvc.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		} else {
			tvc.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
			tvc.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
		}
		tvc.setPaymentMethod(obj.getPaymentMethod());
		tvc.setAgentId(currentAgency.getMa());
		tvc.setAgentName(currentAgency.getTen());
		
		tvc.setReceiverName(obj.getReceiverUser().getName());
		tvc.setReceiverAddress(obj.getReceiverUser().getAddress());
		tvc.setReceiverEmail(obj.getReceiverUser().getEmail());
		tvc.setReceiverMoible(obj.getReceiverUser().getMobile());
		
		if(obj.getInvoiceInfo() != null) {
			tvc.setInvoiceBuyer(obj.getInvoiceInfo().getName());
			tvc.setInvoiceCompany(obj.getInvoiceInfo().getCompany());
			tvc.setInvoiceTaxNo(obj.getInvoiceInfo().getTaxNo());
			tvc.setInvoiceAddress(obj.getInvoiceInfo().getAddress());
			tvc.setInvoiceAccountNo(obj.getInvoiceInfo().getAccountNo());	
        }

		tvc.setPremium(obj.getPremium());
		tvc.setChangePremium(obj.getChangePremium());
		tvc.setNetPremium(obj.getNetPremium());
		tvc.setFeeReceive(obj.getNetPremium() - obj.getPremium());
		tvc.setStatusId("TT");
		tvc.setStatusName("Thanh toán trực tuyến");

		tvc.setTeamId("BM");
		tvc.setTeamName("Bản mềm");

		return tvc;
	}

	private AgreementDTO getObjectAgreement(ProductTvcVM obj, Contact objContact, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("request to getObjectAgreement, ProductTvcVM {}, Contact{} ", obj, objContact);
		AgreementDTO voAg = new AgreementDTO();
        // Insert common data
     	insertAgreementCommonInfo("TVC", voAg, objContact, currentAgency, obj);
     	
        voAg.setInceptionDate(DateUtils.str2Date(obj.getInceptionDate()));
        voAg.setExpiredDate(DateUtils.str2Date(obj.getExpiredDate()));

        PremiumTvcVM premiumTVC = new PremiumTvcVM();
        premiumTVC.setDestination(obj.getDestinationId());
        premiumTVC.setNumberOfPerson(obj.getSoNguoiThamGia());
        premiumTVC.setNgayDi(obj.getInceptionDate());
        premiumTVC.setNgayVe(obj.getExpiredDate());
        premiumTVC.setPlanId(obj.getPlanId());
        premiumTVC.setPremiumPackage(obj.getTvcPackage());
        premiumTVC.setPremiumDiscount(obj.getChangePremium());
        calculatePremium(premiumTVC);
        voAg.setStandardPremium(premiumTVC.getPremiumNet()); // phí gốc sản phẩm
        double changePremium = 0.0;
        if(premiumTVC.getPremiumDiscount() != null) {
        	changePremium = premiumTVC.getPremiumNet() - premiumTVC.getPremiumTvc();
        }
        voAg.setChangePremium(changePremium);
        voAg.setNetPremium(premiumTVC.getPremiumNet());
        voAg.setTotalPremium(premiumTVC.getPremiumTvc()); 
        voAg.setTotalVat(0.0);
        voAg.setUserAgent("");
     	if (objContact.getContactName() != null) {
     		voAg.setStatusRenewalsName(objContact.getContactName());	
     	}
     	
        return voAg;
    }

}
