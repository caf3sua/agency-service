package com.baoviet.agency.web.rest.vm;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ProductBvpVM extends ProductBaseVM {

	@NotNull
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy", required = true)
	private Date inceptionDate ;
	
	@JsonSerialize(using = DateSerializer.class)
	@ApiModelProperty(value = "Ngày kết thúc hiệu lực BH", allowableValues = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date expiredDate ;  
	
	@NotNull
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày sinh người được bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
    private Date nguoidbhNgaysinh ;

	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm", allowableValues = "1,2,3,4,5", required = true)
    private String chuongtrinhBh ;
	
	@NotEmpty
	@ApiModelProperty(value = "Tham gia quyền lợi ngoại trú 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String ngoaitru ;
	
	@ApiModelProperty(value = "Phí quyền lợi ngoại trú")
	@JsonSerialize(using = DoubleSerializer.class)
    private double ngoaitruPhi ;
	
	@ApiModelProperty(value = "Tham gia quyền lợi TNCN 0: Không, 1: Có", allowableValues = "0,1")
    private String tncn ;
	
	@ApiModelProperty(value = "Phí bảo hiểm TNCN")
	@JsonSerialize(using = DoubleSerializer.class)
    private double tncnPhi ;
	
	@JsonSerialize(using = DoubleSerializer.class)
    private double tncnPhiSi ;
	
	@ApiModelProperty(value = "Số tiền bảo hiểm TNCN")
	@JsonSerialize(using = DoubleSerializer.class)
    private double tncnSotienbh ;
	
	@ApiModelProperty(value = "Số tiền bảo hiểm SMCN")
	@JsonSerialize(using = DoubleSerializer.class)
    private double sinhmangSotienbh ;
	
    @NotEmpty
    @ApiModelProperty(value = "Tham gia quyền lợi SMCN 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String sinhmang ;
    
	@ApiModelProperty(value = "Phí bảo hiểm SMCN")
	@JsonSerialize(using = DoubleSerializer.class)
    private double sinhmangPhi ;
	
	@JsonSerialize(using = DoubleSerializer.class)
    private double sinhmangPhiSi ;
	
	@NotEmpty
	@ApiModelProperty(value = "Tham gia quyền lợi Nha khoa 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String nhakhoa;
	
	@ApiModelProperty(value = "Phí bảo hiểm quyền lợi Nha khoa")
	@JsonSerialize(using = DoubleSerializer.class)
    private double nhakhoaPhi;
	
	@NotEmpty
	@ApiModelProperty(value = "Tham gia quyền lợi Thai sản 0: Không, 1: Có",allowableValues = "0,1", required = true)
    private String thaisan;
	
	@ApiModelProperty(value = "Phí bảo hiểm quyền lợi thai sản")
	@JsonSerialize(using = DoubleSerializer.class)
    private double thaisanPhi;
	
	@JsonSerialize(using = DoubleSerializer.class)
    private double discount ;
    private Boolean hasExtracare ;

    // step 02
    @NotEmpty
	@ApiModelProperty(value = "Họ và tên người yêu cầu bảo hiểm", required = true)
    private String nguoiycName ;
	
	@NotNull
	@JsonSerialize(using = DateSerializer.class)
	@ApiModelProperty(value = "Ngày sinh người yêu cầu bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date nguoiycNgaysinh ;
	
	@NotEmpty
	@ApiModelProperty(value = "Họ tên người được bảo hiểm", required = true)
    private String nguoidbhName ;
	
	@NotEmpty
	@ApiModelProperty(value = "Số Chứng minh nhân dân/Hộ chiếu", required = true)
    private String nguoidbhCmnd ;
	
	@NotEmpty
	@ApiModelProperty(value = "Quan hệ với người Yêu cầu bảo hiểm", allowableValues = "30,31,32,33,34", required = true)
    private String nguoidbhQuanhe ;

	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 1: thông tin tình trạng sức khỏe 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String q1 ;
	
	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 2: thông tin tình trạng sức khỏe 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String q2 ;
	
	@NotEmpty
	@ApiModelProperty(value = "Trả lời câu hỏi 3: thông tin tình trạng sức khỏe 0: Không, 1: Có", allowableValues = "0,1", required = true)
    private String q3 ;

	@ApiModelProperty(value = "Có người thụ hưởng hay không")
    private Boolean hasNguoithuhuong;
	
	@ApiModelProperty(value = "Tên người thụ hưởng")
    private String nguoithName;
	
	@ApiModelProperty(value = "Số CMND của người thụ hưởng")
    private String nguoithCmnd;
	
	@ApiModelProperty(value = "Quan hệ với người thụ hưởng")
    private String nguoithQuanhe;
	
	@JsonSerialize(using = DateSerializer.class)
	@ApiModelProperty(value = "Ngày sinh người thụ hưởng", allowableValues = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date nguoithNgaysinh ;

    private Boolean hasNguoinhantien;
    
	@ApiModelProperty(value = "Họ tên người nhận tiền")
    private String nguoinhanName;
	
	@ApiModelProperty(value = "Số Chứng minh nhân dân/Hộ chiếu người nhận tiền")
    private String nguoinhanCmnd;
	
	@ApiModelProperty(value = "Quan hệ với người được bảo hiểm", allowableValues = "31,32,33")
    private String nguoinhanQuanhe ;
	
	@JsonSerialize(using = DateSerializer.class)
	@ApiModelProperty(value = "Ngày sinh người nhận tiền", allowableValues = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date nguointNgaysinh ;     
	
	// 19.06.2018 duclm nguoi nhan ngay sinh ko insert vao DB
//	@JsonSerialize(using = DateSerializer.class)
//	@ApiModelProperty(value = "Ngày sinh người nhận tiền", allowableValues = "dd/MM/yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    private Date nguoinhanNgaysinh ;

	@ApiModelProperty(value = "Số HĐ/GCN")
    private String policyNumber ;  
	
	@ApiModelProperty(value = "Số HĐBH/GCNBH/Thẻ/Mã đơn hàng Bố(Mẹ)")
    private String policyParent ;
    
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chương trình chính", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private double chuongtrinhPhi ;
	
	@ApiModelProperty(value = "Số tiền tăng giảm phí")
	@JsonSerialize(using = DoubleSerializer.class)
    private double tanggiamPhi ;
	
	@ApiModelProperty(value = "Nội dung tăng giảm phí")
    private String tanggiamPhiNoidung ;
    
	@ApiModelProperty(value = "Thông tin về tình trạng sức khỏe")
    private List<SKTDAddVM> lstAdd;
    
    // byte[] -> String encode base64 image
    private String files;
}
