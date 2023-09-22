package com.amit.service;

import java.util.List;

import com.amit.dto.UserDto;
import com.amit.entity.UserAccountDetails;

public interface UserRegisService {

	public boolean saveUser(UserDto dto);

	public boolean deleteUser(Long id);

	public UserAccountDetails getUserDetails(Long id);

	public List<UserAccountDetails> getAllUserDetails();
	
	public String updateUser(UserDto dto);
}
