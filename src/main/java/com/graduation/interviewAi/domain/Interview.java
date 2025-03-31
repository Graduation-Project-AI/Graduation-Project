package com.graduation.interviewAi.domain;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Interview {
    private Integer interviewId;
    private String company;
    private Integer jobId;
    private Integer resumeId;
    private Timestamp createdAt;
    private Integer userId;
}