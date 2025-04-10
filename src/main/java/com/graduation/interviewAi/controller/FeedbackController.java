package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.FeedbackRequestDto;
import com.graduation.interviewAi.service.FeedbackService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name="FeedbackController", description="피드백 점수 저장하는 API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(@RequestBody FeedbackRequestDto dto) {
        System.out.println("DTO: " + dto);
        System.out.println("qScore: " + dto.getQScore());
        
        feedbackService.saveFeedback(dto);
        return ResponseEntity.ok("피드백 저장 완료");
    }
}
