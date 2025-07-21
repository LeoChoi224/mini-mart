package com.zerobase.minimart.user.model;

import lombok.Data;

@Data
public class UserInput {

    private String userId;
    private String userName;
    private String phoneNumber;
    private String password;
    private String rePassword;

}