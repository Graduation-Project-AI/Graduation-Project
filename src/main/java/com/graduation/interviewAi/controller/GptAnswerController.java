package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.domain.Result;
import com.graduation.interviewAi.service.AnswerService;
import com.graduation.interviewAi.service.GptAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptAnswerController {

    private final AnswerService answerService;
    private final GptAnswerService gptAnswerService;

    @PostMapping("/analyze/{interviewId}")
    public Result analyzeAnswers(@PathVariable int interviewId) {
        // 1. interviewId로 답변+질문 리스트 조회
        List<AnswerWithQuestionDto> answers = answerService.getAnswersByInterviewId(interviewId);

        // 2. GPT 분석 호출하고 DB에 저장
        return gptAnswerService.analyzeAnswers(answers);
    }

    @GetMapping("/analyze/{interviewId}")
    public Result getResultByInterviewId(@PathVariable int interviewId) {
        return gptAnswerService.getResultByInterviewId(interviewId);
    }
}
