package com.ashik.storyboard.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ashik.storyboard.config.AppConstant;
import com.ashik.storyboard.entities.Story;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.payloads.ApiResponse;
import com.ashik.storyboard.payloads.StoryDTO;
import com.ashik.storyboard.payloads.StoryPageResponse;
import com.ashik.storyboard.reposotories.StoryRepository;
import com.ashik.storyboard.services.StoryServices;
import com.ashik.storyboard.utils.UpVoted;

@RestController
@RequestMapping("/api")
public class StoryControllers {

	@Autowired
	private StoryServices storyServices;
	
	@Autowired
	private ModelMapper modelMapper;
	


	// create

	@PostMapping("/user/{userId}/posts")
	public ResponseEntity<StoryDTO> createstory(@RequestBody StoryDTO storyDTO, @PathVariable Integer userId
			) {

		StoryDTO storydto = this.storyServices.createStory(storyDTO, userId);

		return new ResponseEntity<StoryDTO>(storydto, HttpStatus.CREATED);

	}
	// get post

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<StoryPageResponse> getstoriesbyuser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue =AppConstant.Page_Number, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.Page_Size, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.Sort_by, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.Sort_Dir, required = false) String sortDir) {

		StoryPageResponse storyPageResponse = this.storyServices.getStoryByUser(userId, pageNumber, pageSize, sortBy,
				sortDir);

		return new ResponseEntity<StoryPageResponse>(storyPageResponse, HttpStatus.OK);

	}
	
	
	
	

//	@GetMapping("/category/{categoryId}/posts")
//	public ResponseEntity<List<StoryDTO>> getstoriesbycategory(@PathVariable Integer categoryId) {
//
//		List<StoryDTO> storyDTOs = this.storyServices.getStoryByCategory(categoryId);
//
//		return new ResponseEntity<List<StoryDTO>>(storyDTOs, HttpStatus.OK);
//
//	}
	
	
	
	
	
	

	// get all
	@GetMapping("/posts")
	public ResponseEntity<List<StoryDTO>> getallpost() {

		List<StoryDTO> allDtos = this.storyServices.getallStories();

		return new ResponseEntity<List<StoryDTO>>(allDtos, HttpStatus.OK);
	}

	// single post

	@GetMapping("/posts/{postId}")
	public ResponseEntity<StoryDTO> getSinglepost(@PathVariable Integer postId) {

		StoryDTO storyDTO = this.storyServices.getStory(postId);

		return new ResponseEntity<StoryDTO>(storyDTO, HttpStatus.OK);
	}

	// with paginationAndSorting
	@GetMapping("/pageStories")
	public ResponseEntity<StoryPageResponse> getallpostwithpage(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.Page_Number, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.Page_Size, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.Sort_by, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.Sort_Dir, required = false) String sortDir) {

		StoryPageResponse ssPageResponse = this.storyServices.allStorieswithpagination(pageNumber, pageSize, sortBy,
				sortDir);

		return new ResponseEntity<StoryPageResponse>(ssPageResponse, HttpStatus.OK);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{storyId}")
	public ResponseEntity<ApiResponse> deleteStory(@PathVariable Integer storyId) {

		this.storyServices.deleteStory(storyId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("story successfully delete", true), HttpStatus.OK);

	}

	// update
	@PutMapping("/posts/{storyId}")
	public ResponseEntity<StoryDTO> updateStory(@RequestBody StoryDTO storyDTO, @PathVariable Integer storyId) {

		StoryDTO updated = this.storyServices.UpdateStory(storyDTO, storyId);

		return new ResponseEntity<StoryDTO>(updated, HttpStatus.OK);

	}
	
	
	//search
	
	@GetMapping("/stories/search/{keywords}")
	public ResponseEntity<List<StoryDTO>> searchStoriesByTitle(
			@PathVariable("keywords") String keywords) {
		
		List<StoryDTO>storyDTOs = this.storyServices.searchStory(keywords);
		
		return new ResponseEntity<List<StoryDTO>>(storyDTOs, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/upvote/{userId}/{postId}")
	public ResponseEntity<StoryDTO> upvote(@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId) {
		
		 StoryDTO storyDTO = this.storyServices.getStory(postId);
		 
		 
		
		UpVoted.upvoteMap.put(postId,UpVoted.upvoteMap.getOrDefault(postId, new HashSet<>()));
		
		UpVoted.downvoteMap.put(postId,UpVoted.downvoteMap.getOrDefault(postId, new HashSet<>()));
		
		if(!UpVoted.downvoteMap.get(postId).contains(userId)) {
			
			if(UpVoted.upvoteMap.get(postId).contains(userId)) {
				UpVoted.upvoteMap.get(postId).remove(userId);
			}else {
				UpVoted.upvoteMap.get(postId).add(userId);
			}
		}
		
		
		
//		UpVoted.upvoteMap.get(postId).add(userId);
		
		storyDTO.setTotalUpvoted(UpVoted.upvoteMap.get(postId).size());
		
		StoryDTO updateStory = this.storyServices.UpdateStory(storyDTO, postId);
		
	     
		
		return new ResponseEntity<StoryDTO>(updateStory, HttpStatus.OK);
		
	}
	
	
	
	@PostMapping("/downvote/{userId}/{postId}")
	public ResponseEntity<StoryDTO> downvote(@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId) {
		
		 StoryDTO storyDTO = this.storyServices.getStory(postId);
		 
		 
		UpVoted.upvoteMap.put(postId,UpVoted.upvoteMap.getOrDefault(postId, new HashSet<>()));
		
		UpVoted.downvoteMap.put(postId,UpVoted.downvoteMap.getOrDefault(postId, new HashSet<>()));
		
		if(!UpVoted.upvoteMap.get(postId).contains(userId)) {
			
			if(UpVoted.downvoteMap.get(postId).contains(userId)) {
				UpVoted.downvoteMap.get(postId).remove(userId);
			}else {
				UpVoted.downvoteMap.get(postId).add(userId);
			}
		}
		
		
		
//		UpVoted.upvoteMap.get(postId).add(userId);
		
		storyDTO.setTotaldownvoted(UpVoted.downvoteMap.get(postId).size());
		
		StoryDTO updateStory = this.storyServices.UpdateStory(storyDTO, postId);
		
	     
		
		return new ResponseEntity<StoryDTO>(updateStory, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	

}
