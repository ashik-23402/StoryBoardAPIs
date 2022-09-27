package com.ashik.storyboard.payloads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StoryDTO {
	
	private Integer storyId;
	
	
	private String storyTitle;
	
	
	private String content;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date addedDate;
	
	private Integer totalUpvoted=0;
	
	private Integer totaldownvoted=0;
//	
//	
//	private CategoryDTO category;
//	
//	
	private UserDTO user;
	

}
