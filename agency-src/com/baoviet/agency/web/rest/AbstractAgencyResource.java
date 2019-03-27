package com.baoviet.agency.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.impl.AbstractProductService;
import com.baoviet.agency.web.rest.vm.ProductBaseVM;

/**
 * REST controller for Agency KCARE resource.
 * @author Nam, Nguyen Hoai
 */
public abstract class AbstractAgencyResource extends AbstractProductService{
	@Autowired
    private AgencyService agencyService;
	
	@Autowired
    private AdminUserService adminUserService;
	
	
	/* Kcare */
	public static final String KCARE_BENEFIT_NOTES = "Hàm lấy danh sách lợi ích của bảo hiểm ung thu KCARE.";
	public static final String KCARE_BENEFIT_VALUE = "Hàm lấy danh sách lợi ích của bảo hiểm ung thu KCARE.";

	public static final String KCARE_PREMIUM_NOTES = "Hàm tính phí Bảo hiểm ung thu KCARE.";
	public static final String KCARE_PREMIUM_VALUE = "Hàm tính phí Bảo hiểm ung thu KCARE.";
	
	public static final String KCARE_CREATE_POLICY_VALUE = "Tạo yêu cầu Bảo hiểm ung thu KCARE.";
	public static final String KCARE_CREATE_POLICY_NOTES = "- Tuổi người được bảo hiểm được tính theo: ngày sinh người được bảo hiểm và thời hạn bảo hiểm từ</br>" + 
			"  + Độ tuổi tham gia bảo hiểm chỉ từ: 16 đến 55 tuổi</br>" + 
			"- Nếu trả lời có trong câu hỏi 4 về thông tin tình trạng sức khỏe là có thì phải cung cấp thông tin về tình trạng sức khỏe( tham chiếu thông tin về TINHTRANG_SK_BASE)</br>" + 
			"- Nếu nhập 1 trong các thông tin về người thụ hưởng bảo hiểm thì phải nhập đầy đủ tất cả các thông tin về người thụ hưởng: tên người thụ hưởng, quan hệ, CMND/Hộ Chiếu, ngày sinh người thụ hưởng.</br>" + 
			"- Nếu nhập 1 trong các thông tin về người nhận bảo hiểm thì phải nhập đầy đủ tất cả các thông tin về người thụ hưởng: tên người nhận tiền, quan hệ, CMND/Hộ Chiếu, ngày sinh người nhận tiền.</br>" + 
			"- Người nhận tiền phải >= 18 tuổi</br>" + 
			"- Nếu khách hàng có lấy hóa đơn GTGT(INVOICE_CHECK = 1) thì cần phải bổ sung thông tin viết hóa đơn GTGT</br>" + 
			"  + Đối với cá nhân: Họ tên người mua, địa chỉ</br>" + 
			"  + Đối với công ty: Tên đơn vị, Mã số thuế, địa chỉ</br>";
	
	public static final String KCARE_UPDATE_POLICY_NOTES = "Cập nhật yêu cầu Bảo hiểm ung thu KCARE.";
	public static final String KCARE_UPDATE_POLICY_VALUE = "Cập nhật yêu cầu Bảo hiểm ung thu KCARE.";
	
	public AgencyDTO getCurrentAccount() throws AgencyBusinessException {
    	final String userLogin = SecurityUtils.getCurrentUserLogin();
    	AgencyDTO existingUser = null;
    	if (SecurityUtils.isCurrentUserInRole(AgencyConstants.ROLE_BAOVIET)) {
    		existingUser = adminUserService.findByEmail(userLogin);
    	} else {
    		existingUser = agencyService.findByEmail(userLogin);
    	}
         
        if (existingUser == null) {
        	throw new AgencyBusinessException("agency", ErrorCode.AGENCY_NOT_FOUND, "Không tìm thấy thông tin Agency hiện tại");
        }
        
        return existingUser;
    }
	
	public <T extends ProductBaseVM> void validateUpdateProduct(T param) throws AgencyBusinessException {
		// agreementId + gycbhId
		if (StringUtils.isEmpty(param.getAgreementId()) || StringUtils.isEmpty(param.getGycbhId())) {
			throw new AgencyBusinessException("error", ErrorCode.NULL_OR_EMPTY, "Bắt buộc nhập AgreementId và GycbhId");
		}
	}
}
