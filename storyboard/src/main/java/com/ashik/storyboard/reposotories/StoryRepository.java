package com.ashik.storyboard.reposotories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ashik.storyboard.entities.Story;
import com.ashik.storyboard.entities.User;

public interface StoryRepository extends JpaRepository<Story, Integer>{
	
	Page<Story> findByUser(User user,Pageable pageable);
//	List<Story> findByCategory(Category category);
	
	List<Story> findByStoryTitleContaining(String storyTitle);

}
