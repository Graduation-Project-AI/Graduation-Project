package com.graduation.interviewAi.domain;
import lombok.Data;

@Data
public class Answer {
    private Integer answerId;
    private Integer questionId;
    private String aContent;
    private Integer interviewId;
}