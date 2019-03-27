package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumKhcVM {

	@NotEmpty
	@ApiModelProperty(value = "Bảo hiểm từ ngày", allowableValues = "dd/MM/yyyy", required = true)
	private String insuranceStartDate;

    // p_songuoithamgia
	@NotNull
	@ApiModelProperty(value = "Số người tham gia", required = true)
    private int numberPerson;

    //p_sothangbaohiem
	@NotNull
	@ApiModelProperty(value = "Số tháng bảo hiểm", required = true)
    private int numberMonth;

    private List<PremiumKhcPersonVM> premiumKhcList;

    //p_sotienbaohiem
    @NotNull
	@ApiModelProperty(value = "Số tiền bảo hiểm", allowableValues = "20000000,30000000,40000000,50000000", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumPackage;

    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumNet;

    @NotNull
	@ApiModelProperty(value = "Phần trăm giảm phí", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumDiscount;

    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumKhc;

	@Override
	public String toString() {
		return "PremiumKhcVM [insuranceStartDate=" + insuranceStartDate + ", numberPerson=" + numberPerson
				+ ", numberMonth=" + numberMonth + ", premiumKhcList=" + premiumKhcList + ", premiumPackage="
				+ premiumPackage + ", premiumNet=" + premiumNet + ", premiumDiscount=" + premiumDiscount
				+ ", premiumKhc=" + premiumKhc + "]";
	}
    
}