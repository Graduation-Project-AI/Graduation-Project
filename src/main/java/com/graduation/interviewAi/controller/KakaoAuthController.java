package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.service.KakaoAuthService;
import com.graduation.interviewAi.service.JwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenService jwtTokenService;

    // 1. 로그인 URL로 리디렉트 (서버가 직접 인가 코드 받게 함)
    @GetMapping("/kakao/login")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String redirectUrl = kakaoAuthService.getKakaoLoginUrl();
        response.sendRedirect(redirectUrl);
    }

    // 2. 카카오 콜백 → 서버가 모든 로직 처리 후 JWT 반환
    @GetMapping("/kakao/callback")
    public ResponseEntity<Map<String, Object>> kakaoCallback(@RequestParam("code") String code) {
        KakaoUserDto user = kakaoAuthService.registerOrLoginWithCode(code);
        String token = jwtTokenService.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }
}