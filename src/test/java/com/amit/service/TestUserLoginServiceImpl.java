package com.amit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.amit.dto.UnlockDto;
import com.amit.dto.forgetDto;
import com.amit.entity.UserAccountDetails;
import com.amit.repository.UserAccountDetailsRepo;
import com.amit.service.impl.UserLoginServiceImpl;

@SpringBootTest
public class TestUserLoginServiceImpl {

	@InjectMocks
	private UserLoginServiceImpl ServiceImpl;

	@Mock
	private UserAccountDetailsRepo userRepo;

	@Test
	public void testLoginSuccess() {

		UserAccountDetails user = new UserAccountDetails();
		user.setAccStatus("ACTIVE");
		when(userRepo.findByAccEmailIdAndAccPwd(anyString(), anyString())).thenReturn(user);

		String result = ServiceImpl.login("amit@gmail.com", "amit@123");

		assertEquals("Login Success", result);
	}

	@Test
    public void testLoginInvalidCredentials() {
        when(userRepo.findByAccEmailIdAndAccPwd(anyString(), anyString())).thenReturn(null);

        String result = ServiceImpl.login("amit@gmail.com", "kumar@132");

        assertEquals("Invalid Credentials", result);
    }

	@Test
	public void testLoginAccountLocked() {
		UserAccountDetails user = new UserAccountDetails();
		user.setAccStatus("LOCKED");
		when(userRepo.findByAccEmailIdAndAccPwd(anyString(), anyString())).thenReturn(user);

		String result = ServiceImpl.login("amit@gmail.com", "password");

		assertEquals("Your Account Locked", result);
	}

	@Test
	    public void testForgetPwdSuccess() {
	        when(userRepo.findByAccEmailId(anyString())).thenReturn(new UserAccountDetails());

	        forgetDto forget = new forgetDto();
	        forget.setAccEmailId("amit@gmail.com");
	        forget.setNewPwd("Amit@123");

	        boolean result = ServiceImpl.forgetPwd(forget);

	        assertTrue(result);

	        verify(userRepo).save(any(UserAccountDetails.class));
	    }

	@Test
	    public void testForgetPwdUserNotFound() {
	        when(userRepo.findByAccEmailId(anyString())).thenReturn(null);

	        forgetDto forget = new forgetDto();
	        forget.setAccEmailId("amit@gmail.com");
	        forget.setNewPwd("Amit@123");

	        boolean result = ServiceImpl.forgetPwd(forget);

	        assertFalse(result);

	        verify(userRepo, never()).save(any(UserAccountDetails.class));
	    }

	@Test
	public void testUnlockAccSuccess() {
		UserAccountDetails userDetails = new UserAccountDetails();
		userDetails.setAccPwd("hgyyRh");

		when(userRepo.findByAccEmailId(anyString())).thenReturn(userDetails);

		UnlockDto unlock = new UnlockDto();
		unlock.setAccEmailId("amit@gmail.com");
		unlock.setTempPwd("hgyyRh");
		unlock.setNewPwd("Amit@123");

		boolean result = ServiceImpl.unlockAcc(unlock);

		assertTrue(result);

		verify(userRepo).save(userDetails);

	}

	@Test
	public void testUnlockAccFailure() {
		UserAccountDetails userDetails = new UserAccountDetails();
		userDetails.setAccPwd("Amit@123");

		when(userRepo.findByAccEmailId(anyString())).thenReturn(userDetails);

		UnlockDto unlock = new UnlockDto();
		unlock.setAccEmailId("amit@gmail.com");
		unlock.setTempPwd("ygGytg");
		unlock.setNewPwd("Amit@123");

		boolean result = ServiceImpl.unlockAcc(unlock);

		assertFalse(result);

		verify(userRepo, never()).save(userDetails);
	}
}
