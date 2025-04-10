package com.graduation.interviewAi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduation.interviewAi.domain.UserAccount;
import com.graduation.interviewAi.dto.KakaoLoginRequest;
import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name="UserController", description="유저 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody KakaoLoginRequest request) {
        // accessToken을 통해 카카오 유저 정보 가져오기 생략
        KakaoUserDto user = new KakaoUserDto();
        user.setEmail("kakao@example.com");
        user.setName("홍길동");

        KakaoUserDto saved = userService.loginOrRegister(user);
        return ResponseEntity.ok(saved);
    }
}

