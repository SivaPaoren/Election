package com.election.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {
	
	//login page caller
	
	@GetMapping("/Login")
    public String callLoginPage(Model model) {
		model.addAttribute("afterSignUp",false);
    	return "sign_in";
    }

}
