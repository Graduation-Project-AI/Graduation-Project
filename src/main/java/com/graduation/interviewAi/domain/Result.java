package com.graduation.interviewAi.domain;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Result {
    private Integer resultId;
    private Integer logicScore;
    private Integer simScore;
    private Integer claScore;
    private Integer totalScore;
    private Timestamp createdAt;
    private Integer interviewId;
    private String suggestion;
    private String allEval;
    private String impAnswer;
}