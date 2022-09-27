package com.ashik.storyboard.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ashik.storyboard.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	
	private int id;
	
	@NotEmpty
	private String firstname;
	
	@NotEmpty
	private String lastname;
	
	@Email(message = "email is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10 , message = "password must be min 3 chars and max 10 chars")
	private String password;
	
	private String profileImage;
	
	private Set<RoleDto> roles = new HashSet<>();

}
