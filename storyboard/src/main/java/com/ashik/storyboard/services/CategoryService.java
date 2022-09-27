package com.ashik.storyboard.services;

import java.util.List;

import com.ashik.storyboard.payloads.CategoryDTO;

public interface CategoryService {
	
	//create
	
	CategoryDTO createCategory(CategoryDTO categoryDTO); 
		
	//update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId); 
	
	//delete
	
	void deleteCategory(Integer categoryId); 
	
	//get
	CategoryDTO getCategory(Integer categoryId); 
	
	//getall
	
	List<CategoryDTO> getallCategories();

}
