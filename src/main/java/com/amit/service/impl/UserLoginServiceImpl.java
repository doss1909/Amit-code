package com.amit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.dto.UnlockDto;
import com.amit.dto.forgetDto;
import com.amit.entity.UserAccountDetails;
import com.amit.repository.UserAccountDetailsRepo;
import com.amit.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserAccountDetailsRepo userRepo;

	private Logger logger = LoggerFactory.getLogger(UserRegiServiceImpl.class);

	public String login(String email, String pwd) {

		UserAccountDetails emailIdAndAccPwd = userRepo.findByAccEmailIdAndAccPwd(email, pwd);

		if (emailIdAndAccPwd == null) {
			return "Invalid Credentials";
		}
		if (emailIdAndAccPwd.getAccStatus().equals("In-Active")) {
			return "Your Account Locked, check email";
		}else {
			return "Login Success";
		}
	}

	@Override
	public boolean forgetPwd(forgetDto forget) {

		UserAccountDetails accountDetails = userRepo.findByAccEmailId(forget.getAccEmailId());
		if (accountDetails != null &&forget.getNewPwd().equals(accountDetails.getAccPwd()) ) {
			accountDetails.setAccPwd(forget.getConfirmPwd());
			userRepo.save(accountDetails);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean unlockAcc(UnlockDto unlock) {

		UserAccountDetails details = userRepo.findByAccEmailId(unlock.getAccEmailId());

		logger.debug("to check database pwd and pwd from request both are equal or not");
		if (details.getAccPwd().equals(unlock.getTempPwd()) && !details.getAccStatus().equals("ACTIVE") ) {
			details.setAccPwd(unlock.getNewPwd());

			logger.debug("setting user account is active");
			details.setAccStatus("ACTIVE");
			userRepo.save(details);
			return true;
		} else {
			return false;
		}
	}
}
