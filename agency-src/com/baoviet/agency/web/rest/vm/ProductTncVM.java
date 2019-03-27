package com.baoviet.agency.web.rest.vm;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.bean.TncAddDTO;
import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTncVM extends ProductBaseVM {

	@NotNull
	@ApiModelProperty(value = "Số người tham gia bảo hiểm.", required = true)
	private Integer numberperson; // get; set; } // p_songuoithamgia
	
	@NotNull
	@ApiModelProperty(value = "Số tháng tham gia bảo hiểm.", required = true)
    private Integer numbermonth; // get; set; } // so thang tham gia: default = 12
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@ApiModelProperty(value = "Ngày bắt đầu tham gia bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private Date insurancestartdate; // get; set; }
	
	@JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
	@ApiModelProperty(value = "Ngày kết thúc", allowableValues = "dd/MM/yyyy", required = true)
    private Date insuranceexpireddate; // get; set; }
    
    @NotNull
	@ApiModelProperty(value = "Số tiền đóng bảo hiểm", allowableValues = "20000000,30000000,40000000,50000000,60000000,70000000,80000000,90000000,100000000", required = true)
    private long premiumPackage; // get; set; } // so tien
    
    @NotNull
	@ApiModelProperty(value = "Mã gói tham gia bảo hiểm", allowableValues = "2,3,4,5,6,7,8,9,10", required = true)
    private Long premiumPackageplanid; // get; set; } // so tien goi nao
    
    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumnet; // get; set; }
    
    @NotNull
	@ApiModelProperty(value = "Phần trăm giảm phí", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumdiscount; // get; set; }
    
    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumtnc; // get; set; }

    // step 02. danh sách người được bảo hiểm
    @ApiModelProperty(value = "Danh sách người được bảo hiểm")
    private List<TncAddDTO> listTncAdd; // get; set; }
}
