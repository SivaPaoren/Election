package com.election.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.election.dao.electionRepository;
import com.election.model.Civilian;
import com.election.model.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private electionRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
	    Civilian user = repo.findByName(name);
	    if(user==null)throw new UsernameNotFoundException("Invalid Username or Password");
	    return new UserDetailsImpl(user);
	}

}
