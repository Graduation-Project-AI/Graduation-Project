package com.graduation.interviewAi.dto;

import lombok.Data;

@Data
public class ResultRequestDto {
    private Integer interviewId;
    private Integer logicScore;
    private Integer simScore;
    private Integer claScore;
    private Integer totalScore;
    private String suggestion;
    private String allEval;
    private String impAnswer;
}