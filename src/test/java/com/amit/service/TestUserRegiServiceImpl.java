package com.amit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amit.dto.UserDto;
import com.amit.entity.UserAccountDetails;
import com.amit.repository.UserAccountDetailsRepo;
import com.amit.service.impl.UserRegiServiceImpl;
import com.amit.util.EmailUtil;
import com.amit.util.PasswordUtils;

@SpringBootTest
public class TestUserRegiServiceImpl {

	@InjectMocks
	private UserRegiServiceImpl ServiceImpl;

	@Mock
	private UserAccountDetailsRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Mock
	private EmailUtil util;

	@Test
	public void testDeleteUserSuccess() {
	        when(userRepo.existsById(anyLong())).thenReturn(true);

	        boolean result = ServiceImpl.deleteUser(1L);

	        assertTrue(result);

	        verify(userRepo).existsById(1L);
	        verify(userRepo).deleteById(1L);
	    }

	@Test
	    public void testDeleteUserFailure() {
	        when(userRepo.existsById(anyLong())).thenReturn(false);

	        boolean result = ServiceImpl.deleteUser(1L);

	        assertFalse(result);

	        verify(userRepo).existsById(1L);
	        verify(userRepo, never()).deleteById(anyLong());
	    }

	@Test
	public void testGetAllUserDetailsSuccess() {
		List<UserAccountDetails> userDetailsList = new ArrayList<>();
		userDetailsList.add(new UserAccountDetails());
		userDetailsList.add(new UserAccountDetails());
		when(userRepo.findAll()).thenReturn(userDetailsList);

		List<UserAccountDetails> result = ServiceImpl.getAllUserDetails();

		verify(userRepo).findAll();

		assertFalse(result.isEmpty());
	}

	@Test
	    public void testGetAllUserDetailsEmptyList() {
	        when(userRepo.findAll()).thenReturn(Collections.emptyList());

	        List<UserAccountDetails> result = ServiceImpl.getAllUserDetails();

	        verify(userRepo).findAll();

	        assertTrue(result.isEmpty());
	    }

	@Test
	public void testGetUserDetailsSuccess() {
		UserAccountDetails user = new UserAccountDetails();
		user.setId(1L);
		when(userRepo.findById(1L)).thenReturn(Optional.of(user));

		UserAccountDetails result = ServiceImpl.getUserDetails(1L);

		verify(userRepo).findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId().longValue());
	}

	@Test
    public void testGetUserDetailsNotFound() {
		
	        when(userRepo.findById(1L)).thenReturn(Optional.empty());

	        UserAccountDetails result = ServiceImpl.getUserDetails(1L);

	        verify(userRepo).findById(1L);

	        assertNull(result);
	    }

	@Test
	public void testUpdateSuccess() {
		Long id = 1L;

		UserDto dto = new UserDto();

		UserAccountDetails user = new UserAccountDetails();
		user.setId(id);
		UserAccountDetails ExistUser = new UserAccountDetails();
		ExistUser.setId(id);
		when(userRepo.findById(id)).thenReturn(Optional.of(ExistUser));
		when(userRepo.save(any(UserAccountDetails.class))).thenReturn(user);

		BeanUtils.copyProperties(ExistUser, dto);

		String msg = ServiceImpl.updateUser(dto);

		assertEquals("Update successfully", msg);

		verify(userRepo, times(1)).findById(id);
		verify(userRepo, times(1)).save(ExistUser);
	}

	@Test
	public void testUpdateFailure() {
		Long id = 1L;

		UserDto dto = new UserDto();

		UserAccountDetails user = new UserAccountDetails();
		user.setId(id);

		when(userRepo.findById(id)).thenReturn(Optional.empty());

		BeanUtils.copyProperties(user, dto);

		String msg = ServiceImpl.updateUser(dto);

		assertEquals("not updated", msg);

		verify(userRepo, times(1)).findById(id);
	}

	@Test
	    public void testSaveUserSuccess() {
	        when(userRepo.findByAccEmailId(anyString())).thenReturn(null);

	        UserDto dto = new UserDto();
			dto.setId(10101L);
			dto.setAccFirstName("Amit");
			dto.setAccMiddleName("Kumar");
			dto.setAccLastName("Bishwas");
			dto.setAccEmailId("amit@gmail.com");
			dto.setAccPhone("984511");
			dto.setAccAge(20);
			
			UserAccountDetails user=new UserAccountDetails();
			user.setAccPwd(PasswordUtils.randomPwd(6));
			

	        boolean result = ServiceImpl.saveUser(dto);

	        assertTrue(result);

	        verify(userRepo).findByAccEmailId("amit@gmail.com");
	        verify(userRepo).save(any(UserAccountDetails.class));
	    }

	
	@Test
	    public void testSaveUserFailureUserExists() {
	        when(userRepo.findByAccEmailId(anyString())).thenReturn(new UserAccountDetails());

	        UserDto dto = new UserDto();
	        dto.setAccEmailId("test@example.com");

	        boolean result = ServiceImpl.saveUser(dto);

	        assertFalse(result);

	        verify(userRepo).findByAccEmailId("test@example.com");
	    }
}
