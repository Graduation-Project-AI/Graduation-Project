package com.graduation.interviewAi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.interviewAi.dto.QuestionDto;
import com.graduation.interviewAi.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

 private final QuestionService questionService;

 @GetMapping("/list")
 public ResponseEntity<List<QuestionDto>> getInterviewQuestions(
		    @RequestParam(name = "interviewId") int interviewId,
		    @RequestParam(name = "count") int count) {
     return ResponseEntity.ok(questionService.getQuestions(interviewId, count));
 }
}
