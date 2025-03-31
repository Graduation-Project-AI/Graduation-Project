package com.graduation.interviewAi.domain;
import lombok.Data;
@Data
public class Feedback {
    private Integer feedbackId;
    private Integer aiScore;
    private Integer qScore;
    private Integer interviewId;
}