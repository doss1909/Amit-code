package com.amit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_TAB")
public class UserAccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "acc_id")
	private Long id;

	@Column(name = "acc_first_name")
	private String accFirstName;

	@Column(name = "acc_middle_name")
	private String accMiddleName;

	@Column(name = "acc_last_name")
	private String accLastName;

	@Column(name = "acc_email_id")
	private String accEmailId;

	@Column(name = "acc_password")
	private String accPwd;

	@Column(name = "acc_status")
	private String accStatus;

	@Column(name = "acc_age_id")
	private Integer accAge;

	@Column(name = "acc_phone")
	private String accPhone;

}