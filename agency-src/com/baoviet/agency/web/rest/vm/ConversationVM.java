package com.baoviet.agency.web.rest.vm;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.FileContentDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class ConversationVM {

	@NotEmpty
	@ApiModelProperty(value = "Số hợp đồng", required = true)
	private String gycbhNumber;
	
	@NotEmpty
	@ApiModelProperty(value = "Tiêu đề cuộc trao đổi", required = true)
	private String title;
	
    @NotEmpty
	@ApiModelProperty(value = "Email thông báo", required = true)
    @Email
    private String sendEmail;
    
    @ApiModelProperty(value = "Nội dung chi tiết trao đổi")
    private String conversationContent;
    
    @ApiModelProperty(value = "data content của file đính kèm")
    private List<FileContentDTO> imgGycbhContents;
    
    @ApiModelProperty(value = "Vai trò tạo", allowableValues = "agency,baoviet")
    private String role;
}
