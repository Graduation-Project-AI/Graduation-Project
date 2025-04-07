package com.graduation.interviewAi.dto;

import lombok.Data;

@Data
public class QuestionDto {
	private Integer jobId;
    private Integer category;
    private String qcontent;
    private Integer interviewSourceId;
}
