package com.amit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.amit.dto.UserDto;
import com.amit.entity.UserAccountDetails;
import com.amit.restcontroler.RegistrationRestController;
import com.amit.service.UserRegisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = RegistrationRestController.class)
public class TestRegistrationRestController {

	@MockBean
	private UserRegisService userRegService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void saveSuccess() throws JsonProcessingException, Exception {

		UserDto dto = new UserDto();
		dto.setId(10101L);
		dto.setAccFirstName("Amit");
		dto.setAccMiddleName("Kumar");
		dto.setAccLastName("Bishwas");
		dto.setAccEmailId("amit@gmail.com");
		dto.setAccPhone("984511");
		dto.setAccAge(20);

		when(userRegService.saveUser(any(UserDto.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/user/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void saveFailure() throws JsonProcessingException, Exception {

		UserDto dto = new UserDto();
		dto.setId(10101L);
		dto.setAccFirstName("Amit");
		dto.setAccMiddleName("Kumar");
		dto.setAccLastName("Bishwas");
		dto.setAccEmailId("amit@gmail.com");
		dto.setAccPhone("984511");
		dto.setAccAge(20);
		when(userRegService.saveUser(any(UserDto.class))).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.post("/user/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	public void deleteSuccess() throws Exception {

		Long id = 10101L;

		when(userRegService.deleteUser(id)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("deleted successfully"));
	}

	@Test
	public void deleteFailure() throws Exception {

		Long id = 10101L;

		when(userRegService.deleteUser(id)).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("user not found"));
	}

	@Test
	public void getUserSuccess() throws Exception {

		Long id = 10101L;
		UserAccountDetails user = new UserAccountDetails();
		user.setId(id);

		when(userRegService.getUserDetails(id)).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getUser/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void getUserFailure() throws Exception {

		Long id = 10101L;
		UserAccountDetails user = new UserAccountDetails();
		user.setId(id);

		when(userRegService.getUserDetails(id)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getUser/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void getUserAllSuccess() throws Exception {

		Long id1 = 10101L;
		UserAccountDetails user1 = new UserAccountDetails();
		user1.setId(id1);

		Long id2 = 10102L;
		UserAccountDetails user2 = new UserAccountDetails();
		user2.setId(id2);

		List<UserAccountDetails> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);

		when(userRegService.getAllUserDetails()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getAllUser")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(users.size()));
	}

	@Test
	public void getUserAllFailure() throws Exception {

		when(userRegService.getAllUserDetails()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getAllUser"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void UpdateSuccess() throws JsonProcessingException, Exception {

		UserDto dto = new UserDto();
		dto.setId(10101L);
		dto.setAccFirstName("Amit");
		dto.setAccMiddleName("Kumar");
		dto.setAccLastName("Bishwas");
		dto.setAccEmailId("amit@gmail.com");
		dto.setAccPhone("984511");
		dto.setAccAge(20);
	

		when(userRegService.updateUser(any(UserDto.class))).thenReturn("Update successfully");

		mockMvc.perform(MockMvcRequestBuilders.put("/user/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void UpdateFailue() throws JsonProcessingException, Exception {

		UserDto dto = new UserDto();
		dto.setId(10101L);
		dto.setAccFirstName("Amit");
		dto.setAccMiddleName("Kumar");
		dto.setAccLastName("Bishwas");
		dto.setAccEmailId("amit@gmail.com");
		dto.setAccPhone("984511");
		dto.setAccAge(20);
		

		when(userRegService.updateUser(any(UserDto.class))).thenReturn("not updated");

		mockMvc.perform(MockMvcRequestBuilders.put("/user/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	
}
