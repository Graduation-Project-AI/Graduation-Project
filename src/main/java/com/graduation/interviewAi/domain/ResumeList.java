package com.graduation.interviewAi.domain;
import lombok.Data;

@Data
public class ResumeList {
    private Integer resumeListId;
    private Integer resumeId;
    private String resumeQuestion;
    private String resumeAnswer;
}
