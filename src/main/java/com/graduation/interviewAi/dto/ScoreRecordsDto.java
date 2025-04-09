package com.graduation.interviewAi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ScoreRecordsDto {
    private Integer interviewId;
    private Double simScore;
    private Double logicScore;
    private Double claScore;
    private LocalDateTime createdAt;
}
