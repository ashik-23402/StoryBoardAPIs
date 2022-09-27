package com.ashik.storyboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashik.storyboard.entities.User;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.reposotories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		//loading user from database
		 User user = this.userRepository.findByEmail(username).orElseThrow(()->
		new ResourceNotFoundException("user", " "+username, 0));
		
		return user;
	}

}
