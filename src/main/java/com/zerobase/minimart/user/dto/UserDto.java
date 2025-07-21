package com.zerobase.minimart.user.dto;

import com.zerobase.minimart.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    String userId;
    String userName;
    String phoneNumber;
    String password;
    LocalDateTime regDt;

//    boolean emailAuthYn;
//    LocalDateTime emailAuthDt;
//    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;

    boolean sellerYn;
//    String userStatus;

    // 추가 컬럼
    long totalCount;
    long seq;

    public static UserDto of(User user) {

        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
//                .password(user.getPassword())

                .regDt(user.getRegDt())
//                .emailAuthYn(user.isEmailAuthYn())
//                .emailAuthDt(user.getEmailAuthDt())
//                .emailAuthKey(user.getEmailAuthKey())

                .resetPasswordKey(user.getResetPasswordKey())
                .resetPasswordLimitDt(user.getResetPasswordLimitDt())

//                .adminYn(user.isAdminYn())
//                .userStatus(user.getUserStatus())

                .build();
    }


}
