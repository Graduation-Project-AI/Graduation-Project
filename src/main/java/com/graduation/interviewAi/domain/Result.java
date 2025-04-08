package com.graduation.interviewAi.domain;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Result {
    private Integer resultId;
    private Integer logicScore;
    private float simScore;
    private Integer claScore;
    private Integer totalScore;
    private Timestamp createdAt;
    private Integer interviewId;
    private String suggestion;
    private String allEval;
    private String impAnswer;
}