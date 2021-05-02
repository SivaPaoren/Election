package com.election.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.election.service.voteService;

@Controller
public class voteController {
	
	@Autowired
	private voteService voteService;

      @GetMapping("/vote")
	  private String vote(@RequestParam("presidentName")String presidentName,@RequestParam("voteid") int voteid,Principal prin,Model model) {
			String name = prin.getName();
		    
		  //check if the user has already voted or not
		  //if true already vote if false have not vote yet
			boolean voteCheck = voteService.votedCheck(name);
			
			
			if(voteCheck) {
				voteService.messageSender(model, name, voteid, voteCheck);
				}
			
			
			if(!voteCheck) {
                voteService.messageSender(model, name, voteid, voteCheck);
				voteService.saveVote(presidentName, voteid, name);
			}
			
			
			return "votedPage";
        }
		
	}
	
	
	
	

