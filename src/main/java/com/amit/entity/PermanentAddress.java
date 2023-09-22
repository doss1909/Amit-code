package com.amit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "PERMANENT_ADDRESS")
public class PermanentAddress {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "street")
	private String street;

	@Column(name = "postalcode")
	private String postalCode;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

}