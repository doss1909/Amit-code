package com.amit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.amit.dto.LoginDto;
import com.amit.dto.UnlockDto;
import com.amit.dto.forgetDto;
import com.amit.restcontroler.LoginRestController;
import com.amit.service.UserLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = LoginRestController.class)
public class TestLoginRestController {

	@MockBean
	private UserLoginService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void loginSuccess() throws JsonProcessingException, Exception{
		
		LoginDto dto=new LoginDto();
		dto.setAccEmailId("amit@gmail");
		dto.setAccPwd("amit@123");
		
		when(userService.login(dto.getAccEmailId(),dto.getAccPwd())).thenReturn("Login Success");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void ForgotSuccess() throws  Exception{
		
		forgetDto dto=new forgetDto();
		dto.setAccEmailId("amit@gmail");
		dto.setNewPwd("amit@123");
		dto.setConfirmPwd("amit@123");
		
		when(userService.forgetPwd(any(forgetDto.class))).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/forgot")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	public void ForgotFailure() throws Exception{
		
		forgetDto dto=new forgetDto();
		dto.setAccEmailId("amit@gmail");
		dto.setNewPwd("amit@123");
		dto.setConfirmPwd("amit@123");
		
		when(userService.forgetPwd(dto)).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/forgot")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
	
	@Test
	public void UnlockSuccess() throws JsonProcessingException, Exception {
		
		UnlockDto dto=new UnlockDto();
		dto.setAccEmailId("amit@gmail");
		dto.setNewPwd("Amit@123");
		dto.setTempPwd("ygHutr");
		
		when(userService.unlockAcc(any(UnlockDto.class))).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/unlock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void UnlockFailure() throws JsonProcessingException, Exception {
		
		UnlockDto dto=new UnlockDto();
		dto.setAccEmailId("amit@gmail");
		dto.setNewPwd("Amit@123");
		dto.setTempPwd("ygHutr");
		
		when(userService.unlockAcc(any(UnlockDto.class))).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/unlock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

}
