package com.amit.restcontroler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.amit.dto.LoginDto;
import com.amit.dto.UnlockDto;
import com.amit.dto.forgetDto;
import com.amit.service.UserLoginService;

@Controller
public class LoginRestController {

	@Autowired
	private UserLoginService userService;

	@PostMapping("/loginUser")
	public String loginUser(@Validated @ModelAttribute("login") LoginDto login, BindingResult result, Model model) {

		if (result.hasErrors())
			return "login";

		String msg = userService.login(login.getAccEmailId(), login.getAccPwd());
		model.addAttribute("msg", msg);
		return "login";
	}

	@PostMapping("/forget")
	public String forgoPassword(@Validated @ModelAttribute("forget") forgetDto forget, BindingResult result,
			Model model) {
		boolean status = userService.forgetPwd(forget);

		if (result.hasErrors())
			return "forget";

		if (status) {
			model.addAttribute("msg", "Successful");
		} else {
			model.addAttribute("msg", "Invalid Credentials");
		}
		return "forget";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@Validated @ModelAttribute("unlock") UnlockDto unlock, BindingResult result,Model model) {

		if (result.hasErrors())
			return "unlock";

		boolean status = userService.unlockAcc(unlock);
		if (status) {
			model.addAttribute("msg", "Account Unlock Successful");
		} else {
			model.addAttribute("msg", "Account not Unlock,Invalid Credentials");
		}
		return "unlock";
	}

	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("login", new LoginDto());
		return "login";
	}

	@GetMapping("/unlock")
	public String UnlockForm(Model model) {
		model.addAttribute("unlock", new UnlockDto());
		return "unlock";
	}

	@GetMapping("/forget")
	public String ForgotForm(Model model) {
		model.addAttribute("forget", new forgetDto());
		return "forget";
	}
}
