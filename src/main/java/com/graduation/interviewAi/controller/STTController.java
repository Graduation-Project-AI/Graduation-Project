package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.service.STTService;
import com.graduation.interviewAi.mapper.AnswerMapper;
import com.graduation.interviewAi.domain.Answer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/stt")
@RequiredArgsConstructor
public class STTController {

    private final STTService sttService;
    private final AnswerMapper answerMapper;

    @PostMapping("/answer")
    public ResponseEntity<String> transcribeAndSaveAnswer(
            @RequestParam("file") MultipartFile file,
            @RequestParam("interviewId") int interviewId,
            @RequestParam("questionId") int questionId
    ) throws Exception {
        // 1. 해당 answer 조회
        Answer answer = answerMapper.findByInterviewIdAndQuestionId(interviewId, questionId);
        if (answer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 answer가 존재하지 않습니다.");
        }

        // 2. 녹음한 파일 임시 저장
        File tempFile = File.createTempFile("audio", ".wav");
        file.transferTo(tempFile);

        // 3. STT 변환
        String sttResult = sttService.transcribeAudio(tempFile.getAbsolutePath());

        // 4. DB update
        answerMapper.updateAcontentByInterviewAndQuestion(interviewId, questionId, sttResult);

        return ResponseEntity.ok("STT 변환 및 저장 완료");
    }

}
