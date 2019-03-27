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
import com.baoviet.agency.domain.MotoHondaCat;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.MotoHondaDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.MotoHondaCatRepository;
import com.baoviet.agency.service.HondaService;
import com.baoviet.agency.service.ProductHondaService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.ProductHondaVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;

/**
 * Service Implementation for managing Honda.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductHondaServiceImpl extends AbstractProductService implements ProductHondaService {

	private final Logger log = LoggerFactory.getLogger(ProductHondaServiceImpl.class);

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private HondaService hondaService;
	
	@Autowired
    private MotoHondaCatRepository motoHondaCatRepository;
	
	@Override
	public ProductHondaVM createOrUpdatePolicy(ProductHondaVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("REST request to createOrUpdateMotoPolicy : {}", obj);
		
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

		// gan du lieu vao obj de insert vao db
		MotoHondaDTO pMOTO = getObjectProduct(obj, co, currentAgency);

		AgreementDTO pAGREEMENT = getObjectAgreement(obj, co, pMOTO, currentAgency);
		
		// thuc hien luu du lieu
		int motoId = hondaService.insertHonda(pMOTO);
		if (motoId > 0) {
			pAGREEMENT.setGycbhId(String.valueOf(motoId));
			// luu agreement
			AgreementDTO agreementDTOSave = agreementService.save(pAGREEMENT);
			log.debug("Result of save agreement, {}", agreementDTOSave);
			
	         sendSmsAndSavePayActionInfo(co, agreementDTOSave, "0");	
	        obj.setAgreementId(agreementDTOSave.getAgreementId());
		}

		return obj;
	}
	
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private void validateDataPolicy(ProductHondaVM obj) throws AgencyBusinessException {
		log.debug("REST request to validateDataPolicy : {}", obj);
		Date dateNow = new Date();
		if (!DateUtils.isValidDate(obj.getThoihantu(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (DateUtils.str2Date(obj.getThoihantu()).before(dateNow)) {
			throw new AgencyBusinessException("thoihantu", ErrorCode.INVALID, "Thời hạn BH phải > ngày hiện tại");
		}

		if (obj.getTongPhi() <= 0) {
			throw new AgencyBusinessException("tongPhi", ErrorCode.INVALID, "Cần nhập phí BH");
		}

		// kiem tra dinh dang so dien thoai
		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID, "Số điện thoại người nhận không đúng định dạng");
		}

	}
	
	private AgreementDTO getObjectAgreement(ProductHondaVM obj, Contact co, MotoHondaDTO pMOTO, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("REST request to getObjectAgreement : ProductMotoVM{}, Contact{}, MotoHondaDTO{}, AgencyDTO{}", obj, co, pMOTO, currentAgency);
		AgreementDTO pAGREEMENT = new AgreementDTO();
		
		// Insert common data
		insertAgreementCommonInfo("MOH", pAGREEMENT, co, currentAgency, obj);
		
		pAGREEMENT.setUserAgent("");
		pAGREEMENT.setInceptionDate(pMOTO.getInceptionDate());
		pAGREEMENT.setExpiredDate(pMOTO.getExpiredDate());

		double tongPhi = obj.getTongPhi();
		pAGREEMENT.setStandardPremium(tongPhi);
		pAGREEMENT.setChangePremium(0.0);
		pAGREEMENT.setNetPremium(tongPhi);
		pAGREEMENT.setTotalVat(0.0);
		pAGREEMENT.setTotalPremium(tongPhi);
		pAGREEMENT.setReceiveMethod(obj.getReceiveMethod());
		
        return pAGREEMENT;
    }
	
	private MotoHondaDTO getObjectProduct(ProductHondaVM obj, Contact co, AgencyDTO currentAgency) {
		log.debug("REST request to getObjectProduct : ProductMotoVM{}, Contact{}, AgencyDTO{}", obj, co, currentAgency);
		MotoHondaDTO pMOTO = new MotoHondaDTO();

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
		
		pMOTO.setGoiBaoHiem(obj.getGoi());
		if (StringUtils.isNotEmpty(obj.getIdModel())) {
			MotoHondaCat motoHondaCat = motoHondaCatRepository.findOne(obj.getIdModel());
			if (motoHondaCat != null) {
				pMOTO.setDungTichXe(motoHondaCat.getDungTich());
				pMOTO.setVcxStbh(motoHondaCat.getGiaTriXe());
				pMOTO.setVcxPhi(obj.getTongPhi());
				pMOTO.setModel(obj.getIdModel());
			}
		}
		
		// lay thong tin user dang nhap
		pMOTO.setContactId(co.getContactId()); // id cua khach hang
		pMOTO.setContactCode(co.getContactCode()); // ma khach hang
		pMOTO.setContactUsername(co.getContactUsername()); // username dang nhap
		pMOTO.setInsuredName(obj.getInsuredName()); // Tên chủ xe(theo đăng ký xe):
		pMOTO.setInsuredAddress(obj.getInsuredAddress()); // Địa chỉ(theo đăng ký xe):
		
		pMOTO.setInsuredEmail(obj.getInsuredEmail());
		pMOTO.setInsuredPhone(obj.getInsuredPhone());
		
		pMOTO.setRegistrationNumber(obj.getRegistrationNumber());// Biển kiểm soát:
		pMOTO.setSokhung(obj.getSokhung());// Số khung:
		pMOTO.setSomay(obj.getSomay());// Số máy:
		pMOTO.setTypeOfMotoId(obj.getTypeOfMoto()); // Chọn nhóm loại xe: id
		pMOTO.setTypeOfMotoName("");
		
		pMOTO.setInceptionDate(DateUtils.str2Date(obj.getThoihantu()));// Thời hạn bảo hiểm: tu
		pMOTO.setExpiredDate(DateUtils.addYear(DateUtils.str2Date(obj.getThoihantu()), Integer.parseInt(obj.getGoi()),-1)); // Thời hạn bảo hiểm: den
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
