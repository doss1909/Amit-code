package com.amit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class forgetDto {

	@NotBlank(message="eamil is mandatory")
	private String accEmailId;
	
	@Size(min=8, message = "newPwd  password should be min 8 character")
	@Size(max=16, message = "newPwd password should be max 12 character")
	@NotBlank(message="new pwd is mandatory")
	private String newPwd;
	
	@Size(min=8, message = "confirmPwd password should be min 8 character")
	@Size(max=16, message = "confirmPwd password should be max 12 character")
	@NotBlank(message="confirmPwd is mandatory")
	private String confirmPwd;
	
}
