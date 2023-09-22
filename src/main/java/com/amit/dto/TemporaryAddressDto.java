package com.amit.dto;

import lombok.Data;

@Data
public class TemporaryAddressDto {

	private Long id;

	private String street;

	private String postalCode;

	private String city;

	private String state;

	private String country;
}