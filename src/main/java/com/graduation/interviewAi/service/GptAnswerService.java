package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Result;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.mapper.ResultMapper;
import com.graduation.interviewAi.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptAnswerService {

    private final GptApiService gptApiService;
    private final ResultMapper ResultMapper;
    private final AnswerMapper AnswerMapper;

    public List<Result> analyzeAnswers(List<AnswerWithQuestionDto> answers) {

        // 프롬프트 템플릿은 밖에 선언
        String promptTemplate = """
        아래는 면접 질문과 면접자의 답변입니다.
    
        답변에 대해 다음 항목을 평가하여 작성해주세요.
        반드시 개선방안과 개선답변이 존재해야 합니다.
    
        - 논리성: ?/10
        - 명확성: ?/10
        - 개선방안:
        - 개선답변:
    
        질문: %s
        답변: %s
        """;

        List<Result> resultList = new ArrayList<>();

        for (AnswerWithQuestionDto dto : answers) {
            int questionId = dto.getQuestionId();
            int interviewId = dto.getInterviewId();
            String question = dto.getQcontent();
            String answer = dto.getAcontent();

            // 1. 프롬프트 생성
            String prompt = String.format(promptTemplate, question, answer);

            // 2. GPT 평가 요청
            List<String> gptResponse = gptApiService.ask(prompt);
            String responseStr = String.join("\n", gptResponse);
            Map<String, String> parsed = gptApiService.parseEvaluationResult(responseStr);

            // 3. 유사도 계산 (같은 질문, 다른 인터뷰들의 답변과 비교)
            List<String> pastAnswers = AnswerMapper.findAnswersByQuestionId(questionId, interviewId);
            double totalSim = 0.0;
            int count = 0;

            for (String past : pastAnswers) {
                double sim = gptApiService.calculateEmbeddingSimilarity(answer, past);
                totalSim += sim;
                count++;
                log.info("질문 ID {} - GPT 유사도: {}", questionId, sim);
            }

            float avgSim = (count == 0) ? 0.0f : (float) (totalSim / count);
            float simPercentage = Math.round(avgSim * 10000) / 100.0f; // 소수점 2자리

            // 4. Result 객체 생성 및 저장
            Result result = Result.builder()
                    .logicScore(Integer.parseInt(parsed.getOrDefault("logicScore", "0")))
                    .claScore(Integer.parseInt(parsed.getOrDefault("claScore", "0")))
                    .interviewId(interviewId)
                    .suggestion(parsed.getOrDefault("suggestion", ""))
                    .impAnswer(parsed.getOrDefault("impAnswer", ""))
                    .simScore(simPercentage)
                    .questionId(questionId)
                    .build();

            ResultMapper.saveResult(result);
            resultList.add(result);

            log.info("질문 ID {} 결과 저장 완료: 논리성 {}, 명확성 {}, 유사도 {}%",
                    questionId, result.getLogicScore(), result.getClaScore(), result.getSimScore());
        }

        return resultList;
    }


    public List<Result> getResultByInterviewId(int interviewId) {
        return ResultMapper.findResultByInterviewId(interviewId);
    }

}
