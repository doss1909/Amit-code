package com.amit.restcontroler;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amit.dto.UserDto;
import com.amit.entity.UserAccountDetails;
import com.amit.service.UserRegisService;

@Controller
public class RegistrationRestController {

	@Autowired
	private UserRegisService userService;

	Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);

	@PostMapping("/user")
	public String saveUser(@Validated @ModelAttribute("user") UserDto user,BindingResult result,Model model) {

		logger.debug("sening data rest to service");
		if (result.hasErrors())
			return "index";

		boolean status = userService.saveUser(user);
		if (status) {
			model.addAttribute("msg","Registration Successful");
			
		}else {
			model.addAttribute("msg","email already exist !");
		}
		return "index";

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {

		boolean status = userService.deleteUser(id);
		if (status) {
			return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserAccountDetails> getUser(@PathVariable Long id) {
		UserAccountDetails userDetails = userService.getUserDetails(id);
		if (userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * @GetMapping("/getAllUser") public ResponseEntity<List<UserAccountDetails>>
	 * getAllUser() {
	 * 
	 * logger.debug("fetching all users data"); List<UserAccountDetails> userDetails
	 * = userService.getAllUserDetails(); if (!userDetails.isEmpty()) { return new
	 * ResponseEntity<>(userDetails, HttpStatus.OK); } else {
	 * 
	 * return new ResponseEntity<>(userDetails, HttpStatus.NOT_FOUND); } }
	 */

	@GetMapping("/getAllUser")
	public String getData(Model model) {
		model.addAttribute("user", new UserDto());
		model.addAttribute("users", userService.getAllUserDetails());
		return "users";
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto dto) {
		String msg = userService.updateUser(dto);
		if ("Update successfully".equals(msg)) {
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("not updated", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public String loadForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "index";
	}
}