package com.graduation.interviewAi.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class ScoreRecordsDto {
    private Integer interviewId;
    private Double simScore;
    private Double logicScore;
    private Double claScore;
    private Timestamp createdAt;
}
