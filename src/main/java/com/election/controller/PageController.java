package com.election.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.election.model.Comment;
import com.election.service.electionService;
import com.election.service.voteService;

@Controller
public class PageController {
     
	@Autowired
	electionService electionService;
	
	@Autowired
	private voteService voteService;
	
	
	@GetMapping("/")
	public String landing(Model model,Principal prin) {
		return callLandingPage(model,prin);
	}
	//landing page caller
		@GetMapping("/home")
	    public String callLandingPage(Model model,Principal prin) {
			String user;
			try {
				 user = prin.getName();
				}
			catch(NullPointerException e) {
				user = "";
			}
	     
	     electionService.loginornotcheck(model, user);
	     return "home";
	    }

	
	//index page caller
	@GetMapping("/index")
    public String callIndex(Model model,Principal prin) {
		
     double[]percentage = voteService.getCandidatesPercentage();
     int j=1;
     for(double d:percentage) {
    	 if(j==5) {
    		 //number of total votes is sent from here
    		 model.addAttribute("a"+j, (int)d);
    		 break;
    	 }
    	 //vote rates
    	 model.addAttribute("a"+j, d);
         j++; 
      }
     
       //adding comment to the page 
       //this will set comments as list into the model // only latest five comments are sent
   		List<Comment> list = electionService.getLastFiveComments();
   		if(list==null||list.size()==0) {
   			model.addAttribute("commentCondition", false);
   		}
   	    //total number of comments
		int totalComment = electionService.totalComment;
		
		model.addAttribute("totalComment", totalComment);
   		model.addAttribute("commentCondition", true);
   		electionService.CommentManager(model);
         
       	  return "index";
     }
     

	
	//voted page caller
	@GetMapping("/voted")
    public String callVotedPage() {
    	return "votedPage";
    }
	 //about page caller
    @GetMapping("/about")
    public String callAboutPage(Model model,Principal prin) {
    	String userName;
        try {
       	 userName = prin.getName();
        }catch(NullPointerException e) {
       	 userName="";
        }
    	 electionService.loginornotcheck(model, userName);
    	return "about";
    }
    
    
 
}
