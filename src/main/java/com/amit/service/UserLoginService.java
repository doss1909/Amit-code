package com.amit.service;

import com.amit.dto.UnlockDto;
import com.amit.dto.forgetDto;

public interface UserLoginService {

	public String login(String email,String pwd);
	
	public boolean forgetPwd(forgetDto forget);
	
	public boolean unlockAcc(UnlockDto unlock);
}
