package com.amit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

	@NotBlank(message="email is mandatory")
	@Email
	private String accEmailId;

	@NotBlank(message="password is mandatory")
	private String accPwd;
}
