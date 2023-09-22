package com.amit.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	@Column(name = "acc_id")
	private Long id;

	@NotBlank(message = "first name is not empty")
	@Size(min = 3, message = "first name should be atleast 3 characters")
	@Size(max = 15, message = "first name should not be greater than 20 characters")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "Name must be alphabets")
	private String accFirstName;

	@NotBlank(message = "middle name is not empty")
	@Size(min = 3, message = "middle name should be atleast 3 characters")
	@Size(max = 15, message = "middle name should not be greater than 20 characters")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "Name must be alphabets")
	private String accMiddleName;

	@NotBlank(message = "last name is not empty")
	@Size(min = 3, message = "last name should be atleast 3 characters")
	@Size(max = 15, message = "last name should not be greater than 20 characters")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "Name must be alphabets")
	private String accLastName;

	@Email
	@NotBlank(message = "email should not empty")
	@Column(name = "acc_email_id")
	private String accEmailId;

	@NotNull
	@Min(18)
	@Max(100)
	private Integer accAge;

	@NotBlank(message = "mobile no. should not empty")
	private String accPhone;
}
