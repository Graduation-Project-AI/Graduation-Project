package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Result;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.mapper.ResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptAnswerService {

    private final GptApiService gptApiService;
    private final ResultMapper ResultMapper;

    public Map<String, String> analyzeAnswers(List<AnswerWithQuestionDto> answers) {
        StringBuilder promptBuilder = new StringBuilder("""
        아래는 면접 질문과 면접자의 답변입니다.
        
        전체 질문과 답변에 대해 다음을 간단히 평가해주세요:
        
        - 논리성 (10점 만점)
        - 명확성 (10점 만점)
        - 총점 (10점 만점)

        마지막에는 전체 답변의 개선방안과 개선답변을 제시해주세요.
        
        형식:
        [전체 질문]
        - 논리성: ?/10
        - 명확성: ?/10
        - 총점: ?/10
        - 전체분석: ...
        
        [전체 개선]
        - 개선방안: ...
        - 개선답변: ...
        """);

        for (int i = 0; i < answers.size(); i++) {
            AnswerWithQuestionDto dto = answers.get(i);
            promptBuilder.append("질문 ").append(i + 1).append(": ").append(dto.getQcontent()).append("\n");
            promptBuilder.append("답변 ").append(i + 1).append(": ").append(dto.getAcontent()).append("\n\n");
        }

        String prompt = promptBuilder.toString();
        // log.info("GPT에 전달할 프롬프트:\n{}", prompt);

        List<String> response = gptApiService.ask(prompt);
        String fullResponse = String.join("\n", response);
        log.info(fullResponse);

        Map<String, String> parsed = gptApiService.parseEvaluationResult(fullResponse);

        // Result 객체 생성 및 세팅
        Result result = Result.builder()
                .logicScore(Integer.parseInt(parsed.getOrDefault("logicScore", "0")))
                .claScore(Integer.parseInt(parsed.getOrDefault("claScore", "0")))
                .totalScore(Integer.parseInt(parsed.getOrDefault("totalScore", "0")))
                .allEval(parsed.getOrDefault("allEval", ""))
                .suggestion(parsed.getOrDefault("suggestion", ""))
                .impAnswer(parsed.getOrDefault("impAnswer", ""))
                .interviewId(answers.get(0).getInterviewId()) // 첫 번째 답변 기준
                .build();

        ResultMapper.saveResult(result);
        return parsed;
    }

}
