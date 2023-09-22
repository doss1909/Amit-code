package com.amit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.entity.UserAccountDetails;

public interface UserAccountDetailsRepo extends JpaRepository<UserAccountDetails, Long>{

	public UserAccountDetails findByAccEmailIdAndAccPwd(String email,String pwd);
	
	public UserAccountDetails findByAccEmailId(String email);
}