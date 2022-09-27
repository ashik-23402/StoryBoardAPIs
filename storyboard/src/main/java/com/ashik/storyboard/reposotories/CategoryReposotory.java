package com.ashik.storyboard.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashik.storyboard.entities.Category;

public interface CategoryReposotory extends JpaRepository<Category,Integer> {

}
