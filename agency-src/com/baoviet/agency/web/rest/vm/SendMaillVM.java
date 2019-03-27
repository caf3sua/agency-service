package com.baoviet.agency.web.rest.vm;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeBase
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class SendMaillVM {

	@ApiModelProperty(value = "Từ")
	private String from;
	
	@ApiModelProperty(value = "Đến")
	private String to;
	
	@ApiModelProperty(value = "C/c")
	private List<String> cc;
	
	@ApiModelProperty(value = "Chủ đề")
	private String subject;
	
	@ApiModelProperty(value = "Nội dung")
	private String content;
	
	@ApiModelProperty(value = "Kết quả gửi mail")
	private boolean result; 
}
