package com.zerobase.minimart.user.service;

import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.model.UserInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 회원 가입
     */
    boolean signUp(UserInput parameter);

    /**
     * 회원 마이 페이지
     */
    UserDto info(String userId);

    /**
     * 회원 정보 수정
     */
    String updateUserInfo(UserInput parameter);


    void applySeller(String userId);
}
