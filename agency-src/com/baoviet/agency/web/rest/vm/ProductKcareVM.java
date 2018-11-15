package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * KCARE_BASE
 * @author Nam, Nguyen Hoai
 */
@Getter
@Setter
public class ProductKcareVM extends ProductBaseVM {

	@ApiModelProperty(value = "Số HĐ/GCN - Hệ thống tự sinh và sẽ trả kết quả về khi thực hiện ghi dữ liệu thành công")
	private String policyNumber ; // get; set; }
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy", required = true)
    private String thoihantu ; // get; set; }
    
	@ApiModelProperty(value = "Ngày kết thúc hiệu lực BH", allowableValues = "dd/MM/yyyy")
	private String thoihanden ; // get; set; }
    
//	@NotEmpty
//	@ApiModelProperty(value = "Họ và tên người yêu cầu bảo hiểm", required = true)
//	private String contactName ; // get; set; }
    
//	@NotEmpty
//	@ApiModelProperty(value = "Ngày sinh người yêu cầu bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
//    private String contactDob ; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Họ tên người được bảo hiểm", required = true)
	private String insuredName ; // get; set; }
    
	@NotEmpty
	@ApiModelProperty(value = "Giới tính người được bảo hiểm (1: Nam, 2: Nữ)", allowableValues = "1,2", required = true)
	private String insuredSex ; // get; set; }
    
	@NotEmpty
	@ApiModelProperty(value = "Ngày sinh người được bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private String insuredNgaysinh ; // get; set; }
	
	@NotEmpty
	@ApiModelProperty(value = "Số Chứng minh nhân dân/Hộ chiếu người được bảo hiểm", required = true)
    private String insuredIdNumber ; // get; set; }
    
	// "30: Bản thân,	31: Vợ/Chồng,	32: Con,	33: Bố/Mẹ,	34: Bố mẹ của vợ/chồng"
	@NotEmpty
	@ApiModelProperty(value = "Quan hệ với người Yêu cầu bảo hiểm", allowableValues = "30,31,32,33,34", required = true)
	private String insuredRelationship ; // get; set; } //quan he ndbh map voi truong note
    
	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm", allowableValues = "PGM1,PGM2,PGM3", required = true)
	private String planId ; // get; set; }
    
	// "0: Không,	1: Có "
	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 1: thông tin tình trạng sức khỏe", allowableValues = "0,1", required = true)
	private String q1 ; // get; set; }
    
	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 2: thông tin tình trạng sức khỏe", allowableValues = "0,1", required = true)
	private String q2 ; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 3: thông tin tình trạng sức khỏe", allowableValues = "0,1", required = true)
	private String q3 ; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 4: thông tin tình trạng sức khỏe", allowableValues = "0,1", required = true)
	private String q4 ; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 5: thông tin tình trạng sức khỏe", allowableValues = "0,1", required = true)
	private String q5 ; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Trả lời chi tiết cho câu hỏi 3: thông tin tình trạng sức khỏe - Mệt mỏi", allowableValues = "0,1", required = true)
	private String qtreatment ; // get; set; } //met moi
	
	@NotEmpty
	@ApiModelProperty(value = "Trả lời chi tiết cho câu hỏi 3: thông tin tình trạng sức khỏe - Giảm cân không rõ nguyên nhân", allowableValues = "0,1", required = true)
    private String qresultTre ; // get; set; } //giam can
	
	@NotEmpty
	@ApiModelProperty(value = "Trả lời chi tiết cho câu hỏi 3: thông tin tình trạng sức khỏe - Nổi hạch lớn", allowableValues = "0,1", required = true)
    private String qtypeCancer ; // get; set; } //noi hach
	
	@NotEmpty
	@ApiModelProperty(value = "Trả lời chi tiết cho câu hỏi 3: thông tin tình trạng sức khỏe - Xuất hiện mảng da không giống với khu vực da xung quanh", allowableValues = "0,1", required = true)
    private String qresultCan ; // get; set; } //xuan hien mang da
	
	@ApiModelProperty(value = "Họ tên người thụ hưởng")
    private String beneficiaryName ; // get; set; }
	
	@ApiModelProperty(value = "Ngày sinh người thụ hưởng", allowableValues = "dd/MM/yyyy")
    private String beneficiaryNgaysinh ; // get; set; }
	
	@ApiModelProperty(value = "Số CMND/Hộ chiếu/Số giấy khai sinh người thụ hưởng")
    private String beneficiaryIdNumber ; // get; set; }
	
	@ApiModelProperty(value = "Quan hệ với người được bảo hiểm", allowableValues = "31,32,33")
    private String beneficiaryRelationship ; // get; set; }
	
	@ApiModelProperty(value = "Họ tên người nhận tiền")
    private String beneficiaryNameD ; // get; set; }
	
	@ApiModelProperty(value = "Ngày sinh người nhận tiền", allowableValues = "dd/MM/yyyy")
    private String beneficiaryNgaysinhD ; // get; set; }
	
	@ApiModelProperty(value = "Số Chứng minh nhân dân/Hộ chiếu người nhận tiền")
    private String beneficiaryIdNumberD ; // get; set; }
    
	@ApiModelProperty(value = "Quan hệ với người được bảo hiểm", allowableValues = "31,32,33")
	private String beneficiaryRelationshipD ; // get; set; }
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm / năm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double netPremium; // get; set; }
    
	@ApiModelProperty(value = "Tăng giảm phí")
	@JsonSerialize(using = DoubleSerializer.class)
	private double changePremium ; // get; set; }
	
	@NotNull
	@ApiModelProperty(value = "Tổng tiền thanh toán", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double totalPremium ; // get; set; }
    
    @ApiModelProperty(value = "danh sách câu trả lời về tình trạng sức khỏe tham chiếu")
    private List<TinhtrangSkDTO> lstTinhtrangSKs;
}
