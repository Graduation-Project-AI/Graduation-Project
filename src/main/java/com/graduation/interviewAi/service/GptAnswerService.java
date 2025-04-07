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

    public Result analyzeAnswers(List<AnswerWithQuestionDto> answers) {
        StringBuilder promptBuilder = new StringBuilder("""
        아래는 면접 질문과 면접자의 답변입니다.
    
        전체 답변에 대해 다음 항목을 평가해주세요:
    
        - 논리성 (10점 만점)
        - 명확성 (10점 만점)
        - 총점 (10점 만점)
        - 전체 답변에 대한 분석 (자유롭게 기술)
    
        반드시 **모든 답변에 대해** 개선방안을 작성해주세요.
        단 하나의 질문이라도 개선 포인트가 없어 보이더라도, **아주 사소한 부분이라도 찾아서** 개선할 수 있는 방안을 제시해주세요.
        이후 각 질문에 대해 그 개선방안을 반영한 **개선된 답변**을 작성해주세요.
    
        반드시 아래 형식으로 출력해주세요:
    
        [전체 질문에 대한 평가]
        - 논리성: ?/10
        - 명확성: ?/10
        - 총점: ?/10
        - 전체분석: (여러 줄 가능)
    
        [각 답변에 대한 개선방안과 개선 답변]
        - 개선방안:
          답변 1.(개선 방안)
          답변 2. (개선 방안)
        - 개선답변:
          답변 1. (개선된 답변)
          답변 2. (개선된 답변)
          ...
        """);

        List<AbstractMap.SimpleEntry<Integer, String>> answerList = new ArrayList<>();

        // Answer table 에서 타 답변 호출 시 필요
        int interviewId = answers.get(0).getInterviewId();

        for (int i = 0; i < answers.size(); i++) {
            AnswerWithQuestionDto dto = answers.get(i);
            answerList.add(new AbstractMap.SimpleEntry<>(dto.getQuestionId(), dto.getAcontent()));
            promptBuilder.append("질문 ").append(i + 1).append(": ").append(dto.getQcontent()).append("\n");
            promptBuilder.append("답변 ").append(i + 1).append(": ").append(dto.getAcontent()).append("\n\n");
        }

        String prompt = promptBuilder.toString();
        // log.info("GPT에 전달할 프롬프트:\n{}", prompt);

        List<String> response = gptApiService.ask(prompt);
        String fullResponse = String.join("\n", response);
        log.info(fullResponse);

        Map<String, String> parsed = gptApiService.parseEvaluationResult(fullResponse);

        // 질문 ID별 기존 답변들과 유사도 계산
        double totalAvgSimilarity = 0.0;
        int questionCount = 0;

        for (AbstractMap.SimpleEntry<Integer, String> entry : answerList) {
            Integer questionId = entry.getKey();
            String currentAnswer = entry.getValue();

            // 현재 인터뷰 ID와 다른 답변들만 조회
            List<String> pastAnswers = AnswerMapper.findAnswersByQuestionId(questionId, interviewId);

            double totalSimilarity = 0.0;
            int count = 0;

            for (String pastAnswer : pastAnswers) {
                double similarity = gptApiService.calculateEmbeddingSimilarity(currentAnswer, pastAnswer);
                totalSimilarity += similarity;
                count++;
                log.info("질문 ID {}: GPT 임베딩 유사도 = {}", questionId, similarity);
            }

            double avgSimilarity = (count == 0) ? 0.0 : totalSimilarity / count;
            log.info("질문 ID {}: 평균 유사도 = {}", questionId, avgSimilarity);

            totalAvgSimilarity += avgSimilarity;
            questionCount++;
        }

        // 전체 질문에 대한 평균 유사도 계산
        float overallSimilarity = (questionCount == 0)
                ? 0.0f
                : (float) totalAvgSimilarity / questionCount;
        float percentageSimilarity = overallSimilarity * 100.0f;
        float roundedSimilarity = Math.round(percentageSimilarity * 100) / 100.0f;

        log.info("전체 질문에 대한 평균 유사도 (퍼센트) = {}", roundedSimilarity);



        // Result 객체 생성 및 세팅
        Result result = Result.builder()
                .logicScore(Integer.parseInt(parsed.getOrDefault("logicScore", "0")))
                .claScore(Integer.parseInt(parsed.getOrDefault("claScore", "0")))
                .totalScore(Integer.parseInt(parsed.getOrDefault("totalScore", "0")))
                .allEval(parsed.getOrDefault("allEval", ""))
                .suggestion(parsed.getOrDefault("suggestion", ""))
                .impAnswer(parsed.getOrDefault("impAnswer", ""))
                .interviewId(answers.get(0).getInterviewId()) // 첫 번째 답변 기준
                .simScore(roundedSimilarity)
                .build();

        ResultMapper.saveResult(result);

        return result;
    }

    public Result getResultByInterviewId(int interviewId) {
        return ResultMapper.findResultByInterviewId(interviewId);
    }

}
