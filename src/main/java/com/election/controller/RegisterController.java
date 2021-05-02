package com.election.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.election.dao.electionRepository;
import com.election.model.Civilian;
import com.election.model.active;
import com.election.service.electionService;

@Controller
public class RegisterController {
	
	 @Autowired
	 PageController pageController;
	
	  @Autowired
	  private electionRepository repo;
	  
	  @Autowired
	  private electionService elecService;
	  
	  @Autowired
	  private active active;
	
	    @GetMapping("/Register")
	    public String callLogoutPage(Model model) {
	    	model.addAttribute("userCheck",false);
    		model.addAttribute("passwordLengthCheck", false);
    		model.addAttribute("passCheck", false);
	    	return "sign_up";
	    }
	    
	    
	    
	    //user register here
	    
	    @PostMapping("/Register")
	    public String performRegister(Civilian civilian,Model model,Principal prin,HttpServletRequest req) {
	    	//check if the user name can be used or not
	    	active = new active();
	    	active.setActive(true);
	    	//ModelAndView mv = new ModelAndView();
	    	String name = civilian.getName();
	    
	    	boolean flag = elecService.availableOrNot(name);
	    	//user name can be used or not?
	    	if(flag==false){
	    		model.addAttribute("userCheck",true);
	    		model.addAttribute("passwordLengthCheck", false);
	    		model.addAttribute("passCheck", false);

	    		model.addAttribute("country", civilian.getCountry());
	    		model.addAttribute("email",civilian.getEmail());
	    		model.addAttribute("message","User name already exist.Please choose another one.");
	    		model.addAttribute("suggestname",elecService.availableNames(name, model, 3));
	    		//mv.setViewName("sign_up");
	    		return "sign_up";
	    	}
	    	//password length check
	    	boolean passlengthcheck = elecService.passlengthCheck(civilian.getPassword().length());
	    	if(!passlengthcheck) {
	    		model.addAttribute("userCheck",false);
	    		model.addAttribute("passwordLengthCheck", true);
	    		model.addAttribute("passCheck", false);
	    		
	    		model.addAttribute("username",civilian.getName());
	    		model.addAttribute("country", civilian.getCountry());
	    		model.addAttribute("email",civilian.getEmail());
	    		model.addAttribute("message","Password length must be greater than 4 and less than 15");
	    		//mv.setViewName("sign_up");
	    		return "sign_up";
	    	}
	    	
	    	
	    	//conpass and pass check
	    	
	    	boolean validPass = elecService.passEqualConpass(civilian.getPassword(),civilian.getConfirm_password());
	    	if(!validPass) {
	    		model.addAttribute("userCheck",false);
	    		model.addAttribute("passwordLengthCheck",false);
	    		model.addAttribute("passCheck",true);
	    		
	    		
	    		model.addAttribute("username",civilian.getName());
	    		model.addAttribute("country", civilian.getCountry());
	    		model.addAttribute("email",civilian.getEmail());
	    		model.addAttribute("message","Password and Confirm password does not match");
	    		//mv.setViewName("sign_up");
	    		return "sign_up";
	    	}
	    		
	    		
	       model.addAttribute("afterSignUp", true);	
	       model.addAttribute("username", civilian.getName());
	       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	       String encodePass = encoder.encode(civilian.getPassword());
	       civilian.setRole("USER");
	       civilian.setPassword(encodePass);
	       repo.save(civilian);
	       return "sign_in";
	    }
		
}
