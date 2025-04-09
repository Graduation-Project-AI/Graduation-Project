package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.ScoreRecordsDto;
import com.graduation.interviewAi.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/{userId}")
    public List<ScoreRecordsDto> getResultsByUser(@PathVariable Integer userId) {
        return resultService.getScoreRecordsByUserId(userId);
    }
}