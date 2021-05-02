package com.election.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.election.dao.commentRepository;
import com.election.dao.electionRepository;
import com.election.model.Civilian;
import com.election.model.Comment;
import com.election.model.active;

@Service
public class electionService{
	
	@Autowired
	private active active; 

	@Autowired
	private electionRepository electionRepo;
	
	@Autowired
	private commentRepository commentRepo;
	
	public static int totalComment;

		public void loginornotcheck(Model model,String username) {
			active = new active();
			active.setActive(true);
			
			//if the user is not loged in this will retuen false
			if(username==null||username.equals("")) {
				active.setActive(false);
				model.addAttribute("status",active);
			}
			model.addAttribute("status",active);
		}



    //check if the user name already exiest //if the username can be used or not
    //name is available or not
    public boolean availableOrNot(String name){
    	Civilian user;
    	
    	try {
    		//try to access the username if the username doesnot exist it will catch exception
    		user = electionRepo.findByName(name);
    	}catch(Exception e) {
    		user = new Civilian();
    	}
    	
    	
        if(user==null||user.equals("")) {
        	return true;
        }
        return false;
    }
    
    
    //generate available names if the username is available
    public String availableNames(String name,Model model,int namelength){
    	String s = "1234567890";
    	StringBuilder sb = new StringBuilder(name);
    	
    	sb.append(s.charAt((int)(Math.random()*s.length())));
    	
    	boolean check = availableOrNot(sb.toString());
    	if(!check) {
    		availableNames(sb.toString(),model,namelength);
    	}
    	return new String(sb.toString());
    }
    
   //password confirm pass and pass match
    public boolean passEqualConpass(String pass,String conPass) {
    	if(!pass.equals(conPass)) {
    		return false;
    	}
    	return true;
    }
    
    //password length must be greater tham 6
    public boolean passlengthCheck(int length) {
    	if(length<4||length>15) {
    		return false;
    	}
    	return true;
    }
    
    
    
    //comment date 
    public void dateAndTimes(Comment comment) {
    	LocalDateTime localTime = 	LocalDateTime.now();
    	int date = localTime.getDayOfMonth();
    	String month = localTime.getMonth().toString();
    	int year = localTime.getYear();
    	int hour = localTime.getHour();
    	int sec = localTime.getMinute();
    	
    	String time = hour+":"+sec;
    	
    	comment.setDate(date);
    	comment.setMonth(month);
    	comment.setTime(time);
        comment.setYear(year);
        
        System.out.println(month+" "+date+" "+year+" "+time);
    }
    
    public String shortenMonth(String month) {
    	return month.substring(0, 2);
    }
    
    
    
    //all the copmments and date and time will be added here to send to the view
    public void CommentManager(Model model) {
    	List<Comment> comments = getLastFiveComments();
    	model.addAttribute("comments", comments);
    	
    }
	
    //get the only latest 5 comments to display
    public List<Comment> getLastFiveComments(){
    	List<Comment> commentList =commentRepo.findAll();
    	//total number of comments is get from here
    	totalComment = commentList.size();
    	List<Comment> LastFive = new ArrayList<>();
    	if(commentList.size()<=5)return commentList;
    
    	
    	for(int i=commentList.size();i>commentList.size()-5;i--) {
    		LastFive.add(commentList.get(i-1));
    	}
    	return LastFive;
    }
    
    //
}
