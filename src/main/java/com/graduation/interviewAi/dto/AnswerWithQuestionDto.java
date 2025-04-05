package com.graduation.interviewAi.dto;
import lombok.Data;

@Data
public class AnswerWithQuestionDto {
    // Answer 관련 필드
    private int answerId;
    private String acontent;
    private int interviewId;

    // Question 관련 필드
    private int questionId;
    private String qcontent;
    private int category;
    private int jobId;
    private int interviewSourceId;
}

