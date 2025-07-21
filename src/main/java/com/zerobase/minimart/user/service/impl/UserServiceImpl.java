package com.zerobase.minimart.user.service.impl;

import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.exception.ErrorCode;
import com.zerobase.minimart.user.dto.UserInput;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.repository.UserRepository;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

//    private final MailService mailService;

    private final UserRepository userRepository;

    @Override
    public boolean signUp(UserInput parameter) {

        Optional<User> existing = userRepository.findByUserId(parameter.getUserId());
        if (existing.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }


        String encodedPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();


        try {

            System.out.println("요청" + parameter);

            User user = User.builder()
                    .userId(parameter.getUserId())
                    .userName(parameter.getUserName())
                    .password(encodedPassword)
                    .phoneNumber(parameter.getPhoneNumber())
                    .regDt(LocalDateTime.now())
    //                .emailAuthYn(false)
    //                .emailAuthKey(uuid)
                    .build();

            userRepository.save(user);

//        mailService.sendSignupEmail(user.getUserId());

            return true;
        } catch (Exception e) {
            System.out.println("오류" + e.getMessage());
            return false;
        }
    }

//    private String getRandomCode() {
//        return RandomStringUtils.random(10, true, true);
//    }
//
//    private String getVerificationEmailBody(String email, String name, String type, String code) {
//        StringBuilder builder = new StringBuilder();
//        return builder.append("Hello ")
//                .append(name)
//                .append("! Please Click Link for verification.\n\n")
//                .append("http://localhost:8080/user/signup/" + type + "/verify?email=")
//                .append(email)
//                .append("&code=")
//                .append(code).toString();
//    }

}