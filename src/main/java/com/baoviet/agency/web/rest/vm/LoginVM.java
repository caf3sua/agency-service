package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.baoviet.agency.config.Constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class LoginVM {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = ManagedUserVM.PASSWORD_MIN_LENGTH, max = ManagedUserVM.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;
    
//    @ApiModelProperty(value="current time format ddMMyyyyHHMM", allowableValues = "080320181345")
//	private String psendAt;
//    
//	@ApiModelProperty(value="mobile - Bảo hiểm Bảo Việt key", allowableValues = "9fd5eff36c59fa2b94d7cd9fa342ddaf")
//	private String psid;
//	
//	@ApiModelProperty(value="md5(pSID + pSendAt)", allowableValues = "9d6fadc4d03e156121db542cf402f4a8")
//	private String tokenKey;

    @Override
    public String toString() {
        return "LoginVM{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
