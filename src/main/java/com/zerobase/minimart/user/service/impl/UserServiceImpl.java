package com.zerobase.minimart.user.service.impl;

import com.zerobase.minimart.components.MailService;
import com.zerobase.minimart.config.UserPrincipal;
import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.exception.ErrorCode;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.model.ResetPasswordInput;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.repository.UserRepository;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                    .emailAuthYn(false)
                    .emailAuthKey(uuid)
                    .userStatus(User.USER_STATUS_REQ)
                    .build();

            userRepository.save(user);

            String email = parameter.getUserId();
            String subject = "[mini-mart] 사이트 가입을 축하드립니다. ";
            String text = "<p>[mini-mart] 사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료 하세요. </p>"
                    + "<div><a target='_blank' href='http://localhost:8080/user/email_auth?uuid=" + uuid + "'> 가입 완료 </a></div>";
            mailService.sendHtmlMail(email, subject, text);

            return true;
        } catch (Exception e) {
            System.out.println("오류" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<User> optionalUser = userRepository.findByEmailAuthKey(uuid);
        if (!optionalUser.isPresent()) {
            return false;
        }

        User user = optionalUser.get();

        if (user.isEmailAuthYn()) {
            return false;
        }

        user.setUserStatus(User.USER_STATUS_ING);
        user.setEmailAuthYn(true);
        user.setEmailAuthDt(LocalDateTime.now());
        userRepository.save(user);

        return true;
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
    public boolean checkResetPassword(String uuid) {
        Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        LocalDateTime limit = user.getResetPasswordLimitDt();

        if (limit == null || limit.isBefore(LocalDateTime.now())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String newPassword) {
        Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        if (user.getResetPasswordLimitDt() == null || user.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            return false;
        }

        String encrypted = passwordEncoder.encode(newPassword);  // PasswordEncoder 주입 필요
        user.setPassword(encrypted);

        // 키와 유효 시간 초기화
        user.setResetPasswordKey(null);
        user.setResetPasswordLimitDt(null);
        userRepository.save(user);

        return true;
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
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<User> optionalUser = userRepository.findByUserIdAndUserName(
                parameter.getUserId(), parameter.getUserName());
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        User user = optionalUser.get();

        String uuid = UUID.randomUUID().toString();

        user.setResetPasswordKey(uuid);
        user.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        userRepository.save(user);

        String email = parameter.getUserId();
        String subject = "[mini-mart] 비밀번호 초기화 메일입니다. ";
        String text = "<p>[mini-mart] 비밀번호 초기화 메일입니다. </p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 하세요. </p>"
                + "<div><a target='_blank' href='http://localhost:8080/user/reset_password?id=" + uuid + "'> 비밀번호 초기화 링크 </a></div>";
        mailService.sendHtmlMail(email, subject, text);

        return true;
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encrypted = passwordEncoder.encode(newPassword);
            user.setPassword(encrypted);
            userRepository.save(user);
        }
    }

    @Override
    public void updatePhoneNumber(String userId, String newPhoneNumber) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPhoneNumber(newPhoneNumber);
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserId(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        return new UserPrincipal(optionalUser.get());
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}