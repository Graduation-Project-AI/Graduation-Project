package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.service.STTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class STTController {

    @Autowired
    private STTService sttService;

    @GetMapping("/api/test-stt")
    public String testSTT() throws Exception {
        // .wav file name in src\main\resources\audio
        return sttService.transcribeAudio("stt_final.wav");
    }
}
