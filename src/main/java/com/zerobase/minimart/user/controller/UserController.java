package com.zerobase.minimart.user.controller;

import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.model.UserParam;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

//    private final MailService mailService;

    private final UserService userService;

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(UserInput parameter, RedirectAttributes redirectAttributes) {
        boolean result = userService.signUp(parameter);

        if (result) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/user/login";
        } else {
            redirectAttributes.addFlashAttribute("signupSuccess", false);
            return "redirect:/user/signup";
        }
    }

    @RequestMapping("/login")
    public String login(UserInput parameter) {
        return "user/login";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userPrincipal) {

        String userId = userPrincipal.getUsername(); // 로그인된 유저 ID
        UserDto user = userService.info(userId);

        model.addAttribute("user", user);
        return "user/info";
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute UserInput userInput) {
        userService.updateUserInfo(userInput);
        return "redirect:/user/info"; // 또는 성공 페이지
    }

    @PostMapping("/applySeller")
    public String applySeller(@RequestParam String userId) {
        try {
            userService.applySeller(userId);
            return "redirect:/user/info?message=" + UriUtils.encode("판매자 신청이 완료되었습니다.", StandardCharsets.UTF_8);
        } catch (CustomException e) {
            return "redirect:/user/info?message=" + UriUtils.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }


//    @PostMapping("/signup")
//    public String signup(@RequestParam String userId) {
//        // 저장 생략...
//        mailService.sendSignupEmail(userId);
//        return "user/signup_complete";
//    }

//    @GetMapping("/email-auth")
//    public String emailAuth(@RequestParam String id) {
//        Optional<User> optionalUser = userRepository.findByEmailAuthKey(id);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setEmailAuthYn(true);
//            user.setEmailAuthDt(LocalDateTime.now());
//            userRepository.save(user);
//            return "이메일 인증이 완료되었습니다.";
//        }
//
//        return "유효하지 않은 인증 요청입니다.";
//    }

}
