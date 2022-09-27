package com.ashik.storyboard.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Stories")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Story {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer storyId;
	
	@Column(name = "storyTitle")
	private String storyTitle;
	
	@Column(length = 10000)
	private String content;
	
	private Date addedDate;
	
	private Integer totalUpvoted=0;
	
	private Integer totaldownvoted=0;
	
//	@ManyToOne
//	@JoinColumn(name = "category_id")
//	private Category category;
	
	@ManyToOne
	private User user;
	
	
	

}
