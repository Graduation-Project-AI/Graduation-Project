package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.service.KakaoAuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.graduation.interviewAi.service.JwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="KakaoAuthController", description="카카오 로그인 관련 API")
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

        String redirectUrl = UriComponentsBuilder
                .fromUriString("http://localhost:3000/kakao/redirect")
                .queryParam("token", token)
                .queryParam("userId", user.getUserId())
                .queryParam("name", user.getName())
                .build()
                .toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}