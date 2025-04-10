package com.graduation.interviewAi.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class InterviewWithJobDto {
    private Integer interviewId;
    private String company;
    private Integer jobId;
    private String job;
    private Integer resumeId;
    private Timestamp createdAt;
    private Integer userId;
}
