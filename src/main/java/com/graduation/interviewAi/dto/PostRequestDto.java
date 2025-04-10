package com.graduation.interviewAi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostRequestDto {
	//post 작성, 수정할때 서버로 보내는 데이터
	private Integer postId;
    private String postName;
    @JsonProperty("pContent")
    private String pContent;
    private Integer userId;
    private Integer userRole;
}
