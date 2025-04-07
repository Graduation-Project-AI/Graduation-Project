package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    // 인터뷰 ID로 답변 + 질문 정보 조회
    @GetMapping("/interview/{interviewId}")
    public List<AnswerWithQuestionDto> getAnswersByInterviewId(@PathVariable int interviewId) {
        return answerService.getAnswersByInterviewId(interviewId);
    }
}
