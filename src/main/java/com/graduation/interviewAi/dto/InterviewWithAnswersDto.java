package com.graduation.interviewAi.dto;

import lombok.Data;

import java.util.List;
import java.sql.Timestamp;

@Data
public class InterviewWithAnswersDto {
    private Integer interviewId;
    private String company;
    private Integer jobId;
    private Integer resumeId;
    private Timestamp createdAt;
    private Integer userId;
    private List<AnswerWithQuestionDto> answers;
}
