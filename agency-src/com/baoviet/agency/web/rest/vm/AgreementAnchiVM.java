package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.FileContentDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AgreementAnchiVM
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class AgreementAnchiVM {

	@NotEmpty
	@ApiModelProperty(value = "Số ấn chỉ", required = true)
    private String soAnchi;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã sản phẩm", required = true)
	private String maSanPham;
	
	@ApiModelProperty(value = "Tên ấn chỉ")
	private String tenAnchi;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã khách hàng do đối tác truyền vào", required = true)
	private String contactCode;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày hiệu lực từ", allowableValues = "dd/MM/yyyy", required = true)
    private String ngayHieulucTu;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày hiệu lực đến", allowableValues = "dd/MM/yyyy", required = true)
    private String ngayHieulucDen;
	
	@NotEmpty
	@ApiModelProperty(value = "Tình trạng cấp TTCM: Mới, TTCL: Tái tục", allowableValues = "TTCM,TTCL", required = true)
	private String tinhTrangCap;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày cấp", allowableValues = "dd/MM/yyyy", required = true)
    private String ngayCap;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm", required = true)
	private double phiBaoHiem;
	
	@NotNull
	@ApiModelProperty(value = "Tổng tiền thanh toán", required = true)
	private double tongTienTT;
	
	@NotNull
	@ApiModelProperty(value = "data content của Giấy chứng nhận (ảnh ấn chỉ)", required = true)
    private List<FileContentDTO> imgGcns;
	
	@ApiModelProperty(value = "data content của Giấy tờ liên quan")
    private List<FileContentDTO> imgGycbhs;
	
//	@ApiModelProperty(value = "data content của Hóa đơn")
//    private FileContentDTO imgHd;
	
	private String gycbhNumber;
	
	private String agreementId;
	
	@ApiModelProperty(value = "Id ấn chỉ, dùng cho trường hợp update")
	private String anchiId;
	
	@ApiModelProperty(value = "Id phòng ban")
	private String departmentId;
}
