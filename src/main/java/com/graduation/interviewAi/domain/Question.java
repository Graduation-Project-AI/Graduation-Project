package com.graduation.interviewAi.domain;
import lombok.Data;

@Data
public class Question {
    private Integer questionId;
    private Integer jobId;
    private Integer category; // 1 ~ 4
    private String qcontent;
}