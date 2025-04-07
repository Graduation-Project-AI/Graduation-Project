package com.graduation.interviewAi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FeedbackRequestDto {
    private Integer interviewId;
    private Integer aiScore;
    @JsonProperty("qScore")
    private Integer qScore;
}