package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.FileContentDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AgreementOffline
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class AgreementYcbhOfflineVM {

	@NotEmpty
	@ApiModelProperty(value = "Mã sản phẩm", required = true)
	private String maSanPham;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã khách hàng do đối tác truyền vào", required = true)
    private String contactCode;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm", required = true)
	private long totalPremium;
	
	@NotNull
	@ApiModelProperty(value = "data content của gycbh", required = true)
    private List<FileContentDTO> imgGycbhContents;
	
	@NotNull
	@ApiModelProperty(value = "data content của giấy khai sinh", required = true)
    private List<FileContentDTO> imgKhaisinhContents;
	
	@ApiModelProperty(value = "data content của tài liệu khác")
    private List<FileContentDTO> imgDocumentContents;	
	
	@ApiModelProperty(value = "Trạng thái đơn hàng: 80-Đang soạn; 93-Chờ BV giám định", allowableValues = "93,80")
	private String statusPolicy;
	
	private String gycbhNumber;
	
	private String agreementId;
	
	@ApiModelProperty(value = "Id phòng ban")
	private String departmentId;
	
	@ApiModelProperty(value = "Ngày hiệu lực từ", allowableValues = "dd/MM/yyyy")
    private String ngayHieulucTu;
	
	@ApiModelProperty(value = "Ngày hiệu lực đến", allowableValues = "dd/MM/yyyy")
    private String ngayHieulucDen;
}
