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

            // 전체분석 ~ 개선방안 전까지
            Matcher evalMatcher = Pattern.compile("전체분석[:：]?\\s*([\\s\\S]*?)개선방안[:：]", Pattern.DOTALL).matcher(content);
            if (evalMatcher.find()) result.put("allEval", evalMatcher.group(1).trim());

            // 개선방안 ~ 개선답변 전까지
            Matcher suggestionMatcher = Pattern.compile("- 개선방안[:：]?\\s*([\\s\\S]*?)개선답변[:：]", Pattern.DOTALL).matcher(content);
            if (suggestionMatcher.find()) result.put("suggestion", suggestionMatcher.group(1).trim());

            // 개선답변부터 끝까지
            Matcher answerMatcher = Pattern.compile("개선답변[:：]?\\s*([\\s\\S]*)", Pattern.DOTALL).matcher(content);
            if (answerMatcher.find()) result.put("impAnswer", answerMatcher.group(1).trim());

        } catch (Exception e) {
            log.error("GPT 응답 파싱 오류: {}", e.getMessage());
        }

        return result;
    }

    private List<Double> getEmbeddingVector(String text) {
        String url = "https://api.openai.com/v1/embeddings";

        Map<String, Object> body = new HashMap<>();
        body.put("model", "text-embedding-ada-002");
        body.put("input", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
            return (List<Double>) data.get(0).get("embedding");
        } catch (Exception e) {
            log.error("임베딩 벡터 요청 실패: {}", e.getMessage());
            return null;
        }
    }

    public double calculateEmbeddingSimilarity(String text1, String text2) {
        List<Double> vector1 = getEmbeddingVector(text1);
        List<Double> vector2 = getEmbeddingVector(text2);

        if (vector1 == null || vector2 == null || vector1.size() != vector2.size()) {
            return 0.0;
        }

        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            double a = vector1.get(i);
            double b = vector2.get(i);
            dot += a * b;
            normA += a * a;
            normB += b * b;
        }

        return normA == 0 || normB == 0 ? 0.0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
