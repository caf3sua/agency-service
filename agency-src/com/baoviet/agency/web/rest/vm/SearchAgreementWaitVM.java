package com.baoviet.agency.web.rest.vm;

import java.util.Date;

import org.hibernate.validator.constraints.Email;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.web.rest.vm.common.PageableVM;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class SearchAgreementWaitVM {

	private PageableVM pageable;
	
	private String gycbhNumber;
	
    private String contactName;
	
    private String phone;
    
    @Email
    private String email;
    
    private String productCode;
    
    private String statusPolicy;
    
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy")
    private Date fromDate;
    
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày kết thúc hiệu lực BH", allowableValues = "dd/MM/yyyy")
    private Date toDate;
	
	private String createType;
	
	@ApiModelProperty(value = "Id đại lý")
	private String agentId;
	
	@ApiModelProperty(value = "Id phòng ban")
	private String departmentId;
	
	@ApiModelProperty(value = "Id công ty")
	private String companyId;
	
	@ApiModelProperty(value = "Ngày nhập", allowableValues = "dd/MM/yyyy")
    private String createDate;
}
