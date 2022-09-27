package com.ashik.storyboard.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashik.storyboard.entities.User;
import com.ashik.storyboard.exceptions.APIException;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.payloads.ImageNameResponse;
import com.ashik.storyboard.payloads.JwtAuthRequest;
import com.ashik.storyboard.payloads.JwtAuthResponse;
import com.ashik.storyboard.payloads.UserDTO;
import com.ashik.storyboard.reposotories.UserRepository;
import com.ashik.storyboard.security.JWTtokenHelper;
import com.ashik.storyboard.services.FileService;
import com.ashik.storyboard.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Value("${project.image}")
	private String path;

	@Autowired
	private JWTtokenHelper jwTtokenHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FileService fileService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwTtokenHelper.generateToken(userDetails);

		User user = this.userRepository.findByEmail(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("user", "" + request.getUsername(), 0));

		this.modelMapper.map(user, UserDTO.class);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

		jwtAuthResponse.setToken(token);

		jwtAuthResponse.setUser(this.modelMapper.map(user, UserDTO.class));

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			// TODO: handle exception

			System.out.println("invalid details");
			throw new APIException("invalid  password");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> RegisterUser(@RequestBody UserDTO userDTO) {

		UserDTO registerNewuser = this.userService.registerNewuser(userDTO);

		return new ResponseEntity<UserDTO>(registerNewuser, HttpStatus.CREATED);
	}
	
	

	// userimageupload
	@PostMapping("/user/image/upload/")
	public ResponseEntity<ImageNameResponse> UploadImage(@RequestParam("image") MultipartFile image) throws IOException {

		String filename = this.fileService.UploadImage(path, image);
		ImageNameResponse imageNameResponse = new ImageNameResponse();
		imageNameResponse.setImageName(filename);

		return new ResponseEntity<ImageNameResponse>(imageNameResponse, HttpStatus.OK);
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
