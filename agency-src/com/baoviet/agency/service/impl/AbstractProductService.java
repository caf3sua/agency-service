package com.baoviet.agency.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.MvAgentAgreement;
import com.baoviet.agency.domain.MvClaOutletLocation;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.SmsSendDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.MvAgentAgreementRepository;
import com.baoviet.agency.repository.MvClaOutletLocationRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.PayActionService;
import com.baoviet.agency.service.SmsSendService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ProductBaseVM;

/**
 * Service Implementation for managing Kcare.
 * 
 * @author Nam, Nguyen Hoai
 */
public class AbstractProductService {

	@Autowired
	protected AgreementService agreementService;
	
	@Autowired
	private PayActionService payActionService;
	
	@Autowired
	private CodeManagementService codeManagementService;
	
	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;
	
	@Autowired
	private MvAgentAgreementRepository mvAgentAgreementRepository;
	
	private final Logger log = LoggerFactory.getLogger(AbstractProductService.class);
	
	protected void validateInvoiceInfo(InvoiceInfoDTO invoiceInfo) throws AgencyBusinessException {
		log.debug("Request to validateInvoiceInfo : invoiceInfo {}", invoiceInfo);
		if (invoiceInfo != null && StringUtils.equals(invoiceInfo.getCheck(), "1")) {
			if (StringUtils.isEmpty(invoiceInfo.getName()))
				throw new AgencyBusinessException("invoiceInfo.name", ErrorCode.NULL_OR_EMPTY,
						"invoiceBuyer (họ tên người mua) not null");

			if (StringUtils.isEmpty(invoiceInfo.getCompany()))
				throw new AgencyBusinessException("invoiceInfo.company", ErrorCode.NULL_OR_EMPTY,
						"invoiceCompany (tên đơn vị) not null");

			if (StringUtils.isEmpty(invoiceInfo.getTaxNo()))
				throw new AgencyBusinessException("invoiceInfo.taxNo", ErrorCode.NULL_OR_EMPTY,
						"invoiceTaxNo (mã số thuế) not null");

			if (StringUtils.isEmpty(invoiceInfo.getAddress()))
				throw new AgencyBusinessException("invoiceInfo.address", ErrorCode.NULL_OR_EMPTY, "invoiceAddress not null");

//			if (StringUtils.isEmpty(invoiceInfo.getAccountNo()))
//				throw new AgencyBusinessException("invoiceInfo.accountNo", ErrorCode.NULL_OR_EMPTY,
//						"invoiceAccountNo (số tài khoản) not null");
		} else {
			if (invoiceInfo == null) {
				invoiceInfo = new InvoiceInfoDTO();
			}
			invoiceInfo.setCheck("0");
		}

	}
	
	protected void validateGycbhNumber(String gycbhNumber, String type) throws AgencyBusinessException {
		log.debug("Request to validateGycbhNumber : gycbhNumber {}, type {}", gycbhNumber , type);
		// Validate exist
		AgreementDTO aDto = agreementService.findByGycbhNumberAndAgentId(gycbhNumber, type);
		if (aDto != null) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.GYCBH_EXIST, "gycbhNumber đã tồn tại: " + gycbhNumber);
		}
	}
	
	protected void sendSmsAndSavePayActionInfo(Contact contact, AgreementDTO agreement, String type) throws AgencyBusinessException {
		PayAction payAction = setPayAction(contact, agreement);
	    
		String payActionId = payActionService.save(payAction);
		
		// update Contact loại khách hàng. Nếu là tiềm năng khi có đơn hàng sẽ là thân thiết
		if (contact.getGroupType().equals("POTENTIAL")) {
			contact.setGroupType("FAMILIAR");
			// save
			contactRepository.save(contact);
		}
		
		// gui sms
		if (!StringUtils.isEmpty(agreement.getOtp())) {
			String content = "";
			if (type.equals("0")) {
				content = "Ma xac nhan YCBH <" + agreement.getGycbhNumber() + "> la: " + agreement.getOtp();	
			} else {
				content = "Ma xac nhan sua doi YCBH <" + agreement.getGycbhNumber() + "> la: " + agreement.getOtp();	
			}
			content += ". Bang viec cung cap ma xac nhan, Quy khach da dong y va chap nhan Quy tac Bao Hiem. Chi tiet xem tai email";
			String phonenumber = StringUtils.EMPTY;
			if (!StringUtils.isEmpty(contact.getPhone())) {
				phonenumber = contact.getPhone();
			} else {
				phonenumber = agreement.getReceiverMoible();
			}
			
			// save SMS
			smsSendService.sendSMS(agreement, contact, phonenumber, content);
			
			// gửi sms xong thì bật gửi mail cờ gửi mail cancel_policy_support3 = 1
			agreement.setCancelPolicySupport3(1.0);
			agreement.setSendEmail(1);
	     	agreementService.save(agreement);
		}
		
		log.debug("Request to savePayActionInfo : payActionId {}", payActionId);
	}
	
	protected void sendSms(Contact contact, AgreementDTO agreement) throws AgencyBusinessException {
		// gui sms
		if (!StringUtils.isEmpty(agreement.getOtp())) {
			String content = "Ma xac nhan YCBH " + agreement.getGycbhNumber() + " la: " + agreement.getOtp();
			content += ". Bang viec cung cap ma xac nhan, Quy khach da dong y va chap nhan Quy tac Bao Hiem. Chi tiet xem tai email";
			String phonenumber = StringUtils.EMPTY;
			if (!StringUtils.isEmpty(contact.getPhone())) {
				phonenumber = contact.getPhone();
			} else {
				phonenumber = agreement.getReceiverMoible();
			}
			
			// save SMS
			smsSendService.sendSMS(agreement, contact, phonenumber, content);	
		}
	}
	
	protected void insertAgreementCommonInfo(String lineId, AgreementDTO voAg, Contact contact, AgencyDTO currentAgency, ProductBaseVM obj) throws AgencyBusinessException{
		log.debug("Request to insertAgreementCommonInfo : lineId {}, AgreementDTO {}, Contact {}, AgencyDTO {}, ProductBaseVM {}", lineId, voAg, contact, currentAgency, obj);
		// Insert policy status
		setPolicyStatus(lineId, voAg);
		
		// TH nút đang soạn
		if (StringUtils.equals(obj.getStatusPolicy(), AppConstants.STATUS_POLICY_ID_DANGSOAN)) {
			voAg.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_DANGSOAN);
			voAg.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_DANGSOAN);
			voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_DANGSOAN);
			voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_DANGSOAN);
		} else {
			if (currentAgency.getSendOtp() != null && currentAgency.getSendOtp() > 0) {
				// tạo otp mới
				String otp = AgencyUtils.getRandomOTP();
				voAg.setOtp(otp);
				voAg.setOtpStatus(AppConstants.STATUS_OTP_0); // 0: timeout
				voAg.setOtpStartTime(DateUtils.date2Str(new Date()));
				
				voAg.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_OTP);
				voAg.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_OTP);
				voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_OTP);
				voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_OTP);
			} else {
				voAg.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				voAg.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
				voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);	
			}
		}
				
		// TH update thì không set GycbhNumber
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			voAg.setGycbhNumber(obj.getGycbhNumber());
			voAg.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				voAg.setGycbhNumber(data.getGycbhNumber());
				voAg.setPolicyNumber(data.getGycbhNumber());
				voAg.setAgreementId(obj.getAgreementId());
			}
		}
		// Insert invoiceInfo & receiver
		voAg.setReceiverName(obj.getReceiverUser().getName());
		voAg.setReceiverAddress(obj.getReceiverUser().getAddress());
		voAg.setReceiverEmail(obj.getReceiverUser().getEmail());
		voAg.setReceiverMoible(obj.getReceiverUser().getMobile());

		if (obj.getInvoiceInfo() != null) {
			voAg.setInvoiceBuyer(obj.getInvoiceInfo().getName());
			voAg.setInvoiceCompany(obj.getInvoiceInfo().getCompany());
			voAg.setInvoiceTaxNo(obj.getInvoiceInfo().getTaxNo());
			voAg.setInvoiceAddress(obj.getInvoiceInfo().getAddress());
			voAg.setInvoiceAccountNo(obj.getInvoiceInfo().getAccountNo());	
		}
		
		// Insert contact info
		voAg.setContactId(contact.getContactId());
		voAg.setContactName(contact.getContactName());
		voAg.setTaxIdNumber(contact.getIdNumber());
		voAg.setContactAddress(contact.getHomeAddress());
		if (!StringUtils.isEmpty(contact.getPhone())) {
			voAg.setContactPhone(contact.getPhone());	
		} else {
			voAg.setContactPhone(contact.getHandPhone());
		}
		
		if (contact.getDateOfBirth() != null) {
			voAg.setContactDob(contact.getDateOfBirth());	
		} else {
			voAg.setContactDob(DateUtils.str2Date("01/01/0001"));
		}
		voAg.setContactUsername(contact.getEmail());
		voAg.setContactAddresstt(contact.getHomeAddress());
		
		// Insert extra
		voAg.setOldGycbhNumber(obj.getOldGycbhNumber());
		voAg.setOldPolicyNumber(obj.getOldGycbhNumber());
		voAg.setReceiveMethod(obj.getReceiveMethod());
		voAg.setAgentId(currentAgency.getMa());
		voAg.setAgentName(currentAgency.getTen());// agency.TEN;
		
		voAg.setAgencyId("");
		voAg.setAgencyName("");
		voAg.setCommision(0.0);
		voAg.setCommisionSupport(0.0);
		voAg.setCancelPolicyPremium(0.0);
		voAg.setCancelPolicyCommision(0.0);
		voAg.setCancelPolicySupport(0.0);
		voAg.setCancelPolicyPremium2(0.0);
		voAg.setCancelPolicyCommision2(0.0);
		voAg.setCancelPolicySupport2(0.0);
		voAg.setCancelPolicyPremium3(0.0);
		voAg.setCancelPolicyCommision3(0.0);
		voAg.setCancelPolicySupport3(0.0);
		voAg.setSendEmail(0);
		voAg.setSendSms(0);
		voAg.setCouponsCode("");
		voAg.setCouponsValue(0.0);
		voAg.setFeeReceive(0.0);
		voAg.setRenewalsReason("");
        voAg.setRenewalsRate(0.0);
        voAg.setRenewalsPremium(0.0);
        voAg.setRenewalsChoice("");
        voAg.setRenewalsSi(0.0);
        voAg.setRenewalsRate1(0.0);
        voAg.setRenewalsRate2(0.0);
        voAg.setRenewalsPremium1(0.0);
        voAg.setRenewalsPremium2(0.0);
        voAg.setRenewalsDes2("");
        voAg.setRenewalsDes1("");
        voAg.setClaimRate(0.0);
		voAg.setClaimRate1(0.0);
		voAg.setClaimRate2(0.0);
		voAg.setStatusRenewalsId("");
        voAg.setStatusRenewalsName("");
        voAg.setStatusRenewalsId1("");
        voAg.setStatusRenewalsName1("");
        voAg.setStatusRenewalsId2("");
        voAg.setStatusRenewalsName2("");
		voAg.setOldPolicyStatusId("");
		voAg.setOldPolicyStatusName("");
		voAg.setBaovietCompanyId("");
		voAg.setBaovietCompanyName("");
		
		// phong ban
		if (StringUtils.isEmpty(obj.getDepartmentId())) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Cần lựa chọn phòng ban");
		}
		
		List<MvClaOutletLocation> lstmvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsIdAndPrOutletAmsId(currentAgency.getMa(), obj.getDepartmentId());
		List<MvAgentAgreement> listMvAgentAgreement = mvAgentAgreementRepository.findByAgentCodeAndDepartmentCode(currentAgency.getMa(), obj.getDepartmentId());
		if(lstmvClaOutletLocation == null && listMvAgentAgreement == null) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Không tồn tại Id phòng ban: " + obj.getDepartmentId());
		} else {
			if (lstmvClaOutletLocation != null && lstmvClaOutletLocation.size() > 0) {
				voAg.setUrnDaily(lstmvClaOutletLocation.get(0).getUrn());
				voAg.setBaovietDepartmentId(obj.getDepartmentId());
				voAg.setBaovietDepartmentName(lstmvClaOutletLocation.get(0).getPrOutletName());
			} else if (listMvAgentAgreement != null && listMvAgentAgreement.size() > 0) {
				voAg.setUrnDaily(listMvAgentAgreement.get(0).getAgentUrn());
				voAg.setBaovietDepartmentId(obj.getDepartmentId());
				voAg.setBaovietDepartmentName(listMvAgentAgreement.get(0).getDepartmentName());	
			} else {
				voAg.setUrnDaily("");
				voAg.setBaovietDepartmentId("");
				voAg.setBaovietDepartmentName("");
			}
		}
		
		List<MvClaOutletLocation> lstOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(obj.getDepartmentId());
		if (lstOutletLocation != null && lstOutletLocation.size() > 0) {
			voAg.setUrnDepartmentId(lstOutletLocation.get(0).getUrn());
			voAg.setBaovietCompanyId(lstOutletLocation.get(0).getPrOutletAmsId());
			voAg.setBaovietCompanyName(lstOutletLocation.get(0).getPrOutletName());
		} else {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Không tồn tại Id phòng ban: " + obj.getDepartmentId());
		}
		
		voAg.setTeamId("");
		voAg.setTeamName("");
		voAg.setBankId("");
		voAg.setBankName("");
		voAg.setUserId("");
		voAg.setUserName("");
		voAg.setOldGycbhId("");
		
		String mciAddId = codeManagementService.getIssueNumber("PAY", "PAY");
		voAg.setMciAddId(mciAddId);
		voAg.setIsPolicy(0);
		voAg.setReasonCancel("");
		Date dateNow = new Date();
		voAg.setSendDate(dateNow);
		voAg.setResponseDate(dateNow);
        voAg.setAgreementSysdate(dateNow);
        voAg.setCancelPolicyDate(dateNow);
        voAg.setDateOfRequirement(dateNow);
        voAg.setDateOfPayment(dateNow);
        voAg.setTypeOfPrint("");
		
		// LOẠI THANH TOÁN
		voAg.setPaymentMethod("");
		if (lineId.equals("CAR") && currentAgency.getMa().equals("T000080696")) {
			voAg.setPaymentGateway("MOMO");
		}
		voAg.setCreateType(0); 	// phân biệt 0: onl, 1: off, 2: anchi
		
		log.debug("Request to insertAgreementCommonInfo : ObjAgrement {}", voAg);
	}
	
	
//	protected void insertSmsSend(AgreementDTO voAg, Contact contact, String content, Integer status) {
//		log.debug("Request to insertSmsSend : AgreementDTO {}, Contact {}", voAg, contact);
//		
//		SmsSendDTO smsSend = new SmsSendDTO();
//		smsSend.setContent(content);
//		if (!StringUtils.isEmpty(contact.getPhone())) {
//			smsSend.setPhoneNumber(contact.getPhone());	
//		} else {
//			smsSend.setPhoneNumber(voAg.getReceiverMoible());
//		}
//		smsSend.setNumberSuccess(status);
//		smsSend.setNumberFails(0);
//		smsSend.setUserId("AGREEMENT");                        
//		smsSend.setUserName("AGREEMENT");                        
//		smsSend.setFullname("AGREEMENT");
//		smsSend.setSmsSysdate(new Date());
//		
//		smsSendService.save(smsSend);
//	}
	
    //************** Private method *****************//
	private void setPolicyStatus(String lineId, AgreementDTO voAg) {
		switch (lineId) {
			case "BVP":
				voAg.setLineName("Bảo Việt An Gia");
				break;
			case "CAR":
				voAg.setLineName("Bảo hiểm ô tô");
				break;
			case "HHV":
				voAg.setLineName("Bảo hiểm Hàng hóa vận chuyện nội địa");
				break;
			case "HOM":
				voAg.setLineName("Bảo hiểm nhà tư nhân");
				break;
			case "KCR":
				voAg.setLineName("Bảo hiểm bệnh ung thư");
				break;
			case "KHC":
				voAg.setLineName("Bảo hiểm kết hợp con người");
				break;
			case "MOT":
				voAg.setLineName("Bảo hiểm xe máy");
				break;
			case "TNC":
				voAg.setLineName("Bảo hiểm tai nạn con người");
				break;
			case "TVI":
				voAg.setLineName("Bảo hiểm du lịch Việt Nam");
				break;
			case "TVC":
				voAg.setLineName("Bảo hiểm du lịch Flexi");
				break;
			default:
				break;
		}
		
		voAg.setLineId(lineId);
	}
	
    private PayAction setPayAction(Contact contact, AgreementDTO agreement)
    {
    	PayAction pAction = new PayAction();
    	pAction.setToPhone(contact.getPhone());
        pAction.setToSendmail(contact.getEmail());
        pAction.setMciAddId(agreement.getMciAddId());
        pAction.setTransactionId(!StringUtils.isEmpty(agreement.getPaymentTransactionId()) ? agreement.getPaymentTransactionId() : "");
        
        if (!StringUtils.isEmpty(agreement.getStatusPolicyId()) && agreement.getStatusPolicyId().equals("91")) {
        	pAction.setStatus(91);
        } else {
        	pAction.setStatus(90);
        }
            
        pAction.setBankcode("");
        pAction.setPaymentGateway("");
        pAction.setNetAmount(agreement.getNetPremium());
        pAction.setRefundAmount(0);
        pAction.setAmount(agreement.getTotalPremium());
        Date dateNow = new Date();
        pAction.setTransactionDate(dateNow);
        if(agreement.getChangePremium() != null) {
        	pAction.setDiscountAmount(agreement.getChangePremium());	
        } else {
        	pAction.setDiscountAmount(0d);	
        }
        pAction.setPayStartDate(dateNow);
        pAction.setPayEndDate(dateNow);
        pAction.setPolicyNumbers(agreement.getGycbhNumber());
        pAction.setPayLog("");
        pAction.setStatusEmailFrom(0);
        pAction.setStatusEmailTo(0);
        pAction.setPaymentDiscount(0);
        pAction.setPaymentRefund(0);
        pAction.setStatusPaymentRefund(0);
        pAction.setNumCard("");
        pAction.setBankName("");
        pAction.setCardName("");
        pAction.setAddress("");
        pAction.setStatusCard(0);
        pAction.setCardUpdateDate(dateNow);
        pAction.setStatusEmailPayFrom(0);
        return pAction;
    }
}
 