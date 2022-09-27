package com.ashik.storyboard.services;

import java.util.List;

import com.ashik.storyboard.payloads.UserDTO;

public interface UserService {
	
	UserDTO registerNewuser(UserDTO userDTO);
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO userDTO, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllusers();
	
	void DeleteUser(Integer userId);
	

}
