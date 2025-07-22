package com.zerobase.minimart.user.entity;

public interface UserCode {

    /**
     * 현재 가입 요청중
     */
    String USER_STATUS_REQ = "REQ";

    /**
     * 현재 이용중인 상태
     */
    String USER_STATUS_ING = "ING";

    /**
     * 현재 정지된 상태
     */
    String USER_STATUS_STOP = "STOP";
}
