package com.graduation.interviewAi.dto;

import lombok.Data;

@Data
public class FeedbackRequestDto {
    private Integer interviewId;
    private Integer aiScore;
    private Integer qScore;
}