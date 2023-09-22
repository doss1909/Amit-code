package com.amit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UnlockDto {
	
	@NotBlank(message="email is mandatory")
	private String accEmailId;

	@Size(min=6, message = "temp password should be min 8 character")
	@Size(max=16, message = "temp password should be max 16 character")
	@NotBlank(message="password is mandatory")
	private String newPwd;
	
	@Size(min=6, message = "temp password should be 6 character")
	@NotBlank(message="password is mandatory")
	private String tempPwd;

}
