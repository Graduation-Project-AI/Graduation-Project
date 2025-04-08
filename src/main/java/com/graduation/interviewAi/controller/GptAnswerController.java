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
    public List<Result> analyzeAnswers(@PathVariable int interviewId) {
        List<AnswerWithQuestionDto> answers = answerService.getAnswersByInterviewId(interviewId);
        return gptAnswerService.analyzeAnswers(answers);
    }

    @GetMapping("/analyze/{interviewId}")
    public List<Result> getResultByInterviewId(@PathVariable int interviewId) {
        return gptAnswerService.getResultByInterviewId(interviewId);
    }
}
