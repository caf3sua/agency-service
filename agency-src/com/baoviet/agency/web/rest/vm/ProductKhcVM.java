package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.dto.TlAddDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * KhcBaseVM
 * 
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class ProductKhcVM extends ProductBaseVM {

	@NotNull
	@ApiModelProperty(value = "Số người tham gia", required = true)
	private int permanentTotalDisablement; // số người tham gia

	@ApiModelProperty(value = "Số tiền bảo hiểm", allowableValues = "2,3,4,5")
	private int plan; // số tiền bảo hiểm 2,3,4,5 * 10000000

	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu tham gia bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private String inceptionDate;

	private List<TlAddDTO> tlAddcollections;

	// More attribute
	private String userAgent;
}
