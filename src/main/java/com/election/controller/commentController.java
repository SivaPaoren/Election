package com.election.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.election.dao.commentRepository;
//import com.election.dao.commentRepository;
import com.election.model.Comment;
import com.election.service.electionService;

@Controller
public class commentController {
    @Autowired
	private commentRepository commentRepo;

    @Autowired
    private PageController mainController;
    
    @Autowired
    private electionService elctionService; 
    
	//comments will be saved here
     @PostMapping(value="/getComment")
	public String saveComment(Comment comment,Model model,Principal prin) {
		String name;
		try {
		  name = prin.getName();
		}catch(Exception e) {
			name = "error";
		}
		comment.setUsername(name);
		elctionService.dateAndTimes(comment);
		commentRepo.save(comment);
		//this will set comments as list into the model // only latest five comments are sent
		List<Comment> list = elctionService.getLastFiveComments();
		if(list==null||list.size()==0) {
			model.addAttribute("commentCondition", false);
		}
		
		//total number of comments
		int totalComment = elctionService.totalComment;
		
		model.addAttribute("totalComment", totalComment);
		model.addAttribute("commentCondition", true);
		elctionService.CommentManager(model);
		return mainController.callIndex(model, prin);
	}
     
     
     //comment page will be redirected from here
     @GetMapping("/moreComments")
     public String moreComment(Model model) {
    	 //total comments goes from here
    	 int totalComment = electionService.totalComment;
   		 model.addAttribute("totalComment", totalComment);
   		 
   		 
    	 List<Comment> allComment = commentRepo.findAll();
    	 model.addAttribute("comments", allComment);
    	 return "comment";
     }
}
