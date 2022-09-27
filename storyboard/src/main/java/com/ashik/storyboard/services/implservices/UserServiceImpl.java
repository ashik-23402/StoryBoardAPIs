package com.ashik.storyboard.services.implservices;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashik.storyboard.config.AppConstant;
import com.ashik.storyboard.entities.Role;
import com.ashik.storyboard.entities.User;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.payloads.UserDTO;
import com.ashik.storyboard.reposotories.RoleRepository;
import com.ashik.storyboard.reposotories.UserRepository;
import com.ashik.storyboard.services.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
		User user = this.dtoTouser(userDTO);
		user.setProfileImage("default.png");
		
		User savedUser =  this.userRepository.save(user);
		return this.userTodto(savedUser);
	}
	
	

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setProfileImage(userDTO.getProfileImage());
		
		User saveUser = this.userRepository.save(user);
		
		
		
		return this.userTodto(saveUser);
	}
	
	

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		
		
		return this.userTodto(user);
	}
	
	

	@Override
	public List<UserDTO> getAllusers() {
		// TODO Auto-generated method stub
		
		List<User>users =  this.userRepository.findAll();
		
		List<UserDTO>userDTOs =  users.stream().map(user -> this.userTodto(user)).collect(Collectors.toList());
		
		
		return userDTOs;
	}

	
	
	@Override
	public void DeleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		
		this.userRepository.delete(user);
		

		
	}
	
	
	
	
	private User dtoTouser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO,User.class);
		
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
		
		return user;
	}
	
	
	private UserDTO userTodto(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		
//		userDTO.setId(user.getId());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setName(user.getName());
//		userDTO.setPassword(user.getPassword());
		
		return userDTO;
	}



	@Override
	public UserDTO registerNewuser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
		User user = this.modelMapper.map(userDTO, User.class);
		
		//encodedpasswoed
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//role
		Role role = this.roleRepository.findById(AppConstant.Role_Normal).get();
		
		user.getRoles().add(role);
		this.userRepository.save(user);
		
		
		return this.modelMapper.map(user, UserDTO.class);
	}
	
	
	
	
	

}
