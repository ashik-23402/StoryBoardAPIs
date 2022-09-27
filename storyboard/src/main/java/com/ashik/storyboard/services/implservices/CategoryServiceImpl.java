package com.ashik.storyboard.services.implservices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashik.storyboard.entities.Category;
import com.ashik.storyboard.exceptions.ResourceNotFoundException;
import com.ashik.storyboard.payloads.CategoryDTO;
import com.ashik.storyboard.reposotories.CategoryReposotory;
import com.ashik.storyboard.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryReposotory categoryReposotory;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category cat= this.modelMapper.map(categoryDTO, Category.class);
		Category addedCategory= this.categoryReposotory.save(cat);
		
		return this.modelMapper.map(addedCategory,CategoryDTO.class);
	}
	
	

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category cat= this.categoryReposotory.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		
		 cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		 cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		 
		 Category addedCategory =  this.categoryReposotory.save(cat);
		
		return this.modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category cat= this.categoryReposotory.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		
		this.categoryReposotory.delete(cat);
		
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat= this.categoryReposotory.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		
		
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getallCategories() {
		// TODO Auto-generated method stub
		
		 List<Category>allCategories =  this.categoryReposotory.findAll();
		 
		 List<CategoryDTO>categoryDTOs =  allCategories.stream().map((cat)->
		  this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		
		
		return categoryDTOs;
	}

}
