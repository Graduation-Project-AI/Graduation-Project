package com.graduation.interviewAi.service;

import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.mapper.UserMapper;
import com.graduation.interviewAi.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserMapper userMapper;
    private final JwtTokenService jwtTokenService;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_LOGIN_URL = "https://kauth.kakao.com/oauth/authorize";

    public String getKakaoLoginUrl() {
        return KAKAO_LOGIN_URL + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    public String getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = rt.postForEntity(KAKAO_TOKEN_URL, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    public KakaoUserDto getUserInfo(String accessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = rt.postForEntity(KAKAO_USER_INFO_URL, request, Map.class);

        Map<String, Object> body = response.getBody();
        Map<String, String> properties = (Map<String, String>) body.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");

        KakaoUserDto dto = new KakaoUserDto();
        dto.setName(properties.get("nickname"));
        dto.setEmail(kakaoAccount.get("email").toString());

        return dto;
    }

    public KakaoUserDto registerOrLoginWithCode(String code) {
        String token = getAccessToken(code);
        KakaoUserDto userInfo = getUserInfo(token);
        System.out.println("Access Token 받음: "+token);

        UserAccount existing = userMapper.findByEmail(userInfo.getEmail());
        if (existing == null) {
            userMapper.insertUser(userInfo);
            System.out.println("신규 회원 가입 완료: " + userInfo.getEmail());
            existing = userMapper.findByEmail(userInfo.getEmail());
        } else {
        	System.out.println("기존 회원 로그인: " + existing.getEmail());        	
        }
        KakaoUserDto result = new KakaoUserDto();
        result.setUserId(existing.getUserId());
        result.setName(existing.getName());
        result.setEmail(existing.getEmail());
        
        String jwt = jwtTokenService.generateToken(result);
        System.out.println("jwt토큰 생성: "+jwt);
        
        return result;
    }
}