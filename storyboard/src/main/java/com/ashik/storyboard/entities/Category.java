package com.ashik.storyboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@Getter
@Setter	
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name = "Title")
	private String categoryTitle;
	
	@Column(name = "Description")
	private String categoryDescription;
	
//	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY )
//	private List<Story>stories = new ArrayList<>();

}
