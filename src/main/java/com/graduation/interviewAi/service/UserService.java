package com.graduation.interviewAi.service;

import org.springframework.stereotype.Service;
import com.graduation.interviewAi.domain.UserAccount;
import com.graduation.interviewAi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public UserAccount loginOrRegister(UserAccount user) {
        UserAccount existing = userMapper.findByEmail(user.getEmail());
        if (existing == null) {
            userMapper.insertUser(user);
            return user;
        }
        return existing;
    }
}
