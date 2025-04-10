package com.graduation.interviewAi.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.interviewAi.dto.InterviewStartRequest;
import com.graduation.interviewAi.service.InterviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
@Tag(name="InterviewController", description="interview 시작 API")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/start")
    public ResponseEntity<?> startInterview(@RequestBody InterviewStartRequest request) {
        Integer interviewId = interviewService.startInterview(request);
        return ResponseEntity.ok(Map.of("interviewId", interviewId));
    }
}
