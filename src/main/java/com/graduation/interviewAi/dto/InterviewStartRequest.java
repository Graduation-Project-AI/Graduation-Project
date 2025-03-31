package com.graduation.interviewAi.dto;
import java.util.List;

import lombok.Data;

@Data
public class InterviewStartRequest {
    private String company;
    private Integer jobId;
    private Integer userId;
    private List<ResumeListDto> resume;
}