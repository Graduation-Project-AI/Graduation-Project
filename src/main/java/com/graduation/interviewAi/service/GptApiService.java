package com.graduation.interviewAi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptApiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";

    public List<String> ask(String prompt) {
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Collections.singletonList(message));
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(GPT_API_URL, request, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            String content = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

            return parseToList(content);
        } catch (Exception e) {
            log.error("GPT 요청 실패: {}", e.getMessage());
            return List.of("GPT 질문 생성 실패");
        }
    }

    private List<String> parseToList(String content) {
        List<String> result = new ArrayList<>();
        String[] lines = content.split("\n");
        for (String line : lines) {
            String cleaned = line.replaceAll("^\\d+[).\\s]*", "").trim();
            if (!cleaned.isBlank()) {
                result.add(cleaned);
            }
        }
        return result;
    }

    public Map<String, String> parseEvaluationResult(String content) {
        Map<String, String> result = new LinkedHashMap<>();

        try {
            Matcher logicMatcher = Pattern.compile("논리성[:：]?\\s*(\\d+)/10").matcher(content);
            if (logicMatcher.find()) result.put("logicScore", logicMatcher.group(1));

            Matcher claMatcher = Pattern.compile("명확성[:：]?\\s*(\\d+)/10").matcher(content);
            if (claMatcher.find()) result.put("claScore", claMatcher.group(1));

            Matcher totalMatcher = Pattern.compile("총점[:：]?\\s*(\\d+)/10").matcher(content);
            if (totalMatcher.find()) result.put("totalScore", totalMatcher.group(1));

            Matcher evalMatcher = Pattern.compile("전체분석[:：]?\\s*(.*)").matcher(content);
            if (evalMatcher.find()) result.put("allEval", evalMatcher.group(1).trim());

            Matcher suggestionMatcher = Pattern.compile("개선방안[:：]?\\s*(.*)").matcher(content);
            if (suggestionMatcher.find()) result.put("suggestion", suggestionMatcher.group(1).trim());

            Matcher answerMatcher = Pattern.compile("개선답변[:：]?\\s*(.*)").matcher(content);
            if (answerMatcher.find()) result.put("impAnswer", answerMatcher.group(1).trim());

        } catch (Exception e) {
            log.error("GPT 응답 파싱 오류: {}", e.getMessage());
        }

        return result;
    }

}
