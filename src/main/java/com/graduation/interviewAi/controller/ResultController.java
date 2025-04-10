package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.InterviewWithAnswersDto;
import com.graduation.interviewAi.dto.ScoreRecordsDto;
import com.graduation.interviewAi.service.ResultService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
@Tag(name="ResultController", description="history 관련 API")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/{userId}")
    @Operation(summary = "이전 내역 불러오기")
    public List<ScoreRecordsDto> getResultsByUser(@PathVariable Integer userId) {
        return resultService.getScoreRecordsByUserId(userId);
    }

    @GetMapping("/{userId}/with-answers")
    @Operation(summary = "직군, 회사별 내역 가져오기")
    public List<InterviewWithAnswersDto> getInterviewsWithAnswers(@PathVariable Integer userId) {
        return resultService.getAllRecordByUserId(userId);
    }
}