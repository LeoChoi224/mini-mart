package com.zerobase.minimart.util;

import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserService userService;

    // 현재 로그인된 사용자 객체 반환
    public User getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName(); // 이메일 또는 username
        return userService.findByUserId(userId);
    }
}