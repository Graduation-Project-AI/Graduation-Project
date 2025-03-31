package com.graduation.interviewAi.dto;
import lombok.Data;

@Data
public class AnswerRequestDto {
    private Integer interviewId;
    private Integer questionId;
    private String aContent;
}