package com.zerobase.minimart.user.service;

import com.zerobase.minimart.user.dto.UserInput;

public interface UserService {

    /**
     * 회원 가입
     */
    boolean signUp(UserInput parameter);
}
