package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.ReceiverUserInfoDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ProductBaseVM {

	@NotNull
	@ApiModelProperty(value = "Thông tin người nhận đơn bảo hiểm", required = true)
   	private ReceiverUserInfoDTO receiverUser;
    
	@ApiModelProperty(value = "Thông tin hóa đơn GTGT")
   	private InvoiceInfoDTO invoiceInfo;
	
	// --- For update
	@ApiModelProperty(value = "ID của hợp đồng - dùng trong trường hợp cập nhật")
    private String agreementId;
	
	@ApiModelProperty(value = "Giấy yêu cầu bảo hiểm - dùng trong trường hợp cập nhật")
	private String gycbhId;
	
	// --- For update
	@NotEmpty
	@ApiModelProperty(value = "Mã khách hàng do đối tác truyền vào", required = true)
    private String contactCode;
	
	@ApiModelProperty(value = "Tên khách hàng - dùng trong trường hợp cập nhật")
	private String contactName;
	
	@ApiModelProperty(value = "Ngày sinh khách hàng - dùng trong trường hợp cập nhật")
	private String contactDob;
    
	@ApiModelProperty(value = "Nhóm khách hàng - dùng trong trường hợp cập nhật")
	private String contactCategoryType;
	
	@ApiModelProperty(value = "Địa chỉ khách hàng - dùng trong trường hợp cập nhật")
	private String contactAddress;
	
	@ApiModelProperty(value = "Phương thức nhận đơn bảo hiểm(1: nhận bản mềm, 2: nhận bản cứng)", allowableValues = "1,2")
    private String receiveMethod;

	@NotEmpty
	@ApiModelProperty(value = "Số GYCBH lấy từ API GetPolicyNumber", required = true)
    private String gycbhNumber;
	
	@ApiModelProperty(value = "Số GYCBH cũ trong trường hợp tái tục")
    private String oldGycbhNumber;
	
	@ApiModelProperty(value = "Mã sản phẩm bảo hiểm")
	private String lineId;
	
	@ApiModelProperty(value = "Trạng thái đơn hàng: 80-Đang soạn; 81-Chờ OTP; 83-Yêu cầu bổ sung thông tin; 89-Đại lý, Khách hàng hủy đơn; 90-Chờ thanh toán; 91-Chờ BV cấp đơn; 92-Chờ Bảo Việt cấp GCNBH (bản cứng); 93-Chờ BV giám định; 99-Bảo Việt từ chối; 100-Hoàn thành", allowableValues = "90,91,92,93,99,100,80,81,83,89")
	private String statusPolicy;
    
	@ApiModelProperty(value = "Id phòng ban")
    private String departmentId;
}
