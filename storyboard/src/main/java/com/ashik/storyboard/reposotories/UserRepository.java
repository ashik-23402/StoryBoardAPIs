package com.ashik.storyboard.reposotories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashik.storyboard.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	Optional<User> findByEmail(String email);
}
