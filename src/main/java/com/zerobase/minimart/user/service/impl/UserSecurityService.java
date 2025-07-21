package com.zerobase.minimart.user.service.impl;

import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUserId())
                        .password(user.getPassword()) // 이미 BCrypt 암호화된 패스워드
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}