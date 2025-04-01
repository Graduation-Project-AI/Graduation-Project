package com.graduation.interviewAi.service;

import org.springframework.stereotype.Service;
import com.graduation.interviewAi.domain.UserAccount;
import com.graduation.interviewAi.dto.KakaoUserDto;
import com.graduation.interviewAi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public KakaoUserDto loginOrRegister(KakaoUserDto user) {
        UserAccount existing = userMapper.findByEmail(user.getEmail());
        if (existing == null) {
            userMapper.insertUser(user);
            existing = userMapper.findByEmail(user.getEmail());
        }
        KakaoUserDto dto = new KakaoUserDto();
        dto.setUserId(existing.getUserId());
        dto.setName(existing.getName());
        dto.setEmail(existing.getEmail());
        return dto;
    }
}
