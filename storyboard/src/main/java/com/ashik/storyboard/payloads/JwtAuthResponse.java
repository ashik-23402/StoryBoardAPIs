package com.ashik.storyboard.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	
	private UserDTO user;
	
	
	

}
