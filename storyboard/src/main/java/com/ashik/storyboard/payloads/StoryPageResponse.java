package com.ashik.storyboard.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StoryPageResponse {
	
	private List<StoryDTO> contents;
	
	private int pageNumber;
	private int pageSize;
	private Long totalElements;
	private int totalpages;
	private boolean islastpage;
	

}
