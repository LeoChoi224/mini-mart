package com.zerobase.minimart.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMAIL_NOT_AUTHENTICATED("이메일 인증이 완료되지 않았습니다."),
    ALREADY_REGISTER_USER("이미 가입된 회원입니다."),
    WRONG_VERIFICATION("잘못된 인증 시도입니다."),
    EXPIRE_CODE("인증 시간이 만료되었습니다."),
    NOT_FOUND_USER("일치하는 회원이 없습니다."),
    LOGIN_CHECK_FAIL("아이디나 패스워드를 확인해 주세요."),
    ALREADY_VERIFY("이미 인증이 완료되었습니다."),
    NOT_ENOUGH_BALANCE("잔액이 부족합니다."),
    ALREADY_SELLER_PHONE("이미 해당 번호로 등록된 판매자 계정이 존재합니다.");

    private final String message;  // detail 대신 message로 변경해도 무방
}