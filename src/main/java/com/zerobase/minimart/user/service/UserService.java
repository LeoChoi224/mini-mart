package com.zerobase.minimart.user.service;

import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.model.ResetPasswordInput;
import com.zerobase.minimart.user.model.UserInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 회원 가입
     */
    boolean signUp(UserInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함.
     */
    boolean emailAuth(String uuid);

    /**
     * 회원 마이 페이지
     */
    UserDto info(String userId);

    /**
     * 회원 정보 수정
     */
    String updateUserInfo(UserInput parameter);

    /**
     * 입력받은 uuid값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);

    /**
     * 입력 받은 uuid에 대해서 password로 초기화 함
     */
    boolean resetPassword(String uuid, String password);

    /**
     * 판매자 계정 신청
     */
    void applySeller(String userId);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 마이 페이지 비밀번호 변경
     */
    void updatePassword(String userId, String newPassword);

    /**
     * 마이 페이지 전화번호 변경
     */
    void updatePhoneNumber(String userId, String newPhoneNumber);
}