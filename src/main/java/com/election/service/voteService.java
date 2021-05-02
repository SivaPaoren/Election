package com.election.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.election.dao.electionRepository;
import com.election.dao.voteRepository;
import com.election.model.Vote;

@Service
public class voteService{

	@Autowired
	private electionRepository elecRepo;
	
	@Autowired
	private voteRepository voteRepo;

	
	//save voter detail
	public void saveVote(String presidentName,int voteid,String username) {
		Vote voter = new Vote();
		String country = elecRepo.findByName(username).getCountry();
		voter.setName(username);
		voter.setCountry(country);
		voter.setPresidentName(presidentName);
		voter.setVote_id(voteid);
		voteRepo.save(voter);
	}
	
	//check voter if he has voted or not
	public boolean votedCheck(String username) {
		Vote voter = voteRepo.findByName(username);
		if(voter==null||voter.equals("")) {
			return false;
		}
		return true;
	}
	
	//cal cuculate percentage and return arry of percentage of elections;
	public double[] getCandidatesPercentage(){
		List<Vote>voters = new ArrayList<>();
		voters = voteRepo.findAll();
		//presidents percentage
		double donaldvote = 0;
		double joebidenvote = 0;
		double jorgensenvote = 0;
		double hawkinsvote = 0;
		for(Vote voter:voters) {
			if(voter.getVote_id()==1)donaldvote++;
			if(voter.getVote_id()==2)joebidenvote++;
			if(voter.getVote_id()==3)jorgensenvote++;
			if(voter.getVote_id()==4)hawkinsvote++;
		}
	   	double trumpPercentage = (donaldvote/voters.size())*100;
		double joebidenPercentage = (joebidenvote/voters.size())*100;
		double jorgensenPercentage = (jorgensenvote/voters.size())*100;
		double hawkinPercentage = (hawkinsvote/voters.size())*100;
		
		return oneDecimalDouble(new double[] {trumpPercentage,joebidenPercentage,jorgensenPercentage,hawkinPercentage,voters.size()});
	}
	
	
	//method to show only one decimal
	private double[] oneDecimalDouble(double arr[]) {
		double formated[] = new double[arr.length];
		DecimalFormat df = new DecimalFormat("#.#");
		int i=0;
		for(double d:arr) {
			double e = Double.parseDouble(df.format(d));
			formated[i]=e;
			i++;
		}
		return formated;
	}
	
	
	//send message to the voted page.If the user has vote already then voteAlready message will be display else vote success message will display
	
	public void messageSender(Model model,String username,int voteid,boolean voteCheck) {
		String quote= "Your voices are being heard and you’re proving to our ancestors that their struggles were not in vain. Now we have one more thing we need to do to walk in our true power, and that is to vote.";
		String quoteName="Beyonce";
		String []candidates = {"Donald Trump","Joe Biden","Jo Jorgensen","Howie Hawkins"};
		
		if(voteCheck) {
		   	int voteId = voteRepo.findByName(username).getVote_id();

		   	int j=1;
			for(String name:candidates) {
				if(voteId==j) { 
	                 model.addAttribute("message", username+", you have already vote for "+name);
	                 break;
                 }
				j++;
               }	
			 model.addAttribute("quote", quote);
			 model.addAttribute("quoteName", quoteName);
			
		}
		
		if(!voteCheck){
					int j=1;
					for(String name:candidates) {
						if(voteid==j) {
							model.addAttribute("message", username+", you have successfully vote for "+name);
							break;
						}
						j++;
					}
				model.addAttribute("quote", quote);
				model.addAttribute("quoteName", quoteName);		
				
           }
		
	}


	


	
	
	
}
