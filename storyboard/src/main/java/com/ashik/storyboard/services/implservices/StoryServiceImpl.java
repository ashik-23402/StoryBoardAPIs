package com.ashik.storyboard.services.implservices;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ashik.storyboard.entities.Story;
import com.ashik.storyboard.entities.User;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.payloads.StoryDTO;
import com.ashik.storyboard.payloads.StoryPageResponse;
import com.ashik.storyboard.reposotories.CategoryReposotory;
import com.ashik.storyboard.reposotories.StoryRepository;
import com.ashik.storyboard.reposotories.UserRepository;
import com.ashik.storyboard.services.StoryServices;

import org.springframework.data.domain.Sort;

@Service
public class StoryServiceImpl implements StoryServices {

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryReposotory categoryReposotory;

	@Override
	public StoryDTO createStory(StoryDTO storyDTO, Integer userId) {
		// TODO Auto-generated method

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
//		Category cat = this.categoryReposotory.findById(categoryId)
//				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		Story story = this.modelMapper.map(storyDTO, Story.class);
		story.setAddedDate(new Date());
//		story.setCategory(cat);
		story.setUser(user);

		Story updatedstory = this.storyRepository.save(story);
//		System.out.println(updatedstory.getAddedDate());
		StoryDTO needreturnDto = this.modelMapper.map(updatedstory, StoryDTO.class);
//		System.out.println(needreturnDto.getAddedDate());

		return needreturnDto;
	}

	@Override
	public StoryDTO UpdateStory(StoryDTO storyDTO, Integer storyId) {
		// TODO Auto-generated method stub
		Story story = this.storyRepository.findById(storyId)
				.orElseThrow(() -> new ResourceNotFoundException("story", "storyId", storyId));

		story.setStoryTitle(storyDTO.getStoryTitle());
		story.setContent(storyDTO.getContent());
		story.setTotalUpvoted(storyDTO.getTotalUpvoted());
		story.setTotaldownvoted(storyDTO.getTotaldownvoted());

		Story updateStory = this.storyRepository.save(story);

		return this.modelMapper.map(updateStory, StoryDTO.class);
	}

	@Override
	public void deleteStory(Integer storyId) {
		// TODO Auto-generated method stub
		Story story = this.storyRepository.findById(storyId)
				.orElseThrow(() -> new ResourceNotFoundException("story", "storyId", storyId));

		this.storyRepository.delete(story);

	}

	@Override
	public List<StoryDTO> getallStories() {
		// TODO Auto-generated method stub

		List<Story> allStories = this.storyRepository.findAll();

		List<StoryDTO> storyDTOs = allStories.stream().map((stori) -> this.modelMapper.map(stori, StoryDTO.class))
				.collect(Collectors.toList());

		return storyDTOs;
	}

	@Override
	public StoryDTO getStory(Integer storyId) {
		// TODO Auto-generated method stub
		Story story = this.storyRepository.findById(storyId)
				.orElseThrow(() -> new ResourceNotFoundException("story", "storyId", storyId));

		return this.modelMapper.map(story, StoryDTO.class);
	}
	
	
	

//	@Override
//	public List<StoryDTO> getStoryByCategory(Integer categoryId) {
//		// TODO Auto-generated method stub
//		Category cat = this.categoryReposotory.findById(categoryId)
//				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
//		List<Story> allStories = this.storyRepository.findByCategory(cat);
//		List<StoryDTO> storyDTOs = allStories.stream().map((stori) -> this.modelMapper.map(stori, StoryDTO.class))
//				.collect(Collectors.toList());
//
//		return storyDTOs;
//	}
	
	
	
	

	@Override
	public StoryPageResponse getStoryByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Story> pagestories = this.storyRepository.findByUser(user, pageable);

		List<Story> allStories = pagestories.getContent();
		List<StoryDTO> storyDTOs = allStories.stream().map((stori) -> this.modelMapper.map(stori, StoryDTO.class))
				.collect(Collectors.toList());

		StoryPageResponse storyPageResponse = new StoryPageResponse();

		storyPageResponse.setContents(storyDTOs);
		storyPageResponse.setPageNumber(pagestories.getNumber());
		storyPageResponse.setPageSize(pagestories.getSize());
		storyPageResponse.setTotalElements(pagestories.getTotalElements());
		storyPageResponse.setTotalpages(pagestories.getTotalPages());
		storyPageResponse.setIslastpage(pagestories.isLast());

		return storyPageResponse;
	}
	
	
	

	@Override
	public List<StoryDTO> searchStory(String keyword) {
		// TODO Auto-generated method stub
		List<Story>allStories =this.storyRepository.findByStoryTitleContaining(keyword);
		List<StoryDTO> storyDTOs = allStories.stream().map((stori) -> this.modelMapper.map(stori, StoryDTO.class))
				.collect(Collectors.toList());
		
		return storyDTOs;
	}
	
	
	
	

	@Override
	public StoryPageResponse allStorieswithpagination(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Story> pagestories = this.storyRepository.findAll(pageable);
		List<Story> allStories = pagestories.getContent();
		List<StoryDTO> storyDTOs = allStories.stream().map((stori) -> this.modelMapper.map(stori, StoryDTO.class))
				.collect(Collectors.toList());

		StoryPageResponse storyPageResponse = new StoryPageResponse();

		storyPageResponse.setContents(storyDTOs);
		storyPageResponse.setPageNumber(pagestories.getNumber());
		storyPageResponse.setPageSize(pagestories.getSize());
		storyPageResponse.setTotalElements(pagestories.getTotalElements());
		storyPageResponse.setTotalpages(pagestories.getTotalPages());
		storyPageResponse.setIslastpage(pagestories.isLast());

		return storyPageResponse;
	}

}
