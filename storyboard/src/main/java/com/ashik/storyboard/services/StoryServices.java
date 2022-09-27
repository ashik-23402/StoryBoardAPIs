package com.ashik.storyboard.services;

import java.util.List;

import com.ashik.storyboard.payloads.StoryDTO;
import com.ashik.storyboard.payloads.StoryPageResponse;

public interface StoryServices {
	
	
	//create
	StoryDTO createStory(StoryDTO storyDTO,Integer userId);
	
	//update
	
	StoryDTO UpdateStory(StoryDTO storyDTO,Integer storyId);
	
	//delete
	
	void deleteStory(Integer storyId);
	
	//getallstories
	
	StoryPageResponse allStorieswithpagination(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
	List<StoryDTO> getallStories();
	
	//getstory
	
	StoryDTO getStory(Integer storyId);
	
	//getstorybycategory
	
//	List<StoryDTO>getStoryByCategory(Integer categoryId);
	
	//getstorybyuser
	
	StoryPageResponse getStoryByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
	//searchstory
	List<StoryDTO>searchStory(String keyword);

}
