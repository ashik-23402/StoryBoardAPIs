package com.ashik.storyboard.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashik.storyboard.payloads.ApiResponse;
import com.ashik.storyboard.payloads.UserDTO;
import com.ashik.storyboard.services.FileService;
import com.ashik.storyboard.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	//post-- create user
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createuser(@Valid @RequestBody UserDTO userDTO){
		
		UserDTO uDto =  this.userService.createUser(userDTO);
		
		return new ResponseEntity<>(uDto, HttpStatus.CREATED);
		
	}
	
	
	//put-- update user
	@PutMapping("/{userid}")
	public ResponseEntity<UserDTO> updateuser(@Valid @RequestBody UserDTO uDto,
			@PathVariable Integer userid){
		
		
		UserDTO updateUserDTO = this.userService.updateUser(uDto, userid);
		
		return ResponseEntity.ok(updateUserDTO);
		
		
	}
	
	
	//Delete -- delete user
	//for admin
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteuser(@PathVariable("userid") Integer uid){
		
		this.userService.DeleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true), HttpStatus.OK);
		
	}
	
	
    // Get -- get 

	@GetMapping("/")
	public ResponseEntity<List<UserDTO>>getallusers(){
		
		return ResponseEntity.ok(this.userService.getAllusers());
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserDTO>getuser(@PathVariable("userid") Integer uid){
		
		return ResponseEntity.ok(this.userService.getUserById(uid));
	}
	
	
	
	//userimageupload
	@PostMapping("/user/image/upload/{userId}")
	public ResponseEntity<UserDTO> UploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("userId") Integer userId) throws IOException {
	
		
		UserDTO userDTO =this.userService.getUserById(userId);
		String filename =this.fileService.UploadImage(path, image);
		
		
		userDTO.setProfileImage(filename);
		this.userService.updateUser(userDTO, userId);
		
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/user/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void Serveimage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	
	
	

}
