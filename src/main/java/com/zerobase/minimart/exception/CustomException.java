package com.zerobase.minimart.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 바로 enum의 message를 getMessage로 사용
        this.errorCode = errorCode;
    }
}