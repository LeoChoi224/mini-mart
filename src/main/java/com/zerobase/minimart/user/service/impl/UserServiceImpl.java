package com.zerobase.minimart.user.service.impl;

import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.exception.ErrorCode;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.repository.UserRepository;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

//    private final MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public UserDto info(String userId) {

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            return null;
        }

        User user = optionalUser.get();

        return UserDto.of(user);
    }

    @Override
    public String updateUserInfo(UserInput parameter) {

        Optional<User> optionalUser = userRepository.findByUserId(parameter.getUserId());

        if (optionalUser.isEmpty()) {
            return "fail";
        }

        User user = optionalUser.get();

        if (StringUtils.hasText(parameter.getPassword())) {
            String encPassword = passwordEncoder.encode(parameter.getPassword());
            user.setPassword(encPassword);
        }

        user.setPhoneNumber(parameter.getPhoneNumber());

        // 3. 저장
        userRepository.save(user);

        return "success";
    }

    @Override
    public void applySeller(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String phone = user.getPhoneNumber();

            // 해당 전화번호로 이미 판매자 등록된 계정이 있는지 확인
            boolean duplicateSeller = userRepository.existsByPhoneNumberAndSellerYnTrue(phone);

            if (duplicateSeller) {
                // ❌ 동일 번호로 이미 판매자인 경우 - 예외 발생
                throw new CustomException(ErrorCode.ALREADY_SELLER_PHONE);
            }

            // ✅ 아직 판매자 등록된 계정이 없을 경우
            user.setSellerYn(true);
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUserId(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        User user = optionalUser.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (user.isSellerYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), grantedAuthorities);
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