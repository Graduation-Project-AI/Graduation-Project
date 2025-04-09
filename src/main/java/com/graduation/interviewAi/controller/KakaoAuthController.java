package com.graduation.interviewAi.controller;

import com.google.api.client.util.Value;
import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.service.KakaoAuthService;
import com.graduation.interviewAi.service.JwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class KakaoAuthController {
	
//	@Value("${frontend.redirect-uri}")
//	private String frontendRedirectUri;
	private final String frontendRedirectUri = "http://localhost:3000/kakao/redirect";


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
    public void kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        KakaoUserDto user = kakaoAuthService.registerOrLoginWithCode(code);
        String token = jwtTokenService.generateToken(user);

        // 프론트로 쿼리스트링에 담아서 리디렉션
        String redirectUrl = UriComponentsBuilder
                .fromUriString(frontendRedirectUri)
                .queryParam("token", token)
                .queryParam("userId", user.getUserId())
                .queryParam("name", URLEncoder.encode(user.getName(), "UTF-8"))
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }



}