package com.amit.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.dto.UserDto;
import com.amit.entity.UserAccountDetails;
import com.amit.repository.UserAccountDetailsRepo;
import com.amit.service.UserRegisService;
import com.amit.util.EmailUtil;
import com.amit.util.PasswordUtils;

@Service
public class UserRegiServiceImpl implements UserRegisService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserAccountDetailsRepo userRepo;

	@Autowired
	private EmailUtil util;

	private Logger logger = LoggerFactory.getLogger(UserRegiServiceImpl.class);

	@Override
	 public boolean saveUser(UserDto dto) {

		UserAccountDetails findByAccEmailId = userRepo.findByAccEmailId(dto.getAccEmailId());

		UserAccountDetails userEntity = new UserAccountDetails();

		if (findByAccEmailId != null) {

			return false;
		}

		logger.debug("copy  dto data to entity class");
		BeanUtils.copyProperties(dto, userEntity);
		userEntity.setAccStatus("In-Active");

		logger.info("generating random password");
		String randomPwd = PasswordUtils.randomPwd(6);

		userEntity.setAccPwd(randomPwd);

		logger.info("setting random password in entity class");
		userRepo.save(userEntity);

		logger.debug("send email to unlock account");

		String to = dto.getAccEmailId();
		String subject = "unlock your accounts";

		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use below tempory password to unlock your accounts</h1>");
		body.append("temprory pwd:" + randomPwd);
		
		body.append("<br/>");

		body.append("<a href=\"http://localhost:9092/unlock?email=" + to + "\">Click here to unlock your Account</a>");

		logger.debug("sending email to given email id");
		util.sendEmail(to, subject, body.toString());
		return true;

	}

	@Override
	public boolean deleteUser(Long id) {
		logger.debug("checking user available or not given by id");
		boolean existsById = userRepo.existsById(id);

		if (existsById) {
			logger.debug("sending request to delete user by id");
			userRepo.deleteById(id);
			logger.debug("user successfully deleted");
			return true;
		}
		logger.debug("user not  deleted");
		return false;
	}

	@Override
	public UserAccountDetails getUserDetails(Long id) {

		Optional<UserAccountDetails> findById = userRepo.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		} else {
			 throw new NullPointerException();
		}

	}

	@Override
	public List<UserAccountDetails> getAllUserDetails() {
		logger.debug("sending request to getting all user details");

		List<UserAccountDetails> findAll = userRepo.findAll();
		if (!findAll.isEmpty()) {
			logger.debug("successfully getting all user details");
			return findAll;
		} else {
			logger.debug("users are not available returing empty list");
			return Collections.emptyList();
		}

	}

	@Override
	public String updateUser(UserDto dto) {

		Optional<UserAccountDetails> findById = userRepo.findById(dto.getId());

		if (findById.isPresent()) {
			logger.debug("sending request to update user details");

			BeanUtils.copyProperties(dto, findById.get());
			userRepo.save(findById.get());

			logger.debug("updated successfully");
			return "Update successfully";
		}

		else {
			return "not updated";
		}

	}
}
