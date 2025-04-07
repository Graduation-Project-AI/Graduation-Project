package com.graduation.interviewAi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.interviewAi.service.GptQuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptQuestionController {

    private final GptQuestionService gptQuestionService;

    @PostMapping("/resume-question")
    public ResponseEntity<List<String>> generateResumeQuestions(@RequestParam("interviewId") int interviewId) {
        List<String> questions = gptQuestionService.generateAndSaveResumeQuestions(interviewId);
        return ResponseEntity.ok(questions);
    }
}
