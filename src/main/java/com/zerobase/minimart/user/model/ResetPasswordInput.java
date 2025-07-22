package com.zerobase.minimart.user.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {

    private String userId;
    private String userName;

    private String uuid;
    private String password;
    private String rePassword;

}
