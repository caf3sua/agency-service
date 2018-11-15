package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AgreementNoPhiVM
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class AgreementNoPhiVM {

	@NotEmpty
	@ApiModelProperty(value = "Mã hợp đồng", required = true)
    private String agreementId;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã khách hàng do đối tác truyền vào", required = true)
	private String contactId;
	
	@ApiModelProperty(value = "Ghi chú")
	private String note;
	
	@NotNull
	@ApiModelProperty(value = "Tổng tiền nợ", required = true)
	private double sotien;
	
	@ApiModelProperty(value = "Kết quả tạo đơn hàng")
	private boolean result;
	
	@ApiModelProperty(value = "Id đơn hàng - Dùng cho trường hợp update")
	private String id;
}
