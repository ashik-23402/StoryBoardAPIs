package com.ashik.storyboard;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ashik.storyboard.config.AppConstant;
import com.ashik.storyboard.entities.Role;
import com.ashik.storyboard.reposotories.RoleRepository;

@SpringBootApplication
public class StoryboardApplication implements CommandLineRunner {
	
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(StoryboardApplication.class, args);
	}
	
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	    System.out.println(this.passwordEncoder.encode("love"));
	    try {
	    	
	    	Role role = new Role();
	    	role.setId(AppConstant.Role_Admin);
	    	role.setRoleName("ROLE_ADMIN");
			
	    	
	    	Role role1 = new Role();
	    	role1.setId(AppConstant.Role_Normal);
	    	role1.setRoleName("ROLE_NORMAL");
	    	
	    	List<Role> roles = List.of(role,role1);
	    	
	    	List<Role> saveAll = this.roleRepository.saveAll(roles);
	    	
	    	saveAll.forEach(e->{
	    		System.out.println(e.getRoleName());
	    	});
	    	
	    	
	    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

	
}
